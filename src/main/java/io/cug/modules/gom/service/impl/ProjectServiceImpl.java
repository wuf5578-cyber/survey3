
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.common.utils.PageUtils;
import io.cug.common.utils.Query;
import io.cug.modules.gom.dao.ProjectDao;
import io.cug.modules.gom.entity.ProjectEntity;
import io.cug.modules.gom.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("projectService")
public class ProjectServiceImpl extends ServiceImpl<ProjectDao, ProjectEntity> implements ProjectService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProjectEntity> page = this.page(
                new Query<ProjectEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<ProjectEntity> listValidProjects(){
        return baseMapper.selectValidProjects();
    }

    @Override
    public List<ProjectEntity> selectProjectsByName(String strKeyword){
        return baseMapper.selectProjectsByName(strKeyword);
    }

    @Override
    public List<ProjectEntity> selectValidProjectsByName(String strKeyword){
        return baseMapper.selectValidProjectsByName(strKeyword);
    }
}