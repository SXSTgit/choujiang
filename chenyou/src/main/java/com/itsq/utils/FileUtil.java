package com.itsq.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 上传文件
 *
 * @author
 * @since 1.0.0
 */
public class FileUtil {

    private Logger log = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 上传file文件
     *
     * @param files  文件
     * @param folder 文件夹路径
     */
    public static String multipart(HttpServletRequest request, MultipartFile files, String folder) {
        //判断file是否为空，必须要有
        try {
            if (files != null && files.getSize() > 0) {
                String newFileName = "";
                //获取原始数据名
                String fileName = files.getOriginalFilename();
                //获取新数据名
                Date data = new Date();
                SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddhhmmss");
                String random = RandomUtil.getRandom(4);
                String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
                newFileName = fmt.format(data) + random + "." + suffix;
                //获取物理路径web所在路径
                String pathRoot = request.getSession().getServletContext().getRealPath("");
                //创建文件实例
                File tempFile = new File(pathRoot + "/" + folder + "/" + newFileName);
                // 判断父级目录是否存在，不存在则创建
                if (!tempFile.getParentFile().exists()) { //这个判断必须加上
                    tempFile.getParentFile().mkdir();
                }
                // 判断文件是否存在，否则创建文件（夹）
                if (!tempFile.exists()) {
                    tempFile.mkdir();
                }
                try {
                    //将接收到的文件传输到指定的目标文件
                    files.transferTo(tempFile);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /*try {
                    Thumbnails.of(tempFile).scale(1f).outputQuality(0.5f).toFile(pathRoot + "/" + folder + "/thumbnails" + newFileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                return folder + "/" + newFileName;//成功
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("上传图片错误!");
        }
        return "";
    }

    /**
     * base64上传文件
     *
     * @param image  base64字符
     * @param folder 文件夹路径
     * @return
     */
    public static String base64(HttpServletRequest request, String image, String folder) {
        // 通过base64来转化图片
        if (image != null && !image.equals("")) {
            String imageFile = "";
            if (image.contains("data:image/jpeg;base64,")) {
                imageFile = image.replaceAll("data:image/jpeg;base64,", "");
            } else if (image.contains("data:image/x-icon;base64,")) {
                imageFile = image.replaceAll("data:image/x-icon;base64,", "");
            } else if (image.contains("data:image/gif;base64,")) {
                imageFile = image.replaceAll("data:image/gif;base64,", "");
            } else if (image.contains("data:image/png;base64,")) {
                imageFile = image.replaceAll("data:image/png;base64,", "");
            }
            BASE64Decoder decoder = new BASE64Decoder();
            // Base64解码
            byte[] imageByte = null;
            try {
                imageByte = decoder.decodeBuffer(imageFile);
                for (int i = 0; i < imageByte.length; ++i) {
                    if (imageByte[i] < 0) {// 调整异常数据
                        imageByte[i] += 256;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 生成文件名
            String files = new SimpleDateFormat("yyyyMMddHHmmssSSS")
                    .format(new Date())
                    + (new Random().nextInt(9000) % (9000 - 1000 + 1) + 1000)
                    + ".png";
            // 生成文件路径
            String pathRoot = request.getSession().getServletContext().getRealPath("");
            String filename = pathRoot + "/" + folder + "/" + files;
            try {
                // 生成文件
                File s = new File(filename);
                s.createNewFile();
                if (!s.exists()) {
                    s.createNewFile();
                }
                OutputStream imageStream = new FileOutputStream(filename);
                imageStream.write(imageByte);
                imageStream.flush();
                imageStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
           /* try {
                Thumbnails.of(new File(filename)).scale(1f).outputQuality(0.5f).toFile(pathRoot + "/" + folder + "/thumbnails" + files);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            return folder + "/" + files;//成功
        } else {
            return "";
        }
    }
}
