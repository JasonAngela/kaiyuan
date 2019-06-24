/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.ActEntity;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 酒精成文修改Entity
 * @author fuyun
 * @version 2018-01-23
 */
public class ClcoholWritten extends ActEntity<ClcoholWritten> {
	
	private static final long serialVersionUID = 1L;
	private ClcoholRegister register;		// register_id
	private String basicFacts;		// 基本案情
	private String sampleStatus;		// 送检样情况
	private String workInstruction;		// 作业指导
	private String testingMaterials;		// 检测材料
	private String testResult;		// 检测结果
	private String other;		// other
	private String other1;		// other1
	private String other3;		// other3
	private String writtenPeople;		// written_people
	
	public ClcoholWritten() {
		super();
	}

	public ClcoholWritten(String id){
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
	
	public String getTestingMaterials() {
		return testingMaterials;
	}

	public void setTestingMaterials(String testingMaterials) {
		this.testingMaterials = testingMaterials;
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
	
	@Length(min=0, max=255, message="other3长度必须介于 0 和 255 之间")
	public String getOther3() {
		return other3;
	}

	public void setOther3(String other3) {
		this.other3 = other3;
	}
	
	@Length(min=0, max=255, message="written_people长度必须介于 0 和 255 之间")
	public String getWrittenPeople() {
		return writtenPeople;
	}

	public void setWrittenPeople(String writtenPeople) {
		this.writtenPeople = writtenPeople;
	}
	
}