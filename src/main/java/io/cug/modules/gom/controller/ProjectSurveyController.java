package io.cug.modules.gom.controller;

import io.cug.common.utils.DateUtil;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.AutoComputeEntity;
import io.cug.modules.gom.entity.ProjectSurveyEntity;
import io.cug.modules.gom.service.AutoComputeService;
import io.cug.modules.gom.service.ProjectSurveyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "监测——项目监测")
@RestController
@RequestMapping("gom/projectSurvey")
public class ProjectSurveyController {

    @Autowired
    private ProjectSurveyService projectSurveyService;

    @Login
    @PostMapping("/List")
    @ApiOperation("列表")
    public R classList(@LoginUser AppUserEntity user, @RequestBody ProjectSurveyEntity projectSurveyEntity){
        List<ProjectSurveyEntity> list = projectSurveyService.selectValidSurvey(projectSurveyEntity.getProjectId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加")
    public R add(@LoginUser AppUserEntity user, @RequestBody ProjectSurveyEntity projectSurveyEntity) {
        projectSurveyEntity.setCreateTime(DateUtil.nowDateTime());
        if (projectSurveyService.save(projectSurveyEntity)) {
            return R.ok().put("result", projectSurveyEntity);
        }
        else {
            return R.error("添加失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除")
    public R delete(@LoginUser AppUserEntity user, @RequestBody ProjectSurveyEntity projectSurveyEntity){
        projectSurveyEntity.setStatus(99);//99-删除状态
        projectSurveyService.updateById(projectSurveyEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改")
    public R edit(@LoginUser AppUserEntity user, @RequestBody ProjectSurveyEntity projectSurveyEntity){
        projectSurveyService.updateById(projectSurveyEntity);
        return R.ok().put("result", "更新成功!");
    }
}
