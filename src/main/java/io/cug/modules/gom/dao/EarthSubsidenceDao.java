
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;


@Mapper
public interface EarthSubsidenceDao extends BaseMapper<EarthSubsidenceEntity> {

    @Select("select * from gom_survey_earth_subsidence where status<99 and object_id=#{objectId}")
    List<EarthSubsidenceEntity> QueryValidEarthSubsidence(@Param("objectId") long objectId);

    @Select("select * from gom_survey_earth_subsidence where status<99 and object_id=#{objectId} and period_id=#{periodId}")
    EarthSubsidenceEntity QueryValidEarthSubsidenceEntityByPeriodId(@Param("objectId") long objectId,@Param("periodId") long periodId);

    @Select("Select earth.*,period.name periodName,period.begin,period.end " +
            "from gom_survey_earth_subsidence earth LEFT JOIN  gom_period period on earth.period_id=period.id " +
            "WHERE object_id=#{objectId} and earth.status<99 and period.status<99 ORDER BY begin")
    List<EarthSubsidenceEntity> EarthSubsidenceWithPeriod(@Param("objectId") long objectId);

    @Select("SELECT gom_survey_earth_subsidence.* from gom_survey_earth_subsidence " +
            "JOIN gom_period on gom_period.id=gom_survey_earth_subsidence.period_id " +
            "WHERE gom_survey_earth_subsidence.object_id=#{objectId} " +
            "ORDER BY gom_period.begin LIMIT 1")
    EarthSubsidenceEntity getFirstData(@Param("objectId") long objectId);

    @Select("SELECT DISTINCT gom_period.* " +
            "FROM gom_period " +
            "JOIN gom_survey_earth_subsidence ON gom_period.id = gom_survey_earth_subsidence.period_id and gom_period.project_id=#{projectId} and gom_survey_earth_subsidence.status<99 and gom_survey_earth_subsidence.type=#{type} " +
            "ORDER BY gom_period.begin")
    List<PeriodEntity> QueryAllPeriodsByProjectId(@Param("projectId") long projectId,@Param("type") long type);

    @Select("select gom_survey_earth_subsidence.* ,gom_object.name objectName from gom_survey_earth_subsidence " +
            "JOIN gom_object ON gom_object.id = gom_survey_earth_subsidence.object_id " +
            "where period_id = #{periodId} and gom_survey_earth_subsidence.status<99 AND (#{type} = -1 OR gom_survey_earth_subsidence.type = #{type})")
    List<EarthSubsidenceEntity> QueryEarthSubsidenceWithObjectName(@Param("periodId") long periodId,@Param("type") int type);

    @Select("SELECT gses.*,gom_period.begin AS begin " +
            "FROM gom_survey_earth_subsidence gses " +
            "JOIN gom_period ON gses.period_id = gom_period.id " +
            "WHERE gses.object_id = #{objectId} AND gses.status<99 AND gom_period.begin > #{begin} ")
    List<EarthSubsidenceEntity> QueryEarthSubsidencetGreaterThanBegin(@Param("objectId") long objectId,@Param("begin") Date begin);

    @Select("select * from gom_survey_earth_subsidence where period_id=#{periodId} and object_id=#{objectId} and status<99 ")
    EarthSubsidenceEntity selectByPeriodAndObject(@Param("objectId") long objectId, @Param("periodId") long periodId);
}
