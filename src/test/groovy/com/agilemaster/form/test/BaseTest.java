package com.agilemaster.form.test;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agilemaster.cassandra.CassandraJunjieConfig;
import com.agilemaster.cassandra.InitSchema;
import com.agilemaster.cassandra.option.CassandraTemplate;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;

public class BaseTest {
	private  final Logger log = LoggerFactory
			.getLogger(this.getClass());
	CassandraTemplate cassandraTemplate ;
	@Before
	public void before(){
		Builder builder = Cluster.builder().addContactPoint("127.0.0.1");
		/**
		 * 设置为删除创建
		 */
		InitSchema initSchema = new InitSchemaTest();
		initSchema.setCreateDrop(true);
		CassandraJunjieConfig.setInitSchema(initSchema);
		CassandraJunjieConfig.init(builder);
		CassandraJunjieConfig.setKeySpace("testKeySpace");
		cassandraTemplate = CassandraJunjieConfig.getInstance();
	}
	
	@After
	public void after(){
		CassandraJunjieConfig.close();
	}
	
}
