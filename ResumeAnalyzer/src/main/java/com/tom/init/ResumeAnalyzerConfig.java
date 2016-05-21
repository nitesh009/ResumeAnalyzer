package com.tom.init;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
@ComponentScan(basePackages={"com.tom"})
@EnableAutoConfiguration
public class ResumeAnalyzerConfig {
	
	@Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("10000KB");
        factory.setMaxRequestSize("10000KB");
        return factory.createMultipartConfig();
    }
	
	public static void main(String[] args){
		ApplicationContext ctx= SpringApplication.run(ResumeAnalyzerConfig.class,args);
	}
}
