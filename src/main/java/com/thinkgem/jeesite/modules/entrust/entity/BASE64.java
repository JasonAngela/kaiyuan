package com.thinkgem.jeesite.modules.entrust.entity;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
public class BASE64 {
	/**
	* 将本地图片进行Base64位编码
	*
	* @param imgUrl
	* 图片的url路径，如e:\\123.png
	* @return
	*/
	@SuppressWarnings("restriction")
	public static String encodeImgageToBase64(File imageFile) {
	// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	// 其进行Base64编码处理
	byte[] data = null;
	// 读取图片字节数组
	try {
	InputStream in = new FileInputStream(imageFile);
	data = new byte[in.available()];
	in.read(data);
	in.close();
	} catch (Exception e) {
	e.printStackTrace();
	}
	// 对字节数组Base64编码
	BASE64Encoder encoder = new BASE64Encoder();
	return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}

	

	
	
	static BASE64Encoder encoder = new sun.misc.BASE64Encoder(); 
static String getImageBinary() throws IOException{
	File f = new File("D://22.jpg");
	BufferedImage bi;
	bi = ImageIO.read(f);
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	ImageIO.write(bi, "jpg", baos);
	byte[] bytes = baos.toByteArray();
	return encoder.encodeBuffer(bytes).trim();
}




public static void main(String[] args) throws Exception {
		String dd=encodeImgageToBase64(new File("D://22.jpg"));
		System.out.println(dd);
		
}
	



}
