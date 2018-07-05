package com.junyu.common.returnBean;


public class ReturnBean extends BaseReturn {

	public String code;
	public String info;
	public int similarity;
	
	public CompareReturnBean crBean1 = new CompareReturnBean();//翻拍照比对现场照
	public CompareReturnBean crBean2 = new CompareReturnBean();//翻拍照比对芯片照
	public CompareReturnBean crBean3 = new CompareReturnBean();//现场照比对芯片照
	public CompareReturnBean crBean4 = new CompareReturnBean();//多源认证接口比对结果
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getSimilarity() {
		return similarity;
	}
	public void setSimilarity(int similarity) {
		this.similarity = similarity;
	}
	public CompareReturnBean getCrBean1() {
		return crBean1;
	}
	public void setCrBean1(CompareReturnBean crBean1) {
		this.crBean1 = crBean1;
	}
	public CompareReturnBean getCrBean2() {
		return crBean2;
	}
	public void setCrBean2(CompareReturnBean crBean2) {
		this.crBean2 = crBean2;
	}
	public CompareReturnBean getCrBean3() {
		return crBean3;
	}
	public void setCrBean3(CompareReturnBean crBean3) {
		this.crBean3 = crBean3;
	}
	public CompareReturnBean getCrBean4() {
		return crBean4;
	}
	public void setCrBean4(CompareReturnBean crBean4) {
		this.crBean4 = crBean4;
	}
}
