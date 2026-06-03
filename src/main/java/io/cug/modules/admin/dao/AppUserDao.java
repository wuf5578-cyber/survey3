
package io.cug.modules.admin.dao;

import io.cug.modules.admin.entity.AppUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * 
 * @author
 * @email 3582996245@qq.com
 * @date 2022-01-20 12:10:43
 */
@Mapper
public interface AppUserDao extends BaseMapper<AppUserEntity> {
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
