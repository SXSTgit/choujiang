package com.itsq.service.oss;


import java.util.UUID;

public class OssService {
    private OssResolve ossResolve;

    public OssService(OssResolve ossResolve) {
        this.ossResolve = ossResolve;
    }

    public String getToken()  {
        return this.ossResolve.getToken();
    }

    public String upload(String filePath) {
        return this.ossResolve.upload(filePath);
    }

    /**
     * 上传文件随机文件名文件
     *
     * @param bytes
     * @return
     */
    public String upload(byte[] bytes){
        return this.ossResolve.upload(bytes);
    }


    /**
     * 上传带前缀的模式
     *
     * @param prefix
     * @param originalFileName
     * @param bytes
     * @return
     */
    public String upload(String prefix, String originalFileName, byte[] bytes){
        int lastDotIndex = originalFileName.lastIndexOf(".");
        String randomName = UUID.randomUUID().toString().toLowerCase().replace("-", "");
        String suffix = lastDotIndex != -1 ? originalFileName.substring(lastDotIndex) : "";
        String fileName = randomName + suffix;
        return this.ossResolve.upload(prefix, fileName, bytes);
    }


    /**
     *
     * @param originalFileName
     * @param bytes
     * @return
     */
    public String upload(String originalFileName, byte[] bytes){
        int lastDotIndex = originalFileName.lastIndexOf(".");
        String randomName = UUID.randomUUID().toString().toLowerCase().replace("-", "");
        String suffix = lastDotIndex != -1 ? originalFileName.substring(lastDotIndex) : "";
        String fileName = randomName + suffix;
        return this.ossResolve.upload(fileName, bytes);
    }


}
