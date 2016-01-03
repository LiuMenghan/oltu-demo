package cn.lmh.oauth.client.service;

import groovy.transform.CompileStatic;

@CompileStatic
public interface OAuthService {
	static final String CALLBACK_URL = "callback_url";
	Map getUserInfo(String sid);
}
