/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entrust.web;

import java.io.*;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.dna.entity.*;
import com.thinkgem.jeesite.modules.entrust.entity.*;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.service.DictService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sun.star.chart2.Break;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.filter.PhotoUploud;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.clcohol.entity.Abc;
import com.thinkgem.jeesite.modules.dna.dao.DnaBoardJggDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaElectrophoresisPartingIteamDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaExperimentStrDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaExtractRecordItemDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaPreparationReagentsIteamDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaReceiveIteamDao;
import com.thinkgem.jeesite.modules.dna.service.DnaBoardService;
import com.thinkgem.jeesite.modules.dna.service.DnaElectrophoresisPartingService;
import com.thinkgem.jeesite.modules.dna.service.DnaExtractRecordService;
import com.thinkgem.jeesite.modules.dna.service.DnaPiResultService;
import com.thinkgem.jeesite.modules.dna.service.DnaPreparationReagentsService;
import com.thinkgem.jeesite.modules.entrust.ExportUtil.WordExportUtil;
import com.thinkgem.jeesite.modules.entrust.dao.EntrustChargeCredentialsDao;
import com.thinkgem.jeesite.modules.entrust.dao.EntrustExpertOpinionDao;
import com.thinkgem.jeesite.modules.entrust.dao.EntrustModifyrecordDao;
import com.thinkgem.jeesite.modules.entrust.dao.LicensedDao;
import com.thinkgem.jeesite.modules.entrust.dao.MappingDao;
import com.thinkgem.jeesite.modules.entrust.service.EntrustAbstractsService;
import com.thinkgem.jeesite.modules.entrust.service.EntrustModifyrecordService;
import com.thinkgem.jeesite.modules.entrust.service.EntrustRegisterService;
import com.thinkgem.jeesite.modules.entrust.service.MappingService;
import com.thinkgem.jeesite.modules.entrust_flow_sheet.dao.entrust.EntrustFlowSheetDao;
import com.thinkgem.jeesite.modules.entrust_flow_sheet.entity.entrust.EntrustFlowSheet;
import com.thinkgem.jeesite.modules.material.service.SpecimenMaterialRegisterService;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.xueka.entity.SpecimenXueka;
import com.thinkgem.jeesite.modules.xueka.service.SpecimenXuekaService;

/**
 * 委托登记Controller
 *
 * @author zhuguli
 * @version 2017-05-12
 */
@Controller
@RequestMapping(value = "${adminPath}/entrust/entrustRegister")
public class EntrustRegisterController extends BaseController {

    @Autowired
    private EntrustRegisterService entrustRegisterService;
    @Autowired
    private SpecimenXuekaService specimenXuekaService;
    @Autowired
    private EntrustAbstractsService entrustAbstractsService;
    @Autowired
    private SpecimenMaterialRegisterService specimenMaterialRegisterService;
    @Autowired
    private DnaExperimentStrDao dnaExperimentStrDao;
    @Autowired
    private EntrustExpertOpinionDao entrustExpertOpinionDao;
    @Autowired
    private DnaBoardJggDao dnaBoardJggDao;
    @Autowired
    private DnaBoardService dnaBoardService;
    @Autowired
    private DnaExtractRecordItemDao dnaExtractRecordItemDao;
    @Autowired
    private DnaExtractRecordService dnaExtractRecordService;
    @Autowired
    private DnaPreparationReagentsService dnaPreparationReagentsService;
    @Autowired
    private DnaPreparationReagentsIteamDao dnaPreparationReagentsIteamDao;
    @Autowired
    private DnaElectrophoresisPartingService dnaElectrophoresisPartingService;
    @Autowired
    private MappingDao mappingDao;
    @Autowired
    private LicensedDao licensedDao;
    @Autowired
    private EntrustFlowSheetDao entrustFlowSheetDao;
    @Autowired
    private EntrustChargeCredentialsDao entrustChargeCredentialsDao;
    @Autowired
    private EntrustModifyrecordService entrustModifyrecordService;
    @Autowired
    private DnaPiResultService dnaPiResultService;
    
    

    @Autowired
	private DictService dictService;

    public final static char[] upper = "零一二三四五六七八九十".toCharArray();

    @ModelAttribute
    public EntrustRegister get(@RequestParam(required = false) String id) {
        EntrustRegister entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = entrustRegisterService.get(id);
        }
        if (entity == null) {
            entity = new EntrustRegister();
        }
        return entity;
    }

  
    //
    @RequiresPermissions("entrust:entrustRegister:view")
    @RequestMapping(value = {"list", ""})
    public String list(EntrustRegister entrustRegister, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<EntrustRegister> page = entrustRegisterService.findPage(new Page<EntrustRegister>(request, response), entrustRegister);
        model.addAttribute("page", page);
         return "modules/entrust/entrustRegisterList";
    }

    //归档 archive
    @RequiresPermissions("entrust:entrustRegister:view")
    @RequestMapping(value = {"archive"})
    public String archive(EntrustRegister entrustRegister, Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
    	Word2007ToHtml word2007ToHtml=new Word2007ToHtml();
    	//委托书pdf展示
    	String attorneyPath="DNA/"+entrustRegister.getCode()+"/"+ "power of attorney" + ".pdf";;
    	model.addAttribute("attorneyPath", attorneyPath);
    	//报告书pdf展示
    	String reportPath="DNA/"+entrustRegister.getCode()+"/"+"report of DNA" + ".pdf";
    	model.addAttribute("reportPath", reportPath);
    	//idpic pdf展示
    	String imgPath="DNA/"+entrustRegister.getCode()+"/"+"idPic/"+ "idpic of dna" + ".pdf";;
    	model.addAttribute("imgPath", imgPath);
    	//mapping pdf展示
        if (mappingDao.findEntrsut(entrustRegister.getId()) != null) {
        	String mapPath="D:\\information\\DNA\\"+entrustRegister.getCode()+"\\mapping";
        	List<String>mapPics=new ArrayList<String>();
        	File mFile = new File(mapPath);
        	if (mFile.exists() && mFile.isDirectory()) {
        		List<File> mlist = new ArrayList<File>();
        		getAllFile(mFile, mlist);
        		// 已经获取了所有图片
        		for (File file2 : mlist) {
        			mapPics.add(file2.getAbsolutePath().substring(file2.getAbsolutePath().lastIndexOf("DNA"), file2.getAbsolutePath().length()));
        		}
        	}
        	if(mapPics.size()>0){
        		model.addAttribute("mapPics", mapPics.get(0));
        	}
        }
     //执业证
        List<Licensed> licenseds = licensedDao.findEntrust(entrustRegister.getId());
        if(licenseds.size()>2){
    	File fileCer=new File("D:\\information\\DNA\\"+entrustRegister.getCode()+"\\certificate of dna.pdf");
    	if(fileCer.exists()){
    	}else{
        if(licenseds.size()>2){
        	model.addAttribute("licenseds1Id", licenseds.get(0).getUser().getId());
        	model.addAttribute("licenseds1Name",licenseds.get(0).getUser().getName());
        	model.addAttribute("licenseds2Id", licenseds.get(1).getUser().getId());
        	model.addAttribute("licenseds2Name",licenseds.get(1).getUser().getName());
        	licenseds.get(0).getUserBy();
        	licenseds.get(1).getUserBy();
        	String imagesPath="D:\\information\\user\\"+licenseds.get(0).getUserBy()+"\\certificate";
             	List<String>mapPics=new ArrayList<String>();
             	File mFile = new File(imagesPath);
             	if (mFile.exists() && mFile.isDirectory()) {
             		List<File> mlist = new ArrayList<File>();
             		getAllFile(mFile, mlist);
             		// 已经获取了所有图片
             		for (File file2 : mlist){
             			mapPics.add(file2.getAbsolutePath());
             		}
             	}
             	String imagesPath2="D:\\information\\user\\"+licenseds.get(1).getUserBy()+"\\certificate";
             	File mFile2 = new File(imagesPath2);
             	if (mFile2.exists() && mFile2.isDirectory()){
             		List<File> mlist2 = new ArrayList<File>();
             		getAllFile(mFile2, mlist2);
             		// 已经获取了所有图片
             		for (File file2 : mlist2) {
             			mapPics.add(file2.getAbsolutePath());
             		}
             	}
             	String FILEPATH="D:\\information\\DNA\\"+entrustRegister.getCode()+"\\";
             	Word2007ToHtml.imagesToPdf2(FILEPATH, "certificate of dna", mapPics.get(0), mapPics.get(1));
        	}
        	
      }
    	model.addAttribute("cerTi", "DNA\\"+entrustRegister.getCode()+"\\certificate of dna.pdf");
   }   
    	
    	//返还清单
    	 if(licenseds.size()>2){
    		 File fileList=new File("D:"+File.separator+"information"+File.separator+"DNA"+File.separator+entrustRegister.getCode()+File.separator+"listing of dna" + ".pdf");
    	    	if(fileList.exists()){
    	    	}else{
    	    		entrustRegisterService.lisTing(entrustRegister);
    	    	} 
    	    	model.addAttribute("lisTing","DNA\\"+entrustRegister.getCode()+"\\listing of dna.pdf");
    	    	//内部扭转单
    	    	File fileCir=new File("D:"+File.separator+"information"+File.separator+"DNA"+File.separator+entrustRegister.getCode()+File.separator+"circulation of dna" + ".pdf");
    	    	if(fileCir.exists()){
    	    	}else{
    	    		entrustRegisterService.circuLation(entrustRegister);
    	    	}
    	    	model.addAttribute("cirCul","DNA\\"+entrustRegister.getCode()+"\\circulation of dna.pdf");
    	    	//DNA扩增表
    	    	String pathAmpli="D:"+File.separator+"information"+File.separator+"DNA"+File.separator+entrustRegister.getCode()+File.separator+"ampliFication of dna" + ".pdf";
    	    	File amplifileCir=new File(pathAmpli);
    	    	if(amplifileCir.exists()){
    	    	}else{
    	    		List<EntrustAbstracts> allentrustAbstracts = entrustAbstractsService.findAllentrustAbstracts(entrustRegister.getId());
    	    		entrustRegisterService.ampliFication(entrustRegister, allentrustAbstracts);
    	    	}
    	    	model.addAttribute("ampLi","DNA\\"+entrustRegister.getCode()+"\\ampliFication of dna.pdf");
    	 }
    	 
    	 //当事人
    	 List<EntrustAbstracts> allentrustAbstracts = entrustAbstractsService.findAllentrustAbstracts(entrustRegister.getId());
	 	   	List<Strs>strs1=new ArrayList<Strs>();
	 	   	List<Strs>strs2=new ArrayList<Strs>();
	 		List<Strs>strs3=new ArrayList<Strs>();
	 		List<Strs>strs4=new ArrayList<Strs>();
	 	   int bc=1;
	 	   int bc1=1;
	 	   int bc2=1;
	 if(allentrustAbstracts.size()>0&&allentrustAbstracts.get(0).getIdPic()!=null){
	 	for (int i = 0; i < allentrustAbstracts.size(); i++) {
			if(allentrustAbstracts.get(i).getIdPic()!=null){
				String mapPath="D:\\information\\DNA\\"+entrustRegister.getCode()+"\\idPic";
			  	File mFile = new File(mapPath);
	           	if (mFile.exists() && mFile.isDirectory()) {
	           		List<File> mlist = new ArrayList<File>();
	           		getAllFile(mFile, mlist);
	           		// 已经获取了所有图片
	           		for (File file2 : mlist) {
	           			String d=file2.getAbsolutePath();
	           			d = d.replace('\\', '/');
	           			d = URLDecoder.decode(d, "utf-8");  
	           			if(d.contains(allentrustAbstracts.get(i).getClientName()+"的正")){
	           				Strs strs=new Strs();
	           				strs.setB(d);
	           				strs.setA(bc++);
	           				strs.setD(allentrustAbstracts.get(i).getSpecimenCode());
	           				for (File file3 : mlist) {
	    	           			String d2=file3.getAbsolutePath();
	    	           			d2 = d2.replace('\\', '/');
	    	           			if(d2.contains(allentrustAbstracts.get(i).getClientName()+"的反")){
	    	           				d2 = URLDecoder.decode(d2, "utf-8");  
	    	           				strs.setC(d2);
	    	           			}
	           				}
	           				strs1.add(strs);
	           			}
	           			if(d.contains(allentrustAbstracts.get(i).getClientName()+"的出生证明")){
	           				Strs strs=new Strs();
	           				strs.setA(bc1++);
	           				strs.setB(d);
	           				strs.setD(allentrustAbstracts.get(i).getSpecimenCode());
	           				strs2.add(strs);
	           			}
	           			if(d.contains(allentrustAbstracts.get(i).getClientName()+"的户口本")){
	           				Strs strs=new Strs();
	           				strs.setA(bc2++);
	           				strs.setB(d);
	           				strs.setD(allentrustAbstracts.get(i).getSpecimenCode());
	           				strs3.add(strs);
	           			}
	           		}
	           	}
			}
		}
 	   if(entrustRegister.getClientArea()!=null){
 		   String mapPath="D:\\information\\DNA\\"+entrustRegister.getCode()+"\\idPic";
			  	File mFile = new File(mapPath);
	           	if (mFile.exists() && mFile.isDirectory()) {
	           		List<File> mlist = new ArrayList<File>();
	           		getAllFile(mFile, mlist);
	           		// 已经获取了所有图片
 			for (File file2 : mlist) {
        			String d=file2.getAbsolutePath();
        			d = d.replace('\\', '/');
        			d = URLDecoder.decode(d, "utf-8");  
        			if(d.contains(entrustRegister.getClientName()+"的人员合照")){
        				Strs strs=new Strs();
        				strs.setD(entrustRegister.getCaseCode());
        				strs.setB(d);
        				strs.setA(bc++);
        				strs4.add(strs);
        			}
 			}
	 }   
}  	 
 	   if(strs1.size()>0||strs2.size()>0||strs3.size()>0||strs4.size()>0){
 		   Word2007ToHtml.imgToPdf2(null, null, entrustRegister, strs1, strs2, strs3, strs4);
 		   model.addAttribute("IDpic","DNA\\"+entrustRegister.getCode()+"\\idPic"+"\\IDpic.pdf");
 	   }
 	   
	}
	 
	 //司法鉴定许可证复印件
	 File fileList=new File( "D:" + File.separator + "information" + File.separator + "office" + File.separator+"jd of dna" + ".pdf");
 	if(fileList.exists()){
 		}else{
 		String jdPath = "D:" + File.separator + "information" + File.separator + "office" + File.separator;
		File mFile = new File(jdPath);
		String d=null;
		if (mFile.exists() && mFile.isDirectory()) {
       		List<File> mlist = new ArrayList<File>();
       		getAllFile(mFile, mlist);
       		// 已经获取了所有图片
		for (File file2 : mlist){
			 d=file2.getAbsolutePath();
			d = d.replace('\\', '/');
			d = URLDecoder.decode(d, "utf-8");  
			break;
				}
			} 
		Word2007ToHtml.imgToPdf(d, jdPath+"jd of dna.pdf");
 		}		
 		model.addAttribute("jdPath","office\\"+"jd of dna.pdf");
        return "modules/entrust/entrustRegisterArchive";
}
    //当事人
    @RequestMapping(value = "parties")
    public String parties(EntrustRegister entrustRegister,Model model) throws Exception{    	  
    	List<EntrustAbstracts> allentrustAbstracts = entrustAbstractsService.findAllentrustAbstracts(entrustRegister.getId());
    	   	List<Strs>strs1=new ArrayList<Strs>();
    	   	List<Strs>strs2=new ArrayList<Strs>();
    		List<Strs>strs3=new ArrayList<Strs>();
    		List<Strs>strs4=new ArrayList<Strs>();
	    	   int  bc= 1;
	    	   int bc1=1;
	    	   int bc2=1;
    	   for (int i = 0; i < allentrustAbstracts.size(); i++) {
			if(allentrustAbstracts.get(i).getIdPic()!=null){
				String mapPath="D:\\information\\DNA\\"+entrustRegister.getCode()+"\\idPic";
			  	File mFile = new File(mapPath);
	           	if (mFile.exists() && mFile.isDirectory()) {
	           		List<File> mlist = new ArrayList<File>();
	           		getAllFile(mFile, mlist);
	           		// 已经获取了所有图片
	           		
	           		for (File file2 : mlist) {
	           			String d=file2.getAbsolutePath();
	           			d = d.replace('\\', '/');
	           			d = URLDecoder.decode(d, "utf-8");  
	           			if(d.contains(allentrustAbstracts.get(i).getClientName()+"的正")){
	           				Strs strs=new Strs();
	           				strs.setB(d);
	           				strs.setA(bc++);
	           				strs.setD(allentrustAbstracts.get(i).getSpecimenCode());
	           				for (File file3 : mlist) {
	    	           			String d2=file3.getAbsolutePath();
	    	           			d2 = d2.replace('\\', '/');
	    	           			if(d2.contains(allentrustAbstracts.get(i).getClientName()+"的反")){
	    	           				d2 = URLDecoder.decode(d2, "utf-8");  
	    	           				strs.setC(d2);
	    	           			}
	           				}
	           				strs1.add(strs);
	           			}
	           			if(d.contains(allentrustAbstracts.get(i).getClientName()+"的出生证明")){
	           				Strs strs=new Strs();
	           				strs.setA(bc1++);
	           				strs.setB(d);
	           				strs.setD(allentrustAbstracts.get(i).getSpecimenCode());
	           				strs2.add(strs);
	           			}
	           			if(d.contains(allentrustAbstracts.get(i).getClientName()+"的户口本")){
	           				Strs strs=new Strs();
	           				strs.setA(bc2++);
	           				strs.setB(d);
	           				strs.setD(allentrustAbstracts.get(i).getSpecimenCode());
	           				strs3.add(strs);
	           			}
	           		}
	           	}
			}
		}
    	   if(entrustRegister.getClientArea()!=null){
    		   String mapPath="D:\\information\\DNA\\"+entrustRegister.getCode()+"\\idPic";
			  	File mFile = new File(mapPath);
	           	if (mFile.exists() && mFile.isDirectory()) {
	           		List<File> mlist = new ArrayList<File>();
	           		getAllFile(mFile, mlist);
	           		// 已经获取了所有图片
    			for (File file2 : mlist) {
           			String d=file2.getAbsolutePath();
           			d = d.replace('\\', '/');
           			d = URLDecoder.decode(d, "utf-8");  
           			if(d.contains(entrustRegister.getClientName()+"的人员合照")){
           				Strs strs=new Strs();
           				strs.setD(entrustRegister.getCaseCode());
           				strs.setB(d);
           				strs.setA(bc++);
           				strs4.add(strs);
           			}
    			}
	          }   
    	   }  	 
    	   if(allentrustAbstracts.size()>0){
    		   Word2007ToHtml.imgToPdf2(null, null, entrustRegister, strs1, strs2, strs3, strs4);
    	   }
		return null;
    }
  
    //详情打印
    @RequestMapping(value = "printDea")
    public void printDea(EntrustRegister entrustRegister){
    	Word2007ToHtml.printByWord("D:\\information\\DNA\\wt2018112101819\\郭靖的委托书.doc");
    }
    

    @RequiresPermissions("entrust:entrustRegister:view")
    @RequestMapping(value = "form")
    public String form(EntrustRegister entrustRegister, Model model) {
        model.addAttribute("entrustRegister", entrustRegister);
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        String casecode= entrustRegister.getCaseCode().substring(11, 16);
    	int ss = 0;
    	int[] a = new int[casecode.length()];
    	for (int i = 0; i < casecode.length(); i++) {
    		//先由字符串转换成char,再转换成String,然后Integer
    		a[i] = Integer.parseInt(String.valueOf(casecode.charAt(i)));
    		if (a[i] != 0) {
    			ss = i;
    			break;
    		}
    	}
    	casecode = casecode.substring(ss, casecode.length());
    	int s=  Integer.parseInt(casecode);
    	model.addAttribute("casecode", s);
    	
    
         model.addAttribute("simple", entrustRegister.getCaseCode().substring(3, 7));
    	
        User user = UserUtils.getUser();
        model.addAttribute("user", user);
        String entrustDate=null;
        if(entrustRegister.getIsNewRecord()){
        	entrustDate=df.format(new Date());
        }else{
        	entrustDate=entrustRegister.getEntrustDate();
        }
        model.addAttribute("entrustDate", entrustDate);
        return "modules/entrust/entrustRegisterForm";
    }


    @RequiresPermissions("entrust:entrustRegister:view")
    @RequestMapping(value = "form1")
    public String form1(EntrustRegister entrustRegister, Model model,String accept,HttpServletRequest request, HttpServletResponse response) {
        Page<EntrustRegister> page = entrustRegisterService.findPage(new Page<EntrustRegister>(request, response), entrustRegister);
        if(!accept.equals("")||accept!=null){
    		entrustRegister.setWhether(accept);
    		if(accept.equals("1")||accept.equals("2")||accept.equals("3")){
    			EntrustAbstracts entrustAbstracts=new EntrustAbstracts();
    			entrustAbstracts.setAppellation("1");
    			List<EntrustAbstracts> entrustAbstractsList =new ArrayList<EntrustAbstracts>();
    			entrustAbstractsList.add(entrustAbstracts);
    			entrustRegister.setEntrustAbstractsList(entrustAbstractsList);
    		}
    		
    		if(accept.equals("4")){
    			EntrustAbstracts entrustAbstracts=new EntrustAbstracts();
    			entrustAbstracts.setAppellation("4");
    			List<EntrustAbstracts> entrustAbstractsList =new ArrayList<EntrustAbstracts>();
    			entrustAbstractsList.add(entrustAbstracts);
    			entrustRegister.setEntrustAbstractsList(entrustAbstractsList);
    		}
    		if(accept.equals("5")){
    			EntrustAbstracts entrustAbstracts=new EntrustAbstracts();
    			entrustAbstracts.setAppellation("5");
    			EntrustAbstracts entrustAbstracts1=new EntrustAbstracts();
    			entrustAbstracts1.setAppellation("5");
    			List<EntrustAbstracts> entrustAbstractsList =new ArrayList<EntrustAbstracts>();
    			entrustAbstractsList.add(entrustAbstracts);
    			entrustAbstractsList.add(entrustAbstracts1);
    			entrustRegister.setEntrustAbstractsList(entrustAbstractsList);
    		}
    		if(accept.equals("6")){
    			EntrustAbstracts entrustAbstracts=new EntrustAbstracts();
    			entrustAbstracts.setAppellation("6");
    			List<EntrustAbstracts> entrustAbstractsList =new ArrayList<EntrustAbstracts>();
    			entrustAbstractsList.add(entrustAbstracts);
    			entrustRegister.setEntrustAbstractsList(entrustAbstractsList);
    		}
    	}
        
        
        if(page.getList().size()==0){
        	int s= 00001;
        	model.addAttribute("casecode", s);
        }else{
        	
        	String casecode=  page.getList().get(0).getCaseCode().substring(11, 16);
        	int ss = 0;
        	int[] a = new int[casecode.length()];
        	for (int i = 0; i < casecode.length(); i++) {
        		//先由字符串转换成char,再转换成String,然后Integer
        		a[i] = Integer.parseInt(String.valueOf(casecode.charAt(i)));
        		if (a[i] != 0) {
        			ss = i;
        			break;
        		}
        	}
        	casecode = casecode.substring(ss, casecode.length());
        	
        	int s=  Integer.parseInt(casecode)+1;
        	model.addAttribute("casecode", s);
        }
     
         SimpleDateFormat simple = new SimpleDateFormat("yyyy");
         model.addAttribute("simple", simple.format(new Date()));
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        User user = UserUtils.getUser();
        model.addAttribute("user", user);
        model.addAttribute("entrustDate", df.format(new Date()));
        return "modules/entrust/entrustRegisterForm";
    }
    
    
 /*   //打印
    @RequestMapping(value = "printPic")
    public void printPic(String[] idPic) {
    	System.out.println(idPic[1]);
    	
    	//DNA\wt2018112301820\idPic\1_161959_3.jpg, DNA\wt2018112301820\idPic\234956-140619222S743.jpg]
    	
    	  try {
    		  
    		  for (String string : idPic) {
  				
    			  String filePath = "D:"+File.separator+"information"+File.separator+string;
    			  System.out.println(filePath);
    			  
    			  File file = new File(filePath);			
    			  printDown(file);			
			}
    		  
    			
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    }
    */
  
    
    //实验记录底稿
    @RequestMapping(value = "modifyrecord")
    public String modifyrecord(EntrustRegister entrustRegister, Model model, HttpServletRequest request, HttpServletResponse response) {
        EntrustModifyrecord entrustModifyrecord = new EntrustModifyrecord();
        entrustModifyrecord.setRegister(entrustRegister);
        Page<EntrustModifyrecord> page = entrustModifyrecordService.findPage(new Page<EntrustModifyrecord>(request, response), entrustModifyrecord);
        model.addAttribute("page", page);
        return "modules/entrust/entrustModifyrecordList";
    }

    
    
   


 
   //word正稿


    @RequestMapping(value = "downLoadFile")
    public void downLoadFile(HttpServletRequest request, HttpServletResponse response, String registerId) {         
        if (StringUtils.isNotEmpty(registerId)) {
            EntrustRegister entrustRegister = entrustRegisterService.get(registerId);
            if (entrustRegister != null) {
                SimpleDateFormat simple = new SimpleDateFormat("yyyy年MM月dd日");
                //DNA2017071100057
                String casecode = entrustRegister.getCaseCode().substring(11, 16);
                int ss = 0;
                int[] a = new int[casecode.length()];
                for (int i = 0; i < casecode.length(); i++) {
                    //先由字符串转换成char,再转换成String,然后Integer
                    a[i] = Integer.parseInt(String.valueOf(casecode.charAt(i)));
                    if (a[i] != 0) {
                        ss = i;
                        break;
                    }
                }
                casecode = casecode.substring(ss, casecode.length());
                String fm = "";
                List<EntrustAbstracts> allentrustAbstracts = entrustAbstractsService.findAllentrustAbstracts(entrustRegister.getId());
                List<String> father = new ArrayList<String>();
                List<String> mather = new ArrayList<String>();
                List<String> children = new ArrayList<String>(); 
                String t=null;
                String t2=null;
                String t3=null;
                if(allentrustAbstracts.size()==1){
        			fm="分析"+allentrustAbstracts.get(0).getClientName()+"的DNA数据1111111";
        		}else if(allentrustAbstracts.get(0).getSpecimenCode().contains("T")){
    				for (EntrustAbstracts entrustAbstracts2 : allentrustAbstracts) {
    					if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 1, entrustAbstracts2.getSpecimenCode().length()).equals("T")) {
    						t=entrustAbstracts2.getClientName();
    					}
    					if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 2, entrustAbstracts2.getSpecimenCode().length()).equals("T2")) {
    						t2=entrustAbstracts2.getClientName();
    					}
    					if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 2, entrustAbstracts2.getSpecimenCode().length()).equals("T3")) {
    						t3=entrustAbstracts2.getClientName();
    					}
    				}
    				if(t3==null){
    					fm=t+"和"+t2+"血样的DNA是否一致1111111";
    				}else{
    					fm=t+","+t2+"和"+t3+"血样的DNA是否一致1111111";
    				}
    			}else{
                for (int i = 0; i < allentrustAbstracts.size(); i++) {
                    String appellationname = allentrustAbstracts.get(i).getAppellation();
                    String ClientName = allentrustAbstracts.get(i).getClientName();
                    if (appellationname.equals("1")) {
                        father.add(ClientName);
                    }
                    if (appellationname.equals("2")) {
                        mather.add(ClientName);
                    }
                    if (appellationname.equals("3")) {
                        children.add(ClientName);
                    }
                }
                if (father.size() != 0) {
                    for (int j = 0; j < father.size(); j++) {
                        for (int ds = 0; ds < children.size(); ds++) {
                            fm += father.get(j) + "是否为" + children.get(ds) + "的生物学父亲" + "<w:br/>";
                        }
                    }

                }
                if (mather.size() > 0) {
                    for (int j = 0; j < mather.size(); j++) {
                        for (int ds = 0; ds < children.size(); ds++) {
                            fm += mather.get(j) + "是否为" + children.get(ds) + "的生物学母亲" + "<w:br/>";
                        }
                    }
                }
        		}

                SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");//设置日期格式

                //数量
                int qty = 0;
                //鉴定类型
                String materialType = "";
                for (int ij = 0; ij < specimenMaterialRegisterService.findmaterial(entrustRegister.getId()).size(); ij++) {
                    qty += specimenMaterialRegisterService.findmaterial(entrustRegister.getId()).get(ij).getQty();
                    materialType = specimenMaterialRegisterService.findmaterial(entrustRegister.getId()).get(ij).getMaterialType();
                }

                //被鉴定人员
                List<EntrustAbstracts> entrustAbstracts = entrustAbstractsService.findAllentrustAbstracts(entrustRegister.getId());
                List<EntrustAbstracts> entrustAbstracts3 = new ArrayList<EntrustAbstracts>();
            	if(allentrustAbstracts.size()==1){
        			for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
        				if(!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())){
        					if(entrustAbstracts2.getSpecimenCode().contains("-G")){
        						entrustAbstracts3.add(entrustAbstracts2);
        					}
        				}
        			}    
        		}
                if (entrustAbstracts.size() == 2) {
                    for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                        if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                            if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 1, entrustAbstracts2.getSpecimenCode().length()).equals("F")) {
                                entrustAbstracts3.add(entrustAbstracts2);
                            }
                        }
                    }
                    for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                        if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                            if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 1, entrustAbstracts2.getSpecimenCode().length()).equals("M")) {
                                entrustAbstracts3.add(entrustAbstracts2);
                            }
                        }
                    }
                    for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                        if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                            if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 2, entrustAbstracts2.getSpecimenCode().length()).contains("C")) {
                                entrustAbstracts3.add(entrustAbstracts2);
                            }
                        }
                    }
                    for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                        if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                            if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 1, entrustAbstracts2.getSpecimenCode().length()).equals("T")) {
                                entrustAbstracts3.add(entrustAbstracts2);
                            }
                        }
                    }
                    for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                        if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                            if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 2, entrustAbstracts2.getSpecimenCode().length()).equals("T2")) {
                                entrustAbstracts3.add(entrustAbstracts2);
                            }
                        }
                    }
                }

                if (entrustAbstracts.size() >= 3) {
                    for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                    	
                        if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                            if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 1, entrustAbstracts2.getSpecimenCode().length()).equals("F")) {
                                entrustAbstracts3.add(entrustAbstracts2);
                            }
                        }
                    }
                    for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                        	  if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                                  if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 1, entrustAbstracts2.getSpecimenCode().length()).equals("C")) {
                                      entrustAbstracts3.add(entrustAbstracts2);
                                  }
                        	  } 
                    } 
                        	   for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                             	  if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                             		 if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 2, entrustAbstracts2.getSpecimenCode().length()).equals("C2")) {
                                           entrustAbstracts3.add(entrustAbstracts2);
                                       }
                             	  }   
                        	   }  
                        	   for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                              	  if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                              		 if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 2, entrustAbstracts2.getSpecimenCode().length()).equals("C3")) {
                                            entrustAbstracts3.add(entrustAbstracts2);
                                        }
                              	  }   
                         	   }  
                        	   for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                              	  if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                              		 if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 2, entrustAbstracts2.getSpecimenCode().length()).equals("C4")) {
                                            entrustAbstracts3.add(entrustAbstracts2);
                                        }
                              	  }   
                         	   }  
                             
                        
                    
                    for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                        if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                            if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 1, entrustAbstracts2.getSpecimenCode().length()).equals("M")) {
                                entrustAbstracts3.add(entrustAbstracts2);
                            }
                        }
                    }
                    
                    for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                        if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                            if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 1, entrustAbstracts2.getSpecimenCode().length()).equals("T")) {
                                entrustAbstracts3.add(entrustAbstracts2);
                            }
                        }
                    }
                    for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                        if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                            if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 2, entrustAbstracts2.getSpecimenCode().length()).equals("T2")) {
                                entrustAbstracts3.add(entrustAbstracts2);
                            }
                        }
                    }
                    for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                        if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                            if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 2, entrustAbstracts2.getSpecimenCode().length()).equals("T3")) {
                                entrustAbstracts3.add(entrustAbstracts2);
                            }
                        }
                    }
                }

                
                String material = null;
                if (StringUtils.isNotEmpty(materialType)) {
                    material = DictUtils.getDictLabels(materialType, "material_TypeCode", "未知");
                }
                //检案摘要
                String testcase = entrustRegister.getEntrustDate() + ",";
                String parens = "";
                String chird = "";
                for (String fathers : father) {
                    parens += fathers + "、";
                }
                for (String mathers : mather) {
                    parens += mathers + "、";
                }
                for (String childrens : children) {
                    chird += childrens + "、";
                }
                
                if(entrustAbstracts.size()==1){
 				   //由我鉴定中心工作人员采集刘欣玥的手指血一份，我鉴定中心仅对样本的检验负责。
 				   testcase+=entrustRegister.getClientName()+"委托我鉴定中心对"+entrustAbstracts.get(0).getClientName()+"的DNA数据，由我鉴定中心工作人员采集"+entrustAbstracts.get(0).getClientName()
 						   +"的手指血一份，我鉴定中心仅对样本的检验负责。";
 			   }else if(entrustAbstracts.get(0).getSpecimenCode().contains("T")){
				   //的DNA是否一致，由委托方提供P000655008与P000655000的血痕各一份，我鉴定中心仅对样本的检验负责。
				   if(t3==null){
					   testcase+=entrustRegister.getClientName()+"委托我鉴定中心鉴定"+t+"和"+t2+"的DNA是否一致，由委托方提供"+t+"与"+t2+"的血痕各一份，我鉴定中心仅对样本的检验负责。";
				   }else{
					   testcase+=entrustRegister.getClientName()+"委托我鉴定中心鉴定"+t+"，"+t2+"和"+t3+"的DNA是否一致，由委托方提供"+t+"，"+t2+"与"+t3+"的血痕各一份，我鉴定中心仅对样本的检验负责。";
				   }
			   }else{
                String dd = parens + chird;
                String cc = dd.substring(0, dd.length() - 1);
                dd = cc.substring(0, cc.lastIndexOf("、")) + "与" + cc.substring(cc.lastIndexOf("、") + 1, cc.length());
                testcase += entrustRegister.getClientName() + "委托我鉴定中心对" + parens.substring(0, parens.length() - 1) + "与" + chird.substring(0, chird.length() - 1) + "进行亲子关系鉴定,由我鉴定中心工作人员采集"
                        + dd + "的" + material + "各一份，我鉴定中心仅对样本的检验负责。";
 			  }
                List<DnaExperimentStr> strList = dnaExperimentStrDao.getByRegisterId(entrustRegister.getId());

                List<String> newList = new ArrayList<String>();
                for (DnaExperimentStr dnaExperimentStr : strList) {
                    if (!newList.contains(dnaExperimentStr.getGeneLoci())) {
                        newList.add(dnaExperimentStr.getGeneLoci());
                    }
                }


                LinkedHashMap<String, LinkedHashMap<String, String>> strMap = genateMapFromList(strList);
            

                //实验报告
                List<EntrustExpertOpinion> entrustExpertOpinionList = entrustExpertOpinionDao.getByRegisterId(entrustRegister.getId());

                //分析说明
                String finaler = "";
                //鉴定意见
                String explain = "";
                if (entrustExpertOpinionList.size() > 0) {
                    for (int i = 0; i < entrustExpertOpinionList.size(); i++) {
                        finaler = entrustExpertOpinionList.get(0).getFinalVersion();
                        explain = entrustExpertOpinionList.get(0).getDraft();
                    }
                }
                Map<String, Object> beanParams = new HashMap<String, Object>();
                beanParams.put("clientName", transform(entrustRegister.getClientName()));
                beanParams.put("fm", fm.substring(0, fm.length() - 7));
                beanParams.put("df", entrustRegister.getEntrustDate());
                if (StringUtils.isNotEmpty(materialType)) {
                    String materialType1 = DictUtils.getDictLabels(materialType, "material_TypeCode", "未知");
                    beanParams.put("materialType", materialType1);
                }
                //qty
                beanParams.put("qty", qty);
                beanParams.put("entrustAbstracts", entrustAbstracts3);
                List<EntrustPerson> personList = new ArrayList<EntrustPerson>();
                for (EntrustAbstracts a1 : entrustAbstracts3) {
                    EntrustPerson b = new EntrustPerson();
                    b.setName(a1.getClientName());

                    if (StringUtils.isNotEmpty(a1.getGender())) {
                        String sex = DictUtils.getDictLabels(a1.getGender(), "sex", "未知");
                        b.setSex(sex);
                    }

                    if (StringUtils.isNotEmpty(a1.getIdType())) {
                        String idTypeCode = DictUtils.getDictLabels(a1.getIdType(), "idTypeCode", "未知");
                        b.setIdType(idTypeCode);
                    }

                    b.setIdNo(a1.getIdNo());
                    b.setSimpleCode(a1.getSpecimenCode());
                    personList.add(b);
                }

                beanParams.put("personList", personList);
                beanParams.put("testcase", transform(testcase).replaceAll("<w:br>", "<w:br/>"));
                beanParams.put("str", strMap);
                beanParams.put("explain", explain.replaceFirst(";", ";<w:br/>   ").replaceAll("\r\n\r\n", "<w:br/>   "));

                //对finaler进行转义
                finaler = finaler.replaceAll("&nbsp;", " ").replaceAll("<p>", "")
                        .replaceAll("</p>", "").replaceAll("&times;", "×")
                        .replaceAll("<w:br>", "<w:br/>").replaceAll("</w:br>", "");
                beanParams.put("finaler", finaler);
                beanParams.put("simple", getUpperDate(simple.format(new Date())));
                SimpleDateFormat year = new SimpleDateFormat("yyyy");
                beanParams.put("year", year.format(new Date()));
                //casecode
                beanParams.put("casecode", casecode);
                 String nameTo=null;  
                if(explain.contains("排除")){
                	nameTo= entrustRegister.getClientName() + "的鉴定意见书（排除）";
                }else{
                	nameTo= entrustRegister.getClientName() + "的鉴定意见书";
                }
                if(allentrustAbstracts.size()==1){
                	   writeResponse("WORD_2003", nameTo, "templateDir", "entrustExpertOpinion2.ftl", beanParams, response);
                }else{
                		writeResponse("WORD_2003", nameTo, "templateDir", "entrustExpertOpinion.ftl", beanParams, response);
                }
            }
        }

    }

    @RequestMapping(value = "downDetails")
    public void downDetails(HttpServletRequest request, HttpServletResponse response) {
    	 String registerId = request.getParameter("registerId");
        
             EntrustRegister entrustRegister = entrustRegisterService.get(registerId);
             
    	Map<String, Object> beanParams = new HashMap<String, Object>();
		String fileName = entrustRegister.getClientName() + "的委托书";
		
		beanParams.put("clientName", entrustRegister.getClientName());
		beanParams.put("clientTel", entrustRegister.getClientTel());
		beanParams.put("clientAddress", entrustRegister.getClientAddress());
		beanParams.put("clientZipcode", entrustRegister.getClientZipcode());
		beanParams.put("entrustDate", entrustRegister.getEntrustDate());
		beanParams.put("agentName", entrustRegister.getAgentName());
		beanParams.put("serverName", entrustRegister.getServerName());
		beanParams.put("agentTel", entrustRegister.getAgentTel());
		
		   String casecode= entrustRegister.getCaseCode().substring(11, 16);
	    	int ss = 0;
	    	int[] a = new int[casecode.length()];
	    	for (int i = 0; i < casecode.length(); i++) {
	    		//先由字符串转换成char,再转换成String,然后Integer
	    		a[i] = Integer.parseInt(String.valueOf(casecode.charAt(i)));
	    		if (a[i] != 0) {
	    			ss = i;
	    			break;
	    		}
	    	}
	    	int s=  Integer.parseInt(casecode);
	    	casecode = casecode.substring(ss, casecode.length());
	    	beanParams.put("simple",entrustRegister.getCaseCode().substring(3, 7));
	    	
	    	beanParams.put("casecode", s);
		
		
		Dict dict = new Dict();
		if (StringUtils.isNotEmpty(entrustRegister.getType())) {
			dict.setType("typeCode");
			dict.setValue(entrustRegister.getType());
			Dict typeCode = dictService.findValue(dict);
			beanParams.put("typeName", typeCode == null ? "" : typeCode.getLabel());
		}

		Boolean auth = entrustRegister.getAuthorizeNotification();
		if (auth != null) {
			beanParams.put("authorizeNotification", auth.booleanValue() == Boolean.TRUE ? "1" : "0");
		} else {
			beanParams.put("authorizeNotification", "");
		}
		// beanParams.put("authorizeNotification",entrustRegister.getAuthorizeNotification());
		
		beanParams.put("whether", entrustRegister.getWhether());
		
		beanParams.put("sendMode", entrustRegister.getSendMode());

		// 子类
		List<Abstracts> abstractsList = new ArrayList<Abstracts>();
		for (EntrustAbstracts entrustAbstracts : entrustRegister.getEntrustAbstractsList()) {
			Abstracts abstracts = new Abstracts();
			abstracts.setA(entrustAbstracts.getClientName());
			// 性别翻译

			if (StringUtils.isNotEmpty(entrustAbstracts.getGender())) {
				dict.setType("sex");
				dict.setValue(entrustAbstracts.getGender());
				Dict sex = dictService.findValue(dict);
				abstracts.setB(sex == null ? "" : sex.getLabel());
			}

			if (StringUtils.isNotEmpty(entrustAbstracts.getAppellation())) {
				dict.setType("appellationCode");
				dict.setValue(entrustAbstracts.getAppellation());
				Dict appellationCode = dictService.findValue(dict);
				abstracts.setC(appellationCode == null ? "" : appellationCode.getLabel());
			}

			abstracts.setD(entrustAbstracts.getBirthday());

			if (StringUtils.isNotEmpty(entrustAbstracts.getIdType())) {
				dict.setType("idTypeCode");
				dict.setValue(entrustAbstracts.getIdType());
				Dict idTypeCode = dictService.findValue(dict);
				abstracts.setE(idTypeCode == null ? "" : idTypeCode.getLabel());
			}
			abstracts.setF(entrustAbstracts.getIdNo());
			abstractsList.add(abstracts);
		}
		beanParams.put("abstractsList", abstractsList);	
		if (StringUtils.isNotEmpty(entrustRegister.getAboutMaterials())) {
			dict.setType("aboutMaterialsCode");
			dict.setValue(entrustRegister.getAboutMaterials());
			Dict aboutMaterialsCode = dictService.findValue(dict);
			beanParams.put("aboutMaterialsName", aboutMaterialsCode == null ? "" : aboutMaterialsCode.getLabel());
		}
		
		if(entrustRegister.getStandardFee()!=null){
			beanParams.put("standardFee", entrustRegister.getStandardFee());
		}else{
			beanParams.put("standardFee", "______");
		}
		
		if(StringUtils.isNotEmpty(entrustRegister.getCapital())){
			beanParams.put("capital", entrustRegister.getCapital());
		}else{
			beanParams.put("capital", "______圆");
		}

		beanParams.put("chargeway", entrustRegister.getChargeway());
		beanParams.put("sendWay", entrustRegister.getSendWay());
		beanParams.put("clientFax", entrustRegister.getClientFax());
		beanParams.put("purposeOther", entrustRegister.getPurposeOther());
		beanParams.put("materialDispose", entrustRegister.getMaterialDispose());
		// specialRequirements
		if(StringUtils.isNoneEmpty(entrustRegister.getSpecialRequirements())){
			
			beanParams.put("specialRequirements", entrustRegister.getSpecialRequirements());
		}else{
			beanParams.put("specialRequirements", "____________________");
		}
		
		// remaining
		beanParams.put("remaining", entrustRegister.getRemaining());
		// weeks
		if(StringUtils.isNoneEmpty(entrustRegister.getWeeks())){
			beanParams.put("weeks", entrustRegister.getWeeks());
		}else{
			beanParams.put("weeks", "__");
		}
		
		
		// specialFee
		
		if(entrustRegister.getSpecialFee()!=null){
			beanParams.put("specialFee", entrustRegister.getSpecialFee());
		}else{
			beanParams.put("specialFee", "_____");
		}
		// otherWay
		
		if(StringUtils.isNoneEmpty(entrustRegister.getOtherWay())){
			beanParams.put("otherWay", entrustRegister.getOtherWay());
		}else{
			beanParams.put("otherWay", "______________");
		}
	
		// timeLimitReport
		beanParams.put("timeLimitReport", entrustRegister.getTimeLimitReport());
		// clientEmail
		
		if(!StringUtils.isEmpty(entrustRegister.getClientEmail())){
			String beforeDate = entrustRegister.getClientEmail();// 这里页面传的字段是日期
				beanParams.put("year", beforeDate.substring(0, 4));
				beanParams.put("month", beforeDate.substring(5, 7));
				beanParams.put("day", beforeDate.substring(8, 10));
		}else{
			beanParams.put("year", "_____");
			beanParams.put("month", "__");
			beanParams.put("day","__");
			
		}
		// timeLimitResult
		if(StringUtils.isNoneEmpty(entrustRegister.getTimeLimitResult())){
			beanParams.put("timeLimitResult", entrustRegister.getTimeLimitResult());
		}else{
			beanParams.put("timeLimitResult", "_____");
		}
		
		// avoidAppraisers
		if(StringUtils.isNoneEmpty(entrustRegister.getAvoidAppraisers())){
			beanParams.put("avoidAppraisers", entrustRegister.getAvoidAppraisers());
		}else{
			beanParams.put("avoidAppraisers", "_______");
		}
		
		// evadingReason
		
		if(StringUtils.isNoneEmpty(entrustRegister.getEvadingReason())){
			beanParams.put("evadingReason", entrustRegister.getEvadingReason());
		}else{
			beanParams.put("evadingReason", "_______");
		}
		
		// clientReceiver
		if(StringUtils.isNoneEmpty(entrustRegister.getClientReceiver())){
			beanParams.put("clientReceiver", entrustRegister.getClientReceiver());
		}else{
			beanParams.put("clientReceiver", "_______________________");
		}
		
		
		// remarks
		beanParams.put("remarks", entrustRegister.getRemarks());
		  writeResponse("WORD_2003", entrustRegister.getClientName() + "的委托书", "templateDir", "temple.ftl", beanParams, response);
    	
    }
    
    
    

    public static void writeResponse(String version, String docFileName, String templateDir, String templateFile, Map<String, Object> beanParams, HttpServletResponse response) {
        Configuration config = new Configuration();

        InputStream is = null;
        File previewFile = null;
        Resource resource = new ClassPathResource(templateDir + File.separator + "temple.ftl");
        Writer out = null;
        ServletOutputStream outputStream = null;
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
            if ("WORD_2007".equals(version)) {
                docFileName = docFileName + ".docx";
            } else {
                docFileName = docFileName + ".doc";
            }
            //String docName = request.getSession().getServletContext().getRealPath(File.separator+docTempDir)+File.separator+docFileName;
            // 换成D盘
            String docName = "D:" + File.separator + "output" + File.separator + docFileName;
            File filePath = new File(docName).getParentFile();
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(docName);
            out = new OutputStreamWriter(fos, "UTF-8");
            template.process(beanParams, out);

            previewFile = new File(docName);
            response.reset();// 清空输出流
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            // 设置浏览器以下载的方式处理该文件默认名为resume.doc
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(docFileName, "UTF-8"));
            is = new FileInputStream(previewFile);
            outputStream = response.getOutputStream();

            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            /*if(previewFile!=null){
                previewFile.delete();
            }*/
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }

                if (is != null) {
                    is.close();
                }

                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    
    

    public static void writeResponse2(String version, String docFileName, String templateDir, String templateFile, Map<String, Object> beanParams, HttpServletResponse response) {
        Configuration config = new Configuration();

        InputStream is = null;
        File previewFile = null;
        Resource resource = new ClassPathResource(templateDir + File.separator + "listing.ftl");
        Writer out = null;
        ServletOutputStream outputStream = null;
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
            if ("WORD_2007".equals(version)) {
                docFileName = docFileName + ".docx";
            } else {
                docFileName = docFileName + ".doc";
            }
            //String docName = request.getSession().getServletContext().getRealPath(File.separator+docTempDir)+File.separator+docFileName;
            // 换成D盘
            String docName = "D:" + File.separator + "output" + File.separator + docFileName;
            File filePath = new File(docName).getParentFile();
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(docName);
            out = new OutputStreamWriter(fos, "UTF-8");
            template.process(beanParams, out);

            previewFile = new File(docName);
            response.reset();// 清空输出流
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            // 设置浏览器以下载的方式处理该文件默认名为resume.doc
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(docFileName, "UTF-8"));
            is = new FileInputStream(previewFile);
            outputStream = response.getOutputStream();

            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            /*if(previewFile!=null){
                previewFile.delete();
            }*/
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }

                if (is != null) {
                    is.close();
                }

                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    
    public static void writeResponse3(String version, String docFileName, String templateDir, String templateFile, Map<String, Object> beanParams, HttpServletResponse response) {
        Configuration config = new Configuration();

        InputStream is = null;
        File previewFile = null;
        Resource resource = new ClassPathResource(templateDir + File.separator + "circuLation.ftl");
        Writer out = null;
        ServletOutputStream outputStream = null;
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
            if ("WORD_2007".equals(version)) {
                docFileName = docFileName + ".docx";
            } else {
                docFileName = docFileName + ".doc";
            }
            //String docName = request.getSession().getServletContext().getRealPath(File.separator+docTempDir)+File.separator+docFileName;
            // 换成D盘
            String docName = "D:" + File.separator + "output" + File.separator + docFileName;
            File filePath = new File(docName).getParentFile();
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(docName);
            out = new OutputStreamWriter(fos, "UTF-8");
            template.process(beanParams, out);

            previewFile = new File(docName);
            response.reset();// 清空输出流
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            // 设置浏览器以下载的方式处理该文件默认名为resume.doc
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(docFileName, "UTF-8"));
            is = new FileInputStream(previewFile);
            outputStream = response.getOutputStream();

            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            /*if(previewFile!=null){
                previewFile.delete();
            }*/
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }

                if (is != null) {
                    is.close();
                }

                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    
    

    /**
     * @param date
     * @return
     */
    public static String getUpperDate(String date) {
        //支持yyyy-MM-dd、yyyy/MM/dd、yyyyMMdd等格式
        if (date == null) return null;
        //非数字的都去掉
        date = date.replaceAll("\\D", "");
        if (date.length() != 8) return null;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {//年
            sb.append(upper[Integer.parseInt(date.substring(i, i + 1))]);
        }
        sb.append("年");//拼接年
        int month = Integer.parseInt(date.substring(4, 6));
        if (month <= 10) {
            sb.append(upper[month]);
        } else {
            sb.append("十").append(upper[month % 10]);
        }
        sb.append("月");//拼接月

        int day = Integer.parseInt(date.substring(6));
        if (day <= 10) {
            sb.append(upper[day]);
        } else if (day < 20) {
            sb.append("十").append(upper[day % 10]);
        } else {
            sb.append(upper[day / 10]).append("十");
            int tmp = day % 10;
            if (tmp != 0) sb.append(upper[tmp]);
        }
        sb.append("日");//拼接日
        return sb.toString();
    }




    /**
     * 处理转义字符
     *
     * @param str
     * @return
     */
    private String transform(String str) {

        if (str.contains("<") || str.contains(">") || str.contains("&")) {
            str = str.replaceAll("&", "&amp;");
            str = str.replaceAll("<", "&lt;");
            str = str.replaceAll(">", "&gt;");
        }

        return str;
    }


    private LinkedHashMap<String, LinkedHashMap<String, String>> genateMapFromList(List<DnaExperimentStr> strList) {

        LinkedHashMap<String, LinkedHashMap<String, String>> strMapList = new LinkedHashMap<String, LinkedHashMap<String, String>>();
        for (DnaExperimentStr str : strList) {
            String geneLoci = str.getGeneLoci();
            String specimenCode = str.getSpecimenCode();
            String x = str.getX();
            String y = str.getY();
            if (strMapList.containsKey(geneLoci)) {
                LinkedHashMap<String, String> map = strMapList.get(geneLoci);
                map.put(specimenCode, x + "  /  " + (y));
                map.put(specimenCode + "x", x);
                map.put(specimenCode + "y", y);
            } else {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put(specimenCode, x + "  /  " + (y));
                map.put(specimenCode + "x", x);
                map.put(specimenCode + "y", y);
                strMapList.put(geneLoci, map);
            }
        }
        return strMapList;
    }

    private Map<String, String> genateMapFromList1(List<DnaExperimentStr> strList) {

        List<String> newList = new ArrayList<String>();
        for (DnaExperimentStr dnaExperimentStr : strList) {
            if (!newList.contains(dnaExperimentStr.getGeneLoci())) {
                newList.add(dnaExperimentStr.getGeneLoci());
            }
        }
        Map<String, String> strMapList = new HashMap<String, String>();
        for (DnaExperimentStr str : strList) {
            String x = str.getX();
            String y = str.getY();
            for (String string : newList) {
                String geneLoci = str.getGeneLoci();
                if (geneLoci.equals(string)) {
                    strMapList.put(geneLoci, x + "   " + (y));
                }
            }
        }
        return strMapList;
    }

    @RequiresPermissions("entrust:entrustRegister:edit")
    @RequestMapping(value = "entrustRegisterReceipt")
    public String entrustRegisterReceipt(EntrustRegister entrustRegister, Model model) {
        List<EntrustAbstracts> entrustAbstracts = entrustAbstractsService.findAllentrustAbstracts(entrustRegister.getId());
        model.addAttribute("entrustAbstracts", entrustAbstracts);
        model.addAttribute("entrustRegister", entrustRegister);
        return "modules/entrust/entrustRegisterReceipt";
    }

    //归档 实验室记录表
    @RequiresPermissions("entrust:entrustRegister:edit")
    @RequestMapping(value = "findBoard")
    public String findBoard(EntrustRegister entrustRegister, Model model) {

        List<EntrustAbstracts> allentrustAbstracts = entrustAbstractsService.findAllentrustAbstracts(entrustRegister.getId());
        List<DnaBoardJgg> dnaBoardJgg = dnaBoardJggDao.findBoard(allentrustAbstracts.get(0).getSpecimenCode());
        //委托单下的样品号
        allentrustAbstracts.get(0).getSpecimenCode();
        //电泳版
        DnaBoard board = dnaBoardService.get(dnaBoardJgg.get(0).getBoard().getId());
        model.addAttribute("board", board);
        //提取室
        List<DnaExtractRecordItem> dnaExtractRecordItems = dnaExtractRecordItemDao.findDnaExtractRecordItem(allentrustAbstracts.get(0).getSpecimenCode());
        DnaExtractRecord dnaExtractRecord = dnaExtractRecordService.get(dnaExtractRecordItems.get(0).getRecord().getId());
        model.addAttribute("dnaExtractRecord", dnaExtractRecord);
        //扩增室
        List<DnaPreparationReagentsIteam> dnaPreparationReagentsIteams = dnaPreparationReagentsIteamDao.findNumber(allentrustAbstracts.get(0).getSpecimenCode());
        DnaPreparationReagents dnaPreparationReagents = dnaPreparationReagentsService.get(dnaPreparationReagentsIteams.get(0).getDnaPreparationReagents().getId());
        model.addAttribute("dnaPreparationReagents", dnaPreparationReagents);
		//电泳室
		DnaElectrophoresisParting dnaElectrophoresisParting=dnaElectrophoresisPartingService.findCode(board.getId()).get(0);
		model.addAttribute("dnaElectrophoresisParting", dnaElectrophoresisParting);
        return "modules/entrust/labrecords";
    }


    //提取
    @RequiresPermissions("entrust:entrustRegister:edit")
    @RequestMapping(value = "findDnaExtract")

    public String finddnaExtract(EntrustRegister entrustRegister, Model model) {

        List<EntrustAbstracts> allentrustAbstracts = entrustAbstractsService.findAllentrustAbstracts(entrustRegister.getId());

        //委托单下的样品号
        allentrustAbstracts.get(0).getSpecimenCode();

        //提取室
        List<DnaExtractRecordItem> dnaExtractRecordItems = dnaExtractRecordItemDao.findDnaExtractRecordItem(allentrustAbstracts.get(0).getSpecimenCode());
        DnaExtractRecord dnaExtractRecord = dnaExtractRecordService.get(dnaExtractRecordItems.get(0).getRecord().getId());
        model.addAttribute("dnaExtractRecord", dnaExtractRecord);
        return "modules/entrust/dnaExtract";
    }

    //扩增
    @RequiresPermissions("entrust:entrustRegister:edit")
    @RequestMapping(value = "findDnaPreparation")

    public String findDnaPreparation(EntrustRegister entrustRegister, Model model) {

        List<EntrustAbstracts> allentrustAbstracts = entrustAbstractsService.findAllentrustAbstracts(entrustRegister.getId());

        //委托单下的样品号
        allentrustAbstracts.get(0).getSpecimenCode();
        List<DnaPreparationReagentsIteam> dnaPreparationReagentsIteams = dnaPreparationReagentsIteamDao.findNumber(allentrustAbstracts.get(0).getSpecimenCode());
        DnaPreparationReagents dnaPreparationReagents = dnaPreparationReagentsService.get(dnaPreparationReagentsIteams.get(0).getDnaPreparationReagents().getId());
        model.addAttribute("dnaPreparationReagents", dnaPreparationReagents);
        return "modules/entrust/dnaPreparation";
    }

    //电泳dnaExtractRecord
    @RequiresPermissions("entrust:entrustRegister:edit")
    @RequestMapping(value = "findDnaPreparationReagents")

    public String findDnaPreparationReagents(EntrustRegister entrustRegister, Model model) {

        List<EntrustAbstracts> allentrustAbstracts = entrustAbstractsService.findAllentrustAbstracts(entrustRegister.getId());

        List<DnaBoardJgg> dnaBoardJgg = dnaBoardJggDao.findBoard(allentrustAbstracts.get(0).getSpecimenCode());
        //委托单下的样品号
        allentrustAbstracts.get(0).getSpecimenCode();
        //电泳版
        DnaBoard board = dnaBoardService.get(dnaBoardJgg.get(0).getBoard().getId());
        DnaElectrophoresisParting dnaElectrophoresisParting=dnaElectrophoresisPartingService.findCode(board.getCode()).get(0);
        model.addAttribute("dnaElectrophoresisParting", dnaElectrophoresisParting);
        model.addAttribute("board", board);
        return "modules/entrust/dnaPreparationReagents";
    }


    @RequiresPermissions("entrust:entrustRegister:edit")
    @RequestMapping(value = "save")
    public String save(EntrustRegister entrustRegister, Model model, HttpServletResponse response, RedirectAttributes redirectAttributes, String remainingq, String timeLimitReportq, String whetherq, String sendWayq, String materialDisposeq,HttpServletRequest request) throws Exception {
        if (!beanValidator(model, entrustRegister)) {
            return form(entrustRegister, model);
        }
        if(entrustRegister.getEntrustAbstractsList().size()==0){
        	throw new RuntimeException("实验人请添加");
        }else{
        	entrustRegister.setRemaining(remainingq);
        	entrustRegister.setTimeLimitReport(timeLimitReportq);
        	entrustRegister.setWhether(whetherq);
        	entrustRegister.setSendWay(sendWayq);
        	entrustRegister.setMaterialDispose(materialDisposeq);
        	entrustRegisterService.save(entrustRegister, response,request);
        	addMessage(redirectAttributes, "保存受理成功");
        }
        return "redirect:" + adminPath + "/act/task/todo/";
     
        
    }

    @RequiresPermissions("entrust:entrustRegister:edit")
    @RequestMapping(value = "delete")
    public String delete(EntrustRegister entrustRegister, RedirectAttributes redirectAttributes) {
        entrustRegisterService.delete(entrustRegister);
        addMessage(redirectAttributes, "删除保存成功成功");
        return "redirect:" + Global.getAdminPath() + "/entrust/entrustRegister/?repage";
    }

    
    @RequestMapping(value = "readcard")
    public String card(HttpServletRequest request, HttpServletResponse response, Model model) {
        String number = null;
        ReadCard t = new ReadCard();
        for (int i = 0; i < 10000; i++) {
            number = t.ss();
            if (number != null && !number.equals("")) {
                break;
            }
        }

        SpecimenXueka specimenXueka = new SpecimenXueka();
        specimenXueka.setXuekaId(number);
        String registerId = null;
        SpecimenXueka xueka = specimenXuekaService.get(number);
        registerId = xueka.getMaterialRegisterItemId();
        EntrustRegister entrustRegister = new EntrustRegister();
        entrustRegister.setCode(registerId);
        Page<EntrustRegister> page = entrustRegisterService.findPage(new Page<EntrustRegister>(request, response), entrustRegister);
        model.addAttribute("page", page);
        return "modules/entrust/entrustRegisterList";
    }

    
    
    //流转单
    @RequestMapping(value = "sheeet")
    public String sheet(EntrustRegister entrustRegister, Model model, EntrustFlowSheet entrustFlowSheet){
        if (entrustFlowSheetDao.findEntrust(entrustRegister.getId()) != null) {
            entrustFlowSheet = entrustFlowSheetDao.findEntrust(entrustRegister.getId());
        } else {
            entrustFlowSheet = new EntrustFlowSheet();
        }
        model.addAttribute("entrustId", entrustRegister.getId());
        model.addAttribute("entrustFlowSheet", entrustFlowSheet);
        return "modules/entrust_flow_sheet/entrust/entrustFlowSheetForm";
    }


    //执业证
    @RequestMapping(value = "licensed")
    public String licensed(EntrustRegister entrustRegister, Model model, Licensed licensed) {
    	File fileCer=new File("D:\\information\\DNA\\"+entrustRegister.getCode()+"\\certificate of dna.pdf");
    	if(fileCer.exists()){
    		System.out.println("nimei");
    	}else{
    	Word2007ToHtml word2007ToHtml=new Word2007ToHtml();
        List<Licensed> licenseds = licensedDao.findEntrust(entrustRegister.getId());
        if(licenseds.size()>1){
        	model.addAttribute("licenseds1Id", licenseds.get(0).getUser().getId());
        	model.addAttribute("licenseds1Name",licenseds.get(0).getUser().getName());
        	model.addAttribute("licenseds2Id", licenseds.get(1).getUser().getId());
        	model.addAttribute("licenseds2Name",licenseds.get(1).getUser().getName());
        	
        	licenseds.get(0).getUserBy();
        	licenseds.get(1).getUserBy();
        	String imagesPath="D:\\information\\user\\"+licenseds.get(0).getUserBy()+"\\certificate";
             	List<String>mapPics=new ArrayList<String>();
             	File mFile = new File(imagesPath);
             	if (mFile.exists() && mFile.isDirectory()) {
             		List<File> mlist = new ArrayList<File>();
             		getAllFile(mFile, mlist);
             		// 已经获取了所有图片
             		for (File file2 : mlist) {
             			mapPics.add(file2.getAbsolutePath());
             		}
             	}
             	String imagesPath2="D:\\information\\user\\"+licenseds.get(1).getUserBy()+"\\certificate";
             	File mFile2 = new File(imagesPath2);
             	if (mFile2.exists() && mFile2.isDirectory()){
             		List<File> mlist2 = new ArrayList<File>();
             		getAllFile(mFile2, mlist2);
             		// 已经获取了所有图片
             		for (File file2 : mlist2) {
             			mapPics.add(file2.getAbsolutePath());
             		}
             	}
             	String FILEPATH="D:\\information\\DNA\\"+entrustRegister.getCode()+"\\";
             	word2007ToHtml.imagesToPdf2(FILEPATH, "certificate of dna", mapPics.get(0), mapPics.get(1));
        	//certificate of dna.pdf
        
        }
        	
      }
        
        
       
        

        return "modules/entrust/entrustLicensed";
    }
    

    //收费凭据
    @RequestMapping(value = "chargeCredentials")
    public String chargeCredentials(EntrustRegister entrustRegister, Model model, EntrustChargeCredentials entrustChargeCredentials) {
        if (entrustChargeCredentialsDao.findEntrust(entrustRegister.getId()) != null) {
            entrustChargeCredentials = entrustChargeCredentialsDao.findEntrust(entrustRegister.getId());
        } else {
            entrustChargeCredentials = new EntrustChargeCredentials();
        }
        model.addAttribute("entrustId", entrustRegister.getId());

        model.addAttribute("entrustChargeCredentials", entrustChargeCredentials);
        return "modules/entrust/entrustChargeCredentialsForm";
    }

    //导出
    @RequestMapping(value = "export")
    public String export(EntrustRegister entrustRegister, HttpServletResponse response) {

        List<EntrustRegister> entrustRgisters = entrustRegisterService.findExport(entrustRegister);

        entrustRegisterService.export(response, entrustRgisters, entrustRegister);


        return null;
    }

    //计算表格  calculate
    @RequestMapping(value = "calculate")
    public String calculate(EntrustRegister entrustRegister, Model model) {
        List<DnaPiResult> results = dnaPiResultService.getRgister(entrustRegister.getId());

        List<DnaPiResultItem> fItemList = new ArrayList<DnaPiResultItem>();
        List<DnaPiResultItem> mItemList = new ArrayList<DnaPiResultItem>();
        List<DnaPiResultItem> fItemList2 = new ArrayList<DnaPiResultItem>();
        List<DnaPiResultItem> mItemList2= new ArrayList<DnaPiResultItem>();
        List<DnaPiResultItem> fItemList3 = new ArrayList<DnaPiResultItem>();
        List<DnaPiResultItem> mItemList3= new ArrayList<DnaPiResultItem>();
        List<DnaPiResultItem> fItemList4 = new ArrayList<DnaPiResultItem>();
        List<DnaPiResultItem> mItemList4= new ArrayList<DnaPiResultItem>();
BigDecimal rcpFC=null;
BigDecimal cpiFC=null;
BigDecimal rcpMC=null;
BigDecimal cpiMC=null;
BigDecimal rcpFC2=null;
BigDecimal cpiFC2=null;
BigDecimal rcpMC2=null;
BigDecimal cpiMC2=null;
BigDecimal rcpFC3=null;
BigDecimal cpiFC3=null;
BigDecimal rcpMC3=null;
BigDecimal cpiMC3=null;
BigDecimal rcpFC4=null;
BigDecimal cpiFC4=null;
BigDecimal rcpMC4=null;
BigDecimal cpiMC4=null;

for(DnaPiResult result:results){
	
	 List<DnaExperimentStr> strList = dnaExperimentStrDao.getByRegisterId(entrustRegister.getId());
     LinkedHashMap<String, LinkedHashMap<String, String>> strMap = genateMapFromList1(strList,result.getDnaPiResultItemList());
     model.addAttribute("str", strMap);
            //f--c   s.indexOf('(')-1, s.indexOf('(')
            if (result.getChildCode().substring(result.getChildCode().indexOf('(')-1,result.getChildCode().indexOf('(')).equals("C")&&result.getParentCode().contains("-F")) {
            	rcpFC=result.getRcp();
            	cpiFC=result.getCpi();
            	fItemList = result.getDnaPiResultItemList();
            }
            //m--c
            if (result.getChildCode().substring(result.getChildCode().indexOf('(')-1,result.getChildCode().indexOf('(')).equals("C")&&result.getParentCode().contains("-M")) {
            	rcpMC=result.getRcp();
            	cpiMC=result.getCpi();
            	mItemList = result.getDnaPiResultItemList();
            }
            
            //f--c2   s.indexOf('(')-1, s.indexOf('(')
            if (result.getChildCode().substring(result.getChildCode().indexOf('(')-2,result.getChildCode().indexOf('(')).equals("C2")&&result.getParentCode().contains("-F")) {
            	rcpFC2=result.getRcp();
            	cpiFC2=result.getCpi();
            	fItemList2 = result.getDnaPiResultItemList();
            }
            //m--c2
            if (result.getChildCode().substring(result.getChildCode().indexOf('(')-2,result.getChildCode().indexOf('(')).equals("C2")&&result.getParentCode().contains("-M")) {
            	rcpMC2=result.getRcp();
            	cpiMC2=result.getCpi();
            	mItemList2= result.getDnaPiResultItemList();
            }
            //f--c2   s.indexOf('(')-1, s.indexOf('(')
            if (result.getChildCode().substring(result.getChildCode().indexOf('(')-2,result.getChildCode().indexOf('(')).equals("C2")&&result.getParentCode().contains("-F")) {
            	rcpFC2=result.getRcp();
            	cpiFC2=result.getCpi();
            	fItemList2 = result.getDnaPiResultItemList();
            }
            //m--c2
            if (result.getChildCode().substring(result.getChildCode().indexOf('(')-2,result.getChildCode().indexOf('(')).equals("C2")&&result.getParentCode().contains("-M")) {
            	rcpMC2=result.getRcp();
            	cpiMC2=result.getCpi();
            	mItemList2= result.getDnaPiResultItemList();
            }
            
            //f--c3   s.indexOf('(')-1, s.indexOf('(')
            if (result.getChildCode().substring(result.getChildCode().indexOf('(')-2,result.getChildCode().indexOf('(')).equals("C3")&&result.getParentCode().contains("-F")) {
            	rcpFC3=result.getRcp();
            	cpiFC3=result.getCpi();
            	fItemList3 = result.getDnaPiResultItemList();
            }
            //m--c3
            if (result.getChildCode().substring(result.getChildCode().indexOf('(')-2,result.getChildCode().indexOf('(')).equals("C3")&&result.getParentCode().contains("-M")) {
            	rcpMC3=result.getRcp();
            	cpiMC3=result.getCpi();
            	mItemList3= result.getDnaPiResultItemList();
            }
            
          //f--c4   s.indexOf('(')-1, s.indexOf('(')
            if (result.getChildCode().substring(result.getChildCode().indexOf('(')-2,result.getChildCode().indexOf('(')).equals("C3")&&result.getParentCode().contains("-F")) {
            	rcpFC4=result.getRcp();
            	cpiFC4=result.getCpi();
            	fItemList4 = result.getDnaPiResultItemList();
            }
            //m--c4
            if (result.getChildCode().substring(result.getChildCode().indexOf('(')-2,result.getChildCode().indexOf('(')).equals("C3")&&result.getParentCode().contains("-M")) {
            	rcpMC4=result.getRcp();
            	cpiMC4=result.getCpi();
            	mItemList4= result.getDnaPiResultItemList();
            }
            
            if (result.getParentCode().contains("-G")) {
            	rcpFC=result.getRcp();
            	cpiFC=result.getCpi();
            	fItemList = result.getDnaPiResultItemList();
            }
            
        }

if(rcpFC!=null){
	model.addAttribute("rcpFC", rcpFC.setScale(10,BigDecimal.ROUND_HALF_UP));
	model.addAttribute("cpiFC", cpiFC.setScale(10,BigDecimal.ROUND_HALF_UP));
	model.addAttribute("fItemList", fItemList);
}
if(rcpMC!=null){
	model.addAttribute("rcpMC", rcpMC.setScale(10,BigDecimal.ROUND_HALF_UP));
		model.addAttribute("cpiMC", cpiMC.setScale(10,BigDecimal.ROUND_HALF_UP));    
		model.addAttribute("mItemList", mItemList);
}
if(rcpFC2!=null){
	
	model.addAttribute("rcpFC2", rcpFC2.setScale(10,BigDecimal.ROUND_HALF_UP));
	model.addAttribute("cpiFC2", cpiFC2.setScale(10,BigDecimal.ROUND_HALF_UP));
	model.addAttribute("fItemList2", fItemList2);
}
if(rcpMC2!=null){
	    model.addAttribute("mItemList2", mItemList2);
		model.addAttribute("rcpMC2", rcpMC2.setScale(10,BigDecimal.ROUND_HALF_UP));
		model.addAttribute("cpiMC2", cpiMC2.setScale(10,BigDecimal.ROUND_HALF_UP));
	
}
if(rcpFC3!=null){
	model.addAttribute("rcpFC3", rcpFC3.setScale(10,BigDecimal.ROUND_HALF_UP));
		model.addAttribute("cpiFC3", cpiFC3.setScale(10,BigDecimal.ROUND_HALF_UP));
		model.addAttribute("fItemList3", fItemList3);
}
if(rcpMC3!=null){
	
	model.addAttribute("rcpMC3", rcpMC3.setScale(10,BigDecimal.ROUND_HALF_UP));
	model.addAttribute("cpiMC3", cpiMC3.setScale(10,BigDecimal.ROUND_HALF_UP));
	model.addAttribute("mItemList3", mItemList3);
}
 	if(rcpFC4!=null){
 		model.addAttribute("fItemList4", fItemList4);
 		model.addAttribute("rcpFC4", rcpFC4.setScale(10,BigDecimal.ROUND_HALF_UP));
 		model.addAttribute("cpiFC4", cpiFC4.setScale(10,BigDecimal.ROUND_HALF_UP));
 	}

 	if(rcpMC4!=null){
 		model.addAttribute("rcpMC4", rcpMC4.setScale(10,BigDecimal.ROUND_HALF_UP));
 		model.addAttribute("cpiMC4", cpiMC4.setScale(10,BigDecimal.ROUND_HALF_UP));
 		model.addAttribute("mItemList4", mItemList4);
 	}
        
       
        //被鉴定人员
        List<EntrustAbstracts> entrustAbstracts = entrustAbstractsService.findAllentrustAbstracts(entrustRegister.getId());
        List<EntrustAbstracts> entrustAbstracts3 = new ArrayList<EntrustAbstracts>();
        List<EntrustAbstracts> entrustAbstracts4= new ArrayList<EntrustAbstracts>();
        List<EntrustAbstracts> entrustAbstracts5= new ArrayList<EntrustAbstracts>();
        List<EntrustAbstracts> entrustAbstracts6= new ArrayList<EntrustAbstracts>();
        List<EntrustAbstracts> entrustAbstracts7= new ArrayList<EntrustAbstracts>();
        
        
        if (entrustAbstracts.size() == 1) {
            for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                    if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 1, entrustAbstracts2.getSpecimenCode().length()).equals("G")) {
                        entrustAbstracts7.add(entrustAbstracts2);
                    }
                }
            }
        } 
        
        
        
        if (entrustAbstracts.size() == 2) {
            for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                    if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 1, entrustAbstracts2.getSpecimenCode().length()).equals("F")) {
                        entrustAbstracts3.add(entrustAbstracts2);
                    }
                }
            }
            for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                    if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 1, entrustAbstracts2.getSpecimenCode().length()).equals("M")) {
                        entrustAbstracts3.add(entrustAbstracts2);
                    }
                }
            }
            for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                    if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 2, entrustAbstracts2.getSpecimenCode().length()).contains("C")) {
                        entrustAbstracts3.add(entrustAbstracts2);
                    }
                }
            }
        }


        if (entrustAbstracts.size() >= 3) {
            for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                    if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 1, entrustAbstracts2.getSpecimenCode().length()).equals("F")) {
                        entrustAbstracts3.add(entrustAbstracts2);
                    }
                }
            }
            for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                    if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 1, entrustAbstracts2.getSpecimenCode().length()).equals("C")) {
                        entrustAbstracts3.add(entrustAbstracts2);
                    }
                    if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 2, entrustAbstracts2.getSpecimenCode().length()).equals("C2")) {
                        entrustAbstracts4.add(entrustAbstracts2);
                    }
                    if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 2, entrustAbstracts2.getSpecimenCode().length()).equals("C3")) {
                        entrustAbstracts5.add(entrustAbstracts2);
                    }
                    if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 2, entrustAbstracts2.getSpecimenCode().length()).equals("C4")) {
                        entrustAbstracts6.add(entrustAbstracts2);
                    }
                    
                }
            }
            for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts){
                if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                    if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 1, entrustAbstracts2.getSpecimenCode().length()).equals("M")) {
                        entrustAbstracts3.add(entrustAbstracts2);
                    }
                }
            }
        }
        model.addAttribute("entrustAbstracts", entrustAbstracts3);
        model.addAttribute("entrustAbstracts4", entrustAbstracts4); 
        model.addAttribute("entrustAbstracts5", entrustAbstracts5);
        model.addAttribute("entrustAbstracts6", entrustAbstracts6);
        model.addAttribute("entrustAbstracts7", entrustAbstracts7);
        return "modules/entrust/entrustCalculate";
    }

    private LinkedHashMap<String, LinkedHashMap<String, String>> genateMapFromList1(List<DnaExperimentStr> strList,List<DnaPiResultItem> dnaPiResultItemList) {
    boolean n=false;
    	for (DnaPiResultItem dnaPiResultItem : dnaPiResultItemList) {
			if(dnaPiResultItem.getLoci()!=null){
				n=true;
				break;
			}
		}
    	LinkedHashMap<String, LinkedHashMap<String, String>> strMapList = new LinkedHashMap<String, LinkedHashMap<String, String>>();
    	if(n==false){
             for (DnaExperimentStr str : strList) {
                 String geneLoci = str.getGeneLoci();
                 String specimenCode = str.getSpecimenCode();
                 String x = str.getX();
                 String y = str.getY();
                 if (strMapList.containsKey(geneLoci)) {
                     LinkedHashMap<String, String> map = strMapList.get(geneLoci);
                     map.put(specimenCode, x + "   " + (y));
                     map.put(specimenCode + "x     ", x);
                     map.put(specimenCode + "y     ", y);
                 } else {
                     LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                     map.put(specimenCode, x + "    " + (y));
                     map.put(specimenCode + "x    ", x);
                     map.put(specimenCode + "y     ", y);
                     strMapList.put(geneLoci, map);
                 }
             }
    	}else{
    		
            for (DnaExperimentStr str : strList) {
            	String geneLoci = null;
            	for (DnaPiResultItem dnaPiResultItem : dnaPiResultItemList) {
            		if(dnaPiResultItem.getGeneLoci().equals(str.getGeneLoci())){
            			geneLoci=str.getGeneLoci();
            		}
            		if(dnaPiResultItem.getGeneLoci().substring(0, dnaPiResultItem.getGeneLoci().length()-1).equals(str.getGeneLoci())){
            			geneLoci=dnaPiResultItem.getGeneLoci();
            		}
            	}
                String specimenCode = str.getSpecimenCode();
                String x = str.getX();
                String y = str.getY();
                if (strMapList.containsKey(geneLoci)) {
                    LinkedHashMap<String, String> map = strMapList.get(geneLoci);
                    map.put(specimenCode, x + "      " + (y));
                    map.put(specimenCode + "x      ", x);
                    map.put(specimenCode + "y      ", y);
                } else {
                    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                    map.put(specimenCode, x + "     " + (y));
                    map.put(specimenCode + "x     ", x);
                    map.put(specimenCode + "y     ", y);
                    strMapList.put(geneLoci, map);
                }
            }
    		
    	}
    	
    	 
         return strMapList;
     }
    
	
	//list 去重
		public static List<String> removeDuplicate(List<String> list){  
	        List<String> listTemp = new ArrayList<String>();  
	        for(int i=0;i<list.size();i++){  
	            if(!listTemp.contains(list.get(i))){  
	                listTemp.add(list.get(i));  
	            }  
	        }  
	        return listTemp;  
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
		 
/*//打印
		 private static void printDown(File file) {
				try {
					PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
					if (file != null) {
						DocPrintJob jon = printService.createPrintJob();
						// 设置纸张大小,也可以新建MediaSize类来自定义大小
						// 文件类型
						DocFlavor flavor = DocFlavor.INPUT_STREAM.PNG;
		 
						PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
						pras.add(MediaSizeName.ISO_A4);
						DocAttributeSet das = new HashDocAttributeSet();
		 
						InputStream input = new FileInputStream(file);
						Doc doc = new SimpleDoc(input, flavor, das);
						jon.print(doc, pras);				
					}
				} catch (PrintException | FileNotFoundException e) {
					e.printStackTrace();
				}
			}*/
		  //实验报告  正稿
		    @RequiresPermissions("entrust:entrustRegister:view")
		    @RequestMapping(value = "report")
		    public String report(EntrustRegister entrustRegister, Model model) {
		        SimpleDateFormat simple = new SimpleDateFormat("yyyy");
		        //DNA2017071100057
		        String casecode = entrustRegister.getCaseCode().substring(11, 16);
		        int ss = 0;
		        int[] a = new int[casecode.length()];
		        for (int i = 0; i < casecode.length(); i++) {
		            //先由字符串转换成char,再转换成String,然后Integer
		            a[i] = Integer.parseInt(String.valueOf(casecode.charAt(i)));
		            if (a[i] != 0) {
		                ss = i;
		                break;
		            }
		        }
		        casecode = casecode.substring(ss, casecode.length());
		        //委托事项
		        String fm = " ";
		        List<EntrustAbstracts> allentrustAbstracts = entrustAbstractsService.findAllentrustAbstracts(entrustRegister.getId());
		        List<String> father = new ArrayList<String>();
		        List<String> mather = new ArrayList<String>();
		        List<String> children = new ArrayList<String>();
		        
		        if(allentrustAbstracts.size()==1){
					//分析刘欣玥的DNA数据
					fm="分析"+allentrustAbstracts.get(0).getClientName()+"的DNA数据";
				}else{
					
				
		        for (int i = 0; i < allentrustAbstracts.size(); i++) {
		            String appellationname = allentrustAbstracts.get(i).getAppellation();
		            String ClientName = allentrustAbstracts.get(i).getClientName();
		            if (appellationname.equals("1")) {
		                father.add(ClientName);
		            }
		            if (appellationname.equals("2")) {
		                mather.add(ClientName);
		            }
		            if (appellationname.equals("3")) {
		                children.add(ClientName);
		            }
		        }
		        if (father.size() != 0) {
		            for (int j = 0; j < father.size(); j++) {
		                for (int ds = 0; ds < children.size(); ds++) {
		                    fm += father.get(j) + "是否为" + children.get(ds) + "的生物学父亲<br/>";//去掉<br/>特殊字符 导出模板会报错
		                }
		            }
		        }
		        if (mather.size() > 0) {
		            for (int j = 0; j < mather.size(); j++) {
		                for (int ds = 0; ds < children.size(); ds++) {
		                    fm += mather.get(j) + "是否为" + children.get(ds) + "的生物学母亲<br/>";//去掉<br/>特殊字符 导出模板会报错
		                }
		            }
		        }
			} 
		        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");//设置日期格式

		        //数量
		        int qty = 0;
		        //鉴定类型
		        String materialType = "";
		        for (int ij = 0; ij < specimenMaterialRegisterService.findmaterial(entrustRegister.getId()).size(); ij++) {
		            qty += specimenMaterialRegisterService.findmaterial(entrustRegister.getId()).get(ij).getQty();
		            materialType = specimenMaterialRegisterService.findmaterial(entrustRegister.getId()).get(ij).getMaterialType();
		        }

		        //被鉴定人员
		        List<EntrustAbstracts> entrustAbstracts = entrustAbstractsService.findAllentrustAbstracts(entrustRegister.getId());
		        List<EntrustAbstracts> entrustAbstracts3 = new ArrayList<EntrustAbstracts>();
		        
		    	if(entrustAbstracts.size()==1){
					for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
						if(!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())){
							if(entrustAbstracts2.getSpecimenCode().contains("-G")){
								entrustAbstracts3.add(entrustAbstracts2);
							}
						}
					}    
				}
		        if (entrustAbstracts.size() == 2) {
		            for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
		                if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
		                    if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 1, entrustAbstracts2.getSpecimenCode().length()).equals("F")) {
		                        entrustAbstracts3.add(entrustAbstracts2);
		                    }
		                }
		            }
		            for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
		                if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
		                    if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 1, entrustAbstracts2.getSpecimenCode().length()).equals("M")) {
		                        entrustAbstracts3.add(entrustAbstracts2);
		                    }
		                }
		            }
		            for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
		                if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
		                    if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 2, entrustAbstracts2.getSpecimenCode().length()).contains("C")) {
		                        entrustAbstracts3.add(entrustAbstracts2);
		                    }
		                }
		            }
		        }


		        if (entrustAbstracts.size() >= 3) {
		            for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
		                if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
		                    if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 1, entrustAbstracts2.getSpecimenCode().length()).equals("F")) {
		                        entrustAbstracts3.add(entrustAbstracts2);
		                    }
		                }
		            }
		            for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
		                	  if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
		                          if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 1, entrustAbstracts2.getSpecimenCode().length()).equals("C")) {
		                              entrustAbstracts3.add(entrustAbstracts2);
		                          }
		                	  } 
		            } 
		                	   for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
		                     	  if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
		                     		 if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 2, entrustAbstracts2.getSpecimenCode().length()).equals("C2")) {
		                                   entrustAbstracts3.add(entrustAbstracts2);
		                               }
		                     	  }   
		                	   }  
		                	   for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
		                      	  if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
		                      		 if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 2, entrustAbstracts2.getSpecimenCode().length()).equals("C3")) {
		                                    entrustAbstracts3.add(entrustAbstracts2);
		                                }
		                      	  }   
		                 	   }  
		                	   for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
		                      	  if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
		                      		 if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 2, entrustAbstracts2.getSpecimenCode().length()).equals("C4")) {
		                                    entrustAbstracts3.add(entrustAbstracts2);
		                                }
		                      	  }   
		                 	   }  
		                     
		                
		            
		            for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
		                if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
		                    if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 1, entrustAbstracts2.getSpecimenCode().length()).equals("M")) {
		                        entrustAbstracts3.add(entrustAbstracts2);
		                    }
		                }
		            }
		        }


		        //检案摘要
		        String testcase = entrustRegister.getEntrustDate()+ ",";
		        String parens = "";
		        String chird = "";
		        for (String fathers : father) {
		            parens += fathers + "、";
		        }
		        for (String mathers : mather) {
		            parens += mathers + "、";
		        }
		        for (String childrens : children) {
		            chird += childrens + "、";
		        }
		        

				   if(entrustAbstracts.size()==1){
					   //由我鉴定中心工作人员采集刘欣玥的手指血一份，我鉴定中心仅对样本的检验负责。
					   testcase+=entrustRegister.getClientName()+"委托我鉴定中心对"+entrustAbstracts.get(0).getClientName()+"的DNA数据，由我鉴定中心工作人员采集"+entrustAbstracts.get(0).getClientName()
							   +"的手指血一份，我鉴定中心仅对样本的检验负责。";
				   }else{
					   
		        
		        String dd = parens + chird;
		        String cc = dd.substring(0, dd.length() - 1);
		        dd = cc.substring(0, cc.lastIndexOf("、")) + "与" + cc.substring(cc.lastIndexOf("、") + 1, cc.length());
		        testcase += entrustRegister.getClientName() + "委托我鉴定中心对" + parens.substring(0, parens.length() - 1) + "与" + chird.substring(0, chird.length() - 1) + "进行亲子关系鉴定,由我鉴定中心工作人员采集"
		                + dd + "的手指血各一份，我鉴定中心仅对样本的检验负责。" + "<w:br/>";
				   }
				   
		        List<DnaExperimentStr> strList = dnaExperimentStrDao.getByRegisterId(entrustRegister.getId());

		        LinkedHashMap<String, LinkedHashMap<String, String>> strMap = genateMapFromList(strList);

		        List<String> newList = new ArrayList<String>();
		        for (DnaExperimentStr dnaExperimentStr : strList) {
		            if (!newList.contains(dnaExperimentStr.getGeneLoci())) {
		                newList.add(dnaExperimentStr.getGeneLoci());
		            }
		        }


		        //实验报告
		        List<EntrustExpertOpinion> entrustExpertOpinionList = entrustExpertOpinionDao.getByRegisterId(entrustRegister.getId());

		        //分析说明
		        String finaler = "   ";
		        //鉴定意见
		        String explain = " ";
		        if (entrustExpertOpinionList.size() > 0) {
		            for (int i = 0; i < entrustExpertOpinionList.size(); i++) {
		                finaler = entrustExpertOpinionList.get(0).getFinalVersion().replaceAll("<w:br>", "<br/>").replaceAll("&nbsp;&nbsp;", "&nbsp;&nbsp;&nbsp;&nbsp;");
		                explain = entrustExpertOpinionList.get(0).getDraft().replaceAll("\r\n\r\n", "<br/>").replaceAll("&nbsp;&nbsp;", "&nbsp;&nbsp;&nbsp;&nbsp;");
		            }
		        }

		        String registerId = entrustRegister.getId();
		        /*Map<String,Double> itemMap = new HashMap<String, Double>();
		        if(!CollectionUtils.isEmpty(itemList)){
		            for(DnaPiResultItem item:itemList){
		                itemMap.put(item.getGeneLoci(),item.getPi());
		            }
		        }*/
		        model.addAttribute("explain", explain);
		        model.addAttribute("finaler", finaler);
		        model.addAttribute("casecode", casecode);
		        model.addAttribute("simple", simple.format(new Date()));
		        model.addAttribute("testcase", testcase);
		        model.addAttribute("entrustAbstracts", entrustAbstracts3);
		        model.addAttribute("materialType", materialType);
		        model.addAttribute("qty", qty);
		        model.addAttribute("df", entrustRegister.getEntrustDate());
		        model.addAttribute("fm", fm);
		        model.addAttribute("str", strMap);
		        model.addAttribute("entrustRegister", entrustRegister);
		        model.addAttribute("newList", newList);
		        return "modules/entrust/entrustRegisterReport";
		    }
   
}
   