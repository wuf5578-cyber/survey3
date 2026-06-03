
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;


@Mapper
public interface PeriodDao extends BaseMapper<PeriodEntity> {

    @Select("select * from gom_period where status<99 and name like CONCAT('%',#{strKeyword},'%') and project_id=#{nProjectID}")
    List<PeriodEntity> selectValidPeriodsByName(@Param("nProjectID") long nProjectID,@Param("strKeyword") String strKeyword);

    @Select("select * from gom_period where status<99 and project_id=#{nProjectID} ORDER BY begin")
    List<PeriodEntity> selectValidPeriods(@Param("nProjectID") long nProjectID);

    @Select("select * from gom_period where status<99 and project_id=#{nProjectID} and begin=#{begin}")
    PeriodEntity selectValidPeriodsByBegin(@Param("nProjectID") long nProjectID,@Param("begin") Date begin);

    @Select("select * from gom_period where status<99 and project_id=#{nProjectID} ORDER BY begin DESC LIMIT 1")
    PeriodEntity selectNewPeriod(@Param("nProjectID") long nProjectID);

    @Select("SELECT DISTINCT gsd.period_id, gp.name as period_name, gp.begin as begin FROM gom_survey_displacement gsd JOIN gom_period gp ON gsd.period_id = gp.id " +
            "where gsd.status<99 and gp.project_id = #{projectId}")
    List<DisplacementEntity> selectDisplacementPeriod(@Param("projectId") Long projectId);

    @Select("SELECT DISTINCT gsd.period_id, gp.name as period_name, gp.begin as begin FROM gom_survey_earth_subsidence gsd JOIN gom_period gp ON gsd.period_id = gp.id " +
            "where gsd.status<99 and gp.project_id = #{projectId} and gsd.type = #{type}")
    List<EarthSubsidenceEntity> selectEarthSubsidencePeriod(@Param("projectId") Long projectId,@Param("type") int type);

    @Select("SELECT DISTINCT gsd.period_id, gp.name as period_name, gp.begin as begin FROM gom_survey_power gsd JOIN gom_period gp ON gsd.period_id = gp.id " +
            "where gsd.status<99 and gp.project_id = #{projectId} and gsd.type = #{type}")
    List<PowerEntity> selectPowerPeriod(@Param("projectId") Long projectId,@Param("type") int type);

    @Select("SELECT DISTINCT gsd.period_id, gp.name as period_name, gp.begin as begin FROM gom_survey_inclinometer gsd JOIN gom_period gp ON gsd.period_id = gp.id " +
            "where gsd.status<99 and gp.project_id = #{projectId} ")
    List<InclinometerEntity> selectInclinometerPeriod(@Param("projectId") Long projectId);

    @Select("SELECT DISTINCT gsd.period_id, gp.name as period_name, gp.begin as begin FROM gom_survey_extend gsd JOIN gom_period gp ON gsd.period_id = gp.id " +
            "where gsd.status<99 and gp.project_id = #{projectId} ")
    List<ExtendEntity> selectExtendPeriod(@Param("projectId") Long projectId);
}
