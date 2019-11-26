#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# https://docs.python.org/3.7/distutils/setupscript.html#installing-package-data
import setuptools

setuptools.setup(name='cloudml-tutorial-01', version='3.7', packages=['trainer'],
                 package_data={'': ['*.txt'], 'mypkg': ['data/*.dat']},  # 表示包含所有目录下的txt文件和mypkg/data目录下的所有dat文件。
                 # data_files=[('bitmaps', ['bm/b1.gif', 'bm/b2.gif']),
                 #             ('config', ['cfg/data.cfg']),
                 #             ('/etc/init.d', ['init-script']),
                 #             ('txt', ['trainer/requirements.txt', '/'])],
                 install_requires=[ # 用来配置依赖的包，在执行任务前会先执行pip install去按照相关的包
                     'tensorflow==2.0.0b0',
                     'scikit-learn',
                     'panda',
                     'pandas'
                 ],
                 )
