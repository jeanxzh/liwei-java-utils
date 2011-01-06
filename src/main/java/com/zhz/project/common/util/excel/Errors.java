/**
 * Zhz.net Inc.
 * Copyright (c) 2004-2011 All Rights Reserved.
 */
package com.zhz.project.common.util.excel;

import java.util.ArrayList;
import java.util.List;

/**
 *  <p>
 *   通用的Error错误包装类，可以将多个错误信息以列表的方式输出的页面上
 *   copy结算系统
 * </p>
 * 
 *
 * @author liwei
 * @version $Id: Errors.java, v 0.1 Jan 7, 2011 3:08:04 AM liwei Exp $
 */
public class Errors {
    private List<Error> errors = new ArrayList<Error>();

    /**
     * 注册一个错误
     * @param error
     */
    public void add(int row, int col, String msg) {
        errors.add(new Error(row, col, msg));
    }

    /**
     * 注册一个错误
     * @param error
     */
    public void add(String msg) {
        errors.add(new Error(msg));
    }

    /**
     * 清除所有的错误 
     *
     */
    public void clear() {
        errors.clear();
    }

    /**
     * 判断是否存在错误
     * @return
     */
    public boolean hasErrors() {
        return errors.size() > 0;
    }

    /**
     * 以列表的形式返回所有的错误信息
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();

        if (this.hasErrors()) {
            sb.append("<ul>\n");
            for (int i = 0; i < errors.size(); i++) {
                Error error = (Error) errors.get(i);
                sb.append("<li>" + error.toString() + "\n");
            }
            sb.append("</ul>");
        }

        return sb.toString();
    }

    /**
     * 解析Excel文件过程中产生的错误信息
     */
    private static class Error {
        /** */
        private StringBuffer sb = new StringBuffer();

        public Error(String msg) {
            sb.append(msg);
        }

        public Error(int row, int col, String msg) {
            sb.append("第[ ").append(row).append(" ]行，第[ ").append(col).append(" ]列, ").append(msg);
        }

        public String toString() {
            return sb.toString();
        }
    }

}