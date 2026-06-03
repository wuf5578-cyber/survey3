
package io.cug.modules.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.cug.common.exception.cugException;
import io.cug.common.vo.*;
import io.cug.common.utils.*;
import io.cug.modules.admin.entity.SystemEntity;
import io.cug.modules.admin.service.*;
import io.cug.modules.app.param.*;
import io.cug.modules.sys.entity.SysUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.admin.dao.AppUserDao;
import io.cug.modules.admin.entity.AppUserEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;


@Service
@Slf4j
public class AppUserServiceImpl extends ServiceImpl<AppUserDao, AppUserEntity> implements AppUserService {


    @Autowired
    private AppUserDao userDao;

    @Autowired
    private RedisUtils redisUtils;


    @Autowired
    private SystemService systemService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<AppUserEntity> queryWrapper = new QueryWrapper<>();
        //模糊查询
        String key = (String) params.get("key");
        if (!ObjectUtil.isEmpty(key)) {
            queryWrapper.like("username", key)
                    .or()
                    .like("mobile", key);
        }
        queryWrapper.lambda().orderByDesc(AppUserEntity::getUid);
        IPage<AppUserEntity> page = this.page(
                new Query<AppUserEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }


    @Override
    public void ban(Integer id) {
        Integer status = this.lambdaQuery().eq(AppUserEntity::getUid, id).one().getStatus();
        if (status.equals(Constant.USER_BANNER)) {
            throw new cugException("该用户已被禁用");
        }
        this.lambdaUpdate()
                .set(AppUserEntity::getStatus, 1)
                .eq(AppUserEntity::getUid, id)
                .update();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void openBan(Integer id) {
        Integer status = this.lambdaQuery().eq(AppUserEntity::getUid, id).one().getStatus();
        if (status.equals(Constant.USER_NORMAL)) {
            throw new cugException("该用户已解除禁用");
        }
        boolean update = this.lambdaUpdate()
                .set(AppUserEntity::getStatus, 0)
                .eq(AppUserEntity::getUid, id)
                .update();
        if(!update){
            throw new cugException("解除失败");
        }
    }

    @Override
    public HomeRateResponse indexDate() {
        String today = cn.hutool.core.date.DateUtil.date().toString("yyyy-MM-dd");
        String yesterday = cn.hutool.core.date.DateUtil.yesterday().toString("yyyy-MM-dd");

        HomeRateResponse response = new HomeRateResponse();

        response.setNewUserNum(this.getRegisterNumByDate(today));
        response.setYesterdayNewUserNum(this.getRegisterNumByDate(yesterday));

        return response;
    }
    @Override
    public Integer smsRegister(RegisterForm form, HttpServletRequest request) {
        String codeKey = "code_" + form.getMobile();
        String s = redisUtils.get(codeKey);
        if (!s.equals(form.getCode())) {
            throw new cugException("验证码错误");
        }
        AppUserEntity appUser = this.lambdaQuery().eq(AppUserEntity::getMobile, form.getMobile()).one();
        if (appUser != null) {
            throw new cugException("该手机号已存在");
        }else{
            AppUserEntity appUserEntity = new AppUserEntity();
            appUserEntity.setMobile(form.getMobile());
            appUserEntity.setGender(form.getGender());
            appUserEntity.setAvatar(Constant.DEAULT_HEAD);
            appUserEntity.setUsername(form.getUsername());
            appUserEntity.setPassword(form.getPassword());
            appUserEntity.setEmail(form.getEmail());
            appUserEntity.setCreateTime(DateUtil.nowDateTime());
            appUserEntity.setUpdateTime(DateUtil.nowDateTime());
            baseMapper.insert(appUserEntity);
            return appUserEntity.getUid();
        }

    }
    @Override
    public Integer Login(LoginForm form, HttpServletRequest request) {
        AppUserEntity appUserEntity = this.lambdaQuery().eq(AppUserEntity::getMobile, form.getMobile()).one();
        String password = appUserEntity.getPassword();
        if (!password.equals(form.getPassword())) {
            throw new cugException("密码错误");
        }
        if (ObjectUtil.isNotNull(appUserEntity)) {
            //登录
            if (appUserEntity.getStatus() == 1) {
                throw new cugException("该账户已被禁用");
            }
            return appUserEntity.getUid();
        } else {
            throw new cugException("用户不存在");
        }
    }

    @Override
    public Integer smsLogin(SmsLoginForm form, HttpServletRequest request) {
        AppUserEntity appUserEntity = this.lambdaQuery().eq(AppUserEntity::getMobile, form.getMobile()).one();
        String codeKey = "code_" + form.getMobile();
        String s = redisUtils.get(codeKey);
        if (!s.equals(form.getCode())) {
            throw new cugException("验证码错误");
        }
        if (ObjectUtil.isNotNull(appUserEntity)) {
            //登录
            if (appUserEntity.getStatus() == 1) {
                throw new cugException("该账户已被禁用");
            }
            return appUserEntity.getUid();
        } else {
            throw new cugException("用户不存在");
          /* 2022-11-26 laizulong
            //注册 -- 用户不存在自动生成用户记录
            AppUserEntity appUser = new AppUserEntity();
            appUser.setMobile(form.getMobile());
            appUser.setGender(0);
            appUser.setAvatar(Constant.DEAULT_HEAD);
            appUser.setUsername("LF_" + RandomUtil.randomNumbers(8));
            appUser.setCreateTime(DateUtil.nowDateTime());
            appUser.setUpdateTime(DateUtil.nowDateTime());
            List<String> list = new ArrayList<>();
            list.add("新人");
            appUser.setTagStr(JSON.toJSONString(list));
            baseMapper.insert(appUser);
            AppUserEntity user = this.lambdaQuery().eq(AppUserEntity::getMobile, form.getMobile()).one();
            return user.getUid();
           */
        }
    }

    @Override
    public String sendSmsCode(SendCodeForm param) {
        String code = RandomUtil.randomNumbers(6);
        String codeKey = "code_" + param.getMobile();
        String s = redisUtils.get(codeKey);
        if (ObjectUtil.isNotNull(s)) {
            return s;
        }
        redisUtils.set(codeKey, code, 60 * 5);
        return code;
    }

    @Override
    public AppUserResponse getUserInfo(AppUserEntity user) {

        AppUserResponse response = new AppUserResponse();
        BeanUtils.copyProperties(user, response);

        return response;
    }

    @Override
    public void updateAppUserInfo(AppUserUpdateForm appUserUpdateForm, AppUserEntity user) {
        if (!ObjectUtil.isEmpty(appUserUpdateForm.getAvatar())) {
            user.setAvatar(appUserUpdateForm.getAvatar());
        }
        if(!ObjectUtil.isEmpty(appUserUpdateForm.getGender())){
            user.setGender(appUserUpdateForm.getGender());
        }
        baseMapper.updateById(user);
        redisUtils.delete("userId:" + user.getUid());
    }

    @Override
    public AppUserInfoResponse findUserInfoById(Integer uid, AppUserEntity user) {
        AppUserEntity userEntity = this.getById(uid);
        AppUserInfoResponse response = new AppUserInfoResponse();
        BeanUtils.copyProperties(userEntity, response);
        return response;
    }

    @Override
    public Integer miniWxLogin(WxLoginForm form) {

        SystemEntity system = systemService.lambdaQuery().eq(SystemEntity::getConfig, "miniapp").one();

        //小程序唯一标识   (在微信小程序管理后台获取)
        String appId = system.getValue();
        //小程序的 app secret (在微信小程序管理后台获取)
        String secret = system.getExtend();
        //授权（必填）
        String grant_type = "authorization_code";
        //https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
        //向微信服务器 使用登录凭证 code 获取 session_key 和 openid
        String params = "appid=" + appId + "&secret=" + secret + "&js_code=" + form.getCode() + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        //解析相应内容（转换成json对象）
        JSONObject json = JSON.parseObject(sr);
        //用户的唯一标识（openId）
        String openId = (String) json.get("openid");
        //根据openId获取数据库信息 判断用户是否登录
        AppUserEntity user = this.lambdaQuery().eq(AppUserEntity::getOpenid, openId).one();
        if (ObjectUtil.isNotNull(user)) {
            //已登录用户
            if (user.getStatus() == 1) {
                throw new cugException("该账户已被禁用");
            }
            //其他业务todo
            return user.getUid();
        } else {
            //新注册用户
            AppUserEntity appUser = new AppUserEntity();
            appUser.setGender(0);
            appUser.setAvatar(Constant.DEAULT_HEAD);
            appUser.setUsername("LF_wx" + RandomUtil.randomNumbers(8));
            appUser.setCreateTime(DateUtil.nowDateTime());
            appUser.setUpdateTime(DateUtil.nowDateTime());
            appUser.setOpenid(openId);

            baseMapper.insert(appUser);
            AppUserEntity users = this.lambdaQuery().eq(AppUserEntity::getOpenid, openId).one();
            return users.getUid();
        }
    }


    private Integer getTotalNum() {
        return this.lambdaQuery().select(AppUserEntity::getUid).count();
    }

    private Integer getRegisterNumByDate(String date) {
        QueryWrapper<AppUserEntity> wrapper = Wrappers.query();
        wrapper.select("uid");
        wrapper.apply("date_format(create_time, '%Y-%m-%d') = {0}", date);
        return userDao.selectCount(wrapper);
    }
    @Override
    public List<String> queryAllPerms(Integer uid) {
        return baseMapper.queryAllPerms(uid);
    }

    @Override
    public List<Long> queryAllMenuId(Integer uid) {
        return baseMapper.queryAllMenuId(uid);
    }

    @Override
    public SysUserEntity queryByUserName(String username) {
        return baseMapper.queryByUserName(username);
    }

}