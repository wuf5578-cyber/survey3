package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.EarlyWarningEntity;
import io.cug.modules.gom.entity.WarningStandardEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WarningStandardDao extends BaseMapper<WarningStandardEntity> {

    @Select("select * from gom_warning_standard where status<99 and survey_type=#{surveyType} ")
    List<WarningStandardEntity> selectWarningStandardBySurveyType(@Param("surveyType") long surveyType);
}
