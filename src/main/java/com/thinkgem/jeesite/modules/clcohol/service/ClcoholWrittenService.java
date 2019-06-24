/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholEvidence;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholEvidenceIteam;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholFirst;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholRegister;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholWritten;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicWritten;
import com.thinkgem.jeesite.modules.entrust.dao.LicensedDao;
import com.thinkgem.jeesite.modules.entrust.entity.Licensed;
import com.thinkgem.jeesite.modules.entrust.entity.Word2007ToHtml;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.clcohol.dao.ClcoholFirstDao;
import com.thinkgem.jeesite.modules.clcohol.dao.ClcoholRegisterDao;
import com.thinkgem.jeesite.modules.clcohol.dao.ClcoholWrittenDao;

/**
 * 酒精成文修改Service
 * @author fuyun
 * @version 2018-01-23
 */
@Service
@Transactional(readOnly = true)
public class ClcoholWrittenService extends CrudService<ClcoholWrittenDao, ClcoholWritten> {
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private ClcoholRegisterDao clcoholRegisterDao;
	@Autowired
	private  ClcoholRegisterService clcoholRegisterService;
	@Autowired
	private ClcoholWrittenDao clcoholWrittenDao;
	@Autowired
	private ClcoholEvidenceService clcoholEvidenceService;
	@Autowired
	private ClcoholFirstDao clcoholFirstDao;
	@Autowired
	private LicensedDao licensedDao;
	
	
	public ClcoholWritten get(String id) {
		return super.get(id);
	}
	
	public List<ClcoholWritten> findList(ClcoholWritten clcoholWritten) {
		return super.findList(clcoholWritten);
	}
	
	public Page<ClcoholWritten> findPage(Page<ClcoholWritten> page, ClcoholWritten clcoholWritten) {
		return super.findPage(page, clcoholWritten);
	}
	
	public ClcoholWritten getRegister(String registerId){
		return clcoholWrittenDao.getRegister(registerId);
	}
	
	@Transactional(readOnly = false)
	public void save(ClcoholWritten clcoholWritten) {
		boolean isNew =  clcoholWritten.getIsNewRecord();
		ClcoholRegister	clcoholRegister =	clcoholRegisterService.get(clcoholWritten.getRegister().getId());
		 if(isNew){
	        	clcoholRegister.setOther2("8");//归档
	        	clcoholRegisterDao.update(clcoholRegister);
	        }
		 try
			{
				exportWord(clcoholWritten,clcoholRegister);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		Map<String, Object> vars = Maps.newHashMap();
		actTaskService.complete(clcoholWritten.getAct().getTaskId(), clcoholWritten.getAct().getProcInsId(), clcoholWritten.getAct().getComment(), vars);
		super.save(clcoholWritten);
	}
	
	
	public void exportWord(ClcoholWritten clcoholWritten,ClcoholRegister clcoholRegister ) throws IOException {
		Map<String, Object> beanParams = new HashMap<String, Object>();
		String casecode= clcoholRegister.getCasecode().substring(10, 15);
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
		List<Licensed> licenseds = licensedDao.findEntrust(clcoholRegister.getId());
		beanParams.put("licenseds1", licenseds.get(0).getUser().getName());
		beanParams.put("licenseds2", licenseds.get(1).getUser().getName());
		beanParams.put("licenseds3", licenseds.get(2).getUser().getName());
		beanParams.put("lide1", UserUtils.get(licenseds.get(0).getUser().getId()).getEmail());
		beanParams.put("lide2", UserUtils.get(licenseds.get(1).getUser().getId()).getEmail());
		beanParams.put("lide3", UserUtils.get(licenseds.get(2).getUser().getId()).getEmail());
		ClcoholEvidence clcoholEvidence= clcoholEvidenceService.getRegister(clcoholRegister.getId());
		List<ClcoholEvidenceIteam>clcoholEvidenceIteamList	=clcoholEvidence.getClcoholEvidenceIteamList();
		String	nameA=null;
		String	codeA=null;
		ClcoholFirst clcoholFirst= clcoholFirstDao.findRegister(clcoholRegister.getId());
		for (ClcoholEvidenceIteam clcoholEvidenceIteam : clcoholEvidenceIteamList) {
			if(!StringUtils.isEmpty(clcoholEvidenceIteam.getCode())){
				codeA   =clcoholEvidenceIteam.getCode();
				nameA	=clcoholEvidenceIteam.getName();
			}
		}
		SimpleDateFormat format = new SimpleDateFormat ("yyyy年MM月dd日");

		
		String signDate = format.format(new Date());
		casecode = casecode.substring(ss, casecode.length());
		int s=  Integer.parseInt(casecode);
		beanParams.put("s", s);
		beanParams.put("t", clcoholRegister.getCasecode().substring(2, 6));
		beanParams.put("entrust", clcoholFirst.getEntrust());
		beanParams.put("acceptDate", clcoholFirst.getAcceptDate());
		beanParams.put("codeA", codeA);
		beanParams.put("personBeing", clcoholFirst.getPersonBeing());
		beanParams.put("basicFacts", clcoholWritten.getBasicFacts());
		beanParams.put("testResult", clcoholWritten.getTestResult());
		try
		{
			signDate =getUpperDate(signDate);
		}catch (Exception e){
			e.printStackTrace();
		}
		beanParams.put("signDate",signDate);
		WordExportUtil.writeResponse(WordExportUtil.WORD_2003,clcoholRegister.getCasecode()+"的法医毒化报告","templateDir","clcoholWritten.ftl",beanParams,clcoholRegister);
		//word  转pdf
	    String docName = "D:"+File.separator+"information"+File.separator+"毒物"+File.separator+clcoholRegister.getCode()+File.separator+clcoholRegister.getCasecode()+"的法医毒化报告";
		String path="D:"+File.separator+"information"+File.separator+"毒物"+File.separator+clcoholRegister.getCode()+File.separator+"clcohol of report" + ".pdf";
		Word2007ToHtml.wordToPDF2(docName, path); 
		}
	
	
	@Transactional(readOnly = false)
	public void delete(ClcoholWritten clcoholWritten) {
		super.delete(clcoholWritten);
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
}