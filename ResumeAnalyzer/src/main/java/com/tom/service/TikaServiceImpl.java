package com.tom.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

@Service
public class TikaServiceImpl implements TikaService {

	@Override
	public List<String> parseRawFiles(String sourceLocation) {

		System.out.println("Starting Tika Parsing");

		//get the file list
		File sourceLoc=new File(sourceLocation);

		File[] fileList=sourceLoc.listFiles();

		List <String> plaintextCVString = new ArrayList<String>(); 

		for(File file:fileList){
			try {
				plaintextCVString.add(parseResume(file));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TikaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return plaintextCVString;

	}

	private String parseResume(File file) throws IOException,TikaException, SAXException {

		//same shit, just get the file and parse
		BodyContentHandler handler = new BodyContentHandler();
		//not using metadata for now
		Metadata metadata = new Metadata();
		FileInputStream inputstream = new FileInputStream(new File(file.getAbsolutePath()));
		ParseContext pcontext = new ParseContext();

		Parser parser=new AutoDetectParser();
		parser.parse(inputstream, handler, metadata, pcontext);

		//now get all the contents into a single string
		StringBuffer sb=new StringBuffer();
		sb.append(handler.toString());

		return sb.toString();

	}

}
