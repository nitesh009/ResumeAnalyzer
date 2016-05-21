package com.tom.engine;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tom.exception.ResumeAnalyzerServiceException;
import com.tom.service.UIMAService;

@EnableAutoConfiguration
@RestController
public class ProcessEngine {
	
	@Autowired
	private UIMAService uimaService;
	//method to upload single file
		@RequestMapping(value="/processRawResumes",method=RequestMethod.POST)
		public void uploadFile(HttpServletRequest request) throws ResumeAnalyzerServiceException, AnalysisEngineProcessException, InvalidXMLException, ResourceInitializationException, IOException{
			System.out.println("Inside processRawResumes");
			uimaService.saveParsedResumeJson(request.getServletContext().getContextPath());
			
		}

}
