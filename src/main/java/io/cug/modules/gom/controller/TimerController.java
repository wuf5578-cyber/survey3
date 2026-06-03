package io.cug.modules.gom.controller;

import io.cug.common.utils.DateUtil;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.AutoComputeEntity;
import io.cug.modules.gom.entity.TimerEntity;
import io.cug.modules.gom.service.AutoComputeService;
import io.cug.modules.gom.service.TimerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "温湿度-定时器")
@RestController
@RequestMapping("gom/Timer")
public class TimerController {

    @Autowired
    private TimerService timerService;

    @Login
    @PostMapping("/select")
    @ApiOperation("查找")
    public R select(@LoginUser AppUserEntity user, @RequestBody TimerEntity timerEntity) {
        TimerEntity entity = timerService.selectTimerByDeviceId(timerEntity.getDeviceId());
        return R.ok().put("result", entity);
    }
    @Login
    @PostMapping("/add")
    @ApiOperation("增加")
    public R add(@LoginUser AppUserEntity user, @RequestBody TimerEntity timerEntity) {
        if (timerService.save(timerEntity)) {
            return R.ok().put("result", timerEntity);
        }
        else {
            return R.error("添加失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除")
    public R delete(@LoginUser AppUserEntity user, @RequestBody TimerEntity timerEntity){
        timerEntity.setStatus(99);//99-删除状态
        timerService.updateById(timerEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改")
    public R edit(@LoginUser AppUserEntity user, @RequestBody TimerEntity timerEntity){
        timerService.updateById(timerEntity);
        return R.ok().put("result", "更新成功!");
    }
}
