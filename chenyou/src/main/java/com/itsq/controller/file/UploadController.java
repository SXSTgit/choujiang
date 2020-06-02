package com.itsq.controller.file;

import com.itsq.common.bean.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: sq
 * @Date: Created in 2019/7/22 23:21
 * @description: 文件上传
 */
@RestController
@RequestMapping("/api/file")
@Api(tags = "admin-本地文件上传")
public class UploadController{
    private static final Logger log = LoggerFactory.getLogger(UploadController.class);

    @Value("${system.prefix}")
    String prefix;

    @Autowired
    private UploadService uploadService;

    /**
     * 附件上传基础路径
     */
    @Value("${system.uploadBasePath}")
    protected String uploadBasePath;



    /**
     *
     * 文件名为随机生成 拼接格式为 yyyyMMddHHmmss + UUID + 文件后缀
     * 
     * @param file 上传的文件
     * @return
     */
    @ApiOperation(value = "上传文件")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Response<?> uploadFile(MultipartFile file) {
        //访问时候例如:http://localhost:8080/images/20200318/HD20200318ac1a220a299f4b61a25ddbf234bd4b11.jpg
        String pathPrefix = uploadBasePath;
        if (StringUtils.isNotEmpty(prefix)) {
            pathPrefix = pathPrefix + prefix + "/";
        }
        return uploadService.uploadFile(file, pathPrefix, prefix);
    }

    /***
     * 图片预览（更具文件路径）
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "图片预览（更具文件路径）")
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public void viewFileByPath(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String path = request.getParameter("path");
        uploadService.viewFile(path, response, uploadBasePath);
    }

}
