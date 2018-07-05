package com.custle.sdk.auth;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;

import com.custle.sdk.auth.vo.Cert;
import com.custle.sdk.auth.vo.CertRequest;
import com.custle.sdk.auth.vo.Person;
import com.custle.sdk.common.util.P12CertificateInfo;
import com.custle.sdk.common.util.ReadPropertiesUtil;
import com.custle.sdk.common.util.UrlConnect;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CertAuthService {

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
			.getLogger(CertAuthService.class);
	/**
	 * @param bizSN
	 *            ҵ����ˮ��
	 * @param clientType
	 *            �ͻ�������
	 * @param clientName
	 *            �ͻ������ͣ�ȡֵ��ΧΪ{ android/ios/web }
	 * @param person
	 *            �û���Ϣ
	 * @param cert
	 *            ֤������
	 * @return
	 * @throws SignatureException
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public String certAuth(String bizSN, String clientType, String clientName,
			Person person, Cert authCert, String appCode) throws SignatureException,
			JsonGenerationException, JsonMappingException,
			IOException {
		if(appCode != null && !appCode.trim().equals("")){
			appId = appCode;
		}
		CertRequest certRequest = new CertRequest();
		certRequest.setAppID(appId);
		certRequest.setBizSN(bizSN);
		certRequest.setClientName(clientName);
		certRequest.setClientType(clientType);
		certRequest.setPerson(person);
		certRequest.setCert(authCert);

		ObjectMapper mapper = new ObjectMapper();

		String json = mapper.writeValueAsString(certRequest);
		logger.debug("request == " + json);

		NameValuePair[] params = { new NameValuePair("CertRequest", json),
				new NameValuePair("SignatureAlgorithm", signAlg),
				new NameValuePair("SignatureValue", sign(json)) };

		UrlConnect urlConnect = new UrlConnect();
		
//		String param = "CertRequest=" + json + "&SignatureAlgorithm=" + signAlg + "&SignatureValue=" + sign(json);
//		String resultString = urlConnect.connectPostMethodExt(param, url + "/services/certAuth");
		
		String resultString = urlConnect.connectPostMethod(params, url + "/services/certAuth");		
//		String resultString = "{\"Result\":\"0\",\"Return\":\"�����ȶԳɹ�\"}";
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
