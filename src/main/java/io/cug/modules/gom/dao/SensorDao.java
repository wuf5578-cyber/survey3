
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.SensorEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;


@Mapper
public interface SensorDao extends BaseMapper<SensorEntity> {

    @Select("SELECT * FROM gom_sensor where device_id=#{deviceId} ORDER BY survey_time DESC LIMIT 1;")
    SensorEntity QueryNewestTH(@Param("deviceId") long deviceId);

    @Select("select * from gom_sensor where device_id=#{deviceId} and survey_time >= DATE_SUB(NOW(), INTERVAL 24 HOUR) " +
            "ORDER BY survey_time; ")
    List<SensorEntity> QueryDefaultTH(@Param("deviceId") long deviceId);

    @Select("select * from gom_sensor where device_id=#{deviceId} and survey_time BETWEEN #{startTime} AND #{endTime} " +
            "ORDER BY survey_time;")
    List<SensorEntity> QueryTHWithTime(@Param("deviceId") long deviceId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

}
