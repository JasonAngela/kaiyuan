/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.web;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholFirst;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholPapers;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholRegister;
import com.thinkgem.jeesite.modules.clcohol.service.ClcoholFirstService;
import com.thinkgem.jeesite.modules.clcohol.service.ClcoholPapersService;
import com.thinkgem.jeesite.modules.clcohol.service.ClcoholRegisterService;

/**
 * 酒精底稿Controller
 * @author fuyun
 * @version 2018-05-21
 */
@Controller
@RequestMapping(value = "${adminPath}/clcohol/clcoholPapers")
public class ClcoholPapersController extends BaseController {

	@Autowired
	private ClcoholPapersService clcoholPapersService;
	@Autowired
	private ClcoholFirstService clcoholFirstService;
	@Autowired
	private ClcoholRegisterService clcoholRegisterService;
	
	@ModelAttribute
	public ClcoholPapers get(@RequestParam(required=false) String id) {
		ClcoholPapers entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = clcoholPapersService.get(id);
		}
		if (entity == null){
			entity = new ClcoholPapers();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(ClcoholPapers clcoholPapers, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ClcoholPapers> page = clcoholPapersService.findPage(new Page<ClcoholPapers>(request, response), clcoholPapers); 
		model.addAttribute("page", page);
		return "modules/clcohol/clcoholPapersList";
	}

	@RequestMapping(value = "form")
	public String form(ClcoholPapers clcoholPapers, Model model) {
		model.addAttribute("clcoholPapers", clcoholPapers);
		ClcoholFirst	clcoholFirst = clcoholFirstService.findRegister(clcoholPapers.getRegister().getId());
		model.addAttribute("clcoholFirst", clcoholFirst);
		ClcoholRegister  clcoholRegister=clcoholRegisterService.get(clcoholPapers.getRegister().getId());
		String casecode= clcoholRegister.getCasecode().substring(10, 15);
		SimpleDateFormat simple = new SimpleDateFormat("yyyy");                                       
		model.addAttribute("casecode", casecode);
		model.addAttribute("simple", simple.format( new Date()));
		
		return "modules/clcohol/clcoholPapersForm";
	}

	@RequestMapping(value = "save")
	public String save(ClcoholPapers clcoholPapers, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, clcoholPapers)){
			return form(clcoholPapers, model);
		}
		clcoholPapersService.save(clcoholPapers);
		addMessage(redirectAttributes, "保存酒精底稿成功");
		return "redirect:"+Global.getAdminPath()+"/clcohol/clcoholPapers/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(ClcoholPapers clcoholPapers, RedirectAttributes redirectAttributes) {
		clcoholPapersService.delete(clcoholPapers);
		addMessage(redirectAttributes, "删除酒精底稿成功");
		return "redirect:"+Global.getAdminPath()+"/clcohol/clcoholPapers/?repage";
	}

}