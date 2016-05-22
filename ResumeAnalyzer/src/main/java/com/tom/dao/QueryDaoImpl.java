package com.tom.dao;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
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
		int unspecifiedCount=0;

		DB db = mc.getDB("greythr_database");
		DBCollection coll = db.getCollection("parsed_resume_db");

		totalEmployee=(int) coll.getCount();

		BasicDBObject query = new BasicDBObject();

		//experience
		//todo:dirty logic need to change
		if(null==(queryDTO.getYearsOfExpLowerBound())||"".equals(queryDTO.getYearsOfExpLowerBound())){
			unspecifiedCount=getFresherCount();
			queryDTO.setYearsOfExpLowerBound("0");
		}

		if(null==queryDTO.getYearsOfExpUpperBound()||"".equals(queryDTO.getYearsOfExpUpperBound())){
			queryDTO.setYearsOfExpUpperBound("99");
		}
		
		System.out.println("Inside getYearsOfExpLowerBound");
		query.put("resumeData.WorkExperience", new BasicDBObject("$gte", Integer.parseInt(queryDTO.getYearsOfExpLowerBound())).append("$lte", Integer.parseInt(queryDTO.getYearsOfExpUpperBound())));


		//skills dirty code need to cleanup

		if(null!=queryDTO.getSkills()&&!queryDTO.getSkills().equals("")){
			String[] skills = queryDTO.getSkills().split(",");
			List<String> list = new ArrayList<String>();

			for(String s:skills){
				list.add(s);
			}

			query.put("resumeData.OtherNamedTags", 
			        new BasicDBObject("$regex",list.get(0) )
			        .append("$options", "i"));

		}


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
		respJson.put("Total", totalEmployee);	
		//skills
		respJson.put("SelectedEmployee", selectedEmployee);
		
		//respJson.put("UnspeciedExperience", unspecifiedCount);

		return respJson;
	}

//get freshers or unspecified
	private int getFresherCount(){
		
		int fresherCount=0;
		
		DB db = mc.getDB("greythr_database");
		DBCollection coll = db.getCollection("parsed_resume_db");
		DBObject query = new BasicDBObject("resumeData.WorkExperience", new BasicDBObject("$exists", false));
		DBCursor result = coll.find(query);
		
		return result.count();
 }


}
