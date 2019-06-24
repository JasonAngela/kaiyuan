/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entrust.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.entrust.ExportUtil.WordExportUtil;
import com.thinkgem.jeesite.modules.entrust.entity.Abstracts;
import com.thinkgem.jeesite.modules.entrust.entity.BASE64;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.service.DictService;

import org.apache.commons.collections.iterators.EntrySetMapIterator;
import org.apache.commons.io.FileUtils;
import org.apache.poi.poifs.filesystem.DirectoryNode;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.filter.PhotoUploud;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.act.utils.ActUtils;
import com.thinkgem.jeesite.modules.dna.dao.DnaExperimentStrDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaPiResultDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaPiResultItemDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaReceiveIteamDao;
import com.thinkgem.jeesite.modules.dna.entity.DnaExperimentStr;
import com.thinkgem.jeesite.modules.dna.entity.DnaGeneFrequency;
import com.thinkgem.jeesite.modules.dna.entity.DnaPiResult;
import com.thinkgem.jeesite.modules.dna.entity.DnaPiResultItem;
import com.thinkgem.jeesite.modules.dna.entity.DnaSpe;
import com.thinkgem.jeesite.modules.dna.entity.DnaSpeIteam;
import com.thinkgem.jeesite.modules.dna.entity.ParentageTestingEntity;
import com.thinkgem.jeesite.modules.dna.service.DnaPiResultService;
import com.thinkgem.jeesite.modules.dna.service.ParentageTesting;
import com.thinkgem.jeesite.modules.entrust.dao.EntrustAbstractsDao;
import com.thinkgem.jeesite.modules.entrust.dao.EntrustRegisterDao;
import com.thinkgem.jeesite.modules.entrust.dao.LicensedDao;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustAbstracts;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustRegister;
import com.thinkgem.jeesite.modules.entrust.entity.Licensed;
import com.thinkgem.jeesite.modules.entrust.entity.Strs;
import com.thinkgem.jeesite.modules.entrust.entity.Word2007ToHtml;
import com.thinkgem.jeesite.modules.material.entity.SpecimenMaterialRegisterItem;
import com.thinkgem.jeesite.modules.material.service.SpecimenMaterialRegisterService;
import com.thinkgem.jeesite.modules.sys.service.SysCodeRuleService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 委托登记Service
 * 
 * @author zhuguli
 * @version 2017-05-12
 */
@Service
@Transactional(readOnly = true)
public class EntrustRegisterService extends CrudService<EntrustRegisterDao, EntrustRegister> {
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private EntrustAbstractsDao entrustAbstractsDao;
	@Autowired
	private SysCodeRuleService codeRuleService;
	@Autowired
	private DnaPiResultService dnaPiResultService;
	@Autowired
	private DnaExperimentStrDao dnaExperimentStrDao;
	@Autowired
	private GeneFrequencyStorage frequencyStorage;
	@Autowired
	private DictService dictService;
	@Autowired
	private EntrustRegisterDao entrustRegisterDao;
	@Autowired
	private DnaPiResultItemDao dnaPiResultItemDao;
	@Autowired
	private DnaPiResultDao  dnaPiResultDao;
	 @Autowired
	    private EntrustAbstractsService entrustAbstractsService;
	@Autowired
	private SysCodeRuleService sysCodeRuleService;
	 @Autowired
	    private LicensedDao licensedDao;
	  @Autowired
	    private DnaReceiveIteamDao dnaReceiveIteamDao;
	 @Autowired
	    private SpecimenMaterialRegisterService specimenMaterialRegisterService;

	public EntrustRegister get(String id) {
		EntrustRegister entrustRegister = super.get(id);
		entrustRegister.setEntrustAbstractsList(entrustAbstractsDao.findList(new EntrustAbstracts(entrustRegister)));
		return entrustRegister;
	}

	public EntrustRegister getId(String id) {
		return entrustRegisterDao.getId(id);
	}

	public List<EntrustRegister> findList(EntrustRegister entrustRegister) {
		return super.findList(entrustRegister);
	}

	public Page<EntrustRegister> findPage(Page<EntrustRegister> page, EntrustRegister entrustRegister) {
		return super.findPage(page, entrustRegister);
	}
   
	public void exportWord(EntrustRegister entrustRegister,HttpServletRequest request) throws IOException {
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
		
		if(!StringUtils.isEmpty(entrustRegister.getSendMode())){
			
			beanParams.put("sendMode", entrustRegister.getSendMode());
		}else{
			beanParams.put("sendMode", "____________");
		}
		

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
		if(abstractsList.size()<6){
			int aInt=5-abstractsList.size();
			for (int i = 0; i < aInt; i++) {
				Abstracts abstracts = new Abstracts();
				abstracts.setA("  ");
				abstracts.setB("  ");
				abstracts.setC("  ");
				abstracts.setD("  ");
				abstracts.setE("  ");
				abstracts.setF("  ");
				abstractsList.add(abstracts);
			}
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
		System.out.println(beforeDate.substring(8, 10));
				beanParams.put("year", beforeDate.substring(0, 4));
				beanParams.put("month", beforeDate.substring(5, 7));
				beanParams.put("day", beforeDate.substring(8, 10));
		}else{
			beanParams.put("year", "_____");
			beanParams.put("month", "_____");
			beanParams.put("day","_____");
			
		}
		// timeLimitResult
		if(StringUtils.isNoneEmpty(entrustRegister.getTimeLimitResult())){
			beanParams.put("timeLimitResult", entrustRegister.getTimeLimitResult());
		}else{
			beanParams.put("timeLimitResult", "_______");
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
			beanParams.put("clientReceiver", "_____________________________");
		}
		// remarks
		beanParams.put("remarks", entrustRegister.getRemarks());
		WordExportUtil.writeResponse(WordExportUtil.WORD_2003, fileName, "templateDir", "temple.ftl", beanParams,
				entrustRegister);
		
		//word  转pdf
		String start = "D:"+File.separator+"information"+File.separator+"DNA"+File.separator+entrustRegister.getCode()+File.separator+entrustRegister.getClientName() + "的委托书.doc";
		String path="D:"+File.separator+"information"+File.separator+"DNA"+File.separator+entrustRegister.getCode()+File.separator+"power of attorney" + ".pdf";
		Word2007ToHtml.wordToPDF2(start, path); 
	}
	
	
	
	
	//创建返还清单pdf及word
	
	public void   lisTing(EntrustRegister entrustRegister){
		 Map<String, Object> beanParams = new HashMap<String, Object>();
         //被鉴定人
         beanParams.put("clientName", entrustRegister.getClientName());
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
         String simple=  entrustRegister.getEntrustDate().substring(0, 4);
         beanParams.put("caseCode", casecode);
         beanParams.put("simple", simple);
         beanParams.put("entrustDate", entrustRegister.getEntrustDate());
     	 //被鉴定人员
         List<EntrustAbstracts> entrustAbstracts = entrustAbstractsService.findAllentrustAbstracts(entrustRegister.getId());
        //idType  idTypeInt
         String idType1=null;
         String idType2=null;  
         String idType3=null;
          List<String>idTypes=new ArrayList<String>();
          for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
          	idTypes.add(entrustAbstracts2.getIdType());
  		}
          idTypes=removeDuplicate(idTypes);
        for (int i = 0; i < idTypes.size(); i++) {
        	
        	
      	  if(idTypes.size()==1){
      		  idType1=idTypes.get(0);
      	  }
      	  if(idTypes.size()==2){
      		  idType1=idTypes.get(0);
      		  idType2=idTypes.get(1);
      	  }
      	  if(idTypes.size()==3){
      		  idType3=idTypes.get(2);
      		  idType1=idTypes.get(0);
     		  idType2=idTypes.get(1);
      	  }
  	}
        Integer idTypeInt=0;
        Integer idTypeInt1=0;
        Integer idTypeInt2=0;
        for (int i = 0; i <entrustAbstracts.size(); i++){
      	  if(entrustAbstracts.get(i).getIdType().equals(idType1)){
      		  idTypeInt++;
      	  }
      	  if(entrustAbstracts.get(i).getIdType().equals(idType2)){
      		  idTypeInt1++;
      	  }
      	  if(entrustAbstracts.get(i).getIdType().equals(idType3)){
      		  idTypeInt2++;
      	  }
  	}
        
        
        int n=1;
        List<Strs>strs1=new ArrayList<Strs>();
        //顺序1
        Strs strs2=new Strs();
 	   strs2.setA(n++);
 	   strs2.setB("委托书");
 	   strs2.setC("1");
 	   strs2.setD("■原件 □复印件");
 	   strs1.add(strs2);
      //顺序2
        if(idType1!=null){
     	   Strs strs=new Strs();
     	   strs.setA(n++);
     	   idType1= DictUtils.getDictLabels(idType1, "idTypeCode", "未知");
     	   strs.setB(idType1);
     	   strs.setC(idTypeInt.toString());
     	   strs.setD("□原件 ■复印件");
     	   strs1.add(strs);
        }
        //顺序3
        if(idType2!=null){
     	   Strs strs=new Strs();
     	   strs.setA(n++);
     	   idType2 = DictUtils.getDictLabels(idType2, "idTypeCode", "未知");
     	   strs.setB(idType2);
     	   strs.setC(idTypeInt1.toString());
     	   strs.setD("□原件 ■复印件");
     	   strs1.add(strs);
        }
        //顺序4
        if(idType3!=null){
     	   Strs strs=new Strs();
     	   strs.setA(n++);
     	   idType3 = DictUtils.getDictLabels(idType3, "idTypeCode", "未知");
     	   strs.setB(idType3);
     	   strs.setC(idTypeInt2.toString());
     	   strs.setD("□原件 ■复印件");
     	   strs1.add(strs);
        }
        //数量
        int qty = 0;
        //鉴定类型
        String materialType = "";
        for (int ij = 0; ij < specimenMaterialRegisterService.findmaterial(entrustRegister.getId()).size(); ij++) {
     	   qty += specimenMaterialRegisterService.findmaterial(entrustRegister.getId()).get(ij).getQty();
     	   materialType = specimenMaterialRegisterService.findmaterial(entrustRegister.getId()).get(ij).getMaterialType();
        }
        //顺序5
        Strs strs5=new Strs();
        strs5.setA(n++);
        materialType = DictUtils.getDictLabels(materialType, "material_TypeCode", "未知");
        strs5.setB(materialType);
        strs5.setC(String.valueOf(qty));
        strs5.setD("■原件 □复印件");
        strs1.add(strs5);
        beanParams.put("strs", strs1);
         //受理人 提交人
         String  serverName=entrustRegister.getServerName();
         beanParams.put("serverName", serverName);
         List<Licensed> licenseds = licensedDao.findEntrust(entrustRegister.getId());
         //第一鉴定人
         beanParams.put("liName", licenseds.get(0).getUser().getName());
         //第二鉴定人
         beanParams.put("liName1", licenseds.get(1).getUser().getName());
         //授权签字人
         beanParams.put("liName2",licenseds.get(2).getUser().getName());
         
         //领取时间
         Date receiveDate=null;
         if(dnaReceiveIteamDao.getCode(entrustAbstracts.get(0).getSpecimenCode())!=null){
         	 receiveDate= dnaReceiveIteamDao.getCode(entrustAbstracts.get(0).getSpecimenCode()).getCreateDate();
         }else{
         	 receiveDate=new Date();
         }
         SimpleDateFormat sim = new SimpleDateFormat("yyyy年MM月dd日");
         beanParams.put("rDate", sim.format(receiveDate));
         //第一到第二
         beanParams.put("rDate1", sim.format(licenseds.get(1).getCreateDate()));
         //第二到授权
         beanParams.put("rDate2", sim.format( licenseds.get(2).getCreateDate()));
       String  fileName="鉴定材料接收流转及返还清单";
 		WordExportUtil.writeResponse(WordExportUtil.WORD_2003, fileName, "templateDir", "listing.ftl", beanParams,
 				entrustRegister);
 		//word转pdf
 		
 		String start = "D:"+File.separator+"information"+File.separator+"DNA"+File.separator+entrustRegister.getCode()+File.separator+"鉴定材料接收流转及返还清单.doc";
 		String path="D:"+File.separator+"information"+File.separator+"DNA"+File.separator+entrustRegister.getCode()+File.separator+"listing of dna" + ".pdf";
 		  
 		Word2007ToHtml.wordToPDF2(start, path); 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 
	
	private List<EntrustAbstracts> markCode(List<EntrustAbstracts> entrustAbs,String code) {
		int f=0;
		int m = 0;
		int c = 1;
		int t=1;
		int q=1;
		for(EntrustAbstracts abstracts:entrustAbs){
			if(abstracts!=null){
				
			if(abstracts.getAppellation().equals("1")){//父亲
				abstracts.setSpecimenCode(code+"-F"+(f>0?f:""));
				f++;
			}else if(abstracts.getAppellation().equals("2")){//母亲
				abstracts.setSpecimenCode(code+"-M"+(m>0?f:""));
				m++;
			}else if(abstracts.getAppellation().equals("3")){//小孩
				if(c==1){
					abstracts.setSpecimenCode(code+"-C");
				}else{
					abstracts.setSpecimenCode(code+"-C"+c);
				}
				c++;
			}else if(abstracts.getAppellation().equals("4")){//个体
				abstracts.setSpecimenCode(code+"-G");
				f++;
			}else if(abstracts.getAppellation().equals("5")){//同体
				if(t==1){
					abstracts.setSpecimenCode(code+"-T");
				}else{
					abstracts.setSpecimenCode(code+"-T"+t);
				}
				t++;
			}else if(abstracts.getAppellation().equals("6")){//其它
				if(t==q){
					abstracts.setSpecimenCode(code+"-Q");
				}else{
					abstracts.setSpecimenCode(code+"-Q"+q);
				}
				q++;
			}
			} 
		}
		return entrustAbs;
	}
	
	@Transactional(readOnly = false)
	public void save(EntrustRegister entrustRegister, HttpServletResponse response,HttpServletRequest request) throws Exception {
		boolean isNew = entrustRegister.getIsNewRecord();
		Word2007ToHtml word2007ToHtml=new Word2007ToHtml();
		if (isNew) {
			entrustRegister.setStatus("2");
			String code = codeRuleService.generateCode("entrust_register_code");
			entrustRegister.setCode(code);
			String caseCode = codeRuleService.generateCode("case_dna_code");
			entrustRegister.setCaseCode(caseCode);
		}
		super.save(entrustRegister);
		List<EntrustAbstracts> entrustAbstractList = entrustRegister.getEntrustAbstractsList();
		String toName = "D:" + File.separator + "information" + File.separator + "DNA" + File.separator + entrustRegister.getCode() + File.separator + "idPic" + File.separator;
        PhotoUploud photoUploud = new PhotoUploud();
        photoUploud.UploudPhoto3(entrustRegister.getClientArea(), request, toName, entrustRegister.getClientName());
        for (int i = 0; i < entrustAbstractList.size(); i++) {
        	if(entrustAbstractList.get(i).getIdPic()!=null){
        		Dict dict = new Dict();
        		if (StringUtils.isNotEmpty(entrustAbstractList.get(i).getIdType())) {
        			dict.setType("idTypeCode");
        			dict.setValue(entrustAbstractList.get(i).getIdType());
        			Dict typeCode = dictService.findValue(dict);
        			photoUploud.UploudPhoto2(entrustAbstractList.get(i).getIdPic(), request, toName,entrustAbstractList.get(i).getClientName(),0,toName,typeCode.getLabel());
        		}
        	}
        }

     /*   
        if(!StringUtils.isEmpty( entrustRegister.getEntrustAbstractsList().get(0).getIdPic()) ||!StringUtils.isEmpty( entrustRegister.getEntrustAbstractsList().get(0).getClientPic())){
        	String imagesPath="D:\\information\\DNA\\"+entrustRegister.getCode()+"\\idPic";
        	String FILEPATH="D:\\information\\DNA\\"+entrustRegister.getCode()+"\\idPic\\";
        	word2007ToHtml.imagesToPdf(FILEPATH, "idpic of dna", imagesPath);
        }
      */
		
		if(isNew){
			String code = sysCodeRuleService.generateCode("specimen_code");
			entrustAbstractList = markCode(entrustAbstractList,code);
		}
		for(int i=0;i<entrustAbstractList.size();i++){
			EntrustAbstracts entrustAbstracts = entrustAbstractList.get(i);
			if (entrustAbstracts.getId() == null) {
				continue;
			}
			if (EntrustAbstracts.DEL_FLAG_NORMAL.equals(entrustAbstracts.getDelFlag())) {
				if (StringUtils.isBlank(entrustAbstracts.getId())) {
					entrustAbstracts.setRegister(entrustRegister);
					entrustAbstracts.setClientCode(entrustRegister.getWhether());
					entrustAbstracts.preInsert();
					entrustAbstractsDao.insert(entrustAbstracts);
				} else {
					entrustAbstracts.preUpdate();
					entrustAbstractsDao.update(entrustAbstracts);
				}
			} else {
				entrustAbstractsDao.delete(entrustAbstracts);
			} 
			
		}
		for (EntrustAbstracts entrustAbstracts : entrustRegister.getEntrustAbstractsList()) {
			/*if (entrustAbstracts.getId() == null) {
				continue;
			}
			if (EntrustAbstracts.DEL_FLAG_NORMAL.equals(entrustAbstracts.getDelFlag())) {
				if (StringUtils.isBlank(entrustAbstracts.getId())) {
					entrustAbstracts.setRegister(entrustRegister);
					
					
					entrustAbstracts.preInsert();
					entrustAbstractsDao.insert(entrustAbstracts);
				} else {
					entrustAbstracts.preUpdate();
					entrustAbstractsDao.update(entrustAbstracts);
				}
			} else {
				entrustAbstractsDao.delete(entrustAbstracts);
			}   */
		}
		// 每次执行后生成新的docx文档
		// exportDocx(entrustRegister);
		if (isNew) {
			// 启动流程
			actTaskService.startProcess(ActUtils.PD_APPRAISAL[0], ActUtils.PD_APPRAISAL[1], entrustRegister.getId(),
					entrustRegister.getCode());
		}

		try {
			exportWord(entrustRegister, request);
		} catch (IOException e) {
			return;
		}

	}
	
	
	
//内部扭转
	  public  void circuLation(EntrustRegister entrustRegister) {         
	        Map<String, Object> beanParams = new HashMap<String, Object>();
	        //被鉴定人
	        beanParams.put("clientName", entrustRegister.getClientName());
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
	        String simple=  entrustRegister.getEntrustDate().substring(0, 4);
	        beanParams.put("casecode", casecode);
	        beanParams.put("simple", simple);
	        beanParams.put("entrustDate", entrustRegister.getEntrustDate());
	        SimpleDateFormat sim = new SimpleDateFormat("yyyy年MM月dd日");
			
	        //受理人 提交人
	        String  serverName=entrustRegister.getServerName();
	        beanParams.put("serverName", serverName);
	        List<Licensed> licenseds = licensedDao.findEntrust(entrustRegister.getId());
	        //实验报告时间
	        beanParams.put("rDate",sim.format(licenseds.get(1).getCreateDate()));
	        //第一鉴定人
	        beanParams.put("liName", licenseds.get(0).getUser().getName());
	        //第二鉴定人
	        beanParams.put("liName1", licenseds.get(1).getUser().getName());
	        //授权签字人
	        beanParams.put("liName2",licenseds.get(2).getUser().getName());
	        //第一到第二
	        beanParams.put("rDate1", sim.format(licenseds.get(1).getCreateDate()));
	        //第二到授权
	        beanParams.put("rDate2", sim.format( licenseds.get(2).getCreateDate()));
	        
	        
	        String  fileName="内部流转审批表";
 		   

	 		WordExportUtil.writeResponse(WordExportUtil.WORD_2003, fileName, "templateDir", "circuLation.ftl", beanParams,
	 				entrustRegister);
	 		
	 		//word转pdf
	 		
	 		String start ="D:"+File.separator+"information"+File.separator+"DNA"+File.separator+entrustRegister.getCode()+File.separator+"内部流转审批表.doc";
	 		String path="D:"+File.separator+"information"+File.separator+"DNA"+File.separator+entrustRegister.getCode()+File.separator+"circulation of dna" + ".pdf";
	 		  
	 		Word2007ToHtml.wordToPDF2(start, path); 
	    }

	  

	  //扩增  
	  public  void ampliFication(EntrustRegister entrustRegister,List<EntrustAbstracts> allentrustAbstracts){

		  Map<String, Object> beanParams = new HashMap<String, Object>();
	        //被鉴定人
	        beanParams.put("clientName", entrustRegister.getClientName());
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
	        //String simple=  entrustRegister.getEntrustDate().substring(0, 4);
	        beanParams.put("code", casecode);
	       // beanParams.put("simple", simple);
	        
	        
	        
	        int	b=1;
	        for (int i = 0; i < allentrustAbstracts.size(); i++) {
	        	beanParams.put("code"+b++, allentrustAbstracts.get(i).getSpecimenCode());
			}
	        
	        
			 
			   SimpleDateFormat sim = new SimpleDateFormat("yyyy年MM月dd日");
			   List<Licensed> licenseds = licensedDao.findEntrust(entrustRegister.getId());
		        //实验报告时间
		        beanParams.put("year",sim.format(licenseds.get(1).getCreateDate()));
		        //第一鉴定人
		        beanParams.put("licenseds1Name", licenseds.get(0).getUser().getName());
		        //第二鉴定人
		        beanParams.put("licenseds2Name", licenseds.get(1).getUser().getName());
	       
	      
	    
		        String  fileName="DNA扩增记录表";
		 		WordExportUtil.writeResponse(WordExportUtil.WORD_2003, fileName, "templateDir", "record.ftl", beanParams,
		 				entrustRegister);
		 		//word转pdf
		 		String start ="D:"+File.separator+"information"+File.separator+"DNA"+File.separator+entrustRegister.getCode()+File.separator+"DNA扩增记录表.doc";
		 		String path="D:"+File.separator+"information"+File.separator+"DNA"+File.separator+entrustRegister.getCode()+File.separator+"ampliFication of dna" + ".pdf";
		 		Word2007ToHtml.wordToPDF2(start, path); 
	  }
	  
	  
	  
	@Transactional(readOnly = false)
	public void delete(EntrustRegister entrustRegister) {
		super.delete(entrustRegister);
		entrustAbstractsDao.delete(new EntrustAbstracts(entrustRegister));
	}

	
	
	
	@Transactional(readOnly = false)
	public void autoCalculatePi2(EntrustRegister entrustRegister) {
		List<EntrustAbstracts> parentList = new ArrayList<EntrustAbstracts>();
		List<EntrustAbstracts> childrenList = new ArrayList<EntrustAbstracts>();
		List<EntrustAbstracts> abstractsList = entrustAbstractsDao.findList(new EntrustAbstracts(entrustRegister));
		for (EntrustAbstracts abstracts : abstractsList) {
			switch (Integer.parseInt(abstracts.getAppellation())) {
			case 1:
				parentList.add(abstracts);
				break;
			case 2:
				parentList.add(abstracts);
				break;
			case 4:
				parentList.add(abstracts);
				break;
			case 3:
				childrenList.add(abstracts);
				break;
			default:
				break;
			}
		}

		// 交叉生成结果
	for (EntrustAbstracts child : childrenList) {
		for (EntrustAbstracts parent : parentList) {
			List<DnaExperimentStr> parentStrList = dnaExperimentStrDao
					.getByExperimentIdAndAbstractsId(parent.getDnaExperimentId(), parent.getId());
			BigDecimal cpi = new BigDecimal(1);
				DnaPiResult dnaPiResult = new DnaPiResult();
				dnaPiResult.setParentCode("样本" + parent.getSpecimenCode() + "(" + parent.getClientName() + ")");
				dnaPiResult.setChildCode("样本" + child.getSpecimenCode() + "(" + child.getClientName() + ")");
				dnaPiResult.setRegister(entrustRegister);

				List<DnaExperimentStr> childStrList = dnaExperimentStrDao
						.getByExperimentIdAndAbstractsId(child.getDnaExperimentId(), child.getId());
				// 保存明细
				for (DnaExperimentStr parentStr : parentStrList) {
					if (parentStr.getGeneLoci().toUpperCase().equals("AMEL")) {
						continue;
					}
					DnaExperimentStr childStr = getChildStr(parentStr, childStrList);
					if (childStr == null) {
						continue;
					}
					ParentageTestingEntity p = new ParentageTestingEntity(parent.getAppellation(),
							new DnaGeneFrequency(parentStr.getXValue(),
									frequencyStorage.getProb(parentStr.getGeneLoci() + "_" + parentStr.getXValue())),
							new DnaGeneFrequency(parentStr.getYValue(),
									frequencyStorage.getProb(parentStr.getGeneLoci() + "_" + parentStr.getYValue())));

					ParentageTestingEntity c = new ParentageTestingEntity(child.getAppellation(),
							new DnaGeneFrequency(childStr.getXValue(),
									frequencyStorage.getProb(childStr.getGeneLoci() + "_" + childStr.getXValue())),
							new DnaGeneFrequency(childStr.getYValue(),
									frequencyStorage.getProb(childStr.getGeneLoci() + "_" + childStr.getYValue())));

					
						Object[] result = ParentageTesting.duos(c, p,childStr.getGeneLoci());// 0 公式
						DnaPiResultItem dnaPiResultItem = new DnaPiResultItem();
						dnaPiResultItem.setGeneLoci(parentStr.getGeneLoci());
						dnaPiResultItem.setPi((Double) result[1]);
						dnaPiResultItem.setFormula(result[0] + "    " + result[2]);
						if(result[6]!=null){
							dnaPiResultItem.setLoci(result[6].toString());
						}
						dnaPiResultItem.setResult(dnaPiResult);
						cpi = cpi.multiply(new BigDecimal(dnaPiResultItem.getPi()));
				dnaPiResult.setCpi(cpi);
				dnaPiResult.setRcp(cpi.divide(cpi.add(new BigDecimal(1)), 10, BigDecimal.ROUND_DOWN));
				//dnaPiResult.setDnaPiResultItemList(dnaPiResultItemList);
				dnaPiResultService.save3(dnaPiResult);
				dnaPiResultService.save2(dnaPiResultItem);
				}
		}
	}
}
	
	
	@Transactional(readOnly = false)
	public void autoCalculatePi(EntrustRegister entrustRegister) {
		List<EntrustAbstracts> parentList = new ArrayList<EntrustAbstracts>();
		List<EntrustAbstracts> childrenList = new ArrayList<EntrustAbstracts>();
		List<EntrustAbstracts> abstractsList = entrustAbstractsDao.findList(new EntrustAbstracts(entrustRegister));
		for (EntrustAbstracts abstracts : abstractsList) {
			switch (Integer.parseInt(abstracts.getAppellation())) {
			case 1:
				parentList.add(abstracts);
				break;
			case 2:
				parentList.add(abstracts);
				break;
			case 4:
				parentList.add(abstracts);
				break;
			case 3:
				childrenList.add(abstracts);
				break;
			default:
				break;
			}
		}

		// 交叉生成结果
		for (EntrustAbstracts child : childrenList) {
		for (EntrustAbstracts parent : parentList) {
			List<DnaExperimentStr> parentStrList = dnaExperimentStrDao
					.getByExperimentIdAndAbstractsId(parent.getDnaExperimentId(), parent.getId());
			BigDecimal cpi = new BigDecimal(1);
				DnaPiResult dnaPiResult = new DnaPiResult();
				dnaPiResult.setParentCode("样本" + parent.getSpecimenCode() + "(" + parent.getClientName() + ")");
				dnaPiResult.setChildCode("样本" + child.getSpecimenCode() + "(" + child.getClientName() + ")");
				dnaPiResult.setRegister(entrustRegister);
				List<DnaPiResultItem> dnaPiResultItemList = new ArrayList<DnaPiResultItem>();

				List<DnaExperimentStr> childStrList = dnaExperimentStrDao
						.getByExperimentIdAndAbstractsId(child.getDnaExperimentId(), child.getId());
				// 保存明细
				for (DnaExperimentStr parentStr : parentStrList) {
					if (parentStr.getGeneLoci().toUpperCase().equals("AMEL")) {
						continue;
					}
					DnaExperimentStr childStr = getChildStr(parentStr, childStrList);
					if (childStr == null) {
						continue;
					}
					
					
					ParentageTestingEntity p = new ParentageTestingEntity(parent.getAppellation(),
							new DnaGeneFrequency(parentStr.getXValue(),
									frequencyStorage.getProb(parentStr.getGeneLoci() + "_" + parentStr.getXValue())),
							new DnaGeneFrequency(parentStr.getYValue(),
									frequencyStorage.getProb(parentStr.getGeneLoci() + "_" + parentStr.getYValue())));

					ParentageTestingEntity c = new ParentageTestingEntity(child.getAppellation(),
							new DnaGeneFrequency(childStr.getXValue(),
									frequencyStorage.getProb(childStr.getGeneLoci() + "_" + childStr.getXValue())),
							new DnaGeneFrequency(childStr.getYValue(),
									frequencyStorage.getProb(childStr.getGeneLoci() + "_" + childStr.getYValue())));

					if(c.getFirstProbability()==null||c.getSecondProbability()==null||p.getFirstProbability()==null||p.getSecondProbability()==null){
  						break;
  					}else{
						Object[] result = ParentageTesting.duos(c, p,childStr.getGeneLoci());// 0 公式
						DnaPiResultItem dnaPiResultItem = new DnaPiResultItem(); 
						dnaPiResultItem.setPi((Double) result[1]);
						dnaPiResultItem.setFormula(result[0] + "    " + result[2]);
						//dnaPiResultItem.setGeneLoci(parentStr.getGeneLoci());
						if(result[6]!=null){
							if(result[6].toString().substring(0, result[6].toString().length()-1).equals(parentStr.getGeneLoci())){
								dnaPiResultItem.setGeneLoci(parentStr.getGeneLoci()+"*");
								dnaPiResultItem.setLoci(result[6].toString());
							}
						}else{
							dnaPiResultItem.setGeneLoci(parentStr.getGeneLoci());
						}
						dnaPiResultItemList.add(dnaPiResultItem);
						cpi = cpi.multiply(new BigDecimal(dnaPiResultItem.getPi()));
				}
				dnaPiResult.setCpi(cpi);
				dnaPiResult.setRcp(cpi.divide(cpi.add(new BigDecimal(1)), 10, BigDecimal.ROUND_DOWN));
				dnaPiResult.setDnaPiResultItemList(dnaPiResultItemList);
				dnaPiResultService.save(dnaPiResult);
			}
		}
		}
		
	}

	public List<EntrustRegister> findExport(EntrustRegister entrustRegister) {
		return entrustRegisterDao.findExport(entrustRegister);
	}

	// 获得对应的子值
	private DnaExperimentStr getChildStr(DnaExperimentStr parentStr, List<DnaExperimentStr> childStrList) {
		for (DnaExperimentStr child : childStrList) {
			if (child.getGeneLoci().equals(parentStr.getGeneLoci())) {
				return child;
			}
		}
		return null;
	}

	// 选择导出exel
	public void export(HttpServletResponse response, List<EntrustRegister> entrustRegister2,
			EntrustRegister entrustRegister) {
		WritableWorkbook book;

		try {
			OutputStream os = response.getOutputStream();// 取得输出流
			response.reset();// 清空输出流
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String("查询exel".getBytes("GB2312"), "iso8859_1") + ".xls");// 设定输出文件头
			response.setContentType("application/msexcel");// 定义输出类型
			book = Workbook.createWorkbook(os);
			WritableSheet sheet = book.createSheet("受理", 0);

			
			List<EntrustRegister> entrustRegisters = new ArrayList<EntrustRegister>();
			List<String> bn = new ArrayList<String>();
			if (!StringUtils.isEmpty(entrustRegister.getCode())) {
				bn.add("编码");
			}
			if (!StringUtils.isEmpty(entrustRegister.getCaseCode())) {
				bn.add("案件编码");
			}
			if (!StringUtils.isEmpty(entrustRegister.getClientName())) {
				bn.add("委托人");
			}
			if (!StringUtils.isEmpty(entrustRegister.getClientTel())) {
				bn.add("委托人电话");
			}
			if (!StringUtils.isEmpty(entrustRegister.getClientZipcode())) {
				bn.add("联系电话");
			}
			if (!StringUtils.isEmpty(entrustRegister.getType())) {
				bn.add("类型");
			}
			if (!StringUtils.isEmpty(entrustRegister.getAgentName())) {
				bn.add("送检人(机构)");
			}
			if (!StringUtils.isEmpty(entrustRegister.getServerName())) {
				bn.add("受理人");
			}
			
			if (entrustRegister.getStandardFee() != null) {
				bn.add("合计费用 ");
			}
			bn.add("父亲");
			bn.add("母亲");
			bn.add("小孩");
			bn.add("小孩2");
			bn.add("小孩3");
			bn.add("小孩4");
			bn.add("个体");
			bn.add("其它");
			bn.add("其它2");
			bn.add("其它3");
			bn.add("其它4");
			bn.add("其它5");
			bn.add("同一");
			bn.add("同一2");
			
			
			// 标题
			String[] columns = bn.toArray(new String[bn.size()]);
			for (int j = 0; j < columns.length; j++) {
				sheet.addCell(new Label(j, 0, columns[j]));
			}
			/**
			 * 最多三个 父亲 儿子 母亲 选择三个的话 把按照顺序 分批显示 两个的话 就是一对显示即可 F C M
			 */
			
			
			for (int i = 0; i < entrustRegister2.size(); i++) {
				EntrustRegister entrustRegister4 = new EntrustRegister();
				if (!StringUtils.isEmpty(entrustRegister.getCode())) {
					entrustRegister4.setCode(entrustRegister2.get(i).getCode());
				}
				if (!StringUtils.isEmpty(entrustRegister.getCaseCode())) {
					entrustRegister4.setCaseCode(entrustRegister2.get(i).getCaseCode());
				}
				if (!StringUtils.isEmpty(entrustRegister.getClientName())) {
					entrustRegister4.setClientName(entrustRegister2.get(i).getClientName());
				}
				if (!StringUtils.isEmpty(entrustRegister.getClientTel())) {
					entrustRegister4.setClientTel(entrustRegister2.get(i).getClientTel());
				}
				if (!StringUtils.isEmpty(entrustRegister.getClientReceiver())) {
					entrustRegister4.setClientReceiver(entrustRegister2.get(i).getClientReceiver());
				}
				if (!StringUtils.isEmpty(entrustRegister.getClientZipcode())) {
					entrustRegister4.setClientZipcode(entrustRegister2.get(i).getClientZipcode());
				}
				if (!StringUtils.isEmpty(entrustRegister.getType())) {
					String materialType= DictUtils.getDictLabels(entrustRegister2.get(i).getType(), "typeCode" ,"");
					entrustRegister4.setType(materialType);
				}
				if (!StringUtils.isEmpty(entrustRegister.getAgentName())) {
					entrustRegister4.setAgentName(entrustRegister2.get(i).getAgentName());
				}
				if (!StringUtils.isEmpty(entrustRegister.getServerName())) {
					entrustRegister4.setServerName(entrustRegister2.get(i).getServerName());
				}
				if (entrustRegister.getStandardFee()!= null) {
					entrustRegister4.setStandardFee(entrustRegister2.get(i).getStandardFee());
				}
				entrustRegisters.add(entrustRegister4);
			}

			
			// 标题加上父亲 母亲 孩子 其它
int vc=0;
int vb=0;
			for (int i = 0; i < entrustRegisters.size(); i++) {
				int j = 0;
				if (!StringUtils.isEmpty(entrustRegisters.get(i).getCode())) {
					sheet.addCell(new Label(j++, i + 1, entrustRegisters.get(i).getCode()));
				}
				if (!StringUtils.isEmpty(entrustRegisters.get(i).getCaseCode())) {
					sheet.addCell(new Label(j++, i + 1, entrustRegisters.get(i).getCaseCode()));
				}
				if (!StringUtils.isEmpty(entrustRegisters.get(i).getClientName())) {
					sheet.addCell(new Label(j++, i + 1, entrustRegisters.get(i).getClientName()));
				}
				if (!StringUtils.isEmpty(entrustRegisters.get(i).getClientTel())) {
					sheet.addCell(new Label(j++, i + 1, entrustRegisters.get(i).getClientTel()));
				}
				if (!StringUtils.isEmpty(entrustRegisters.get(i).getClientReceiver())) {
					sheet.addCell(new Label(j++, i + 1, entrustRegisters.get(i).getClientReceiver()));
				}
				if (!StringUtils.isEmpty(entrustRegisters.get(i).getClientZipcode())) {
					sheet.addCell(new Label(j++, i + 1, entrustRegisters.get(i).getClientZipcode()));
				}
				if (!StringUtils.isEmpty(entrustRegisters.get(i).getType())) {
					String materialType= DictUtils.getDictLabels(entrustRegister2.get(i).getType(), "typeCode" ,"未知");
					sheet.addCell(new Label(j++, i + 1,materialType));
				}
				if (!StringUtils.isEmpty(entrustRegisters.get(i).getAgentName())) {
					sheet.addCell(new Label(j++, i + 1, entrustRegisters.get(i).getAgentName()));
				}
				if (!StringUtils.isEmpty(entrustRegisters.get(i).getServerName())) {
					sheet.addCell(new Label(j++, i + 1, entrustRegisters.get(i).getServerName()));
				}
				if (entrustRegisters.get(i).getStandardFee() != null) {
					sheet.addCell(new Number(j++, i + 1, entrustRegisters.get(i).getStandardFee()));
				}
				vc=j;
				vb=i+1;
			}
			for (int f = 0; f < entrustRegister2.size(); f++) {
				List<EntrustAbstracts> abstractsList = entrustAbstractsDao.findList(new EntrustAbstracts(entrustRegister2.get(f)));
				for (int v = 0; v < abstractsList.size(); v++) {
						//父亲
						if (abstractsList.get(v).getAppellation().equals("1")) {
							sheet.addCell(new Label(vc, f+1,abstractsList.get(v).getClientName()));
						}
						//母亲
						if (abstractsList.get(v).getAppellation().equals("2")) {
							sheet.addCell(new Label(vc+1, f+1,abstractsList.get(v).getClientName()));
						}
						// 小孩
						if (abstractsList.get(v).getAppellation().equals("3")) {
							if(abstractsList.get(v).getSpecimenCode()!=null){
								if(abstractsList.get(v).getSpecimenCode().contains("-C2")){
									sheet.addCell(new Label(vc+3, f+1,abstractsList.get(v).getClientName()));
								}else if(abstractsList.get(v).getSpecimenCode().contains("-C3")){
									sheet.addCell(new Label(vc+4, f+1,abstractsList.get(v).getClientName()));
								}else if(abstractsList.get(v).getSpecimenCode().contains("-C4")){
									sheet.addCell(new Label(vc+5, f+1,abstractsList.get(v).getClientName()));
								}else {
									sheet.addCell(new Label(vc+2, f+1,abstractsList.get(v).getClientName()));
								}
							}
							
						}
						//个体
						if (abstractsList.get(v).getAppellation().equals("4")) {
							if(abstractsList.get(v).getSpecimenCode()!=null){
								sheet.addCell(new Label(vc+6, f+1,abstractsList.get(v).getClientName()));
							}
						}
						
						
						
						// 其它
						if (abstractsList.get(v).getAppellation().equals("6")) {
							if(abstractsList.get(v).getSpecimenCode()!=null){
								if(abstractsList.get(v).getSpecimenCode().contains("-Q2")){
									sheet.addCell(new Label(vc+8, f+1,abstractsList.get(v).getClientName()));
								}else if(abstractsList.get(v).getSpecimenCode().contains("-Q3")){
									sheet.addCell(new Label(vc+9, f+1,abstractsList.get(v).getClientName()));
								}else if(abstractsList.get(v).getSpecimenCode().contains("-Q4")){
									sheet.addCell(new Label(vc+10, f+1,abstractsList.get(v).getClientName()));
								}else if(abstractsList.get(v).getSpecimenCode().contains("-Q5")){
									sheet.addCell(new Label(vc+11, f+1,abstractsList.get(v).getClientName()));
								}else {
									sheet.addCell(new Label(vc+7, f+1,abstractsList.get(v).getClientName()));
								}
							}
						}
						//同体
						if (abstractsList.get(v).getAppellation().equals("5")) {
							if(abstractsList.get(v).getSpecimenCode()!=null){
								if(abstractsList.get(v).getSpecimenCode().contains("-T2")){
									sheet.addCell(new Label(vc+13, f+1,abstractsList.get(v).getClientName()));
								}else if(abstractsList.get(v).getSpecimenCode().contains("-T3")){
									sheet.addCell(new Label(vc+14, f+1,abstractsList.get(v).getClientName()));
								}else if(abstractsList.get(v).getSpecimenCode().contains("-T4")){
									sheet.addCell(new Label(vc+15, f+1,abstractsList.get(v).getClientName()));
								}else {
									sheet.addCell(new Label(vc+12, f+1,abstractsList.get(v).getClientName()));
								}
							}
						}
						
						
				}	
			
			}
			
			// 添加数据进去即可
			// 写入数据并关闭文件
			book.write();
			book.close();
			os.flush();
			os.close();
			response.flushBuffer();
			PrintWriter out = response.getWriter();
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
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
			
			
			
			public static void main(String[] args) {
				BASE64 base64=new BASE64();
				List<Strs>strs1=new ArrayList<Strs>();
				//顺序1
				
				//D:\information\DNA\wt2018112901826\idPic
				String mapPath="D:\\information\\DNA\\"+"wt2018112901826"+"\\idPic";
	        	List<String>mapPics=new ArrayList<String>();
	        	File mFile = new File(mapPath);
	        	Strs strs2=new Strs();
	        	Strs strs3=new Strs();
	        	Strs strs4=new Strs();
	        	Strs strs5=new Strs();
	        	Strs strs6=new Strs();
	        	if (mFile.exists() && mFile.isDirectory()) {
	        		List<File> mlist = new ArrayList<File>();
	        		getAllFile(mFile, mlist);
	        		// 已经获取了所有图片
	        		
	        		for (File file2 : mlist) {
	        			if(file2.toString().contains("谢群的正")){
	        				strs2.setC(base64.encodeImgageToBase64(file2));
	        				if(file2.toString().contains("谢群的反")){
	        					strs2.setB(base64.encodeImgageToBase64(file2));
	        				}
	        			}
	        			if(file2.toString().contains("胡云的正")){
	        				strs3.setC(base64.encodeImgageToBase64(file2));
	        				if(file2.toString().contains("胡云的反")){
	        					strs3.setB(base64.encodeImgageToBase64(file2));
	        				}
	        			}
	        		}
	        	}
				
	        	if(strs2!=null){
	        		strs1.add(strs2);
	        	}
	        	if(strs3!=null){
	        		strs1.add(strs3);
	        	}
	        	Map<String, Object> beanParams = new HashMap<String, Object>();
	        	// remarks
	    		beanParams.put("strs",strs1);

	    	/*	WordExportUtil.writeResponse(WordExportUtil.WORD_2003, "", "templateDir", "temple.ftl", beanParams,
	    				entrustRegister);*/
				
				
			}
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