# coding=utf-8
"""
Encoder model
"""
import torch
from torch import nn
import numpy as np

from modes.pytorch.transformer import dataset


def get_sinusoid_embedding_position_matrix(max_seq_len, embedding_size, padding_idx=None):
    """
    Sinusoid position encoding table : 正弦型号未知编码矩阵
    :param max_seq_len: 最大句子长度(注意是全局最大句子长度，不是batch中最大句子长度)
    :param embedding_size: 位置编码输出的维度数，这个要和word的embedding_size的维数相同
    :param padding_idx: 如果有PAD的占位，那么这个PAD占位的one-hot编码是多少，如果给定，那么最后关于PAD的embedding编码将全为0
    :return: 一个size 为[max_seq_len,embedding_size]的embedding矩阵
    """

    def cal_angle(position_i, embedding_j):
        """
        embedding矩阵初始(i,j)元的值
        :param position_i: 矩阵的第i行：对应是是位置i
        :param embedding_j: 矩阵的第j列：对应的是位置i在第j个embedding维度上的坐标值
        :return: i/((10000^(2*(j//2))*embedding_size) ： 对于给定位置i不变，j越大值越小；对于给定j不变，i越大值越大
        """
        return position_i / np.power(10000, 2 * (embedding_j // 2) / embedding_size)

    def get_position_i_angle_vec(position_i):
        """
        获取第i行所有元素的值：即one-hot位置编码为i的初始embedding编码
        :param position_i: 第i行
        :return: 位置i的初始embedding编码
        """
        return [cal_angle(position_i, embedding_j) for embedding_j in range(embedding_size)]

    # 首先生成一个size 为[max_seq_len,embedding_size]的embedding矩阵，并初始化每一个(i,j)元
    sinusoid_matrix = np.array([get_position_i_angle_vec(pos_i) for pos_i in range(max_seq_len)])

    #
    sinusoid_matrix[:, 0::2] = np.sin(sinusoid_matrix[:, 0::2])  # dim 2i
    sinusoid_matrix[:, 1::2] = np.cos(sinusoid_matrix[:, 1::2])  # dim 2i+1

    # 将占位PAD的embedding值全部置为0
    if padding_idx is not None:
        # zero vector for padding dimension
        sinusoid_matrix[padding_idx] = 0.

    assert sinusoid_matrix.shape == (max_seq_len, embedding_size)
    return torch.FloatTensor(sinusoid_matrix)


class SelfAttentionLayer(nn.Module):
    """
    SelfAttention layer:
    """

    def __init__(self, temperature, self_attention_dropout=0.1):
        """
        :param temperature: "温度"：用于调整句子中某个词对句子中其他词的关注度占比：T越大那些原先占比小的就会有越大的关注度占比
        :param self_attention_dropout:
        """
        super(SelfAttentionLayer, self).__init__()

        # 在计算完q,k矩阵的点积之后要除以一个temperature，然后在进行softmax，不然一开始Attention值较小的在softmax之后权重会趋于0，这不利于模型的学习
        # 这个值跟知识蒸馏中的temperature含义一样(T必须大于1，T越小只能提取出权重占比越大的特征，二占比小的特征将"蒸"不出来，T过大又会把无关痛痒的特征也"蒸"出来，干扰模型的预测)
        # tensor([0.8000, 0.1000, 0.0800, 0.0200]) :
        # 温度为1时: tensor([0.4095, 0.2034, 0.1993, 0.1877])
        # 温度为2时: tensor([0.3247, 0.2288, 0.2266, 0.2199])
        # 温度为0.5时： tensor([0.5904, 0.1456, 0.1399, 0.1241])
        self.temperature = temperature

        self.dropout = nn.Dropout(self_attention_dropout)

        # input_size:[num_head * batch_size,batch_seq_len,batch_seq_len]  output_size:[num_head * batch_size,batch_seq_len,batch_seq_len]
        self.softmax = nn.Softmax(dim=2)

    def forward(self, q, k, v, mask=None):
        """

        :param q: [num_head * batch_size,batch_seq_len,q_k_out_dim]
        :param k: [num_head * batch_size,batch_seq_len,q_k_out_dim]
        :param v: [num_head * batch_size,batch_seq_len,v_out_dim]
        :param mask: [num_head * batch_size,batch_seq_len,batch_seq]
        :return:
        """
        # input: q:[num_head * batch_size,batch_seq_len,q_k_out_dim];  k:[num_head * batch_size,batch_seq_len, q_k_out_dim]
        # output: [num_head * batch_size,batch_seq_len,batch_seq_len]
        attention = torch.bmm(q, k.transpose(1, 2))

        attention = attention / self.temperature  # [num_head * batch_size,batch_seq_len,batch_seq_len]

        if mask is not None:
            # mask 如果是一个size和attention完全一样的上三角矩阵：主对角线一下全为零，其余位置全为1；这样的目的是为了让句子中每个词只注意一下它前面的词
            # masked_fill会把mask中为1的位置对应于attention矩阵中值都置为 - np.inf
            # 为什么是 负无穷而不是0是因为后面在进行softmax的时候要执行exe操作(e^x次幂):当x=负无穷时，
            attention = attention.masked_fill(mask, -np.inf)  # -np.inf代表负无穷

        # 进行softmax:得到每个词应该对其他句子中所有值产生的注意力权重（0-1）
        attention = self.softmax(attention)  # [num_head * batch_size,batch_seq_len,batch_seq_len]
        # dropout操作
        attention = self.dropout(attention)  # [num_head * batch_size,batch_seq_len,batch_seq_len]

        # 最终输出加了selfAttention的v矩阵
        # input:attention: [num_head * batch_size,batch_seq_len,batch_seq_len]; v :[num_head * batch_size,seq_len, v_out_dim]
        output = torch.bmm(attention, v)  # [num_head * batch_size, seq_len,  v_out_dim]

        return output, attention


class MultiHeadAttentionLayer(nn.Module):
    """
    使用num_head个q，k,v计算出num_head个value值，然后在将他们连接起来，然后在进行全连接
    注意：(1)embedding_dim要和word embedding输出的维度一致; (2) q,k的输出维度要一致；(3)q,k,v的输入维度都要是embedding_dim
    """

    def __init__(self, num_head, embedding_dim, q_k_out_dim, v_out_dim, dropout=0.1):
        """
        :param num_head: 需要多少的head，一个head对应的就是某一种关注关系(比如:主谓之间的关系；代词的指代关系)
        :param embedding_dim: one-hot的词与位置编码被embedding后的维度,在进行全连接变换后的维度，如果不做变换就等于embedding_dim
        :param q_k_out_dim: q，k的输出维度必须一致
        :param v_out_dim: v 的输出维度
        :param dropout:
        """
        super(MultiHeadAttentionLayer, self).__init__()

        self.num_head = num_head
        self.q_k_out_dim = q_k_out_dim
        self.v_out_dim = v_out_dim

        # 通过word embedding + pos embedding之后的句子信息作为输入，进行q,k,v的计算
        # 针对q，k的输入与输出维度一致：input size:[batch_size,batch_seq_len,embedding_dim]; output size:[batch_size,batch_seq_len,num_head * q_k_out_dim]
        self.w_q = nn.Linear(embedding_dim, num_head * q_k_out_dim)
        self.w_k = nn.Linear(embedding_dim, num_head * q_k_out_dim)
        # 使用正太分布进行权重的初始化: 大部分的值会分布在[mean -std,mean+std]之间
        q_k_std = np.sqrt(2.0 / (embedding_dim + q_k_out_dim))
        nn.init.normal_(self.w_q.weight, mean=0.0, std=q_k_std)  # mean是正太分布的均值，std是正太分布的标准差
        nn.init.normal_(self.w_k.weight, mean=0.0, std=q_k_std)

        # 针对v的input size与q,k一致，output size:[batch_size,seq_len,num_head * v_out_dim]
        self.w_v = nn.Linear(embedding_dim, num_head * v_out_dim)
        v_std = np.sqrt(2.0 / (embedding_dim + v_out_dim))
        self.w_v.weight.data.normal_(mean=0.0, std=v_std)  # 跟上面nn.init.normal_的效果一样

        # selfAttention Layer
        temperature = np.power(q_k_out_dim, 0.5)
        self.attention = SelfAttentionLayer(temperature)  ## [batch_size, seq_len, num_head * v_out_dim]

        # 最后进行全连接降维，因为后面会有多层MultiHeadAttentionLayer,所以降维的维度要保持和MultiHeadAttentionLayer的输入维度一样：即为embedding_dim
        self.fc = nn.Linear(num_head * v_out_dim, embedding_dim)
        nn.init.xavier_normal_(self.fc.weight)  # 权重初始化

        self.dropout = nn.Dropout(dropout)

        # 最后的输出要做下norm
        self.layer_norm = nn.LayerNorm(embedding_dim)

    def forward(self, input_q, input_k, input_v, mask=None):
        """
        一般情况下的input_q，input_k,input_v三者是一样的在最开始就是word_embedding+position_embedding之后的源语言的数据；后面就是每层EncoderLayer的输出
        :param input_q:  [batch_size,batch_seq_len,embedding_dim]
        :param input_k: [batch_size,batch_seq_len,embedding_dim]
        :param input_v: [batch_size,batch_seq_len,embedding_dim]
        :param mask: [batch_size,batch_seq_len,batch_seq_len] :为0的位置是要保留的注意力权重，为1的是要mask掉的
        :return: [batch_size,batch_seq_len,embedding_dim] 加上了多个head方式的的self-Attention之后的value值：
                输出之所以后面的维度为embedding_dim，是为了后面进行多层的EncoderLayer(当然embedding_dim也可以做一层全连接降维)
        """
        assert input_q.size() == input_k.size() == input_v.size()  # size相同
        batch_size = input_q.size()[0]
        batch_seq_len = input_q.size()[1]
        q_k_out_dim = self.q_k_out_dim
        v_out_dim = self.v_out_dim
        num_head = self.num_head

        # 原始的输入：在第一次的时候就是word_embedding+position_embedding之后的源语言的数据；后面就是每层EncoderLayer的输出
        input_original = input_q  # [batch_size,batch_seq_len,embedding_dim]

        # 计算q
        # 首先进过全连接计算出MultiHead的q矩阵：output的size: [batch_size, batch_seq_len,num_head,q_k_out_dim]
        q = self.w_q(input_q).view(batch_size, batch_seq_len, num_head,
                                   q_k_out_dim)  # [batch_size, batch_seq_len,num_head,q_k_out_dim]
        # 将计算出的q进行size形状的调整,调整后的output的size：# [(num_head* batch_size),batch_seq_len,q_k_out_dim]
        # contiguous()是为了把进过permute操作后物理地址不连续的tensor的物理地址重写变成连续的，以加速和方便后面的运算
        q = q.permute(2, 0, 1, 3).contiguous().view(-1, batch_seq_len,
                                                    q_k_out_dim)  # [num_head * batch_size,batch_seq_len,q_k_out_dim]

        # 同上计算出k
        k = self.w_k(input_k).view(batch_size, batch_seq_len, num_head,
                                   q_k_out_dim)  # [batch_size,batch_seq_len,num_head,q_k_out_dim]
        k = k.permute(2, 0, 1, 3).contiguous().view(-1, batch_seq_len,
                                                    q_k_out_dim)  # [num_head * batch_size, batch_seq_len,q_k_out_dim]

        # 同上计算出v
        v = self.w_v(input_v).view(batch_size, batch_seq_len, num_head,
                                   v_out_dim)  # [batch_size,batch_seq_len,num_head,v_out_dim]
        v = v.permute(2, 0, 1, 3).contiguous().view(-1, batch_seq_len, v_out_dim)

        # 对mask的Tensor进行num_head的repeat操作:维度0 repeat上num_head次，其他维度不repeat
        mask = mask.repeat(num_head, 1, 1)  # [num_head * batch_size, batch_seq_len, batch_seq_len]
        # 计算出self-Attention的value值 和 Attention矩阵
        # output:[num_head * batch_size,batch_seq_len,v_out_dim]
        # attention : [num_head * batch_size,batch_seq_len,batch_seq_len]
        output, attention = self.attention(q, k, v, mask=mask)

        # 把output的维度进行调整，使其最终size为:[batch_size,batch_seq_len,num_head * v_out_dim]
        output = output.view(num_head, batch_size, batch_seq_len,
                             v_out_dim)  # [num_head , batch_size,batch_seq_len,v_out_dim]
        output = output.permute(1, 2, 0, 3).contiguous().view(batch_size, batch_seq_len,
                                                              -1)  # [batch_size,batch_seq_len,num_head * v_out_dim]

        # 进行全连接操作，进行降维
        output = self.dropout(self.fc(output))  # [batch_size,batch_seq_len,embedding_dim]

        # 这个把原始的数据加上相当于传统注意力机制
        output = output + input_original  # [batch_size,batch_seq_len,embedding_dim]

        # 最后要把输出做做下norm
        output = self.layer_norm(output)

        return output, attention


class EncoderLayer(nn.Module):
    """
    在word embedding + pos embedding之后的size为[batch_size,batch_seq_len,embedding_size]的tensor作为输入
    通过EncoderLayer 最终输出
    """

    def __init__(self, embedding_dim):
        super(EncoderLayer, self).__init__()
        #todo 
        #


class EncoderModel(nn.Module):
    """
    Encoder 模型:负责进行源语言的Encode处理
    """

    def __init__(self, num_vocab_size, embedding_dim, max_seq_len, num_encoder_layers, use_absolute_position=False, ):
        super(EncoderModel, self).__init__()

        # 首先进行word embedding操作:num_vocab_size:one-hot词表的个数，embedding_size：需要embedding表示的维数，
        # padding_idx：在one-hot中占位符PAD的one-hot值，给定的话将会始终将PAD的embedding表示的权重始终置为零
        # input_size:[batch_size,batch_seq_length];  output_size:[batch_size,batch_seq_length,embedding_dim]
        self.word_embeddings = nn.Embedding(num_vocab_size, embedding_dim, padding_idx=dataset.PAD)

        # position embedding
        # 注意这里的max_seq_len不是值得bat中最长的句子长度，而是用户传入的一个超参数：
        # 它可以是所有源语言中句子最长的那个句子长度(加上开启和结束以及其他标志的句子长度)，也可以是源语言和目标语言中所有句子中最长的长度，或者是比他们更大的数(取决于正式翻译场景中的值，或者是性能较优的值)
        # input_size:[batch_size,batch_seq_length]; output_size:[batch_size,batch_seq_length,embedding_dim]
        if use_absolute_position:  # 使用绝对位置编码: 虽然是绝对位置编码，但是embedding的值任然是要梯度下降来调整的
            self.position_embeddings = nn.Embedding(max_seq_len, embedding_dim, padding_idx=dataset.PAD_POSITION)
        else:  # 使用相对位置编码：虽然是相对位置编码，单这个是通过人为设计的原则来计算每一个位置的embedding值的，不需要通过梯度下降来调整
            self.position_embeddings = nn.Embedding.from_pretrained(get_sinusoid_embedding_position_matrix(),
                                                                    freeze=True)

        # 下面就要进行多层的EncoderLayer
        self.encoder_layers = nn.ModuleList([
            EncoderLayer()
            for _ in range(num_encoder_layers)
        ])

    def forward(self):
        return
