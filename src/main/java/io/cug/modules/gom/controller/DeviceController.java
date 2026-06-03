package io.cug.modules.gom.controller;

import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.DeviceEntity;
import io.cug.modules.gom.service.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "监测——项目设备信息")
@RestController
@RequestMapping("gom/Device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Login
    @PostMapping("/List")
    @ApiOperation("设备信息列表")
    public R classList(@LoginUser AppUserEntity user, @RequestBody DeviceEntity deviceEntity){
        List<DeviceEntity> list = deviceService.listValidDevices(deviceEntity.getProjectId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/ListDevice")
    @ApiOperation("设备信息列表")
    public R ListPowerDevice(@LoginUser AppUserEntity user, @RequestBody DeviceEntity deviceEntity){
        List<DeviceEntity> list = deviceService.selectValidPowerDevices(deviceEntity.getProjectId(),deviceEntity.getType());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/ListSensor")
    @ApiOperation("传感器信息列表")
    public R ListSensor(@LoginUser AppUserEntity user, @RequestBody DeviceEntity deviceEntity){
        List<DeviceEntity> list = deviceService.selectValidSensor(deviceEntity.getProjectId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/ListByName")
    @ApiOperation("根据设备name查找")
    public R ListByName(@LoginUser AppUserEntity user, @RequestBody DeviceEntity deviceEntity){
        List<DeviceEntity> list = deviceService.listValidDevicesByName(deviceEntity.getProjectId(),deviceEntity.getName());
        if(!list.isEmpty()){
            return R.ok().put("result", list);
        }else{
            deviceEntity.setStatus(0);
            if (deviceService.save(deviceEntity)) {
                return R.ok().put("result", deviceEntity);
            }
            else {
                return R.error("添加设备信息失败!");
            }
        }
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加设备信息")
    public R add(@LoginUser AppUserEntity user, @RequestBody DeviceEntity deviceEntity) {

        if (deviceService.save(deviceEntity)) {
            return R.ok().put("result", deviceEntity);
        }
        else {
            return R.error("添加设备信息失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除设备信息")
    public R delete(@LoginUser AppUserEntity user, @RequestBody DeviceEntity deviceEntity){
        deviceEntity.setStatus(99);
        deviceService.updateById(deviceEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改设备信息")
    public R edit(@LoginUser AppUserEntity user, @RequestBody DeviceEntity deviceEntity){
        deviceService.updateById(deviceEntity);
        return R.ok().put("result", "更新成功!");
    }

    @Login
    @PostMapping("/searchByKeyWord")
    @ApiOperation("根据查找关键词查找设备")
    public R searchByKeyWord(@LoginUser AppUserEntity user, @RequestBody DeviceEntity deviceEntity){
        if(deviceEntity.getName()==null){
            deviceEntity.setName("");
        }
        deviceService.updateById(deviceEntity);
        List<DeviceEntity> list;
        list = deviceService.selectValidDevicesByKeyword(deviceEntity.getProjectId(),deviceEntity.getName());
        return R.ok().put("result", list);
    }
}
