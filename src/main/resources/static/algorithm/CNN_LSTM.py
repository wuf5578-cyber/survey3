import numpy as np
import sys
from sklearn.preprocessing import MinMaxScaler
import torch
import torch.nn as nn


class CNN_LSTM(nn.Module):
    def __init__(self, num_features=1, out_channels=64, hidden_size=128, num_layers=2, dropout=0.2, num_classes=1):
        super(CNN_LSTM, self).__init__()
        self.conv = nn.Conv2d(in_channels=1, out_channels=out_channels, kernel_size=(3, num_features), padding=(1, 0))
        self.relu = nn.ReLU()
        self.maxpool = nn.MaxPool2d((2, 1))  # 只在特征维度上进行池化，时间维度上保持不变
        self.lstm = nn.LSTM(out_channels, hidden_size, num_layers, batch_first=True,dropout=dropout if num_layers > 1 else 0)
        self.fc = nn.Linear(hidden_size, num_classes)

    def forward(self, x):
        x = x.unsqueeze(1)
        x = self.conv(x)
        x = self.relu(x)
        x = self.maxpool(x)
        x = x.squeeze(3)
        x = x.permute(0, 2, 1)
        lstm_out, _ = self.lstm(x)
        out = self.fc(lstm_out[:, -1, :])
        return out
if __name__ == "__main__":
    #value1 = "0.00,0.05,0.28,0.46,0.32,0.06,0.36,0.61,0.70,0.69,0.60,1.32,1.85,2.50,3.10,0.99,-1.22,1.68,4.57"
    #data = np.array([float(value) for value in value1.split(',')])
    data = np.array([float(value) for value in sys.argv[1].split(',')])
    model = CNN_LSTM()
    model = torch.load('model-CNNLSTM.pth')
    data_min = data.min()
    data_max = data.max()
    normalized_data = (data - data_min) / (data_max - data_min) * 2 - 1
    seq = torch.FloatTensor(normalized_data)
    model.eval()
    with torch.no_grad():
        y_pred = model(seq[-10:].reshape(1, 10, 1))
    print((y_pred.item() + 1) / 2 * (data_max - data_min) + data_min, end='')
