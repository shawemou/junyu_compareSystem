package com.junyu.pojo;

public class Rate {
	private String typeId;
	private String totalCount;
	private String rightCount;

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getRightCount() {
		return rightCount;
	}

	public void setRightCount(String rightCount) {
		this.rightCount = rightCount;
	}

	@Override
	public String toString() {
		return "Rate [typeId=" + typeId + ", totalCount=" + totalCount + ", rightCount=" + rightCount + "]";
	}

}
