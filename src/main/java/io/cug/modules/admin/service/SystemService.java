
package io.cug.modules.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.common.utils.PageUtils;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.admin.entity.SystemEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 
 *
 * @author
 * @email 3582996245@qq.com
 * @date 2022-01-19 16:29:48
 */
public interface SystemService extends IService<SystemEntity> {

    PageUtils queryPage(Map<String, Object> params);


}

