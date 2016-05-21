package com.tom.engine;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tom.dto.QueryEngineReqDTO;
import com.tom.service.QueryService;

@EnableAutoConfiguration
@RestController
public class QueryEngine {
	
	@Autowired
	private QueryService queryService;
	
	@RequestMapping(value="/queryGeneralData",method=RequestMethod.POST,consumes="application/json")
	public JSONObject getTest(@RequestBody(required=true) QueryEngineReqDTO requestDto ) {
		
		return queryService.getGeneralQuery(requestDto);
		
	}


}
