/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholEvidence;
import com.thinkgem.jeesite.modules.clcohol.dao.ClcoholEvidenceDao;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholEvidenceIteam;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholRegister;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholSamples;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicRegister;
import com.thinkgem.jeesite.modules.entrust.entity.Word2007ToHtml;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.clcohol.dao.ClcoholEvidenceIteamDao;
import com.thinkgem.jeesite.modules.clcohol.dao.ClcoholRegisterDao;

/**
 * 酒精物证Service
 * @author fuyun
 * @version 2018-01-23
 */
@Service
@Transactional(readOnly = true)
public class ClcoholEvidenceService extends CrudService<ClcoholEvidenceDao, ClcoholEvidence> {

	@Autowired
	private ClcoholEvidenceIteamDao clcoholEvidenceIteamDao;
	@Autowired
	private ClcoholEvidenceDao clcoholEvidenceDao;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private ClcoholRegisterDao clcoholRegisterDao;
	@Autowired
	private  ClcoholRegisterService clcoholRegisterService;
	
	public ClcoholEvidence get(String id) {
		ClcoholEvidence clcoholEvidence = super.get(id);
		clcoholEvidence.setClcoholEvidenceIteamList(clcoholEvidenceIteamDao.findList(new ClcoholEvidenceIteam(clcoholEvidence)));
		return clcoholEvidence;
	}
	
	public List<ClcoholEvidence> findList(ClcoholEvidence clcoholEvidence){
		return super.findList(clcoholEvidence);
	}
	
	public ClcoholEvidence getRegister(String registerId){
		ClcoholEvidence clcoholEvidence = clcoholEvidenceDao.getRegister(registerId);
		clcoholEvidence.setClcoholEvidenceIteamList(clcoholEvidenceIteamDao.findList(new ClcoholEvidenceIteam(clcoholEvidence)));
		return  clcoholEvidence;
	}
	
	
	public Page<ClcoholEvidence> findPage(Page<ClcoholEvidence> page, ClcoholEvidence clcoholEvidence) {
		return super.findPage(page, clcoholEvidence);
	}
	
	@Transactional(readOnly = false)
	public void save(ClcoholEvidence clcoholEvidence,ClcoholRegister entity) {
		boolean isNew =  clcoholEvidence.getIsNewRecord();
		ClcoholRegister	clcoholRegister =	clcoholRegisterService.get(clcoholEvidence.getResgister().getId());
        if(isNew){
        	clcoholRegister.setOther2("3");//未领取样品
        	clcoholRegisterDao.update(clcoholRegister);
        }
		super.save(clcoholEvidence);
		for (ClcoholEvidenceIteam clcoholEvidenceIteam : clcoholEvidence.getClcoholEvidenceIteamList()){

			if (clcoholEvidenceIteam.getId() == null){
				continue;
			}
			if (ClcoholEvidenceIteam.DEL_FLAG_NORMAL.equals(clcoholEvidenceIteam.getDelFlag())){
					clcoholEvidenceIteam.setClcoholEvidence(clcoholEvidence);
					clcoholEvidenceIteam.preInsert();
					clcoholEvidenceIteamDao.insert(clcoholEvidenceIteam);
			}else{
				clcoholEvidenceIteamDao.delete(clcoholEvidenceIteam);
			}
		}
		try{
			exportWord(clcoholEvidence,entity);
		}catch (IOException e){
			return;
		}
		
		if(isNew){
			Map<String, Object> vars = Maps.newHashMap();
			actTaskService.complete(clcoholEvidence.getAct().getTaskId(), clcoholEvidence.getAct().getProcInsId(), clcoholEvidence.getAct().getComment(), vars);
		}
		
	}
	
	private void exportWord(ClcoholEvidence clcoholEvidence,ClcoholRegister	entity) throws IOException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		String casecode= entity.getCasecode().substring(10, 15);
    	int ss = 0;
    	int[] a = new int[casecode.length()];
    	for (int i = 0; i < casecode.length(); i++) {
    		//先由字符串转换成char,再转换成String,然后Integer
    		a[i] = Integer.parseInt(String.valueOf(casecode.charAt(i)));
    		if (a[i] != 0) {
    			ss = i;
    			break;
    		}
    	}
    	casecode = casecode.substring(ss, casecode.length());
    	int s=  Integer.parseInt(casecode);
    	UserUtils.getUser().getName();
		Map<String, Object> beanParams = new HashMap<String, Object>();
		beanParams.put("entrust", clcoholEvidence.getEntrust());
		beanParams.put("entrustDate",clcoholEvidence.getEntrustDate());
		beanParams.put("address",clcoholEvidence.getAddress());
		beanParams.put("miningdate",clcoholEvidence.getMiningdate());
		beanParams.put("other", clcoholEvidence.getOther());
		beanParams.put("inspectionNumber", clcoholEvidence.getInspectionNumber());
		beanParams.put("iteamList",clcoholEvidence.getClcoholEvidenceIteamList());
		beanParams.put("casecode",s);
		beanParams.put("t", entity.getCasecode().substring(2,6));
		beanParams.put("time",df.format(new Date()));
		beanParams.put("us", UserUtils.getUser().getName());
		if(clcoholEvidence.getType()!=null){
			
			beanParams.put("type", clcoholEvidence.getType());
		}else{
			beanParams.put("type", 3);
		}
		
		if( clcoholEvidence.getSampleState()!=null){
			
			beanParams.put("sampleStateCode", clcoholEvidence.getSampleState());
		}else{
			beanParams.put("sampleStateCode", 0);
		}
		
		if(clcoholEvidence.getMining()!=null){
			beanParams.put("mining",clcoholEvidence.getMining());
		}else{
			beanParams.put("mining",0);
		}
		WordExportUtil.writeResponse(WordExportUtil.WORD_2003,entity.getCasecode()+"的法医毒化室取（采）样 表","templateDir","clcoholevidence.ftl",beanParams,entity);
		//word  转pdf
	    String docName = "D:"+File.separator+"information"+File.separator+"毒物"+File.separator+entity.getCode()+File.separator+entity.getCasecode()+"的法医毒化室取（采）样 表";
		String path="D:"+File.separator+"information"+File.separator+"毒物"+File.separator+entity.getCode()+File.separator+"examination of forensic" + ".pdf";
		Word2007ToHtml.wordToPDF2(docName, path); 
		// TODO Auto-generated method stub
	}

	@Transactional(readOnly = false)
	public void delete(ClcoholEvidence clcoholEvidence) {
		super.delete(clcoholEvidence);
		clcoholEvidenceIteamDao.delete(new ClcoholEvidenceIteam(clcoholEvidence));
	}
	
}