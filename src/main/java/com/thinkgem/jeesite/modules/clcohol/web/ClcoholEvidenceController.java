/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clcohol.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.thinkgem.jeesite.modules.clcohol.service.ClcoholEvidenceService;
import com.thinkgem.jeesite.modules.clcohol.service.ClcoholRegisterService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SysCodeRuleService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 酒精物证Controller
 * @author fuyun
 * @version 2018-01-23
 */
@Controller
@RequestMapping(value = "${adminPath}/clcohol/clcoholEvidence")
public class ClcoholEvidenceController extends BaseController {

	@Autowired
	private ClcoholEvidenceService clcoholEvidenceService;
	@Autowired
	private ClcoholRegisterService clcoholRegisterService;
	@Autowired
	private SysCodeRuleService codeRuleService;
	
	
	@ModelAttribute
	public ClcoholEvidence get(@RequestParam(required=false) String id) {
		ClcoholEvidence entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = clcoholEvidenceService.get(id);
		}
		if (entity == null){
			entity = new ClcoholEvidence();
		}
		return entity;
	}
	
	@RequiresPermissions("clcohol:clcoholEvidence:view")
	@RequestMapping(value = {"list", ""})
	public String list(ClcoholEvidence clcoholEvidence, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ClcoholEvidence> page = clcoholEvidenceService.findPage(new Page<ClcoholEvidence>(request, response), clcoholEvidence); 
		model.addAttribute("page", page);
		return "modules/clcohol/clcoholEvidenceList";
	}

	@RequiresPermissions("clcohol:clcoholEvidence:view")
	@RequestMapping(value = "form")
	public String form(ClcoholEvidence clcoholEvidence, Model model){
		String registerId;
		boolean isNew =  clcoholEvidence.getIsNewRecord();
		if(clcoholEvidence.getResgister()!=null){
			registerId = clcoholEvidence.getResgister().getId();
		}else{
			registerId = clcoholEvidence.getAct().getBusinessId();
		}
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
		//酒精材料登记标号
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		if(isNew){
			String code=codeRuleService.generateCode("clinic_fymrCode");
			clcoholEvidence.setCode(code);
			List<ClcoholEvidenceIteam>clcoholEvidenceIteams=new ArrayList<ClcoholEvidenceIteam>();
			ClcoholEvidenceIteam clcoholEvidenceIteam=new ClcoholEvidenceIteam();
			clcoholEvidenceIteam.setCode(code+"-A");
			clcoholEvidenceIteams.add(clcoholEvidenceIteam);
			clcoholEvidence.setClcoholEvidenceIteamList(clcoholEvidenceIteams);
			model.addAttribute("entrustDate", /*df.format(new Date())*/  entity.getEntrustdate());
		}
		clcoholEvidence.setEntrust(entity.getClientname());
		model.addAttribute("registerId", registerId);
		model.addAttribute("clcoholEvidence", clcoholEvidence);
		//法医物证code
		clcoholEvidence.setMiningdate(df.format(new Date()));
		User user=UserUtils.getUser(); 
		model.addAttribute("user", user);
		return "modules/clcohol/clcoholEvidenceForm";
	}
	

	@RequiresPermissions("clcohol:clcoholEvidence:edit")
	@RequestMapping(value = "save")
	public String save(ClcoholEvidence clcoholEvidence, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, clcoholEvidence)){
			return form(clcoholEvidence, model);
		}
		ClcoholRegister	entity = clcoholRegisterService.get(clcoholEvidence.getResgister().getId());
		String casecode= entity.getCasecode().substring(10, 15);
		SimpleDateFormat simple = new SimpleDateFormat("yyyy");   
		clcoholEvidence.setCasecode(casecode);
		clcoholEvidenceService.save(clcoholEvidence,entity);
		addMessage(redirectAttributes, "保存酒精物证成功");
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	@RequiresPermissions("clcohol:clcoholEvidence:edit")
	@RequestMapping(value = "delete")
	public String delete(ClcoholEvidence clcoholEvidence, RedirectAttributes redirectAttributes) {
		clcoholEvidenceService.delete(clcoholEvidence);
		addMessage(redirectAttributes, "删除酒精物证成功");
		return "redirect:"+Global.getAdminPath()+"/clcohol/clcoholEvidence/?repage";
	}
	
	@RequestMapping(value = "downLoad")
	public void downLoadFile(HttpServletRequest request,HttpServletResponse response) {
	   
		Map<String, Object> beanParams = new HashMap<String, Object>();
		
		
		
	}
	
	

}