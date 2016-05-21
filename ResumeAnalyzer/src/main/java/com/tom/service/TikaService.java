package com.tom.service;

public interface TikaService {
	
	//get the server location and parse the files and storeinto destination locations
	public void parseRawFiles(String sourceLocation,String destinationLocation);

}
