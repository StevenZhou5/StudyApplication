# coding=utf-8
"""
Transformer model
"""
import torch
from torch import nn
import numpy as np

from modes.pytorch.transformer import dataset


class DecodeModel(nn.Module):
    """
    Decoder 模型:负责进行目标语言的Decoder处理
    """

    def __init__(self):
        super(DecodeModel, self).__init__()


class TransformerModel(nn.Module):
    """
    Transformer 翻译模型
    """

    def __init__(self):
        super(TransformerModel, self).__init__()
