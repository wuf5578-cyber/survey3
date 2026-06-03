
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.AutoLearnDao;
import io.cug.modules.gom.dao.ParamDao;
import io.cug.modules.gom.entity.AutoLearnEntity;
import io.cug.modules.gom.entity.ObjectEntity;
import io.cug.modules.gom.entity.ParamEntity;
import io.cug.modules.gom.service.AutoLearnService;
import io.cug.modules.gom.service.ParamService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("paramService")
public class ParamServiceImpl extends ServiceImpl<ParamDao, ParamEntity> implements ParamService {

    @Override
    public List<ParamEntity> selectValidParamsBySurveyType(int surveyType){

        //数据查询
        List<ParamEntity> list = baseMapper.selectValidParamsBySurveyType(surveyType);

        return list;
    }

    @Override
    public ParamEntity getByObjectId(Long objectId){
        ParamEntity paramEntity = baseMapper.getByObjectId(objectId);
        return paramEntity;
    }
}