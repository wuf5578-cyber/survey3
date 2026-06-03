
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.AutoComputeDao;
import io.cug.modules.gom.dao.AutoComputeGroupDao;
import io.cug.modules.gom.entity.AutoComputeEntity;
import io.cug.modules.gom.entity.AutoComputeGroupEntity;
import io.cug.modules.gom.service.AutoComputeGroupService;
import io.cug.modules.gom.service.AutoComputeService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("autoComputeService")
public class AutoComputeServiceImpl extends ServiceImpl<AutoComputeDao, AutoComputeEntity> implements AutoComputeService {

    @Override
    public List<AutoComputeEntity> QueryValidCompute(Long groupId){
        return baseMapper.QueryValidCompute(groupId);
    }

    @Override
    public int UpdateComputeByGroupIdAndSurveyIndex(Long stationId, Long groupId, int surveyIndex){
        return baseMapper.UpdateComputeByGroupIdAndSurveyIndex(stationId, groupId, surveyIndex);
    }

    @Override
    public int DelComputeByGroupIdAndSurveyIndex(Long groupId,int surveyIndex){
        return baseMapper.DelComputeByGroupIdAndSurveyIndex(groupId,surveyIndex);
    }
}