package com.rpc.protocol;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.springframework.stereotype.Component;

import com.rpc.protocol.request.Request;
import com.rpc.protocol.response.Response;

@Component("javaSerializeMessageProtocol")
public class JavaSerializeMessageProtocol implements MessageProtocol {
	
	private  byte[] serialize(Object obj) throws Exception {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(bout);
		out.writeObject(obj);
		return bout.toByteArray();
	}
	
	private Object unserialize(byte[] data) throws Exception {
		ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));
		return  in.readObject();
	}
	@Override
	public byte[] marshallingRequest(Request req) throws Exception {
		// TODO Auto-generated method stub
		return this.serialize(req);
	}

	@Override
	public Request unmarshallingRequest(byte[] data) throws Exception {
		// TODO Auto-generated method stub
		return (Request) this.unserialize(data);
	}

	@Override
	public byte[] marshallingResponse(Response rsp) throws Exception {
		// TODO Auto-generated method stub
		return this.serialize(rsp);
	}

	@Override
	public Response unmarshallingResponse(byte[] data) throws Exception {
		// TODO Auto-generated method stub
		return (Response) this.unserialize(data);
	}

}
