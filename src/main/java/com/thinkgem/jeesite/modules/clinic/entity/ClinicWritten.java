/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clinic.entity;

import com.thinkgem.jeesite.common.utils.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.ActEntity;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 法医成文修改Entity
 * @author fuyun
 * @version 2017-12-21
 */
public class ClinicWritten extends ActEntity<ClinicWritten> {
	
	private static final long serialVersionUID = 1L;
	private ClinicRegister register;		// register_id
	private String file;		// 文件上传
	private String commentaries;		// commentaries
	private String opinion;		// 意见
	private String clinicthispaper;		// 资料摘要
	private String inspectionmethods;		// 检验方法
	private String appraisalstandard;		// 鉴定标准
	private String identifiedthrough;		// 鉴定经过
	private String cc;		// 主诉
	private String body;		// 查体
	private String reading;		// 阅片
	private String clinicattorney;		// 据委托书介绍
	private String analysisshows;		// 分析说明
	private String expertopinion;		// 鉴定意见
	private String authorisesurveyor;		// 鉴定日期
	private String other;		// 其它
	private String delegate;		// 委托方
	private String toaccept;		// 受理事项
	private String acceptdate;		// 受理时间
	private String identification;		// 鉴定材料
	private String bysurveyor;		// 被鉴定人
	private String firstuser;		// 第一鉴定人
	private String secouduser;		// 第二鉴定人
	private String authoriseuser;		// 授权签字人
	private String identifylocations;		// 鉴定地点
	private String personnel;		// 在场人员
	
	public ClinicWritten() {
		super();
	}

	public ClinicWritten(String id){
		super(id);
	}

	
	public ClinicRegister getRegister() {
		return register;
	}

	public void setRegister(ClinicRegister register) {
		this.register = register;
	}

	@Length(min=0, max=255, message="文件上传长度必须介于 0 和 255 之间")
	public String getFile() {
		return StringUtils.isNotEmpty(file)?file:"";
	}

	public void setFile(String file) {
		this.file = file;
	}
	
	@Length(min=0, max=255, message="commentaries长度必须介于 0 和 255 之间")
	public String getCommentaries() {
		return StringUtils.isNotEmpty(commentaries)?commentaries:"";
	}

	public void setCommentaries(String commentaries) {
		this.commentaries = commentaries;
	}
	
	public String getOpinion() {
		return StringUtils.isNotEmpty(opinion)?opinion:"";
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	public String getClinicthispaper() {
		return StringUtils.isNotEmpty(clinicthispaper)?clinicthispaper:"";
	}

	public void setClinicthispaper(String clinicthispaper) {
		this.clinicthispaper = clinicthispaper;
	}
	
	public String getInspectionmethods() {
		return StringUtils.isNotEmpty(inspectionmethods)?inspectionmethods:"";
	}

	public void setInspectionmethods(String inspectionmethods) {
		this.inspectionmethods = inspectionmethods;
	}
	
	public String getAppraisalstandard() {
		return StringUtils.isNotEmpty(appraisalstandard)?appraisalstandard:"";
	}

	public void setAppraisalstandard(String appraisalstandard) {
		this.appraisalstandard = appraisalstandard;
	}
	
	public String getIdentifiedthrough() {
		return StringUtils.isNotEmpty(identifiedthrough)?identifiedthrough:"";
	}

	public void setIdentifiedthrough(String identifiedthrough) {
		this.identifiedthrough = identifiedthrough;
	}
	
	public String getCc() {
		return StringUtils.isNotEmpty(cc)?cc:"";
	}

	public void setCc(String cc) {
		this.cc = cc;
	}
	
	public String getBody() {
		return StringUtils.isNotEmpty(body)?body:"";
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	public String getReading() {
		return StringUtils.isNotEmpty(reading)?reading:"";
	}

	public void setReading(String reading) {
		this.reading = reading;
	}
	
	public String getClinicattorney() {
		return StringUtils.isNotEmpty(clinicattorney)?clinicattorney:"";
	}

	public void setClinicattorney(String clinicattorney) {
		this.clinicattorney = clinicattorney;
	}
	
	public String getAnalysisshows() {
		return StringUtils.isNotEmpty(analysisshows)?analysisshows:"";
	}

	public void setAnalysisshows(String analysisshows) {
		this.analysisshows = analysisshows;
	}
	
	public String getExpertopinion() {
		return StringUtils.isNotEmpty(expertopinion)?expertopinion:"";
	}

	public void setExpertopinion(String expertopinion) {
		this.expertopinion = expertopinion;
	}
	
	public String getAuthorisesurveyor() {
		return StringUtils.isNotEmpty(authorisesurveyor)?authorisesurveyor:"";
	}

	public void setAuthorisesurveyor(String authorisesurveyor) {
		this.authorisesurveyor = authorisesurveyor;
	}
	
	public String getOther() {
		return StringUtils.isNotEmpty(other)?other:"";
	}

	public void setOther(String other) {
		this.other = other;
	}
	
	public String getDelegate() {
		return StringUtils.isNotEmpty(delegate)?delegate:"";
	}

	public void setDelegate(String delegate) {
		this.delegate = delegate;
	}
	
	public String getToaccept() {
		return StringUtils.isNotEmpty(toaccept)?toaccept:"";
	}

	public void setToaccept(String toaccept) {
		this.toaccept = toaccept;
	}
	
	public String getAcceptdate() {
		return StringUtils.isNotEmpty(acceptdate)?acceptdate:"";
	}

	public void setAcceptdate(String acceptdate) {
		this.acceptdate = acceptdate;
	}
	
	public String getIdentification() {
		return StringUtils.isNotEmpty(identification)?identification:"";
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}
	
	public String getBysurveyor() {
		return StringUtils.isNotEmpty(bysurveyor)?bysurveyor:"";
	}

	public void setBysurveyor(String bysurveyor) {
		this.bysurveyor = bysurveyor;
	}
	
	public String getFirstuser() {
		return StringUtils.isNotEmpty(firstuser)?firstuser:"";
	}

	public void setFirstuser(String firstuser) {
		this.firstuser = firstuser;
	}
	
	public String getSecouduser() {
		return StringUtils.isNotEmpty(secouduser)?secouduser:"";
	}

	public void setSecouduser(String secouduser) {
		this.secouduser = secouduser;
	}
	
	public String getAuthoriseuser() {
		return StringUtils.isNotEmpty(authoriseuser)?authoriseuser:"";
	}

	public void setAuthoriseuser(String authoriseuser) {
		this.authoriseuser = authoriseuser;
	}
	
	public String getIdentifylocations() {
		return StringUtils.isNotEmpty(identifylocations)?identifylocations:"";
	}

	public void setIdentifylocations(String identifylocations) {
		this.identifylocations = identifylocations;
	}
	
	public String getPersonnel() {
		return StringUtils.isNotEmpty(personnel)?personnel:"";
	}

	public void setPersonnel(String personnel) {
		this.personnel = personnel;
	}
	
}