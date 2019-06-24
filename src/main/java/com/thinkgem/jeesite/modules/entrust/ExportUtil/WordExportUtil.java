package com.thinkgem.jeesite.modules.entrust.ExportUtil;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import com.thinkgem.jeesite.modules.entrust.entity.EntrustRegister;

import javax.imageio.ImageIO;
import javax.mail.internet.MimeUtility;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * Created by jason on 2017/11/30.
 */
public class WordExportUtil {
    public static String WORD_2007 = "WORD_2007";
    public static String WORD_2003 = "WORD_2003";

    /**
     * 设置下载文件中文件的名称
     * @param filename
     * @param request
     * @return
     */
    public static String encodeFilename(String filename, HttpServletRequest request) {
        String agent = request.getHeader("USER-AGENT");
        try {
            if ((agent != null) && (-1 != agent.indexOf("MSIE"))) {
                String newFileName = URLEncoder.encode(filename, "UTF-8");
                newFileName = StringUtils.replace(newFileName, "+", "%20");
                if (newFileName.length() > 150) {
                    newFileName = new String(filename.getBytes("GB2312"), "ISO8859-1");
                    newFileName = StringUtils.replace(newFileName, " ", "%20");
                }
                return newFileName;
            }
            if ((agent != null) && (-1 != agent.indexOf("Mozilla")))
                return MimeUtility.encodeText(filename, "UTF-8", "B");
            return URLEncoder.encode(filename, "UTF-8");
        } catch (Exception ex) {
            return filename;
        }
    }

    /**
     *
     * @param version  Word_2003/Word_2007
     * @param docFileName 生成的doc临时文件名
     * @param templateDir 存放freemark模板的目录
     * @param templateFile freemark模板文件名
     * @param beanParams   入参数据:  Map<String, Object>类型
     */
    public static void writeResponse( String version,  String docFileName, String templateDir, String templateFile, Map<String, Object> beanParams,EntrustRegister entrustRegister) {
        Configuration config = new Configuration();
        //InputStream is = null;
        File previewFile = null;
        Resource resource = new ClassPathResource(templateDir + File.separator + "temple.ftl");

        try {
            File templateDirFile = resource.getFile();
            File parentTemplateFile = templateDirFile.getParentFile();
            if (!parentTemplateFile.exists()) {
                boolean result = parentTemplateFile.mkdirs();
                if (!result) {
                    System.out.println("创建失败");
                }
            }

            config.setDirectoryForTemplateLoading(parentTemplateFile);
            //config.setObjectWrapper(new DefaultObjectWrapper());
            Template template = config.getTemplate(templateFile, "UTF-8");
            if (WORD_2007.equals(version)) {
                docFileName =docFileName+ ".docx" ;
            } else {
                docFileName = docFileName+ ".doc";
            }
            //String docName = request.getSession().getServletContext().getRealPath(File.separator+docTempDir)+File.separator+docFileName;
            // 换成D盘
            String docName = "D:"+File.separator+"information"+File.separator+"DNA"+File.separator+entrustRegister.getCode()+File.separator+docFileName;
            File filePath = new File(docName).getParentFile();
            if(!filePath.exists())
            {
                filePath.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(docName);
            Writer out = new OutputStreamWriter(fos, "UTF-8");
            template.process(beanParams, out);

            previewFile = new File(docName);
            out.flush();
            out.close();
            /*is = new FileInputStream(previewFile);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            // 设置浏览器以下载的方式处理该文件默认名为resume.doc
            response.addHeader("Content-Disposition", "attachment;filename="+docFileName);
            byte[] b = new byte[1024];
            int len;
            while ((len=is.read(b)) >0) {
                response.getOutputStream().write(b,0,len);
            }

            out.flush();
            out.close();
            response.getOutputStream().flush();
            response.getOutputStream().close();*/
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        }
    }
    
    
   /* public static void main(String[] args) {
    	// int screenWidth=java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    	// int screenHeight=java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
    	ScreenSize ss=new ScreenSize();
    	
    	
    	
    	int screenWidth=ss.getScreenWidth();
    	int screenHeight=ss.getScreenHeight();
    	System.out.println("屏幕宽为："+screenWidth+"---屏幕高为："+screenHeight);
    	}*/
    
    

public static void CompieImage(String mbdest,int widthCut,float cut_proportion_paper,int width,int height,String firstimagepath,String secondimagepath){
	
 
int heightCut = (int)(widthCut/cut_proportion_paper);
		//合成图片
		//搞出2个类
		BufferedImage image = new BufferedImage(width, height,
		BufferedImage.SCALE_DEFAULT);
		Graphics graphics = image.getGraphics();
		BufferedImage img = null;
		BufferedImage img2 = null;
		try {
		//分别从文件中读取流：：
		//第一张图片的全路径
		img = ImageIO.read(new FileInputStream(firstimagepath));
		//第二张图片的全路径
		img2 = ImageIO.read(new FileInputStream(secondimagepath));
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
 
}
		//画出一个白色的A4纸：：
		graphics.setColor(Color.white);
		graphics.fillRect(1, 0, width, height);
		int positionPaperX = width/2 - widthCut/2;
		int positionPaperY = height/2 - heightCut;
		graphics.drawImage(img, positionPaperX, positionPaperY, widthCut, heightCut, null);
		graphics.drawImage(img2, positionPaperX, positionPaperY+heightCut, widthCut, heightCut,null);
		graphics.dispose();
		//------合成文件为文件起名字
		 
		//将文件写入到新的目的地mbdest目的地地址
		try {
		ImageIO.write(image, "JPG", new File(mbdest));
		} catch (IOException e) {
		e.printStackTrace();
		}
}


public static void changeSize(int newWidth, int newHeight, String path) throws Exception {
    BufferedInputStream in = null;
   
        in = new BufferedInputStream(new FileInputStream(path));

        //字节流转图片对象
        Image bi = ImageIO.read(in);
        //构建图片流
        BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        //绘制改变尺寸后的图
        tag.getGraphics().drawImage(bi, 0, 0, newWidth, newHeight, null);
        //输出流
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path));
        //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        //encoder.encode(tag);
        ImageIO.write(tag, "PNG", out);
        in.close();
        out.close();
       
   
}


public static void main(String[] args) throws Exception {
	changeSize(86, 54, "d://88.jpg");
	

    	
}

}