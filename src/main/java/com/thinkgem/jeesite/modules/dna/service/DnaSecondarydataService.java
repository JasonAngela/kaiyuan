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
import com.thinkgem.jeesite.modules.dna.entity.DnaSecondarydata;
import com.thinkgem.jeesite.modules.dna.dao.DnaSecondarydataDao;

/**
 * dna临时导入二次数据表Service
 * @author fuyun
 * @version 2018-06-28
 */
@Service
@Transactional(readOnly = true)
public class DnaSecondarydataService extends CrudService<DnaSecondarydataDao, DnaSecondarydata> {
@Autowired
private DnaSecondarydataDao dnaSecondarydataDao;
	public DnaSecondarydata get(String id) {
		return super.get(id);
	}
	
	public List<DnaSecondarydata>findNo(){
		return dnaSecondarydataDao.findNo();
	}
	
	
	public List<DnaSecondarydata> findList(DnaSecondarydata dnaSecondarydata) {
		return super.findList(dnaSecondarydata);
	}
	
	public Page<DnaSecondarydata> findPage(Page<DnaSecondarydata> page, DnaSecondarydata dnaSecondarydata) {
		return super.findPage(page, dnaSecondarydata);
	}
	
	@Transactional(readOnly = false)
	public void save(DnaSecondarydata dnaSecondarydata) {
		super.save(dnaSecondarydata);
	}
	
	@Transactional(readOnly = false)
	public void delete(DnaSecondarydata dnaSecondarydata) {
		super.delete(dnaSecondarydata);
	}
	
}