
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.TunnelDao;
import io.cug.modules.gom.entity.TunnelEntity;
import io.cug.modules.gom.service.TunnelService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("tunnelService")
public class TunnelServiceImpl extends ServiceImpl<TunnelDao, TunnelEntity> implements TunnelService {

    @Override
    public List<TunnelEntity> SelectAll(long projectId){
        return baseMapper.SelectAll(projectId);
    }

}