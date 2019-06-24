/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.entity;

import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.ActEntity;
import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.google.common.collect.Lists;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 酒精物证Entity
 * @author fuyun
 * @version 2018-01-23
 */
public class ClcoholEvidence extends ActEntity<ClcoholEvidence> {
	
	private static final long serialVersionUID = 1L;
	private ClcoholRegister resgister;		// resgister_id
	private String name;		// 名称
	private String code;		// 物证编号
	private String casecode;		// 受理编号
	private String entrust;		// 委托方
	private String entrustDate;		// 委托日期
	private String address;		// 取样地点
	private String mining;		// 采取
	private String miningdate;		// 采取时间
	private String type;		// 样品类型
	private String other;		// other
	private String inspection;		// 送检人签名
	private String inspectionNumber;		// 联系电话
	private String sampleState;		// 样品状态
	private String other1;		// other1
	private String other2;		// other2
	private String other3;		// other3
	private List<ClcoholEvidenceIteam> clcoholEvidenceIteamList = Lists.newArrayList();		// 子表列表
	
	public ClcoholEvidence() {
		super();
	}

	public ClcoholEvidence(String id){
		super(id);
	}

	
	public ClcoholRegister getResgister() {
		return resgister;
	}

	public void setResgister(ClcoholRegister resgister) {
		this.resgister = resgister;
	}

	@Length(min=0, max=255, message="名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="物证编号长度必须介于 0 和 255 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=255, message="受理编号长度必须介于 0 和 255 之间")
	public String getCasecode() {
		return casecode;
	}

	public void setCasecode(String casecode) {
		this.casecode = casecode;
	}
	
	@Length(min=0, max=255, message="委托方长度必须介于 0 和 255 之间")
	public String getEntrust() {
		return entrust;
	}

	public void setEntrust(String entrust) {
		this.entrust = entrust;
	}
	
	@Length(min=0, max=255, message="委托日期长度必须介于 0 和 255 之间")
	public String getEntrustDate() {
		return entrustDate;
	}

	public void setEntrustDate(String entrustDate) {
		this.entrustDate = entrustDate;
	}
	
	@Length(min=0, max=255, message="取样地点长度必须介于 0 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=255, message="采取长度必须介于 0 和 255 之间")
	public String getMining() {
		return mining;
	}

	public void setMining(String mining) {
		this.mining = mining;
	}
	
	@Length(min=0, max=255, message="采取时间长度必须介于 0 和 255 之间")
	public String getMiningdate() {
		return miningdate;
	}

	public void setMiningdate(String miningdate) {
		this.miningdate = miningdate;
	}
	
	@Length(min=0, max=255, message="样品类型长度必须介于 0 和 255 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="other长度必须介于 0 和 255 之间")
	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
	
	@Length(min=0, max=255, message="送检人签名长度必须介于 0 和 255 之间")
	public String getInspection() {
		return inspection;
	}

	public void setInspection(String inspection) {
		this.inspection = inspection;
	}
	
	@Length(min=0, max=255, message="联系电话长度必须介于 0 和 255 之间")
	public String getInspectionNumber() {
		return inspectionNumber;
	}

	public void setInspectionNumber(String inspectionNumber) {
		this.inspectionNumber = inspectionNumber;
	}
	
	@Length(min=0, max=255, message="样品状态长度必须介于 0 和 255 之间")
	public String getSampleState() {
		return sampleState;
	}

	public void setSampleState(String sampleState) {
		this.sampleState = sampleState;
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
	
	@Length(min=0, max=255, message="other3长度必须介于 0 和 255 之间")
	public String getOther3() {
		return other3;
	}

	public void setOther3(String other3) {
		this.other3 = other3;
	}
	
	public List<ClcoholEvidenceIteam> getClcoholEvidenceIteamList() {
		return clcoholEvidenceIteamList;
	}

	public void setClcoholEvidenceIteamList(List<ClcoholEvidenceIteam> clcoholEvidenceIteamList) {
		this.clcoholEvidenceIteamList = clcoholEvidenceIteamList;
	}
}