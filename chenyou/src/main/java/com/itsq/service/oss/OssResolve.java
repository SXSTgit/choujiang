package com.itsq.service.oss;


public interface OssResolve {

    String getHost();

    /**
     * 获得OSS token
     * 包括上传, 下载.
     *
     * @return
     */
    String getToken();

    public String upload(String filePath);

    /**
     * 上传文件
     *
     * @param bytes
     * @return
     */
    public String upload(byte[] bytes) ;


    /**
     * 上传严格路径文件
     *
     * @param prefix   前缀
     * @param fileName 文件名
     * @param bytes    文件内容
     * @return 是否成功
     * @throws
     */
    public String upload(String prefix, String fileName, byte[] bytes);

    /**
     * 上传文件
     *
     * @param fileName 文件名
     * @param bytes    文件内容
     * @return 是否成功
     */
    public String upload(String fileName, byte[] bytes);
}
