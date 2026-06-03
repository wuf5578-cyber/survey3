
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.AlarmEntity;
import io.cug.modules.gom.entity.ProjectSurveyEntity;

import java.util.List;

public interface ProjectSurveyService extends IService<ProjectSurveyEntity> {

    List<ProjectSurveyEntity> selectValidSurvey(Long projectId);
}

