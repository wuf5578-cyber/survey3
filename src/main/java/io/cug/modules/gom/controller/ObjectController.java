package io.cug.modules.gom.controller;

import io.cug.common.utils.DateUtil;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.app.param.ProjectIDForm;
import io.cug.modules.gom.entity.EarlyWarningEntity;
import io.cug.modules.gom.entity.ObjectEntity;
import io.cug.modules.gom.entity.PointEntity;
import io.cug.modules.gom.service.EarlyWarningService;
import io.cug.modules.gom.service.ObjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "监测——监测目标点")
@RestController
@RequestMapping("gom/Object")
public class ObjectController {

    @Autowired
    private ObjectService ObjectService;
    @Autowired
    private EarlyWarningService earlyWarningService;

    @Login
    @PostMapping("/List")
    @ApiOperation("监测目标点列表")
    public R classList(@LoginUser AppUserEntity user, @RequestBody ProjectIDForm projectIDForm){
        List<ObjectEntity> list = ObjectService.QueryValidObjectTree(projectIDForm.getProjectId());
        if (list.size() < 1){
            //自动创建根节点
            ObjectEntity ObjectEntity = new ObjectEntity();
            ObjectEntity.setCreateByUserid( (long)user.getUid() );
            ObjectEntity.setCreateTime(DateUtil.nowDateTime());
            ObjectEntity.setProjectId( projectIDForm.getProjectId() );
            ObjectEntity.setParentId((long)0);
            ObjectEntity.setName("监测目标");
            ObjectService.save(ObjectEntity);
            list.add(ObjectEntity);
        }
        
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/tunnelObject")
    @ApiOperation("查找所有点")
    public R classTunnelObject(@LoginUser AppUserEntity user, @RequestBody ObjectEntity objectEntity){
        List<ObjectEntity> list = ObjectService.SelectTunnelObject(objectEntity.getProjectId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/FindIdByName")
    @ApiOperation("通过节点name查找id")
    public R findIdByName(@LoginUser AppUserEntity user, @RequestBody ObjectEntity objectEntity){
        ObjectEntity entity = ObjectService.QueryValidObjectByName(objectEntity.getProjectId(),objectEntity.getName());
        if (entity!=null){
            return R.ok().put("result", entity.getId());
        }else{
            return R.ok().put("result", -1);
        }
    }
    @Login
    @PostMapping("/SelectObjectWithWarning")
    @ApiOperation("列表测量点和他们的预警值")
    public R SelectObjectWithWarning(@LoginUser AppUserEntity user, @RequestBody PointEntity pointEntity){
        List<ObjectEntity> list=ObjectService.SelectObjectWithWarning(pointEntity.getProjectId(),pointEntity.getSurveyType());
        for(int i = 0; i<list.size();i++){
            EarlyWarningEntity earlyWarningEntity = earlyWarningService.selectValidEarlyWarningByObjectId(list.get(i).getId(),pointEntity.getSurveyType());
            if(earlyWarningEntity!=null){
                list.get(i).setWarningA1(earlyWarningEntity.getWarningA1());
                list.get(i).setWarningA2(earlyWarningEntity.getWarningA2());
                list.get(i).setWarningB1(earlyWarningEntity.getWarningB1());
                list.get(i).setWarningB2(earlyWarningEntity.getWarningB2());
            }else{
                list.get(i).setWarningA1(0);
                list.get(i).setWarningA2(0);
                list.get(i).setWarningB1(0);
                list.get(i).setWarningB2(0);
            }
        }
        return R.ok().put("result", list);
    }
    @Login
    @PostMapping("/SelectObjectWithWarningById")
    @ApiOperation("列表测量点和他们的预警值")
    public R SelectAllObjectWithWarning(@LoginUser AppUserEntity user, @RequestBody PointEntity pointEntity){
        ObjectEntity objectEntity=ObjectService.SelectObjectWithWarningById(pointEntity.getObjectId(),pointEntity.getSurveyType());
        return R.ok().put("result", objectEntity);
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加监测目标点")
    public R add(@LoginUser AppUserEntity user, @RequestBody ObjectEntity ObjectEntity) {

        ObjectEntity.setCreateByUserid( (long)user.getUid() );
        ObjectEntity.setCreateTime(DateUtil.nowDateTime());
        if (ObjectService.save(ObjectEntity)) {
            return R.ok().put("result", ObjectEntity);
        }
        else {
            return R.error("添加监测目标点失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除监测目标点")
    public R delete(@LoginUser AppUserEntity user, @RequestBody ObjectEntity ObjectEntity){
        ObjectEntity.setStatus(99);//99-删除状态
        ObjectService.updateById(ObjectEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改监测目标点")
    public R edit(@LoginUser AppUserEntity user, @RequestBody ObjectEntity ObjectEntity){
        ObjectService.updateById(ObjectEntity);
        return R.ok().put("result", "更新成功!");
    }

    @Login
    @PostMapping("/num")
    @ApiOperation("查找监测目标点个数")
    public R num(@LoginUser AppUserEntity user, @RequestBody ObjectEntity ObjectEntity){
        int num = ObjectService.queryValidObjectNum(ObjectEntity.getProjectId());
        return R.ok().put("result", num);
    }

    @Login
    @PostMapping("/getById")
    @ApiOperation("根据id查找object")
    public R getById(@LoginUser AppUserEntity user, @RequestBody ObjectEntity ObjectEntity){
        return R.ok().put("result", ObjectService.getById(ObjectEntity));
    }
}
