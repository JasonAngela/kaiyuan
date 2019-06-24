package com.thinkgem.jeesite.modules.entrust.entity;

import java.io.Serializable;

public class DnaExperimentStrDTO implements Serializable {
     private String specimenCode;		// 检样编码

     private String xy1;

     private String xy2;


    public String getSpecimenCode() {
        return specimenCode;
    }

    public void setSpecimenCode(String specimenCode) {
        this.specimenCode = specimenCode;
    }

    public String getXy1() {
        return xy1;
    }

    public void setXy1(String xy1) {
        this.xy1 = xy1;
    }

    public String getXy2() {
        return xy2;
    }

    public void setXy2(String xy2) {
        this.xy2 = xy2;
    }
}