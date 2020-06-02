package com.itsq.service.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.itsq.common.constant.APIException;
import com.itsq.config.AliyunOssConfig;
import jodd.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service("aliyunOssResolve")
@Slf4j
public class AliyunOssResolve implements OssResolve {

    private OSSClient ossClient;

    @Autowired
    private AliyunOssConfig ossConfig;

    public AliyunOssResolve(AliyunOssConfig ossConfig) {
        this.ossClient = new OSSClient(ossConfig.getEndpoint(), ossConfig.getAccessKey(), ossConfig.getSecretKey());
        this.ossConfig = ossConfig;
    }

    @Override
    public String getHost() {
        return this.ossConfig.getHost();
    }

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public String upload(String filePath) throws APIException {
        String fileName = UUID.randomUUID().toString().replace("-", "");
        try {
            log.info("=> <Cloud> begin upload file.");
            PutObjectResult result = this.ossClient.putObject(ossConfig.getBucket(), fileName, new File(filePath));
            log.info("=> <Cloud> end upload file.");
        } catch (Exception e) {
            throw new APIException(e.getMessage(), e);
        }

        return fileName;
    }

    @Override
    public String upload(byte[] bytes){

        String fileName = UUID.randomUUID().toString().replace("-", "");
        try {
            log.info("=> <Cloud> begin upload file.");
            PutObjectResult result = this.ossClient.putObject(ossConfig.getBucket(), fileName, new ByteArrayInputStream(bytes));
            log.info("=> <Cloud> end upload file.");
        } catch (Exception e) {
            throw new APIException(e.getMessage(), e);
        }
        return fileName;
    }

    @Override
    public String upload(String prefix, String fileName, byte[] bytes){
        String uploadPath = (prefix + "/" + fileName).replace("//", "/");

        try {
            log.info("=> <Cloud> begin upload file.");
            PutObjectResult result = this.ossClient.putObject(ossConfig.getBucket(), uploadPath, new ByteArrayInputStream(bytes));
            log.info("=> <Cloud> end upload file. eTag:{}", result.getETag());
        } catch (Exception e) {
            throw new APIException(e.getMessage(), e);
        }
        return uploadPath;
    }

    @Override
    public String upload(String fileName, byte[] bytes) {
        String uploadPath = (fileName).replace("//", "/");

        try {
            log.info("=> <Cloud> begin upload file.");
            PutObjectResult result = this.ossClient.putObject(ossConfig.getBucket(), uploadPath, new ByteArrayInputStream(bytes));
            log.info("=> <Cloud> end upload file, eTag:{}", result.getETag());
        } catch (Exception e) {
            throw new APIException(e.getMessage(), e);
        }
        return uploadPath;
    }

    public static void main(String[] args)throws IOException {
        AliyunOssConfig config = new AliyunOssConfig().setAccessKey("LTAIiptc0chbJf1A").setSecretKey("9keOL7tByFGJf5muqKg6qms5JfXRwk").setBucket("jumax-store-test").setHost("oss-cn-shanghai.aliyuncs.com");
        AliyunOssResolve resolve = new AliyunOssResolve(config);
        byte[] bytes = FileUtil.readBytes("/Users/tim/Downloads/a.jpg");
        String abc = resolve.upload("abc", "a.jpg", bytes);
        System.out.println(abc);
    }
}
