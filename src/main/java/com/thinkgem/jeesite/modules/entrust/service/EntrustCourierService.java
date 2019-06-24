/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entrust.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustCourier;
import com.thinkgem.jeesite.modules.entrust.dao.EntrustCourierDao;

/**
 * dna快递Service
 * @author fuyun
 * @version 2018-01-12
 */
@Service
@Transactional(readOnly = true)
public class EntrustCourierService extends CrudService<EntrustCourierDao, EntrustCourier> {

	@Autowired
     private EntrustCourierDao courierDao;
	public EntrustCourier get(String id) {
		return super.get(id);
	}
	
	public List<EntrustCourier> findList(EntrustCourier entrustCourier) {
		return super.findList(entrustCourier);
	}
	
	public EntrustCourier findEntrust(String entrustId){
		return courierDao.findEntrust(entrustId);
	}
	
	
	public Page<EntrustCourier> findPage(Page<EntrustCourier> page, EntrustCourier entrustCourier) {
		return super.findPage(page, entrustCourier);
	}
	
	@Transactional(readOnly = false)
	public void save(EntrustCourier entrustCourier) {
		super.save(entrustCourier);
	}
	
	@Transactional(readOnly = false)
	public void delete(EntrustCourier entrustCourier) {
		super.delete(entrustCourier);
	}
	
	
	
	
}