
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.DisplacementEntity;
import io.cug.modules.gom.entity.PeriodEntity;
import io.cug.modules.gom.entity.PowerEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface DisplacementService extends IService<DisplacementEntity> {

    List<DisplacementEntity> QueryValidDisplacement(long objectId);

    List<DisplacementEntity> QueryDisplacementWithPeriod(long objectId);

    DisplacementEntity getFirstData(long objectId);

    List<PeriodEntity> QueryAllPeriodsByProjectId(long projectId);

    List<DisplacementEntity> QueryDisplacementWithObjectName(long periodId);

    Boolean QueryDisplacementGreaterThanBegin(long objectId,Date begin);

    DisplacementEntity QueryValidDisplacementByPeriodId(long objectId,long periodId);

    DisplacementEntity selectByPeriodAndObject(long objectId, long periodId);
}

