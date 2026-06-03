
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.common.utils.PageUtils;
import io.cug.common.utils.Query;
import io.cug.modules.gom.dao.ObjectDao;
import io.cug.modules.gom.entity.ObjectEntity;
import io.cug.modules.gom.service.ObjectService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("objectService")
public class ObjectServiceImpl extends ServiceImpl<ObjectDao, ObjectEntity> implements ObjectService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ObjectEntity> page = this.page(
                new Query<ObjectEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<ObjectEntity> QueryValidObjectTree(Long nProjectID){

        //数据查询
        List<ObjectEntity> list = baseMapper.QueryValidObject(nProjectID);

        //新建一个用于接收数据的list
        List<ObjectEntity> resultList = new ArrayList<>();
        for (ObjectEntity result : list) {
            if (result.getParentId() == 0) {
                //调用方法给子类添加数据
                resultList.add(getMenuTree(result, list));
            }
        }
        return resultList;
    }

    @Override
    public ObjectEntity QueryValidObjectByName(Long nProjectID,String name){

        //数据查询
        ObjectEntity objectEntity = baseMapper.QueryValidObjectByName(nProjectID,name);

        return objectEntity;
    }

    @Override
    public List<ObjectEntity> SelectTunnelObject(Long projectId){
        return baseMapper.SelectTunnelObject(projectId);
    }

    @Override
    public ObjectEntity QueryObjectById(Long id){
        return baseMapper.QueryObjectById(id);
    }

    @Override
    public List<ObjectEntity> SelectObjectWithWarning(Long projectId,long surveyType){
        return baseMapper.SelectObjectWithWarning(projectId,surveyType);
    }
    @Override
    public ObjectEntity SelectObjectWithWarningById(Long objectId,long surveyType){
        return baseMapper.SelectObjectWithWarningById(objectId,surveyType);
    }

    private ObjectEntity getMenuTree(ObjectEntity result, List<ObjectEntity> list) {
        for (ObjectEntity menu : list) {
            //如果父类主键等于传过来实体类的ID
            if (menu.getParentId() == (long)result.getId()) {
                if (result.getChildObjectEntity() == null) {
                    result.setChildObjectEntity(new ArrayList<>());
                }
                // 递归调用
                result.getChildObjectEntity().add(getMenuTree(menu, list));
            }
        }
        return result;
    }

    @Override
    public int queryValidObjectNum(Long nProjectID){
        return baseMapper.QueryValidSurveyObject(nProjectID).size();
    }

}