package com.tom.dao;

import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.tom.dto.GeneralQueryResponseDTO;
import com.tom.dto.QueryEngineReqDTO;

@Component
public class QueryDaoImpl implements QueryDao {

	@Autowired
	private MongoClient mc;


	public JSONObject getGeneralQueryxxx(QueryEngineReqDTO queryDTO) {
		GeneralQueryResponseDTO resp=new GeneralQueryResponseDTO();

		//todo:access db and find out data
		resp.setSkill(50);
		resp.setTotalNummber(300);

		JSONObject respJson = new JSONObject();

		respJson.put("java", 50);
		respJson.put("total", 300);


		return respJson;
	}

	@Override
	public JSONObject getGeneralQuery(QueryEngineReqDTO queryDTO) {

		

		int totalEmployee=0;
		int selectedEmployee=0;

		DB db = mc.getDB("greythr_database");
		DBCollection coll = db.getCollection("parsed_resume_db");

		totalEmployee=(int) coll.getCount();

		BasicDBObject query = new BasicDBObject();

		//lower bound of experience
		if(null!=queryDTO.getYearsOfExpLowerBound()&&(null!=queryDTO.getYearsOfExpUpperBound())){
			System.out.println("Inside getYearsOfExpLowerBound");
			query.put("resumeData.WorkExperience", new BasicDBObject("$gte", Integer.parseInt(queryDTO.getYearsOfExpLowerBound())).append("$lte", Integer.parseInt(queryDTO.getYearsOfExpUpperBound())));
		}

		//skills
		
//		if(null!=queryDTO.getSkills()){
//			String[] skills = queryDTO.getSkills().split(",");
//			
//			
//			
//			for(String s:skills){
//				
//				query.put("resumeData.OtherNamedTags", java.util.regex.Pattern.compile(skills[0],Pattern.CASE_INSENSITIVE));
//				
//				query.put("resumeData.OtherNamedTags", java.util.regex.Pattern.compile(skills[1],Pattern.CASE_INSENSITIVE));
//			}
//			
//		}
		
		System.out.println("Final Query: "+query);
		
		DBCursor cursor = coll.find(query);

		//loop to set the response
		try {
			while(cursor.hasNext()) {
				selectedEmployee++;
				cursor.next();
			}
		} finally {
			cursor.close();
		}

		JSONObject respJson = new JSONObject();

		//total count
		respJson.put("total", totalEmployee);	
		//skills
		respJson.put("selectedEmployee", selectedEmployee);

		return respJson;
	}





}
