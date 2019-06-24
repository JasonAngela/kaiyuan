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
import com.thinkgem.jeesite.modules.clinic.entity.ClinicAuthorise;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicPapers;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicRegister;
import com.thinkgem.jeesite.modules.entrust.entity.Licensed;
import com.thinkgem.jeesite.modules.entrust.service.LicensedService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.clinic.dao.ClinicAuthoriseDao;
import com.thinkgem.jeesite.modules.clinic.dao.ClinicRegisterDao;

/**
 * 法医授权签字人Service
 * @author fuyun
 * @version 2017-11-26
 */
@Service
@Transactional(readOnly = true)
public class ClinicAuthoriseService extends CrudService<ClinicAuthoriseDao, ClinicAuthorise> {
	
   @Autowired
   private ActTaskService actTaskService;
   @Autowired
   private ClinicAuthoriseDao clinicAuthoriseDao;
   @Autowired
   private ClinicPapersService clinicPapersService;
   @Autowired
   private ClinicRegisterService clinicRegisterService;
   @Autowired
   private ClinicRegisterDao clinicRegisterDao;
   @Autowired 
   private LicensedService licensedService;
   
   
	public ClinicAuthorise get(String id) {
		return super.get(id);
	}
	
	public List<ClinicAuthorise> findList(ClinicAuthorise clinicAuthorise) {
		return super.findList(clinicAuthorise);
	}
	
	public Page<ClinicAuthorise> findPage(Page<ClinicAuthorise> page, ClinicAuthorise clinicAuthorise) {
		return super.findPage(page, clinicAuthorise);
	}
	
	@Transactional(readOnly = false)
	public void save(ClinicAuthorise clinicAuthorise) {
		boolean isNew =  clinicAuthorise.getIsNewRecord();
		ClinicRegister clinicRegister=clinicRegisterService.get(clinicAuthorise.getRegister().getId());
		if(isNew){
			//修改状态
			clinicRegister.setStatus("7");//clinic_status 未成文修改
			clinicRegisterDao.update(clinicRegister);
		}
		clinicAuthorise.setOpinion(StringEscapeUtils.unescapeHtml4(clinicAuthorise.getOpinion()));
		clinicAuthorise.setClinicThisPaper(StringEscapeUtils.unescapeHtml4(clinicAuthorise.getClinicThisPaper()));
		clinicAuthorise.setInspectionMethods(StringEscapeUtils.unescapeHtml4(clinicAuthorise.getInspectionMethods()));
		clinicAuthorise.setAppraisalStandard(StringEscapeUtils.unescapeHtml4(clinicAuthorise.getAppraisalStandard()));
		clinicAuthorise.setCc(StringEscapeUtils.unescapeHtml4(clinicAuthorise.getCc()));
		clinicAuthorise.setBody(StringEscapeUtils.unescapeHtml4(clinicAuthorise.getBody()));
		clinicAuthorise.setReading(StringEscapeUtils.unescapeHtml4(clinicAuthorise.getReading()));
		clinicAuthorise.setAnalysisShows(StringEscapeUtils.unescapeHtml4(clinicAuthorise.getAnalysisShows()));
		clinicAuthorise.setExpertOpinion(StringEscapeUtils.unescapeHtml4(clinicAuthorise.getExpertOpinion()));
		clinicAuthorise.setRemarks(StringEscapeUtils.unescapeHtml4(clinicAuthorise.getRemarks()));
			super.save(clinicAuthorise);
		ClinicPapers clinicPapers=new ClinicPapers();
		clinicPapers.setRegister(clinicAuthorise.getRegister());
		clinicPapers.setCommentaries(StringEscapeUtils.unescapeHtml4(clinicAuthorise.getOpinion()));
		clinicPapers.setClinicThisPaper(StringEscapeUtils.unescapeHtml4(clinicAuthorise.getClinicThisPaper()));
		clinicPapers.setInspectionMethods(StringEscapeUtils.unescapeHtml4(clinicAuthorise.getInspectionMethods()));
		clinicPapers.setAppraisalStandard(StringEscapeUtils.unescapeHtml4(clinicAuthorise.getAppraisalStandard()));
		clinicPapers.setCc(StringEscapeUtils.unescapeHtml4(clinicAuthorise.getCc()));
		clinicPapers.setBody(StringEscapeUtils.unescapeHtml4(clinicAuthorise.getBody()));
		clinicPapers.setReading(StringEscapeUtils.unescapeHtml4(clinicAuthorise.getReading()));
		clinicPapers.setAnalysisShows(StringEscapeUtils.unescapeHtml4(clinicAuthorise.getAnalysisShows()));
		clinicPapers.setExpertOpinion(StringEscapeUtils.unescapeHtml4(clinicAuthorise.getExpertOpinion()));
		clinicPapers.setRemarks(StringEscapeUtils.unescapeHtml4(clinicAuthorise.getRemarks()));
		clinicPapers.setSurver(clinicAuthorise.getAuthoriseSurveyor());
		clinicPapersService.save(clinicPapers);
		 Licensed licensed=new Licensed();
		 licensed.setEntrustId(clinicAuthorise.getRegister().getId());
		 licensed.setUser(UserUtils.getUser());
		 licensed.setUserBy(UserUtils.getUser().getLoginName());
		 licensedService.save(licensed);
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("flag", "yes".equals(clinicAuthorise.getAct().getFlag())? "1" : "0");
		actTaskService.complete(clinicAuthorise.getAct().getTaskId(), clinicAuthorise.getAct().getProcInsId(), clinicAuthorise.getAct().getComment(), vars);
	}
	
	@Transactional(readOnly = false)
	public void delete(ClinicAuthorise clinicAuthorise) {
		super.delete(clinicAuthorise);
	}
	
	@Transactional(readOnly = false)
	public ClinicAuthorise findRegister(String registerId) {
		return clinicAuthoriseDao.findRegister(registerId);
	}
	
}