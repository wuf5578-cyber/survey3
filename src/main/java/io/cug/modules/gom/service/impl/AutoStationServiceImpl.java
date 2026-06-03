
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.AutoStationDao;
import io.cug.modules.gom.dao.DepartmentDao;
import io.cug.modules.gom.entity.AutoStationEntity;
import io.cug.modules.gom.entity.DepartmentEntity;
import io.cug.modules.gom.service.AutoStationService;
import io.cug.modules.gom.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("autoStationService")
public class AutoStationServiceImpl extends ServiceImpl<AutoStationDao, AutoStationEntity> implements AutoStationService {

    @Override
    public List<AutoStationEntity> QueryValidStation(long projectId){

        //数据查询
        List<AutoStationEntity> list = baseMapper.QueryValidStation(projectId);

        return list;
    }


}