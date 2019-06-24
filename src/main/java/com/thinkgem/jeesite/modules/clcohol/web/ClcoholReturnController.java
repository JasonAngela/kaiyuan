/**
-  * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.web;

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
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholReturn;
import com.thinkgem.jeesite.modules.clcohol.service.ClcoholReturnService;

/**
 * 酒精样品归还Controller
 * @author fuyun
 * @version 2018-01-23
 */
@Controller
@RequestMapping(value = "${adminPath}/clcohol/clcoholReturn")
public class ClcoholReturnController extends BaseController {

	@Autowired
	private ClcoholReturnService clcoholReturnService;
	
	@ModelAttribute
	public ClcoholReturn get(@RequestParam(required=false) String id) {
		ClcoholReturn entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = clcoholReturnService.get(id);
		}
		if (entity == null){
			entity = new ClcoholReturn();
		}
		return entity;
	}
	
	@RequiresPermissions("clcohol:clcoholReturn:view")
	@RequestMapping(value = {"list", ""})
	public String list(ClcoholReturn clcoholReturn, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ClcoholReturn> page = clcoholReturnService.findPage(new Page<ClcoholReturn>(request, response), clcoholReturn); 
		model.addAttribute("page", page);
		return "modules/clcohol/clcoholReturnList";
	}

	@RequiresPermissions("clcohol:clcoholReturn:view")
	@RequestMapping(value = "form")
	public String form(ClcoholReturn clcoholReturn, Model model) {
		model.addAttribute("clcoholReturn", clcoholReturn);
		return "modules/clcohol/clcoholReturnForm";
	}

	@RequiresPermissions("clcohol:clcoholReturn:edit")
	@RequestMapping(value = "save")
	public String save(ClcoholReturn clcoholReturn, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, clcoholReturn)){
			return form(clcoholReturn, model);
		}
		clcoholReturnService.save(clcoholReturn);
		addMessage(redirectAttributes, "保存酒精样品归还成功");
		return "redirect:"+Global.getAdminPath()+"/clcohol/clcoholReturn/?repage";
	}
	
	@RequiresPermissions("clcohol:clcoholReturn:edit")
	@RequestMapping(value = "delete")
	public String delete(ClcoholReturn clcoholReturn, RedirectAttributes redirectAttributes) {
		clcoholReturnService.delete(clcoholReturn);
		addMessage(redirectAttributes, "删除酒精样品归还成功");
		return "redirect:"+Global.getAdminPath()+"/clcohol/clcoholReturn/?repage";
	}

}