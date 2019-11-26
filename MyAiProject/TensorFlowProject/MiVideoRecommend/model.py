#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import pandas as pd
from sklearn.model_selection import train_test_split
import numpy as np
from collections import Counter
import tensorflow as tf

import os
import pickle
import re
from tensorflow.python.ops import math_ops
from tensorflow.keras import layers, models
import time


# 下面要对数据进行一些预处理操作
# UserID,职业ID和MovieID不用变
# Age字段，转换成7个连续的数字表示0-6：每个代表一个年龄段
# 电影风格Genres字段要转换成数字：首先将电影风格中的每种风格对应的描述词语在One-Hot表示的字典中去查询出来，
# 每个风格单词就对应一个One-Hot表征向量，把所有风格的One-Hot表征向量相加就是这个电影的风格组合描述
# 电影名称的title字段处理方式和电影风格类似，（另外title中的年份需要去掉，年份不应该加到标题特征中）
# 电影风格和电影名称要使用同一个one-hot词典中表示成相同长度的表征向量，方便在神经网络中处理；空白部分用'<PAD>'对应的数字填充
def load_data():
    # ================================================User数据处理相关================================================
    # 读取User数据
    users_title = ['UserID', 'Gender', 'Age', 'JobID', 'Zip-code']
    users = pd.read_table('/Users/zhenwuzhou/.keras/datasets/ml-1m/users.dat', sep='::', header=None, names=users_title, engine='python')
    users = users.filter(regex='UserID|Gender|Age|JobID')
    users_orig = users.values

    # 改变User数据中的性别和年龄
    gender_map = {'F': 0, 'M': 1}
    users['Gender'] = users['Gender'].map(gender_map)

    age_map = {val: ii for ii, val in enumerate(set(users['Age']))}
    users['Age'] = users['Age'].map(age_map)
    #     print(users.head(3))

    # ================================================Movie数据处理相关================================================
    # 读取Movie数据集
    movies_title = ['MovieID', 'Title', 'Genres']
    movies = pd.read_table('/Users/zhenwuzhou/.keras/datasets/ml-1m/movies.dat', sep='::', header=None, names=movies_title, engine='python')
    movies_orig = movies.values

    # 将title中的年份去掉
    pattern = re.compile(r'(.*)\((\d+)\)$')  # '(第一个字符串)((第二个字符串左右两边要有括号且在最后一个货号内))'

    title_map = {val: pattern.match(val).group(1) for ii, val in enumerate(set(movies['Title']))}
    movies['Title'] = movies['Title'].map(title_map)

    # 将电影名称转成数字字典中的向量
    # 将title转换成数字字典，既用表征向量表示
    title_set = set()
    for val in movies['Title'].str.split():  # 将名字拆分成字符串数组：如[Toy, Story]；
        #     print(val) # 每一个val对应一个['Grumpier', 'Old', 'Men']
        title_set.update(val)  # 将val中没有在title_set中的单词添加到title_set中
    title_set.add('<PAD>')
    # 将电影标题分割的词，转换成了字典的形式
    title2int = {val: ii for ii, val in enumerate(title_set)}  # {'Takes': 0, 'Woke': 1,'Midnight': 2,...}

    # 将电影Title转成等长的数字列表，长度15
    title_count = 15
    # title循环中的val为：“Close Shave, A”  ,tile2int[row]为每一个key为‘Close’，‘Shave,’，‘A’时在title2int中的value(一个数字)
    # 最后的val是一组数字组成的数组
    title_map2 = {val: [title2int[row] for row in val.split()] for ii, val in enumerate(set(movies['Title']))}

    for key in title_map2:  # key就是这样的每一个"Close Shave, A","Return to Oz "电影的名称
        # print(title) # "Close Shave, A"
        # print(title_map2[key]) # [4066, 3272, 3331]
        for cnt in range(title_count - len(title_map2[key])):
            # print(cnt) # 0,1,2这样的数字
            # print(title2int['<PAD>']) #2412
            title_map2[key].insert(len(title_map2[key]) + cnt, title2int['<PAD>'])
        # print(title_map2[key]) # [4066, 3272, 3331, 2412, 2412, 2412, 2412, 2412, 2412, 2412, 2412, 2412, 2412, 2412, 2412]
        # 每一次循环就是将不到长度不到15的整型数组补全至15，不到的地方用title2int['<PAD>']=2412补上
    movies['Title'] = movies['Title'].map(title_map2)

    # 将电影类型转换成数字字典
    genres_set = set()
    for val in movies['Genres'].str.split('|'):
        genres_set.update(val)
    genres_set.add('<PAD>')
    genres2int = {val: ii for ii, val in enumerate(genres_set)}

    max_genre_length = max(genres2int.values())  # 最多有多少个类型，'<PAD>'也算一个
    # 将电影类型转换成等长的数字列表，长度为18
    genres_map = {val: [genres2int[row] for row in val.split('|')] for ii, val in enumerate(set(movies['Genres']))}
    for key in genres_map:
        #     print(genres_map[key])#[8, 9, 12]
        for cnt in range(max_genre_length - len(genres_map[key])):
            genres_map[key].insert(len(genres_map[key]) + cnt, genres2int['<PAD>'])
    #     print(genres_map[key]) #[8, 9, 12, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3]
    movies['Genres'] = movies['Genres'].map(genres_map)

    #     print(movies.head(1))

    # ================================================评分数据处理相关================================================
    ratings_title = ['UserID', 'MovieID', 'ratings', 'timestamps']  # rating是作为训练输出的值
    ratings = pd.read_table('/Users/zhenwuzhou/.keras/datasets/ml-1m/ratings.dat', sep='::', header=None, names=ratings_title, engine='python')
    ratings = ratings.filter(regex='UserID|MovieID|ratings')

    #     print(ratings.head(100))

    # ================================================数据合并处理相关================================================
    # 合并三个表
    data = pd.merge(pd.merge(ratings, users), movies)
    # print(data.head(1))

    # 将数据分成X和Y两张表
    target_fields = ['ratings']
    features_pd, targets_pd = data.drop(target_fields, axis=1), data[target_fields]

    features = features_pd.values
    targets_values = targets_pd.values

    #     print(features.shape)

    # title_count：Title字段的长度（15）
    # title_set：Title文本的集合
    # genres2int：电影类型转数字的字典
    # features：是输入X
    # targets_values：是学习目标y
    # ratings：评分数据集的Pandas对象
    # users：用户数据集的Pandas对象
    # movies：电影数据的Pandas对象
    # data：三个数据集组合在一起的Pandas对象
    # movies_orig：没有做数据处理的原始电影数据
    # users_orig：没有做数据处理的原始用户数据
    return title_count, title_set, genres2int, features, targets_values, ratings, users, movies, data, movies_orig, users_orig


def init_and_save_data():
    # 加载数据
    title_count, title_set, genres2int, features, targets_values, ratings, users, movies, data, movies_orig, users_orig = load_data()

    # 存入文件中
    pickle.dump(
        (title_count, title_set, genres2int, features, targets_values, ratings, users, movies, data, movies_orig,
         users_orig), open('/Users/zhenwuzhou/.keras/datasets/preprocess.p', 'wb'))


def create_user_feature_model(features):
    # 处理User的信息来构建生成用户特征向量的模型
    user_embed_dim = 32

    # 用户Uid信息处理
    uid_array = features.take(0, 1)  # array([1, 2, 12, ..., 5780, 5851, 5938], dtype=object)
    uid_max = max(uid_array) + 1  # 6040+1 用户Id的个数
    uid_inputs = layers.Input(shape=())
    embedded_uid = layers.Embedding(uid_max, user_embed_dim, input_length=1)(uid_inputs)  # 嵌入后的uid
    flattened_embedded_uid = layers.Flatten()(embedded_uid)  # 打平

    # 用户性别信息处理
    gender_array = features.take(2, 1)  # array([0, 1, 1, ..., 1, 0, 1], dtype=object)
    gender_max = max(gender_array) + 1  # 1+1=2 用户性别个数
    gender_inputs = layers.Input(shape=())
    embedded_gender = layers.Embedding(gender_max, user_embed_dim, input_length=1)(gender_inputs)  # 嵌入后的性别
    flattened_embedded_gender = layers.Flatten()(embedded_gender)

    # 用户年龄信息处理
    age_class_array = features.take(3, 1)  # array([0, 5, 6, ..., 4, 4, 6], dtype=object)
    age_class_max = max(age_class_array) + 1  # 6+1 年龄类别个数
    age_class_inputs = layers.Input(shape=())
    embedded_age_class = layers.Embedding(age_class_max, user_embed_dim, input_length=1)(age_class_inputs)  # 嵌入后的年龄类别
    flattened_embedded_age_class = layers.Flatten()(embedded_age_class)

    # 用户职业信息处理
    job_array = features.take(4, 1)  # array([10, 16, 12, ..., 17, 20, 1], dtype=object)
    job_max = max(job_array) + 1  # 20+1 职业个数
    job_inputs = layers.Input(shape=())
    embedded_job = layers.Embedding(job_max, user_embed_dim, input_length=1)(job_inputs)  # 嵌入后的职业
    flattened_embedded_job = layers.Flatten()(embedded_job)

    # 将打平后的嵌入式信息进行连接:(1000209, 128)
    user_concatenate = layers.concatenate(
        [flattened_embedded_uid, flattened_embedded_gender, flattened_embedded_age_class, flattened_embedded_job])

    # 进行全连接的神经网络搭建
    user_feature_layer1 = layers.Dense(200)(user_concatenate)
    user_final_layer = layers.Dense(32)(user_feature_layer1)

    # 最后构建出用户特征提取的模型：模型最终将输出（1000209, 32）的矩阵数据
    model_user_feature = models.Model(inputs=[uid_inputs, gender_inputs, age_class_inputs, job_inputs],
                                      outputs=user_final_layer)

    return model_user_feature, uid_array, gender_array, age_class_array, job_array


def list_to_array(list):
    array = []
    for val in list:
        array.append(np.array(val, dtype=int))
    return np.array(array, dtype=int)


def create_movie_feature_model(features, title_set, genres_set):
    # 处理电影数据来构建生成电影特征向量的模型
    movie_embed_dim = 32

    # ===========电影ID信息处理
    movie_id_array = features.take(1, 1)  # array([1193, 1193, 1193, ..., 2845, 3607, 2909], dtype=object)
    movie_id_max = max(movie_id_array) + 1  # 3952+1
    movie_id_inputs = layers.Input(shape=())
    embedded_movie_id = layers.Embedding(movie_id_max, movie_embed_dim, input_length=1)(movie_id_inputs)
    flattened_embedded_movie_id = layers.Flatten()(embedded_movie_id)

    # ===========电影名称信息处理：如果是标题需要卷积加池化，如果是电影标题特征分词那么可以不用卷积池化
    movie_title_array = list_to_array(features.take(5, 1))  # 注意这里比较坑，需要把list转换成array
    max_movie_title = len(title_set)  # 5215
    movie_title_input_length = len(movie_title_array[0])  # 15
    movie_title_inputs = layers.Input(shape=(movie_title_input_length,))
    embedded_movie_title = layers.Embedding(max_movie_title, movie_embed_dim, input_length=movie_title_input_length)(
        movie_title_inputs)
    # 要进行卷积操作需要将shape(None,15,32)变为shape(None,15,32，1)
    conv_inputs = layers.Reshape((movie_title_input_length, movie_embed_dim, 1))(embedded_movie_title)
    movie_title_window_sizes = {2, 3, 4, 5}  # 文本卷积滑动窗口，分别滑动2, 3, 4, 5个单词
    movie_title_conv_filter_num = 8  # 卷积操作时需要的过滤器个数
    conv_pool_movie_title_layer_list = []  # 卷积池化后的每一层
    for window_size in movie_title_window_sizes:
        conv_layer = layers.Conv2D(filters=movie_title_conv_filter_num,
                                   kernel_size=(window_size, movie_embed_dim), activation='relu')(conv_inputs)

        # 在执行完文本卷积操作后，length变为了（movie_title_input_length-window_size+1）
        pool_layer = layers.MaxPool2D(pool_size=(movie_title_input_length - window_size + 1, 1))(conv_layer)

        conv_pool_movie_title_layer_list.append(pool_layer)

    # 将池化后的结果连接在一起：由4个(None, 1, 1, 8)变为(None, 1, 1, 32)
    movie_title_concatenate_layer = layers.concatenate(conv_pool_movie_title_layer_list)
    # 最终打平后的电影标题(None, 32)
    flattened_final_movie_title = layers.Flatten()(movie_title_concatenate_layer)

    # ===========电影类型标签信息处理：先embeding然后把特征向量做加和,最后生成一个(None, 32)的嵌入表征来代表18个标签的组合值
    movie_genre_array = list_to_array(features.take(6, 1))  # 注意这里同样要把list转换成array
    max_movie_genre = len(genres_set)  # 19
    movie_genre_input_length = len(movie_genre_array[0])  # 18
    # 先embeding，然后把特征向量做加和
    movie_genre_inputs = layers.Input(shape=(movie_genre_input_length,))
    embedded_movie_genre = layers.Embedding(max_movie_genre, movie_embed_dim, input_length=movie_genre_input_length)(
        movie_genre_inputs)
    # 将所有嵌入后的特征：注意axis=1，最后shape=(None, 32)，因为每32个代表一个类型标签，我们要把18个类型标签整合成一个新的32维向量，
    # 这个新的32维向量包含了18个特征的值
    sumed_embedded_movie_genre = tf.reduce_sum(embedded_movie_genre, axis=1, keepdims=False)

    # ===========将电影ID、电影名称、电影类型标签进行整合:shape=(None, 96)
    movie_concatenate = layers.concatenate(
        [flattened_embedded_movie_id, flattened_final_movie_title, sumed_embedded_movie_genre])

    # ===========进行全连接的神经网络搭建
    movie_feature_layer1 = layers.Dense(200)(movie_concatenate)
    movie_final_layer = layers.Dense(32)(movie_feature_layer1)

    model_movie_feature = models.Model(inputs=[movie_id_inputs, movie_title_inputs, movie_genre_inputs],
                                       outputs=movie_final_layer)
    return model_movie_feature, movie_id_array, movie_title_array, movie_genre_array


def create_model_rate_predict(model_user_feature, model_movie_feature):
    # 最后整合模型：用用户特征与电影特征结合起来构成完整的评分模型
    # 用户特征与电影特征相乘
    layerMultiply = layers.multiply([model_user_feature.output, model_movie_feature.output])
    # 用相乘之后的值再相加:注意这里一定要keepdims=True，不然输出shape为（None，），这样在梯度下降的时候会有错误
    final_rate_layer = tf.reduce_sum(layerMultiply, axis=1, keepdims=True)

    # 构建最终的评分预测模型
    rate_predict_model = models.Model(inputs=[model_user_feature.inputs, model_movie_feature.inputs],
                                      outputs=final_rate_layer)

    rate_predict_model.compile(optimizer=tf.keras.optimizers.Adam(), loss='mse')
    return rate_predict_model


file_path = '/Users/zhenwuzhou/.keras/datasets/preprocess.p'
# file_path = "/hdfs/user/h_data_platform/platform/phonevideo/data_middle/cloudml_test/preprocess.p"
export_path = "/fds/test"


def main():
    title_count, title_set, genres2int, genres_set, features, targets_values, ratings, users, movies, data, movies_orig, users_orig \
        = pickle.load(open(file_path, mode='rb'))

    print('数据读取完成')
    print(features.shape)
    print(features[0])  # UserID,MovieID,Gender,Age,JobID,Title,Genres

    print(len(genres_set))
    # ===================================================创建用户特征生成模型===================================================
    model_user_feature, uid_array, gender_array, age_class_array, job_array \
        = create_user_feature_model(features)

    # ===================================================创建电影特征生成模型===================================================
    model_movie_feature, movie_id_array, movie_title_array, movie_genre_array \
        = create_movie_feature_model(features, title_set, genres_set)

    # ===================================================创建评分预测模型===================================================
    rate_predict_model = create_model_rate_predict(model_user_feature, model_movie_feature)

    # 初始化评分预测模型
    rate_predict_model.compile(optimizer=tf.keras.optimizers.Adam(), loss='mse', metrics=['acc'])
    rate_predict_model.summary()

    # ===================================================开始进行模型训练===================================================
    # 取50000条数据作为测试数据
    test_size = 50000
    random_state = 666
    train_uid_array, test_uid_array = train_test_split(uid_array, test_size=test_size, random_state=random_state)
    train_gender_array, test_gender_array = train_test_split(gender_array, test_size=test_size,
                                                             random_state=random_state)
    train_age_class_array, test_age_class_array = train_test_split(age_class_array, test_size=test_size,
                                                                   random_state=random_state)
    train_job_array, test_job_array = train_test_split(job_array, test_size=test_size, random_state=random_state)
    train_movie_id_array, test_movie_id_array = train_test_split(movie_id_array, test_size=test_size,
                                                                 random_state=random_state)
    train_movie_title_array, test_movie_title_array = train_test_split(movie_title_array, test_size=test_size,
                                                                       random_state=random_state)
    train_movie_genre_array, test_movie_genre_array = train_test_split(movie_genre_array, test_size=test_size,
                                                                       random_state=random_state)

    train_targets_values, test_targets_values = train_test_split(targets_values, test_size=test_size,
                                                                 random_state=random_state)

    # TensorBoard的使用
    # 设定格式化模型名称，以时间戳作为标记
    model_name = rate_predict_model.name.format(int(time.time()))
    # 设定存储位置，每个模型不一样的路径
    tensorboard = tf.keras.callbacks.TensorBoard(
        # log_dir=export_path + '/tensorboardLogs'.format(model_name),
        log_dir='/Users/zhenwuzhou/TensorBoardLogs/tensorboardLogs'.format(model_name),
        histogram_freq=1, batch_size=32,
        write_graph=True, write_grads=False, write_images=True,
        embeddings_freq=0, embeddings_layer_names=None,
        embeddings_metadata=None, embeddings_data=None, update_freq=500
    )

    # 把训练数据都转为float类型，不然在tf2.0以下会报错
    train_uid_array = np.array(train_uid_array, dtype=float)
    train_gender_array = np.array(train_gender_array, dtype=float)
    train_age_class_array = np.array(train_age_class_array, dtype=float)
    train_job_array = np.array(train_job_array, dtype=float)
    train_movie_id_array = np.array(train_movie_id_array, dtype=float)
    train_movie_title_array = np.array(train_movie_title_array, dtype=float)
    train_movie_genre_array = np.array(train_movie_genre_array, dtype=float)
    train_targets_values = np.array(train_targets_values, dtype=float)

    # 把测试数据也都转为float类型
    test_uid_array = np.array(test_uid_array, dtype=float)
    test_gender_array = np.array(test_gender_array, dtype=float)
    test_age_class_array = np.array(test_age_class_array, dtype=float)
    test_job_array = np.array(test_job_array, dtype=float)
    test_movie_id_array = np.array(test_movie_id_array, dtype=float)
    test_movie_title_array = np.array(test_movie_title_array, dtype=float)
    test_movie_genre_array = np.array(test_movie_genre_array, dtype=float)
    test_targets_values = np.array(test_targets_values, dtype=float)

    history = rate_predict_model.fit(
        [train_uid_array, train_gender_array, train_age_class_array, train_job_array,
         train_movie_id_array, train_movie_title_array, train_movie_genre_array],
        train_targets_values, batch_size=32, epochs=100,
        validation_data=(
            [test_uid_array, test_gender_array, test_age_class_array, test_job_array,
             test_movie_id_array, test_movie_title_array, test_movie_genre_array],
            test_targets_values), callbacks=[tensorboard])

    print("训练任务完成了")

    model_user_feature.save(export_path + '/model_user_feature.h5')
    model_movie_feature.save(export_path + '/model_movie_feature.h5')
    rate_predict_model.save(export_path + '/rate_predict_model.h5')

    print("模型存储完毕")
    # ===================================================用训练好的模型预测===================================================
    # predict_rates = rate_predict_model.predict(
    #     [uid_array, gender_array, age_class_array, job_array, movie_id_array, movie_title_array, movie_genre_array])
    #
    # print('predict_rates的shape为：', predict_rates.shape)
    # print(predict_rates)


if __name__ == '__main__':
    main()
