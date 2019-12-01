package com.rpc.client.zk.serializer;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.springframework.stereotype.Component;

@Component
public class MyZkSerializer implements ZkSerializer{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String charset = "UTF-8";

	public Object deserialize(byte[] bytes) throws ZkMarshallingError {
		try {
			return new String(bytes, charset);
		} catch (UnsupportedEncodingException e) {
			throw new ZkMarshallingError(e);
		}
	}

	public byte[] serialize(Object obj) throws ZkMarshallingError {
		try {
			return String.valueOf(obj).getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new ZkMarshallingError(e);
		}
	}
}
