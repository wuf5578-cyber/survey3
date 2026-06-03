package io.cug.modules.gom.controller;

import io.cug.common.utils.DateUtil;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.app.param.SqlWhereForm;
import io.cug.modules.gom.entity.EarlyWarningEntity;
import io.cug.modules.gom.entity.ProjectEntity;
import io.cug.modules.gom.entity.ReportEntity;
import io.cug.modules.gom.service.EarlyWarningService;
import io.cug.modules.gom.service.EarthSubsidenceService;
import io.cug.modules.gom.service.ProjectService;
import io.cug.modules.gom.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author
 * @date 2022/9/4 10:06
 */
@Api(tags = "监测——项目")
@RestController
@RequestMapping("gom/project")
public class ProjectController {


    @Autowired
    private ProjectService projectService;

    @Login
    @PostMapping("/List")
    @ApiOperation("项目列表")
    public R classList(@LoginUser AppUserEntity user, @RequestBody SqlWhereForm sqlWhereForm){
        List<ProjectEntity> list;
        if (sqlWhereForm.getWhere().isEmpty()){
            list = projectService.listValidProjects();
        }
        else {
            list = projectService.selectValidProjectsByName(sqlWhereForm.getWhere());
        }

        return R.ok().put("result",list);
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加项目")
    public R add(@LoginUser AppUserEntity user, @RequestBody ProjectEntity projectEntity) {
        projectEntity.setCreateByUserid( (long)user.getUid() );
        projectEntity.setCreateTime(DateUtil.nowDateTime());
        if (projectService.save(projectEntity)) {
            //添加测量类型预警默认值节点
            addDefaultWarning(projectEntity.getId());
            addReport(projectEntity.getId());
            return R.ok().put("result", projectEntity);
        }
        else {
            return R.error("添加项目失败!");
        }
    }

    @Autowired
    private EarlyWarningService earlyWarningService;
    public void addDefaultWarning(long projectId){
        for(int i = 1; i <= 12; i++){
            EarlyWarningEntity earlyWarningEntity = new EarlyWarningEntity();
            earlyWarningEntity.setProjectId(projectId);
            earlyWarningEntity.setObjectId((long)0);
            earlyWarningEntity.setSurveyType(i);
            earlyWarningEntity.setType(1);
            earlyWarningService.save(earlyWarningEntity);
        }
        return;
    }
    @Autowired
    private ReportService reportService;
    public void addReport(long projectId){
        ReportEntity reportEntity=new ReportEntity();
        reportEntity.setProjectId(projectId);
        reportService.save(reportEntity);
        return;
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除项目")
    public R delete(@LoginUser AppUserEntity user, @RequestBody ProjectEntity projectEntity){
      //  if ((long)user.getUid() == projectEntity.getCreate_by_userid())
        {
            projectEntity.setStatus(99);//99-删除状态
            projectService.updateById(projectEntity);
        }

        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改项目")
    public R edit(@LoginUser AppUserEntity user, @RequestBody ProjectEntity projectEntity){

        projectService.updateById(projectEntity);

        return R.ok().put("result", projectEntity);
    }

    @Login
    @PostMapping("/findNameExist")
    @ApiOperation("查询是否存在该name")
    public R findNameExist(@LoginUser AppUserEntity user, @RequestBody ProjectEntity projectEntity){
        boolean flag = false;
        List<ProjectEntity> list = projectService.listValidProjects();
        for (int i = 0;i<list.size();i++){
            if(list.get(i).getName().equals(projectEntity.getName())){
                flag = true;
                break;
            }
        }

        return R.ok().put("result", flag);
    }

}
