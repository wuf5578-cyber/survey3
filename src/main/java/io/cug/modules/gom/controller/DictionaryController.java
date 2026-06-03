package io.cug.modules.gom.controller;

import io.cug.common.utils.DateUtil;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.DepartmentEntity;
import io.cug.modules.gom.entity.DictionaryEntity;
import io.cug.modules.gom.service.DepartmentService;
import io.cug.modules.gom.service.DictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "系统字典表")
@RestController
@RequestMapping("gom/Dictionary")
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    @Login
    @PostMapping("/List")
    @ApiOperation("通过父节点查找字典")
    public R classList(@LoginUser AppUserEntity user,@RequestBody DictionaryEntity dictionaryEntity){
        List<DictionaryEntity> list = dictionaryService.QueryValidDictionaryByParentId(dictionaryEntity.getParentId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加字典")
    public R add(@LoginUser AppUserEntity user, @RequestBody DictionaryEntity dictionaryEntity) {

        if (dictionaryService.save(dictionaryEntity)) {
            return R.ok().put("result", dictionaryEntity);
        }
        else {
            return R.error("添加失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除字典")
    public R delete(@LoginUser AppUserEntity user, @RequestBody DictionaryEntity dictionaryEntity){
        dictionaryEntity.setStatus(99);//99-删除状态
        dictionaryService.updateById(dictionaryEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改字典")
    public R edit(@LoginUser AppUserEntity user, @RequestBody DictionaryEntity dictionaryEntity){
        dictionaryService.updateById(dictionaryEntity);
        return R.ok().put("result", "更新成功!");
    }
}
