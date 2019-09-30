#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import numpy as np


# 规范化初始值的方法（归一化）：规范化可以使得所有值的取值在（-1，1）之间，可以使梯度递减的更快

def uniformizationRows(x):
    """
    Implement a function that normalizes each row of the matrix x (to have unit length).

    Argument:
    x -- A numpy matrix of shape (n, m)

    Returns:
    x -- The normalized (by row) numpy matrix. You are allowed to modify x.
    """
    x_norm = np.linalg.norm(x, axis=1, keepdims=True)  # 行向量平方和开根号
    x = x / x_norm
    return x


A = np.array([
    [0, 3, 4],
    [-3, 0, 4]])
print("normalizeRows(A)", uniformizationRows(A))


def softmax(x):
    """
    (当进行分类算法时，可以用此方法将初始数据规范化)
     Implement a softmax function using numpy.
     You can think of softmax as a normalizing function used
     when your algorithm needs to classify two or more classes
    Calculates the softmax for each row of the input x.

            Your code should work for a row vector and also for matrices of shape (n, m).

            Argument:
            x -- A numpy matrix of shape (n,m)

            Returns:
            s -- A numpy matrix equal to the softmax of x, of shape (n,m)
    """
    x_exp = np.exp(x)
    x_sum = np.sum(x_exp, axis=1, keepdims=True)

    s = x_exp / x_sum
    return s


x = np.array([
    [9, 2, 5, 0, 0],
    [7, 5, 0, 0, 0]])
print("softmax(x) = " + str(softmax(x)))
