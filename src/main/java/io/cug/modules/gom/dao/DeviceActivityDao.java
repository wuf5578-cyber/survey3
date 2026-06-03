
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.AlarmEntity;
import io.cug.modules.gom.entity.DeviceActivityEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface DeviceActivityDao extends BaseMapper<DeviceActivityEntity> {

    @Select("select * from gom_device_activity device where device_id = #{deviceId} and status<99 ")
    List<DeviceActivityEntity> selectByDeviceId(@Param("deviceId") Long deviceId);

}
