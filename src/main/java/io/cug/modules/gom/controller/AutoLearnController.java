package io.cug.modules.gom.controller;

import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.AutoLearnEntity;
import io.cug.modules.gom.entity.AutoStationEntity;
import io.cug.modules.gom.entity.ObjectEntity;
import io.cug.modules.gom.service.AutoLearnService;
import io.cug.modules.gom.service.AutoStationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "监测——监测自动学习")
@RestController
@RequestMapping("gom/AutoLearn")
public class AutoLearnController {

    @Autowired
    private AutoLearnService autoLearnService;

    @Login
    @PostMapping("/ListExt")
    @ApiOperation("学习列表")
    public R classList(@LoginUser AppUserEntity user, @RequestBody AutoLearnEntity autoLearnEntity){
        List<AutoLearnEntity> list = autoLearnService.QueryValidLearn(autoLearnEntity.getStationId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加学习")
    public R add(@LoginUser AppUserEntity user, @RequestBody AutoLearnEntity autoLearnEntity) {

        if (autoLearnService.save(autoLearnEntity)) {
            return R.ok().put("result", autoLearnEntity);
        }
        else {
            return R.error("添加失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除学习")
    public R delete(@LoginUser AppUserEntity user, @RequestBody AutoLearnEntity autoLearnEntity){
        autoLearnEntity.setStatus(99);//99-删除状态
        autoLearnService.updateById(autoLearnEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改学习")
    public R edit(@LoginUser AppUserEntity user, @RequestBody AutoLearnEntity autoLearnEntity){
        autoLearnService.updateById(autoLearnEntity);
        return R.ok().put("result", "更新成功!");
    }

    @Login
    @PostMapping("/QueryObject")
    @ApiOperation("查找自动监测节点")
    public R QueryObject(@LoginUser AppUserEntity user, @RequestBody ObjectEntity objectEntity){

        return R.ok().put("result", autoLearnService.QueryObject(objectEntity.getProjectId()));
    }
}
