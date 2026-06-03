
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.common.utils.PageUtils;
import io.cug.modules.gom.entity.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface PeriodService extends IService<PeriodEntity> {

    PageUtils queryPage(Map<String, Object> params);


    List<PeriodEntity> listValidPeriods(long projectId);
    List<PeriodEntity> selectValidPeriodsByName(long projectId,String strKeyword);
    PeriodEntity selectValidPeriodsByBegin(long projectId, Date begin);

    PeriodEntity selectById(Long periodId);

    PeriodEntity selectNewPeriod(Long projectId);

    List<DisplacementEntity> selectDisplacementPeriod(Long projectId);

    List<EarthSubsidenceEntity> selectEarthSubsidencePeriod(Long projectId,int type);

    List<PowerEntity> selectPowerPeriod(Long projectId, int type);

    List<InclinometerEntity> selectInclinometerPeriod(Long projectId);

    List<ExtendEntity> selectExtendPeriod(Long projectId);
}

