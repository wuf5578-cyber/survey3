
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.AutoLearnEntity;
import io.cug.modules.gom.entity.AutoStationEntity;
import io.cug.modules.gom.entity.ObjectEntity;

import java.util.List;

public interface AutoLearnService extends IService<AutoLearnEntity> {

    List<AutoLearnEntity> QueryValidLearn(long projectId);

    List<ObjectEntity> QueryObject(long projectId);

}

