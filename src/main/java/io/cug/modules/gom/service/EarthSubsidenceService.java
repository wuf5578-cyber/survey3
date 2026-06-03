
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.EarthSubsidenceEntity;
import io.cug.modules.gom.entity.PeriodEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface EarthSubsidenceService extends IService<EarthSubsidenceEntity> {

    List<EarthSubsidenceEntity> QueryValidEarthSubsidence(long objectId);
    EarthSubsidenceEntity QueryValidEarthSubsidenceEntityByPeriodId(long objectId,long periodId);
    List<EarthSubsidenceEntity> EarthSubsidenceWithPeriod(long objectId);
    EarthSubsidenceEntity getFirstData(long objectId);
    List<PeriodEntity> QueryAllPeriodsByProjectId( long projectId, long type);
    List<EarthSubsidenceEntity> QueryEarthSubsidenceWithObjectName(long periodId,int type);
    Boolean QueryEarthSubsidenceGreaterThanBegin(long objectId, Date begin);
    EarthSubsidenceEntity selectByPeriodAndObject(long objectId,long periodId);
}

