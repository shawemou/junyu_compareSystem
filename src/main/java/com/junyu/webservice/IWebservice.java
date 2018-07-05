package com.junyu.webservice;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface IWebservice {
	
	/**
	 * ²âÊÔµ÷ÓÃ
	 * @param message
	 * @return ×Ö·û´®
	 */
	@WebResult(name = "strResult")
	public String example(@WebParam(name = "message") String message);
	
	@WebResult(name = "strJsonResult")
	public String method(@WebParam(name = "strJson") String strJson);
}
