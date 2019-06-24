/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clinic.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicPapers;
import com.thinkgem.jeesite.modules.clinic.dao.ClinicPapersDao;

/**
 * 法医底稿Service
 * @author fuyun
 * @version 2017-12-20
 */
@Service
@Transactional(readOnly = true)
public class ClinicPapersService extends CrudService<ClinicPapersDao, ClinicPapers> {

	public ClinicPapers get(String id) {
		return super.get(id);
	}
	
	public List<ClinicPapers> findList(ClinicPapers clinicPapers) {
		return super.findList(clinicPapers);
	}
	
	public Page<ClinicPapers> findPage(Page<ClinicPapers> page, ClinicPapers clinicPapers) {
		return super.findPage(page, clinicPapers);
	}
	
	@Transactional(readOnly = false)
	public void save(ClinicPapers clinicPapers) {
		super.save(clinicPapers);
	}
	
	@Transactional(readOnly = false)
	public void delete(ClinicPapers clinicPapers) {
		super.delete(clinicPapers);
	}
	
}