package com.cloud.mall.sms.listener;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : breeze
 * @date : 2020/10/17
 * @description : 短信服务mq监听类
 */
@Component
public class SmsListener {

    @Value("${sms.accessKeyId}")
    private String accessKeyId;
    @Value("${sms.accessSecret}")
    private String accessSecret;

    private static final String SYS_DOMAIN = "dysmsapi.aliyuncs.com";
    private static final String SYS_VERSION = "2017-05-25";
    private static final String SYS_ACTION = "SendSms";
    private static final String REGION_ID = "cn-hangzhou";
    private static final String SIGN_NAME = "团子";
    private static final String TEMPLATE_CODE = "SMS_180060107";

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "MALL-MESSAGE-QUEUE", durable = "true"),
            exchange = @Exchange(value = "mall.message.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = {"message.*"}
    ))
    public void listenerCode(Map<String, String> map){
        //手机号
        String phone = map.get("phone");
        String code = map.get("code");

        Map<String, String> templateParamMap = new HashMap<>(2);
        templateParamMap.put("code", code);

        DefaultProfile profile = DefaultProfile.getProfile(REGION_ID, accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(SYS_DOMAIN);
        request.setSysVersion(SYS_VERSION);
        request.setSysAction(SYS_ACTION);
        request.putQueryParameter("RegionId", REGION_ID);
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", SIGN_NAME);
        request.putQueryParameter("TemplateCode", TEMPLATE_CODE);
        request.putQueryParameter("TemplateParam", JSON.toJSONString(templateParamMap));
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }
}
