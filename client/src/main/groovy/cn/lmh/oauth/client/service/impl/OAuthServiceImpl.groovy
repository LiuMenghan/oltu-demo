package cn.lmh.oauth.client.service.impl;

import groovy.transform.CompileStatic

import javax.annotation.*

import org.apache.commons.lang3.StringUtils
import org.apache.oltu.oauth2.common.OAuth
import org.apache.oltu.oauth2.common.message.types.GrantType
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

import cn.lmh.oauth.client.service.CacheService
import cn.lmh.oauth.client.service.OAuthService

@CompileStatic
@Service("oauth.client.service.oauth")
public class OAuthServiceImpl implements OAuthService{

	private String callbackUrl = "http://localhost:8080/sapi/oauth/callback";
	private String authUrl = "http://localhost:8081/sapi/auth";
	private String resourceUrl = "http://localhost:8081/sapi/auth";
	
	@Resource(name="oauth.client.service.cache")
	CacheService cacheService;
	
	
	public static RestTemplate client = new RestTemplate();
	
	@Override
	public Map getUserInfo(String sid) {
		String token = cacheService.get(sid);
		
		if(StringUtils.isEmpty(token)){
			//if no token exisits
			Map headers = [
				"Content-type" : OAuth.ContentType.URL_ENCODED
			];
		
			Map body = [
				"${OAuth.OAUTH_GRANT_TYPE}" : GrantType.CLIENT_CREDENTIALS.toString(),
				"${OAuth.OAUTH_CLIENT_ID}" : sid,
				"${OAuth.OAUTH_CLIENT_SECRET}" : sid
			];			
			
			
			HttpEntity<MultiValueMap, MultiValueMap> request = new HttpEntity(new LinkedMultiValueMap(body), new LinkedMultiValueMap(headers));
			ResponseEntity<Map> response = client.postForEntity(new URI(authUrl), request, Map.class);
			token = response.body.get(OAuth.OAUTH_ACCESS_TOKEN).toString();
			
			cacheService.put(sid, token);
		}
		
		return null;
	}

}
