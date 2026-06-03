package io.cug.modules.gom.controller;

import io.cug.common.utils.DateUtil;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.DepartmentEntity;
import io.cug.modules.gom.entity.PointEntity;
import io.cug.modules.gom.entity.ProLinkDepartEntity;
import io.cug.modules.gom.service.DepartmentService;
import io.cug.modules.gom.service.ProLinkDepartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "监测——监测项目部门关系")
@RestController
@RequestMapping("gom/ProLinkDepart")
public class ProLinkDepartController {

    @Autowired
    private ProLinkDepartService proLinkDepartService;

    @Login
    @PostMapping("/List")
    @ApiOperation("项目部门关系列表")
    public R classList(@LoginUser AppUserEntity user, @RequestBody ProLinkDepartEntity proLinkDepartEntity){
        List<ProLinkDepartEntity> list = proLinkDepartService.QueryValidProLinkDepart(proLinkDepartEntity.getProjectId());
        String[] projectDepartment=new String[3];
        if(!list.isEmpty()){
           for(ProLinkDepartEntity data:list){
               projectDepartment[data.getDepartmentType()-1]=proLinkDepartService.getDepartmentName(data.getDepartmentId());
           }
        }
        return R.ok().put("result", projectDepartment);
    }



    @Login
    @PostMapping("/add")
    @ApiOperation("增加项目部门关系")
    public R add(@LoginUser AppUserEntity user, @RequestBody ProLinkDepartEntity proLinkDepartEntity) {

        if (proLinkDepartService.save(proLinkDepartEntity)) {
            return R.ok().put("result", proLinkDepartEntity);
        }
        else {
            return R.error("添加失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除项目部门关系")
    public R delete(@LoginUser AppUserEntity user, @RequestBody ProLinkDepartEntity proLinkDepartEntity){
        proLinkDepartEntity.setStatus(99);//99-删除状态
        //ProLinkDepartService.updateById(proLinkDepartEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改项目部门关系")
    public R edit(@LoginUser AppUserEntity user, @RequestBody ProLinkDepartEntity proLinkDepartEntity){
        proLinkDepartService.updateById(proLinkDepartEntity);
        return R.ok().put("result", "更新成功!");
    }
}
