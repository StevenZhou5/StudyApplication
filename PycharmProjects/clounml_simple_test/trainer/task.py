#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import tensorflow as tf
import numpy as np
from tensorflow import keras

file_path = "/hdfs/user/h_data_platform/platform/phonevideo/data_middle/cloudml_test/imdb.npz"
export_path = "/fds/test"


def main():
    (trainXFile, trainYFile), (testXFile, testYFile) = keras.datasets.imdb.load_data(path=file_path,
                                                                                     num_words=3000)  # 加载训练和测试数据
    print('GPU_Available', tf.test.is_gpu_available())
    print(trainXFile.shape)
    print(trainYFile.shape)
    print(testXFile.shape)
    print(testYFile.shape)

    trainX = np.random.randn(100)
    trainY = 2 * trainX + 5

    model = tf.keras.Sequential([
        tf.keras.layers.Dense(1, input_shape=(1,))
    ])

    optimizer = tf.keras.optimizers.Adam(0.01)
    model.compile(optimizer, loss='mse')

    history = model.fit(trainX, trainY, epochs=500)

    model.save_weights(export_path + '/test_weight.h5')
    print(model.weights)


if __name__ == '__main__':
    main()
