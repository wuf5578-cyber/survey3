
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.DeviceDao;
import io.cug.modules.gom.entity.DeviceEntity;
import io.cug.modules.gom.service.DeviceService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("deviceService")
public class DeviceServiceImpl extends ServiceImpl<DeviceDao, DeviceEntity> implements DeviceService {

    @Override
    public List<DeviceEntity> listValidDevices(long projectId){
        return baseMapper.selectValidDevices(projectId);
    }

    @Override
    public List<DeviceEntity> selectValidPowerDevices(long nProjectID,int type){
        return baseMapper.selectValidPowerDevices(nProjectID,type);
    }

    @Override
    public List<DeviceEntity> selectValidSensor(long nProjectID){
        return baseMapper.selectValidSensor(nProjectID);
    }

    @Override
    public List<DeviceEntity> listValidDevicesByName(long projectId,String name){
        return baseMapper.selectValidDevicesByName(projectId,name);
    }

    @Override
    public List<DeviceEntity> selectValidDevicesByKeyword(long projectId, String strKeyword){
        return baseMapper.selectValidDevicesByKeyword(projectId, strKeyword);
    }

}