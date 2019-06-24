/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dna.web;

import java.util.ArrayList;
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
import com.thinkgem.jeesite.modules.dna.dao.DnaReceiveIteamDao;
import com.thinkgem.jeesite.modules.dna.entity.DnaExperiment;
import com.thinkgem.jeesite.modules.dna.entity.DnaExperimentSpecimen;
import com.thinkgem.jeesite.modules.dna.entity.DnaExtractRecord;
import com.thinkgem.jeesite.modules.dna.entity.DnaExtractRecordItem;
import com.thinkgem.jeesite.modules.dna.entity.DnaPreparationReagents;
import com.thinkgem.jeesite.modules.dna.entity.DnaPreparationReagentsIteam;
import com.thinkgem.jeesite.modules.dna.entity.DnaReceiveIteam;
import com.thinkgem.jeesite.modules.dna.service.DnaExperimentService;
import com.thinkgem.jeesite.modules.dna.service.DnaExtractRecordService;
import com.thinkgem.jeesite.modules.dna.service.DnaPreparationReagentsService;
import com.thinkgem.jeesite.modules.synth.dao.SynthEquipmentDao;
import com.thinkgem.jeesite.modules.synth.entity.SynthEquipment;
import com.thinkgem.jeesite.modules.synth.entity.SynthLab;
import com.thinkgem.jeesite.modules.synth.service.SynthLabService;

/**
 * 提取室记录Controller
 * @author yunyun
 * @version 2017-08-19
 */
@Controller
@RequestMapping(value = "${adminPath}/dna/dnaExtractRecord")
public class DnaExtractRecordController extends BaseController {

	@Autowired
	private DnaExtractRecordService dnaExtractRecordService;
	@Autowired
	private DnaPreparationReagentsService dnaPreparationReagentsService;
	@Autowired
	private SynthLabService synthLabService;
	 @Autowired
	 private DnaExperimentService dnaExperimentService;
	 @Autowired
	 private SynthEquipmentDao synthEquipmentDao;
	 @Autowired
	 private DnaReceiveIteamDao dnaReceiveIteamDao;
	@ModelAttribute
	public DnaExtractRecord get(@RequestParam(required=false) String id) {
		DnaExtractRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dnaExtractRecordService.get(id);
		}
		if (entity == null){
			entity = new DnaExtractRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("dna:dnaExtractRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(DnaExtractRecord dnaExtractRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DnaExtractRecord> page = dnaExtractRecordService.findPage(new Page<DnaExtractRecord>(request, response), dnaExtractRecord); 
		model.addAttribute("page", page);
		return "modules/dna/dnaExtractRecordList";
	}

	@RequiresPermissions("dna:dnaExtractRecord:view")
	@RequestMapping(value = "form")
	public String form(DnaExperiment dnaExperiment,DnaExtractRecord dnaExtractRecord, Model model) {
	
		SynthLab lab=  synthLabService.getName("提取室");
		if(lab!=null){
			List<SynthEquipment>synthEquipments= synthEquipmentDao.findAll(lab.getId());
			model.addAttribute("extraction", synthEquipments);
			model.addAttribute("lab", lab);
		}
		//List<DnaExperimentSpecimen> dnaExperimentSpecimenList=dnaPreparationReagentsService.getNot();
		List<DnaReceiveIteam> dnaReceiveIteams=dnaReceiveIteamDao.getNot();
		//提取
		List<DnaExtractRecordItem> dnaExtractRecordItemList=new ArrayList<DnaExtractRecordItem>(); 
		for (DnaReceiveIteam dnaReceiveIteam : dnaReceiveIteams){
			DnaExtractRecordItem dnaExtractRecordItem=new DnaExtractRecordItem();
			dnaExtractRecordItem.setSampleNumber(dnaReceiveIteam.getSpecode());
			dnaExtractRecordItemList.add(dnaExtractRecordItem);
		}
		dnaExtractRecord.setDnaExtractRecordItemList(dnaExtractRecordItemList);
		model.addAttribute("dnaExtractRecord", dnaExtractRecord);
		
		return "modules/dna/dnaExtractRecordForm";
	}
	
	
	

	@RequiresPermissions("dna:dnaExtractRecord:edit")
	@RequestMapping(value = "save")
	public String save(DnaExtractRecord dnaExtractRecord, Model model, RedirectAttributes redirectAttributes,DnaExperiment dnaExperiment) {
		
		
		
		if (!beanValidator(model, dnaExtractRecord)){
			return form(dnaExperiment,dnaExtractRecord, model);
		}
		dnaExtractRecordService.save(dnaExtractRecord);
		addMessage(redirectAttributes, "保存提取表成功");
		return "redirect:" + adminPath + "/sys/user/info";
	}
	
	@RequiresPermissions("dna:dnaExtractRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(DnaExtractRecord dnaExtractRecord, RedirectAttributes redirectAttributes) {
		dnaExtractRecordService.delete(dnaExtractRecord);
		addMessage(redirectAttributes, "删除保存成功成功");
		return "redirect:"+Global.getAdminPath()+"/dna/dnaExtractRecord/?repage";
	}

}