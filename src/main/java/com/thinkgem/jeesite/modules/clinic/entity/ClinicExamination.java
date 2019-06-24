/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clinic.entity;

import com.thinkgem.jeesite.common.utils.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.ActEntity;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 人员验伤Entity
 * @author fuyun
 * @version 2017-12-04
 */
public class ClinicExamination extends ActEntity<ClinicExamination> {
	
	private static final long serialVersionUID = 1L;
	private ClinicRegister register;		// register_id
	private String name;		// 名字
	private String sex;		// 性别
	private String checkTime;		// 检查时间
	private String checkName;		// 检查人
	private String clinicSurveyor;		// 被鉴定人
	private String checkAddress;		// 检查地点
	private String cc;		// 主诉
	private String situation;		// 一般情况
	private String headFace;		// 头面部
	private String trunk;		// 躯干
	private String limbs;		// 四肢
	private String other;		// 其他
	private String reading;		// 阅片
	private String douCheck;		// 复核签发
	private String uploud;//上传
	
	public ClinicExamination() {
		super();
	}

	public ClinicExamination(String id){
		super(id);
	}
	
	public String getUploud() {
		return StringUtils.isNotEmpty(uploud)?uploud:"";
	}

	public void setUploud(String uploud) {
		this.uploud = uploud;
	}

	public ClinicRegister getRegister() {
		return register;
	}

	public void setRegister(ClinicRegister register) {
		this.register = register;
	}

	@Length(min=0, max=255, message="名字长度必须介于 0 和 255 之间")
	public String getName() {
		return StringUtils.isNotEmpty(name)?name:"";
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="性别长度必须介于 0 和 255 之间")
	public String getSex() {
		return StringUtils.isNotEmpty(sex)?sex:"";
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=255, message="检查时间长度必须介于 0 和 255 之间")
	public String getCheckTime() {
		return StringUtils.isNotEmpty(checkTime)?checkTime:"";
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	
	@Length(min=0, max=255, message="检查人长度必须介于 0 和 255 之间")
	public String getCheckName() {
		return StringUtils.isNotEmpty(checkName)?checkName:"";
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
	
	@Length(min=0, max=255, message="被鉴定人长度必须介于 0 和 255 之间")
	public String getClinicSurveyor() {
		return StringUtils.isNotEmpty(clinicSurveyor)?clinicSurveyor:"";
	}

	public void setClinicSurveyor(String clinicSurveyor) {
		this.clinicSurveyor = clinicSurveyor;
	}
	
	@Length(min=0, max=255, message="检查地点长度必须介于 0 和 255 之间")
	public String getCheckAddress() {
		return StringUtils.isNotEmpty(checkAddress)?checkAddress:"";
	}

	public void setCheckAddress(String checkAddress) {
		this.checkAddress = checkAddress;
	}
	
	public String getCc() {
		return StringUtils.isNotEmpty(cc)?cc:"";
	}

	public void setCc(String cc) {
		this.cc = cc;
	}
	
	public String getSituation() {
		return StringUtils.isNotEmpty(situation)?situation:"";
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}
	
	public String getHeadFace() {
		return StringUtils.isNotEmpty(headFace)?headFace:"";
	}

	public void setHeadFace(String headFace) {
		this.headFace = headFace;
	}
	
	public String getTrunk() {
		return StringUtils.isNotEmpty(trunk)?trunk:"";
	}

	public void setTrunk(String trunk) {
		this.trunk = trunk;
	}
	
	public String getLimbs() {
		return StringUtils.isNotEmpty(limbs)?limbs:"";
	}

	public void setLimbs(String limbs) {
		this.limbs = limbs;
	}
	
	public String getOther() {
		return StringUtils.isNotEmpty(other)?other:"";
	}

	public void setOther(String other) {
		this.other = other;
	}
	
	public String getReading() {
		return StringUtils.isNotEmpty(reading)?reading:"";
	}

	public void setReading(String reading) {
		this.reading = reading;
	}
	
	public String getDouCheck() {
		return StringUtils.isNotEmpty(douCheck)?douCheck:"";
	}

	public void setDouCheck(String douCheck) {
		this.douCheck = douCheck;
	}
	
}