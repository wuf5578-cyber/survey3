
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.AutoComputeEntity;
import io.cug.modules.gom.entity.AutoCoorEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface AutoCoorDao extends BaseMapper<AutoCoorEntity> {

    @Select("SELECT gom_auto_coor.*,gom_object.name from gom_auto_coor " +
            "left join gom_object on gom_object.id = gom_auto_coor.object_id " +
            "where gom_auto_coor.status<99 and gom_auto_coor.period_id<0 and gom_auto_coor.project_id = #{projectId}")
    List<AutoCoorEntity> QueryValidGivenPoint(@Param("projectId") Long projectId);

    @Select("SELECT gom_auto_coor.*,gom_object.name from gom_auto_coor " +
            "left join gom_object on gom_object.id = gom_auto_coor.object_id " +
            "where gom_auto_coor.status<99 and gom_auto_coor.object_id = #{objectId}")
    List<AutoCoorEntity> QueryValidCoorByObjectId(@Param("objectId") Long objectId);

    @Select("SELECT gom_auto_coor.*,gom_object.name from gom_auto_coor " +
            "left join gom_object on gom_object.id = gom_auto_coor.object_id " +
            "where gom_auto_coor.status<99 and gom_auto_coor.period_id = #{periodId}")
    List<AutoCoorEntity> QueryValidCoorByPeriodId(@Param("periodId") Long periodId);

    @Select("SELECT gom_auto_coor.*,gom_object.name from gom_auto_coor " +
            "left join gom_object on gom_object.id = gom_auto_coor.object_id " +
            "where gom_auto_coor.status<99 and gom_auto_coor.period_id = #{periodId} and gom_auto_coor.object_id = #{objectId}")
    AutoCoorEntity QueryValidCoorByPeriodIdAndObjectId(@Param("periodId") Long periodId,@Param("objectId") Long objectId);

    @Update("update gom_auto_coor set status=99 where period_id=#{periodId} and object_id=#{objectId}")
    AutoCoorEntity updateByObjectId(@Param("periodId") Long periodId,@Param("objectId") Long objectId);

}
