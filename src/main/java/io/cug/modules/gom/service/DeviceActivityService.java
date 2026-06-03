
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.AlarmEntity;
import io.cug.modules.gom.entity.DeviceActivityEntity;

import java.util.List;

public interface DeviceActivityService extends IService<DeviceActivityEntity> {

    List<DeviceActivityEntity> selectByDeviceId(Long deviceId);
}

