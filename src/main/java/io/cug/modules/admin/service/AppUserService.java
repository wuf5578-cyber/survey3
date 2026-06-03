
package io.cug.modules.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.common.vo.AppUserInfoResponse;
import io.cug.common.vo.AppUserResponse;
import io.cug.common.vo.HomeRateResponse;
import io.cug.common.utils.PageUtils;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.param.*;
import io.cug.modules.sys.entity.SysUserEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author
 * @email 3582996245@qq.com
 * @date 2022-01-20 12:10:43
 */
public interface AppUserService extends IService<AppUserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void ban(Integer id);

    void openBan(Integer id);

    /**
     * 首页数据
     * @return HomeRateResponse
     */
    HomeRateResponse indexDate();

    Integer Login(LoginForm form, HttpServletRequest request);

    Integer smsLogin(SmsLoginForm form, HttpServletRequest request);

    Integer smsRegister(RegisterForm form, HttpServletRequest request);

    String sendSmsCode(SendCodeForm param);

    AppUserResponse getUserInfo(AppUserEntity user);

    void updateAppUserInfo(AppUserUpdateForm appUserUpdateForm, AppUserEntity user);


    AppUserInfoResponse findUserInfoById(Integer uid, AppUserEntity user);

    Integer miniWxLogin(WxLoginForm form);

    /**
     * 查询用户的所有权限
     * @param uid  用户ID
     */
    List<String> queryAllPerms(Integer uid);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Integer uid);

    /**
     * 根据用户名，查询系统用户
     */
    SysUserEntity queryByUserName(String username);
}

