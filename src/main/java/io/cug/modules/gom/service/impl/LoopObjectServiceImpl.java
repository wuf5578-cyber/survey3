package io.cug.modules.gom.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.LoopObjectDao;
import io.cug.modules.gom.entity.LoopObjectEntity;
import io.cug.modules.gom.service.LoopObjectService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("loopObjectService")
public class LoopObjectServiceImpl extends ServiceImpl<LoopObjectDao, LoopObjectEntity> implements LoopObjectService {
    @Override
    public List<LoopObjectEntity> SelectByObjectId(long objectId) {
        return baseMapper.SelectByObjectId(objectId);
    }

    @Override
    public List<LoopObjectEntity> SelectAll(long projectId) {
        return baseMapper.SelectAll(projectId);
    }

    @Override
    public  List<LoopObjectEntity> SelectByPeriod(long objectId){
        return baseMapper.SelectByPeriod(objectId);
    }

    @Override
    public List<LoopObjectEntity> SelectDisplacement(long loopId, int objectType, Date startTime, Date endTime){
        return baseMapper.SelectDisplacement(loopId, objectType, startTime, endTime);
    }

    @Override
    public List<LoopObjectEntity> SelectByObjectType(long loopId, int objectType){
        return baseMapper.SelectByObjectType(loopId, objectType);
    }

    @Override
    public List<LoopObjectEntity> SelectByPeriodId(long loopId, long periodId){
        return baseMapper.SelectByPeriodId(loopId,periodId);
    }

    @Override
    public List<LoopObjectEntity> SelectFirstData(long objectId){
        return baseMapper.SelectFirstData(objectId);
    }

    @Override
    public List<LoopObjectEntity> SelectByPeriodIdAndObjectId(long objectType, long periodId){
        return baseMapper.SelectByPeriodIdAndObjectId(objectType, periodId);
    }
}
