#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import numpy as np


# 激活函数
def liner(Z):
    return Z


def sigmoid(Z):
    """
    激活函数sigmoid：只有在输出层结果表示结果为（0-1）的分类/概率问题时作为输出层的激活函数，否则其他情况下都不使用
    特点：可以解释，比如将0-1之间的取值解释成一个神经元的激活率（firing rate）
    缺陷：1：有饱和区域，是软饱和，在大的正数和负数作为输入的时候，梯度就会变成零，使得神经元基本不能更新(斜率太低，梯度下降太慢)。
         2：只有正数输出（不是zero-centered），这就导致所谓的zigzag现象；
         3 . 计算量大（exp）
    :param Z: 横轴
    :return: (0-1)的概率值
    """
    s = 1 / (1 + np.exp(-Z))
    return s


def linerDZ():
    return 1


def sigmoidDZ(A):
    """
    激活函数sigmoid对Z求导
    :param A:
    :return:
    """
    sigmoidDZ = np.multiply(A, 1 - A)
    return sigmoidDZ


def tanh(Z):
    """
    tanh函数：sinhx/coshx,出来在（0-1）输入层的情况下，任何时候tanh函数都比sigmoid函数号
    特点：均值更加接近与0，对于下一层输入更加优于sigmoid函数
    :param Z:
    :return:（-1，1）
    """
    t = (np.exp(Z) - np.exp(-Z)) / (np.exp(Z) + np.exp(-Z))
    return t


def tanhDZ(A):
    """
    tanh函数对Z求导
    :param A:
    :return:
    """
    tanhDZ = 1 - np.power(A, 2)
    return tanhDZ


def ReLU(Z):
    """
    f(x) = max(0,x)
    ReLU(Rectified Linear Unit:修正的线性单元):
    CNN中常用。对正数原样输出，负数直接置零。
    在正数不饱和，在负数硬饱和。relu计算上比sigmoid或者tanh更省计算量，因为不用exp，因而收敛较快。
    但是还是非zero-centered。
    relu在负数区域被kill的现象叫做dead relu，这样的情况下，有人通过初始化的时候用一个稍微大于零的数比如0.01来初始化神经元，
    从而使得relu更偏向于激活而不是死掉，但是这个方法是否有效有争议。
    特点：计算量小，训练速度快
    缺陷：在值为负是函数斜率为零，激活失效，梯度下降无效
    :param Z:
    :return:
    """
    r = np.maximum(0, Z)
    return r


def ReLUDZ(Z):
    """
     ReLU函数对Z求导:
    :param Z:
    :return:
    """
    # Z[Z > 0] = 1  # 如果Z大于等于0，那么导数即斜率为1
    # Z[Z < 0] = 0  # 如果Z小于0，那么导数即斜率为0
    ReLUDZ = np.int64(Z > 0)
    return ReLUDZ


def Leaky_ReLU(Z):
    """
    f(x) = max(0.01x,x)
    为了解决上述的dead ReLU现象。这里选择一个数，让负数区域不在饱和死掉。这里的斜率都是确定的。
    :param Z:
    :return:
    """
    lr = np.maximum(0.01 * Z, Z)
    return lr


def Leaky_ReLUDZ(Z):
    """
     Leaky_ReLU函数对Z求导:
    :param Z:
    :return:
    """
    Z[Z >= 0] = 1  # 如果Z大于等于0，那么导数即斜率为1
    Z[Z < 0] = 0.01  # 如果Z小于0，那么导数即斜率为0.01
    Leaky_ReLUDZ = Z
    return Leaky_ReLUDZ


def PReLU(Z, a):
    """
    f(x) = max(ax,x)
    负数域的斜率（梯度下降速度）可控
    :param Z:
    :param a: 负数曲线的斜率值
    :return:
    """
    pr = np.maximum(a * Z, Z)
    return pr


def ELU(Z):
    """
    f(x) = {x(if X>0); α(exp(x))
    具有relu的优势，且输出均值接近零，实际上prelu和LeakyReLU都有这一优点。有负数饱和区域，从而对噪声有一些鲁棒性（健壮性：针对异常情况可以正常运行）。
    可以看做是介于relu和LeakyReLU之间的一个东西。当然，这个函数也需要计算exp，从而计算量上更大一些。
    特点：对于异常(噪音)的鲁棒性(健壮性)好；
    缺点：计算量较大
    :param Z:
    :return:
    """


def Maxout(Z):
    """
    maxout是通过分段线性函数来拟合所有可能的凸函数来作为激活函数的，但是由于线性函数是可学习，
    所以实际上是可以学出来的激活函数。具体操作是对所有线性取最大，也就是把若干直线的交点作为分段的界，然后每一段取最大。
    maxout可以看成是relu家族的一个推广。
    缺点在于增加了参数量。
    :param Z:
    :return:
    """


# ======================================================= 正向传播 ======================================================
# 正向传播的通用公式
def forward_propagation(preA, W, B, g_fun_type=1):
    """
    :param PreA: A^[l-1],上一层的计算结果A
    :param W: W^[l],当前要计算层的权重
    :param B: B^[l],当前要计算层的常量偏差
    :param g_fun_name: 激活函数名称
    :return: Z^[l]:反向传播时使用
             A^[l]:作为下一层的输入值
    """
    m = preA.shape[1]
    pren = preA.shape[0]  # 上一层神经元的个数
    n = W.shape[0]  # 当前神经元的个数
    Z = np.dot(W, preA) + B
    assert Z.shape == (n, m)
    if g_fun_type == 0:
        A = sigmoid(Z)
    elif g_fun_type == 1:
        A = tanh(Z)
    elif g_fun_type == 2:
        A = ReLU(Z)
    elif g_fun_type == 3:
        A = Leaky_ReLU(Z)
    elif g_fun_type == 4:
        A = liner(Z)
    else:
        A = tanh(Z)
    assert A.shape == (n, m)
    return Z, A


# ======================================================= 反向传播 ======================================================
def backward_propagation(A, dA, Z, W, preA, g_funDZ_type=1):
    """
    反向传播
    :param A: l层的A
    :param dA: l层的dA：输出值的关于下一层的偏导/斜率
    :param Z:  l层的Z：激活函数的参数:有些激活函数的求dZ需要用到它，如ReLU，有些不需要
    :param W: l层的W：关于上一层输入值的各个权重值
    :param preA:
    :param g_funDZ_name: 激活函数g(Z)对Z求导的导函数
    :return: dW:l层的权重W梯度下降值：l层的W关于最终cost函数的偏导
             db:l层的偏差b梯度下降值: l层的b关于最终cost函数的偏导
             dpreA：l-1层的关于l层的对A求偏导/斜率
    """
    m = A.shape[1]
    pren = preA.shape[0]
    n = A.shape[0]  # 当前神经元的个数
    if g_funDZ_type == 0:
        dZ = np.multiply(dA, sigmoidDZ(A))
    elif g_funDZ_type == 1:
        dZ = np.multiply(dA, tanhDZ(A))
    elif g_funDZ_type == 2:
        dZ = np.multiply(dA, ReLUDZ(Z))
    elif g_funDZ_type == 3:
        dZ = np.multiply(dA, Leaky_ReLUDZ(Z))
    elif g_funDZ_type == 4:
        dZ = np.multiply(dA, linerDZ())
    else:
        dZ = np.multiply(dA, tanhDZ(A))
    assert dZ.shape == (n, m)

    dW = (1 / m) * np.dot(dZ, preA.T)
    assert dW.shape == (n, pren)

    db = (1 / m) * np.sum(dZ, axis=1, keepdims=True)
    assert db.shape == (n, 1)

    predA = np.dot(W.T, dZ)
    assert predA.shape == (pren, m)

    return dW, db, predA
