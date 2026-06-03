
package io.cug.modules.gom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.gom.entity.ImageEntity;
import io.cug.modules.gom.entity.ReportEntity;
import org.apache.ibatis.annotations.Param;

import java.awt.*;
import java.util.List;

public interface ImageService extends IService<ImageEntity> {

    List<ImageEntity> selectImageByProjectId(long projectId, int type);
    List<ImageEntity> selectCesiumFile(long nProjectID);
}

