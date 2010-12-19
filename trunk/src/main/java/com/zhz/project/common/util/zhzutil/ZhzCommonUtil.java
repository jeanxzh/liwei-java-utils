package com.zhz.project.common.util.zhzutil;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class ZhzCommonUtil {
    private static Logger logger = Logger.getLogger(ZhzCommonUtil.class);

    static public String generatemd5string(String csinput) {
        byte[] b, b2;
        StringBuffer buf;
        String csreturn = null;

        try {
            b = csinput.getBytes("iso-8859-1");
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(b);
            b2 = md.digest();

            buf = new StringBuffer(b2.length * 2);
            for (int nLoopindex = 0; nLoopindex < b2.length; nLoopindex++) {
                if (((int) b2[nLoopindex] & 0xff) < 0x10) {
                    buf.append("0");
                }
                buf.append(Long.toString((int) b2[nLoopindex] & 0xff, 16));
            }
            csreturn = new String(buf);
        } catch (Exception e) {
            e.printStackTrace();
            csreturn = null;
        }

        return csreturn;
    }

    // 获取客户端IP
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    static public String getFileNameWithTime(String fileName) {
        int dot = fileName.indexOf(".");
        String name = fileName.substring(0, dot);

        String ext = fileName.substring(dot, fileName.length());
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = df.format(new Date());
        String finalName = name + time + ext;
        return finalName;
    }

}
