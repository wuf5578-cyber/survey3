
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.TunnelStringEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface TunnelStringDao extends BaseMapper<TunnelStringEntity> {
    @Select("select * from gom_loop where period_id =#{periodId} and loop_name =#{loopName} and status < 99")
    TunnelStringEntity SelectByStringLoopName(@Param("periodId") long periodId,@Param("loopName") long loopName);
}
