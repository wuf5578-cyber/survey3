
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.AutoComputeGroupEntity;
import io.cug.modules.gom.entity.AutoLearnEntity;
import io.cug.modules.gom.entity.ObjectEntity;

import java.util.List;

public interface AutoComputeGroupService extends IService<AutoComputeGroupEntity> {

    List<AutoComputeGroupEntity> QueryValidComputeGroup(Long projectId);


}

