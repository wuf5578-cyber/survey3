import numpy as np
import sys
import joblib

if __name__ == "__main__":
    # 时间序列数据
    #value1 = "0.00,0.05,0.28,0.46,0.32,0.06,0.36,0.61,0.70,0.69,0.60,1.32,1.85,2.50,3.10,0.99,-1.22,1.68,4.57"
    #data = np.array([float(value) for value in value1.split(',')])
    data = np.array([float(value) for value in sys.argv[1].split(',')])

    loaded_model = joblib.load('src/main/resources/static/algorithm/random_forest_model.joblib')
    predicted_value = loaded_model.predict([data[-10:]])
    print(predicted_value[0], end='')
