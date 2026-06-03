
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.ImageEntity;
import io.cug.modules.gom.entity.ReportEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface ImageDao extends BaseMapper<ImageEntity> {

    @Select("SELECT * " +
            "FROM gom_image " +
            "WHERE project_id = #{projectId} and type = #{type} and status<99 ")
    List<ImageEntity> selectImageByProjectId(@Param("projectId") long nProjectID, @Param("type") long type);

    @Select("SELECT * " +
            "FROM gom_image " +
            "WHERE project_id = #{projectId} and status<99 and (type = 1 or type = 2 ) ")
    List<ImageEntity> selectCesiumFile(@Param("projectId") long nProjectID);
}
