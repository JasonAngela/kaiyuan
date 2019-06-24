package com.thinkgem.jeesite.modules.dna.entity;

import java.util.List;

public class DnaMutation{

	
	private  String Ccode;
	private String Pcode;
	private String Mutation;
	private int Totle;
	private String locis;
	

	public String getLocis() {
		return locis;
	}
	public void setLocis(String locis) {
		this.locis = locis;
	}
	public String getCcode() {
		return Ccode;
	}
	public void setCcode(String ccode) {
		Ccode = ccode;
	}
	public String getPcode() {
		return Pcode;
	}
	public void setPcode(String pcode) {
		Pcode = pcode;
	}
	public String getMutation() {
		return Mutation;
	}
	public void setMutation(String mutation) {
		Mutation = mutation;
	}
	public int getTotle() {
		return Totle;
	}
	public void setTotle(int locil) {
		Totle = locil;
	}
	
	
	
}
