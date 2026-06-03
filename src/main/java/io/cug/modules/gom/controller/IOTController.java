package io.cug.modules.gom.controller;

import cn.hutool.core.io.FileUtil;
import io.cug.common.utils.DateUtil;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.AliIOTEntity;
import io.cug.modules.gom.entity.AutoMeasureEntity;
import io.cug.modules.gom.service.AliIOTService;
import io.cug.modules.gom.utils.AliMQTTUtils;
import io.cug.modules.gom.utils.AliOSSUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Api(tags = "阿里云IOT mqtt连接")
@RestController
@RequestMapping("gom/IOT")
public class IOTController {

    @Autowired
    private AliMQTTUtils aliMQTTUtils;

    @Autowired
    private AliIOTService aliIOTService;

    @Login
    @PostMapping("/selectByType")
    @ApiOperation("根据类型查找")
    public R selectByType(@LoginUser AppUserEntity user, @RequestBody AliIOTEntity aliIOTEntity) {

        List<AliIOTEntity> list = aliIOTService.selectValidIOTByType(aliIOTEntity.getProjectId(),aliIOTEntity.getType());

        return R.ok().put("result",list);
    }
    @Login
    @PostMapping("/getById")
    @ApiOperation("根据id查找")
    public R getById(@LoginUser AppUserEntity user, @RequestBody AliIOTEntity aliIOTEntity) {

        AliIOTEntity entity = aliIOTService.getById(aliIOTEntity.getId());

        return R.ok().put("result",entity);
    }
    @Login
    @PostMapping("/getByDeviceId")
    @ApiOperation("根据设备id查找")
    public R getByDeviceId(@LoginUser AppUserEntity user, @RequestBody AliIOTEntity aliIOTEntity) {

        AliIOTEntity entity = aliIOTService.getByDeviceId(aliIOTEntity.getDeviceId());

        return R.ok().put("result",entity);
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加")
    public R add(@LoginUser AppUserEntity user, @RequestBody AliIOTEntity aliIOTEntity) {

        if (aliIOTService.save(aliIOTEntity)) {
            return R.ok().put("result", aliIOTEntity);
        }
        else {
            return R.error("添加失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除")
    public R delete(@LoginUser AppUserEntity user, @RequestBody AliIOTEntity aliIOTEntity){
        aliIOTEntity.setStatus(99);//99-删除状态
        aliIOTService.updateById(aliIOTEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改")
    public R edit(@LoginUser AppUserEntity user, @RequestBody AliIOTEntity aliIOTEntity){
        aliIOTService.updateById(aliIOTEntity);
        return R.ok().put("result", "更新成功!");
    }

}
