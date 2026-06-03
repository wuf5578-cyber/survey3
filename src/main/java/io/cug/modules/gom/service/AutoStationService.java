
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.AutoStationEntity;
import io.cug.modules.gom.entity.DepartmentEntity;

import java.util.List;

public interface AutoStationService extends IService<AutoStationEntity> {

    List<AutoStationEntity> QueryValidStation(long projectId);

}

