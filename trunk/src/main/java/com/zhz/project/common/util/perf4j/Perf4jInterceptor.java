package com.zhz.project.common.util.perf4j;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.perf4j.StopWatch;
import org.perf4j.log4j.Log4JStopWatch;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

/**
 * Perf4j拦截器，通过AOP记录方法的执行时间
 *
 * @author LiWei：<a href="mailto:liwei2672@gmail.com">liwei2672@gmail.com</a>
 * @version Perf4jInterceptor.java, v 0.1 May 24, 2012 3:30:06 PM
 */
public class Perf4jInterceptor implements MethodBeforeAdvice, AfterReturningAdvice {

    private Map<String, StopWatch> watches = new HashMap<String, StopWatch>();

    public void before(Method method, Object[] args, Object target) throws Throwable {
        String completeMethodName = getCompleteMethodName(target, method);
        // 创建性能日志记录器 
        StopWatch stopWatch;
        if (watches.containsKey(completeMethodName)) {
            stopWatch = watches.get(completeMethodName);
            stopWatch.start();
        } else {
            stopWatch = new Log4JStopWatch(completeMethodName, Arrays.toString(args));
            watches.put(completeMethodName, stopWatch);
        }
    }

    public void afterReturning(Object returnValue, Method method, Object[] args, Object target)
                                                                                               throws Throwable {
        String completeMethodName = getCompleteMethodName(target, method);

        // 记录性能 
        if (watches.containsKey(completeMethodName)) {
            StopWatch stopWatch = watches.get(completeMethodName);
            stopWatch.stop();
        }
    }

    /** 
     * 根据目标对象与方法获取方法完整名称 . 
     * @param target 目标对象 
     * @param method 方法 
     * @return 方法完整名称 
     */
    private String getCompleteMethodName(Object target, Method method) {
        String className = "";
        if (target != null) {
            className = target.toString();
            int loc = className.indexOf("@");
            if (loc >= 0) {
                className = className.substring(className.lastIndexOf(".") + 1, loc);
            }
        }

        return className + "_" + method.getName();
    }

}
