package org.example.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ApiUtils {


    public static String appSign() {
        String str = "123";
        try {
            String url = "http://10.177.71.116:8809/cert/appSign";

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            NameValuePair pair = new BasicNameValuePair("appId", "c79b82b4e32d69b130786c63c2a4edb8");
            NameValuePair pair2 = new BasicNameValuePair("appName", "newjingfen");
            params.add(pair);
            params.add(pair2);

            JSONObject jsonObject = sendReqPost(url, params);



            if (jsonObject == null) {
                return "null";
            }
            log.info(jsonObject.toJSONString());
            str = jsonObject.toJSONString();

            String appToken = jsonObject.getString("token");
            log.info("appToken: {}", appToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }


    public static JSONObject sendReqPost(String url, List<NameValuePair> body) throws IOException {
        if (url == null) {
            return null;
        }

        HttpPost httpPost = new HttpPost(url);

        CloseableHttpClient httpClient = HttpClients.custom().build();

        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");

        httpPost.setEntity(new UrlEncodedFormEntity(body,"UTF-8"));

        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

        JSONObject jsonObject = entityTOJSONObject(httpResponse);
//    log.info("获取返回值");
//    log.info(jsonObject.toJSONString());
        return jsonObject;
    }

    public static JSONObject entityTOJSONObject(CloseableHttpResponse httpResponse)
            throws IOException {
        HttpEntity entity = httpResponse.getEntity();
        String entityString = EntityUtils.toString(entity);
        return JSONObject.parseObject(entityString);
    }
}
