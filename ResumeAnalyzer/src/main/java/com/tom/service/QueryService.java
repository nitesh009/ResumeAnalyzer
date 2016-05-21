package com.tom.service;

import org.json.simple.JSONObject;

import com.tom.dto.QueryEngineReqDTO;

public interface QueryService {
	//get general skills query
		public JSONObject getGeneralQuery(QueryEngineReqDTO queryDTO);

}
