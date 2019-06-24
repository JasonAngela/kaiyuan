/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entrust.web;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

import com.drew.lang.StringUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.filter.PhotoUploud;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DnaDataParser;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.dna.dao.DnaExperimentStrDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaPiResultDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaPiResultItemDao;
import com.thinkgem.jeesite.modules.dna.entity.DnaExperimentStr;
import com.thinkgem.jeesite.modules.dna.entity.DnaGeneFrequency;
import com.thinkgem.jeesite.modules.dna.entity.DnaMutation;
import com.thinkgem.jeesite.modules.dna.entity.DnaPiResult;
import com.thinkgem.jeesite.modules.dna.entity.DnaPiResultItem;
import com.thinkgem.jeesite.modules.dna.entity.ParentageTestingEntity;
import com.thinkgem.jeesite.modules.dna.service.DnaPiResultService;
import com.thinkgem.jeesite.modules.dna.service.ParentageTesting;
import com.thinkgem.jeesite.modules.entrust.dao.EntrustAbstractsDao;
import com.thinkgem.jeesite.modules.entrust.dao.EntrustRegisterDao;
import com.thinkgem.jeesite.modules.entrust.dao.MappingDao;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustAbstracts;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustExpertOpinion;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustModifyrecord;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustRegister;
import com.thinkgem.jeesite.modules.entrust.entity.Licensed;
import com.thinkgem.jeesite.modules.entrust.entity.Mapping;
import com.thinkgem.jeesite.modules.entrust.service.EntrustAbstractsService;
import com.thinkgem.jeesite.modules.entrust.service.EntrustExpertOpinionService;
import com.thinkgem.jeesite.modules.entrust.service.EntrustModifyrecordService;
import com.thinkgem.jeesite.modules.entrust.service.EntrustRegisterService;
import com.thinkgem.jeesite.modules.entrust.service.GeneFrequencyStorage;
import com.thinkgem.jeesite.modules.entrust.service.LicensedService;
import com.thinkgem.jeesite.modules.entrust.service.MappingService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import freemarker.core.ReturnInstruction.Return;

/**
 * 鉴定意见Controller
 * @author zhuguli
 * @version 2017-05-11
 */
@Controller
@RequestMapping(value = "${adminPath}/entrust/entrustExpertOpinion")
public class EntrustExpertOpinionController extends BaseController {

	@Autowired
	private EntrustExpertOpinionService entrustExpertOpinionService;
	@Autowired
	private EntrustRegisterService entrustRegisterService;
	@Autowired
	private DnaPiResultService dnaPiResultService;
	@Autowired
	private LicensedService licensedService;
	@Autowired
	private DnaExperimentStrDao dnaExperimentStrDao;
	@Autowired
	private EntrustAbstractsService entrustAbstractsService;
	@Autowired
	private MappingService mappingService;
    @Autowired
    private EntrustModifyrecordService entrustModifyrecordService;
	@Autowired
	private EntrustRegisterDao entrustRegisterDao;
	@Autowired
	private GeneFrequencyStorage frequencyStorage;
	@Autowired
	private EntrustAbstractsDao entrustAbstractsDao;
	
	@ModelAttribute
	public EntrustExpertOpinion get(@RequestParam(required=false) String id) {
		EntrustExpertOpinion entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = entrustExpertOpinionService.get(id);
		}
		if (entity == null){
			entity = new EntrustExpertOpinion();
		}
		return entity;
	}
	
	@RequiresPermissions("entrust:entrustExpertOpinion:view")
	@RequestMapping(value = {"list", ""})
	public String list(EntrustExpertOpinion entrustExpertOpinion, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<EntrustExpertOpinion> page = entrustExpertOpinionService.findPage(new Page<EntrustExpertOpinion>(request, response), entrustExpertOpinion); 
		model.addAttribute("page", page);
		return "modules/entrust/entrustExpertOpinionList";
	}
	
	
	@RequiresPermissions("entrust:entrustExpertOpinion:view")
	@RequestMapping(value = "form")
	public String form(EntrustExpertOpinion entrustExpertOpinion, Model model) {
		 
		EntrustRegister register = entrustRegisterService.get(entrustExpertOpinion.getAct().getBusinessId());
		Act act  = entrustExpertOpinion.getAct();
		List<DnaPiResult> dnaResultList = dnaPiResultService.selectByRegisterId(register.getId());
		for (DnaPiResult dnaPiResult : dnaResultList){
			dnaPiResult.setCpi(dnaPiResult.getCpi().setScale(5,BigDecimal.ROUND_HALF_UP));
		}
		
		List<DnaExperimentStr>strList=dnaExperimentStrDao.getByRegisterId(register.getId());
		Map<String,Map<String,String>> strMap = genateMapFromList(strList);
		model.addAttribute("str", strMap);
		List<EntrustAbstracts> entrustAbstracts= entrustAbstractsService.findAllentrustAbstracts(register.getId());
		List<EntrustAbstracts> entrustAbstracts3=new ArrayList<EntrustAbstracts>();
		
		if(entrustAbstracts.size()==1){
			for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
				if(!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())){
					if(entrustAbstracts2.getSpecimenCode().contains("-G")){
						entrustAbstracts3.add(entrustAbstracts2);
					}
				}
			}    
		}
		
			if(entrustAbstracts.size()==2){
				for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
					if(!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())){
						if(entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length()-1, entrustAbstracts2.getSpecimenCode().length()).equals("M")){
							entrustAbstracts3.add(entrustAbstracts2);
						}
					}
				}    
				for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
					if(!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())){
						if(entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length()-1, entrustAbstracts2.getSpecimenCode().length()).equals("F")){
							entrustAbstracts3.add(entrustAbstracts2);
						}
					}
				}
				for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
					if(!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())){
						if(entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length()-2, entrustAbstracts2.getSpecimenCode().length()).contains("C")){
							entrustAbstracts3.add(entrustAbstracts2);
						}
					}
				}

				for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
					if(!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())){
						if(entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length()-1, entrustAbstracts2.getSpecimenCode().length()).equals("T")){
							entrustAbstracts3.add(entrustAbstracts2);
						}
					}
				}    
				
				for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
					if(!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())){
						if(entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length()-2, entrustAbstracts2.getSpecimenCode().length()).equals("T2")){
							entrustAbstracts3.add(entrustAbstracts2);
						}
					}
				}    
		}
		
			
			 
		
			if(entrustAbstracts.size()>=3){ 
				for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
					if(!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())){
						if(entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length()-1, entrustAbstracts2.getSpecimenCode().length()).equals("F")){
							entrustAbstracts3.add(entrustAbstracts2);
						}
					}
				}
				for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
					if(!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())){
						if(entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length()-2, entrustAbstracts2.getSpecimenCode().length()).contains("C")){
							entrustAbstracts3.add(entrustAbstracts2);
						}
					}
				}
				for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
					if(!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())){
						if(entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length()-1, entrustAbstracts2.getSpecimenCode().length()).equals("M")){
							entrustAbstracts3.add(entrustAbstracts2);
						}
					}
			}


				for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
					if(!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())){
						if(entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length()-1, entrustAbstracts2.getSpecimenCode().length()).equals("T")){
							entrustAbstracts3.add(entrustAbstracts2);
						}
					}
				}    
				
				for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
					if(!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())){
						if(entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length()-2, entrustAbstracts2.getSpecimenCode().length()).equals("T2")){
							entrustAbstracts3.add(entrustAbstracts2);
						}
					}
				}    
				
				for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
					if(!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())){
						if(entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length()-2, entrustAbstracts2.getSpecimenCode().length()).equals("T3")){
							entrustAbstracts3.add(entrustAbstracts2);
						}
					}
				}    
			}
		model.addAttribute("entrustAbstracts",entrustAbstracts3 );                                    
		model.addAttribute("dnaResultList",dnaResultList);
		for(DnaPiResult result:dnaResultList){
				EntrustAbstracts parentAbstracts = getAbstracts(register.getEntrustAbstractsList(),result.getParentCode());
				result.setParentAbstracts(parentAbstracts);
				EntrustAbstracts childAbstracts = getAbstracts(register.getEntrustAbstractsList(),result.getChildCode());
				result.setChildAbstracts(childAbstracts);
		}
		List<EntrustExpertOpinion> opinionList = entrustExpertOpinionService.getByRegisterId(register.getId());
			if(!opinionList.isEmpty()){
				entrustExpertOpinion = opinionList.get(0);
				entrustExpertOpinion.setAct(act);
			}else{                                          
			if(entrustAbstracts.size()==1){
				//以上20个常染色体STR基因座的是样本WZ201809-01743-1（刘欣玥）的DNA数据。
				entrustExpertOpinion.setExplainRemark("以上20个常染色体STR基因座为样本"+entrustAbstracts3.get(0).getSpecimenCode()+"("+entrustAbstracts3.get(0).getClientName()+")"+"的DNA数据");
			}else if(entrustAbstracts.get(0).getSpecimenCode().contains("T")){
				//同体比较
				String t=null;
				String t2=null;
				String t3=null;
				for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
						if(entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length()-1, entrustAbstracts2.getSpecimenCode().length()).equals("T")){
							 t=entrustAbstracts2.getSpecimenCode();
						}    
						if(entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length()-2, entrustAbstracts2.getSpecimenCode().length()).equals("T2")){
							t2=entrustAbstracts2.getSpecimenCode();
						}
						if(entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length()-2, entrustAbstracts2.getSpecimenCode().length()).equals("T3")){
							t3=entrustAbstracts2.getSpecimenCode();
						}
			}
				List<Integer>li=	entrustExpertOpinionService.enable(t,t2,t3);
				//样本WZ201810-01879-1(P000655008)和样本WZ201810-01879-2(P000655000)的等位基因分型（DNA）共有11个不一致。
			/*	li.add(tORt2);
				li.add(t3ORt2);
				li.add(t3ORt);*/
				String sb="&nbsp;&nbsp;以上21个基因座的检验结果显示：";
				String sb2="";
				if(t3==null){
					if(li.size()==0){
						sb+="样本"+t+"和样本"+t2+"的等位基因分型（DNA）一致。"+"\n";
						sb2=t+"与"+t2+"的等位基因分型（DNA）一致。"+"\n";
					}else{
						sb+="样本"+t+"和样本"+t2+"的等位基因分型（DNA）共有"+li.get(0)+"个不一致。"+"\n";
						sb2=t+"与"+t2+"的等位基因分型（DNA）不一致。"+"\n";
					}
					
				}else{
					//	li.add(tORt2);	li.add(t3ORt2); li.add(t3ORt);
					if(li.get(0)==0&&li.get(1)==0&&li.get(2)==0){
						sb+="样本"+t+"，样本"+t2+"和样本"+t3+"的等位基因分型（DNA）一致。"+"\n";
						sb2=t+","+t2+"与"+t3+"的等位基因分型（DNA）一致"+"\n";
					}else if(li.get(0)==0&&li.get(1)!=0&&li.get(2)!=0){
						//12 2!3 1!3
						sb+="样本"+t+"与样本"+t2+"的等位基因分型（DNA）一致。"+"\n"+"样本"+t2+"与样本"+t3+"的等位基因分型（DNA）共有"+li.get(1)+"个不一致。"+"\n"+
								"样本"+t+"与样本"+t3+"的等位基因分型（DNA）共有"+li.get(2)+"个不一致。"+"\n";
						sb2=t+"与"+t2+"的等位基因分型（DNA）一致"+"\n"+t+"与"+t3+"的等位基因分型（DNA）不一致"+"\n";
					}else if(li.get(0)!=0&&li.get(1)!=0&&li.get(2)==0){
						  //1!2  2!3  13
						sb+="样本"+t+"与样本"+t2+"的等位基因分型（DNA）共有+"+li.get(0)+ "个不一致。"+"\n"+"样本"+t2+"与样本"+t3+"的等位基因分型共有"+li.get(1)+"（DNA）个不一致。"+"\n"
						  +"样本"+t+"与样本"+t3+"的等位基因分型（DNA）一致。"+"\n";
						sb2=t+"与"+t3+"的等位基因分型（DNA）一致"+"\n"+t+"与"+t2+"的等位基因分型（DNA）不一致"+"\n";
					}else if(li.get(0)==0&&li.get(1)!=0&&li.get(2)!=0){
						//1!2 23 1!3 
						sb+="样本"+t+"与样本"+t2+"的等位基因分型（DNA）共有+"+li.get(0)+ "个不一致。"+"\n"+"样本"+t2+"与样本"+t3+"的等位基因分型（DNA）一致。"+"\n"
								  +"样本"+t+"与样本"+t3+"的等位基因分型（DNA）共有"+li.get(2)+"个不一致。"+"\n";
						sb2=t2+"与"+t3+"的等位基因分型（DNA）一致"+"\n"+t+"与"+t2+"的等位基因分型（DNA）不一致"+"\n";
					}else{
						//1!2 2!3 3!1
						sb+="样本"+t+"与样本"+t2+"的等位基因分型（DNA）共有"+li.get(0)+ "个不一致。"+"\n"+"样本"+t2+"与样本"+t3+"的等位基因分型（DNA）共有"+li.get(1)+"个不一致。"+"\n"+
								"样本"+t+"与样本"+t3+"的等位基因分型（DNA）共有"+li.get(2)+"个不一致。"+"\n";
						sb2=t+"与"+t2+"的等位基因分型（DNA）不一致"+"\n"+t+"与"+t3+"的等位基因分型（DNA）不一致"+"\n"+t2+"与"+t3+"的等位基因分型（DNA）不一致"+"\n";
					}
				}
				entrustExpertOpinion.setExplainRemark(sb);
				entrustExpertOpinion.setDraft(sb2);
		}else{
			  String[] explain = generateDraftFromResult(dnaResultList);
			  entrustExpertOpinion.setDraftRemark(explain[0]);
			  entrustExpertOpinion.setDraft(explain[0]);
			  entrustExpertOpinion.setExplain("   "+explain[1]);
			  entrustExpertOpinion.setExplainRemark(explain[1]);
			}
		}
		entrustExpertOpinion.setRegister(register);
		model.addAttribute("entrustExpertOpinion", entrustExpertOpinion);
		String taskKey = entrustExpertOpinion.getAct().getTaskDefKey();
         model.addAttribute("register", register) ;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            /*Mapping mapping= mappingDao.findEntrsut(register.getId());
		entrustExpertOpinion.setMapping(mapping);
		//"/jeesite/userfiles/1/images/entrust/mapping/2017/09/1111.jpg"
		List<String>pics=new ArrayList<String>();
		 if(mapping!=null){
			String []d=mapping.getPic().split("\\|");
			for (String pic : d) {
				 if(pic.equals("")){
				 
				 	}else{
					pics.add(pic);
				 }
			}
		 } 
		model.addAttribute("pic",pics);*/
         
         String casecode= register.getCaseCode().substring(11, 16);
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
        model.addAttribute("simple", register.getCaseCode().substring(3, 7));
        int s=  Integer.parseInt(casecode);
      	model.addAttribute("casecode", s);
         
		if("reportInit".equals(taskKey)){
			//第二鉴定人
			 register.setStatus("11");
			 entrustRegisterDao.update(register);
			 return "modules/entrust/entrustExpertOpinionInitForm";
		}else if("SecondPersonCheck".equals(taskKey)){
			//授权签字人
			 register.setStatus("12");
			 entrustRegisterDao.update(register);
			 model.addAttribute("user", UserUtils.getUser().getName());
			return "modules/entrust/entrustExpertOpinionSecondForm";
		}else if("managerCheck".equals(taskKey)){
			 model.addAttribute("user", UserUtils.getUser().getName());
			 //成文修改
			 register.setStatus("13");
			entrustRegisterDao.update(register);
			return "modules/entrust/entrustExpertOpinionManagerForm";	
		}else{
			//归档
			register.setStatus("14");
			entrustRegisterDao.update(register);
			 model.addAttribute("user", UserUtils.getUser().getName());
			entrustExpertOpinion.setFinalVersion(StringEscapeUtils.unescapeHtml4(entrustExpertOpinion.getExplain().trim()));
			return "modules/entrust/entrustExpertOpinionEndForm";
		}
	}

	//图谱页面	
		@RequiresPermissions("entrust:entrustExpertOpinion:view")
		@RequestMapping(value = "analyze")
		public String analyze(EntrustExpertOpinion entrustExpertOpinion, Model model) {
		  
			EntrustRegister register = entrustRegisterService.get(entrustExpertOpinion.getAct().getBusinessId());
			Act act  = entrustExpertOpinion.getAct();
			
			model.addAttribute("registerId", register.getId());
			List<DnaPiResult> dnaResultList = dnaPiResultService.selectByRegisterId(register.getId());
			for(DnaPiResult result:dnaResultList){
				EntrustAbstracts parentAbstracts = getAbstracts(register.getEntrustAbstractsList(),result.getParentCode());
				result.setParentAbstracts(parentAbstracts);
				EntrustAbstracts childAbstracts = getAbstracts(register.getEntrustAbstractsList(),result.getChildCode());
				result.setChildAbstracts(childAbstracts);
			}
			  String[] explain = generateDraftFromResult(dnaResultList);
			  if(explain[0].contains("不排除")){
					 model.addAttribute("noPai", "这是个不排除案件");
				 }
			List<DnaExperimentStr>strList=dnaExperimentStrDao.getByRegisterId(register.getId());
			Map<String,Map<String,String>> strMap = genateMapFromList(strList);
			model.addAttribute("str", strMap);
			List<EntrustAbstracts> entrustAbstracts= entrustAbstractsService.findAllentrustAbstracts(register.getId());
			
			model.addAttribute("entrustAbstracts",entrustAbstracts);                                    
			model.addAttribute("dnaResultList",dnaResultList);
			for(DnaPiResult result:dnaResultList){
				EntrustAbstracts parentAbstracts = getAbstracts(register.getEntrustAbstractsList(),result.getParentCode());
				result.setParentAbstracts(parentAbstracts);
				EntrustAbstracts childAbstracts = getAbstracts(register.getEntrustAbstractsList(),result.getChildCode());
				result.setChildAbstracts(childAbstracts);                                     
			}
			return "modules/entrust/entrustExpertOpinionAnalyze";
		}
	//上传图谱
	@RequiresPermissions("entrust:entrustExpertOpinion:edit")
	@RequestMapping(value = "saveAnalyze")
	public String saveAnalyze(EntrustExpertOpinion entrustExpertOpinion, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request,String [] noCode) throws Exception {
		
		Mapping mapping=new Mapping();
		mapping.setPic(entrustExpertOpinion.getMapping().getPic());
		mapping.setEntrustId(entrustExpertOpinion.getRegister().getId());
		mapping.setId(entrustExpertOpinion.getMapping().getId());
		EntrustRegister entrustRegister=entrustRegisterService.get(entrustExpertOpinion.getRegister().getId());
		PhotoUploud photoUploud=new PhotoUploud();
		String toName = "D:"+File.separator+"information"+File.separator+"DNA"+File.separator+entrustRegister.getCode()+File.separator+"mapping"+File.separator;
		photoUploud.UploudPhoto(entrustExpertOpinion.getMapping().getPic(), request, toName);
		mappingService.save(mapping);
		entrustExpertOpinionService.saveAnalyze(entrustExpertOpinion,noCode);
		addMessage(redirectAttributes, "上传图谱成功");
		return "redirect:" + adminPath + "/act/task/todo/";
		//return "redirect:"+Global.getAdminPath()+"/entrust/entrustExpertOpinion/?repage";
	}
	
	
	private Map<String,Map<String,String>> genateMapFromList(List<DnaExperimentStr> strList) {
		Map<String,Map<String,String>> strMapList =  new TreeMap<String, Map<String,String>>();
		
		for(DnaExperimentStr str :strList){
			String geneLoci = str.getGeneLoci();
			String specimenCode = str.getSpecimenCode();
			String x = str.getX();
			String y = str.getY();   
			if(strMapList.containsKey(geneLoci)){
				Map<String, String> map = strMapList.get(geneLoci);
				map.put(specimenCode, x+"  / "+y);
			}else{
				Map<String, String> map =  new TreeMap<String, String>();
				map.put(specimenCode, x+"  /  "+y);
				strMapList.put(geneLoci, map);
			}
		}
		return strMapList;
	}
	
	private EntrustAbstracts getAbstracts(List<EntrustAbstracts> entrustAbstractsList, String code){
		code=code.substring(2, code.indexOf("("));
		for(EntrustAbstracts abstracts:entrustAbstractsList){
			if(code.equals(abstracts.getSpecimenCode())){
				return abstracts;
			}
		}
	
		return null;
	} 
	private String[] generateDraftFromResult(List<DnaPiResult> dnaResultList) {

		StringBuffer sb = new StringBuffer();
		
		
		String[] explain = new String[2];
		StringBuffer draft = new StringBuffer();
		sb.append("&nbsp;&nbsp;根据孟德尔遗传定律，亲代各自将一半的遗传信息给子代；以上21个基因座的检验结果显示:"+"\n");
		//"样本WZ201707-01602-F(杨焱火)具有成为样本WZ201707-01602-C(杨洋)生物学父亲的遗传学条件，两者之间的累积亲权指数（CPI值）达1.99×108以上，亲权关系概率达99.99%以上，故支持杨焱火为杨洋的生物学父亲。";
		String trueTemplate = "<w:br/>"+"&nbsp;&nbsp;%s具有成为%s生物学%s的遗传学条件，两者之间的累积亲权指数（CPI值）达%s以上，亲权关系概率达%s以上，%s。";
		String falseTemplate = "<w:br/>"+"&nbsp;&nbsp;%s和%s之间共有%s个STR基因座(%s)的等位基因分型检验结果不符合孟德尔遗传定律，%s。";
	
		 // 样本WZ201808-01448-F(金良国)%和样本WZ201808-01448-C(金艳琳)%之间共有9%个STR基因座%（*号标记）的等位基因分型检验结果不符合孟德尔遗传定律，故排除金良国为金艳琳的生物学父亲。
		String trueDratTemplate = "支持%s为%s的生物学%s\n";
		String falseDratTempalte="排除%s为%s的生物学%s\n";
		/*根据孟德尔遗传定律，亲代各自将一半的遗传信息传给子代；以上21个基因座的检验结果显示，样本JD1400XX-XXXX-1（B）在除基因座vWA
		 * （可能存在基因突变）外的其它20个基因座均具备成为样本JD1400XX-XXXX-2（A）生物学父亲的遗传学条件；
		 * 两者之间的累积亲权指数（CPI值）达8.41×105，亲权关系概率达99.99%
		 * */
		
		/*根据孟德尔遗传定律，亲代各自将一半的遗传信息传给子代；以上21个基因座的检验结果显示，%s在除基因座%s
		 * （可能存在基因突变）外的其它%s个基因座均具备成为样本%s生物学%s的遗传学条件；
		 * 两者之间的累积亲权指数（CPI值）达%s，亲权关系概率达%s
		 * */
		
		
		Map<String,List<String>> trueMap = new HashMap<String,List<String>> ();
		
		Map<String,List<String>> trueMap1= new HashMap<String,List<String>> ();
		Map<String,List<String>> falseMap = new HashMap<String,List<String>> ();
		String  fathersNames="";
		String mathersNames="";
		String trueDratTemplate1= "支持%s为%s的生物学%s";
		String trueDratTemplate2= "不排除%s为%s的生物学%s";
		String falseDratTempalte1 = "排除%s为%s的生物学%s";
		
		String draftValue1="";
		List<String>valueKey=new ArrayList<String>();
		List<DnaPiResultItem>dnaPiResultItemList=new ArrayList<DnaPiResultItem>();
		String loci=null;
		
	
		
		
		
		//FC
		for(DnaPiResult result:dnaResultList){
			 String vn= result.getChildCode().substring(0, result.getChildCode().indexOf("("));
			 String vc= result.getParentCode().substring(0, result.getParentCode().indexOf("("));
			if( vn.substring(vn.length()-1, vn.length()).equals("C")&&vc.substring(vc.length()-1, vc.length()).equals("F")){
				DnaMutation dnaMutation=	dnaPiResultService.getLoci(result.getChildCode(), result.getParentCode());
			String relation = DictUtils.getDictLabel(result.getParentAbstracts().getAppellation(), "appellationCode", "父亲");
			String template=result.getRcp().doubleValue()>0.99?trueTemplate:falseTemplate;
			if(result.getRcp().doubleValue()>0.9999){
				 draftValue1= String.format(trueDratTemplate1, result.getParentAbstracts().getClientName(),result.getChildAbstracts().getClientName(),DictUtils.getDictLabel(result.getParentAbstracts().getAppellation(), "appellationCode", "父亲"));
				String parentName = result.getParentAbstracts().getClientName();
				if(trueMap.containsKey(parentName+"_"+relation )){
					trueMap.get(parentName+"_"+relation ).add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
				}else{
					List<String> list = new ArrayList<String>();
					list.add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
					trueMap.put(parentName+"_"+relation, list);
				}
				
				
			}else if(result.getRcp().doubleValue()>0.9900&&result.getRcp().doubleValue()<0.9999){
				 draftValue1= String.format(trueDratTemplate2, result.getParentAbstracts().getClientName(),result.getChildAbstracts().getClientName(),DictUtils.getDictLabel(result.getParentAbstracts().getAppellation(), "appellationCode", "父亲"));
				String parentName = result.getParentAbstracts().getClientName();
				if(trueMap1.containsKey(parentName+"_"+relation )){
					trueMap1.get(parentName+"_"+relation ).add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
				}else{
					List<String> list = new ArrayList<String>();
					list.add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
					trueMap1.put(parentName+"_"+relation, list);
				}
			}
			else{
				 draftValue1= String.format(falseDratTempalte1, result.getParentAbstracts().getClientName(),result.getChildAbstracts().getClientName(),DictUtils.getDictLabel(result.getParentAbstracts().getAppellation(), "appellationCode", "父亲"));
				String parentName = result.getParentAbstracts().getClientName();
				if(falseMap.containsKey(parentName+"_"+relation )){
					falseMap.get(parentName+"_"+relation).add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
				}else{
					List<String> list = new ArrayList<String>();
					list.add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
					falseMap.put(parentName+"_"+relation, list);
				}
			}
			
			
			String zuo="";
			Integer n=0;  
			String cpi="";
			int temp=0;
		
			if(result.getCpi().doubleValue()>10000){
				String ji=String.valueOf(result.getCpi().doubleValue()).toString();
			if(ji!=null){
				if(ji.contains("E")){
					 temp=ji.indexOf("E");
					n=Integer.parseInt(ji.substring(temp+1, ji.length()));
					zuo=ji.substring(0,4);
				}
				else{
				    temp=ji.indexOf(".");
					n=temp-1;
					zuo=ji.substring(0,1)+"."+ji.substring(1,2);
				}
			}
			if(!zuo.equals("")&&n!=0){
				cpi=zuo+"×10^"+n;
			}
			}
			else{
				cpi=String.valueOf(result.getCpi().doubleValue()).toString();
			}
			String rcp="";
			if(result.getRcp().doubleValue()>0.9){
				rcp=String.valueOf(result.getRcp().doubleValue()*100).substring(0, 5)+"%";
			}else{
				rcp=String.valueOf(result.getRcp());
			} 
			String value=null;
			String true1="<w:br/>"+"&nbsp;&nbsp;%s在除基因座%s（可能存在基因突变）外的其它%s个基因座均具备成为%s生物学%s的遗传学条件；两者之间的累积亲权指数（CPI值）达%s以上，亲权关系概率达%s以上，%s。";
			//String true1="<w:br/>"+"&nbsp;&nbsp;1  在除基因座%s（可能存在基因突变）外的其它%s个基因座均具备成为2生物学3的遗传学条件； 两者之间的累积亲权指数（CPI值）达4，亲权关系概率达5,6";
			String true2="<w:br/>"+"&nbsp;&nbsp;%s在除基因座%s（可能存在基因突变）外的其它%s个基因座均具备成为%s生物学%s的遗传学条件；但是两者之间的累积亲权指数（CPI值）为%s（小于10000），亲权关系概率无法达到%s以上，%s。";
			//样本WZ201804-00733-F(汪发良)在除XXX（可能存在基因突变）外的其它20个基因座均具有成为样本WZ201804-00733-C(汪静媛)生物学父亲的遗传学条件，但是两者之间的累积亲权指数（CPI值）为305（小于10000），亲权关系概率无法达到99.99%以上，故不排除汪发良为汪静媛的生物学父亲。
			
			
			
			if(result.getRcp().doubleValue()>0.9999){
			if(dnaMutation.getLocis() != null && dnaMutation.getLocis().length() != 0){
						if(result.getChildCode().equals(dnaMutation.getCcode())&&result.getParentCode().equals(dnaMutation.getPcode())){
									value = String.format(true1+"\n", 
											result.getParentCode(),
											dnaMutation.getLocis().substring(0, dnaMutation.getLocis().length()-1).trim(),
											dnaMutation.getTotle(),
											result.getChildCode(),
											relation,
											cpi,
											rcp,
											"故"+draftValue1
											);
							}else{
								value = String.format(template+"\n", 
										  result.getParentCode(),
										  result.getChildCode(),
										  relation,
										  cpi,
										  rcp,
										  "故"+draftValue1
										  );
								
							}
			}else{
									value = String.format(template+"\n", 
									  result.getParentCode(),
									  result.getChildCode(),
									  relation,
									  cpi,
									  rcp,
									  "故"+draftValue1
									  );
							
						}
					
			}else if(result.getRcp().doubleValue()>0.9900&&result.getRcp().doubleValue()<0.9999){
				if(dnaMutation.getLocis() != null && dnaMutation.getLocis().length() != 0){
						if(result.getChildCode().equals(dnaMutation.getCcode())&&result.getParentCode().equals(dnaMutation.getPcode())){
									value = String.format(true2+"\n", 
											result.getParentCode(),
											dnaMutation.getLocis().substring(0, dnaMutation.getLocis().length()-1).trim(),
											dnaMutation.getTotle(),
											result.getChildCode(),
											relation,
											cpi,
											"99.99%",
											"故"+draftValue1
											);
							}else{
								value = String.format(template+"\n", 
										  result.getParentCode(),
										  result.getChildCode(),
										  relation,
										  cpi,
										  rcp,
										  "故"+draftValue1
										  );
								
							}
						}
			
			}
		
		
		
		else{
			if(dnaMutation.getLocis() != null && dnaMutation.getLocis().length() != 0){
				
				 value = String.format(template+"\n", 
						  result.getParentCode(),
						  result.getChildCode(),
						 dnaMutation.getTotle(),
						  dnaMutation.getLocis().substring(0, dnaMutation.getLocis().length()-1).trim().toString(),
						  "故"+draftValue1
						  );
			}
		}
			System.out.println(value.toString());
			sb.append(value.toString());
		}
		}	
		
		
		
		// FC2
		
		
		for(DnaPiResult result:dnaResultList){
			 String vn= result.getChildCode().substring(0, result.getChildCode().indexOf("("));
			 String vc= result.getParentCode().substring(0, result.getParentCode().indexOf("("));
				if( vn.substring(vn.length()-2, vn.length()).equals("C2")&&vc.substring(vc.length()-1, vc.length()).equals("F")){
						DnaMutation dnaMutation=	dnaPiResultService.getLoci(result.getChildCode(), result.getParentCode());
					String relation = DictUtils.getDictLabel(result.getParentAbstracts().getAppellation(), "appellationCode", "父亲");
					String template=result.getCpi().doubleValue()>0.99?trueTemplate:falseTemplate;
			if(result.getCpi().doubleValue()>0.99){
				 draftValue1= String.format(trueDratTemplate1, result.getParentAbstracts().getClientName(),result.getChildAbstracts().getClientName(),DictUtils.getDictLabel(result.getParentAbstracts().getAppellation(), "appellationCode", "父亲"));
				String parentName = result.getParentAbstracts().getClientName();
				if(trueMap.containsKey(parentName+"_"+relation )){
					trueMap.get(parentName+"_"+relation ).add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
				}else{
					List<String> list = new ArrayList<String>();
					list.add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
					trueMap.put(parentName+"_"+relation, list);
				}
			}else{
				 draftValue1= String.format(falseDratTempalte1, result.getParentAbstracts().getClientName(),result.getChildAbstracts().getClientName(),DictUtils.getDictLabel(result.getParentAbstracts().getAppellation(), "appellationCode", "父亲"));
				String parentName = result.getParentAbstracts().getClientName();
				if(falseMap.containsKey(parentName+"_"+relation )){
					falseMap.get(parentName+"_"+relation).add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
				}else{
					List<String> list = new ArrayList<String>();
					list.add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
					falseMap.put(parentName+"_"+relation, list);
				}
			}
			
			
			String zuo="";
			Integer n=0;  
			String cpi="";
			int temp=0;
		
			if(result.getCpi().doubleValue()>10000){
				String ji=String.valueOf(result.getCpi().doubleValue()).toString();
			if(ji!=null){
				if(ji.contains("E")){
					 temp=ji.indexOf("E");
					n=Integer.parseInt(ji.substring(temp+1, ji.length()));
					zuo=ji.substring(0,4);
				}
				else{
				    temp=ji.indexOf(".");
					n=temp-1;
					zuo=ji.substring(0,1)+"."+ji.substring(1,2);
				}
				
			}
			if(!zuo.equals("")&&n!=0){
				cpi=zuo+"×10^"+n;
			}
			}
			else{
				cpi=String.valueOf(result.getCpi().doubleValue()).toString();
			}
			String rcp="";
			if(result.getRcp().doubleValue()>0.9){
				rcp=String.valueOf(result.getRcp().doubleValue()*100).substring(0, 5)+"%";
			}else{
				rcp=String.valueOf(result.getRcp());
			}
			String value=null;
			String true1="<w:br/>"+"&nbsp;&nbsp;%s在除基因座%s（可能存在基因突变）外的其它%s个基因座均具备成为 %s生物学%s的遗传学条件；两者之间的累积亲权指数（CPI值）达%s以上，亲权关系概率达%s以上，%s。";
			//String true1="<w:br/>"+"&nbsp;&nbsp;1  在除基因座%s（可能存在基因突变）外的其它%s个基因座均具备成为2生物学3的遗传学条件； 两者之间的累积亲权指数（CPI值）达4，亲权关系概率达5,6";
			String true2="<w:br/>"+"&nbsp;&nbsp;%s在除基因座%s（可能存在基因突变）外的其它%s个基因座均具备成为 %s生物学%s的遗传学条件；但是两者之间的累积亲权指数（CPI值）为%s（小于10000），亲权关系概率无法达到%s以上，%s。";
			//样本WZ201804-00733-F(汪发良)在除XXX（可能存在基因突变）外的其它20个基因座均具有成为样本WZ201804-00733-C(汪静媛)生物学父亲的遗传学条件，但是两者之间的累积亲权指数（CPI值）为305（小于10000），亲权关系概率无法达到99.99%以上，故不排除汪发良为汪静媛的生物学父亲。
			
			if(result.getRcp().doubleValue()>0.9999){
			if(dnaMutation.getLocis() != null && dnaMutation.getLocis().length() != 0){
						if(result.getChildCode().equals(dnaMutation.getCcode())&&result.getParentCode().equals(dnaMutation.getPcode())){
									value = String.format(true1+"\n", 
											result.getParentCode(),
											dnaMutation.getLocis().substring(0, dnaMutation.getLocis().length()-1).trim(),
											dnaMutation.getTotle(),
											result.getChildCode(),
											relation,
											cpi,
											rcp,
											"故"+draftValue1
											);
							}else{
								value = String.format(template+"\n", 
										  result.getParentCode(),
										  result.getChildCode(),
										  relation,
										  cpi,
										  rcp,
										  "故"+draftValue1
										  );
								
							}
			}else{
									value = String.format(template+"\n", 
									  result.getParentCode(),
									  result.getChildCode(),
									  relation,
									  cpi,
									  rcp,
									  "故"+draftValue1
									  );
							
						}
					
			}else if(result.getRcp().doubleValue()>0.9900&&result.getRcp().doubleValue()<0.9999){
				if(dnaMutation.getLocis() != null && dnaMutation.getLocis().length() != 0){
						if(result.getChildCode().equals(dnaMutation.getCcode())&&result.getParentCode().equals(dnaMutation.getPcode())){
									value = String.format(true2+"\n", 
											result.getParentCode(),
											dnaMutation.getLocis().substring(0, dnaMutation.getLocis().length()-1).trim(),
												dnaMutation.getTotle(),
											result.getChildCode(),
											relation,
											cpi,
											"99.99%",
											"故"+draftValue1
											);
							}else{
								value = String.format(template+"\n", 
										  result.getParentCode(),
										  result.getChildCode(),
										  relation,
										  cpi,
										  rcp,
										  "故"+draftValue1
										  );
								
							}
			}
		
			}
		
		
		else{
			if(dnaMutation.getLocis() != null && dnaMutation.getLocis().length() != 0){
				 value = String.format(template+"\n", 
						  result.getParentCode(),
						  result.getChildCode(),
						 21- dnaMutation.getTotle(),
						  dnaMutation.getLocis().substring(0, dnaMutation.getLocis().length()-1).trim(),
						  "故"+draftValue1
						  );
			}
		}
		sb.append(value);
		}
		}
		
		//FC3
		for(DnaPiResult result:dnaResultList){
			 String vn= result.getChildCode().substring(0, result.getChildCode().indexOf("("));
			 String vc= result.getParentCode().substring(0, result.getParentCode().indexOf("("));
				if( vn.substring(vn.length()-2, vn.length()).equals("C3")&&vc.substring(vc.length()-1, vc.length()).equals("F")){
						DnaMutation dnaMutation=	dnaPiResultService.getLoci(result.getChildCode(), result.getParentCode());
					String relation = DictUtils.getDictLabel(result.getParentAbstracts().getAppellation(), "appellationCode", "父亲");
					String template=result.getCpi().doubleValue()>0.99?trueTemplate:falseTemplate;
					
			if(result.getCpi().doubleValue()>0.99){
				 draftValue1= String.format(trueDratTemplate1, result.getParentAbstracts().getClientName(),result.getChildAbstracts().getClientName(),DictUtils.getDictLabel(result.getParentAbstracts().getAppellation(), "appellationCode", "父亲"));
				String parentName = result.getParentAbstracts().getClientName();
				if(trueMap.containsKey(parentName+"_"+relation )){
					trueMap.get(parentName+"_"+relation ).add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
				}else{
					List<String> list = new ArrayList<String>();
					list.add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
					trueMap.put(parentName+"_"+relation, list);
				}
			}else{
				 draftValue1= String.format(falseDratTempalte1, result.getParentAbstracts().getClientName(),result.getChildAbstracts().getClientName(),DictUtils.getDictLabel(result.getParentAbstracts().getAppellation(), "appellationCode", "父亲"));
				String parentName = result.getParentAbstracts().getClientName();
				if(falseMap.containsKey(parentName+"_"+relation )){
					falseMap.get(parentName+"_"+relation).add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
				}else{
					List<String> list = new ArrayList<String>();
					list.add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
					falseMap.put(parentName+"_"+relation, list);
				}
			}
			
			
			String zuo="";
			Integer n=0;  
			String cpi="";
			int temp=0;
		
			if(result.getCpi().doubleValue()>10000){
				String ji=String.valueOf(result.getCpi().doubleValue()).toString();
			if(ji!=null){
				if(ji.contains("E")){
					 temp=ji.indexOf("E");
					n=Integer.parseInt(ji.substring(temp+1, ji.length()));
					zuo=ji.substring(0,4);
				}
				else{
				    temp=ji.indexOf(".");
					n=temp-1;
					zuo=ji.substring(0,1)+"."+ji.substring(1,2);
				}
				
			}
			if(!zuo.equals("")&&n!=0){
				cpi=zuo+"×10^"+n;
			}
			}
			else{
				cpi=String.valueOf(result.getCpi().doubleValue()).toString();
			}
			String rcp="";
			if(result.getRcp().doubleValue()>0.9){
				rcp=String.valueOf(result.getRcp().doubleValue()*100).substring(0, 5)+"%";
			}else{
				rcp=String.valueOf(result.getRcp());
			}
			String value=null;
			String true1="<w:br/>"+"&nbsp;&nbsp;%s在除基因座%s（可能存在基因突变）外的其它%s个基因座均具备成为 %s生物学%s的遗传学条件；两者之间的累积亲权指数（CPI值）达%s以上，亲权关系概率达%s以上，%s。";
			//String true1="<w:br/>"+"&nbsp;&nbsp;1  在除基因座%s（可能存在基因突变）外的其它%s个基因座均具备成为2生物学3的遗传学条件； 两者之间的累积亲权指数（CPI值）达4，亲权关系概率达5,6";
			String true2="<w:br/>"+"&nbsp;&nbsp;%s在除基因座%s（可能存在基因突变）外的其它%s个基因座均具备成为 %s生物学%s的遗传学条件；但是两者之间的累积亲权指数（CPI值）为%s（小于10000），亲权关系概率无法达到%s以上，%s。";
			//样本WZ201804-00733-F(汪发良)在除XXX（可能存在基因突变）外的其它20个基因座均具有成为样本WZ201804-00733-C(汪静媛)生物学父亲的遗传学条件，但是两者之间的累积亲权指数（CPI值）为305（小于10000），亲权关系概率无法达到99.99%以上，故不排除汪发良为汪静媛的生物学父亲。
			
			if(result.getRcp().doubleValue()>0.9999){
			if(dnaMutation.getLocis() != null && dnaMutation.getLocis().length() != 0){
						if(result.getChildCode().equals(dnaMutation.getCcode())&&result.getParentCode().equals(dnaMutation.getPcode())){
									value = String.format(true1+"\n", 
											result.getParentCode(),
											dnaMutation.getLocis().substring(0, dnaMutation.getLocis().length()-1).trim(),
											dnaMutation.getTotle(),
											result.getChildCode(),
											relation,
											cpi,
											rcp,
											"故"+draftValue1
											);
							}else{
								value = String.format(template+"\n", 
										  result.getParentCode(),
										  result.getChildCode(),
										  relation,
										  cpi,
										  rcp,
										  "故"+draftValue1
										  );
								
							}
			}else{
									value = String.format(template+"\n", 
									  result.getParentCode(),
									  result.getChildCode(),
									  relation,
									  cpi,
									  rcp,
									  "故"+draftValue1
									  );
							
						}
					
			}else if(result.getRcp().doubleValue()>0.9900&&result.getRcp().doubleValue()<0.9999){
				if(dnaMutation.getLocis() != null && dnaMutation.getLocis().length() != 0){
						if(result.getChildCode().equals(dnaMutation.getCcode())&&result.getParentCode().equals(dnaMutation.getPcode())){
									value = String.format(true2+"\n", 
											result.getParentCode(),
											dnaMutation.getLocis().substring(0, dnaMutation.getLocis().length()-1).trim(),
												dnaMutation.getTotle(),
											result.getChildCode(),
											relation,
											cpi,
											"99.99%",
											"故"+draftValue1
											);
							}else{
								value = String.format(template+"\n", 
										  result.getParentCode(),
										  result.getChildCode(),
										  relation,
										  cpi,
										  rcp,
										  "故"+draftValue1
										  );
								
							}
			}
		}
		
		
		
		
		else{
			if(dnaMutation.getLocis() != null && dnaMutation.getLocis().length() != 0){
				 value = String.format(template+"\n", 
						  result.getParentCode(),
						  result.getChildCode(),
						21-  dnaMutation.getTotle(),
						  dnaMutation.getLocis().substring(0, dnaMutation.getLocis().length()-1).trim(),
						  "故"+draftValue1
						  );
			}
		}
		sb.append(value);
		}
		}
		
		
	//FC4	
		for(DnaPiResult result:dnaResultList){
			 String vn= result.getChildCode().substring(0, result.getChildCode().indexOf("("));
			 String vc= result.getParentCode().substring(0, result.getParentCode().indexOf("("));
				if( vn.substring(vn.length()-2, vn.length()).equals("C4")&&vc.substring(vc.length()-1, vc.length()).equals("F")){
						DnaMutation dnaMutation=	dnaPiResultService.getLoci(result.getChildCode(), result.getParentCode());
					String relation = DictUtils.getDictLabel(result.getParentAbstracts().getAppellation(), "appellationCode", "父亲");
					String template=result.getCpi().doubleValue()>0.99?trueTemplate:falseTemplate;
					
			if(result.getCpi().doubleValue()>0.99){
				 draftValue1= String.format(trueDratTemplate1, result.getParentAbstracts().getClientName(),result.getChildAbstracts().getClientName(),DictUtils.getDictLabel(result.getParentAbstracts().getAppellation(), "appellationCode", "父亲"));
				String parentName = result.getParentAbstracts().getClientName();
				if(trueMap.containsKey(parentName+"_"+relation )){
					trueMap.get(parentName+"_"+relation ).add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
				}else{
					List<String> list = new ArrayList<String>();
					list.add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
					trueMap.put(parentName+"_"+relation, list);
				}
			}else{
				 draftValue1= String.format(falseDratTempalte1, result.getParentAbstracts().getClientName(),result.getChildAbstracts().getClientName(),DictUtils.getDictLabel(result.getParentAbstracts().getAppellation(), "appellationCode", "父亲"));
				String parentName = result.getParentAbstracts().getClientName();
				if(falseMap.containsKey(parentName+"_"+relation )){
					falseMap.get(parentName+"_"+relation).add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
				}else{
					List<String> list = new ArrayList<String>();
					list.add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
					falseMap.put(parentName+"_"+relation, list);
				}
			}
			
			
			String zuo="";
			Integer n=0;  
			String cpi="";
			int temp=0;
		
			if(result.getCpi().doubleValue()>10000){
				String ji=String.valueOf(result.getCpi().doubleValue()).toString();
			if(ji!=null){
				if(ji.contains("E")){
					 temp=ji.indexOf("E");
					n=Integer.parseInt(ji.substring(temp+1, ji.length()));
					zuo=ji.substring(0,4);
				}
				else{
				    temp=ji.indexOf(".");
					n=temp-1;
					zuo=ji.substring(0,1)+"."+ji.substring(1,2);
				}
				
			}
			if(!zuo.equals("")&&n!=0){
				cpi=zuo+"×10^"+n;
			}
			}
			else{
				cpi=String.valueOf(result.getCpi().doubleValue()).toString();
			}
			String rcp="";
			if(result.getRcp().doubleValue()>0.9){
				rcp=String.valueOf(result.getRcp().doubleValue()*100).substring(0, 5)+"%";
			}else{
				rcp=String.valueOf(result.getRcp());
			}
			String value=null;
			String true1="<w:br/>"+"&nbsp;&nbsp;%s在除基因座%s（可能存在基因突变）外的其它%s个基因座均具备成为 %s生物学%s的遗传学条件；两者之间的累积亲权指数（CPI值）达%s以上，亲权关系概率达%s以上，%s。";
			//String true1="<w:br/>"+"&nbsp;&nbsp;1  在除基因座%s（可能存在基因突变）外的其它%s个基因座均具备成为2生物学3的遗传学条件； 两者之间的累积亲权指数（CPI值）达4，亲权关系概率达5,6";
			String true2="<w:br/>"+"&nbsp;&nbsp;%s在除基因座%s（可能存在基因突变）外的其它%s个基因座均具备成为 %s生物学%s的遗传学条件；但是两者之间的累积亲权指数（CPI值）为%s（小于10000），亲权关系概率无法达到%s以上，%s。";
			//样本WZ201804-00733-F(汪发良)在除XXX（可能存在基因突变）外的其它20个基因座均具有成为样本WZ201804-00733-C(汪静媛)生物学父亲的遗传学条件，但是两者之间的累积亲权指数（CPI值）为305（小于10000），亲权关系概率无法达到99.99%以上，故不排除汪发良为汪静媛的生物学父亲。
			
			if(result.getRcp().doubleValue()>0.9999){
			if(dnaMutation.getLocis() != null && dnaMutation.getLocis().length() != 0){
						if(result.getChildCode().equals(dnaMutation.getCcode())&&result.getParentCode().equals(dnaMutation.getPcode())){
									value = String.format(true1+"\n", 
											result.getParentCode(),
											dnaMutation.getLocis().substring(0, dnaMutation.getLocis().length()-1).trim(),
											dnaMutation.getTotle(),
											result.getChildCode(),
											relation,
											cpi,
											rcp,
											"故"+draftValue1
											);
							}else{
								value = String.format(template+"\n", 
										  result.getParentCode(),
										  result.getChildCode(),
										  relation,
										  cpi,
										  rcp,
										  "故"+draftValue1
										  );
								
						}
			}else{
									value = String.format(template+"\n", 
									  result.getParentCode(),
									  result.getChildCode(),
									  relation,
									  cpi,
									  rcp,
									  "故"+draftValue1
									  );
							
						}
					
			}else if(result.getRcp().doubleValue()>0.9900&&result.getRcp().doubleValue()<0.9999){
				if(dnaMutation.getLocis() != null && dnaMutation.getLocis().length() != 0){
						if(result.getChildCode().equals(dnaMutation.getCcode())&&result.getParentCode().equals(dnaMutation.getPcode())){
									value = String.format(true2+"\n", 
											result.getParentCode(),
											dnaMutation.getLocis().substring(0, dnaMutation.getLocis().length()-1).trim(),
												dnaMutation.getTotle(),
											result.getChildCode(),
											relation,
											cpi,
											"99.99%",
											"故"+draftValue1
											);
							}else{
								value = String.format(template+"\n", 
										  result.getParentCode(),
										  result.getChildCode(),
										  relation,
										  cpi,
										  rcp,
										  "故"+draftValue1
										  );
								
						}
			}
		}
		else{
			if(dnaMutation.getLocis() != null && dnaMutation.getLocis().length() != 0){
				 value = String.format(template+"\n", 
						  result.getParentCode(),
						  result.getChildCode(),
						 21- dnaMutation.getTotle(),
						  dnaMutation.getLocis().substring(0, dnaMutation.getLocis().length()-1).trim(),
						  "故"+draftValue1
						  );
			}
		}
		sb.append(value);
		}
	}
		
		//MC
		for(DnaPiResult result:dnaResultList){
			 String vn= result.getChildCode().substring(0, result.getChildCode().indexOf("("));
			 String vc= result.getParentCode().substring(0, result.getParentCode().indexOf("("));
				if( vn.substring(vn.length()-1, vn.length()).equals("C")&&vc.substring(vc.length()-1, vc.length()).equals("M")){
						DnaMutation dnaMutation=	dnaPiResultService.getLoci(result.getChildCode(), result.getParentCode());
					
					String relation = DictUtils.getDictLabel(result.getParentAbstracts().getAppellation(), "appellationCode", "父亲");
					String template=result.getCpi().doubleValue()>0.99?trueTemplate:falseTemplate;
					
			if(result.getCpi().doubleValue()>0.99){
				 draftValue1= String.format(trueDratTemplate1, result.getParentAbstracts().getClientName(),result.getChildAbstracts().getClientName(),DictUtils.getDictLabel(result.getParentAbstracts().getAppellation(), "appellationCode", "父亲"));
				String parentName = result.getParentAbstracts().getClientName();
				if(trueMap.containsKey(parentName+"_"+relation )){
					trueMap.get(parentName+"_"+relation ).add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
				}else{
					List<String> list = new ArrayList<String>();
					list.add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
					trueMap.put(parentName+"_"+relation, list);
				}
			}else{
				 draftValue1= String.format(falseDratTempalte1, result.getParentAbstracts().getClientName(),result.getChildAbstracts().getClientName(),DictUtils.getDictLabel(result.getParentAbstracts().getAppellation(), "appellationCode", "父亲"));
				String parentName = result.getParentAbstracts().getClientName();
				if(falseMap.containsKey(parentName+"_"+relation )){
					falseMap.get(parentName+"_"+relation).add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
				}else{
					List<String> list = new ArrayList<String>();
					list.add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
					falseMap.put(parentName+"_"+relation, list);
				}
			}
			
			
			String zuo="";
			Integer n=0;  
			String cpi="";
			int temp=0;
		
			if(result.getCpi().doubleValue()>10000){
				String ji=String.valueOf(result.getCpi().doubleValue()).toString();
			if(ji!=null){
				if(ji.contains("E")){
					 temp=ji.indexOf("E");
					n=Integer.parseInt(ji.substring(temp+1, ji.length()));
					zuo=ji.substring(0,4);
				}
				else{
				    temp=ji.indexOf(".");
					n=temp-1;
					zuo=ji.substring(0,1)+"."+ji.substring(1,2);
				}
				
			}
			if(!zuo.equals("")&&n!=0){
				cpi=zuo+"×10^"+n;
			}
			}
			else{
				cpi=String.valueOf(result.getCpi().doubleValue()).toString();
			}
			String rcp="";
			if(result.getRcp().doubleValue()>0.9){
				rcp=String.valueOf(result.getRcp().doubleValue()*100).substring(0, 5)+"%";
			}else{
				rcp=String.valueOf(result.getRcp());
			}
			String value=null;
			String true1="<w:br/>"+"&nbsp;&nbsp;%s在除基因座%s（可能存在基因突变）外的其它%s个基因座均具备成为 %s生物学%s的遗传学条件；两者之间的累积亲权指数（CPI值）达%s以上，亲权关系概率达%s以上，%s。";
			//String true1="<w:br/>"+"&nbsp;&nbsp;1  在除基因座%s（可能存在基因突变）外的其它%s个基因座均具备成为2生物学3的遗传学条件； 两者之间的累积亲权指数（CPI值）达4，亲权关系概率达5,6";
			String true2="<w:br/>"+"&nbsp;&nbsp;%s在除基因座%s（可能存在基因突变）外的其它%s个基因座均具备成为 %s生物学%s的遗传学条件；但是两者之间的累积亲权指数（CPI值）为%s（小于10000），亲权关系概率无法达到%s以上，%s。";
			//样本WZ201804-00733-F(汪发良)在除XXX（可能存在基因突变）外的其它20个基因座均具有成为样本WZ201804-00733-C(汪静媛)生物学父亲的遗传学条件，但是两者之间的累积亲权指数（CPI值）为305（小于10000），亲权关系概率无法达到99.99%以上，故不排除汪发良为汪静媛的生物学父亲。
			
			if(result.getRcp().doubleValue()>0.9999){
			if(dnaMutation.getLocis() != null && dnaMutation.getLocis().length() != 0){
						if(result.getChildCode().equals(dnaMutation.getCcode())&&result.getParentCode().equals(dnaMutation.getPcode())){
									value = String.format(true1+"\n", 
											result.getParentCode(),
											dnaMutation.getLocis().substring(0, dnaMutation.getLocis().length()-1).trim(),
											dnaMutation.getTotle(),
											result.getChildCode(),
											relation,
											cpi,
											rcp,
											"故"+draftValue1
											);
							}else{
								value = String.format(template+"\n", 
										  result.getParentCode(),
										  result.getChildCode(),
										  relation,
										  cpi,
										  rcp,
										  "故"+draftValue1
										  );
						}
			}else{
									value = String.format(template+"\n", 
									  result.getParentCode(),
									  result.getChildCode(),
									  relation,
									  cpi,
									  rcp,
									  "故"+draftValue1
									  );
							
						}
					
			}else if(result.getRcp().doubleValue()>0.9900&&result.getRcp().doubleValue()<0.9999){
				if(dnaMutation.getLocis() != null && dnaMutation.getLocis().length() != 0){
						if(result.getChildCode().equals(dnaMutation.getCcode())&&result.getParentCode().equals(dnaMutation.getPcode())){
									value = String.format(true2+"\n", 
											result.getParentCode(),
											dnaMutation.getLocis().substring(0, dnaMutation.getLocis().length()-1).trim(),
												dnaMutation.getTotle(),
											result.getChildCode(),
											relation,
											cpi,
											"99.99%",
											"故"+draftValue1
											);
							}else{
								value = String.format(template+"\n", 
										  result.getParentCode(),
										  result.getChildCode(),
										  relation,
										  cpi,
										  rcp,
										  "故"+draftValue1
										  );
								
						}
			}
		}
		
		
		else{
			if(dnaMutation.getLocis() != null && dnaMutation.getLocis().length() != 0){
				 value = String.format(template+"\n", 
						  result.getParentCode(),
						  result.getChildCode(),
						 21- dnaMutation.getTotle(),
						  dnaMutation.getLocis().substring(0, dnaMutation.getLocis().length()-1).trim(),
						  "故"+draftValue1
						  );
			}
		}
		sb.append(value);
		}
	}
		//MC2
		for(DnaPiResult result:dnaResultList){
			 String vn= result.getChildCode().substring(0, result.getChildCode().indexOf("("));
			 String vc= result.getParentCode().substring(0, result.getParentCode().indexOf("("));
				if( vn.substring(vn.length()-2, vn.length()).equals("C2")&&vc.substring(vc.length()-1, vc.length()).equals("M")){
						DnaMutation dnaMutation=	dnaPiResultService.getLoci(result.getChildCode(), result.getParentCode());
					String relation = DictUtils.getDictLabel(result.getParentAbstracts().getAppellation(), "appellationCode", "父亲");
					String template=result.getCpi().doubleValue()>0.99?trueTemplate:falseTemplate;
					
			if(result.getCpi().doubleValue()>0.99){
				 draftValue1= String.format(trueDratTemplate1, result.getParentAbstracts().getClientName(),result.getChildAbstracts().getClientName(),DictUtils.getDictLabel(result.getParentAbstracts().getAppellation(), "appellationCode", "父亲"));
				String parentName = result.getParentAbstracts().getClientName();
				if(trueMap.containsKey(parentName+"_"+relation )){
					trueMap.get(parentName+"_"+relation ).add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
				}else{
					List<String> list = new ArrayList<String>();
					list.add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
					trueMap.put(parentName+"_"+relation, list);
				}
			}else{
				 draftValue1= String.format(falseDratTempalte1, result.getParentAbstracts().getClientName(),result.getChildAbstracts().getClientName(),DictUtils.getDictLabel(result.getParentAbstracts().getAppellation(), "appellationCode", "父亲"));
				String parentName = result.getParentAbstracts().getClientName();
				if(falseMap.containsKey(parentName+"_"+relation )){
					falseMap.get(parentName+"_"+relation).add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
				}else{
					List<String> list = new ArrayList<String>();
					list.add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
					falseMap.put(parentName+"_"+relation, list);                                                                                                                                                                                                                                                                                                                                                                                                                                                  
				}
			}
			
			
			String zuo="";
			Integer n=0;  
			String cpi="";
			int temp=0;
		
			if(result.getCpi().doubleValue()>10000){
				String ji=String.valueOf(result.getCpi().doubleValue()).toString();
			if(ji!=null){
				if(ji.contains("E")){
					 temp=ji.indexOf("E");
					n=Integer.parseInt(ji.substring(temp+1, ji.length()));
					zuo=ji.substring(0,4);
				}
				else{
				    temp=ji.indexOf(".");
					n=temp-1;
					zuo=ji.substring(0,1)+"."+ji.substring(1,2);
				}
				
			}
			if(!zuo.equals("")&&n!=0){
				cpi=zuo+"×10^"+n;
			}
			}
			else{
				cpi=String.valueOf(result.getCpi().doubleValue()).toString();
			}
			String rcp="";
			if(result.getRcp().doubleValue()>0.9){
				rcp=String.valueOf(result.getRcp().doubleValue()*100).substring(0, 5)+"%";
			}else{
				rcp=String.valueOf(result.getRcp());
			}
			String value=null;
			String true1="<w:br/>"+"&nbsp;&nbsp;%s在除基因座%s（可能存在基因突变）外的其它%s个基因座均具备成为 %s生物学%s的遗传学条件；两者之间的累积亲权指数（CPI值）达%s以上，亲权关系概率达%s以上，%s。";
			//String true1="<w:br/>"+"&nbsp;&nbsp;1  在除基因座%s（可能存在基因突变）外的其它%s个基因座均具备成为2生物学3的遗传学条件； 两者之间的累积亲权指数（CPI值）达4，亲权关系概率达5,6";
			String true2="<w:br/>"+"&nbsp;&nbsp;%s在除基因座%s（可能存在基因突变）外的其它%s个基因座均具备成为 %s生物学%s的遗传学条件；但是两者之间的累积亲权指数（CPI值）为%s（小于10000），亲权关系概率无法达到%s以上，%s。";
			//样本WZ201804-00733-F(汪发良)在除XXX（可能存在基因突变）外的其它20个基因座均具有成为样本WZ201804-00733-C(汪静媛)生物学父亲的遗传学条件，但是两者之间的累积亲权指数（CPI值）为305（小于10000），亲权关系概率无法达到99.99%以上，故不排除汪发良为汪静媛的生物学父亲。
			
			if(result.getRcp().doubleValue()>0.9999){
			if(dnaMutation.getLocis() != null && dnaMutation.getLocis().length() != 0){
						if(result.getChildCode().equals(dnaMutation.getCcode())&&result.getParentCode().equals(dnaMutation.getPcode())){
									value = String.format(true1+"\n", 
											result.getParentCode(),
											dnaMutation.getLocis().substring(0, dnaMutation.getLocis().length()-1).trim(),
											dnaMutation.getTotle(),
											result.getChildCode(),
											relation,
											cpi,
											rcp,
											"故"+draftValue1
											);
							}else{
								value = String.format(template+"\n", 
										  result.getParentCode(),
										  result.getChildCode(),
										  relation,
										  cpi,
										  rcp,
										  "故"+draftValue1
										  );
								
						}
			}else{
									value = String.format(template+"\n", 
									  result.getParentCode(),
									  result.getChildCode(),
									  relation,
									  cpi,
									  rcp,
									  "故"+draftValue1
									  );
							
						}
					
			}else if(result.getRcp().doubleValue()>0.9900&&result.getRcp().doubleValue()<0.9999){
				if(dnaMutation.getLocis() != null && dnaMutation.getLocis().length() != 0){
						if(result.getChildCode().equals(dnaMutation.getCcode())&&result.getParentCode().equals(dnaMutation.getPcode())){
									value = String.format(true2+"\n", 
											result.getParentCode(),
											dnaMutation.getLocis().substring(0, dnaMutation.getLocis().length()-1).trim(),
												dnaMutation.getTotle(),
											result.getChildCode(),
											relation,
											cpi,
											"99.99%",
											"故"+draftValue1
											);
							}else{
								value = String.format(template+"\n", 
										  result.getParentCode(),
										  result.getChildCode(),
										  relation,
										  cpi,
										  rcp,
										  "故"+draftValue1
										  );
								
							}
			}
		}
		else{
			if(dnaMutation.getLocis() != null && dnaMutation.getLocis().length() != 0){
				 value = String.format(template+"\n", 
						  result.getParentCode(),
						  result.getChildCode(),
						 21- dnaMutation.getTotle(),
						  dnaMutation.getLocis().substring(0, dnaMutation.getLocis().length()-1).trim(),
						  "故"+draftValue1
						  );
			}
		}
		sb.append(value);
		}
	}
		//MC3
		
		for(DnaPiResult result:dnaResultList){
			 String vn= result.getChildCode().substring(0, result.getChildCode().indexOf("("));
			 String vc= result.getParentCode().substring(0, result.getParentCode().indexOf("("));
				if( vn.substring(vn.length()-2, vn.length()).equals("C3")&&vc.substring(vc.length()-1, vc.length()).equals("M")){
						DnaMutation dnaMutation=	dnaPiResultService.getLoci(result.getChildCode(), result.getParentCode());
					//if( vn.substring(vn.length()-2, vn.length()).equals("C2")&&vc.substring(vc.length()-1, vc.length()).equals("M")){
					
					String relation = DictUtils.getDictLabel(result.getParentAbstracts().getAppellation(), "appellationCode", "父亲");
					String template=result.getCpi().doubleValue()>0.99?trueTemplate:falseTemplate;
					
			if(result.getCpi().doubleValue()>0.99){
				 draftValue1= String.format(trueDratTemplate1, result.getParentAbstracts().getClientName(),result.getChildAbstracts().getClientName(),DictUtils.getDictLabel(result.getParentAbstracts().getAppellation(), "appellationCode", "父亲"));
				String parentName = result.getParentAbstracts().getClientName();
				if(trueMap.containsKey(parentName+"_"+relation )){
					trueMap.get(parentName+"_"+relation ).add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
				}else{
					List<String> list = new ArrayList<String>();
					list.add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
					trueMap.put(parentName+"_"+relation, list);
				}
			}else{
				 draftValue1= String.format(falseDratTempalte1, result.getParentAbstracts().getClientName(),result.getChildAbstracts().getClientName(),DictUtils.getDictLabel(result.getParentAbstracts().getAppellation(), "appellationCode", "父亲"));
				String parentName = result.getParentAbstracts().getClientName();
				if(falseMap.containsKey(parentName+"_"+relation )){
					falseMap.get(parentName+"_"+relation).add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
				}else{
					List<String> list = new ArrayList<String>();
					list.add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
					falseMap.put(parentName+"_"+relation, list);
				}
			}
			
			
			String zuo="";
			Integer n=0;  
			String cpi="";
			int temp=0;
		
			if(result.getCpi().doubleValue()>10000){
				String ji=String.valueOf(result.getCpi().doubleValue()).toString();
			if(ji!=null){
				if(ji.contains("E")){
					 temp=ji.indexOf("E");
					n=Integer.parseInt(ji.substring(temp+1, ji.length()));
					zuo=ji.substring(0,4);
				}
				else{
				    temp=ji.indexOf(".");
					n=temp-1;
					zuo=ji.substring(0,1)+"."+ji.substring(1,2);
				}
				
			}
			if(!zuo.equals("")&&n!=0){
				cpi=zuo+"×10^"+n;
			}
			}
			else{
				cpi=String.valueOf(result.getCpi().doubleValue()).toString();
			}
			String rcp="";
			if(result.getRcp().doubleValue()>0.9){
				rcp=String.valueOf(result.getRcp().doubleValue()*100).substring(0, 5)+"%";
			}else{
				rcp=String.valueOf(result.getRcp());
			}
			String value=null;
			String true1="<w:br/>"+"&nbsp;&nbsp;%s在除基因座%s外的其它%s个基因座均具备成为 %s生物学%s的遗传学条件；两者之间的累积亲权指数（CPI值）达%s以上，亲权关系概率达%s以上，%s。";
			//String true1="<w:br/>"+"&nbsp;&nbsp;1  在除基因座%s（可能存在基因突变）外的其它%s个基因座均具备成为2生物学3的遗传学条件； 两者之间的累积亲权指数（CPI值）达4，亲权关系概率达5,6";
			String true2="<w:br/>"+"&nbsp;&nbsp;%s在除基因座%s外的其它%s个基因座均具备成为 %s生物学%s的遗传学条件；但是两者之间的累积亲权指数（CPI值）为%s（小于10000），亲权关系概率无法达到%s以上，%s。";
			//样本WZ201804-00733-F(汪发良)在除XXX（可能存在基因突变）外的其它20个基因座均具有成为样本WZ201804-00733-C(汪静媛)生物学父亲的遗传学条件，但是两者之间的累积亲权指数（CPI值）为305（小于10000），亲权关系概率无法达到99.99%以上，故不排除汪发良为汪静媛的生物学父亲。
			
			if(result.getRcp().doubleValue()>0.9999){
			if(dnaMutation.getLocis() != null && dnaMutation.getLocis().length() != 0){
						if(result.getChildCode().equals(dnaMutation.getCcode())&&result.getParentCode().equals(dnaMutation.getPcode())){
									value = String.format(true1+"\n",  
											result.getParentCode(),
											dnaMutation.getLocis().substring(0, dnaMutation.getLocis().length()-1).trim(),
											dnaMutation.getTotle(),
											result.getChildCode(),
											relation,
											cpi,
											rcp,
											"故"+draftValue1
											);
							}else{
								value = String.format(template+"\n", 
										  result.getParentCode(),
										  result.getChildCode(),
										  relation,
										  cpi,
										  rcp,
										  "故"+draftValue1
										  );
								
						}
			}else{
									value = String.format(template+"\n", 
									  result.getParentCode(),
									  result.getChildCode(),
									  relation,
									  cpi,
									  rcp,
									  "故"+draftValue1
									  );
							
						}
					
			}else if(result.getRcp().doubleValue()>0.9900&&result.getRcp().doubleValue()<0.9999){
				if(dnaMutation.getLocis() != null && dnaMutation.getLocis().length() != 0){
						if(result.getChildCode().equals(dnaMutation.getCcode())&&result.getParentCode().equals(dnaMutation.getPcode())){
									value = String.format(true2+"\n", 
											result.getParentCode(),
											dnaMutation.getLocis().substring(0, dnaMutation.getLocis().length()-1).trim(),
												dnaMutation.getTotle(),
											result.getChildCode(),
											relation,
											cpi,
											"99.99%",
											"故"+draftValue1
											);
							}else{
								value = String.format(template+"\n", 
										  result.getParentCode(),
										  result.getChildCode(),
										  relation,
										  cpi,
										  rcp,
										  "故"+draftValue1
										  );
								
						}
			}
		}
		else{
			if(dnaMutation.getLocis() != null && dnaMutation.getLocis().length() != 0){
				 value = String.format(template+"\n", 
						  result.getParentCode(),
						  result.getChildCode(),
						21-  dnaMutation.getTotle(),
						  dnaMutation.getLocis().substring(0, dnaMutation.getLocis().length()-1).trim(),
						  "故"+draftValue1
						  );
			}
		}
		sb.append(value);
		}
	}
		
		//MC4
		
		for(DnaPiResult result:dnaResultList){
			 String vn= result.getChildCode().substring(0, result.getChildCode().indexOf("("));
			 String vc= result.getParentCode().substring(0, result.getParentCode().indexOf("("));
				if( vn.substring(vn.length()-2, vn.length()).equals("C4")&&vc.substring(vc.length()-1, vc.length()).equals("M")){
						DnaMutation dnaMutation=	dnaPiResultService.getLoci(result.getChildCode(), result.getParentCode());
					String relation = DictUtils.getDictLabel(result.getParentAbstracts().getAppellation(), "appellationCode", "父亲");
					String template=result.getCpi().doubleValue()>0.99?trueTemplate:falseTemplate;
					
			if(result.getCpi().doubleValue()>0.99){
				 draftValue1= String.format(trueDratTemplate1, result.getParentAbstracts().getClientName(),result.getChildAbstracts().getClientName(),DictUtils.getDictLabel(result.getParentAbstracts().getAppellation(), "appellationCode", "父亲"));
				String parentName = result.getParentAbstracts().getClientName();
				if(trueMap.containsKey(parentName+"_"+relation )){
					trueMap.get(parentName+"_"+relation ).add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
				}else{
					List<String> list = new ArrayList<String>();
					list.add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
					trueMap.put(parentName+"_"+relation, list);
				}
			}else{
				 draftValue1= String.format(falseDratTempalte1, result.getParentAbstracts().getClientName(),result.getChildAbstracts().getClientName(),DictUtils.getDictLabel(result.getParentAbstracts().getAppellation(), "appellationCode", "父亲"));
				String parentName = result.getParentAbstracts().getClientName();
				if(falseMap.containsKey(parentName+"_"+relation )){
					falseMap.get(parentName+"_"+relation).add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
				}else{
					List<String> list = new ArrayList<String>();
					list.add(result.getChildAbstracts().getClientName()+"_"+result.getChildAbstracts().getSpecimenCode());
					falseMap.put(parentName+"_"+relation, list);
				}
			}
			
			
			String zuo="";
			Integer n=0;  
			String cpi="";
			int temp=0;
		
			if(result.getCpi().doubleValue()>10000){
				String ji=String.valueOf(result.getCpi().doubleValue()).toString();
			if(ji!=null){
				if(ji.contains("E")){
					 temp=ji.indexOf("E");
					n=Integer.parseInt(ji.substring(temp+1, ji.length()));
					zuo=ji.substring(0,4);
				}
				else{
				    temp=ji.indexOf(".");
					n=temp-1;
					zuo=ji.substring(0,1)+"."+ji.substring(1,2);
				}
				
			}
			if(!zuo.equals("")&&n!=0){
				cpi=zuo+"×10^"+n;
			}
			}
			else{
				cpi=String.valueOf(result.getCpi().doubleValue()).toString();
			}
			String rcp="";
			if(result.getRcp().doubleValue()>0.9){
				rcp=String.valueOf(result.getRcp().doubleValue()*100).substring(0, 5)+"%";
			}else{
				rcp=String.valueOf(result.getRcp());
			}
			String value=null;
			String true1="<w:br/>"+"&nbsp;&nbsp;%s在除基因座%s（可能存在基因突变）外的其它%s个基因座均具备成为 %s生物学%s的遗传学条件；两者之间的累积亲权指数（CPI值）达%s以上，亲权关系概率达%s以上，%s。";
			//String true1="<w:br/>"+"&nbsp;&nbsp;1  在除基因座%s（可能存在基因突变）外的其它%s个基因座均具备成为2生物学3的遗传学条件； 两者之间的累积亲权指数（CPI值）达4，亲权关系概率达5,6";
			String true2="<w:br/>"+"&nbsp;&nbsp;%s在除基因座%s（可能存在基因突变）外的其它%s个基因座均具备成为 %s生物学%s的遗传学条件；但是两者之间的累积亲权指数（CPI值）为%s（小于10000），亲权关系概率无法达到%s以上，%s。";
			//样本WZ201804-00733-F(汪发良)在除XXX（可能存在基因突变）外的其它20个基因座均具有成为样本WZ201804-00733-C(汪静媛)生物学父亲的遗传学条件，但是两者之间的累积亲权指数（CPI值）为305（小于10000），亲权关系概率无法达到99.99%以上，故不排除汪发良为汪静媛的生物学父亲。
			
			if(result.getRcp().doubleValue()>0.9999){
			if(dnaMutation.getLocis() != null && dnaMutation.getLocis().length() != 0){
						if(result.getChildCode().equals(dnaMutation.getCcode())&&result.getParentCode().equals(dnaMutation.getPcode())){
									value = String.format(true1+"\n", 
											result.getParentCode(),
											dnaMutation.getLocis().substring(0, dnaMutation.getLocis().length()-1).trim(),
											dnaMutation.getTotle(),
											result.getChildCode(),
											relation,
											cpi,
											rcp,
											"故"+draftValue1
											);
							}else{
								value = String.format(template+"\n", 
										  result.getParentCode(),
										  result.getChildCode(),
										  relation,
										  cpi,
										  rcp,
										  "故"+draftValue1
										  );
								
						}
			}else{
									value = String.format(template+"\n", 
									  result.getParentCode(),
									  result.getChildCode(),
									  relation,
									  cpi,
									  rcp,
									  "故"+draftValue1
									  );
							
						}
					
			}else if(result.getRcp().doubleValue()>0.9900&&result.getRcp().doubleValue()<0.9999){
				if(dnaMutation.getLocis() != null && dnaMutation.getLocis().length() != 0){
						if(result.getChildCode().equals(dnaMutation.getCcode())&&result.getParentCode().equals(dnaMutation.getPcode())){
									value = String.format(true2+"\n", 
											result.getParentCode(),
											dnaMutation.getLocis().substring(0, dnaMutation.getLocis().length()-1).trim(),
												dnaMutation.getTotle(),
											result.getChildCode(),
											relation,
											cpi,
											"99.99%",
											"故"+draftValue1
											);
							}else{
								value = String.format(template+"\n", 
										  result.getParentCode(),
										  result.getChildCode(),
										  relation,
										  cpi,
										  rcp,
										  "故"+draftValue1
										  );
								
						}
			}
			}
		
		
		
		
		else{
			if(dnaMutation.getLocis() != null && dnaMutation.getLocis().length() != 0){
				 value = String.format(template+"\n", 
						  result.getParentCode(),
						  result.getChildCode(),
						21-  dnaMutation.getTotle(),
						  dnaMutation.getLocis().substring(0, dnaMutation.getLocis().length()-1).trim(),
						  "故"+draftValue1
						  );
			}
		}
		sb.append(value);
		}
		}
			
		String draftValue="";
		List<String>chirdsList=new ArrayList<String>();
		List<String>chirdsList1=new ArrayList<String>();
	
		
		if(!trueMap.isEmpty()){
			for(String key : trueMap.keySet()){     
				valueKey.addAll(trueMap.get(key));
			}
			valueKey=	removeDuplicate(valueKey);
			for (String chirdsNames : valueKey) {
				if(!StringUtils.isEmpty(chirdsNames)||chirdsNames!=null){
					chirdsList.add(chirdsNames);
				}
			}
		}
		if(!trueMap.isEmpty()){
				for (String string : trueMap.keySet()){
					if(string.substring(string.length()-2,string.length()).equals("父亲")){
						fathersNames=string;
					}else{
						mathersNames=string;
					}
				}
		}		
		
		
		
		List<String>chirdfalse=new ArrayList<String>();
		String fathersNamesfalse="";
		String mathersNamesfalse="";
		 
		
		if(!falseMap.isEmpty()){
			for(String key : falseMap.keySet()){                                                                                                                                                                                                                                                                                                                          
				valueKey= falseMap.get(key); 
			}
			for (String chirdsNames : valueKey) {
				if(!StringUtils.isEmpty(chirdsNames)||chirdsNames!=null){
					chirdfalse.add(chirdsNames);
				}
			}
		}
		for (String string : falseMap.keySet()){
			if(string.substring(string.length()-2,string.length()).equals("父亲")){
				fathersNamesfalse=string;
			}else{
				mathersNamesfalse=string;
			}
		}
		
		
		
		
		List<String>chirdTrue1=new ArrayList<String>();
		String fathersTrue1="";
		String mathersTrue1="";
		 
		
		if(!trueMap1.isEmpty()){
			for(String key : trueMap1.keySet()){                                                                                                                                                                                                                                                                                                                          
				valueKey= trueMap1.get(key); 
			}
			for (String chirdsNames : valueKey) {
				if(!StringUtils.isEmpty(chirdsNames)||chirdsNames!=null){
					chirdTrue1.add(chirdsNames);
				}
			}
		}
		for (String string : trueMap1.keySet()){
			if(string.substring(string.length()-2,string.length()).equals("父亲")){
				fathersTrue1=string;
			}else{
				mathersTrue1=string;
			}
		}
		
		
		//不排除
		String trueDratTemplate3 = "不排除%s为%s的生物学%s\n";
		for (String chid : chirdTrue1) {
			if(!StringUtils.isEmpty(fathersTrue1)){
					if(fathersTrue1.substring(fathersTrue1.length()-2,fathersTrue1.length()).equals("父亲")){
						if(chid.substring(chid.length()-1, chid.length()).equals("C")){
							draftValue+= String.format(trueDratTemplate3,fathersTrue1.substring(0, fathersTrue1.indexOf("_")),chid.substring(0, chid.indexOf("_")),fathersTrue1.substring(fathersTrue1.length()-2,fathersTrue1.length()))+"\n";
						}
					}
				}
			}
		for (String chid : chirdTrue1) {
			if(!StringUtils.isEmpty(fathersTrue1)){
					if(fathersTrue1.substring(fathersTrue1.length()-2,fathersTrue1.length()).equals("父亲")){
						if(chid.substring(chid.length()-2, chid.length()).equals("C2")){
							draftValue+= String.format(trueDratTemplate3,fathersTrue1.substring(0, fathersTrue1.indexOf("_")),chid.substring(0, chid.indexOf("_")),fathersTrue1.substring(fathersTrue1.length()-2,fathersTrue1.length()))+"\n";
						}
					}
				}
			}		
		
		for (String chid : chirdTrue1) {
			if(!StringUtils.isEmpty(fathersTrue1)){
					if(fathersTrue1.substring(fathersTrue1.length()-2,fathersTrue1.length()).equals("父亲")){
						if(chid.substring(chid.length()-2, chid.length()).equals("C3")){
							draftValue+= String.format(trueDratTemplate3,fathersTrue1.substring(0, fathersTrue1.indexOf("_")),chid.substring(0, chid.indexOf("_")),fathersTrue1.substring(fathersTrue1.length()-2,fathersTrue1.length()))+"\n";
						}
					}
				}
			}	
		
		
		for (String chid : chirdTrue1) {
			if(!StringUtils.isEmpty(fathersTrue1)){
					if(fathersTrue1.substring(fathersTrue1.length()-2,fathersTrue1.length()).equals("父亲")){
						if(chid.substring(chid.length()-2, chid.length()).equals("C4")){
							draftValue+= String.format(trueDratTemplate3,fathersTrue1.substring(0, fathersTrue1.indexOf("_")),chid.substring(0, chid.indexOf("_")),fathersTrue1.substring(fathersTrue1.length()-2,fathersTrue1.length()))+"\n";
						}
					}
				}
			}	
			
				
		for (String chid : chirdTrue1) {
			if(!StringUtils.isEmpty(mathersTrue1)){
					if(mathersTrue1.substring(mathersTrue1.length()-2,mathersTrue1.length()).equals("母亲")){
						if(chid.substring(chid.length()-1, chid.length()).equals("C")){
							draftValue+= String.format(trueDratTemplate3,mathersTrue1.substring(0, mathersTrue1.indexOf("_")),chid.substring(0, chid.indexOf("_")),mathersTrue1.substring(mathersTrue1.length()-2,mathersTrue1.length()))+"\n";
						}
					}
				}
			}
		
		for (String chid : chirdTrue1) {
			if(!StringUtils.isEmpty(mathersTrue1)){
					if(mathersTrue1.substring(mathersTrue1.length()-2,mathersTrue1.length()).equals("母亲")){
						if(chid.substring(chid.length()-2, chid.length()).equals("C2")){
							draftValue+= String.format(trueDratTemplate3,mathersTrue1.substring(0, mathersTrue1.indexOf("_")),chid.substring(0, chid.indexOf("_")),mathersTrue1.substring(mathersTrue1.length()-2,mathersTrue1.length()))+"\n";
						}
					}
				}
			}
		for (String chid : chirdTrue1) {
			if(!StringUtils.isEmpty(mathersTrue1)){
					if(mathersTrue1.substring(mathersTrue1.length()-2,mathersTrue1.length()).equals("母亲")){
						if(chid.substring(chid.length()-2, chid.length()).equals("C3")){
							draftValue+= String.format(trueDratTemplate3,mathersTrue1.substring(0, mathersTrue1.indexOf("_")),chid.substring(0, chid.indexOf("_")),mathersTrue1.substring(mathersTrue1.length()-2,mathersTrue1.length()))+"\n";
						}
					}
				}
			}
		for (String chid : chirdTrue1) {
			if(!StringUtils.isEmpty(mathersTrue1)){
					if(mathersTrue1.substring(mathersTrue1.length()-2,mathersTrue1.length()).equals("母亲")){
						if(chid.substring(chid.length()-2, chid.length()).equals("C4")){
							draftValue+= String.format(trueDratTemplate3,mathersTrue1.substring(0, mathersTrue1.indexOf("_")),chid.substring(0, chid.indexOf("_")),mathersTrue1.substring(mathersTrue1.length()-2,mathersTrue1.length()))+"\n";
						}
					}
				}
			}
		
		
		
		
		
	
				
			
					//支持
		
				
					if(!StringUtils.isEmpty(fathersNames)){
							if(fathersNames.substring(fathersNames.length()-2,fathersNames.length()).equals("父亲")){
								for(String f_父亲 : trueMap.keySet()){                                                                                                                                                                                                                                                                                                                          
									chirdsList1= trueMap.get(f_父亲); 
								}
								for (String chid : chirdsList1) {
									if(chid.substring(chid.length()-1, chid.length()).equals("C")){
										draftValue+= String.format(trueDratTemplate,fathersNames.substring(0, fathersNames.indexOf("_")),chid.substring(0, chid.indexOf("_")),fathersNames.substring(fathersNames.length()-2,fathersNames.length()))+"\n";
									   break;
									}
								}
							}
						}
					
					
					
					
					
					
					
					//排除
					
					for (String chid : chirdfalse) {
						if(!StringUtils.isEmpty(fathersNamesfalse)){
							if(fathersNamesfalse.substring(fathersNamesfalse.length()-2,fathersNamesfalse.length()).equals("父亲")){
								if(chid.substring(chid.length()-1, chid.length()).equals("C")){
									draftValue+= String.format(falseDratTempalte,fathersNamesfalse.substring(0, fathersNamesfalse.indexOf("_")),chid.substring(0, chid.indexOf("_")),fathersNamesfalse.substring(fathersNamesfalse.length()-2,fathersNamesfalse.length()))+"\n";
								}
							}
						}
					}
					
					//支持
					
					
					if(!StringUtils.isEmpty(fathersNames)){
						if(fathersNames.substring(fathersNames.length()-2,fathersNames.length()).equals("父亲")){
							for (String chid : chirdsList1) {
								if(chid.substring(chid.length()-2, chid.length()).equals("C2")){
									draftValue+= String.format(trueDratTemplate,fathersNames.substring(0, fathersNames.indexOf("_")),chid.substring(0, chid.indexOf("_")),fathersNames.substring(fathersNames.length()-2,fathersNames.length()))+"\n";
								   break;
								}
							}
						}
					}
					
					
							
							
							
								
						
					for (String chid : chirdfalse) {
						if(!StringUtils.isEmpty(fathersNamesfalse)){
							if(fathersNamesfalse.substring(fathersNamesfalse.length()-2,fathersNamesfalse.length()).equals("父亲")){
								if(chid.substring(chid.length()-2, chid.length()).equals("C2")){
									draftValue+= String.format(falseDratTempalte,fathersNamesfalse.substring(0, fathersNamesfalse.indexOf("_")),chid.substring(0, chid.indexOf("_")),fathersNamesfalse.substring(fathersNamesfalse.length()-2,fathersNamesfalse.length()))+"\n";
								}
							}
						}
					}
					
					
					if(!StringUtils.isEmpty(fathersNames)){
						if(fathersNames.substring(fathersNames.length()-2,fathersNames.length()).equals("父亲")){
							for (String chid : chirdsList1) {
								if(chid.substring(chid.length()-2, chid.length()).equals("C3")){
									draftValue+= String.format(trueDratTemplate,fathersNames.substring(0, fathersNames.indexOf("_")),chid.substring(0, chid.indexOf("_")),fathersNames.substring(fathersNames.length()-2,fathersNames.length()))+"\n";
								   break;
								}
							}
						}
					}
				
					
					
					
					for (String chid : chirdfalse) {
						if(!StringUtils.isEmpty(fathersNamesfalse)){
							if(fathersNamesfalse.substring(fathersNamesfalse.length()-2,fathersNamesfalse.length()).equals("父亲")){
								if(chid.substring(chid.length()-2, chid.length()).equals("C3")){
									draftValue+= String.format(falseDratTempalte,fathersNamesfalse.substring(0, fathersNamesfalse.indexOf("_")),chid.substring(0, chid.indexOf("_")),fathersNamesfalse.substring(fathersNamesfalse.length()-2,fathersNamesfalse.length()))+"\n";
								}
							}
						}
					}
					
					if(!StringUtils.isEmpty(fathersNames)){
						if(fathersNames.substring(fathersNames.length()-2,fathersNames.length()).equals("父亲")){
							for (String chid : chirdsList1) {
								if(chid.substring(chid.length()-2, chid.length()).equals("C4")){
									draftValue+= String.format(trueDratTemplate,fathersNames.substring(0, fathersNames.indexOf("_")),chid.substring(0, chid.indexOf("_")),fathersNames.substring(fathersNames.length()-2,fathersNames.length()))+"\n";
								   break;
								}
							}
						}
					}
				
					
					
					
					for (String chid : chirdfalse) {
						if(!StringUtils.isEmpty(fathersNamesfalse)){
							if(fathersNamesfalse.substring(fathersNamesfalse.length()-2,fathersNamesfalse.length()).equals("父亲")){
								if(chid.substring(chid.length()-2, chid.length()).equals("C4")){
									draftValue+= String.format(falseDratTempalte,fathersNamesfalse.substring(0, fathersNamesfalse.indexOf("_")),chid.substring(0, chid.indexOf("_")),fathersNamesfalse.substring(fathersNamesfalse.length()-2,fathersNamesfalse.length()))+"\n";
								}
							}
						}
					}
					
					
					List<String>chirdsList2=new ArrayList<String>();
					if(!StringUtils.isEmpty(mathersNames)){
						if(mathersNames.substring(mathersNames.length()-2,mathersNames.length()).equals("母亲")){
							for(String m_母亲 : trueMap.keySet()){                                                                                                                                                                                                                                                                                                                          
								chirdsList2.addAll( trueMap.get(m_母亲)); 
							}
							chirdsList2=	removeDuplicate(chirdsList2);
							for (String chid : chirdsList2) {
								if(chid.substring(chid.length()-1, chid.length()).equals("C")){
									draftValue+= String.format(trueDratTemplate,mathersNames.substring(0, mathersNames.indexOf("_")),chid.substring(0, chid.indexOf("_")),mathersNames.substring(mathersNames.length()-2,mathersNames.length()))+"\n";
								   break;
								}
							}
						}
					}
				
					
				
					
					
					
					
					for (String chid : chirdfalse) {
						if(!StringUtils.isEmpty(mathersNamesfalse)){
							if(mathersNamesfalse.substring(mathersNamesfalse.length()-2,mathersNamesfalse.length()).equals("母亲")){
								if(chid.substring(chid.length()-1, chid.length()).equals("C")){
									draftValue+= String.format(falseDratTempalte,mathersNamesfalse.substring(0, mathersNamesfalse.indexOf("_")),chid.substring(0, chid.indexOf("_")),mathersNamesfalse.substring(mathersNamesfalse.length()-2,mathersNamesfalse.length()))+"\n";
								}
							}
						}
					}
					
					
					if(!StringUtils.isEmpty(mathersNames)){
						if(mathersNames.substring(mathersNames.length()-2,mathersNames.length()).equals("母亲")){
							for (String chid : chirdsList2) {
								if(chid.substring(chid.length()-2, chid.length()).equals("C2")){
									draftValue+= String.format(trueDratTemplate,mathersNames.substring(0, mathersNames.indexOf("_")),chid.substring(0, chid.indexOf("_")),mathersNames.substring(mathersNames.length()-2,mathersNames.length()))+"\n";
								   break;
								}
							}
						}
					}
					
					
					for (String chid : chirdfalse) {
						if(!StringUtils.isEmpty(mathersNamesfalse)){
							if(mathersNamesfalse.substring(mathersNamesfalse.length()-2,mathersNamesfalse.length()).equals("母亲")){
								if(chid.substring(chid.length()-2, chid.length()).equals("C2")){
									draftValue+= String.format(falseDratTempalte,mathersNamesfalse.substring(0, mathersNamesfalse.indexOf("_")),chid.substring(0, chid.indexOf("_")),mathersNamesfalse.substring(mathersNamesfalse.length()-2,mathersNamesfalse.length()))+"\n";
								}
							}
						}
					}
					
					if(!StringUtils.isEmpty(mathersNames)){
						if(mathersNames.substring(mathersNames.length()-2,mathersNames.length()).equals("母亲")){
							
							for (String chid : chirdsList2) {
								if(chid.substring(chid.length()-2, chid.length()).equals("C3")){
									draftValue+= String.format(trueDratTemplate,mathersNames.substring(0, mathersNames.indexOf("_")),chid.substring(0, chid.indexOf("_")),mathersNames.substring(mathersNames.length()-2,mathersNames.length()))+"\n";
								   break;
								}
							}
						}
					}
					
				
					for (String chid : chirdfalse) {
						if(!StringUtils.isEmpty(mathersNamesfalse)){
							if(mathersNamesfalse.substring(mathersNamesfalse.length()-2,mathersNamesfalse.length()).equals("母亲")){
								if(chid.substring(chid.length()-2, chid.length()).equals("C3")){
									draftValue+= String.format(falseDratTempalte,mathersNamesfalse.substring(0, mathersNamesfalse.indexOf("_")),chid.substring(0, chid.indexOf("_")),mathersNamesfalse.substring(mathersNamesfalse.length()-2,mathersNamesfalse.length()))+"\n";
								}
							}
						}
					}
					if(!StringUtils.isEmpty(mathersNames)){
						if(mathersNames.substring(mathersNames.length()-2,mathersNames.length()).equals("母亲")){
							for (String chid : chirdsList2) {
								if(chid.substring(chid.length()-2, chid.length()).equals("C4")){
									draftValue+= String.format(trueDratTemplate,mathersNames.substring(0, mathersNames.indexOf("_")),chid.substring(0, chid.indexOf("_")),mathersNames.substring(mathersNames.length()-2,mathersNames.length()))+"\n";
								   break;
								}
							}
						}
					}
		

					for (String chid : chirdfalse) {
						if(!StringUtils.isEmpty(mathersNamesfalse)){
							if(mathersNamesfalse.substring(mathersNamesfalse.length()-2,mathersNamesfalse.length()).equals("母亲")){
								if(chid.substring(chid.length()-2, chid.length()).equals("C4")){
									draftValue+= String.format(falseDratTempalte,mathersNamesfalse.substring(0, mathersNamesfalse.indexOf("_")),chid.substring(0, chid.indexOf("_")),mathersNamesfalse.substring(mathersNamesfalse.length()-2,mathersNamesfalse.length()))+"\n";
								}
							}
						}
					}
		draft.append(draftValue);
   		explain[1] = sb.toString();
		explain[0]=draft.toString();
		return explain;
	}
	
	@RequiresPermissions("entrust:entrustExpertOpinion:edit")
	@RequestMapping(value = "save")
	public String save(EntrustExpertOpinion entrustExpertOpinion, String[] noCode, HttpServletRequest request,Model model, RedirectAttributes redirectAttributes,String userBy) {
		
		String taskKey = entrustExpertOpinion.getAct().getTaskDefKey();
		
		if(!StringUtils.isEmpty(entrustExpertOpinion.getDraftRemark())){
			entrustExpertOpinion.setDraftRemark(StringEscapeUtils.unescapeHtml4(entrustExpertOpinion.getDraftRemark()).trim());
			entrustExpertOpinion.setExplainRemark(StringEscapeUtils.unescapeHtml4(entrustExpertOpinion.getExplainRemark().trim()));
			//实验底稿
			EntrustModifyrecord entrustModifyrecord=new EntrustModifyrecord();
			entrustModifyrecord.setRegister(entrustExpertOpinion.getRegister());
			entrustModifyrecord.setModefy(StringEscapeUtils.unescapeHtml4(entrustExpertOpinion.getExplainRemark().trim()));
			entrustModifyrecord.setUserby(UserUtils.getUser().getName());
			entrustModifyrecordService.save(entrustModifyrecord);
		}
		if(StringUtils.isEmpty(entrustExpertOpinion.getDraftRemark())){
			//实验底稿
			EntrustModifyrecord entrustModifyrecord=new EntrustModifyrecord();
			entrustModifyrecord.setRegister(entrustExpertOpinion.getRegister());
			entrustModifyrecord.setModefy(entrustExpertOpinion.getExplain());
			entrustModifyrecord.setUserby(UserUtils.getUser().getName());
			entrustModifyrecordService.save(entrustModifyrecord);
		}
		//三人
		Licensed licensed=new Licensed();
		licensed.setEntrustId(entrustExpertOpinion.getRegister().getId());
		licensed.setUser(UserUtils.getUser());
		licensed.setUserBy(UserUtils.getUser().getLoginName());
		licensedService.save(licensed);
		if (!beanValidator(model, entrustExpertOpinion)) { 
			return form(entrustExpertOpinion, model);
		}
		entrustExpertOpinionService.save(entrustExpertOpinion);
		addMessage(redirectAttributes, "保存鉴定意见成功");
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	@RequiresPermissions("entrust:entrustExpertOpinion:edit")
	@RequestMapping(value = "delete")
	public String delete(EntrustExpertOpinion entrustExpertOpinion, RedirectAttributes redirectAttributes) {
		entrustExpertOpinionService.delete(entrustExpertOpinion);
		addMessage(redirectAttributes, "删除鉴定意见成功");
		return "redirect:"+Global.getAdminPath()+"/entrust/entrustExpertOpinion/?repage";
	}
	
	
	public void autoCalculatePi2(EntrustRegister entrustRegister) {
		List<EntrustAbstracts> parentList = new ArrayList<EntrustAbstracts>();
		List<EntrustAbstracts> childrenList = new ArrayList<EntrustAbstracts>();
		List<EntrustAbstracts> abstractsList = entrustAbstractsDao.findList(new EntrustAbstracts(entrustRegister));
		for (EntrustAbstracts abstracts : abstractsList) {
			switch (Integer.parseInt(abstracts.getAppellation())) {
			case 1:
				parentList.add(abstracts);
				break;
			case 2:
				parentList.add(abstracts);
				break;
			case 4:
				parentList.add(abstracts);
				break;
			case 3:
				childrenList.add(abstracts);
				break;
			default:
				break;
			}
		}

		// 交叉生成结果
	for (EntrustAbstracts child : childrenList) {
		for (EntrustAbstracts parent : parentList) {
			List<DnaExperimentStr> parentStrList = dnaExperimentStrDao
					.getByExperimentIdAndAbstractsId(parent.getDnaExperimentId(), parent.getId());
			BigDecimal cpi = new BigDecimal(1);
				DnaPiResult dnaPiResult = new DnaPiResult();
				dnaPiResult.setParentCode("样本" + parent.getSpecimenCode() + "(" + parent.getClientName() + ")");
				dnaPiResult.setChildCode("样本" + child.getSpecimenCode() + "(" + child.getClientName() + ")");
				dnaPiResult.setRegister(entrustRegister);
				List<DnaPiResultItem> dnaPiResultItemList = new ArrayList<DnaPiResultItem>();

				List<DnaExperimentStr> childStrList = dnaExperimentStrDao
						.getByExperimentIdAndAbstractsId(child.getDnaExperimentId(), child.getId());
				// 保存明细
				for (DnaExperimentStr parentStr : parentStrList) {
					if (parentStr.getGeneLoci().toUpperCase().equals("AMEL")) {
						continue;
					}
					DnaExperimentStr childStr = getChildStr(parentStr, childStrList);
					if (childStr == null) {
						continue;
					}
					ParentageTestingEntity p = new ParentageTestingEntity(parent.getAppellation(),
							new DnaGeneFrequency(parentStr.getXValue(),
									frequencyStorage.getProb(parentStr.getGeneLoci() + "_" + parentStr.getXValue())),
							new DnaGeneFrequency(parentStr.getYValue(),
									frequencyStorage.getProb(parentStr.getGeneLoci() + "_" + parentStr.getYValue())));

					ParentageTestingEntity c = new ParentageTestingEntity(child.getAppellation(),
							new DnaGeneFrequency(childStr.getXValue(),
									frequencyStorage.getProb(childStr.getGeneLoci() + "_" + childStr.getXValue())),
							new DnaGeneFrequency(childStr.getYValue(),
									frequencyStorage.getProb(childStr.getGeneLoci() + "_" + childStr.getYValue())));

					
						Object[] result = ParentageTesting.duos(c, p,childStr.getGeneLoci());// 0 公式
						DnaPiResultItem dnaPiResultItem = new DnaPiResultItem();
						dnaPiResultItem.setGeneLoci(parentStr.getGeneLoci());
						dnaPiResultItem.setPi((Double) result[1]);
						dnaPiResultItem.setFormula(result[0] + "    " + result[2]);
						if(result[6]!=null){
							dnaPiResultItem.setLoci(result[6].toString());
						}
						dnaPiResultItem.setResult(dnaPiResult);
						
						cpi = cpi.multiply(new BigDecimal(dnaPiResultItem.getPi()));
				
				dnaPiResult.setCpi(cpi);
				dnaPiResult.setRcp(cpi.divide(cpi.add(new BigDecimal(1)), 10, BigDecimal.ROUND_DOWN));
				//dnaPiResult.setDnaPiResultItemList(dnaPiResultItemList);
				dnaPiResultService.save3(dnaPiResult);
				dnaPiResultService.save2(dnaPiResultItem);
				
				}
		}
	}
	}
	//得到当前基因突变
	public void getLoci(String parentCode,String chirdCode){
	//	dnaPiResultDao
		
		
		
/*for(DnaPiResult result:dnaResultList){
			
			dnaPiResultItemList.addAll(dnaPiResultItemDao.findResult(result.getId()));
		}
		String locis2="";
		List<String>locis=new ArrayList<String>();
		DnaPiResult dnaPiResult=new DnaPiResult();
		for (DnaPiResultItem dnaPiResultItem : dnaPiResultItemList) {
			if(!StringUtils.isEmpty(dnaPiResultItem.getLoci())){
				loci=dnaPiResultItem.getLoci();
				locis2+=loci;
				locis.add(loci);
			}
			 dnaPiResult= dnaPiResultDao.get(dnaPiResultItem.getResult().getId());
		}
		DnaMutation dnaMutation=new DnaMutation();
		dnaMutation.setCcode(dnaPiResult.getChildCode());
		dnaMutation.setPcode(dnaPiResult.getParentCode());
		dnaMutation.setLocis(locis2);
		dnaMutation.setTotle(locis.size());*/
	}
	
	
	
	// 获得对应的子值
	private DnaExperimentStr getChildStr(DnaExperimentStr parentStr, List<DnaExperimentStr> childStrList) {
		for (DnaExperimentStr child : childStrList) {
			if (child.getGeneLoci().equals(parentStr.getGeneLoci())){
				return child;
			}
		}
		return null;
	}
	
	
	//list 去重
	public static List<String> removeDuplicate(List<String> list){  
        List<String> listTemp = new ArrayList<String>();  
        for(int i=0;i<list.size();i++){  
            if(!listTemp.contains(list.get(i))){  
                listTemp.add(list.get(i));  
            }  
        }  
        return listTemp;  
    }  

		
}