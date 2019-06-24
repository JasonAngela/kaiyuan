/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clinic.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicInspection;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicPapers;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicRegister;
import com.thinkgem.jeesite.modules.entrust.entity.Licensed;
import com.thinkgem.jeesite.modules.entrust.service.LicensedService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.clinic.dao.ClinicInspectionDao;
import com.thinkgem.jeesite.modules.clinic.dao.ClinicRegisterDao;

/**
 * 法医第二鉴定人复检Service
 * @author fuyun
 * @version 2017-11-26
 */
@Service
@Transactional(readOnly = true)
public class ClinicInspectionService extends CrudService<ClinicInspectionDao, ClinicInspection> {

	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private ClinicInspectionDao clinicInspectionDao;
	@Autowired
	private ClinicPapersService clinicPapersService;
	@Autowired
	private ClinicRegisterDao clinicRegisterDao;
	@Autowired
	private ClinicRegisterService clinicRegisterService;
	@Autowired
	private LicensedService licensedService;
	
	public ClinicInspection findRegister(String registerId){
		return clinicInspectionDao.findRegister(registerId);
	}
	
	
	public ClinicInspection get(String id) {
		return super.get(id);
	}
	
	public List<ClinicInspection> findList(ClinicInspection clinicInspection) {
		return super.findList(clinicInspection);
	}
	
	public Page<ClinicInspection> findPage(Page<ClinicInspection> page, ClinicInspection clinicInspection) {
		return super.findPage(page, clinicInspection);
	}
	
	@Transactional(readOnly = false)
	public void save(ClinicInspection clinicInspection) {
		boolean isNew =  clinicInspection.getIsNewRecord();
		ClinicRegister clinicRegister=clinicRegisterService.get(clinicInspection.getRegister().getId());
		if(isNew){
			//修改状态
			clinicRegister.setStatus("6");//clinic_status 未授权签字人鉴定
			clinicRegisterDao.update(clinicRegister);
		}
		clinicInspection.setClinicAttorney(StringEscapeUtils.unescapeHtml4(clinicInspection.getClinicAttorney().trim()));
		clinicInspection.setClinicThisPaper(StringEscapeUtils.unescapeHtml4(clinicInspection.getClinicThisPaper().trim()));
		clinicInspection.setInspectionMethods(StringEscapeUtils.unescapeHtml4(clinicInspection.getInspectionMethods().trim()));
		clinicInspection.setAppraisalStandard(StringEscapeUtils.unescapeHtml4(clinicInspection.getAppraisalStandard().trim()));
		clinicInspection.setCc(StringEscapeUtils.unescapeHtml4(clinicInspection.getCc().trim()));
		clinicInspection.setBody(StringEscapeUtils.unescapeHtml4(clinicInspection.getBody().trim()));
		clinicInspection.setAnalysisShows(StringEscapeUtils.unescapeHtml4(clinicInspection.getAnalysisShows().trim()));
		clinicInspection.setExpertOpinion(StringEscapeUtils.unescapeHtml4(clinicInspection.getExpertOpinion().trim()));
		clinicInspection.setRemarks(StringEscapeUtils.unescapeHtml4(clinicInspection.getRemarks().trim()));
		clinicInspection.setReading(StringEscapeUtils.unescapeHtml4(clinicInspection.getReading().trim()));
		super.save(clinicInspection);
		ClinicPapers clinicPapers=new ClinicPapers();
		clinicPapers.setRegister(clinicInspection.getRegister());
		clinicPapers.setCommentaries(StringEscapeUtils.unescapeHtml4(clinicInspection.getClinicAttorney()));
		clinicPapers.setClinicThisPaper(StringEscapeUtils.unescapeHtml4(clinicInspection.getClinicThisPaper()));
		clinicPapers.setInspectionMethods(StringEscapeUtils.unescapeHtml4(clinicInspection.getInspectionMethods()));
		clinicPapers.setAppraisalStandard(StringEscapeUtils.unescapeHtml4(clinicInspection.getAppraisalStandard()));
		clinicPapers.setCc(StringEscapeUtils.unescapeHtml4(clinicInspection.getCc()));
		clinicPapers.setBody(StringEscapeUtils.unescapeHtml4(clinicInspection.getBody()));
		clinicPapers.setReading(StringEscapeUtils.unescapeHtml4(clinicInspection.getReading()));
		clinicPapers.setAnalysisShows(StringEscapeUtils.unescapeHtml4(clinicInspection.getAnalysisShows()));
		clinicPapers.setExpertOpinion(StringEscapeUtils.unescapeHtml4(clinicInspection.getExpertOpinion()));
		clinicPapers.setRemarks(StringEscapeUtils.unescapeHtml4(clinicInspection.getRemarks()));
		clinicPapers.setSurver(clinicInspection.getSecondSurveyor());
		clinicPapersService.save(clinicPapers);
		 Licensed licensed=new Licensed();
		 licensed.setEntrustId(clinicInspection.getRegister().getId());
		 licensed.setUser(UserUtils.getUser());
		 licensed.setUserBy(UserUtils.getUser().getLoginName());
		 licensedService.save(licensed);
		Map<String, Object> vars = Maps.newHashMap();
		actTaskService.complete(clinicInspection.getAct().getTaskId(), clinicInspection.getAct().getProcInsId(), clinicInspection.getAct().getComment(), vars);
	}
	
	@Transactional(readOnly = false)
	public void delete(ClinicInspection clinicInspection) {
		super.delete(clinicInspection);
	}
	
}