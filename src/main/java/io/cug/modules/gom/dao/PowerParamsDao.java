
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.AlarmEntity;
import io.cug.modules.gom.entity.PowerParamsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface PowerParamsDao extends BaseMapper<PowerParamsEntity> {

    @Select("select * from gom_power_params where object_id = #{objectId} and status<99 ")
    PowerParamsEntity QueryValidPowerParams(@Param("objectId") long objectId);

}
