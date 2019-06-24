/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clinic.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicAuthorise;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicFirstdraft;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicInspection;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicPhysicalIteam;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicRegister;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicWritten;
import com.thinkgem.jeesite.modules.clinic.service.ClinicAuthoriseService;
import com.thinkgem.jeesite.modules.clinic.service.ClinicFirstdraftService;
import com.thinkgem.jeesite.modules.clinic.service.ClinicInspectionService;
import com.thinkgem.jeesite.modules.clinic.service.ClinicRegisterService;
import com.thinkgem.jeesite.modules.clinic.service.ClinicWrittenService;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 法医成文修改Controller
 * @author fuyun
 * @version 2017-11-26
 */
@Controller
@RequestMapping(value = "${adminPath}/clinic/clinicWritten")
public class ClinicWrittenController extends BaseController {

	@Autowired
	private ClinicWrittenService clinicWrittenService;
	@Autowired
	private ClinicAuthoriseService clinicAuthoriseService;
	@Autowired
	private ClinicRegisterService clinicRegisterService;
	@Autowired
	private ClinicFirstdraftService clinicFirstdraftService;  
	@Autowired
	private ClinicInspectionService clinicInspectionService;
	
	@ModelAttribute
	public ClinicWritten get(@RequestParam(required=false) String id) {
		ClinicWritten entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = clinicWrittenService.get(id);
		}
		if (entity == null){
			entity = new ClinicWritten();
		}
		return entity;
	}
	 
	@RequiresPermissions("clinic:clinicWritten:view")
	@RequestMapping(value = {"list", ""})
	public String list(ClinicWritten clinicWritten, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ClinicWritten> page = clinicWrittenService.findPage(new Page<ClinicWritten>(request, response), clinicWritten); 
		model.addAttribute("page", page);
		return "modules/clinic/clinicWrittenList";
	}

	@RequiresPermissions("clinic:clinicWritten:view")
	@RequestMapping(value = "form")
	public String form(ClinicWritten clinicWritten, Model model) {
		String registerId;
		if(clinicWritten.getRegister()!=null){
			registerId = clinicWritten.getRegister().getId();
		}else{
			registerId = clinicWritten.getAct().getBusinessId();
		}
		model.addAttribute("registerId", registerId);
		ClinicFirstdraft clinicFirstdraft= clinicFirstdraftService.findFirst(registerId);
	    ClinicInspection clinicInspection=	clinicInspectionService.findRegister(registerId);
	    ClinicAuthorise clinicAuthorise= clinicAuthoriseService.findRegister(registerId);
	    ClinicRegister clinicRegister=clinicRegisterService.get(registerId);
	    //尚法[2017]伤鉴字第X号
	    String datecode=clinicRegister.getCaseCode().substring(2,6);
	    String casecode=  clinicRegister.getCaseCode().substring(11, 16);
		int ss = 0;
		int[] a = new int[casecode.length()];
		for (int i = 0; i < casecode.length(); i++) {
			//先由字符串转换成char,再转换成String,然后Integer
			a[i] = Integer.parseInt(String.valueOf(casecode.charAt(i)));
			if (a[i] != 0) {
				ss = i;
				break;
			}
		}
		casecode = casecode.substring(ss, casecode.length());
		int s=  Integer.parseInt(casecode);
		model.addAttribute("casecode", s);
	    model.addAttribute("datecode", datecode);
		//委托方
	    clinicWritten.setDelegate(clinicRegister.getClientName());
	    //委托事项
	    clinicWritten.setToaccept(clinicRegister.getType());
	    //受理日期
        clinicWritten.setAcceptdate(clinicRegister.getClientZipcode());	    
	    //鉴定材料 
        int i=1;
        String identification="1、委托书1份\n";
        if(!StringUtils.isEmpty( clinicRegister.getClinicTriage())){
        	i=i+1;
        	identification+=i+"、验伤通知书"+clinicRegister.getClinicTriage()+"份\n";
        }
        if(!StringUtils.isEmpty(clinicRegister.getClinicMedical())){
        	i=i+1;
        	identification+=i+"、病史"+clinicRegister.getClinicMedical()+"份\n";
        }
        if(!StringUtils.isEmpty(clinicRegister.getClinicSummary())){
        	i=i+1;
        	identification+=i+"、出院小结"+clinicRegister.getClinicSummary()+"份\n";
        }
        if(!StringUtils.isEmpty(clinicRegister.getClinicXray())){
        	i=i+1;
        	identification+=i+"、X片"+clinicRegister.getClinicXray()+"张\n";
        }
        if(!StringUtils.isEmpty(clinicRegister.getClinicCt())){
        	i=i+1;
        	identification+=i+"、CT"+clinicRegister.getClinicCt()+"张\n";
        }
        if(!StringUtils.isEmpty(clinicRegister.getClinicMri())){
        	i=i+1;
        	identification+=i+"、MRI"+clinicRegister.getClinicMri()+"张\n";
        }
        clinicWritten.setIdentification(identification);
	    //被鉴定人&nbsp;
       
        String bysurveyor="";
        //clinicWritten.setBysurveyor(clinicRegister.getSurveyorName());
         
        bysurveyor+= clinicRegister.getSurveyorName()+"           ";
        
        if(clinicRegister.getSurveyorSex()=="1"){
        	bysurveyor+="男       ";
        }else{
        	bysurveyor+="女       ";
        }
        bysurveyor+= "出生日期"+clinicRegister.getSurveyorBirthday()+"\n"+"身份证号:"+clinicRegister.getIdCard();
        clinicWritten.setBysurveyor(bysurveyor);
        //鉴定日期
	      clinicWritten.setAuthorisesurveyor("   "+clinicFirstdraft.getAppraisalDate());
	    //鉴定地点
	      clinicWritten.setIdentifylocations("本司法鉴定所");
		//第一鉴定人
	      clinicWritten.setFirstuser(clinicFirstdraft.getFirstSurveyor());
	    //第二鉴定人
	      clinicWritten.setSecouduser(clinicInspection.getSecondSurveyor());
	     //授权签字人 
	      clinicWritten.setAuthoriseuser(clinicAuthorise.getAuthoriseSurveyor());
	      //在场人员
	      clinicWritten.setPersonnel("本司法鉴定所工作人员");
	      //基本案情
		clinicWritten.setOpinion("    "+clinicFirstdraft.getClinicAttorney());
		//资料摘要
		clinicWritten.setClinicthispaper("    "+clinicFirstdraft.getClinicThisPaper());
		//检验方法
		clinicWritten.setInspectionmethods("    "+clinicFirstdraft.getInspectionMethods());
		//鉴定标准
		clinicWritten.setAppraisalstandard("    "+clinicFirstdraft.getAppraisalStandard());
		clinicWritten.setCc("    "+clinicFirstdraft.getCc());
		clinicWritten.setBody("    "+clinicFirstdraft.getBody());  
		clinicWritten.setReading("    "+clinicFirstdraft.getReading());
		//分析说明
		clinicWritten.setAnalysisshows("    "+clinicFirstdraft.getAnalysisShows());
		clinicWritten.setExpertopinion("    "+clinicFirstdraft.getExpertOpinion());
		clinicWritten.setReading("    "+clinicFirstdraft.getReading());
		model.addAttribute("clinicWritten",clinicWritten);
		return "modules/clinic/clinicWrittenForm";
	}

	@RequiresPermissions("clinic:clinicWritten:edit")
	@RequestMapping(value = "save")
	public String save(ClinicWritten clinicWritten, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, clinicWritten)){
			return form(clinicWritten, model);
		}
		clinicWrittenService.save(clinicWritten);
		addMessage(redirectAttributes, "保存法医成文修改成功");
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	@RequiresPermissions("clinic:clinicWritten:edit")
	@RequestMapping(value = "delete")
	public String delete(ClinicWritten clinicWritten, RedirectAttributes redirectAttributes) {
		clinicWrittenService.delete(clinicWritten);
		addMessage(redirectAttributes, "删除法医成文修改成功");
		return "redirect:"+Global.getAdminPath()+"/clinic/clinicWritten/?repage";
	}
	
	
	public static void main(String[] args) {
		String dd="	HT2018011700019";
		System.out.println(dd.substring(3, 7));
	}
	

}