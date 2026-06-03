import numpy as np
import sys
import pandas as pd

if __name__ == "__main__":
    #value1 = "0.00,0.05,0.28,0.46,0.32,0.06,0.36,0.61,0.70,0.69,0.60,1.32,1.85,2.50,3.10,0.99,1.22,1.68,4.57"
    #data = [float(value) for value in value1.split(',')]
    data = [float(value) for value in sys.argv[1].split(',')]
    # 创建时间步长
    time_steps = np.arange(1, len(data) + 1)
    # 用多项式进行曲线拟合
    degree = 3  # 多项式的次数
    coefficients = np.polyfit(time_steps, data, degree)
    # 构造多项式模型
    model = np.poly1d(coefficients)
    # 预测下一个时间步长的值
    next_time_step = len(data) + 1
    predicted_value = model(next_time_step)
    print(predicted_value, end='')
