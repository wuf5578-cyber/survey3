
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.EarthSubsidenceDao;
import io.cug.modules.gom.dao.PowerDao;
import io.cug.modules.gom.entity.*;
import io.cug.modules.gom.service.EarthSubsidenceService;
import io.cug.modules.gom.service.PowerService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service("powerService")
public class PowerServiceImpl extends ServiceImpl<PowerDao, PowerEntity> implements PowerService {
    @Override
    public List<PowerEntity> QueryValidPower(long objectId){
        //数据查询
        List<PowerEntity> list = baseMapper.QueryValidPower(objectId);

        return list;
    }

    @Override
    public List<PowerEntity> QueryPowerWithPeriod(long objectId){
        List<PowerEntity> list = baseMapper.QueryPowerWithPeriod(objectId);
        return list;
    }

    @Override
    public PowerEntity QueryValidPowerEntityByPeriodId(long objectId,long periodId){
        return baseMapper.QueryValidPowerEntityByPeriodId(objectId, periodId);
    }
    @Override
    public PowerEntity getFirstData(long objectId){
        return baseMapper.getFirstData(objectId);
    }
    @Override
    public List<PeriodEntity> QueryAllPeriodsByProjectId(long projectId, long type){
        return baseMapper.QueryAllPeriodsByProjectId(projectId, type);
    }
    @Override
    public List<PowerEntity> QueryPowerWithObjectName(long periodId,int type){
        return baseMapper.QueryPowerWithObjectName(periodId,type);
    }

    @Override
    public Boolean QueryPowerGreaterThanBegin(long objectId, Date begin){
        List<PowerEntity> list = baseMapper.QueryPowerGreaterThanBegin(objectId, begin);
        if(list.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public PowerEntity selectPowerByPeriodAndObject(long objectId,long periodId){
        return baseMapper.selectPowerByPeriodAndObject(objectId, periodId);
    }

}