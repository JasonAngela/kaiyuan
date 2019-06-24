/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clinic.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 法医委托受理Entity
 * @author fuyun
 * @version 2018-05-03
 */
public class ClinicRegisterphy extends DataEntity<ClinicRegisterphy> {
	
	private static final long serialVersionUID = 1L;
	private ClinicRegister register;		// register_id 父类
	private String type;		// 类型
	private String name;		// 名称
	private String quantity;		// 数量
	private String uploud;		// uploud
	private String remark;		// remark
	
	public ClinicRegisterphy() {
		super();
	}

	public ClinicRegisterphy(String id){
		super(id);
	}

	public ClinicRegisterphy(ClinicRegister register){
		this.register = register;
	}

	@NotNull(message="register_id不能为空")
	public ClinicRegister getRegister() {
		return register;
	}

	public void setRegister(ClinicRegister register) {
		this.register = register;
	}
	
	@Length(min=0, max=255, message="类型长度必须介于 0 和 255 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="数量长度必须介于 0 和 255 之间")
	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	public String getUploud() {
		return uploud;
	}

	public void setUploud(String uploud) {
		this.uploud = uploud;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}