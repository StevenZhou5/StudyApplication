import os
import numpy as np
import tensorflow as tf

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'


# 求矩阵的L2范数
def L2Test(X):
    L2X = np.dot(X, X.T)
    print(L2X.shape)
    print(X)
    print(X.T)
    print(L2X)
    return L2X


# 求矩阵中每两个向量之间的宇轩相似度
def cos_sim(L2X):
    cos_simX = np.zeros((L2X.shape[0], L2X.shape[1]))
    assert L2X.shape == cos_simX.shape
    for i in range(L2X.shape[0]):
        for j in range(L2X.shape[1]):
            distance_mul = np.sqrt(L2X[i][i] * L2X[j][j])
            sim_value = L2X[i][j] / distance_mul
            cos_simX[i][j] = sim_value

    print(cos_simX)


X = np.array([[1, 1], [1, 2], [1, 0]])
L2X = L2Test(X)

cos_sim(L2X)


# 矩阵顺时针旋转90度
def rotate_90degree_test(A):
    # step1:将A进行转职
    A_T = A.T
    # print("A_T:", A_T)

    # step2:讲A_T的列进行前后交换，构建交换矩阵（右乘基本矩阵可以起到交换列的作用）
    column = A_T.shape[1]
    # print("A_T的列数为：", column)
    exchange_column = np.zeros((column, column))
    maxChangeCount = int((column + 1) / 2)  # 最大需要交换的次数：5，6列交换三次；7，8列交换4次
    # print("maxChangeCount:", maxChangeCount)
    for l in range(0, maxChangeCount):
        i = l
        j = max(i, column - 1 - l)
        # print("i,j元为：", i, ";", j)
        exchange_column[i, j] = 1  # 第i列换到第j列
        exchange_column[j, i] = 1  # 第j列换到第i列
    # print("exchange_column初始值:", exchange_column)

    # step3:将A_T右乘exchange_column就是旋转90度后的矩阵
    A_rotate_90degree = np.dot(A_T, exchange_column)
    # print(A_rotate_90degree)
    return A_rotate_90degree


# A = np.array([[1, 1, 1, 5],
#               [12, 15, 22, 4],
#               [-2, 1, 0, 4],
#               ])
# B = rotate_90degree_test(A)
# print(B)
# C = rotate_90degree_test(B)
# print(C)


def inv_def_test():
    A = np.array([[1, 1, 1],
                  [12, 15, 22],
                  [-2, 1, 0],
                  ])
    B = np.linalg.inv(A)  # 求矩阵的逆
    print(A)
    print(np.linalg.det(A))
    print(B)
    print(np.dot(A, B))
    print(np.dot(B, A))
    C = np.array([[1], [20], [0]])
    print("求解：", np.dot(B, C))


# 计算A*W =B时，已知A向量和B向量，求解W向量的值时：A逆*A*W = A逆*B----W = A逆*B
def get_w_result(X, Y):
    X_inv = np.linalg.inv(X)
    W = np.dot(X_inv, Y)
    print("W结果为：", W)
    return W


A = np.array([[1, 1, 1],
              [12, 15, 22],
              [1, 1, -1],
              ])
B = np.array([[1], [20], [0]])


# W = get_w_result(A, B)
# print(np.matmul(A, W))


# 用单位矩阵交换矩阵的行或者列
def exchange_row_column_test():
    A = np.array([[0, 1, 0, 1, 5],
                  [2, 5, 7, 6, 8],
                  [3, 1, 4, 7, 6]])
    print(A)
    print(A[:, 2])
    # AX_23 = np.zeros((3, 3))  # 换行的话，矩阵的行列数必须等于原矩阵的行数
    AX_23 = np.identity(3)  # 生成一个单位矩阵
    AX_23[1, 2] = 1  # 把第A的第三行换到第二行
    print(AX_23)
    print(np.dot(AX_23, A))

    AY_35 = np.zeros((5, 5))  # 换列的话，矩阵的行列数必须等于原矩阵的列数
    AY_35[2, 4] = 1  # 把第A的第3列换到第5列
    print(AY_35)
    print(np.matmul(A, AY_35))


# exchange_row_column_test()


def take_special_row_and_column_test():
    atmp2 = np.array([[1, 2, 3, 4],
                      [5, 6, 7, 8],
                      [9, 10, 11, 12],
                      [3, 5, 8, 9],
                      [2, 4, 7, 5],
                      [8, 7, 2, 4]
                      ])
    print(atmp2)
    B = atmp2[0:5:2, 0:3:2]  # B矩阵等于取A矩阵0到5行每隔两行的行与0到3列每隔两列的列的元素组成的一个新的矩阵
    print("B", B)
    atmp2[0:5:3] = 999  #
    print(atmp2)
    atmp2[0:2:1, 0:4:2] = 888
    print(atmp2)


def get_shape():
    a = np.array([1])
    print(a.shape)
    a = np.array([11, 12])
    print(a.shape)
    a = np.array([[11, 22],
                  [21, 22]])
    print(a.shape)
    a = np.array(
        [
            [
                [
                    [1111, 1112, 1113, 1114],
                    [1121, 1122, 1123, 1124]
                ],
                [
                    [1211, 1212, 1213, 1214],
                    [1221, 1222, 1223, 1224]
                ],
                [
                    [1311, 1312, 1313, 1314],
                    [1321, 1322, 1323, 1324]
                ]
            ],
            [
                [
                    [2111, 2112, 2113, 2114],
                    [2121, 2122, 2123, 2124]
                ],
                [
                    [2211, 2212, 2213, 2214],
                    [2221, 2222, 2223, 2224]
                ],
                [
                    [2311, 2312, 2313, 2314],
                    [2321, 2322, 2323, 2324]
                ]
            ]
        ]
    )
    print(a.shape)


# get_shape()


def simple_operation_test():
    a1 = np.array([1, 2, 3, 4])
    a2 = np.array((1, 2, 3, 4))

    a3 = np.array([[1, 2, 3, 4], [5, 6, 7, 8]])

    # 第2行第3列
    print
    "*" * 50
    print
    a3[1, 2]
    print
    "*" * 50

    # 第2列所有 :表示所有的
    print
    "*" * 50
    print
    a3[:, 1]
    print
    "*" * 50

    # 第1行所有
    print
    "*" * 50
    print
    a3[0, :]
    print
    "*" * 50

    # 从第0个开始取值赋予999 并且以后每次右移2位继续取值赋值为999
    print
    "*" * 50
    atmp1 = np.array([1, 2, 3, 4])
    atmp1[0:3:2] = 999  # 3个值分别代表：0从第0行开始，3到第三行结束，2每隔两行
    print
    "atmp1[0:3:2]", atmp1
    print
    "*" * 50

    # 前面表示行往下到第二行间隔为1赋值为888，后面表示列到第四列间隔为2的列赋值为888，前面的0可以省略
    print
    "*" * 50
    atmp2 = np.array([[1, 2, 3, 4, 8],
                      [5, 6, 7, 8, 5],
                      [9, 10, 11, 12, 6],
                      [7, 5, 5, 2, 3],
                      [6, 10, 1, 1, 6]
                      ])
    # 前3个值分别代表：0从第0行开始，2到第二行结束，1每隔1行； 后三个值代表：0从第0列开始，4到第四列结束，2每隔2列
    atmp2[0:2:1, 0:4:2] = 888
    print
    "atmp2[0:2:1,0:4:2]", atmp2
    print
    "*" * 50

    # 反转一维矩阵
    print
    "*" * 50
    print
    "a2[::-1]", a2[::-1]
    print
    "*" * 50

    # 反转多维矩阵第一个-1表示列反转，第二个表示行反转 可以只反转一个
    print
    "*" * 50
    print
    "a3[::-1,::-1]", a3[::-1, ::-1]
    print
    "*" * 50

    # 当改变tmp1中的数据 原矩阵也会改变
    print
    "*" * 50
    tmp1 = a3[:, 1]
    tmp1[0] = 999
    print
    "a3", a3
    print
    "*" * 50

    # 产生一行矩阵
    print
    "*" * 50
    a4 = np.arange(21)
    print
    "a4", a4
    print
    "*" * 50

    # 产生多行矩阵 4行5列 其中reshape是会返回新的矩阵，resize是直接改变原始矩阵
    print
    "*" * 50
    a5 = np.arange(20).reshape(4, 5)
    print
    "a5", a5
    print
    "*" * 50

    # 在1到20中产生30个数矩阵
    print
    "*" * 50
    a6 = np.linspace(1, 20, 30)
    print
    "a6", a6
    print
    "*" * 50

    # 随机2*3 矩阵     （0，1】
    print
    "*" * 50
    a7 = np.random.random((2, 3))
    print
    "a7", a7
    print
    "*" * 50

    # 0矩阵 构造4*5 0矩阵
    print
    "*" * 50
    a8 = np.zeros((4, 5))
    print
    "a8", a8
    print
    "*" * 50

    # 0矩阵 构造4*5 1矩阵
    print
    "*" * 50
    a9 = np.ones((4, 5))
    print
    "a9", a9
    print
    "*" * 50

    # e矩阵 构造5*5 E矩阵(单位矩阵)
    print
    "*" * 50
    a10 = np.eye(5)
    print
    "a10", a10
    print
    "*" * 50

    ############矩阵计算
    print
    "*" * 50
    a11 = np.array([20, 30, 40, 50])
    a12 = np.array([10, 20, 30, 40])
    print
    "a11+a12", a11 + a12
    print
    "a11-a12", a11 - a12
    print
    "a11*a12", a11 * a12
    print
    "a11/a12", a11 / a12
    print
    "*" * 50

    # 对矩阵内的所有值平方
    print
    "*" * 50
    a13 = np.arange(4).reshape(2, 2)
    print
    "a13**2", a13 ** 2
    print
    "*" * 50

    # 对矩阵内的所有值乘2
    print
    "*" * 50
    print
    "a13*2", a13 * 2
    print
    "*" * 50

    # 对矩阵内的值求sin
    print
    "*" * 50
    print
    "numpy.sin(a13)", np.sin(a13)
    print
    "*" * 50

    # 判断返回boolean矩阵
    print
    "*" * 50
    print
    "a13<2", a13 < 2
    print
    "*" * 50

    # 数组内部计算
    print
    "*" * 50
    a14 = np.array(((1, 3), (4, 5)))
    print
    "*" * 50

    # 相当于所有相加
    print
    "*" * 50
    print
    "numpy.sum(a14)", np.sum(a14)
    print
    "*" * 50

    # 竖向相加
    print
    "*" * 50
    print
    "numpy.sum(a14,axis=0)", np.sum(a14, axis=0)
    print
    "*" * 50

    # 横向相加
    print
    "*" * 50
    print
    "numpy.sum(a14,axis=1)", np.sum(a14, axis=1)
    print
    "*" * 50

    # 计算每一列的和
    print
    "*" * 50
    print
    "a14.sum(axis=0)", a14.sum(axis=0)
    print
    "*" * 50

    # 计算每一行的和
    print
    "*" * 50
    print
    "a14.sum(axis=1)", a14.sum(axis=1)
    print
    "*" * 50

    # 计算每一列的最小值
    print
    "*" * 50
    print
    "a14.min(axis=0)", a14.min(axis=0)
    print
    "*" * 50

    # 计算每一列的最小值
    print
    "*" * 50
    print
    "a14.min(axis=1)", a14.min(axis=1)
    print
    "*" * 50

    # 计算每一列的最大值
    print
    "*" * 50
    print
    "a14.max(axis=0)", a14.max(axis=0)
    print
    "*" * 50

    # 计算每一列的最大值
    print
    "*" * 50
    print
    "a14.max(axis=1)", a14.max(axis=1)
    print
    "*" * 50

    # 列叠加
    print
    "*" * 50
    print
    "a14.cumsum(axis=0)", a14.cumsum(axis=0)
    print
    "*" * 50

    # 行叠加
    print
    "*" * 50
    print
    "a14.cumsum(axis=1)", a14.cumsum(axis=1)
    print
    "*" * 50

    # 矩阵所有最大值
    print
    "*" * 50
    print
    "a14.max()", a14.max()
    print
    "*" * 50
    # 矩阵所有最小值
    print
    "*" * 50
    print
    "a14.min()", a14.min()
    print
    "*" * 50
    ##矩阵的遍历
    print
    "*" * 50
    atmp3 = np.array([[1, 2, 3, 4], [5, 6, 7, 8], [5, 6, 7, 8]])
    for i in atmp3.flat:
        print
        i
    print
    "*" * 50

    # 矩阵的合并
    # 纵向合并
    a15 = np.ones((2, 2))
    a16 = np.eye(2)

    print
    "*" * 50
    print
    "numpy.vstack((a15,a16))", np.vstack((a15, a16))
    print
    "*" * 50

    # 横向合并
    print
    "*" * 50
    print
    "numpy.hstack((a15,a16))", np.hstack((a15, a16))
    print
    "*" * 50

    # 矩阵浅拷贝
    print
    "*" * 50
    a17 = np.eye(2)
    a18 = a17
    a18[0, 1] = 999
    print
    "浅拷贝", a17
    print
    "*" * 50

    # 矩阵深拷贝
    print
    "*" * 50
    a19 = np.eye(2)
    a20 = a19.copy()
    a20[0, 1] = 999
    print
    "深拷贝", a19
    print
    "*" * 50

    # 矩阵转置 行专列
    print
    "*" * 50
    a21 = np.array([[1, 2, 3, 4], [5, 6, 7, 8], [9, 10, 11, 12]])
    print
    "转置a21", a21.transpose()
    print
    "*" * 50

    # 查看矩阵的行 列数量
    print
    "*" * 50
    a22 = np.array([[1, 2, 3, 4], [5, 6, 7, 8], [9, 10, 11, 12]])
    print
    "行列数为：", a22.shape
    print
    "行数为：", a22.shape[0]
    print
    "列数为：", a22.shape[1]
    print
    "*" * 50

    # 矩阵tile
    print
    "*" * 50
    a23 = np.array([[1, 2, 3, 4], [5, 6, 7, 8], [9, 10, 11, 12]])
    # 内部横向扩展
    print
    "numpy.tile(a23,1)\n", np.tile(a23, (2))
    # 内部横向纵向扩展
    print
    "numpy.tile(a23,(2,2))\n", np.tile(a23, (2, 2))
    print
    "*" * 50

    # 矩阵排序获取下标 或者最大最小值(注意是下标)
    print
    "*" * 50
    a23 = np.array([1, 2, 3, 4, 10, 9, 8, 7])
    print
    "a23.argsort()", a23.argsort()
    print
    "a23.argmax()", a23.argmax()
    print
    "a23.argmin()", a23.argmin()
    print
    "*" * 50
