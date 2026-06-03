
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.AutoCoorEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AutoCoorService extends IService<AutoCoorEntity> {

    List<AutoCoorEntity> QueryValidGivenPoint(Long projectId);

    List<AutoCoorEntity> QueryValidCoorByObjectId(Long objectId);

    List<AutoCoorEntity> QueryValidCoorByPeriodId(Long periodId);

    AutoCoorEntity QueryValidCoorByPeriodIdAndObjectId(Long periodId,Long objectId);

    AutoCoorEntity updateByObjectId( Long periodId, Long objectId);

}

