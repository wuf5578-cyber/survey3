package io.cug.modules.gom.controller;

import io.cug.common.utils.DateUtil;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.DepartmentEntity;
import io.cug.modules.gom.entity.EarlyWarningEntity;
import io.cug.modules.gom.service.DepartmentService;
import io.cug.modules.gom.service.EarlyWarningService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "监测——监测预警")
@RestController
@RequestMapping("gom/EarlyWarning")
public class EarlyWarningController {

    @Autowired
    private EarlyWarningService earlyWarningService;

    @Login
    @PostMapping("/List")
    @ApiOperation("所有预警信息")
    public R classList(@LoginUser AppUserEntity user){
        List<EarlyWarningEntity> list = earlyWarningService.selectAllValidEarlyWarning();
        return R.ok().put("result", list);
    }
    @Login
    @PostMapping("/ListByObjectId")
    @ApiOperation("所有预警信息")
    public R ListByObjectId(@LoginUser AppUserEntity user, @RequestBody EarlyWarningEntity earlyWarningEntity){
        EarlyWarningEntity list = earlyWarningService.selectByObjectId(earlyWarningEntity.getProjectId(),earlyWarningEntity.getObjectId(),earlyWarningEntity.getSurveyType());
        return R.ok().put("result", list);
    }
    @Login
    @PostMapping("/insertOrUpdate")
    @ApiOperation("增加或更新预警")
    public R insertOrUpdate(@LoginUser AppUserEntity user, @RequestBody EarlyWarningEntity earlyWarningEntity) {
        EarlyWarningEntity entity = earlyWarningService.selectByObjectId(earlyWarningEntity.getProjectId(), earlyWarningEntity.getObjectId(),earlyWarningEntity.getSurveyType());
        if(entity==null){
            earlyWarningService.save(earlyWarningEntity);
        }else{
            earlyWarningEntity.setId(entity.getId());
            earlyWarningService.updateById(earlyWarningEntity);
        }
        return R.ok().put("result", earlyWarningEntity);

    }
    @Login
    @PostMapping("/add")
    @ApiOperation("增加预警")
    public R add(@LoginUser AppUserEntity user, @RequestBody EarlyWarningEntity earlyWarningEntity) {

        if (earlyWarningService.save(earlyWarningEntity)) {
            return R.ok().put("result", earlyWarningEntity);
        }
        else {
            return R.error("添加部门失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除预警")
    public R delete(@LoginUser AppUserEntity user, @RequestBody EarlyWarningEntity earlyWarningEntity){
        earlyWarningEntity.setStatus(99);//99-删除状态
        earlyWarningService.updateById(earlyWarningEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改预警")
    public R edit(@LoginUser AppUserEntity user, @RequestBody EarlyWarningEntity earlyWarningEntity){
        earlyWarningService.updateById(earlyWarningEntity);
        return R.ok().put("result", "更新成功!");
    }

    @Login
    @PostMapping("/selectDefaultEarlyWarningBySurveyType")
    @ApiOperation("查找默认预警值")
    public R selectDefaultEarlyWarningBySurveyType(@LoginUser AppUserEntity user, @RequestBody EarlyWarningEntity earlyWarningEntity){
        EarlyWarningEntity defaultEarlyWarning = earlyWarningService.selectDefaultEarlyWarningBySurveyType(earlyWarningEntity.getProjectId(),earlyWarningEntity.getSurveyType());

        return R.ok().put("result", defaultEarlyWarning);
    }
}
