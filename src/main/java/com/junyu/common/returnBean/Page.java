package com.junyu.common.returnBean;

import java.util.List;

public class Page<T> {

	private String itemCount;

	private String page_no;

	private List<T> data;

	private Boolean success;

	private String info;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getItemCount() {
		return itemCount;
	}

	public void setItemCount(String itemCount) {
		this.itemCount = itemCount;
	}

	public String getPage_no() {
		return page_no;
	}

	public void setPage_no(String page_no) {
		this.page_no = page_no;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public Page() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Page(String itemCount, String page_no, List<T> data) {
		super();
		this.itemCount = itemCount;
		this.page_no = page_no;
		this.data = data;
	}

	@Override
	public String toString() {
		return "Page [itemCount=" + itemCount + ", page_no=" + page_no + ", data=" + data + ", success=" + success + ", info=" + info + "]";
	}

}
