package cn.lmh.oauth.server.service;

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest
import org.apache.oltu.oauth2.common.exception.OAuthProblemException

public interface ValidateService {
	static final String CALLBACK_URL = "callback_url";
	void validateClient(OAuthTokenRequest request) throws OAuthProblemException;
}
