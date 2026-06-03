
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.AutoComputeGroupDao;
import io.cug.modules.gom.dao.AutoLearnDao;
import io.cug.modules.gom.entity.AutoComputeGroupEntity;
import io.cug.modules.gom.entity.AutoLearnEntity;
import io.cug.modules.gom.entity.ObjectEntity;
import io.cug.modules.gom.service.AutoComputeGroupService;
import io.cug.modules.gom.service.AutoLearnService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("autoComputeGroupService")
public class AutoComputeGroupServiceImpl extends ServiceImpl<AutoComputeGroupDao, AutoComputeGroupEntity> implements AutoComputeGroupService {

    @Override
    public List<AutoComputeGroupEntity> QueryValidComputeGroup(Long projectId){
        return baseMapper.QueryValidComputeGroup(projectId);
    }
}