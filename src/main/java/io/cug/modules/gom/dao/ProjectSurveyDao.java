
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.AlarmEntity;
import io.cug.modules.gom.entity.ProjectSurveyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface ProjectSurveyDao extends BaseMapper<ProjectSurveyEntity> {

    @Select("select * from gom_project_survey where status<99 and project_id = #{projectId} order by schedule_time")
    List<ProjectSurveyEntity> selectValidSurvey(@Param("projectId")Long projectId);

}
