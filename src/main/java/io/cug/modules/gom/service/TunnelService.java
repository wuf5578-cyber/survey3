
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.TunnelEntity;

import java.util.List;

public interface TunnelService extends IService<TunnelEntity> {
    List<TunnelEntity> SelectAll(long projectId);

}

