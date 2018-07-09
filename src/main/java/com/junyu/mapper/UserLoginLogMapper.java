package com.junyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.abel533.mapper.Mapper;
import com.junyu.pojo.UserLoginLog;

public interface UserLoginLogMapper extends Mapper<UserLoginLog> {

	List<Object> queryOnline(@Param("offlineTime")String offlineTime);
}