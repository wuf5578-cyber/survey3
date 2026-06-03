package io.cug.modules.gom.controller;

import cn.hutool.core.io.FileUtil;
import com.aspose.cad.Image;
import com.aspose.cad.imageoptions.PdfOptions;
import io.cug.common.utils.R;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.gom.utils.AliOSSUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Api(tags = "文件")
@RestController
@RequestMapping("gom/file")
public class FileController {

    @Value("${ip:localhost}")
    String ip;

    @Value("${server.port}")
    String port;

    @Autowired
    private AliOSSUtils aliOSSUtils;

    private static final String ROOT_PATH = System.getProperty("user.dir") + File.separator + "files";

    @Login
    @PostMapping("/upload")
    @ApiOperation("上传")
    public R upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();  //文件的原始名称
        String mainName = FileUtil.mainName(originalFilename);  //文件不包含后缀的名称
        String extName = FileUtil.extName(originalFilename);  //文件后缀名
        if (!FileUtil.exist(ROOT_PATH)){
            FileUtil.mkdir(ROOT_PATH);  //若当前文件的父级目录不存在，则创建
        }
        if (FileUtil.exist(ROOT_PATH + File.separator + originalFilename)){  //当前上传的文件已存在，则重命名
            originalFilename = mainName + System.currentTimeMillis() + "." + extName;
        }
        File saveFile = new File(ROOT_PATH + File.separator + originalFilename);
        file.transferTo(saveFile);  //存储文件到本地的磁盘
        String url = "http://" + ip + ":" + port + "/gom/file/download/" + originalFilename;
        return R.ok().put("result",url);  //返回文件存储的地址
    }

    @GetMapping("/download/{fileName}")
    @ApiOperation("下载")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        String filePath = ROOT_PATH + File.separator +fileName;
        if(!FileUtil.exist(filePath)){
            return;
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);  //数组是一个字节数组 文件的字节流数组
        outputStream.flush();
        outputStream.close();
    }

    @PostMapping("/uploadOSS")
    public R uploadOSS(MultipartFile file) throws IOException{
        // 避免文件覆盖
        String originalFilename = file.getOriginalFilename();
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = currentDateTime.format(formatter);
        String fileName = "projectImage/"+ formattedDateTime + FileUtil.mainName(originalFilename) + originalFilename.substring(originalFilename.lastIndexOf("."));
        System.out.println("文件上传，文件名为；" + fileName);
        String url = aliOSSUtils.upload(file,fileName);
        System.out.println("文件上传完成，url:" + url);
        return R.ok().put("result",url);
    }

    @PostMapping("/deleteOSSFile")
    public R deleteOSSFile(@RequestBody String url) throws IOException {
        //分割url
        String keyword = "projectImage";
        int index = url.indexOf(keyword);
        if(index!=-1){
            String subString = url.substring(index);
            String str = aliOSSUtils.delete(subString);
            return R.ok().put("result","删除成功");
        }else{
            return R.ok().put("result","失败");
        }
    }

    @PostMapping("/uploadCesiumOSS")
    public R uploadCesiumOSS(@RequestParam("file")MultipartFile file, @RequestParam("projectId") Long projectId) throws IOException{
        // 避免文件覆盖
        String originalFilename = file.getOriginalFilename();
        String fileName = "projectModule/project"+ projectId +"/" + FileUtil.mainName(originalFilename) + originalFilename.substring(originalFilename.lastIndexOf("."));
        System.out.println("文件上传，文件名为；" + fileName);
        String url = aliOSSUtils.upload(file,fileName);
        System.out.println("文件上传完成，url:" + url);
        return R.ok().put("result",url);
    }

    @PostMapping("/deleteCesiumOSSFile")
    public R deleteCesiumOSSFile(@RequestBody String url) throws IOException {
        //分割url
        String keyword = "projectModule";
        int index = url.indexOf(keyword);
        if(index!=-1){
            String subString = url.substring(index);
            String str = aliOSSUtils.delete(subString);
            return R.ok().put("result","删除成功");
        }else{
            return R.ok().put("result","失败");
        }
    }

    @PostMapping("/convert")
    public ResponseEntity<byte[]> convertCadToPdf(@RequestParam("file")MultipartFile file) {
        try {
            // 将文件内容转换为输入流
            byte[] fileBytes = file.getBytes();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(fileBytes);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            // 调用转换方法
            convertCadToPdf(inputStream, outputStream);
            // 设置响应头部
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=converted_file.pdf");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");
            // 返回 PDF 文件内容
            return ResponseEntity.ok().headers(headers).body(outputStream.toByteArray());
        }catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("转换失败".getBytes());
        }
    }

    private void convertCadToPdf(ByteArrayInputStream inputStream, ByteArrayOutputStream outputStream) {
        // 加载 DWG 文件
        Image objImage = Image.load(inputStream);

        // 创建 PdfOptions 对象
        PdfOptions pdfOptions = new PdfOptions();

        // 导出 DWG 文件为 PDF
        objImage.save(outputStream, pdfOptions);
        System.out.println("转换成功，输出字节流长度：" + outputStream.size());
    }
}
