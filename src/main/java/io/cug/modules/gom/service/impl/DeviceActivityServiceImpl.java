
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.AlarmDao;
import io.cug.modules.gom.dao.DeviceActivityDao;
import io.cug.modules.gom.entity.AlarmEntity;
import io.cug.modules.gom.entity.DeviceActivityEntity;
import io.cug.modules.gom.service.AlarmService;
import io.cug.modules.gom.service.DeviceActivityService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("deviceActivityService")
public class DeviceActivityServiceImpl extends ServiceImpl<DeviceActivityDao, DeviceActivityEntity> implements DeviceActivityService {

    @Override
    public List<DeviceActivityEntity> selectByDeviceId(Long deviceId){
        return baseMapper.selectByDeviceId(deviceId);
    }

}