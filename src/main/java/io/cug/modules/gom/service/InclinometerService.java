
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface InclinometerService extends IService<InclinometerEntity> {

    List<InclinometerEntity> QueryValidInclinometer(long objectId);
    InclinometerEntity QueryValidInclinometerEntityByPeriodId(long objectId,long periodId);
    List<InclinometerEntity> QueryInclinometerWithPeriod(long objectId);
    InclinometerEntity getFirstData(long objectId);
    List<PeriodEntity> QueryAllPeriodsByProjectId(long projectId);

    List<InclinometerEntity> QueryInclinometerWithObjectName(long periodId);
    Boolean QueryInclinometerGreaterThanBegin(long objectId, Date begin);

    InclinometerEntity selectByPeriodAndObject(long objectId, long periodId);
}

