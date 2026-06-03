
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.DepartmentDao;
import io.cug.modules.gom.dao.ReportDao;
import io.cug.modules.gom.entity.DepartmentEntity;
import io.cug.modules.gom.entity.ReportEntity;
import io.cug.modules.gom.service.DepartmentService;
import io.cug.modules.gom.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("reportService")
public class ReportServiceImpl extends ServiceImpl<ReportDao, ReportEntity> implements ReportService {

    @Override
    public ReportEntity selectValidReportByProjectId(long projectId){

        //数据查询
        ReportEntity list = baseMapper.selectValidReportByProjectId(projectId);

        return list;
    }


}