package com.rpc.client.net;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rpc.discovery.ServiceInfo;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("nettyNetClient")
public class NettyNetClient implements NetClient {
	
	@Autowired
	@Qualifier("bootstrap")
	private Bootstrap b;
	
    private ChannelFuture future;
    
	@Override
	public byte[] sendRequest(byte[] data, ServiceInfo sinfo) throws Throwable {
		// TODO Auto-generated method stub
		String[] addInfoArray = sinfo.getAddress().split(":");
		future = b.connect(addInfoArray[0], Integer.valueOf(addInfoArray[1])).sync();
		ByteBuf reqBuf = Unpooled.buffer(data.length);
		reqBuf.writeBytes(data);
		log.info("客户端发送消息：" + reqBuf);
		future.channel().writeAndFlush(reqBuf);
		future.channel().closeFuture().sync();
		AttributeKey<byte[]> key = AttributeKey.valueOf("serverData");
		byte[] respData = future.channel().attr(key).get();
		log.info("sendRequest get reply: " + respData);
		return respData;
	}

}
