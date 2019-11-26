#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# import pandas as pd
import pickle
import time

import numpy as np
import tensorflow as tf
from sklearn.model_selection import train_test_split
from tensorflow.keras import layers, models

# file_path = '/Users/zhenwuzhou/.keras/datasets/preprocess.p'
file_path = "/hdfs/user/h_data_platform/platform/phonevideo/data_middle/cloudml_test/preprocess.p"
export_path = "/fds/test"


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
    tf.redu
    # 构建最终的评分预测模型
    rate_predict_model = models.Model(inputs=[model_user_feature.inputs, model_movie_feature.inputs],
                                      outputs=final_rate_layer)

    rate_predict_model.compile(optimizer=tf.keras.optimizers.Adam(), loss='mse')
    return rate_predict_model


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
        log_dir=export_path + '/tensorboardLogs'.format(model_name),
        # log_dir='/Users/zhenwuzhou/TensorBoardLogs/tensorboardLogs'.format(model_name),
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
