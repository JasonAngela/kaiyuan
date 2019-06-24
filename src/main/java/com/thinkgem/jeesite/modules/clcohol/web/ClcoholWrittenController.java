/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.web;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.thinkgem.jeesite.modules.clcohol.dao.ClcoholFirstDao;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholAuthorization;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholEvidence;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholEvidenceIteam;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholFirst;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholRegister;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholSecond;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholWritten;
import com.thinkgem.jeesite.modules.clcohol.service.ClcoholAuthorizationService;
import com.thinkgem.jeesite.modules.clcohol.service.ClcoholEvidenceService;
import com.thinkgem.jeesite.modules.clcohol.service.ClcoholRegisterService;
import com.thinkgem.jeesite.modules.clcohol.service.ClcoholSecondService;
import com.thinkgem.jeesite.modules.clcohol.service.ClcoholWrittenService;

/**
 * 酒精成文修改Controller
 * @author fuyun
 * @version 2018-01-23
 */
@Controller
@RequestMapping(value = "${adminPath}/clcohol/clcoholWritten")
public class ClcoholWrittenController extends BaseController {

	@Autowired
	private ClcoholWrittenService clcoholWrittenService;
	@Autowired
	private ClcoholSecondService  clcoholSecondService;	
	@Autowired
	private ClcoholFirstDao clcoholFirstDao;
	@Autowired
	private ClcoholAuthorizationService clcoholAuthorizationService;
	@Autowired
	private ClcoholRegisterService clcoholRegisterService;
	@Autowired
	private ClcoholEvidenceService clcoholEvidenceService;
	@ModelAttribute
	public ClcoholWritten get(@RequestParam(required=false) String id) {
		ClcoholWritten entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = clcoholWrittenService.get(id);
		}
		if (entity == null){
			entity = new ClcoholWritten();
		}
		return entity;
	} 
	
	@RequiresPermissions("clcohol:clcoholWritten:view")
	@RequestMapping(value = {"list", ""})
	public String list(ClcoholWritten clcoholWritten, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ClcoholWritten> page = clcoholWrittenService.findPage(new Page<ClcoholWritten>(request, response), clcoholWritten); 
		model.addAttribute("page", page);
		return "modules/clcohol/clcoholWrittenList";
	}

	@RequiresPermissions("clcohol:clcoholWritten:view")
	@RequestMapping(value = "form")
	public String form(ClcoholWritten clcoholWritten, Model model) {
		String registerId;
		if(clcoholWritten.getRegister()!=null){
			registerId = clcoholWritten.getRegister().getId();
		}else{
			registerId = clcoholWritten.getAct().getBusinessId();
		}
		
		ClcoholRegister clcoholRegister =  clcoholRegisterService.get(registerId);
		String casecode= clcoholRegister.getCasecode().substring(10, 15);
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
		
		ClcoholEvidence clcoholEvidence= clcoholEvidenceService.getRegister(registerId);
		List<ClcoholEvidenceIteam>clcoholEvidenceIteamList	=clcoholEvidence.getClcoholEvidenceIteamList();
		String	nameA=null;
		String	codeA=null;
		for (ClcoholEvidenceIteam clcoholEvidenceIteam : clcoholEvidenceIteamList) {
			if(!StringUtils.isEmpty(clcoholEvidenceIteam.getCode())){
				codeA   =clcoholEvidenceIteam.getCode();
				nameA	=clcoholEvidenceIteam.getName();
			}
		}
		String	testingMaterials=
				"1、委托书1份\r"+"<br/>"+
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、血样1份（物证密封袋编号："+codeA+")";
		casecode = casecode.substring(ss, casecode.length());
		int s=  Integer.parseInt(casecode);
		model.addAttribute("casecode", s);
		model.addAttribute("simple", clcoholRegister.getCasecode().substring(2, 6)); 
		ClcoholFirst clcoholFirst= clcoholFirstDao.findRegister(registerId);
		ClcoholSecond clcoholSecond=clcoholSecondService.findRegister(registerId);
		ClcoholAuthorization clcoholAuthorization= clcoholAuthorizationService.findRegister(registerId);
		clcoholWritten.setBasicFacts(clcoholAuthorization.getBasicFacts());
		clcoholWritten.setTestResult(clcoholAuthorization.getTestResult());
		clcoholFirst.setTestingMaterials(testingMaterials);
		model.addAttribute("clcoholFirst", clcoholFirst);
		model.addAttribute("clcoholSecond", clcoholSecond);
		model.addAttribute("clcoholWritten", clcoholWritten);
		model.addAttribute("clcoholAuthorization", clcoholAuthorization);
		model.addAttribute("registerId", registerId);
		return "modules/clcohol/clcoholWrittenForm";
	}

	@RequiresPermissions("clcohol:clcoholWritten:edit")
	@RequestMapping(value = "save")
	public String save(ClcoholWritten clcoholWritten, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, clcoholWritten)){
			return form(clcoholWritten, model);
		}
		clcoholWrittenService.save(clcoholWritten);
		addMessage(redirectAttributes, "保存酒精成文修改成功");
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	@RequiresPermissions("clcohol:clcoholWritten:edit")
	@RequestMapping(value = "delete")
	public String delete(ClcoholWritten clcoholWritten, RedirectAttributes redirectAttributes) {
		clcoholWrittenService.delete(clcoholWritten);
		addMessage(redirectAttributes, "删除酒精成文修改成功");
		return "redirect:"+Global.getAdminPath()+"/clcohol/clcoholWritten/?repage";
	}

}