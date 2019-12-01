package com.rpc.client.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.rpc.client.discovery.ServiceInfoDiscoverer;
import com.rpc.client.net.NetClient;
import com.rpc.discovery.ServiceInfo;
import com.rpc.protocol.MessageProtocol;
import com.rpc.protocol.request.Request;
import com.rpc.protocol.response.Response;

@Component("invocationHandler")
public class ClientStubInvocationHandler implements InvocationHandler {
	
	@Qualifier("zkServiceInfoDiscoverer")
	@Autowired
	private ServiceInfoDiscoverer sid;
	
	@Autowired
	private ClientStubProxyFactory clientStubProxyFactory;
	
	@Qualifier("nettyNetClient")
	@Autowired
	private NetClient netClient;

	private Random random = new Random();

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		if (method.getName().equals("toString")) {
			return proxy.getClass().toString();
		}

		if (method.getName().equals("hashCode")) {
			return 0;
		}

		// 1、获得服务信息
		String serviceName = clientStubProxyFactory.getInterf().getName();
		List<ServiceInfo> sinfos = sid.getServiceInfo(serviceName);

		if (sinfos == null || sinfos.size() == 0) {
			throw new Exception("远程服务不存在！");
		}

		// 随机选择一个服务提供者（软负载均衡）
		ServiceInfo sinfo = sinfos.get(random.nextInt(sinfos.size()));

		// 2、构造request对象
		Request req = new Request();
		req.setServiceName(sinfo.getName());
		req.setMethod(method.getName());
		req.setPrameterTypes(method.getParameterTypes());
		req.setParameters(args);

		// 3、协议层编组
		// 获得该方法对应的协议
		MessageProtocol protocol = clientStubProxyFactory.getSupportMessageProtocols().get(sinfo.getProtocol());
		// 编组请求
		byte[] data = protocol.marshallingRequest(req);
		// 4、调用网络层发送请求
		byte[] repData = netClient.sendRequest(data, sinfo);

		// 5解组响应消息
		Response rsp = protocol.unmarshallingResponse(repData);

		// 6、结果处理
		if (rsp.getException() != null) {
			throw rsp.getException();
		}

		return rsp.getReturnValue();
	}
}
