/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entrust.web;

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
import com.thinkgem.jeesite.modules.entrust.entity.EntrustModifyrecord;
import com.thinkgem.jeesite.modules.entrust.service.EntrustModifyrecordService;

/**
 * 报告修改记录Controller
 * @author fuyun
 * @version 2017-12-14
 */
@Controller
@RequestMapping(value = "${adminPath}/entrust/entrustModifyrecord")
public class EntrustModifyrecordController extends BaseController {

	@Autowired
	private EntrustModifyrecordService entrustModifyrecordService;
	
	@ModelAttribute
	public EntrustModifyrecord get(@RequestParam(required=false) String id) {
		EntrustModifyrecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = entrustModifyrecordService.get(id);
		}
		if (entity == null){
			entity = new EntrustModifyrecord();
		}
		return entity;
	}
	
	
	@RequestMapping(value = {"list", ""})
	public String list(EntrustModifyrecord entrustModifyrecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<EntrustModifyrecord> page = entrustModifyrecordService.findPage(new Page<EntrustModifyrecord>(request, response), entrustModifyrecord); 
		model.addAttribute("page", page);
		return "modules/entrust/entrustModifyrecordList";
	}

	
	@RequestMapping(value = "form")
	public String form(EntrustModifyrecord entrustModifyrecord, Model model) {
		model.addAttribute("entrustModifyrecord", entrustModifyrecord);
		return "modules/entrust/entrustModifyrecordForm";
	}

	
	@RequestMapping(value = "save")
	public String save(EntrustModifyrecord entrustModifyrecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, entrustModifyrecord)){
			return form(entrustModifyrecord, model);
		}
		entrustModifyrecordService.save(entrustModifyrecord);
		addMessage(redirectAttributes, "保存报告修改记录成功");
		return "redirect:"+Global.getAdminPath()+"/entrust/entrustModifyrecord/?repage";
	}
	
	
	@RequestMapping(value = "delete")
	public String delete(EntrustModifyrecord entrustModifyrecord, RedirectAttributes redirectAttributes) {
		entrustModifyrecordService.delete(entrustModifyrecord);
		addMessage(redirectAttributes, "删除报告修改记录成功");
		return "redirect:"+Global.getAdminPath()+"/entrust/entrustModifyrecord/?repage";
	}

}