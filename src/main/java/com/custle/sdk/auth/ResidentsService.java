package com.custle.sdk.auth;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;

import com.custle.sdk.auth.vo.AuditApplyRequest;
import com.custle.sdk.auth.vo.CompareRequest;
import com.custle.sdk.auth.vo.VerifyApplyRequest;
import com.custle.sdk.common.util.P12CertificateInfo;
import com.custle.sdk.common.util.ReadPropertiesUtil;
import com.custle.sdk.common.util.UrlConnect;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResidentsService {

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

	private static final Logger logger = Logger
			.getLogger(ResidentsService.class);

	/**
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws SignatureException
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 */
	public String verifyApply(VerifyApplyRequest request) 
        throws IOException, SignatureException, JsonGenerationException, JsonMappingException{
		ObjectMapper mapper = new ObjectMapper();

		String json = mapper.writeValueAsString(request);
		logger.info("verifyApply request == " + json);

		NameValuePair[] params = { new NameValuePair("VerifyApplyRequest", json),
				new NameValuePair("SignatureAlgorithm", signAlg),
				new NameValuePair("SignatureValue", sign(json)) };

		UrlConnect urlConnect = new UrlConnect();
		String resultString = urlConnect.connectPostMethod(params, url
				+ "/services/residentsVerifyApply");
		logger.info("verifyApply response == " + resultString);
		return resultString;
	}
	
	/**
	 * 
	 * @param bizSN
	 * @param result
	 * @param msg
	 * @param similarity
	 * @return
	 * @throws IOException
	 * @throws SignatureException
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 */
	public String compare(String bizSN, int result, String msg, String similarity) 
	    throws IOException, SignatureException, JsonGenerationException, JsonMappingException{
		CompareRequest compareRequest = new CompareRequest();
		compareRequest.setAppID(appId);
		compareRequest.setBizSN(bizSN);
		compareRequest.setResult(result);
		compareRequest.setMsg(msg);
		compareRequest.setSimilarity(similarity);
		
		ObjectMapper mapper = new ObjectMapper();

		String json = mapper.writeValueAsString(compareRequest);
		logger.info("compare request == " + json);

		NameValuePair[] params = { new NameValuePair("CompareRequest", json),
				new NameValuePair("SignatureAlgorithm", signAlg),
				new NameValuePair("SignatureValue", sign(json)) };

		UrlConnect urlConnect = new UrlConnect();
		String resultString = urlConnect.connectPostMethod(params, url
				+ "/services/compareByLanid");
		logger.info("compare response == " + resultString);
		return resultString;
	}
	
	public String auditApply(AuditApplyRequest request) 
	    throws IOException, SignatureException, JsonGenerationException, JsonMappingException{
		ObjectMapper mapper = new ObjectMapper();

		String json = mapper.writeValueAsString(request);
		logger.info("auditApply request == " + json);

		NameValuePair[] params = { new NameValuePair("AuditApplyRequest", json),
				new NameValuePair("SignatureAlgorithm", signAlg),
				new NameValuePair("SignatureValue", sign(json)) };

		UrlConnect urlConnect = new UrlConnect();
		String resultString = urlConnect.connectPostMethod(params, url
				+ "/services/residentsAuditApply");
		logger.info("auditApply response == " + resultString);
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
