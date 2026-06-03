package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.LoopObjectEntity;

import java.util.Date;
import java.util.List;

public interface LoopObjectService extends IService<LoopObjectEntity> {
    List<LoopObjectEntity> SelectByObjectId(long objectId);

    List<LoopObjectEntity> SelectAll(long projectId);

    List<LoopObjectEntity> SelectByPeriod(long objectId);
    List<LoopObjectEntity> SelectDisplacement(long loopId, int objectType, Date startTime, Date endTime);

    List<LoopObjectEntity> SelectByObjectType(long loopId, int objectType);

    List<LoopObjectEntity> SelectByPeriodId(long loopId, long periodId);

    List<LoopObjectEntity> SelectFirstData(long objectId);

    List<LoopObjectEntity> SelectByPeriodIdAndObjectId(long objectType, long periodId);
}
