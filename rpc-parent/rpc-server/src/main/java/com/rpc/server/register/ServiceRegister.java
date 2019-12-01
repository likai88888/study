package com.rpc.server.register;

public interface ServiceRegister {
	
	public void register(ServiceObject so, String protocol, int port) throws Exception;

	public ServiceObject getServiceObject(String name) throws Exception;
}
