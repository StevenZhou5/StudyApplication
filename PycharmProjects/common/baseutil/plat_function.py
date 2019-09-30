#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import matplotlib.pyplot as plt


def plt_costs(costs, learning_rate):
    plt.plot(costs)
    plt.ylabel("cost")
    plt.xlabel("iterations(per hundreds)")
    plt.title("learning_rate:" + str(learning_rate))
    plt.show()
