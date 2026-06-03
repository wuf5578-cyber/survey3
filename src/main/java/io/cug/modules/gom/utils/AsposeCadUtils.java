package io.cug.modules.gom.utils;

import com.aspose.cad.Image;
import com.aspose.cad.imageoptions.CadRasterizationOptions;
import com.aspose.cad.imageoptions.ImageOptionsBase;
import com.aspose.cad.imageoptions.JpegOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@RestController
@RequestMapping("gom/cad")
public class AsposeCadUtils {
    // 上传 DWG 文件并转换为 JPEG
    @PostMapping("/convert")
    public ResponseEntity<String> convertCadToImage(@RequestParam("file")MultipartFile file) throws IOException {
        // 将上传的文件保存到本地临时目录
        File tempFile = File.createTempFile("uploaded-", ".dwg");
        file.transferTo(tempFile);
        // 使用 Aspose.CAD 加载 DWG 文件
        Image image = Image.load(tempFile.getAbsolutePath());

        //ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // 设置光栅化选项
        CadRasterizationOptions rasterizationOptions = new CadRasterizationOptions();
        rasterizationOptions.setPageWidth(1200); // 设置输出图像的宽度
        rasterizationOptions.setPageHeight(1200); // 设置输出图像的高度
        // 设置输出格式为 JPEG
        ImageOptionsBase options = new JpegOptions();
        options.setVectorRasterizationOptions(rasterizationOptions);
        // 设置保存图像的文件名
        String outputFileName = "projectImage/"+ "converted-" + System.currentTimeMillis();
        File outputFile = File.createTempFile(outputFileName,".jpg");
        // 将图像保存到指定目录
        image.save(outputFile.getAbsolutePath(), options);

        InputStream inputStream = Files.newInputStream(outputFile.toPath());

        AliOSSUtils aliOSSUtils = new AliOSSUtils();
        String url = aliOSSUtils.upload1(inputStream,outputFileName+".jpg");
        System.out.println("文件上传完成，url:" + url);
         // 返回保存的文件路径或文件名
        // 构造响应 JSON 格式
        String jsonResponse = String.format(
                "{\"msg\":\"success\",\"result\":\"%s\",\"code\":0}",
                url
        );
        // 返回 JSON 响应
        return ResponseEntity.ok().body(jsonResponse);
    }
}