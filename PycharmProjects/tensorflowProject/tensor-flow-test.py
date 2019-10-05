import os

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'
import tensorflow as tf
import numpy as np


def simple_cost_test():
    """
    cost = w^2 - 4w + 4
    :return:
    """
    coefficients = np.array([[1], [-16], [64]])  # 训练数据的数据源

    w = tf.Variable(0, dtype=tf.float32)  # 定义w的初始值为0，类型为32位的浮点数
    x = tf.placeholder(tf.float32, [3, 1])  # 定义训练数据矩阵
    # cost = tf.add(tf.add(w ** 2, tf.multiply(-4.0, w)), 4)  # 定义代价函数
    # cost = w ** 2 - 4 * w + 4  # 用这种普通的写法定义代价函数也是可以的
    cost = x[0][0] * w ** 2 + x[1][0] * w + x[2][0]  # 用训练数据中的矩阵来和w进行运算，从而得出代价函数
    train = tf.compat.v1.train.GradientDescentOptimizer(0.01).minimize(cost)  # 定义梯度下降是使得cost值最小，下降速度为0.01

    init = tf.compat.v1.global_variables_initializer()  # 全局变量初始化
    session = tf.compat.v1.Session()  # 创建session
    session.run(init)  # 初始化session
    print("w的初始值为", session.run(w))  # 直接输入w的值

    session.run(train, feed_dict={x: coefficients})  # 进行一次迭代
    print(session.run(w))

    for i in range(1000):  # 训练1000次
        session.run(train, feed_dict={x: coefficients})
    print(session.run(w))


if __name__ == "__main__":
    simple_cost_test()
