/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 酒精样品归还Entity
 * @author fuyun
 * @version 2018-01-23
 */
public class ClcoholReturnIteam extends DataEntity<ClcoholReturnIteam> {
	
	private static final long serialVersionUID = 1L;
	private ClcoholReturn register;		// register_id 父类
	private String code;		// code
	private String idnumber;		// 证件号
	private String contactphone;		// 联系电话
	private String other;		// other
	private String remaining;		// 剩余数量
	private String totalNumber;		// 总数
	private String useNumber;		// 使用数量
	
	public ClcoholReturnIteam() {
		super();
	}

	public ClcoholReturnIteam(String id){
		super(id);
	}

	public ClcoholReturnIteam(ClcoholReturn register){
		this.register = register;
	}

	@NotNull(message="register_id不能为空")
	public ClcoholReturn getRegister() {
		return register;
	}

	public void setRegister(ClcoholReturn register) {
		this.register = register;
	}
	
	@Length(min=0, max=255, message="code长度必须介于 0 和 255 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=255, message="证件号长度必须介于 0 和 255 之间")
	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}
	
	@Length(min=0, max=255, message="联系电话长度必须介于 0 和 255 之间")
	public String getContactphone() {
		return contactphone;
	}

	public void setContactphone(String contactphone) {
		this.contactphone = contactphone;
	}
	
	@Length(min=0, max=255, message="other长度必须介于 0 和 255 之间")
	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
	
	@Length(min=0, max=255, message="剩余数量长度必须介于 0 和 255 之间")
	public String getRemaining() {
		return remaining;
	}

	public void setRemaining(String remaining) {
		this.remaining = remaining;
	}
	
	@Length(min=0, max=255, message="总数长度必须介于 0 和 255 之间")
	public String getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(String totalNumber) {
		this.totalNumber = totalNumber;
	}
	
	@Length(min=0, max=255, message="使用数量长度必须介于 0 和 255 之间")
	public String getUseNumber() {
		return useNumber;
	}

	public void setUseNumber(String useNumber) {
		this.useNumber = useNumber;
	}
	
}