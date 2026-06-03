package io.cug.modules.gom.controller;

import io.cug.common.utils.DateUtil;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.SensorEntity;
import io.cug.modules.gom.entity.TimerEntity;
import io.cug.modules.gom.param.SensorInfo;
import io.cug.modules.gom.param.SensorRangeInfo;
import io.cug.modules.gom.service.SensorService;
import io.cug.modules.gom.service.TimerService;
import io.cug.modules.gom.utils.AliMQTTUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


@Api(tags = "温湿度-数据")
@RestController
@RequestMapping("gom/Sensor")
@EnableScheduling
public class SensorController {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
    private final Map<Long, ScheduledFuture<?>> timers = new ConcurrentHashMap<>();

    @Autowired
    private SensorService sensorService;
    @Autowired
    private TimerService timerService;
    @Autowired
    private AliMQTTUtils aliMQTTUtils;

    private com.aliyun.iot20180120.Client client;


    @Login
    @PostMapping("/listNewest")
    @ApiOperation("获取最新数据")
    public R listNewest(@LoginUser AppUserEntity user, @RequestBody SensorEntity sensorEntity){
        SensorEntity th = sensorService.QueryNewestTH(sensorEntity.getDeviceId());
        return R.ok().put("result", th);
    }

    @Login
    @PostMapping("/list24")
    @ApiOperation("默认一天数据")
    public R classList(@LoginUser AppUserEntity user, @RequestBody SensorEntity sensorEntity){
        List<SensorEntity> list = sensorService.QueryDefaultTH(sensorEntity.getDeviceId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/listRange")
    @ApiOperation("范围数据")
    public R listRange(@LoginUser AppUserEntity user, @RequestBody SensorRangeInfo sensorRangeInfo){
        List<SensorEntity> list = sensorService.QueryTHWithTime(sensorRangeInfo.getDeviceId(), sensorRangeInfo.getStartTime(), sensorRangeInfo.getEndTime());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加")
    public R add(@LoginUser AppUserEntity user, @RequestBody SensorEntity sensorEntity) {
        if (sensorService.save(sensorEntity)) {
            return R.ok().put("result", sensorEntity);
        }
        else {
            return R.error("添加失败!");
        }
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改")
    public R edit(@LoginUser AppUserEntity user, @RequestBody SensorEntity sensorEntity){
        sensorService.updateById(sensorEntity);
        return R.ok().put("result", "更新成功!");
    }

    @Login
    @PostMapping("/start")
    @ApiOperation("开启一个定时器")
    public R start(@LoginUser AppUserEntity user, @RequestBody TimerEntity timerEntity) throws Exception {
        client = aliMQTTUtils.createClient();
        return R.ok().put("result", startTimer(timerEntity));
    }

    @Login
    @PostMapping("/stop")
    @ApiOperation("关闭一个定时器")
    public R stop(@LoginUser AppUserEntity user, @RequestBody TimerEntity timerEntity){
        stopTimer(timerEntity.getDeviceId());
        timerEntity.setStatus(99);
        timerService.updateById(timerEntity);
        return R.ok().put("result", 0);
    }


    // 启动定时器
    public TimerEntity startTimer(TimerEntity timerEntity) {
        long createTime = System.currentTimeMillis();
        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(() -> {
            System.out.println("定时器 " + timerEntity.getDeviceId() + " 执行时间: " + System.currentTimeMillis());
            // 你的定时任务逻辑
            try {
                aliMQTTUtils.pubToIOT(client, timerEntity.getDeviceName());
                SensorInfo info = aliMQTTUtils.queryDevicePropertiesData(client, timerEntity.getDeviceName(), createTime);
                //存入数据库
                if(info.getHumidity()==0&&info.getTemperature()==0&&info.getSurveyTime()==null){

                }else{
                    SensorEntity entity = new SensorEntity();
                    entity.setProjectId(timerEntity.getProjectId());
                    entity.setDeviceId(timerEntity.getDeviceId());
                    entity.setHumidity(info.getHumidity());
                    entity.setTemperature(info.getTemperature());
                    entity.setSurveyTime(info.getSurveyTime());
                    sensorService.save(entity);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 10, TimeUnit.SECONDS);
        timerEntity.setCreateTime(DateUtil.nowDateTime());
        timerService.save(timerEntity);
        timers.put(timerEntity.getDeviceId(), scheduledFuture);
        return timerEntity;
    }

    // 停止指定的定时器
    public void stopTimer(long deviceId) {
        ScheduledFuture<?> scheduledFuture = timers.get(deviceId);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false); // false表示不强制中断正在运行的任务
            timers.remove(deviceId);
        }
    }

    // 关闭所有定时器
    public void stopAllTimers() {
        for (long deviceId : timers.keySet()) {
            stopTimer(deviceId);
        }
        scheduler.shutdown(); // 可选择性关闭调度器
    }
}
