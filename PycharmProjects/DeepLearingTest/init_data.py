#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import numpy as np
import h5py


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


