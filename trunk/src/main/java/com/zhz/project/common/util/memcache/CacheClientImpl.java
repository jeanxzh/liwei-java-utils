package com.zhz.project.common.util.memcache;

import java.util.Date;

import com.danga.MemCached.MemCachedClient;

/**
 * 缓存的一个实现类(memcached client)
 * 
 * @author LiWei <a href="mailto:liwei2672@gmail.com">liwei2672@gmail.com</a>
 * @version 创建时间：2010-5-9 下午08:57:07
 * 
 */
public class CacheClientImpl implements ICacheClient {

    private MemCachedClient cacheProvider;

    public boolean add(String key, Object value) {
        return this.cacheProvider.add(key, value);
    }

    public boolean add(String key, Object value, int expiry) {
        return this.cacheProvider.add(key, value, new Date(expiry));
    }

    public boolean add(String key, Object value, int expiry, Integer hashCode) {
        return this.cacheProvider.add(key, value, new Date(expiry), hashCode);
    }

    public boolean add(String key, Object value, Integer hashCode) {
        return this.cacheProvider.add(key, value, hashCode);
    }

    public boolean delete(String key) {
        return this.cacheProvider.delete(key);
    }

    public boolean delete(String key, int expiry) {
        return this.cacheProvider.delete(key, new Date(expiry));
    }

    public boolean delete(String key, Integer hashCode, int expiry) {
        return this.cacheProvider.delete(key, hashCode, new Date(expiry));
    }

    public Object get(String key) {
        return this.cacheProvider.get(key);
    }

    public Object get(String key, Integer hashCode) {
        return this.cacheProvider.get(key, hashCode);
    }

    public Object get(String key, Integer hashCode, boolean asString) {
        return this.cacheProvider.get(key, hashCode, asString);
    }

    public boolean keyExists(String key) {
        return this.cacheProvider.keyExists(key);
    }

    public boolean replace(String key, Object value) {
        return this.cacheProvider.replace(key, value);
    }

    public boolean replace(String key, Object value, int expiry) {
        return this.cacheProvider.replace(key, value, new Date(expiry));
    }

    public boolean replace(String key, Object value, int expiry, Integer hashCode) {
        return this.cacheProvider.replace(key, value, new Date(expiry), hashCode);
    }

    public boolean replace(String key, Object value, Integer hashCode) {
        return this.cacheProvider.replace(key, value, hashCode);
    }

    public boolean set(String key, Object value) {
        return this.cacheProvider.set(key, value);
    }

    public boolean set(String key, Object value, int expiry) {
        return this.cacheProvider.set(key, value, new Date(expiry));
    }

    public boolean set(String key, Object value, int expiry, Integer hashCode) {
        return this.cacheProvider.set(key, value, new Date(expiry), hashCode);
    }

    public boolean set(String key, Object value, Integer hashCode) {
        return this.cacheProvider.set(key, value, hashCode);
    }

    public MemCachedClient getCacheProvider() {
        return cacheProvider;
    }

    public void setCacheProvider(MemCachedClient cacheProvider) {
        this.cacheProvider = cacheProvider;
    }
}
