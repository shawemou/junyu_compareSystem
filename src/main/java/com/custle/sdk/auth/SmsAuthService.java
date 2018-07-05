
package com.custle.sdk.auth;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;

import com.custle.sdk.auth.vo.Person;
import com.custle.sdk.auth.vo.Sms;
import com.custle.sdk.auth.vo.SmsRequest;
import com.custle.sdk.common.util.P12CertificateInfo;
import com.custle.sdk.common.util.ReadPropertiesUtil;
import com.custle.sdk.common.util.UrlConnect;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SmsAuthService {

	static final private String signAlg = ReadPropertiesUtil.getInstance()
			.getValueFromFile("config", "sign_alg");

	static final private String cert = ReadPropertiesUtil.getInstance()
			.getValueFromFile("config", "app_cert");

	static final private String pincode = ReadPropertiesUtil.getInstance()
			.getValueFromFile("config", "app_cert_pincode");

	static final private String appId = ReadPropertiesUtil.getInstance()
			.getValueFromFile("config", "app_id");

	static final private String url = ReadPropertiesUtil.getInstance()
			.getValueFromFile("config", "url");

	private static final Logger logger = Logger.getLogger(SmsAuthService.class);

	public String sendMsg(String mobilePhone) throws SignatureException {
		String inData = "AppID=" + appId + "&Mobilephone=" + mobilePhone;

		NameValuePair[] params = { new NameValuePair("AppID", appId),
				new NameValuePair("MobilePhone", mobilePhone),
				new NameValuePair("SignatureAlgorithm", signAlg),
				new NameValuePair("SignatureValue", sign(inData)) };

		logger.info("mobilePhone == " + mobilePhone);

		UrlConnect urlConnect = new UrlConnect();
		String resultString = urlConnect.connectPostMethod(params, url
				+ "/services/sendMsg");
		logger.info("response == " + resultString);
		// FaceResponse faceResponse = (FaceResponse) mapper.readValue(
		// resultString, FaceResponse.class);

		return resultString;

	}

	/**
	 * @param bizSN
	 *            ҵ����ˮ��
	 * @param clientType
	 *            �ͻ�������
	 * @param clientName
	 *            �ͻ������ͣ�ȡֵ��ΧΪ{ android/ios/web }
	 * @param person
	 *            �û���Ϣ
	 * @param Sms
	 *            ��������
	 * @return
	 * @throws SignatureException
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public String smsAuth(String bizSN, String clientType, String clientName,
			Person person, Sms sms) throws SignatureException,
			JsonGenerationException, JsonMappingException, IOException {

		SmsRequest smsRequest = new SmsRequest();
		smsRequest.setAppID(appId);
		smsRequest.setBizSN(bizSN);
		smsRequest.setClientName(clientName);
		smsRequest.setClientType(clientType);
		smsRequest.setPerson(person);
		smsRequest.setSms(sms);

		ObjectMapper mapper = new ObjectMapper();

		String json = mapper.writeValueAsString(smsRequest);
		logger.info("request == " + json);

		NameValuePair[] params = { new NameValuePair("SmsRequest", json),
				new NameValuePair("SignatureAlgorithm", signAlg),
				new NameValuePair("SignatureValue", sign(json)) };

		UrlConnect urlConnect = new UrlConnect();
		String resultString = urlConnect.connectPostMethod(params, url
				+ "/services/smsAuth");
		logger.info("response == " + resultString);
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
