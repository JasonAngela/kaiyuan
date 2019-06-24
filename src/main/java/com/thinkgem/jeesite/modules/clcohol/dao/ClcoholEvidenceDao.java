/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholEvidence;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholSamples;

/**
 * 酒精物证DAO接口
 * @author fuyun
 * @version 2018-01-23
 */
@MyBatisDao
public interface ClcoholEvidenceDao extends CrudDao<ClcoholEvidence> {
	ClcoholEvidence getRegister (String registerId);
}