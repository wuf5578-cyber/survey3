
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.TimerDao;
import io.cug.modules.gom.entity.TimerEntity;
import io.cug.modules.gom.service.TimerService;
import org.springframework.stereotype.Service;


@Service("timerService")
public class TimerServiceImpl extends ServiceImpl<TimerDao, TimerEntity> implements TimerService {

    @Override
    public TimerEntity selectTimerByDeviceId(long deviceId){
        return baseMapper.selectTimerByDeviceId(deviceId);
    }

}