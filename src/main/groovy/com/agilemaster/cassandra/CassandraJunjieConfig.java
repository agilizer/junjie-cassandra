package com.agilemaster.cassandra;

import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agilemaster.cassandra.option.CassandraTemplate;
import com.agilemaster.cassandra.option.CassandraTemplateDefault;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.annotations.Table;
import com.datastax.driver.mapping.annotations.UDT;

public class CassandraJunjieConfig {
	private static Cluster cluster;
	private static Session session;
	private static InitSchema initSchema;
	private static MappingManager mappingManager;
	private static CassandraTemplate cassandraTemplate ;
	private static String mappingPackage="";
	private static String keySpace="junjie_form";
	private static final Logger log = LoggerFactory
			.getLogger(CassandraJunjieConfig.class);
	public static CassandraTemplate getInstance(){
		if(cassandraTemplate==null){
			cassandraTemplate = new CassandraTemplateDefault(session,mappingManager);
		}
		return cassandraTemplate;
	}
	
	private static  void init() {
		if(null==cluster){
			log.warn("init warn!!!!!! cluster is null load 127.0.0.1 default!");
			cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		}
		Metadata metadata = cluster.getMetadata();
		log.info("Connected to cluster: {}\n",
				metadata.getClusterName());
		for (Host host : metadata.getAllHosts()) {
			log.info("Datatacenter: {}; Host: {}; Rack: {}\n",
					host.getDatacenter(), host.getAddress(), host.getRack());
		}
		session = cluster.connect();
		if(null==initSchema){
			initSchema = new InitSchemaDefault();
		}
		initSchema.init(session);
		initMapper();
	}
	public static void init(String node) {
		Builder builder = Cluster.builder().addContactPoint(node);
		init(builder);
	}
	public static void init(Builder builder) {
		cluster = builder.build();
		init();
	}	
	
	public static Session getSession() {
		return session;
	}

	public static Cluster getCluster() {
		return cluster;
	}
	
	@SuppressWarnings("unchecked")
	private static void initMapper(){
		mappingManager = new MappingManager(session);
		Reflections reflections = new Reflections(mappingPackage);
		 Set<Class<?>> tableDomains = reflections.getTypesAnnotatedWith(Table.class);
		 for(Class t:tableDomains){
			 mappingManager.mapper(t);
			 log.info("-------->mapping Table class:{}",t.getName());
		 }
		 
		 Set<Class<?>> udtDomains = reflections.getTypesAnnotatedWith(UDT.class);
		 for(Class t:udtDomains){
			 mappingManager.udtMapper(t);
			 log.info("-------->mapping UDT class:{}",t.getName());
		 }
	}

	public static void close() {
		if(null!=session){
			session.close();
		}
		if(null!=cluster){
			cluster.close();
		}
	}

	public static Cluster setCluster(Cluster clusterInput) {
		return cluster=clusterInput;
	}

	public static void setInitSchema(InitSchema initSchemaInput) {
		initSchema = initSchemaInput;
	}

	public static MappingManager getMappingManager() {
		return mappingManager;
	}

	
	public static CassandraTemplate getCassandraTemplate() {
		return cassandraTemplate;
	}

	public static void setCassandraTemplate(CassandraTemplate cassandraTemplate) {
		CassandraJunjieConfig.cassandraTemplate = cassandraTemplate;
	}

	public static String getKeySpace() {
		return keySpace;
	}

	public static void setKeySpace(String keySpace) {
		CassandraJunjieConfig.keySpace = keySpace;
	}

	public static InitSchema getInitSchema() {
		return initSchema;
	}

	public static void setSession(Session session) {
		CassandraJunjieConfig.session = session;
	}

	public static void setMappingManager(MappingManager mappingManager) {
		CassandraJunjieConfig.mappingManager = mappingManager;
	}

	

	public static String getMappingPackage() {
		return mappingPackage;
	}

	public static void setMappingPackage(String mappingPackageInput) {
		mappingPackage = mappingPackageInput;
	}
	
	

}
