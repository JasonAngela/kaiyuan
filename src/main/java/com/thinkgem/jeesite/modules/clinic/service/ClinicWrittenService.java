/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clinic.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.service.DictService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicRegister;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicWritten;
import com.thinkgem.jeesite.modules.entrust.dao.LicensedDao;
import com.thinkgem.jeesite.modules.entrust.entity.Licensed;
import com.thinkgem.jeesite.modules.entrust.entity.Strs;
import com.thinkgem.jeesite.modules.entrust.entity.Word2007ToHtml;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.clinic.dao.ClinicRegisterDao;
import com.thinkgem.jeesite.modules.clinic.dao.ClinicWrittenDao;

/**
 * 法医成文修改Service
 * @author fuyun
 * @version 2017-11-26
 */
@Service
@Transactional(readOnly = true)
public class ClinicWrittenService extends CrudService<ClinicWrittenDao, ClinicWritten> {

	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private ClinicWrittenDao clinicWrittenDao;
	@Autowired
	private DictService dictService;
	@Autowired
	private ClinicRegisterService clinicRegisterService;
	@Autowired
	private ClinicRegisterDao clinicRegisterDao;
	@Autowired
	private LicensedDao licensedDao;
	

	public ClinicWritten get(String id) {
		return super.get(id);
	}
	public List<ClinicWritten> findList(ClinicWritten clinicWritten) {
		return super.findList(clinicWritten);
	}
	
	public Page<ClinicWritten> findPage(Page<ClinicWritten> page, ClinicWritten clinicWritten) {
		return super.findPage(page, clinicWritten);
	}

	public final static char[] upper = "零一二三四五六七八九十".toCharArray();
	/**
	 *
	 * @param date
	 * @return
	 */
	public static String getUpperDate(String date) {
		//支持yyyy-MM-dd、yyyy/MM/dd、yyyyMMdd等格式
		if(date == null) return null;
		//非数字的都去掉
		date = date.replaceAll("\\D", "");
		if(date.length() != 8) return null;
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<4;i++) {//年
			sb.append(upper[Integer.parseInt(date.substring(i, i+1))]);
		}
		sb.append("年");//拼接年
		int month = Integer.parseInt(date.substring(4, 6));
		if(month <= 10) {
			sb.append(upper[month]);
		} else {
			sb.append("十").append(upper[month%10]);
		}
		sb.append("月");//拼接月

		int day = Integer.parseInt(date.substring(6));
		if (day <= 10) {
			sb.append(upper[day]);
		} else if(day < 20) {
			sb.append("十").append(upper[day % 10]);
		} else {
			sb.append(upper[day / 10]).append("十");
			int tmp = day % 10;
			if (tmp != 0) sb.append(upper[tmp]);
		}
		sb.append("日");//拼接日
		return sb.toString();
	}

	public void exportWord(ClinicWritten clinicWritten) throws IOException {
		Map<String, Object> beanParams = new HashMap<String, Object>();
		ClinicRegister	clinicRegiste=clinicRegisterService.get(clinicWritten.getRegister().getId());
		String casecode=  clinicRegiste.getCaseCode().substring(11, 16);
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
		beanParams.put("casecode", s);
		beanParams.put("t", clinicRegiste.getCaseCode().substring(2, 6));  
		String fileName = "法医鉴定报告";
		beanParams.put("delegate",clinicWritten.getDelegate());
		Dict dict = new Dict();
		if(StringUtils.isNotEmpty(clinicWritten.getToaccept()))
		{
			dict.setType("fayitypeCode");
			dict.setValue(clinicWritten.getToaccept());
			Dict toacceptCode = dictService.get(dict);
			beanParams.put("toaccept",toacceptCode==null?"":toacceptCode.getLabel());
		}
		beanParams.put("surveyorName",clinicRegiste.getSurveyorName());
		  if(clinicRegiste.getSurveyorSex()=="1"){
			  beanParams.put("surveyorSex","男");
	        }else{
	        beanParams.put("surveyorSex","女");
	        }
		beanParams.put("surveyorBirthday",clinicRegiste.getSurveyorBirthday());
		beanParams.put("idCard",clinicRegiste.getIdCard());
		
		int i=1;
		List<Strs>strs=new ArrayList<Strs>();
        if(!StringUtils.isEmpty( clinicRegiste.getClinicTriage())){
        	i=i+1;
        	Strs str1=new Strs();
        	str1.setB(i+"、验伤通知书"+clinicRegiste.getClinicTriage()+"份");
        	strs.add(str1);
        }
        if(!StringUtils.isEmpty(clinicRegiste.getClinicMedical())){
        	i=i+1;
        	Strs str1=new Strs();
        	str1.setB(i+"、病史"+clinicRegiste.getClinicMedical()+"份");
        	strs.add(str1);
        }
        if(!StringUtils.isEmpty(clinicRegiste.getClinicSummary())){
        	i=i+1;
        	Strs str1=new Strs();
        	str1.setB(i+"、出院小结"+clinicRegiste.getClinicSummary()+"份");
        	strs.add(str1);
        }
        if(!StringUtils.isEmpty(clinicRegiste.getClinicXray())){
        	i=i+1;
        	Strs str1=new Strs();
        	str1.setB(i+"、X片"+clinicRegiste.getClinicXray()+"张");
        	strs.add(str1);
        }
        if(!StringUtils.isEmpty(clinicRegiste.getClinicCt())){
        	i=i+1;
        	Strs str1=new Strs();
        	str1.setB(i+"、CT"+clinicRegiste.getClinicCt()+"张");
        	strs.add(str1);
        }
        if(!StringUtils.isEmpty(clinicRegiste.getClinicMri())){
        	i=i+1;
        	Strs str1=new Strs();
        	str1.setB(i+"、MRI"+clinicRegiste.getClinicMri()+"张");
        	strs.add(str1);
        }
        List<Licensed> licenseds = licensedDao.findEntrust(clinicRegiste.getId());
		beanParams.put("licenseds1", licenseds.get(0).getUser().getName());
		beanParams.put("licenseds2", licenseds.get(1).getUser().getName());
		beanParams.put("licenseds3", licenseds.get(2).getUser().getName());
		beanParams.put("lide1", UserUtils.get(licenseds.get(0).getUser().getId()).getEmail());
		beanParams.put("lide2", UserUtils.get(licenseds.get(1).getUser().getId()).getEmail());
		beanParams.put("lide3", UserUtils.get(licenseds.get(2).getUser().getId()).getEmail());
		beanParams.put("str1","1、委托书1份");
        beanParams.put("strs",strs);
		beanParams.put("acceptdate",clinicWritten.getAcceptdate());
		beanParams.put("identification",clinicWritten.getIdentification());
		beanParams.put("bysurveyor",clinicWritten.getBysurveyor());
		beanParams.put("opinion",clinicWritten.getOpinion());
		beanParams.put("clinicthispaper",clinicWritten.getClinicthispaper());
		beanParams.put("inspectionmethods",clinicWritten.getInspectionmethods());
		beanParams.put("appraisalstandard",clinicWritten.getAppraisalstandard());
		beanParams.put("authorisesurveyor",clinicWritten.getAuthorisesurveyor());
		beanParams.put("identifylocations",clinicWritten.getIdentifylocations());
		beanParams.put("personnel",clinicWritten.getPersonnel());
		beanParams.put("cc",clinicWritten.getCc());
		beanParams.put("body",clinicWritten.getBody());
		beanParams.put("reading",clinicWritten.getReading());
		beanParams.put("analysisshows",clinicWritten.getAnalysisshows());
		beanParams.put("expertopinion",clinicWritten.getExpertopinion());
		beanParams.put("firstuser",clinicWritten.getFirstuser());
		beanParams.put("secouduser",clinicWritten.getSecouduser());
		beanParams.put("authoriseuser",clinicWritten.getAuthoriseuser());


		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat ("yyyy年MM月dd日");

		String signDate = format.format(date);
		try
		{
			signDate =getUpperDate(signDate);
		}catch (Exception e){
			e.printStackTrace();
		}
		beanParams.put("signDate",signDate);
		WordExportUtil.writeResponse(WordExportUtil.WORD_2003,fileName,"templateDir","clinicWritten.ftl",beanParams,clinicRegiste);
	
		//word  转pdf
	    String docName = "D:"+File.separator+"information"+File.separator+"法医临床病理"+File.separator+clinicRegiste.getCode()+File.separator+fileName;
		String path="D:"+File.separator+"information"+File.separator+"法医临床病理"+File.separator+clinicRegiste.getCode()+File.separator+"report of forensic" + ".pdf";
		Word2007ToHtml.wordToPDF2(docName, path); 
	
	}


	@Transactional(readOnly = false)
	public void save(ClinicWritten clinicWritten) {
		
		boolean isNew =  clinicWritten.getIsNewRecord();
		ClinicRegister clinicRegister=clinicRegisterService.get(clinicWritten.getRegister().getId());
		if(isNew){
			//修改状态
			clinicRegister.setStatus("8");//clinic_status 归档
			clinicRegisterDao.update(clinicRegister);
		}
		
		super.save(clinicWritten);
		//生成文档
		try
		{
			exportWord(clinicWritten);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		Map<String, Object> vars = Maps.newHashMap();
		actTaskService.complete(clinicWritten.getAct().getTaskId(), clinicWritten.getAct().getProcInsId(), clinicWritten.getAct().getComment(), vars);
	}
	
	@Transactional(readOnly = false)
	public void delete(ClinicWritten clinicWritten) {
		super.delete(clinicWritten);
	}
	@Transactional(readOnly = false)
	public ClinicWritten findRegister(String registerId) {
		return clinicWrittenDao.findRegister(registerId);
	}
	
}