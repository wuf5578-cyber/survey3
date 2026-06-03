
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;


@Mapper
public interface InclinometerDao extends BaseMapper<InclinometerEntity> {

    @Select("select * from gom_survey_inclinometer where status<99 and object_id=#{objectId}")
    List<InclinometerEntity> QueryValidInclinometer(@Param("objectId") long objectId);

    @Select("select * from gom_survey_inclinometer where status<99 and object_id=#{objectId} and period_id=#{periodId}")
    InclinometerEntity QueryValidInclinometerEntityByPeriodId(@Param("objectId") long objectId,@Param("periodId") long periodId);

    @Select("Select incli.*,period.name periodName,period.begin,period.end " +
            "from gom_survey_inclinometer incli LEFT JOIN  gom_period period on incli.period_id=period.id " +
            "WHERE object_id=#{objectId} and incli.status<99 and period.status<99 ORDER BY begin")
    List<InclinometerEntity> QueryInclinometerWithPeriod(@Param("objectId") long objectId);

    @Select("SELECT gom_survey_inclinometer.* from gom_survey_inclinometer " +
            "JOIN gom_period on gom_period.id=gom_survey_inclinometer.period_id " +
            "WHERE gom_survey_inclinometer.object_id=#{objectId} " +
            "ORDER BY gom_period.begin LIMIT 1")
    InclinometerEntity getFirstData(@Param("objectId") long objectId);

    @Select("SELECT DISTINCT gom_period.* " +
            "FROM gom_period " +
            "JOIN gom_survey_inclinometer ON gom_period.id = gom_survey_inclinometer.period_id and gom_period.project_id=#{projectId} and gom_survey_inclinometer.status<99 " +
            "ORDER BY gom_period.begin")
    List<PeriodEntity> QueryAllPeriodsByProjectId(@Param("projectId") long projectId);

    @Select("select gom_survey_inclinometer.* ,gom_object.name objectName from gom_survey_inclinometer " +
            "JOIN gom_object ON gom_object.id = gom_survey_inclinometer.object_id " +
            "where period_id = #{periodId} and gom_survey_inclinometer.status<99 ")
    List<InclinometerEntity> QueryInclinometerWithObjectName(@Param("periodId") long periodId);

    @Select("SELECT gsi.*,gom_period.begin AS begin " +
            "FROM gom_survey_inclinometer gsi " +
            "JOIN gom_period ON gsi.period_id = gom_period.id " +
            "WHERE gsi.object_id = #{objectId} AND gsi.status<99 AND gom_period.begin > #{begin}")
    List<InclinometerEntity> QueryInclinometerGreaterThanBegin(@Param("objectId") long objectId,@Param("begin") Date begin);

    @Select("select * from gom_survey_inclinometer where period_id=#{periodId} and object_id=#{objectId} and status<99 ")
    InclinometerEntity selectByPeriodAndObject(@Param("objectId") long objectId, @Param("periodId") long periodId);
}
