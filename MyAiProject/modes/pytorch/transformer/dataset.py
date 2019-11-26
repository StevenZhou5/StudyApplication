# coding=utf-8
"""通用的数据预处理操作"""

from collections import Counter  # 计数器

import jieba  # 中文分词
import nltk  # 引文分词
from tqdm import tqdm  # 用于进度条展示

import torch.utils.data.dataset
import numpy as np

PAD = 0
UNK = 1

PAD_POSITION = 0


def get_seq_split_list(data_dir):
    """
    数据预处理的第一步：读出文件，然后将句子分成字符串list
    :param data_dir: 需要进行句子分割及句子中词分割的文件路径
    :return:
    """
    source_lan = []  # 源语言 : 英语
    target_lan = []  # 目标语言 : 中文

    # 首先先将源语言与目标语言都转换成分词后的list
    # ['BOS', 'the', 'fat', 'is', 'in', 'the', 'fire', 'EOS']
    # ['BOS', '近在眼前', '的', '麻烦', 'EOS']
    with open(data_dir, mode='r') as file:
        # 将文本逐行读出
        line_iterator = tqdm(file, desc="逐行读取进行分词")
        for line in line_iterator:
            # time.sleep(0.1) # 测试读取进度
            line = line.strip().split("\t")  # 将每行数据按tab的空格进行分开,返回的是一个List
            # print(line) #['en', 'zh', 'the fat is in the fire', '近在眼前的麻烦']

            # 英文我们使用nltk进行分词
            source_seq_split = ["BOS"] + nltk.word_tokenize(line[2].lower()) + ["EOS"]
            # print(source_seq_split)  # ['BOS', 'the', 'fat', 'is', 'in', 'the', 'fire', 'EOS']
            source_lan.append(source_seq_split)

            # 中文我们使用jieba进行分词
            target_seq_split = ["BOS"] + [word for word in jieba.cut(line[3])] + ["EOS"]
            # print(target_seq_split) # ['BOS', '近在眼前', '的', '麻烦', 'EOS']
            target_lan.append(target_seq_split)

    return source_lan, target_lan


def build_vocab(seq_split_lists, max_words=50000):
    """
    构建词表
    :param seq_split_lists: 所有已经分好词的句子list的集合
    :param max_words: 最大的词表数目：按照每个词的出现次数排序；(实际最大可能是50002个，因为加入了["UNK"]和["PAD"])
    :return:  词表，词表中词的数量
    """
    word_counter = Counter()  # 用一个dict来记录不重复的词出现的次数:
    seq_iterator = tqdm(seq_split_lists, desc="构建词表")
    for seq in seq_iterator:  # 进行循环统计
        # time.sleep(0.1) # 测试进度
        # print(seq)  # ['BOS', 'the', 'fat', 'is', 'in', 'the', 'fire', 'EOS']
        for word in seq:
            word_counter[word] += 1  # 为出现的词加入dict，已出现的计数值+1

    # 将出现频率最高的前max_words个词统计出来
    top_words = word_counter.most_common(max_words)
    # print(len(top_words))
    # print(top_words)  # [('BOS', 187), ('EOS', 187), ('the', 47),...,('chip', 1), ('shoulder', 1)]

    # 构建词表
    vocab = {"PAD": PAD, "UNK": UNK}  # 先在词表中加入特殊字符
    special_word_count = len(vocab)  # 记录特殊字符的数目
    for idx, word in enumerate(top_words):
        # 注意只要word[0]:词就行了
        vocab[word[0]] = idx + special_word_count
    vocab_size = len(vocab)  # 真正的词表长度等于top_max的词加上特殊词的个数
    # print(vocab_size)
    # print(vocab)  # {'UNK': 0, 'PAD': 1, 'BOS': 2, 'EOS': 3, 'the': 4, "'s": 5, 'one': 6, 'in': 7...,'chip': 371, 'shoulder': 372}
    return vocab, vocab_size


def word_to_one_hot(source_lan, target_lan, source_vocab, target_vocab, sort_by_source_len=True):
    """
    将句子单词list中的每个单词转换成vocab中的one-hot的数字source_
    :param source_lan: 源语言句子分词构成的列表
    :param target_lan: 目标语言句子分词构成的列表
    :param source_vocab: 源语言的词表
    :param target_vocab: 目标语言的词表
    :param sort_by_source_len: 是否如按照源语言句子长度排序：默认为True；这个是为了尽量把句子长度差不多的句子分到同一个batch，以提高训练效果
    :return:
    """
    assert len(source_lan) == len(target_lan)  # 源语言的句子总数与目标语言的句子总数要一致

    # 把源语言的单词(source_lan)转换成源语言词表(source_vocab)对应的数字
    # 从source_lan取出每一句seq，在从每一句seq中取出每一个word，在把这个ward对应的source_vocab[word]one-hot值取出来
    out_source_lan = [[source_vocab[word] for word in seq] for seq in source_lan]
    # print(out_source_lan)  # [[2, 4, 94, 17, 7, 4, 34, 3], [2, 95, 3],...,[2, 368, 7, 369, 3], [2, 73, 7, 4, 370, 3], [2, 371, 10, 6, 5, 372, 3]]

    # 同上的操作进行目标语言的转换
    out_target_lan = [[target_vocab[word] for word in seq] for seq in target_lan]
    # print(out_target_lan)  # [[2, 53, 4, 54, 3], [2, 13, 4, 8, 55, 6, 3], [2, 56, 57, 3], [2, 22, 58, 14, 3],...,[2, 322, 3], [2, 323, 3], [2, 324, 3]]

    assert len(out_source_lan) == len(out_target_lan)

    # 如果不需要按照句子长度排序，则将结果返回
    if not sort_by_source_len:
        return out_source_lan, out_target_lan

    # 将源语言和目标语言都按照《源语言》的句子长度进行排序，以为后面分batch做准备
    def get_sorted_idx_list(seqs):
        # range(len(seqs)) 是[0,1，2，...,len(seqs)-1]的一个原始句子位置索引的未排序序列
        # 然后按照规则 x = len(seqs[x]) 对应位置索引的句子的长度从大到小对顺序的位置索引序列进行排序,并最终返回排序后的序列
        return sorted(range(len(seqs)), key=lambda x: len(seqs[x]))

    sorted_idx_list = get_sorted_idx_list(out_source_lan)  # 源语言句子长度从小到大的位置索引排序

    # 最终的列表中，out_source_lan 和 out_target_lan 都必须按照相同的规则进行排序
    out_sorted_source_lan = [out_source_lan[idx] for idx in sorted_idx_list]

    out_sorted_target_lan = [out_target_lan[idx] for idx in sorted_idx_list]

    # print(out_sorted_source_lan) #[[2, 95, 3], [2, 338, 3], [2, 21, 96, 3], [2, 101, 102, 3],...,[2, 92, 57, 320, 321, 6, 322, 8, 27, 3], [2, 47, 12, 9, 84, 16, 6, 262, 263, 264, 3]]
    # print(out_sorted_target_lan) #[[2, 13, 4, 8, 55, 6, 3], [2, 287, 288, 3], [2, 56, 57, 3],..., [2, 266, 267, 268, 3], [2, 47, 215, 216, 217, 3]]
    assert len(out_sorted_source_lan) == len(out_sorted_target_lan)
    return out_sorted_source_lan, out_sorted_target_lan


class TranslationDataset(torch.utils.data.Dataset):
    def __init__(self, data_dir):
        """
        自定义的torch的dataset;后面用torch.utils.data.DataLoader来进行构建batch
        :param data_dir: 训练数据的文件路径
        """
        # 得到句子的分词List
        source_lan, target_lan = get_seq_split_list(data_dir)

        # 构建单词表 ：key是单词，value是one-hot索引
        self.source_vocab_word2idx, self.source_vocab_size = build_vocab(source_lan)
        self.target_vocab_word2idx, self.target_vocab_size = build_vocab(target_lan)

        # 构建反向单词表：key是one-hot索引,value是单词
        self.source_vocab_idx2word = {idx: word for word, idx in self.source_vocab_word2idx.items()}
        # print(self.source_vocab_idx2word)#{0: 'PAD', 1:'UNK' , 2: 'BOS', 3: 'EOS', 4: 'the',...,370: 'middle', 371: 'chip', 372: 'shoulder'}
        self.target_vocab_idx2word = {idx: word for word, idx in self.target_vocab_word2idx.items()}
        # print(self.target_vocab_idx2word) #{0: 'PAD', 1: 'UNK', 2: 'BOS', 3: 'EOS', 4: '的',...,'板上钉钉', 323: '左右为难', 324: '怀恨在心'}

        # 将句子List中的词转换为one-hot的数字
        self.sorted_source_lan, self.sorted_target_lan = word_to_one_hot(source_lan, target_lan,
                                                                         self.source_vocab_word2idx,
                                                                         self.target_vocab_word2idx)

        assert len(self.sorted_source_lan) == len(self.sorted_target_lan)
        self.total_num_simples = len(self.sorted_source_lan)  # 这里需要统计出总样本数目

    def __len__(self):
        """
        这个方法也一定要重写，且返回这必须正确，__getitem__的参数：index就是由他决定的
        :return: 总样本数：即为总共的句子条数
        """
        return self.total_num_simples

    def __getitem__(self, index):
        """
        这个方法一定要重写，torch.utils.data.DataLoader就是通过这个方法来构建数据的；
        在下面的batch_collate_fn方法中接收的的参数one_batch_list其实就是这里的返回值的每个元素组成的
        :param index: 位置索引，总有最大值有上面的__len__(self)决定
        :return: 一个包含源语言与目标语言的元组数据:(self.sorted_source_lan[index], self.sorted_target_lan[index])
        """
        return self.sorted_source_lan[index], self.sorted_target_lan[index]


# 下面的方法是为了在torch.utils.data.DataLoader构建batch的时候对数据进行预处理的方法
def train_batch_collate_fn(one_batch_list):
    """
    在生成每一个batch之前我们先要把batch中所有的句子长度进行统一，然后加上位置编码信息
    :param one_batch_list: 一个完整的batch数据:包含源语言和目标语言数据
    :return: 句子长度统一，并且含有位置编码信息的完整batch数据
    """
    # print("one_batch_list:", one_batch_list) # [([2, 95, 3], [2, 13, 4, 8, 55, 6, 3]), ([2, 338, 3], [2, 287, 288, 3]),...,[2, 40, 160, 3]), ([2, 18, 210, 3], [2, 170, 3])]

    # 首先需要将source_lan_list ， target_lan_list 分别取出来
    source_lan_list, target_lan_list = list(zip(*one_batch_list))
    # print(source_lan_list)  # ([2, 95, 3], [2, 338, 3], [2, 21, 96, 3], [2, 101, 102, 3],...,[2, 75, 200, 3], [2, 76, 203, 3], [2, 18, 210, 3])
    # print("target_lan_list1:",target_lan_list) # ([2, 13, 4, 8, 55, 6, 3], [2, 287, 288, 3], [2, 56, 57, 3],...,[2, 158, 29, 14, 3], [2, 40, 160, 3], [2, 170, 3])

    # 将源语言的句子长度统一：找到最大长度的句子作为句子的最长长度；不够的用PAD索引补全
    source_lan_list = batch_collate_fn(source_lan_list)
    target_lan_list = batch_collate_fn(target_lan_list)

    result = (*source_lan_list, *target_lan_list)
    # ( 源语言句子列表，源语言位置编码，目标语言句子列表，目标语言位置编码）
    # print(result) # (tensor([[  2,  95,   3,   0],..),tensor([[1, 2, 3, 0],...), tensor([[  2,  13,   4,   8,  55,   6,   3],..), tensor([[1, 2, 3, 4, 5, 6, 7],..))
    return result


def batch_collate_fn(batch_seq_list):
    """
    句子长度按最长句子的长度补齐，添加绝对位置编码信息
    :param one_lan_list: 值包含一种语言的batch_list
    :return: 一个元组(补全后的列表，位置编码列表)
    """
    max_len = max(len(seq) for seq in batch_seq_list)  # 取出此batch中最长句子的长度
    # print("seq_max_len:", max_len)  # seq_max_len: 4

    batch_seq_list = np.array([seq + [PAD] * (max_len - len(seq)) for seq in batch_seq_list])
    # print("batch_seq_list：", batch_seq_list)  # [[  2  95   3   0] [  2 338   3   0]...[  2  76 203   3][  2  18 210   3]]

    # 采用绝对位置编码：如果word不是[PAD]其位置值 = 索引idx + 1；否则等于0
    batch_position_list = np.array(
        [[idx + 1 if [PAD] != word else PAD_POSITION for idx, word in enumerate(seq)] for seq in batch_seq_list])
    # print("batch_position_list：", batch_position_list)  # [[1 2 3 0] [1 2 3 0] ... [1 2 3 4] [1 2 3 4]]

    batch_seq_list = torch.LongTensor(batch_seq_list)
    batch_position_list = torch.LongTensor(batch_position_list)

    return batch_seq_list, batch_position_list
