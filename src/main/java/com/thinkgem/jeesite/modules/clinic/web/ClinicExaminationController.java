/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clinic.web;

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
import com.thinkgem.jeesite.modules.clinic.entity.ClinicExamination;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicRegister;
import com.thinkgem.jeesite.modules.clinic.service.ClinicExaminationService;
import com.thinkgem.jeesite.modules.clinic.service.ClinicRegisterService;

/**
 * 人员验伤Controller
 * @author fuyun
 * @version 2017-11-26
 */
@Controller
@RequestMapping(value = "${adminPath}/clinic/clinicExamination")
public class ClinicExaminationController extends BaseController {

	@Autowired
	private ClinicExaminationService clinicExaminationService;
	@Autowired
	private ClinicRegisterService clinicRegisterService;
	@ModelAttribute
	public ClinicExamination get(@RequestParam(required=false) String id) {
		ClinicExamination entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = clinicExaminationService.get(id);
		}
		if (entity == null){
			entity = new ClinicExamination();
		}
		return entity;
	}
	
	@RequiresPermissions("clinic:clinicExamination:view")
	@RequestMapping(value = {"list", ""})
	public String list(ClinicExamination clinicExamination, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ClinicExamination> page = clinicExaminationService.findPage(new Page<ClinicExamination>(request, response), clinicExamination); 
		model.addAttribute("page", page);
		return "modules/clinic/clinicExaminationList";
	}

	@RequiresPermissions("clinic:clinicExamination:view")
	@RequestMapping(value = "form")
	public String form(ClinicExamination clinicExamination, Model model) {
		model.addAttribute("clinicExamination", clinicExamination);
		String registerId;
		if(clinicExamination.getRegister()!=null){
			registerId = clinicExamination.getRegister().getId();
		}else{
			registerId = clinicExamination.getAct().getBusinessId();
		}
		
		ClinicRegister clinicRegister = clinicRegisterService.get(registerId);
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
		model.addAttribute("simple", clinicRegister.getCaseCode().substring(2, 6));     
		model.addAttribute("registerId", registerId);
		return "modules/clinic/clinicExaminationForm";
	}
	
	
	
	@RequiresPermissions("clinic:clinicExamination:edit")
	@RequestMapping(value = "save")
	public String save(ClinicExamination clinicExamination, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception {
		if (!beanValidator(model, clinicExamination)){
			return form(clinicExamination, model);
		}
		clinicExaminationService.save(clinicExamination,request);
		addMessage(redirectAttributes, "保存人员验伤成功");
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	@RequiresPermissions("clinic:clinicExamination:edit")
	@RequestMapping(value = "delete")
	public String delete(ClinicExamination clinicExamination, RedirectAttributes redirectAttributes) {
		clinicExaminationService.delete(clinicExamination);
		addMessage(redirectAttributes, "删除人员验伤成功");
		return "redirect:"+Global.getAdminPath()+"/clinic/clinicExamination/?repage";
	}

}