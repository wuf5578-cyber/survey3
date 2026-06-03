package io.cug.modules.gom.service;

import java.io.IOException;

public interface PythonService {
    String linearRegression(String array) throws IOException;
    String curveFitting(String array) throws IOException;
    String greyPrediction(String array) throws IOException;
    String ARIMA(String array) throws IOException;
    String randomForest(String array) throws IOException;
    String LSTM(String array) throws IOException;
    String CNN(String array) throws IOException;
    String CNNLSTM(String array) throws IOException;
}
