package io.cug.modules.gom.controller;

import io.cug.common.utils.DateUtil;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.AutoLearnEntity;
import io.cug.modules.gom.entity.AutoStationEntity;
import io.cug.modules.gom.entity.DepartmentEntity;
import io.cug.modules.gom.entity.DeviceEntity;
import io.cug.modules.gom.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;


@Api(tags = "监测——监测测站")
@RestController
@RequestMapping("gom/AutoStation")
public class AutoStationController {

    @Autowired
    private AutoStationService autoStationService;

    @Autowired
    private AutoLearnService autoLearnService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private AliIOTService aliIOTService;

    @Login
    @PostMapping("/List")
    @ApiOperation("测站列表")
    public R classList(@LoginUser AppUserEntity user, @RequestBody AutoStationEntity autoStationEntity){
        List<AutoStationEntity> list = autoStationService.QueryValidStation(autoStationEntity.getProjectId());
        for (int i = 0;i<list.size();i++){
            if(list.get(i).getDeviceId()!=null&&list.get(i).getDeviceId()!=0){
                DeviceEntity deviceEntity = deviceService.getById(list.get(i).getDeviceId());
                deviceEntity.setAliIOTEntity(aliIOTService.getByDeviceId(deviceEntity.getId()));
                list.get(i).setDeviceEntity(deviceEntity);
            }else {
                list.get(i).setDeviceEntity(null);
            }
        }
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加测站")
    public R add(@LoginUser AppUserEntity user, @RequestBody AutoStationEntity autoStationEntity) {
        if (autoStationService.save(autoStationEntity)) {
            //添加绑定监测点
            Long[] objectList = autoStationEntity.getObjectList();
            for(Long objectId : objectList){
                AutoLearnEntity entity = new AutoLearnEntity();
                entity.setObjectId(objectId);
                entity.setStationId(autoStationEntity.getId());
                autoLearnService.save(entity);
            }
            return R.ok().put("result", autoStationEntity);
        }
        else {
            return R.error("添加测站失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除测站")
    public R delete(@LoginUser AppUserEntity user, @RequestBody AutoStationEntity autoStationEntity){
        autoStationEntity.setStatus(99);//99-删除状态
        autoStationService.updateById(autoStationEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改测站")
    public R edit(@LoginUser AppUserEntity user, @RequestBody AutoStationEntity autoStationEntity){
        autoStationService.updateById(autoStationEntity);
        setLearnObject(autoStationEntity);
        return R.ok().put("result", "更新成功!");
    }

    public void setLearnObject(AutoStationEntity autoStationEntity){
        List<AutoLearnEntity> autoLearnEntities = autoLearnService.QueryValidLearn(autoStationEntity.getId());
        Long[] objectList = autoStationEntity.getObjectList();
        for (Long objectId : objectList) {
            boolean exists = false;
            for (AutoLearnEntity entity : autoLearnEntities) {
                if (entity.getObjectId().equals(objectId)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                AutoLearnEntity entity = new AutoLearnEntity();
                entity.setObjectId(objectId);
                entity.setStationId(autoStationEntity.getId());
                autoLearnService.save(entity);
            }
        }
        // 删除不存在的元素
        Iterator<AutoLearnEntity> iterator = autoLearnEntities.iterator();
        while (iterator.hasNext()) {
            AutoLearnEntity entity = iterator.next();
            boolean found = false;
            for (Long objectId : objectList) {
                if (entity.getObjectId().equals(objectId)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                entity.setStatus(99);
                autoLearnService.updateById(entity);
            }
        }
    }
}
