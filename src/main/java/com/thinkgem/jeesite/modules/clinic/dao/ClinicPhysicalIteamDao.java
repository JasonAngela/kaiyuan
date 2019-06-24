/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clinic.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicPhysicalIteam;

/**
 * 材料登记DAO接口
 * @author fuyun
 * @version 2017-11-28
 */
@MyBatisDao
public interface ClinicPhysicalIteamDao extends CrudDao<ClinicPhysicalIteam> {
	List<ClinicPhysicalIteam> findone (String id);
}