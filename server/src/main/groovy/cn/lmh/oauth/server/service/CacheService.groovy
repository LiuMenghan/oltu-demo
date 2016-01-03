package cn.lmh.oauth.server.service;

public interface CacheService {
	String get(String key);
	boolean put(String key, String value);
}
