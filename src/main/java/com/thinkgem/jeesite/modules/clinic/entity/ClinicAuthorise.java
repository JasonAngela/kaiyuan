/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clinic.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.ActEntity;

/**
 * 法医授权签字人Entity
 * @author fuyun
 * @version 2017-12-18
 */
public class ClinicAuthorise extends ActEntity<ClinicAuthorise> {
	
	private static final long serialVersionUID = 1L;
	private ClinicRegister register;		// register_id
	private String opinion;		// 意见
	private String clinicThisPaper;		// 资料摘要
	private String inspectionMethods;		// 检验方法
	private String appraisalStandard;		// 鉴定标准
	private String identifiedThrough;		// 鉴定经过
	private String cc;		// 主诉
	private String body;		// 查体
	private String reading;		// 阅片
	private String analysisShows;		// 分析说明
	private String expertOpinion;		// 鉴定意见
	private String authoriseSurveyor;		// 授权签字人
	private String other;		// 其它
	
	public ClinicAuthorise() {
		super();
	}

	public ClinicAuthorise(String id){
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
	
	public String getAuthoriseSurveyor() {
		return authoriseSurveyor;
	}

	public void setAuthoriseSurveyor(String authoriseSurveyor) {
		this.authoriseSurveyor = authoriseSurveyor;
	}
	
	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
	
}