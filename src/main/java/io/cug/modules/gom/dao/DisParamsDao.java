
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.DisParamsEntity;
import io.cug.modules.gom.entity.PowerParamsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface DisParamsDao extends BaseMapper<DisParamsEntity> {

    @Select("select * from gom_dis_params where object_id = #{objectId} and status<99 ")
    DisParamsEntity getByObjectId(@Param("objectId") long objectId);

}
