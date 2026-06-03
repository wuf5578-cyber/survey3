
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.AutoStationEntity;
import io.cug.modules.gom.entity.DepartmentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface AutoStationDao extends BaseMapper<AutoStationEntity> {

    @Select("select * from gom_auto_station where status<99 and project_id=#{projectId} and status<99")
    List<AutoStationEntity> QueryValidStation(@Param("projectId") long projectId);

}
