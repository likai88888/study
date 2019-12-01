package com.rpc.server.netty;

import java.net.InetSocketAddress;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rpc.server.RpcServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class NettyRpcServer extends RpcServer {

	@Autowired
	@Qualifier("serverBootstrap")
	private ServerBootstrap b;

	@Autowired
	@Qualifier("tcpSocketAddress")
	private InetSocketAddress tcpPort;

	private ChannelFuture serverChannelFuture;
	
	@PostConstruct
	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub
		log.info("Starting server at " + tcpPort);
	    serverChannelFuture = b.bind(tcpPort).sync();
	}
	
	@PreDestroy
	@Override
	public void stop() throws Exception{
		// TODO Auto-generated method stub
		serverChannelFuture.channel().closeFuture().sync();
		log.info("stoping server at " + tcpPort);
	}

}
