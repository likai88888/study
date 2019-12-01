package com;


import java.awt.Point;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.rpc.client.factory.ClientStubProxyFactory;
import com.rpc.service.DemoService;
import com.rpc.service.RpcInfoService;

@SpringBootApplication
public class ClientApp {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ApplicationContext appContext = SpringApplication.run(ClientApp.class, args);

		ClientStubProxyFactory cspf = (ClientStubProxyFactory) appContext.getBean("clientStubProxyFactory");
//		ApplicationContext appContext = 
//		 = new ClientStubProxyFactory();
		
//		ApplicationContextUtils.getBean(TestBean.class);
		DemoService demoService = cspf.getProxy(DemoService.class); // 获取远程服务代理
		String hello = demoService.sayHello("world"); // 执行远程方法
		System.out.println(hello); // 显示调用结果

		System.out.println(demoService.multiPoint(new Point(5, 10), 2));
		RpcInfoService infoService = cspf.getProxy(RpcInfoService.class);
		System.out.println(infoService.get("xiao san "));
	}

}
