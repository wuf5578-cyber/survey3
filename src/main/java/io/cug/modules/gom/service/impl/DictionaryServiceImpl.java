
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.DepartmentDao;
import io.cug.modules.gom.dao.DictionaryDao;
import io.cug.modules.gom.entity.DepartmentEntity;
import io.cug.modules.gom.entity.DictionaryEntity;
import io.cug.modules.gom.service.DepartmentService;
import io.cug.modules.gom.service.DictionaryService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("dictionaryService")
public class DictionaryServiceImpl extends ServiceImpl<DictionaryDao, DictionaryEntity> implements DictionaryService {

    @Override
    public List<DictionaryEntity> QueryValidDictionaryByParentId(Long parentId){

        //数据查询
        List<DictionaryEntity> list = baseMapper.QueryValidDictionaryByParentId(parentId);

        return list;
    }


}