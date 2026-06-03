package io.cug.modules.gom.controller;

import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.*;
import io.cug.modules.gom.param.TableDateInfo;
import io.cug.modules.gom.service.DisplacementService;
import io.cug.modules.gom.service.EarthSubsidenceService;
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


@Api(tags = "监测——earth subsidence")
@RestController
@RequestMapping("gom/EarthSubsidence")
public class EarthSubsidenceController {

    @Autowired
    private EarthSubsidenceService earthSubsidenceService;

    @Login
    @PostMapping("/List")
    @ApiOperation("列表")
    public R classList(@LoginUser AppUserEntity user, @RequestBody EarthSubsidenceEntity earthSubsidenceEntity){
        List<EarthSubsidenceEntity> list = earthSubsidenceService.QueryValidEarthSubsidence(earthSubsidenceEntity.getObjectId());
        return R.ok().put("result", list);
    }
    @Login
    @PostMapping("/getPeriod")
    @ApiOperation("沉降周期数据")
    public R PeriodList(@LoginUser AppUserEntity user, @RequestBody EarthSubsidenceEntity earthSubsidenceEntity){
        List<EarthSubsidenceEntity> list = earthSubsidenceService.EarthSubsidenceWithPeriod(earthSubsidenceEntity.getObjectId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/getFirstData")
    @ApiOperation("获取第一条沉降周期数据")
    public R getFirstData(@LoginUser AppUserEntity user, @RequestBody EarthSubsidenceEntity earthSubsidenceEntity){
        EarthSubsidenceEntity first = earthSubsidenceService.getFirstData(earthSubsidenceEntity.getObjectId());
        return R.ok().put("result", first);
    }
    @Login
    @PostMapping("/getAllPeriodByPeriodId")
    @ApiOperation("通过项目id获取所有的周期数据")
    public R getAllPeriodByPeriodId(@LoginUser AppUserEntity user, @RequestBody ObjectEntity objectEntity){
        List<PeriodEntity> list = earthSubsidenceService.QueryAllPeriodsByProjectId(objectEntity.getProjectId(),objectEntity.getType());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/getDataWithObjectName")
    @ApiOperation("通过周期id获得数据和目标name")
    public R getDisplacementWithObjectName(@LoginUser AppUserEntity user, @RequestBody EarthSubsidenceEntity earthSubsidenceEntity){
        List<EarthSubsidenceEntity> list = earthSubsidenceService.QueryEarthSubsidenceWithObjectName(earthSubsidenceEntity.getPeriodId(),earthSubsidenceEntity.getType());
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setInitialValue(earthSubsidenceService.getFirstData(list.get(i).getObjectId()).getValue());
        }
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加")
    public R add(@LoginUser AppUserEntity user, @RequestBody EarthSubsidenceEntity earthSubsidenceEntity) {
        earthSubsidenceEntity.setStatus(0);
        earthSubsidenceEntity.setRemark("");
        if (earthSubsidenceService.save(earthSubsidenceEntity)) {
            return R.ok().put("result", earthSubsidenceEntity);
        }
        else {
            return R.error("添加测量数据!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除")
    public R delete(@LoginUser AppUserEntity user, @RequestBody EarthSubsidenceEntity earthSubsidenceEntity){
        earthSubsidenceEntity.setStatus(99);//99-删除状态
        earthSubsidenceService.updateById(earthSubsidenceEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改")
    public R edit(@LoginUser AppUserEntity user, @RequestBody EarthSubsidenceEntity earthSubsidenceEntity){
        earthSubsidenceService.updateById(earthSubsidenceEntity);
        return R.ok().put("result", "更新成功!");
    }

    @Login
    @PostMapping("/editList")
    @ApiOperation("修改测量数据")
    public R editList(@LoginUser AppUserEntity user, @RequestBody List<EarthSubsidenceEntity> list){
        for(int i = 0;i<list.size();i++){
            earthSubsidenceService.updateById(list.get(i));
        }
        return R.ok().put("result", "更新成功!");
    }

    @Login
    @PostMapping("/importData")
    @ApiOperation("批量导入数据")
    public R importData(@LoginUser AppUserEntity user, @RequestBody EarthSubsidenceEntity earthSubsidenceEntity) {
        EarthSubsidenceEntity entity = earthSubsidenceService.selectByPeriodAndObject(earthSubsidenceEntity.getObjectId(),earthSubsidenceEntity.getPeriodId());
        if(entity==null){//不存在该条数据
            earthSubsidenceService.save(earthSubsidenceEntity);
        }else{//存在 则修改
            earthSubsidenceEntity.setId(entity.getId());
            earthSubsidenceService.updateById(earthSubsidenceEntity);
        }
        return R.ok().put("result", earthSubsidenceEntity);
    }

    @Login
    @PostMapping("getWeekData")
    @ApiOperation("获取一周数据")
    public R getWeekData(@LoginUser AppUserEntity user, @RequestBody TableDateInfo tableDateInfo) {
        Long[] periodIdList = tableDateInfo.getPeriodIdList();
        Map<Long, List<EarthSubsidenceEntity>> dataMap = new HashMap<>();
        for (Long periodId : periodIdList) {
            List<EarthSubsidenceEntity> list = earthSubsidenceService.QueryEarthSubsidenceWithObjectName(periodId,tableDateInfo.getSurveyType());
            for (EarthSubsidenceEntity data : list) {
                Long objectId = data.getObjectId();
                if (!dataMap.containsKey(objectId)) {
                    dataMap.put(objectId, new ArrayList<>());
                }
                data.setInitialValue(earthSubsidenceService.getFirstData(data.getObjectId()).getValue());
                dataMap.get(objectId).add(data);
            }
        }

        Map<Long, List<EarthSubsidenceEntity>> periodData = new HashMap<>();
        for (Long periodId : periodIdList) {
            for (Map.Entry<Long, List<EarthSubsidenceEntity>> entry : dataMap.entrySet()) {
                Long objectId = entry.getKey();
                List<EarthSubsidenceEntity> dataList = entry.getValue();
                EarthSubsidenceEntity matchedData = null;
                for (EarthSubsidenceEntity data : dataList) {
                    if (data.getPeriodId() == periodId) {
                        matchedData = data;
                        break;
                    }
                }
                periodData.computeIfAbsent(objectId, k -> new ArrayList<>()).add(matchedData);
            }
        }
        List<List<Object>> resultList = new ArrayList<>();

        for (Map.Entry<Long, List<EarthSubsidenceEntity>> entry : periodData.entrySet()) {
            List<EarthSubsidenceEntity> dataList = entry.getValue();

            List<Object> objectData = new ArrayList<>();
            objectData.addAll(dataList);

            resultList.add(objectData);
        }
        return R.ok().put("result", resultList);
    }
}
