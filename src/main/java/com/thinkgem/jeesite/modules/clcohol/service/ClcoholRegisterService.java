/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.act.utils.ActUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.filter.PhotoUploud;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholRegister;
import com.thinkgem.jeesite.modules.sys.service.SysCodeRuleService;
import com.thinkgem.jeesite.modules.clcohol.dao.ClcoholRegisterDao;

/**
 * 酒精委托书Service
 * @author fuyun
 * @version 2018-01-23
 */
@Service
@Transactional(readOnly = true)
public class ClcoholRegisterService extends CrudService<ClcoholRegisterDao, ClcoholRegister> {
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private SysCodeRuleService codeRuleService;
	
	public ClcoholRegister get(String id) {
		return super.get(id);
	}
	
	public List<ClcoholRegister> findList(ClcoholRegister clcoholRegister) {
		return super.findList(clcoholRegister);
	}
	
	public Page<ClcoholRegister> findPage(Page<ClcoholRegister> page, ClcoholRegister clcoholRegister) {
		return super.findPage(page, clcoholRegister);
	}
	
	@Transactional(readOnly = false)
	public void save(ClcoholRegister clcoholRegister,HttpServletRequest request) throws Exception {
		boolean isNew =  clcoholRegister.getIsNewRecord();
		if(isNew){
			clcoholRegister.setOther2("2");//未物证登记
			String code=codeRuleService.generateCode("entrust_register_code");
			clcoholRegister.setCode(code);
			String casecode=codeRuleService.generateCode("clcoholRegister_code");
			clcoholRegister.setCasecode(casecode);
		}
		// 上传到D盘的图片
		PhotoUploud photoUploud=new PhotoUploud();
		String toName = "D:"+File.separator+"information"+File.separator+"酒精"+File.separator+clcoholRegister.getCode()+File.separator+"酒精受理材料"+File.separator;
		photoUploud.UploudPhoto(clcoholRegister.getOther3(), request, toName);	
		super.save(clcoholRegister);
		if(isNew){
			actTaskService.startProcess(ActUtils.PD_ALCOHOL[0], ActUtils.PD_ALCOHOL[1], clcoholRegister.getId(), clcoholRegister.getCode());
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ClcoholRegister clcoholRegister) {
		super.delete(clcoholRegister);
	}
	
}