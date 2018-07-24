package com.custle.sdk.common.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

public class UrlConnect {
	private static Logger logger = Logger.getLogger(UrlConnect.class);

	public String connectPostMethod(NameValuePair[] data, String url) {
		
		logger.debug(url);
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		postMethod.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		postMethod.setRequestBody(data);

		int statusCode = 0;
		try {
			statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + postMethod.getStatusLine());
			}

			byte[] responseBody = inputStreamToByte(postMethod
					.getResponseBodyAsStream());
			logger.debug(new String(responseBody, "UTF-8"));
			return new String(responseBody, "UTF-8");
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			logger.error("HttpException: " + e);
			return null;
		} catch (UnknownHostException e) {
			logger.error("UnknownHostException: " + e);
			return null;
		} catch (IOException e) {
			// 发生网络异常
			logger.error("IOException: " + e);
			return null;
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
	}

	public String connectPostMethodExt(String param, String url) {
		logger.info(url);
		logger.info(param);
		HttpsURLConnection httpsConn = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());

			// 创建URL对象
			URL requestUrl = new URL(url);
			// 创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
			httpsConn = (HttpsURLConnection) requestUrl.openConnection();
			// 设置套接工厂
			httpsConn.setSSLSocketFactory(sslContext.getSocketFactory());
			httpsConn
					.setHostnameVerifier(new UrlConnect().new TrustAnyHostnameVerifier());

			// 加入数据
			httpsConn.setRequestProperty(HttpMethodParams.HTTP_CONTENT_CHARSET,
					"UTF-8");
			httpsConn.setRequestProperty("Content-Type",
					"application/json; charset=utf-8");
			httpsConn.setRequestMethod("POST");
			httpsConn.setDoOutput(true);
			
			DataOutputStream out = new DataOutputStream(httpsConn
					.getOutputStream());
			if (param != null)
				out.writeBytes(param);

			out.flush();
			out.close();

			int code = httpsConn.getResponseCode();

			if (HttpsURLConnection.HTTP_OK == code) {
				// 取得该连接的输入流，以读取响应内容
				byte[] responseBody = inputStreamToByte(httpsConn
						.getInputStream());

				logger.info(new String(responseBody, "UTF-8"));
				return new String(responseBody, "UTF-8");
			} else {
				logger.error("https resturn == " + code);
				return null;
			}
		} catch (KeyManagementException e) {
			logger.error("KeyManagementException: ", e);
			return null;
		} catch (NoSuchAlgorithmException e) {
			logger.error("NoSuchAlgorithmException: ", e);
			return null;
		} catch (NoSuchProviderException e) {
			logger.error("NoSuchProviderException: ", e);
			return null;
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return null;
		} finally {
			// 释放连接
			if (httpsConn != null) {
				httpsConn.disconnect();
			}
		}
	}

	public String connectGetMethod(String url) {
		logger.info(url);
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());

		getMethod.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

		int statusCode = 0;
		try {
			statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + getMethod.getStatusLine());
			}

			byte[] responseBody = inputStreamToByte(getMethod
					.getResponseBodyAsStream());

			logger.debug(new String(responseBody, "UTF-8"));
			return new String(responseBody, "UTF-8");
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			logger.error("HttpException: " + e);
			return null;
		} catch (UnknownHostException e) {
			logger.error("UnknownHostException: " + e);
			return null;
		} catch (IOException e) {
			// 发生网络异常
			logger.error("IOException: " + e);
			return null;
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}
	}

	private byte[] inputStreamToByte(InputStream is) throws IOException {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		while ((ch = is.read()) != -1) {
			bytestream.write(ch);
		}
		byte imgdata[] = bytestream.toByteArray();
		bytestream.close();
		return imgdata;
	}

	public class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			// 直接返回true
			return true;
		}
	}

	public static void main(String[] args) throws IOException {
		UrlConnect client = new UrlConnect();
		//		
		String para = "{\"AppOID\":\"String content\",\"CertSN\":\"String content\",\"Result\":\"String content\",\"SignatureAlgorithm\":\"String content\",\"SignatureValue\":\"String content\"}";
		String response = client
				.connectPostMethodExt(para,
						"https://192.168.2.146/AASService/ApplyCertAppAuthorizeResponse");
		System.out.println("==============================" + response);

		// com.sheca.eausp.innerface.yzt.HttpClient client2 = new
		// com.sheca.eausp.innerface.yzt.HttpClient();
		// byte[] res =
		// client2.execute("http://192.168.2.146/AASService/ApplyCertAppAuthorizeResponse",
		// para.getBytes());
		// System.out.println("res==============================" +new
		// String(res));

	}

}
