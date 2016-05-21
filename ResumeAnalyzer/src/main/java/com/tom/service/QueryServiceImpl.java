package com.tom.service;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tom.dao.QueryDao;
import com.tom.dto.QueryEngineReqDTO;

@Service
public class QueryServiceImpl implements QueryService {
	
	@Autowired
	private QueryDao queryDao;

	@Override
	public JSONObject getGeneralQuery(QueryEngineReqDTO queryDTO) {
		// TODO Auto-generated method stub
		return queryDao.getGeneralQuery(queryDTO);
	}

}
