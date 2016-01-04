package cn.lmh.oauth.server.web.interceptor;

import groovy.transform.CompileStatic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.annotation.*

import org.apache.commons.lang3.StringUtils
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.*

import cn.lmh.oauth.server.service.CacheService;

@Component("oauth.server.web.interceptor.resource")
@CompileStatic
public class ResourceInterceptor implements HandlerInterceptor {	
	
	@Resource(name="oauth.server.service.cache")
	CacheService cacheService;

		@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		try {
			// Make the OAuth Request out of this request and validate it
			// Specify where you expect OAuth access token (request header, body or query string)
			OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.HEADER);

			// Get the access token
			String accessToken = oauthRequest.getAccessToken();
			String refreshToken = cacheService.get(accessToken);
			if(StringUtils.isEmpty(refreshToken)){
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				return false
			}
			// ... validate access token

			// if something goes wrong
		} catch (OAuthProblemException ex) {
			// build error response
			OAuthResponse oauthResponse = OAuthRSResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED).setRealm("Album Example")
					.buildHeaderMessage();

			response.addHeader(OAuth.HeaderType.WWW_AUTHENTICATE, oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
			return false;

		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
