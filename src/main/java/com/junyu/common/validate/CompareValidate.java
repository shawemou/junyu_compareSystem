package com.junyu.common.validate;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.custle.sdk.Log4jUtil;
import com.junyu.common.requestBean.CompareBean;
import com.junyu.common.requestBean.VisitInfoBean;
import com.junyu.common.returnBean.EnumInstance.EReturn;
import com.junyu.common.returnBean.EnumInstance.EReturnCompare;
import com.junyu.common.returnBean.ReturnBean;
import com.junyu.pojo.Compare;
import com.junyu.pojo.ComparePhoto;
import com.junyu.pojo.UserLoginLog;
import com.junyu.utils.Base64ImgUtil;
import com.sun.tools.javac.comp.Check;

/**
 * @ClassName: CompareValidate 
 * @Description: 比对信息验证 
 * @author lulinlin
 * @date 2017-11-24 下午02:14:22 
 *
 */
public class CompareValidate {

	public static boolean vali(String strJson, VisitInfoBean viBean, ReturnBean bean){
		boolean boo = true;
		try {
			Compare compareBean = new Compare();
			UserLoginLog loginLog = new UserLoginLog();
			ComparePhoto comparePhoto = new ComparePhoto();
			JSONObject json = new JSONObject(strJson);
			if( !json.has("loginName") || StringUtils.isBlank(json.getString("loginName"))){
				bean.setCode(EReturnCompare.RT_NotMatch_Null_LoginName);
				boo = false;
			}else{
				loginLog.setLoginName(json.getString("loginName"));
			}
			
			if( !json.has("name") || StringUtils.isBlank(json.getString("name"))){
				bean.setCode(EReturnCompare.RT_NotMatch_Null_name);
				boo = false;
			}else{
				compareBean.setName(json.getString("name"));
			}
			
			if( !json.has("certid") || StringUtils.isBlank(json.getString("certid"))){
				bean.setCode(EReturnCompare.RT_NotMatch_Null_ID);
				boo = false;
			}else{
				compareBean.setCertid(json.getString("certid"));
			}
			/**
			 * 
			 */
			if( !json.has("compareType") || StringUtils.isBlank(json.getString("compareType"))){
				bean.setCode(EReturnCompare.RT_NotMatch_Format);
				boo = false;
			}else{
				compareBean.setCompareType(json.getString("compareType"));
			}
			
			//证件翻拍照必填
			if( !json.has("strIDPhoto") || StringUtils.isBlank(json.getString("strIDPhoto"))){
				bean.setCode(EReturnCompare.RT_NotMatch_Null_IDPhoto);
				boo = false;
			}else{
				//翻拍照
				comparePhoto.setPhotoId(Base64ImgUtil.fromBase64(json.getString("strIDPhoto")));
				viBean.getCompareBean().setPhoto_id(json.getString("strIDPhoto"));
			}
			
			//现场照必填
			if( !json.has("strUserPhoto") || StringUtils.isBlank(json.getString("strUserPhoto"))){
				bean.setCode(EReturnCompare.RT_NotMatch_Null_UserPhoto);
				boo = false;
			}else{
				comparePhoto.setPhotoUser(Base64ImgUtil.fromBase64(json.getString("strUserPhoto")));
				viBean.getCompareBean().setPhoto_user(json.getString("strIDPhoto"));
			}
			
			//芯片照不必填
			if( json.has("strChipPhoto") && !StringUtils.isBlank(json.getString("strChipPhoto"))){
				comparePhoto.setPhotoChip(Base64ImgUtil.fromBase64(json.getString("strChipPhoto")));
				viBean.getCompareBean().setChipPhoto(json.getString("strChipPhoto"));
			}
			
			if( json.has("sex")){
				compareBean.setSex(json.getString("sex"));
			}
			if( json.has("birthday")){
				compareBean.setBirthday(json.getString("birthday"));
			}
			if( json.has("fork")){
				compareBean.setFork(json.getString("fork"));
			}
			if( json.has("address")){
				compareBean.setAddress(json.getString("address"));
			}
			if( json.has("issue_authority")){
				compareBean.setIssueAuthority(json.getString("issue_authority"));
			}
			if( json.has("vaild_priod")){
				compareBean.setValidPriod(json.getString("vaild_priod"));
			}
			compareBean.setIp(viBean.getIp());
			viBean.setCompare(compareBean);
			viBean.setLoginLog(loginLog);
			viBean.setComparePhoto(comparePhoto);
			
		} catch (JSONException e) {
			Log4jUtil.log.error("入参strJson解析失败", e);
			bean.setCode(EReturn.RT_NotMatch_Format_Json);
			return false;
		}
		return boo;
	}
}
