/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
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
import com.thinkgem.jeesite.modules.entrust.entity.Licensed;
import com.thinkgem.jeesite.modules.entrust.service.LicensedService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.clcohol.dao.ClcoholAuthorizationDao;
import com.thinkgem.jeesite.modules.clcohol.dao.ClcoholRegisterDao;

/**
 * 酒精授权签字人Service
 * @author fuyun
 * @version 2018-01-23
 */
@Service
@Transactional(readOnly = true)
public class ClcoholAuthorizationService extends CrudService<ClcoholAuthorizationDao, ClcoholAuthorization> {
	
	 @Autowired
	private ActTaskService actTaskService;
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
	public ClcoholAuthorization get(String id) {
		return super.get(id);
	}
	
	public ClcoholAuthorization findRegister(String registerId){
		return clcoholAuthorizationDao.findRegister(registerId);
	}
	
	public List<ClcoholAuthorization> findList(ClcoholAuthorization clcoholAuthorization) {
		return super.findList(clcoholAuthorization);
	}
	
	public Page<ClcoholAuthorization> findPage(Page<ClcoholAuthorization> page, ClcoholAuthorization clcoholAuthorization) {
		return super.findPage(page, clcoholAuthorization);
	}
	
	@Transactional(readOnly = false)
	public void save(ClcoholAuthorization clcoholAuthorization) { 
		boolean isNew =  clcoholAuthorization.getIsNewRecord();
		ClcoholRegister	clcoholRegister =	clcoholRegisterService.get(clcoholAuthorization.getRegister().getId());
		 if(isNew){
	        	clcoholRegister.setOther2("7");//未成文修改
	        	clcoholRegisterDao.update(clcoholRegister);
	        }
		clcoholAuthorization.setBasicFacts(StringEscapeUtils.unescapeHtml4(clcoholAuthorization.getBasicFacts()));
		clcoholAuthorization.setTestResult(StringEscapeUtils.unescapeHtml4(clcoholAuthorization.getTestResult()));
		super.save(clcoholAuthorization);
		ClcoholPapers clcoholPapers=new ClcoholPapers();
		clcoholPapers.setRegister(clcoholAuthorization.getRegister());
		clcoholPapers.setBasicfacts(StringEscapeUtils.unescapeHtml4(clcoholAuthorization.getBasicFacts()));
		clcoholPapers.setWorkinstruction(StringEscapeUtils.unescapeHtml4(clcoholAuthorization.getTestResult()));
		clcoholPapers.setPeople(clcoholAuthorization.getOther1());
		clcoholPapersService.save(clcoholPapers);
		 Licensed licensed=new Licensed();
		 licensed.setEntrustId(clcoholAuthorization.getRegister().getId());
		 licensed.setUser(UserUtils.getUser());
		 licensed.setUserBy(UserUtils.getUser().getLoginName());
		 licensedService.save(licensed);
		Map<String, Object> vars = Maps.newHashMap();
		System.out.println(clcoholAuthorization.getAct().getFlag());
		vars.put("flag", "yes".equals(clcoholAuthorization.getAct().getFlag())? "1" : "0");
		actTaskService.complete(clcoholAuthorization.getAct().getTaskId(), clcoholAuthorization.getAct().getProcInsId(), clcoholAuthorization.getAct().getComment(), vars);
	}
	
	@Transactional(readOnly = false)
	public void delete(ClcoholAuthorization clcoholAuthorization) {
		super.delete(clcoholAuthorization);
	}
	
}