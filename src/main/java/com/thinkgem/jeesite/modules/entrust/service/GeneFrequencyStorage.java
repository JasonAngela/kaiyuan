package com.thinkgem.jeesite.modules.entrust.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.modules.dna.dao.DnaGeneFrequencyDao;
import com.thinkgem.jeesite.modules.dna.entity.DnaGeneFrequency;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import freemarker.core.ReturnInstruction.Return;

@Service
public class GeneFrequencyStorage {
	@Autowired
	private DnaGeneFrequencyDao geneFrequencyDao;
	private Map<String, Double> storage;
	private String js;
	

	
	@PostConstruct
	public void init(){
		
		CacheUtils cacheUtils=new CacheUtils();
		storage = new HashMap<String, Double>();
		List<DnaGeneFrequency> list = geneFrequencyDao.findAllList(new DnaGeneFrequency());
		//清理缓存
		for(DnaGeneFrequency frequency:list){
			cacheUtils.removeAll(frequency.getLoci().getName());
		}
		for(DnaGeneFrequency frequency:list){
			storage.put(frequency.getLoci().getName()+"_"+frequency.getValue(),frequency.getProbability());
		}
		
	}
	
	public Double getProb(String key){
		return storage.get(key);
	}
	
	
	
}
 