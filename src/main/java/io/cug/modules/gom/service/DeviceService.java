
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.DeviceEntity;

import java.util.List;

public interface DeviceService extends IService<DeviceEntity> {

    List<DeviceEntity> listValidDevices(long projectId);

    List<DeviceEntity> selectValidPowerDevices(long nProjectID,int type);
    List<DeviceEntity> selectValidSensor(long nProjectID);

    List<DeviceEntity> listValidDevicesByName(long projectId,String name);

    List<DeviceEntity> selectValidDevicesByKeyword(long projectId,String strKeyword);

}

