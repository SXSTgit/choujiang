package com.itsq.utils;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.*;
import java.util.Enumeration;

/**
 * 解压文件
 */
public class DecompressionUtil {
  /*  public static void main(String[] args) {
        try {
            String ss = unZip(new File("C:\\Users\\Administrator\\Desktop\\asdsa.zip"), "C:\\Users\\Administrator\\Desktop\\test\\");
            System.out.println(ss);
        } catch (Exception e) {
            e.printStackTrace();
        }
       ** File directory = new File("surname-web\\src\\main\\webapp");参数为空
        String courseFile = null;
        try {
            courseFile = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(courseFile);**
    }*/

    /**
     * 文件
     *
     * @param zipFile 要解压的文件
     * @param outDir  要解压的地址
     * @throws IOException
     */
    public static String unZip(File zipFile, String outDir) throws IOException {

        File outFileDir = new File(outDir);
        if (!outFileDir.exists()) {
            boolean isMakDir = outFileDir.mkdirs();
            if (isMakDir) {
                System.out.println("创建压缩目录成功");
            }
        }

        ZipFile zip = new ZipFile(zipFile);
        String zipEntryName = "";
        for (Enumeration enumeration = zip.getEntries(); enumeration.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) enumeration.nextElement();
            zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);

            if (entry.isDirectory()) {
                //处理压缩文件包含文件夹的情况
                File fileDir = new File(outDir + zipEntryName);
                fileDir.mkdir();
                continue;
            }

            File file = new File(outDir, zipEntryName);
            file.createNewFile();
            OutputStream out = new FileOutputStream(file);
            byte[] buff = new byte[1024];
            int len;
            while ((len = in.read(buff)) > 0) {
                out.write(buff, 0, len);
            }
            in.close();
            out.close();
        }
        if(zipEntryName.indexOf("/") != -1){
            return zipEntryName.substring(0, zipEntryName.indexOf("/"));
        } else {
            return zipEntryName;
        }

    }

    /**
     * 解压文件
     *
     * @param rarFile 要解压的文件
     * @param outDir  要解压的地址
     * @throws Exception
     */
    public static String unRar(File rarFile, String outDir) throws Exception {
        File outFileDir = new File(outDir);
        if (!outFileDir.exists()) {
            boolean isMakDir = outFileDir.mkdirs();
            if (isMakDir) {
                System.out.println("创建压缩目录成功");
            }
        }
        Archive archive = new Archive(new FileInputStream(rarFile));
        FileHeader fileHeader = archive.nextFileHeader();
        String name = fileHeader.getFileNameString().substring(0, fileHeader.getFileNameString().indexOf("\\"));
        while (fileHeader != null) {
            if (fileHeader.isDirectory()) {
                fileHeader = archive.nextFileHeader();
                continue;
            }
            String file = outDir + fileHeader.getFileNameString();
            file = file.replace("\\", "/");
            File out = new File(file);
            if (!out.exists()) {
                if (!out.getParentFile().exists()) {
                    out.getParentFile().mkdirs();
                }
                out.createNewFile();
            }
            FileOutputStream os = new FileOutputStream(out);
            archive.extractFile(fileHeader, os);

            os.close();

            fileHeader = archive.nextFileHeader();
        }
        archive.close();
        return name;
    }
}
