
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.DeviceEntity;
import io.cug.modules.gom.entity.FaultEntity;
import io.cug.modules.gom.entity.ObjectEntity;
import io.cug.modules.gom.entity.PointEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface FaultDao extends BaseMapper<FaultEntity> {

    @Select("select * from gom_fault " +
            "where project_id=#{projectId} and type=#{type} and status=#{status}")
    List<FaultEntity> selectFaultByStatusType(@Param("projectId") Long projectId, @Param("status") int status, @Param("type")int type);

    @Select("select * from gom_fault " +
            "where project_id=#{projectId}")
    List<FaultEntity> selectAllFault(@Param("projectId") Long projectId);

    @Select("select * from gom_fault " +
            "where project_id=#{projectId} and status=#{status}")
    List<FaultEntity> selectFaultByStatus(@Param("projectId") Long projectId, @Param("status") int status);

    @Select("select * from gom_fault " +
            "where project_id=#{projectId} and type=#{type}")
    List<FaultEntity> selectFaultByType(@Param("projectId") Long projectId, @Param("type")int type);
}
