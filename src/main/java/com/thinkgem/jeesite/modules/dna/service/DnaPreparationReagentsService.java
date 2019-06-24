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
import com.thinkgem.jeesite.modules.dna.entity.DnaExperimentSpecimen;
import com.thinkgem.jeesite.modules.dna.entity.DnaExtractRecordItem;
import com.thinkgem.jeesite.modules.dna.entity.DnaPreparationReagents;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.dna.dao.DnaExperimentSpecimenDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaExtractRecordItemDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaPreparationReagentsDao;
import com.thinkgem.jeesite.modules.dna.entity.DnaPreparationReagentsIteam;
import com.thinkgem.jeesite.modules.entrust.dao.EntrustAbstractsDao;
import com.thinkgem.jeesite.modules.entrust.dao.EntrustRegisterDao;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustRegister;
import com.thinkgem.jeesite.modules.entrust.service.EntrustRegisterService;
import com.thinkgem.jeesite.modules.dna.dao.DnaPreparationReagentsIteamDao;

/**
 * 试剂配制记录表Service
 * @author fyun
 * @version 2017-09-06
 */
@Service
@Transactional(readOnly = true)
public class DnaPreparationReagentsService extends CrudService<DnaPreparationReagentsDao, DnaPreparationReagents> {

	@Autowired
	private DnaPreparationReagentsIteamDao dnaPreparationReagentsIteamDao;
	@Autowired
	private DnaExperimentSpecimenDao dnaExperimentSpecimenDao;
	@Autowired
	private DnaExtractRecordItemDao dnaExtractRecordItemDao;
	@Autowired
	private EntrustAbstractsDao entrustAbstractsDao;
	@Autowired
	private EntrustRegisterDao entrustRegisterDao;
	@Autowired
	private EntrustRegisterService entrustRegisterService;

	
	
	public DnaPreparationReagents get(String id) {
		DnaPreparationReagents dnaPreparationReagents = super.get(id);
		dnaPreparationReagents.setDnaPreparationReagentsIteamList(dnaPreparationReagentsIteamDao.findList(new DnaPreparationReagentsIteam(dnaPreparationReagents)));
		return dnaPreparationReagents;
	}
	
	
	public DnaPreparationReagentsIteam noSecondary(String code){
		return dnaPreparationReagentsIteamDao.noSecondary(code);
	}
	
	public List<DnaPreparationReagentsIteam> getNotPre(){
		return dnaPreparationReagentsIteamDao.getNot();
	}
	
	
	public List<DnaExtractRecordItem>getNotExtra(){
		return dnaExtractRecordItemDao.getNot();
	}
	
	public List<DnaExperimentSpecimen>getNot(){
		return dnaExperimentSpecimenDao.getNot();
	}
	
	public List<DnaPreparationReagents> findList(DnaPreparationReagents dnaPreparationReagents) {
		return super.findList(dnaPreparationReagents);
	}
	
	public Page<DnaPreparationReagents> findPage(Page<DnaPreparationReagents> page, DnaPreparationReagents dnaPreparationReagents) {
		return super.findPage(page, dnaPreparationReagents);
	}
	
	@Transactional(readOnly = false)
	public void save(DnaPreparationReagents dnaPreparationReagents) {
		super.save(dnaPreparationReagents);
		for (DnaPreparationReagentsIteam dnaPreparationReagentsIteam : dnaPreparationReagents.getDnaPreparationReagentsIteamList()){
			if (dnaPreparationReagentsIteam.getId() == null){
				continue;
			}
			
			if(entrustAbstractsDao.findSpecimenCode(dnaPreparationReagentsIteam.getSampleNumber())!=null){
	 			EntrustRegister entrustRegister = entrustRegisterService.get(entrustAbstractsDao.findSpecimenCode(dnaPreparationReagentsIteam.getSampleNumber()));
	 			if(entrustRegister!=null){
	 				//已扩增
					entrustRegister.setStatus("6");
					entrustRegisterDao.update(entrustRegister);
		 			}
				}	
			
			if (DnaPreparationReagentsIteam.DEL_FLAG_NORMAL.equals(dnaPreparationReagentsIteam.getDelFlag())){
				if (StringUtils.isBlank(dnaPreparationReagentsIteam.getId())){
					dnaPreparationReagentsIteam.setDnaPreparationReagents(dnaPreparationReagents);
					dnaPreparationReagentsIteam.preInsert();
					dnaPreparationReagentsIteamDao.insert(dnaPreparationReagentsIteam);
				}else{
					dnaPreparationReagentsIteam.preUpdate();
					dnaPreparationReagentsIteamDao.update(dnaPreparationReagentsIteam);
				}
			}else{
				dnaPreparationReagentsIteamDao.delete(dnaPreparationReagentsIteam);
			}
		}
		// 启动流程
	//	Map<String, Object> vars = Maps.newHashMap();
	//actTaskService.complete(dnaPreparationReagents.getAct().getTaskId(), dnaPreparationReagents.getAct().getProcInsId(), dnaPreparationReagents.getAct().getComment(), dnaPreparationReagents.getCode(), vars);
	}
	
	@Transactional(readOnly = false)
	public void delete(DnaPreparationReagents dnaPreparationReagents) {
		super.delete(dnaPreparationReagents);
		dnaPreparationReagentsIteamDao.delete(new DnaPreparationReagentsIteam(dnaPreparationReagents));
	}
	
}