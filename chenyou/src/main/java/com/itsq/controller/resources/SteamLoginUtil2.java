package com.itsq.controller.resources;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itsq.common.base.BaseController;
import com.itsq.common.bean.Response;
import com.itsq.enums.EnumTokenType;
import com.itsq.pojo.dto.LoginRespDto;
import com.itsq.pojo.entity.Players;
import com.itsq.pojo.entity.SteamOpenIDIdentity;
import com.itsq.service.resources.PlayersService;
import com.itsq.service.resources.UserService;
import com.itsq.token.AuthToken;
import com.itsq.utils.RegexUtils;
import com.itsq.utils.StringUtils;
import com.itsq.utils.http.ClientUtils;
import com.itsq.utils.steam.SteamLoginUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/steam")
@CrossOrigin
@Api(tags = "steam相关接口")
@AllArgsConstructor
public class SteamLoginUtil2 extends BaseController {

    final static String STEAM_LOGIN = "https://steamcommunity.com/openid/login";
    private static String ApiKey = "F27A2B0367DD7FC1398522E878F6C769";

    private PlayersService playersService;
    /**
     * 组装steam登录url
     *
     * @param returnTo
     * @param
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getUrl(String returnTo) throws UnsupportedEncodingException {

        Map<String, String> params = new HashMap<String, String>();
        params = new HashMap<String, String>();
        // String loginTicket = request.getAttribute("loginTicket")==null ? "" :
        // "?lt="+ request.getAttribute("loginTicket").toString();
        // params.put("lt", request.getAttribute("loginTicket")==null
        // ?"":request.getAttribute("loginTicket").toString());
        params.put("openid.ns", "http://specs.openid.net/auth/2.0");
        params.put("openid.mode", "checkid_setup");
        params.put("openid.return_to", returnTo);
        params.put("openid.realm", returnTo);
        params.put("openid.identity", "http://specs.openid.net/auth/2.0/identifier_select");
        params.put("openid.claimed_id", "http://specs.openid.net/auth/2.0/identifier_select");
        return STEAM_LOGIN + "?" + SteamLoginUtil2.getUrlParamsByMap(params);
    }

    /**
     * 将数据提交steam进行验证，是否成功登录
     *
     * @param request
     * @return boolean
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String validate(Map<String, String> request){
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();
        CloseableHttpClient httpclient = null;
        HttpPost httppost = null;
        try {
            Object signed = request.get("openid.signed");
            if (signed == null || "".equals(signed)) {
                return "";
            }
            httpclient = HttpClients.createDefault();
            httppost = new HttpPost(STEAM_LOGIN + "?" + SteamLoginUtil2.getUrlParamsByMap(request));
            httppost.setConfig(defaultRequestConfig);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();

            String[] signeds = signed.toString().split(",");
            for (int i = 0; i < signeds.length; i++) {
                String val = request.get("openid." + signeds[i]);
                nvps.add(new BasicNameValuePair("openid." + signeds[i], val == null ? "" : val));
            }
            nvps.add(new BasicNameValuePair("openid.mode", "check_authentication"));
            httppost.setEntity(new UrlEncodedFormEntity(nvps));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return "";
            }
            InputStream instreams = entity.getContent();
            String result = SteamLoginUtil2.convertStreamToString(instreams);
            // Do not need the rest
            httppost.abort();
            String steamid = "";
            steamid = request.get("openid.claimed_id");
            steamid = steamid.replace("https://steamcommunity.com/openid/id/", "");
            if (!result.contains("is_valid:true")) {
                return "";
            }
            return steamid;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(httppost != null){
                httppost.releaseConnection();
            }
            if(httpclient != null){
                try {
                    httpclient.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * 将url中传递参数转化为map 其中值进行encode
     *
     * @param param
     *            aa=11&bb=22&cc=33
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String> getUrlParams(String param) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<String, String>(0);
        if (StringUtils.isBlank(param)) {
            return map;
        }
        String[] params = param.split("&");
        for (int i = 0; i < params.length; i++) {
            String[] p = params[i].split("=");
            if (p.length == 2) {
                map.put(p[0], URLDecoder.decode(p[1], "UTF-8"));
            }
        }
        return map;
    }

    /**
     * 将map转化为url可携带的参数字符串
     *
     * @param map
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getUrlParamsByMap(Map<String, String> map) throws UnsupportedEncodingException {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            // 解码
            sb.append(entry.getKey() + "=" + URLEncoder.encode(entry == null ? "" : entry.getValue(), "UTF-8"));
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = StringUtils.substringBeforeLast(s, "&");
        }
        return s;
    }

    public static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ApiOperation(value = "绑定steam", notes = "", httpMethod = "POST")
    @ResponseBody
    public Response loginSteam(){
        try {
            String url=getUrl("http://121.36.199.219/steam/huidiao");
            return Response.success(url);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "huidiao")
    public String isShouQuan(HttpServletRequest request2, HttpServletResponse response1)  throws ClientProtocolException, IOException{
        String identity=request2.getParameter("openid.identity");
        String response_nonce=request2.getParameter("openid.response_nonce");
        String assoc_handle=request2.getParameter("openid.assoc_handle");
        String signed2=request2.getParameter("openid.signed");
        String sig=request2.getParameter("openid.sig");
        String claimed_id=request2.getParameter("openid.claimed_id");
        Map<String,String> request=new HashMap();
        request.put("openid.identity",identity);
        request.put("openid.response_nonce",response_nonce);
        request.put("openid.assoc_handle",assoc_handle);
        request.put("openid.signed",signed2);
        request.put("openid.sig",sig);
        request.put("openid.claimed_id",claimed_id);

        //openid.signed这里面的参数用是“，”号隔开的，是提示你返回了哪些参数
        Object signed = request.get("openid.signed");
        //如果没有openid.signed，那肯定这个请求是不正确的直接跳出即可
        if(signed ==null || "".equals(signed)){
            return null;
        }
        //此处开始构造HttpClient对象，配置参数，设置访问方法，获取返回值等，进行一次完整访问
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(STEAM_LOGIN+"?"+SteamLoginUtil.getUrlParamsByMap(request));
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        String[] signeds = signed.toString().split(",");
        for(int i=0;i<signeds.length;i++){
            String val = request.get("openid."+signeds[ i ]);
            nvps.add(new BasicNameValuePair("openid."+signeds[ i ], val==null?"":val));
        }
        nvps.add(new BasicNameValuePair("openid.mode", "check_authentication"));
        httppost.setEntity(new UrlEncodedFormEntity(nvps));
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        if (entity == null) {
            return null;
        }
        InputStream instreams = entity.getContent();
        String result = SteamLoginUtil.convertStreamToString(instreams);
        //System.out.println("Do something");
        System.out.println(result);
        // Do not need the rest
        httppost.abort();
        //此处是为了将steamid截取出来
        String steamid = "";
        steamid = request.get("openid.claimed_id");
        steamid = steamid.replace("//steamcommunity.com/openid/id/","");
        //虽然steamid能从上一次请求参数中截取出来，我们还是要判断HttpClient返回来的消息是否授权了，判断方式是看字符串中是否含有“is_valid:true”，有就是授权成功了，如果没有，就是“is_valid:false”。
        if(steamid==null||steamid==""){
            return null;
        }


            String info= ClientUtils.httpGetWithJSon("http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=" + ApiKey + "&steamids=" + steamid,null);

       /* {

            identity = new SteamOpenIDIdentity();
            identity.SteamId = Regex.Match(HttpUtility.UrlDecode(Request.Url.Query), "(?<=openid/id/)\\d+", RegexOptions.IgnoreCase).Value;

            result = ClientUtils.DownloadString("http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=" + ApiKey + "&steamids=" + identity.SteamId);
            identity.Avatar = Regex.Match(result, "(?<=\"avatarfull\":\\s*?\").+?(?=\")", RegexOptions.IgnoreCase).Value;
            identity.Profile = Regex.Match(result, "(?<=\"profileurl\":\\s*?\").+?(?=\")", RegexOptions.IgnoreCase).Value;
            identity.UserName = Regex.Match(result, "(?<=\"personaname\":\\s*?\").+?(?=\")", RegexOptions.IgnoreCase).Value;
            identity.ReturnTo = new Uri(Request.QueryString["openid.return_to"]).PathAndQuery;
        }*/
        System.out.println(steamid);
        JSONObject objInfo = JSONObject.parseObject(info);
        JSONObject objInfo1=objInfo.getJSONObject("response");
        JSONArray jsonArray=objInfo1.getJSONArray("players");

        Players players=new Players();

            JSONObject json=(JSONObject)jsonArray.get(0);
            System.out.println(json);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("steam_id",json.getString("steamid"));
        List<Players> list=playersService.list(queryWrapper);
        if(list.size()==0){
            players.setSteamId(json.getString("steamid"));
            players.setImage(json.getString("avatar"));
            players.setName(json.getString("realname"));
            players.setCreDate(new Date());
            playersService.save(players);
        }
        if(list.size()>0){
            Players players1=list.get(0);
            String authToken = new AuthToken(players1.getId(),players1.getName()).token();
            //return  "Response.success(new LoginRespDto<>(players1,authToken, EnumTokenType.BEARER.getCode()));
        }
        response1.sendRedirect("/csgo/index.html");
        return "redirect:http://121.36.199.219/csgo/index.html";
    }
  /*  @RequestMapping(value = "huidiao")
    public Response isShouQuan( HttpServletRequest request2) throws ClientProtocolException,IOException {
        String identity=request2.getParameter("openid.identity");
        String response_nonce=request2.getParameter("openid.response_nonce");
        String assoc_handle=request2.getParameter("openid.assoc_handle");
        String signed=request2.getParameter("openid.signed");
        String sig=request2.getParameter("openid.sig");
        if(RegexUtils.match("steamcommunity.com/openid/id/\\d+",identity)&&StringUtils.isNotEmpty(response_nonce)
                && !StringUtils.isNotEmpty(assoc_handle)&&StringUtils.isNotEmpty(signed)&&StringUtils.isNotEmpty(sig)){
                SteamOpenIDIdentity identity = null;
            if(request2!=null){
                StringBuffer url=request2.getRequestURL();

                Pattern p = Pattern.compile(url.toString(), Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher("(?<=openid.mode=).+?(?=\\&)");
                String ret=m.replaceAll("check_authentication");
                String delStr="";
                for (int i = 0; i < ret.length(); i++) {
                    if(ret.charAt(i) != '?'){
                        delStr += ret.charAt(i);
                    }
                }


                try {
                    CloseableHttpClient client = null;
                    CloseableHttpResponse response = null;
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        Map<String, Object> data = new HashMap<String, Object>();

                        HttpPost httpPost = new HttpPost(uri + "/test2");
                        httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
                        httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(data),
                                ContentType.create("text/json", "UTF-8")));

                        client = HttpClients.createDefault();
                        response = client.execute(httpPost);
                        HttpEntity entity = response.getEntity();
                        String result = EntityUtils.toString(entity);
                        System.out.println(result);
                    } finally {
                        if (response != null) {
                            response.close();
                        }
                        if (client != null) {
                            client.close();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }



            }

        }
        return Response.success();
    }*/

  /*  public static void main(String[] args){
        try {
           String url= getUrl("http://www.yuanrise.cn");
            System.out.println(url);
        }catch (Exception e){
            e.printStackTrace();
        }

    }*/
}