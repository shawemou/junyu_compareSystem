package com.junyu.common.returnBean;

import java.util.HashMap;
import java.util.Map;

public class EnumInstance {
	public final static String loginType = "1";//登录type
	public final static String modifyPwdType = "2";//密码修改type
	public final static String compareType = "3";//本地比对type

	// 返回给客户的
	public final static class Return {
		public final static String RT_Success = "1"; // 比对通过
		public final static String RT_Fail = "0";// 系统判断为不同人
		public final static String RT_Not_Compare = "-1";// 未比对（无人脸，多张人脸，服务器错误，链接超时）

		public final static String RT_Source_Fail = "-2";// 多源认证接口调用结果（不为同一人，身份证号码不存在，姓名身份证不匹配）
		public final static String RT_Source_Error = "-3";// 多源认证接口调用失败
	}

	// 调用来源接口的
	public static class EReturn {
		// 成功
		public final static String RT_Success = "0"; // 成功,是同一人
		public final static String RT_Unsure = "1";// 无法判定
		public final static String RT_Fail = "2";// 不是同一人

		public final static String RT_Unqualified = "-3";// 照片质量不合格
		public final static String RT_Fail_IDPhoto = "-4";// 二代身份证照片不合格(原来是-8)
		public final static String RT_Fail_Feature = "-2";// 提取特征码失败
		public final static String RT_Fail_Feature_IdPhoto = "-6";// 提取证件照特征值失败
		public final static String RT_NotMatch_Format_IDPhoto = "-7";// 1张证件照解码失败
		public final static String RT_NotMatch_Format_Photo = "-8";// 多张抓拍照解码失败
		public final static String RT_More_Face_IDPhoto = "-9";// 1张证件照检测到多张人脸
		public final static String RT_More_Face_Photo = "-10";// 多张抓拍照检测到多张人脸

		public final static String RT_InError = "-1300";// 服务器内部错误
		public final static String RT_Timeout = "-1301";// 远程连接超时错误

		public final static String RT_NotMount = "12002";// 服务未挂载

		public final static String RT_NotMatch_Format = "-1001"; // 接口入参必填
		public final static String RT_NotMatch_Format_Json = "-1002"; // 接口入参JSON格式不正确
		public final static String RT_NotMatch_type_null = "-1003"; // 接口类型type必填
		public final static String RT_NotMatch_Service_null = "-1004"; // 接口类型type对应的服务不存在

	}

	// 多源认证接口返回码
	public final class EReturnSource {
		public final static String RT_Success = "0"; // 成功,是同一人

		public final static String RT_InError = "-1400";// 服务器内部错误
		public final static String RT_Timeout = "-1401";// 远程连接超时错误
	}

	/**
	 * 登录返回码
	 */
	public static class EReturnLogin extends EReturn {
		public final static String RT_Success = "0"; // 成功
		public final static String RT_NotMatch_Null_LoginName = "-1101"; // 登录名必填
		public final static String RT_NotMatch_Null_Password = "-1102"; // 密码必填
		public final static String RT_NotMatch_Format_Login = "-1103"; // 登录名或密码不正确
		public final static String RT_NotMatch_Format_Busable = "-1104"; // 账户已停用

		public static Map<String, String> map = new HashMap<String, String>();
		static {
			map.put(RT_Success, "成功");
			map.put(RT_NotMatch_Null_LoginName, "登录名必填");
			map.put(RT_NotMatch_Null_Password, "密码必填");
			map.put(RT_NotMatch_Format_Login, "登录名或密码不正确");
			map.put(RT_NotMatch_Format_Busable, "账户已停用");

			map.put(RT_NotMatch_Format, "接口入参必填");
			map.put(RT_NotMatch_Format_Json, "接口入参JSON格式不正确");
			map.put(RT_NotMatch_type_null, "接口类型type必填");
			map.put(RT_NotMatch_Service_null, "接口类型type对应的服务不存在");
			map.put(RT_InError, "服务器内部错误");
			map.put(RT_Timeout, "服务器连接超时");
		}
	}

	/**
	 * 密码修改返回码
	 */
	public static class EReturnModifyPwd extends EReturnLogin {
		public final static String RT_Success = "0"; // 成功
		public final static String RT_NotMatch_Null_LoginName = "-1101"; // 登录名必填
		public final static String RT_NotMatch_Null_Password = "-1102"; // 密码必填
		public final static String RT_NotMatch_Null_NewPassword = "-1203"; // 新密码必填
		public final static String RT_NotMatch_Format_Login = "-1103"; // 登录名或密码不正确
		public final static String RT_NotMatch_Format_Busable = "-1104"; // 账户已停用

		public static Map<String, String> map = new HashMap<String, String>();
		static {
			map.put(RT_Success, "成功");
			map.put(RT_NotMatch_Null_LoginName, "登录名必填");
			map.put(RT_NotMatch_Null_Password, "密码必填");
			map.put(RT_NotMatch_Null_NewPassword, "新密码必填");
			map.put(RT_NotMatch_Format_Login, "登录名或密码不正确");
			map.put(RT_NotMatch_Format_Busable, "账户已停用");

			map.put(RT_NotMatch_Format, "接口入参必填");
			map.put(RT_NotMatch_Format_Json, "接口入参JSON格式不正确");
			map.put(RT_NotMatch_type_null, "接口类型type必填");
			map.put(RT_NotMatch_Service_null, "接口类型type对应的服务不存在");
			map.put(RT_InError, "服务器内部错误");
			map.put(RT_Timeout, "服务器连接超时");
		}
	}

	/**
	 * 本地比对返回码
	 */
	public static class EReturnCompare extends EReturnModifyPwd {
		public final static String RT_Success = "0"; // 系统判断为同一人
		public final static String RT_Unsure = "1"; // 系统不能确定是否为同一人
		public final static String RT_Fail = "2"; // 系统判断为不同人

		public final static String RT_NotMatch_Null_LoginName = "-1101"; // 登录名必填
		public final static String RT_NotMatch_Null_name = "-1102"; // 姓名必填
		public final static String RT_NotMatch_Null_ID = "-1303"; // 证件号码必填
		public final static String RT_NotMatch_Null_IDPhoto = "-1304"; // 证件翻拍照必填
		public final static String RT_NotMatch_Null_UserPhoto = "-1305"; // 现场照必填

		public final static String RT_NotMatch_Format_Login = "-1306"; // 登录名不正确
		public final static String RT_NotMatch_Format_Busable = "-1310"; // 账户已停用

		public final static String RT_NotMatch_Null_ChipPhoto = "-1307"; // 芯片照格式不正确
		public final static String RT_NotMatch_Format_IDPhoto = "-1308"; // 证件翻拍照格式不正确
		public final static String RT_NotMatch_Format_UserPhoto = "-1309"; // 现场照格式不正确

		public final static String RT_Not_Face_ChipPhoto = "-1311"; // 芯片照检测人脸失败
		public final static String RT_Not_Face_IDPhoto = "-1312"; // 证件翻拍照检测人脸失败
		public final static String RT_Not_Face_UserPhoto = "-1313"; // 现场照检测人脸失败

		public final static String RT_More_Face_ChipPhoto = "-1314"; // 芯片照检测到多张人脸
		public final static String RT_More_Face_IDPhoto = "-1315"; // 证件翻拍照检测到多张人脸
		public final static String RT_More_Face_UserPhoto = "-1316"; // 现场照检测到多张人脸

		public final static String RT_InError_source = "-1321";// 多源认证接口调用失败
		public static Map<String, String> map = new HashMap<String, String>();
		static {
			map.put(RT_Success, "系统判断为同一人");
			map.put(RT_Unsure, "系统不能确定是否为同一人");
			map.put(RT_Fail, "系统判断为不同人");

			map.put(RT_NotMatch_Null_LoginName, "登录名必填");
			map.put(RT_NotMatch_Null_name, "姓名必填");
			map.put(RT_NotMatch_Null_ID, "证件号码必填");
			map.put(RT_NotMatch_Null_IDPhoto, "证件翻拍照必填");
			map.put(RT_NotMatch_Null_UserPhoto, "现场照必填");

			map.put(RT_NotMatch_Format_Login, "登录名不正确");
			map.put(RT_NotMatch_Format_Busable, "账户已停用");

			map.put(RT_NotMatch_Null_ChipPhoto, "芯片照格式不正确");
			map.put(RT_NotMatch_Format_IDPhoto, "证件翻拍照格式不正确");
			map.put(RT_NotMatch_Format_UserPhoto, "现场照格式不正确");

			map.put(RT_Not_Face_ChipPhoto, "芯片照检测人脸失败");
			map.put(RT_Not_Face_IDPhoto, "证件翻拍照检测人脸失败");
			map.put(RT_Not_Face_UserPhoto, "现场照检测人脸失败");

			map.put(RT_More_Face_ChipPhoto, "芯片照检测到多张人脸");
			map.put(RT_More_Face_IDPhoto, "证件翻拍照检测到多张人脸");
			map.put(RT_More_Face_UserPhoto, "现场照检测到多张人脸");

			map.put(RT_InError_source, "多源认证接口调用失败");

			map.put(RT_NotMatch_Format, "接口入参必填");
			map.put(RT_NotMatch_Format_Json, "接口入参JSON格式不正确");
			map.put(RT_NotMatch_type_null, "接口类型type必填");
			map.put(RT_NotMatch_Service_null, "接口类型type对应的服务不存在");
			map.put(RT_InError, "服务器内部错误");
			map.put(RT_Timeout, "服务器连接超时");
		}
	}

	// 骏聿比对返回码
	public final class EReturnJY {
		public final static String RT_Success = "0";// 成功
		public final static String RT_Unsure = "1";// 无法判定
		public final static String RT_Fail = "2";// 未通过,相似度太低
		public final static String RT_Transfer = "2000";// ESB成功将消息转发

		public final static String RT_Unqualified = "-3";// 照片质量不合格
		// 去掉了原来的-4活体检测失败
		public final static String RT_Fail_IDPhoto = "-4";// 二代身份证照片不合格(原来是-8)

		public final static String RT_NotMatch_Format_IDPhoto = "-7";// 1张证件照解码失败
		public final static String RT_NotMatch_Format_Photo = "-8";// 多张抓拍照解码失败

		public final static String RT_Fail_Feature_IdPhoto = "-6";// 提取证件照特征值失败
		public final static String RT_Fail_Feature = "-2";// 多张抓拍照提取特征码失败

		public final static String RT_More_Face_IDPhoto = "-9";// 1张证件照检测到多张人脸
		public final static String RT_More_Face_Photo = "-10";// 多张抓拍照检测到多张人脸
	}

}
