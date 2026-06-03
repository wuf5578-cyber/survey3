package io.cug.modules.gom.controller;

import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.EarlyWarningEntity;
import io.cug.modules.gom.entity.WarningStandardEntity;
import io.cug.modules.gom.service.EarlyWarningService;
import io.cug.modules.gom.service.WarningStandardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "预警规范表")
@RestController
@RequestMapping("gom/WarningStandard")
public class WarningStandardController {

    @Autowired
    private WarningStandardService warningStandardService;

    @Login
    @PostMapping("/ListBySurveyType")
    @ApiOperation("根据测量类型获取预警规范值")
    public R classList(@LoginUser AppUserEntity user, @RequestBody WarningStandardEntity warningStandardEntity){
        List<WarningStandardEntity> list = warningStandardService.selectWarningStandardBySurveyType(warningStandardEntity.getSurveyType());
        return R.ok().put("result", list);
    }

}
