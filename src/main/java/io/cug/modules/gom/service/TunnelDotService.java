package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.TunnelDotEntity;

public interface TunnelDotService extends IService<TunnelDotEntity> {
    TunnelDotEntity SelectDotByObjectId(long projectId, long objectId, long periodId);
}
