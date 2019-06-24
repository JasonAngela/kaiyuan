/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dna.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.dna.entity.DnaPiResult;
import com.thinkgem.jeesite.modules.dna.entity.DnaPiResultItem;

/**
 * 亲权指数DAO接口
 * @author zhuguli
 * @version 2017-07-04
 */
@MyBatisDao
public interface DnaPiResultDao extends CrudDao<DnaPiResult> {
	 void  delete();
	 List<DnaPiResult> findAll();
	 List<DnaPiResult> getRegister(String registerId);
	 void delRegister(String id);
	 DnaPiResult  getParentCode(String piResultId);
	 DnaPiResult	getParentCodeAndChirdCode(@Param("childCode")String childCode,@Param("parentCode")String parentCode);
}