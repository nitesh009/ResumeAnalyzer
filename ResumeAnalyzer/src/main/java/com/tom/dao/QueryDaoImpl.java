package com.tom.dao;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import com.tom.dto.GeneralQueryResponseDTO;
import com.tom.dto.QueryEngineReqDTO;

@Component
public class QueryDaoImpl implements QueryDao {

	@Override
	public JSONObject getGeneralQuery(QueryEngineReqDTO queryDTO) {
		GeneralQueryResponseDTO resp=new GeneralQueryResponseDTO();
		
		//todo:access db and find out data
		resp.setSkill(50);
		resp.setTotalNummber(300);
		
		JSONObject respJson = new JSONObject();
		
		respJson.put("java", 50);
		respJson.put("total", 300);
		
		
		return respJson;
	}

}
