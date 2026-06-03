
package io.cug.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.common.utils.PageUtils;
import io.cug.modules.sys.entity.SysLogEntity;

import java.util.Map;


/**
 * 系统日志
 *
 */
public interface SysLogService extends IService<SysLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
