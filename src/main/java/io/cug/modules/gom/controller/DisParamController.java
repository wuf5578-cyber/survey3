package io.cug.modules.gom.controller;

import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.DisParamsEntity;
import io.cug.modules.gom.entity.ParamEntity;
import io.cug.modules.gom.service.DeviceService;
import io.cug.modules.gom.service.DisParamsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "监测——水平位移参数")
@RestController
@RequestMapping("gom/DisParam")
public class DisParamController {

    @Autowired
    private DisParamsService paramService;

    @Login
    @PostMapping("getByObjectId")
    @ApiOperation("通过objectId获取数据")
    public R getByObjectId(@LoginUser AppUserEntity user, @RequestBody DisParamsEntity disParamsEntity){
        DisParamsEntity selectEntity = paramService.getByObjectId(disParamsEntity.getObjectId());
        return R.ok().put("result", selectEntity);
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加")
    public R add(@LoginUser AppUserEntity user, @RequestBody DisParamsEntity disParamsEntity) {

        if (paramService.save(disParamsEntity)) {
            return R.ok().put("result", disParamsEntity);
        }
        else {
            return R.error("添加失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除")
    public R delete(@LoginUser AppUserEntity user, @RequestBody DisParamsEntity disParamsEntity){
        disParamsEntity.setStatus(99);//99-删除状态
        paramService.updateById(disParamsEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改")
    public R edit(@LoginUser AppUserEntity user, @RequestBody DisParamsEntity disParamsEntity){
        if(disParamsEntity.getId()!=null){
            paramService.updateById(disParamsEntity);
            return R.ok().put("result", "更新成功!");
        }else{
            paramService.save(disParamsEntity);
            return R.ok().put("result", disParamsEntity);
        }
    }
}
