
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.TunnelStringDao;
import io.cug.modules.gom.entity.TunnelStringEntity;
import io.cug.modules.gom.service.TunnelStringService;
import org.springframework.stereotype.Service;


@Service("tunnelStringService")
public class TunnelStringServiceImpl extends ServiceImpl<TunnelStringDao, TunnelStringEntity> implements TunnelStringService {

    @Override
    public TunnelStringEntity SelectByStringLoopName(long periodId, long loopName){
        return baseMapper.SelectByStringLoopName(periodId, loopName);
    }
}