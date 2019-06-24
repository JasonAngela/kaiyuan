/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.ActEntity;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 酒精领取样品Entity
 * @author fuyun
 * @version 2018-01-29
 */
public class ClcoholSamples extends ActEntity<ClcoholSamples> {
	
	private static final long serialVersionUID = 1L;
	private ClcoholRegister register;		// register_id
	private String totalNumber;		// 总数
	private String remaining;		// 剩余数量
	private String receiveNumber;		// 领取数量
	private String other;		// other
	private String other2;		// other2
	private String other1;		// other1
	private String code;		// 委托编码
	private String casecode;		// 案件编码
	private String entrust;		// 委托方
	private String contactphone;		// 联系电话
	private String type;		// 样品类型
	private String sampleState;		// 样品状态
	private String entrustDate;		// 委托日期
	private String miningdate;		// 采取时间
	private String uploud;		// 上传
	private List<ClcoholSamplesIteam> clcoholSamplesIteamList = Lists.newArrayList();		// 子表列表
	
	public ClcoholSamples() {
		super();
	}

	public ClcoholSamples(String id){
		super(id);
	}
	
	public ClcoholRegister getRegister() {
		return register;
	}

	public void setRegister(ClcoholRegister register) {
		this.register = register;
	}

	@Length(min=0, max=255, message="总数长度必须介于 0 和 255 之间")
	public String getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(String totalNumber) {
		this.totalNumber = totalNumber;
	}
	
	@Length(min=0, max=255, message="剩余数量长度必须介于 0 和 255 之间")
	public String getRemaining() {
		return remaining;
	}

	public void setRemaining(String remaining) {
		this.remaining = remaining;
	}
	
	@Length(min=0, max=255, message="领取数量长度必须介于 0 和 255 之间")
	public String getReceiveNumber() {
		return receiveNumber;
	}

	public void setReceiveNumber(String receiveNumber) {
		this.receiveNumber = receiveNumber;
	}
	
	@Length(min=0, max=255, message="other长度必须介于 0 和 255 之间")
	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
	
	@Length(min=0, max=255, message="other2长度必须介于 0 和 255 之间")
	public String getOther2() {
		return other2;
	}

	public void setOther2(String other2) {
		this.other2 = other2;
	}
	
	@Length(min=0, max=255, message="other1长度必须介于 0 和 255 之间")
	public String getOther1() {
		return other1;
	}

	public void setOther1(String other1) {
		this.other1 = other1;
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
	
	@Length(min=0, max=255, message="委托方长度必须介于 0 和 255 之间")
	public String getEntrust() {
		return entrust;
	}

	public void setEntrust(String entrust) {
		this.entrust = entrust;
	}
	
	@Length(min=0, max=255, message="联系电话长度必须介于 0 和 255 之间")
	public String getContactphone() {
		return contactphone;
	}

	public void setContactphone(String contactphone) {
		this.contactphone = contactphone;
	}
	
	@Length(min=0, max=255, message="样品类型长度必须介于 0 和 255 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="样品状态长度必须介于 0 和 255 之间")
	public String getSampleState() {
		return sampleState;
	}

	public void setSampleState(String sampleState) {
		this.sampleState = sampleState;
	}
	
	@Length(min=0, max=255, message="委托日期长度必须介于 0 和 255 之间")
	public String getEntrustDate() {
		return entrustDate;
	}

	public void setEntrustDate(String entrustDate) {
		this.entrustDate = entrustDate;
	}
	
	@Length(min=0, max=255, message="采取时间长度必须介于 0 和 255 之间")
	public String getMiningdate() {
		return miningdate;
	}

	public void setMiningdate(String miningdate) {
		this.miningdate = miningdate;
	}
	
	@Length(min=0, max=255, message="上传长度必须介于 0 和 255 之间")
	public String getUploud() {
		return uploud;
	}

	public void setUploud(String uploud) {
		this.uploud = uploud;
	}
	
	public List<ClcoholSamplesIteam> getClcoholSamplesIteamList() {
		return clcoholSamplesIteamList;
	}

	public void setClcoholSamplesIteamList(List<ClcoholSamplesIteam> clcoholSamplesIteamList) {
		this.clcoholSamplesIteamList = clcoholSamplesIteamList;
	}
}