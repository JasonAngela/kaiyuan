/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clinic.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.ActEntity;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 材料登记Entity
 * @author fuyun
 * @version 2017-11-28
 */
public class ClinicPhysical extends ActEntity<ClinicPhysical> {
	
	private static final long serialVersionUID = 1L;
	private ClinicRegister register;		// register_id
	private String code;		// 物证编号
	private String caseCode;		// 受理编号
	private Date beginCreateDate;		// 开始 创建时间
	private Date endCreateDate;		// 结束 创建时间
	private List<ClinicPhysicalIteam> clinicPhysicalIteamList = Lists.newArrayList();		// 子表列表
	
	public ClinicPhysical() {
		super();
	}

	public ClinicPhysical(String id){
		super(id);
	}

	public ClinicRegister getRegister() {
		return register;
	}

	public void setRegister(ClinicRegister register) {
		this.register = register;
	}

	@Length(min=0, max=255, message="物证编号长度必须介于 0 和 255 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=255, message="受理编号长度必须介于 0 和 255 之间")
	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
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
		
	public List<ClinicPhysicalIteam> getClinicPhysicalIteamList() {
		return clinicPhysicalIteamList;
	}

	public void setClinicPhysicalIteamList(List<ClinicPhysicalIteam> clinicPhysicalIteamList) {
		this.clinicPhysicalIteamList = clinicPhysicalIteamList;
	}
}