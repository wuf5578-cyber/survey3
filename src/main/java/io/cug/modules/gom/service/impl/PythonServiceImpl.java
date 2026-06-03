
package io.cug.modules.gom.service.impl;

import java.io.*;
import com.google.common.collect.Lists;
import io.cug.modules.gom.service.PythonService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service("PythonService")
public class PythonServiceImpl implements PythonService {
    private String getPythonResult(String pythonScriptPath,String param){
        try {
            //windows localhost
            ProcessBuilder processBuilder = new ProcessBuilder("C:\\Users\\95396\\Anaconda3\\python.exe", pythonScriptPath, param);
            //centOS
            //ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath, param);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            StringBuilder output = new StringBuilder(); // 用于存储输出结果的字符串变量
            // 创建了BufferedReader，可以使用BufferedReader的readLine()或read()方法从进程的输入流中读取字符。
            while ((line = reader.readLine()) != null) {
                output.append(line); // 将每行输出添加到output中
                System.out.println("Python script output: " + line);
            }
            int exitCode = process.waitFor();// 等待脚本执行结束后获取返回的状态码
            System.out.println("Python script executed with exit code " + exitCode);
            return output.toString();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public String linearRegression(String array) throws IOException{//线性回归
        //String pythonScriptPath = "linearRegression.py";
        String pythonScriptPath = "src/main/resources/static/algorithm/linearRegression.py";
        return getPythonResult(pythonScriptPath,array);
    }
    @Override
    public String curveFitting(String array) throws IOException{//曲线拟合
        //String pythonScriptPath = "curveFitting.py";
        String pythonScriptPath = "src/main/resources/static/algorithm/curveFitting.py";
        return getPythonResult(pythonScriptPath,array);
    }
    @Override
    public String greyPrediction(String array) throws IOException{//灰色预测
        //String pythonScriptPath = "greyPrediction.py";
        String pythonScriptPath = "src/main/resources/static/algorithm/greyPrediction.py";
        return getPythonResult(pythonScriptPath,array);
    }
    @Override
    public String ARIMA(String array) throws IOException{//ARIMA
        //String pythonScriptPath = "ARIMA.py";
        String pythonScriptPath = "src/main/resources/static/algorithm/ARIMA.py";
        return getPythonResult(pythonScriptPath,array);
    }
    @Override
    public String randomForest(String array) throws IOException{//随机森林
        //String pythonScriptPath = "randomForest.py";
        String pythonScriptPath = "src/main/resources/static/algorithm/randomForest.py";
        return getPythonResult(pythonScriptPath,array);
    }
    @Override
    public String LSTM(String array) throws IOException{//LSTM
        //String pythonScriptPath = "LSTM.py";
        String pythonScriptPath = "src/main/resources/static/algorithm/LSTM.py";
        return getPythonResult(pythonScriptPath,array);
    }
    @Override
    public String CNN(String array) throws IOException{//CNN
        //String pythonScriptPath = "CNN.py";
        String pythonScriptPath = "src/main/resources/static/algorithm/CNN.py";
        return getPythonResult(pythonScriptPath,array);
    }
    @Override
    public String CNNLSTM(String array) throws IOException{//CNNLSTM
        //String pythonScriptPath = "CNN.py";
        String pythonScriptPath = "src/main/resources/static/algorithm/CNNLSTM.py";
        return getPythonResult(pythonScriptPath,array);
    }

}
