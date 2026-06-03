
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.DisplacementDao;
import io.cug.modules.gom.dao.EarthSubsidenceDao;
import io.cug.modules.gom.entity.*;
import io.cug.modules.gom.service.DisplacementService;
import io.cug.modules.gom.service.EarthSubsidenceService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service("earthSubsidenceService")
public class EarthSubsidenceServiceImpl extends ServiceImpl<EarthSubsidenceDao, EarthSubsidenceEntity> implements EarthSubsidenceService {
    @Override
    public List<EarthSubsidenceEntity> QueryValidEarthSubsidence(long objectId){
        //数据查询
        List<EarthSubsidenceEntity> list = baseMapper.QueryValidEarthSubsidence(objectId);

        return list;
    }
    @Override
    public List<EarthSubsidenceEntity> EarthSubsidenceWithPeriod(long objectId){
        List <EarthSubsidenceEntity> list = baseMapper.EarthSubsidenceWithPeriod(objectId);
        return list;
    }

    @Override
    public EarthSubsidenceEntity QueryValidEarthSubsidenceEntityByPeriodId(long objectId,long periodId){
        return baseMapper.QueryValidEarthSubsidenceEntityByPeriodId(objectId, periodId);
    }

    @Override
    public EarthSubsidenceEntity getFirstData(long objectId){
        return baseMapper.getFirstData(objectId);
    }
    @Override
    public List<PeriodEntity> QueryAllPeriodsByProjectId(long projectId, long type){
        return baseMapper.QueryAllPeriodsByProjectId(projectId, type);
    }
    @Override
    public List<EarthSubsidenceEntity> QueryEarthSubsidenceWithObjectName(long periodId,int type){
        return baseMapper.QueryEarthSubsidenceWithObjectName(periodId,type);
    }

    @Override
    public Boolean QueryEarthSubsidenceGreaterThanBegin(long objectId, Date begin){
        List<EarthSubsidenceEntity> list = baseMapper.QueryEarthSubsidencetGreaterThanBegin(objectId, begin);
        if(list.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public EarthSubsidenceEntity selectByPeriodAndObject(long objectId,long periodId){
        return baseMapper.selectByPeriodAndObject(objectId, periodId);
    }

}