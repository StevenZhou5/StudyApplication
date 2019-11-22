# coding=utf-8
import tensorflow as tf
import numpy as np


class TFLinear(tf.keras.layers.Layer):
    """
    自定义一个线性回归模型的自定义Layer
    """

    def __init__(self, output_size, **kwargs):
        super(TFLinear, self).__init__(**kwargs)
        # 定义一个全连接层，输出为1个神经元
        self.linear = tf.keras.layers.Dense(output_size)

    def call(self, inputs):
        outputs = self.linear(inputs)
        return outputs


class TFLinearModel(tf.keras.Model):
    """
    自定义模型
    """

    def __init__(self, output_size, *inputs, **kwargs):
        super(TFLinearModel, self).__init__(*inputs, *kwargs)

        self.linear = TFLinear(output_size)

    def call(self, inputs, **kwargs):
        outputs = self.linear(inputs)
        return outputs


def main():
    trainX = np.random.random_integers(0, 8, (100, 1))
    print(trainX.shape)
    print(trainX)
    trainY = 3 * trainX + 8
    print(trainY)

    output_size = 1
    model = TFLinearModel(output_size)

    # 定义optimizer和loss
    optimizer = tf.keras.optimizers.Adam(0.03)  # Adam优化器
    #mse：均方根误差损失函数；用预测值与真实在的差的平方来作为loss函数
    model.compile(optimizer=optimizer, loss='mse')  #

    # 模型训练
    history = model.fit(trainX, trainY, epochs=500)

    print(model.weights)
    print(model.predict([[5], [15]]))


if __name__ == '__main__':
    main()
