package com.agilemaster.cassandra.option;

import java.util.Map;

public interface BaseOptions<T> {

	T save(T domain);

	T delete(T domain);

	void delete(String id);

	boolean update(String id, Map<String, Object> params);

	T findOne(Object id);
	
	long count();
	
	

}
