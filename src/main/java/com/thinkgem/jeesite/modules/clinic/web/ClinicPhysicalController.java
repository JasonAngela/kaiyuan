/**
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clinic.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
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
import com.thinkgem.jeesite.modules.clinic.dao.ClinicPhysicalDao;
import com.thinkgem.jeesite.modules.clinic.dao.ClinicPhysicalIteamDao;
import com.thinkgem.jeesite.modules.clinic.dao.ClinicRegisterphyDao;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicPhysical;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicPhysicalIteam;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicRegister;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicRegisterphy;
import com.thinkgem.jeesite.modules.clinic.service.ClinicPhysicalService;
import com.thinkgem.jeesite.modules.clinic.service.ClinicRegisterService;
import com.thinkgem.jeesite.modules.sys.service.SysCodeRuleService;

/**
 * 文件上传Controller
 * @author fuyun
 * @version 2017-11-26
 */
@Controller
@RequestMapping(value = "${adminPath}/clinic/clinicPhysical")
public class ClinicPhysicalController extends BaseController {

	@Autowired
	private ClinicPhysicalService clinicPhysicalService;
	@Autowired
	private ClinicPhysicalIteamDao clinicPhysicalIteamdao;
	@Autowired
	private ClinicPhysicalDao clinicPhysicalDao;
	@Autowired
	private ClinicRegisterService clinicRegisterService;
	@Autowired
	private ClinicRegisterphyDao clinicRegisterphyDao;
	@Autowired
	private SysCodeRuleService codeRuleService;
	
	@ModelAttribute
	public ClinicPhysical get(@RequestParam(required=false) String id) {
		ClinicPhysical entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = clinicPhysicalService.get(id);
		}
		if (entity == null){
			entity = new ClinicPhysical();
		}
		return entity;
	}
	
	@RequiresPermissions("clinic:clinicPhysical:view")
	@RequestMapping(value = {"list", ""})
	public String list(ClinicPhysical clinicPhysical, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ClinicPhysical> page = clinicPhysicalService.findPage(new Page<ClinicPhysical>(request, response), clinicPhysical); 
		model.addAttribute("page", page);
		return "modules/clinic/clinicPhysicalList";
	}

	@RequiresPermissions("clinic:clinicPhysical:view")
	@RequestMapping(value = "form")
	public String form(ClinicPhysical clinicPhysical, Model model) {
		List<ClinicPhysicalIteam>clinicPhysicalIteam =new ArrayList<ClinicPhysicalIteam>();
		model.addAttribute("clinicPhysical", clinicPhysical);
		String registerId;
		if(clinicPhysical.getRegister()!=null){
			registerId = clinicPhysical.getRegister().getId();
		}else{
			registerId = clinicPhysical.getAct().getBusinessId();                               
		}
		ClinicRegister clinicRegister = clinicRegisterService.get(registerId);
		String casecode=  clinicRegister.getCaseCode().substring(11, 16);
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
		
		
		  model.addAttribute("simple", clinicRegister.getCaseCode().substring(2, 6));                           
			clinicRegisterphyDao.findList(new ClinicRegisterphy(clinicRegister));
		List<ClinicRegisterphy>clinicRegisterphyList=	clinicRegisterphyDao.findList(new ClinicRegisterphy(clinicRegister));
			for (ClinicRegisterphy clinicRegisterphy : clinicRegisterphyList) {
				ClinicPhysicalIteam iteam=new ClinicPhysicalIteam();
				String code=codeRuleService.generateCode("clinic_fymrCode");
				iteam.setCode(code);
				iteam.setName(clinicRegisterphy.getName());
				iteam.setQuantity(clinicRegisterphy.getQuantity());
				iteam.setType(clinicRegisterphy.getType());
				iteam.setRemarks(clinicRegisterphy.getRemarks());
				clinicPhysicalIteam.add(iteam);
			}
			
		   model.addAttribute("clinicPhysicalIteam",clinicPhysicalIteam); 
			clinicPhysical.setClinicPhysicalIteamList(clinicPhysicalIteam);
			model.addAttribute("clinicPhysical", clinicPhysical);
			model.addAttribute("registerId", registerId);
			return "modules/clinic/clinicPhysicalForm";
		}
	
	@RequiresPermissions("clinic:clinicPhysical:view")
	@RequestMapping(value = "form1")
	public String form1( Model model,String registerId) {
		ClinicPhysical	clinicPhysical=clinicPhysicalDao.findOne(registerId);
		List<ClinicPhysicalIteam>clinicPhysicalIteam =clinicPhysicalIteamdao.findone(clinicPhysical.getId());
		clinicPhysical.setClinicPhysicalIteamList(clinicPhysicalIteam);
		model.addAttribute("clinicPhysical", clinicPhysical);
		if(clinicPhysical.getRegister()!=null){
			registerId = clinicPhysical.getRegister().getId();
		}else{
			registerId = clinicPhysical.getAct().getBusinessId();
		}
		model.addAttribute("clinicPhysicalIteam", clinicPhysicalIteam);
		model.addAttribute("registerId", registerId);
		return "modules/clinic/clinicPhysicalForm2";
	}
	

	@RequiresPermissions("clinic:clinicPhysical:edit")
	@RequestMapping(value = "save")
	public String save(String id,ClinicPhysical clinicPhysical, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception {
		if (!beanValidator(model, clinicPhysical)){
			return form(clinicPhysical, model);
		}
		clinicPhysicalService.save(clinicPhysical,request);
		addMessage(redirectAttributes, "保存文件上传成功");
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	@RequiresPermissions("clinic:clinicPhysical:edit")
	@RequestMapping(value = "nextone")
	public String nextone(String registerId, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception {
		clinicPhysicalService.nextOne(registerId);
		addMessage(redirectAttributes, "到达下一个节点");
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	@RequiresPermissions("clinic:clinicPhysical:edit")
	@RequestMapping(value = "delete")
	public String delete(ClinicPhysical clinicPhysical, RedirectAttributes redirectAttributes) {
		clinicPhysicalService.delete(clinicPhysical);
		addMessage(redirectAttributes, "删除文件上传成功");
		return "redirect:"+Global.getAdminPath()+"/clinic/clinicPhysical/?repage";
	}
}