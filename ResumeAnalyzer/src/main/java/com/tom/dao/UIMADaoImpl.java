package com.tom.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

public class UIMADaoImpl implements UIMADao {

	@Autowired
	private MongoClient mc;
	
	@Override
	public boolean saveParsedJsons(List<String> jsonList) {
		// TODO parse the list and store the data in mongo
		MongoDatabase db = mc.getDatabase("greyhr_database");
		DBCollection collection = (DBCollection) db.getCollection("parsed_resume_db");
		collection.insert(getDocumentList(jsonList));
		return true;
	}
	
	//get domunet objects
	private List<DBObject> getDocumentList(List<String> jsonList){
		
		List<DBObject> docList=new ArrayList<DBObject>();		
		for(String json:jsonList){
			DBObject dbObject = (DBObject) JSON.parse(json);
			docList.add(dbObject);
		}
		
		return docList;
	}
	
	

}
