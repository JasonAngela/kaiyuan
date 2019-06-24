/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dna.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.ActEntity;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 选择板子流程Entity
 * @author fuyun
 * @version 2018-06-20
 */
public class Choosebord extends ActEntity<Choosebord> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// code
	
	public Choosebord() {
		super();
	}

	public Choosebord(String id){
		super(id);
	}

	@Length(min=0, max=255, message="code长度必须介于 0 和 255 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}