#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import numpy as np

print("你好 world")

layer_keep_prob = [0.6, 0.8, 0.8, 1]  # 每一层保持激活的神经元占比
a = 0.8
assert a == layer_keep_prob[1] == 0.8
print("layer_keep_prob1:", layer_keep_prob[1])
X = np.random.rand(2, 3) < layer_keep_prob[1]
Y = np.random.rand(2, 3)
print(X)
print(Y)
print(X * Y)
