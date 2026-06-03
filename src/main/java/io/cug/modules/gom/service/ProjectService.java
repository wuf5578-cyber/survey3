
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.common.utils.PageUtils;
import io.cug.modules.gom.entity.ProjectEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ProjectService extends IService<ProjectEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<ProjectEntity> listValidProjects();
    List<ProjectEntity> selectProjectsByName(String strKeyword);
    List<ProjectEntity> selectValidProjectsByName(String strKeyword);
}

