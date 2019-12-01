package com.rpc.service.impl;

import org.springframework.stereotype.Service;

import com.rpc.service.RpcInfoService;

@Service("rpcInfoService")
public class RpcInfoServiceImpl implements RpcInfoService {

	@Override
	public String get(String name) {
		// TODO Auto-generated method stub
		return "我来找你，"+name;
	}

}
