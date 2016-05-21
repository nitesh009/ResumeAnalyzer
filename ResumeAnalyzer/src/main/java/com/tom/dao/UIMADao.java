package com.tom.dao;

import java.util.List;

import org.json.simple.JSONObject;

public interface UIMADao {
	//save paresed date from UIMA
	public boolean saveParsedJsons(List<JSONObject> jsonList);

}
