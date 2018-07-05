package com.custle.sdk.auth;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;

import com.custle.sdk.common.util.ReadPropertiesUtil;
import com.custle.sdk.common.util.UrlConnect;

public class FaceAuthLocalService {

	static final private String appId = ReadPropertiesUtil.getInstance()
			.getValueFromFile("config", "app_id");

	static final private String appKey = ReadPropertiesUtil.getInstance()
			.getValueFromFile("config", "app_key");

	static final private String url = ReadPropertiesUtil.getInstance()
			.getValueFromFile("config", "url");

	private static final Logger logger = Logger
			.getLogger(FaceAuthLocalService.class);

	public String creatPerson(String personName, String personID, String idPhoto) {

		NameValuePair[] params = { new NameValuePair("AppID", appId),
				new NameValuePair("AppKey", appKey),
				new NameValuePair("PersonName", personName),
				new NameValuePair("PersonID", personID),
				new NameValuePair("IdPhoto", idPhoto) };

		UrlConnect urlConnect = new UrlConnect();
		String resultString = urlConnect.connectPostMethod(params, url
				+ "/person/create");
		logger.info("response == " + resultString);

		return resultString;

	}

	public String recognitionVerify(String personName, String personID,
			String facePhoto, String clientName, String clientType) {

		NameValuePair[] params = { new NameValuePair("AppID", appId),
				new NameValuePair("AppKey", appKey),
				new NameValuePair("ClientName", clientName),
				new NameValuePair("ClientType", clientType),
				new NameValuePair("PersonName", personName),
				new NameValuePair("PersonID", personID),
				new NameValuePair("FacePhoto", facePhoto) };

		UrlConnect urlConnect = new UrlConnect();
		String resultString = urlConnect.connectPostMethod(params, url
				+ "/recognition/verify");
		logger.info("response == " + resultString);

		return resultString;

	}

}
