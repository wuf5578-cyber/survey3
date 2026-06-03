
package io.cug.modules.gom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.gom.dao.ImageDao;
import io.cug.modules.gom.dao.ReportDao;
import io.cug.modules.gom.entity.ImageEntity;
import io.cug.modules.gom.entity.ReportEntity;
import io.cug.modules.gom.service.ImageService;
import io.cug.modules.gom.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("imageService")
public class ImageServiceImpl extends ServiceImpl<ImageDao, ImageEntity> implements ImageService {

    @Override
    public List<ImageEntity> selectImageByProjectId(long projectId,int type){

        //数据查询
        List<ImageEntity> list = baseMapper.selectImageByProjectId(projectId,type);

        return list;
    }

    @Override
    public List<ImageEntity> selectCesiumFile(long nProjectID){
        return baseMapper.selectCesiumFile(nProjectID);
    }


}