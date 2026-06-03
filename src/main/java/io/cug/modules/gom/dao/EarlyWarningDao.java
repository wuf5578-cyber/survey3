
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.DeviceEntity;
import io.cug.modules.gom.entity.EarlyWarningEntity;
import io.cug.modules.gom.entity.PointEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface EarlyWarningDao extends BaseMapper<EarlyWarningEntity> {

    @Select("select * from gom_object_early_warning where status<99")
    List<EarlyWarningEntity> selectAllValidEarlyWarning();

    @Select("select * from gom_object_early_warning where status<99 and object_id=#{objectId} and survey_type=#{surveyType}")
    EarlyWarningEntity selectValidEarlyWarningByObjectId(@Param("objectId") long objectId,@Param("surveyType") long surveyType);

    @Insert("INSERT INTO gom_object_early_warning (project_id,object_id, survey_type, warning_a1, warning_a2, warning_b1, warning_b2,status) " +
            "VALUES (#{projectId}, #{objectId}, #{surveyType}, #{warningA1},#{warningA2},#{warningB1},#{warningB2},#{status}) " +
            "ON DUPLICATE KEY UPDATE warning_a1 = #{warningA1},warning_a2 = #{warningA2},warning_b1 = #{warningB1},warning_b2 = #{warningB2},status=#{status};")
    void InsertOrUpdateWarning(@Param("projectId")long projectId,@Param("objectId")long objectId,@Param("surveyType")long surveyType,@Param("warningA1")double warningA1,@Param("warningA2")double warningA2,@Param("warningB1")double warningB1,@Param("warningB2")double warningB2,@Param("status")long status);

    @Select("select warning.* from gom_object_early_warning warning where warning.project_id = #{projectId} and warning.survey_type=#{surveyType} and warning.type=1 ")
    EarlyWarningEntity selectDefaultEarlyWarningBySurveyType(@Param("projectId") long projectId,@Param("surveyType") long surveyType);

    @Select("select * from gom_object_early_warning where object_id = #{objectId} and status<99 and survey_type=#{surveyType} and project_id=#{projectId}")
    EarlyWarningEntity selectByObjectId(@Param("projectId") long projectId,@Param("objectId") long objectId,@Param("surveyType") int surveyType);
}
