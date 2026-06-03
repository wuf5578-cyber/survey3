
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.LoopTypeEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface LoopTypeDao extends BaseMapper<LoopTypeEntity> {
    @Select("select * from gom_loop_type where object_id =#{objectId} and status < 99")
    List<LoopTypeEntity> SelectAll(@Param("objectId") long objectId);

    @Insert("insert into gom_loop_type (project_id, loop_id, type, object_id,status " +
            "values (#{projectId}, #{loopId}, #{type}, #{objectId}, #{status}")
    void InsertObjectType(@Param("projectId") long projectId, @Param("loopId") long loopId, @Param("type") long type, @Param("objectId") long objectId, @Param("status") long status);

}
