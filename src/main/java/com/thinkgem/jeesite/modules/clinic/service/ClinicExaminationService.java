/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clinic.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.filter.PhotoUploud;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicExamination;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicPhysicalIteam;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicRegister;
import com.thinkgem.jeesite.modules.entrust.entity.Word2007ToHtml;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.clinic.dao.ClinicExaminationDao;
import com.thinkgem.jeesite.modules.clinic.dao.ClinicRegisterDao;

/**
 * 人员验伤Service
 * @author fuyun
 * @version 2017-11-26
 */
@Service
@Transactional(readOnly = true)
public class ClinicExaminationService extends CrudService<ClinicExaminationDao, ClinicExamination> {

	
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private ClinicRegisterService clinicRegisterService;
	@Autowired
	private ClinicRegisterDao clinicRegisterDao;
	
	
	public ClinicExamination get(String id) {
		return super.get(id);
	}
	
	public List<ClinicExamination> findList(ClinicExamination clinicExamination) {
		return super.findList(clinicExamination);
	}
	
	public Page<ClinicExamination> findPage(Page<ClinicExamination> page, ClinicExamination clinicExamination) {
		return super.findPage(page, clinicExamination);
	}

	public void exportWord(ClinicExamination clinicExamination) throws IOException{
		Map<String, Object> beanParams = new HashMap<String, Object>();
		ClinicRegister	clinicRegister =	clinicRegisterService.get(clinicExamination.getRegister().getId());
		
		 //DNA2017071100057
        String casecode = clinicRegister.getCaseCode().substring(11, 16);
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
         String time=  clinicExamination.getCheckTime().substring(0, 4)+"-"+clinicExamination.getCheckTime().substring(5, 7)+"-"+clinicExamination.getCheckTime().substring(8, 10);
        casecode = casecode.substring(ss, casecode.length());
        beanParams.put("simple", clinicRegister.getCaseCode().substring(2, 6));
        beanParams.put("casecode",casecode);
        beanParams.put("time",time);
		String fileName =clinicRegister.getAgentName() +"的人员验伤";
		beanParams.put("name",clinicExamination.getName());
		beanParams.put("sex",clinicExamination.getSex());
		beanParams.put("checkTime",clinicExamination.getCheckTime());
		beanParams.put("checkName",clinicExamination.getCheckName());
		beanParams.put("clinicSurveyor",clinicExamination.getClinicSurveyor());
		beanParams.put("checkAddress",clinicExamination.getCheckAddress());
		beanParams.put("cc",clinicExamination.getCc());
		beanParams.put("situation",clinicExamination.getSituation());
		beanParams.put("headFace",clinicExamination.getHeadFace());
		beanParams.put("trunk",clinicExamination.getTrunk());
		beanParams.put("limbs",clinicExamination.getLimbs());
		beanParams.put("other",clinicExamination.getOther());
		beanParams.put("reading",clinicExamination.getReading());
		beanParams.put("douCheck",clinicExamination.getDouCheck());
		WordExportUtil.writeResponse(WordExportUtil.WORD_2003,fileName,"templateDir","clinicExamination.ftl",beanParams,clinicRegister);
	
		//word  转pdf
	    String docName = "D:"+File.separator+"information"+File.separator+"法医临床病理"+File.separator+clinicRegister.getCode()+File.separator+fileName;
		String path="D:"+File.separator+"information"+File.separator+"法医临床病理"+File.separator+clinicRegister.getCode()+File.separator+"examination of forensic" + ".pdf";
		  
		Word2007ToHtml.wordToPDF2(docName, path); 
	
	}

	@Transactional(readOnly = false)
	public void save(ClinicExamination clinicExamination,HttpServletRequest request ) throws Exception {
		boolean isNew =  clinicExamination.getIsNewRecord();
		ClinicRegister	clinicRegister =	clinicRegisterService.get(clinicExamination.getRegister().getId());
		if(isNew){
			clinicRegister.setStatus("4");//clinic_status 待鉴定
			clinicRegisterDao.update(clinicRegister);
		}
		// 上传到D盘的图片
		PhotoUploud photoUploud=new PhotoUploud();
		String toName = "D:"+File.separator+"information"+File.separator+"法医临床病理"+File.separator+clinicRegister.getCode()+File.separator+"人员验伤图片"+File.separator;
		photoUploud.UploudPhoto(clinicExamination.getUploud(), request, toName);		
				
			super.save(clinicExamination);
			if(isNew){
			Map<String, Object> vars = Maps.newHashMap();
			actTaskService.complete(clinicExamination.getAct().getTaskId(), clinicExamination.getAct().getProcInsId(), clinicExamination.getAct().getComment(), vars);
			}

			//生成文档
		try{
			exportWord(clinicExamination);
		}catch (IOException e){
			return;
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ClinicExamination clinicExamination) {
		super.delete(clinicExamination);
	}
	
}