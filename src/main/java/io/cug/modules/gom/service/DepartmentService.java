
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.common.utils.PageUtils;
import io.cug.modules.gom.entity.DepartmentEntity;
import io.cug.modules.gom.entity.ObjectEntity;

import java.util.List;
import java.util.Map;

public interface DepartmentService extends IService<DepartmentEntity> {

    List<DepartmentEntity> QueryValidDepartment();

}

