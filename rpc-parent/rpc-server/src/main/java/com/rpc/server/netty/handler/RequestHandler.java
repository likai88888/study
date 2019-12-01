package com.rpc.server.netty.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.rpc.server.register.ServiceObject;
import com.rpc.server.register.ServiceRegister;

import lombok.extern.slf4j.Slf4j;

import com.rpc.protocol.MessageProtocol;
import com.rpc.protocol.request.Request;
import com.rpc.protocol.response.Response;
import com.rpc.protocol.status.Status;

@Slf4j
@Component
@Qualifier("requestHandler")
public class RequestHandler {
	
	@Autowired
	private MessageProtocol protocol;
	
	@Qualifier("zookeeperServiceRegister")
	@Autowired
	private ServiceRegister serviceRegister;

	public byte[] handleRequest(byte[] data) throws Exception {
		// 1、解组消息
		Request req = this.protocol.unmarshallingRequest(data);
		log.info(req.toString());

		// 2、查找服务对象
		ServiceObject so = this.serviceRegister.getServiceObject(req.getServiceName());
		Response rsp = null;

		if (so == null) {
			rsp = new Response(Status.NOT_FOUND);
		} else {
			// 3、反射调用对应的过程方法 
			try {
				log.info(so.toString());
				Method m = so.getInterf().getMethod(req.getMethod(), req.getPrameterTypes());
				Object returnValue = m.invoke(so.getObj(), req.getParameters());
				rsp = new Response(Status.SUCCESS);
				rsp.setReturnValue(returnValue);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				rsp = new Response(Status.ERROR);
				rsp.setException(e);
			}
		}

		// 4、编组响应消息
		log.info(rsp.toString());
		return this.protocol.marshallingResponse(rsp);
	}
}
