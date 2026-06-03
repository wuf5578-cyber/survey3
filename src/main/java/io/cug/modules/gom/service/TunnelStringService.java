
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.TunnelStringEntity;

public interface TunnelStringService extends IService<TunnelStringEntity> {
    TunnelStringEntity SelectByStringLoopName(long periodId, long loopName);
}

