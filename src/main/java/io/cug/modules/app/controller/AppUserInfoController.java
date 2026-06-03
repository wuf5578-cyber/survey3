
package io.cug.modules.app.controller;

import io.cug.common.utils.DateUtil;
import io.cug.common.vo.AppUserInfoResponse;
import io.cug.common.vo.AppUserResponse;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.admin.service.AppUserService;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.app.param.*;
import io.cug.modules.app.utils.JwtUtils;
import io.cug.modules.gom.entity.ProjectEntity;
import io.cug.modules.gom.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * APP登录接口
 *
 * @author
 * @date 2022/6/9 22:40
 */
@RestController
@RequestMapping("/app/user")
@Api(tags = "APP登录接口")
public class AppUserInfoController {


    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private ProjectService projectService;


    @Value("${sms.open}")
    private boolean isOpen;

    @GetMapping("/isok")
    @ApiOperation("判断API服务是否启动")
    public R isok() {
        return R.ok().put("result", true);
    }

    @PostMapping("/sendSmsCode")
    @ApiOperation("发送手机验证码")
    public R sendSmsCode(@RequestBody SendCodeForm param) {
        String code = appUserService.sendSmsCode(param);
        if (isOpen) {
            //TODO
            //send Aliyun Sms code -- 手机短信 发送验证码
        }
        return R.ok("手机验证码:" + code);
    }
    /**
     * 手机验证码注册
     */
    @PostMapping("/smsRegister")
    @ApiOperation("手机验证码注册")
    public R smsRegister(@RequestBody RegisterForm form, HttpServletRequest request) {
        //用户登录
        Integer uid = appUserService.smsRegister(form, request);

        return R.ok().put("success",uid);
    }
    /**
     * 手机密码登录
     */
    @PostMapping("/Login")
    @ApiOperation("手机密码登录")
    public R Login(@RequestBody LoginForm form, HttpServletRequest request) {

        //用户登录
        Integer uid = appUserService.Login(form, request);

        //生成token
        String token = jwtUtils.generateToken(uid);

        Map<String, Object> map = new HashMap<>();
        map.put("MenuId", appUserService.queryAllMenuId(uid));
        map.put("Perms", appUserService.queryAllPerms(uid));
        map.put("token", token);
        map.put("expire", jwtUtils.getExpire());

        return R.ok(map);
    }
    /**
     * 手机验证码登录
     */
    @PostMapping("/smsLogin")
    @ApiOperation("手机验证码登录")
    public R smsLogin(@RequestBody SmsLoginForm form, HttpServletRequest request) {

        //用户登录
        Integer uid = appUserService.smsLogin(form, request);

        //生成token
        String token = jwtUtils.generateToken(uid);

        Map<String, Object> map = new HashMap<>();
        map.put("MenuId", appUserService.queryAllMenuId(uid));
        map.put("Perms", appUserService.queryAllPerms(uid));
        map.put("token", token);
        map.put("expire", jwtUtils.getExpire());

        return R.ok(map);
    }

    /**
     * 微信小程序登录
     */
    @PostMapping("/miniWxlogin")
    @ApiOperation("手机验证码登录")
    public R miniWxLogin(@RequestBody WxLoginForm form) {

        //用户登录
        Integer userId = appUserService.miniWxLogin(form);

        //生成token
        String token = jwtUtils.generateToken(userId);

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expire", jwtUtils.getExpire());

        return R.ok(map);
    }


    @Login
    @GetMapping("/userInfo")
    @ApiOperation("获取用户信息")
    public R userInfo(@LoginUser AppUserEntity user) {

        AppUserResponse response = appUserService.getUserInfo(user);
        return R.ok().put("result", response);
    }


    @Login
    @PostMapping("/userInfoEdit")
    @ApiOperation("用户修改个人信息")
    public R userInfoEdit(@LoginUser AppUserEntity user, @RequestBody AppUserUpdateForm appUserUpdateForm) {
        appUserService.updateAppUserInfo(appUserUpdateForm, user);
        return R.ok("修改成功");
    }


    @Login
    @PostMapping("/userInfoById")
    @ApiOperation("用户个人主页信息")
    public R userInfoById(@RequestBody AppUserInfoForm request, @LoginUser AppUserEntity user) {
        AppUserInfoResponse response = appUserService.findUserInfoById(request.getUid(), user);

        return R.ok().put("result", response);
    }

}
