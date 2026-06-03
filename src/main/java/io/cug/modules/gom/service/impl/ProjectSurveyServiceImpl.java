
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.AlarmDao;
import io.cug.modules.gom.dao.ProjectSurveyDao;
import io.cug.modules.gom.entity.AlarmEntity;
import io.cug.modules.gom.entity.ProjectSurveyEntity;
import io.cug.modules.gom.service.AlarmService;
import io.cug.modules.gom.service.ProjectSurveyService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("projectSurveyService")
public class ProjectSurveyServiceImpl extends ServiceImpl<ProjectSurveyDao, ProjectSurveyEntity> implements ProjectSurveyService {

    @Override
    public List<ProjectSurveyEntity> selectValidSurvey(Long projectId){

        return baseMapper.selectValidSurvey(projectId);
    }


}