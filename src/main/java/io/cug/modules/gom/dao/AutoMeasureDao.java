
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.AutoMeasureEntity;
import io.cug.modules.gom.entity.DepartmentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface AutoMeasureDao extends BaseMapper<AutoMeasureEntity> {

    @Select("select gom_auto_measure.*,gom_object.name from gom_auto_measure " +
            "INNER JOIN gom_object ON gom_auto_measure.object_id = gom_object.id where gom_auto_measure.status<99 and gom_auto_measure.station_id=#{stationId} and gom_auto_measure.period_id=#{periodId}")
    List<AutoMeasureEntity> QueryValidMeasure(@Param("stationId") long stationId,@Param("periodId") long periodId);

}
