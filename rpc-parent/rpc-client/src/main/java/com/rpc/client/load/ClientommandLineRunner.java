package com.rpc.client.load;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.rpc.client.factory.ClientStubProxyFactory;
import com.rpc.protocol.JavaSerializeMessageProtocol;

@Order(2)
@Component
public class ClientommandLineRunner implements CommandLineRunner{
	
	@Autowired
	private JavaSerializeMessageProtocol javaSerializeMessageProtocol;
	
	@Autowired
	private ClientStubProxyFactory clientStubProxyFactory;
		
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
//		rpcRegister.register();
		clientStubProxyFactory.getSupportMessageProtocols().put("javas", javaSerializeMessageProtocol);
	}
	
}
