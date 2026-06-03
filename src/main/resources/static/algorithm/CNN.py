import numpy as np
import sys
from sklearn.preprocessing import MinMaxScaler
import torch
import torch.nn as nn


class CNNnetwork(nn.Module):
    def __init__(self):
        super().__init__()
        self.conv1d = nn.Conv1d(1, 64, kernel_size=2)
        self.relu = nn.ReLU(inplace=True)
        self.Linear1 = nn.Linear(64 * 9, 50)
        self.Linear2 = nn.Linear(50, 1)

    def forward(self, x):
        x = self.conv1d(x)
        x = self.relu(x)
        x = x.view(-1)
        x = self.Linear1(x)
        x = self.relu(x)
        x = self.Linear2(x)
        return x


def create_dataset(data, num):
    inputs = []
    targets = []
    scaler = MinMaxScaler(feature_range=(-1, 1))  # 创建归一化器
    for i in range(len(data)):
        row = data.iloc[i].dropna().values  # 删除空值并转换为numpy数组
        if len(row) >= num:
            # 对数据进行归一化
            normalized_row = scaler.fit_transform(row.reshape(-1, 1)).flatten()
            for j in range(len(normalized_row) - num):
                inputs.append(normalized_row[j:j + num])
                targets.append([normalized_row[j + num]])
    # 将inputs和targets转换为PyTorch的张量
    inputs = torch.tensor(inputs, dtype=torch.float32)
    targets = torch.tensor(targets, dtype=torch.float32)
    # 将输入数据reshape成LSTM所需的形状 (samples, time steps, features)
    return inputs, targets


if __name__ == "__main__":
    #value1 = "0.00,0.05,0.28,0.46,0.32,0.06,0.36,0.61,0.70,0.69,0.60,1.32,1.85,2.50,3.10,0.99,-1.22,1.68,4.57"
    #data = np.array([float(value) for value in value1.split(',')])
    data = np.array([float(value) for value in sys.argv[1].split(',')])
    torch.manual_seed(101)
    model = CNNnetwork()
    model = torch.load('src/main/resources/static/algorithm/model-CNN.pth')
    data_min = data.min()
    data_max = data.max()
    normalized_data = (data - data_min) / (data_max - data_min) * 2 - 1
    seq = torch.FloatTensor(normalized_data)
    model.eval()
    with torch.no_grad():
        y_pred = model(seq[-10:].reshape(1, 1, -1))
    print((y_pred.item() + 1) / 2 * (data_max - data_min) + data_min, end='')
