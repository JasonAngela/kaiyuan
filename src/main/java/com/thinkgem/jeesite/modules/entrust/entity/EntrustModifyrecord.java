/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entrust.entity;

import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 报告修改记录Entity
 * @author fuyun
 * @version 2017-12-14
 */
public class EntrustModifyrecord extends DataEntity<EntrustModifyrecord> {
	
	private static final long serialVersionUID = 1L;
	private EntrustRegister register;		// register_id
	private String modefy;		// modefy
	private String userby;		// 使用者
	
	
	
	
	public String getUserby() {
		return userby;
	}

	public void setUserby(String userby) {
		this.userby = userby;
	}

	public EntrustModifyrecord() {
		super();
	}

	public EntrustModifyrecord(String id){
		super(id);
	}

	
	public EntrustRegister getRegister() {
		return register;
	}

	public void setRegister(EntrustRegister register) {
		this.register = register;
	}

	public String getModefy() {
		return modefy;
	}

	public void setModefy(String modefy) {
		this.modefy = modefy;
	}
	
}