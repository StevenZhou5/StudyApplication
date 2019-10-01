import numpy as np
import matplotlib.pyplot as plt
import h5py

from init_utils import *
from common.baseutil.plat_function import *

# sklearn是一个Python第三方提供的非常强力的机器学习库，
# 它包含了从数据预处理到训练模型的各个方面。
# 在实战使用scikit-learn中可以极大的节省我们编写代码的时间以及减少我们的代码量，
# 使我们有更多的精力去分析数据分布，调整模型和修改超参
import sklearn
# sklearn的datasets中提供一些训练数据，我们可以使用这些数据来进行分类或者回归等等
import sklearn.datasets


# 设置好画图工具
plt.rcParams['figure.figsize'] = (7.0, 4.0)  # set default size of plots
plt.rcParams['image.interpolation'] = 'nearest'
plt.rcParams['image.cmap'] = 'gray'


def initialize_parameters_zeros(layers_dims):
    parameters = {}
    for l in range(1, len(layers_dims)):
        parameters['W' + str(l)] = np.zeros((layers_dims[l], layers_dims[l - 1]))
        parameters['b' + str(l)] = np.zeros((layers_dims[l], 1))
    return parameters


def initialize_parameters_random(layers_dims):
    parameters = {}
    for l in range(1, len(layers_dims)):
        parameters['W' + str(l)] = np.random.randn(layers_dims[l], layers_dims[l - 1]) * 10  # 取（-10，10）之间的随机值
        parameters['b' + str(l)] = np.zeros((layers_dims[l], 1))
    return parameters


def initialize_parameters_he(layers_dims):
    parameters = {}
    for l in range(1, len(layers_dims)):
        # 将权重矩阵初始化为一个较小的数
        parameters['W' + str(l)] = np.random.randn(layers_dims[l], layers_dims[l - 1]) * np.sqrt(2 / layers_dims[l - 1])
        parameters['b' + str(l)] = np.zeros((layers_dims[l], 1))
    return parameters


# 通过三种不同初始化参数的方式来验证参数初始化的重要性
def model(X, Y, learning_rate=0.01, num_iterations=15000, print_cost=True, initialization="he"):
    grads = {}
    costs = []
    m = X.shape[1]  # 训练数据个数
    layers_dims = [X.shape[0], 10, 5, 1]  # 构建三层神经网络

    # 三种不同的初始化方法
    if initialization == "zeros":  # 初始化全为0
        parameters = initialize_parameters_zeros(layers_dims)
    elif initialization == "random":
        parameters = initialize_parameters_random(layers_dims)
    elif initialization == "he":
        parameters = initialize_parameters_he(layers_dims)

    # 进行梯度下降
    for i in range(0, num_iterations):
        a3, cache = forward_propagation(X, parameters)
        cost = compute_loss(a3, Y)
        grads = backward_propagation(X, Y, cache)
        parameters = update_parameters(parameters, grads, learning_rate)

        if print_cost and i % 1000 == 0:
            print("训练到第{}次是的cost为:{}".format(i, cost))
            costs.append(cost)

    # 画出训练次数与cost值得走势图
    plt_costs(costs, learning_rate)

    return parameters


if __name__ == "__main__":
    print("__main__")
    train_X, train_Y, test_X, test_Y = load_dataset()
    # 权重矩阵初始化全为0的话，训练梯度完全不下降，预测完成了0.5概率的抛硬币式的乱猜
    # parameters = model(train_X, train_Y, initialization='zeros')

    # 权重矩阵随机初始化为(-10,10)之间的随机数，预测准确度变为0.83左右，
    # 但是这种初始权重过大或过小，回使得训练时间增加很多，严重的话更会导致梯度爆炸或者梯度消失
    # parameters = model(train_X, train_Y, initialization='random')

    # 将权重矩阵初始化为较小的
    parameters = model(train_X, train_Y, initialization='he')

    print("训练数据的预测准确度：")
    predictions_train = predict(train_X, train_Y, parameters)
    print("predictions_train =", predictions_train)

    print("测试数据的预测准确度：")
    predictions_test = predict(test_X, test_Y, parameters)
    print("predictions_test =", predictions_test)

    plt.title("Model with large random initialization")
    axes = plt.gca()
    axes.set_xlim([-1.5, 1.5])
    axes.set_ylim([-1.5, 1.5])
    plot_decision_boundary(lambda x: predict_dec(parameters, x.T), train_X, train_Y)
