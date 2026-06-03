
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.AutoLearnEntity;
import io.cug.modules.gom.entity.AutoMeasureEntity;
import io.cug.modules.gom.entity.ObjectEntity;

import java.util.List;

public interface AutoMeasureService extends IService<AutoMeasureEntity> {

    List<AutoMeasureEntity> QueryValidMeasure(long stationId,long periodId);


}

