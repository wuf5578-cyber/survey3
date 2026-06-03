
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.EarlyWarningEntity;
import io.cug.modules.gom.entity.WarningStandardEntity;

import java.util.List;

public interface WarningStandardService extends IService<WarningStandardEntity> {

    List<WarningStandardEntity> selectWarningStandardBySurveyType(long surveyType);

}

