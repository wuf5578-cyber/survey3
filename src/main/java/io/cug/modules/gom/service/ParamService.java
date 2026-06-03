
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.AutoLearnEntity;
import io.cug.modules.gom.entity.ObjectEntity;
import io.cug.modules.gom.entity.ParamEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ParamService extends IService<ParamEntity> {

    List<ParamEntity> selectValidParamsBySurveyType(int surveyType);

    ParamEntity getByObjectId(Long objectId);

}

