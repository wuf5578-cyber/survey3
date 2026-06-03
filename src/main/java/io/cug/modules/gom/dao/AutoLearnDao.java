
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.AutoLearnEntity;
import io.cug.modules.gom.entity.ObjectEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface AutoLearnDao extends BaseMapper<AutoLearnEntity> {

    @Select("SELECT gom_auto_learn.*, gom_object.name " +
            "FROM gom_auto_learn " +
            "INNER JOIN gom_object ON gom_auto_learn.object_id = gom_object.id " +
            "WHERE gom_auto_learn.status < 99 AND gom_auto_learn.station_id = #{stationId} ")
    List<AutoLearnEntity> QueryValidLearn(@Param("stationId") long stationId);

    @Select("SELECT object.* " +
            "FROM gom_object object " +
            "INNER JOIN gom_point_link_survey point " +
            "ON object.id = point.object_id " +
            "WHERE object.status<99 and point.status<99 and point.project_id=#{projectId} and point.survey_type=13")
    List<ObjectEntity> QueryObject(@Param("projectId") long projectId);

}
