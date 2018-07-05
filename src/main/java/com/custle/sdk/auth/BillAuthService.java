package com.custle.sdk.auth;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;

import com.alibaba.fastjson.JSONObject;
import com.custle.sdk.common.util.P12CertificateInfo;
import com.custle.sdk.common.util.ReadPropertiesUtil;
import com.custle.sdk.common.util.UrlConnect;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class BillAuthService {

	static private String signAlg = ReadPropertiesUtil.getInstance()
			.getValueFromFile("config", "sign_alg");

	static private String cert = ReadPropertiesUtil.getInstance()
			.getValueFromFile("config", "app_cert");

	static private String pincode = ReadPropertiesUtil.getInstance()
			.getValueFromFile("config", "app_cert_pincode");

	static private String appId = ReadPropertiesUtil.getInstance()
			.getValueFromFile("config", "app_id");

	static private String url = ReadPropertiesUtil.getInstance()
			.getValueFromFile("config", "url");

	private static Logger logger = Logger
			.getLogger(BillAuthService.class);

	public String billAuth(String bizSN, String appCode, String productCode, String paidAppId, String exprise) throws SignatureException,
			JsonGenerationException, JsonMappingException,
			IOException {
		if(appCode != null && !appCode.trim().equals("")){
			appId = appCode;
		}
		JSONObject billAuthJson = new JSONObject();
		billAuthJson.put("AppID", appId);
		billAuthJson.put("BizSN", bizSN);
		billAuthJson.put("ProductCode", productCode);
		billAuthJson.put("PaidAppID", paidAppId);
		billAuthJson.put("ExpandsProperties", exprise);

		String json = billAuthJson.toString();
		logger.debug("request == " + json);

		NameValuePair[] params = { new NameValuePair("BillAuthRequest", URLEncoder.encode(json,"GBK")),
				new NameValuePair("SignatureAlgorithm", signAlg),
				new NameValuePair("SignatureValue", sign(json)) };

		UrlConnect urlConnect = new UrlConnect();
		String resultString = urlConnect.connectPostMethod(params, url
				+ "/services/billAuth");
		logger.debug("response == " + resultString);
		return resultString;
	}

	private String sign(String inData) throws SignatureException {
		String signValue = "";
		try {
			P12CertificateInfo p12 = new P12CertificateInfo(cert, pincode);
			PrivateKey priKey = p12.getPrikey();

			Signature signature = Signature.getInstance(signAlg);
			signature.initSign(priKey);
			signature.update(inData.getBytes());
			signValue = new String(Base64.encode(signature.sign()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new SignatureException(e.getMessage());
		}

		return signValue;
	}
}
