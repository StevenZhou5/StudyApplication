#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import numpy as np

from init_data import init_cat_data
from g_function import sigmoid, tanh, sigmoidDZ, tanhDZ

# 初始化数据：
TrainX, TrainY, TestX, TestY = init_cat_data()
m = TrainX.shape[1]  # 样本数量
L = 2  # 神经网络的层数


# ======================================================= 正向传播 ======================================================
# 正向传播的通用公式
def forward_propagation(l, preA, W, B):
    """
    :param l: 当前是第几层
    :param PreA: A^[l-1],上一层的计算结果A
    :param W: W^[l],当前要计算层的权重
    :param B: B^[l],当前要计算层的常量偏差
    :return: Z^[l]:反向传播时使用
             A^[l]:作为下一层的输入值
    """
    pren = preA.shape[0]  # 上一层神经元的个数
    n = W.shape[0]  # 当前神经元的个数
    Z = np.dot(W, preA) + B
    assert Z.shape == (n, m)
    if l == L:
        A = sigmoid(Z)
    else:
        A = tanh(Z)
    assert A.shape == (n, m)
    return Z, A


# ======================================================= 反向传播 ======================================================
def backward_propagation(l, A, dA, Z, W, preA):
    """
    反向传播
    :param l: 当前是第几层
    :param A: l层的A
    :param dA: l层的dA：输出值的关于下一层的偏导/斜率
    :param Z:  l层的Z：激活函数的参数:有些激活函数的求dZ需要用到它，如ReLU，有些不需要
    :param W: l层的W：关于上一层输入值的各个权重值
    :param preA:
    :return: dW:l层的权重W梯度下降值：l层的W关于最终cost函数的偏导
             db:l层的偏差b梯度下降值: l层的b关于最终cost函数的偏导
             dpreA：l-1层的关于l层的对A求偏导/斜率
    """
    pren = preA.shape[0]
    n = A.shape[0]  # 当前神经元的个数
    if (l == L):
        dZ = np.multiply(dA, sigmoidDZ(A))
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


# ======================================================= 梯度递减 ======================================================
def update_parameters(parameters, grads, learning_rate=0.2):
    W1 = parameters["W1"]
    B1 = parameters["B1"]
    W2 = parameters["W2"]
    B2 = parameters["B2"]

    dw1 = grads["dw1"]
    db1 = grads["db1"]
    dW2 = grads["dw2"]
    db2 = grads["db2"]

    W1 = W1 - learning_rate * dw1
    assert W1.shape == (n1, n0)
    B1 = B1 - learning_rate * db1
    assert B1.shape == (n1, 1)
    W2 = W2 - learning_rate * dW2
    assert W2.shape == (n2, n1)
    B2 = B2 - learning_rate * db2
    assert B2.shape == (n2, 1)

    parameters = {"W1": W1,
                  "B1": B1,
                  "W2": W2,
                  "B2": B2}
    return parameters


# 第0层:输入层
n0 = TrainX.shape[0]
A0 = TrainX

# 第1层
n1 = 3  # 第一层有三个神经元
W1 = np.random.randn(n1, n0) * 0.01  # 初始权重要尽可能小，不然在sigmoid和tanh作为激活函数是Z值过大，训练效率低
B1 = np.zeros((n1, 1))
Z1, A1 = forward_propagation(1, TrainX, W1, B1)
print("A1.shape:", A1.shape)

# 第2层
n2 = 1
W2 = np.random.randn(n2, n1) * 0.01
B2 = np.zeros((n2, 1))
Z2, A2 = forward_propagation(2, A1, W2, B2)
print("A2.shape:", A2.shape)

# 第2层：输出层
dA2 = np.divide(-TrainY, A2) + np.divide(1 - TrainY, 1 - A2)
dw2, db2, dA1 = backward_propagation(2, A2, dA2, Z2, W2, A1)
print("dA2.shape:", dA2.shape)
print("dW2.shape:", dw2.shape)
print("db2.shape:", db2.shape)

# 第1层
dw1, db1, dA0 = backward_propagation(1, A1, dA1, Z1, W1, A0)
print("dA1.shape:", dA1.shape)
print("dw1.shape:", dw1.shape)
print("db1.shape:", db1.shape)

# def coumpute_cost(Y,predict):



def run(TrainX, TrainY, TestX, TestY, num_iterations=10000, learning_rate=0.02, print_cost=False):
    parameters = {"W1": W1,
                  "B1": B1,
                  "W2": W2,
                  "B2": B2}

    for i in range(num_iterations):
        # 正向传播
        Z1, A1 = forward_propagation(1, A0, W1, B1)
        Z2, A2 = forward_propagation(2, A1, W2, B2)

        # 反向传播
        dW2, db2, dA1 = backward_propagation(2, A2, dA2, Z2, W2, A1)
        dw1, db1, dA0 = backward_propagation(1, A1, dA1, Z1, W1, A0)

        grads = {"dw1": dw1,
                 "db1": db1,
                 "dw2": dw2,
                 "db2": db2}

        # 梯度递减，参数更新
        parameters = update_parameters(parameters, grads, learning_rate)
