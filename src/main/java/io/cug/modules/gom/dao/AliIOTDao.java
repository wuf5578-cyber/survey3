
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.AlarmEntity;
import io.cug.modules.gom.entity.AliIOTEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface AliIOTDao extends BaseMapper<AliIOTEntity> {
    @Select("select * from gom_iot_mqtt where project_id=#{projectId} and type=#{type} and status<99")
    List<AliIOTEntity> selectValidIOTByType(@Param("projectId")Long projectId,@Param("type") int type);

    @Select("select * from gom_iot_mqtt where device_id=#{deviceId} and status<99")
    AliIOTEntity getByDeviceId(@Param("deviceId")Long deviceId);
}
