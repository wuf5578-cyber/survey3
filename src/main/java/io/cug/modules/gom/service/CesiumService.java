
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.CesiumEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CesiumService extends IService<CesiumEntity> {

    List<CesiumEntity> QueryValidCesium(long projectId);

    CesiumEntity QueryCesiumByPointId(long projectId, String pointId);

}

