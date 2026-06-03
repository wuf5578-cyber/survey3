
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.DisParamsEntity;
import io.cug.modules.gom.entity.ParamEntity;

import java.util.List;

public interface DisParamsService extends IService<DisParamsEntity> {

    DisParamsEntity getByObjectId(Long objectId);

}

