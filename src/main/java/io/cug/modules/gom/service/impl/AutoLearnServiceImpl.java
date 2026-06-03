
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.AutoLearnDao;
import io.cug.modules.gom.entity.AutoLearnEntity;
import io.cug.modules.gom.entity.AutoStationEntity;
import io.cug.modules.gom.entity.ObjectEntity;
import io.cug.modules.gom.service.AutoLearnService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("autoLearnService")
public class AutoLearnServiceImpl extends ServiceImpl<AutoLearnDao, AutoLearnEntity> implements AutoLearnService {

    @Override
    public List<AutoLearnEntity> QueryValidLearn(long projectId){

        //数据查询
        List<AutoLearnEntity> list = baseMapper.QueryValidLearn(projectId);

        return list;
    }

    @Override
    public List<ObjectEntity> QueryObject(long projectId){

        //数据查询
        List<ObjectEntity> list = baseMapper.QueryObject(projectId);

        return list;
    }


}