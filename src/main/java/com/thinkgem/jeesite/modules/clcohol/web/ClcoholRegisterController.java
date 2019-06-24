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
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholEvidence;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholEvidenceIteam;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholFirst;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholPapers;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholRegister;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholSamples;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholSamplesIteam;
import com.thinkgem.jeesite.modules.clcohol.entity.ClcoholWritten;
import com.thinkgem.jeesite.modules.clcohol.service.ClcoholEvidenceService;
import com.thinkgem.jeesite.modules.clcohol.service.ClcoholFirstService;
import com.thinkgem.jeesite.modules.clcohol.service.ClcoholPapersService;
import com.thinkgem.jeesite.modules.clcohol.service.ClcoholRegisterService;
import com.thinkgem.jeesite.modules.clcohol.service.ClcoholSamplesService;
import com.thinkgem.jeesite.modules.clcohol.service.ClcoholWrittenService;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicExamination;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicPapers;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicPhysical;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicPhysicalIteam;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicRegister;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 酒精委托书Controller
 * @author fuyun
 * @version 2018-01-23
 */
@Controller
@RequestMapping(value = "${adminPath}/clcohol/clcoholRegister")
public class ClcoholRegisterController extends BaseController {

	@Autowired
	private ClcoholRegisterService clcoholRegisterService;
	@Autowired
	private ClcoholEvidenceService clcoholEvidenceService;
	 @Autowired
	 private ClcoholSamplesService clcoholSamplesService;
	 @Autowired
	 private ClcoholPapersService clcoholPapersService;
	 @Autowired
	 private ClcoholFirstService clcoholFirstService;
	 @Autowired
	 private ClcoholWrittenService clcoholWrittenService;
	   
	@ModelAttribute
	public ClcoholRegister get(@RequestParam(required=false) String id) {
		ClcoholRegister entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = clcoholRegisterService.get(id);
		}
		if (entity == null){
			entity = new ClcoholRegister();
		}
		return entity;
	}
	
	@RequiresPermissions("clcohol:clcoholRegister:view")
	@RequestMapping(value = {"list", ""})
	public String list(ClcoholRegister clcoholRegister, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ClcoholRegister> page = clcoholRegisterService.findPage(new Page<ClcoholRegister>(request, response), clcoholRegister); 
		model.addAttribute("page", page);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
		return "modules/clcohol/clcoholRegisterList";
	}
	
	
	//归档 archive
	@RequiresPermissions("clcohol:clcoholRegister:view")
	@RequestMapping(value = {"archive"})
	public String archive(ClcoholRegister clcoholRegiste, Model model) {
		model.addAttribute("ClcoholRegister",clcoholRegiste);
		//材料
		String attorneyPath="毒物/"+clcoholRegiste.getCode()+"/"+ "examination of forensic.pdf";;
    	model.addAttribute("attorneyPath", attorneyPath);
    	//报告
    	String reportPath="毒物/"+clcoholRegiste.getCode()+"/"+ "clcohol of report.pdf";;
    	model.addAttribute("reportPath", reportPath);
    	
		
		return "modules/clcohol/clcoholArchive";
	}
	
	
	
	
	
	//鉴定委托书
	@RequiresPermissions("clcohol:clcoholRegister:view")
	@RequestMapping(value = "details")
	public String details(ClcoholRegister clcoholRegiste, Model model) {
		
		String casecode= clcoholRegiste.getCasecode().substring(10, 15);
		SimpleDateFormat simple = new SimpleDateFormat("yyyy");                                       
		model.addAttribute("casecode", casecode);
		model.addAttribute("simple", simple.format( new Date()));
		model.addAttribute("clcoholRegiste", clcoholRegiste);
		return "modules/clcohol/clcoholRegisterDetails";
	}
	
	
	//正稿
	@RequestMapping(value = "report")
	public String report(ClcoholRegister clcoholRegiste, Model model) {
		ClcoholFirst	clcoholFirst = clcoholFirstService.findRegister(clcoholRegiste.getId());
		model.addAttribute("clcoholFirst", clcoholFirst);
		ClcoholWritten clcoholWritten=	clcoholWrittenService.getRegister(clcoholRegiste.getId());
		model.addAttribute("clcoholWritten", clcoholWritten);
		String casecode= clcoholRegiste.getCasecode().substring(10, 15);
		SimpleDateFormat simple = new SimpleDateFormat("yyyy");                                       
		model.addAttribute("casecode", casecode);
		model.addAttribute("simple", simple.format( new Date()));
		model.addAttribute("clcoholRegiste", clcoholRegiste);
		return "modules/clcohol/clcoholReport";
	}
	
	
	//材料登记
		@RequestMapping(value ={"mater"})
		public String mater(ClcoholRegister clcoholRegiste, Model model){
			
			ClcoholEvidence clcoholEvidence=clcoholEvidenceService.getRegister(clcoholRegiste.getId());
			List<ClcoholEvidenceIteam> clcoholEvidenceIteams=clcoholEvidence.getClcoholEvidenceIteamList();
		for (ClcoholEvidenceIteam clcoholEvidenceIteam : clcoholEvidenceIteams) {
			if(clcoholEvidenceIteam.getCode().contains("A")){
				model.addAttribute("codeA", clcoholEvidenceIteam.getCode());
				model.addAttribute("nameA", clcoholEvidenceIteam.getName());
				model.addAttribute("idnumberA", clcoholEvidenceIteam.getIdnumber());
			}
		}
			
			model.addAttribute("clcoholEvidence", clcoholEvidence);
			model.addAttribute("casecode", clcoholRegiste.getCasecode().substring(10, 15));
			SimpleDateFormat simple = new SimpleDateFormat("yyyy");                                       
			model.addAttribute("simple", simple.format(new Date()));
			return "modules/clcohol/clcoholsicalMater";
		}
		//物证领取
		@RequestMapping(value ={"sheeet"})
		public String sheeet(ClcoholRegister clcoholRegiste, Model model) {
			
			String casecode= clcoholRegiste.getCasecode().substring(10, 15);
			SimpleDateFormat simple = new SimpleDateFormat("yyyy");                                       
			model.addAttribute("casecode", casecode);
			model.addAttribute("simple", simple.format( new Date()));
			ClcoholSamples	clcoholSamples	=clcoholSamplesService.getRegister(clcoholRegiste.getId());
			List<ClcoholSamplesIteam> clcoholSamplesIteamList=clcoholSamples.getClcoholSamplesIteamList();
		for (ClcoholSamplesIteam clcoholSamplesIteamLists : clcoholSamplesIteamList) {
			if(clcoholSamplesIteamLists.getOther().contains("A")){
				model.addAttribute("codeA", clcoholSamplesIteamLists.getOther());
				model.addAttribute("nameA", clcoholSamplesIteamLists.getName());
				model.addAttribute("idnumberA", clcoholSamplesIteamLists.getIdnumber());
			}
		}
			
			return "modules/clcohol/clcoholExaminationSheet";
		}
		
		//实验记录底稿
		@RequestMapping(value = "papers")
		public String papers(ClcoholRegister clcoholRegister, Model model,HttpServletRequest request, HttpServletResponse response) {
			ClcoholPapers clcoholPapers=new ClcoholPapers();
			clcoholPapers.setRegister(clcoholRegister);
			Page<ClcoholPapers> page =clcoholPapersService.findPage(new Page<ClcoholPapers>(request, response), clcoholPapers); 
			model.addAttribute("page", page);
			return "modules/clcohol/clcoholPapersList";
		}
	
	@RequiresPermissions("clcohol:clcoholRegister:view")
	@RequestMapping(value = "form")
	public String form(ClcoholRegister clcoholRegister, Model model) {
		
	        String casecode= clcoholRegister.getCasecode().substring(15, 20);
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
	    model.addAttribute("simple", clcoholRegister.getCasecode().substring(7, 11));
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		User user=UserUtils.getUser(); 
		model.addAttribute("user", user);
		model.addAttribute("entrustDate", df.format(new Date()));
		
		
		model.addAttribute("clcoholRegister", clcoholRegister);
		return "modules/clcohol/clcoholRegisterForm";
	}

	
	@RequiresPermissions("clcohol:clcoholRegister:view")
	@RequestMapping(value = "form1")
	public String form1(ClcoholRegister clcoholRegister, Model model, HttpServletRequest request, HttpServletResponse response) {
		Page<ClcoholRegister> page = clcoholRegisterService.findPage(new Page<ClcoholRegister>(request, response), clcoholRegister); 
		  
        if(page.getList().size()==0){
        	int s= 00001;
        	model.addAttribute("casecode", s);
        }else{
        	String casecode=  page.getList().get(0).getCasecode().substring(10, 15);
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
        	int s=  Integer.parseInt(casecode)+1;
        	model.addAttribute("casecode", s);
        }
     
         SimpleDateFormat simple = new SimpleDateFormat("yyyy");
         model.addAttribute("simple", simple.format(new Date()));
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		User user=UserUtils.getUser(); 
		model.addAttribute("user", user);
		model.addAttribute("entrustDate", df.format(new Date()));
		model.addAttribute("clcoholRegister", clcoholRegister);
		return "modules/clcohol/clcoholRegisterForm";
	}
	
	
	
	@RequiresPermissions("clcohol:clcoholRegister:edit")
	@RequestMapping(value = "save")
	public String save(ClcoholRegister clcoholRegister, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception {
		if (!beanValidator(model, clcoholRegister)){
			return form(clcoholRegister, model);
		}
		clcoholRegisterService.save(clcoholRegister, request);
		addMessage(redirectAttributes, "保存酒精委托书成功");
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	@RequiresPermissions("clcohol:clcoholRegister:edit")
	@RequestMapping(value = "delete")
	public String delete(ClcoholRegister clcoholRegister, RedirectAttributes redirectAttributes) {
		clcoholRegisterService.delete(clcoholRegister);
		addMessage(redirectAttributes, "删除酒精委托书成功");
		return "redirect:"+Global.getAdminPath()+"/clcohol/clcoholRegister/?repage";
	}

	
	public static void main(String[] args) {
		System.out.println("alcohol2018110900005".substring(7, 11));
	}
}