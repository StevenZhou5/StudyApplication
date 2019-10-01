#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import numpy as np
# 这个库是用来画图的

from init_data import init_cat_data
from common.baseutil.g_function import forward_propagation, backward_propagation
from common.baseutil.plat_function import plt_costs

""" 深度神经网络DNN(Deep Neural Networks)模型:"""


# 根据神经网络的层数，初始化每一层的权重矩阵W和常量偏移矩阵B
def init_params_deep_network(layer_cell_counts):
    '''
    :param layer_cell_counts:  一个存放了每层神经元个数的数组，输入层的神经元个数是数组的第一个位置
    :param layer_keep_prob: 每一层保持激活状态的神经元占比
    :return: 每层神经元的权重矩阵和常量偏移矩阵组成的字典
    '''

    params = {}
    L = len(layer_cell_counts) - 1  # 神经网络层数：包含输入层
    for l in range(1, L + 1):  # 从1开始是因为输入层没有权重矩阵，
        # randn会生成负值，而rand只有正的值
        params['W' + str(l)] = np.random.randn(layer_cell_counts[l], layer_cell_counts[l - 1]) / np.sqrt(
            layer_cell_counts[l - 1])  # 初始权重要尽可能小，不然在sigmoid和tanh作为激活函数是Z值过大，训练效率低
        params['B' + str(l)] = np.zeros((layer_cell_counts[l], 1))
        assert params['W' + str(l)].shape == (layer_cell_counts[l], layer_cell_counts[l - 1])
        assert params["B" + str(l)].shape == (layer_cell_counts[l], 1)
        # print('W' + str(l), params['W' + str(l)])
        # print('B' + str(l), params['B' + str(l)])
        # print("====================================")
    return params


# 执行L层的前项传播
def L_mode_forward(X, params, layer_keep_prob, L):
    """
    :param X:  初始输入层数据
    :param params: 参数字典
    :param L: 神经网络层数
    :return:
    """
    preA = X
    params['A0'] = preA  # 把A0)记录下来

    for l in range(1, L + 1):
        g_fun_type = 2  # 前面L-1层的激活函数采用Relu
        if l == L:  # 最后一层的激活函数采用sigmod
            g_fun_type = 0
        W = params['W' + str(l)]
        B = params['B' + str(l)]
        Z, preA = forward_propagation(preA, W, B, g_fun_type)

        # dropOut的正则化，在没有出现过拟合问题的时候不要用随机失活去进行正则化
        # D = np.random.rand(preA.shape[0], preA.shape[1]) < layer_keep_prob[l]  # 初始化每一层的随机失活性矩阵
        # preA = preA * D  # 失活后真正参与计算的A
        # preA = preA / layer_keep_prob[l]
        # params['D' + str(l)] = D

        params['A' + str(l)] = preA  # 把A1-AL记录下来
        params['Z' + str(l)] = Z  # 把Z1-ZL记录下来

    # 最后输出的矩阵是一个X(X等于最后一层神经元的个数)行m(m是训练数据的个数)列的矩阵
    assert preA.shape == (params['W' + str(L)].shape[0], X.shape[1])
    return params


# 多层神经网络的逻辑回归花销计算
def compute_cost(Y, predictY):
    m = Y.shape[1]
    assert Y.shape == predictY.shape
    assert m != 0
    cost = -np.sum(np.multiply(Y, np.log(predictY)) + np.multiply(1 - Y, np.log(1 - predictY))) / m

    cost = np.square(cost)  # 确保cost是一个数值
    assert cost.shape == ()
    return cost


def L_mode_back(dAL, params, layer_keep_prob, L):
    """
    :param dAL:  最后一层的dA值
    :param params:
    :param L:
    :return:
    """
    dA = dAL
    m = dAL.shape[1]  # 总训练数据个数
    for l in reversed(range(1, L + 1)):  # reversed(range(1,L))的结果是L-1,L-2...1。是不包括L的。第0层是输入层，不必计算。
        # D = params['D' + str(l)] # 随机失活时用到的D
        A = params['A' + str(l)]
        Z = params['Z' + str(l)]
        W = params['W' + str(l)]
        preA = params['A' + str(l - 1)]
        g_funDZ_type = 2
        if l == L:
            g_funDZ_type = 0

        # 随机失活的正则化
        # dA = dA * D
        # dA = dA / layer_keep_prob[l]

        dW, dB, dA = backward_propagation(A, dA, Z, W, preA, g_funDZ_type)
        # params['dW' + str(l)] = dW + (0.7 / m) * W  # 加上L2正则化项的dW
        params['dW' + str(l)] = dW  # 不加L2正则化项的dW
        params['dB' + str(l)] = dB
    return params


def update_params(params, L, learning_rate=0.02):
    """
    :param params:
    :param L: 神经网络层数
    :param learning_rate: 学习速度
    :return:
    """
    for l in range(1, L + 1):
        W = params['W' + str(l)]
        dW = params['dW' + str(l)]
        assert W.shape == dW.shape
        params['W' + str(l)] = W - learning_rate * dW

        B = params['B' + str(l)]
        dB = params['dB' + str(l)]
        assert B.shape == dB.shape
        params['B' + str(l)] = B - learning_rate * dB
    return params


# 标准多层神经网络模型
def dnn_mode(TrainX, TrainY, TestX, TestY, layer_cell_counts, layer_keep_prob, num_iterations=2000, learning_rate=0.02,
             print_cost=False):
    """
    :param TrainX: 训练数据输入：X
    :param TrainY: 训练数据的真实输出：Y
    :param TestX:  测试数据的输入：X
    :param TestY:  训练数据的真实输出：Y
    :param layer_cell_counts:  神经元网络的层数以及每一层神经元的个数：注意是包含隐藏层的
    :param num_iterations:  迭代次数
    :param learning_rate: 学习速度
    :param print_cost: 是否输出花销值
    :return:
    """
    assert TrainX.shape[0] == layer_cell_counts[0]
    assert np.max(TrainX) <= 1 and np.min(TrainX) >= 0  # 要限制训练数据进行过正则化了，介于（0-1）之间
    assert np.max(TestX) <= 1 and np.min(TestX) >= 0  # 要限制测试数据进行过正则化了，介于（0-1）之间
    params = init_params_deep_network(layer_cell_counts)
    L = len(layer_cell_counts) - 1
    m = TrainX.shape[1]

    costs = []
    for i in range(1, num_iterations + 1):
        # step1:前向转播
        params = L_mode_forward(TrainX, params, layer_keep_prob, L)

        # step2 :计算最后一层的dAL及为dY
        predictY = params['A' + str(L)]  # 最后一层的输出就是预测值

        dY = np.divide(-TrainY, predictY) + np.divide(1 - TrainY, 1 - predictY)

        # step3:反向传播
        params = L_mode_back(dY, params, layer_keep_prob, L)

        # step4:更新新的权重值
        update_params(params, L, learning_rate)

        if i % 100 == 0:
            # 进行L2正则化的cost ，即最后的总花销
            # L2_W = 0
            # for l in range(1, L + 1):
            #     Wl = params['W' + str(l)]
            #     L2_W = L2_W + np.sum(np.square(Wl))
            # cost = compute_cost(TrainY, predictY) + (0.7 / (2 * m)) * L2_W

            # 未进行L2正则化的cost
            cost = compute_cost(TrainY, predictY)
            costs.append(cost)
            if print_cost:
                print("训练到底%i次是的代价(花销)值为：%f" % (i, cost))

    # 训练数据的准确率为
    TrainM = TrainY.shape[1]  # 训练数据的个数
    PredictTrainY = (params['A' + str(L)] + 0.5).astype(int)  # 把结果按照>=0.5为1，否则为0
    TrainPredictRongNum = np.sum(PredictTrainY ^ TrainY)  # 训练数据预测错误的数量 ^ 是异或操作符：相同为1，不同为0
    TrainPredictRightRate = (TrainM - TrainPredictRongNum) * 100 / TrainM
    print("训练数据的准确率为：", TrainPredictRightRate)

    test_layer_keep_prob = [1, 1, 1, 1, 1]
    TestM = TestY.shape[1]  # 测试数据个数
    paramsTest = L_mode_forward(TestX, params, test_layer_keep_prob, L)
    PredictTestY = (paramsTest['A' + str(L)] + 0.5).astype(int)  # 把训练数据的预测结果按照>=0.5为1，否则为0
    TestPredictRongNum = np.sum(PredictTestY ^ TestY)  # 训练数据预测错误的数量
    TestPredictRightRate = (TestM - TestPredictRongNum) * 100 / TestM
    print("测试数据的准确率为：", TestPredictRightRate)

    return costs


def real_test():
    X = np.array([
        [1, 5, 6, 8],
        [5, 3, 4, 7]
    ])
    Y = np.array([[1, 0, 0, 1]])
    layer_cell_counts = [2, 4, 3, 1]


def cat_test():
    TrainX, TrainY, TestX, TestY = init_cat_data()
    layer_cell_counts = [TrainX.shape[0], 20, 7, 5, 1]  # 每一层神经元个数
    layer_keep_prob = [1, 0.65, 0.7, 0.8, 1]  # 每一层保持激活的神经元占比
    costs = dnn_mode(TrainX, TrainY, TestX, TestY, layer_cell_counts, layer_keep_prob, 3000, 0.0075, print_cost=True)
    plt_costs(costs, 0.0075)


if __name__ == "__main__":
    print("测试开始")
    cat_test()
