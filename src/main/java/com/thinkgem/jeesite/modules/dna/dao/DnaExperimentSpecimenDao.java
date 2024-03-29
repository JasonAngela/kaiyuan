/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dna.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.dna.entity.DnaExperimentSpecimen;

/**
 * dna试验DAO接口
 * @author zhuguli
 * @version 2017-06-11
 */
@MyBatisDao
public interface DnaExperimentSpecimenDao extends CrudDao<DnaExperimentSpecimen> {
	List<DnaExperimentSpecimen>getNot();
}