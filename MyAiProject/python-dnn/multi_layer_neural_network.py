#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import numpy as np
# 这个库是用来画图的
import matplotlib.pyplot as plt

from init_data import init_cat_data
from common.baseutil.g_function import forward_propagation, backward_propagation


# 多层神经网络测试
def compute_cost(Y, predictY):
    m = Y.shape[1]
    assert Y.shape == predictY.shape
    cost = -np.sum(np.multiply(Y, np.log(predictY)) + np.multiply(1 - Y, np.log(1 - predictY))) / m
    return cost


def run(TrainX, TrainY, TestX, TestY, num_iterations=10000, learning_rate=0.02, print_cost=False):
    trainM = TrainY.shape[1]  # 训练样本数
    A0 = TrainX
    n0 = TrainX.shape[0]  # 第0层神经元个数

    n1 = 4  # 第1层神经元个数

    n2 = 1  # 第2层神经元个数

    # n3 = 1  # 第3层神经元个数

    # n4 = 1  # 第4层神经元个数

    W1 = np.random.randn(n1, n0) * 0.01  # 初始权重要尽可能小，不然在sigmoid和tanh作为激活函数是Z值过大，训练效率低
    B1 = np.zeros((n1, 1))
    W2 = np.random.randn(n2, n1) * 0.01
    B2 = np.zeros((n2, 1))
    # W3 = np.random.randn(n3, n2) * 0.01
    # B3 = np.zeros((n3, 1))
    # W4 = np.random.randn(n4, n3) * 0.01
    # B4 = np.zeros((n4, 1))

    costs = []
    for i in range(num_iterations):
        # 正向传播
        Z1, A1 = forward_propagation(A0, W1, B1, g_fun_type=2)
        Z2, A2 = forward_propagation(A1, W2, B2, g_fun_type=0)
        # Z3, A3 = forward_propagation(A2, W3, B3, g_fun_type=0)
        # Z4, A4 = forward_propagation(A3, W4, B4, g_fun_type=0)

        # 反向传播
        # dA4 = np.divide(-TrainY, A4) + np.divide(1 - TrainY, 1 - A4)
        # dw4, db4, dA3 = backward_propagation(A4, dA4, Z4, W4, A3, g_funDZ_type=0)
        # dA3 = np.divide(-TrainY, A3) + np.divide(1 - TrainY, 1 - A3)
        # dw3, db3, dA2 = backward_propagation(A3, dA3, Z3, W3, A2, g_funDZ_type=0)
        dA2 = np.divide(-TrainY, A2) + np.divide(1 - TrainY, 1 - A2)
        dw2, db2, dA1 = backward_propagation(A2, dA2, Z2, W2, A1, g_funDZ_type=0)
        dw1, db1, dA0 = backward_propagation(A1, dA1, Z1, W1, A0, g_funDZ_type=2)

        grads = {"dw1": dw1,
                 "db1": db1,
                 "dw2": dw2,
                 "db2": db2}

        # 梯度递减，参数更新
        # W1,B1
        assert W1.shape == dw1.shape
        W1 = W1 - learning_rate * dw1
        assert W1.shape == (n1, n0)

        assert B1.shape == db1.shape
        B1 = B1 - learning_rate * db1
        assert B1.shape == (n1, 1)

        # W2,B2
        assert W2.shape == dw2.shape
        W2 = W2 - learning_rate * dw2
        assert W2.shape == (n2, n1)

        assert B2.shape == db2.shape
        B2 = B2 - learning_rate * db2
        assert B2.shape == (n2, 1)

        # W2,B2
        # assert W3.shape == dw3.shape
        # W3 = W3 - learning_rate * dw3
        # assert W3.shape == (n3, n2)
        #
        # assert B3.shape == db3.shape
        # B3 = B3 - learning_rate * db3
        # assert B3.shape == (n3, 1)

        # W2,B2
        # assert W4.shape == dw4.shape
        # W4 = W4 - learning_rate * dw4
        # assert W4.shape == (n4, n3)
        #
        # assert B4.shape == db4.shape
        # B4 = B4 - learning_rate * db4
        # assert B4.shape == (n4, 1)

        if i % 100 == 0:
            cost = compute_cost(TrainY, A2)
            costs.append(cost)
            if print_cost:
                print("当训练到第%i次时的代价函数值为：%f" % (i, cost))
    TrainM = TrainY.shape[1]
    A0 = TrainX
    Z1, A1 = forward_propagation(A0, W1, B1, g_fun_type=2)
    Z2, A2 = forward_propagation(A1, W2, B2, g_fun_type=0)
    # Z3, A3 = forward_propagation(A2, W3, B3, g_fun_type=0)
    # Z4, A4 = forward_propagation(A3, W4, B4, g_fun_type=0)
    TrainA = A2
    # PredictTrainY = np.round(TrainA + 1) - 1  # 把结果按照>=0.5为1，否则为0
    PredictTrainY = (TrainA + 0.5).astype(int)  # 把结果按照>=0.5为1，否则为0
    TrainPredictRongNum = np.sum(PredictTrainY ^ TrainY)  # 训练数据预测错误的数量 ^ 是异或操作符
    TrainPredictRightRate = (TrainM - TrainPredictRongNum) * 100 / TrainM
    print("训练数据的准确率为：", TrainPredictRightRate)

    TestM = TestY.shape[1]
    A0 = TestX
    Z1, A1 = forward_propagation(A0, W1, B1, g_fun_type=2)
    Z2, A2 = forward_propagation(A1, W2, B2, g_fun_type=0)
    # Z3, A3 = forward_propagation(A2, W3, B3, g_fun_type=0)
    # Z4, A4 = forward_propagation(A3, W4, B4, g_fun_type=0)
    TestA = A2
    PredictTestY = (TestA + 0.5).astype(int)
    TestPredictRongNum = np.sum(PredictTestY ^ TestY)  # 测试数据预测错误的数量
    TestPredictRightRate = (TestM - TestPredictRongNum) * 100 / TestM
    print("测试数据的准确率为：", TestPredictRightRate)

    return costs


# 初始化数据：
TrainX, TrainY, TestX, TestY = init_cat_data()

costs = run(TrainX, TrainY, TestX, TestY, num_iterations=5000, learning_rate=0.002, print_cost=True)


def plt_costs(costs, learning_rate):
    plt.plot(costs)
    plt.ylabel("cost")
    plt.xlabel("iterations(per hundreds)")
    plt.title("learning_rate:" + str(learning_rate))
    plt.show()


plt_costs(costs, 0.02)
