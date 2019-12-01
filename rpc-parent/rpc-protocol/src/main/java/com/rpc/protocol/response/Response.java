package com.rpc.protocol.response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.rpc.protocol.status.Status;

import lombok.Data;


@Data
public class Response implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Status status;
	
	private Map<String,String> headers = new HashMap<String,String>();
	
	private Object returnValue;
	
	private Exception exception;
	
	public Response() {
	};

	public Response(Status status) {
		this.status = status;
	}

	public String getHeader(String name) {
		return this.headers == null ? null : this.headers.get(name);
	}

	public void setHaader(String name, String value) {
		this.headers.put(name, value);

	}

}
