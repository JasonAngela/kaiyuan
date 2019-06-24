/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entrust.web;

import java.io.File;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.filter.PhotoUploud;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicExamination;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustCourier;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustRegister;
import com.thinkgem.jeesite.modules.entrust.entity.Start;
import com.thinkgem.jeesite.modules.entrust.service.EntrustCourierService;
import com.thinkgem.jeesite.modules.entrust.service.EntrustRegisterService;

/**
 * dna快递Controller
 * @author fuyun
 * @version 2018-01-12
 */
@Controller
@RequestMapping(value = "${adminPath}/entrust/entrustCourier")
public class EntrustCourierController extends BaseController {

	@Autowired
	private EntrustCourierService entrustCourierService;
	@Autowired
	private EntrustRegisterService entrustRegisterService;
	
	@ModelAttribute
	public EntrustCourier get(@RequestParam(required=false) String id) {
		EntrustCourier entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = entrustCourierService.get(id);
		}
		if (entity == null){
			entity = new EntrustCourier();
		}
		return entity;
	}
	
	@RequiresPermissions("entrust:entrustCourier:view")
	@RequestMapping(value = {"list", ""})
	public String list(EntrustCourier entrustCourier, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<EntrustCourier> page = entrustCourierService.findPage(new Page<EntrustCourier>(request, response), entrustCourier); 
		model.addAttribute("page", page);
		return "modules/entrust/entrustCourierList";
	}

	@RequiresPermissions("entrust:entrustCourier:view")
	@RequestMapping(value = "form")
	public String form(EntrustCourier entrustCourier, Model model) {
		
		model.addAttribute("entrustCourier", entrustCourier);
		return "modules/entrust/entrustCourierForm";
	}
	
	@RequestMapping(value="update")
	@ResponseBody
	public EntrustCourier update(String entrustId){
		EntrustCourier entrustCourier=entrustCourierService.findEntrust(entrustId);
		if(entrustCourier!=null){
			return entrustCourier;
		}else{
			EntrustCourier entrustCourier1=new EntrustCourier();
			EntrustRegister entrustRegister= entrustRegisterService.getId(entrustId);	
			entrustCourier1.setOther("顺丰快递");
			entrustCourier1.setSender(entrustRegister.getClientName());
			entrustCourier1.setOther1(entrustRegister.getClientTel());
			entrustCourier1.setEntrustId(entrustId);
			return entrustCourier1; 
		}
	}
	
	@RequestMapping(value="getID")
	@ResponseBody
	public HashMap<String,String> getID(String imagePath,HttpServletRequest request ) throws Exception{
		String toName = "D:" + File.separator + "information" + File.separator + "DNA" + File.separator+ "shibie" + File.separator;
		String toName1 = "D:" + File.separator + "information" + File.separator + "DNA" + File.separator+ "shibie" ;
        PhotoUploud photoUploud = new PhotoUploud();
        photoUploud.UploudPhoto(imagePath, request, toName);
        imagePath=toName1+  imagePath.substring(imagePath.lastIndexOf("/"),imagePath.length());
        Start start=new Start();
		return	start.getMap(imagePath);
	}
	

	@RequestMapping(value = "save")
	public String save(EntrustCourier entrustCourier, Model model, RedirectAttributes redirectAttributes) {
		entrustCourierService.save(entrustCourier);
		addMessage(redirectAttributes, "保存dna快递成功");
		return "redirect:"+Global.getAdminPath()+"/entrust/entrustRegister/?repage";
	}
	
	@RequiresPermissions("entrust:entrustCourier:edit")
	@RequestMapping(value = "delete")
	public String delete(EntrustCourier entrustCourier, RedirectAttributes redirectAttributes) {
		entrustCourierService.delete(entrustCourier);
		addMessage(redirectAttributes, "删除dna快递成功");
		return "redirect:"+Global.getAdminPath()+"/entrust/entrustCourier/?repage";
	}

}