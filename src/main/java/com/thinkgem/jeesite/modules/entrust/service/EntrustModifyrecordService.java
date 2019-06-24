/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entrust.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustAbstracts;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustExpertOpinion;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustModifyrecord;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustRegister;
import com.thinkgem.jeesite.modules.dna.dao.DnaExperimentStrDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaPiResultDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaPiResultItemDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaSecondarydataDao;
import com.thinkgem.jeesite.modules.dna.entity.DnaExperiment;
import com.thinkgem.jeesite.modules.dna.entity.DnaExperimentStr;
import com.thinkgem.jeesite.modules.dna.entity.DnaGeneFrequency;
import com.thinkgem.jeesite.modules.dna.entity.DnaPiResult;
import com.thinkgem.jeesite.modules.dna.entity.DnaPiResultItem;
import com.thinkgem.jeesite.modules.dna.entity.DnaSecondarydata;
import com.thinkgem.jeesite.modules.dna.entity.DnaSpeIteam;
import com.thinkgem.jeesite.modules.dna.entity.ParentageTestingEntity;
import com.thinkgem.jeesite.modules.dna.service.DnaPiResultService;
import com.thinkgem.jeesite.modules.dna.service.DnaSecondarydataService;
import com.thinkgem.jeesite.modules.dna.service.ParentageTesting;
import com.thinkgem.jeesite.modules.entrust.dao.EntrustAbstractsDao;
import com.thinkgem.jeesite.modules.entrust.dao.EntrustModifyrecordDao;

/**
 * 报告修改记录Service
 * @author fuyun
 * @version 2017-12-14
 */
@Service
@Transactional(readOnly = true)
public class EntrustModifyrecordService extends CrudService<EntrustModifyrecordDao, EntrustModifyrecord> {
	@Autowired
	private DnaExperimentStrDao dnaExperimentStrDao;
	@Autowired
	private DnaSecondarydataService dnaSecondarydataService;
	@Autowired
	private DnaSecondarydataDao dnaSecondarydataDao;
	@Autowired
	private GeneFrequencyStorage frequencyStorage;
	@Autowired
	private DnaPiResultService dnaPiResultService;
	@Autowired
	private EntrustAbstractsDao entrustAbstractsDao;
	@Autowired
	private DnaPiResultItemDao dnaPiResultItemDao;
	@Autowired
	private DnaPiResultDao  dnaPiResultDao;
	public EntrustModifyrecord get(String id) {
		return super.get(id);
	}
	
	
	public List<EntrustModifyrecord> findList(EntrustModifyrecord entrustModifyrecord) {
		return super.findList(entrustModifyrecord);
	}
	
	public Page<EntrustModifyrecord> findPage(Page<EntrustModifyrecord> page, EntrustModifyrecord entrustModifyrecord) {
		return super.findPage(page, entrustModifyrecord);
	}
	
	@Transactional(readOnly = false)
	public void save(EntrustModifyrecord entrustModifyrecord) {
		super.save(entrustModifyrecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(EntrustModifyrecord entrustModifyrecord) {
		super.delete(entrustModifyrecord);
	}
	
	@Transactional(readOnly = false)
	public void repeat(EntrustExpertOpinion entrustExpertOpinion, List<Map<String, String>> data,String[] noCode) {
		// TODO Auto-generated method stub
		dnaSecondarydataDao.deleteAll();
		String code =null;
		List<DnaExperimentStr> dnaExperimentStrs=new ArrayList<DnaExperimentStr>();
		DnaExperiment experiment=new DnaExperiment();
		String dnaId=null;
		for (String noCo : noCode) {
			dnaExperimentStrs=dnaExperimentStrDao.getCode(noCo);
			dnaId=dnaExperimentStrs.get(0).getExperiment().getId();
		}
		for(Map<String,String>item:data){
			code = item.get("Sample Name");
				for (String noCode1 : noCode) {
				 if(noCode1.equals(code)){
					 			DnaSecondarydata  secondarydata=new DnaSecondarydata();    
								 secondarydata.setSpecimencode(code);
								 secondarydata.setX(item.get("Allele 1"));
								 secondarydata.setY(item.get("Allele 2"));
								 secondarydata.setGeneloci(item.get("Marker"));
								dnaSecondarydataService.save(secondarydata);
			 	}
			}
		}
		//第二次全部导入数据
		List<DnaSecondarydata> dnaSecondarydataAll=dnaSecondarydataDao.findAll();
		//第二次与第一次相同的数据
	    List<DnaSecondarydata>	dnaSecondarydataNo=dnaSecondarydataDao.findNo();
	    //第二次与第一次不相同的数据
	    dnaSecondarydataAll.removeAll(dnaSecondarydataNo); 
	  
	    if(dnaId!=null){
	    	for (DnaSecondarydata dnaSecondarydata : dnaSecondarydataAll) {
	    		experiment.setId(dnaId);
	    		DnaExperimentStr dnaExperimentStr=new DnaExperimentStr();
	    		dnaExperimentStr.setExperiment(experiment);
	    		dnaExperimentStr.setGeneLoci(dnaSecondarydata.getGeneloci());
	    		dnaExperimentStr.setX(dnaSecondarydata.getX());
	    		dnaExperimentStr.setY(dnaSecondarydata.getY());
	    		dnaExperimentStr.setSpecimenCode(dnaSecondarydata.getSpecimencode());
	    		dnaExperimentStr.preInsert();
	    		dnaExperimentStrDao.insert(dnaExperimentStr);
			}
	    }
	  
	    calculatePi( entrustExpertOpinion.getRegister());
	    		
		
	}
	public void calculatePi(EntrustRegister entrustRegister) {
		
		
		/*dnaPiResultItemDao.delete();
		dnaPiResultDao.delete();*/
	List<DnaPiResult>  piResults=dnaPiResultService.getRgister(entrustRegister.getId());
	for(DnaPiResult result:piResults){
		List<DnaPiResultItem>	dnaPiResultItems=result.getDnaPiResultItemList();
		for (DnaPiResultItem dnaPiResultItem : dnaPiResultItems) {
			dnaPiResultItemDao.delRegister(dnaPiResultItem.getId());
		}
		dnaPiResultDao.delRegister(result.getId());
	}

	
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

					if(c.getFirstProbability()==null||c.getSecondProbability()==null||p.getFirstProbability()==null||p.getSecondProbability()==null){
  						break;
  					}else{
						Object[] result = ParentageTesting.duos(c, p,childStr.getGeneLoci());// 0 公式
						DnaPiResultItem dnaPiResultItem = new DnaPiResultItem();
						dnaPiResultItem.setGeneLoci(parentStr.getGeneLoci());
						dnaPiResultItem.setPi((Double) result[1]);
						dnaPiResultItem.setFormula(result[0] + "    " + result[2]);
						if(result[6]!=null){
							dnaPiResultItem.setLoci(result[6].toString());
						}
						dnaPiResultItemList.add(dnaPiResultItem);
						cpi = cpi.multiply(new BigDecimal(dnaPiResultItem.getPi()));
				}
				dnaPiResult.setCpi(cpi);
				dnaPiResult.setRcp(cpi.divide(cpi.add(new BigDecimal(1)), 10, BigDecimal.ROUND_DOWN));
				dnaPiResult.setDnaPiResultItemList(dnaPiResultItemList);
				dnaPiResultService.save(dnaPiResult);
			}
		}
		}
		
	}
	// 获得对应的子值
		private DnaExperimentStr getChildStr(DnaExperimentStr parentStr, List<DnaExperimentStr> childStrList) {
			for (DnaExperimentStr child : childStrList) {
				if (child.getGeneLoci().equals(parentStr.getGeneLoci())) {
					return child;
				}
			}
			return null;
		}
	
}