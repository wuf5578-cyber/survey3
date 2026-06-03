
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.PointEntity;
import io.cug.modules.gom.entity.ProLinkDepartEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface ProLinkDepartDao extends BaseMapper<ProLinkDepartEntity> {

    @Select("select * from gom_project_link_department where status<99 and project_id=#{nProjectID}")
    List<ProLinkDepartEntity> QueryValidProLinkDepart(@Param("nProjectID") long nProjectID);

    @Select("select name from sys_department where id=#{departmentId}")
    String getDepartmentName(@Param("departmentId") long departmentId);
}
