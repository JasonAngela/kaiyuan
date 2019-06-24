/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clinic.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
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
import com.thinkgem.jeesite.modules.clinic.dao.ClinicInspectionDao;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicExamination;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicFirstdraft;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicInspection;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicPhysicalIteam;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicRegister;
import com.thinkgem.jeesite.modules.clinic.service.ClinicFirstdraftService;
import com.thinkgem.jeesite.modules.clinic.service.ClinicInspectionService;
import com.thinkgem.jeesite.modules.clinic.service.ClinicRegisterService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 法医第二鉴定人复检Controller
 * @author fuyun
 * @version 2017-11-26
 */
@Controller
@RequestMapping(value = "${adminPath}/clinic/clinicInspection")
public class ClinicInspectionController extends BaseController {

	@Autowired
	private ClinicInspectionService clinicInspectionService;
	@Autowired
	private ClinicFirstdraftService clinicFirstdraftService;
	@Autowired
	private ClinicInspectionDao clinicInspectionDao;
	@Autowired
	private ClinicRegisterService clinicRegisterService;
	
	@ModelAttribute
	public ClinicInspection get(@RequestParam(required=false) String id) {
		ClinicInspection entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = clinicInspectionService.get(id);
		}
		if (entity == null){
			entity = new ClinicInspection();
		}
		return entity;
	}
	
	@RequiresPermissions("clinic:clinicInspection:view")
	@RequestMapping(value = {"list", ""})
	public String list(ClinicInspection clinicInspection, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ClinicInspection> page = clinicInspectionService.findPage(new Page<ClinicInspection>(request, response), clinicInspection); 
		model.addAttribute("page", page);
		return "modules/clinic/clinicInspectionList";
	}

	@RequiresPermissions("clinic:clinicInspection:view")
	@RequestMapping(value = "form")
	public String form(ClinicInspection clinicInspection, Model model) {
		String registerId;
		Act act=clinicInspection.getAct();
		if(clinicInspection.getRegister()!=null){
			registerId = clinicInspection.getRegister().getId();
		}else{
			registerId = clinicInspection.getAct().getBusinessId();
		}
		
		if(clinicInspectionDao.findRegister(registerId)!=null){	
			clinicInspection=clinicInspectionDao.findRegister(registerId);
			clinicInspection.setAct(act);
		}
		ClinicRegister	clinicRegister =clinicRegisterService.get(registerId);
		ClinicFirstdraft clinicFirstdraft= clinicFirstdraftService.findFirst(registerId);
		model.addAttribute("clinicFirstdraft",clinicFirstdraft);
		model.addAttribute("registerId", registerId);
		model.addAttribute("clinicInspection", clinicInspection);
		 model.addAttribute("user", UserUtils.getUser().getName());
		 List<ClinicPhysicalIteam> clinicPhysicalIteams=clinicFirstdraftService.findClinicPhysical(registerId);
		model.addAttribute("clinicPhysicalIteams", clinicPhysicalIteams);
		List<String>pics=new ArrayList<String>();
		List<String>pdfs=new ArrayList<String>();
		List<String>pdf1=new ArrayList<String>();
		for (int i = 0; i < clinicPhysicalIteams.size(); i++) {
			if(clinicPhysicalIteams.get(i).getUpload()!=null){
				String []d=clinicPhysicalIteams.get(i).getUpload().split("\\|");
				for (String pic : d){
					if(pic.equals("")){
					}else if(pic.contains(".pdf")){
						pdf1.add(pic.substring(pic.lastIndexOf('/')+1, pic.length()));
						pdfs.add(pic);
					}
					else{
						pics.add(pic);
					}
				}
		 	}
		}	
		//人员验伤图片
		ClinicExamination clinicExamination= clinicFirstdraftService.findExam(registerId);
		List<String>pics2=new ArrayList<String>();
		List<String>pdfs2=new ArrayList<String>();
		List<String>pdf2=new ArrayList<String>();
	String[] d1=clinicExamination.getUploud().split("\\|");
	for (String pic : d1) {
		if(pic.equals("")){
		}else if(pic.contains(".pdf")||pic.contains(".doc")){
			pdf2.add(pic.substring(pic.lastIndexOf('/')+1, pic.length()));
			pdfs2.add(pic);
		}else{
			pics2.add(pic);
		}
	}
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
		 model.addAttribute("pdf1",pdf1); 
		 model.addAttribute("pdf",pdfs); 
		 model.addAttribute("pic",pics); 
		 model.addAttribute("pdf2",pdf2); 
		 model.addAttribute("pdf2",pdfs2); 
		 model.addAttribute("pic2",pics2); 
		return "modules/clinic/clinicInspectionForm";
	}

	@RequiresPermissions("clinic:clinicInspection:edit")
	@RequestMapping(value = "save")
	public String save(ClinicInspection clinicInspection, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, clinicInspection)){
			return form(clinicInspection, model);
		}
	    clinicInspectionService.save(clinicInspection);
		addMessage(redirectAttributes, "保存法医第二鉴定人复检成功");
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	@RequiresPermissions("clinic:clinicInspection:edit")
	@RequestMapping(value = "delete")
	public String delete(ClinicInspection clinicInspection, RedirectAttributes redirectAttributes) {
		clinicInspectionService.delete(clinicInspection);
		addMessage(redirectAttributes, "删除法医第二鉴定人复检成功");
		return "redirect:"+Global.getAdminPath()+"/clinic/clinicInspection/?repage";
	}

}