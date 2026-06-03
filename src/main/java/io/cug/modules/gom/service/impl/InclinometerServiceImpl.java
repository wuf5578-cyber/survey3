
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.DisplacementDao;
import io.cug.modules.gom.dao.InclinometerDao;
import io.cug.modules.gom.entity.*;
import io.cug.modules.gom.service.DisplacementService;
import io.cug.modules.gom.service.InclinometerService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service("inclinometerService")
public class InclinometerServiceImpl extends ServiceImpl<InclinometerDao, InclinometerEntity> implements InclinometerService {
    @Override
    public List<InclinometerEntity> QueryValidInclinometer(long objectId){
        //数据查询
        List<InclinometerEntity> list = baseMapper.QueryValidInclinometer(objectId);

        return list;
    }

    @Override
    public List<InclinometerEntity> QueryInclinometerWithPeriod(long objectId){
        List<InclinometerEntity> list = baseMapper.QueryInclinometerWithPeriod(objectId);
        return list;
    }

    @Override
    public InclinometerEntity QueryValidInclinometerEntityByPeriodId(long objectId,long periodId){
        return baseMapper.QueryValidInclinometerEntityByPeriodId(objectId, periodId);
    }

    @Override
    public InclinometerEntity getFirstData(long objectId){
        return baseMapper.getFirstData(objectId);
    }

    @Override
    public List<PeriodEntity> QueryAllPeriodsByProjectId(long projectId){
        return baseMapper.QueryAllPeriodsByProjectId(projectId);
    }

    @Override
    public List<InclinometerEntity> QueryInclinometerWithObjectName(long periodId){
        return baseMapper.QueryInclinometerWithObjectName(periodId);
    }

    @Override
    public Boolean QueryInclinometerGreaterThanBegin(long objectId, Date begin){
        List<InclinometerEntity> list = baseMapper.QueryInclinometerGreaterThanBegin(objectId, begin);
        if(list.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public InclinometerEntity selectByPeriodAndObject(long objectId, long periodId){
        return baseMapper.selectByPeriodAndObject(objectId, periodId);
    }

}