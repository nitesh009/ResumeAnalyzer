package com.tom.service;

import java.io.IOException;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;

import com.tom.exception.ResumeAnalyzerServiceException;

public interface UIMAService {
	
	//save parsed json string
	public void saveParsedResumeJson(String contextPath) throws ResumeAnalyzerServiceException, AnalysisEngineProcessException, InvalidXMLException, ResourceInitializationException, IOException;

}
