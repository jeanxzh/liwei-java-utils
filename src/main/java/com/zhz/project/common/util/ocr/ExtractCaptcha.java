/**
 * Zhznet.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.zhz.project.common.util.ocr;

/**
 *
 * @author Administrator
 * @version $Id: ExtractCaptcha.java, v 0.1 May 18, 2012 9:06:46 AM Administrator Exp $
 */

import java.io.InputStreamReader;
import java.io.LineNumberReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.perf4j.StopWatch;
import org.perf4j.log4j.Log4JStopWatch;

import com.zhz.project.common.util.alibaba.StringUtil;

/**
 * 
 * 在linux系统调用gocr命令进行图片解析<br/>
 *
 * @author 李威-liwei2672@gmail.com
 * @version $Id: ExtractCaptcha.java, v 0.1 2012-4-18 下午04:04:45 李威 Exp $
 */
public class ExtractCaptcha {
    private static Log   logger             = LogFactory.getLog(ExtractCaptcha.class);

    //调用GOCR命令的参数
    public static String GOCR_SHELL         = "/usr/local/bin/gocr";
    public static String GOCR_IMG_PARAMETER = "-i";

    /**
     * 获取gocr解析后的字符串，因为没有进行过处理，还不能作为价格来使用
     *
     * @param imagePath
     * @param single
     * @return
     */
    public static String extract(String imagePath, boolean single) {
        StopWatch stopWatch = new Log4JStopWatch("ExtractCaptcha_extract");

        if (imagePath == null)
            return null;
        try {
            logger.info("extract_file:" + imagePath);

            Process process = Runtime.getRuntime().exec(
                new String[] { GOCR_SHELL, GOCR_IMG_PARAMETER, imagePath });

            LineNumberReader input = new LineNumberReader(new InputStreamReader(process
                .getInputStream()));

            return input.readLine();
        } catch (Exception e) {
            logger.error("没有找到检测脚本", e);
            return null;
        } finally {
            stopWatch.stop("ExtractCaptcha_extract");
        }
    }

    /**
     * 对GOCR返回值进一步处理，得到数值型字符串<br/>
     * <pre>
     * 例如：
     * "Y 199.oo"-->"199.00"
     * "Y 949.oo"-->"949.00"
     * </pre>
     * @param code
     * @param urlId
     * @param single 
     * @return
     */
    public static Float getNumeric(String code, long urlId, boolean single) {
        if (StringUtil.isBlank(code)) {
            return null;
        }
        String tempCode = code;
        tempCode = StringUtil.replace(tempCode, " ", "");
        tempCode = StringUtil.replace(tempCode, "o", "0");
        tempCode = StringUtil.replace(tempCode, "Y", "");
        tempCode = StringUtil.replace(tempCode, "y", "");

        //将"."去掉，检查s剩余的字符是否为数字，如果全为数字，则说明已经正确处理
        String temp = StringUtil.replace(tempCode, ".", "");

        //检验是否为数值型字符串
        if (StringUtil.isNumeric(temp)) {
            Float price = Float.parseFloat(tempCode);
            logger.info("\turlid:" + urlId + "\tvalue：" + code + "\tprice：" + price + "\t"
                        + (price + 0.00001));

            return price;
        } else {
            logger.info("\turlid:" + urlId + "\tvalue：" + code + "\tprice：" + tempCode);
            return null;
        }
    }
}
