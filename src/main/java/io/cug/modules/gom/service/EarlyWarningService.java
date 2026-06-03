
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.DepartmentEntity;
import io.cug.modules.gom.entity.EarlyWarningEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EarlyWarningService extends IService<EarlyWarningEntity> {

    List<EarlyWarningEntity> selectAllValidEarlyWarning();

    EarlyWarningEntity selectValidEarlyWarningByObjectId(long objectId,long surveyType);

    void InsertOrUpdateWarning(EarlyWarningEntity earlyWarningEntity);

    EarlyWarningEntity selectDefaultEarlyWarningBySurveyType(long projectId,long surveyType);

    EarlyWarningEntity selectByObjectId(long projectId,long objectId,int surveyType);
}

