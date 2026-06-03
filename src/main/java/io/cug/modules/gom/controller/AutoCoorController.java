package io.cug.modules.gom.controller;

import io.cug.common.utils.DateUtil;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.AutoCoorEntity;
import io.cug.modules.gom.entity.AutoStationEntity;
import io.cug.modules.gom.service.AutoCoorService;
import io.cug.modules.gom.service.AutoStationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "监测——自动监测点数据")
@RestController
@RequestMapping("gom/AutoCoor")
public class AutoCoorController {

    @Autowired
    private AutoCoorService autoCoorService;

    @Login
    @PostMapping("/listByPeriodAndObject")
    @ApiOperation("根据周期和节点查找数据")
    public R listByPeriodAndObject(@LoginUser AppUserEntity user, @RequestBody AutoCoorEntity autoCoorEntity){
        AutoCoorEntity entity = autoCoorService.QueryValidCoorByPeriodIdAndObjectId(autoCoorEntity.getPeriodId(),autoCoorEntity.getObjectId());
        return R.ok().put("result", entity);
    }

    @Login
    @PostMapping("/listByPeriod")
    @ApiOperation("根据周期查找数据")
    public R listByPeriod(@LoginUser AppUserEntity user, @RequestBody AutoCoorEntity autoCoorEntity){
        List<AutoCoorEntity> list = autoCoorService.QueryValidCoorByPeriodId(autoCoorEntity.getPeriodId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/listByObject")
    @ApiOperation("根据节点查找数据")
    public R listByObject(@LoginUser AppUserEntity user, @RequestBody AutoCoorEntity autoCoorEntity){
        List<AutoCoorEntity> list = autoCoorService.QueryValidCoorByObjectId(autoCoorEntity.getObjectId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/listGivenPoint")
    @ApiOperation("查找已知点")
    public R listGivenPoint(@LoginUser AppUserEntity user, @RequestBody AutoCoorEntity autoCoorEntity){
        List<AutoCoorEntity> list = autoCoorService.QueryValidGivenPoint(autoCoorEntity.getProjectId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加点数据")
    public R add(@LoginUser AppUserEntity user, @RequestBody AutoCoorEntity autoCoorEntity) {
        autoCoorEntity.setCreateTime(DateUtil.nowDateTime());
        if (autoCoorService.save(autoCoorEntity)) {
            return R.ok().put("result", autoCoorEntity);
        }
        else {
            return R.error("添加失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除")
    public R delete(@LoginUser AppUserEntity user, @RequestBody AutoCoorEntity autoCoorEntity){
        autoCoorEntity.setStatus(99);//99-删除状态
        autoCoorService.updateById(autoCoorEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改")
    public R edit(@LoginUser AppUserEntity user, @RequestBody AutoCoorEntity autoCoorEntity){
        autoCoorService.updateById(autoCoorEntity);
        return R.ok().put("result", "更新成功!");
    }
}
