/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entrust.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustModifyrecord;

/**
 * 报告修改记录DAO接口
 * @author fuyun
 * @version 2017-12-14
 */
@MyBatisDao
public interface EntrustModifyrecordDao extends CrudDao<EntrustModifyrecord> {
	List<EntrustModifyrecord>getRegister(String registerId);
}