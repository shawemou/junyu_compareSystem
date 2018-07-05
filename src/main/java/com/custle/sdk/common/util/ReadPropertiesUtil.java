package com.custle.sdk.common.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;

public class ReadPropertiesUtil {
	private static ReadPropertiesUtil singleton;

	private HashMap<String, Hashtable<String, String>> properties;

	private ReadPropertiesUtil() {
	}

	public static ReadPropertiesUtil getInstance() {
		if (singleton == null) {
			singleton = new ReadPropertiesUtil();
		}
		return singleton;
	}

	/**
	 * 初始化配置文件
	 */
	private Hashtable<String, String> initConfig(String fileName) {
		ResourceBundle rb = ResourceBundle.getBundle(fileName,
				Locale.SIMPLIFIED_CHINESE);
		Hashtable<String, String> confAttris = new Hashtable<String, String>();
		try {
			Enumeration iter = rb.getKeys();
			while (iter.hasMoreElements()) {
				String key = (String) iter.nextElement();
				confAttris.put(key, new String(rb.getString(key)
						.getBytes("GBK")));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return confAttris;
	}

	/**
	 * 查询配置文件值
	 * 
	 * @param key
	 * @return
	 */
	public String getValueFromFile(String fileName, String key) {
		if (properties == null) {
			properties = new HashMap<String, Hashtable<String, String>>();
		}
		if (!properties.containsKey(fileName)) {
			properties.put(fileName, initConfig(fileName));
		}
		return properties.get(fileName).get(key);
	}

}
