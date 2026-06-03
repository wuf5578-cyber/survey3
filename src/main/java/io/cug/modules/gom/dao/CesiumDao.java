
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.AlarmEntity;
import io.cug.modules.gom.entity.CesiumEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface CesiumDao extends BaseMapper<CesiumEntity> {

    @Select("select * from gom_3dtiles where status<99 and project_id=#{projectId} ")
    List<CesiumEntity> QueryValidCesium(@Param("projectId") long projectId);

    @Select("select * from gom_3dtiles where status<99 and point_id=#{pointId} and project_id=#{projectId} ")
    CesiumEntity QueryCesiumByPointId(@Param("projectId") long projectId,@Param("pointId") String pointId);

}
