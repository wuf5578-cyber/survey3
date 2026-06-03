
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.common.utils.PageUtils;
import io.cug.common.utils.Query;

import io.cug.modules.gom.dao.PointDao;
import io.cug.modules.gom.entity.ObjectEntity;
import io.cug.modules.gom.entity.PeriodEntity;
import io.cug.modules.gom.entity.PointEntity;
import io.cug.modules.gom.service.PointService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("pointService")
public class PointServiceImpl extends ServiceImpl<PointDao, PointEntity> implements PointService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PointEntity> page = this.page(
                new Query<PointEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<PointEntity> listValidPoints(long projectId){
        return baseMapper.selectValidPoints(projectId);
    }

    @Override
    public PointEntity selectByObjectId(long objectId){return baseMapper.selectByObjectId(objectId);}

    @Override
    public List<PointEntity> IsExist(long objectId,long surveyType){
        return baseMapper.IsExist(objectId,surveyType);
    }
    @Override
    public List<ObjectEntity> selectObjectBySurveyType(long projectId, long surveyType){
        return baseMapper.selectObjectBySurveyType(projectId, surveyType);
    }

    @Override
    public int getObjectNum(Long projectId,int surveyType){
        return baseMapper.getObjectNum(projectId, surveyType).size();
    }
}