package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.LoopObjectEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface LoopObjectDao extends BaseMapper<LoopObjectEntity> {

    @Select("select * from gom_loop_object where project_id =#{projectId} and object_id =#{objectId} and statue <99")
    List<LoopObjectEntity> SelectByObjectId(@Param("objectId") long objectId);

    @Select("select * from gom_loop_object where project_id =#{projectId} and status < 99")
    List<LoopObjectEntity> SelectAll(@Param("projectId") long projectId);

    @Select("select objectData.*,  objectPeriod.name as period_name, objectPeriod.begin as begin " +
            "from gom_loop_object objectData " +
            "join gom_period objectPeriod on objectData.period_id = objectPeriod.id " +
            "where objectData.object_id = #{objectId} and objectData.status <99 order by objectPeriod.begin")
    List<LoopObjectEntity> SelectByPeriod(@Param("objectId") long objectId);
    @Select("SELECT displacementData.*, glt.type AS object_type, gp.begin AS begin, glt.loop_id AS loop_id " +
            "FROM gom_loop_object displacementData " +
            "JOIN gom_loop_type glt ON displacementData.object_id = glt.object_id " +
            "JOIN gom_period gp ON displacementData.period_id = gp.id " +
            "JOIN gom_loop gl ON glt.loop_id = gl.id "+
            "WHERE gl.id = #{loopId} AND glt.type = #{objectType} AND gp.begin BETWEEN #{startTime} AND #{endTime} AND displacementData.status <99 ORDER BY gp.begin")
    List<LoopObjectEntity> SelectDisplacement(@Param("loopId") long loopId,
                                              @Param("objectType") int objectType,
                                              @Param("startTime") Date startTime,
                                              @Param("endTime") Date endTime);

    @Select("select objectData.*, glt.type as object_type, objectPeriod.begin as begin, gl.id as loop_id " +
            "from gom_loop_object objectData " +
            "join gom_period objectPeriod on objectData.period_id = objectPeriod.id " +
            "join gom_loop_type glt on objectData.object_id = glt.object_id " +
            "join gom_loop gl ON glt.loop_id = gl.id " +
            "where gl.id = #{loopId} AND glt.type = #{objectType} and objectData.status <99 order by objectPeriod.begin")
    List<LoopObjectEntity> SelectByObjectType(@Param("loopId") long loopId,
                                             @Param("objectType") int objectType);

    @Select("select objectData.*, glt.type as object_type,  gl.id as loop_id " +
            "from gom_loop_object objectData " +
            "join gom_period objectPeriod on objectData.period_id = objectPeriod.id " +
            "join gom_loop_type glt on objectData.object_id = glt.object_id " +
            "join gom_loop gl ON glt.loop_id = gl.id " +
            "where gl.id = #{loopId} AND objectData.period_id = #{periodId} and objectData.status <99 ")
    List<LoopObjectEntity> SelectByPeriodId(@Param("loopId") long loopId,
                                            @Param("periodId") long periodId);

    @Select("select objectData.*, glt.type as object_type " +
            "from gom_loop_object objectData " +
            "join gom_loop_type glt on glt.object_id =  objectData.object_id " +
            "join  gom_period objectPeriod on objectData.period_id = objectPeriod.id " +
            "where objectData.object_id= #{objectId} ORDER BY objectPeriod.begin LIMIT 1")
    List<LoopObjectEntity> SelectFirstData(@Param("objectId") long objectId);

    @Select("select objectData.*, glt.type as object_type, gom_object.name as object_name " +
            "from gom_loop_object objectData " +
            "join gom_object on gom_object.id = objectData.object_id " +
            "join gom_loop_type glt on glt.object_id =  objectData.object_id " +
            "where objectData.period_id = #{periodId} and glt.type = #{objectType} and objectData.status <99 ")
    List<LoopObjectEntity> SelectByPeriodIdAndObjectId(@Param("objectType") long objectType,
                                                       @Param("periodId") long periodId);


}
