package com.agilemaster.form.test;

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.agilemaster.cassandra.InitSchema
import com.datastax.driver.core.Session

public class InitSchemaTest implements InitSchema{
	private static final Logger log = LoggerFactory
	.getLogger(InitSchemaTest.class);
	private boolean createDrop =  false;
	@Override
	public void init(Session session){
		if(createDrop){
			
		}
	}
	@Override
	public void setCreateDrop(boolean createDrop) {
		this.createDrop  = createDrop;
	}
}
