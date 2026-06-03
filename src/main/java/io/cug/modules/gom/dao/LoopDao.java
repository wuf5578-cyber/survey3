
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.LoopEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface LoopDao extends BaseMapper<LoopEntity> {
    @Select("select * from gom_loop where project_id =#{projectId} and status < 99")
    List<LoopEntity> SelectAll(@Param("projectId") long projectId);

    @Select("select * from gom_loop where project_id =#{projectId} and loop_name =#{loopName} and status < 99")
    List<LoopEntity> SelectByLoopName(@Param("projectId") long projectId, @Param("loopName") long loopName);

    @Select("select loopData.* , object.id as object_id , object.name as object_name , loop_type.type as object_type " +
            "from gom_loop loopData " +
            "join gom_loop_type loop_type on loopData.id = loop_type.loop_id " +
            "join gom_object object on loop_type.object_id = object.id where loopData.project_id =#{projectId} and loopData.status < 99")
    List<LoopEntity> SelectLoop(@Param("projectId") long projectId);
}
