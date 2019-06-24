/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dna.web;

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
import com.thinkgem.jeesite.modules.dna.entity.Choosebord;
import com.thinkgem.jeesite.modules.dna.entity.DnaBoard;
import com.thinkgem.jeesite.modules.dna.entity.DnaExperiment;
import com.thinkgem.jeesite.modules.dna.service.ChoosebordService;
import com.thinkgem.jeesite.modules.dna.service.DnaBoardService;

/**
 * 选择板子流程Controller
 * @author fuyun
 * @version 2018-06-20
 */
@Controller
@RequestMapping(value = "${adminPath}/dna/choosebord")
public class ChoosebordController extends BaseController {

	@Autowired
	private ChoosebordService choosebordService;
	@Autowired
	private DnaBoardService dnaBoardService;
	
	@ModelAttribute
	public Choosebord get(@RequestParam(required=false) String id) {
		Choosebord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = choosebordService.get(id);
		}
		if (entity == null){
			entity = new Choosebord();
		}
		return entity;
	}
	
	@RequiresPermissions("dna:dna:choosebord:view")
	@RequestMapping(value = {"list", ""})
	public String list(Choosebord choosebord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Choosebord> page = choosebordService.findPage(new Page<Choosebord>(request, response), choosebord); 
		model.addAttribute("page", page);
		return "modules/dna/dna/choosebordList";
	}

	
	@RequestMapping(value = "form")
	public String form(Choosebord choosebord, Model model) {
		
		model.addAttribute("choosebord", choosebord);
		return "modules/dna/dna/choosebordForm";
	}
	
	//选择板子页面
			@RequiresPermissions("dna:dnaExperiment:edit")
			@RequestMapping(value = "chooseBoardAdd")
			public String chooseBoard(DnaExperiment dnaExperiment,Model model){
				List<DnaBoard> boardList = dnaBoardService.findList(new DnaBoard());
				if(boardList.isEmpty()){
					//为空时自动添加一块板子
					DnaBoard board =  dnaBoardService.autoMakeBoard(null);
					boardList.add(board);
				}
				
				model.addAttribute("boardList", boardList);
				return "modules/dna/dnaChooseBoard"; 
			}

	
	@RequestMapping(value = "save")
	public String save(Choosebord choosebord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, choosebord)){
			return form(choosebord, model);
		}
		choosebordService.save(choosebord);
		addMessage(redirectAttributes, "保存选择板子成功");
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	@RequiresPermissions("dna:dna:choosebord:edit")
	@RequestMapping(value = "delete")
	public String delete(Choosebord choosebord, RedirectAttributes redirectAttributes) {
		choosebordService.delete(choosebord);
		addMessage(redirectAttributes, "删除选择板子流程成功");
		return "redirect:"+Global.getAdminPath()+"/dna/dna/choosebord/?repage";
	}

}