package com.custle.sdk.auth.error;

public class FaceAuthErrorCode {

	public static final int success = 0;
	public static final String success_desc = "人脸识别成功，用户身份信息审核已通过";

	public static final int not_judged = -1;
	public static final String not_judged_desc = "无法判定，用户身份信息审核结果未定";

	// -2 未通过，相似度太低
	// -3 未通过，照片质量不合格
	// -4 未通过，活体检测失败
	// -5 未通过，连接超时，请稍后重试
	// -6 未通过，ESB服务器内部错误
	// -7 未通过，公安服务器内部错误
	// -8 未通过，二代身份证照片不合格
	// -9 未通过，公安服务器连接超时
	// -10 未通过，ESB服务器连接超时
	// -11 未通过，SESSION连接超时
	// -12 未通过，姓名不一致
	// -13 未通过，库中无此号
	// -14 未通过，暂无身份证照片，无法识别
	// -15 未通过，上传最优人脸照片失败，无法识别
	// -16 未通过，开启流程失败，无法识别

	public static final int data_error = -17;
	public static final String data_error_desc = "格式不正确";

	public static final int client_type_error = -18;
	public static final String client_type_error_desc = "无效的客户端类型";

	public static final int client_type_not_match = -19;
	public static final String client_type_not_match_desc = "照片与客户端类型不匹配";

	public static final int id_null = -20;
	public static final String id_null_desc = "身份证号码为必填项";

	public static final int id_error = -21;
	public static final String id_error_desc = "身份证号码格式不正确";
	
	public static final int name_null = -22;
	public static final String name_null_desc = "姓名为必填项";

	public static final int name_error = -23;
	public static final String name_error_desc = "姓名格式不正确";
	
	public static final int photo_null = -24;
	public static final String photo_null_desc = "照片为必填项";

	public static final int photo_error = -25;
	public static final String photo_error_desc = "照片格式不正确";
	
	//-26	检测人脸失败
	//-27	检测到多张人脸
	
	public static final int parameter_null = -90;
	public static final String parameter_null_desc = "接口参数为空";
	
	public static final int json_error = -91;
	public static final String json_error_desc = "FaceRequest，解析JSON错误";
	
	public static final int app_cert_null = -92;
	public static final String app_cert_null_desc = "接入应用鉴权失败，没有配置身份证书";
	
	public static final int app_verify_error = -93;
	public static final String app_verify_error_desc = "接入应用鉴权失败，验证签名失败";
	
	public static final int app_auth_error = -94;
	public static final String app_auth_error_desc = "接入应用鉴权失败，没有认证服务权限";
	
	public static final int app_ip_error = -95;
	public static final String app_ip_error_desc = "接入应用鉴权失败，校验IP白名单错误";
	
	public static final int rkk_proxy_error = -96;
	public static final String rkk_proxy_error_desc = "与人口前置接口通讯异常";
	
	public static final int face_proxy_error = -97;
	public static final String face_proxy_error_desc = "与人证系统接口通讯异常";
	
	public static final int system_error = -98;
	public static final String system_error_desc = "统一认证平台系统异常";

}
