package com.rpc.client.net;

import com.rpc.discovery.ServiceInfo;

public interface NetClient {
	
	public byte[] sendRequest(byte[] data, ServiceInfo sinfo) throws Throwable;
}
