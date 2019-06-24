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
import com.thinkgem.jeesite.modules.clinic.entity.ClinicPapers;
import com.thinkgem.jeesite.modules.clinic.service.ClinicPapersService;

/**
 * 法医底稿Controller
 * @author fuyun
 * @version 2017-12-20
 */
@Controller
@RequestMapping(value = "${adminPath}/clinic/clinicPapers")
public class ClinicPapersController extends BaseController {

	@Autowired
	private ClinicPapersService clinicPapersService;
	
	@ModelAttribute
	public ClinicPapers get(@RequestParam(required=false) String id) {
		ClinicPapers entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = clinicPapersService.get(id);
		}
		if (entity == null){
			entity = new ClinicPapers();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(ClinicPapers clinicPapers, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ClinicPapers> page = clinicPapersService.findPage(new Page<ClinicPapers>(request, response), clinicPapers); 
		model.addAttribute("page", page);
		return "modules/clinic/clinicPapersList";
	}

	@RequestMapping(value = "form")
	public String form(ClinicPapers clinicPapers, Model model) {
		model.addAttribute("clinicPapers", clinicPapers);
		return "modules/clinic/clinicPapersForm";
	}

	@RequestMapping(value = "save")
	public String save(ClinicPapers clinicPapers, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, clinicPapers)){
			return form(clinicPapers, model);
		}
		clinicPapersService.save(clinicPapers);
		addMessage(redirectAttributes, "保存法医底稿成功");
		return "redirect:"+Global.getAdminPath()+"/clinic/clinicPapers/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(ClinicPapers clinicPapers, RedirectAttributes redirectAttributes) {
		clinicPapersService.delete(clinicPapers);
		addMessage(redirectAttributes, "删除法医底稿成功");
		return "redirect:"+Global.getAdminPath()+"/clinic/clinicPapers/?repage";
	}

}