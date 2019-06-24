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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholRegister;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholSamples;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.clcohol.dao.ClcoholRegisterDao;
import com.thinkgem.jeesite.modules.clcohol.dao.ClcoholSamplesDao;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholSamplesIteam;
import com.thinkgem.jeesite.modules.clcohol.dao.ClcoholSamplesIteamDao;

/**
 * 酒精领取样品Service
 * @author fuyun
 * @version 2018-01-29
 */
@Service
@Transactional(readOnly = true)
public class ClcoholSamplesService extends CrudService<ClcoholSamplesDao, ClcoholSamples> {

	@Autowired
	private ClcoholSamplesIteamDao clcoholSamplesIteamDao;
	@Autowired
	private ClcoholSamplesDao clcoholSamplesDao;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private ClcoholRegisterDao clcoholRegisterDao;
	@Autowired
	private  ClcoholRegisterService clcoholRegisterService;

	
	public ClcoholSamples get(String id) {
		ClcoholSamples clcoholSamples = super.get(id);
		clcoholSamples.setClcoholSamplesIteamList(clcoholSamplesIteamDao.findList(new ClcoholSamplesIteam(clcoholSamples)));
		return clcoholSamples;
	}
	
	public ClcoholSamples getRegister(String registerId){
		ClcoholSamples clcoholSamples = clcoholSamplesDao.getRegister(registerId);
		clcoholSamples.setClcoholSamplesIteamList(clcoholSamplesIteamDao.findList(new ClcoholSamplesIteam(clcoholSamples)));
		return clcoholSamples;
	}
	
	public List<ClcoholSamples> findList(ClcoholSamples clcoholSamples) {
		return super.findList(clcoholSamples);
	}
	
	public Page<ClcoholSamples> findPage(Page<ClcoholSamples> page, ClcoholSamples clcoholSamples) {
		return super.findPage(page, clcoholSamples);
	}
	
	@Transactional(readOnly = false)
	public void save(ClcoholSamples clcoholSamples) {
		boolean isNew =  clcoholSamples.getIsNewRecord();
		ClcoholRegister	clcoholRegister =	clcoholRegisterService.get(clcoholSamples.getRegister().getId());
		 if(isNew){
	        	clcoholRegister.setOther2("4");//待鉴定
	        	clcoholRegisterDao.update(clcoholRegister);
	        }
		
		super.save(clcoholSamples);
		for (ClcoholSamplesIteam clcoholSamplesIteam : clcoholSamples.getClcoholSamplesIteamList()){
			if (clcoholSamplesIteam.getId() == null){
				continue;
			}
			if (ClcoholSamplesIteam.DEL_FLAG_NORMAL.equals(clcoholSamplesIteam.getDelFlag())){
				if (StringUtils.isBlank(clcoholSamplesIteam.getId())){
					clcoholSamplesIteam.setSamples(clcoholSamples);
					clcoholSamplesIteam.preInsert();
					clcoholSamplesIteamDao.insert(clcoholSamplesIteam);
				}else{
					clcoholSamplesIteam.preUpdate();
					clcoholSamplesIteamDao.update(clcoholSamplesIteam);
				}
			}else{
				clcoholSamplesIteamDao.delete(clcoholSamplesIteam);
			}
		}
		if(isNew){
			Map<String, Object> vars = Maps.newHashMap();
			actTaskService.complete(clcoholSamples.getAct().getTaskId(), clcoholSamples.getAct().getProcInsId(), clcoholSamples.getAct().getComment(), vars);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ClcoholSamples clcoholSamples) {
		super.delete(clcoholSamples);
		clcoholSamplesIteamDao.delete(new ClcoholSamplesIteam(clcoholSamples));
	}
	
}