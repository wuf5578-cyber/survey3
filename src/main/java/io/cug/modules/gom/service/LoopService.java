
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.LoopEntity;

import java.util.List;

public interface LoopService extends IService<LoopEntity> {
    List<LoopEntity> SelectAll(long projectId);

    List<LoopEntity> SelectByLoopName(long projectId, long loopName);

    List<LoopEntity> SelectLoop(long projectId);
}

