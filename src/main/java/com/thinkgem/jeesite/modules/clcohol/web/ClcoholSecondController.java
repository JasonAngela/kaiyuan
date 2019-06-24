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
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.clcohol.dao.ClcoholFirstDao;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholEvidence;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholEvidenceIteam;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholFirst;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholRegister;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholSecond;
import com.thinkgem.jeesite.modules.clcohol.service.ClcoholEvidenceService;
import com.thinkgem.jeesite.modules.clcohol.service.ClcoholRegisterService;
import com.thinkgem.jeesite.modules.clcohol.service.ClcoholSecondService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 酒精第二鉴定人Controller
 * @author fuyun
 * @version 2018-01-23
 */
@Controller
@RequestMapping(value = "${adminPath}/clcohol/clcoholSecond")
public class ClcoholSecondController extends BaseController {

	@Autowired
	private ClcoholSecondService clcoholSecondService;
	@Autowired
	private ClcoholFirstDao clcoholFirstDao;
	@Autowired
	private ClcoholRegisterService clcoholRegisterService;
	@Autowired
	private ClcoholEvidenceService clcoholEvidenceService;
	
	
	@ModelAttribute
	public ClcoholSecond get(@RequestParam(required=false) String id){
		ClcoholSecond entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = clcoholSecondService.get(id);
		}
		if (entity == null){
			entity = new ClcoholSecond();
		}
		return entity;
	}
	
	@RequiresPermissions("clcohol:clcoholSecond:view")
	@RequestMapping(value = {"list", ""})
	public String list(ClcoholSecond clcoholSecond, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ClcoholSecond> page = clcoholSecondService.findPage(new Page<ClcoholSecond>(request, response), clcoholSecond); 
		model.addAttribute("page", page);
		return "modules/clcohol/clcoholSecondList";
	}

	@RequiresPermissions("clcohol:clcoholSecond:view")
	@RequestMapping(value = "form")
	public String form(ClcoholSecond clcoholSecond, Model model) {
		String registerId;
		Act act=clcoholSecond.getAct();
		if(clcoholSecond.getRegister()!=null){
			registerId = clcoholSecond.getRegister().getId();
		}else{
			registerId = clcoholSecond.getAct().getBusinessId();
		}
		if( clcoholSecondService.findRegister(registerId)!=null){
			clcoholSecond=clcoholSecondService.findRegister(registerId);
			clcoholSecond.setId(clcoholSecond.getId());
			clcoholSecond.setAct(act);
		}
		
		
		
		//DNA2017071100057
		
		ClcoholRegister	entity = clcoholRegisterService.get(registerId);
		String casecode= entity.getCasecode().substring(10, 15);
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
		model.addAttribute("simple", entity.getCasecode().substring(2, 6)); 
		ClcoholFirst clcoholFirst= clcoholFirstDao.findRegister(registerId);
		clcoholFirst.setTestingMaterials(testingMaterials);
		clcoholSecond.setBasicFacts(clcoholFirst.getBasicFacts());//检案再要
		clcoholSecond.setSampleStatus(clcoholFirst.getSampleStatus());//检材的一般检验及描述
		clcoholSecond.setWorkInstruction(clcoholFirst.getTestResults());//鉴定意见
		User user=UserUtils.getUser(); 
		model.addAttribute("user", user);	
		model.addAttribute("clcoholSecond", clcoholSecond);
		model.addAttribute("clcoholFirst", clcoholFirst);
		model.addAttribute("registerId", registerId);
		return "modules/clcohol/clcoholSecondForm";
	}

	@RequiresPermissions("clcohol:clcoholSecond:edit")
	@RequestMapping(value = "save")
	public String save(ClcoholSecond clcoholSecond, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, clcoholSecond)){
			return form(clcoholSecond, model);
		}
		clcoholSecondService.save(clcoholSecond);
		addMessage(redirectAttributes, "保存酒精第二鉴定人成功");
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	@RequiresPermissions("clcohol:clcoholSecond:edit")
	@RequestMapping(value = "delete")
	public String delete(ClcoholSecond clcoholSecond, RedirectAttributes redirectAttributes) {
		clcoholSecondService.delete(clcoholSecond);
		addMessage(redirectAttributes, "删除酒精第二鉴定人成功");
		return "redirect:"+Global.getAdminPath()+"/clcohol/clcoholSecond/?repage";
	}

}