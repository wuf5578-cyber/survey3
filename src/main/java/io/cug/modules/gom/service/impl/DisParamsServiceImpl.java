
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.DisParamsDao;
import io.cug.modules.gom.entity.DisParamsEntity;
import io.cug.modules.gom.entity.DisplacementEntity;
import io.cug.modules.gom.entity.PowerEntity;
import io.cug.modules.gom.service.DisParamsService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;


@Service("disParamsService")
public class DisParamsServiceImpl extends ServiceImpl<DisParamsDao, DisParamsEntity> implements DisParamsService {

    @Override
    public DisParamsEntity getByObjectId(Long objectId){
        DisParamsEntity disParamsEntity = baseMapper.getByObjectId(objectId);
        return disParamsEntity;
    }
}