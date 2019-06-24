package com.thinkgem.jeesite.common.filter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class PhotoUploud {
	
	// 上传到D盘的图片
	public void UploudPhoto(String object,HttpServletRequest request,String toName) throws Exception{
		//toName 需要上传的路劲   object 当前文件名
		List<String>pics=new ArrayList<String>();
		String []ax=object.split("\\|");
		for (String pic : ax){
			
			if(pic.equals("")){
				
			}else{
				 pic = URLDecoder.decode(pic,"UTF-8");
				 System.out.println(pic.replace('\\', '/').substring(0,pic.length()));
				pics.add(pic.replace('\\', '/').substring(0,pic.length()));
			}
		}
		for (String pi : pics) {
			String comeName= request.getSession().getServletContext().getRealPath("/").replace('\\', '/')+pi;
			copy(comeName, toName);
		} 
	}
	
	
	

	// 上传到D盘的图片2
	public void UploudPhoto2(String object,HttpServletRequest request,String toName,String name,int d,String imgph,String idType) throws Exception{
		//toName 需要上传的路劲   object 当前文件名
		List<String>pics=new ArrayList<String>();
		String []ax=object.split("\\|");
		for (String pic : ax){  
			
			if(pic.equals("")){
				
			}else{
				 pic = URLDecoder.decode(pic,"UTF-8");
				 System.out.println(pic.replace('\\', '/').substring(9,pic.length()));
				pics.add(pic.replace('\\', '/').substring(9,pic.length()));
			}
		}
		for (String pi : pics) {
			String comeName= request.getSession().getServletContext().getRealPath("/").replace('\\', '/')+pi;
			copy2(comeName, toName,name,d,imgph,idType);
		} 
	}
	
	// 上传到D盘的图片3
		public void UploudPhoto3(String object,HttpServletRequest request,String toName,String name) throws Exception{
			//toName 需要上传的路劲   object 当前文件名
			List<String>pics=new ArrayList<String>();
			String []ax=object.split("\\|");
			for (String pic : ax){
				
				if(pic.equals("")){
					
				}else{
					 pic = URLDecoder.decode(pic,"UTF-8");
					pics.add(pic.replace('\\', '/').substring(9,pic.length()));
				}
			}
			for (String pi : pics) {
				String comeName= request.getSession().getServletContext().getRealPath("/").replace('\\', '/')+pi;
				copy3(comeName, toName,name);
			} 
		}
		
	
/*
*/	
	  public static void copy(String file1, String file2) {
		  File src=new File(file1);
		  File dst=new File(file2);
		  if(!dst.exists()){
		  dst.mkdirs();
		  }
		  InputStream in = null;
		  OutputStream out = null;
		  try {
		  in = new BufferedInputStream(new FileInputStream(src), 16 * 1024);
		  FileOutputStream f= new FileOutputStream(dst+file1.substring(file1.lastIndexOf("/"),file1.length()));//一定要加上文件名称
		  out = new BufferedOutputStream(f, 16 * 1024);
		  byte[] buffer = new byte[16 * 1024];
		  int len = 0;
		  while ((len = in.read(buffer)) > 0) {
		  out.write(buffer, 0, len);
		  }
		  } catch (Exception e) {
		  e.printStackTrace();
		  } finally {
		  if (null != in) {
		  try {
		  in.close();
		  } catch (IOException e) {
		  e.printStackTrace();
		  }
		  }
		  if (null != out) {
		  try {
		  out.close();
		  } catch (IOException e) {
		  e.printStackTrace();
		  }
		  }
		  }
		  }
	  
	  

	  public static void copy2(String file1, String file2,String name, int d,String imgph,String idType) throws Exception {
		 if(idType.equals("身份证")){
		  if(!name.contains("正")){
			  name=name+"的正";
		  }
		  	File mFile = new File(imgph);
         	if (mFile.exists() && mFile.isDirectory()){
         		List<File> mlist = new ArrayList<File>();
         		getAllFile(mFile, mlist);
         		// 已经获取了所有图片
         		
         		for (File file3 : mlist) {
         			String math=file3.getAbsolutePath();
         			math = math.replace('\\', '/');
         			math = URLDecoder.decode(math, "utf-8");  
           			if(math.contains(name)){
           				name=name.substring(0, name.indexOf("的"))+"的反";
           			}
         		}
         	}
		 }
		 
		 if(idType.contains("出生证明")){
			 name=name+"的出生证明";
		 }
		 
		 
		 if(idType.contains("户口本")){
			 name=name+"的户口本";
		 }
		 
		 
		 
		  
		  File src=new File(file1);
		  File dst=new File(file2);
		  if(!dst.exists()){
		  dst.mkdirs();
		  }
		  InputStream in = null;
		  OutputStream out = null;
		  try {
		  in = new BufferedInputStream(new FileInputStream(src), 16 * 1024);
		  FileOutputStream f= new FileOutputStream(dst+"/"+name+file1.substring(file1.lastIndexOf("."),file1.length()));//一定要加上文件名称
		  out = new BufferedOutputStream(f, 16 * 1024);
		  byte[] buffer = new byte[16 * 1024];
		  int len = 0;
		  while ((len = in.read(buffer)) > 0) {
		  out.write(buffer, 0, len);
		  }
		  } catch (Exception e) {
		  e.printStackTrace();
		  } finally {
		  if (null != in) {
		  try {
		  in.close();
		  } catch (IOException e) {
		  e.printStackTrace();
		  }
		  }
		  if (null != out) {
		  try {
		  out.close();
		  } catch (IOException e) {
		  e.printStackTrace();
		  }
		  }
		  }
		  }
	  
	  public static void copy3(String file1, String file2,String name) throws Exception {
			 name=name+"的人员合照";
			  File src=new File(file1);
			  File dst=new File(file2);
			  if(!dst.exists()){
			  dst.mkdirs();
			  }
			  InputStream in = null;
			  OutputStream out = null;
			  try {
			  in = new BufferedInputStream(new FileInputStream(src), 16 * 1024);
			  FileOutputStream f= new FileOutputStream(dst+"/"+name+file1.substring(file1.lastIndexOf("."),file1.length()));//一定要加上文件名称
			  out = new BufferedOutputStream(f, 16 * 1024);
			  byte[] buffer = new byte[16 * 1024];
			  int len = 0;
			  while ((len = in.read(buffer)) > 0) {
			  out.write(buffer, 0, len);
			  }
			  } catch (Exception e) {
			  e.printStackTrace();
			  } finally {
			  if (null != in) {
			  try {
			  in.close();
			  } catch (IOException e) {
			  e.printStackTrace();
			  }
			  }
			  if (null != out) {
			  try {
			  out.close();
			  } catch (IOException e) {
			  e.printStackTrace();
			  }
			  }
			  }
			  }
	  
	//读取D盘的图片
		 private static void getAllFile(File mFile, List<File> mlist) {
		        // 1.获取子目录
		        File[] files = mFile.listFiles();
		        // 2.判断files是否是空的 否则程序崩溃
		        if (files != null) {

		            for (File file : files) {
		                if (file.isDirectory()) {
		                    getAllFile(file, mlist);//调用递归的方式
		                } else {
		                    // 4. 添加到集合中去
		                    String fileName = file.getName();
		                    if (fileName.endsWith(".jpg") || fileName.endsWith(".png")
		                            || fileName.endsWith(".gif")||fileName.endsWith(".pdf")) {
		                        mlist.add(file);//如果是这几种图片格式就添加进去
		                    }
		                }
		            }
		        }
		    }

}
