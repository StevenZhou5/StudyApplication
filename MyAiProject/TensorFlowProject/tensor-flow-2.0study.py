import os

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'
import tensorflow as tf
import numpy as np

import matplotlib.pyplot as plt


# 2.0可见及可得，不在以来Session来执行graphs来运行，写一行就可以执行一行
# 清理废弃的API和减少重复来简化API
# 训练方面：使用Keras和eager execution 来实线轻松构建模型，为研究提供强大的实线工具
#  Tf.keras:允许创建复杂的拓扑,包括使用残差层，自定义多输入/输出模型以及强制编写的正向传递，轻松创建自定义循环。
#  低级TensorFlow Api始终可用，并与更高级别的抽象一起工作，以实现完成可定制的逻辑
# 在部署方面：
# 在任意平台实现稳健的生产环境模型部署；
# 不论是在服务器，边缘设备还是网页，移动平台，也不论你使用的是什么语言或平台，TensorFlow总能让你轻松训练和部署模型
# TensorFlow2.0中，通过标准化交换格式来改进跨平台和跨语言部署

# （一）tf.keras:构建和训练模型的核心高级API
#      1：单输入和单输出的Sequential顺序模型
#      2：函数式API

# （二）Eager模型与自定义训练：直接迭代和直观调试，Eager模式下求解梯度与自定义训练
#      1：Eager模型：直接迭代和直观调试
#      2：tf.GradientTape:求解梯度，自定义训练逻辑

# （三）tf.data:加载图片数据与结构化数据
# （四）tf.fuction:自动图运算
# （五）卷积神经网络
# （六）多输出卷积神经网络综合实例
# （七）迁移学习
# （八）模型的保存和可视化
# （九）Tensorboard可视化


def study_test():
    # tf的keras来创建线性回归模型
    trainX = np.random.rand(100)
    trainY = trainX * 5 + 3

    model = tf.keras.Sequential()  # 用keras建立一个序列模型

    model.add(tf.keras.layers.Dense(1, input_shape=(1,)))  # model添加了一层输出层:第一个1代表输出数据的维度，input_shape代表输入的shape

    model.summary()  # 预览模型

    model.compile(optimizer='adam', loss='mse')  # 模型编译:optimizer优化方法adam（自适应矩估计:防止抖动与加速训练）;loss='mse'是均方差误差函数

    history = model.fit(trainX, trainY, epochs=2000, batch_size=16)  # epochs:miniBatch的循环次数;batch_size:默认是32

    # print("Train_X:{}".format(trainX))
    # print("Train_Y:{}".format(trainY))
    # print("预测值：{}".format(model.predict(trainX)))
    print("当X=5时，预测值为", model.predict([5]))
    print("训练得到的权重：", history['model']['weights'])


study_test()
