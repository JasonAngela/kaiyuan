/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholPapers;

/**
 * 酒精底稿DAO接口
 * @author fuyun
 * @version 2018-05-18
 */
@MyBatisDao
public interface ClcoholPapersDao extends CrudDao<ClcoholPapers> {
	
}