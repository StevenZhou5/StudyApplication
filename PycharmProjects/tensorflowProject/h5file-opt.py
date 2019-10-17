import h5py as h5py
import numpy as np


def read():
    # read
    f = h5py.File("/Users/zhenwuzhou/.keras/datasets/xception_weights_tf_dim_ordering_tf_kernels.h5", 'r')  # 打开h5文件
    for key in f.keys():  # 查看所有键值

        print(f[key].name)
        if f[key].name == '/dense_2':
            print('dense_2的长度为:', len(f[key]))
            for key2 in f[key].keys():
                print("key2的值为：", f[key][key2])
        # print(f[key].shape)
        # print(f[key].value)
    f.close()


def delete_key():
    # f = h5py.File('file.h5', 'a')
    # dsetname = 'del'
    f = h5py.File("/Users/zhenwuzhou/.keras/datasets/xception_weights_tf_dim_ordering_tf_kernels.h5", 'a')  # 读写权限都打开
    dsetname = '/input_1'
    if dsetname in f.keys():
        f.__delitem__(dsetname)




def delete_in_group():
    # 如果是组中的dset
    f = h5py.File('file.h5', 'a')
    dsetname = 'del'
    if dsetname in f['grpname'].keys():
        f['grpname'].__delitem__(dsetname)


def create_test():
    # create and write
    f = h5py.File("testh.h5", 'w')  # 创建一个h5文件，文件指针是f
    f['data'] = imgData  # 将数据写入文件的主键data下面
    f['labels'] = range(3)  # 将数据写入文件的主键labels下面
    f.close()  # 关闭文件
