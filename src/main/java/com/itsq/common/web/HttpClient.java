package com.itsq.common.web;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public final class HttpClient {

    /**
     * 支持的HTTP请求方式
     */
    private static final String HTTP_METHOD_GET = "GET";
    private static final String HTTP_METHOD_POST = "POST";

    /**
     * 支持的Request Header传User信息
     */
    private static final String HTTP_HEADER_USER = "X-User-Id";
    private static final String HTTP_HEADER_ACCOUNT = "X-Account-Id";
    private static final String HTTP_HEADER_TOKEN = "X-Token";

    /**
     * 请求默认编码
     */
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 支持的请求内容类型（json）
     */
    private static final String MEDIA_TYPE = "application/json";

    private HttpClient() {
    }

    /**
     * 发送HTTP GET请求
     *
     * @param url
     * @throws IOException
     */
    public static String get(String url, HttpHeader header) throws IOException {
        URL uri = new URL(url);
        HttpURLConnection httpUrlConn = (HttpURLConnection) uri.openConnection();
        httpUrlConn.addRequestProperty(HTTP_HEADER_USER, header.getUserId());
        httpUrlConn.addRequestProperty(HTTP_HEADER_ACCOUNT, header.getAccountId());
        httpUrlConn.addRequestProperty(HTTP_HEADER_TOKEN, header.getToken());

        httpUrlConn.setUseCaches(false);
        httpUrlConn.setRequestMethod(HTTP_METHOD_GET);
        httpUrlConn.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(httpUrlConn.getInputStream(), DEFAULT_CHARSET));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        reader.close();
        return buffer.toString();
    }

    /**
     * 发送HTTP POST请求
     *
     * @param url  <p>
     *             请求路径
     *             </p>
     * @param body
     * @throws IOException
     */
    public static String post(String url, String body, HttpHeader header) throws IOException {

        byte[] postDataBytes = body.getBytes(DEFAULT_CHARSET);

        URL uri = new URL(url);
        URLConnection urlConnection = uri.openConnection();
        HttpURLConnection httpUrlConn = (HttpURLConnection) urlConnection;
        if (header != null) {
            httpUrlConn.addRequestProperty(HTTP_HEADER_USER, header.getUserId());
            httpUrlConn.addRequestProperty(HTTP_HEADER_ACCOUNT, header.getAccountId());
            httpUrlConn.addRequestProperty(HTTP_HEADER_TOKEN, header.getToken());
        }
        httpUrlConn.setDoOutput(true);
        httpUrlConn.setDoInput(true);

        // Post请求不能被缓存下来
        httpUrlConn.setUseCaches(false);
        httpUrlConn.setRequestProperty("Content-type", MEDIA_TYPE);
        httpUrlConn.setRequestProperty("charset", DEFAULT_CHARSET);
        httpUrlConn.setRequestProperty("Content-length", String.valueOf(postDataBytes.length));
        httpUrlConn.setRequestMethod(HTTP_METHOD_POST);

        // 将body里的内容以流的形式交给http请求
        httpUrlConn.getOutputStream().write(postDataBytes);

        // 读取请求的结果
        BufferedReader reader = new BufferedReader(new InputStreamReader(httpUrlConn.getInputStream(), DEFAULT_CHARSET));
        StringBuilder content = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
            content.append(line);
        }
        reader.close();
        return content.toString();
    }
}
