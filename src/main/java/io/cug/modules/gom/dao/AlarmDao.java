
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.AlarmEntity;
import io.cug.modules.gom.entity.AutoStationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface AlarmDao extends BaseMapper<AlarmEntity> {

    @Select("select gom_alarm.*,gom_object.name as object_name,gom_period.begin as period_begin " +
            "from gom_alarm " +
            "JOIN gom_object on gom_object.id = gom_alarm.object_id " +
            "Join gom_period on gom_period.id = gom_alarm.period_id " +
            "where gom_alarm.status<99 and gom_alarm.project_id=#{projectId} and gom_alarm.period_id=#{periodId} ")
    List<AlarmEntity> QueryValidAlarmByPeriodId(@Param("projectId") long projectId,@Param("periodId") long periodId);

    @Select("select gom_alarm.*,gom_object.name as object_name,gom_period.begin as period_begin " +
            "from gom_alarm " +
            "JOIN gom_object on gom_object.id = gom_alarm.object_id " +
            "Join gom_period on gom_period.id = gom_alarm.period_id " +
            "where gom_alarm.status<99 and gom_alarm.project_id=#{projectId} ")
    List<AlarmEntity> QueryValidAlarm(@Param("projectId") long projectId);

    @Select("SELECT ga.*,go.name as object_name,gp.begin as period_begin " +
            "FROM gom_alarm ga " +
            "JOIN gom_object go ON ga.object_id = go.id " +
            "JOIN gom_period gp ON ga.period_id = gp.id " +
            "WHERE ga.project_id = #{projectId} and ga.status < 99 " +
            "AND (ga.object_id, gp.begin) " +
            "IN (SELECT object_id, MAX(begin) AS max_begin " +
            "FROM gom_alarm " +
            "JOIN gom_period ON gom_alarm.period_id = gom_period.id " +
            "WHERE ga.project_id = #{projectId} " +
            "GROUP BY object_id " +
            ");")
    List<AlarmEntity> QueryNewAlarm(@Param("projectId") long projectId);

    @Select("select * from gom_alarm where status<99 and project_id=#{projectId} and object_id=#{objectId} and survey_type=#{surveyType}")
    List<AlarmEntity> selectAlarmByObjectId(@Param("projectId") long projectId,@Param("objectId") long objectId,@Param("surveyType") int surveyType);

}
