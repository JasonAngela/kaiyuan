/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entrust.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.filter.PhotoUploud;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustChargeCredentials;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustRegister;
import com.thinkgem.jeesite.modules.entrust.dao.EntrustChargeCredentialsDao;

/**
 * 收费凭据Service
 * @author 浮云
 * @version 2017-09-14
 */
@Service
@Transactional(readOnly = true)
public class EntrustChargeCredentialsService extends CrudService<EntrustChargeCredentialsDao, EntrustChargeCredentials> {

	public EntrustChargeCredentials get(String id) {
		return super.get(id);
	}
	
	public List<EntrustChargeCredentials> findList(EntrustChargeCredentials entrustChargeCredentials) {
		return super.findList(entrustChargeCredentials);
	}
	
	public Page<EntrustChargeCredentials> findPage(Page<EntrustChargeCredentials> page, EntrustChargeCredentials entrustChargeCredentials) {
		return super.findPage(page, entrustChargeCredentials);
	}
	
	@Transactional(readOnly = false)
	public void save(EntrustChargeCredentials entrustChargeCredentials,EntrustRegister entrustRegiste,HttpServletRequest request) throws Exception {
		String toName = "D:" + File.separator + "information" + File.separator + "DNA" + File.separator + entrustRegiste.getCode() + File.separator + "charge" + File.separator;
		PhotoUploud photoUploud = new PhotoUploud();
            photoUploud.UploudPhoto(entrustChargeCredentials.getPic(), request, toName);
		
		super.save(entrustChargeCredentials);
	}
	
	@Transactional(readOnly = false)
	public void delete(EntrustChargeCredentials entrustChargeCredentials) {
		super.delete(entrustChargeCredentials);
	}
	
}