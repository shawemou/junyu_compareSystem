package com.junyu.mapper;

import com.github.abel533.mapper.Mapper;
import com.junyu.pojo.ComparePhoto;

public interface ComparePhotoMapper extends Mapper<ComparePhoto> {

	public void movedate(String bar_code);
}