package com.zhz.project.common.util.result;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author LiWei：<a href="mailto:liwei2672@gmail.com">liwei2672@gmail.com</a>
 * @version Result.java, v 0.1 May 24, 2012 3:29:49 PM
 */
public interface Result extends Serializable {
    /** 在models表中代表默认的model的key。 */
    String DEFAULT_MODEL_KEY = "_defaultModel";

    /**
     * 请求是否成功。
     *
     * @return 如果成功，则返回<code>true</code>
     */
    boolean isSuccess();

    /**
     * 设置请求成功标志。
     *
     * @param success 成功标志
     */
    void setSuccess(boolean success);

    /**
     * 取得默认model对象的key。
     *
     * @return 默认model对象的key
     */
    String getDefaultModelKey();

    /**
     * 取得model对象。
     * 
     * <p>
     * 此调用相当于<code>getModels().get(getDefaultModelKey())</code>。
     * </p>
     *
     * @return model对象
     */
    Object getDefaultModel();

    /**
     * 设置model对象。
     * 
     * <p>
     * 此调用相当于<code>getModels().put(DEFAULT_MODEL_KEY, model)</code>。
     * </p>
     *
     * @param model model对象
     */
    void setDefaultModel(Object model);

    /**
     * 设置model对象。
     * 
     * <p>
     * 此调用相当于<code>getModels().put(key, model)</code>。
     * </p>
     *
     * @param key 字符串key
     * @param model model对象
     */
    void setDefaultModel(String key, Object model);

    /**
     * 取得所有model对象。
     *
     * @return model对象表
     */
    Map getModels();
}