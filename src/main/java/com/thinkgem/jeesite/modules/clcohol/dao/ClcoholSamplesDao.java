/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholSamples;

/**
 * 酒精领取样品DAO接口
 * @author fuyun
 * @version 2018-01-29
 */
@MyBatisDao
public interface ClcoholSamplesDao extends CrudDao<ClcoholSamples> {
	ClcoholSamples getRegister (String registerId);
}