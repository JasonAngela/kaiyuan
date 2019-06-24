/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dna.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.dna.entity.DnaElectrophoresisParting;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.act.utils.ActUtils;
import com.thinkgem.jeesite.modules.dna.dao.DnaElectrophoresisPartingDao;
import com.thinkgem.jeesite.modules.dna.entity.DnaElectrophoresisPartingIteam;
import com.thinkgem.jeesite.modules.entrust.dao.EntrustAbstractsDao;
import com.thinkgem.jeesite.modules.entrust.dao.EntrustRegisterDao;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustRegister;
import com.thinkgem.jeesite.modules.entrust.service.EntrustRegisterService;
import com.thinkgem.jeesite.modules.dna.dao.DnaElectrophoresisPartingIteamDao;

/**
 * 电泳室Service
 * @author fuyun
 * @version 2017-09-06
 */
@Service
@Transactional(readOnly = true)
public class DnaElectrophoresisPartingService extends CrudService<DnaElectrophoresisPartingDao, DnaElectrophoresisParting> {

	@Autowired
	private DnaElectrophoresisPartingIteamDao dnaElectrophoresisPartingIteamDao;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private DnaElectrophoresisPartingDao dnaElectrophoresisPartingDao;

	public DnaElectrophoresisParting get(String id) {
		DnaElectrophoresisParting dnaElectrophoresisParting = super.get(id);
		dnaElectrophoresisParting.setDnaElectrophoresisPartingIteamList(dnaElectrophoresisPartingIteamDao.findList(new DnaElectrophoresisPartingIteam(dnaElectrophoresisParting)));
		return dnaElectrophoresisParting;
	}
	
	public List<DnaElectrophoresisParting> findList(DnaElectrophoresisParting dnaElectrophoresisParting) {
		return super.findList(dnaElectrophoresisParting);
	}
	
	public Page<DnaElectrophoresisParting> findPage(Page<DnaElectrophoresisParting> page, DnaElectrophoresisParting dnaElectrophoresisParting) {
		return super.findPage(page, dnaElectrophoresisParting);
	}
	
	public List<DnaElectrophoresisParting> findCode(String code){
		return	dnaElectrophoresisPartingDao.getFind (code); 
	}
	
	
	
	@Transactional(readOnly = false)
	public void save(DnaElectrophoresisParting dnaElectrophoresisParting) {
		super.save(dnaElectrophoresisParting);
		for (DnaElectrophoresisPartingIteam dnaElectrophoresisPartingIteam : dnaElectrophoresisParting.getDnaElectrophoresisPartingIteamList()){
			if (dnaElectrophoresisPartingIteam.getId() == null){
				continue;
			}
			
			/*if(entrustAbstractsDao.findSpecimenCode(dnaElectrophoresisPartingIteam.getSampleNumber())!=null){
	 			EntrustRegister entrustRegister = entrustRegisterService.get(entrustAbstractsDao.findSpecimenCode(dnaElectrophoresisPartingIteam.getSampleNumber()));
	 			if(entrustRegister!=null){
	 				//已电泳
					entrustRegister.setStatus("7");
					entrustRegisterDao.update(entrustRegister);
		 			}
				}	*/
			if (DnaElectrophoresisPartingIteam.DEL_FLAG_NORMAL.equals(dnaElectrophoresisPartingIteam.getDelFlag())){
				if (StringUtils.isBlank(dnaElectrophoresisPartingIteam.getId())){
					dnaElectrophoresisPartingIteam.setDnaElectrophoresisParting(dnaElectrophoresisParting);
					dnaElectrophoresisPartingIteam.preInsert();
					dnaElectrophoresisPartingIteamDao.insert(dnaElectrophoresisPartingIteam);
				}else{
					dnaElectrophoresisPartingIteam.preUpdate();
					dnaElectrophoresisPartingIteamDao.update(dnaElectrophoresisPartingIteam);
				}
			}else{
				dnaElectrophoresisPartingIteamDao.delete(dnaElectrophoresisPartingIteam);
			}
		}
		// 启动流程
		
		actTaskService.startProcess(ActUtils.PD_DNA_APPRAISAL[0], ActUtils.PD_DNA_APPRAISAL[1], dnaElectrophoresisParting.getId(), dnaElectrophoresisParting.getCode());
		//Map<String, Object> vars = Maps.newHashMap();
		//actTaskService.complete(dnaElectrophoresisParting.getAct().getTaskId(), dnaElectrophoresisParting.getAct().getProcInsId(), dnaElectrophoresisParting.getAct().getComment(), dnaElectrophoresisParting.getCode(), vars);
	}
	
	@Transactional(readOnly = false)
	public void delete(DnaElectrophoresisParting dnaElectrophoresisParting) {
		super.delete(dnaElectrophoresisParting);
		dnaElectrophoresisPartingIteamDao.delete(new DnaElectrophoresisPartingIteam(dnaElectrophoresisParting));
	}
	
}