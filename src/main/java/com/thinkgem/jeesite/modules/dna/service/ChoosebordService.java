/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dna.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.act.utils.ActUtils;
import com.thinkgem.jeesite.modules.dna.dao.ChoosebordDao;
import com.thinkgem.jeesite.modules.dna.entity.Choosebord;


/**
 * 选择板子流程Service
 * @author fuyun
 * @version 2018-06-20
 */
@Service
@Transactional(readOnly = true)
public class ChoosebordService extends CrudService<ChoosebordDao, Choosebord> {
	@Autowired
	private ActTaskService actTaskService;
	public Choosebord get(String id) {
		return super.get(id);
	}
	
	public List<Choosebord> findList(Choosebord choosebord) {
		return super.findList(choosebord);
	}
	
	public Page<Choosebord> findPage(Page<Choosebord> page, Choosebord choosebord) {
		return super.findPage(page, choosebord);
	}
	
	@Transactional(readOnly = false)
	public void save(Choosebord choosebord) {
		super.save(choosebord);
		actTaskService.startProcess(ActUtils.PD_DNA_APPRAISAL[0], ActUtils.PD_DNA_APPRAISAL[1], choosebord.getId(), choosebord.getCode());
	}
	
	@Transactional(readOnly = false)
	public void delete(Choosebord choosebord) {
		super.delete(choosebord);
	}
	
}