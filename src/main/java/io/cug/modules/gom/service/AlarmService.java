
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.AlarmEntity;
import io.cug.modules.gom.entity.AutoMeasureEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlarmService extends IService<AlarmEntity> {

    List<AlarmEntity> QueryValidAlarm(long projectId);

    List<AlarmEntity> QueryValidAlarmByPeriodId(long projectId,long periodId);

    List<AlarmEntity> QueryNewAlarm(long projectId);

    List<AlarmEntity> selectAlarmByObjectId(long projectId, long objectId, int surveyType);
}

