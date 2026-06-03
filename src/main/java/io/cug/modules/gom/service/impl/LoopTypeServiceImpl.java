
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.LoopTypeDao;
import io.cug.modules.gom.entity.LoopTypeEntity;
import io.cug.modules.gom.service.LoopTypeService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("loopTypeService")
public class LoopTypeServiceImpl extends ServiceImpl<LoopTypeDao, LoopTypeEntity> implements LoopTypeService {

    @Override
    public List<LoopTypeEntity> SelectAll(long objectId){
        return baseMapper.SelectAll(objectId);
    }

    @Override
    public void InsertObjectType(LoopTypeEntity loopTypeEntity){
        baseMapper.InsertObjectType(
                loopTypeEntity.getProjectId(),
                loopTypeEntity.getLoopId(),
                loopTypeEntity.getType(),
                loopTypeEntity.getObjectId(),
                loopTypeEntity.getStatus()
                );
    }

}