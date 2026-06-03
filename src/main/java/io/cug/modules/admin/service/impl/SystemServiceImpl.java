
package io.cug.modules.admin.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.common.utils.PageUtils;
import io.cug.common.utils.Query;

import io.cug.modules.admin.dao.SystemDao;
import io.cug.modules.admin.entity.SystemEntity;
import io.cug.modules.admin.service.SystemService;

@Service("systemService")
public class SystemServiceImpl extends ServiceImpl<SystemDao, SystemEntity> implements SystemService {


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SystemEntity> page = this.page(
                new Query<SystemEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }


}