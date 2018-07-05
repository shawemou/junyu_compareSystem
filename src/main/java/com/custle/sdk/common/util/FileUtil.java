package com.custle.sdk.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {

	/**
	 * 读取文件内容
	 * 
	 * @param f
	 * @return
	 * @throws IOException
	 */
	public static String readFileToString(File f) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(f));
		StringBuffer sb = new StringBuffer();
		try {
			String str = null;
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (br != null)
				br.close();
		}
		return sb.toString();
	}

	/**
	 * 读文件
	 * 
	 * @param f
	 * @return
	 * @throws IOException
	 */
	public static byte[] readFileToByte(File f) throws IOException {
		byte buff[] = new byte[(int) f.length()];
		FileInputStream in = null;
		try {
			in = new FileInputStream(f);
			in.read(buff);
		} catch (IOException e) {
			throw e;
		} finally {
			if (in != null)
				in.close();
		}
		return buff;
	}

	/**
	 * 写文件
	 * 
	 * @param fileName
	 * @param data
	 * @throws IOException
	 */
	public static void writeFile(String fileName, byte data[])
			throws IOException {
		File f = new File(fileName);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(f);
			out.write(data);
		} catch (IOException e) {
			throw e;
		} finally {
			if (out != null)
				out.close();
		}
	}

}
