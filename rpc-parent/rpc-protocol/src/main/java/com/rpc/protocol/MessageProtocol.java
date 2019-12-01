package com.rpc.protocol;

import com.rpc.protocol.request.Request;
import com.rpc.protocol.response.Response;

public interface MessageProtocol {
	
	public byte[] marshallingRequest(Request req) throws Exception;

	public Request unmarshallingRequest(byte[] data) throws Exception;

	public byte[] marshallingResponse(Response rsp) throws Exception;

	public Response unmarshallingResponse(byte[] data) throws Exception;

}
