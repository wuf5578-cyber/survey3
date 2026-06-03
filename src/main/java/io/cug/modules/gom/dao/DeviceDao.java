
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.DeviceEntity;
import io.cug.modules.gom.entity.PointEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface DeviceDao extends BaseMapper<DeviceEntity> {

    @Select("select * from gom_project_device where status<99 and project_id=#{nProjectID}")
    List<DeviceEntity> selectValidDevices(@Param("nProjectID") long nProjectID);

    @Select("select * from gom_project_device where status<99 and project_id=#{nProjectID} and type=#{type}")
    List<DeviceEntity> selectValidPowerDevices(@Param("nProjectID") long nProjectID,@Param("type") int type);

    @Select("select * from gom_project_device where status<99 and project_id=#{nProjectID} and type IN (2, 3, 4, 5 ,6)")
    List<DeviceEntity> selectValidSensor(@Param("nProjectID") long nProjectID);

    @Select("select * from gom_project_device where status<99 and project_id=#{nProjectID} and name=#{name}")
    List<DeviceEntity> selectValidDevicesByName(@Param("nProjectID") long nProjectID,@Param("name") String name);

    @Select("select * from gom_point_link_survey where status<99 and object_id=#{nObjectID}")
    List<PointEntity> selectByObjectId(@Param("nObjectID") long nObjectID);

    @Update("update gom_point_link_survey set status=#{status} where object_id=#{objectId} and survey_type=#{surveyType}")
    int updateByObjectId(@Param("status") long status,@Param("objectId") long objectId,@Param("surveyType") long surveyType);

    @Update("update gom_point_link_survey set status=99 where object_id=#{objectId}")
    int updateStatus99(@Param("objectId") long objectId);

    @Select("select * from gom_point_link_survey where object_id=#{objectId} and survey_type=#{surveyType}")
    List<PointEntity> IsExist(@Param("objectId") long objectId,@Param("surveyType") long surveyType);

    @Select("select * from gom_project_device where status<99 and name like CONCAT('%',#{strKeyword},'%') and project_id=#{nProjectID}")
    List<DeviceEntity> selectValidDevicesByKeyword(@Param("nProjectID") long nProjectID,@Param("strKeyword") String strKeyword);


}
