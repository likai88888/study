package com.rpc.server.netty.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rpc.server.netty.handler.ServerHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

@Component
public class ProtocolInitalizer extends ChannelInitializer<SocketChannel> {

	@Autowired
	private StringDecoder stringDecoder;

	@Autowired
	private StringEncoder stringEncoder;

	@Autowired
	private ServerHandler serverHandler;

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
//		pipeline.addLast("decoder", stringDecoder);
		pipeline.addLast("handler", serverHandler);
//		pipeline.addLast("encoder", stringEncoder);
	}
}
