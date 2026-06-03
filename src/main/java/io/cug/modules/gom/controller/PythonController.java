package io.cug.modules.gom.controller;

import io.cug.common.utils.R;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.gom.service.PythonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@Api(tags = "预测算法——Python")
@RestController
@RequestMapping("gom/python")
public class PythonController {
    @Autowired
    private PythonService pythonService;
    @Login
    @RequestMapping("/linearRegression")
    @ApiOperation("线性回归")
    public R linearRegression(@RequestBody String values) throws IOException {
        return R.ok().put("result", pythonService.linearRegression(values));
    }
    @Login
    @RequestMapping("/curveFitting")
    @ApiOperation("曲线拟合")
    public R curveFitting(@RequestBody String values) throws IOException {
        return R.ok().put("result", pythonService.curveFitting(values));
    }
    @Login
    @RequestMapping("/greyPrediction")
    @ApiOperation("灰度预测")
    public R greyPrediction(@RequestBody String values) throws IOException {
        return R.ok().put("result", pythonService.greyPrediction(values));
    }
    @Login
    @RequestMapping("/ARIMA")
    @ApiOperation("ARIMA")
    public R ARIMA(@RequestBody String values) throws IOException {
        return R.ok().put("result", pythonService.ARIMA(values));
    }
    @Login
    @RequestMapping("/randomForest")
    @ApiOperation("随机森林")
    public R randomForest(@RequestBody String values) throws IOException {
        return R.ok().put("result", pythonService.randomForest(values));
    }
    @Login
    @RequestMapping("/LSTM")
    @ApiOperation("LSTM")
    public R LSTM(@RequestBody String values) throws IOException {
        return R.ok().put("result", pythonService.LSTM(values));
    }
    @Login
    @RequestMapping("/CNN")
    @ApiOperation("CNN")
    public R CNN(@RequestBody String values) throws IOException {
        return R.ok().put("result", pythonService.CNN(values));
    }
    @Login
    @RequestMapping("/CNNLSTM")
    @ApiOperation("CNNLSTM")
    public R CNNLSTM(@RequestBody String values) throws IOException {
        return R.ok().put("result", pythonService.CNNLSTM(values));
    }

}

