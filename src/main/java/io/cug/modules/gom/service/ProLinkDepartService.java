
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.common.utils.PageUtils;
import io.cug.modules.gom.entity.ObjectEntity;
import io.cug.modules.gom.entity.ProLinkDepartEntity;

import java.util.List;
import java.util.Map;

public interface ProLinkDepartService extends IService<ProLinkDepartEntity> {

    List<ProLinkDepartEntity> QueryValidProLinkDepart(long projectId);

    String getDepartmentName(long departmentId);

}

