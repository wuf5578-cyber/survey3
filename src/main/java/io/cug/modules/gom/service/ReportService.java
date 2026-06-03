
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.DepartmentEntity;
import io.cug.modules.gom.entity.ReportEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReportService extends IService<ReportEntity> {

    ReportEntity selectValidReportByProjectId(long projectId);

}

