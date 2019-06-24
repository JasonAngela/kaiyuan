/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clinic.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicPhysical;

/**
 * 材料登记DAO接口
 * @author fuyun
 * @version 2017-11-28
 */
@MyBatisDao
public interface ClinicPhysicalDao extends CrudDao<ClinicPhysical> {
	ClinicPhysical findOne(String registerId); 
}