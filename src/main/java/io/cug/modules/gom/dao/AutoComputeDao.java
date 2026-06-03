
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.AutoComputeEntity;
import io.cug.modules.gom.entity.AutoComputeGroupEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface AutoComputeDao extends BaseMapper<AutoComputeEntity> {

    @Select("SELECT gom_auto_compute.*,gom_auto_station.name as station_name " +
            "FROM gom_auto_compute " +
            "LEFT JOIN gom_auto_station on gom_auto_compute.station_id = gom_auto_station.id " +
            "WHERE gom_auto_compute.status < 99 AND group_id = #{groupId} order by survey_index")
    List<AutoComputeEntity> QueryValidCompute(@Param("groupId") Long groupId);

    @Update("UPDATE gom_auto_compute set station_id = #{stationId} where group_id = #{groupId} and survey_index = #{surveyIndex} and status<99")
    int UpdateComputeByGroupIdAndSurveyIndex(@Param("stationId") Long stationId,@Param("groupId") Long groupId,@Param("surveyIndex") int surveyIndex);

    @Update("UPDATE gom_auto_compute set status = 99 where group_id = #{groupId} and survey_index = #{surveyIndex}")
    int DelComputeByGroupIdAndSurveyIndex(@Param("groupId") Long groupId,@Param("surveyIndex") int surveyIndex);
}
