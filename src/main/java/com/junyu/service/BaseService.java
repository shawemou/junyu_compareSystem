package com.junyu.service;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.junyu.pojo.BasePojo;

public abstract class BaseService<T extends BasePojo> {
	//Spring4的泛型注入
	@Autowired
	private Mapper<T> mapper;
	
	public Class<T> clazz;
	
	{
		ParameterizedType type =  (ParameterizedType) this.getClass().getGenericSuperclass();
		this.clazz = (Class<T>) type.getActualTypeArguments()[0];
	}

	/**
	 * 1,根据主键id查询数据
	 */
	public T queryById(String id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	/**
	 * 2,查询所有数据
	 */
	public List<T> queryAll() {
		return this.mapper.select(null);
	}

	/**
	 * 3,根据条件查询一条数据
	 */
	public T queryOne(T record) {
		return this.mapper.selectOne(record);
	}

	/**
	 * 4,根据条件查询多条数据
	 */
	public List<T> queryListByWhere(T record) {
		return this.mapper.select(record);
	}

	/**
	 * 5,根据条件分页查询多条数据
	 */
	public PageInfo<T> queryPageListByWhere(T record, Integer pageNum,
			Integer pageSize) {
		// 设置分页参数
		PageHelper.startPage(pageNum, pageSize);
		List<T> list = this.mapper.select(record);
		return new PageInfo<T>(list);
	}
	
	/**
	 * 5.1分页查询并排序
	 */
	public PageInfo<T> queryPageListOrderBy(String orderByClause, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Example example = new Example(clazz);
		example.setOrderByClause(orderByClause);
		List<T> list = this.mapper.selectByExample(example);
		return new PageInfo<>(list);
	}

	/**
	 * 6,新增数据
	 */
	public Integer save(T record){
		record.setCreateTime(new Date());
		return this.mapper.insert(record);
	}
	
	/**
	 *7, 新增数据(选择不为null的字段,插入数据库
	 */
	public Integer saveSelective(T record){
		record.setCreateTime(new Date());
		return this.mapper.insertSelective(record);
	}
	
	/**
	 * 8,根据主键更新数据
	 */
	public Integer Update(T record){
		return this.mapper.updateByPrimaryKey(record);
	}
	
	/**
	 * 9, 更新数据（选择不为null的字段，进行操作）
	 * @param record
	 * @return
	 */
	public Integer updateSelective(T record){
		record.setCreateTime(null);
		return this.mapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 10,根据主键删除数据
	 */
	public Integer deleteById(T key){
		return  this.mapper.delete(key);
	}
	
	/**
	 * 11,批量删除
	 */
	public Integer deleteByIds(List<Object> ids, String property){
		Example example = new Example(clazz);
		example.createCriteria().andIn(property, ids);
		return this.mapper.deleteByExample(example);
	}
	
	/**
	 * 12,根据条件删除数据
	 */
	public Integer  deleteByWhere(T record){
		return this.mapper.delete(record);
	}


}
