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
import com.thinkgem.jeesite.modules.clinic.entity.ClinicAuthorise;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicFirstdraft;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicInspection;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicPhysicalIteam;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicRegister;
import com.thinkgem.jeesite.modules.clinic.service.ClinicAuthoriseService;
import com.thinkgem.jeesite.modules.clinic.service.ClinicFirstdraftService;
import com.thinkgem.jeesite.modules.clinic.service.ClinicInspectionService;
import com.thinkgem.jeesite.modules.clinic.service.ClinicRegisterService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 法医授权签字人Controller
 * @author fuyun
 * @version 2017-11-26
 */
@Controller
@RequestMapping(value = "${adminPath}/clinic/clinicAuthorise")
public class ClinicAuthoriseController extends BaseController {

	@Autowired
	private ClinicAuthoriseService clinicAuthoriseService;
	@Autowired
	private ClinicFirstdraftService clinicFirstdraftService;
	@Autowired
	private ClinicInspectionService clinicInspectionService;
	@Autowired
	private ClinicRegisterService clinicRegisterService;
	
	@ModelAttribute
	public ClinicAuthorise get(@RequestParam(required=false) String id) {
		ClinicAuthorise entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = clinicAuthoriseService.get(id);
		}
		if (entity == null){
			entity = new ClinicAuthorise();
		}
		return entity;
	}
	
	@RequiresPermissions("clinic:clinicAuthorise:view")
	@RequestMapping(value = {"list", ""})
	public String list(ClinicAuthorise clinicAuthorise, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ClinicAuthorise> page = clinicAuthoriseService.findPage(new Page<ClinicAuthorise>(request, response), clinicAuthorise); 
		model.addAttribute("page", page);
		return "modules/clinic/clinicAuthoriseList";
	}

	@RequiresPermissions("clinic:clinicAuthorise:view")
	@RequestMapping(value = "form")
	public String form(ClinicAuthorise clinicAuthorise, Model model) {
		String registerId;
		Act act=clinicAuthorise.getAct();
		if(clinicAuthorise.getRegister()!=null){
			registerId = clinicAuthorise.getRegister().getId();
		}else{
			registerId = clinicAuthorise.getAct().getBusinessId();
		}
		if(clinicAuthoriseService.findRegister(registerId)!=null){
			clinicAuthorise=clinicAuthoriseService.findRegister(registerId);
			clinicAuthorise.setAct(act);
		}
		ClinicInspection clinicInspection=clinicInspectionService.findRegister(registerId);
		model.addAttribute("clinicInspection",clinicInspection);
		model.addAttribute("registerId", registerId);
		 model.addAttribute("user", UserUtils.getUser().getName());
		List<String>pics=new ArrayList<String>();
		List<String>pdfs=new ArrayList<String>();
		List<String>pdf1=new ArrayList<String>();
		 List<ClinicPhysicalIteam> clinicPhysicalIteams=clinicFirstdraftService.findClinicPhysical(registerId);
			model.addAttribute("clinicPhysicalIteams", clinicPhysicalIteams);
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
		ClinicRegister	clinicRegister =clinicRegisterService.get(registerId);
		model.addAttribute("pdf1", pdf1);
		model.addAttribute("pdf", pdfs);
		 model.addAttribute("pic",pics); 
		model.addAttribute("clinicAuthorise", clinicAuthorise);
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
		return "modules/clinic/clinicAuthoriseForm";
	}

	@RequiresPermissions("clinic:clinicAuthorise:edit")
	@RequestMapping(value = "save")
	public String save(ClinicAuthorise clinicAuthorise, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, clinicAuthorise)){
			return form(clinicAuthorise, model);
		}
		clinicAuthoriseService.save(clinicAuthorise);
		addMessage(redirectAttributes, "保存法医授权签字人成功");
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	@RequiresPermissions("clinic:clinicAuthorise:edit")
	@RequestMapping(value = "delete")
	public String delete(ClinicAuthorise clinicAuthorise, RedirectAttributes redirectAttributes) {
		clinicAuthoriseService.delete(clinicAuthorise);
		addMessage(redirectAttributes, "删除法医授权签字人成功");
		return "redirect:"+Global.getAdminPath()+"/clinic/clinicAuthorise/?repage";
	}

}