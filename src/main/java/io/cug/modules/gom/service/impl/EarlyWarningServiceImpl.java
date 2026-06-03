
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.EarlyWarningDao;
import io.cug.modules.gom.entity.EarlyWarningEntity;
import io.cug.modules.gom.service.EarlyWarningService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("earlywarningService")
public class EarlyWarningServiceImpl extends ServiceImpl<EarlyWarningDao, EarlyWarningEntity> implements EarlyWarningService {

    @Override
    public List<EarlyWarningEntity> selectAllValidEarlyWarning(){

        List<EarlyWarningEntity> list = baseMapper.selectAllValidEarlyWarning();

        return list;
    }

    @Override
    public EarlyWarningEntity selectValidEarlyWarningByObjectId(long objectId,long surveyType){
        return baseMapper.selectValidEarlyWarningByObjectId(objectId,surveyType);
    }

    @Override
    public void InsertOrUpdateWarning(EarlyWarningEntity earlyWarningEntity){
        baseMapper.InsertOrUpdateWarning(earlyWarningEntity.getProjectId(),
                earlyWarningEntity.getObjectId(),
                earlyWarningEntity.getSurveyType(),
                earlyWarningEntity.getWarningA1(),
                earlyWarningEntity.getWarningA2(),
                earlyWarningEntity.getWarningB1(),
                earlyWarningEntity.getWarningB2(),
                earlyWarningEntity.getStatus());
        return;
    }

    @Override
    public EarlyWarningEntity selectDefaultEarlyWarningBySurveyType(long projectId,long surveyType){
        return baseMapper.selectDefaultEarlyWarningBySurveyType(projectId, surveyType);
    }

    @Override
    public EarlyWarningEntity selectByObjectId(long projectId,long objectId,int surveyType){
        return baseMapper.selectByObjectId(projectId,objectId,surveyType);
    }

}