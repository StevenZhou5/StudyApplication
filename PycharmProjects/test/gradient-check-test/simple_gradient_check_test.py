#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import numpy as np


# 梯度检验测试：利用双边误差检验偏导计算是否正确

def forward_propagation(x, theta):
    J = np.dot(x, theta)
    return J


def backward_propagation(x, theta):
    dtheta = x  # 对theta求导
    return dtheta


# 简单的线性函数的双边误差检验对theta(权重)的求导是否正确
def simple_gradient_check_test():
    # x为训练数据输入，theta的是我们假定的权重，每次梯度递减后都会更新
    x, theta = 2, 4
    epsilon = 1e-7  # 一个非常小的数
    # 正向传播计算forward_propagation(x,theta+epsilon)和forward_propagation(x,theta-epsilon)的值
    y_plus = forward_propagation(x, theta + epsilon)
    y_minus = forward_propagation(x, theta - epsilon)
    dtheta_approx = (y_plus - y_minus) / (2 * epsilon)  # 利用双边误差求得theta在X点导数的近似值

    # 反向传播计算dtheta= backward_propagation(x, theta)
    dtheta = backward_propagation(x, theta)

    # 计算近似值和反向传播求出的正式导数误差
    numerator = np.linalg.norm(dtheta - dtheta_approx)  # 分子：计算值与近似值差值的绝对值
    denominator = np.linalg.norm(dtheta) + np.linalg.norm(dtheta_approx)  # 分母：计算值的绝对值 + 近似值的绝对值
    difference = numerator / denominator
    print(difference)
    if difference < 1e-7:
        print("方向传播的求导正确")
    elif difference < 1e-5:
        print("方向传播的求导可能不正确，请注意排查问题!")
    else:
        print("方向传播的求导不正确，请排查问题!")


if __name__ == "__main__":
    simple_gradient_check_test()
