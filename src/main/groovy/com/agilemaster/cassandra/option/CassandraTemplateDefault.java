package com.agilemaster.cassandra.option;

import static com.datastax.driver.core.querybuilder.QueryBuilder.set;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agilemaster.cassandra.CassandraJunjieConfig;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Clause;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Update;
import com.datastax.driver.core.querybuilder.Update.Assignments;
import com.datastax.driver.core.querybuilder.Update.Where;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class CassandraTemplateDefault implements CassandraTemplate {
	private static final Logger log = LoggerFactory
			.getLogger(CassandraTemplateDefault.class);
	private Session session;
	private MappingManager mappingManager;

	public CassandraTemplateDefault() {
		
	}

	public CassandraTemplateDefault(Session session,
			MappingManager mappingManager) {
		this.session = session;
		this.mappingManager = mappingManager;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public void setMappingManager(MappingManager mappingManager) {
		this.mappingManager = mappingManager;
	}

	@Override
	public Session getSession() {
		return this.session;
	}

	@Override
	public MappingManager getMappingManager() {
		return this.mappingManager;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T save(T object) {
		mappingManager.mapper((Class<T>) object.getClass()).save(object);
		return object;
	}

	@Override
	public <T> T getEntity(Class<T> t, Object id) {
		return mappingManager.mapper(t).get(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void delete(T object) {
		mappingManager.mapper((Class<T>) object.getClass()).delete(object);
	}

	@Override
	public <T> void deleteById(Class<T> t, Object id) {
		mappingManager.mapper(t).delete(id);
	}

	@Override
	public <T> Mapper<T> getMapper(Class<T> t) {
		return mappingManager.mapper(t);
	}

	@Override
	public boolean update(String tableName, Map<String, Object> updateFields,
			List<Clause> whereList) {
		if(null==updateFields||updateFields.size()==0){
			log.warn("update {} warn,updateFields is null or size is 0",tableName);
			return false;
		}
		Update update = QueryBuilder.update(CassandraJunjieConfig.getKeySpace(), tableName);
		Assignments temp = null ;
		for (Entry<String, Object> entry : updateFields.entrySet()) {
			temp = update.with(set(entry.getKey(), entry.getValue()));
		}
		Where where = null;
		if(null!=whereList&&whereList.size()>0){
			for(Clause clause:whereList){
				if(null!=temp){
					where =temp.where(clause);
				}else{
					where =update.where(clause);
				}
			}
		}
		if(where==null){
			 session.execute(temp);
		}else{
			session.execute(where);
		}
		return true;
	}

	@Override
	public ResultSet execute(String cql, Object... args) {
		ResultSet result = null;
		if(null==args){
			result = session.execute(cql);
		}else{
			result = session.execute(cql, args);
		}
		return result;
	}

	@Override
	public <T> T queryForObject(Class<T> t, String cql, Object... args) {
		ResultSet resultTempSet = execute(cql,args);
		com.datastax.driver.mapping.Result<T> resultTemp = mappingManager.mapper(t).map(resultTempSet);
		T result = resultTemp.one();
		return result;
	}
}
