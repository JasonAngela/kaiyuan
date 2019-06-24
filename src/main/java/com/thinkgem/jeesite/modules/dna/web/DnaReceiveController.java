/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dna.web;

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
import com.thinkgem.jeesite.modules.dna.entity.DnaExperimentSpecimen;
import com.thinkgem.jeesite.modules.dna.entity.DnaReceive;
import com.thinkgem.jeesite.modules.dna.entity.DnaReceiveIteam;
import com.thinkgem.jeesite.modules.dna.service.DnaReceiveService;
import com.thinkgem.jeesite.modules.material.dao.SpecimenMaterialRegisterItemDao;
import com.thinkgem.jeesite.modules.material.entity.SpecimenMaterialRegisterItem;

/**
 * 领取样品Controller
 * @author fuyun
 * @version 2018-06-22
 */
@Controller
@RequestMapping(value = "${adminPath}/dna/dnaReceive")
public class DnaReceiveController extends BaseController {

	@Autowired
	private DnaReceiveService dnaReceiveService;
	@Autowired
	private SpecimenMaterialRegisterItemDao specimenMaterialRegisterItemDao;
	@ModelAttribute
	public DnaReceive get(@RequestParam(required=false) String id) {
		DnaReceive entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dnaReceiveService.get(id);
		}
		if (entity == null){
			entity = new DnaReceive();
		}
		return entity;
	}
	
	@RequiresPermissions("dna:dnaReceive:view")
	@RequestMapping(value = {"list", ""})
	public String list(DnaReceive dnaReceive, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DnaReceive> page = dnaReceiveService.findPage(new Page<DnaReceive>(request, response), dnaReceive); 
		model.addAttribute("page", page);
		return "modules/dna/dnaReceiveList";
	}

	@RequestMapping(value = "form")
	public String form(DnaReceive dnaReceive, Model model) {
		List<SpecimenMaterialRegisterItem> notExperimentList =  specimenMaterialRegisterItemDao.getNotExperiment();
		for(SpecimenMaterialRegisterItem item:notExperimentList){
			DnaReceiveIteam  dnaReceiveIteam = new DnaReceiveIteam();
			dnaReceiveIteam.setSpecode(item.getCode());
			dnaReceive.getDnaReceiveIteamList().add(dnaReceiveIteam);
		}
		model.addAttribute("dnaReceive", dnaReceive);
		return "modules/dna/dnaReceiveForm";
	}

	@RequestMapping(value = "form2")
	public String form1(DnaReceive dnaReceive, Model model) {
		List<SpecimenMaterialRegisterItem> notExperimentList =  specimenMaterialRegisterItemDao.getNotExperiment2();
		for(SpecimenMaterialRegisterItem item:notExperimentList){
			DnaReceiveIteam  dnaReceiveIteam = new DnaReceiveIteam();
			dnaReceiveIteam.setSpecode(item.getCode());
			dnaReceive.getDnaReceiveIteamList().add(dnaReceiveIteam);
		}
		model.addAttribute("dnaReceive", dnaReceive);
		return "modules/dna/dnaReceiveForm";
	}

	@RequestMapping(value = "form3")
	public String form3(DnaReceive dnaReceive, Model model) {
		List<SpecimenMaterialRegisterItem> notExperimentList =  specimenMaterialRegisterItemDao.getNotExperiment3();
		for(SpecimenMaterialRegisterItem item:notExperimentList){
			DnaReceiveIteam  dnaReceiveIteam = new DnaReceiveIteam();
			dnaReceiveIteam.setSpecode(item.getCode());
			dnaReceive.getDnaReceiveIteamList().add(dnaReceiveIteam);
		}
		model.addAttribute("dnaReceive", dnaReceive);
		return "modules/dna/dnaReceiveForm";
	}
	@RequestMapping(value = "form4")
	public String form4(DnaReceive dnaReceive, Model model) {
		List<SpecimenMaterialRegisterItem> notExperimentList =  specimenMaterialRegisterItemDao.getNotExperiment4();
		for(SpecimenMaterialRegisterItem item:notExperimentList){
			DnaReceiveIteam  dnaReceiveIteam = new DnaReceiveIteam();
			dnaReceiveIteam.setSpecode(item.getCode());
			dnaReceive.getDnaReceiveIteamList().add(dnaReceiveIteam);
		}
		model.addAttribute("dnaReceive", dnaReceive);
		return "modules/dna/dnaReceiveForm";
	}
	
	
	@RequestMapping(value = "save")
	public String save(DnaReceive dnaReceive, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, dnaReceive)){
			return form(dnaReceive, model);
		}
		dnaReceiveService.save(dnaReceive);
		addMessage(redirectAttributes, "保存领取样品成功");
		return "redirect:" + adminPath + "/sys/user/info";
	}
	
	@RequiresPermissions("dna:dnaReceive:edit")
	@RequestMapping(value = "delete")
	public String delete(DnaReceive dnaReceive, RedirectAttributes redirectAttributes) {
		dnaReceiveService.delete(dnaReceive);
		addMessage(redirectAttributes, "删除领取样品成功");
		return "redirect:"+Global.getAdminPath()+"/dna/dnaReceive/?repage";
	}

}