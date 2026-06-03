
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface PowerService extends IService<PowerEntity> {

    List<PowerEntity> QueryValidPower(long objectId);
    PowerEntity QueryValidPowerEntityByPeriodId(long objectId,long periodId);
    List<PowerEntity> QueryPowerWithPeriod(long objectId);
    PowerEntity getFirstData(long objectId);
    List<PeriodEntity> QueryAllPeriodsByProjectId(long projectId, long type);
    List<PowerEntity> QueryPowerWithObjectName(long periodId,int type);
    Boolean QueryPowerGreaterThanBegin(long objectId, Date begin);
    PowerEntity selectPowerByPeriodAndObject(long objectId,long periodId);
}

