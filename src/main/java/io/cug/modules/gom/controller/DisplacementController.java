package io.cug.modules.gom.controller;

import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.DisParamsEntity;
import io.cug.modules.gom.entity.DisplacementEntity;
import io.cug.modules.gom.entity.PeriodEntity;
import io.cug.modules.gom.entity.PowerEntity;
import io.cug.modules.gom.param.TableDateInfo;
import io.cug.modules.gom.service.DisParamsService;
import io.cug.modules.gom.service.DisplacementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(tags = "监测——测量数据")
@RestController
@RequestMapping("gom/Displacement")
public class DisplacementController {

    @Autowired
    private DisplacementService displacementService;
    @Autowired
    private DisParamsService disParamsService;

    @Login
    @PostMapping("/List")
    @ApiOperation("监测测量数据")
    public R List(@LoginUser AppUserEntity user, @RequestBody DisplacementEntity displacementEntity){
        List<DisplacementEntity> list = displacementService.QueryValidDisplacement(displacementEntity.getObjectId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/getPeriod")
    @ApiOperation("沉降周期数据")
    public R PeriodList(@LoginUser AppUserEntity user, @RequestBody DisplacementEntity displacementEntity){
        List<DisplacementEntity> list = displacementService.QueryDisplacementWithPeriod(displacementEntity.getObjectId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/getFirstData")
    @ApiOperation("获取第一条沉降周期数据")
    public R getFirstData(@LoginUser AppUserEntity user, @RequestBody DisplacementEntity displacementEntity){
        DisplacementEntity first = displacementService.getFirstData(displacementEntity.getObjectId());
        return R.ok().put("result", first);
    }

    @Login
    @PostMapping("/getAllPeriodByPeriodId")
    @ApiOperation("通过项目id获取所有的周期数据")
    public R getAllPeriodByPeriodId(@LoginUser AppUserEntity user, @RequestBody PeriodEntity periodEntity){
        List<PeriodEntity> list = displacementService.QueryAllPeriodsByProjectId(periodEntity.getProjectId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/getDataWithObjectName")
    @ApiOperation("通过周期id获得数据和目标name")
    public R getDisplacementWithObjectName(@LoginUser AppUserEntity user, @RequestBody DisplacementEntity displacementEntity){
        List<DisplacementEntity> list = displacementService.QueryDisplacementWithObjectName(displacementEntity.getPeriodId());
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setInitialX(displacementService.getFirstData(list.get(i).getObjectId()).getX());
            list.get(i).setInitialY(displacementService.getFirstData(list.get(i).getObjectId()).getY());
        }
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加测量数据")
    public R add(@LoginUser AppUserEntity user, @RequestBody DisplacementEntity displacementEntity) {
        displacementEntity.setStatus(0);
        if (displacementService.save(displacementEntity)) {
            return R.ok().put("result", displacementEntity);
        }
        else {
            return R.error("添加测量数据!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除测量数据")
    public R delete(@LoginUser AppUserEntity user, @RequestBody DisplacementEntity displacementEntity){
        displacementEntity.setStatus(99);//99-删除状态
        displacementService.updateById(displacementEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改测量数据")
    public R edit(@LoginUser AppUserEntity user, @RequestBody DisplacementEntity displacementEntity){
        displacementService.updateById(displacementEntity);
        return R.ok().put("result", "更新成功!");
    }

    @Login
    @PostMapping("/editList")
    @ApiOperation("修改测量数据")
    public R editList(@LoginUser AppUserEntity user, @RequestBody List<DisplacementEntity> list){
        //修改测量数据
        for(int i = 0;i<list.size();i++){
            displacementService.updateById(list.get(i));
        }
        return R.ok().put("result", "更新成功!");
    }

    @Login
    @PostMapping("/importData")
    @ApiOperation("批量导入数据")
    public R importData(@LoginUser AppUserEntity user, @RequestBody DisplacementEntity displacementEntity) {
        DisplacementEntity entity = displacementService.selectByPeriodAndObject(displacementEntity.getObjectId(),displacementEntity.getPeriodId());
        if(entity==null){//不存在该条数据
            displacementService.save(displacementEntity);
        }else{//存在 则修改
            displacementEntity.setId(entity.getId());
            displacementService.updateById(displacementEntity);
        }
        return R.ok().put("result", displacementEntity);
    }

    @Login
    @PostMapping("getWeekData")
    @ApiOperation("获取一周数据")
    public R getWeekData(@LoginUser AppUserEntity user, @RequestBody TableDateInfo tableDateInfo) {
        Long[] periodIdList = tableDateInfo.getPeriodIdList();
        Map<Long, List<DisplacementEntity>> dataMap = new HashMap<>();
        for (Long periodId : periodIdList) {
            List<DisplacementEntity> list = displacementService.QueryDisplacementWithObjectName(periodId);
            for (DisplacementEntity data : list) {
                Long objectId = data.getObjectId();
                if (!dataMap.containsKey(objectId)) {
                    dataMap.put(objectId, new ArrayList<>());
                }
                DisParamsEntity disParamsEntity = disParamsService.getByObjectId(data.getObjectId());
                data.setRemark(disParamsEntity.getXRatio()+" "+disParamsEntity.getYRatio());
                data.setInitialX(displacementService.getFirstData(data.getObjectId()).getX());
                data.setInitialY(displacementService.getFirstData(data.getObjectId()).getY());
                dataMap.get(objectId).add(data);
            }
        }

        Map<Long, List<DisplacementEntity>> periodData = new HashMap<>();
        for (Long periodId : periodIdList) {
            for (Map.Entry<Long, List<DisplacementEntity>> entry : dataMap.entrySet()) {
                Long objectId = entry.getKey();
                List<DisplacementEntity> dataList = entry.getValue();
                DisplacementEntity matchedData = null;
                for (DisplacementEntity data : dataList) {
                    if (data.getPeriodId() == periodId) {
                        matchedData = data;
                        break;
                    }
                }
                periodData.computeIfAbsent(objectId, k -> new ArrayList<>()).add(matchedData);
            }
        }
        List<List<Object>> resultList = new ArrayList<>();

        for (Map.Entry<Long, List<DisplacementEntity>> entry : periodData.entrySet()) {
            List<DisplacementEntity> dataList = entry.getValue();

            List<Object> objectData = new ArrayList<>();
            objectData.addAll(dataList);

            resultList.add(objectData);
        }
        return R.ok().put("result", resultList);
    }
}
