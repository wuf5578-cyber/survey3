
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.AlarmDao;
import io.cug.modules.gom.dao.CesiumDao;
import io.cug.modules.gom.entity.AlarmEntity;
import io.cug.modules.gom.entity.CesiumEntity;
import io.cug.modules.gom.service.AlarmService;
import io.cug.modules.gom.service.CesiumService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("cesiumService")
public class CesiumServiceImpl extends ServiceImpl<CesiumDao, CesiumEntity> implements CesiumService {

    @Override
    public List<CesiumEntity> QueryValidCesium(long projectId){

        //数据查询
        List<CesiumEntity> list = baseMapper.QueryValidCesium(projectId);

        return list;
    }

    @Override
    public CesiumEntity QueryCesiumByPointId(long projectId, String pointId){
        return baseMapper.QueryCesiumByPointId(projectId, pointId);
    }


}