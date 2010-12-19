package com.zhz.project.common.util.result;

import java.io.Serializable;
import java.util.Map;

public interface Result extends Serializable {
    /**
     * 请求是否成功。
     * 
     * @return 如果成功，则返回<code>true</code>
     */
    boolean isSuccess();

    /**
     * 设置请求成功标志。
     * 
     * @param success
     *            成功标志
     */
    void setSuccess(boolean success);

    /**
     * 取得所有model对象。
     * 
     * @return model对象表
     */
    Map getModels();

}