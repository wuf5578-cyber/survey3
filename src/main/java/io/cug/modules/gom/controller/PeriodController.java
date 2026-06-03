package io.cug.modules.gom.controller;

import io.cug.common.utils.DateUtil;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.app.param.ProjectIDForm;
import io.cug.modules.app.param.SqlWhereForm;
import io.cug.modules.gom.entity.*;
import io.cug.modules.gom.param.PeriodTypeInfo;
import io.cug.modules.gom.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(tags = "监测——周期")
@RestController
@RequestMapping("gom/Period")
public class PeriodController {

    @Autowired
    private PeriodService PeriodService;

    @Login
    @PostMapping("/ListByType")
    @ApiOperation("根据监测项列表")
    public R classListByType(@RequestBody PeriodEntity periodEntity){
        List<PeriodTypeInfo> periodTypeInfos = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[] typeList = {"水平位移","竖向位移","测斜","倾斜","主筋应力","混凝土支撑","支撑轴力","孔隙水压力","土压力","锚索应力","地下水位"};
        for(int i = 0;i<typeList.length;i++){
            PeriodTypeInfo info = new PeriodTypeInfo();
            info.setId((long) -(i+1));
            info.setLabel(typeList[i]);
            List<PeriodTypeInfo> childrenInfo = new ArrayList<>();
            if (i+1==1){//Displacement
                List<DisplacementEntity> list = PeriodService.selectDisplacementPeriod(periodEntity.getProjectId());
                for(int j = 0;j<list.size();j++){
                    PeriodTypeInfo typeInfo = new PeriodTypeInfo();
                    typeInfo.setId(list.get(j).getPeriodId());
                    typeInfo.setLabel(list.get(j).getPeriodName()+ ": "+dateFormat.format(list.get(j).getBegin()));
                    typeInfo.setChildren(new ArrayList<>());
                    childrenInfo.add(typeInfo);
                }
            }else if(i+1==2||i+1==4||i+1==12){//earthSubsidence
                List<EarthSubsidenceEntity> list = PeriodService.selectEarthSubsidencePeriod(periodEntity.getProjectId(),i+1);
                for(int j = 0;j<list.size();j++){
                    PeriodTypeInfo typeInfo = new PeriodTypeInfo();
                    typeInfo.setId(list.get(j).getPeriodId());
                    typeInfo.setLabel(list.get(j).getPeriodName()+ ": "+dateFormat.format(list.get(j).getBegin()));
                    typeInfo.setChildren(new ArrayList<>());
                    childrenInfo.add(typeInfo);
                }
            }else if(i+1==3){//Inclinometer
                List<InclinometerEntity> list = PeriodService.selectInclinometerPeriod(periodEntity.getProjectId());
                for(int j = 0;j<list.size();j++){
                    PeriodTypeInfo typeInfo = new PeriodTypeInfo();
                    typeInfo.setId(list.get(j).getPeriodId());
                    typeInfo.setLabel(list.get(j).getPeriodName()+ ": "+dateFormat.format(list.get(j).getBegin()));
                    typeInfo.setChildren(new ArrayList<>());
                    childrenInfo.add(typeInfo);
                }
            }else if(i+1==5){//Extend
                List<ExtendEntity> list = PeriodService.selectExtendPeriod(periodEntity.getProjectId());
                for(int j = 0;j<list.size();j++){
                    PeriodTypeInfo typeInfo = new PeriodTypeInfo();
                    typeInfo.setId(list.get(j).getPeriodId());
                    typeInfo.setLabel(list.get(j).getPeriodName()+ ": "+dateFormat.format(list.get(j).getBegin()));
                    typeInfo.setChildren(new ArrayList<>());
                    childrenInfo.add(typeInfo);
                }
            }else if(i+1==6||i+1==7||i+1==8||i+1==9||i+1==10||i+1==11){//power
                List<PowerEntity> list = PeriodService.selectPowerPeriod(periodEntity.getProjectId(),i+1);
                for(int j = 0;j<list.size();j++){
                    PeriodTypeInfo typeInfo = new PeriodTypeInfo();
                    typeInfo.setId(list.get(j).getPeriodId());
                    typeInfo.setLabel(list.get(j).getPeriodName()+ ": "+dateFormat.format(list.get(j).getBegin()));
                    typeInfo.setChildren(new ArrayList<>());
                    childrenInfo.add(typeInfo);
                }
            }
            info.setChildren(childrenInfo);
            periodTypeInfos.add(info);
        }

        return R.ok().put("result",periodTypeInfos);
    }

    @Login
    @PostMapping("/List")
    @ApiOperation("周期列表")
    public R classList(@RequestBody PeriodEntity periodEntity){
        List<PeriodEntity> list;
        if (periodEntity.getKeyWord()==null){
            list=PeriodService.listValidPeriods(periodEntity.getProjectId());
        }
        else {
            list = PeriodService.selectValidPeriodsByName(periodEntity.getProjectId(),periodEntity.getKeyWord());
        }

        return R.ok().put("result",list);
    }
    @Login
    @PostMapping("/ListById")
    @ApiOperation("通过id查找周期")
    public R ListById(@LoginUser AppUserEntity user,@RequestBody PeriodEntity periodEntity){
        PeriodEntity period=PeriodService.selectById(periodEntity.getId());
        return R.ok().put("result", period);
    }

    @Login
    @PostMapping("/ListByBegin")
    @ApiOperation("通过开始时间查找周期")
    public R FindPeriodByBegin(@LoginUser AppUserEntity user,@RequestBody PeriodEntity periodEntity){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        PeriodEntity entity = PeriodService.selectValidPeriodsByBegin(periodEntity.getProjectId(), periodEntity.getBegin());
        if (entity!=null){
            return R.ok().put("result", entity.getId());
        }else{
            PeriodEntity periodEntity1=new PeriodEntity();
            periodEntity1.setName(format.format(periodEntity.getBegin()));
            periodEntity1.setProjectId(periodEntity.getProjectId());
            periodEntity1.setBegin(periodEntity.getBegin());
            periodEntity1.setEnd(periodEntity.getBegin());
            periodEntity1.setCreateByUserId((long) user.getUid());
            periodEntity1.setCreateTime(DateUtil.nowDateTime());
            periodEntity1.setStatus(1);
            PeriodService.save(periodEntity1);
            return R.ok().put("result", periodEntity1.getId());
        }
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加周期")
    public R add(@LoginUser AppUserEntity user, @RequestBody PeriodEntity PeriodEntity) {
        PeriodEntity.setCreateByUserId( (long)user.getUid() );
        PeriodEntity.setCreateTime(DateUtil.nowDateTime());
        PeriodEntity.setStatus(1);
        if (PeriodService.save(PeriodEntity)) {
            return R.ok().put("result", PeriodEntity);
        }
        else {
            return R.error("添加周期失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除周期")
    public R classList(@LoginUser AppUserEntity user, @RequestBody PeriodEntity PeriodEntity){
      //  if ((long)user.getUid() == PeriodEntity.getCreate_by_userid())
        {
            PeriodEntity.setStatus(99);//99-删除状态
            PeriodService.updateById(PeriodEntity);
        }

        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改周期")
    public R edit(@LoginUser AppUserEntity user, @RequestBody PeriodEntity periodEntity){
        PeriodService.updateById(periodEntity);
        return R.ok().put("result", "更新成功!");
    }

    @Login
    @PostMapping("/selectNew")
    @ApiOperation("查找最新周期")
    public R selectNew(@LoginUser AppUserEntity user, @RequestBody PeriodEntity periodEntity){
        PeriodEntity newPeriodEntity= PeriodService.selectNewPeriod(periodEntity.getProjectId());
        return R.ok().put("result", newPeriodEntity);
    }
}
