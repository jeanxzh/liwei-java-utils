package com.zhz.project.common.util.memcache;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.springframework.beans.factory.InitializingBean;

import com.zhz.project.common.util.alibaba.StringUtil;

/**
 *
 * @author LiWei：<a href="mailto:liwei2672@gmail.com">liwei2672@gmail.com</a>
 * @version MethodCacheInterceptor.java, v 0.1 May 24, 2012 3:31:54 PM
 */
public class MethodCacheInterceptor implements MethodInterceptor, InitializingBean {
    private static Logger        logger        = Logger.getLogger(MethodCacheInterceptor.class);

    // 默认一个小时失效
    private Integer              defaultExpiry = 1000 * 60;
    private ICacheClient         cacheClient;
    private Map<String, Integer> timeMap;

    /**
     * 拦截Service/DAO的方法，并查找该结果是否存在，如果存在就返回cache中的值，
     * 否则，返回数据库查询结果，并将查询结果放入cacheClient
     */
    public Object invoke(MethodInvocation invocation) throws Throwable {

        // 构造CacheKey的元素
        String targetName = invocation.getThis().getClass().getName();
        String methodName = invocation.getMethod().getName();
        Object[] arguments = invocation.getArguments();

        String cacheKey = getCacheKey(targetName, methodName, arguments);

        // 查询缓存
        Object result = cacheClient.get(cacheKey);
        long startTime = System.currentTimeMillis();
        if (result == null) {
            // 缓存中没有对应的对象，则调用原有方法，获得结果，保存该结果到缓存中
            logger.debug("Hold up method , Get method result and create cache........!");
            result = invocation.proceed();
            long endTime = System.currentTimeMillis();
            if (result != null) {
                int expiry = getExpiry(cacheKey);
                // 保存到缓存中
                cacheClient.set(cacheKey, (Serializable) result, expiry);
                logger.info("\r\n" + targetName + "." + methodName
                            + " method first called and will be cached!!!。 \r\nTime="
                            + (endTime - startTime) + "ms" + " cacheKey:" + cacheKey
                            + " cacheTime:" + expiry);
            } else {
                logger.info("\r\n" + targetName + "." + methodName
                            + " method first called。\r\nTime=" + (endTime - startTime) + "ms"
                            + ".But the result is null will not be cached" + " cacheKey:"
                            + cacheKey);
            }
        } else {
            long endTime = System.currentTimeMillis();
            logger.info("\r\n" + targetName + "." + methodName
                        + " the result is read from cache。\r\nTime=" + (endTime - startTime) + "ms"
                        + " cacheKey:" + cacheKey);
        }
        return result;
    }

    /**
     * 获得cache key的方法，cache key是Cache中一个Object的唯一标识 cache key包括 包名+类名+方法名+参数
     */
    private String getCacheKey(String targetName, String methodName, Object[] arguments) {
        StringBuffer sb = new StringBuffer();
        sb.append(targetName).append(".").append(methodName);
        if ((arguments != null) && (arguments.length != 0)) {
            for (int i = 0; i < arguments.length; i++) {
                sb.append(".").append(arguments[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 获得失效时间
     */
    private int getExpiry(String cacheKey) {

        Set<Entry<String, Integer>> keys = timeMap.entrySet();
        Iterator<java.util.Map.Entry<String, Integer>> it = keys.iterator();

        for (; it.hasNext();) {
            java.util.Map.Entry<String, Integer> entry = (java.util.Map.Entry<String, Integer>) it
                .next();
            String key = (String) entry.getKey();
            if (StringUtil.contains(cacheKey, key)) {
                return (Integer) entry.getValue();
            }
        }
        return defaultExpiry;
    }

    /**
     * 检查cache是否为空
     */
    public void afterPropertiesSet() throws Exception {
        Assert.assertNotNull(cacheClient);
    }

    public void setCacheClient(ICacheClient cacheClient) {
        this.cacheClient = cacheClient;
    }

    public void setTimeMap(Map<String, Integer> timeMap) {
        this.timeMap = timeMap;
    }

    public void setDefaultExpiry(Integer defaultExpiry) {
        this.defaultExpiry = defaultExpiry;
    }
}