package com.tom.engine;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.tika.exception.TikaException;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

@EnableAutoConfiguration
@RestController
public class MainEngine {
		
	@RequestMapping(value="/test",method=RequestMethod.GET)
	public String getTest(HttpServletRequest request) throws IOException, TikaException, SAXException{
		System.out.println("request.getContextPath(): "+request.getContextPath());
		System.out.println("request.getServletContext().getContextPath(): "+request.getServletContext().getContextPath());
		return "CV Analyzer Server Is running, context path is: "+request.getServletContext().getContextPath();
		
	}

}
