package com.tom.service;

import org.springframework.web.multipart.MultipartFile;

import com.tom.exception.ResumeAnalyzerServiceException;

public interface FileUploadService {
	
	//upload multiple files
	//todo: make kafka and que implementations
	public void saveMultibleFiles(MultipartFile[] fileList) throws ResumeAnalyzerServiceException;

}
