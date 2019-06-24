/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholAuthorization;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholFirst;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholPapers;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholRegister;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicPapers;
import com.thinkgem.jeesite.modules.clinic.service.ClinicPapersService;
import com.thinkgem.jeesite.modules.entrust.entity.Licensed;
import com.thinkgem.jeesite.modules.entrust.service.LicensedService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.clcohol.dao.ClcoholAuthorizationDao;
import com.thinkgem.jeesite.modules.clcohol.dao.ClcoholFirstDao;
import com.thinkgem.jeesite.modules.clcohol.dao.ClcoholRegisterDao;

/**
 * 酒精初稿Service
 * @author fuyun
 * @version 2018-01-23
 */
@Service
@Transactional(readOnly = true)
public class ClcoholFirstService extends CrudService<ClcoholFirstDao, ClcoholFirst> {
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private ClcoholFirstDao clcoholFirstDao;
	@Autowired
	private ClcoholAuthorizationDao clcoholAuthorizationDao;
	@Autowired
	private ClcoholRegisterDao clcoholRegisterDao;
	@Autowired
	private  ClcoholRegisterService clcoholRegisterService;
	@Autowired
	private ClcoholPapersService clcoholPapersService;
	@Autowired
	private LicensedService licensedService;
	
	public ClcoholAuthorization findRegisterAu(String registerId){
		return clcoholAuthorizationDao.findRegister(registerId);
	}
	
	public ClcoholFirst get(String id) {
		return super.get(id);
	}
	
	public ClcoholFirst findRegister(String registerId){
		return clcoholFirstDao.findRegister(registerId);
	}
	
	public List<ClcoholFirst> findList(ClcoholFirst clcoholFirst) {
		return super.findList(clcoholFirst);
	}
	
	public Page<ClcoholFirst> findPage(Page<ClcoholFirst> page, ClcoholFirst clcoholFirst) {
		return super.findPage(page, clcoholFirst);
	}
	
	@Transactional(readOnly = false)
	public void save(ClcoholFirst clcoholFirst) {
		Act act  = clcoholFirst.getAct();
		boolean isNew =  clcoholFirst.getIsNewRecord();
		ClcoholRegister	clcoholRegister =	clcoholRegisterService.get(clcoholFirst.getRegister().getId());
		 if(isNew){
	        	clcoholRegister.setOther2("5");//未第二鉴定人鉴定
	        	clcoholRegisterDao.update(clcoholRegister);
	        }
		 
		 ClcoholPapers clcoholPapers=new ClcoholPapers();
		 clcoholPapers.setRegister(clcoholFirst.getRegister());
		 clcoholPapers.setBasicfacts(clcoholFirst.getBasicFacts());
		 clcoholPapers.setWorkinstruction(clcoholFirst.getTestResults());
		 clcoholPapers.setPeople(clcoholFirst.getOther1());
		 clcoholPapersService.save(clcoholPapers);
			Licensed licensed=new Licensed();
			licensed.setEntrustId(clcoholFirst.getRegister().getId());
			licensed.setUser(UserUtils.getUser());
			licensed.setUserBy(UserUtils.getUser().getLoginName());
			licensedService.save(licensed);
		 
		 if(!isNew){
			clcoholFirst.setAct(act);
		}
		super.save(clcoholFirst);
		Map<String, Object> vars = Maps.newHashMap();
		actTaskService.complete(clcoholFirst.getAct().getTaskId(), clcoholFirst.getAct().getProcInsId(), clcoholFirst.getAct().getComment(), vars);
	}
	
	@Transactional(readOnly = false)
	public void delete(ClcoholFirst clcoholFirst) {
		super.delete(clcoholFirst);
	}
	
}