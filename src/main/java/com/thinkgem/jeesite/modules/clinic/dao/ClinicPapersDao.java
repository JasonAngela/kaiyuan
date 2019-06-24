/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clinic.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicPapers;

/**
 * 法医底稿DAO接口
 * @author fuyun
 * @version 2017-12-20
 */
@MyBatisDao
public interface ClinicPapersDao extends CrudDao<ClinicPapers> {
	
}