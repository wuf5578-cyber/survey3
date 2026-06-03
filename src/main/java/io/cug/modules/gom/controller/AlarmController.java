package io.cug.modules.gom.controller;

import io.cug.common.utils.DateUtil;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.*;
import io.cug.modules.gom.param.RiskAssessDataInfo;
import io.cug.modules.gom.param.RiskAssessInfo;
import io.cug.modules.gom.param.TimelineData;
import io.cug.modules.gom.param.WarningData;
import io.cug.modules.gom.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;


@Api(tags = "报警表")
@RestController
@RequestMapping("gom/Alarm")
public class AlarmController {

    @Autowired
    private AlarmService alarmService;
    @Autowired
    private DisplacementService displacementService;
    @Autowired
    private EarthSubsidenceService earthSubsidenceService;
    @Autowired
    private InclinometerService inclinometerService;
    @Autowired
    private ExtendService extendService;
    @Autowired
    private PowerService powerService;

    @Autowired
    private ObjectService objectService;
    @Autowired
    private PeriodService periodService;
    @Autowired
    private EarlyWarningService earlyWarningService;

    @Login
    @PostMapping("/ListByPeriodId")
    @ApiOperation("通过项目id和周期id查找报警")
    public R ListByPeriodId(@LoginUser AppUserEntity user, @RequestBody AlarmEntity alarmEntity){
        List<AlarmEntity> list = alarmService.QueryValidAlarmByPeriodId(alarmEntity.getProjectId(),alarmEntity.getPeriodId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/List")
    @ApiOperation("通过项目id查找报警")
    public R classList(@LoginUser AppUserEntity user, @RequestBody AlarmEntity alarmEntity){
        List<AlarmEntity> list = alarmService.QueryValidAlarm(alarmEntity.getProjectId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/ListNew")
    @ApiOperation("通过项目id查找最新报警")
    public R ListNew(@LoginUser AppUserEntity user, @RequestBody AlarmEntity alarmEntity){
        List<AlarmEntity> list = ListNew(alarmEntity);
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/getAlarmChartData")
    @ApiOperation("通过项目id查找报警chart信息")
    public R getAlarmChartData(@LoginUser AppUserEntity user, @RequestBody AlarmEntity alarmEntity){
        //查找预警表中每个object中的最新数据
        List<AlarmEntity> list = ListNew(alarmEntity);
        int normalNum = objectService.queryValidObjectNum(alarmEntity.getProjectId());
        int yellowNum = 0;
        int redNum = 0;
        if(list.size()!=0){
            for (int i = 0;i < list.size(); i++){
                if (list.get(i).getAlarmType() == 1){
                    yellowNum++;
                }else if(list.get(i).getAlarmType() == 2){
                    redNum++;
                }
            }
        }
        Map<String, Object> normalData = new HashMap<>();
        normalData.put("name", "正常");
        normalData.put("value", normalNum);
        normalData.put("labelShow", false);

        Map<String, Object> yellowData = new HashMap<>();
        yellowData.put("name", "黄色预警");
        yellowData.put("value", yellowNum);
        yellowData.put("labelShow", false);

        Map<String, Object> redData = new HashMap<>();
        redData.put("name", "红色预警");
        redData.put("value", redNum);
        redData.put("labelShow", false);

        List<Map<String, Object>> dataList = new ArrayList<>();
        dataList.add(normalData);
        dataList.add(yellowData);
        dataList.add(redData);

        Map<String, Object> series = new HashMap<>();
        series.put("data", dataList);

        List<Map<String, Object>> seriesList = new ArrayList<>();
        seriesList.add(series);

        Map<String, Object> result = new HashMap<>();
        result.put("series", seriesList);

        return R.ok().put("result", result).put("list",list);
    }

    //查找预警表中每个object中的最新数据
    public List<AlarmEntity> ListNew(AlarmEntity alarmEntity){
        List<AlarmEntity> list = alarmService.QueryNewAlarm(alarmEntity.getProjectId());
        if(list.size()!=0){
            List<AlarmEntity> resultList = new ArrayList<>();
            for(int i = 0;i < list.size(); i++){
                boolean result = false;
                if(list.get(i).getSurveyType()==1){//displacement
                    result = displacementService.QueryDisplacementGreaterThanBegin(list.get(i).getObjectId(),list.get(i).getPeriodBegin());
                }else if(list.get(i).getSurveyType()==2 || list.get(i).getSurveyType()==4 ||list.get(i).getSurveyType()==12){//EarthSubsidence
                    result = earthSubsidenceService.QueryEarthSubsidenceGreaterThanBegin(list.get(i).getObjectId(),list.get(i).getPeriodBegin());
                }else if(list.get(i).getSurveyType()==3){//Inclinometer
                    result = inclinometerService.QueryInclinometerGreaterThanBegin(list.get(i).getObjectId(),list.get(i).getPeriodBegin());
                }else if(list.get(i).getSurveyType()==5){//Extend
                    result = extendService.QueryExtendGreaterThanBegin(list.get(i).getObjectId(),list.get(i).getPeriodBegin());
                }else if(list.get(i).getSurveyType()==6|| list.get(i).getSurveyType()==7|| list.get(i).getSurveyType()==8|| list.get(i).getSurveyType()==9|| list.get(i).getSurveyType()==10|| list.get(i).getSurveyType()==11){//power
                    result = powerService.QueryPowerGreaterThanBegin(list.get(i).getObjectId(),list.get(i).getPeriodBegin());
                }
                if(result){
                    resultList.add(list.get(i));
                }
            }
            return resultList;
        }
        return list;
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加报警信息")
    public R add(@LoginUser AppUserEntity user, @RequestBody AlarmEntity alarmEntity) {

        alarmEntity.setAlarmTime(DateUtil.nowDateTime());
        if (alarmService.save(alarmEntity)) {
            return R.ok().put("result", alarmEntity);
        }
        else {
            return R.error("添加失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除报警记录")
    public R delete(@LoginUser AppUserEntity user, @RequestBody AlarmEntity alarmEntity){
        alarmEntity.setStatus(99);//99-删除状态
        alarmService.updateById(alarmEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改")
    public R edit(@LoginUser AppUserEntity user, @RequestBody AlarmEntity alarmEntity){
        alarmService.updateById(alarmEntity);
        return R.ok().put("result", "更新成功!");
    }

    @Login
    @PostMapping("/warningAnalyse")
    @ApiOperation("预警分析页面")
    public R warningAnalyse(@LoginUser AppUserEntity user, @RequestBody AlarmEntity alarmEntity){
        List<TimelineData> timelineList = new ArrayList<>();
        //先获取所有的周期数据
        List<PeriodEntity> periodList=periodService.listValidPeriods(alarmEntity.getProjectId());
        for(int i = 0;i < periodList.size(); i++){
            List<AlarmEntity> alarmList = alarmService.QueryValidAlarmByPeriodId(alarmEntity.getProjectId(),periodList.get(i).getId());
            int index = i;
            String leftTime = periodList.get(i).getName();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String startTime = dateFormat.format(periodList.get(i).getBegin());
            String endTime = dateFormat.format(periodList.get(i).getEnd());
            String color = "blue";
            int objectNum = 0, normalNum = 0, yellowNum = 0, redNum = 0;
            List<WarningData> yellowDataList = new ArrayList<>();
            List<WarningData> redDataList = new ArrayList<>();
            int surveyType = alarmEntity.getSurveyType();
            for(int j = 0;j < alarmList.size(); j++){
                if(alarmList.get(j).getSurveyType() == surveyType){
                   WarningData warningData = null;
                    if(surveyType == 1){//Displacement
                        DisplacementEntity displacementEntity = displacementService.QueryValidDisplacementByPeriodId(alarmList.get(j).getObjectId(),alarmList.get(j).getPeriodId());
                        warningData = new WarningData(j, alarmList.get(j).getObjectName(), "累计变化值：" + displacementEntity.getValueAccumulative() + "mm", "变化速率：" + displacementEntity.getSpeed() + "mm/d");
                    }else if(surveyType == 2||surveyType == 4||surveyType == 12){
                        EarthSubsidenceEntity earthSubsidenceEntity = earthSubsidenceService.QueryValidEarthSubsidenceEntityByPeriodId(alarmList.get(j).getObjectId(),alarmList.get(j).getPeriodId());
                        warningData = new WarningData(j, alarmList.get(j).getObjectName(), "累计变化值：" + earthSubsidenceEntity.getValueAccumulative() + "mm", "变化速率：" + earthSubsidenceEntity.getSpeed() + "mm/d");
                    }else if(surveyType == 3){
                        InclinometerEntity inclinometerEntity = inclinometerService.QueryValidInclinometerEntityByPeriodId(alarmList.get(j).getObjectId(),alarmList.get(j).getPeriodId());
                        warningData = new WarningData(j, alarmList.get(j).getObjectName(), "","");
                    }else if(surveyType == 5){
                        ExtendEntity extendEntity = extendService.QueryValidExtendEntityByPeriodId(alarmList.get(j).getObjectId(),alarmList.get(j).getPeriodId());
                        warningData = new WarningData(j, alarmList.get(j).getObjectName(), "倾斜量：" + extendEntity.getInclination() + "m", "倾斜率：" + extendEntity.getInclinationRate() + "‰");
                    }else if(surveyType == 6||surveyType == 7||surveyType == 8||surveyType == 9||surveyType == 10||surveyType == 11){
                        PowerEntity powerEntity = powerService.QueryValidPowerEntityByPeriodId(alarmList.get(j).getObjectId(),alarmList.get(j).getPeriodId());
                        warningData = new WarningData(j, alarmList.get(j).getObjectName(), "应力累计变化值：" + powerEntity.getPowerAccumulative() + "KN", "应力变化速率：" + powerEntity.getSpeed() + "KN/d");
                    }
                    if(alarmList.get(j).getAlarmType()==1){
                        yellowDataList.add(warningData);
                        yellowNum++;
                    }else if(alarmList.get(j).getAlarmType()==2){
                        redDataList.add(warningData);
                        redNum++;
                    }
                }
            }
            if(surveyType == 1){//Displacement
                objectNum = displacementService.QueryDisplacementWithObjectName(periodList.get(i).getId()).size();
            }else if(surveyType == 2||surveyType == 4||surveyType == 12){
                objectNum = earthSubsidenceService.QueryEarthSubsidenceWithObjectName(periodList.get(i).getId(),surveyType).size();
            }else if(surveyType == 3){
                objectNum = inclinometerService.QueryInclinometerWithObjectName(periodList.get(i).getId()).size();
            }else if(surveyType == 5){
                objectNum = extendService.QueryExtendWithObjectName(periodList.get(i).getId()).size();
            }else if(surveyType == 6||surveyType == 7||surveyType == 8||surveyType == 9||surveyType == 10||surveyType == 11){
                objectNum = powerService.QueryPowerWithObjectName(periodList.get(i).getId(),surveyType).size();
            }
            normalNum = objectNum - yellowNum - redNum;
            TimelineData timeline = new TimelineData(index,leftTime,startTime,endTime,color,yellowDataList,redDataList,objectNum,normalNum,yellowNum,redNum);
            timelineList.add(timeline);
        }
        return R.ok().put("result", timelineList);
    }

    @Login
    @PostMapping("/judgeAlarm")
    @ApiOperation("判断预警情况")
    public R judgeAlarm(@LoginUser AppUserEntity user, @RequestBody AlarmEntity alarmEntity){
        //先获取预警值
        EarlyWarningEntity earlyWarningEntity = earlyWarningService.selectByObjectId(alarmEntity.getProjectId(),alarmEntity.getObjectId(),alarmEntity.getSurveyType());
        if(earlyWarningEntity==null){//不存在预警值
            return R.ok().put("result", -1);
        }
        //根据objectId和surveyType获取周期数据
        int surveyType = alarmEntity.getSurveyType();
        long objectId = alarmEntity.getObjectId();
        List<AlarmEntity> alarmList = alarmService.selectAlarmByObjectId(alarmEntity.getProjectId(),objectId,surveyType);
        for(int i = 0; i < alarmList.size(); i++){
            alarmList.get(i).setStatus(99);
            alarmService.updateById(alarmList.get(i));
        }
        double warningA1 = earlyWarningEntity.getWarningA1();
        double warningA2 = earlyWarningEntity.getWarningA2();
        double warningB1 = earlyWarningEntity.getWarningB1();
        double warningB2 = earlyWarningEntity.getWarningB2();
        List<AlarmEntity> alarmEntityList = new LinkedList<>();
        if(surveyType == 1){
            List<DisplacementEntity> list = displacementService.QueryDisplacementWithPeriod(objectId);
            for(int i = 0; i<list.size();i++){
                int alarmType=0;
                if(Math.abs(list.get(i).getValueAccumulative())>=warningA2||Math.abs(list.get(i).getSpeed())>=warningB2){
                    alarmType=2;
                }else if(Math.abs(list.get(i).getValueAccumulative())>=warningA1||Math.abs(list.get(i).getSpeed())>=warningB1){
                    alarmType=1;
                }
                if(alarmType!=0){//有超限数据
                    AlarmEntity entity = new AlarmEntity();
                    entity.setProjectId(alarmEntity.getProjectId());
                    entity.setObjectId(objectId);
                    entity.setPeriodId(list.get(i).getPeriodId());
                    entity.setSurveyType(surveyType);
                    entity.setAlarmType(alarmType);
                    entity.setAlarmTime(DateUtil.nowDateTime());
                    entity.setStatus(0);
                    alarmService.save(entity);
                    alarmEntityList.add(entity);
                }
            }
        }else if(surveyType == 2||surveyType == 4||surveyType == 12){
            List<EarthSubsidenceEntity> list = earthSubsidenceService.EarthSubsidenceWithPeriod(objectId);
            for(int i = 0; i<list.size();i++){
                int alarmType=0;
                if(Math.abs(list.get(i).getValueAccumulative())>=warningA2||Math.abs(list.get(i).getSpeed())>=warningB2){
                    alarmType=2;
                }else if(Math.abs(list.get(i).getValueAccumulative())>=warningA1||Math.abs(list.get(i).getSpeed())>=warningB1){
                    alarmType=1;
                }
                if(alarmType!=0){//有超限数据
                    AlarmEntity entity = new AlarmEntity();
                    entity.setProjectId(alarmEntity.getProjectId());
                    entity.setObjectId(objectId);
                    entity.setPeriodId(list.get(i).getPeriodId());
                    entity.setSurveyType(surveyType);
                    entity.setAlarmType(alarmType);
                    entity.setAlarmTime(DateUtil.nowDateTime());
                    entity.setStatus(0);
                    alarmService.save(entity);
                    alarmEntityList.add(entity);
                }
            }
        }else if(surveyType == 3){
            List<InclinometerEntity> list = inclinometerService.QueryInclinometerWithPeriod(objectId);
            for(int i = 0; i<list.size();i++){
                int alarmType=0;
                String[] speedList = list.get(i).getSpeed().split("\\s+");
                String[] AccumulativeList = list.get(i).getValueAccumulative().split("\\s+");
                for (int j = 0; j < speedList.length; j++) {
                    double speed = Math.abs(Double.parseDouble(speedList[j]));
                    double accumulative = Math.abs(Double.parseDouble(AccumulativeList[j]));
                    if(speed>=warningA2||accumulative>=warningB2){
                        alarmType=2;
                        break;
                    }else if(speed>=warningA1||accumulative>=warningB1){
                        alarmType=1;
                    }
                }
                if(alarmType!=0){//有超限数据
                    AlarmEntity entity = new AlarmEntity();
                    entity.setProjectId(alarmEntity.getProjectId());
                    entity.setObjectId(objectId);
                    entity.setPeriodId(list.get(i).getPeriodId());
                    entity.setSurveyType(surveyType);
                    entity.setAlarmType(alarmType);
                    entity.setAlarmTime(DateUtil.nowDateTime());
                    entity.setStatus(0);
                    alarmService.save(entity);
                    alarmEntityList.add(entity);
                }
            }
        }else if(surveyType == 5){
            List<ExtendEntity> list = extendService.QueryExtendWithPeriod(objectId);
            for(int i = 0; i<list.size();i++){
                int alarmType=0;
                if(list.get(i).getInclination()>=warningA2||list.get(i).getInclinationRate()>=warningB2){
                    alarmType=2;
                }else if(list.get(i).getInclination()>=warningA1||list.get(i).getInclinationRate()>=warningB1){
                    alarmType=1;
                }
                if(alarmType!=0){//有超限数据
                    AlarmEntity entity = new AlarmEntity();
                    entity.setProjectId(alarmEntity.getProjectId());
                    entity.setObjectId(objectId);
                    entity.setPeriodId(list.get(i).getPeriodId());
                    entity.setSurveyType(surveyType);
                    entity.setAlarmType(alarmType);
                    entity.setAlarmTime(DateUtil.nowDateTime());
                    entity.setStatus(0);
                    alarmService.save(entity);
                    alarmEntityList.add(entity);
                }
            }
        }else if(surveyType == 6||surveyType == 7||surveyType == 8||surveyType == 9||surveyType == 10||surveyType == 11){
            List<PowerEntity> list = powerService.QueryPowerWithPeriod(objectId);
            for(int i = 0; i<list.size();i++){
                int alarmType=0;
                if(Math.abs(list.get(i).getPowerAccumulative())>=warningA2||Math.abs(list.get(i).getSpeed())>=warningB2){
                    alarmType=2;
                }else if(Math.abs(list.get(i).getPowerAccumulative())>=warningA1||Math.abs(list.get(i).getSpeed())>=warningB1){
                    alarmType=1;
                }
                if(alarmType!=0){//有超限数据
                    AlarmEntity entity = new AlarmEntity();
                    entity.setProjectId(alarmEntity.getProjectId());
                    entity.setObjectId(objectId);
                    entity.setPeriodId(list.get(i).getPeriodId());
                    entity.setSurveyType(surveyType);
                    entity.setAlarmType(alarmType);
                    entity.setAlarmTime(DateUtil.nowDateTime());
                    entity.setStatus(0);
                    alarmService.save(entity);
                    alarmEntityList.add(entity);
                }
            }
        }
        return R.ok().put("result", alarmEntityList);
    }

    @Login
    @PostMapping("/riskAssess")
    @ApiOperation("风险评估")
    public R riskAssess(@LoginUser AppUserEntity user, @RequestBody ProjectEntity projectEntity){
        Long projectId = projectEntity.getId();
        //获取该项目的所有周期
        List<PeriodEntity> periodList = periodService.listValidPeriods(projectId);
        for(int i = 0; i<periodList.size(); i++){
            RiskAssessInfo info = new RiskAssessInfo();
            info.setProjectId(projectId);
            info.setPeriodId(periodList.get(i).getId());
            double[] result = riskAssess(info);
            periodList.get(i).setRiskProbability(result[0]);
            periodList.get(i).setDegree((int) result[1]);
        }
        return R.ok().put("result", periodList);
    }

    public double[] riskAssess(RiskAssessInfo info){
        //通过项目获取项目的风险评估值
        //根据四种测量类型的数据进行评估 水平位移1 沉降2 支撑轴力8 地下水位12
        //查找当前周期中 监测值与预警值比值最大的数据
        //水平位移 根据周期id获取当前周期所有的测量数据 并返回预警值
        List<DisplacementEntity> list1 = displacementService.QueryDisplacementWithObjectName(info.getPeriodId());
        double maxDis = 0;
        for(int i = 0; i < list1.size(); i++ ){
            RiskAssessDataInfo riskAssessDataInfo = new RiskAssessDataInfo();
            //获取预警值
            EarlyWarningEntity earlyWarningEntity = earlyWarningService.selectByObjectId(info.getProjectId(),list1.get(i).getObjectId(),1);
            riskAssessDataInfo.setSpeed(list1.get(i).getSpeed());
            riskAssessDataInfo.setAccumulative(list1.get(i).getValueAccumulative());
            riskAssessDataInfo.setAccumulativeWarning1(earlyWarningEntity.getWarningA1());
            riskAssessDataInfo.setAccumulativeWarning2(earlyWarningEntity.getWarningA2());
            riskAssessDataInfo.setSpeedWarning1(earlyWarningEntity.getWarningB1());
            riskAssessDataInfo.setSpeedWarning2(earlyWarningEntity.getWarningB2());
            riskAssessDataInfo.setResult(0.25*getRatio(riskAssessDataInfo.getSpeed(),riskAssessDataInfo.getSpeedWarning1())+0.75*getRatio(riskAssessDataInfo.getAccumulative(),riskAssessDataInfo.getAccumulativeWarning1()));
            if(riskAssessDataInfo.getResult()>maxDis){
                maxDis = riskAssessDataInfo.getResult();
            }
        }
        //沉降
        List<EarthSubsidenceEntity> list2 = earthSubsidenceService.QueryEarthSubsidenceWithObjectName(info.getPeriodId(),2);
        double maxEarth = 0;
        for(int i = 0; i < list2.size(); i++ ){
            RiskAssessDataInfo riskAssessDataInfo = new RiskAssessDataInfo();
            //获取预警值
            EarlyWarningEntity earlyWarningEntity = earlyWarningService.selectByObjectId(info.getProjectId(),list2.get(i).getObjectId(),2);
            riskAssessDataInfo.setSpeed(list2.get(i).getValueOne());
            riskAssessDataInfo.setAccumulative(list2.get(i).getValueAccumulative());
            riskAssessDataInfo.setSpeedWarning1(earlyWarningEntity.getWarningB1());
            riskAssessDataInfo.setSpeedWarning2(earlyWarningEntity.getWarningB2());
            riskAssessDataInfo.setAccumulativeWarning1(earlyWarningEntity.getWarningA1());
            riskAssessDataInfo.setAccumulativeWarning2(earlyWarningEntity.getWarningA2());
            riskAssessDataInfo.setResult(0.333*getRatio(riskAssessDataInfo.getSpeed(),riskAssessDataInfo.getSpeedWarning1())+0.667*getRatio(riskAssessDataInfo.getAccumulative(),riskAssessDataInfo.getAccumulativeWarning1()));
            if(riskAssessDataInfo.getResult()>maxEarth){
                maxEarth = riskAssessDataInfo.getResult();
            }
        }
        //支撑轴力
        List<PowerEntity> list3 = powerService.QueryPowerWithObjectName(info.getPeriodId(),8);
        double maxPower = 0;
        for(int i = 0; i < list3.size(); i++ ){
            RiskAssessDataInfo riskAssessDataInfo = new RiskAssessDataInfo();
            //获取预警值
            EarlyWarningEntity earlyWarningEntity = earlyWarningService.selectByObjectId(info.getProjectId(),list3.get(i).getObjectId(),8);
            riskAssessDataInfo.setSpeed(list3.get(i).getSpeed());
            riskAssessDataInfo.setAccumulative(list3.get(i).getPowerAccumulative());
            riskAssessDataInfo.setAccumulativeWarning1(earlyWarningEntity.getWarningA1());
            riskAssessDataInfo.setAccumulativeWarning2(earlyWarningEntity.getWarningA2());
            riskAssessDataInfo.setSpeedWarning1(earlyWarningEntity.getWarningB1());
            riskAssessDataInfo.setSpeedWarning2(earlyWarningEntity.getWarningB2());
            riskAssessDataInfo.setResult(getRatio(riskAssessDataInfo.getAccumulative(),riskAssessDataInfo.getAccumulativeWarning1()));
            if(riskAssessDataInfo.getResult()>maxPower){
                maxPower = riskAssessDataInfo.getResult();
            }
        }
        //地下水位
        List<EarthSubsidenceEntity> list4 = earthSubsidenceService.QueryEarthSubsidenceWithObjectName(info.getPeriodId(),12);
        double maxWater = 0;
        for(int i = 0; i < list4.size(); i++ ){
            RiskAssessDataInfo riskAssessDataInfo = new RiskAssessDataInfo();
            //获取预警值
            EarlyWarningEntity earlyWarningEntity = earlyWarningService.selectByObjectId(info.getProjectId(),list2.get(i).getObjectId(),2);
            riskAssessDataInfo.setSpeed(list4.get(i).getSpeed());
            riskAssessDataInfo.setAccumulative(list4.get(i).getValueAccumulative());
            riskAssessDataInfo.setAccumulativeWarning1(earlyWarningEntity.getWarningA1());
            riskAssessDataInfo.setAccumulativeWarning1(earlyWarningEntity.getWarningA2());
            riskAssessDataInfo.setSpeedWarning1(earlyWarningEntity.getWarningB1());
            riskAssessDataInfo.setSpeedWarning2(earlyWarningEntity.getWarningB2());
            riskAssessDataInfo.setResult(0.25*getRatio(riskAssessDataInfo.getSpeed(),riskAssessDataInfo.getSpeedWarning1())+0.75*getRatio(riskAssessDataInfo.getAccumulative(),riskAssessDataInfo.getAccumulativeWarning1()));
            if(riskAssessDataInfo.getResult()>maxWater){
                maxWater = riskAssessDataInfo.getResult();
            }
        }
        //得到四种类型的最大值 接下来乘以权重 0.456 0.256 0.256 0.032
        double riskProbability = maxDis*0.456+maxEarth*0.256+maxPower*0.256+maxWater*0.032;
        int riskProbabilityLevel = 0;//风险概率等级
        if(riskProbability>=0&&riskProbability<0.2){
            riskProbabilityLevel=1;
        }else if(riskProbability<0.4){
            riskProbabilityLevel=2;
        }else if(riskProbability<0.6){
            riskProbabilityLevel=3;
        }else if(riskProbability<0.8){
            riskProbabilityLevel=4;
        }else if(riskProbability<=1){
            riskProbabilityLevel=5;
        }
        double[] result = {riskProbability,riskProbabilityLevel};
        return result;
    }

    public double getRatio(double value,double warning){
        double ratio = Math.abs(value) / Math.abs(warning);
        double riskProbability;

        if (ratio < 0.6) {
            riskProbability = ratio * 0.2 / 0.6;
        } else if (ratio < 0.7) {
            riskProbability = (ratio - 0.6) * (0.4 / 0.1) + 0.2;
        } else if (ratio < 0.8) {
            riskProbability = (ratio - 0.7) * (0.4 / 0.1) + 0.4;
        } else if (ratio <= 1) {
            riskProbability = (ratio - 0.8) * (0.2 / 0.2) + 0.6;
        } else {
            riskProbability = Math.min((ratio - 1) * (0.2 / 0.2) + 0.8, 1);
        }
        return riskProbability;
    }

}
