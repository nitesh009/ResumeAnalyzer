package com.tom.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

@Component
public class UIMADaoImpl implements UIMADao {

	@Autowired
	private MongoClient mc;

	@Override
	public boolean saveParsedJsons(List<JSONObject> jsonList) {
		saveDocumentList(jsonList);
		return true;
	}

	//get domunet objects
	private void saveDocumentList(List<JSONObject> jsonList){
		//get the db
		//to do: clean the code
		MongoDatabase db = mc.getDatabase("greythr_database");

		for(JSONObject json:jsonList){
			System.out.println(json.toJSONString());
			db.getCollection("parsed_resume_db").insertOne(new Document("resumeData",json.toJSONString()));
		}
	}



}
