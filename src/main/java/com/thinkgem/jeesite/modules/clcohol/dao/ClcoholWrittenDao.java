/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholWritten;

/**
 * 酒精成文修改DAO接口
 * @author fuyun
 * @version 2018-01-23
 */
@MyBatisDao
public interface ClcoholWrittenDao extends CrudDao<ClcoholWritten> {
	ClcoholWritten	 getRegister(String registerId);
}