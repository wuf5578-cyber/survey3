
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;


@Mapper
public interface PowerDao extends BaseMapper<PowerEntity> {

    @Select("select * from gom_survey_power where status<99 and object_id=#{objectId}")
    List<PowerEntity> QueryValidPower(@Param("objectId") long objectId);

    @Select("select * from gom_survey_power where status<99 and object_id=#{objectId} and period_id=#{periodId}")
    PowerEntity QueryValidPowerEntityByPeriodId(@Param("objectId") long objectId,@Param("periodId") long periodId);

    @Select("Select pow.*,period.name periodName,period.begin,period.end " +
            "from gom_survey_power pow LEFT JOIN  gom_period period on pow.period_id=period.id " +
            "WHERE object_id=#{objectId} and pow.status<99 and period.status<99 ORDER BY begin")
    List<PowerEntity> QueryPowerWithPeriod(@Param("objectId") long objectId);

    @Select("SELECT gom_survey_power.* from gom_survey_power " +
            "JOIN gom_period on gom_period.id=gom_survey_power.period_id " +
            "WHERE gom_survey_power.object_id=#{objectId} " +
            "ORDER BY gom_period.begin LIMIT 1")
    PowerEntity getFirstData(@Param("objectId") long objectId);

    @Select("SELECT DISTINCT gom_period.* " +
            "FROM gom_period " +
            "JOIN gom_survey_power ON gom_period.id = gom_survey_power.period_id and gom_period.project_id=#{projectId} and gom_survey_power.status<99 and gom_survey_power.type=#{type} " +
            "ORDER BY gom_period.begin")
    List<PeriodEntity> QueryAllPeriodsByProjectId(@Param("projectId") long projectId, @Param("type") long type);

    @Select("select gom_survey_power.* ,gom_object.name objectName from gom_survey_power " +
            "JOIN gom_object ON gom_object.id = gom_survey_power.object_id " +
            "where period_id = #{periodId} and gom_survey_power.status<99 AND (#{type} = -1 OR gom_survey_power.type = #{type})")
    List<PowerEntity> QueryPowerWithObjectName(@Param("periodId") long periodId,@Param("type") int type);

    @Select("SELECT gsp.*,gom_period.begin AS begin " +
            "FROM gom_survey_power gsp " +
            "JOIN gom_period ON gsp.period_id = gom_period.id " +
            "WHERE gsp.object_id = #{objectId} AND gsp.status<99 AND gom_period.begin > #{begin}")
    List<PowerEntity> QueryPowerGreaterThanBegin(@Param("objectId") long objectId,@Param("begin") Date begin);

    @Select("select * from gom_survey_power where period_id=#{periodId} and object_id=#{objectId} and status<99 ")
    PowerEntity selectPowerByPeriodAndObject(@Param("objectId") long objectId,@Param("periodId") long periodId);
}
