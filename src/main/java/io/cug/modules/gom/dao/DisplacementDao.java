
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.DisplacementEntity;
import io.cug.modules.gom.entity.PeriodEntity;
import io.cug.modules.gom.entity.PowerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;


@Mapper
public interface DisplacementDao extends BaseMapper<DisplacementEntity> {

    @Select("select * from gom_survey_displacement where status<99 and object_id=#{objectId}")
    List<DisplacementEntity> QueryValidDisplacement(@Param("objectId") long objectId);

    @Select("select * from gom_survey_displacement where status<99 and object_id=#{objectId} and period_id=#{periodId}")
    DisplacementEntity QueryValidDisplacementByPeriodId(@Param("objectId") long objectId,@Param("periodId") long periodId);

    @Select("Select dis.*,period.name periodName,period.begin,period.end " +
            "from gom_survey_displacement dis LEFT JOIN  gom_period period on dis.period_id=period.id " +
            "WHERE object_id=#{objectId} and dis.status<99 and period.status<99 ORDER BY begin")
    List<DisplacementEntity> QueryDisplacementWithPeriod(@Param("objectId") long objectId);

    @Select("SELECT gom_survey_displacement.* from gom_survey_displacement " +
            "JOIN gom_period on gom_period.id=gom_survey_displacement.period_id " +
            "WHERE gom_survey_displacement.object_id=#{objectId} " +
            "ORDER BY gom_period.begin LIMIT 1")
    DisplacementEntity getFirstData(@Param("objectId") long objectId);

    @Select("SELECT DISTINCT gom_period.* " +
            "FROM gom_period " +
            "JOIN gom_survey_displacement ON gom_period.id = gom_survey_displacement.period_id and gom_period.project_id=#{projectId} and gom_survey_displacement.status<99 " +
            "ORDER BY gom_period.begin")
    List<PeriodEntity> QueryAllPeriodsByProjectId(@Param("projectId") long projectId);

    @Select("select gom_survey_displacement.* ,gom_object.name objectName from gom_survey_displacement " +
            "JOIN gom_object ON gom_object.id = gom_survey_displacement.object_id " +
            "where period_id = #{periodId} and gom_survey_displacement.status<99 ")
    List<DisplacementEntity> QueryDisplacementWithObjectName(@Param("periodId") long periodId);

    @Select("SELECT gsd.*,gom_period.begin AS begin " +
            "FROM gom_survey_displacement gsd " +
            "JOIN gom_period ON gsd.period_id = gom_period.id " +
            "WHERE gsd.object_id = #{objectId} AND gsd.status<99 AND gom_period.begin > #{begin};")
    List<DisplacementEntity> QueryDisplacementGreaterThanBegin(@Param("objectId") long objectId,@Param("begin") Date begin);

    @Select("select * from gom_survey_displacement where period_id=#{periodId} and object_id=#{objectId} and status<99 ")
    DisplacementEntity selectByPeriodAndObject(@Param("objectId") long objectId, @Param("periodId") long periodId);

}
