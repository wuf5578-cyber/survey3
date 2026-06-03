
package io.cug.modules.gom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.gom.entity.DepartmentEntity;
import io.cug.modules.gom.entity.DictionaryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface DictionaryDao extends BaseMapper<DictionaryEntity> {

    @Select("select * from sys_dictionary where status=0 and parent_id=#{parentId} ORDER BY sys_dictionary.sort")
    List<DictionaryEntity> QueryValidDictionaryByParentId(@Param("parentId") long parentId);

}
