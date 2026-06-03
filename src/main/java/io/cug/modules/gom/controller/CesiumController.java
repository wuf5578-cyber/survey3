package io.cug.modules.gom.controller;

import io.cug.common.utils.DateUtil;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.AlarmEntity;
import io.cug.modules.gom.entity.CesiumEntity;
import io.cug.modules.gom.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@Api(tags = "3dtiles表")
@RestController
@RequestMapping("gom/Cesium")
public class CesiumController {

    @Autowired
    private CesiumService cesiumService;

    @Autowired
    private ObjectService objectService;


    @Login
    @PostMapping("/List")
    @ApiOperation("通过项目id查找3dtiles")
    public R classList(@LoginUser AppUserEntity user, @RequestBody CesiumEntity cesiumEntity){
        List<CesiumEntity> list = cesiumService.QueryValidCesium(cesiumEntity.getProjectId());
        if(list.size()!=0){
            for(int i = 0;i < list.size(); i++){
                list.get(i).setObjectEntity(objectService.QueryObjectById(list.get(i).getObjectId()));
            }
        }
        return R.ok().put("result", list);
    }


    @Login
    @PostMapping("/add")
    @ApiOperation("增加entity信息")
    public R add(@LoginUser AppUserEntity user, @RequestBody CesiumEntity cesiumEntity) {

        if (cesiumService.save(cesiumEntity)) {
            return R.ok().put("result", cesiumEntity);
        }
        else {
            return R.error("添加失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除entity记录")
    public R delete(@LoginUser AppUserEntity user, @RequestBody CesiumEntity cesiumEntity){
        cesiumEntity.setStatus(99);//99-删除状态
        cesiumService.updateById(cesiumEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/deleteByPointId")
    @ApiOperation("根据pointId删除entity记录")
    public R deleteByPointId(@LoginUser AppUserEntity user, @RequestBody CesiumEntity cesiumEntity){
        CesiumEntity deleteCesium = cesiumService.QueryCesiumByPointId(cesiumEntity.getProjectId(),cesiumEntity.getPointId());
        if(deleteCesium==null){
            return R.ok().put("result", "删除失败!");
        }else{
            deleteCesium.setStatus(99);//99-删除状态
            cesiumService.updateById(deleteCesium);
            return R.ok().put("result", "删除成功!");
        }
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改")
    public R edit(@LoginUser AppUserEntity user, @RequestBody CesiumEntity cesiumEntity){
        cesiumService.updateById(cesiumEntity);
        return R.ok().put("result", "更新成功!");
    }

}
