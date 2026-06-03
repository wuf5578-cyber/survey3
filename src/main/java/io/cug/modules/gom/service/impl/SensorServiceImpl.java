
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.SensorDao;
import io.cug.modules.gom.entity.SensorEntity;
import io.cug.modules.gom.service.SensorService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service("THService")
public class SensorServiceImpl extends ServiceImpl<SensorDao, SensorEntity> implements SensorService {

    @Override
    public SensorEntity QueryNewestTH(long deviceId){
        return baseMapper.QueryNewestTH(deviceId);
    }

    @Override
    public List<SensorEntity> QueryDefaultTH(long deviceId){
        return baseMapper.QueryDefaultTH(deviceId);
    }

    public List<SensorEntity> QueryTHWithTime(long deviceId, Date startTime, Date endTime){
        return baseMapper.QueryTHWithTime(deviceId,startTime,endTime);
    }

}