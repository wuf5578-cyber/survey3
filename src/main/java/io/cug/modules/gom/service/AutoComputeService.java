
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.AutoComputeEntity;
import io.cug.modules.gom.entity.AutoComputeGroupEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AutoComputeService extends IService<AutoComputeEntity> {

    List<AutoComputeEntity> QueryValidCompute(Long groupId);

    int UpdateComputeByGroupIdAndSurveyIndex(Long stationId, Long groupId, int surveyIndex);

    int DelComputeByGroupIdAndSurveyIndex(Long groupId,int surveyIndex);

}

