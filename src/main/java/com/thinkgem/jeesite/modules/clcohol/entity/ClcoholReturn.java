/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.google.common.collect.Lists;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 酒精样品归还Entity
 * @author fuyun
 * @version 2018-01-23
 */
public class ClcoholReturn extends DataEntity<ClcoholReturn> {
	
	private static final long serialVersionUID = 1L;
	private ClcoholRegister register;		// register_id
	private String code;		// 物证编号
	private List<ClcoholReturnIteam> clcoholReturnIteamList = Lists.newArrayList();		// 子表列表
	
	public ClcoholReturn() {
		super();
	}

	public ClcoholReturn(String id){
		super(id);
	}
	
	public ClcoholRegister getRegister() {
		return register;
	}

	public void setRegister(ClcoholRegister register) {
		this.register = register;
	}
	
	@Length(min=0, max=255, message="物证编号长度必须介于 0 和 255 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public List<ClcoholReturnIteam> getClcoholReturnIteamList() {
		return clcoholReturnIteamList;
	}

	public void setClcoholReturnIteamList(List<ClcoholReturnIteam> clcoholReturnIteamList) {
		this.clcoholReturnIteamList = clcoholReturnIteamList;
	}
}