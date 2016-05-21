package com.tom.dao;

import java.util.List;

public interface UIMADao {
	//save paresed date from UIMA
	public boolean saveParsedJsons(List<String> jsonList);

}
