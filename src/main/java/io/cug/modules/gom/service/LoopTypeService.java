
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.LoopTypeEntity;

import java.util.List;

public interface LoopTypeService extends IService<LoopTypeEntity> {
    List<LoopTypeEntity> SelectAll(long objectId);

    void InsertObjectType(LoopTypeEntity loopTypeEntity);
}

