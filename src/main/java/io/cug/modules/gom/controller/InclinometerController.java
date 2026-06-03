package io.cug.modules.gom.controller;

import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.*;
import io.cug.modules.gom.service.DisplacementService;
import io.cug.modules.gom.service.InclinometerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "监测——Inclinometer")
@RestController
@RequestMapping("gom/Inclinometer")
public class InclinometerController {

    @Autowired
    private InclinometerService inclinometerService;

    @Login
    @PostMapping("/List")
    @ApiOperation("列表")
    public R classList(@LoginUser AppUserEntity user, @RequestBody InclinometerEntity inclinometerEntity){
        List<InclinometerEntity> list = inclinometerService.QueryValidInclinometer(inclinometerEntity.getObjectId());
        return R.ok().put("result", list);
    }
    @Login
    @PostMapping("/getPeriod")
    @ApiOperation("测斜周期数据")
    public R PeriodList(@LoginUser AppUserEntity user, @RequestBody InclinometerEntity inclinometerEntity){
        List<InclinometerEntity> list = inclinometerService.QueryInclinometerWithPeriod(inclinometerEntity.getObjectId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/getFirstData")
    @ApiOperation("获取第一条测斜周期数据")
    public R getFirstData(@LoginUser AppUserEntity user, @RequestBody InclinometerEntity inclinometerEntity){
        InclinometerEntity first = inclinometerService.getFirstData(inclinometerEntity.getObjectId());
        return R.ok().put("result", first);
    }
    @Login
    @PostMapping("/getAllPeriodByPeriodId")
    @ApiOperation("通过项目id获取所有的周期数据")
    public R getAllPeriodByPeriodId(@LoginUser AppUserEntity user, @RequestBody PeriodEntity periodEntity){
        List<PeriodEntity> list = inclinometerService.QueryAllPeriodsByProjectId(periodEntity.getProjectId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/getDataWithObjectName")
    @ApiOperation("通过周期id获得数据和目标name")
    public R getDisplacementWithObjectName(@LoginUser AppUserEntity user, @RequestBody InclinometerEntity inclinometerEntity){
        List<InclinometerEntity> list = inclinometerService.QueryInclinometerWithObjectName(inclinometerEntity.getPeriodId());
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setInitialValue(inclinometerService.getFirstData(list.get(i).getObjectId()).getValue());
        }
        return R.ok().put("result", list);
    }
    @Login
    @PostMapping("/add")
    @ApiOperation("增加测量数据")
    public R add(@LoginUser AppUserEntity user, @RequestBody InclinometerEntity inclinometerEntity) {
        inclinometerEntity.setStatus(0);
        inclinometerEntity.setRemark("");
        if (inclinometerService.save(inclinometerEntity)) {
            return R.ok().put("result", inclinometerEntity);
        }
        else {
            return R.error("添加测量数据!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除测量数据")
    public R delete(@LoginUser AppUserEntity user, @RequestBody InclinometerEntity inclinometerEntity){
        inclinometerEntity.setStatus(99);//99-删除状态
        inclinometerService.updateById(inclinometerEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改测量数据")
    public R edit(@LoginUser AppUserEntity user, @RequestBody InclinometerEntity inclinometerEntity){
        inclinometerService.updateById(inclinometerEntity);
        return R.ok().put("result", "更新成功!");
    }
    @Login
    @PostMapping("/editList")
    @ApiOperation("修改测量数据")
    public R editList(@LoginUser AppUserEntity user, @RequestBody List<InclinometerEntity> list){
        for(int i = 0;i<list.size();i++){
            inclinometerService.updateById(list.get(i));
        }
        return R.ok().put("result", "更新成功!");
    }

    @Login
    @PostMapping("/importData")
    @ApiOperation("批量导入数据")
    public R importData(@LoginUser AppUserEntity user, @RequestBody InclinometerEntity inclinometerEntity) {
        InclinometerEntity entity = inclinometerService.selectByPeriodAndObject(inclinometerEntity.getObjectId(),inclinometerEntity.getPeriodId());
        if(entity==null){//不存在该条数据
            inclinometerService.save(inclinometerEntity);
        }else{//存在 则修改
            inclinometerEntity.setId(entity.getId());
            inclinometerService.updateById(inclinometerEntity);
        }
        return R.ok().put("result", inclinometerEntity);
    }

}
