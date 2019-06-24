/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.material.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.material.entity.SpecimenMaterialRegister;

/**
 * 物证登记DAO接口
 * @author zhuguli
 * @version 2017-05-10
 */
@MyBatisDao
public interface SpecimenMaterialRegisterDao extends CrudDao<SpecimenMaterialRegister> {

	SpecimenMaterialRegister getByEntrustRegisterId(@Param("registerId")String registerId);
	SpecimenMaterialRegister findid(String id);	
}