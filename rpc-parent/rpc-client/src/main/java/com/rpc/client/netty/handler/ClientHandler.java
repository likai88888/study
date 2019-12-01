package com.rpc.client.netty.handler;


import org.springframework.stereotype.Component;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;


@ChannelHandler.Sharable
@Component("clientHandler")
@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter{
	

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("连接服务端成功：" + ctx);
	}


	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.info("client read msg: " + msg);
		ByteBuf msgBuf = (ByteBuf) msg;
		byte[] resp = new byte[msgBuf.readableBytes()];
		msgBuf.readBytes(resp);
		AttributeKey<byte[]> key = AttributeKey.valueOf("serverData");
        ctx.channel().attr(key).set(resp);
        Object result = ctx.channel().attr(key).get();
        log.info("客户端收到消息完成");
        ctx.channel().close();
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
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
