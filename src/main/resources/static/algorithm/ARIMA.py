import numpy as np
import pandas as pd
import sys
from statsmodels.tsa.arima.model import ARIMA


if __name__ == "__main__":
    #value1 = "0.00,0.05,0.28,0.46,0.32,0.06,0.36,0.61,0.70,0.69,0.60,1.32,1.85,2.50,3.10,0.99,1.22,1.68,4.57"
    #data = np.array([float(x) for x in value1.split(',')])
    data = np.array([float(value) for value in sys.argv[1].split(',')])

    model = ARIMA(data, order=(1, 1, 1))
    model_fit = model.fit()

    forecast = model_fit.forecast(steps=1)
    print(forecast[0], end='')
