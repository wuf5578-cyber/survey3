
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.DeviceActivityDao;
import io.cug.modules.gom.dao.FaultDao;
import io.cug.modules.gom.entity.DeviceActivityEntity;
import io.cug.modules.gom.entity.FaultEntity;
import io.cug.modules.gom.service.DeviceActivityService;
import io.cug.modules.gom.service.FaultService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("faultService")
public class FaultServiceImpl extends ServiceImpl<FaultDao, FaultEntity> implements FaultService {
    @Override
    public List<FaultEntity> selectFaultByStatusType(Long projectId,int status,int type){
        return baseMapper.selectFaultByStatusType(projectId, status, type);
    }

    @Override
    public List<FaultEntity> selectAllFault(Long projectId){
        return baseMapper.selectAllFault(projectId);
    }

    @Override
    public List<FaultEntity> selectFaultByStatus( Long projectId,int status){
        return baseMapper.selectFaultByStatus(projectId, status);
    }
    @Override
    public List<FaultEntity> selectFaultByType( Long projectId,int type){
        return baseMapper.selectFaultByType(projectId, type);
    }

}