
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.common.utils.PageUtils;
import io.cug.common.utils.Query;
import io.cug.modules.gom.dao.DepartmentDao;
import io.cug.modules.gom.dao.ObjectDao;
import io.cug.modules.gom.entity.DepartmentEntity;
import io.cug.modules.gom.entity.ObjectEntity;
import io.cug.modules.gom.service.DepartmentService;
import io.cug.modules.gom.service.ObjectService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("departmentService")
public class DepartmentServiceImpl extends ServiceImpl<DepartmentDao, DepartmentEntity> implements DepartmentService {

    @Override
    public List<DepartmentEntity> QueryValidDepartment(){

        //数据查询
        List<DepartmentEntity> list = baseMapper.QueryValidDepartment();

        return list;
    }


}