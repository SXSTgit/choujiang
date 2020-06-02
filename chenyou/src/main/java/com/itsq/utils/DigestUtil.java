package com.itsq.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加解密、验证工具类
 *
 * @author
 * @version 1.0
 */
public class DigestUtil {

	public static final Logger logger = LoggerFactory.getLogger(DigestUtil.class);

    /**
     * Print the command line usage string.
     */
    public static void printUsage() {
        System.out.println("Usage: "
                           + "java org.apache.james.security.DigestUtil"
                           + " [-alg algorithm]"
                           + " [-file] filename|string");
    }

    /**
     * Calculate digest of given file with given algorithm.
     * Writes digest to file named filename.algorithm .
     *
     * @param filename the String name of the file to be hashed
     * @param algorithm the algorithm to be used to compute the digest
     */
    @SuppressWarnings("unused")
	public static void digestFile(String filename, String algorithm) {
        byte[] b = new byte[65536];
//        int count = 0;
        int read = 0;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            fis = new FileInputStream(filename);
            while (fis.available() > 0) {
                read = fis.read(b);
                md.update(b, 0, read);
//                count += read;
            }
            byte[] digest = md.digest();
            StringBuilder fileNameBuffer =
                    new StringBuilder(128)
                    .append(filename)
                    .append(".")
                    .append(algorithm);
            fos = new FileOutputStream(fileNameBuffer.toString());
//            OutputStream encodedStream = MimeUtility.encode(fos, "base64");
//            encodedStream.write(digest);
//            fos.flush();
        } catch (Exception e) {
//            System.out.println("Error computing Digest: " + e);
            logger.error("异常Error:{}" + e.getMessage());
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (Exception ignored) {
            	throw new RuntimeException("Fatal error: " + ignored);
            }
        }
    }

    /**
     * Calculate digest of given String using given algorithm.
     * Encode digest in MIME-like base64.
     *
     * @param pass the String to be hashed
     * @param algorithm the algorithm to be used
     * @return String Base-64 encoding of digest
     *
     * @throws NoSuchAlgorithmException if the algorithm passed in cannot be found
     */
//    public static String digestString(String pass, String algorithm) throws
//            NoSuchAlgorithmException {
//
//        MessageDigest md;
//        ByteArrayOutputStream bos;
//
//        try {
//            md = MessageDigest.getInstance(algorithm);
//            byte[] digest = md.digest(pass.getBytes("iso-8859-1"));
//            bos = new ByteArrayOutputStream();
//            OutputStream encodedStream = MimeUtility.encode(bos, "base64");
//            encodedStream.write(digest);
//            return bos.toString("iso-8859-1");
//        } catch (IOException ioe) {
//            throw new RuntimeException("Fatal error: " + ioe);
//        } catch (MessagingException me) {
//            throw new RuntimeException("Fatal error: " + me);
//        }
//    }

    public static String digestString2(String pass, String algorithm) throws
            NoSuchAlgorithmException {

        MessageDigest md;

        try {
            md = MessageDigest.getInstance(algorithm);
            byte[] digest = md.digest(pass.getBytes());
            byte[] dest = new byte[8];
            System.arraycopy(digest, 4, dest, 0, 8);
            char[] pwd = org.apache.commons.codec.binary.Hex.encodeHex(dest);
            return new String(pwd);
        } catch (Exception ioe) {
            throw new RuntimeException("Fatal error: " + ioe);
        }
    }
    
    public static String digestString(String pass, String algorithm,String charset) throws
    Exception {
    	String code="";
		try{
			MessageDigest md = MessageDigest.getInstance(algorithm); 
		    md.update(pass.getBytes(charset)); 
		    byte[] result = md.digest(); 
		 
		    StringBuilder sb = new StringBuilder(); 
		 
		    for (byte b : result) { 
		        int i = b & 0xff; 
		        if (i < 0xf) { 
		            sb.append(0); 
		        } 
		        sb.append(Integer.toHexString(i)); 
		    } 
		    code=sb.toString();
		}catch(Exception e){
			throw e;
		}
	  return code;
	}
    
    

}
