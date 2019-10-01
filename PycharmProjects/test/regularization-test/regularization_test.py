import numpy as np
import matplotlib.pyplot as plt
from reg_utils import sigmoid, relu, plot_decision_boundary, initialize_parameters, load_2D_dataset, predict_dec
from reg_utils import compute_cost, predict, forward_propagation, backward_propagation, update_parameters
import sklearn
import sklearn.datasets
import scipy.io
from testCases import *
from common.baseutil.plat_function import *

plt.rcParams['figure.figsize'] = (7.0, 4.0)
plt.rcParams['image.interpolation'] = 'nearest'
plt.rcParams['image.cmap'] = 'gray'


def forward_propagation_with_dropout(X, parameters, keep_prob):
    """

    :param X:
    :param parameters:
    :param keep_prob:
    :return:
    """
    # retrieve parameters
    W1 = parameters["W1"]
    b1 = parameters["b1"]
    W2 = parameters["W2"]
    b2 = parameters["b2"]
    W3 = parameters["W3"]
    b3 = parameters["b3"]

    # LINEAR -> RELU -> LINEAR -> RELU -> LINEAR -> SIGMOID
    Z1 = np.dot(W1, X) + b1
    A1 = relu(Z1)
    # 对A1进行随机失活正则化：
    D1 = np.random.rand(A1.shape[0], A1.shape[1])  # 生成一个跟A1的shape一样，值在(0-1)之间的随机小数
    D1 = D1 < keep_prob  # 将小于keep_prob的值为true(运算时就是1)，否则置为false(运算时就是0)
    A1 = np.multiply(A1, D1)  # 将A1余D1做对应元素相乘，即为将A1中的(1-keep_prob)比例的神经元的值变为了0(即为失活)
    A1 = A1 / keep_prob

    Z2 = np.dot(W2, A1) + b2
    A2 = relu(Z2)
    # 对A2进行随机失活正则化：
    D2 = np.random.rand(A2.shape[0], A2.shape[1])
    D2 = D2 < keep_prob
    A2 = A2 * D2
    A2 = A2 / keep_prob

    Z3 = np.dot(W3, A2) + b3
    A3 = sigmoid(Z3)

    cache = (Z1, D1, A1, W1, b1, Z2, D2, A2, W2, b2, Z3, A3, W3, b3)

    return A3, cache


# 单元测试：正向传播的随机失活验证
def forward_drop_test():
    X_assess, parameters = forward_propagation_with_dropout_test_case()
    A3, cache = forward_propagation_with_dropout(X_assess, parameters, keep_prob=0.7)
    print("A3 = " + str(A3))


# forward_drop_test()


def backward_propagation_with_dropout(X, Y, cache, keep_prob):
    m = X.shape[1]
    (Z1, D1, A1, W1, b1, Z2, D2, A2, W2, b2, Z3, A3, W3, b3) = cache

    dZ3 = A3 - Y
    dW3 = 1. / m * np.dot(dZ3, A2.T)
    db3 = 1. / m * np.sum(dZ3, axis=1, keepdims=True)
    dA2 = np.dot(W3.T, dZ3)

    dA2 = dA2 * D2  # 第一步
    dA2 = dA2 / keep_prob  # 第二步

    dZ2 = np.multiply(dA2, np.int64(A2 > 0))
    dW2 = 1. / m * np.dot(dZ2, A1.T)
    db2 = 1. / m * np.sum(dZ2, axis=1, keepdims=True)

    dA1 = np.dot(W2.T, dZ2)

    dA1 = dA1 * D1
    dA1 = dA1 / keep_prob

    dZ1 = np.multiply(dA1, np.int64(A1 > 0))
    dW1 = 1. / m * np.dot(dZ1, X.T)
    db1 = 1. / m * np.sum(dZ1, axis=1, keepdims=True)

    gradients = {"dZ3": dZ3, "dW3": dW3, "db3": db3, "dA2": dA2,
                 "dZ2": dZ2, "dW2": dW2, "db2": db2, "dA1": dA1,
                 "dZ1": dZ1, "dW1": dW1, "db1": db1}

    return gradients


def back_dropout_test():
    X_assess, Y_assess, cache = backward_propagation_with_dropout_test_case()

    gradients = backward_propagation_with_dropout(X_assess, Y_assess, cache, keep_prob=0.8)

    print("dA1 = " + str(gradients["dA1"]))
    print("dA2 = " + str(gradients["dA2"]))


# back_dropout_test()


def compute_cost_with_regularization(a3, Y, parameters, lambd):
    """
    L2正则化就是在花销函数后面加一个"尾巴"，这个尾巴由正则化系数lambd和权重矩阵来计算
    :param a3: 预测值
    :param Y:  真实值
    :param parameters: 参数值
    :param lambd: 正则化系数
    :return:
    """
    m = Y.shape[1]
    W1 = parameters["W1"]
    W2 = parameters["W2"]
    W3 = parameters["W3"]

    # 获的常规花销
    cost = compute_cost(a3, Y)

    # 计算正则化项
    L2_regularization_cost = (lambd / (2 * m)) * (np.sum(np.square(W1)) + np.sum(np.square(W2)) + np.sum(np.square(W3)))

    # 计算正则化后的花销
    cost = cost + L2_regularization_cost

    return cost


# 单元测试L2正则化花销函数
def test_L2_regularization_cost():
    A3, Y_assess, parameters = compute_cost_with_regularization_test_case()
    print("cost = " + str(compute_cost_with_regularization(A3, Y_assess, parameters, lambd=0.1)))


def backward_propagation_with_regularization(X, Y, cache, lambd):
    """
    L2 正则化后的反向传播中需要在每个dW计算是，把正则化项的求偏导算进去
    :param X:
    :param Y:
    :param cache:
    :param lambd:
    :return:
    """
    m = X.shape[1]
    (Z1, A1, W1, b1, Z2, A2, W2, b2, Z3, A3, W3, b3) = cache

    dZ3 = A3 - Y
    dW3 = 1. / m * np.dot(dZ3, A2.T) + (lambd / m) * W3
    db3 = 1. / m * np.sum(dZ3, axis=1, keepdims=True)

    dA2 = np.dot(W3.T, dZ3)
    dZ2 = np.multiply(dA2, np.int64(A2 > 0))
    dW2 = 1. / m * np.dot(dZ2, A1.T) + (lambd / m) * W2
    db2 = 1. / m * np.sum(dZ2, axis=1, keepdims=True)

    dA1 = np.dot(W2.T, dZ2)
    dZ1 = np.multiply(dA1, np.int64(A1 > 0))
    dW1 = 1. / m * np.dot(dZ1, X.T) + (lambd / m) * W1
    db1 = 1. / m * np.sum(dZ1, axis=1, keepdims=True)

    gradients = {"dZ3": dZ3, "dW3": dW3, "db3": db3,
                 "dA2": dA2, "dZ2": dZ2, "dW2": dW2, "db2": db2,
                 "dA1": dA1, "dZ1": dZ1, "dW1": dW1, "db1": db1}

    return gradients


# 单元测试L2正则化反向传播的dW计算
def test_L2_regularization_backward():
    X_assess, Y_assess, cache = backward_propagation_with_regularization_test_case()

    grads = backward_propagation_with_regularization(X_assess, Y_assess, cache, lambd=0.7)
    print("dW1 = " + str(grads["dW1"]))
    print("dW2 = " + str(grads["dW2"]))
    print("dW3 = " + str(grads["dW3"]))


# test_L2_regularization_backward()


# 正则化测试
def mode(X, Y, learning_rate=0.3, num_iterations=30000, print_cost=True, lambd=0, keep_prob=1):
    """
    :param X:
    :param Y:
    :param learning_rate:
    :param num_iterations:
    :param print_cost:
    :param lambd: 如果要添加 L2正则化 -- 那么就将参数lambd设置为非0的值。
                  控制偏差和方差度，太大会出现高偏差（欠拟合）：因为W趋于0；
                  太小会导致高方差（过拟合）：正则化项将失去作用;
                  lambda 是Python的一个关键字，所以使用lambd来代表lambda
    :param keep_prob: 如果要使用 dropout -- 那么就设置keep_prob为小于1的数。
    :return:
    """
    # 防止同时使用L2正则化和dropout。
    # 其实是可以同时使用它们的，但是为了测试目的，突出重点，所以单次只允许用其中一个。
    assert (lambd == 0 or keep_prob == 1)

    grads = {}
    costs = []
    m = X.shape[1]
    layers_dims = [X.shape[0], 20, 3, 1]

    parameters = initialize_parameters(layers_dims)

    for i in range(0, num_iterations):
        if keep_prob == 1:
            a3, cache = forward_propagation(X, parameters)
        elif keep_prob > 0 and keep_prob < 1:
            a3, cache = forward_propagation_with_dropout(X, parameters, keep_prob)

        if lambd == 0 and keep_prob == 1:
            grads = backward_propagation(X, Y, cache)
        elif lambd != 0:
            grads = backward_propagation_with_regularization(X, Y, cache, lambd)
        elif keep_prob != 1:
            grads = backward_propagation_with_dropout(X, Y, cache, keep_prob)

        parameters = update_parameters(parameters, grads, learning_rate)

        if print_cost and i % 1000 == 0:
            if lambd == 0:
                cost = compute_cost(a3, Y)
            else:
                cost = compute_cost_with_regularization(a3, Y, parameters, lambd)
            print("训练到第{}次是的cost为:{}".format(i, cost))
            costs.append(cost)

    plt_costs(costs, learning_rate)
    plt.show()
    return parameters


if __name__ == "__main__":
    print("__main__")
    # 蓝色的点代表门将开球被自己人接住，红点代表被对手接住
    # 目标：找出踢到那些位置比较容易让自己人接住
    train_X, train_Y, test_X, test_Y = load_2D_dataset()

    # 未使用任何正则化的时候，训练数据准确率为：94.7%；测试数据的准确率为：91.5%
    # parameters = mode(train_X, train_Y)

    # -L2正则化：训练数据的准确率为93.8%；测试数据的准确率为：93%；
    # 虽然训练数据的准确率降低了，但是测试数据的准确率有了明显提升，说明之前的存在过拟合的现象，现在则好了很多
    # parameters = mode(train_X, train_Y, lambd=0.7)  # lambd不能太小(会导致正则化无效)，也不能太大（导致高偏差）

    # dropout正则化：训练数据的准确率为94.3%，测试数据的准确率为:93.5%
    parameters = mode(train_X, train_Y, keep_prob=0.86, learning_rate=0.3)

    print("训练数据的预测准确度：")
    predictions_train = predict(train_X, train_Y, parameters)
    print("predictions_train =", predictions_train)

    print("测试数据的预测准确度：")
    predictions_test = predict(test_X, test_Y, parameters)
    print("predictions_test =", predictions_test)

    plt.title("Model regularization test")
    axes = plt.gca()
    axes.set_xlim([-0.75, 0.40])
    axes.set_ylim([-0.75, 0.65])
    plot_decision_boundary(lambda x: predict_dec(parameters, x.T), train_X, train_Y.ravel())
