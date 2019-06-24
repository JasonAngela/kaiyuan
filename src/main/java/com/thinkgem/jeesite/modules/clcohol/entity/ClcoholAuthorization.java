/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.ActEntity;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 酒精授权签字人Entity
 * @author fuyun
 * @version 2018-01-23
 */
public class ClcoholAuthorization extends ActEntity<ClcoholAuthorization> {
	
	private static final long serialVersionUID = 1L;
	private ClcoholRegister register;		// register_id
	private String basicFacts;		// 基本案情
	private String sampleStatus;		// 送检样情况
	private String workInstruction;		// 作业指导
	private String testingStandards;		// 检测标准
	private String instruments;		// 仪器
	private String testResult;		// 检测结果
	private String other;		// other
	private String other1;		// other1
	private String other2;		// other2
	private String other3;		// other3
	private String authorizationPeople;		// 授权签字人
	
	public ClcoholAuthorization() {
		super();
	}

	public ClcoholAuthorization(String id){
		super(id);
	}

	
	public ClcoholRegister getRegister() {
		return register;
	}

	public void setRegister(ClcoholRegister register) {
		this.register = register;
	}

	public String getBasicFacts() {
		return basicFacts;
	}

	public void setBasicFacts(String basicFacts) {
		this.basicFacts = basicFacts;
	}
	
	public String getSampleStatus() {
		return sampleStatus;
	}

	public void setSampleStatus(String sampleStatus) {
		this.sampleStatus = sampleStatus;
	}
	
	public String getWorkInstruction() {
		return workInstruction;
	}

	public void setWorkInstruction(String workInstruction) {
		this.workInstruction = workInstruction;
	}
	
	public String getTestingStandards() {
		return testingStandards;
	}

	public void setTestingStandards(String testingStandards) {
		this.testingStandards = testingStandards;
	}
	
	public String getInstruments() {
		return instruments;
	}

	public void setInstruments(String instruments) {
		this.instruments = instruments;
	}
	
	public String getTestResult() {
		return testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
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
	
	@Length(min=0, max=255, message="授权签字人长度必须介于 0 和 255 之间")
	public String getAuthorizationPeople() {
		return authorizationPeople;
	}

	public void setAuthorizationPeople(String authorizationPeople) {
		this.authorizationPeople = authorizationPeople;
	}
	
}