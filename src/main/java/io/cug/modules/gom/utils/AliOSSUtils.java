package io.cug.modules.gom.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component//标注一个类为Spring容器的Bean
public class AliOSSUtils {

    private String endpoint = "https://oss-cn-beijing.aliyuncs.com";

    private String accessKeyId = "KEY";

    private String accessKeySecret = "KEY2";

    private String bucketName = "monitoring-cug";

    /**
     * 实现上传图片到OSS
     */
    public String upload(MultipartFile file,String fileName) throws IOException {
        // 获取上传的文件的输入流
        InputStream inputStream = file.getInputStream();

        //上传文件到 OSS
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, fileName, inputStream);

        //文件访问路径
        String url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + fileName;
        // 关闭ossClient
        ossClient.shutdown();
        return url;// 把上传到oss的路径返回
    }

    public String upload1(InputStream inputStream, String fileName) throws IOException {
        // 获取上传的文件的输入流

        //上传文件到 OSS
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, fileName, inputStream);

        //文件访问路径
        String url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + fileName;
        // 关闭ossClient
        ossClient.shutdown();
        return url;// 把上传到oss的路径返回
    }

    /**
     * 实现删除OSS上指定文件
     */
    public String delete(String fileName) {
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        ossClient.deleteObject(bucketName,fileName);
        ossClient.shutdown();
        return "删除成功";
    }
}
