/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 酒精领取样品Entity
 * @author fuyun
 * @version 2018-01-29
 */
public class ClcoholSamplesIteam extends DataEntity<ClcoholSamplesIteam> {
	
	private static final long serialVersionUID = 1L;
	private ClcoholSamples samples;		// samples_id 父类
	private String name;		// 名称
	private String number;		// 数量
	private String other;		// other
	private String other1;		// other1
	private String idnumber;		// 证件号
	private String other2;		// other2
	private String other3;		// other3
	private String other4;		// other4
	private String receiveNumber;		// 领取数量
	
	public ClcoholSamplesIteam() {
		super();
	}

	public ClcoholSamplesIteam(String id){
		super(id);
	}

	public ClcoholSamplesIteam(ClcoholSamples samples){
		this.samples = samples;
	}

	@NotNull(message="samples_id不能为空")
	public ClcoholSamples getSamples() {
		return samples;
	}

	public void setSamples(ClcoholSamples samples) {
		this.samples = samples;
	}
	
	@Length(min=0, max=255, message="名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="数量长度必须介于 0 和 255 之间")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@Length(min=0, max=255, message="other长度必须介于 0 和 255 之间")
	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
	
	@Length(min=0, max=255, message="other1长度必须介于 0 和 255 之间")
	public String getOther1() {
		return other1;
	}

	public void setOther1(String other1) {
		this.other1 = other1;
	}
	
	@Length(min=0, max=255, message="证件号长度必须介于 0 和 255 之间")
	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}
	
	@Length(min=0, max=255, message="other2长度必须介于 0 和 255 之间")
	public String getOther2() {
		return other2;
	}

	public void setOther2(String other2) {
		this.other2 = other2;
	}
	
	@Length(min=0, max=255, message="other3长度必须介于 0 和 255 之间")
	public String getOther3() {
		return other3;
	}

	public void setOther3(String other3) {
		this.other3 = other3;
	}
	
	@Length(min=0, max=255, message="other4长度必须介于 0 和 255 之间")
	public String getOther4() {
		return other4;
	}

	public void setOther4(String other4) {
		this.other4 = other4;
	}
	
	@Length(min=0, max=255, message="领取数量长度必须介于 0 和 255 之间")
	public String getReceiveNumber() {
		return receiveNumber;
	}

	public void setReceiveNumber(String receiveNumber) {
		this.receiveNumber = receiveNumber;
	}
	
}