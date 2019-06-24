/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.web;

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
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholEvidence;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholEvidenceIteam;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholRegister;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholSamples;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholSamplesIteam;
import com.thinkgem.jeesite.modules.clcohol.service.ClcoholEvidenceService;
import com.thinkgem.jeesite.modules.clcohol.service.ClcoholRegisterService;
import com.thinkgem.jeesite.modules.clcohol.service.ClcoholSamplesService;
import com.thinkgem.jeesite.modules.sys.service.SysCodeRuleService;

/**
 * 酒精领取样品Controller
 * @author fuyun
 * @version 2018-01-29
 */
@Controller
@RequestMapping(value = "${adminPath}/clcohol/clcoholSamples")
public class ClcoholSamplesController extends BaseController {

	@Autowired
	private ClcoholSamplesService clcoholSamplesService;
	@Autowired
	private ClcoholRegisterService clcoholRegisterService;
	@Autowired
	private ClcoholEvidenceService clcoholEvidenceService;
	
	
	
	
	@ModelAttribute
	public ClcoholSamples get(@RequestParam(required=false) String id) {
		ClcoholSamples entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = clcoholSamplesService.get(id);
		}
		if (entity == null){
			entity = new ClcoholSamples();
		}
		return entity;
	}
	
	@RequiresPermissions("clcohol:clcoholSamples:view")
	@RequestMapping(value = {"list", ""})
	public String list(ClcoholSamples clcoholSamples, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ClcoholSamples> page = clcoholSamplesService.findPage(new Page<ClcoholSamples>(request, response), clcoholSamples); 
		model.addAttribute("page", page);
		return "modules/clcohol/clcoholSamplesList";
	}

	@RequiresPermissions("clcohol:clcoholSamples:view")
	@RequestMapping(value = "form")
	public String form(ClcoholSamples clcoholSamples, Model model) {
		
		String registerId;
		if(clcoholSamples.getRegister()!=null){
			registerId = clcoholSamples.getRegister().getId();
		}else{
			registerId = clcoholSamples.getAct().getBusinessId();
		}
		ClcoholEvidence clcoholEvidence= clcoholEvidenceService.getRegister(registerId);
		List<ClcoholEvidenceIteam>clcoholEvidenceIteamList	=clcoholEvidence.getClcoholEvidenceIteamList();
		String nameA="";
		String codeA="";
		String codeB="";
		String nameB="";
		for (ClcoholEvidenceIteam clcoholEvidenceIteam : clcoholEvidenceIteamList) {
			if(clcoholEvidenceIteam.getCode().contains("-A")){
				nameA=clcoholEvidenceIteam.getName();
				codeA=clcoholEvidenceIteam.getCode();
			}
			if(clcoholEvidenceIteam.getCode().contains("-B")){
				nameB=clcoholEvidenceIteam.getName();
				codeB=clcoholEvidenceIteam.getCode();
			}
			
		}
	     model.addAttribute("codeA", codeA);
	     model.addAttribute("nameA", nameA);
	     model.addAttribute("codeB", codeB);
	     model.addAttribute("nameB", nameB);
	     model.addAttribute("registerId", registerId);
		 model.addAttribute("clcoholSamples", clcoholSamples);
		return "modules/clcohol/clcoholSamplesForm";
	}

	@RequiresPermissions("clcohol:clcoholSamples:edit")
	@RequestMapping(value = "save")
	public String save(ClcoholSamples clcoholSamples, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, clcoholSamples)){
			return form(clcoholSamples, model);
		}
		clcoholSamplesService.save(clcoholSamples);
		addMessage(redirectAttributes, "保存酒精领取样品成功");
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	@RequiresPermissions("clcohol:clcoholSamples:edit")
	@RequestMapping(value = "delete")
	public String delete(ClcoholSamples clcoholSamples, RedirectAttributes redirectAttributes) {
		clcoholSamplesService.delete(clcoholSamples);
		addMessage(redirectAttributes, "删除酒精领取样品成功");
		return "redirect:"+Global.getAdminPath()+"/clcohol/clcoholSamples/?repage";
	}

}