package cn.lmh.oauth.client.service.impl;

import javax.annotation.PostConstruct;

import net.sf.ehcache.Cache
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element
import net.sf.ehcache.config.CacheConfiguration

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import groovy.transform.CompileStatic;
import cn.lmh.oauth.client.service.CacheService;

@CompileStatic
@Service("oauth.client.service.cache")
public class CacheServiceImpl implements CacheService {
	private static final Logger logger = LogManager.getLogger(CacheServiceImpl.class);
	
	private CacheManager cacheManager = CacheManager.newInstance();
		
	private Cache cache = new Cache(new CacheConfiguration());
	
	@PostConstruct
	public void init() throws Exception {
		cacheManager.addCache(cache);
	}

	@Override
	public String get(String key) {
		Element ele = cache.get(key);
		return ele ? ele.value : null;
	}

	@Override
	public boolean put(String key, String value) {
		try{
			Element ele = new Element(key, value);
			cache.put(ele);
			return true;
		}catch(Exception e){
			logger.error(e);
			return false;
		}
	}

}
