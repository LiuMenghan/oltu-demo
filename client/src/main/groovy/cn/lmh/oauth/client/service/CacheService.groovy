package cn.lmh.oauth.client.service;

public interface CacheService {
	String get(String key);
	boolean put(String key, String value);
}
