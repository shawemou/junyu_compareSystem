package com.junyu.server;

import java.util.concurrent.Callable;

import com.junyu.common.returnBean.CompareReturnBean;

public class WSCompareServerThread extends WSCompareBaseServer implements Callable<CompareReturnBean> {
	
	private String strPhoto1;
	private String strPhoto2;
	
	public WSCompareServerThread(String strPhoto1, String strPhoto2){
		this.strPhoto1 = strPhoto1;
		this.strPhoto2 = strPhoto2;
	}
	
	public CompareReturnBean call() throws Exception {
		return WSCompareBaseServer.localCompare(strPhoto1, strPhoto2);
	}
}
