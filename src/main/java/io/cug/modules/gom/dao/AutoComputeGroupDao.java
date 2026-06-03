
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.AutoComputeGroupEntity;
import io.cug.modules.gom.entity.AutoLearnEntity;
import io.cug.modules.gom.entity.ObjectEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface AutoComputeGroupDao extends BaseMapper<AutoComputeGroupEntity> {

    @Select("SELECT * FROM gom_auto_compute_group WHERE status < 99 AND project_id = #{projectId} ")
    List<AutoComputeGroupEntity> QueryValidComputeGroup(@Param("projectId") Long projectId);

}
