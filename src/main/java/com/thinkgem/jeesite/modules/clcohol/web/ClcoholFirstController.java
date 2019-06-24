/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholAuthorization;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholEvidence;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholEvidenceIteam;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholFirst;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholRegister;
import com.thinkgem.jeesite.modules.clcohol.service.ClcoholEvidenceService;
import com.thinkgem.jeesite.modules.clcohol.service.ClcoholFirstService;
import com.thinkgem.jeesite.modules.clcohol.service.ClcoholRegisterService;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicExamination;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 酒精初稿Controller
 * @author fuyun
 * @version 2018-01-23
 */
@Controller
@RequestMapping(value = "${adminPath}/clcohol/clcoholFirst")
public class ClcoholFirstController extends BaseController {

	@Autowired
	private ClcoholFirstService clcoholFirstService;
	@Autowired
	private ClcoholRegisterService clcoholRegisterService;
	@Autowired
	private ClcoholEvidenceService clcoholEvidenceService;
	
	@ModelAttribute
	public ClcoholFirst get(@RequestParam(required=false) String id) {
		ClcoholFirst entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = clcoholFirstService.get(id);
		}
		if (entity == null){
			entity = new ClcoholFirst();
		}
		return entity;
	}
	
	@RequiresPermissions("clcohol:clcoholFirst:view")
	@RequestMapping(value = {"list", ""})
	public String list(ClcoholFirst clcoholFirst, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ClcoholFirst> page = clcoholFirstService.findPage(new Page<ClcoholFirst>(request, response), clcoholFirst); 
		model.addAttribute("page", page);
		return "modules/clcohol/clcoholFirstList";
	}

	@RequiresPermissions("clcohol:clcoholFirst:view")
	@RequestMapping(value = "form")
	public String form(ClcoholFirst clcoholFirst, Model model) {
		
		String registerId;
		if(clcoholFirst.getRegister()!=null){
			registerId = clcoholFirst.getRegister().getId();
		}else{
			registerId = clcoholFirst.getAct().getBusinessId();
		}
		ClcoholRegister clcoholRegister =  clcoholRegisterService.get(registerId);
		SimpleDateFormat simple = new SimpleDateFormat("yyyy");
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
		casecode = casecode.substring(ss, casecode.length());
		int s=  Integer.parseInt(casecode);
		model.addAttribute("casecode", s);
		model.addAttribute("simple", entity.getCasecode().substring(2, 6)); 
		
		Act act=clcoholFirst.getAct();
		ClcoholEvidence clcoholEvidence= clcoholEvidenceService.getRegister(registerId);
		if( clcoholFirstService.findRegister(registerId)!=null){
			clcoholFirst.setId(clcoholFirstService.findRegister(registerId).getId());
			clcoholFirst.setAct(act);
		}
		List<ClcoholEvidenceIteam>clcoholEvidenceIteamList	=clcoholEvidence.getClcoholEvidenceIteamList();
		String	nameA=null;
		String	codeA=null;
		for (ClcoholEvidenceIteam clcoholEvidenceIteam : clcoholEvidenceIteamList) {
			if(!StringUtils.isEmpty(clcoholEvidenceIteam.getCode())){
				codeA   =clcoholEvidenceIteam.getCode();
				nameA	=clcoholEvidenceIteam.getName();
			}
		}
		clcoholFirst.setEntrust(clcoholRegister.getClientname());
		clcoholFirst.setAcceptDate(clcoholRegister.getEntrustdate());
		String	testingMaterials=
				"1、委托书1份\r"+"<br/>"+
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、血样1份（物证密封袋编号："+codeA+")";
		clcoholFirst.setTestingMaterials(testingMaterials);
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		clcoholFirst.setOther(df.format(new Date()));
		String personBeing=nameA;
		
		//送检物证密封袋两份（编号：310107000756），内含具塞酒精检测专用抗凝管各一支,约2毫升，无破损，无渗漏，震荡样品未见凝血块。
		clcoholFirst.setSampleStatus("送检物证密封袋两份（编号："+codeA+"），内含具塞酒精检测专用抗凝管各一支,约2毫升，无破损，无渗漏，震荡样品未见凝血块。");
		
		clcoholFirst.setBasicFacts("经平行双样检测，送检的血样内未检出乙醇（检出限0.01mg/mL）。");
		clcoholFirst.setPersonBeing(personBeing);
		clcoholFirst.setTestResults("送检的被检测人"+nameA+"血样内未检出乙醇。");
		User user=UserUtils.getUser(); 
		model.addAttribute("registerId", registerId);
		model.addAttribute("user", user);
		model.addAttribute("clcoholFirst", clcoholFirst);
		ClcoholAuthorization clcoholAuthorization= clcoholFirstService.findRegisterAu(registerId);
		model.addAttribute("clcoholAuthorization", clcoholAuthorization);
		
	String uploud=clcoholRegister.getOther3();
		
		//物证登记图片
		List<String>pics=new ArrayList<String>();
		List<String>pdfs=new ArrayList<String>();
		List<String>pdf1=new ArrayList<String>();
			if(uploud!=null){
				String []d=uploud.split("\\|");
				for (String pic : d){
					if(pic.equals("")){
						
					}else if(pic.contains(".pdf")||pic.contains(".doc")){
						pdf1.add(pic.substring(pic.lastIndexOf('/')+1, pic.length()));
						pdfs.add(pic);
					}else{
						pics.add(pic);
					}
				}
		 	}
		
			 model.addAttribute("pdf1",pdf1); 
			 model.addAttribute("pdf",pdfs); 
			 model.addAttribute("pic",pics);
		return "modules/clcohol/clcoholFirstForm";
	}

	@RequiresPermissions("clcohol:clcoholFirst:edit")
	@RequestMapping(value = "save")
	public String save(ClcoholFirst clcoholFirst, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, clcoholFirst)){
			return form(clcoholFirst, model);
		}
		clcoholFirstService.save(clcoholFirst);
		addMessage(redirectAttributes, "保存酒精初稿成功");
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	@RequiresPermissions("clcohol:clcoholFirst:edit")    
	@RequestMapping(value = "delete")
	public String delete(ClcoholFirst clcoholFirst, RedirectAttributes redirectAttributes) {
		clcoholFirstService.delete(clcoholFirst);
		addMessage(redirectAttributes, "删除酒精初稿成功");
		return "redirect:"+Global.getAdminPath()+"/clcohol/clcoholFirst/?repage";
	}

}