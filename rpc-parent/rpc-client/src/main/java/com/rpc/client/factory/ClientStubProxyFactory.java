package com.rpc.client.factory;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import com.rpc.protocol.MessageProtocol;
import lombok.Data;


@Data
@Component
@DependsOn("javaSerializeMessageProtocol")
public class ClientStubProxyFactory {

	
	private Class<?> interf;
	
	

	private Map<String, MessageProtocol> supportMessageProtocols = new HashMap<>(); ;
	
	

	private Map<Class<?>, Object> objectCache = new HashMap<>();
		
	
	@Qualifier("invocationHandler")
	@Autowired
	private InvocationHandler invocationHandler;
	

	@SuppressWarnings("unchecked")
	public <T> T getProxy(Class<T> interf) {
		T obj = (T) this.objectCache.get(interf);
		if (obj == null) {
			this.setInterf(interf);
			obj = (T) Proxy.newProxyInstance(interf.getClassLoader(), new Class<?>[] { interf },
					invocationHandler);
			this.objectCache.put(interf, obj);
		}
		return obj;
	}	
}
