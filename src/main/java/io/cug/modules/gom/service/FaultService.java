
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.DeviceActivityEntity;
import io.cug.modules.gom.entity.FaultEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FaultService extends IService<FaultEntity> {

    List<FaultEntity> selectFaultByStatusType(Long projectId,int status,int type);

    List<FaultEntity> selectAllFault(Long projectId);

    List<FaultEntity> selectFaultByStatus( Long projectId,int status);

    List<FaultEntity> selectFaultByType( Long projectId,int type);
}

