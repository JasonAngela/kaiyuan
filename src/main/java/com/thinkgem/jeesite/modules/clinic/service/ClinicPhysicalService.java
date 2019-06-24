/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clinic.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.filter.PhotoUploud;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicPhysical;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicPhysicalIteam;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicRegister;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustRegister;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.service.SysCodeRuleService;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.clinic.dao.ClinicPhysicalDao;
import com.thinkgem.jeesite.modules.clinic.dao.ClinicPhysicalIteamDao;
import com.thinkgem.jeesite.modules.clinic.dao.ClinicRegisterDao;

/**
 * 文件上传Service
 * @author fuyun
 * @version 2017-11-26
 */
@Service
@Transactional(readOnly = true)
public class ClinicPhysicalService extends CrudService<ClinicPhysicalDao, ClinicPhysical> {
	
	
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private ClinicPhysicalIteamDao clinicPhysicalIteamDao;
	@Autowired
	private SysCodeRuleService codeRuleService;
	@Autowired
	private ClinicRegisterService  clinicRegisterService;
	@Autowired
	private ClinicRegisterDao clinicRegisterDao;
	@Autowired
	private DictDao dictDao;
	@Autowired
	private ClinicPhysicalDao clinicPhysicalDao;
	
	public ClinicPhysical get(String id) {
		return super.get(id);
	}
	
	public List<ClinicPhysical> findList(ClinicPhysical clinicPhysical) {
		return super.findList(clinicPhysical);
	}
	
	public Page<ClinicPhysical> findPage(Page<ClinicPhysical> page, ClinicPhysical clinicPhysical) {
		return super.findPage(page, clinicPhysical);
	}
	
	@Transactional(readOnly = false)
	 public void save(ClinicPhysical clinicPhysical,HttpServletRequest request) throws Exception {
		boolean isNew =  clinicPhysical.getIsNewRecord();
		ClinicRegister clinicRegister=clinicRegisterService.get(clinicPhysical.getRegister().getId());
		//查看法医病理具体状态
		String fileName=dictDao.findType(clinicRegister.getType()).getLabel();
		if(isNew){
			//法医材料登记标号
			
			//修改状态
			clinicRegister.setStatus("3");//clinic_status 未人员验伤
			clinicRegisterDao.update(clinicRegister);
		}
		
		
		super.save(clinicPhysical);
		for (ClinicPhysicalIteam clinicPhysicalIteam : clinicPhysical.getClinicPhysicalIteamList()){
			if (clinicPhysicalIteam.getId() == null){ 
				continue;
			}
			if (ClinicPhysicalIteam.DEL_FLAG_NORMAL.equals(clinicPhysicalIteam.getDelFlag())){
				if (StringUtils.isBlank(clinicPhysicalIteam.getId())){
					if(isNew){
						String casecode=codeRuleService.generateCode("clinic_cailiaoCode");
						//材料编号
						clinicPhysicalIteam.setCode(casecode);
					}
					clinicPhysicalIteam.setPhysical(clinicPhysical);
					clinicPhysicalIteam.preInsert();
					clinicPhysicalIteamDao.insert(clinicPhysicalIteam);
				}else{
					clinicPhysicalIteam.setPhysical(clinicPhysical);
					clinicPhysicalIteam.preUpdate();
					clinicPhysicalIteamDao.update(clinicPhysicalIteam);
				}
			}else{
				clinicPhysicalIteamDao.delete(clinicPhysicalIteam);
			}    
		}
		if(isNew){
			Map<String, Object> vars = Maps.newHashMap();
		    actTaskService.complete(clinicPhysical.getAct().getTaskId(), clinicPhysical.getAct().getProcInsId(), clinicPhysical.getAct().getComment(), vars);
		}
	}
	
	
	
	@Transactional(readOnly = false)
	public void nextOne(String  registerId) {
		ClinicPhysical	clinicPhysical=clinicPhysicalDao.findOne(registerId);
		Map<String, Object> vars = Maps.newHashMap();
	    actTaskService.complete(clinicPhysical.getAct().getTaskId(), clinicPhysical.getAct().getProcInsId(), clinicPhysical.getAct().getComment(), vars);
	}
	
	
	@Transactional(readOnly = false)
	public void delete(ClinicPhysical clinicPhysical) {
		super.delete(clinicPhysical);
	}

	
}