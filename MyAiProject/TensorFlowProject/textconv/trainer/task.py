import numpy as np
import matplotlib.pyplot as plt
from tensorflow import keras
from tensorflow.keras import layers
from tensorflow.keras.preprocessing.sequence import pad_sequences

num_features = 3000  # 样本数量
sequence_length = 300  # 经过词嵌入矩阵的变换后，每一个句子的长度
embedding_dimension = 100  # 输出

export_path = "/fds/mvideo-rec"


# 下面用2D的多核卷积来对文本进行卷积操作：
def convolution():
    filter_size = [3, 4, 5]  # 每个过滤器一次选择几个词进行卷积
    inn = layers.Input(shape=(sequence_length, embedding_dimension, 1))
    cnns = []
    for size in filter_size:
        conv = layers.Conv2D(filters=64, kernel_size=(size, embedding_dimension),
                             strides=1, padding='valid', activation='relu')(inn)
        # 在执行完文本卷积操作后，length变为了（sequence_length-size+1）
        pool = layers.MaxPool2D(pool_size=(sequence_length - size + 1, 1), padding='valid')(conv)
        cnns.append(pool)
    outt = layers.concatenate(cnns)  # 将所有文本卷积和池化后的向量矩阵拼接成一个（3*64，1）的长向量:shape:[None, 1, 1, 192]

    model = keras.Model(inputs=inn, outputs=outt)
    return model


def cnn_mufiter():
    model = keras.Sequential([
        layers.Embedding(input_dim=num_features, output_dim=embedding_dimension, input_length=sequence_length),
        layers.Reshape((sequence_length, embedding_dimension, 1)),
        convolution(),
        layers.Flatten(),
        layers.Dense(10, activation='relu'),
        layers.Dropout(0.2),
        layers.Dense(1, activation='sigmoid'),
    ])

    model.compile(optimizer=keras.optimizers.Adam(),
                  loss=keras.losses.BinaryCrossentropy(),
                  metrics=['accuracy'])
    return model


def main():
    (trainX, trainY), (testX, testY) = keras.datasets.imdb.load_data(path='./imdb.npz',
                                                                     num_words=num_features)  # 加载训练和测试数据
    print(trainX.shape)
    print(trainY.shape)
    print(testX.shape)
    print(testY.shape)

    # trainX 和 testX的句子 都用300维词嵌入矩阵中的一个向量表示
    trainX = pad_sequences(trainX, maxlen=sequence_length)
    testX = pad_sequences(testX, maxlen=sequence_length)
    print(trainX.shape)
    print(trainY.shape)
    print(testX.shape)
    print(testY.shape)

    model = cnn_mufiter()
    print(model.summary())

    history = model.fit(trainX, trainY, batch_size=64, epochs=5, validation_split=0.1, validation_data=(testX, testY))

    plt.plot(history.epoch, history.history['loss'], label='loss')
    plt.plot(history.epoch, history.history['val_loss'], label='bal_loss')
    plt.legend()  # 显示右上角的指示

    plt.plot(history.epoch, history.history['accuracy'])
    plt.plot(history.epoch, history.history['val_accuracy'])
    plt.legend(['acc', 'val_accuracy'], loc='upper left')

    print("训练结束")
    model.save(export_path)


if __name__ == '__main__':
    main()
