/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clinic.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 材料登记Entity
 * @author fuyun
 * @version 2017-11-28
 */
public class ClinicPhysicalIteam extends DataEntity<ClinicPhysicalIteam> {
	
	private static final long serialVersionUID = 1L;
	private ClinicPhysical physical;		// 材料登记id 父类
	private String name;		// 材料名称
	private String quantity;		// 材料数量
	private String type;		// 材料类型
	private String upload;		// 上传
	private String code;		// code
	private Date beginCreateDate;		// 开始 创建时间
	private Date endCreateDate;		// 结束 创建时间
	
	public ClinicPhysicalIteam() {
		super();
	}

	public ClinicPhysicalIteam(String id){
		super(id);
	}

	public ClinicPhysicalIteam(ClinicPhysical physical){
		this.physical = physical;
	}

	@NotNull(message="材料登记id不能为空")
	public ClinicPhysical getPhysical() {
		return physical;
	}

	public void setPhysical(ClinicPhysical physical) {
		this.physical = physical;
	}
	
	@Length(min=0, max=255, message="材料名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="材料数量长度必须介于 0 和 255 之间")
	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	@Length(min=0, max=255, message="材料类型长度必须介于 0 和 255 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="上传长度必须介于 0 和 255 之间")
	public String getUpload() {
		return upload;
	}

	public void setUpload(String upload) {
		this.upload = upload;
	}
	
	@Length(min=0, max=255, message="code长度必须介于 0 和 255 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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