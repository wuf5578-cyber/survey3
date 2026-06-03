
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.AlarmDao;
import io.cug.modules.gom.dao.AutoMeasureDao;
import io.cug.modules.gom.entity.AlarmEntity;
import io.cug.modules.gom.entity.AutoMeasureEntity;
import io.cug.modules.gom.service.AlarmService;
import io.cug.modules.gom.service.AutoMeasureService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("alarmService")
public class AlarmServiceImpl extends ServiceImpl<AlarmDao, AlarmEntity> implements AlarmService {

    @Override
    public List<AlarmEntity> QueryValidAlarm(long projectId){

        //数据查询
        List<AlarmEntity> list = baseMapper.QueryValidAlarm(projectId);

        return list;
    }

    @Override
    public List<AlarmEntity> QueryValidAlarmByPeriodId(long projectId,long periodId){

        //数据查询
        List<AlarmEntity> list = baseMapper.QueryValidAlarmByPeriodId(projectId,periodId);

        return list;
    }

    @Override
    public List<AlarmEntity> QueryNewAlarm(long projectId){
        //数据查询
        List<AlarmEntity> list = baseMapper.QueryNewAlarm(projectId);

        return list;
    }

    @Override
    public List<AlarmEntity> selectAlarmByObjectId(long projectId, long objectId, int surveyType){
        return baseMapper.selectAlarmByObjectId(projectId, objectId, surveyType);
    }

}