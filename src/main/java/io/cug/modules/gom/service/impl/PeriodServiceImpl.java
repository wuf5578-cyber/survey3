
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.common.utils.PageUtils;
import io.cug.common.utils.Query;
import io.cug.modules.gom.dao.PeriodDao;
import io.cug.modules.gom.entity.*;
import io.cug.modules.gom.service.PeriodService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("periodService")
public class PeriodServiceImpl extends ServiceImpl<PeriodDao, PeriodEntity> implements PeriodService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PeriodEntity> page = this.page(
                new Query<PeriodEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }
    @Override
    public List<PeriodEntity> listValidPeriods(long projectId){
        return baseMapper.selectValidPeriods(projectId);
    }

    @Override
    public List<PeriodEntity> selectValidPeriodsByName(long projectId,String strKeyword){
        return baseMapper.selectValidPeriodsByName(projectId,strKeyword);
    }

    @Override
    public PeriodEntity selectValidPeriodsByBegin(long projectId, Date begin){
        return baseMapper.selectValidPeriodsByBegin(projectId,begin);
    }
    @Override
    public PeriodEntity selectById(Long periodId){
        return baseMapper.selectById(periodId);
    }

    @Override
    public PeriodEntity selectNewPeriod(Long projectId){return baseMapper.selectNewPeriod(projectId);}

    @Override
    public List<DisplacementEntity> selectDisplacementPeriod(Long projectId){
        return baseMapper.selectDisplacementPeriod(projectId);
    }

    @Override
    public List<EarthSubsidenceEntity> selectEarthSubsidencePeriod(Long projectId, int type){
        return baseMapper.selectEarthSubsidencePeriod(projectId,type);
    }

    @Override
    public List<PowerEntity> selectPowerPeriod(Long projectId, int type){
        return baseMapper.selectPowerPeriod(projectId, type);
    }

    @Override
    public List<InclinometerEntity> selectInclinometerPeriod(Long projectId){
        return baseMapper.selectInclinometerPeriod(projectId);
    }

    @Override
    public List<ExtendEntity> selectExtendPeriod(Long projectId){
        return baseMapper.selectExtendPeriod(projectId);
    }

}