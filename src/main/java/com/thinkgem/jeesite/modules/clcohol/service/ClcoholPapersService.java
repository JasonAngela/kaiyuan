/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholPapers;
import com.thinkgem.jeesite.modules.clcohol.dao.ClcoholPapersDao;

/**
 * 酒精底稿Service
 * @author fuyun
 * @version 2018-05-18
 */
@Service
@Transactional(readOnly = true)
public class ClcoholPapersService extends CrudService<ClcoholPapersDao, ClcoholPapers> {

	public ClcoholPapers get(String id) {
		return super.get(id);
	}
	
	public List<ClcoholPapers> findList(ClcoholPapers clcoholPapers) {
		return super.findList(clcoholPapers);
	}
	
	public Page<ClcoholPapers> findPage(Page<ClcoholPapers> page, ClcoholPapers clcoholPapers) {
		return super.findPage(page, clcoholPapers);
	}
	
	@Transactional(readOnly = false)
	public void save(ClcoholPapers clcoholPapers) {
		super.save(clcoholPapers);
	}
	
	@Transactional(readOnly = false)
	public void delete(ClcoholPapers clcoholPapers) {
		super.delete(clcoholPapers);
	}
	
}