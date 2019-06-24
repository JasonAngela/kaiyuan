/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entrust.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;

import com.thinkgem.jeesite.common.utils.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 委托登记Entity
 * @author zhuguli
 * @version 2017-07-21
 */
public class EntrustRegister extends DataEntity<EntrustRegister> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 编码
	private String clientName;		// 委托人
	private String caseCode;		// case_code
	private String clientTel;		// 委托人电话
	private String clientReceiver;		// 委托收件人 其他约定事项
	private String clientEmail;		// 委托人邮箱  
	private String clientFax;		// 委托人传真  
	private String clientZipcode;		// 委托人邮编
	private String clientArea;		// 委托人区域
	private String clientAddress;		// 委托人地址   
	private String agentName;		// 送检人(机构)
	private String agentTel;		// 送检人电话
	private String serverName;		// 受理人  
	private String serverOrgId;		// 受理机构
	private String sendMode;		// 报告传递方式  其他
	private String specialty;		// 专业
	private String type;		// 类型
	private String material;		// 关于鉴定材料
	private String materialDispose;		// 检材处理  关于鉴定材料
	private String timeLimitResult;		// 时限结果  几个工作日完成
	private String timeLimitReport;		// 时间报告
	private Double standardFee;		// 标准费用 预计收费总金额
	private Double specialFee;		// 特殊费用 处理费
	private Double totalFee;		// 合计费用
	private String appraisalItem;		// 鉴定项
	private Boolean clientAvoid;		// 鉴定人回避
	private Boolean authorizeNotification;		// 授权客服人员通知
	private String status;		// 状态
	private String procInsId;		// 流程id
	private String whether;		// 鉴定用途
	private String purposeOther;		// 鉴定用途其他
	private String chargeway;		// 收取方式
	private String sendWay;		// 发送方式
	private String sendExplain;		// 发送方式说明
	private String capital;		// 大写
	private String aboutMaterials;		// 关于鉴定材料
	private String specialRequirements;		// 关于鉴定材料特殊要求
	private String remaining;		// 剩余鉴定材料
	private String weeks;		// 周内
	private String otherWay;		// 其他方式
	private String avoidAppraisers;		// 需要回避鉴定人
	private String evadingReason;		// 回避事由
	private String entrustDate;		// 委托日期
	private List<EntrustAbstracts> entrustAbstractsList = Lists.newArrayList();		// 子表列表
	private Date beginEntrustDate;		// 开始 委托日期
	private Date endEntrustDate;		// 结束 委托日期
	
	
	
	
	public List<EntrustAbstracts> getEntrustAbstractsList() {
		return entrustAbstractsList;
	}

	public void setEntrustAbstractsList(List<EntrustAbstracts> entrustAbstractsList) {
		this.entrustAbstractsList = entrustAbstractsList;
	}

	public Date getBeginEntrustDate() {
		return beginEntrustDate;
	}

	public void setBeginEntrustDate(Date beginEntrustDate) {
		this.beginEntrustDate = beginEntrustDate;
	}

	public Date getEndEntrustDate() {
		return endEntrustDate;
	}

	public void setEndEntrustDate(Date endEntrustDate) {
		this.endEntrustDate = endEntrustDate;
	}

	public EntrustRegister() {
		super();
	}

	public EntrustRegister(String id){
		super(id);
	}

	@Length(min=0, max=255, message="编码长度必须介于 0 和 255 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=255, message="委托人长度必须介于 0 和 255 之间")
	public String getClientName() {
		return StringUtils.isEmpty(clientName)?"":clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	@Length(min=0, max=255, message="case_code长度必须介于 0 和 255 之间")
	public String getCaseCode() {
		return StringUtils.isEmpty(caseCode)?"":caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	
	@Length(min=0, max=255, message="委托人电话长度必须介于 0 和 255 之间")
	public String getClientTel() {
		return StringUtils.isEmpty(clientTel)?"":clientTel;
	}

	public void setClientTel(String clientTel) {
		this.clientTel = clientTel;
	}
	
	@Length(min=0, max=255, message="委托收件人长度必须介于 0 和 255 之间")
	public String getClientReceiver() {
		return StringUtils.isEmpty(clientReceiver)?"":clientReceiver;
	}

	public void setClientReceiver(String clientReceiver) {
		this.clientReceiver = clientReceiver;
	}
	
	@Length(min=0, max=255, message="委托人邮箱长度必须介于 0 和 255 之间")
	public String getClientEmail() {
		return StringUtils.isEmpty(clientEmail)?"":clientEmail;
	}

	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}
	
	@Length(min=0, max=255, message="委托人传真长度必须介于 0 和 255 之间")
	public String getClientFax() {
		return StringUtils.isEmpty(clientFax)?"":clientFax;
	}

	public void setClientFax(String clientFax) {
		this.clientFax = clientFax;
	}
	
	@Length(min=0, max=255, message="委托人邮编长度必须介于 0 和 255 之间")
	public String getClientZipcode() {
		return StringUtils.isEmpty(clientZipcode)?"":clientZipcode;
	}

	public void setClientZipcode(String clientZipcode) {
		this.clientZipcode = clientZipcode;
	}
	
	@Length(min=0, max=1000, message="委托人区域长度必须介于 0 和 1000 之间")
	public String getClientArea() {
		return StringUtils.isEmpty(clientArea)?"":clientArea;
	}

	public void setClientArea(String clientArea) {
		this.clientArea = clientArea;
	}
	
	@Length(min=0, max=1000, message="委托人地址长度必须介于 0 和 1000 之间")
	public String getClientAddress() {
		return StringUtils.isEmpty(clientAddress)?"":clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}
	
	@Length(min=0, max=255, message="送检人(机构)长度必须介于 0 和 255 之间")
	public String getAgentName() {
		return StringUtils.isEmpty(agentName)?"":agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	@Length(min=0, max=255, message="送检人电话长度必须介于 0 和 255 之间")
	public String getAgentTel() {
		return StringUtils.isEmpty(agentTel)?"":agentTel;
	}

	public void setAgentTel(String agentTel) {
		this.agentTel = agentTel;
	}
	
	@Length(min=0, max=255, message="受理人长度必须介于 0 和 255 之间")
	public String getServerName() {
		return StringUtils.isEmpty(serverName)?"":serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	
	@Length(min=0, max=255, message="受理机构长度必须介于 0 和 255 之间")
	public String getServerOrgId() {
		return StringUtils.isEmpty(serverOrgId)?"":serverOrgId;
	}

	public void setServerOrgId(String serverOrgId) {
		this.serverOrgId = serverOrgId;
	}
	
	public String getSendMode() {
		return StringUtils.isEmpty(sendMode)?"":sendMode;
	}

	public void setSendMode(String sendMode) {
		this.sendMode = sendMode;
	}
	
	@Length(min=0, max=1, message="专业长度必须介于 0 和 1 之间")
	public String getSpecialty() {
		return StringUtils.isEmpty(specialty)?"":specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	
	@Length(min=0, max=1, message="类型长度必须介于 0 和 1 之间")
	public String getType() {
		return StringUtils.isEmpty(type)?"":type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="鉴定材料长度必须介于 0 和 255 之间")
	public String getMaterial() {
		return StringUtils.isEmpty(material)?"":material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
	
	@Length(min=0, max=255, message="检材处理长度必须介于 0 和 255 之间")
	public String getMaterialDispose() {
		return StringUtils.isEmpty(materialDispose)?"":materialDispose;
	}

	public void setMaterialDispose(String materialDispose) {
		this.materialDispose = materialDispose;
	}
	
	@Length(min=0, max=255, message="时限结果长度必须介于 0 和 255之间")
	public String getTimeLimitResult() {
		return StringUtils.isEmpty(timeLimitResult)?"":timeLimitResult;
	}

	public void setTimeLimitResult(String timeLimitResult) {
		this.timeLimitResult = timeLimitResult;
	}
	
	@Length(min=0, max=1, message="时间报告长度必须介于 0 和 1 之间")
	public String getTimeLimitReport() {
		return StringUtils.isEmpty(timeLimitReport)?"":timeLimitReport;
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
		return StringUtils.isEmpty(appraisalItem)?"":appraisalItem;
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
		return StringUtils.isEmpty(status)?"":status ;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=255, message="流程id长度必须介于 0 和 255 之间")
	public String getProcInsId() {
		return StringUtils.isEmpty(procInsId)?"":procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=0, max=255, message="是否重新鉴定长度必须介于 0 和 255 之间")
	public String getWhether() {
		return StringUtils.isEmpty(whether)?"":whether;
	}

	public void setWhether(String whether) {
		this.whether = whether;
	}
	
	@Length(min=0, max=255, message="鉴定用途其他长度必须介于 0 和 255 之间")
	public String getPurposeOther() {
		return StringUtils.isEmpty(purposeOther)?"":purposeOther;
	}

	public void setPurposeOther(String purposeOther) {
		this.purposeOther = purposeOther;
	}
	
	@Length(min=0, max=255, message="收取方式长度必须介于 0 和 255 之间")
	public String getChargeway() {
		return StringUtils.isEmpty(chargeway)?"":chargeway;
	}

	public void setChargeway(String chargeway) {
		this.chargeway = chargeway;
	}
	
	@Length(min=0, max=255, message="发送方式长度必须介于 0 和 255 之间")
	public String getSendWay() {
		return StringUtils.isEmpty(sendWay)?"":sendWay;
	}

	public void setSendWay(String sendWay) {
		this.sendWay = sendWay;
	}
	
	@Length(min=0, max=255, message="发送方式说明长度必须介于 0 和 255 之间")
	public String getSendExplain() {
		return StringUtils.isEmpty(sendExplain)?"":sendExplain;
	}

	public void setSendExplain(String sendExplain) {
		this.sendExplain = sendExplain;
	}
	
	@Length(min=0, max=255, message="大写长度必须介于 0 和 255 之间")
	public String getCapital() {
		return StringUtils.isEmpty(capital)?"":capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}
	
	@Length(min=0, max=255, message="关于鉴定材料长度必须介于 0 和 255 之间")
	public String getAboutMaterials() {
		return StringUtils.isEmpty(aboutMaterials)?"":aboutMaterials;
	}

	public void setAboutMaterials(String aboutMaterials) {
		this.aboutMaterials = aboutMaterials;
	}
	
	@Length(min=0, max=255, message="关于鉴定材料特殊要求长度必须介于 0 和 255 之间")
	public String getSpecialRequirements() {
		return StringUtils.isEmpty(specialRequirements)?"":specialRequirements;
	}

	public void setSpecialRequirements(String specialRequirements) {
		this.specialRequirements = specialRequirements;
	}
	
	@Length(min=0, max=255, message="剩余鉴定材料长度必须介于 0 和 255 之间")
	public String getRemaining() {
		return StringUtils.isEmpty(remaining)?"":remaining;
	}

	public void setRemaining(String remaining) {
		this.remaining = remaining;
	}
	
	@Length(min=0, max=255, message="周内长度必须介于 0 和 255 之间")
	public String getWeeks() {
		return StringUtils.isEmpty(weeks)?"":weeks;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}
	
	@Length(min=0, max=255, message="其他方式长度必须介于 0 和 255 之间")
	public String getOtherWay() {
		return StringUtils.isEmpty(otherWay)?"":otherWay;
	}

	public void setOtherWay(String otherWay) {
		this.otherWay = otherWay;
	}
	
	@Length(min=0, max=255, message="需要回避鉴定人长度必须介于 0 和 255 之间")
	public String getAvoidAppraisers() {
		return StringUtils.isEmpty(avoidAppraisers)?"":avoidAppraisers;
	}

	public void setAvoidAppraisers(String avoidAppraisers) {
		this.avoidAppraisers = avoidAppraisers;
	}
	
	@Length(min=0, max=255, message="回避事由长度必须介于 0 和 255 之间")
	public String getEvadingReason() {
		return StringUtils.isEmpty(evadingReason)?"":evadingReason;
	}

	public void setEvadingReason(String evadingReason) {
		this.evadingReason = evadingReason;
	}
	
	@Length(min=0, max=255, message="委托日期长度必须介于 0 和 255 之间")
	public String getEntrustDate() {
		return StringUtils.isEmpty(entrustDate)?"":entrustDate;
	}

	public void setEntrustDate(String entrustDate) {
		this.entrustDate = entrustDate;
	}
	

 



	
}