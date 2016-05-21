package com.tom.service;

import java.io.File;

public class TikaServiceImpl implements TikaService {

	@Override
	public void parseRawFiles(String sourceLocation, String destinationLocation) {
		
		System.out.println("Starting Tika Parsing");
		
		//get the file list
		File file=new File(sourceLocation);
		
		File[] fileList=file.listFiles();
		
//		for(File file:fileList){
//				
//		}
		
	}

}
