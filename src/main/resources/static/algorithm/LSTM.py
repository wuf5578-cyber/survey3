import torch
import torch.nn as nn
import numpy as np
import sys


class LSTMnetwork(nn.Module):
    def __init__(self, input_size=1, hidden_size=100, output_size=1):
        super().__init__()
        self.hidden_size = hidden_size
        # 定义LSTM层
        self.lstm = nn.LSTM(input_size, hidden_size)
        # 定义全连接层
        self.linear = nn.Linear(hidden_size, output_size)
        # 初始化h0，c0
        self.hidden = (torch.zeros(1, 1, self.hidden_size),
                       torch.zeros(1, 1, self.hidden_size))

    def forward(self, seq):
        # 前向传播的过程是输入->LSTM层->全连接层->输出

        # https://pytorch.org/docs/stable/generated/torch.nn.LSTM.html?highlight=lstm#torch.nn.LSTM
        # 在观察查看LSTM输入的维度，LSTM的第一个输入input_size维度是(L, N, H_in), L是序列长度，N是batch size，H_in是输入尺寸，也就是变量个数
        # LSTM的第二个输入是一个元组，包含了h0,c0两个元素，这两个元素的维度都是（D∗num_layers,N,H_out)，D=1表示单向网络，num_layers表示多少个LSTM层叠加，N是batch size，H_out表示隐层神经元个数

        '''
        pytorch中LSTM输入为[time_step,batch,feature],这里窗口time_step=12,feature=1[1维数据]，batch我们这里设置为1
        所以使用seq.view(len(seq),1,-1)将tensor[12]数据转化为tensor[12,1,1]
        '''
        lstm_out, self.hidden = self.lstm(seq.view(len(seq), 1, -1), self.hidden)
        # print(lstm_out) #torch.Size([12, 1, 100]) [time_step,batch,hidden]
        # print(lstm_out.view(len(seq),-1))  #[12,100]
        pred = self.linear(lstm_out.view(len(seq), -1))

        # print(pred) #torch.Size([12, 1])
        return pred[-1]  # 输出只用取最后一个值


if __name__ == "__main__":
    # 时间序列数据
    #value1 = "0.00,0.05,0.28,0.46,0.32,0.06,0.36,0.61,0.70,0.69,0.60,1.32,1.85,2.50,3.10,0.99,-1.22,1.68,4.57"
    #data = np.array([float(value) for value in value1.split(',')])
    data = np.array([float(value) for value in sys.argv[1].split(',')])
    torch.manual_seed(101)
    model = LSTMnetwork()
    model = torch.load("src/main/resources/static/algorithm/model-LSTM.pth")
    data_min = data.min()
    data_max = data.max()
    normalized_data = (data - data_min) / (data_max - data_min) * 2 - 1
    seq = torch.FloatTensor(normalized_data)
    model.eval()
    with torch.no_grad():
        model.hidden = (torch.zeros(1, 1, model.hidden_size), torch.zeros(1, 1, model.hidden_size))
        y_pred = model(seq[-10:])
    print((y_pred.item() + 1) / 2 * (data_max - data_min) + data_min, end='')
