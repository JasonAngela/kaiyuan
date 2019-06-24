/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dna.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 领取样品Entity
 * @author fuyun
 * @version 2018-06-22
 */
public class DnaReceiveIteam extends DataEntity<DnaReceiveIteam> {
	
	private static final long serialVersionUID = 1L;
	private DnaReceive recive;		// recive_id 父类
	private String specode;		// specode
	
	public DnaReceiveIteam() {
		super();
	}

	public DnaReceiveIteam(String id){
		super(id);
	}

	public DnaReceiveIteam(DnaReceive recive){
		this.recive = recive;
	}

	@NotNull(message="recive_id不能为空")
	public DnaReceive getRecive() {
		return recive;
	}

	public void setRecive(DnaReceive recive) {
		this.recive = recive;
	}
	
	@Length(min=0, max=255, message="specode长度必须介于 0 和 255 之间")
	public String getSpecode() {
		return specode;
	}

	public void setSpecode(String specode) {
		this.specode = specode;
	}
	
}