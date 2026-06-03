package io.cug.modules.gom.utils;

import com.aliyun.iot20180120.models.PubRequest;
import com.aliyun.iot20180120.models.PubResponse;
import com.aliyun.iot20180120.models.QueryDevicePropertiesDataRequest;
import com.aliyun.iot20180120.models.QueryDevicePropertiesDataResponse;
import com.aliyun.tea.TeaException;
import io.cug.modules.gom.param.SensorInfo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class AliMQTTUtils {
    private String accessKeyId = "KEY";
    private String accessKeySecret = "KEY2";

    /**
     * 使用AK&SK初始化Client。
     */
    public com.aliyun.iot20180120.Client createClient() throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        // Endpoint 请参考 https://api.aliyun.com/product/Iot
        config.endpoint = "iot.cn-shanghai.aliyuncs.com";
        return new com.aliyun.iot20180120.Client(config);
    }


    // 将16进制字符串转换为字节数组
    public byte[] hexToBinary(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            // 将每一对16进制字符转换为字节
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i+1), 16));
        }

        return data;
    }

    // 对字节数组进行Base64编码
    public String binaryToBase64(byte[] binaryData) {
        return Base64.getEncoder().encodeToString(binaryData);
    }

    //发送指令到服务器
    public void pubToIOT(com.aliyun.iot20180120.Client client,String deviceName) throws Exception {
        String hexString = "010300000002C40B"; // 示例输入
        byte[] binaryData = hexToBinary(hexString);
        String messageContent = binaryToBase64(binaryData);
        String topicFullName = "/k0m2t50eDR0/" + deviceName +"/user/get";
        PubRequest pubRequest = new PubRequest()
                .setIotInstanceId("iot-06z00d4ca0qnor9")
                .setProductKey("k0m2t50eDR0")
                .setMessageContent(messageContent)
                .setTopicFullName(topicFullName)
                .setQos(0);
        try{
            PubResponse response =  client.pub(pubRequest);
        }catch (TeaException error){
            System.out.println(error.getCode());
            System.out.println(error.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public SensorInfo queryDevicePropertiesData(com.aliyun.iot20180120.Client client, String deviceName, long endTime){
        List<String> identifier = new ArrayList<>();
        // 添加元素
        identifier.add("CurrentHumidity");
        identifier.add("CurrentTemperature");
        long startTime = System.currentTimeMillis();
        QueryDevicePropertiesDataRequest queryDevicePropertiesDataRequest = new QueryDevicePropertiesDataRequest()
                .setIotInstanceId("iot-06z00d4ca0qnor9")
                .setProductKey("k0m2t50eDR0")
                .setDeviceName(deviceName)
                .setAsc(0)
                .setStartTime(startTime)
                .setEndTime(endTime)
                .setIdentifier(identifier)
                .setPageSize(1);
        SensorInfo info = new SensorInfo();
        try{
            QueryDevicePropertiesDataResponse response = client.queryDevicePropertiesData(queryDevicePropertiesDataRequest);
            if(!response.body.propertyDataInfos.propertyDataInfo.isEmpty()){
                long surveyTime = response.body.propertyDataInfos.propertyDataInfo.get(0).list.propertyInfo.get(0).time;
                double humidity = Double.parseDouble(response.body.propertyDataInfos.propertyDataInfo.get(0).list.propertyInfo.get(0).value);
                double temperature = Double.parseDouble(response.body.propertyDataInfos.propertyDataInfo.get(1).list.propertyInfo.get(0).value);
                info.setSurveyTime(new Date(surveyTime));
                info.setHumidity(humidity);
                info.setTemperature(temperature);
            }
        }catch (TeaException error){
            System.out.println(error.getCode());
            System.out.println(error.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }
        return info;
    }

}
