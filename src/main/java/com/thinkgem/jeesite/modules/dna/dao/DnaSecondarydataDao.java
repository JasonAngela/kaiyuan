/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dna.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.dna.entity.DnaSecondarydata;

/**
 * dna临时导入二次数据表DAO接口
 * @author fuyun
 * @version 2018-06-28
 */
@MyBatisDao
public interface DnaSecondarydataDao extends CrudDao<DnaSecondarydata> {
List<DnaSecondarydata>	findNo();
void deleteAll();
List<DnaSecondarydata> findAll();
}