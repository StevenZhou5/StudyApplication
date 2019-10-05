import os

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'
import tensorflow as tf
import numpy as np

import matplotlib.pyplot as plt


# 使用 graphs（图） 来表示计算任务
# 在Session（会话）的context（上下文）中执行graphs
# 使用 tensor（张量） 表示数据
# 使用 Variable（变量）维护状态
# 使用feed 和 fetch（取）可以为任意的操作赋值或者从中获取数据

# graphs 中的节点成为op（operation）；一个op可以获得0个或者多个Tensor并执行计算，产生0个或多个Tensor；
# Tensor是一个n维度矩阵，graphs 必须在会话Session里被启动

def study1_test():
    # 创建一个常量op
    m1 = tf.constant([[5, 8]])
    # 创建一个常量op
    m2 = tf.constant([[2], [6]])

    # 创建一个矩阵乘法的OP，把m1 与m2相乘
    m1_matmul_m2 = tf.matmul(m1, m2)  # 內积

    # 定义一个Session，启动默认图
    # session = tf.compat.v1.Session()
    # session.close()

    # 用这种方式启动Session不用手动关闭Session
    with tf.compat.v1.Session() as session:
        # 调用session 的run方法触发图中的3个op
        m1_matmul_m2 = session.run(m1_matmul_m2)
        print(m1_matmul_m2)


def study2_test():
    # 创建一个变量x
    x = tf.Variable([1, 5])
    a = tf.constant([3, 5])

    sub = tf.subtract(x, a)  # 一个减法的op
    # add = tf.add(x, a)  # 一个加法的op
    add = x + a  # 一个加法的op

    # 创建一个变量初始化为0
    state = tf.Variable(0, name='counter')
    # 对state执行++操作并重新更新state的值
    state_plus = tf.assign(state, state + 1)

    # 如果有变量存在，要执行变量初始化，这个是一个全局变量初始化
    init = tf.compat.v1.global_variables_initializer()

    with tf.compat.v1.Session() as session:
        session.run(init)
        print(session.run(sub))
        print(session.run(add))

        for i in range(5):
            session.run(state_plus)
            print(session.run(state))


def study3_test():
    # Fetch
    input1 = tf.constant(2.0)
    input2 = tf.constant(3.0)
    input3 = tf.constant(4.0)

    op1 = (input1 + input2) * input3
    op2 = input1 - input2 * input3

    # Feed 创建占位符
    input4 = tf.compat.v1.placeholder(tf.float32)  # 用占位符的方式创建input4
    input5 = tf.compat.v1.placeholder(tf.float32)  # 用占位符的方式创建input5
    input6 = tf.compat.v1.placeholder(tf.float32)  # 用占位符的方式创建input6

    op4 = input4 + input5 + input6

    # 如果有变量存在，要执行变量初始化，这个是一个全局变量初始化
    init = tf.compat.v1.global_variables_initializer()

    with tf.compat.v1.Session() as session:
        # Fetch:可以同时执行多个op
        fetch1 = session.run([op1, op2])
        print(fetch1)

        # Feed 的数据以字典的形式传入
        print(session.run(op4, feed_dict={input4: [3.], input5: [6.], input6: [8.]}))


def study4_test():
    # 简单线性模型测试
    # 使用np 生成100个随机点
    trainX = np.random.rand(100)  # (0-1)
    trainY = trainX * 0.5 + 0.8

    # step1 定义预测函数
    w = tf.Variable(0.)
    b = tf.Variable(0.)
    y = w * trainX + b

    # step2 定义代价函数
    cost = (trainY - y) ** 2 / trainX.shape[0]

    # step3 执行梯度下降
    train = tf.train.GradientDescentOptimizer(0.2).minimize(cost)

    # 如果有变量存在，要执行变量初始化，这个是一个全局变量初始化
    init = tf.compat.v1.global_variables_initializer()

    with tf.compat.v1.Session() as session:
        session.run(init)
        for i in range(100):  # 执行200次梯度下降
            session.run(train)
            print("w和b的更新值:", session.run(w), session.run(b))


def study5_test():
    # 非线性回归测试
    # 生成从-0.5 - 0.5 均匀分布的200个点
    trainX = np.linspace(-0.5, 0.5, 200, dtype=np.float32)[:, np.newaxis]  # np.newaxis是增加一个维度
    noise = np.random.normal(0, 0.02, trainX.shape)
    trainY = np.square(trainX) + noise

    # 定义两个placehoder（占位符）
    x = tf.compat.v1.placeholder(tf.float32, [None, 1])  # None说明行可以是任意行，列为1列
    y = tf.compat.v1.placeholder(tf.float32, [None, 1])  # None说明行可以是任意行，列为1列

    # 定义两层的Dnn神经网络模型
    # 初始化第一层神经网络
    W1 = tf.Variable(tf.random.normal([1, 10]))
    b1 = tf.Variable(tf.zeros([1, 10]))
    Z1 = tf.matmul(x, W1) + b1  # 注意这里要用占位符x
    A1 = tf.nn.relu(Z1)

    # 初始化第二层神经网络(输出层)
    W2 = tf.Variable(tf.random.normal([10, 1]))
    b2 = tf.Variable(tf.zeros([1, 1]))
    Z2 = tf.matmul(A1, W2) + b2
    predictY = tf.nn.tanh(Z2)

    # 定义代价函数
    cost = tf.reduce_mean((y - predictY) ** 2)  # reduce_mean是用来求平均值得；注意这里要用y

    # 执行梯度下降
    train = tf.compat.v1.train.GradientDescentOptimizer(0.1).minimize(cost)

    # 如果有变量存在，要执行变量初始化，这个是一个全局变量初始化
    init = tf.compat.v1.global_variables_initializer()

    with tf.compat.v1.Session() as session:
        session.run(init)
        for i in range(200):
            session.run(train, feed_dict={x: trainX, y: trainY})

        # 获得预测值
        predictValue = session.run(predictY, feed_dict={x: trainX})
        # 用画图的方式来展示训练结果
        plt.figure()  # 画离散点
        plt.scatter(trainX, trainY)  # 把有trainX, trainY组成的二维离散点画出来
        plt.plot(trainX, predictValue, 'r-', lw=5)  # 'r-'表示用红色实线画出，lw=5表示线宽5像素
        plt.show()


study5_test()
