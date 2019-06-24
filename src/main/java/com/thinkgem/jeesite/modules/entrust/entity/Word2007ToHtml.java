package com.thinkgem.jeesite.modules.entrust.entity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;


import java.io.File;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;

/**
* <p>Title:Word2007ToHtml </p>
* <p>Company: </p> 
* @author 
* @date 2018年4月11日下午2:21:30
* Description: 
*/
public class Word2007ToHtml { // 将word格式的文件转换为pdf格式

private static final int wdFormatPDF = 17;// PDF 格式    
	public static void wordToPDF(String startFile, String overFile){    
 
		ActiveXComponent app = null;  
		Dispatch doc = null;  
		
		/*File file = new File(overFile);
		File dir = file.getParentFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
			try {      
				app = new ActiveXComponent("Word.Application");      
			app.setProperty("Visible", new Variant(false));  
			Dispatch docs = app.getProperty("Documents").toDispatch();    
 
			/*//转换前的文件路径
			startFile = "D:\\2.doc";
			//转换后的文件路劲
			 overFile ="D\\2.pdf";*/
 
			doc = Dispatch.call(docs,  "Open" , startFile).toDispatch();  
			File tofile = new File(overFile);      
			if (tofile.exists()) {      
				tofile.delete();      
			}      
			Dispatch.call(doc,"SaveAs", overFile, wdFormatPDF);      
		} catch (Exception e) {      
			System.out.println(e.getMessage());      
		} finally {  
			Dispatch.call(doc,"Close",false);  
			if (app != null)      
				app.invoke("Quit", new Variant[] {});      
		}  
		//结束后关闭进程   
		ComThread.Release();
	}
 
	
	
	
	//打印
	 public static void printByWord(String path){
	       
	        System.out.println("开始打印");
	        ComThread.InitSTA();
	        ActiveXComponent word=new ActiveXComponent("Word.Application");
	        Dispatch doc=null;
	        Dispatch.put(word, "Visible", new Variant(false));
	        Dispatch docs=word.getProperty("Documents").toDispatch();
	        try {
	            doc=Dispatch.call(docs, "Open", path).toDispatch();
	            Dispatch.call(doc, "PrintOut");//打印
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("打印失败");
	        }finally{
	            try {
	                if(doc!=null){
	                    Dispatch.call(doc, "Close",new Variant(0));
	                }
	            } catch (Exception e2) {
	                e2.printStackTrace();
	            }
	            //释放资源
	            ComThread.Release();
	        }
	    }
	 
	 private static final int wdFormatPDF2 = 17; // PDF 格式
	
	   public static void wordToPDF2(String sfileName, String toFileName) {
	   	  System.out.println("启动 Word...");
	      long start = System.currentTimeMillis();
	      ActiveXComponent app = null;
	      Dispatch doc = null;
	        try {
	            app = new ActiveXComponent("Word.Application");
           		app.setProperty("Visible", new Variant(false));
           		Dispatch docs = app.getProperty("Documents").toDispatch();
           		doc = Dispatch.call(docs, "Open", sfileName).toDispatch();
            	System.out.println("打开文档..." + sfileName);
            	System.out.println("转换文档到 PDF..." + toFileName);
	            File tofile = new File(toFileName);
            	if (tofile.exists()) {
	                  tofile.delete();
	              }
	            Dispatch.call(doc, "SaveAs", toFileName, // FileName
	                     wdFormatPDF);
            	long end = System.currentTimeMillis();
            	System.out.println("转换完成..用时：" + (end - start) + "ms.");
	  
	         } catch (Exception e) {
	              System.out.println("========Error:文档转换失败：" + e.getMessage());
	          } finally {
	              Dispatch.call(doc, "Close", false);
	              System.out.println("关闭文档");
	              if (app != null)
	                  app.invoke("Quit", new Variant[] {});
        }
	         // 如果没有这句话,winword.exe进程将不会关闭
	        ComThread.Release();
     }

	  
	    /**
	     *
	     * @param fileName
	     *            生成pdf文件
	     * @param imagesPath
	     *            需要转换的图片路径的数组
	     */
	   
	  
	    public static void imagesToPdf(String FILEPATH,String fileName, String imagesPath) {
	    	

	      
	        try {
	            fileName = FILEPATH+fileName+".pdf";
	            File file = new File(fileName);
	            
	            // 第一步：创建一个document对象。
	            Document document = new Document();
	            document.setMargins(0, 0, 0, 0);
	            // 第二步：
	            // 创建一个PdfWriter实例，
	            PdfWriter.getInstance(document, new FileOutputStream(file));
	            // 第三步：打开文档。
	            document.open();
	            // 第四步：在文档中增加图片。
	            File files = new File(imagesPath);
	            String[] images = files.list();
	            int len = images.length;

	            for (int i = 0; i < len; i++)
	            {
	                if (images[i].toLowerCase().endsWith(".bmp")
	                        || images[i].toLowerCase().endsWith(".jpg")
	                        || images[i].toLowerCase().endsWith(".jpeg")
	                        || images[i].toLowerCase().endsWith(".gif")
	                        || images[i].toLowerCase().endsWith(".png")) {
	                    String temp = imagesPath + "\\" + images[i];
	                    Image img = Image.getInstance(temp);
	                    img.setAlignment(Image.ALIGN_CENTER);
	                    // 根据图片大小设置页面，一定要先设置页面，再newPage（），否则无效
	                    document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
	                    document.newPage();
	                    document.add(img);
	                }
	            }
	            // 第五步：关闭文档。
	            document.close();
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }

	    
	    

	    public static void imagesToPdf2(String FILEPATH,String fileName, String imagesPath,String imagesPath2) {
	    	

	      
	        try {
	            fileName = FILEPATH+fileName+".pdf";
	            File file = new File(fileName);
	            
	            // 第一步：创建一个document对象。
	            Document document = new Document();
	            document.setMargins(0, 0, 0, 0);
	            // 第二步：
	            // 创建一个PdfWriter实例，
	            PdfWriter.getInstance(document, new FileOutputStream(file));
	            // 第三步：打开文档。
	            document.open();
	            // 第四步：在文档中增加图片。
	          
	                    Image img = Image.getInstance(imagesPath);
	                    Image img2 = Image.getInstance(imagesPath2);
	                    img.setAlignment(Image.ALIGN_CENTER);
	                    img.setAlignment(Image.ALIGN_CENTER);
	                    
	                    
	                    // 根据图片大小设置页面，一定要先设置页面，再newPage（），否则无效
	                    document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
	                    document.setPageSize(new Rectangle(img2.getWidth(), img2.getHeight()));
	                    document.newPage();
	                   document.add(img);
	                   document.add(img2);
	                   
	            // 第五步：关闭文档。
	            document.close();
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
	    

	    public static boolean imgToPdf(String imgFilePath, String pdfFilePath)throws IOException {
	    	File file=new File(pdfFilePath);
	    	if(!file.exists()){
	    	Document document = new Document();
	    	FileOutputStream fos = null;
	    	fos = new FileOutputStream(pdfFilePath);
	    	
	    	
	    	try {
	    	PdfWriter writer = 	PdfWriter.getInstance(document, fos);
	    	
	    	// 添加PDF文档的某些信息，比如作者，主题等等
	    	document.addAuthor("arui");
	    	document.addSubject("test pdf.");
	    	// 设置文档的大小
	    	document.setPageSize(PageSize.A4);
	    	// 打开文档
	    	document.open();
	    	
	    	/*// 读取一个图片
	    	Image image = Image.getInstance(imgFilePath);
	    	image.scaleAbsolute(508, 372);
	    	//设置图片的绝对位置
	    	image.setAbsolutePosition(39, 80);
	    	document.add(image);
	    	*/
	    	
	    	// 读取一个图片
	    	Image image = Image.getInstance(imgFilePath);
	    	//设置图片的绝对位置
	    	image.scaleAbsolute(518, 702);
	    	image.setAbsolutePosition(39,60);
	    	document.add(image);
	    /*	
	    	Image image2 = Image.getInstance("d://22.jpg");
	    	image2.scaleAbsolute(285, 192);
	    	image2.setAbsolutePosition(300, 575);
	    	document.add(image2);
	    	Image image3 = Image.getInstance("d://22.jpg");
	    	image3.scaleAbsolute(285, 192);
	    	image3.setAbsolutePosition(5,162);
	    	document.add(image3);
	    	Image image4 = Image.getInstance("d://22.jpg");
	    	image4.scaleAbsolute(285, 192);
	    	image4.setAbsolutePosition(300,162);
	    	document.add(image4);
	    	document.newPage();
	    	*/
	    	//BaseFont bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
	    	
	    	
	    	/*Image image4 = Image.getInstance("d://22.jpg");
	    	image4.scaleAbsolute(285, 192);
	    	image4.setAbsolutePosition(300,175);
	    	document.add(image4);
	    	
	    	document.newPage();
	    	
	    	Image image5 = Image.getInstance("d://22.jpg");
	    	image5.setAlignment(Image.MIDDLE);
	    	document.add(image5);*/
	    	
	    	/*Font headFont = new Font(bfChinese, 10, Font.NORMAL);// 设置字体大小
	    	 Paragraph title = new Paragraph("你好，Pdf！", " ");
	    	 document.newPage();
	    	// 读取一个图片
	    	Image image2 = Image.getInstance("d://22.jpg");
	    	image2.scaleAbsolute(303, 202);
	    	image2.setAlignment(Image.LEFT);
	    	document.add(image2);
	    	
	    	//设置图片的绝对位置
	    	//image.setAbsolutePosition(0, 0);
	    	//image.scaleAbsolute(100, 400);
	    	 */
	    
	    	// 插入一个图片
	    	
	    	} catch (DocumentException de) {
	    	System.out.println(de.getMessage());
	    	} catch (IOException ioe) {
	    	System.out.println(ioe.getMessage());
	    	}
	    	document.close();
	    	fos.flush();
	    	fos.close();
	    	return true;
	    	}else{
	    	return false;
	    	}
	    	}
	  
	    
	    
	    public static boolean imgToPdf2(String imgFilePath, String pdfFilePath,EntrustRegister entrustRegister,	List<Strs>strs1,List<Strs>strs2,List<Strs>strs3,List<Strs>strs4)throws IOException {
	    	String toName = "D:" + File.separator + "information" + File.separator + "DNA" + File.separator + entrustRegister.getCode() + File.separator + "idPic" + File.separator+"IDpic.pdf";
	    	File file=new File(toName);
	    	String code=entrustRegister.getCaseCode();
	    	file.delete();
	    	if(!file.exists()){
	    	Document document = new Document();
	    	FileOutputStream fos = null;
	    	fos = new FileOutputStream(toName);
	    	int cd=1;
	    	try {
	    		PdfWriter writer = 	PdfWriter.getInstance(document, fos);
		    	
		    	// 添加PDF文档的某些信息，比如作者，主题等等
		    	document.addAuthor("arui");
		    	document.addSubject("test pdf.");
		    	// 设置文档的大小
		    	document.setPageSize(PageSize.A4);
		    	// 打开文档
		    	document.open();
		    	PdfContentByte cb = writer.getDirectContent();
		    	BaseFont bf=BaseFont.createFont("C:/WINDOWS/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);   
		    
	    	//strs1身份证
	    	if(strs1.size()==1){
		    	// 写入一段文字
	    		
	    		cb.beginText();
		    	cb.setFontAndSize(bf, 12);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, code, 60,783, 0);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, strs1.get(0).getD()+"/p"+cd++, 60,773, 0);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, strs1.get(0).getD()+"/p"+cd++, 352,773, 0);
		    	cb.endText();
		    	// 读取一个图片
		    	Image image = Image.getInstance(strs1.get(0).getB());
		    	image.scaleAbsolute(285, 192);
		    	//设置图片的绝对位置
		    	image.setAbsolutePosition(5, 575);
		    	document.add(image);
		    	Image image2 = Image.getInstance(strs1.get(0).getC());
		    	image2.scaleAbsolute(285, 192);
		    	image2.setAbsolutePosition(300, 575);
		    	document.add(image2);
	    	}
	    	if(strs1.size()==2){
	    		cb.beginText();
		    	cb.setFontAndSize(bf, 12);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, code, 60,783, 0);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, strs1.get(0).getD()+"/p"+cd++, 60,773, 0);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, strs1.get(0).getD()+"/p"+cd++, 352,773, 0);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, code, 60,369, 0);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, strs1.get(1).getD()+"/p"+cd++, 60,359, 0);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, strs1.get(1).getD()+"/p"+cd++, 352,359, 0);
		    	cb.endText();
		    	// 读取一个图片
		    	Image image = Image.getInstance(strs1.get(0).getB());
		    	image.scaleAbsolute(285, 192);
		    	//设置图片的绝对位置
		    	image.setAbsolutePosition(5, 575);
		    	document.add(image);
		    	
		    	Image image2 = Image.getInstance(strs1.get(0).getC());
		    	image2.scaleAbsolute(285, 192);
		    	image2.setAbsolutePosition(300, 575);
		    	document.add(image2);
		    	Image image3 = Image.getInstance(strs1.get(1).getB());
		    	image3.scaleAbsolute(285, 192);
		    	image3.setAbsolutePosition(5,162);
		    	document.add(image3);
		    	Image image4 = Image.getInstance(strs1.get(1).getC());
		    	image4.scaleAbsolute(285, 192);
		    	image4.setAbsolutePosition(300,162);
		    	document.add(image4);
	    	}
	    	if(strs1.size()==3){
	    		cb.beginText();
		    	cb.setFontAndSize(bf, 12);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, code, 60,783, 0);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, strs1.get(0).getD()+"/p"+cd++, 70,773, 0);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, strs1.get(0).getD()+"/p"+cd++, 352,773, 0);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, code, 60,369, 0);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, strs1.get(1).getD()+"/p"+cd++, 70,359, 0);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, strs1.get(1).getD()+"/p"+cd++, 352,359, 0);
		    	cb.endText();
		    	// 读取一个图片
		    	Image image = Image.getInstance(strs1.get(0).getB());
		    	image.scaleAbsolute(285, 192);
		    	//设置图片的绝对位置
		    	image.setAbsolutePosition(5, 575);
		    	document.add(image);
		    	Image image2 = Image.getInstance(strs1.get(0).getC());
		    	image2.scaleAbsolute(285, 192);
		    	image2.setAbsolutePosition(300, 575);
		    	document.add(image2);
		    	Image image3 = Image.getInstance(strs1.get(1).getB());
		    	image3.scaleAbsolute(285, 192);
		    	image3.setAbsolutePosition(5,162);
		    	document.add(image3);
		    	Image image4 = Image.getInstance(strs1.get(1).getC());
		    	image4.scaleAbsolute(285, 192);
		    	image4.setAbsolutePosition(300,162);
		    	document.add(image4);
	    		document.newPage();
	    		cb.beginText();
		    	cb.setFontAndSize(bf, 12);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, code, 60,783, 0);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, strs1.get(2).getD()+"/p"+cd++, 70,773, 0);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, strs1.get(2).getD()+"/p"+cd++, 352,773, 0);
		    	cb.endText();
		    	// 读取一个图片
		    	Image image5 = Image.getInstance(strs1.get(2).getB());
		    	image5.scaleAbsolute(285, 192);
		    	//设置图片的绝对位置
		    	image5.setAbsolutePosition(5, 575);
		    	document.add(image5);
		    	Image image6 = Image.getInstance(strs1.get(2).getC());
		    	image6.scaleAbsolute(285, 192);
		    	image6.setAbsolutePosition(300,575);
		    	document.add(image6);
	    		//111  new page
	    	}
	    	if(strs1.size()==4){
	    		// 写入一段文字
	    		cb.beginText();
		    	cb.setFontAndSize(bf, 12);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, code, 60,783, 0);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, strs1.get(0).getD()+"/p"+cd++, 70,773, 0);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, strs1.get(0).getD()+"/p"+cd++, 352,773, 0);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, code, 60,369, 0);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, strs1.get(1).getD()+"/p"+cd++, 70,359, 0);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, strs1.get(1).getD()+"/p"+cd++, 352,359, 0);
		    	cb.endText();
	    		// 读取一个图片
		    	Image image = Image.getInstance(strs1.get(0).getB());
		    	image.scaleAbsolute(285, 192);
		    	//设置图片的绝对位置
		    	image.setAbsolutePosition(5, 575);
		    	document.add(image);
		    	Image image2 = Image.getInstance(strs1.get(0).getC());
		    	image2.scaleAbsolute(285, 192);
		    	image2.setAbsolutePosition(300, 575);
		    	document.add(image2);
		    	Image image3 = Image.getInstance(strs1.get(1).getB());
		    	image3.scaleAbsolute(285, 192);
		    	image3.setAbsolutePosition(5,162);
		    	document.add(image3);
		    	Image image4 = Image.getInstance(strs1.get(1).getC());
		    	image4.scaleAbsolute(285, 192);
		    	image4.setAbsolutePosition(300,162);
		    	document.add(image4);
	    		document.newPage();
	    		cb.beginText();
		    	cb.setFontAndSize(bf, 12);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, code, 60,783, 0);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, strs1.get(2).getD()+"/p"+cd++, 70,773, 0);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, strs1.get(2).getD()+"/p"+cd++, 352,773, 0);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, code, 60,369, 0);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, strs1.get(3).getD()+"/p"+cd++, 70,359, 0);
		    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, strs1.get(3).getD()+"/p"+cd++, 352,359, 0);
		    	cb.endText();
		    	// 读取一个图片
		    	Image image5 = Image.getInstance(strs1.get(2).getB());
		    	image5.scaleAbsolute(285, 192);
		    	//设置图片的绝对位置
		    	image5.setAbsolutePosition(5, 575);
		    	document.add(image5);
		    	Image image6 = Image.getInstance(strs1.get(2).getC());
		    	image6.scaleAbsolute(285, 192);
		    	image6.setAbsolutePosition(300,575);
		    	document.add(image6);
		    	Image image7 = Image.getInstance(strs1.get(3).getB());
		    	image7.scaleAbsolute(285, 192);
		    	image7.setAbsolutePosition(5,162);
		    	document.add(image3);
		    	Image image8 = Image.getInstance(strs1.get(3).getC());
		    	image8.scaleAbsolute(285, 192);
		    	image8.setAbsolutePosition(300,162);
		    	document.add(image8);
	    	}
	    	
	    	if(strs3.size()==1){
	    		//户口本new page
	    		if(strs1.size()==1||strs1.size()==3||strs1.size()==5){
	    			cb.beginText();
	    	    	cb.setFontAndSize(bf, 12);
	    	    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER,code, 95,406, 0);
	    	    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, strs3.get(0).getD()+"/p"+cd++, 105, 396, 0);
	    	    	cb.endText();
	    	    	Image image12 = Image.getInstance(strs3.get(0).getB());
	    	    	image12.scaleAbsolute(508, 372);
	    	    	image12.setAbsolutePosition(39,20);
	    	    	document.add(image12);
	    		}else{
	    			document.newPage();
	    			cb.beginText();
	    	    	cb.setFontAndSize(bf, 12);
	    			cb.showTextAligned(PdfContentByte.ALIGN_CENTER, code, 95,820, 0);
	    	    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER,strs3.get(0).getD()+"/p"+cd++, 105,810, 0);
	    	    	cb.endText();
	    			Image image13 = Image.getInstance(strs3.get(0).getB());
	    			image13.scaleAbsolute(508, 372);
	    			image13.setAbsolutePosition(39, 435);
	    			document.add(image13);
	    		}
	    	}
	    	if(strs3.size()==2){
	    		if(strs1.size()==1||strs1.size()==3||strs1.size()==5){
	    			cb.beginText();
	    	    	cb.setFontAndSize(bf, 12);
	    	    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER,code, 95,406, 0);
	    	    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, strs3.get(0).getD()+"/p"+cd++, 105, 396, 0);
	    	    	cb.endText();
	    	    	Image image12 = Image.getInstance(strs3.get(0).getB());
	    	    	image12.scaleAbsolute(508, 372);
	    	    	image12.setAbsolutePosition(39,20);
	    	    	document.add(image12);
	    	    	document.newPage();
	    	    	cb.beginText();
	    	    	cb.setFontAndSize(bf, 12);
	    	    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER,code, 95,820, 0);
	    	    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, strs3.get(1).getD()+"/p"+cd++, 105, 810, 0);
	    	    	cb.endText();
	    	    	Image image14 = Image.getInstance(strs3.get(1).getB());
	    	    	image14.scaleAbsolute(508, 372);
	    	    	image14.setAbsolutePosition(39, 435);
	    	    	document.add(image14);
	    		}else{
	    			document.newPage();
	    			cb.beginText();
	    	    	cb.setFontAndSize(bf, 12);
	    	    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER,code, 95,820, 0);
	    	    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, strs3.get(1).getD()+"/p"+cd++, 105, 810, 0);
	    	    	cb.endText();
	    			Image image13 = Image.getInstance(strs3.get(1).getB());
	    			image13.scaleAbsolute(508, 372);
	    			image13.setAbsolutePosition(39, 435);
	    			document.add(image13);
	    			cb.beginText();
	    	    	cb.setFontAndSize(bf, 12);
	    	    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER,code, 95,406, 0);
	    	    	cb.showTextAligned(PdfContentByte.ALIGN_CENTER, strs3.get(2).getD()+"/p"+cd++, 105, 396, 0);
	    	    	cb.endText();
	    	    	Image image12 = Image.getInstance(strs3.get(2).getB());
	    	    	image12.scaleAbsolute(508, 372);
	    	    	image12.setAbsolutePosition(39,20);
	    	    	document.add(image12);
	    		}
	    	}
	    	if(strs2.size()>0){
	    		//出生证明 new page
	    		for (int i = 0; i < strs2.size(); i++) {
	    			document.newPage();
	    			document.add(new Paragraph(code));
	    			document.add(new Paragraph(strs2.get(i).getD()+"/p"+cd++));
	    			Image image10 = Image.getInstance(strs2.get(i).getB());
	    			image10.scaleAbsolute(518, 702);
	    	    	image10.setAbsolutePosition(39,60);
	    			document.add(image10);
	    		}
	    	}
	    	if(strs4.size()>0){     	
	    		//人员合照new page
	    		for (int i = 0; i < strs4.size(); i++) {
	    			document.newPage();
	    			document.add(new Paragraph(code));
	    			document.add(new Paragraph(strs4.get(i).getD()+"/p"+cd++));
	    			Image image11 = Image.getInstance(strs4.get(i).getB());
	    			image11.scaleAbsolute(518, 702);
	    	    	image11.setAbsolutePosition(39,60);
	    			document.add(image11);
	    		}
	    	}
	    	
	    	
	    	/*Image image2 = Image.getInstance("d://22.jpg");
	    	image2.scaleAbsolute(303, 202);
	    	image2.setAlignment(Image.MIDDLE);
	    	document.add(image2);*/
	    	
	    	/*Font headFont = new Font(bfChinese, 10, Font.NORMAL);// 设置字体大小
	    	 Paragraph title = new Paragraph("你好，Pdf！", " ");
	    	 document.newPage();
	    	// 读取一个图片
	    	Image image2 = Image.getInstance("d://22.jpg");
	    	image2.scaleAbsolute(303, 202);
	    	image2.setAlignment(Image.LEFT);
	    	document.add(image2);
	    	
	    	//设置图片的绝对位置
	    	//image.setAbsolutePosition(0, 0);
	    	//image.scaleAbsolute(100, 400);
	    	 */
	    
	    	// 插入一个图片
	    	
	    	} catch (DocumentException de) {
	    	System.out.println(de.getMessage());
	    	} catch (IOException ioe) {
	    	System.out.println(ioe.getMessage());
	    	}
	    	document.close();
	    	fos.flush();
	    	fos.close();
	    	return true;
	    	}else{
	    	return false;
	    	}
	    	}
	    
public static void main(String[] args) {
	System.out.println("1111");
	wordToPDF2("D://information//DNA//wt2019032300508//换个方法和的委托书.doc", "D://information//DNA//wt2019032300508//power of attorney.pdf");
	
}
}