package com.rpc.server.register;

import lombok.Data;

@Data
public class ServiceObject {
	
	private String name;

	private Class<?> interf;

	private Object obj;

	public ServiceObject(String name, Class<?> interf, Object obj) {
		super();
		this.name = name;
		this.interf = interf;
		this.obj = obj;
	}
}
