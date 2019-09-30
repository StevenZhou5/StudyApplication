#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import numpy as np
# 这个库是用来画图的
import matplotlib.pyplot as plt
import skimage.transform as tf  # 这里我们用它来缩放图片
from init_data import init_cat_data
from common.baseutil.g_function import forward_propagation, backward_propagation


# 两层神经网络测试
def coumpute_cost(Y, predictY):
    m = Y.shape[1]
    assert Y.shape == predictY.shape
    cost = -np.sum(np.multiply(Y, np.log(predictY)) + np.multiply(1 - Y, np.log(1 - predictY))) / m
    return cost


def run(TrainX, TrainY, TestX, TestY, num_iterations=10000, learning_rate=0.02, print_cost=False):
    trainM = TrainY.shape[1]  # 训练样本数
    A0 = TrainX
    n0 = TrainX.shape[0]  # 第0层神经元个数

    n1 = 3  # 第1层神经元个数

    n2 = 1  # 第2层神经元个数

    W1 = np.random.randn(n1, n0) * 0.01  # 初始权重要尽可能小，不然在sigmoid和tanh作为激活函数是Z值过大，训练效率低
    B1 = np.zeros((n1, 1))
    W2 = np.random.randn(n2, n1) * 0.01
    B2 = np.zeros((n2, 1))

    costs = []
    for i in range(num_iterations):
        # 正向传播
        Z1, A1 = forward_propagation(A0, W1, B1, g_fun_type=2)
        Z2, A2 = forward_propagation(A1, W2, B2, g_fun_type=0)

        # 反向传播
        dA2 = np.divide(-TrainY, A2) + np.divide(1 - TrainY, 1 - A2)
        dw2, db2, dA1 = backward_propagation(A2, dA2, Z2, W2, A1, g_funDZ_type=0)
        dw1, db1, dA0 = backward_propagation(A1, dA1, Z1, W1, A0, g_funDZ_type=2)

        grads = {"dw1": dw1,
                 "db1": db1,
                 "dw2": dw2,
                 "db2": db2}

        # 梯度递减，参数更新
        assert W1.shape == dw1.shape
        W1 = W1 - learning_rate * dw1
        assert W1.shape == (n1, n0)

        assert B1.shape == db1.shape
        B1 = B1 - learning_rate * db1
        assert B1.shape == (n1, 1)

        assert W2.shape == dw2.shape
        W2 = W2 - learning_rate * dw2
        assert W2.shape == (n2, n1)

        assert B2.shape == db2.shape
        B2 = B2 - learning_rate * db2
        assert B2.shape == (n2, 1)

        if i % 100 == 0:
            cost = coumpute_cost(TrainY, A2)
            costs.append(cost)
            if print_cost:
                print("当训练到第%i次时的代价函数值为：%f" % (i, cost))
    TrainM = TrainY.shape[1]
    A0 = TrainX
    Z1, A1 = forward_propagation(A0, W1, B1, g_fun_type=2)
    Z2, A2 = forward_propagation(A1, W2, B2, g_fun_type=0)
    TrainA = A2
    # PredictTrainY = np.round(TrainA + 1) - 1  # 把结果按照>=0.5为1，否则为0
    PredictTrainY = (TrainA + 0.5).astype(int)  # 把结果按照>=0.5为1，否则为0
    TrainPredictRongNum = np.sum(PredictTrainY ^ TrainY)  # 训练数据预测错误的数量
    TrainPredictRightRate = (TrainM - TrainPredictRongNum) * 100 / TrainM
    print("训练数据的准确率为：", TrainPredictRightRate)

    TestM = TestY.shape[1]
    A0 = TestX
    Z1, A1 = forward_propagation(A0, W1, B1, g_fun_type=2)
    Z2, A2 = forward_propagation(A1, W2, B2, g_fun_type=0)
    TestA = A2
    PredictTestY = (TestA + 0.5).astype(int)
    TestPredictRongNum = np.sum(PredictTestY ^ TestY)  # 测试数据预测错误的数量
    TestPredictRightRate = (TestM - TestPredictRongNum) * 100 / TestM
    print("测试数据的准确率为：", TestPredictRightRate)

    parameters = {"W1": W1,
                  "B1": B1,
                  "W2": W2,
                  "B2": B2}
    return costs, parameters


# 初始化数据：
TrainX, TrainY, TestX, TestY = init_cat_data()

costs, parameters = run(TrainX, TrainY, TestX, TestY, learning_rate=0.002, print_cost=True)

W1 = parameters["W1"]
B1 = parameters["B1"]
W2 = parameters["W2"]
B2 = parameters["B2"]

# 下面我们用检测我们自己的图片
# 在本文档的同目录下创建一个文件夹images,把你的任意图片改名成my_image1.jpg后放入文件夹
num_px = 64
my_image = "5.jpg"
fname = "images/" + my_image

image = np.array(plt.imread(fname))
my_image = tf.resize(image, (num_px, num_px, 3), mode='reflect').reshape((1, num_px * num_px * 3)).T
print("my_image:", my_image.shape)
A0 = my_image / 255
Z1, A1 = forward_propagation(A0, W1, B1, g_fun_type=2)
Z2, A2 = forward_propagation(A1, W2, B2, g_fun_type=0)
my_predicted_image = A2[0][0]
print("是猫的概率：", my_predicted_image)
# print("预测结果为" + str(int(np.squeeze(my_predicted_image))))
plt.imshow(tf.resize(image, (num_px, num_px, 3), mode='reflect'))
plt.show()

# 第0层:输入层
# n0 = TrainX.shape[0]
# A0 = TrainX
#
# # 第1层
# n1 = 3  # 第一层有三个神经元
# W1 = np.random.randn(n1, n0) * 0.01  # 初始权重要尽可能小，不然在sigmoid和tanh作为激活函数是Z值过大，训练效率低
# B1 = np.zeros((n1, 1))
# Z1, A1 = forward_propagation(1, TrainX, W1, B1)
# print("A1.shape:", A1.shape)
#
# # 第2层
# n2 = 1
# W2 = np.random.randn(n2, n1) * 0.01
# B2 = np.zeros((n2, 1))
# Z2, A2 = forward_propagation(2, A1, W2, B2)
# print("A2.shape:", A2.shape)
#
# # 第2层：输出层
# dA2 = np.divide(-TrainY, A2) + np.divide(1 - TrainY, 1 - A2)
# dw2, db2, dA1 = backward_propagation(2, A2, dA2, Z2, W2, A1)
# print("dA2.shape:", dA2.shape)
# print("dW2.shape:", dw2.shape)
# print("db2.shape:", db2.shape)
#
# # 第1层
# dw1, db1, dA0 = backward_propagation(1, A1, dA1, Z1, W1, A0)
# print("dA1.shape:", dA1.shape)
# print("dw1.shape:", dw1.shape)
# print("db1.shape:", db1.shape)
