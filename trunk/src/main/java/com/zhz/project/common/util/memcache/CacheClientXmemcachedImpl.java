package com.zhz.project.common.util.memcache;

import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

/**
 * ICacheClient 的 xmemcached客户端 实现(并发性能甚至超越spymemcached)
 * 
 * @author LiWei <a href="mailto:liwei2672@gmail.com">liwei2672@gmail.com</a>
 * @version 创建时间：2010-5-9 下午08:58:08
 * 
 */
public class CacheClientXmemcachedImpl implements ICacheClient {

    private MemcachedClient cacheProvider;

    public void setCacheProvider(MemcachedClient cacheProvider) {
        this.cacheProvider = cacheProvider;
    }

    public boolean add(String key, Object value) {
        try {
            return this.cacheProvider.add(key, 0, value);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean add(String key, Object value, int expiry) {
        try {
            return this.cacheProvider.add(key, expiry, value);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean add(String key, Object value, int expiry, Integer hashCode) {
        try {
            return this.cacheProvider.add(key, expiry, value);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 因为xmemcached 自动分配负载，这里不需要使用hashcode
     */

    public boolean add(String key, Object value, Integer hashCode) {
        try {
            return this.cacheProvider.add(key, 0, value);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String key) {
        try {
            return this.cacheProvider.delete(key);
        } catch (TimeoutException e) {

            e.printStackTrace();
        } catch (InterruptedException e) {

            e.printStackTrace();
        } catch (MemcachedException e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String key, int expiry) {
        try {
            return this.cacheProvider.delete(key, expiry);
        } catch (TimeoutException e) {

            e.printStackTrace();
        } catch (InterruptedException e) {

            e.printStackTrace();
        } catch (MemcachedException e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String key, Integer hashCode, int expiry) {
        try {
            return this.cacheProvider.delete(key, expiry);
        } catch (TimeoutException e) {

            e.printStackTrace();
        } catch (InterruptedException e) {

            e.printStackTrace();
        } catch (MemcachedException e) {

            e.printStackTrace();
        }
        return false;
    }

    public Object get(String key) {
        try {
            return this.cacheProvider.get(key);
        } catch (TimeoutException e) {

            e.printStackTrace();
        } catch (InterruptedException e) {

            e.printStackTrace();
        } catch (MemcachedException e) {

            e.printStackTrace();
        }
        return null;
    }

    public Object get(String key, Integer hashCode) {
        try {
            return this.cacheProvider.get(key);
        } catch (TimeoutException e) {

            e.printStackTrace();
        } catch (InterruptedException e) {

            e.printStackTrace();
        } catch (MemcachedException e) {

            e.printStackTrace();
        }
        return null;
    }

    public Object get(String key, Integer hashCode, boolean asString) {
        return null;
    }

    public boolean keyExists(String key) {
        return this.get(key) == null ? false : true;
    }

    public boolean replace(String key, Object value) {
        try {
            return this.cacheProvider.replace(key, 0, value);
        } catch (TimeoutException e) {

            e.printStackTrace();
        } catch (InterruptedException e) {

            e.printStackTrace();
        } catch (MemcachedException e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean replace(String key, Object value, int expiry) {
        try {
            return this.cacheProvider.replace(key, expiry, value);
        } catch (TimeoutException e) {

            e.printStackTrace();
        } catch (InterruptedException e) {

            e.printStackTrace();
        } catch (MemcachedException e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean replace(String key, Object value, int expiry, Integer hashCode) {
        try {
            return this.cacheProvider.replace(key, expiry, value);
        } catch (TimeoutException e) {

            e.printStackTrace();
        } catch (InterruptedException e) {

            e.printStackTrace();
        } catch (MemcachedException e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean replace(String key, Object value, Integer hashCode) {
        try {
            return this.cacheProvider.replace(key, 0, value);
        } catch (TimeoutException e) {

            e.printStackTrace();
        } catch (InterruptedException e) {

            e.printStackTrace();
        } catch (MemcachedException e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean set(String key, Object value) {
        try {
            return this.cacheProvider.set(key, 0, value);
        } catch (TimeoutException e) {

            e.printStackTrace();
        } catch (InterruptedException e) {

            e.printStackTrace();
        } catch (MemcachedException e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean set(String key, Object value, int expiry) {
        try {
            return this.cacheProvider.set(key, expiry, value);
        } catch (TimeoutException e) {

            e.printStackTrace();
        } catch (InterruptedException e) {

            e.printStackTrace();
        } catch (MemcachedException e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean set(String key, Object value, int expiry, Integer hashCode) {
        try {
            return this.cacheProvider.set(key, expiry, value);
        } catch (TimeoutException e) {

            e.printStackTrace();
        } catch (InterruptedException e) {

            e.printStackTrace();
        } catch (MemcachedException e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean set(String key, Object value, Integer hashCode) {
        try {
            return this.cacheProvider.set(key, 0, value);
        } catch (TimeoutException e) {

            e.printStackTrace();
        } catch (InterruptedException e) {

            e.printStackTrace();
        } catch (MemcachedException e) {

            e.printStackTrace();
        }
        return false;
    }

}
