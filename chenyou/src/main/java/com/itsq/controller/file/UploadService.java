package com.itsq.controller.file;

import com.alibaba.fastjson.JSONObject;
import com.itsq.common.bean.ErrorEnum;
import com.itsq.common.bean.Response;
import com.itsq.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.UUID;


/**
 * @Author: sq
 * @Date: Created in 2019/7/22 23:26
 * @description:
 */
@Service
public class UploadService {

//    @Autowired
//    public SysDictionaryItemService sysDictionaryItemService;

    private Logger logger = LoggerFactory.getLogger(UploadService.class);

    /** 1024 */
    private static final int NUMBER_1024 = 1024;

    /** 1048576 */
    private static final int NUMBER_1048576 = 1048576;

    /** 1073741824 */
    private static final int NUMBER_1073741824 = 1073741824;

    /**
     * 文件名为随机生成 拼接格式为 yyyyMMddHHmmss + UUID + 文件后缀
     * 
     * @param file
     * @param pathPrefix E:/uploadFile/
     * @return
     */
    public Response<?> uploadFile(MultipartFile file, String pathPrefix, String prefix) {
        String fileSizeString = getFileSizeString(file);
        logger.info("文件大小======================== {}", fileSizeString);
        String randName = UUID.randomUUID().toString().replace("-", "");
        String[] fileNameArray = file.getOriginalFilename().split("\\.");
        // 文件名拼接格式为 yyyyMMddHHmmss + UUID + 文件后缀
        String fileName = "HD" + DateUtils.getUnsignedDate() + randName + "." + fileNameArray[fileNameArray.length - 1];
        String target_dir = DateUtils.getUnsignedDate() + "/";
        File targetFile = new File(pathPrefix + target_dir);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        String saveFilePath = pathPrefix + target_dir;
        logger.info("文件名称======================== {}", fileName);
        logger.info("文件路径======================== {}", saveFilePath);

        if (!file.isEmpty()) {
            try {
                file.transferTo(new File(saveFilePath + fileName));
            } catch (Exception e) {
                e.printStackTrace();
                return Response.fail(ErrorEnum.UPLOAD_ERROR);
            }
        }
        String filePath = target_dir + fileName;
        if (StringUtils.isNotBlank(prefix)) {
            filePath = prefix + "/" + filePath;
        }
        logger.info("返回文件路径======================== {}", filePath);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("filePath", filePath);
        jsonObject.put("fileName", fileName);
        //http://localhost:8080/images/20200318/HD20200318cab6e1495be74dcbaec65445c07245e4.jpg
        jsonObject.put("filePathURL", "images/"+target_dir+fileName);
        return Response.success(jsonObject);
    }

    /**
     * 根据文件路径查看文件
     * 
     * @param path
     * @param response
     * @param uploadBasePath
     * @throws Exception
     */
    @SuppressWarnings("all")
    public void viewFile(String path, HttpServletResponse response, String uploadBasePath) throws Exception {
        String imagePath = null;
        String suffix = imagePath.substring(imagePath.lastIndexOf(".") + 1);
        File file = new File(uploadBasePath + imagePath);
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", -10);
        response.setHeader("Content-Disposition", "inline; ");
        response.setContentType("application/+" + suffix + ";charset=UTF-8");
        InputStream inputStream = null;
        OutputStream os = null;
        try {
            inputStream = new FileInputStream(file);
            os = response.getOutputStream();
            byte[] b = new byte[NUMBER_1024];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        response.flushBuffer();
    }

    /**
     * 获取文件大小
     * 
     * @param file
     * @return
     */
    public static String getFileSizeString(MultipartFile file) {
        DecimalFormat df1 = new DecimalFormat("0.00");
        String fileSizeString = "";
        long fileSize = file.getSize();
        if (fileSize < NUMBER_1024) {
            fileSizeString = df1.format((double) fileSize) + "B";
        } else if (fileSize < NUMBER_1048576) {
            fileSizeString = df1.format((double) fileSize / NUMBER_1024) + "K";
        } else if (fileSize < NUMBER_1073741824) {
            fileSizeString = df1.format((double) fileSize / NUMBER_1048576) + "M";
        }
        return fileSizeString;
    }

    public static void main(String[] args) {
        File file = new File("E:/uploadFile/");
        file.mkdir();
    }
}
