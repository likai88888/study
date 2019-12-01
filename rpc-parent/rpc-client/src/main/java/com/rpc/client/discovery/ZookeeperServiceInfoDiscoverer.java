package com.rpc.client.discovery;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.rpc.discovery.ServiceInfo;

@Component("zkServiceInfoDiscoverer")
public class ZookeeperServiceInfoDiscoverer implements ServiceInfoDiscoverer {

	@Autowired
	private ZkClient client;

	@Value("${rpc.path}")
	private String centerRootPath = "/Rpc-framework";

	
	@Override
	public List<ServiceInfo> getServiceInfo(String name) {
		String servicePath = centerRootPath + "/" + name + "/service";
		List<String> children = client.getChildren(servicePath);
		List<ServiceInfo> resources = new ArrayList<ServiceInfo>();
		for (String ch : children) {
			try {
				String deCh = URLDecoder.decode(ch, "UTF-8");
				ServiceInfo r = JSON.parseObject(deCh, ServiceInfo.class);
				resources.add(r);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return resources;
	}

}
