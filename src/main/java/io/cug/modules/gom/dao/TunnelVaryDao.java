
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.TunnelVaryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;


@Mapper
public interface TunnelVaryDao extends BaseMapper<TunnelVaryEntity> {
    @Select("select * from gom_tunnel_vary where project_id =#{projectId} and loop_id =#{loopId} and period_id =#{periodId} and status < 99")
    List<TunnelVaryEntity> SelectByPeriodId(@Param("projectId") long projectId, @Param("loopId") long loopId, @Param("periodId") long periodId);

    @Select("select analyseData.*, gp.begin AS begin, gl.id AS loop_id, gp.name as period_name " +
            "from gom_tunnel_vary analyseData " +
            "join gom_period gp on analyseData.period_id = gp.id " +
            "join gom_loop gl ON analyseData.loop_id = gl.id " +
            "where gl.id = #{loopId}  AND gp.begin BETWEEN #{startTime} AND #{endTime} AND analyseData.status <99 ORDER BY gp.begin")
    List<TunnelVaryEntity> SelectAnalyseData(@Param("loopId") long loopId,
                                            @Param("startTime") Date startTime,
                                            @Param("endTime") Date endTime);
}
