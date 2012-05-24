package com.zhz.project.common.util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 *
 *
 * @author LiWei：<a href="mailto:liwei2672@gmail.com">liwei2672@gmail.com</a>
 * @version FileUtils.java, v 0.1 May 24, 2012 3:32:59 PM
 */
public class FileUtils {
    private static Logger logger = Logger.getLogger(FileUtils.class);

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     * 
     * @param fileName
     *            文件名
     */
    public static String readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sbStr = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                sbStr.append(tempString).append("\r\n");
                line++;
            }
            reader.close();

        } catch (IOException e) {
            logger.error(e);
        }
        return sbStr.toString();
    }

    /**
     * 读取一个文件到字符串里.
     */
    public static String readTextFile(String sFileName, String sEncode) {
        StringBuffer sbStr = new StringBuffer();

        try {
            File ff = new File(sFileName);
            InputStreamReader read = new InputStreamReader(new FileInputStream(ff), sEncode);
            BufferedReader ins = new BufferedReader(read);

            String dataLine = "";
            while (null != (dataLine = ins.readLine())) {
                sbStr.append(dataLine);
                sbStr.append("\r\n");
            }

            ins.close();
        } catch (Exception e) {
            logger.error("读取一个文件到字符串里时发送异常", e);
        }

        return sbStr.toString();
    }

    /**
     * 将HashMap写进文件里面
     * 
     * @param map
     *            HashMap
     * @param fileName
     *            文件名
     */
    public static void writeMapToFile(HashMap map, String fileName) {
        StringBuffer sb = new StringBuffer();

        Set keys = map.entrySet();

        for (Iterator it = keys.iterator(); it.hasNext();) {
            java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
            String key = (String) entry.getKey();
            sb.append(map.get(key).toString());
        }
        writeFileByChars(sb.toString(), fileName);
    }

    /**
     * 以字符为单位写文件。
     * 
     * @param str
     *            String
     * @param fileName
     *            文件名
     */
    public static void writeFileByChars(String str, String fileName) {
        File file = new File(fileName);
        Writer writer = null;
        try {
            // 打开文件输出流
            writer = new OutputStreamWriter(new FileOutputStream(file));
            writer.write(str);
        } catch (IOException e) {
            logger.error("写文件" + file.getAbsolutePath() + "失败！", e);
        } finally {
            if (writer != null) {
                try {
                    // 关闭输出文件流
                    writer.close();
                } catch (IOException e1) {
                    logger.error("写文件" + file.getAbsolutePath() + "失败！", e1);
                }
            }
        }
    }

    /**
     * 这是一个内部类，实现了FilenameFilter接口，用于过滤文件
     */
    static class MyFilenameFilter implements FilenameFilter {
        // 文件名后缀
        private String suffix = "";

        public MyFilenameFilter(String surfix) {
            this.suffix = surfix;
        }

        public boolean accept(File dir, String name) {
            // 如果文件名以surfix指定的后缀相同，便返回true，否则返回false
            if (new File(dir, name).isFile()) {
                return name.endsWith(suffix);
            } else {
                // 如果是文件夹，则直接返回true
                return true;
            }
        }
    }

    /**
     * 列出目录下所有文件包括子目录的文件路径
     * 
     * @param dirName
     *            文件夹的文件路径
     */
    public static void listAllFiles(String dirName) {

        // 如果dir不以文件分隔符结尾，自动添加文件分隔符。
        if (!dirName.endsWith(File.separator)) {
            dirName = dirName + File.separator;
        }
        File dirFile = new File(dirName);
        // 如果dir对应的文件不存在，或者不是一个文件夹，则退出
        if (!dirFile.exists() || (!dirFile.isDirectory())) {
            logger.error("List失败！找不到目录：" + dirName);
            return;
        }
        // 列出源文件夹下所有文件（包括子目录）
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                logger.error(files[i].getAbsolutePath() + " 是文件!");
            } else if (files[i].isDirectory()) {
                logger.error(files[i].getAbsolutePath() + " 是目录!");
                listAllFiles(files[i].getAbsolutePath());
            }
        }
    }

    /**
     * 列出目录中通过文件名过滤器过滤后的文件。
     * 
     * @param filter
     *            文件名过滤器对象
     * @param dirName
     *            目录名
     */
    public static void listFilesByFilenameFilter(FilenameFilter filter, String dirName) {

        // 如果dir不以文件分隔符结尾，自动添加文件分隔符。
        if (!dirName.endsWith(File.separator)) {
            dirName = dirName + File.separator;
        }
        File dirFile = new File(dirName);
        // 如果dir对应的文件不存在，或者不是一个文件夹，则退出
        if (!dirFile.exists() || (!dirFile.isDirectory())) {
            logger.error("List失败！找不到目录：" + dirName);
            return;
        }
        // 列出源文件夹下所有文件（包括子目录）
        File[] files = dirFile.listFiles(filter);
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                logger.error(files[i].getAbsolutePath() + " 是文件!");
            } else if (files[i].isDirectory()) {
                logger.error(files[i].getAbsolutePath() + " 是目录!");
                listFilesByFilenameFilter(filter, files[i].getAbsolutePath());
            }
        }
    }

    /**
     * 创建单个文件
     * 
     * @param destFileName
     *            目标文件名
     * @return 创建成功，返回true，否则返回false
     */
    public static boolean createFile(String destFileName) {
        File file = new File(destFileName);
        if (file.exists()) {
            logger.error("创建单个文件" + destFileName + "失败，目标文件已存在！");
            return false;
        }
        if (destFileName.endsWith(File.separator)) {
            logger.error("创建单个文件" + destFileName + "失败，目标文件不能为目录！");
            return false;
        }
        // 判断目标文件所在的目录是否存在
        if (!file.getParentFile().exists()) {
            // 如果目标文件所在的文件夹不存在，则创建父文件夹
            logger.error("目标文件所在目录不存在，准备创建它！");
            if (!file.getParentFile().mkdirs()) {
                logger.error("创建目标文件所在的目录失败！");
                return false;
            }
        }
        // 创建目标文件
        try {
            if (file.createNewFile()) {
                logger.error("创建单个文件" + destFileName + "成功！");
                return true;
            } else {
                logger.error("创建单个文件" + destFileName + "失败！");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("创建单个文件" + destFileName + "失败！", e);
            return false;
        }
    }

    /**
     * 创建目录
     * 
     * @param destDirName
     *            目标目录名
     * @return 目录创建成功放回true，否则返回false
     */
    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            logger.error("创建目录" + destDirName + "失败，目标目录已存在！");
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        // 创建目标目录
        if (dir.mkdirs()) {
            logger.error("创建目录" + destDirName + "成功！");
            return true;
        } else {
            logger.error("创建目录" + destDirName + "失败！");
            return false;
        }
    }

    /**
     * 创建临时文件
     * 
     * @param prefix
     *            临时文件名的前缀
     * @param suffix
     *            临时文件名的后缀
     * @param dirName
     *            临时文件所在的目录，如果输入null，则在用户的文档目录下创建临时文件
     * @return 临时文件创建成功返回true，否则返回false
     */
    public static String createTempFile(String prefix, String suffix, String dirName) {
        File tempFile = null;
        if (dirName == null) {
            try {
                // 在默认文件夹下创建临时文件
                tempFile = File.createTempFile(prefix, suffix);
                // 返回临时文件的路径
                return tempFile.getCanonicalPath();
            } catch (IOException e) {
                logger.error("创建临时文件失败!", e);
                return null;
            }
        } else {
            File dir = new File(dirName);
            // 如果临时文件所在目录不存在，首先创建
            if (!dir.exists()) {
                if (createDir(dirName)) {
                    logger.error("创建临时文件失败，不能创建临时文件所在的目录！");
                    return null;
                }
            }
            try {
                // 在指定目录下创建临时文件
                tempFile = File.createTempFile(prefix, suffix, dir);
                return tempFile.getCanonicalPath();
            } catch (IOException e) {
                logger.error("创建临时文件失败!", e);
                return null;
            }
        }
    }

    /**
     * 删除文件，可以是单个文件或文件夹
     * 
     * @param fileName
     *            待删除的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            logger.error("删除文件失败：" + fileName + "文件不存在！");
            return false;
        } else {
            if (file.isFile()) {
                return deleteFile(fileName);
            } else {
                return deleteDirectory(fileName);
            }
        }
    }

    /**
     * 删除单个文件
     * 
     * @param fileName
     *            被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径对应的文件存在，并且是一个文件，则直接删除。
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                logger.error("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                logger.error("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            logger.error("删除单个文件失败：" + fileName + "文件不存在！");
            return false;
        }
    }

    /**
     * 删除目录（文件夹）以及目录下的文件，只删除文件夹
     * 
     * @param dir
     *            被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符。
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个文件夹，则退出
        if (!dirFile.exists() || (!dirFile.isDirectory())) {
            logger.error("删除目录失败：" + dir + "目录不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹下所有文件（包括子目录）
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            logger.error("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            logger.error("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }

    /**
     * 移动单个文件，不覆盖已存在的目标文件
     * 
     * @param srcFileName
     *            待移动的原文件名
     * @param destFileName
     *            目标文件名
     * @return 文件移动成功返回true，否则返回false
     */
    public static boolean moveFile(String srcFileName, String destFileName) {
        // 默认为不覆盖目标文件
        return moveFile(srcFileName, destFileName, false);
    }

    /**
     * 移动单个文件
     * 
     * @param srcFileName
     *            待移动的原文件名
     * @param destFileName
     *            目标文件名
     * @param overlay
     *            如果目标文件存在，是否覆盖
     * @return 文件移动成功返回true，否则返回false
     */
    public static boolean moveFile(String srcFileName, String destFileName, boolean overlay) {
        // 判断原文件是否存在
        File srcFile = new File(srcFileName);
        if (!srcFile.exists()) {
            logger.error("移动文件失败：原文件" + srcFileName + "不存在！");
            return false;
        } else if (!srcFile.isFile()) {
            logger.error("移动文件失败：" + srcFileName + "不是一个文件！");
            return false;
        }
        File destFile = new File(destFileName);
        // 如果目标文件存在
        if (destFile.exists()) {
            // 如果允许文件覆盖
            if (overlay) {
                // 删除已存在的目标文件，无论目标文件是目录还是单个文件
                logger.error("目标文件已存在，准备删除它！");
                if (!delete(destFileName)) {
                    logger.error("移动文件失败：删除目标文件" + destFileName + "失败！");
                    return false;
                }
            } else {
                logger.error("移动文件失败：目标文件" + destFileName + "已存在！");
                return false;
            }
        } else {
            if (!destFile.getParentFile().exists()) {
                // 如果目标文件所在的目录不存在，则创建目录
                logger.error("目标文件所在目录不存在，准备创建它！");
                if (!destFile.getParentFile().mkdirs()) {
                    logger.error("移动文件失败：创建目标文件所在的目录失败！");
                    return false;
                }
            }
        }
        // 移动原文件至目标文件
        if (srcFile.renameTo(destFile)) {
            logger.error("移动单个文件" + srcFileName + "至" + destFileName + "成功！");
            return true;
        } else {
            logger.error("移动单个文件" + srcFileName + "至" + destFileName + "失败！");
            return true;
        }
    }

    /**
     * 移动目录，不覆盖已存在的目标目录
     * 
     * @param srcDirName
     *            待移动的原目录名
     * @param destDirName
     *            目标目录名
     * @return 目录移动成功返回true，否则返回false
     */
    public static boolean moveDirectory(String srcDirName, String destDirName) {
        // 默认为不覆盖目标文件
        return moveDirectory(srcDirName, destDirName, false);
    }

    /**
     * 移动目录。
     * 
     * @param srcDirName
     *            待移动的原目录名
     * @param destDirName
     *            目标目录名
     * @param overlay
     *            如果目标目论存在，是否覆盖
     * @return 目录移动成功返回true，否则返回false
     */
    public static boolean moveDirectory(String srcDirName, String destDirName, boolean overlay) {
        // 判断原目录是否存在
        File srcDir = new File(srcDirName);
        if (!srcDir.exists()) {
            logger.error("移动目录失败：原目录" + srcDirName + "不存在！");
            return false;
        } else if (!srcDir.isDirectory()) {
            logger.error("移动目录失败：" + srcDirName + "不是一个目录！");
            return false;
        }
        // 如果目标文件夹名不以文件分隔符结尾，自动添加文件分隔符
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        File destDir = new File(destDirName);
        // 如果目标文件夹存在，
        if (destDir.exists()) {
            if (overlay) {
                // 允许覆盖则删除已存在的目标目录
                logger.error("目标目录已存在，准备删除它！");
                if (!delete(destDirName)) {
                    logger.error("移动目录失败：删除目标目录" + destDirName + "失败！");
                }
            } else {
                logger.error("移动目录失败：目标目录" + destDirName + "已存在！");
                return false;
            }
        } else {
            // 创建目标目录
            logger.error("目标目录不存在，准备创建它！");
            if (!destDir.mkdirs()) {
                logger.error("移动目录失败：创建目标目录失败！");
                return false;
            }
        }
        boolean flag = true;
        // 移动原目录下的文件和子目录到目标目录下------------递归调用
        File[] files = srcDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 移动子文件
            if (files[i].isFile()) {
                flag = moveFile(files[i].getAbsolutePath(), destDirName + files[i].getName(),
                    overlay);
                if (!flag) {
                    break;
                }
            }
            // 移动子目录
            else if (files[i].isDirectory()) {
                flag = moveDirectory(files[i].getAbsolutePath(), destDirName + files[i].getName(),
                    overlay);
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            logger.error("移动目录" + srcDirName + "至" + destDirName + "失败！");
            return false;
        }
        // 删除原目录
        if (deleteDirectory(srcDirName)) {
            logger.error("移动目录" + srcDirName + "至" + destDirName + "成功！");
            return true;
        } else {
            logger.error("移动目录" + srcDirName + "至" + destDirName + "失败！");
            return false;
        }
    }

    /**
     * 复制单个文件， 如果目标文件存在，则不覆盖。
     * 
     * @param srcFileName
     *            待复制的文件名
     * @param destFileName
     *            目标文件名
     * @return 如果复制成功，则返回true，否则返回false
     */
    public static boolean copyFile(String srcFileName, String destFileName) {
        return copyFile(srcFileName, destFileName, false);
    }

    /**
     * 复制单个文件
     * 
     * @param srcFileName
     *            待复制的文件名
     * @param destFileName
     *            目标文件名
     * @param overlay
     *            如果目标文件存在，是否覆盖
     * @return 如果复制成功，则返回true，否则返回false
     */
    public static boolean copyFile(String srcFileName, String destFileName, boolean overlay) {
        // 判断原文件是否存在
        File srcFile = new File(srcFileName);
        if (!srcFile.exists()) {
            logger.error("复制文件失败：原文件" + srcFileName + "不存在！");
            return false;
        } else if (!srcFile.isFile()) {
            logger.error("复制文件失败：" + srcFileName + "不是一个文件！");
            return false;
        }
        // 判断目标文件是否存在
        File destFile = new File(destFileName);
        if (destFile.exists()) {
            // 如果目标文件存在，而且复制时允许覆盖。
            if (overlay) {
                // 删除已存在的目标文件，无论目标文件是目录还是单个文件
                logger.error("目标文件已存在，准备删除它！");
                if (!delete(destFileName)) {
                    logger.error("复制文件失败：删除目标文件" + destFileName + "失败！");
                    return false;
                }
            } else {
                logger.error("复制文件失败：目标文件" + destFileName + "已存在！");
                return false;
            }
        } else {
            if (!destFile.getParentFile().exists()) {
                // 如果目标文件所在的目录不存在，则创建目录
                logger.error("目标文件所在的目录不存在，准备创建它！");
                if (!destFile.getParentFile().mkdirs()) {
                    logger.error("复制文件失败：创建目标文件所在的目录失败！");
                    return false;
                }
            }
        }
        // 准备复制文件
        int byteread = 0;// 读取的位数
        InputStream in = null;
        OutputStream out = null;
        try {
            // 打开原文件
            in = new FileInputStream(srcFile);
            // 打开连接到目标文件的输出流
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];
            // 一次读取1024个字节，当byteread为-1时表示文件已经读完
            while ((byteread = in.read(buffer)) != -1) {
                // 将读取的字节写入输出流
                out.write(buffer, 0, byteread);
            }
            logger.error("复制单个文件" + srcFileName + "至" + destFileName + "成功！");
            return true;
        } catch (Exception e) {
            logger.error("复制文件失败：", e);
            return false;
        } finally {
            // 关闭输入输出流，注意先关闭输出流，再关闭输入流
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error(e);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error(e);
                }
            }
        }
    }

    /**
     * 复制整个目录的内容，如果目标目录存在，则不覆盖
     * 
     * @param srcDirName
     *            待复制的目录名
     * @param destDirName
     *            目标目录名
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyDirectory(String srcDirName, String destDirName) {
        return copyDirectory(srcDirName, destDirName, false);
    }

    /**
     * 复制整个目录的内容
     * 
     * @param srcDirName
     *            待复制的目录名
     * @param destDirName
     *            目标目录名
     * @param overlay
     *            如果目标目录存在，是否覆盖
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyDirectory(String srcDirName, String destDirName, boolean overlay) {
        // 判断原目录是否存在
        File srcDir = new File(srcDirName);
        if (!srcDir.exists()) {
            logger.error("复制目录失败：原目录" + srcDirName + "不存在！");
            return false;
        } else if (!srcDir.isDirectory()) {
            logger.error("复制目录失败：" + srcDirName + "不是一个目录！");
            return false;
        }
        // 如果目标文件夹名不以文件分隔符结尾，自动添加文件分隔符
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        File destDir = new File(destDirName);
        // 如果目标文件夹存在，
        if (destDir.exists()) {
            if (overlay) {
                // 允许覆盖则删除已存在的目标目录
                logger.error("目标目录已存在，准备删除它！");
                if (!delete(destDirName)) {
                    logger.error("复制目录失败：删除目标目录" + destDirName + "失败！");
                }
            } else {
                logger.error("复制目录失败：目标目录" + destDirName + "已存在！");
                return false;
            }
        } else {
            // 创建目标目录
            logger.error("目标目录不存在，准备创建它！");
            if (!destDir.mkdirs()) {
                logger.error("复制目录失败：创建目标目录失败！");
                return false;
            }
        }
        boolean flag = true;
        // 列出源文件夹下所有文件（包括子目录）的文件名
        File[] files = srcDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 如果是一个单个文件，则进行复制
            if (files[i].isFile()) {
                flag = copyFile(files[i].getAbsolutePath(), destDirName + files[i].getName());
                if (!flag) {
                    break;
                }
            }
            // 如果是子目录，继续复制目录
            if (files[i].isDirectory()) {
                flag = copyDirectory(files[i].getAbsolutePath(), destDirName + files[i].getName());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            logger.error("复制目录" + srcDirName + "至" + destDirName + "失败！");
            return false;
        }
        logger.error("复制目录" + srcDirName + "至" + destDirName + "成功！");
        return true;

    }

    /**
     * 查找文件。
     * @param baseDirName       待查找的目录
     * @param targetFileName    目标文件名，支持通配符形式
     * @param count             期望结果数目，如果为0，则表示查找全部。
     * @return      满足查询条件的文件名列表
     */
    public static List findFiles(String baseDirName, String targetFileName, int count) {
        /**
         * 算法简述：
         * 从某个给定的需查找的文件夹出发，搜索该文件夹的所有子文件夹及文件，
         * 若为文件，则进行匹配，匹配成功则加入结果集，若为子文件夹，则进队列。
         * 队列不空，重复上述操作，队列为空，程序结束，返回结果。
         */
        List fileList = new ArrayList();
        //判断目录是否存在
        File baseDir = new File(baseDirName);
        if (!baseDir.exists() || !baseDir.isDirectory()) {
            logger.error("文件查找失败：" + baseDirName + "不是一个目录！");
            return fileList;
        }
        String tempName = null;
        //创建一个队列，Queue在第四章有定义
        Queue queue = new ArrayDeque();//实例化队列 
        queue.add(baseDir);//入队 
        File tempFile = null;
        while (!queue.isEmpty()) {
            //从队列中取目录
            tempFile = (File) queue.remove();
            if (tempFile.exists() && tempFile.isDirectory()) {
                File[] files = tempFile.listFiles();
                for (int i = 0; i < files.length; i++) {
                    //如果是目录则放进队列
                    if (files[i].isDirectory()) {
                        queue.add(files[i]);
                    } else {
                        //如果是文件则根据文件名与目标文件名进行匹配 
                        tempName = files[i].getName();
                        if (wildcardMatch(targetFileName, tempName)) {
                            //匹配成功，将文件名添加到结果集
                            fileList.add(files[i].getAbsoluteFile());
                            //如果已经达到指定的数目，则退出循环
                            if ((count != 0) && (fileList.size() >= count)) {
                                return fileList;
                            }
                        }
                    }
                }
            }
        }

        return fileList;
    }

    /**
     * 通配符匹配
     * @param pattern   通配符模式
     * @param str   待匹配的字符串
     * @return  匹配成功则返回true，否则返回false
     */
    private static boolean wildcardMatch(String pattern, String str) {
        int patternLength = pattern.length();
        int strLength = str.length();
        int strIndex = 0;
        char ch;
        for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
            ch = pattern.charAt(patternIndex);
            if (ch == '*') {
                //通配符星号*表示可以匹配任意多个字符
                while (strIndex < strLength) {
                    if (wildcardMatch(pattern.substring(patternIndex + 1), str.substring(strIndex))) {
                        return true;
                    }
                    strIndex++;
                }
            } else if (ch == '?') {
                //通配符问号?表示匹配任意一个字符
                strIndex++;
                if (strIndex > strLength) {
                    //表示str中已经没有字符匹配?了。
                    return false;
                }
            } else {
                if ((strIndex >= strLength) || (ch != str.charAt(strIndex))) {
                    return false;
                }
                strIndex++;
            }
        }
        return (strIndex == strLength);
    }

}
