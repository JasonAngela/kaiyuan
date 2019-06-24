/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clinic.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.ActEntity;

/**
 * 法医第一鉴定人Entity
 * @author fuyun
 * @version 2017-12-04
 */
public class ClinicFirstdraft extends ActEntity<ClinicFirstdraft> {
	
	private static final long serialVersionUID = 1L;
	private ClinicRegister register;		// rgister_id
	private String opinion;		// 意见
	private String firstOpinion;		// first_opinion
	private String clinicParty;		// 委托方
	private String clinicentRusted;		// 委托事项
	private String clinicEntrustDate;		// 委托时间
	private String clinicIdentification;		// 鉴定材料
	private String clinicPerson;		// 被鉴定人
	private String clinicAttorney;		// 据委托书介绍
	private String clinicThisPaper;		// 资料摘要
	private String inspectionMethods;		// 检验方法
	private String appraisalStandard;		// 鉴定标准
	private String identifiedThrough;		// 鉴定经过
	private String appraisalDate;		// 鉴定日期
	private String presencePersonnel;		// 在场人员
	private String cc;		// 主诉
	private String body;		// 查体
	private String reading;		// 阅片
	private String analysisShows;		// 分析说明
	private String expertOpinion;		// 鉴定意见
	private String firstSurveyor;		// 第一鉴定人
	private String surveyorDate;		// 鉴定时间
	private String beginAppraisalDate;		// 开始 鉴定日期
	private String endAppraisalDate;		// 结束 鉴定日期
	
	public ClinicFirstdraft() {
		super();
	}

	public ClinicFirstdraft(String id){
		super(id);
	}

	
	public ClinicRegister getRegister() {
		return register;
	}

	public void setRegister(ClinicRegister register) {
		this.register = register;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	@Length(min=0, max=255, message="first_opinion长度必须介于 0 和 255 之间")
	public String getFirstOpinion() {
		return firstOpinion;
	}

	public void setFirstOpinion(String firstOpinion) {
		this.firstOpinion = firstOpinion;
	}
	
	@Length(min=0, max=255, message="委托方长度必须介于 0 和 255 之间")
	public String getClinicParty() {
		return clinicParty;
	}

	public void setClinicParty(String clinicParty) {
		this.clinicParty = clinicParty;
	}
	
	@Length(min=0, max=255, message="委托事项长度必须介于 0 和 255 之间")
	public String getClinicentRusted() {
		return clinicentRusted;
	}

	public void setClinicentRusted(String clinicentRusted) {
		this.clinicentRusted = clinicentRusted;
	}
	
	@Length(min=0, max=255, message="委托时间长度必须介于 0 和 255 之间")
	public String getClinicEntrustDate() {
		return clinicEntrustDate;
	}

	public void setClinicEntrustDate(String clinicEntrustDate) {
		this.clinicEntrustDate = clinicEntrustDate;
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
	
	@Length(min=0, max=255, message="鉴定日期长度必须介于 0 和 255 之间")
	public String getAppraisalDate() {
		return appraisalDate;
	}

	public void setAppraisalDate(String appraisalDate) {
		this.appraisalDate = appraisalDate;
	}
	
	@Length(min=0, max=255, message="在场人员长度必须介于 0 和 255 之间")
	public String getPresencePersonnel() {
		return presencePersonnel;
	}

	public void setPresencePersonnel(String presencePersonnel) {
		this.presencePersonnel = presencePersonnel;
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
	
	@Length(min=0, max=255, message="第一鉴定人长度必须介于 0 和 255 之间")
	public String getFirstSurveyor() {
		return firstSurveyor;
	}

	public void setFirstSurveyor(String firstSurveyor) {
		this.firstSurveyor = firstSurveyor;
	}
	
	@Length(min=0, max=255, message="鉴定时间长度必须介于 0 和 255 之间")
	public String getSurveyorDate() {
		return surveyorDate;
	}

	public void setSurveyorDate(String surveyorDate) {
		this.surveyorDate = surveyorDate;
	}
	
	public String getBeginAppraisalDate() {
		return beginAppraisalDate;
	}

	public void setBeginAppraisalDate(String beginAppraisalDate) {
		this.beginAppraisalDate = beginAppraisalDate;
	}
	
	public String getEndAppraisalDate() {
		return endAppraisalDate;
	}

	public void setEndAppraisalDate(String endAppraisalDate) {
		this.endAppraisalDate = endAppraisalDate;
	}
		
}