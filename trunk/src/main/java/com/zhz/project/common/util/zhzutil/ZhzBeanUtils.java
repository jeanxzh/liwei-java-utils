/**
 * Alipay.com Inc.
 * Copyright (c) 2010-2010 All Rights Reserved.
 */
package com.zhz.project.common.util.zhzutil;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author laopo
 * @version $Id: ZhzBeanUtils.java, v 0.1 2010-12-12 下午02:58:45 laopo Exp $
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