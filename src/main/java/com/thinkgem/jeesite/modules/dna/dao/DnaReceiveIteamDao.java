/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dna.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.dna.entity.DnaReceiveIteam;

/**
 * 领取样品DAO接口
 * @author fuyun
 * @version 2018-06-22
 */
@MyBatisDao
public interface DnaReceiveIteamDao extends CrudDao<DnaReceiveIteam> {
	List<DnaReceiveIteam>  getNot();
	DnaReceiveIteam	getCode(String code);
}