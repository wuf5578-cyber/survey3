package io.cug.modules.gom.controller;

import io.cug.common.utils.DateUtil;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.app.param.ProjectIDForm;
import io.cug.modules.gom.entity.DepartmentEntity;
import io.cug.modules.gom.entity.ObjectEntity;
import io.cug.modules.gom.service.DepartmentService;
import io.cug.modules.gom.service.ObjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "监测——监测部门department")
@RestController
@RequestMapping("gom/Department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Login
    @PostMapping("/List")
    @ApiOperation("部门列表")
    public R classList(@LoginUser AppUserEntity user){
        List<DepartmentEntity> list = departmentService.QueryValidDepartment();
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加部门")
    public R add(@LoginUser AppUserEntity user, @RequestBody DepartmentEntity departmentEntity) {

        departmentEntity.setCreateBy( (long)user.getUid() );
        departmentEntity.setCreateTime(DateUtil.nowDateTime());
        if (departmentService.save(departmentEntity)) {
            return R.ok().put("result", departmentEntity);
        }
        else {
            return R.error("添加部门失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除部门")
    public R delete(@LoginUser AppUserEntity user, @RequestBody DepartmentEntity departmentEntity){
        departmentEntity.setStatus(99);//99-删除状态
        departmentService.updateById(departmentEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改部门")
    public R edit(@LoginUser AppUserEntity user, @RequestBody DepartmentEntity departmentEntity){
        departmentService.updateById(departmentEntity);
        return R.ok().put("result", "更新成功!");
    }
}
