package com.zhz.project.common.util.zhzutil;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author LiWei：<a href="mailto:liwei2672@gmail.com">liwei2672@gmail.com</a>
 * @version ZhzBeanUtils.java, v 0.1 May 24, 2012 3:28:28 PM
 */
public class ZhzBeanUtils {
    private static Logger logger = Logger.getLogger(ZhzBeanUtils.class);

    public static Map describe(Object bean) {
        Map description = new HashMap();
        try {
            description = BeanUtils.describe(bean);
        } catch (Exception e) {
            logger.error("ZhzBeanUtils.describe()发生异常", e);
        }
        return description;
    }
}