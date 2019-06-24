/**


 * 
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clinic.web;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.clinic.dao.ClinicAuthoriseDao;
import com.thinkgem.jeesite.modules.clinic.dao.ClinicExaminationDao;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicAuthorise;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicExamination;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicFirstdraft;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicInspection;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicPhysicalIteam;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicRegister;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicRegisterphy;
import com.thinkgem.jeesite.modules.clinic.service.ClinicAuthoriseService;
import com.thinkgem.jeesite.modules.clinic.service.ClinicFirstdraftService;
import com.thinkgem.jeesite.modules.clinic.service.ClinicInspectionService;
import com.thinkgem.jeesite.modules.clinic.service.ClinicRegisterService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 法医鉴定初稿Controller
 * @author fuyn
 * @version 2017-11-26
 */
@Controller
@RequestMapping(value = "${adminPath}/clinic/clinicFirstdraft")
public class ClinicFirstdraftController extends BaseController {

	@Autowired
	private ClinicFirstdraftService clinicFirstdraftService;
	@Autowired
	private ClinicExaminationDao clinicExaminationDao;
	@Autowired
	private ClinicAuthoriseService clinicAuthoriseService;
	@Autowired
	private ClinicRegisterService clinicRegisterService;
	
	@ModelAttribute
	public ClinicFirstdraft get(@RequestParam(required=false) String id) {
		ClinicFirstdraft entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = clinicFirstdraftService.get(id);
		}                                                                                      
		if (entity == null){
			entity = new ClinicFirstdraft();
		}
		return entity;
	}
	
	@RequiresPermissions("clinic:clinicFirstdraft:view")
	@RequestMapping(value = {"list", ""})
	public String list(ClinicFirstdraft clinicFirstdraft, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ClinicFirstdraft> page = clinicFirstdraftService.findPage(new Page<ClinicFirstdraft>(request, response), clinicFirstdraft); 
		model.addAttribute("page", page);
		return "modules/clinic/clinicFirstdraftList";
	}

	@RequiresPermissions("clinic:clinicFirstdraft:view")
	@RequestMapping(value = "form")
	public String form(ClinicFirstdraft clinicFirstdraft, Model model) {
		String registerId;
	Act act=	clinicFirstdraft.getAct();
		if(clinicFirstdraft.getRegister()!=null){
			registerId = clinicFirstdraft.getRegister().getId();
		}else{
			registerId = clinicFirstdraft.getAct().getBusinessId();
		}
		ClinicExamination clinicExamination= clinicFirstdraftService.findExam(registerId);
		
		if( clinicFirstdraftService.findFirst(registerId)!=null){
			clinicFirstdraft=  clinicFirstdraftService.findFirst(registerId);
			clinicFirstdraft.setAct(act);
		}else{
			SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
			clinicFirstdraft.setAppraisalStandard("根据《人体损伤程度鉴定标准》进行鉴定。");
			clinicFirstdraft.setPresencePersonnel("本司法鉴定所工作人员");
			clinicFirstdraft.setCc(clinicExamination.getCc());
			clinicFirstdraft.setReading(clinicExamination.getReading());
			model.addAttribute("date", df.format(new Date()));
		}
		model.addAttribute("clinicFirstdraft", clinicFirstdraft);
		model.addAttribute("registerId", registerId);
		User user=UserUtils.getUser(); 
		model.addAttribute("user", user);
		ClinicRegister	clinicRegister =clinicRegisterService.get(registerId);
		 List<ClinicRegisterphy> clinicRegisterphyList=	clinicRegisterService.findListclinic(clinicRegister);
		model.addAttribute("clinicRegisterphyList", clinicRegisterphyList);
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
		 List<ClinicPhysicalIteam> clinicPhysicalIteams=clinicFirstdraftService.findClinicPhysical(registerId);
			model.addAttribute("clinicPhysicalIteams", clinicPhysicalIteams);
			//物证登记图片
			List<String>pics=new ArrayList<String>();
			List<String>pdfs=new ArrayList<String>();
			List<String>pdf1=new ArrayList<String>();
			for (int i = 0; i < clinicRegisterphyList.size(); i++) {
				if(clinicRegisterphyList.get(i).getUploud()!=null){
					String []d=clinicRegisterphyList.get(i).getUploud().split("\\|");
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
			}	
		//人员验伤图片
			List<String>pics2=new ArrayList<String>();
			List<String>pdfs2=new ArrayList<String>();
			List<String>pdf2=new ArrayList<String>();
		String[] d1=clinicExamination.getUploud().split("\\|");
		for (String pic : d1) {
			if(pic.equals("")){
			}else if(pic.contains(".pdf")||pic.contains(".doc")){
				pdf2.add(pic.substring(pic.lastIndexOf('/')+1, pic.length()));
				pdfs2.add(pic);
			}else{
				pics2.add(pic);
			}
		}
		 ClinicAuthorise clinicAuthorise= clinicAuthoriseService.findRegister(registerId);
		 if(clinicAuthorise!=null){
			 model.addAttribute("clinicAuthorise", clinicAuthorise);
		 } 
			 model.addAttribute("pdf1",pdf1); 
			 model.addAttribute("pdf",pdfs); 
			 model.addAttribute("pic",pics); 
			 model.addAttribute("pdf2",pdf2); 
			 model.addAttribute("pdf2",pdfs2);                                       
			 model.addAttribute("pic2",pics2); 
			 
			 
		return "modules/clinic/clinicFirstdraftForm";
	}


	@RequiresPermissions("clinic:clinicFirstdraft:edit")
	@RequestMapping(value = "save")
	public String save(ClinicFirstdraft clinicFirstdraft, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, clinicFirstdraft)){
			return form(clinicFirstdraft, model);
		}
		clinicFirstdraftService.save(clinicFirstdraft);
		addMessage(redirectAttributes, "保存法医鉴定初稿成功");
		return "redirect:" + adminPath + "/act/task/todo/";
	} 
	
	
	
	//查看一般情况
	@RequestMapping(value="checksituation")
	@ResponseBody
	public Map<String, Object> checksituation(String registerId){
		ClinicExamination clinicExamination=clinicExaminationDao.findone(registerId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", clinicExamination.getSituation());
		return map;
	}
	
	//查看头面部 headFace
	@RequestMapping(value="checkheadFace")
	@ResponseBody
	public Map<String, Object> checkheadFace(String registerId){
		ClinicExamination clinicExamination=clinicExaminationDao.findone(registerId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", clinicExamination.getHeadFace());
		return map;
	}
	
	//查看躯干trunk
	@RequestMapping(value="checktrunk")
	@ResponseBody
	public Map<String, Object> checktrunk(String registerId){
		ClinicExamination clinicExamination=clinicExaminationDao.findone(registerId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", clinicExamination.getTrunk());
		return map;
	}
	
	//查看四肢 limbs
	@RequestMapping(value="checklimbs")
	@ResponseBody
	public Map<String, Object> checklimbs(String registerId){
		ClinicExamination clinicExamination=clinicExaminationDao.findone(registerId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", clinicExamination.getLimbs());
		return map;
	}
	
	//查看其它 other
	@RequestMapping(value="checkother")
	@ResponseBody
	public Map<String, Object> checkother(String registerId){
		ClinicExamination clinicExamination=clinicExaminationDao.findone(registerId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", clinicExamination.getOther());
		return map;
	}
	
	
	//查看阅片 reading
	@RequestMapping(value="checkreading")
	@ResponseBody
	public Map<String, Object> checkreading(String registerId){
		ClinicExamination clinicExamination=clinicExaminationDao.findone(registerId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", clinicExamination.getReading());
		return map;
	}
	
	
	@RequiresPermissions("clinic:clinicFirstdraft:edit")
	@RequestMapping(value = "delete")
	public String delete(ClinicFirstdraft clinicFirstdraft, RedirectAttributes redirectAttributes) {
		clinicFirstdraftService.delete(clinicFirstdraft);
		addMessage(redirectAttributes, "删除法医鉴定初稿成功");
		return "redirect:"+Global.getAdminPath()+"/clinic/clinicFirstdraft/?repage";
	}

	public static void main(String[] args) {
		String d="/jeesite/userfiles/1/files/clinic/clinicPhysical/2017/12/233.pdf";
		System.out.println(d.substring( d.lastIndexOf('/')+1, d.length()));
		
	}
}