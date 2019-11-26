# coding=utf-8
import torch
from torch import nn
import logging

from torch.utils.data import (DataLoader, RandomSampler, SequentialSampler,
                              TensorDataset)

from modes.pytorch.optimization import AdamZZW
from tqdm import tqdm, trange  # 用于进度条展示

logger = logging.getLogger(__name__)


class LinearMode(nn.Module):

    def __init__(self, in_features, out_features):
        super(LinearMode, self).__init__()
        self.is_print_forward_log = False  # 设置一个标志位，标识是否已经打印过前向传播Log
        self.out = nn.Linear(in_features, out_features, bias=True)
        self.loss = nn.MSELoss()

    def forward(self, inputs):
        train_batch_x = inputs['train_batch_x']  # [batch_size,in_features]
        train_batch_y = inputs['train_batch_y']  # [batch_size,out_features]
        output = self.out(train_batch_x)

        loss = self.loss(output, train_batch_y)

        # 如果没有打印过log的话，将Log打印出来看一下，以保证各项计算的size都是正确的
        if (not self.is_print_forward_log):
            self.is_print_forward_log = True
            logger.info("train_batch_x的size为：", train_batch_x.size())
            logger.info("train_batch_y的size为：", train_batch_y.size())
            logger.info("output的size为：", output.size())
        return output, loss


def train(train_dataset, model, device):
    # 训练样本初始化
    batch_size = 32  # 设置batch_size
    train_sampler = RandomSampler(train_dataset)  # 随机打乱，样本不允许重复
    train_dataLoader = DataLoader(train_dataset, sampler=train_sampler, batch_size=batch_size)

    # 初始化自定义的AdamZZW优化器
    optimizer = AdamZZW(model.parameters(), amsgrad=True)

    # train
    logger.info("开始训练")
    model.train()  # 将模型设置为训练模式
    model.zero_grad()
    num_train_epochs = 10
    epoch_iterator = trange(int(num_train_epochs), desc="EpochIterating")
    for epoch in epoch_iterator:
        batch_iterator = tqdm(train_dataLoader, desc="BatchIterating")
        for step, batch in enumerate(batch_iterator):
            batch = tuple(t.to(device) for t in batch)  # 将batch中的tensor数据转换成对应的device格式
            inputs = {
                'train_batch_x': batch[0],
                'train_batch_y': batch[1]
            }

            # 前向传播集计算是loss
            output, loss = model(inputs)

            # backward
            loss.backward()

            # GD
            optimizer.step()

            # 清空梯度
            optimizer.zero_grad()

            if (step % 100 == 0):
                logger.info("Train Epoch:{},iteration:{},Loss:{}".format(epoch, step, loss.item()))


def main():
    # 初始化device，如果Gpu可用用Gpu，否则用cpu
    device = torch.device("cuda" if torch.cuda.is_available() else "cpu")

    # 数据初始化
    train_num = 10000  # 训练样本数
    in_features = 5  # 输入特征
    out_features = 1  # 输出特征
    trainX = torch.randn(train_num, in_features)
    # print(trainX)
    trainY = trainX.sum_to_size(trainX.size()[0], 1)
    # print(trainY)

    # 样本数据数据，分组打乱
    train_dataset = TensorDataset(trainX, trainY)  # 只要传入的tensor的第一个维度是相同的即可

    # 模型初始化, 损失函数直接定义在模型中即可
    model = LinearMode(in_features, out_features)
    model.to(device)

    train(train_dataset, model, device)


if __name__ == '__main__':
    main()
