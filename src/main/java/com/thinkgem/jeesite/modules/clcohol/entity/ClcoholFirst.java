/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.ActEntity;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 酒精初稿Entity
 * @author fuyun
 * @version 2018-01-23
 */
public class ClcoholFirst extends ActEntity<ClcoholFirst> {
	
	private static final long serialVersionUID = 1L;
	private ClcoholRegister register;		// register_id
	private String entrust;		// 委托方
	private String mattersEntrusted;		// 委托事项
	private String acceptDate;		// 受理日期
	private String testingMaterials;		// 检测材料
	private String personBeing;		// 被检测人姓名
	private String basicFacts;		// 基本案情 检案摘要
	private String sampleStatus;		// 送检样情况
	private String workInstruction;		// 作业指导
	private String testingStandards;		// 检测标准
	private String instruments;		// 仪器
	private String testPasses;		// 检测经过
	private String testResults;		// 检测结果
	private String firstPeple;		// first_peple
	private String other;		// other
	private String other1;		// 第一鉴定人
	private String other2;		// other2
	
	public ClcoholFirst() {
		super();
	}

	public ClcoholFirst(String id){
		super(id);
	}

	
	public ClcoholRegister getRegister() {
		return register;
	}

	public void setRegister(ClcoholRegister register) {
		this.register = register;
	}

	@Length(min=0, max=255, message="委托方长度必须介于 0 和 255 之间")
	public String getEntrust() {
		return entrust;
	}

	public void setEntrust(String entrust) {
		this.entrust = entrust;
	}
	
	public String getMattersEntrusted() {
		return mattersEntrusted;
	}

	public void setMattersEntrusted(String mattersEntrusted) {
		this.mattersEntrusted = mattersEntrusted;
	}
	
	@Length(min=0, max=255, message="受理日期长度必须介于 0 和 255 之间")
	public String getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(String acceptDate) {
		this.acceptDate = acceptDate;
	}
	
	public String getTestingMaterials() {
		return testingMaterials;
	}

	public void setTestingMaterials(String testingMaterials) {
		this.testingMaterials = testingMaterials;
	}
	
	@Length(min=0, max=255, message="被检测人姓名长度必须介于 0 和 255 之间")
	public String getPersonBeing() {
		return personBeing;
	}

	public void setPersonBeing(String personBeing) {
		this.personBeing = personBeing;
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
	
	public String getTestPasses() {
		return testPasses;
	}

	public void setTestPasses(String testPasses) {
		this.testPasses = testPasses;
	}
	
	public String getTestResults() {
		return testResults;
	}

	public void setTestResults(String testResults) {
		this.testResults = testResults;
	}
	
	@Length(min=0, max=255, message="first_peple长度必须介于 0 和 255 之间")
	public String getFirstPeple() {
		return firstPeple;
	}

	public void setFirstPeple(String firstPeple) {
		this.firstPeple = firstPeple;
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
	
}