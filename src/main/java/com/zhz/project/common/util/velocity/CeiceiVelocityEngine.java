package com.zhz.project.common.util.velocity;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

/**
 *
 * @author 李威-liwei2672@gmail.com
 * @version $Id: HelloVelocity.java, v 0.1 May 9, 2012 1:48:28 PM 李威 Exp $
 */

public class CeiceiVelocityEngine {
    private static VelocityEngine ve;

    public static VelocityEngine getInstance(String resource, String defaultEncoding)
                                                                                     throws Exception {
        if (ve == null) {

            ve = new VelocityEngine();
            String fileDir = CeiceiVelocityEngine.class.getResource(resource).getPath();
            // 初始化并取得Velocity引擎

            Properties properties = new Properties();
            properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, fileDir);
            properties.setProperty(Velocity.INPUT_ENCODING, defaultEncoding);
            properties.setProperty("resource.loader", "jar,file");
            properties.setProperty("jar.resource.loader.description",
                "properties Jar Resource Loader");
            properties.setProperty("jar.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            properties.setProperty("jar.resource.loader.cache", "false");
            properties.setProperty("jar.resource.loader.modificationCheckInterval", "2");

            //初始化
            ve.init(properties);
        }
        return ve;
    }

    /**
     * 得到模版内容
     *
     * @param context
     * @param templateLocation
     * @return
     * @throws Exception 
     * @throws  
     */
    public String getContent(Map<String, Object> map, String templateLocation, String resource,
                             String defaultEncoding) throws Exception {

        VelocityEngine velocityEngine = getInstance(resource, defaultEncoding);
        // 取得velocity的模版
        Template t = velocityEngine.getTemplate(templateLocation);

        // 取得velocity的上下文context
        VelocityContext context = new VelocityContext();

        Set<java.util.Map.Entry<String, Object>> keys = map.entrySet();
        for (Iterator<java.util.Map.Entry<String, Object>> it = keys.iterator(); it.hasNext();) {
            java.util.Map.Entry<String, Object> entry = (java.util.Map.Entry<String, Object>) it
                .next();
            context.put((String) entry.getKey(), entry.getValue());
        }

        // 输出流
        StringWriter writer = new StringWriter();
        // 转换输出
        t.merge(context, writer);

        return writer.toString();
    }

}
