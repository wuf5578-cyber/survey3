
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.TimerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface TimerDao extends BaseMapper<TimerEntity> {

    @Select("select * from gom_timer where device_id = #{deviceId} and status<99 LIMIT 1")
    TimerEntity selectTimerByDeviceId(@Param("deviceId") long deviceId);

}
