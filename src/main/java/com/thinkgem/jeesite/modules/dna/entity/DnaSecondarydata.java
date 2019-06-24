/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dna.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * dna临时导入二次数据表Entity
 * @author fuyun
 * @version 2018-06-28
 */
public class DnaSecondarydata extends DataEntity<DnaSecondarydata> {
	
	private static final long serialVersionUID = 1L;
	private String x;		// x
	private String y;		// y
	private String specimencode;		// specimenCode
	private String geneloci;		// geneLoci
	
	public DnaSecondarydata() {
		super();
	}

	public DnaSecondarydata(String id){
		super(id);
	}

	@Length(min=0, max=255, message="x长度必须介于 0 和 255 之间")
	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}
	
	@Length(min=0, max=255, message="y长度必须介于 0 和 255 之间")
	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}
	
	@Length(min=0, max=255, message="specimenCode长度必须介于 0 和 255 之间")
	public String getSpecimencode() {
		return specimencode;
	}

	public void setSpecimencode(String specimencode) {
		this.specimencode = specimencode;
	}
	
	@Length(min=0, max=255, message="geneLoci长度必须介于 0 和 255 之间")
	public String getGeneloci() {
		return geneloci;
	}

	public void setGeneloci(String geneloci) {
		this.geneloci = geneloci;
	}
	
}