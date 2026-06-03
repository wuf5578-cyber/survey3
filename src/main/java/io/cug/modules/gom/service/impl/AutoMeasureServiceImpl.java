
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.AutoLearnDao;
import io.cug.modules.gom.dao.AutoMeasureDao;
import io.cug.modules.gom.entity.AutoLearnEntity;
import io.cug.modules.gom.entity.AutoMeasureEntity;
import io.cug.modules.gom.entity.ObjectEntity;
import io.cug.modules.gom.service.AutoLearnService;
import io.cug.modules.gom.service.AutoMeasureService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("autoMeasureService")
public class AutoMeasureServiceImpl extends ServiceImpl<AutoMeasureDao, AutoMeasureEntity> implements AutoMeasureService {

    @Override
    public List<AutoMeasureEntity> QueryValidMeasure(long stationId,long periodId){

        //数据查询
        List<AutoMeasureEntity> list = baseMapper.QueryValidMeasure(stationId,periodId);

        return list;
    }



}