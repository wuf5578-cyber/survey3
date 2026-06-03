
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;


@Mapper
public interface ExtendDao extends BaseMapper<ExtendEntity> {

    @Select("select * from gom_survey_extend where status<99 and object_id=#{objectId}")
    List<ExtendEntity> QueryValidExtend(@Param("objectId") long objectId);

    @Select("select * from gom_survey_extend where status<99 and object_id=#{objectId} and period_id=#{periodId}")
    ExtendEntity QueryValidExtendEntityByPeriodId(@Param("objectId") long objectId,@Param("periodId") long periodId);

    @Select("Select extend.*,period.name periodName,period.begin,period.end " +
            "from gom_survey_extend extend LEFT JOIN gom_period period on extend.period_id=period.id " +
            "WHERE object_id=#{objectId} and extend.status<99 and period.status<99 ORDER BY begin")
    List<ExtendEntity> QueryExtendWithPeriod(@Param("objectId") long objectId);

    @Select("SELECT DISTINCT gom_period.* " +
            "FROM gom_period " +
            "JOIN gom_survey_extend ON gom_period.id = gom_survey_extend.period_id and gom_period.project_id=#{projectId} and gom_survey_extend.status<99 " +
            "ORDER BY gom_period.begin")
    List<PeriodEntity> QueryAllPeriodsByProjectId(@Param("projectId") long projectId);

    @Select("select gom_survey_extend.* ,gom_object.name objectName from gom_survey_extend " +
            "JOIN gom_object ON gom_object.id = gom_survey_extend.object_id " +
            "where period_id = #{periodId} and gom_survey_extend.status<99 ")
    List<ExtendEntity> QueryExtendWithObjectName(@Param("periodId") long periodId);

    @Select("SELECT gse.*,gom_period.begin AS begin " +
            "FROM gom_survey_extend gse " +
            "JOIN gom_period ON gse.period_id = gom_period.id " +
            "WHERE gse.object_id = #{objectId} and gse.status<99 AND gom_period.begin > #{begin}")
    List<ExtendEntity> QueryExtendGreaterThanBegin(@Param("objectId") long objectId,@Param("begin") Date begin);

}
