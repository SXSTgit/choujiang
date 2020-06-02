package com.itsq.utils.steam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;


public class SteamLoginUtil {

	final static String STEAM_LOGIN = "https://steamcommunity.com/openid/login";

	public static void main(String[] args) throws UnsupportedEncodingException {
		String url = getUrl("http://121.36.199.219/yundian-templates/login.html");
		System.out.println(url);
	}

	/**
	 * 组装steam登录url
	 * 
	 * @param returnTo
	 * @param request
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
		return STEAM_LOGIN + "?" + SteamLoginUtil.getUrlParamsByMap(params);
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
			httppost = new HttpPost(STEAM_LOGIN + "?" + SteamLoginUtil.getUrlParamsByMap(request));
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
			String result = SteamLoginUtil.convertStreamToString(instreams);
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
}