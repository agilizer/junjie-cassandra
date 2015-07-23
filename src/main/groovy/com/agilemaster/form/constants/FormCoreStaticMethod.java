package com.agilemaster.form.constants;

import java.util.UUID;

public class FormCoreStaticMethod {
	public static String genUUID(){
		return  UUID.randomUUID().toString().replaceAll("-", "");
	}

}
