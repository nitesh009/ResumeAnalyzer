package com.tom.dao;

import org.json.simple.JSONObject;

import com.tom.dto.GeneralQueryResponseDTO;
import com.tom.dto.QueryEngineReqDTO;

public interface QueryDao {
	
	//get general skills query
	public JSONObject getGeneralQuery(QueryEngineReqDTO queryDTO);

}
