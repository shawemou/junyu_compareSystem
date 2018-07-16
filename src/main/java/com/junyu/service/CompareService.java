package com.junyu.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.custle.sdk.Log4jUtil;
import com.custle.sdk.TestIDPhotoAuthService;
import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.junyu.common.requestBean.CacheListBean;
import com.junyu.common.requestBean.CompareBean;
import com.junyu.common.requestBean.CompleteBean;
import com.junyu.common.requestBean.VisitInfoBean;
import com.junyu.common.requestBean.WebStatBean;
import com.junyu.common.returnBean.CompareReturn;
import com.junyu.common.returnBean.CompareReturnBean;
import com.junyu.common.returnBean.EReturnCompareCode;
import com.junyu.common.returnBean.EnumInstance.EReturn;
import com.junyu.common.returnBean.EnumInstance.EReturnCompare;
import com.junyu.common.returnBean.EnumInstance.EReturnJY;
import com.junyu.common.returnBean.EnumInstance.Return;
import com.junyu.common.returnBean.Page;
import com.junyu.common.returnBean.ReturnBean;
import com.junyu.config.ReadingSetting;
import com.junyu.mapper.CacheHistoryMapper;
import com.junyu.mapper.CompareMapper;
import com.junyu.mapper.ComparePhotoMapper;
import com.junyu.mapper.UserMapper;
import com.junyu.pojo.CacheHistory;
import com.junyu.pojo.Compare;
import com.junyu.pojo.ComparePhoto;
import com.junyu.pojo.Rate;
import com.junyu.pojo.User;
import com.junyu.server.WSCompareServerThread;
import com.junyu.server.WSSourceCompareServerThread;
import com.junyu.server.Client.JYWebserviceClient;
import com.junyu.utils.Base64ImgUtil;
import com.junyu.utils.CommonUtils;
import com.junyu.utils.GUID;
import com.junyu.utils.IPUtils;
import com.sun.tools.javac.comp.Check;

@Service
public class CompareService extends BaseService<Compare> {

	private static Logger logger = LoggerFactory.getLogger(CompareService.class);

	private static int TOTAL_COUNT = 2;
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private CompareMapper compareMapper;

	@Autowired
	private ComparePhotoMapper comparePhoMapper;

	@Autowired
	private ReadingSetting readingSetting;

	@Autowired
	private CacheHistoryMapper cacheMapper;

	@Autowired
	private IPUtils ipUtils;

	/**
	 * 1,查询完成的业务
	 * 
	 * @Title: getDataList
	 * @Description: TODO
	 * @param @param completeBean
	 * @param @return
	 * @return List<Compare>
	 * @throws
	 */
	public List<Compare> getDataList(CompleteBean completeBean) {
		// 1,查询所有完成的信息
		Example example = new Example(Compare.class);
		example.setOrderByClause("createTime asc");
		example.createCriteria().andEqualTo("barCode", completeBean.getBar_code());
		List<Compare> compareList = this.compareMapper.selectByExample(example);

		List<Compare> dataList = new ArrayList<Compare>();
		// 2,去重
		if (compareList != null) {
			Map<String, Object> map_tmep = new HashMap<String, Object>();
			// 遍历第一个list
			for (Compare map : compareList) {
				if (!map_tmep.containsKey(map.getName()+"" + "||" + map.getCertid()+"" + "||" + map.getReturnCode()+"")) {
					map_tmep.put(map.getName()+"" + "||" + map.getCertid()+"" + "||" + map.getReturnCode()+"", null);
					dataList.add(map);
				}
			}
		}
		return dataList;
	}

	/**
	 * 2,调用服务
	 * 
	 * @Title: compare
	 * @Description: TODO
	 * @param @param compareBean
	 * @param @param cr
	 * @param @return
	 * @return CompareReturn
	 * @throws
	 */
	public CompareReturn compare(CompareBean compareBean, CompareReturn cr) {

		String[] arrPhoto = new String[] { compareBean.getPhoto_user() };
		String headXml = JYWebserviceClient.createInHeadXml(new GUID().toString(), JYWebserviceClient.iType_V3, JYWebserviceClient.iSubType_Compare);
		String compareDataXml = JYWebserviceClient.createInCompareDataXml(new GUID().toString(), compareBean.getName(), compareBean.getId_number(),
				compareBean.getPhoto_id(), arrPhoto);

		String compare_url = readingSetting.getCompare_url();
		String clientReturn = null;
		int iTryCount = 0;
		do {
			iTryCount++;
			clientReturn = JYWebserviceClient.executeClient(compare_url, headXml, compareDataXml);
			if (clientReturn != null) {
				break;
			}
		} while (iTryCount < TOTAL_COUNT);

		if (iTryCount > 1) {
			logger.debug("---ESB比对照片次数:" + iTryCount);
		}

		cr = JYWebserviceClient.parserCompareOutXml(JYWebserviceClient.parseXmlForData(clientReturn), cr);

		if (cr.getSuccess()) {
			int iSimilarity = cr.getSimilarity();
			int iSim = Integer.valueOf(readingSetting.getSimilarity());
			int otherISim = Integer.valueOf(readingSetting.getOther_similarity());
			if (iSimilarity >= 0) {
				if (StringUtils.equals("1", compareBean.getId_type())) {
					if (iSim >= 0 && iSim <= 1000) {
						if (iSimilarity >= iSim) {
							cr.setSuccess(true);
							cr.setReturn_code(Return.RT_Success);
							cr.setInfo("比对通过");
							Log4jUtil.log.warn("身份证，判断相似度通过");
						} else {
							cr.setSuccess(false);
							cr.setReturn_code(Return.RT_Fail);
							cr.setInfo("系统判断为不同人");
							Log4jUtil.log.warn("身份证，判断相似度未通过");
						}
					}
				} else {
					if (iSim >= 0 && iSim <= 1000) {
						if (iSimilarity >= otherISim) {
							cr.setSuccess(true);
							cr.setReturn_code(Return.RT_Success);
							cr.setInfo("比对通过");
							Log4jUtil.log.warn("其他证件，判断相似度未通过");
						} else {
							cr.setSuccess(false);
							cr.setReturn_code(Return.RT_Fail);
							cr.setInfo("系统判断为不同人");
							Log4jUtil.log.warn("其他证件，判断相似度未通过");
						}
					}
				}
			}
		}

		if (cr.getCode()!=null&&cr.getCode().equals(Return.RT_Not_Compare) && StringUtils.isNotBlank(cr.getReturn_code())) {
			if (EReturnCompareCode.map.containsKey(cr.getReturn_code())) {
				cr.setInfo(EReturnCompareCode.map.get(cr.getReturn_code()));
			}
		}
		return cr;
	}

	/**
	 * 3,直接进入最终表
	 * 
	 * @Title: saveCompare
	 * @Description: TODO
	 * @param @param compareBean
	 * @param @param cr
	 * @return void
	 * @throws
	 */
	public void saveCompare(CompareBean compareBean, CompareReturn cr) {
		String mobile = compareBean.getMobile();
		if (mobile != null && mobile.length() > 11) {
			mobile = mobile.substring(0, 11);
		}
		Compare compare = new Compare();
		compare.setBarCode(compareBean.getBar_code());
		compare.setCertid(compareBean.getId_number());
		compare.setCertidType(compareBean.getId_type());
		compare.setCode1(cr.getCode());
		compare.setCompareType(compareBean.getCompare_guid());
		compare.setGuid(CommonUtils.getUUID());
		compare.setHoldTime(new BigDecimal(new Date().getTime() - cr.getBeginDate().getTime()));
		compare.setIp(ipUtils.getIpAddr());
		compare.setMobile(mobile);
		compare.setName(compareBean.getName());
		compare.setReturnCode(cr.getReturn_code());
		compare.setReturnInfo(cr.getInfo());
		compare.setSimilarity1(new BigDecimal(cr.getSimilarity()));
		compare.setSourceCode(cr.getReturn_code());
		compare.setSourceInfo(cr.getInfo());
		compare.setUserGuid(compareBean.getUser_guid());
		compare.setUserType(compareBean.getType_guid());
		this.saveSelective(compare);

		ComparePhoto comparePhoto = new ComparePhoto();
		comparePhoto.setGuid(CommonUtils.getUUID());
		comparePhoto.setHistoryGuid(compare.getGuid());
		comparePhoto.setCreateTime(new Date());
		comparePhoto.setPhotoUser(Base64ImgUtil.fromBase64(compareBean.getPhoto_user()));
		comparePhoto.setPhotoId(Base64ImgUtil.fromBase64(compareBean.getPhoto_id()));
		this.comparePhoMapper.insertSelective(comparePhoto);
	}
	

	/**
	 * 8,webService对比完了之后对对比结果进行保存
	* @Title: saveWebServiceCompare
	* @Description: TODO
	* @param @param viBean
	* @param @param bean   
	* @return void 
	* @throws
	*/
	public void saveWebServiceCompare(VisitInfoBean viBean, ReturnBean bean) {
		Compare compare = viBean.getCompare();
		compare.setCode1(bean.getCrBean1()==null?null:bean.getCrBean1().getCode());
		compare.setCode2(bean.getCrBean2()==null?null:bean.getCrBean2().getCode());
		compare.setCode3(bean.getCrBean3()==null?null:bean.getCrBean3().getCode());
		compare.setGuid(viBean.getGuid());
		compare.setHoldTime(viBean.getBeginDate()==null?null:(new BigDecimal(new Date().getTime() - viBean.getBeginDate().getTime())));
		compare.setReturnCode(bean.getCode());
		compare.setReturnInfo(bean.getInfo());
		compare.setSimilarity1(new BigDecimal(bean.getCrBean1()==null?-1:bean.getCrBean1().getSimilarity()));
		compare.setSimilarity2(new BigDecimal(bean.getCrBean2()==null?-1:bean.getCrBean3().getSimilarity()));
		compare.setSimilarity3(new BigDecimal(bean.getCrBean3()==null?-1:bean.getCrBean3().getSimilarity()));
		compare.setSourceCode(bean.getCrBean4()==null?null:bean.getCrBean4().getCode());
		compare.setSourceInfo(bean.getCrBean4()==null?null:bean.getCrBean4().getInfo());
		compare.setUserGuid(viBean.getUser()== null ?"": viBean.getUser().getGuid());
		compare.setUserType(viBean.getUser()== null ?"": viBean.getUser().getTypeGuid());
		this.saveSelective(compare);
		
		ComparePhoto comparePhoto = viBean.getComparePhoto();
		comparePhoto.setGuid(CommonUtils.getUUID());
		comparePhoto.setHistoryGuid(viBean.getGuid());
		this.comparePhoMapper.insertSelective(comparePhoto);
	}

	/**
	 * 4,将数据迁移到最终表中
	 * 
	 * @Title: fromCacheToCompare
	 * @Description: TODO
	 * @param @param cacheListBean
	 * @return void
	 * @throws
	 */
	public void fromCacheToCompare(CacheListBean cacheListBean) {
		CacheHistory cache = new CacheHistory();
		cache.setBarCode(cacheListBean.getBar_code());
		cache.setEtype("1");
		cache.setCompareType(cacheListBean.getCompare_guid());
		//非市中心用户-->加userguid
		User user = this.userMapper.selectByPrimaryKey(cacheListBean.getUser_guid());
		if(!StringUtils.equals("000", user.getTypeGuid())){
			cache.setUserGuid(user.getGuid());
		}
		List<CacheHistory> cacheList = this.cacheMapper.select(cache);
		
		if (CollectionUtils.isNotEmpty(cacheList)) {
			// 1,基本内容搬家
			this.compareMapper.movedate(cacheListBean.getBar_code(),cacheListBean);

			// 2,依赖的表搬家
			this.comparePhoMapper.movedate(cacheListBean.getBar_code());

			Example example = new Example(CacheHistory.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("etype", "1");
			criteria.andEqualTo("barCode", cacheListBean.getBar_code());
			cache.setEtype("2");
			this.cacheMapper.updateByExampleSelective(cache, example);
		}
	}

	/**
	 * 5,统计信息
	 * 
	 * @Title: query
	 * @Description: TODO
	 * @param @param page
	 * @param @param statBean
	 * @param @return
	 * @return Page<Compare>
	 * @throws
	 */
	public Page<Compare> query(Page<Compare> page, WebStatBean statBean) {
		int startNum = NumberUtils.toInt(statBean.getStart(), 0);
		int pageSize = NumberUtils.toInt(statBean.getLength(), 10);
		int startPage = startNum/pageSize+1;

		PageHelper.startPage(startPage, pageSize);
		
		Example example = new Example(Compare.class);
		example.setOrderByClause("createTime asc");
		Criteria criteria = example.createCriteria();
		if (statBean.getBeginDate() != null) {
			criteria.andGreaterThanOrEqualTo("createTime", statBean.getBeginDate());
		}
		if (statBean.getEndDate() != null) {
			criteria.andLessThanOrEqualTo("createTime", statBean.getEndDate());
		}
		if (StringUtils.isNoneBlank(statBean.getTypeGuid())) {
			criteria.andEqualTo("compareType", statBean.getTypeGuid()); }
		if (StringUtils.isNoneBlank(statBean.getBarCode())) {
			criteria.andEqualTo("barCode", statBean.getBarCode());
		}
		if (StringUtils.isNoneBlank(statBean.getReturnCode())) {
			if(StringUtils.equals("0", statBean.getReturnCode())){
				criteria.andEqualTo("returnCode", "0");
			}else{
				criteria.andNotEqualTo("returnCode", "0");
			}
		}
		PageInfo<Compare> pageInfo = new PageInfo<Compare>(this.compareMapper.selectByExample(example));

		page.setData(pageInfo.getList());
		page.setItemCount(pageInfo.getTotal() + "");
		page.setPage_no(pageInfo.getPageNum() + "");
		page.setSuccess(true);
		page.setInfo("成功");
		return page;
	}

	/**
	 * 6,人证一致使用率统计
	 * 
	 * @Title: queryRate
	 * @Description: TODO
	 * @param @param page
	 * @param @param statBean
	 * @param @return
	 * @return Page<Rate>
	 * @throws
	 */
	public Page<Rate> queryRate(Page<Rate> page, WebStatBean statBean) {
		int startNum = NumberUtils.toInt(statBean.getStart(), 0);
		int pageSize = NumberUtils.toInt(statBean.getLength(), 10);
		Integer end = startNum + pageSize;
		
		List<Rate> rateList = this.compareMapper.selectRateList(statBean, startNum, end);
		page.setData(rateList);
		page.setItemCount(this.compareMapper.selectTotalCount(statBean) + "");
		page.setPage_no(statBean.getPage_no());
		page.setSuccess(true);
		page.setInfo("成功");
		return page;
	}

	/**
	 * 7,webService调用的接口
	 * 
	 * @Title: client
	 * @Description: TODO
	 * @param @param viBean
	 * @param @param bean
	 * @return void
	 * @throws
	 */
	public void client(VisitInfoBean viBean, ReturnBean bean) {

		// 本地比对服务
		localCompare(viBean, bean);

		if (EReturnCompare.RT_Success.equals(bean.getCode())) {
			// 有芯片照时，多线程调用多源认证接口
			if (StringUtils.isNotBlank(viBean.getCompareBean().getChipPhoto())) {// 有芯片照
				new WSSourceCompareServerThread(viBean).start();
			} else {
				// 没有芯片照时，顺序调用多源认证接口
				sourceCompare(viBean, bean);
			}
		}
	}

	public void localCompare(VisitInfoBean viBean, ReturnBean bean) {
		// 翻拍照比对现场照
		Future<CompareReturnBean> fs1 = null;
		ExecutorService es1 = Executors.newSingleThreadExecutor();
		if (StringUtils.isNotBlank(viBean.getCompareBean().getPhoto_id()) && StringUtils.isNotBlank(viBean.getCompareBean().getPhoto_user())) {
			logger.info("翻拍照比对现场照比对开始");
			fs1 = es1.submit(new WSCompareServerThread(viBean.getCompareBean().getPhoto_id(), viBean.getCompareBean().getPhoto_user()));
		}

		// 翻拍照比对芯片照
		Future<CompareReturnBean> fs2 = null;
		ExecutorService es2 = Executors.newSingleThreadExecutor();
		if (StringUtils.isNotBlank(viBean.getCompareBean().getPhoto_id()) && StringUtils.isNotBlank(viBean.getCompareBean().getChipPhoto())) {
			Log4jUtil.log.warn("翻拍照比对芯片照比对开始");
			fs2 = es2.submit(new WSCompareServerThread(viBean.getCompareBean().getPhoto_id(), viBean.getCompareBean().getChipPhoto()));
		}

		// 现场照比对芯片照
		Future<CompareReturnBean> fs3 = null;
		ExecutorService es3 = Executors.newSingleThreadExecutor();
		if (StringUtils.isNotBlank(viBean.getCompareBean().getPhoto_user()) && StringUtils.isNotBlank(viBean.getCompareBean().getChipPhoto())) {
			Log4jUtil.log.warn("现场照比对芯片照比对开始");
			fs3 = es3.submit(new WSCompareServerThread(viBean.getCompareBean().getPhoto_user(), viBean.getCompareBean().getChipPhoto()));
		}

		CompareReturnBean rbean1 = null;
		if (fs1 != null) {
			try {
				rbean1 = fs1.get();
				bean.setCrBean1(rbean1);
			} catch (Exception e) {
				Log4jUtil.log.error("翻拍照比对现场照比较线程1异常", e);
			} finally {
				es1.shutdown();
			}
		}

		CompareReturnBean rbean2 = null;
		if (fs2 != null) {
			try {
				rbean2 = fs2.get();
				bean.setCrBean2(rbean2);
			} catch (Exception e) {
				Log4jUtil.log.error("翻拍照比对芯片照比较线程2异常", e);
			} finally {
				es2.shutdown();
			}
		}

		CompareReturnBean rbean3 = null;
		if (fs3 != null) {
			try {
				rbean3 = fs3.get();
				bean.setCrBean3(rbean3);
			} catch (Exception e) {
				Log4jUtil.log.error("现场照比对芯片照比较线程3异常", e);
			} finally {
				es3.shutdown();
			}
		}

		/**
		 * 三照齐全时,fs2翻拍照比对芯片照,必须通过，其他两个比对任一通过即为通过
		 */
		if (fs1 != null && fs2 != null && fs3 != null) {
			String code2 = resetCode2(rbean2);
			if (!code2.equals(EReturnCompare.RT_Success)) {
				bean.setCode(code2);
				return;
			} else {
				String code1 = resetCode1(rbean1);
				String code3 = resetCode3(rbean3);
				if (!code1.equals(EReturnCompare.RT_Success) && !code3.equals(EReturnCompare.RT_Success)) {
					bean.setCode(code1);
					return;
				} else {
					bean.setCode(EReturnCompare.RT_Success);
					return;
				}
			}
		} else if (fs1 != null) {
			// 翻拍照比对现场照
			String code1 = resetCode1(rbean1);
			bean.setCode(code1);
			return;
		} else if (fs3 != null) {
			// 现场照比对芯片照
			String code3 = resetCode3(rbean3);
			bean.setCode(code3);
			return;
		}
	}

	private String resetCode1(CompareReturnBean rbean1) {
		String code = resetCode(rbean1, readingSetting.getSimilarity1());
		if (!code.equals(EReturnCompare.RT_Success)) {
			if (code.equals(EReturn.RT_Timeout) || code.equals(EReturn.RT_InError) || code.equals(EReturnCompare.RT_Fail)) {
				return code;
			} else if (EReturnJY.RT_NotMatch_Format_IDPhoto.equals(code)) {
				return EReturnCompare.RT_NotMatch_Format_IDPhoto;
			} else if (EReturnJY.RT_NotMatch_Format_Photo.equals(code)) {
				return EReturnCompare.RT_NotMatch_Format_UserPhoto;
			} else if (EReturnJY.RT_Fail_Feature_IdPhoto.equals(code)) {
				return EReturnCompare.RT_Not_Face_IDPhoto;
			} else if (EReturnJY.RT_Fail_Feature.equals(code)) {
				return EReturnCompare.RT_Not_Face_UserPhoto;
			} else if (EReturnJY.RT_More_Face_IDPhoto.equals(code)) {
				return EReturnCompare.RT_More_Face_IDPhoto;
			} else if (EReturnJY.RT_More_Face_Photo.equals(code)) {
				return EReturnCompare.RT_More_Face_UserPhoto;
			} else {
				return EReturn.RT_InError;
			}
		}
		return code;
	}

	private String resetCode2(CompareReturnBean rbean2) {
		String code = resetCode(rbean2, readingSetting.getSimilarity2());
		if (!code.equals(EReturnCompare.RT_Success)) {
			if (code.equals(EReturn.RT_Timeout) || code.equals(EReturn.RT_InError) || code.equals(EReturnCompare.RT_Fail)) {
				return code;
			} else if (EReturnJY.RT_NotMatch_Format_IDPhoto.equals(code)) {
				return EReturnCompare.RT_NotMatch_Format_IDPhoto;
			} else if (EReturnJY.RT_NotMatch_Format_Photo.equals(code)) {
				return EReturnCompare.RT_NotMatch_Null_ChipPhoto;
			} else if (EReturnJY.RT_Fail_Feature_IdPhoto.equals(code)) {
				return EReturnCompare.RT_Not_Face_IDPhoto;
			} else if (EReturnJY.RT_Fail_Feature.equals(code)) {
				return EReturnCompare.RT_Not_Face_ChipPhoto;
			} else if (EReturnJY.RT_More_Face_IDPhoto.equals(code)) {
				return EReturnCompare.RT_More_Face_IDPhoto;
			} else if (EReturnJY.RT_More_Face_Photo.equals(code)) {
				return EReturnCompare.RT_More_Face_ChipPhoto;
			} else {
				return EReturn.RT_InError;
			}
		}
		return code;
	}

	private String resetCode3(CompareReturnBean rbean3) {
		String code = resetCode(rbean3, readingSetting.getSimilarity3());
		if (!code.equals(EReturnCompare.RT_Success)) {
			if (code.equals(EReturn.RT_Timeout) || code.equals(EReturn.RT_InError) || code.equals(EReturnCompare.RT_Fail)) {
				return code;
			} else if (EReturnJY.RT_NotMatch_Format_IDPhoto.equals(code)) {
				return EReturnCompare.RT_NotMatch_Format_UserPhoto;
			} else if (EReturnJY.RT_NotMatch_Format_Photo.equals(code)) {
				return EReturnCompare.RT_NotMatch_Null_ChipPhoto;
			} else if (EReturnJY.RT_Fail_Feature_IdPhoto.equals(code)) {
				return EReturnCompare.RT_Not_Face_UserPhoto;
			} else if (EReturnJY.RT_Fail_Feature.equals(code)) {
				return EReturnCompare.RT_Not_Face_ChipPhoto;
			} else if (EReturnJY.RT_More_Face_IDPhoto.equals(code)) {
				return EReturnCompare.RT_More_Face_UserPhoto;
			} else if (EReturnJY.RT_More_Face_Photo.equals(code)) {
				return EReturnCompare.RT_More_Face_ChipPhoto;
			} else {
				return EReturn.RT_InError;
			}
		}
		return code;
	}

	/**
	 * 相似度比较
	 * 
	 * @param ri
	 * @param cbean
	 * @return
	 */
	private static String resetCode(CompareReturnBean rbean, String similarity) {
		if (rbean == null) {
			return EReturn.RT_InError;
		} else {
			if (rbean.getCode() == null || EReturn.RT_InError.equals(rbean.getCode())) {
				return EReturn.RT_InError;
			} else if (EReturn.RT_Timeout.equals(rbean.getCode())) {
				return EReturn.RT_Timeout;
			} else if (EReturnJY.RT_Success.equals(rbean.getCode()) || EReturnJY.RT_Unsure.equals(rbean.getCode()) || EReturnJY.RT_Fail.equals(rbean.getCode())) {
				if (rbean.getSimilarity() >= NumberUtils.toInt(similarity, -1)) {
					return EReturnCompare.RT_Success;
				} else {
					return EReturnCompare.RT_Fail;
				}

				// 左侧、右侧检测人脸失败，左侧、右侧检测到多张人脸
			} else if (EReturnJY.RT_Fail_Feature_IdPhoto.equals(rbean.getCode()) || EReturnJY.RT_Fail_Feature.equals(rbean.getCode())
					|| EReturnJY.RT_NotMatch_Format_IDPhoto.equals(rbean.getCode()) || EReturnJY.RT_NotMatch_Format_Photo.equals(rbean.getCode())
					|| EReturnJY.RT_More_Face_IDPhoto.equals(rbean.getCode()) || EReturnJY.RT_More_Face_Photo.equals(rbean.getCode())) {
				return rbean.getCode();
			} else {
				return EReturn.RT_InError;
			}
		}
	}

	/**
	 * 多源认证接口服务
	 * 
	 * @param viBean
	 * @param bean
	 */
	public void sourceCompare(VisitInfoBean viBean, ReturnBean bean) {
		CompareReturnBean crBean4 = bean.getCrBean4();
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
		}
	}

}
