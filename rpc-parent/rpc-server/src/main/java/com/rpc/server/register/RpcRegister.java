package com.rpc.server.register;



import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class RpcRegister {
	
	@Value("${rpc.protocol}")
	private String protocol ;
	
	@Value("${rpc.scanPackage}")
	private String scanPackage ;
	
	@Value("${netty.tcp.port}")
	private int port;
	
	
	@Autowired
	private ServiceRegister sr;
	
	@Autowired
    private ApplicationContext appContext;
	         
	public void register() throws Exception {
		String[] beans = appContext.getBeanDefinitionNames();
        Arrays.sort(beans);
        
        for (String bean : beans)
        {
        	Object beanclass = appContext.getBean(bean);
            if(beanclass.getClass().getName().indexOf(scanPackage)!=-1) {           	
            	Class<?>[] classArr=beanclass.getClass().getInterfaces();
            	if(classArr!=null&&classArr.length>0) {
            		Class<?> ifClass =classArr[0];
                	ServiceObject so = new ServiceObject(ifClass.getName(), ifClass, beanclass);
            		sr.register(so, protocol, port);
            	}
            	
            }
        }
	}
}
