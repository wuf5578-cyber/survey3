
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.SensorEntity;

import java.util.Date;
import java.util.List;

public interface SensorService extends IService<SensorEntity> {

    SensorEntity QueryNewestTH(long deviceId);

    List<SensorEntity> QueryDefaultTH(long deviceId);

    List<SensorEntity> QueryTHWithTime(long deviceId, Date startTime, Date endTime);

}


