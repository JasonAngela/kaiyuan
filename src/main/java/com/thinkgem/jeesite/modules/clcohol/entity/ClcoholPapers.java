/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicRegister;

/**
 * 酒精底稿Entity
 * @author fuyun
 * @version 2018-05-21
 */
public class ClcoholPapers extends DataEntity<ClcoholPapers> {
	
	private static final long serialVersionUID = 1L;
	private ClcoholRegister register;	// register
	private String entrust;		// 委托方
	private String mattersentrusted;		// 委托事项
	private String acceptdate;		// 受理日期
	private String personbeing;		// 被检测人姓名
	private String basicfacts;		// 检案摘要
	private String samplestatus;		// 送检样情况
	private String workinstruction;		// 作业指导
	private String testingstandards;		// 检测标准
	private String instruments;		// 仪器
	private String testpasses;		// 检测结果
	private String people;		// 鉴定人
	private String other;		// other
	private String other1;		// other1
	
	public ClcoholPapers() {
		super();
	}

	public ClcoholPapers(String id){
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
	
	public String getMattersentrusted() {
		return mattersentrusted;
	}

	public void setMattersentrusted(String mattersentrusted) {
		this.mattersentrusted = mattersentrusted;
	}
	
	@Length(min=0, max=255, message="受理日期长度必须介于 0 和 255 之间")
	public String getAcceptdate() {
		return acceptdate;
	}

	public void setAcceptdate(String acceptdate) {
		this.acceptdate = acceptdate;
	}
	
	@Length(min=0, max=255, message="被检测人姓名长度必须介于 0 和 255 之间")
	public String getPersonbeing() {
		return personbeing;
	}

	public void setPersonbeing(String personbeing) {
		this.personbeing = personbeing;
	}
	
	public String getBasicfacts() {
		return basicfacts;
	}

	public void setBasicfacts(String basicfacts) {
		this.basicfacts = basicfacts;
	}
	
	public String getSamplestatus() {
		return samplestatus;
	}

	public void setSamplestatus(String samplestatus) {
		this.samplestatus = samplestatus;
	}
	
	@Length(min=0, max=255, message="作业指导长度必须介于 0 和 255 之间")
	public String getWorkinstruction() {
		return workinstruction;
	}

	public void setWorkinstruction(String workinstruction) {
		this.workinstruction = workinstruction;
	}
	
	public String getTestingstandards() {
		return testingstandards;
	}

	public void setTestingstandards(String testingstandards) {
		this.testingstandards = testingstandards;
	}
	
	@Length(min=0, max=255, message="仪器长度必须介于 0 和 255 之间")
	public String getInstruments() {
		return instruments;
	}

	public void setInstruments(String instruments) {
		this.instruments = instruments;
	}
	
	public String getTestpasses() {
		return testpasses;
	}

	public void setTestpasses(String testpasses) {
		this.testpasses = testpasses;
	}
	
	@Length(min=0, max=255, message="鉴定人长度必须介于 0 和 255 之间")
	public String getPeople() {
		return people;
	}

	public void setPeople(String people) {
		this.people = people;
	}
	
	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
	
	public String getOther1() {
		return other1;
	}

	public void setOther1(String other1) {
		this.other1 = other1;
	}
	
}