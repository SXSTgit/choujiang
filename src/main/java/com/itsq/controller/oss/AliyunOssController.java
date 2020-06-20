package com.itsq.controller.oss;


import com.itsq.service.oss.OssResolve;
import com.itsq.service.oss.OssService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/oss/aliyun")
@Api(tags = "oos云存储")
public class AliyunOssController {
    @Resource(name = "aliyunOssResolve")
    private OssResolve ossResolve;

    @PostMapping("/uploadByPrefix")
    public String upload(@RequestParam("prefix") String prefix, @RequestParam("file") MultipartFile multipartFile) throws  IOException {
        OssService ossService = new OssService(this.ossResolve);
        String fileName = ossService.upload(prefix, multipartFile.getOriginalFilename(), multipartFile.getBytes());
        return this.ossResolve.getHost()+"/"+fileName;
    }


    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        OssService ossService = new OssService(this.ossResolve);
        String fileName = ossService.upload("admin", multipartFile.getOriginalFilename(), multipartFile.getBytes());
        return this.ossResolve.getHost()+"/"+fileName;
    }
}
