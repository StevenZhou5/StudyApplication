#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import numpy as np

print("你好 world")

X = np.array([0.2, 0.3])
assert np.max(X) <= 1 and np.min(X) >= 0
print(np.min(X))
