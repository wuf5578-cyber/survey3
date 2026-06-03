package io.cug.modules.gom.controller;

import io.cug.common.utils.DateUtil;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.ImageEntity;
import io.cug.modules.gom.entity.ObjectEntity;
import io.cug.modules.gom.entity.ReportEntity;
import io.cug.modules.gom.service.ImageService;
import io.cug.modules.gom.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "文件存储")
@RestController
@RequestMapping("gom/Image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Login
    @PostMapping("/List")
    @ApiOperation("根据项目id和图片类型获取image")
    public R classList(@LoginUser AppUserEntity user, @RequestBody ImageEntity imageEntity){
        List<ImageEntity> list = imageService.selectImageByProjectId(imageEntity.getProjectId(),imageEntity.getType());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/listCesiumFile")
    @ApiOperation("根据项目id获取cesium文件")
    public R listCesiumFile(@LoginUser AppUserEntity user, @RequestBody ImageEntity imageEntity){
        List<ImageEntity> list = imageService.selectCesiumFile(imageEntity.getProjectId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除image信息")
    public R delete(@LoginUser AppUserEntity user, @RequestBody ImageEntity imageEntity){
        imageEntity.setStatus(99);//99-删除状态
        imageService.updateById(imageEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加image")
    public R add(@LoginUser AppUserEntity user, @RequestBody ImageEntity imageEntity) {

        imageEntity.setStatus(0);
        if (imageService.save(imageEntity)) {
            return R.ok().put("result", imageEntity);
        }
        else {
            return R.error("添加失败!");
        }
    }
}
