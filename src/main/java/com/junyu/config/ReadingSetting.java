package com.junyu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ReadingSetting {

	@Value("${ocr.url}")
	private  String service_url;
	
	@Value("${ocr.connection_timeout}")
	private String connection_timeout;
	
	@Value("${ocr.so_timeout}")
	private String so_timeout;
	
	@Value("${ocr.license}")
	private String license;
	
	@Value("${ocr.secret_key}")
	private String secret_key;

	@Value("${compare.url}")
	private String compare_url;
	
	@Value("${compare.similarity}")
	private String similarity;
	
	@Value("${compare.similarity}")
	private String other_similarity;

	@Value("${jdbc.url}")
	private String con_url;
	
	@Value("${jdbc.conn_user}")
	private String conn_user;
	
	@Value("${jdbc.conn_pwd}")
	private String conn_pwd;

	@Value("${project.version}")
	private String version;
	
	@Value("${compare.similarity1}")
	private String similarity1;
	
	@Value("${compare.similarity2}")
	private String similarity2;
	
	@Value("${compare.similarity3}")
	private String  similarity3;

	public ReadingSetting() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReadingSetting(String service_url, String connection_timeout, String so_timeout, String license, String secret_key, String compare_url,
			String similarity, String other_similarity, String con_url, String conn_user, String conn_pwd, String version) {
		super();
		this.service_url = service_url;
		this.connection_timeout = connection_timeout;
		this.so_timeout = so_timeout;
		this.license = license;
		this.secret_key = secret_key;
		this.compare_url = compare_url;
		this.similarity = similarity;
		this.other_similarity = other_similarity;
		this.con_url = con_url;
		this.conn_user = conn_user;
		this.conn_pwd = conn_pwd;
		this.version = version;
	}

	
	public String getSimilarity1() {
		return similarity1;
	}

	public void setSimilarity1(String similarity1) {
		this.similarity1 = similarity1;
	}

	public String getSimilarity2() {
		return similarity2;
	}

	public void setSimilarity2(String similarity2) {
		this.similarity2 = similarity2;
	}

	public String getSimilarity3() {
		return similarity3;
	}

	public void setSimilarity3(String similarity3) {
		this.similarity3 = similarity3;
	}

	public String getService_url() {
		return service_url;
	}

	public void setService_url(String service_url) {
		this.service_url = service_url;
	}

	public String getConnection_timeout() {
		return connection_timeout;
	}

	public void setConnection_timeout(String connection_timeout) {
		this.connection_timeout = connection_timeout;
	}

	public String getSo_timeout() {
		return so_timeout;
	}

	public void setSo_timeout(String so_timeout) {
		this.so_timeout = so_timeout;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getSecret_key() {
		return secret_key;
	}

	public void setSecret_key(String secret_key) {
		this.secret_key = secret_key;
	}

	public String getCompare_url() {
		return compare_url;
	}

	public void setCompare_url(String compare_url) {
		this.compare_url = compare_url;
	}

	public String getSimilarity() {
		return similarity;
	}

	public void setSimilarity(String similarity) {
		this.similarity = similarity;
	}

	public String getOther_similarity() {
		return other_similarity;
	}

	public void setOther_similarity(String other_similarity) {
		this.other_similarity = other_similarity;
	}

	public String getCon_url() {
		return con_url;
	}

	public void setCon_url(String con_url) {
		this.con_url = con_url;
	}

	public String getConn_user() {
		return conn_user;
	}

	public void setConn_user(String conn_user) {
		this.conn_user = conn_user;
	}

	public String getConn_pwd() {
		return conn_pwd;
	}

	public void setConn_pwd(String conn_pwd) {
		this.conn_pwd = conn_pwd;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "ReadingSetting [service_url=" + service_url + ", connection_timeout=" + connection_timeout + ", so_timeout=" + so_timeout + ", license="
				+ license + ", secret_key=" + secret_key + ", compare_url=" + compare_url + ", similarity=" + similarity + ", other_similarity="
				+ other_similarity + ", con_url=" + con_url + ", conn_user=" + conn_user + ", conn_pwd=" + conn_pwd + ", version=" + version + "]";
	}
	
}
