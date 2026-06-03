
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.LoopDao;
import io.cug.modules.gom.entity.LoopEntity;
import io.cug.modules.gom.service.LoopService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("loopService")
public class LoopServiceImpl extends ServiceImpl<LoopDao, LoopEntity> implements LoopService {

    @Override
    public List<LoopEntity> SelectAll(long projectId){
        return baseMapper.SelectAll(projectId);
    }

    @Override
    public List<LoopEntity> SelectByLoopName(long projectId, long loopName){
        return baseMapper.SelectByLoopName(projectId, loopName);
    }

    @Override
    public List<LoopEntity> SelectLoop(long projectId){
        return baseMapper.SelectLoop(projectId);
    }
}