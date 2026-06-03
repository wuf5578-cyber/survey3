package io.cug.modules.gom.controller;

import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.*;
import io.cug.modules.gom.param.TableDateInfo;
import io.cug.modules.gom.service.ExtendService;
import io.cug.modules.gom.service.InclinometerService;
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


@Api(tags = "监测——Extend")
@RestController
@RequestMapping("gom/Extend")
public class ExtendController {

    @Autowired
    private ExtendService extendService;

    @Login
    @PostMapping("/List")
    @ApiOperation("列表")
    public R classList(@LoginUser AppUserEntity user, @RequestBody ExtendEntity extendEntity){
        List<ExtendEntity> list = extendService.QueryValidExtend(extendEntity.getObjectId());
        return R.ok().put("result", list);
    }
    @Login
    @PostMapping("/getPeriod")
    @ApiOperation("扩展周期数据")
    public R PeriodList(@LoginUser AppUserEntity user, @RequestBody ExtendEntity extendEntity){
        List<ExtendEntity> list = extendService.QueryExtendWithPeriod(extendEntity.getObjectId());
        return R.ok().put("result", list);
    }
    @Login
    @PostMapping("/getAllPeriodByPeriodId")
    @ApiOperation("通过项目id获取所有的周期数据")
    public R getAllPeriodByPeriodId(@LoginUser AppUserEntity user, @RequestBody PeriodEntity periodEntity){
        List<PeriodEntity> list = extendService.QueryAllPeriodsByProjectId(periodEntity.getProjectId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/getDataWithObjectName")
    @ApiOperation("通过周期id获得数据和目标name")
    public R getDisplacementWithObjectName(@LoginUser AppUserEntity user, @RequestBody ExtendEntity extendEntity){
        List<ExtendEntity> list = extendService.QueryExtendWithObjectName(extendEntity.getPeriodId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加测量数据")
    public R add(@LoginUser AppUserEntity user, @RequestBody ExtendEntity extendEntity) {
        extendEntity.setStatus(0);
        extendEntity.setRemark("");
        if (extendService.save(extendEntity)) {
            return R.ok().put("result", extendEntity);
        }
        else {
            return R.error("添加测量数据!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除测量数据")
    public R delete(@LoginUser AppUserEntity user, @RequestBody ExtendEntity extendEntity){
        extendEntity.setStatus(99);//99-删除状态
        extendService.updateById(extendEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改测量数据")
    public R edit(@LoginUser AppUserEntity user, @RequestBody ExtendEntity extendEntity){
        extendService.updateById(extendEntity);
        return R.ok().put("result", "更新成功!");
    }

    @Login
    @PostMapping("getWeekData")
    @ApiOperation("获取一周数据")
    public R getWeekData(@LoginUser AppUserEntity user, @RequestBody TableDateInfo tableDateInfo) {
        Long[] periodIdList = tableDateInfo.getPeriodIdList();
        Map<Long, List<ExtendEntity>> dataMap = new HashMap<>();
        for (Long periodId : periodIdList) {
            List<ExtendEntity> list = extendService.QueryExtendWithObjectName(periodId);
            for (ExtendEntity data : list) {
                Long objectId = data.getObjectId();
                if (!dataMap.containsKey(objectId)) {
                    dataMap.put(objectId, new ArrayList<>());
                }
                dataMap.get(objectId).add(data);
            }
        }

        Map<Long, List<ExtendEntity>> periodData = new HashMap<>();
        for (Long periodId : periodIdList) {
            for (Map.Entry<Long, List<ExtendEntity>> entry : dataMap.entrySet()) {
                Long objectId = entry.getKey();
                List<ExtendEntity> dataList = entry.getValue();
                ExtendEntity matchedData = null;
                for (ExtendEntity data : dataList) {
                    if (data.getPeriodId() == periodId) {
                        matchedData = data;
                        break;
                    }
                }
                periodData.computeIfAbsent(objectId, k -> new ArrayList<>()).add(matchedData);
            }
        }
        List<List<Object>> resultList = new ArrayList<>();

        for (Map.Entry<Long, List<ExtendEntity>> entry : periodData.entrySet()) {
            List<ExtendEntity> dataList = entry.getValue();

            List<Object> objectData = new ArrayList<>();
            objectData.addAll(dataList);

            resultList.add(objectData);
        }
        return R.ok().put("result", resultList);
    }
}
