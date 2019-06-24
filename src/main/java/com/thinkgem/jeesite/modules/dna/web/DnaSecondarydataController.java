/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dna.web;

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
import com.thinkgem.jeesite.modules.dna.entity.DnaSecondarydata;
import com.thinkgem.jeesite.modules.dna.service.DnaSecondarydataService;

/**
 * dna临时导入二次数据表Controller
 * @author fuyun
 * @version 2018-06-28
 */
@Controller
@RequestMapping(value = "${adminPath}/dna/dnaSecondarydata")
public class DnaSecondarydataController extends BaseController {

	@Autowired
	private DnaSecondarydataService dnaSecondarydataService;
	
	@ModelAttribute
	public DnaSecondarydata get(@RequestParam(required=false) String id) {
		DnaSecondarydata entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dnaSecondarydataService.get(id);
		}
		if (entity == null){
			entity = new DnaSecondarydata();
		}
		return entity;
	}
	
	@RequiresPermissions("dna:dnaSecondarydata:view")
	@RequestMapping(value = {"list", ""})
	public String list(DnaSecondarydata dnaSecondarydata, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DnaSecondarydata> page = dnaSecondarydataService.findPage(new Page<DnaSecondarydata>(request, response), dnaSecondarydata); 
		model.addAttribute("page", page);
		return "modules/dna/dnaSecondarydataList";
	}

	@RequiresPermissions("dna:dnaSecondarydata:view")
	@RequestMapping(value = "form")
	public String form(DnaSecondarydata dnaSecondarydata, Model model) {
		model.addAttribute("dnaSecondarydata", dnaSecondarydata);
		return "modules/dna/dnaSecondarydataForm";
	}

	@RequiresPermissions("dna:dnaSecondarydata:edit")
	@RequestMapping(value = "save")
	public String save(DnaSecondarydata dnaSecondarydata, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, dnaSecondarydata)){
			return form(dnaSecondarydata, model);
		}
		dnaSecondarydataService.save(dnaSecondarydata);
		addMessage(redirectAttributes, "保存dna临时导入二次数据表成功");
		return "redirect:"+Global.getAdminPath()+"/dna/dnaSecondarydata/?repage";
	}
	
	@RequiresPermissions("dna:dnaSecondarydata:edit")
	@RequestMapping(value = "delete")
	public String delete(DnaSecondarydata dnaSecondarydata, RedirectAttributes redirectAttributes) {
		dnaSecondarydataService.delete(dnaSecondarydata);
		addMessage(redirectAttributes, "删除dna临时导入二次数据表成功");
		return "redirect:"+Global.getAdminPath()+"/dna/dnaSecondarydata/?repage";
	}

}