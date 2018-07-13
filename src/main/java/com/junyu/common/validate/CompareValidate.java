package com.junyu.common.validate;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.custle.sdk.Log4jUtil;
import com.junyu.common.requestBean.VisitInfoBean;
import com.junyu.common.returnBean.EnumInstance.EReturn;
import com.junyu.common.returnBean.EnumInstance.EReturnCompare;
import com.junyu.common.returnBean.ReturnBean;
import com.junyu.pojo.Compare;
import com.junyu.pojo.ComparePhoto;
import com.junyu.pojo.ServiceDic;
import com.junyu.pojo.UserLoginLog;
import com.junyu.pojo.UserType;
import com.junyu.service.ServiceDicService;
import com.junyu.service.UserService;
import com.junyu.service.UserTypeService;
import com.junyu.utils.Base64ImgUtil;
import com.junyu.utils.SpringBeanUtils;

/**
 * @ClassName: CompareValidate
 * @Description: 比对信息验证
 * @author lulinlin
 * @date 2017-11-24 下午02:14:22
 *
 */
public class CompareValidate {

	public static boolean vali(String strJson, VisitInfoBean viBean, ReturnBean bean) {
		boolean boo = true;
		try {
			UserTypeService userTypeService = (UserTypeService) SpringBeanUtils.getBean(UserTypeService.class);
			ServiceDicService serviceDicService = (ServiceDicService) SpringBeanUtils.getBean(ServiceDicService.class);
			UserService userService = (UserService) SpringBeanUtils.getBean(UserService.class);
			
			

			Compare compareBean = new Compare();
			UserLoginLog loginLog = new UserLoginLog();
			ComparePhoto comparePhoto = new ComparePhoto();
			JSONObject json = new JSONObject(strJson);
			if (!json.has("loginName") || StringUtils.isBlank(json.getString("loginName"))) {
				bean.setCode(EReturnCompare.RT_NotMatch_Null_LoginName);
				boo = false;
			} else {
				loginLog.setLoginName(json.getString("loginName"));
			}

			if (!json.has("name") || StringUtils.isBlank(json.getString("name"))) {
				bean.setCode(EReturnCompare.RT_NotMatch_Null_name);
				boo = false;
			} else {
				compareBean.setName(json.getString("name"));
			}

			if (!json.has("certid") || StringUtils.isBlank(json.getString("certid"))) {
				bean.setCode(EReturnCompare.RT_NotMatch_Null_ID);
				boo = false;
			} else {
				compareBean.setCertid(json.getString("certid"));
			}
			
			

			// 证件翻拍照必填
			if (!json.has("strIDPhoto") || StringUtils.isBlank(json.getString("strIDPhoto"))) {
				bean.setCode(EReturnCompare.RT_NotMatch_Null_IDPhoto);
				boo = false;
			} else {
				// 翻拍照
				comparePhoto.setPhotoId(Base64ImgUtil.fromBase64(json.getString("strIDPhoto")));
				viBean.getCompareBean().setPhoto_id(json.getString("strIDPhoto"));
			}

			// 现场照必填
			if (!json.has("strUserPhoto") || StringUtils.isBlank(json.getString("strUserPhoto"))) {
				bean.setCode(EReturnCompare.RT_NotMatch_Null_UserPhoto);
				boo = false;
			} else {
				comparePhoto.setPhotoUser(Base64ImgUtil.fromBase64(json.getString("strUserPhoto")));
				viBean.getCompareBean().setPhoto_user(json.getString("strIDPhoto"));
			}

			// 芯片照不必填
			if (json.has("strChipPhoto") && !StringUtils.isBlank(json.getString("strChipPhoto"))) {
				comparePhoto.setPhotoChip(Base64ImgUtil.fromBase64(json.getString("strChipPhoto")));
				viBean.getCompareBean().setChipPhoto(json.getString("strChipPhoto"));
			}

			if (json.has("sex")) {
				compareBean.setSex(json.getString("sex"));
			}
			if (json.has("birthday")) {
				compareBean.setBirthday(json.getString("birthday"));
			}
			if (json.has("fork")) {
				compareBean.setFork(json.getString("fork"));
			}
			if (json.has("address")) {
				compareBean.setAddress(json.getString("address"));
			}
			if (json.has("issue_authority")) {
				compareBean.setIssueAuthority(json.getString("issue_authority"));
			}
			if (json.has("vaild_priod")) {
				compareBean.setValidPriod(json.getString("vaild_priod"));
			}
			compareBean.setIp(viBean.getIp());
			viBean.setCompare(compareBean);
			viBean.setLoginLog(loginLog);
			viBean.setComparePhoto(comparePhoto);
			
			userService.validateUser(viBean, bean);
			//1,如果为中心用户
			if (StringUtils.equals(viBean.getUser().getTypeGuid(), "000")) {
				// 1.1有合同号
				if (json.has("barCode") && StringUtils.isNotBlank(json.getString("barCode"))) {
					compareBean.setBarCode(json.getString("barCode"));
					// 1.1.1 代理机构名称不能为空,通过代理机构名称查询机构的id
					if (!json.has("compareName") || StringUtils.isBlank(json.getString("compareName"))) {
						bean.setCode(EReturnCompare.RT_NotMatch_Null_CompareName);
						boo = false;
					} else {
						UserType record = new UserType();
						record.setName(json.getString("compareName"));
						compareBean.setCompareType(userTypeService.queryOne(record).getGuid());
					}
					// 1.2无合同号
				} else {
					compareBean.setBarFlag("1");
					compareBean.setCompareType("000");
					// 1.2.1 机构名称不能为空
					if (!json.has("orgName") || StringUtils.isBlank(json.getString("orgName"))) {
						bean.setCode(EReturnCompare.RT_NotMatch_Null_OrgName);
						boo = false;
					} else {
						compareBean.setOrgName(json.getString("orgName"));
					}
					// 1.2.2 业务代理类型名称不能为空 , 通过业务代理名称去查询业务代理类型的id
					if (!json.has("serviceName") || StringUtils.isBlank(json.getString("serviceName"))) {
						bean.setCode(EReturnCompare.RT_NotMatch_Null_ServiceName);
						boo = false;
					} else {
						ServiceDic record = new ServiceDic();
						record.setName(json.getString("serviceName"));
						compareBean.setServiceId(serviceDicService.queryOne(record).getGuid());
					}
					// 1.2.3受理编号不能为空
					if (!json.has("serviceNumber") || StringUtils.isBlank(json.getString("serviceNumber"))) {
						bean.setCode(EReturnCompare.RT_NotMatch_Null_OrgName);
						boo = false;
					} else {
						compareBean.setServiceNumber(json.getString("serviceNumber"));
					}
				}
			// 2 非市中心用户必须有合同号,,合同的类型就是用户的类型
			} else {
				compareBean.setBarCode(json.getString("barCode"));
				if (!json.has("barCode") || StringUtils.isBlank(json.getString("barCode"))) {
					bean.setCode(EReturnCompare.RT_NotMatch_Null_BarCode);
					boo = false;
				} else {
					compareBean.setCompareType(viBean.getUser().getTypeGuid());
				}
			}

		} catch (JSONException e) {
			Log4jUtil.log.error("入参strJson解析失败", e);
			bean.setCode(EReturn.RT_NotMatch_Format_Json);
			return false;
		}
		return boo;
	}
}
