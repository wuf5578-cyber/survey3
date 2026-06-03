
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.AutoCoorDao;
import io.cug.modules.gom.entity.AutoCoorEntity;
import io.cug.modules.gom.service.AutoCoorService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("autoCoorService")
public class AutoCoorServiceImpl extends ServiceImpl<AutoCoorDao, AutoCoorEntity> implements AutoCoorService {

    @Override
    public List<AutoCoorEntity> QueryValidGivenPoint(Long projectId){
        return baseMapper.QueryValidGivenPoint(projectId);
    }

    @Override
    public List<AutoCoorEntity> QueryValidCoorByObjectId(Long objectId){
        return baseMapper.QueryValidCoorByObjectId(objectId);
    }

    @Override
    public List<AutoCoorEntity> QueryValidCoorByPeriodId(Long periodId){
        return baseMapper.QueryValidCoorByPeriodId(periodId);
    }

    @Override
    public AutoCoorEntity QueryValidCoorByPeriodIdAndObjectId(Long periodId,Long objectId){
        return baseMapper.QueryValidCoorByPeriodIdAndObjectId(periodId, objectId);
    }

    @Override
    public AutoCoorEntity updateByObjectId( Long periodId, Long objectId){
        return baseMapper.updateByObjectId(periodId,objectId);
    }
}