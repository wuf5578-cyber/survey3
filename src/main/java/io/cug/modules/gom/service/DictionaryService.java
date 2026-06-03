
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.DepartmentEntity;
import io.cug.modules.gom.entity.DictionaryEntity;

import java.util.List;

public interface DictionaryService extends IService<DictionaryEntity> {

    List<DictionaryEntity> QueryValidDictionaryByParentId(Long parentId);

}

