/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholFirst;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholRegister;

/**
 * 酒精初稿DAO接口
 * @author fuyun
 * @version 2018-01-23
 */
@MyBatisDao
public interface ClcoholFirstDao extends CrudDao<ClcoholFirst> {
	ClcoholFirst findRegister (String registerId);
}