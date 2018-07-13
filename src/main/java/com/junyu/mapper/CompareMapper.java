package com.junyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.abel533.mapper.Mapper;
import com.junyu.common.requestBean.CacheListBean;
import com.junyu.common.requestBean.WebStatBean;
import com.junyu.pojo.Compare;
import com.junyu.pojo.Rate;

public interface CompareMapper extends Mapper<Compare> {

	public void movedate(@Param("barCode") String barCode, @Param("cacheListBean") CacheListBean cacheListBean);

	public List<Rate> selectRateList(@Param("statBean") WebStatBean statBean, @Param("begin") Integer begin, @Param("end") Integer end);

	public int selectTotalCount(WebStatBean statBean);

}