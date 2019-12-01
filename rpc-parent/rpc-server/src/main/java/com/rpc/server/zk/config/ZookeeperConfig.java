package com.rpc.server.zk.config;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rpc.server.zk.serializer.MyZkSerializer;

@Configuration
public class ZookeeperConfig {

	@Value("${zk.address}")
	private String zkAddress;
	
	@Autowired
	private MyZkSerializer myZkSerializer;
	
	@Bean
	public ZkClient getZkClient() {
		ZkClient client = new ZkClient(zkAddress);
		client.setZkSerializer(myZkSerializer);
		return client;
		
	}
	
}
