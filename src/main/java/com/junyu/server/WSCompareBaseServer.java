package com.junyu.server;

import java.io.IOException;
import java.util.Properties;

import com.custle.sdk.Log4jUtil;
import com.junyu.common.returnBean.CompareReturnBean;
import com.junyu.server.Client.JYWebserviceClient;
import com.junyu.utils.GUID;

public class WSCompareBaseServer {

	private static int TOTAL_COUNT = 2;
	
	/**
	 * 本地比对接口服务
	 * @param viBean
	 * @param bean
	 */
	public static CompareReturnBean localCompare(String strPhoto1, String strPhoto2){
		final Properties props = new Properties();
		// 使用类加载器读取配置文件
		ClassLoader loader = CompareReturnBean.class.getClassLoader();
		try {
			props.load(loader.getResourceAsStream("application.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] arrPhoto = new String[]{ strPhoto2};
		String headXml = JYWebserviceClient.createInHeadXml(new GUID().toString(),JYWebserviceClient.iType_V3,JYWebserviceClient.iSubType_Compare);
		String compareDataXml = JYWebserviceClient.createInCompareDataXml(new GUID().toString(), null, null, strPhoto1, arrPhoto);
		
		String compare_url = props.getProperty("compare.url");
		String clientReturn = null;
		int iTryCount = 0;
		do {
			iTryCount++;
			clientReturn = JYWebserviceClient.executeClient(compare_url , headXml,compareDataXml);
//			Log4jUtil.log.warn(clientReturn);
			if (clientReturn != null) {
				break;
			}
		}while (iTryCount < TOTAL_COUNT);
		
		if(iTryCount > 1){
			Log4jUtil.log.warn("---ESB比对照片次数:" + iTryCount);
		}
		
		return JYWebserviceClient.parserCompareOutXml(JYWebserviceClient.parseXmlForData(clientReturn));
	}
}
