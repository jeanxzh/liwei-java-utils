/**
 * 
 */
package com.zhz.project.common.util.zhzutil;

import com.zhz.project.common.util.alibaba.StringUtil;

/**  
 * @author LiWei <a href="mailto:liwei2672@gmail.com">liwei2672@gmail.com</a>  
 * @version 创建时间：2009-12-10 下午09:35:26
 * 
 */
/**
 * @author liwei
 * 
 */
public class ZhzStringUtil {

    /**
     * 转码IS0转UFT-8
     * 
     * @param input
     * @return
     */
    static public String ISOtoUTF8(String input) {
        try {
            byte[] bytes = input.getBytes("ISO8859-1");
            return new String(bytes, "UTF-8");
        } catch (Exception ex) {
        }
        return input;
    }

    /**
     * 转码UFT-8转IS0
     * 
     * @param input
     * @return
     */
    static public String UTF8toISO(String input) {
        try {
            byte[] bytes = input.getBytes("UTF-8");
            return new String(bytes, "ISO8859-1");
        } catch (Exception ex) {
        }
        return input;
    }

    /**
     * 将字符串中得“ ”转换为"&nbsp;"
     * 
     * @param stt
     *            * @return
     */
    static public String delSpace(String str) // 
    {
        return str.replaceAll(" ", "&nbsp;");
    }

    /**
     * 截取字符串的前targetCount个字符，以“...”结尾
     * 
     * @param str
     *            被处理字符串
     * @param targetCount
     *            截取长度
     * @version 1.1
     * @author Strong Yuan
     * @return String
     */
    public static String subContentStringOrialBytes(String str, int targetCount) {
        return subContentStringOrialBytes(str, targetCount, "...");
    }

    /**
     * 截取指定长度的字符串,基于bytes,即是中文的长度为2,英文为1
     * 
     * @param str
     *            被处理字符串
     * @param targetCount
     *            截取长度
     * @param more
     *            后缀字符串
     * @author Strong Yuan
     * @version 1.1
     * @return
     */
    public static String subContentStringOrialBytes(String str, int targetCount, String more) {
        if (str == null)
            return "";
        int initVariable = 0;
        StringBuffer restr = new StringBuffer();
        if (getStringByteLength(str, targetCount) <= targetCount)
            return str;

        String s1 = null;
        byte[] b;
        char[] tempchar = str.toCharArray();
        for (int i = 0; (i < tempchar.length && targetCount > initVariable); i++) {
            s1 = String.valueOf(tempchar[i]);
            b = s1.getBytes();
            initVariable += b.length;
            restr.append(tempchar[i]);
        }

        if (targetCount == initVariable || (targetCount == initVariable - 1)) {
            restr.append(more);
        }
        return restr.toString();
    }

    /**
     * 获取指定长度字符串的字节长
     * 
     * @param str
     *            被处理字符串
     * @param maxlength
     *            截取长度
     * @author Strong Yuan
     * @version 1.1
     * @return String
     */
    private static long getStringByteLength(String str, int maxlength) {
        if (str == null)
            return 0;
        int tmp_len = maxlength;

        if (str.length() < maxlength)
            tmp_len = str.length();
        else if (str.length() > maxlength * 2)
            tmp_len = maxlength * 2;

        char[] tempchar = str.substring(0, tmp_len).toCharArray();

        int intVariable = 0;
        String s1 = null;
        for (int i = 0; i < tempchar.length && intVariable <= maxlength; i++) {
            s1 = String.valueOf(tempchar[i]);
            intVariable += s1.getBytes().length;
        }
        s1 = null;
        tempchar = null;
        return intVariable;
    }

    /**
     * 截取指定长度的字符串,存在问题,但效率会高一点点.just a little
     * 
     * @param str
     *            被处理字符串
     * @param targetCount
     *            截取长度
     * @param more
     *            后缀字符串
     * @version 1.1
     * @author Strong Yuan
     * @return String
     */
    public static String subContentStringOrial(String str, int targetCount) {
        return subContentStringOrial(str, targetCount, "...");
    }

    /**
     * 截取指定长度的字符串,存在问题,但效率会高一点点.just a little
     * 
     * @param str
     *            被处理字符串
     * @param targetCount
     *            截取长度
     * @param more
     *            后缀字符串
     * @author Strong Yuan
     * @return String
     */
    public static String subContentStringOrial(String str, int targetCount, String more) {
        if (str == null)
            return "";
        int initVariable = 0;
        StringBuffer restr = new StringBuffer();
        if (str.length() <= targetCount)
            return str;

        String s1 = null;
        byte[] b;
        char[] tempchar = str.toCharArray();
        for (int i = 0; (i < tempchar.length && targetCount > initVariable); i++) {
            s1 = String.valueOf(tempchar[i]);
            b = s1.getBytes();
            initVariable += b.length;
            restr.append(tempchar[i]);
        }

        if (targetCount == initVariable || (targetCount == initVariable - 1)) {
            restr.append(more);
        }
        return restr.toString();
    }

    // public static void main(String[] args) {
    // int maxLength = 30;
    // String hanzi = "2008年下半年钱学森图书馆读者培训讲座计划";
    // String english =
    // "0123456789012345678901234567890123456789012345678901234567890123456789";
    // String aa = null;
    // aa = ZhzStringUtils.subContentStringOrial(hanzi, maxLength);
    // System.out.println(aa);
    // aa = ZhzStringUtils.subContentStringOrialBytes(hanzi, maxLength);
    // System.out.println(aa);
    // aa = ZhzStringUtils.subContentStringOrial(english, maxLength);
    // System.out.println(aa);
    // aa = ZhzStringUtils.subContentStringOrialBytes(english, maxLength);
    // System.out.println(aa);
    // }

    public static void main(String args[]) {
        String str = "2008年下半年钱学森图书馆读者培训讲座计划";
        String str2 = "# SSCI:Social Sciences Citation Index 数据库正式开通";
        int num = 30;
        try {
            System.out.println(ZhzStringUtil.subString(str, num));
            System.out.println(ZhzStringUtil.subString(str2, num));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String subStringWithDot(String s, int length) {
        try {
            return subString(s, length) + "...";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public static String subString(String s, int length) throws Exception {
        byte[] bytes = s.getBytes("Unicode");
        int n = 0; // 表示当前的字节数
        int i = 2; // 要截取的字节数，从第3个字节开始
        for (; i < bytes.length && n < length; i++) {
            // 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
            if (i % 2 == 1) {
                n++; // 在UCS2第二个字节时n加1
            } else {
                // 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
                if (bytes[i] != 0) {
                    n++;
                }
            }
        }
        // 如果i为奇数时，处理成偶数
        if (i % 2 == 1) {
            // 该UCS2字符是汉字时，去掉这个截一半的汉字
            if (bytes[i - 1] != 0)
                i = i - 1;
            // 该UCS2字符是字母或数字，则保留该字符
            else
                i = i + 1;
        }
        return new String(bytes, 0, i, "Unicode");
    }

    public static boolean isNotNull(String str) {
        if (str == null)
            return false;
        else if (str.equals("null"))
            return false;
        else if (str.equals(""))
            return false;
        return true;

    }

    public static String trimIfNotNull(String str) {
        if (str == null)
            return null;
        else if (str.equals("null"))
            return null;
        else if (str.equals(""))
            return null;
        return str.trim();
    }

    public static String ISOtoUTF8AfterTrimIfNotNull(String str) {
        if (str == null)
            return null;
        else if (str.equals("null"))
            return null;
        else if (str.equals(""))
            return null;
        return ISOtoUTF8(str.trim());
    }

    public static String getStringAfterProcessWithNull(String str) {
        if (StringUtil.isBlank(str) || StringUtil.equals(str, "null")) {
            str = null;
        }
        return str;
    }
}
