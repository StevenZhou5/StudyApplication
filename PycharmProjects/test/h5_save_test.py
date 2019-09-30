#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import numpy as np
import h5py


def save_data(file_name):
    name = '周振武'
    age = 18
    f = h5py.File('datasets/' + str(file_name) + '.h5', 'w')  # 注意'w'必须小写
    f.create_dataset('name', data=name)  # 可以使用这种Key——Value形式存储
    f['age'] = age  # 也可以使用这种Key——Value形式存储
    f.close()


def get_data(file_name):
    f = h5py.File('datasets/' + str(file_name) + '.h5', 'r')
    print(f['name'][()])
    print(f['age'][()])
    f.close()


if __name__ == "__main__":  # 程序的入口，类似Java的public static void mian
    save_data('user_info')
    get_data('user_info')
