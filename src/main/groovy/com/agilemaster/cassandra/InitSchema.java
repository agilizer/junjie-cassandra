package com.agilemaster.cassandra;

import com.datastax.driver.core.Session;


public interface InitSchema {
	void init(Session session);
	void setCreateDrop(boolean createDrop);
}
