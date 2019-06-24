/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entrust.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * dna快递Entity
 * @author fuyun
 * @version 2018-01-12
 */
public class EntrustCourier extends DataEntity<EntrustCourier> {
	
	private static final long serialVersionUID = 1L;
	private String entrustId;		// entrust_id
	private String orderno;		// orderno
	private String sender;		// sender
	private String instructions;		// instructions
	private String cost;		// cost
	private String other;		// other
	private String other1;		// other1
	
	public EntrustCourier() {
		super();
	}

	public EntrustCourier(String id){
		super(id);
	}

	@Length(min=1, max=64, message="entrust_id长度必须介于 1 和 64 之间")
	public String getEntrustId() {
		return entrustId;
	}

	public void setEntrustId(String entrustId) {
		this.entrustId = entrustId;
	}
	
	@Length(min=0, max=64, message="orderno长度必须介于 0 和 64 之间")
	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	
	@Length(min=0, max=255, message="sender长度必须介于 0 和 255 之间")
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	
	@Length(min=0, max=255, message="instructions长度必须介于 0 和 255 之间")
	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	
	@Length(min=0, max=255, message="cost长度必须介于 0 和 255 之间")
	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
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
	
}