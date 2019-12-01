package com.rpc.client.discovery;

import java.util.List;

import com.rpc.discovery.ServiceInfo;

public interface ServiceInfoDiscoverer {
	
	public List<ServiceInfo> getServiceInfo(String name);

}
