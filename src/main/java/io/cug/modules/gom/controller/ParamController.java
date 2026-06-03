package io.cug.modules.gom.controller;

import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.AutoLearnEntity;
import io.cug.modules.gom.entity.ObjectEntity;
import io.cug.modules.gom.entity.ParamEntity;
import io.cug.modules.gom.service.AutoLearnService;
import io.cug.modules.gom.service.DeviceService;
import io.cug.modules.gom.service.ParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "监测——应力参数")
@RestController
@RequestMapping("gom/Param")
public class ParamController {

    @Autowired
    private ParamService paramService;

    @Autowired
    private DeviceService deviceService;

    @Login
    @PostMapping("/List")
    @ApiOperation("列表")
    public R classList(@LoginUser AppUserEntity user, @RequestBody ParamEntity paramEntity){
        List<ParamEntity> list = paramService.selectValidParamsBySurveyType(paramEntity.getSurveyType());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("getByObjectId")
    @ApiOperation("通过objectId获取数据")
    public R getByObjectId(@LoginUser AppUserEntity user, @RequestBody ParamEntity paramEntity){
        ParamEntity selectEntity = paramService.getByObjectId(paramEntity.getObjectId());
        selectEntity.setDeviceEntity(deviceService.getById(selectEntity.getDeviceId()));
        return R.ok().put("result", selectEntity);
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加")
    public R add(@LoginUser AppUserEntity user, @RequestBody ParamEntity paramEntity) {

        if (paramService.save(paramEntity)) {
            return R.ok().put("result", paramEntity);
        }
        else {
            return R.error("添加失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除")
    public R delete(@LoginUser AppUserEntity user, @RequestBody ParamEntity paramEntity){
        paramEntity.setStatus(99);//99-删除状态
        paramService.updateById(paramEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改")
    public R edit(@LoginUser AppUserEntity user, @RequestBody ParamEntity paramEntity){
        if(paramEntity.getId()!=null){
            paramService.updateById(paramEntity);
            return R.ok().put("result", "更新成功!");
        }else{
            paramService.save(paramEntity);
            return R.ok().put("result", paramEntity);
        }
    }
}
