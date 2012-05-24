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
 * @author LiWei：<a href="mailto:liwei2672@gmail.com">liwei2672@gmail.com</a>
 * @version CeiceiVelocityEngine.java, v 0.1 May 24, 2012 3:29:34 PM
 */
public class CeiceiVelocityEngine {
    private VelocityEngine ve;
    private String         defaultEncoding;
    private String         resource;

    public void init() throws Exception {
        ve = new VelocityEngine();
        String fileDir = CeiceiVelocityEngine.class.getResource(resource).getPath();
        // 初始化并取得Velocity引擎

        Properties properties = new Properties();
        properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, fileDir);
        properties.setProperty(Velocity.INPUT_ENCODING, defaultEncoding);
        properties.setProperty("resource.loader", "jar,file");
        properties.setProperty("jar.resource.loader.description", "properties Jar Resource Loader");
        properties.setProperty("jar.resource.loader.class",
            "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        properties.setProperty("jar.resource.loader.cache", "false");
        properties.setProperty("jar.resource.loader.modificationCheckInterval", "2");

        //初始化
        ve.init(properties);
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
    public String getContent(Map<String, Object> map, String templateLocation) throws Exception {

        // 取得velocity的模版
        Template t = ve.getTemplate(templateLocation);

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

    public void setDefaultEncoding(String defaultEncoding) {
        this.defaultEncoding = defaultEncoding;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
