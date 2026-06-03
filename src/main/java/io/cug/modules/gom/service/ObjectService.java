
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.common.utils.PageUtils;
import io.cug.modules.gom.entity.ObjectEntity;

import java.util.List;
import java.util.Map;

public interface ObjectService extends IService<ObjectEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<ObjectEntity> QueryValidObjectTree(Long nProjectID);
    List<ObjectEntity> SelectTunnelObject(Long projectId);
    ObjectEntity QueryObjectById(Long id);

    ObjectEntity QueryValidObjectByName(Long nProjectId,String name);

    List<ObjectEntity> SelectObjectWithWarning(Long projectId,long surveyType);
    ObjectEntity SelectObjectWithWarningById(Long objectId,long surveyType);

    int queryValidObjectNum(Long nProjectID);
}

