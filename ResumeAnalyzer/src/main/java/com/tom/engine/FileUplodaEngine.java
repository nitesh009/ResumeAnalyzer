package com.tom.engine;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.entity.mime.HttpMultipart;
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

@EnableAutoConfiguration
@RestController
public class FileUplodaEngine {
	
	@Autowired
	private FileUploadService fileuploadservice;

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
	public String uploadMultipleFiles(@RequestParam("uploadedFile") MultipartFile[] files,MultipartHttpServletRequest request) throws ResumeAnalyzerServiceException{
		
		System.out.println("Nitesh multiple file upload request came: "+files.length);
		fileuploadservice.saveMultibleFiles(files);
		return "File Uploaded Successfully";
				
	}

}
