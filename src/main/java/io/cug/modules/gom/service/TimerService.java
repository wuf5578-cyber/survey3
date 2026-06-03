
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.TimerEntity;

public interface TimerService extends IService<TimerEntity> {

    TimerEntity selectTimerByDeviceId(long deviceId);
}


