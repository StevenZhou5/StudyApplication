#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import numpy as np
from gradient_check_testCases import *
from gc_utils import *


# 多维多层的神经网络的梯度检验测试：利用双边误差检验偏导计算是否正确

def forward_propagation(X, Y, parameters):
    """
    3层的前项传播
    :param X:
    :param Y:
    :param parameters:
    :return:
    """
    m = X.shape[1]
    W1 = parameters["W1"]
    b1 = parameters["b1"]
    W2 = parameters["W2"]
    b2 = parameters["b2"]
    W3 = parameters["W3"]
    b3 = parameters["b3"]

    # LINEAR -> RELU -> LINEAR -> RELU -> LINEAR -> SIGMOID
    Z1 = np.dot(W1, X) + b1
    A1 = relu(Z1)
    Z2 = np.dot(W2, A1) + b2
    A2 = relu(Z2)
    Z3 = np.dot(W3, A2) + b3
    A3 = sigmoid(Z3)

    logprobs = np.multiply(-np.log(A3), Y) + np.multiply(-np.log(1 - A3), 1 - Y)
    cost = 1. / m * np.sum(logprobs)

    cache = (Z1, A1, W1, b1, Z2, A2, W2, b2, Z3, A3, W3, b3)

    return cost, cache


def backward_propagation(X, Y, cache):
    """
    三层神经网络的反向传播
    :param X:
    :param Y:
    :param cache:
    :return:
    """
    m = X.shape[1]
    (Z1, A1, W1, b1, Z2, A2, W2, b2, Z3, A3, W3, b3) = cache

    dZ3 = A3 - Y
    dW3 = 1. / m * np.dot(dZ3, A2.T)
    db3 = 1. / m * np.sum(dZ3, axis=1, keepdims=True)

    dA2 = np.dot(W3.T, dZ3)
    dZ2 = np.multiply(dA2, np.int64(A2 > 0))
    dW2 = 1. / m * np.dot(dZ2, A1.T)  # ~~
    db2 = 1. / m * np.sum(dZ2, axis=1, keepdims=True)

    dA1 = np.dot(W2.T, dZ2)
    dZ1 = np.multiply(dA1, np.int64(A1 > 0))
    dW1 = 1. / m * np.dot(dZ1, X.T)
    db1 = 1. / m * np.sum(dZ1, axis=1, keepdims=True)  # ~~

    gradients = {"dZ3": dZ3, "dW3": dW3, "db3": db3,
                 "dA2": dA2, "dZ2": dZ2, "dW2": dW2, "db2": db2,
                 "dA1": dA1, "dZ1": dZ1, "dW1": dW1, "db1": db1}

    return gradients


# 简单的线性函数的双边误差检验对theta(权重)的求导是否正确
def gradient_check_test(parameters, gradients, X, Y, epsilon=1e-7):
    """
    :param parameters: 此时的W和B
    :param gradients: 反向传播计算出来的正式梯度（斜率/W和B的偏导数：dW和db）
    :param X:  训练数据的输入
    :param Y:  训练数据的真实输出
    :param epsilon:
    :return:
    """

    # 计算梯度（斜率/W和B的偏导数：dW和db）近似值
    big_theta_vector, _ = dictionary_to_vector(parameters)  # 将W和b矩阵变成一个大向量
    big_theta_vector_num = big_theta_vector.shape[0]  # 大向量的向量个数
    J_plus = np.zeros((big_theta_vector_num, 1))
    J_minus = np.zeros((big_theta_vector_num, 1))
    grad_approx = np.zeros((big_theta_vector_num, 1))  # 梯度（斜率/W和B的偏导数：dW和db）近似值

    for i in range(big_theta_vector_num):  # 这层循环是为了单独计算每一个小w和小b的双边误差值，
        theta_plus = np.copy(
            big_theta_vector)  # 每一个每一个小w和小b的双边误差值计算时，其他权重变量必须保持不变，所以要用深度复制，改变theta_plus 的值不会影响big_theta_vector
        theta_plus[i][0] = theta_plus[i][0] + epsilon
        J_plus[i], _ = forward_propagation(X, Y, vector_to_dictionary(theta_plus))

        theta_minus = np.copy(big_theta_vector)  # 深度复制，改变theta_minus 的值不会影响big_theta_vector
        theta_minus[i][0] = theta_minus[i][0] - epsilon
        J_minus[i], _ = forward_propagation(X, Y, vector_to_dictionary(theta_minus))

        grad_approx[i][0] = (J_plus[i] - J_minus[i]) / (2 * epsilon)

    # 反向传播中梯度（斜率/W和B的偏导数：dW和db）的真实值
    grad = gradients_to_vector(gradients)

    # 计算近似值和反向传播求出的正式导数误差
    numerator = np.linalg.norm(grad - grad_approx)  # 分子：计算值与近似值差值的绝对值
    denominator = np.linalg.norm(grad) + np.linalg.norm(grad_approx)  # 分母：计算值的绝对值 + 近似值的绝对值
    difference = numerator / denominator
    if difference < 2e-7:
        print("方向传播的求导正确")
    elif difference < 1e-5:
        print("方向传播的求导可能不正确，请注意排查问题!")
    else:
        print("方向传播的求导不正确，请排查问题!")

    return difference


if __name__ == "__main__":
    X, Y, parameters = gradient_check_n_test_case()

    cost, cache = forward_propagation(X, Y, parameters)
    gradients = backward_propagation(X, Y, cache)

    difference = gradient_check_test(parameters, gradients, X, Y)

    print(difference)
