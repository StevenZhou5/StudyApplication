#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import numpy as np
import h5py

import tensorflow as tf
import matplotlib.pyplot as plt


# 初始化数据
def init_cat_data():
    train_dataset = h5py.File('/Users/zhenwuzhou/.keras/datasets/train_catvnoncat.h5', "r")
    train_x = np.array(train_dataset["train_set_x"][:])  # shape(209,64,64,3)
    train_y = np.array(train_dataset["train_set_y"][:])  # shape(209,)

    test_dataset = h5py.File('/Users/zhenwuzhou/.keras/datasets/test_catvnoncat.h5', "r")
    test_x = np.array(test_dataset["test_set_x"][:])
    test_y = np.array(test_dataset["test_set_y"][:])
    print("初始数据\n", train_x.shape, train_y.shape, test_x.shape, test_y.shape)

    train_m = train_y.shape[0]
    TrainX = train_x.reshape(train_x.shape[0], -1).T / 255  # 64*64*3=12288行，m列的输入矩阵 ，除以255是为了做均值归一化操作
    TrainY = train_y.reshape(1, train_m)  # 1行m列真实结果的矩阵
    test_m = test_y.shape[0]
    TestX = test_x.reshape(test_x.shape[0], -1).T / 255
    TestY = test_y.reshape(1, test_m)
    print("整理后的数据\n", TrainX.shape, TrainY.shape, TestX.shape, TestY.shape)
    return TrainX, TrainY, TestX, TestY


def dnn_mode_test():
    # 初始化训练数据和测试数据
    TrainX, TrainY, TestX, TestY = init_cat_data()
    TrainX = TrainX.T
    TrainY = TrainY.T

    # 建立神经网络模型
    mode = tf.keras.Sequential()  # 用 keras建立一个序列模型

    # 创建layer1 ：注意第一层要制定input_shape,
    layer1 = tf.keras.layers.Dense(10, input_shape=(TrainX.shape[1],), activation='relu')  # 第一层10个神经单元，激活函数采用relu;
    mode.add(layer1)

    # 创建layer2 ： 可以不制定input_shape，会
    layer2 = tf.keras.layers.Dense(7, activation='relu')  # 第二层7个神经单元，激活函数采用relu
    mode.add(layer2)

    # 创建输出层 ：输出层是一个神经单元，激活函数采用二元分类函数sigmoid
    layer_out_put = tf.keras.layers.Dense(1, activation='sigmoid')
    mode.add(layer_out_put)

    mode.summary()  # 打出model的各项参数

    # 定义loss代价函数及一些其他相关超参数
    mode.compile(optimizer="adam", loss="binary_crossentropy",
                 metrics=['acc'])  # 采用adam优化，binary_crossentropy二元分类损失函数;metrics可以输出正确率acc

    # 执行梯度下降，进行训练
    history = mode.fit(TrainX, TrainY, epochs=200)

    print("history的key：", history.history.keys())

    # 取出loss值与acc（正确率）值来绘图
    loss = history.history.get('loss')
    plt.plot(history.epoch, loss)
    plt.show()


dnn_mode_test()
