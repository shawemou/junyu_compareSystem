package com.junyu.server.Client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.codehaus.xfire.XFireRuntimeException;
import org.codehaus.xfire.aegis.AegisBindingProvider;
import org.codehaus.xfire.annotations.AnnotationServiceFactory;
import org.codehaus.xfire.annotations.jsr181.Jsr181WebAnnotations;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxy;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.jaxb2.JaxbTypeRegistry;
import org.codehaus.xfire.service.Endpoint;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.soap.AbstractSoapBinding;
import org.codehaus.xfire.transport.TransportManager;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.custle.sdk.Log4jUtil;
import com.junyu.common.returnBean.CompareReturn;
import com.junyu.common.returnBean.CompareReturnBean;
import com.junyu.common.returnBean.EnumInstance;
import com.junyu.common.returnBean.EnumInstance.EReturn;
import com.junyu.common.returnBean.EnumInstance.EReturnModifyPwd;
import com.junyu.common.returnBean.EnumInstance.Return;
import com.sun.tools.javac.comp.Check;

public class JYWebserviceClient{
	public static final int iType = 2201;          // 本地比对标志位3.9算法
	public static final int iSubType_Compare = 1;  //1单张证件照比对多张抓拍照
	public static final int iType_V2 = 2202;       //本地比对标志位4.0算法，2015-08-28 陆林林
	public static final int iType_V3 = 2204;       //本地比对标志位4.1算法
	public static final int iSubType_Photo = 2;    //2检测证件照
	public static final int TIME_OUT_Default = 1000 * 10;// 2015-07-01将60秒改为5秒 2015-08-28 改为10秒陆林林
	public static final int TIME_OUT_LONG = 1000 * 60;
	
	public static final int iType_V5 = 2203;       //人工坐席

	public static final int iSubType_Shear = 3;    //3照片剪切
	
	private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
	private HashMap endpoints = new HashMap();
	private Service service0;
	public JYWebserviceClient() {
		create0();
		Endpoint JYWebserviceSoapEP = service0.addEndpoint(new QName(
				"http://tempuri.org/", "JYWebserviceSoap"), new QName(
				"http://tempuri.org/", "JYWebserviceSoap"),
				"http://127.0.0.1/service.asmx");
		endpoints.put(new QName("http://tempuri.org/", "JYWebserviceSoap"),
				JYWebserviceSoapEP);
		Endpoint JYWebserviceSoapLocalEndpointEP = service0.addEndpoint(
				new QName("http://tempuri.org/",
						"JYWebserviceSoapLocalEndpoint"), new QName(
						"http://tempuri.org/", "JYWebserviceSoapLocalBinding"),
				"xfire.local://JYWebservice");
		endpoints.put(new QName("http://tempuri.org/",
				"JYWebserviceSoapLocalEndpoint"),
				JYWebserviceSoapLocalEndpointEP);
	}

	public JYWebserviceClient(String url) {
		create0();
		Endpoint JYWebserviceSoapEP = service0.addEndpoint(new QName(
				"http://tempuri.org/", "JYWebserviceSoap"), new QName(
				"http://tempuri.org/", "JYWebserviceSoap"), "http://" + url);
		endpoints.put(new QName("http://tempuri.org/", "JYWebserviceSoap"),
				JYWebserviceSoapEP);
		Endpoint JYWebserviceSoapLocalEndpointEP = service0.addEndpoint(
				new QName("http://tempuri.org/",
						"JYWebserviceSoapLocalEndpoint"), new QName(
						"http://tempuri.org/", "JYWebserviceSoapLocalBinding"),
				"xfire.local://JYWebservice");
		endpoints.put(new QName("http://tempuri.org/",
				"JYWebserviceSoapLocalEndpoint"),
				JYWebserviceSoapLocalEndpointEP);
	}

	public Object getEndpoint(Endpoint endpoint) {
		try {
			return proxyFactory.create((endpoint).getBinding(), (endpoint)
					.getUrl());
		} catch (MalformedURLException e) {
			throw new XFireRuntimeException("Invalid URL", e);
		}
	}

	public Object getEndpoint(QName name) {
		Endpoint endpoint = ((Endpoint) endpoints.get((name)));
		if ((endpoint) == null) {
			throw new IllegalStateException("No such endpoint!");
		}
		return getEndpoint((endpoint));
	}

	public Collection getEndpoints() {
		return endpoints.values();
	}

	private void create0() {
		TransportManager tm = (org.codehaus.xfire.XFireFactory.newInstance()
				.getXFire().getTransportManager());
		HashMap props = new HashMap();
		props.put("annotations.allow.interface", true);
		AnnotationServiceFactory asf = new AnnotationServiceFactory(
				new Jsr181WebAnnotations(), tm, new AegisBindingProvider(
						new JaxbTypeRegistry()));
		asf.setBindingCreationEnabled(false);
		service0 = asf.create((JYWebserviceSoap.class), props);
		{
			AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0,
					new QName("http://tempuri.org/", "JYWebserviceSoap"),
					"http://schemas.xmlsoap.org/soap/http");
		}
		{
			AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0,
					new QName("http://tempuri.org/",
							"JYWebserviceSoapLocalBinding"),
					"urn:xfire:transport:local");
		}
	}

	public JYWebserviceSoap getJYWebserviceSoap() {
		return ((JYWebserviceSoap) (this).getEndpoint(new QName(
				"http://tempuri.org/", "JYWebserviceSoap")));
	}

	public JYWebserviceSoap getJYWebserviceSoap(String url) {
		JYWebserviceSoap var = getJYWebserviceSoap();
		org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
		return var;
	}

	public JYWebserviceSoap getJYWebserviceSoapLocalEndpoint() {
		return ((JYWebserviceSoap) (this).getEndpoint(new QName(
				"http://tempuri.org/", "JYWebserviceSoapLocalEndpoint")));
	}

	public JYWebserviceSoap getJYWebserviceSoapLocalEndpoint(String url) {
		JYWebserviceSoap var = getJYWebserviceSoapLocalEndpoint();
		org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
		return var;
	}

	/**
	 * 得到JYESBHead xml信息
	 * 
	 * @param strGuid 每次生成
	 * @param iType
	 * @param iSubType
	 * @return JYESBHead xml头信息
	 */
	public static String createInHeadXml(String strGuid, int iType, int iSubType) {
		return "<JYESBHead><iVersion>1000</iVersion><iType>" + iType
				+ "</iType><iSubType>" + iSubType
				+ "</iSubType><iMsgAttribute>0</iMsgAttribute>"
				+ "<strTaskGuid>" + strGuid + "</strTaskGuid>" + "</JYESBHead>";
	}
	
	/**
	 * 创建解密数据包的InXml(与C++通信)
	 * 
	 * @param strDataBase64
	 * @return xml字符串
	 */
	public static String createDecodeInXml(String strDataBase64) {
		return "<JYSendRoot><Data><Data_Base64>" + strDataBase64
				+ "</Data_Base64></Data></JYSendRoot>";
	}

	/**
	 * 动态生成比对输入的xml字符串，主要包括身份证照片和最优人脸照片等信息
	 * 
	 * @param strTaskGuid
	 * @param strName
	 * @param strId
	 * @param strIdPhoto
	 * @param arrPhoto
	 * @return
	 */
	public static String createInCompareDataXml(String strTaskGuid, String strName, String strId, String strIdPhoto, String[] arrPhoto) {
		String xmlStr = null;
		ByteArrayOutputStream bos = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			Element root = document.createElement("CompareRoot");
			document.appendChild(root);

			Element taskGuidE = document.createElement("TaskGuid");
			taskGuidE.appendChild(document.createTextNode(strTaskGuid));
			root.appendChild(taskGuidE);

			Element nameE = document.createElement("PersonName");
			if (strName != null)
				nameE.appendChild(document.createTextNode(strName));
			root.appendChild(nameE);

			Element idE = document.createElement("PersonId");
			if (strId != null)
				idE.appendChild(document.createTextNode(strId));
			root.appendChild(idE);

			Element idPhoto_Base64E = document.createElement("IdPhoto_Base64");
			idPhoto_Base64E.appendChild(document.createTextNode(strIdPhoto));
			root.appendChild(idPhoto_Base64E);

			Element itemsE = document.createElement("Items");
			if (arrPhoto != null) {
				for (int i = 0; i < arrPhoto.length; i++) {
					Element itemE = document.createElement("Item");
					Element optimalFace_Base64E = document
							.createElement("Photo_Base64");
					optimalFace_Base64E.appendChild(document
							.createTextNode(arrPhoto[i]));
					// optimalFace_Base64E.appendChild(document
					// .createTextNode(""));//测试用
					itemE.appendChild(optimalFace_Base64E);
					itemsE.appendChild(itemE);
				}
			}
			root.appendChild(itemsE);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();

			// 存在乱码问题
			transformer.setOutputProperty(OutputKeys.ENCODING, "gb2312");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			bos = new ByteArrayOutputStream();

			transformer.transform(new DOMSource(document),
					new StreamResult(bos));
			xmlStr = bos.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return xmlStr;
		} finally {
			try {
				if (bos != null)
					bos.close();
			} catch (IOException e) {
				e.printStackTrace();

			}
		}
		return xmlStr;
	}

	/**
	 * 创建检测照片的传入XML
	 * @param IDPhotos 需要检测照片组
	 * @return xml字符串
	 */
	public static String createCheckPhotoInXml(String[] IDPhotos) {
		String strXml = null;
		if (IDPhotos == null || IDPhotos.length <= 0) {
			return strXml;
		}
		strXml = "<JYSendRoot>" + "<Data>" + "<IDPhotos>";
		for (String strPhoto : IDPhotos) {
			strXml += "<Item>" + strPhoto + "</Item>";
		}
		strXml += "</IDPhotos>" + "</Data>" + "</JYSendRoot>";
		return strXml;
	}
	
	/**
	 * 
	 * 创建剪切证件翻拍xml
	 * @param IDPhoto
	 * @return
	 */
	public static String createShearPhotoInXml(String IDPhoto){
		return "<JYSendRoot><Photo_Base64>" + IDPhoto + "</Photo_Base64></JYSendRoot>";
	}

	/**
	 * 解析接口返回的xml,只解析第一层包装
	 * @param strXmlIn JYESBReturn返回的xml字符串
	 * @param rtInfo
	 * @return 判断ESB返回成功与否,得到strData节点的信息(节点信息也是xml字符串)
	 */
	public static String parseXmlForData(String strXmlIn) {
		if (strXmlIn == null || ("").equals(strXmlIn)) {
			return null;
		}
		StringReader sr = new StringReader(strXmlIn);
		InputSource is = new InputSource(sr);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
			Document document = db.parse(is);
			Element root = document.getDocumentElement();

			NodeList iReturnNode = root.getElementsByTagName("iReturnType");
			if (iReturnNode != null && iReturnNode.getLength() > 0) {
				int iReturn = Integer.parseInt(iReturnNode.item(0).getTextContent());// ESB返回值 >0是警告 12002服务未挂载
				if (iReturn >= Integer.parseInt(EnumInstance.EReturn.RT_Success) 
						&& iReturn <= Integer.parseInt(EnumInstance.EReturn.RT_NotMount)) {
					NodeList returnDataNode = root.getElementsByTagName("strData");
					if (returnDataNode != null && returnDataNode.getLength() > 0) {
						Element returnDataE = (Element) returnDataNode.item(0);
						return returnDataE.getTextContent();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 解析检测照片的返回值
	 * @param xmlString
	 * @param IDPhotos 传入的照片组
	 * @param iPhotoReturn 照片的检测结果返回码
	 * @return
	 */
	public static boolean parserCheckOutXml(String xmlString) {
		if (StringUtils.isBlank(xmlString)) {
			return false;
		}
		Document document = null;
		try {
			StringReader sr = new StringReader(xmlString);
			InputSource is = new InputSource(sr);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = null;
			db = dbf.newDocumentBuilder();
			document = db.parse(is);

			Element root = document.getDocumentElement();
			NodeList Return = root.getElementsByTagName("iReturn");
			if (Return != null && Return.getLength() == 1) {
				Element e = (Element) Return.item(0);
				if (StringUtils.isNotBlank(e.getTextContent())) {
					
					int iReturn = NumberUtils.toInt(e.getTextContent(), -1);
					if (iReturn >= Integer.parseInt(EnumInstance.EReturn.RT_Success)) {// 失败的可能:-1解析xml失败;-1000算法初始化失败
						NodeList idPhotoItemNL = root.getElementsByTagName("Item");
						if (idPhotoItemNL != null) {
							Element itemE = null;
							for (int i = 0; i < idPhotoItemNL.getLength(); i++) {
								itemE = (Element) idPhotoItemNL.item(i);
								
								iReturn = NumberUtils.toInt((itemE.getElementsByTagName("Result")).item(0).getTextContent(), -1);
								if (iReturn >= Integer.parseInt(EnumInstance.EReturn.RT_Success)){

									return true;
								}
							}
						}
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 解析比对的C++ webservice输出xml格式
	 * @param xmlString
	 * @param rtInfo
	 * @return
	 */
	//做修改设置returncode为code
	public static CompareReturn parserCompareOutXml(String xmlString, CompareReturn cr) {
		if (StringUtils.isBlank(xmlString)) {
			cr.setSuccess(false);
			cr.setCode(EReturn.RT_Timeout);
			cr.setReturn_code(Return.RT_Not_Compare);
			cr.setInfo("比对服务异常");
			return cr;
		}
		
		Log4jUtil.log.warn(xmlString);
		
		// 将传进来的XmlString字符串变成xml文件格式解析
		Document document = null;
		try {
			StringReader sr = new StringReader(xmlString);
			InputSource is = new InputSource(sr);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = null;
			db = dbf.newDocumentBuilder();
			document = db.parse(is);

			Element root = document.getDocumentElement();
			NodeList returnList = root.getElementsByTagName("iReturn");
			if (returnList.getLength() >= 1) {
				Element e = (Element) returnList.item(0);
				if (StringUtils.isNotBlank(e.getTextContent())) {
					if( e.getTextContent().equals(EnumInstance.EReturn.RT_Success) ){
						cr.setReturn_code(Return.RT_Success);
						cr.setCode(EnumInstance.EReturn.RT_Success);
						cr.setInfo(EReturnModifyPwd.map.get(EnumInstance.EReturn.RT_Success));
					}else if( e.getTextContent().equals(EnumInstance.EReturn.RT_Fail) || e.getTextContent().equals(EnumInstance.EReturn.RT_Unsure) ){
						cr.setReturn_code(Return.RT_Fail);
						cr.setCode(EnumInstance.EReturn.RT_Fail);
						cr.setInfo(EReturnModifyPwd.map.get(EnumInstance.EReturn.RT_Fail));
					}else{
						cr.setSuccess(false);
						cr.setReturn_code(Return.RT_Not_Compare);
						cr.setCode(e.getTextContent());
						cr.setInfo(EReturnModifyPwd.map.get(e.getTextContent()));
					}
					
					// 日志记录strLastErr
					NodeList lastErrNL = root.getElementsByTagName("strLastErr");
					if (lastErrNL != null && lastErrNL.getLength() > 0 
							&& StringUtils.isNotBlank(lastErrNL.item(0).getTextContent())) {
						Log4jUtil.log.warn(lastErrNL.item(0).getTextContent());
					}
					
					NodeList simNList = root.getElementsByTagName("iSim");
					if (simNList != null && simNList.getLength() > 0) {
						//比对 返回结果 解析
						for (int i = 0; i < simNList.getLength(); i++) {
							int iSimilarity = Integer.valueOf(simNList.item(i).getTextContent());
							if(iSimilarity >= 0){
								cr.setSimilarity(iSimilarity);
							}else{
								cr.setSuccess(false);
								cr.setReturn_code(Return.RT_Not_Compare);
								cr.setCode(String.valueOf(iSimilarity));
								cr.setInfo("未验证");
							}
						}
					}
				}
			}
		} catch (Exception e1) {
			Log4jUtil.log.error("比对服务异常", e1);
			cr.setSuccess(false);
			cr.setReturn_code(Return.RT_Not_Compare);
			cr.setCode(EReturn.RT_Timeout);
			cr.setInfo("比对服务异常");
		}
		return cr;
	}

	/**
	 * 执行调用C++
	 * 返回调用检测证件照webservice接口，返回xml字符串
	 * @param URL      接口url
	 * @param strHead  入参(调用类型xml)
	 * @param strData  入参(照片xml)
	 * @return
	 * &lt;JYESBReturn&gt; <br>
	 * &lt;iReturnType&gt;1&lt;/iReturnType&gt;	&lt;!--参见ESB返回值定义--&gt; <br>
	 * &lt;strData&gt; <br>
	 * &lt;!-- 返回的XML此节点下，需要用xml解析器再次解析该节点的值--&gt; <br>
	 * &lt;/strData&gt; <br>
	 * &lt;/JYESBReturn&gt;
	 */
	public static String executeClient(String URL, String strHead, String strData) {
		return executeClient(URL, strHead, strData, TIME_OUT_Default);
	}
	/**
	 * 执行调用C++
	 * 
	 * @param URL
	 * @param strHead
	 * @param strData
	 * @return
	 */
	public static String executeClient(String url, String strHead, String strData, int iTimeOut) {
		String strResult = null;
		HttpURLConnection httpConnection = null;
		try {
			JYWebserviceClient client = new JYWebserviceClient(url);
			JYWebserviceSoap service = client.getJYWebserviceSoap();
			// /////////////////////////////////////////////////
			Client client2 = ((XFireProxy) Proxy.getInvocationHandler(service)).getClient();
			// 设置连接参数
			HttpClientParams params = new HttpClientParams();
			params.setBooleanParameter(HttpClientParams.USE_EXPECT_CONTINUE, Boolean.FALSE);
//			params.setLongParameter(HttpClientParams.HEAD_BODY_CHECK_TIMEOUT,1000l);
//			// 连接超时时间单位是毫秒
//			params.setLongParameter(HttpClientParams.CONNECTION_MANAGER_TIMEOUT,1000l);
//			// 等待数据超时时间单位是毫秒
			params.setIntParameter(HttpClientParams.SO_TIMEOUT, iTimeOut);//
//			
			client2.setProperty(CommonsHttpMessageSender.HTTP_CLIENT_PARAMS, params);
			// /////////////////////////////////////////////////////////////////
			ToIngestInvokeHead head = new ToIngestInvokeHead();
			head.setStrXmlHead(strHead);

			ToIngestInvokeData data = new ToIngestInvokeData();
			data.setStrData(strData);
			ToIngestInvokeResponse invokeResult = service.toIngestInvoke(head, data);
			if (invokeResult != null) {
				strResult = invokeResult.getToIngestInvokeResult();
			}
		} catch (Exception e) {
			return strResult;
		}
		return strResult;
	}
	
	/**
	 * 解析比对的C++ webservice输出xml格式
	 * @param xmlString
	 * @param rtInfo
	 * @return
	 */
	public static CompareReturnBean parserCompareOutXml(String xmlString) {
		CompareReturnBean bean = new CompareReturnBean();
		if (StringUtils.isBlank(xmlString)) {
			bean.setCode(EReturn.RT_Timeout);
			return bean;
		}
		
		Log4jUtil.log.warn(xmlString);
		
		// 将传进来的XmlString字符串变成xml文件格式解析
		Document document = null;
		try {
			StringReader sr = new StringReader(xmlString);
			InputSource is = new InputSource(sr);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = null;
			db = dbf.newDocumentBuilder();
			document = db.parse(is);

			Element root = document.getDocumentElement();
			NodeList Return = root.getElementsByTagName("iReturn");
			if (Return.getLength() >= 1) {
				Element e = (Element) Return.item(0);

				if (StringUtils.isNotBlank(e.getTextContent())) {
					bean.setCode(e.getTextContent());
					
					// 日志记录strLastErr
					NodeList lastErrNL = root.getElementsByTagName("strLastErr");
					if (lastErrNL != null && lastErrNL.getLength() > 0 
							&& StringUtils.isNotBlank(lastErrNL.item(0).getTextContent())) {
						Log4jUtil.log.warn(lastErrNL.item(0).getTextContent());
					}
					
					NodeList simNList = root.getElementsByTagName("iSim");
					if (simNList != null && simNList.getLength() > 0) {
						//比对 返回结果 解析
						for (int i = 0; i < simNList.getLength(); i++) {
							int iSimilarity = Integer.valueOf(simNList.item(i).getTextContent());
							if(iSimilarity >= 0){
								bean.setSimilarity(iSimilarity);
							}else{
								bean.setCode(String.valueOf(iSimilarity));
							}
						}
					}
				}else{
					bean.setCode(EReturn.RT_InError);
				}
			}
		} catch (Exception e1) {
			Log4jUtil.log.error("比对服务异常", e1);
			bean.setCode(EReturn.RT_InError);
		}
		return bean;
	}
}
