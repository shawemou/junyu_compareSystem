package com.custle.sdk;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4jUtil {

	static {
		InputStream in = Log4jUtil.class.getClassLoader().getResourceAsStream("log4j.properties");
		Properties prop = new Properties();
		try {
			prop.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PropertyConfigurator.configure(prop);
		// BasicConfigurator.configure();// 默认配置

		// 在J2EE应用使用Log4j，必须先在启动服务时加载Log4j的配置文件进行初始化，可以在web.xml中进行

	}
	public static org.apache.log4j.Logger log = Logger.getLogger(Log4jUtil.class);

	public static void main(String[] args) {
		log.info("Info Message!");
		log.debug("debug message!");
		log.error("error message");
		log.warn("warn message");
	}
}