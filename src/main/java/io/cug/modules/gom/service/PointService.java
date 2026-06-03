
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.cug.common.utils.PageUtils;
import io.cug.modules.gom.entity.ObjectEntity;
import io.cug.modules.gom.entity.PeriodEntity;
import io.cug.modules.gom.entity.PointEntity;
import net.bytebuddy.matcher.BooleanMatcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PointService extends IService<PointEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<PointEntity> listValidPoints(long projectId);

    PointEntity selectByObjectId(long objectId);

    List<PointEntity> IsExist(long objectId,long surveyType);

    List<ObjectEntity> selectObjectBySurveyType(long projectId, long surveyType);

    int getObjectNum(Long projectId,int surveyType);
}

