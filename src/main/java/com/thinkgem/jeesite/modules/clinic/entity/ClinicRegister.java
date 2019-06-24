/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clinic.entity;

import com.thinkgem.jeesite.common.utils.StringUtils;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.ActEntity;

/**
 * 法医委托受理Entity
 * @author fuyun
 * @version 2017-11-27
 */
public class ClinicRegister extends ActEntity<ClinicRegister> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 受理编码
	private String caseCode;		// 案件编号
	private String clientName;		// 委托人
	private String clientTel;		// 委托人电话
	private String clientReceiver;		// 未知项
	private String clientEmail;		// 鉴定类别
	private String clientFax;		// 本鉴定所意见
	private String clientZipcode;		// 委托日期
	private String clientArea;		// 鉴定材料
	private String clientAddress;		// 委托人地址 和收件人
	private String agentName;		// 送检人
	private String agentTel;		// 送检人电话
	private String serverName;		// 受理人
	private String serverOrgId;		// 受理机构
	private String sendMode;		// 报告传递方式
	private String specialty;		// 回避理由
	private String type;		// 类型
	private String material;		// 鉴定收费
	private String materialDispose;		// 从协议签订之日起
	private String timeLimitResult;		//是否申请回避
	private String timeLimitReport;		// 申请回避人
	private Double standardFee;		// 标准费用
	private Double specialFee;		// 特殊费用
	private Double totalFee;		// 合计费用
	private String appraisalItem;		// 其他
	private Boolean clientAvoid;		// 鉴定人回避
	private Boolean authorizeNotification;		// 授权客服人员通知
	private String status;		// status
	private String procInsId;		// 流程id
	private String surveyorName;		// 被检人姓名
	private String surveyorSex;		// 被检人性别
	private String surveyorBirthday;		// 被检人出生日期
	private String idCard;  //身份证
	private String mattersEntrusted;		// 委托事项
	private String clinicIdentification;		// 鉴定材料
	private String clinicTriage;		// 验伤通知书
	private String clinicMedical;		// 病史
	private String clinicSummary;		// 出院小结
	private String clinicXray;		// X片
	private String clinicCt;		// CT
	private String clinicMri;		// MRI
	private Date beginCreateDate;		// 开始 创建时间
	private Date endCreateDate;		// 结束 创建时间
	private List<ClinicRegisterphy> clinicRegisterphyList = Lists.newArrayList();		// 子表列表
	
	public List<ClinicRegisterphy> getClinicRegisterphyList() {
		return clinicRegisterphyList;
	}

	public void setClinicRegisterphyList(List<ClinicRegisterphy> clinicRegisterphyList) {
		this.clinicRegisterphyList = clinicRegisterphyList;
	}

	public ClinicRegister() {
		super();
	}

	
	public ClinicRegister(String id){
		super(id);
	}

	@Length(min=0, max=255, message="编码长度必须介于 0 和 255 之间")
	public String getCode() {
		return StringUtils.isNotEmpty(code)?code:"";
	}

	public void setCode(String code){
		this.code = code;
	}
	
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	@Length(min=0, max=255, message="案件编号长度必须介于 0 和 255 之间")
	public String getCaseCode() {
		return StringUtils.isNotEmpty(caseCode)?caseCode:"";
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	
	@Length(min=0, max=255, message="委托人长度必须介于 0 和 255 之间")
	public String getClientName() {
		return StringUtils.isNotEmpty(clientName)?clientName:"";
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	@Length(min=0, max=255, message="委托人电话长度必须介于 0 和 255 之间")
	public String getClientTel() {
		return StringUtils.isNotEmpty(clientTel)?clientTel:"";
	}

	public void setClientTel(String clientTel) {
		this.clientTel = clientTel;
	}
	
	@Length(min=0, max=255, message="委托收件人长度必须介于 0 和 255 之间")
	public String getClientReceiver() {
		return StringUtils.isNotEmpty(clientReceiver)?clientReceiver:"";
	}

	public void setClientReceiver(String clientReceiver) {
		this.clientReceiver = clientReceiver;
	}
	
	@Length(min=0, max=255, message="鉴定类别长度必须介于 0 和 255 之间")
	public String getClientEmail() {
		return StringUtils.isNotEmpty(clientEmail)?clientEmail:"";
	}

	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}
	
	@Length(min=0, max=255, message="委托人传真长度必须介于 0 和 255 之间")
	public String getClientFax() {
		return StringUtils.isNotEmpty(clientFax)?clientFax:"";
	}

	public void setClientFax(String clientFax) {
		this.clientFax = clientFax;
	}
	
	@Length(min=0, max=255, message="委托人邮编长度必须介于 0 和 255 之间")
	public String getClientZipcode() {
		return StringUtils.isNotEmpty(clientZipcode)?clientZipcode:"";
	}

	public void setClientZipcode(String clientZipcode) {
		this.clientZipcode = clientZipcode;
	}
	
	@Length(min=0, max=1000, message="委托人区域长度必须介于 0 和 1000 之间")
	public String getClientArea() {
		return StringUtils.isNotEmpty(clientArea)?clientArea:"";
	}

	public void setClientArea(String clientArea) {
		this.clientArea = clientArea;
	}
	
	@Length(min=0, max=1000, message="委托人地址长度必须介于 0 和 1000 之间")
	public String getClientAddress() {
		return StringUtils.isNotEmpty(clientAddress)?clientAddress:"";
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}
	
	@Length(min=0, max=255, message="送检人(机构)长度必须介于 0 和 255 之间")
	public String getAgentName() {
		return StringUtils.isNotEmpty(agentName)?agentName:"";
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	@Length(min=0, max=255, message="送检人电话长度必须介于 0 和 255 之间")
	public String getAgentTel() {
		return StringUtils.isNotEmpty(agentTel)?agentTel:"";
	}

	public void setAgentTel(String agentTel) {
		this.agentTel = agentTel;
	}
	
	@Length(min=0, max=255, message="受理人长度必须介于 0 和 255 之间")
	public String getServerName() {
		return StringUtils.isNotEmpty(serverName)?serverName:"";
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	
	@Length(min=0, max=255, message="受理机构长度必须介于 0 和 255 之间")
	public String getServerOrgId() {
		return StringUtils.isNotEmpty(serverOrgId)?serverOrgId:"";
	}

	public void setServerOrgId(String serverOrgId) {
		this.serverOrgId = serverOrgId;
	}
	
	public String getSendMode() {
		return StringUtils.isNotEmpty(sendMode)?sendMode:"";
	}

	public void setSendMode(String sendMode) {
		this.sendMode = sendMode;
	}
	
	public String getSpecialty() {
		return StringUtils.isNotEmpty(specialty)?specialty:"";
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	
	public String getType() {
		return StringUtils.isNotEmpty(type)?type:"";
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getMaterial() {
		return StringUtils.isNotEmpty(material)?material:"";
	}

	public void setMaterial(String material) {
		this.material = material;
	}
	
	@Length(min=0, max=255, message="检材处理长度必须介于 0 和 255 之间")
	public String getMaterialDispose() {
		return StringUtils.isNotEmpty(materialDispose)?materialDispose:"";
	}

	public void setMaterialDispose(String materialDispose) {
		this.materialDispose = materialDispose;
	}
	
	@Length(min=0, max=1, message="时限结果长度必须介于 0 和 1 之间")
	public String getTimeLimitResult() {
		return StringUtils.isNotEmpty(timeLimitResult)?timeLimitResult:"";
	}

	public void setTimeLimitResult(String timeLimitResult) {
		this.timeLimitResult = timeLimitResult;
	}
	
	public String getTimeLimitReport() {
		return StringUtils.isNotEmpty(timeLimitReport)?timeLimitReport:"";
	}

	public void setTimeLimitReport(String timeLimitReport) {
		this.timeLimitReport = timeLimitReport;
	}
	
	public Double getStandardFee() {
		return standardFee;
	}

	public void setStandardFee(Double standardFee) {
		this.standardFee = standardFee;
	}
	
	public Double getSpecialFee() {
		return specialFee;
	}

	public void setSpecialFee(Double specialFee) {
		this.specialFee = specialFee;
	}
	
	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}
	
	@Length(min=0, max=255, message="鉴定项长度必须介于 0 和 255 之间")
	public String getAppraisalItem() {
		return StringUtils.isNotEmpty(appraisalItem)?appraisalItem:"";
	}

	public void setAppraisalItem(String appraisalItem) {
		this.appraisalItem = appraisalItem;
	}
	
	public Boolean getClientAvoid() {
		return clientAvoid;
	}

	public void setClientAvoid(Boolean clientAvoid) {
		this.clientAvoid = clientAvoid;
	}
	
	public Boolean getAuthorizeNotification() {
		return authorizeNotification;
	}

	public void setAuthorizeNotification(Boolean authorizeNotification) {
		this.authorizeNotification = authorizeNotification;
	}
	
	public String getStatus() {
		return StringUtils.isNotEmpty(status)?status:"";
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=255, message="流程id长度必须介于 0 和 255 之间")
	public String getProcInsId() {
		return StringUtils.isNotEmpty(procInsId)?procInsId:"";
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=0, max=255, message="被检人姓名长度必须介于 0 和 255 之间")
	public String getSurveyorName() {
		return StringUtils.isNotEmpty(surveyorName)?surveyorName:"";
	}

	public void setSurveyorName(String surveyorName) {
		this.surveyorName = surveyorName;
	}
	
	@Length(min=0, max=255, message="被检人性别长度必须介于 0 和 255 之间")
	public String getSurveyorSex() {
		return StringUtils.isNotEmpty(surveyorSex)?surveyorSex:"";
	}

	public void setSurveyorSex(String surveyorSex) {
		this.surveyorSex = surveyorSex;
	}
	
	@Length(min=0, max=255, message="被检人出生日期长度必须介于 0 和 255 之间")
	public String getSurveyorBirthday() {
		return StringUtils.isNotEmpty(surveyorBirthday)?surveyorBirthday:"";
	}

	public void setSurveyorBirthday(String surveyorBirthday) {
		this.surveyorBirthday = surveyorBirthday;
	}
	
	@Length(min=0, max=255, message="委托事项长度必须介于 0 和 255 之间")
	public String getMattersEntrusted() {
		return StringUtils.isNotEmpty(mattersEntrusted)?mattersEntrusted:"";
	}

	public void setMattersEntrusted(String mattersEntrusted) {
		this.mattersEntrusted = mattersEntrusted;
	}
	
	@Length(min=0, max=255, message="鉴定材料长度必须介于 0 和 255 之间")
	public String getClinicIdentification() {
		return StringUtils.isNotEmpty(clinicIdentification)?clinicIdentification:"";
	}

	public void setClinicIdentification(String clinicIdentification) {
		this.clinicIdentification = clinicIdentification;
	}
	
	@Length(min=0, max=255, message="验伤通知书长度必须介于 0 和 255 之间")
	public String getClinicTriage() {
		return StringUtils.isNotEmpty(clinicTriage)?clinicTriage:"";
	}

	public void setClinicTriage(String clinicTriage) {
		this.clinicTriage = clinicTriage;
	}
	
	@Length(min=0, max=255, message="病史长度必须介于 0 和 255 之间")
	public String getClinicMedical() {
		return StringUtils.isNotEmpty(clinicMedical)?clinicMedical:"";
	}

	public void setClinicMedical(String clinicMedical) {
		this.clinicMedical = clinicMedical;
	}
	
	@Length(min=0, max=255, message="出院小结长度必须介于 0 和 255 之间")
	public String getClinicSummary() {
		return StringUtils.isNotEmpty(clinicSummary)?clinicSummary:"";
	}

	public void setClinicSummary(String clinicSummary) {
		this.clinicSummary = clinicSummary;
	}
	
	@Length(min=0, max=245, message="X片长度必须介于 0 和 245 之间")
	public String getClinicXray() {
		return StringUtils.isNotEmpty(clinicXray)?clinicXray:"";
	}

	public void setClinicXray(String clinicXray) {
		this.clinicXray = clinicXray;
	}
	
	@Length(min=0, max=255, message="CT长度必须介于 0 和 255 之间")
	public String getClinicCt() {
		return StringUtils.isNotEmpty(clinicCt)?clinicCt:"";
	}

	public void setClinicCt(String clinicCt) {
		this.clinicCt = clinicCt;
	}
	
	@Length(min=0, max=255, message="MRI长度必须介于 0 和 255 之间")
	public String getClinicMri() {
		return StringUtils.isNotEmpty(clinicMri)?clinicMri:"";
	}

	public void setClinicMri(String clinicMri) {
		this.clinicMri = clinicMri;
	}
	
	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}
	
	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
		
}