package com.itsq.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AliyunOssConfig {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${oss.aliyun.accessKey}")
    private String accessKey;
    @Value("${oss.aliyun.secretKey}")
    private String secretKey;
    @Value("${oss.aliyun.bucket}")
    private String bucket;
    @Value("${oss.aliyun.host}")
    private String host;
    @Value("${oss.aliyun.endpoint}")
    private String endpoint;


    public AliyunOssConfig() {

    }



    public String getAccessKey() {
        return accessKey;
    }

    public AliyunOssConfig setAccessKey(String accessKey) {
        this.accessKey = accessKey == null ? null : accessKey.trim();
        return this;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public AliyunOssConfig setSecretKey(String secretKey) {
        this.secretKey = secretKey == null ? null : secretKey.trim();
        return this;
    }

    public String getBucket() {
        return bucket;
    }

    public AliyunOssConfig setBucket(String bucket) {
        this.bucket = bucket == null ? null : bucket.trim();
        return this;
    }

    public String getHost() {
        return host;
    }

    public AliyunOssConfig setHost(String host) {
        this.host = host == null ? null : host.trim();
        return this;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public AliyunOssConfig setEndpoint(String endpoint) {
        this.endpoint = endpoint == null ? null : endpoint.trim();
        return this;
    }


    public boolean enable() {
        boolean flag = StringUtils.isNotEmpty(this.accessKey) && StringUtils.isNotEmpty(this.secretKey) && StringUtils.isNotEmpty(this.bucket) && StringUtils.isNotEmpty(this.host);
        if (!flag) {
            logger.warn("Qiniu Oss Config Not Complete");
        }
        return flag;
    }


}
