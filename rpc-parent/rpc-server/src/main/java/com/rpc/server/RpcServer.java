package com.rpc.server;

import com.rpc.server.netty.handler.ServerHandler;

public abstract class RpcServer {
	
	protected int port;

	protected String protocol;

	protected ServerHandler handler;
	/**
	 * 开启服务
	 * @throws Exception 
	 */
	public abstract void start() throws Exception;

	/**
	 * 停止服务
	 * @throws Exception 
	 */
	public abstract void stop() throws Exception;
}
