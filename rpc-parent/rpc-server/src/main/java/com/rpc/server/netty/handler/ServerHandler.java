package com.rpc.server.netty.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@Component("serverHandler")
@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter{
	
	@Autowired
	private RequestHandler handler;
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("激活");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.info("服务端收到消息：" + msg);
		ByteBuf msgBuf = (ByteBuf) msg;
		byte[] req = new byte[msgBuf.readableBytes()];
		msgBuf.readBytes(req);
		byte[] res = handler.handleRequest(req);
		ByteBuf respBuf = Unpooled.buffer(res.length);
		respBuf.writeBytes(res);
		log.info("发送响应：" + respBuf);
		ctx.write(respBuf);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		log.info("ctx.flush");
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// Close the connection when an exception is raised.
		cause.printStackTrace();
		log.error("发生异常：" + cause.getMessage());
		ctx.close();
	}
	
}
