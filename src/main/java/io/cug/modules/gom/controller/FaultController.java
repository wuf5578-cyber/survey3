package io.cug.modules.gom.controller;

import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.DeviceEntity;
import io.cug.modules.gom.entity.FaultEntity;
import io.cug.modules.gom.service.DeviceService;
import io.cug.modules.gom.service.FaultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "监测——故障信息")
@RestController
@RequestMapping("gom/Fault")
public class FaultController {

    @Autowired
    private FaultService faultService;

    @Login
    @PostMapping("/ListAll")
    @ApiOperation("所有故障")
    public R classList(@LoginUser AppUserEntity user, @RequestBody FaultEntity faultEntity){
        List<FaultEntity> list = faultService.selectAllFault(faultEntity.getProjectId());
        return R.ok().put("result", list);
    }
    @Login
    @PostMapping("/ListByStatusType")
    @ApiOperation("")
    public R ListByStationType(@LoginUser AppUserEntity user, @RequestBody FaultEntity faultEntity){
        List<FaultEntity> list = faultService.selectFaultByStatusType(faultEntity.getProjectId(),faultEntity.getStatus(),faultEntity.getType());
        return R.ok().put("result", list);
    }
    @Login
    @PostMapping("/ListByStatus")
    @ApiOperation("")
    public R ListByStation(@LoginUser AppUserEntity user, @RequestBody FaultEntity faultEntity){
        List<FaultEntity> list = faultService.selectFaultByStatus(faultEntity.getProjectId(),faultEntity.getStatus());
        return R.ok().put("result", list);
    }
    @Login
    @PostMapping("/ListByType")
    @ApiOperation("")
    public R selectFaultByType(@LoginUser AppUserEntity user, @RequestBody FaultEntity faultEntity){
        List<FaultEntity> list = faultService.selectFaultByType(faultEntity.getProjectId(),faultEntity.getType());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加信息")
    public R add(@LoginUser AppUserEntity user, @RequestBody FaultEntity faultEntity) {

        if (faultService.save(faultEntity)) {
            return R.ok().put("result", faultEntity);
        }
        else {
            return R.error("添加失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除设信息")
    public R delete(@LoginUser AppUserEntity user, @RequestBody FaultEntity faultEntity){
        faultEntity.setStatus(99);
        faultService.updateById(faultEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改信息")
    public R edit(@LoginUser AppUserEntity user, @RequestBody FaultEntity faultEntity){
        faultService.updateById(faultEntity);
        return R.ok().put("result", "更新成功!");
    }

}
