package cn.lmh.oauth.client.web;

import javax.annotation.*
import javax.servlet.http.HttpServletResponse

import cn.lmh.oauth.client.service.CacheService
import groovy.transform.CompileStatic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*

@CompileStatic
@Controller
@RequestMapping("/sapi/oauth/")
public class CallbackController {
	
	@Resource(name="oauth.client.service.cache")
	CacheService cacheService;
	
	@RequestMapping(value="/")
	@ResponseBody
	String post(@RequestParam("sid") String sid, HttpServletResponse response) {
		String value = cacheService;
		if(null == value){
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		response.setStatus(HttpServletResponse.SC_OK);
	}

}
