
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.ProjectEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface ProjectDao extends BaseMapper<ProjectEntity> {

    @Select("select * from gom_project where name like CONCAT('%',#{strKeyword},'%')")
    List<ProjectEntity> selectProjectsByName(@Param("strKeyword") String strKeyword);

    @Select("select * from gom_project where status<99 and name like CONCAT('%',#{strKeyword},'%')")
    List<ProjectEntity> selectValidProjectsByName(@Param("strKeyword") String strKeyword);

    @Select("select * from gom_project where status<99")
    List<ProjectEntity> selectValidProjects();

}
