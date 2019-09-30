#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import numpy as np
import matplotlib.pyplot as plt
from common.baseutil.g_function import sigmoid
from init_data import init_cat_data

print("这是一个识别小猫的Ai算法，基于stand NN")


# step2: 定义预测函数
def predict(X, W, b):
    z = np.dot(W.T, X) + b  # 用每一个权重乘以每一个特征值计算出每一个Z值: 1行M列
    A = sigmoid(z)  # 用逻辑回归函数sigmoid作为预测概率输出值:1行，m列
    return A


# A = predict(X, W, b)
# print("预测函数A:", A.shape)


# step3: 定义cost函数
def cost(A, Y):
    m = Y.shape[1]
    cost = -np.sum(Y * np.log(A) + (1 - Y) * np.log(1 - A)) / m
    return cost  # 具体的花销/代价值


# cost = cost(A, Y)
# print(cost)


# step4: 求偏导进行梯度递减更新W
def update_w(X, Y, A, W, b, learning_rate=0.005):
    m = Y.shape[1]
    dw = np.dot(X, (A - Y).T) / m  # 返回为12288行，1列的dw
    db = np.sum(A - Y) / m
    W = W - learning_rate * dw
    b = b - learning_rate * db
    return W, b


# step5: 开始执行
def run(TrainX, TrainY, TestX, TestY, learning_rate=0.02, count=200, print_cost=False):
    W = np.zeros((TrainX.shape[0], 1))
    b = 0
    costs = []
    for i in range(count):
        A = predict(TrainX, W, b)
        c = cost(A, TrainY)
        W, b = update_w(TrainX, TrainY, A, W, b, learning_rate)
        if print_cost and i % 100 == 0:
            costs.append(c)
            print("当训练第%i次后的花销为:%f" % (i, c))
    TrainM = TrainY.shape[1]
    TrainA = predict(TrainX, W, b)
    # PredictTrainY = np.round(TrainA + 1) - 1  # 把结果按照>=0.5为1，否则为0
    PredictTrainY = (TrainA + 0.5).astype(int)  # 把结果按照>=0.5为1，否则为0
    TrainPredictRongNum = np.sum(PredictTrainY ^ TrainY)  # 训练数据预测错误的数量
    TrainPredictRightRate = (TrainM - TrainPredictRongNum) * 100 / TrainM
    print("训练数据的准确率为：", TrainPredictRightRate)

    TestM = TestY.shape[1]
    TestA = predict(TestX, W, b)
    PredictTestY = (TestA + 0.5).astype(int)
    TestPredictRongNum = np.sum(PredictTestY ^ TestY)  # 测试数据预测错误的数量
    TestPredictRightRate = (TestM - TestPredictRongNum) * 100 / TestM
    print("测试数据的准确率为：", TestPredictRightRate)

    result = {"costs": costs,
              "learning_rate": learning_rate}
    return result


def plt_costs(costs, learning_rate):
    plt.plot(costs)
    plt.ylabel("cost")
    plt.xlabel("iterations(per hundreds)")
    plt.title("learning_rate:" + str(learning_rate))
    plt.show()


if __name__ == "__main__":
    TrainX, TrainY, TestX, TestY = init_cat_data()
    result = run(TrainX, TrainY, TestX, TestY, learning_rate=0.002, count=2000, print_cost=True)
    plt_costs(result["costs"], result["learning_rate"])

# A = np.ones((1, 5))
# B = np.ones((1, 5)) + 2
# print(A, "\n", B)
# print(A * B)
