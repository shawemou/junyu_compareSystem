package com.custle.sdk.auth;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;

import com.custle.sdk.auth.vo.QryIDPhotoRequest;
import com.custle.sdk.common.util.P12CertificateInfo;
import com.custle.sdk.common.util.ReadPropertiesUtil;
import com.custle.sdk.common.util.UrlConnect;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class QryIDPhotoService {

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
			.getLogger(QryIDPhotoService.class);

	public String qryIDPhoto(String bizSN, String personName, String personID, String appCode) throws SignatureException,
			JsonGenerationException, JsonMappingException,
			IOException {
		if(appCode != null && !appCode.trim().equals("")){
			appId = appCode;
		}
		QryIDPhotoRequest qryIDPhotoRequest = new QryIDPhotoRequest();
		qryIDPhotoRequest.setAppID(appId);
		qryIDPhotoRequest.setBizSN(bizSN);
		qryIDPhotoRequest.setPersonID(personID);
		qryIDPhotoRequest.setPersonName(personName);

		ObjectMapper mapper = new ObjectMapper();

		String json = mapper.writeValueAsString(qryIDPhotoRequest);
		logger.debug("request == " + json);

		NameValuePair[] params = { new NameValuePair("QryIDPhotoRequest", json),
				new NameValuePair("SignatureAlgorithm", signAlg),
				new NameValuePair("SignatureValue", sign(json)) };

		UrlConnect urlConnect = new UrlConnect();
		String resultString = urlConnect.connectPostMethod(params, url
				+ "/services/qryIDPhoto");
		logger.debug("response == " + resultString);
		// FaceResponse faceResponse = (FaceResponse) mapper.readValue(
		// resultString, FaceResponse.class);

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
