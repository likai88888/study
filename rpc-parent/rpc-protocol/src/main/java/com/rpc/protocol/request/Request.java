package com.rpc.protocol.request;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class Request implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	private String serviceName;

	private String method;

	private Map<String, String> headers = new HashMap<String, String>();

	private Class<?>[] prameterTypes;

	private Object[] parameters;

}
