
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.AlarmEntity;
import io.cug.modules.gom.entity.AliIOTEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AliIOTService extends IService<AliIOTEntity> {

    List<AliIOTEntity> selectValidIOTByType(Long projectId, int type);

    AliIOTEntity getByDeviceId(Long deviceId);
}


