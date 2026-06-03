
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.ObjectEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface ObjectDao extends BaseMapper<ObjectEntity> {

    @Select("select * from gom_object where status<99 and project_id=#{nProjectID}")
    List<ObjectEntity> QueryValidObject(@Param("nProjectID") Long nProjectID);

    @Select("select * from gom_object where status<99 and project_id=#{nProjectID} and type=1 ")
    List<ObjectEntity> QueryValidSurveyObject(@Param("nProjectID") Long nProjectID);

    @Select("select * from gom_object where status<99 and project_id=#{projectId} and parent_id=637 ")
    List<ObjectEntity> SelectTunnelObject(@Param("projectId") Long projectId);

    @Select("select * from gom_object where status<99 and id=#{id}")
    ObjectEntity QueryObjectById(@Param("id") Long id);

    @Select("select * from gom_object where status<99 and project_id=#{nProjectID} and name=#{name}")
    ObjectEntity QueryValidObjectByName(@Param("nProjectID") Long nProjectID,@Param("name") String name);

    @Select("select object.* from gom_object object " +
            "left join gom_point_link_survey point on object.id = point.object_id " +
            "where object.project_id=#{projectId} and object.`status`<99 and point.survey_type=#{surveyType}")
    List<ObjectEntity> SelectObjectWithWarning(@Param("projectId") Long projectId,@Param("surveyType") long surveyType);

    @Select("SELECT object.*, " +
            "COALESCE(warning.warning_a1, pw.warning_a1, NULL) AS warning_a1, " +
            "COALESCE(warning.warning_a2, pw.warning_a2, NULL) AS warning_a2, " +
            "COALESCE(warning.warning_b1, pw.warning_b1, NULL) AS warning_b1, " +
            "COALESCE(warning.warning_b2, pw.warning_b2, NULL) AS warning_b2 " +
            "FROM gom_object object " +
            "LEFT JOIN gom_object_early_warning warning ON object.id = warning.object_id AND warning.survey_type = #{surveyType} " +
            "LEFT JOIN gom_object parent ON object.parent_id = parent.id " +
            "LEFT JOIN gom_object_early_warning pw ON parent.id = pw.object_id AND pw.survey_type = #{surveyType} " +
            "WHERE object.id =#{objectId}")
    ObjectEntity SelectObjectWithWarningById(@Param("objectId") Long objectId,@Param("surveyType") long surveyType);
}
