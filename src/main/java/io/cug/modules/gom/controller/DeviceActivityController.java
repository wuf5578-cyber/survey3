package io.cug.modules.gom.controller;

import io.cug.common.utils.DateUtil;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.*;
import io.cug.modules.gom.param.RiskAssessDataInfo;
import io.cug.modules.gom.param.RiskAssessInfo;
import io.cug.modules.gom.param.TimelineData;
import io.cug.modules.gom.param.WarningData;
import io.cug.modules.gom.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(tags = "报警表")
@RestController
@RequestMapping("gom/DeviceActivity")
public class DeviceActivityController {

    @Autowired
    private DeviceActivityService deviceActivityService;

    @Login
    @PostMapping("/List")
    @ApiOperation("通过设备id查找")
    public R classList(@LoginUser AppUserEntity user, @RequestBody DeviceActivityEntity deviceActivityEntity){
        List<DeviceActivityEntity> list = deviceActivityService.selectByDeviceId(deviceActivityEntity.getDeviceId());
        return R.ok().put("result", list);
    }


    @Login
    @PostMapping("/add")
    @ApiOperation("增加信息")
    public R add(@LoginUser AppUserEntity user, @RequestBody DeviceActivityEntity deviceActivityEntity) {

        if (deviceActivityService.save(deviceActivityEntity)) {
            return R.ok().put("result", deviceActivityEntity);
        }
        else {
            return R.error("添加失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除记录")
    public R delete(@LoginUser AppUserEntity user, @RequestBody DeviceActivityEntity deviceActivityEntity){
        deviceActivityEntity.setStatus(99);//99-删除状态
        deviceActivityService.updateById(deviceActivityEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改")
    public R edit(@LoginUser AppUserEntity user, @RequestBody DeviceActivityEntity deviceActivityEntity){
        deviceActivityService.updateById(deviceActivityEntity);
        return R.ok().put("result", "更新成功!");
    }

}
