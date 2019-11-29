import numpy as np
import tensorflow as tf
from tensorflow.keras import layers as layers
from tensorflow.keras import models as models


class Test():
    def __init__(self, a, b):
        self.a = a
        self.b = b

    def plus(self, add):
        print(self.a + self.b)

    def sub(self):
        print(self.a - self.b)


def main():
    test = Test(3, 5)
    test.sub()


if __name__ == '__main__':
    print("开始执行：")
    main()
