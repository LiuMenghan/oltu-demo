package cn.lmh.oauth.server.service.impl

import groovy.transform.CompileStatic

import javax.annotation.Resource
import javax.servlet.http.HttpServletResponse

import org.apache.commons.lang3.StringUtils
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest
import org.apache.oltu.oauth2.common.exception.OAuthProblemException
import org.apache.oltu.oauth2.common.utils.OAuthUtils
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.*
import org.springframework.web.client.RestTemplate

import cn.lmh.oauth.server.service.ValidateService

@CompileStatic
@Service("oath.server.service.validate")
class ValidateServiceImpl implements ValidateService {
	private static final Logger logger = LogManager.getLogger(this.class);
	
	@Resource(name="oath.server.constant.authorizedWebSites")
	Set<String> authorizedHosts;
	
	public static RestTemplate client = new RestTemplate();
	
	@Override
	public void validateClient(OAuthTokenRequest request)
			throws OAuthProblemException {
		
		String encodedCallbackUrl = request.getParam(CALLBACK_URL);
		if(StringUtils.isEmpty(encodedCallbackUrl)){
			throw OAuthUtils.handleOAuthProblemException("Missing callback url");
		}
		
		String callbackUrl = URLDecoder.decode(encodedCallbackUrl, "UTF-8");
		URI uri = new URI(callbackUrl);
		String host = uri.getHost();
		
		if(!authorizedHosts.contains(host)){
			throw OAuthUtils.handleOAuthProblemException("Missing callback url");
		}
		
		ResponseEntity<Map<String, String>> response = client.getForEntity(uri, Map);
		if(HttpServletResponse.SC_OK != response.statusCode){			
			throw OAuthUtils.handleOAuthProblemException("Callback Validating failed");
		}

	}

}
