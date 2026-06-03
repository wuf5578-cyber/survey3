import numpy as np
import sys
from sklearn.linear_model import LinearRegression

if __name__ == "__main__":
    #value1 = "0.00,0.05,0.28,0.46,0.32,0.06,0.36,0.61,0.70,0.69,0.60,1.32,1.85,2.50,3.10,0.99,1.22,1.68,4.57"
    #data = np.array([float(value) for value in value1.split(',')])
    data = np.array([float(value) for value in sys.argv[1].split(',')])
    # 构建特征矩阵X
    X = np.arange(1, len(data) + 1).reshape(-1, 1)
    # 构建目标变量y
    y = data
    # 创建线性回归模型
    model = LinearRegression()
    # 拟合模型
    model.fit(X, y)
    # 预测新数据
    x_new = np.array([[len(data) + 1]])
    y_pred = model.predict(x_new)
    print(y_pred[0], end='')
