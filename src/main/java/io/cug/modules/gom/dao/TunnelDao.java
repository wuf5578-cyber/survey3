
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.TunnelEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface TunnelDao extends BaseMapper<TunnelEntity> {
    @Select("select * from gom_tunnel where project_id =#{projectId} and status < 99")
    List<TunnelEntity> SelectAll(@Param("projectId") long projectId);

}
