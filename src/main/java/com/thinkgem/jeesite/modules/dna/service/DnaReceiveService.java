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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.dna.entity.DnaReceive;
import com.thinkgem.jeesite.modules.dna.dao.DnaReceiveDao;
import com.thinkgem.jeesite.modules.dna.entity.DnaReceiveIteam;
import com.thinkgem.jeesite.modules.entrust.dao.EntrustAbstractsDao;
import com.thinkgem.jeesite.modules.entrust.dao.EntrustRegisterDao;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustRegister;
import com.thinkgem.jeesite.modules.entrust.service.EntrustRegisterService;
import com.thinkgem.jeesite.modules.dna.dao.DnaReceiveIteamDao;

/**
 * 领取样品Service
 * @author fuyun
 * @version 2018-06-22
 */
@Service
@Transactional(readOnly = true)
public class DnaReceiveService extends CrudService<DnaReceiveDao, DnaReceive> {

	@Autowired
	private DnaReceiveIteamDao dnaReceiveIteamDao;
	@Autowired
	private EntrustAbstractsDao entrustAbstractsDao;
	@Autowired
	private EntrustRegisterDao entrustRegisterDao;
	@Autowired
	private EntrustRegisterService entrustRegisterService;
	public DnaReceive get(String id) {
		DnaReceive dnaReceive = super.get(id);
		dnaReceive.setDnaReceiveIteamList(dnaReceiveIteamDao.findList(new DnaReceiveIteam(dnaReceive)));
		return dnaReceive;
	}
	
	public List<DnaReceive> findList(DnaReceive dnaReceive) {
		return super.findList(dnaReceive);
	}
	
	public Page<DnaReceive> findPage(Page<DnaReceive> page, DnaReceive dnaReceive) {
		return super.findPage(page, dnaReceive);
	}
	
	@Transactional(readOnly = false)
	public void save(DnaReceive dnaReceive) {
		super.save(dnaReceive);
		for (DnaReceiveIteam dnaReceiveIteam : dnaReceive.getDnaReceiveIteamList()){
			if (dnaReceiveIteam.getId() == null){
				continue;
			}
			if(entrustAbstractsDao.findSpecimenCode(dnaReceiveIteam.getSpecode())!=null){
	 			EntrustRegister entrustRegister = entrustRegisterService.get(entrustAbstractsDao.findSpecimenCode(dnaReceiveIteam.getSpecode()));
	 			if(entrustRegister!=null){
					entrustRegister.setStatus("4");
					entrustRegisterDao.update(entrustRegister);
		 			}
				}	
			
			
		
			if (DnaReceiveIteam.DEL_FLAG_NORMAL.equals(dnaReceiveIteam.getDelFlag())){
				if (StringUtils.isBlank(dnaReceiveIteam.getId())){
					dnaReceiveIteam.setRecive(dnaReceive);
					dnaReceiveIteam.preInsert();
					dnaReceiveIteamDao.insert(dnaReceiveIteam);
				}else{
					dnaReceiveIteam.preUpdate();
					dnaReceiveIteamDao.update(dnaReceiveIteam);
				}
			}else{
				dnaReceiveIteamDao.delete(dnaReceiveIteam);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(DnaReceive dnaReceive) {
		super.delete(dnaReceive);
		dnaReceiveIteamDao.delete(new DnaReceiveIteam(dnaReceive));
	}
	
}