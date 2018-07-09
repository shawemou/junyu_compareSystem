package com.junyu.service;

import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.Date;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.xfire.XFire;
import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxy;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;
import org.codehaus.xfire.transport.http.EasySSLProtocolSocketFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.custle.sdk.Log4jUtil;
import com.junyu.common.requestBean.OcrBean;
import com.junyu.common.returnBean.OcrReturn;
import com.junyu.config.ReadingSetting;
import com.junyu.mapper.UserMapper;
import com.junyu.pojo.OcrHistory;
import com.junyu.pojo.User;
import com.junyu.utils.Base64ImgUtil;
import com.junyu.utils.CommonUtils;

@Service
public class OCRService extends BaseService<OcrHistory> {

	public static long TIME_OUT_CONNECT = 2000L;
	public static int TIME_OUT_Default = 1000 * 60;

	@Autowired
	private ReadingSetting readingSettinng;
	
	@Autowired
	private UserMapper userMapper;

	/**
	 * 1,����ocr����
	 * 
	 * @Title: ocr
	 * @Description: ��ʼocr����
	 * @param @param ocrBean
	 * @param @param ocrReturn
	 * @return void
	 * @throws
	 */
	public OcrReturn ocr(OcrBean ocrBean, OcrReturn or) {
		JSONObject object = new JSONObject();
		try {
			object.put("front_photo", ocrBean.getClass());
			object.put("head_option", 0);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String strReturn = this.ocr(object);
		Log4jUtil.log.warn(strReturn);

		or.setSuccess(false);

		if (StringUtils.isNotBlank(strReturn)) {
			try {
				JSONObject json = new JSONObject(strReturn);
				String code = String.valueOf(json.get("code"));

				if (StringUtils.isNotBlank(code) && code.equals("0")) {
					json = new JSONObject(String.valueOf(json.get("data")));
					or.setName(json.getString("name"));
					or.setId_number(json.getString("certid"));
					or.setSuccess(true);
					or.setInfo("���֤OCRʶ��ɹ�");
				} else {
					or.setInfo("���֤OCRʶ��ʧ��");
				}
			} catch (JSONException e) {
				e.printStackTrace();
				or.setInfo("���֤OCRʶ��ʧ��,���ݽ����쳣");
			}
		} else {
			or.setInfo("���֤OCRʶ��ʧ��,����OCR�ӿ��쳣");
		}
		return or;
	}

	/**
	 * ����ocr��Webservice�ӿ�
	 * 
	 * @param object
	 * @return
	 */
	public String ocr(JSONObject object) {
		String result = null;
		try {
			String connection_timeout = readingSettinng.getConnection_timeout();
			TIME_OUT_CONNECT = Long.valueOf(connection_timeout);
		} catch (Exception e) {
			Log4jUtil.log.error("ocr�ӿڷ������ӳ�ʱ���ô���", e);
		}
		try {
			String so_timeout = readingSettinng.getSo_timeout();
			TIME_OUT_Default = Integer.valueOf(so_timeout);
		} catch (Exception e) {
			Log4jUtil.log.error("ocr�ӿڷ��ʶ�ȡ��ʱ���ô���", e);
		}

		try {
			// ��Ϊ֧��Xfire HTTPS�Ĵ���ʵ��
			ProtocolSocketFactory easy = new EasySSLProtocolSocketFactory();
			Protocol protocol = new Protocol("https", easy, 443);
			Protocol.registerProtocol("https", protocol);
			org.codehaus.xfire.service.Service service = new ObjectServiceFactory().create(IJYOcrWebservice.class);
			XFire xfire = XFireFactory.newInstance().getXFire();
			XFireProxyFactory factory = new XFireProxyFactory(xfire);
			IJYOcrWebservice client = (IJYOcrWebservice) factory.create(service, readingSettinng.getService_url());

			Client xfireClient = ((XFireProxy) Proxy.getInvocationHandler(client)).getClient();
			HttpClientParams params = new HttpClientParams();
			// http client�����Դ�����Ĭ��3��Ϊ����
			params.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
			// �رճ�����
			params.setBooleanParameter(HttpClientParams.USE_EXPECT_CONTINUE, Boolean.FALSE);
			// �������ӳ�ʱʱ��

			params.setLongParameter(HttpClientParams.CONNECTION_MANAGER_TIMEOUT, TIME_OUT_CONNECT);
			// ���ö�ȡ��ʱʱ��
			params.setIntParameter(HttpClientParams.SO_TIMEOUT, TIME_OUT_Default);

			xfireClient.setProperty(CommonsHttpMessageSender.DISABLE_KEEP_ALIVE, "1");
			xfireClient.setProperty(CommonsHttpMessageSender.DISABLE_EXPECT_CONTINUE, "1");
			xfireClient.setProperty(CommonsHttpMessageSender.HTTP_CLIENT_PARAMS, params);

			String strLicenseCode = readingSettinng.getLicense();
			String strAppSecret = readingSettinng.getSecret_key();
			result = client.ocr(object.toString(), strLicenseCode, string2MD5(object.toString() + strLicenseCode + strAppSecret));
		} catch (Exception e) {
			e.printStackTrace();
			Log4jUtil.log.error("ocr�ӿڷ����쳣", e);
		}
		return result;
	}

	public static String string2MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)// �����һ���ַ�ascii��С��16�Ļ� ��ô��ת16���ƵĻ�����Ե�ǰ���0
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	interface IJYOcrWebservice {
		public String ocr(String strJsonIn, String strLicenseCode, String strEncryValue);
	}

	/**
	 * 2,��¼��־
	 * 
	 * @Title: saveOcrLog
	 * @Description: TODO
	 * @param @param ocrBean
	 * @param @param ocrReturn
	 * @return void
	 * @throws
	 */
	public void saveOcrLog(OcrBean ocrBean, OcrReturn ocrReturn) {

		String mobile = ocrBean.getMobile();
		if (mobile != null && mobile.length() > 11) {
			ocrBean.setMobile(mobile.substring(0, 11));
		}
		OcrHistory ocrHistory = new OcrHistory();
		ocrHistory.setBarCode(ocrBean.getBar_code());
		ocrHistory.setGuid(CommonUtils.getUUID());
		ocrHistory.setHoldTime(new BigDecimal(new Date().getTime() - ocrReturn.getBeginDate().getTime()));
		ocrHistory.setIdNumber(ocrReturn.getId_number());
		ocrHistory.setIdPhoto(ocrBean.getId_photo());
		ocrHistory.setMobile(ocrBean.getMobile());
		ocrHistory.setName(ocrReturn.getName());
		ocrHistory.setSuccess(ocrReturn.getSuccess() ? "1" : "0");
		ocrHistory.setPhoId(Base64ImgUtil.fromBase64(ocrBean.getId_photo()));
		User user = this.userMapper.selectByPrimaryKey(ocrBean.getUser_guid());
		ocrHistory.setUserName(user.getName());
		Integer result = this.save(ocrHistory);
	}
}
