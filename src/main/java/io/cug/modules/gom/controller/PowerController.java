package io.cug.modules.gom.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.*;
import io.cug.modules.gom.param.TableDateInfo;
import io.cug.modules.gom.service.EarthSubsidenceService;
import io.cug.modules.gom.service.PowerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(tags = "监测——power")
@RestController
@RequestMapping("gom/Power")
public class PowerController {

    @Autowired
    private PowerService powerService;

    @Login
    @PostMapping("/List")
    @ApiOperation("列表")
    public R classList(@LoginUser AppUserEntity user, @RequestBody PowerEntity powerEntity){
        List<PowerEntity> list = powerService.QueryValidPower(powerEntity.getObjectId());
        return R.ok().put("result", list);
    }
    @Login
    @PostMapping("/getPeriod")
    @ApiOperation("力周期数据")
    public R PeriodList(@LoginUser AppUserEntity user, @RequestBody PowerEntity powerEntity){
        List<PowerEntity> list = powerService.QueryPowerWithPeriod(powerEntity.getObjectId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/getFirstData")
    @ApiOperation("获取第一条的力数据")
    public R getFirstData(@LoginUser AppUserEntity user, @RequestBody PowerEntity powerEntity){
        PowerEntity first = powerService.getFirstData(powerEntity.getObjectId());
        return R.ok().put("result", first);
    }
    @Login
    @PostMapping("/getAllPeriodByPeriodId")
    @ApiOperation("通过项目id获取所有的周期数据")
    public R getAllPeriodByPeriodId(@LoginUser AppUserEntity user, @RequestBody ObjectEntity objectEntity){
        List<PeriodEntity> list = powerService.QueryAllPeriodsByProjectId(objectEntity.getProjectId(),objectEntity.getType());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/getDataWithObjectName")
    @ApiOperation("通过周期id获得数据和目标name")
    public R getDisplacementWithObjectName(@LoginUser AppUserEntity user, @RequestBody PowerEntity powerEntity){
        List<PowerEntity> list = powerService.QueryPowerWithObjectName(powerEntity.getPeriodId(),powerEntity.getType());
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setInitialValue(powerService.getFirstData(list.get(i).getObjectId()).getValue());
        }
        return R.ok().put("result", list);
    }
    @Login
    @PostMapping("/add")
    @ApiOperation("增加")
    public R add(@LoginUser AppUserEntity user, @RequestBody PowerEntity powerEntity) {
        powerEntity.setStatus(0);
        if (powerService.save(powerEntity)) {
            return R.ok().put("result", powerEntity);
        }
        else {
            return R.error("添加测量数据!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除")
    public R delete(@LoginUser AppUserEntity user, @RequestBody PowerEntity powerEntity){
        powerEntity.setStatus(99);//99-删除状态
        powerService.updateById(powerEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改")
    public R edit(@LoginUser AppUserEntity user, @RequestBody PowerEntity powerEntity){
        powerService.updateById(powerEntity);
        return R.ok().put("result", "更新成功!");
    }

    @Login
    @PostMapping("/editList")
    @ApiOperation("修改测量数据")
    public R editList(@LoginUser AppUserEntity user, @RequestBody List<PowerEntity> list){
        for(int i = 0;i<list.size();i++){
            powerService.updateById(list.get(i));
        }
        return R.ok().put("result", "更新成功!");
    }

    @Login
    @PostMapping("/importData")
    @ApiOperation("批量导入数据")
    public R importData(@LoginUser AppUserEntity user, @RequestBody PowerEntity powerEntity) {
        PowerEntity entity = powerService.selectPowerByPeriodAndObject(powerEntity.getObjectId(),powerEntity.getPeriodId());
        if(entity==null){//不存在该条数据
            powerService.save(powerEntity);
        }else{//存在 则修改
            powerEntity.setId(entity.getId());
            powerService.updateById(powerEntity);
        }
        return R.ok().put("result", powerEntity);
    }

    @Login
    @PostMapping("getWeekData")
    @ApiOperation("获取一周数据")
    public R getWeekData(@LoginUser AppUserEntity user, @RequestBody TableDateInfo tableDateInfo) {
        Long[] periodIdList = tableDateInfo.getPeriodIdList();
        Map<Long, List<PowerEntity>> dataMap = new HashMap<>();
        for (Long periodId : periodIdList) {
            List<PowerEntity> list = powerService.QueryPowerWithObjectName(periodId,tableDateInfo.getSurveyType());
            for (PowerEntity data : list) {
                Long objectId = data.getObjectId();
                if (!dataMap.containsKey(objectId)) {
                    dataMap.put(objectId, new ArrayList<>());
                }
                data.setInitialValue(powerService.getFirstData(data.getObjectId()).getValue());
                dataMap.get(objectId).add(data);
            }
        }

        Map<Long, List<PowerEntity>> periodData = new HashMap<>();
        for (Long periodId : periodIdList) {
            for (Map.Entry<Long, List<PowerEntity>> entry : dataMap.entrySet()) {
                Long objectId = entry.getKey();
                List<PowerEntity> dataList = entry.getValue();
                PowerEntity matchedData = null;
                for (PowerEntity data : dataList) {
                    if (data.getPeriodId() == periodId) {
                        matchedData = data;
                        break;
                    }
                }
                periodData.computeIfAbsent(objectId, k -> new ArrayList<>()).add(matchedData);
            }
        }
        List<List<Object>> resultList = new ArrayList<>();

        for (Map.Entry<Long, List<PowerEntity>> entry : periodData.entrySet()) {
            List<PowerEntity> dataList = entry.getValue();

            List<Object> objectData = new ArrayList<>();
            objectData.addAll(dataList);

            resultList.add(objectData);
        }
        return R.ok().put("result", resultList);
    }
}
