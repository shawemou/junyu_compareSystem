package com.junyu.server.Client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "JYWebserviceSoap", targetNamespace = "http://tempuri.org/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface JYWebserviceSoap {

	@WebMethod(operationName = "ToIngestInvoke", action = "http://tempuri.org/ToIngestInvoke")
	@WebResult(name = "ToIngestInvokeResponse", targetNamespace = "http://tempuri.org/")
	public ToIngestInvokeResponse toIngestInvoke(
			@WebParam(name = "ToIngestInvokeHead", targetNamespace = "http://tempuri.org/") ToIngestInvokeHead ToIngestInvokeHead,
			@WebParam(name = "ToIngestInvokeData", targetNamespace = "http://tempuri.org/") ToIngestInvokeData ToIngestInvokeData);

}
