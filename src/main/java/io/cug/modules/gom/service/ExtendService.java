
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ExtendService extends IService<ExtendEntity> {

    List<ExtendEntity> QueryValidExtend(long objectId);
    ExtendEntity QueryValidExtendEntityByPeriodId(long objectId,long periodId);
    List<ExtendEntity> QueryExtendWithPeriod(long objectId);
    List<PeriodEntity> QueryAllPeriodsByProjectId(long projectId);

    List<ExtendEntity> QueryExtendWithObjectName(long periodId);

    Boolean QueryExtendGreaterThanBegin(long objectId, Date begin);
}

