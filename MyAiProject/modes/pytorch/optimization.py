# coding=utf-8

"""和PyTorch 相关的 optimization"""
import torch
from torch.optim import Optimizer
import math

from torch.optim.lr_scheduler import LambdaLR


class AdamZZW(Optimizer):
    r"""自定义Adam优化器(Adaptive Moment Estimation:自适应矩估计)
        Adam优化器的核心是利用指数加权平均数来进行梯度下降的自适应调整，增加对不同学习速率的鲁棒性
        使得即使在固定的学习速率的约束下:通过调整学习速率的权重值，使得在正确梯度方向上收敛的更快(学习速率权重>1)；
                                    在出现下抖动的时候解决抖动(学习速度权重<1)
        学习速率权重的指数加权平均数(有正有负，它的符号决定了下降的方向，正负相当于一阶段偏导的斜率的正负，和反向传播的dw本身的正负一致;
                                          它的绝对值正比于下降幅度，值越大下降幅度越大，值越小下降幅度越小:如果dw都是同方向的=>说明
                                          没有抖动现象=>此时每次参数更新都会更加接近收敛值=>学习速率无需减小=>绝对数值相对较大对更快收敛有帮助；
                                          如果dw是方向在来回变化的=>此时参数更新不能保证每次都更加接近收敛值=>学习速率需要减小以避免抖动=>有正
                                          有负使得绝对数值相对较小对解决抖动加快收敛也是有帮助的):
        Vdw = beta1 * Vdw + (1 - beta1) * dw ;
        Vdw-correct = Vdw / （1 -  beta1 ** t）； 在前几次平均值和真实值误差较大，采用此方法进行偏差修正，代表第t次(即为state['step'])参数更新
        学习速率权重的指数加权平均数的平方在开根号(始终为正，它的值反比与下降幅度:
                                             值越大说明=>方差大=>每次变化有快有慢，且变化幅度差距较大=>相对减小多一些学习速率可以使得收敛整体更加平稳；
                                             值越小说明=>方差小=>每次变化的绝对数值相差不大，变化幅度较小=>相对不需要减少太多的学习速率,收敛本身就是较为平稳的)
        Sdw = beta2 * Sdw + (1 - beta2) * (dw ** 2)
        Sdw-correct = Sdw / (1 - beta2 ** t)
        Sdw-correct = sqrt(Sdw-correct)

        real_dw = lr*(Vdw-correct/(Sdw-correct + eps)) # eps是一个很小的值，是为了防止分母为零的情况导致发散问题

        注意：betas=(0.9, 0.999)：
             beta1 = 0.9 代表取前面(1/(1-0.9) = 10)10个数的指数加权平均数；
             beta2 = 0.999 代表取前面(1/(1-0.999)=1000)1000个数的平方指数加权平均数；
             为什么要这么做呢：因为beta1决定下降方向和下降幅度，所以不能太多数的平均数，下降方向和之前很久以前的方向相关性不大，取太多反而会影响正确的下降方向；
                            beta2不能取太少，beta2是决定下降幅度的变化程度的(方差)，如果取得跟beta1一样，那么除非每次的dw的绝对值都相等，才能保证：
                            Vdw-correct/Sdw-correct等于1(如果考虑到eps则始终会小于1)；那么无论何时real_dw都会比计算的dw小，但是我们希望在没有抖动的时候
                            real_dw要大于dw才能加速收敛，所以这里的beta2的取值就不能太小

        参数说明:
            params: 模型中需要更新的参数；必须由一组可迭代的Tensor组成，一般传入模型需要更新的参数即可
            lr : 学习速率,默认1e-3
            betas : beta1和beta2的值，默认:(0.9, 0.999)
            eps : 防止除数为零的ε，默认1e-8，一般情况下不需要特殊修改
            weight_decay : 权重衰减值
            amsgrad : 是否使用 AMSGrad variant;默认不使用
    """

    def __init__(self, params, lr=1e-3, betas=(0.9, 0.999), eps=1e-8,
                 weight_decay=0, amsgrad=False):
        if not 0.0 <= lr:
            raise ValueError("learning rate 必须大于等于0: {}".format(lr))
        if not 0.0 <= eps:
            raise ValueError("epsilon 的值必须大于等于0: {}".format(eps))
        if not 0.0 <= betas[0] < 1.0:
            raise ValueError("beta1的值必须在[0,1]之间: {}".format(betas[0]))
        if not 0.0 <= betas[1] < 1.0:
            raise ValueError("beta2的值必须在[0,1]之间: {}".format(betas[1]))
        defaults = dict(lr=lr, betas=betas, eps=eps,
                        weight_decay=weight_decay, amsgrad=amsgrad)
        super(AdamZZW, self).__init__(params, defaults)

    def __setstate__(self, state):
        super(AdamZZW, self).__setstate__(state)
        for group in self.param_groups:
            group.setdefault('amsgrad', False)

    def step(self, closure=None):
        """
        每一步地图下降时需要进行的操作
        :param closure: 可选参数，一般不用传；
        :return: loss
        """
        loss = None
        if closure is not None:
            loss = closure()

        for group in self.param_groups:
            """
            # group 一定是一个字典：里面有梯度下降需要更新的模型参数
                                  在初始化Optimizer是设置的超参数
            """
            for p in group['params']:
                # p.data : 此时模型中的前向传播的权重值
                # p.grad : 求导计算出来的梯度
                if p.grad is None:
                    continue
                grad = p.grad.data
                if grad.is_sparse:
                    raise RuntimeError('Adam does not support sparse gradients, please consider SparseAdam instead')
                amsgrad = group['amsgrad']

                # state 存放梯度下降过程中需要缓存的值：如state['step']:第几次梯度下降
                state = self.state[p]

                # State 初始化
                if len(state) == 0:
                    state['step'] = 0
                    # 梯度值(dw)指数加权平均数的值
                    state['exp_avg'] = torch.zeros_like(p.data)
                    # 梯度值平方的指数加权平均值
                    state['exp_avg_sq'] = torch.zeros_like(p.data)
                    if amsgrad:
                        # Maintains max of all exp. moving avg. of sq. grad. values
                        state['max_exp_avg_sq'] = torch.zeros_like(p.data)

                exp_avg, exp_avg_sq = state['exp_avg'], state['exp_avg_sq']
                if amsgrad:
                    max_exp_avg_sq = state['max_exp_avg_sq']
                beta1, beta2 = group['betas']

                state['step'] += 1
                bias_correction1 = 1 - beta1 ** state['step']  # exp_avg的偏差修正项
                bias_correction2 = 1 - beta2 ** state['step']  # exp_avg_sq的偏差修正项

                if group['weight_decay'] != 0:  # 权重衰减值不为0时
                    grad.add_(group['weight_decay'],
                              p.data)  # grad矩阵tensor的值变为data权重矩阵tensor值+上group['weight_decay']权重衰减值

                exp_avg.mul_(beta1).add_(1 - beta1, grad)  # 计算exp_avg的指数加权平均值
                exp_avg_sq.mul_(beta2).addcmul_(1 - beta2, grad, grad)  # 计算exp_avg_sq的指数加权平均值
                if amsgrad:
                    # 将平方指数加权平均数更新并保持下来
                    # max_exp_avg_sq中的每个元素值将会取max_exp_avg_sq,exp_avg_sq中更大的值
                    torch.max(max_exp_avg_sq, exp_avg_sq, out=max_exp_avg_sq)
                    # 使用max_exp_avg_sq来计算分母项
                    denom = (max_exp_avg_sq.sqrt() / math.sqrt(bias_correction2)).add_(group['eps'])
                else:
                    # 使用exp_avg_sq计算分母项
                    denom = (exp_avg_sq.sqrt() / math.sqrt(bias_correction2)).add_(group['eps'])

                # 因为偏差修正项跟具体的dw无关，为了减少计算次数，所以这里直接在lr上进行的偏差修正项bias_correction1的计算
                # 当然偏差修正项bias_correction2也可以加到这里，这里可能是考虑到分母项上有group['eps']，且是在开根号之后才加上的，所以没有把bias_correction2拿到这里处理
                step_size = group['lr'] / bias_correction1

                # 参数更新
                p.data.addcdiv_(-step_size, exp_avg, denom)  # data - step_size * (exp_avg / denom)

        return loss


def get_linear_schedule_with_warmup(optimizer, num_warmup_steps, num_training_steps, last_epoch=-1):
    """ Create a schedule with a learning rate that decreases linearly after
    linearly increasing during a warmup period.
    """
    def lr_lambda(current_step):
        if current_step < num_warmup_steps:
            return float(current_step) / float(max(1, num_warmup_steps))
        return max(0.0, float(num_training_steps - current_step) / float(max(1, num_training_steps - num_warmup_steps)))

    return LambdaLR(optimizer, lr_lambda, last_epoch)