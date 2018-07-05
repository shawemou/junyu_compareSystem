package com.junyu.server;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.ctc.wstx.util.StringUtil;
import com.custle.sdk.Log4jUtil;
import com.custle.sdk.TestIDPhotoAuthService;
import com.junyu.common.requestBean.VisitInfoBean;
import com.junyu.common.returnBean.CompareReturnBean;
import com.junyu.pojo.Compare;
import com.junyu.service.CompareService;
import com.junyu.utils.SpringBeanUtils;
import com.sun.tools.javac.comp.Check;

/**
 * @ClassName: WSSourceCompareServerThread
 * @Description: 多线程调用多源接口，保存比对结果
 * @author lulinlin
 * @date 2018-4-19 下午04:41:18
 *
 */
public class WSSourceCompareServerThread extends Thread {

	private VisitInfoBean viBean;

	public WSSourceCompareServerThread() {
	}

	public WSSourceCompareServerThread(VisitInfoBean viBean) {
		this.viBean = viBean;
	}

	public void run() {
		CompareReturnBean crBean4 = new CompareReturnBean();// 多源认证接口比对结果
		String resultString = TestIDPhotoAuthService.runQryIDPhoto(viBean.getCompareBean().getName(), viBean.getCompareBean().getId_number(), viBean
				.getCompareBean().getPhoto_id());
		if (StringUtils.isNotBlank(resultString)) {
			try {
				JSONObject json = new JSONObject(resultString);
				if (json.has("Result") && StringUtils.isNotBlank(json.getString("Result"))) {
					crBean4.setCode(json.getString("Result"));
				}
				if (json.has("Return") && StringUtils.isNotBlank(json.getString("Return"))) {
					crBean4.setInfo(json.getString("Return"));
				}
			} catch (JSONException e) {
				Log4jUtil.log.error("解析多源认证接口返回json异常", e);
			}

			if (StringUtils.isNotBlank(crBean4.getCode()) || StringUtils.isNotBlank(crBean4.getInfo())) {
				CompareService compareService = (CompareService) SpringBeanUtils.getBean(CompareService.class);
				Compare record = new Compare();
				record.setSourceCode(crBean4.getCode());
				record.setSourceInfo(crBean4.getInfo());
				record.setGuid(viBean.getGuid());
				compareService.updateSelective(record);
			}
		}
	}

	public VisitInfoBean getViBean() {
		return viBean;
	}

	public void setViBean(VisitInfoBean viBean) {
		this.viBean = viBean;
	}
}
