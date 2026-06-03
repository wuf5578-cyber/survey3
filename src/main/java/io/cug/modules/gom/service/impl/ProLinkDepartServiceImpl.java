
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.ProLinkDepartDao;
import io.cug.modules.gom.entity.ProLinkDepartEntity;
import io.cug.modules.gom.service.ProLinkDepartService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("proLinkDepartService")
public class ProLinkDepartServiceImpl extends ServiceImpl<ProLinkDepartDao, ProLinkDepartEntity> implements ProLinkDepartService {

    @Override
    public List<ProLinkDepartEntity> QueryValidProLinkDepart(long projectId){
        //数据查询
        List<ProLinkDepartEntity> list = baseMapper.QueryValidProLinkDepart(projectId);

        return list;
    }

    @Override
    public String getDepartmentName(long departmentId){
        String name=baseMapper.getDepartmentName(departmentId);
        return name;
    }

}