package com.junyu.utils;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import sun.misc.BASE64Decoder;

import com.custle.sdk.Log4jUtil;

public class Base64ImgUtil {
	public static void main(String[] args) {
		// 测试从图片文件转换为Base64编码
		//System.out.println(GetImageStr("D:\\dat\\下载.jpg"));
//		String strImg = GetImageStr("e://Koala.jpg");
		// 测试从Base64编码转换为图片文件
//		GenerateImage(strImg, "e://t.jpg");

	}

	/**
	 * 根据图片路径对图片进行BASE64编码
	 * 
	 * @param imgFilePath图片文件所在路径
	 * @return 图片的base64编码字符串
	 */
//	public static String GetImageStr(String imgFilePath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
//		byte[] data = null;
//		InputStream in = null;
//		// 读取图片字节数组
//		try {
//			in = new FileInputStream(imgFilePath);
//			// data = new byte[in.available()];
//			// data=new byte[1024];
//
//			int count = 0;
//			while (count == 0) {
//				count = in.available();
//			}
//			data = new byte[count];
//			in.read(data);
//
//			// in.read(data);
//			in.close();
//		} catch (IOException e) {
////			e.printStackTrace();
//			Log4jUtil.log.error("根据文件路径获取文件失败");
//			return null;
//		} finally {
//			try {
//				if (in != null) {
//					in.close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		// 对字节数组Base64编码
//		return BASE64Encoder.encode(data);// 返回Base64编码过的字节数组字符??
//	}

	/**
	 * 对BASE64字符串解码并保存成文件
	 * 
	 * @param imgStr
	 *            base64编码的字符串
	 * @param imgFilePath
	 *            保存图片的文件路径
	 * @return 成功或失败
	 */
	public static boolean GenerateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图??
		if (imgStr == null) // 图像数据为空
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		OutputStream out = null;
		try {
			// Base64解码
			byte[] bytes = decoder.decodeBuffer(imgStr);

			// for (int i = 0; i < bytes.length; ++i) {
			// if (bytes[i] < 0) {//
			// // 调整异常数据
			// bytes[i] += 256;
			// }
			// }

			// 生成jpeg图片
			out = new FileOutputStream(imgFilePath);
			out.write(bytes);
			out.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 对BASE64字符串解码并保存成字节数组
	 * @param imgStr  base64编码的字符串
	 * @return 返回Base64解码后的字节数组
	 */
	public static byte[] GenerateImageByte(String imgStr) {// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] bytes = decoder.decodeBuffer(imgStr);
			for (int i = 0, j = bytes.length; i < j; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param p_Str
	 *            Base64编码的字符窜
	 * @return byte[]类型的照片信息
	 * @throws IOException
	 */
	public static byte[] fromBase64(String p_Str) {
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			return decoder.decodeBuffer(p_Str);
		} catch (IOException e) {
			Log4jUtil.log.error("Base64编码照片转byte时出现异常", e);
		}
		return null;
	}

	/**
	 * @param bytes
	 *            byte[]类型的照片信息
	 * @return Java Image对象。可以直接在java程序中绘制到UI界面
	 */
	public static Image getImage(byte[] bytes) {
		Image img = null;
		InputStream isPhoto = null;
		try {
			isPhoto = new ByteArrayInputStream(bytes);
			img = ImageIO.read(isPhoto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (isPhoto != null) {
					isPhoto.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		return img;
	}
}
