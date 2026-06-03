
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.TunnelVaryEntity;

import java.util.Date;
import java.util.List;

public interface TunnelVaryService extends IService<TunnelVaryEntity> {
    List<TunnelVaryEntity> SelectByPeriodId(long projectId, long loopId, long periodId);

    List<TunnelVaryEntity> SelectAnalyseData(long loopId, Date startTime, Date endTime);
}

