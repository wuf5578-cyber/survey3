
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.DepartmentEntity;
import io.cug.modules.gom.entity.ObjectEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface DepartmentDao extends BaseMapper<DepartmentEntity> {

    @Select("select * from sys_department where status<99")
    List<DepartmentEntity> QueryValidDepartment();

}
