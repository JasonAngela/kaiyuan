/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 酒精物证Entity
 * @author fuyun
 * @version 2018-01-23
 */
public class ClcoholEvidenceIteam extends DataEntity<ClcoholEvidenceIteam> {
	
	private static final long serialVersionUID = 1L;
	private ClcoholEvidence clcoholEvidence;		// clcohol_evidence_id 父类
	private String name;		// 名称
	private String number;		// 数量
	private String type;		// type
	private String code;		// code
	private String uploud;		// 上传
	private String idnumber;		// 证件号
	private String other1;		// other1
	private String other2;		// other2
	
	public ClcoholEvidenceIteam() {
		super();
	}

	public ClcoholEvidenceIteam(String id){
		super(id);
	}

	public ClcoholEvidenceIteam(ClcoholEvidence clcoholEvidence){
		this.clcoholEvidence = clcoholEvidence;
	}

	@NotNull(message="clcohol_evidence_id不能为空")
	public ClcoholEvidence getClcoholEvidence() {
		return clcoholEvidence;
	}

	public void setClcoholEvidence(ClcoholEvidence clcoholEvidence) {
		this.clcoholEvidence = clcoholEvidence;
	}
	
	@Length(min=0, max=255, message="名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="数量长度必须介于 0 和 255 之间")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@Length(min=0, max=255, message="type长度必须介于 0 和 255 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="code长度必须介于 0 和 255 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=255, message="上传长度必须介于 0 和 255 之间")
	public String getUploud() {
		return uploud;
	}

	public void setUploud(String uploud) {
		this.uploud = uploud;
	}
	
	@Length(min=0, max=255, message="证件号长度必须介于 0 和 255 之间")
	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
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
	
}