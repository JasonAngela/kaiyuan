/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dna.entity;

import java.util.List;
import com.google.common.collect.Lists;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 领取样品Entity
 * @author fuyun
 * @version 2018-06-22
 */
public class DnaReceive extends DataEntity<DnaReceive> {
	
	private static final long serialVersionUID = 1L;
	private List<DnaReceiveIteam> dnaReceiveIteamList = Lists.newArrayList();		// 子表列表
	
	public DnaReceive() {
		super();
	}

	public DnaReceive(String id){
		super(id);
	}

	public List<DnaReceiveIteam> getDnaReceiveIteamList() {
		return dnaReceiveIteamList;
	}

	public void setDnaReceiveIteamList(List<DnaReceiveIteam> dnaReceiveIteamList) {
		this.dnaReceiveIteamList = dnaReceiveIteamList;
	}
}