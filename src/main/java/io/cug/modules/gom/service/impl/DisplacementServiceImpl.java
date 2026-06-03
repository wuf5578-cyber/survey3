
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.DisplacementDao;
import io.cug.modules.gom.entity.DisplacementEntity;
import io.cug.modules.gom.entity.PeriodEntity;
import io.cug.modules.gom.service.DisplacementService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service("displacementService")
public class DisplacementServiceImpl extends ServiceImpl<DisplacementDao, DisplacementEntity> implements DisplacementService {
    @Override
    public List<DisplacementEntity> QueryValidDisplacement(long objectId){
        //数据查询
        List<DisplacementEntity> list = baseMapper.QueryValidDisplacement(objectId);

        return list;
    }

    @Override
    public DisplacementEntity QueryValidDisplacementByPeriodId(long objectId,long periodId){
        return baseMapper.QueryValidDisplacementByPeriodId(objectId, periodId);
    }

    @Override
    public List<DisplacementEntity> QueryDisplacementWithPeriod(long objectId){
        List <DisplacementEntity> list = baseMapper.QueryDisplacementWithPeriod(objectId);
        return list;
    }

    @Override
    public DisplacementEntity getFirstData(long objectId)
    {
        return baseMapper.getFirstData(objectId);
    }

    @Override
    public List<PeriodEntity> QueryAllPeriodsByProjectId(long projectId){
        return baseMapper.QueryAllPeriodsByProjectId(projectId);
    }

    @Override
    public List<DisplacementEntity> QueryDisplacementWithObjectName(long periodId){
        return baseMapper.QueryDisplacementWithObjectName(periodId);
    }

    @Override
    public Boolean QueryDisplacementGreaterThanBegin(long objectId, Date begin){
        List<DisplacementEntity> list = baseMapper.QueryDisplacementGreaterThanBegin(objectId, begin);
        if(list.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
    @Override
    public DisplacementEntity selectByPeriodAndObject(long objectId, long periodId){
        return baseMapper.selectByPeriodAndObject(objectId, periodId);
    }
}