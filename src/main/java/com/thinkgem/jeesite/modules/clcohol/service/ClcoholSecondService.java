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
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholFirst;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholPapers;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholRegister;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholSecond;
import com.thinkgem.jeesite.modules.entrust.entity.Licensed;
import com.thinkgem.jeesite.modules.entrust.service.LicensedService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.clcohol.dao.ClcoholRegisterDao;
import com.thinkgem.jeesite.modules.clcohol.dao.ClcoholSecondDao;

/**
 * 酒精第二鉴定人Service
 * @author fuyun
 * @version 2018-01-23
 */
@Service
@Transactional(readOnly = true)
public class ClcoholSecondService extends CrudService<ClcoholSecondDao, ClcoholSecond> {
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private ClcoholSecondDao clcoholSecondDao;
	@Autowired
	private ClcoholRegisterDao clcoholRegisterDao;
	@Autowired
	private  ClcoholRegisterService clcoholRegisterService;
	@Autowired
	private ClcoholPapersService clcoholPapersService;
	@Autowired
	private LicensedService licensedService;
	
	public ClcoholSecond get(String id) {
		return super.get(id);
	}

	public ClcoholSecond findRegister(String registerId){
	   return 	clcoholSecondDao.findRegister(registerId);
	}
	
	public List<ClcoholSecond> findList(ClcoholSecond clcoholSecond) {
		return super.findList(clcoholSecond);
	}
	
	public Page<ClcoholSecond> findPage(Page<ClcoholSecond> page, ClcoholSecond clcoholSecond) {
		return super.findPage(page, clcoholSecond);
	}
	
	@Transactional(readOnly = false)
	public void save(ClcoholSecond clcoholSecond) {
		boolean isNew =  clcoholSecond.getIsNewRecord();
		ClcoholRegister	clcoholRegister =	clcoholRegisterService.get(clcoholSecond.getRegister().getId());
		 if(isNew){
	        	clcoholRegister.setOther2("6");//未授权签字人鉴定
	        	clcoholRegisterDao.update(clcoholRegister);
	        }
		 clcoholSecond.setBasicFacts(StringEscapeUtils.unescapeHtml4(clcoholSecond.getBasicFacts()));
		 clcoholSecond.setTestResult(StringEscapeUtils.unescapeHtml4(clcoholSecond.getTestResult()));
		 super.save(clcoholSecond);
		 ClcoholPapers clcoholPapers=new ClcoholPapers();
		 clcoholPapers.setRegister(clcoholSecond.getRegister());
		 clcoholPapers.setBasicfacts(StringEscapeUtils.unescapeHtml4(clcoholSecond.getBasicFacts()));
		 clcoholPapers.setWorkinstruction(StringEscapeUtils.unescapeHtml4(clcoholSecond.getTestResult()));
		 clcoholPapers.setPeople(clcoholSecond.getOther1());
		 clcoholPapersService.save(clcoholPapers);
		 Licensed licensed=new Licensed();
		 licensed.setEntrustId(clcoholSecond.getRegister().getId());
		 
		 licensed.setUser(UserUtils.getUser());
		 licensed.setUserBy(UserUtils.getUser().getLoginName());
		 licensedService.save(licensed);
		 Map<String, Object> vars = Maps.newHashMap();
		 actTaskService.complete(clcoholSecond.getAct().getTaskId(), clcoholSecond.getAct().getProcInsId(), clcoholSecond.getAct().getComment(), vars);
	}
	
	@Transactional(readOnly = false)
	public void delete(ClcoholSecond clcoholSecond) {
		super.delete(clcoholSecond);
	}
	
}