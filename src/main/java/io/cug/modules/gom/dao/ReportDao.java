
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.PeriodEntity;
import io.cug.modules.gom.entity.ReportEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface ReportDao extends BaseMapper<ReportEntity> {

    @Select("SELECT * " +
            "FROM gom_project_report " +
            "WHERE project_id = #{projectId} and status<99 ")
    ReportEntity selectValidReportByProjectId(@Param("projectId") long nProjectID);
}
