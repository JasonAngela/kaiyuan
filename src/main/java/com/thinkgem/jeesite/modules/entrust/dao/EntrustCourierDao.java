/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entrust.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustCourier;

/**
 * dna快递DAO接口
 * @author fuyun
 * @version 2018-01-12
 */
@MyBatisDao
public interface EntrustCourierDao extends CrudDao<EntrustCourier> {
	EntrustCourier  findEntrust (String  entrustId);
}