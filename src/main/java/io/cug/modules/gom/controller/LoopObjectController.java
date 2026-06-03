package io.cug.modules.gom.controller;

import io.cug.common.utils.DateUtil;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.LoopObjectEntity;
import io.cug.modules.gom.service.LoopObjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


@Api(tags = "隧道测量点位置信息")
@RestController
@RequestMapping("gom/LoopObject")
public class LoopObjectController {

    @Autowired
    private LoopObjectService loopObjectService;

    @Login
    @PostMapping("/list")
    @ApiOperation("查找所有点")
    public R classList(@LoginUser AppUserEntity user, @RequestBody LoopObjectEntity loopObjectEntity){
        List<LoopObjectEntity> list = loopObjectService.SelectAll(loopObjectEntity.getObjectId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/select")
    @ApiOperation("通过点ID查找")
    public R ListByObjectId(@LoginUser AppUserEntity user, @RequestBody LoopObjectEntity loopObjectEntity){
        List<LoopObjectEntity> list = loopObjectService.SelectByObjectId(loopObjectEntity.getObjectId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/selectByPeriod")
    @ApiOperation("通过周期查找点")
    public R ListByPeriod(@LoginUser AppUserEntity user, @RequestBody LoopObjectEntity loopObjectEntity){
        List<LoopObjectEntity> list = loopObjectService.SelectByPeriod(loopObjectEntity.getObjectId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加信息")
    public R add(@LoginUser AppUserEntity user, @RequestBody LoopObjectEntity loopObjectEntity) {
        loopObjectEntity.setSurveyTime(DateUtil.nowDateTime());

        if (loopObjectService.save(loopObjectEntity)) {
            return R.ok().put("result", loopObjectEntity);
        }
        else {
            return R.error("添加失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除设信息")
    public R delete(@LoginUser AppUserEntity user, @RequestBody LoopObjectEntity loopObjectEntity){
        loopObjectEntity.setStatus(99);
        loopObjectService.updateById(loopObjectEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改信息")
    public R edit(@LoginUser AppUserEntity user, @RequestBody LoopObjectEntity loopObjectEntity){
        loopObjectService.updateById(loopObjectEntity);
        return R.ok().put("result", "更新成功!");
    }

    @Login
    @PostMapping("/Displacement")
    @ApiOperation("获取断面位移信息")
    public R displacement(@LoginUser AppUserEntity user, @RequestBody LoopObjectEntity loopObjectEntity){
        Date startTime = loopObjectEntity.getStartTime(); // 起始时间
        Date endTime = loopObjectEntity.getEndTime(); // 结束时间

        if (startTime == null || endTime == null) {
            return R.error("时间范围不能为空");
        }
        List<LoopObjectEntity> list = loopObjectService.SelectDisplacement(
                loopObjectEntity.getLoopId(),
                loopObjectEntity.getObjectType(),
                startTime,
                endTime);
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/selectByObjectType")
    @ApiOperation("通过类型查询点的信息")
    public R ListByObjectType(@LoginUser AppUserEntity user, @RequestBody LoopObjectEntity loopObjectEntity){
        List<LoopObjectEntity> list = loopObjectService.SelectByObjectType(loopObjectEntity.getLoopId(), loopObjectEntity.getObjectType());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/selectByPeriodId")
    @ApiOperation("通过周期查询点的个数")
    public R ListByPeriodId(@LoginUser AppUserEntity user, @RequestBody LoopObjectEntity loopObjectEntity){
        List<LoopObjectEntity> list = loopObjectService.SelectByPeriodId(loopObjectEntity.getLoopId(), loopObjectEntity.getPeriodId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/selectFirstData")
    @ApiOperation("通过周期查询第一条数据")
    public R ListFirstData(@LoginUser AppUserEntity user, @RequestBody LoopObjectEntity loopObjectEntity){
        List<LoopObjectEntity> list = loopObjectService.SelectFirstData(loopObjectEntity.getObjectId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/selectByPeriodIdAndObjectId")
    @ApiOperation("通过周期和点类型查询数据")
    public R ListByPeriodIdAndObjectId(@LoginUser AppUserEntity user, @RequestBody LoopObjectEntity loopObjectEntity){
        List<LoopObjectEntity> list = loopObjectService.SelectByPeriodIdAndObjectId(loopObjectEntity.getObjectType(), loopObjectEntity.getPeriodId());
        return R.ok().put("result", list);
    }

}
