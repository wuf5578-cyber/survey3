
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.TunnelVaryDao;
import io.cug.modules.gom.entity.TunnelVaryEntity;
import io.cug.modules.gom.service.TunnelVaryService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service("tunnelVaryService")
public class TunnelVaryServiceImpl extends ServiceImpl<TunnelVaryDao, TunnelVaryEntity> implements TunnelVaryService {

    @Override
    public List<TunnelVaryEntity> SelectByPeriodId(long projectId, long loopId, long periodId){
        return baseMapper.SelectByPeriodId(projectId, periodId, loopId);
    }

    @Override
    public List<TunnelVaryEntity> SelectAnalyseData(long loopId, Date startTime, Date endTime){
        return baseMapper.SelectAnalyseData(loopId, startTime, endTime);
    }
}