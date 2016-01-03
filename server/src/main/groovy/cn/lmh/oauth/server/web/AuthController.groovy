package cn.lmh.oauth.server.web;

import cn.lmh.oauth.server.service.ValidateService
import groovy.transform.CompileStatic
import javax.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.apache.oltu.oauth2.as.issuer.OAuthIssuer
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl
import org.apache.oltu.oauth2.as.issuer.MD5Generator
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest
import org.apache.oltu.oauth2.as.response.OAuthASResponse
import org.apache.oltu.oauth2.common.exception.OAuthProblemException
import org.apache.oltu.oauth2.common.message.OAuthResponse
import org.springframework.boot.*
import org.springframework.boot.autoconfigure.*
import org.springframework.stereotype.*
import org.springframework.web.bind.annotation.*

@CompileStatic
@Controller
@RequestMapping("/sapi/auth")
public class AuthController {

	@Resource(name="oath.server.service.validate")
	ValidateService service;
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	String post(HttpServletRequest request, HttpServletResponse response) {
		OAuthTokenRequest oauthRequest = null;
		OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
		try {
			oauthRequest = new OAuthTokenRequest(request);
			service.validateClient(oauthRequest);
			String authzCode = oauthRequest.getCode();
			// some code
			String accessToken = oauthIssuerImpl.accessToken();
			String refreshToken = oauthIssuerImpl.refreshToken();
			// some code
			OAuthResponse r = OAuthASResponse
					.tokenResponse(HttpServletResponse.SC_OK)
					.setAccessToken(accessToken)
					.setExpiresIn("3600")
					.setRefreshToken(refreshToken)
					.buildJSONMessage();
			response.setStatus(r.getResponseStatus());
			PrintWriter pw = response.getWriter();
			pw.print(r.getBody());
			pw.flush();
			pw.close();
			//if something goes wrong
		} catch(OAuthProblemException ex) {
			OAuthResponse r = OAuthResponse
					.errorResponse(401)
					.error(ex)
					.buildJSONMessage();
			response.setStatus(r.getResponseStatus());
			PrintWriter pw = response.getWriter();
			pw.print(r.getBody());
			pw.flush();
			pw.close();
			response.sendError(401);
		}
	}
}
