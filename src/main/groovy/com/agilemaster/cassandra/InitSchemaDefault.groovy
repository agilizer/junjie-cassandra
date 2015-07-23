package com.agilemaster.cassandra;

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.datastax.driver.core.Session

public class InitSchemaDefault implements InitSchema{
	private static final Logger log = LoggerFactory
	.getLogger(InitSchemaDefault.class);
	private boolean createDrop =  false;
	@Override
	public void init(Session session){
		if(createDrop){
			log.warn("<-------------config InitSchema warn,there is no InitSchema impl---------->")
		}
	}
	@Override
	public void setCreateDrop(boolean createDrop) {
		this.createDrop  = createDrop;
	}
}
