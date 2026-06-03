package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.TunnelDotEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TunnelDotDao extends BaseMapper<TunnelDotEntity> {

    @Select("select * from gom_loop_object where project_id =#{projectId} and object_id =#{objectId} and period_id =#{periodId} and statue <99")
    TunnelDotEntity SelectDotByObjectId(@Param("projectID") long projectID, @Param("objectId") long objectId, @Param("periodId") long periodId);
}
