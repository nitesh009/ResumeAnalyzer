package com.tom.engine;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.entity.mime.HttpMultipart;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tom.exception.ResumeAnalyzerServiceException;
import com.tom.service.FileUploadService;
import com.tom.service.UIMAService;

@EnableAutoConfiguration
@RestController
public class FileUplodaEngine {
	
	@Autowired
	private FileUploadService fileuploadservice;
	
	@Autowired
	private UIMAService service;

	//method to upload single file
	@RequestMapping(value="/uploadSingleFile",method=RequestMethod.POST)
	public void uploadFile(@RequestParam("file") MultipartFile file) throws ResumeAnalyzerServiceException{

		System.out.println("===========> Nitesh single file upload request came");
		MultipartFile[] files=new MultipartFile[1];
		files[0]=file;
		fileuploadservice.saveMultibleFiles(files);
		
	}

	//method to upload multiple files
	//method to upload single file
	@RequestMapping(value="/uploadMultipleFiles",method=RequestMethod.POST)
	public String uploadMultipleFiles(@RequestParam("uploadedFile") MultipartFile[] files,MultipartHttpServletRequest request) throws ResumeAnalyzerServiceException, AnalysisEngineProcessException, InvalidXMLException, ResourceInitializationException, IOException{
		
		System.out.println("Nitesh multiple file upload request came: "+files.length);
		fileuploadservice.saveMultibleFiles(files);
		
		//start processing
		service.saveParsedResumeJson("");
		
		return "File Uploaded Successfully";
				
	}

}
