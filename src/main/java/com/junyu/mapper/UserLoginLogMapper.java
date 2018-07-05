package com.junyu.mapper;

import java.util.Date;
import java.util.List;

import com.github.abel533.mapper.Mapper;
import com.junyu.pojo.UserLoginLog;

public interface UserLoginLogMapper extends Mapper<UserLoginLog> {

	List<Object> queryOnline();
}