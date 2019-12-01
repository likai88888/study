package com.rpc.server.register;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.rpc.discovery.ServiceInfo;

@Component("zookeeperServiceRegister")
public class ZookeeperExportServiceRegister extends DefaultServiceRegister implements ServiceRegister{

	@Autowired
	private ZkClient client;
	
	@Value("${rpc.path}")
	private String centerRootPath ;
	
	@Override
	public void register(ServiceObject so, String protocolName, int port) throws Exception {
		super.register(so, protocolName, port);
		String host = InetAddress.getLocalHost().getHostAddress();
		
		String address = host + ":" + port;
		ServiceInfo soInf = new ServiceInfo();
		soInf.setAddress(address);
		soInf.setName(so.getInterf().getName());
		soInf.setProtocol(protocolName);
		this.exportService(soInf);

	}

	private void exportService(ServiceInfo serviceResource) {
		String serviceName = serviceResource.getName();
		String uri = JSON.toJSONString(serviceResource);
		try {
			uri = URLEncoder.encode(uri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String servicePath = centerRootPath + "/" + serviceName + "/service";
		if (!client.exists(servicePath)) {
			client.createPersistent(servicePath, true);
		}
		String uriPath = servicePath + "/" + uri;
		if (client.exists(uriPath)) {
			client.delete(uriPath);
		}
		client.createEphemeral(uriPath);
	}

}
