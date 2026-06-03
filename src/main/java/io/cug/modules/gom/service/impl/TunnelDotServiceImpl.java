package io.cug.modules.gom.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.TunnelDotDao;
import io.cug.modules.gom.entity.TunnelDotEntity;
import io.cug.modules.gom.service.TunnelDotService;
import org.springframework.stereotype.Service;

@Service("tunnelDotService")
public class TunnelDotServiceImpl extends ServiceImpl<TunnelDotDao, TunnelDotEntity> implements TunnelDotService {
    @Override
    public TunnelDotEntity SelectDotByObjectId(long projectId, long objectId, long periodId){
        return baseMapper.SelectDotByObjectId(projectId, objectId, periodId);
    }
}
