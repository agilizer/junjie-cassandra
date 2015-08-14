package com.agilemaster.cassandra.option;

import java.util.List;
import java.util.Map;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Clause;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.UDTMapper;

public interface CassandraTemplate {
	public Session getSession();
	MappingManager getMappingManager();

	<T> Mapper<T> getMapper(Class<T> t);

	<T> UDTMapper<T> getUDTMapper(Class<T> t);
	
	<T> T getAccessorMapper(Class<T> t);
	
	<T> T save(T object);

	<T> T getEntity(Class<T> t, Object id);
	
	<T> void delete(T object);

	<T> void deleteById(Class<T> t, Object id);
	
	boolean update(String tableName, Map<String, Object> updateFields,
			List<Clause> whereList);
	ResultSet execute(String cql,Object... args);
	
	<T> T queryForObject(Class<T> t,String cql,Object... args);
	
	
	
}
