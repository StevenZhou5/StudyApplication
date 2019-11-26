import os

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'
import tensorflow as tf


def first_test():
    hello = tf.constant('Hello, TensorFlow!')
    sess = tf.compat.v1.Session()
    print(sess.run(hello))


def second_test():
    y_hat = tf.constant(36, name="y_hat")  # 定义y_hat为固定值36
    y = tf.constant(39, name="y")  # 定义y为固定值39

    loss = tf.Variable((y - y_hat) ** 2, name="loss")  # 为损失函数创建一个变量

    init = tf.compat.v1.global_variables_initializer()  # 运行之后的初始化(ession.run(init))
    # 损失变量将被初始化并准备计算
    with tf.compat.v1.Session() as session:  # 创建一个session并打印输出
        session.run(init)  # 初始化变量
        print(session.run(loss))

first_test()
second_test()
