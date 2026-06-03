package io.cug.modules.gom.controller;

import io.cug.common.utils.DateUtil;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.app.param.ProjectIDForm;
import io.cug.modules.gom.entity.DisParamsEntity;
import io.cug.modules.gom.entity.ObjectEntity;
import io.cug.modules.gom.entity.ParamEntity;
import io.cug.modules.gom.entity.PointEntity;
import io.cug.modules.gom.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "监测——目标点监测信息")
@RestController
@RequestMapping("gom/Point")
public class PointController {

    @Autowired
    private PointService pointService;

    @Autowired
    private ParamService paramService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DisParamsService disParamsService;

    @Login
    @PostMapping("/List")
    @ApiOperation("目标点检测信息列表")
    public R classList(@LoginUser AppUserEntity user, @RequestBody PointEntity pointEntity){
        List<PointEntity> list = pointService.listValidPoints(pointEntity.getProjectId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/ListByObjectId")
    @ApiOperation("目标点id检测信息列表")
    public R List(@LoginUser AppUserEntity user, @RequestBody PointEntity pointEntity){
        PointEntity selectPoint = pointService.selectByObjectId(pointEntity.getObjectId());
        return R.ok().put("result", selectPoint);
    }

    @Login
    @PostMapping("/selectObjectBySurveyType")
    @ApiOperation("通过项目id和测量类型检索测量点")
    public R selectObjectBySurveyType(@LoginUser AppUserEntity user, @RequestBody PointEntity pointEntity){
        List<ObjectEntity> list = pointService.selectObjectBySurveyType(pointEntity.getProjectId(),pointEntity.getSurveyType());
        return R.ok().put("result", list);
    }
    @Login
    @PostMapping("/add")
    @ApiOperation("增加目标点检测信息")
    public R add(@LoginUser AppUserEntity user, @RequestBody PointEntity pointEntity) {

        if (pointService.save(pointEntity)) {
            return R.ok().put("result", pointEntity);
        }
        else {
            return R.error("添加监测目标点失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除目标点检测信息")
    public R delete(@LoginUser AppUserEntity user, @RequestBody PointEntity pointEntity){
        pointEntity.setStatus(99);//99-删除状态
        pointService.updateById(pointEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改目标点检测信息")
    public R edit(@LoginUser AppUserEntity user, @RequestBody PointEntity pointEntity){
        pointService.updateById(pointEntity);
        /*
        //判断目标点是否存在该类型的监测信息
        List<PointEntity> list =pointService.IsExist(pointEntity.getObjectId(),pointEntity.getSurveyType());
        if(!list.isEmpty()){
            pointEntity.setStatus(0);
            pointService.updateByObjectId(pointEntity);
            return R.ok().put("result", "0成功!");
        }
        pointService.save(pointEntity);
        */
        return R.ok().put("result", "修改成功!");
    }

    @Login
    @PostMapping("/listWithParams")
    @ApiOperation("获取应力参数")
    public R listWithParams(@LoginUser AppUserEntity user, @RequestBody PointEntity pointEntity){
        List<ObjectEntity> list = pointService.selectObjectBySurveyType(pointEntity.getProjectId(),pointEntity.getSurveyType());
        for(int i = 0;i < list.size(); i++){
            ParamEntity paramEntity = paramService.getByObjectId(list.get(i).getId());
            if (paramEntity!=null){
                if(paramEntity.getDeviceId()!=null){
                    paramEntity.setDeviceEntity(deviceService.getById(paramEntity.getDeviceId()));
                }
            }
            list.get(i).setParamEntity(paramEntity);
        }
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/listWithDisParams")
    @ApiOperation("获取水平位移参数")
    public R listWithDisParams(@LoginUser AppUserEntity user, @RequestBody PointEntity pointEntity){
        List<ObjectEntity> list = pointService.selectObjectBySurveyType(pointEntity.getProjectId(),pointEntity.getSurveyType());
        for(int i = 0;i < list.size(); i++){
            DisParamsEntity disParamsEntity = disParamsService.getByObjectId(list.get(i).getId());
            list.get(i).setDisParamsEntity(disParamsEntity);
        }
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/getObjectNum")
    @ApiOperation("获取测点布设点数量")
    public R getObjectNum(@LoginUser AppUserEntity user, @RequestBody PointEntity pointEntity){
        int[] count = {0,0,0,0,0,0,0,0,0,0,0,0,0};
        for(int i=0;i<13;i++){
            count[i]=pointService.getObjectNum(pointEntity.getProjectId(),i+1);
        }
        return R.ok().put("result", count);
    }
}
