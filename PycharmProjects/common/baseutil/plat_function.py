#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import matplotlib.pyplot as plt


def plt_costs(costs, learning_rate):
    plt.plot(costs)
    plt.ylabel("cost")
    plt.xlabel("iterations(per hundreds)")
    plt.title("learning_rate:" + str(learning_rate))
    plt.show()


def plt_XY(X, Y):
    # 下面用scatter来将数据集中的400个点画出来。
    # X[0, :]表示400点的横坐标，X[1, :]表示纵坐标，c=Y.ravel()是指定400个点的颜色，s=40指定点的大小，
    # cmap指定调色板，如果用不同的调色板，那么Y的值对应的颜色也会不同。用plt.cm.Spectral这个调色板时，Y等于0指代红色，1指代蓝色。
    # 你可能会有疑问，为什么不直接用c=Y,而用c=Y.ravel()，它们只是维度表示方式不同，
    # Y的维度是(1,400),Y.ravel()的维度是(400,)，scatter这个库函数需要后面的形式。
    plt.scatter(X[0, :], X[1, :], c=Y.ravel(), s=40, cmap=plt.cm.Spectral)
    plt.show()
