/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholReturn;
import com.thinkgem.jeesite.modules.clcohol.dao.ClcoholReturnDao;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholReturnIteam;
import com.thinkgem.jeesite.modules.clcohol.dao.ClcoholReturnIteamDao;

/**
 * 酒精样品归还Service
 * @author fuyun
 * @version 2018-01-23
 */
@Service
@Transactional(readOnly = true)
public class ClcoholReturnService extends CrudService<ClcoholReturnDao, ClcoholReturn> {

	@Autowired
	private ClcoholReturnIteamDao clcoholReturnIteamDao;
	
	public ClcoholReturn get(String id) {
		ClcoholReturn clcoholReturn = super.get(id);
		clcoholReturn.setClcoholReturnIteamList(clcoholReturnIteamDao.findList(new ClcoholReturnIteam(clcoholReturn)));
		return clcoholReturn;
	}
	
	public List<ClcoholReturn> findList(ClcoholReturn clcoholReturn) {
		return super.findList(clcoholReturn);
	}
	
	public Page<ClcoholReturn> findPage(Page<ClcoholReturn> page, ClcoholReturn clcoholReturn) {
		return super.findPage(page, clcoholReturn);
	}
	
	@Transactional(readOnly = false)
	public void save(ClcoholReturn clcoholReturn) {
		super.save(clcoholReturn);
		for (ClcoholReturnIteam clcoholReturnIteam : clcoholReturn.getClcoholReturnIteamList()){
			if (clcoholReturnIteam.getId() == null){
				continue;
			}
			if (ClcoholReturnIteam.DEL_FLAG_NORMAL.equals(clcoholReturnIteam.getDelFlag())){
				if (StringUtils.isBlank(clcoholReturnIteam.getId())){
					clcoholReturnIteam.setRegister(clcoholReturn);
					clcoholReturnIteam.preInsert();
					clcoholReturnIteamDao.insert(clcoholReturnIteam);
				}else{
					clcoholReturnIteam.preUpdate();
					clcoholReturnIteamDao.update(clcoholReturnIteam);
				}
			}else{
				clcoholReturnIteamDao.delete(clcoholReturnIteam);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ClcoholReturn clcoholReturn) {
		super.delete(clcoholReturn);
		clcoholReturnIteamDao.delete(new ClcoholReturnIteam(clcoholReturn));
	}
	
}