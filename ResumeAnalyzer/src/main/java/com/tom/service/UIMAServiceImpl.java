package com.tom.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tom.constants.ResumeAnalyzerConstants;
import com.tom.exception.ResumeAnalyzerServiceException;

@Service
public class UIMAServiceImpl implements UIMAService{

	private TikaService tikaService;
	
	@Override
	public void saveParsedResumeJson() throws ResumeAnalyzerServiceException {
		// getting list of string from raw cv format
		List<String> parsedResumes=tikaService.parseRawFiles(ResumeAnalyzerConstants.CVANALYZER_FILE_UPLOAD_LOCATION);
		
		//parsing and storing in mongo
		analyzeAndStore(parsedResumes);
	}
	
	private void analyzeAndStore(List<String> parsedResumes){
			System.out.println("Inside analyze and store cv");
	}
	

}
