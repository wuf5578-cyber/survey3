
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.AlarmDao;
import io.cug.modules.gom.dao.AliIOTDao;
import io.cug.modules.gom.entity.AlarmEntity;
import io.cug.modules.gom.entity.AliIOTEntity;
import io.cug.modules.gom.service.AlarmService;
import io.cug.modules.gom.service.AliIOTService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("aliIOTService")
public class AliIOTServiceImpl extends ServiceImpl<AliIOTDao, AliIOTEntity> implements AliIOTService {

    @Override
    public List<AliIOTEntity> selectValidIOTByType(Long projectId, int type){

        //数据查询
        List<AliIOTEntity> list = baseMapper.selectValidIOTByType(projectId,type);

        return list;
    }

    @Override
    public AliIOTEntity getByDeviceId(Long deviceId){
        return baseMapper.getByDeviceId(deviceId);
    }

}