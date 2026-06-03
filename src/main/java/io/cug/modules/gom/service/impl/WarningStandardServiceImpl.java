
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.EarlyWarningDao;
import io.cug.modules.gom.dao.WarningStandardDao;
import io.cug.modules.gom.entity.EarlyWarningEntity;
import io.cug.modules.gom.entity.WarningStandardEntity;
import io.cug.modules.gom.service.EarlyWarningService;
import io.cug.modules.gom.service.WarningStandardService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("warningStandardService")
public class WarningStandardServiceImpl extends ServiceImpl<WarningStandardDao, WarningStandardEntity> implements WarningStandardService {

    @Override
    public List<WarningStandardEntity> selectWarningStandardBySurveyType(long surveyType){

        List<WarningStandardEntity> list = baseMapper.selectWarningStandardBySurveyType(surveyType);

        return list;
    }

}