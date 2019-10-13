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
    users = pd.read_table('./datasets/ml-1m/users.dat', sep='::', header=None, names=users_title, engine='python')
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
    movies = pd.read_table('./datasets/ml-1m/movies.dat', sep='::', header=None, names=movies_title, engine='python')
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
    ratings = pd.read_table('./datasets/ml-1m/ratings.dat', sep='::', header=None, names=ratings_title, engine='python')
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
         users_orig), open('./datasets/preprocess.p', 'wb'))


title_count, title_set, genres2int, features, targets_values, ratings, users, movies, data, movies_orig, users_orig = \
    pickle.load(open('./datasets/preprocess.p', mode='rb'))

head1 = data.head(1)
print(head1)

tf.keras.layers.concatenate
