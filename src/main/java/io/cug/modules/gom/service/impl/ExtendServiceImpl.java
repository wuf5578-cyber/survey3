
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.ExtendDao;
import io.cug.modules.gom.dao.InclinometerDao;
import io.cug.modules.gom.entity.*;
import io.cug.modules.gom.service.ExtendService;
import io.cug.modules.gom.service.InclinometerService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service("extendService")
public class ExtendServiceImpl extends ServiceImpl<ExtendDao, ExtendEntity> implements ExtendService {
    @Override
    public List<ExtendEntity> QueryValidExtend(long objectId){
        //数据查询
        List<ExtendEntity> list = baseMapper.QueryValidExtend(objectId);

        return list;
    }

    @Override
    public List<ExtendEntity> QueryExtendWithPeriod(long objectId){
        List<ExtendEntity> list = baseMapper.QueryExtendWithPeriod(objectId);
        return list;
    }
    @Override
    public ExtendEntity QueryValidExtendEntityByPeriodId(long objectId,long periodId){
        return baseMapper.QueryValidExtendEntityByPeriodId(objectId, periodId);
    }
    @Override
    public List<PeriodEntity> QueryAllPeriodsByProjectId(long projectId){
        return baseMapper.QueryAllPeriodsByProjectId(projectId);
    }

    @Override
    public List<ExtendEntity> QueryExtendWithObjectName(long periodId){
        return baseMapper.QueryExtendWithObjectName(periodId);
    }

    @Override
    public Boolean QueryExtendGreaterThanBegin(long objectId, Date begin){
        List<ExtendEntity> list = baseMapper.QueryExtendGreaterThanBegin(objectId, begin);
        if(list.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

}