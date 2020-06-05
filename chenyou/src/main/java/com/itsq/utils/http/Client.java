package com.itsq.utils.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.itsq.pojo.dto.NormalBuyParamDTO;
import com.itsq.utils.RandUtil;
import com.itsq.utils.RandomUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

/**
 * HttpClient工具类
 */
@Component
public class  Client {

    /*
     *&#x8bbf;&#x95ee;url
     */
    private static String gserverUrl = "https://app.zbt.com";
    private static String appKey ="0b791fef5d1cc463edda79924704e8a7";
    private static String   language="zh_CN";
    private static final String DEFAULT_CHARSET = "UTF-8";
    public static void main(String[] args) {

       Map<String,String> param=new HashMap<>();
       /* String s = httpGetWithJSon("https://app.zbt.com/open/product/v1/search", param);
      Object succesResponse = JSON.parse(s);    //先转换成Object

        Map map = (Map)succesResponse;         //Object强转换为Map
        Object succesResponse1 = JSON.parse(map.get("data")+"");
        Map map1 = (Map)succesResponse1;

       List list= (List)map1.get("list");
        for (Object o : list) {
            System.out.println(o.toString());
        }*/

        JSONObject jsonObject=new JSONObject();

        jsonObject.put("app-key",appKey);

        jsonObject.put("language",language);
        NormalBuyParamDTO normalBuyParamDTO=new NormalBuyParamDTO(RandomUtil.getRandom(32),"23592","https://steamcommunity.com/tradeoffer/new/?partner=484669140&token=WEhy_ZWD");
        jsonObject.put("normalBuyParamDTO",normalBuyParamDTO);
        System.out.println(jsonObject.toJSONString());
        String json = httpPostWithJSON("https://app.zbt.com/open/trade/v1/buy", jsonObject);
        System.out.println(json);
    }




    /**
     * 请求编码
     */


    /**
     * * 执行HTTP POST请求
     *
     * @param url   url
     * @param jsonObject 参数
     * @return
     */
    public static String httpPostWithJSON(String url, JSONObject jsonObject) {
        // 返回body
        String body = null;
        // 获取连接客户端工具
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        // 2、创建一个HttpPost请求
        HttpPost post = new HttpPost(url);
        // 5、设置header信息
        /**header中通用属性*/

        // 设置参数
        if (jsonObject != null) {

            //System.out.println(JSON.toJSONString(map));
            try {
                StringEntity entity1 = new StringEntity(jsonObject.toString(), "UTF-8");
                entity1.setContentEncoding("UTF-8");
                entity1.setContentType("application/json");
                post.setEntity(entity1);

                // 7、执行post请求操作，并拿到结果
                httpResponse = httpClient.execute(post);
                // 获取结果实体
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    // 按指定编码转换结果实体为String类型
                    body = EntityUtils.toString(entity, "UTF-8");
                }
                try {
                    httpResponse.close();
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return body;
    }
    /**
     * 执行HTTP GET请求
     *
     * @param url   url
     * @param param 参数
     * @return
     */

    public static String httpGetWithJSon(String url, Map<String,String> param) {
        param.put("app-key",appKey);
        param.put("appId","730");
        param.put("language",language);
        CloseableHttpClient client = null;
        try {
            if (url == null || url.trim().length() == 0) {
                throw new Exception("URL is null");
            }

            client = HttpClients.createDefault();
            if (param != null) {
                StringBuffer sb = new StringBuffer("?");
                for (String key : param.keySet()) {
                    sb.append(key).append("=").append(param.get(key)).append("&");
                }
                url = url.concat(sb.toString());
                url = url.substring(0, url.length() - 1);
            }
            HttpGet httpGet = new HttpGet(url);
            HttpResponse resp = client.execute(httpGet);
            if (resp.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(resp.getEntity(), DEFAULT_CHARSET);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(client);
        }
        return null;
    }


    /**
     * * 关闭HTTP请求
     * * @param client
     */
    private static void close(CloseableHttpClient client) {
        if (client == null) {
            return;
        }
        try {
            client.close();
        } catch (Exception e) {
        }
    }
}
