package com.tom.service;

import java.util.List;

public interface TikaService {
	
	//get the server location and parse the files and storeinto destination locations
	public List<String> parseRawFiles(String sourceLocation);

}
