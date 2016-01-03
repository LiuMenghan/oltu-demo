package cn.lmh.oauth.server;

import groovy.transform.CompileStatic;
import org.springframework.boot.SpringApplication
import org.springframework.context.annotation.ImportResource
import org.springframework.boot.autoconfigure.SpringBootApplication

@CompileStatic
@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
