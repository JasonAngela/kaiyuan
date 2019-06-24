/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clinic.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.ActEntity;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 法医第二鉴定人Entity
 * @author fuyun
 * @version 2017-12-06
 */
public class ClinicInspection extends ActEntity<ClinicInspection> {
	
	private static final long serialVersionUID = 1L;
	private ClinicRegister register;		// register_id
	private String file;		// 文件
	private String commentaries;		// 注释
	private String clinicentRusted;		// 委托事项  
	private String clinicIdentification;		// 鉴定材料
	private String clinicPerson;		// 被鉴定人
	private String clinicAttorney;		// 据委托书介绍
	private String clinicThisPaper;		// 资料摘要
	private String inspectionMethods;		// 检验方法
	private String appraisalStandard;		// 鉴定标准
	private String identifiedThrough;		// 鉴定经过
	private String cc;		// 主诉
	private String body;		// 查体
	private String reading;		// 阅片
	private String analysisShows;		// 分析说明
	private String expertOpinion;		// 鉴定意见
	private String secondSurveyor;		// 第二鉴定人
	private String remarks;
	
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public ClinicInspection() {
		super();
	}

	public ClinicInspection(String id){
		super(id);
	}

	public ClinicRegister getRegister() {
		return register;
	}

	public void setRegister(ClinicRegister register) {
		this.register = register;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
	public String getCommentaries() {
		return commentaries;
	}

	public void setCommentaries(String commentaries) {
		this.commentaries = commentaries;
	}
	
	public String getClinicentRusted() {
		return clinicentRusted;
	}

	public void setClinicentRusted(String clinicentRusted) {
		this.clinicentRusted = clinicentRusted;
	}
	
	public String getClinicIdentification() {
		return clinicIdentification;
	}

	public void setClinicIdentification(String clinicIdentification) {
		this.clinicIdentification = clinicIdentification;
	}
	
	public String getClinicPerson() {
		return clinicPerson;
	}

	public void setClinicPerson(String clinicPerson) {
		this.clinicPerson = clinicPerson;
	}
	
	public String getClinicAttorney() {
		return clinicAttorney;
	}

	public void setClinicAttorney(String clinicAttorney) {
		this.clinicAttorney = clinicAttorney;
	}
	
	public String getClinicThisPaper() {
		return clinicThisPaper;
	}

	public void setClinicThisPaper(String clinicThisPaper) {
		this.clinicThisPaper = clinicThisPaper;
	}
	
	public String getInspectionMethods() {
		return inspectionMethods;
	}

	public void setInspectionMethods(String inspectionMethods) {
		this.inspectionMethods = inspectionMethods;
	}
	
	public String getAppraisalStandard() {
		return appraisalStandard;
	}

	public void setAppraisalStandard(String appraisalStandard) {
		this.appraisalStandard = appraisalStandard;
	}
	
	public String getIdentifiedThrough() {
		return identifiedThrough;
	}

	public void setIdentifiedThrough(String identifiedThrough) {
		this.identifiedThrough = identifiedThrough;
	}
	
	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}
	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	public String getReading() {
		return reading;
	}

	public void setReading(String reading) {
		this.reading = reading;
	}
	
	public String getAnalysisShows() {
		return analysisShows;
	}

	public void setAnalysisShows(String analysisShows) {
		this.analysisShows = analysisShows;
	}
	
	public String getExpertOpinion() {
		return expertOpinion;
	}

	public void setExpertOpinion(String expertOpinion) {
		this.expertOpinion = expertOpinion;
	}
	
	public String getSecondSurveyor() {
		return secondSurveyor;
	}

	public void setSecondSurveyor(String secondSurveyor) {
		this.secondSurveyor = secondSurveyor;
	}
	
}