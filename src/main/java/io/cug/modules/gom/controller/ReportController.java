package io.cug.modules.gom.controller;

import io.cug.common.utils.DateUtil;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.DepartmentEntity;
import io.cug.modules.gom.entity.ReportEntity;
import io.cug.modules.gom.service.DepartmentService;
import io.cug.modules.gom.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "监测——项目报告report")
@RestController
@RequestMapping("gom/Report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Login
    @PostMapping("/List")
    @ApiOperation("根据项目id获取报告list")
    public R classList(@LoginUser AppUserEntity user, @RequestBody ReportEntity reportEntity){
        ReportEntity list = reportService.selectValidReportByProjectId(reportEntity.getProjectId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除报告信息")
    public R delete(@LoginUser AppUserEntity user, @RequestBody ReportEntity reportEntity){
        reportEntity.setStatus(99);//99-删除状态
        reportService.updateById(reportEntity);
        return R.ok().put("result", "删除成功!");
    }

}
