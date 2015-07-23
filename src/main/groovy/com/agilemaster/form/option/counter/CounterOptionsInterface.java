package com.agilemaster.form.option.counter;

public interface CounterOptionsInterface {
	void inc(String id);
	void dec(String id);
	void inc(String id,long count);
	void dec(String id,long count);
	long getCount(String id);
}
