package com.thinkgem.jeesite.modules.entrust.entity;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.util.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;

public class Start {// 设置APPID/AK/SK

public static final String APP_ID = "";// 你的 App ID

public static final String API_KEY = "";// 你的 Api Key

public static final String SECRET_KEY = "";// 你的Secret

 
				
				
				public static String getAuth() {
				// 官网获取的 API Key
				String clientId = "WmlcIbeHAgfoq6Uy5KSyDOaL";
				// 官网获取的 Secret Key
				String clientSecret = "jtPol18ZnkFo9y2cKGMncPlSx1PaiMkL";
				return getAuth(clientId, clientSecret);
				}
				/**
				* 获取API访问token
				* 该token有一定的有效期，需要自行管理，当失效时需重新获取.
				* @param ak - 百度云的 API Key
				* @param sk - 百度云的 Securet Key
				* @return assess_token 示例：
				* "24.c9303e47f0729c40f2bc2be6f8f3d589.2592000.1530936208.282335-1234567"
				*/
				public static String getAuth(String ak, String sk) {
				// 获取token地址
				String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
				String getAccessTokenUrl = authHost
				// 1. grant_type为固定参数
				+ "grant_type=client_credentials"
				// 2. 官网获取的 API Key
				+ "&client_id=" + ak
				// 3. 官网获取的 Secret Key
				+ "&client_secret=" + sk;
				try {
				URL realUrl = new URL(getAccessTokenUrl);
				// 打开和URL之间的连接
				HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
				connection.setRequestMethod("POST");//百度推荐使用POST请求
				connection.connect();
				// 获取所有响应头字段
				Map<String, List<String>> map = connection.getHeaderFields();
				// 定义 BufferedReader输入流来读取URL的响应
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String result = "";
				String line;
				while ((line = in.readLine()) != null) {
				result += line;
				}
				System.err.println("result:" + result);
				JSONObject jsonObject = new JSONObject(result);
				String access_token = jsonObject.getString("access_token");
				return access_token;
				} catch (Exception e) {
				System.err.printf("获取token失败！");
				e.printStackTrace(System.err);
				}
				return null;
				}
				
				

public static String request(String httpUrl, String httpArg) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		try {
		//用java JDK自带的URL去请求
		URL url = new URL(httpUrl);
		HttpURLConnection connection = (HttpURLConnection) url
		.openConnection();
		//设置该请求的消息头
		//设置HTTP方法：POST
		connection.setRequestMethod("POST");
		//设置其Header的Content-Type参数为application/x-www-form-urlencoded
		connection.setRequestProperty("Content-Type",
		"application/x-www-form-urlencoded");
		// 填入apikey到HTTP header
		connection.setRequestProperty("apikey", "uml8HFzu2hFd8iEG2LkQGMxm");
		//将第二步获取到的token填入到HTTP header
		connection.setRequestProperty("access_token", getAuth());
		connection.setDoOutput(true);
		connection.getOutputStream().write(httpArg.getBytes("UTF-8"));
		connection.connect();
		InputStream is = connection.getInputStream();
		reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		String strRead = null;
		while ((strRead = reader.readLine()) != null) {
		sbf.append(strRead);
		sbf.append("\r\n");
		}
		reader.close();
		result = sbf.toString();
		} catch (Exception e) {
		e.printStackTrace();
		}
		return result;


}
 public static HashMap<String,String> getHashMapByJson(String jsonResult){
			HashMap map = new HashMap<String,String>();
			try {
			JSONObject jsonObject = new JSONObject(jsonResult);
			JSONObject words_result= jsonObject.getJSONObject("words_result");
			Iterator<String> it = words_result.keys();
			while (it.hasNext()){
			String key = it.next();
			JSONObject result = words_result.getJSONObject(key);
			String value=result.getString("words");
			switch (key){
			case "姓名":
			map.put("name",value);
			break;
			case "民族":
			map.put("nation",value);
			break;
			case "住址":
			map.put("address",value);
			break;
			case "公民身份号码":
			map.put("IDCard",value);
			break;
			case "出生":
			map.put("Birth",value);
			break;
			case "性别":
			map.put("sex",value);
			break;
}
}
} catch (Exception e) {
e.printStackTrace();
}
return map;
}

 
 public static HashMap<String,String> getMap(String imgPath){
	 File file = new File(imgPath);
		//进行BASE64位编码
		String imageBase = BASE64.encodeImgageToBase64(file);
		imageBase = imageBase.replaceAll("\r\n","");
		imageBase = imageBase.replaceAll("\\+","%2B");
		//百度云的文字识别接口,后面参数为获取到的token
		String httpUrl="https://aip.baidubce.com/rest/2.0/ocr/v1/idcard?access_token="+getAuth();
		String httpArg = "detect_direction=true&id_card_side=front&image="+imageBase;
		String jsonResult = request(httpUrl, httpArg);
		
		HashMap<String, String> map = getHashMapByJson(jsonResult); 
		
		
		 for (String key : map.keySet()) {
		      System.out.println(key);
		    }
		
		 for (String value : map.values()) {
		      System.out.println(value);
		    }
	 
	return map;
	 
 }
			public static void main(String[] args) {
				
			//	getMap("D:\\88.jpg");
				/*
				File file = new File("D:\\88.jpg");
				//进行BASE64位编码
				String imageBase = BASE64.encodeImgageToBase64(file);
				imageBase = imageBase.replaceAll("\r\n","");
				imageBase = imageBase.replaceAll("\\+","%2B");
				//百度云的文字识别接口,后面参数为获取到的token
				String httpUrl="https://aip.baidubce.com/rest/2.0/ocr/v1/idcard?access_token="+getAuth();
				String httpArg = "detect_direction=true&id_card_side=front&image="+imageBase;
				String jsonResult = request(httpUrl, httpArg);
				System.out.println("返回的结果--------->"+jsonResult);
				
				HashMap<String, String> map = getHashMapByJson(jsonResult);
				
				
				 for (String key : map.keySet()) {
				      System.out.println(key);
				    }
				
				 for (String value : map.values()) {
				      System.out.println(value);
				    }
				
				
				Collection<String> values=map.values();
				Iterator<String> iterator2=values.iterator();
				while (iterator2.hasNext()){
					System.out.print(iterator2.next()+", ");
					
					
				}*/
			}
			
			
			

}
