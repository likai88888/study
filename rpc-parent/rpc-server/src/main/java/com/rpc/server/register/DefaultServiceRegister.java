package com.rpc.server.register;

import java.util.HashMap;
import java.util.Map;

public class DefaultServiceRegister implements ServiceRegister {
	
	private Map<String, ServiceObject> serviceMap = new HashMap<>();
	
	@Override
	public void register(ServiceObject so, String protocol, int port) throws Exception {
		// TODO Auto-generated method stub
		if (so == null) {
			throw new IllegalArgumentException("参数不能为空");
		}

		this.serviceMap.put(so.getName(), so);
	}

	@Override
	public ServiceObject getServiceObject(String name) throws Exception {
		// TODO Auto-generated method stub
		return this.serviceMap.get(name);
	}

}
