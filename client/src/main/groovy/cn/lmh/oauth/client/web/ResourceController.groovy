package cn.lmh.oauth.client.web;

import javax.annotation.*
import javax.servlet.http.HttpServletResponse;

import cn.lmh.oauth.client.service.CacheService;
import cn.lmh.oauth.client.service.OAuthService
import groovy.transform.CompileStatic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*

@CompileStatic
@Controller
@RequestMapping("/sapi/resource/")
public class ResourceController {
	@Resource(name="oauth.client.service.oauth")
	OAuthService service	
	
	@Resource(name="oauth.client.service.cache")
	CacheService cacheService;
	
	@RequestMapping(value="/")
	@ResponseBody
	Map post(@RequestParam("sid") String sid) {
		cacheService.put(sid, "");
		Map result = service.getUserInfo(sid);
		return result;
	}
}
