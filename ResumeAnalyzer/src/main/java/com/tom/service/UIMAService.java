package com.tom.service;

import com.tom.exception.ResumeAnalyzerServiceException;

public interface UIMAService {
	
	//save parsed json string
	public void saveParsedResumeJson() throws ResumeAnalyzerServiceException;

}
