/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 酒精委托书Entity
 * @author fuyun
 * @version 2018-01-24
 */
public class ClcoholRegister extends DataEntity<ClcoholRegister> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 委托编码
	private String casecode;		// 案件编码
	private String clientname;		// 委托人
	private String entrustdate;		// 委托日期
	private String contactphone;		// 联系电话
	private String name;		// 姓名
	private String sex;		// 性别
	private String idnumber;		// 证件号
	private String mattersentrusted;		// 委托事项
	private String fication;		// 鉴定材料
	private String waytosend;		// 发送方式
	private String address;		// 地址
	private String recipient;		// 收件人
	private String zipcode;		// 邮编
	private String avoid;		// 申请人回避
	private String reson;		// 理由
	private String opinion;		// 本鉴定所意见
	private String complete;		// 工作人完成
	private String material;		// 鉴定收费
	private Double standardfee;		// 标准收费
	private Double specialfee;		// 特殊收费
	private Double totalfee;		// 合计收费
	private String appraisalitem;		// 其它
	private String other;		// other
	private String other1;		// other1
	private String other2;		// 状态
	private String other3;		// 登记材料
	private String other4;		// other4
	private String other5;		// other5
	private String procInsId;		// PROC_INS_ID
	private String makesPeople;		// 送检人
	private String type;		// 鉴定类别
	private String withdrawal;		// 申请回避
	private String signed;		// 签订既日起
	
	private Date beginCreateDate;		// 开始 创建时间
	private Date endCreateDate;		// 结束 创建时间
	
	
	
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

	public ClcoholRegister() {
		super();
	}

	public ClcoholRegister(String id){
		super(id);
	}

	@Length(min=0, max=255, message="委托编码长度必须介于 0 和 255 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=255, message="案件编码长度必须介于 0 和 255 之间")
	public String getCasecode() {
		return casecode;
	}

	public void setCasecode(String casecode) {
		this.casecode = casecode;
	}
	
	@Length(min=0, max=255, message="委托人长度必须介于 0 和 255 之间")
	public String getClientname() {
		return clientname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname;
	}
	
	@Length(min=0, max=255, message="委托日期长度必须介于 0 和 255 之间")
	public String getEntrustdate() {
		return entrustdate;
	}

	public void setEntrustdate(String entrustdate) {
		this.entrustdate = entrustdate;
	}
	
	@Length(min=0, max=255, message="联系电话长度必须介于 0 和 255 之间")
	public String getContactphone() {
		return contactphone;
	}

	public void setContactphone(String contactphone) {
		this.contactphone = contactphone;
	}
	
	@Length(min=0, max=255, message="姓名长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="性别长度必须介于 0 和 255 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=255, message="证件号长度必须介于 0 和 255 之间")
	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}
	
	@Length(min=0, max=255, message="委托事项长度必须介于 0 和 255 之间")
	public String getMattersentrusted() {
		return mattersentrusted;
	}

	public void setMattersentrusted(String mattersentrusted) {
		this.mattersentrusted = mattersentrusted;
	}
	
	@Length(min=0, max=255, message="鉴定材料长度必须介于 0 和 255 之间")
	public String getFication() {
		return fication;
	}

	public void setFication(String fication) {
		this.fication = fication;
	}
	
	@Length(min=0, max=255, message="发送方式长度必须介于 0 和 255 之间")
	public String getWaytosend() {
		return waytosend;
	}

	public void setWaytosend(String waytosend) {
		this.waytosend = waytosend;
	}
	
	@Length(min=0, max=255, message="地址长度必须介于 0 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=255, message="收件人长度必须介于 0 和 255 之间")
	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	
	@Length(min=0, max=255, message="邮编长度必须介于 0 和 255 之间")
	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	@Length(min=0, max=255, message="申请人回避长度必须介于 0 和 255 之间")
	public String getAvoid() {
		return avoid;
	}

	public void setAvoid(String avoid) {
		this.avoid = avoid;
	}
	
	@Length(min=0, max=255, message="理由长度必须介于 0 和 255 之间")
	public String getReson() {
		return reson;
	}

	public void setReson(String reson) {
		this.reson = reson;
	}
	
	@Length(min=0, max=255, message="本鉴定所意见长度必须介于 0 和 255 之间")
	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	@Length(min=0, max=255, message="工作人完成长度必须介于 0 和 255 之间")
	public String getComplete() {
		return complete;
	}

	public void setComplete(String complete) {
		this.complete = complete;
	}
	
	@Length(min=0, max=255, message="鉴定收费长度必须介于 0 和 255 之间")
	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
	
	public Double getStandardfee() {
		return standardfee;
	}

	public void setStandardfee(Double standardfee) {
		this.standardfee = standardfee;
	}
	
	public Double getSpecialfee() {
		return specialfee;
	}

	public void setSpecialfee(Double specialfee) {
		this.specialfee = specialfee;
	}
	
	public Double getTotalfee() {
		return totalfee;
	}

	public void setTotalfee(Double totalfee) {
		this.totalfee = totalfee;
	}
	
	@Length(min=0, max=255, message="其它长度必须介于 0 和 255 之间")
	public String getAppraisalitem() {
		return appraisalitem;
	}

	public void setAppraisalitem(String appraisalitem) {
		this.appraisalitem = appraisalitem;
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
	
	public String getOther3() {
		return other3;
	}

	public void setOther3(String other3) {
		this.other3 = other3;
	}
	
	@Length(min=0, max=255, message="other4长度必须介于 0 和 255 之间")
	public String getOther4() {
		return other4;
	}

	public void setOther4(String other4) {
		this.other4 = other4;
	}
	
	@Length(min=0, max=255, message="other5长度必须介于 0 和 255 之间")
	public String getOther5() {
		return other5;
	}

	public void setOther5(String other5) {
		this.other5 = other5;
	}
	
	@Length(min=0, max=255, message="PROC_INS_ID长度必须介于 0 和 255 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=0, max=255, message="送检人长度必须介于 0 和 255 之间")
	public String getMakesPeople() {
		return makesPeople;
	}

	public void setMakesPeople(String makesPeople) {
		this.makesPeople = makesPeople;
	}
	
	@Length(min=0, max=255, message="鉴定类别长度必须介于 0 和 255 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="申请回避长度必须介于 0 和 255 之间")
	public String getWithdrawal() {
		return withdrawal;
	}

	public void setWithdrawal(String withdrawal) {
		this.withdrawal = withdrawal;
	}
	
	@Length(min=0, max=255, message="签订既日起长度必须介于 0 和 255 之间")
	public String getSigned() {
		return signed;
	}

	public void setSigned(String signed) {
		this.signed = signed;
	}
	
}