/**

 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clinic.web;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.DictService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
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
import com.thinkgem.jeesite.modules.clinic.dao.ClinicExaminationDao;
import com.thinkgem.jeesite.modules.clinic.dao.ClinicPhysicalDao;
import com.thinkgem.jeesite.modules.clinic.dao.ClinicPhysicalIteamDao;
import com.thinkgem.jeesite.modules.clinic.dao.ClinicRegisterphyDao;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicExamination;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicPapers;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicPhysical;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicPhysicalIteam;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicRegister;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicRegisterphy;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicWritten;
import com.thinkgem.jeesite.modules.clinic.service.ClinicPapersService;
import com.thinkgem.jeesite.modules.clinic.service.ClinicRegisterService;
import com.thinkgem.jeesite.modules.clinic.service.ClinicWrittenService;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustModifyrecord;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustRegister;
import com.thinkgem.jeesite.modules.entrust.entity.Word2007ToHtml;


/**
 * 临床登记Controller
 * @author zhuguli
 * @version 2017-10-15
 */
@Controller
@RequestMapping(value = "${adminPath}/clinic/clinicRegister")
public class ClinicRegisterController extends BaseController {
	@Autowired
	private ClinicRegisterService clinicRegisterService;
	@Autowired
	private ClinicPhysicalDao clinicPhysicalDao;
	@Autowired
	private ClinicPhysicalIteamDao clinicPhysicalIteamdao;
	@Autowired
	private ClinicExaminationDao clinicExaminationDao;
	@Autowired
	private ClinicPapersService clinicPapersService;
	@Autowired
	private ClinicWrittenService clinicWrittenService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ClinicRegisterphyDao clinicRegisterphyDao;
	@Autowired
	private DictService dictService;
	
	@ModelAttribute
	public ClinicRegister get(@RequestParam(required=false) String id) {
		ClinicRegister clinicRegister = null;
		if (StringUtils.isNotBlank(id)){
			clinicRegister = clinicRegisterService.get(id);
			clinicRegister.setClinicRegisterphyList(clinicRegisterphyDao.findList(new ClinicRegisterphy(clinicRegister)));
		}
		if (clinicRegister == null){
			clinicRegister = new ClinicRegister();
		}
		return clinicRegister;
	}
	
	@RequiresPermissions("clinic:clinicRegister:view")
	@RequestMapping(value = {"list", ""})
	public String list(ClinicRegister clinicRegister, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			clinicRegister.setCreateBy(user);
		}
		Page<ClinicRegister> page = clinicRegisterService.findPage(new Page<ClinicRegister>(request, response), clinicRegister); 
		model.addAttribute("page", page);
		return "modules/clinic/clinicRegisterList";
	}
	
	
	//归档 archive
	@RequiresPermissions("clinic:clinicRegister:view")
	@RequestMapping(value = {"archive"})
	public String archive(ClinicRegister clinicRegister, Model model) {
		//委托书pdf展示
    	String attorneyPath="法医临床病理/"+clinicRegister.getCode()+"/"+ "power of attorney" + ".pdf";;
    	model.addAttribute("attorneyPath", attorneyPath);
    	//验伤pdf
    	
    	String examinationPath="法医临床病理/"+clinicRegister.getCode()+"/"+ "examination of forensic.pdf";;
    	model.addAttribute("examinationPath", examinationPath);
    	
    	//报告pdf
    	String reportPath="法医临床病理/"+clinicRegister.getCode()+"/"+ "report of forensic.pdf";
    	model.addAttribute("reportPath", reportPath);
		
		model.addAttribute("clinicRegister",clinicRegister);
		return "modules/clinic/clinicRegisterArchive";
	}
	
	//
	//法医鉴定委托书
	@RequiresPermissions("clinic:clinicRegister:view")
	@RequestMapping(value = "details")
	public String details(ClinicRegister clinicRegister, Model model) {
		if(StringUtils.isEmpty(clinicRegister.getSurveyorName())){
			clinicRegister.setSurveyorName("________");
		}
		if(StringUtils.isEmpty(clinicRegister.getSurveyorSex())){
			clinicRegister.setSurveyorSex("________");
		}if(StringUtils.isEmpty(clinicRegister.getSurveyorBirthday())){
			clinicRegister.setSurveyorBirthday("________");
		}
		if(StringUtils.isEmpty(clinicRegister.getClientReceiver())){
			clinicRegister.setClientReceiver("________");
		}if(StringUtils.isEmpty(clinicRegister.getClinicTriage())){
			clinicRegister.setClinicTriage("________");
		}if(StringUtils.isEmpty(clinicRegister.getClinicMedical())){
			clinicRegister.setClinicMedical("________");
		}if(StringUtils.isEmpty(clinicRegister.getClinicSummary())){
			clinicRegister.setClinicSummary("________");
		}if(StringUtils.isEmpty(clinicRegister.getClinicXray())){
			clinicRegister.setClinicXray("________");
		}if(StringUtils.isEmpty(clinicRegister.getClinicCt())){
			clinicRegister.setClinicCt("________");
		}if(StringUtils.isEmpty(clinicRegister.getClinicMri())){
			clinicRegister.setClinicMri("________");
		}if(StringUtils.isEmpty(clinicRegister.getClinicMri())){
			clinicRegister.setClinicMri("________");
		}if(StringUtils.isEmpty(clinicRegister.getAppraisalItem())){
			clinicRegister.setAppraisalItem("________");
		}if(StringUtils.isEmpty(clinicRegister.getMattersEntrusted())){
			clinicRegister.setMattersEntrusted("________");
		}if(StringUtils.isEmpty(clinicRegister.getClientAddress())){
			clinicRegister.setClientAddress("________");
		}if(StringUtils.isEmpty(clinicRegister.getClientFax())){
			clinicRegister.setClientFax("________");
		}if(StringUtils.isEmpty(clinicRegister.getMaterialDispose())){
			clinicRegister.setMaterialDispose("________");
		}if(StringUtils.isEmpty(clinicRegister.getTimeLimitReport())){
			clinicRegister.setTimeLimitReport("________");
		}if(StringUtils.isEmpty(clinicRegister.getSpecialty())){
			clinicRegister.setSpecialty("________");
		}if(clinicRegister.getStandardFee()==null){
			clinicRegister.setStandardFee(0.0000);
		}if(clinicRegister.getSpecialFee()==null){
			clinicRegister.setSpecialFee(0.0000);
		}if(clinicRegister.getTotalFee()==null){
			clinicRegister.setTotalFee(0.0000);
		}
		//HT2018010300017
		String casecode= clinicRegister.getCaseCode().substring(10, 15);
		SimpleDateFormat simple = new SimpleDateFormat("yyyy");                                       
		model.addAttribute("casecode", casecode);
		model.addAttribute("simple", simple.format( new Date()));
		model.addAttribute("clinicRegister", clinicRegister);
		return "modules/clinic/clinicRegisterDetails";
	}
	 	
	//材料登记
	@RequestMapping(value ={"mater"})
	public String mater(ClinicRegister clinicRegister, Model model){
		ClinicPhysical clinicPhysical=	clinicPhysicalDao.findOne(clinicRegister.getId());
		List<ClinicPhysicalIteam>clinicPhysicalIteam =clinicPhysicalIteamdao.findone(clinicPhysical.getId());
		clinicPhysical.setClinicPhysicalIteamList(clinicPhysicalIteam);
	    model.addAttribute("clinicPhysicalIteam",clinicPhysicalIteam); 
		clinicPhysical.setClinicPhysicalIteamList(clinicPhysicalIteam);
		model.addAttribute("casecode", clinicRegister.getCaseCode().substring(10, 15));
		model.addAttribute("clinicPhysical", clinicPhysical);
		model.addAttribute("clinicPhysical", clinicPhysical);
		SimpleDateFormat simple = new SimpleDateFormat("yyyy");                                       
		model.addAttribute("simple", simple.format(new Date()));
		return "modules/clinic/clinicPhysicalMater";
	}
	
	//人员验伤
	@RequestMapping(value ={"sheeet"})
	public String sheeet(ClinicRegister clinicRegister, Model model) {
		String casecode= clinicRegister.getCaseCode().substring(10, 15);
		SimpleDateFormat simple = new SimpleDateFormat("yyyy");                                       
		model.addAttribute("casecode", casecode);
		model.addAttribute("simple", simple.format( new Date()));
		ClinicExamination clinicExamination=clinicExaminationDao.findone(clinicRegister.getId());
		model.addAttribute("clinicExamination", clinicExamination);
		return "modules/clinic/clinicExaminationSheet";
	}
	
	//人员验伤登记材料
		@RequestMapping(value ={"photo"})
		public String photo(ClinicRegister clinicRegister, Model model) throws Exception {
			ClinicExamination clinicExamination=clinicExaminationDao.findone(clinicRegister.getId());
			//人员验伤图片
			List<String>pics2=new ArrayList<String>();
			List<String>pdfs2=new ArrayList<String>();
			List<String>pdf2=new ArrayList<String>();
			if(!StringUtils.isEmpty(clinicExamination.getUploud())){
			
		String[] d1=clinicExamination.getUploud().split("\\|");
		for (String pic : d1) {
			 pic = URLDecoder.decode(pic,"UTF-8");
			if(pic.equals("")){
			}else if(pic.contains(".pdf")||pic.contains(".doc")){
				pdf2.add(pic.substring(pic.lastIndexOf('/')+1, pic.length()));
				pdfs2.add(pic);
			}else{
				pics2.add(pic);
			}
		}
			}	
			model.addAttribute("clinicExamination", clinicExamination);
			model.addAttribute("pdf2",pdf2); 
			model.addAttribute("pdf2",pdfs2); 
			model.addAttribute("pic2",pics2); 
			return "modules/clinic/clinicExaminationPhoto";
		}
	
	
	
	@RequiresPermissions("clinic:clinicRegister:view")
	@RequestMapping(value = "form")
	public String form(ClinicRegister clinicRegister, Model model) throws Exception {
		model.addAttribute("clinicRegister", clinicRegister);
		  String casecode= clinicRegister.getCaseCode().substring(11, 16);
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
	    model.addAttribute("casecode", casecode);
	    model.addAttribute("simple", clinicRegister.getCaseCode().substring(2, 6));
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		User user=UserUtils.getUser(); 
		model.addAttribute("user", user);
	String 	clientZipcode=null;
		 if(clinicRegister.getIsNewRecord()){
			 clientZipcode=df.format(new Date());
	        }else{
	        	clientZipcode=clinicRegister.getClientZipcode();
	        }
		 
		model.addAttribute("clientZipcode", clientZipcode);
		 
		 //司法鉴定许可证复印件
		 File fileList=new File( "D:" + File.separator + "information" + File.separator + "office" + File.separator+"jd of dna" + ".pdf");
	 	if(fileList.exists()){
	 		}else{
	 		String jdPath = "D:" + File.separator + "information" + File.separator + "office" + File.separator;
			File mFile = new File(jdPath);
			String d=null;
			if (mFile.exists() && mFile.isDirectory()) {
	       		List<File> mlist = new ArrayList<File>();
	       		getAllFile(mFile, mlist);
	       		// 已经获取了所有图片
			for (File file2 : mlist){
				 d=file2.getAbsolutePath();
				d = d.replace('\\', '/');
				d = URLDecoder.decode(d, "utf-8");  
				break;
					}
				} 
			Word2007ToHtml.imgToPdf(d, jdPath+"jd of dna.pdf");
	 		}		
	 		model.addAttribute("jdPath","office\\"+"jd of dna.pdf");
		return "modules/clinic/clinicRegisterForm";
	}
	
	@RequiresPermissions("clinic:clinicRegister:view")
	@RequestMapping(value = "form1")
	public String form1(ClinicRegister clinicRegister, Model model,String accpet,HttpServletRequest request, HttpServletResponse response) {
		Page<ClinicRegister> page = clinicRegisterService.findPage(new Page<ClinicRegister>(request, response), clinicRegister); 
		if(page.getList().size()==0){
			int s=00001;
			model.addAttribute("casecode", s);
		}else{
			//HT2018070400031
			String casecode=  page.getList().get(0).getCaseCode().substring(11, 16);
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
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy");
		model.addAttribute("simple", df1.format(new Date()));         
		if(!StringUtils.isEmpty(accpet)){
			clinicRegister.setType(accpet);
		}
		model.addAttribute("clinicRegister", clinicRegister);
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		User user=UserUtils.getUser(); 
		model.addAttribute("user", user);
		model.addAttribute("clientZipcode", df.format(new Date()));
		return "modules/clinic/clinicRegisterForm";
	}
	
	
	//正稿report
	@RequestMapping(value = "report")
	public String report(ClinicRegister clinicRegister,Model model) {
		ClinicWritten clinicWritten= clinicWrittenService.findRegister(clinicRegister.getId());
		model.addAttribute("clinicWritten", clinicWritten);
		SimpleDateFormat simple = new SimpleDateFormat("yyyy");                                       
		model.addAttribute("simple", simple.format(new Date()));
		model.addAttribute("casecode", clinicRegister.getCaseCode().substring(10, 15));
		return "modules/clinic/clinicReport";
	}
	
	
	//实验记录底稿
		@RequestMapping(value = "papers")
		public String papers(ClinicRegister clinicRegister, Model model,HttpServletRequest request, HttpServletResponse response) {
			ClinicPapers clinicPapers=new ClinicPapers();
			clinicPapers.setRegister(clinicRegister);
			Page<ClinicPapers> page = clinicPapersService.findPage(new Page<ClinicPapers>(request, response), clinicPapers); 
			model.addAttribute("page", page);
			return "modules/clinic/clinicPapersList";
		}
	
		
		
	@RequiresPermissions("clinic:clinicRegister:view")
	@RequestMapping(value = "materialForm")
	public String materialRegisterForm(ClinicRegister clinicRegister, Model model) {
		model.addAttribute("clinicRegister", clinicRegister);
		return "modules/clinic/========= ````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````";
	}
	

 	                                           
	@RequiresPermissions("clinic:clinicRegister:edit")
	@RequestMapping(value = "save")
	public String save(ClinicRegister clinicRegister, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request,String typeq) throws Exception {
		if (!beanValidator(model, clinicRegister)){
			return form(clinicRegister, model);
		}
		clinicRegister.setType(typeq);
		
		clinicRegisterService.save(clinicRegister,request);
		addMessage(redirectAttributes, "保存临床登记成功");
		return "redirect:" + adminPath + "/act/task/todo/";
		//return "redirect:"+Global.getAdminPath()+"/clinic/clinicRegister/?repage";
	}
	
	/**
	 * 流程提交
	 * @param clinicRegister
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * @throws Exception 
	 */
	@RequiresPermissions("clinic:clinicRegister:edit")
	@RequestMapping(value = "saveAudit")
	public String saveAudit(ClinicRegister clinicRegister, Model model, RedirectAttributes redirectAttributes) throws Exception {
		if (!beanValidator(model, clinicRegister)){
			return form(clinicRegister, model);
		}
		clinicRegisterService.saveMaterial(clinicRegister);
		addMessage(redirectAttributes, "保存临床登记成功");
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	@RequiresPermissions("clinic:clinicRegister:edit")
	@RequestMapping(value = "delete")
	public String delete(ClinicRegister clinicRegister, RedirectAttributes redirectAttributes) {
		clinicRegisterService.delete(clinicRegister);
		addMessage(redirectAttributes, "删除临床登记成功");
		return "redirect:"+Global.getAdminPath()+"/clinic/clinicRegister/?repage";
	}
	//export
	@RequestMapping(value = "export")
	public String export(ClinicRegister clinicRegister,HttpServletResponse response) {
		List<ClinicRegister>clinicRegisters= clinicRegisterService.findExport(clinicRegister);
		clinicRegisterService.export(response, clinicRegisters,clinicRegister);

		return null;
	}
	//读取D盘的图片
	 private static void getAllFile(File mFile, List<File> mlist) {
	        // 1.获取子目录
	        File[] files = mFile.listFiles();
	        // 2.判断files是否是空的 否则程序崩溃
	        if (files != null) {

	            for (File file : files) {
	                if (file.isDirectory()) {
	                    getAllFile(file, mlist);//调用递归的方式
	                } else {
	                    // 4. 添加到集合中去
	                    String fileName = file.getName();
	                    if (fileName.endsWith(".jpg") || fileName.endsWith(".png")
	                            || fileName.endsWith(".gif")||fileName.endsWith(".pdf")) {
	                        mlist.add(file);//如果是这几种图片格式就添加进去
	                    }
	                }
	            }
	        }
	    }
	
}