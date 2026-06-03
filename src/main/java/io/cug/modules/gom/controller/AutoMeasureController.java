package io.cug.modules.gom.controller;

import io.cug.common.utils.DateUtil;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.AutoLearnEntity;
import io.cug.modules.gom.entity.AutoMeasureEntity;
import io.cug.modules.gom.entity.ObjectEntity;
import io.cug.modules.gom.service.AutoLearnService;
import io.cug.modules.gom.service.AutoMeasureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "自动测量数据")
@RestController
@RequestMapping("gom/AutoMeasure")
public class AutoMeasureController {

    @Autowired
    private AutoMeasureService autoMeasureService;

    @Login
    @PostMapping("/ListExt")
    @ApiOperation("自动测量列表")
    public R classList(@LoginUser AppUserEntity user, @RequestBody AutoMeasureEntity autoMeasureEntity){
        List<AutoMeasureEntity> list = autoMeasureService.QueryValidMeasure(autoMeasureEntity.getStationId(),autoMeasureEntity.getPeriodId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加自动测量记录")
    public R add(@LoginUser AppUserEntity user, @RequestBody AutoMeasureEntity autoMeasureEntity) {

        autoMeasureEntity.setSurveyTime(DateUtil.nowDateTime());
        if (autoMeasureService.save(autoMeasureEntity)) {
            return R.ok().put("result", autoMeasureEntity);
        }
        else {
            return R.error("添加失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除自动测量记录")
    public R delete(@LoginUser AppUserEntity user, @RequestBody AutoMeasureEntity autoMeasureEntity){
        autoMeasureEntity.setStatus(99);//99-删除状态
        autoMeasureService.updateById(autoMeasureEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改")
    public R edit(@LoginUser AppUserEntity user, @RequestBody AutoMeasureEntity autoMeasureEntity){
        autoMeasureService.updateById(autoMeasureEntity);
        return R.ok().put("result", "更新成功!");
    }

}
