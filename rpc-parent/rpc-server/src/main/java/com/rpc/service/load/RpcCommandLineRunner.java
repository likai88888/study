package com.rpc.service.load;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.rpc.server.register.RpcRegister;

@Order(2)
@Component
public class RpcCommandLineRunner implements CommandLineRunner{
	
	@Autowired
	private RpcRegister rpcRegister;
		
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		rpcRegister.register();
		
	}
	
}
