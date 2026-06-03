import numpy as np
import sys
import pandas as pd


class GM_1_1:
    def __init__(self):
        self.test_data = np.array(())  # 实验数据集
        self.add_data = np.array(())  # 一次累加产生数据
        self.argu_a = 0  # 参数a
        self.argu_b = 0  # 参数b
        self.MAT_B = np.array(())  # 矩阵B
        self.MAT_Y = np.array(())  # 矩阵Y
        self.modeling_result_arr = np.array(())  # 对实验数据的拟合值
        self.P = 0  # 小误差概率
        self.C = 0  # 后验方差比值

    def set_model(self, arr: list):
        self.__acq_data(arr)
        self.__compute()
        self.__modeling_result()

    def __acq_data(self, arr: list):  # 构建并计算矩阵B和矩阵Y
        self.test_data = np.array(arr).flatten()
        add_data = list()
        sum = 0
        for i in range(len(self.test_data)):
            sum = sum + self.test_data[i]
            add_data.append(sum)
        self.add_data = np.array(add_data)
        ser = list()
        for i in range(len(self.add_data) - 1):
            temp = (-1) * ((1 / 2) * self.add_data[i] + (1 / 2) * self.add_data[i + 1])
            ser.append(temp)
        B = np.vstack((np.array(ser).flatten(), np.ones(len(ser), ).flatten()))
        self.MAT_B = np.array(B).T
        Y = np.array(self.test_data[1:])
        self.MAT_Y = np.reshape(Y, (len(Y), 1))

    def __compute(self):  # 计算灰参数 a,b
        temp_1 = np.dot(self.MAT_B.T, self.MAT_B)
        temp_2 = np.matrix(temp_1).I
        temp_3 = np.dot(np.array(temp_2), self.MAT_B.T)
        vec = np.dot(temp_3, self.MAT_Y)
        self.argu_a = vec.flatten()[0]
        self.argu_b = vec.flatten()[1]

    def __predict(self, k: int) -> float:  # 定义预测计算函数
        part_1 = 1 - pow(np.e, self.argu_a)
        part_2 = self.test_data[0] - self.argu_b / self.argu_a
        part_3 = pow(np.e, (-1) * self.argu_a * k)
        return part_1 * part_2 * part_3

    def __modeling_result(self):  # 获得对实验数据的拟合值
        ls = [self.__predict(i + 1) for i in range(len(self.test_data) - 1)]
        ls.insert(0, self.test_data[0])
        self.modeling_result_arr = np.array(ls)

    def predict(self, number: int) -> list:  # 外部预测接口，预测后指定个数的数据
        prediction = [self.__predict(i + len(self.test_data)) for i in range(number)]
        return prediction

    def precision_evaluation(self):  # 模型精度评定函数
        error = [
            self.test_data[i] - self.modeling_result_arr[i]
            for i in range(len(self.test_data))
        ]
        aver_error = sum(error) / len(error)
        aver_test_data = np.sum(self.test_data) / len(self.test_data)
        temp1 = 0
        temp2 = 0
        for i in range(len(error)):
            temp1 = temp1 + pow(self.test_data[i] - aver_test_data, 2)
            temp2 = temp2 + pow(error[i] - aver_error, 2)
        square_S_1 = temp1 / len(self.test_data)
        square_S_2 = temp2 / len(error)
        self.C = np.sqrt(square_S_2) / np.sqrt(square_S_1)
        ls = [i
              for i in range(len(error))
              if np.abs(error[i] - aver_error) < (0.6745 * np.sqrt(square_S_1))
              ]
        self.P = len(ls) / len(error)
        print("精度指标P,C值为：", self.P, self.C)


if __name__ == "__main__":
    GM = GM_1_1()
    data = result_array = [float(value) for value in sys.argv[1].split(',')]
    GM.set_model(data)
    print(GM.predict(1)[0], end='')
