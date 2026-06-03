
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.ObjectEntity;
import io.cug.modules.gom.entity.PointEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.awt.*;
import java.util.List;


@Mapper
public interface PointDao extends BaseMapper<PointEntity> {

    @Select("select * from gom_point_link_survey where status<99 and project_id=#{nProjectID}")
    List<PointEntity> selectValidPoints(@Param("nProjectID") long nProjectID);

    @Select("select * from gom_point_link_survey where status<99 and object_id=#{nObjectID}")
    PointEntity selectByObjectId(@Param("nObjectID") long nObjectID);

    @Select("select * from gom_point_link_survey where object_id=#{objectId} and survey_type=#{surveyType}")
    List<PointEntity> IsExist(@Param("objectId") long objectId,@Param("surveyType") long surveyType);

    @Select("SELECT object.* " +
            "FROM gom_object object " +
            "INNER JOIN gom_point_link_survey point " +
            "ON object.id = point.object_id " +
            "WHERE object.status<99 and point.status<99 and point.project_id=#{projectId} and point.survey_type=#{surveyType}")
    List<ObjectEntity> selectObjectBySurveyType(@Param("projectId") long projectId,@Param("surveyType") long surveyType);

    @Select("select * from gom_point_link_survey where project_id=#{projectId} and survey_type=#{surveyType} and status<99")
    List<PointEntity> getObjectNum(@Param("projectId")Long projectId,@Param("surveyType")int surveyType);
}
