/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clinic.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicExamination;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicFirstdraft;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicPapers;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicPhysical;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicPhysicalIteam;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicRegister;
import com.thinkgem.jeesite.modules.entrust.entity.Licensed;
import com.thinkgem.jeesite.modules.entrust.service.LicensedService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.clinic.dao.ClinicExaminationDao;
import com.thinkgem.jeesite.modules.clinic.dao.ClinicFirstdraftDao;
import com.thinkgem.jeesite.modules.clinic.dao.ClinicPhysicalDao;
import com.thinkgem.jeesite.modules.clinic.dao.ClinicPhysicalIteamDao;
import com.thinkgem.jeesite.modules.clinic.dao.ClinicRegisterDao;

/**
 * 法医鉴定初稿Service
 * @author fuyn
 * @version 2017-11-26
 */
@Service
@Transactional(readOnly = true)
public class ClinicFirstdraftService extends CrudService<ClinicFirstdraftDao, ClinicFirstdraft> {

	@Autowired
     private ActTaskService actTaskService;
	@Autowired
	private ClinicExaminationDao clinicExaminationDao;
	@Autowired
	private ClinicFirstdraftDao clinicFirstdraftDao;
	@Autowired
	private ClinicPhysicalDao clinicPhysicalDao;
	@Autowired
	private ClinicPhysicalIteamDao clinicPhysicalIteamDao;
	@Autowired
	private ClinicRegisterService clinicRegisterService;
	@Autowired
	private ClinicRegisterDao clinicRegisterDao;
	@Autowired
	private ClinicPapersService clinicPapersService;
	@Autowired
	private LicensedService licensedService;
	
	public ClinicFirstdraft get(String id) {
		return super.get(id);
	}
	
	public List<ClinicFirstdraft> findList(ClinicFirstdraft clinicFirstdraft) {
		return super.findList(clinicFirstdraft);
	}
	
	public Page<ClinicFirstdraft> findPage(Page<ClinicFirstdraft> page, ClinicFirstdraft clinicFirstdraft) {
		return super.findPage(page, clinicFirstdraft);
	}
	
	
	
	
	
	@Transactional(readOnly = false)
	public void save(ClinicFirstdraft clinicFirstdraft) {
		boolean isNew =  clinicFirstdraft.getIsNewRecord();
		ClinicRegister clinicRegister=clinicRegisterService .get(clinicFirstdraft.getRegister().getId());
		Act act  = clinicFirstdraft.getAct();
		if(isNew){
			//修改状态
			clinicRegister.setStatus("5");//clinic_status 未第二鉴定人鉴定
			clinicRegisterDao.update(clinicRegister);
		}
		ClinicPapers clinicPapers=new ClinicPapers();
		clinicPapers.setRegister(clinicFirstdraft.getRegister());
		clinicPapers.setCommentaries(clinicFirstdraft.getClinicAttorney());
		clinicPapers.setClinicThisPaper(clinicFirstdraft.getClinicThisPaper());
		clinicPapers.setInspectionMethods(clinicFirstdraft.getInspectionMethods());
		clinicPapers.setAppraisalStandard(clinicFirstdraft.getAppraisalStandard());
		clinicPapers.setCc(clinicFirstdraft.getCc());
		clinicPapers.setBody(clinicFirstdraft.getBody());
		clinicPapers.setReading(clinicFirstdraft.getReading());
		clinicPapers.setAnalysisShows(clinicFirstdraft.getAnalysisShows());
		clinicPapers.setExpertOpinion(clinicFirstdraft.getExpertOpinion());
		clinicPapers.setRemarks(clinicFirstdraft.getRemarks());
		clinicPapers.setSurver(clinicFirstdraft.getFirstSurveyor());
		clinicPapersService.save(clinicPapers);
		 Licensed licensed=new Licensed();
		 licensed.setEntrustId(clinicFirstdraft.getRegister().getId());
		 licensed.setUser(UserUtils.getUser());
		 licensed.setUserBy(UserUtils.getUser().getLoginName());
		 licensedService.save(licensed);
		if(!isNew){
			clinicFirstdraft.setAct(act);
		}
			super.save(clinicFirstdraft);
	
		
		Map<String, Object> vars = Maps.newHashMap();
		actTaskService.complete(clinicFirstdraft.getAct().getTaskId(), clinicFirstdraft.getAct().getProcInsId(), clinicFirstdraft.getAct().getComment(), vars);
		
	
	}
	@Transactional(readOnly = false)
	public void delete(ClinicFirstdraft clinicFirstdraft) {
		super.delete(clinicFirstdraft);
	}
	
	@Transactional(readOnly = false)
	public ClinicExamination findExam(String registerId) {
		return clinicExaminationDao.findone(registerId);
	}
	
	@Transactional(readOnly = false)
	public ClinicFirstdraft findFirst(String registerId) {
		return clinicFirstdraftDao.findOne(registerId);
	}
	
	@Transactional(readOnly = false)
	public List<ClinicPhysicalIteam>  findClinicPhysical(String registerId){
		ClinicPhysical clinicPhysical= clinicPhysicalDao.findOne(registerId);
		List<ClinicPhysicalIteam> clinicPhysicalIteams= clinicPhysicalIteamDao.findone(clinicPhysical.getId());
		return clinicPhysicalIteams;
	}
	
	
	
	
}