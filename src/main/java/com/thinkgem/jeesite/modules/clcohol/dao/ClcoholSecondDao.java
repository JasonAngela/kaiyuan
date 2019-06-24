/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholSecond;

/**
 * 酒精第二鉴定人DAO接口
 * @author fuyun
 * @version 2018-01-23
 */
@MyBatisDao
public interface ClcoholSecondDao extends CrudDao<ClcoholSecond> {
	ClcoholSecond	findRegister(String registerId);
}