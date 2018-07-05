package com.junyu.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;
import com.junyu.common.requestBean.CacheListBean;
import com.junyu.common.requestBean.CompareBean;
import com.junyu.common.requestBean.CompleteBean;
import com.junyu.common.returnBean.CompareReturn;
import com.junyu.mapper.CacheHistoryMapper;
import com.junyu.mapper.CacheHistoryPhotoMapper;
import com.junyu.pojo.CacheHistory;
import com.junyu.pojo.CacheHistoryPhoto;
import com.junyu.utils.Base64ImgUtil;
import com.junyu.utils.CommonUtils;

@Service
public class CacheService extends BaseService<CacheHistory> {

	@Autowired
	private CacheHistoryMapper cacheHistoryMapper;

	@Autowired
	private CacheHistoryPhotoMapper cachePhoMapper;

	public List<CacheHistory> getDataList(CompleteBean completeBean) {
		// 1,查询所有的暂存区的信息
		Example example = new Example(CacheHistory.class);
		example.setOrderByClause("createTime asc");
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("etype", "1");
		criteria.andEqualTo("barCode", completeBean.getBar_code());
		List<CacheHistory> cacheList = this.cacheHistoryMapper.selectByExample(example);

		List<CacheHistory> dataList = new ArrayList<CacheHistory>();
		if (cacheList != null) {
			// 2,遍历
			Map<String, Object> map_tmep = new HashMap<String, Object>();
			for (CacheHistory map : cacheList) {
				if (!map_tmep.containsKey(map.getName().toString() + "||" + map.getIdNumber().toString() + "||" + map.getReturnCode().toString())) {
					map_tmep.put(map.getName().toString() + "||" + map.getIdNumber().toString() + "||" + map.getReturnCode().toString(), null);
					dataList.add(map);
				}
			}
		}
		return dataList;
	}

	/**
	 * 2,保存到暂存区
	 * 
	 * @Title: saveCacheLog
	 * @Description: TODO
	 * @param @param compareBean
	 * @param @param cr
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean saveCacheHistory(CompareBean compareBean, CompareReturn cr) {
		String mobile = compareBean.getMobile();
		if (mobile != null && mobile.length() > 11) {
			mobile = mobile.substring(0, 11);
		}
		CacheHistory cache = new CacheHistory();
		cache.setBarCode(compareBean.getBar_code());
		cache.setCode(cr.getCode());
		cache.setCompareType(compareBean.getCompare_guid());
		cache.setEtype("1");
		cache.setGuid(CommonUtils.getUUID());
		cache.setHoldTime(new BigDecimal(new Date().getTime() - cr.getBeginDate().getTime()));
		cache.setIdNumber(compareBean.getId_number());
		cache.setIdType(compareBean.getId_type());
		cache.setMobile(compareBean.getMobile());
		cache.setName(compareBean.getName());
		cache.setReturnCode(cr.getReturn_code());
		cache.setReturnInfo(cr.getInfo());
		cache.setSimilarity(new BigDecimal(cr.getSimilarity()));
		// 多元认证接口和success一致
		cache.setSourceCode(cr.getReturn_code());
		cache.setSourceInfo(cr.getInfo());
		cache.setSuccess(cr.getReturn_code());
		cache.setUserGuid(compareBean.getUser_guid());
		cache.setUserType(compareBean.getType_guid());
		this.save(cache);

		CacheHistoryPhoto cachePho = new CacheHistoryPhoto();
		cachePho.setGuid(CommonUtils.getUUID());
		cachePho.setCreateTime(new Date());
		cachePho.setHistoryGuid(cache.getGuid());
		cachePho.setPhoId(Base64ImgUtil.fromBase64(compareBean.getPhoto_id()));
		cachePho.setPhoUser(Base64ImgUtil.fromBase64(compareBean.getPhoto_user()));

		this.cachePhoMapper.insertSelective(cachePho);

		return true;
	}

	/**
	 * 3,查询暂存记录
	 * 
	 * @Title: getCachList
	 * @Description: TODO
	 * @param @param cacheListBean
	 * @param @return
	 * @return List<CacheHistory>
	 * @throws
	 */
	public List<CacheHistory> getCachList(CacheListBean cacheListBean) {
		Example example = new Example(CacheHistory.class);
		example.setOrderByClause("createTime asc");
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("barCode", cacheListBean.getBar_code());
		List<CacheHistory> cacheList = this.cacheHistoryMapper.selectByExample(example);
		return cacheList;
	}

	/**
	 * 4,根据id批量删除
	 * 
	 * @Title: deleteByGuids
	 * @Description: TODO
	 * @param @param data
	 * @param @param string
	 * @param @return
	 * @return Integer
	 * @throws
	 */
	public Integer deleteByGuids(List<Object> data, String string) {
		Example example = new Example(CacheHistory.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("etype", "1");
		criteria.andIn("guid", data);
		CacheHistory record = new CacheHistory();
		record.setEtype("0");
		return this.cacheHistoryMapper.updateByExampleSelective(record, example);
	}
}
