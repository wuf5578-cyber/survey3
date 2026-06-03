
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.AlarmEntity;
import io.cug.modules.gom.entity.DeviceEntity;
import io.cug.modules.gom.entity.ParamEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface ParamDao extends BaseMapper<ParamEntity> {
    @Select("select * from gom_power_params where status<99 and survey_type =#{surveyType}")
    List<ParamEntity> selectValidParamsBySurveyType(@Param("surveyType") int surveyType);

    @Select("select * from gom_power_params where status<99 and object_id =#{objectId}")
    ParamEntity getByObjectId(@Param("objectId") Long objectId);

}
