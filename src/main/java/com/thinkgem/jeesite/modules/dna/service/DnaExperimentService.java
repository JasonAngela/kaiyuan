/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dna.service;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.dna.entity.*;
import jxl.write.*;
import jxl.write.Number;
import org.activiti.engine.runtime.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DnaDataParser;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.act.utils.ActUtils;
import com.thinkgem.jeesite.modules.dna.dao.DnaBoardDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaBoardJggDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaElectrophoresisPartingDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaExperimentBoardDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaExperimentDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaExperimentImportDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaExperimentSpecimenDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaExperimentStrDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaGeneLociDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaPiResultDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaPiResultItemDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaSecondarydataDao;
import com.thinkgem.jeesite.modules.entrust.dao.EntrustAbstractsDao;
import com.thinkgem.jeesite.modules.entrust.dao.EntrustRegisterDao;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustAbstracts;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustRegister;
import com.thinkgem.jeesite.modules.entrust.service.EntrustRegisterService;
import com.thinkgem.jeesite.modules.entrust.service.GeneFrequencyStorage;
import com.thinkgem.jeesite.modules.material.dao.SpecimenMaterialRegisterItemDao;
import com.thinkgem.jeesite.modules.material.entity.SpecimenMaterialRegisterItem;
import com.thinkgem.jeesite.modules.sys.service.SysCodeRuleService;

import jxl.Workbook;
import org.springframework.util.CollectionUtils;

/**
 * dna试验Service
 * @author zhuguli
 * @version 2017-06-11
 */
@Service
@Transactional(readOnly = true)
public class DnaExperimentService extends CrudService<DnaExperimentDao, DnaExperiment> {
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private SysCodeRuleService codeRuleService;
	@Autowired
	private DnaExperimentImportDao dnaExperimentImportDao;
	@Autowired
	private DnaExperimentSpecimenDao dnaExperimentSpecimenDao;
	@Autowired
	private DnaExperimentBoardDao dnaExperimentBoardDao;
	@Autowired
	private DnaBoardDao dnaBoardDao;
	@Autowired
	private DnaBoardJggDao boardJggDao;
	@Autowired
	private EntrustAbstractsDao entrustAbstractsDao;
	@Autowired
	private DnaExperimentStrDao dnaExperimentStrDao;
	@Autowired
	private SpecimenMaterialRegisterItemDao specimenMaterialRegisterItemDao;
	@Autowired
	private EntrustRegisterDao entrustRegisterDao;
	
	@Autowired
	private EntrustRegisterService entrustRegisterService;
	@Autowired
	private GeneFrequencyStorage frequencyStorage;
	@Autowired
	private DnaPiResultService dnaPiResultService;
	@Autowired
	private DnaPiResultDao dnaPiResultDao;
	@Autowired
	private DnaPiResultItemDao dnaPiResultItemDao;
	@Autowired
	private DnaGeneLociDao dnaGeneLociDao;
	@Autowired
	private DnaElectrophoresisPartingDao dnaElectrophoresisPartingDao;
	@Autowired
	private DnaSecondarydataService dnaSecondarydataService;
	@Autowired
	private DnaSecondarydataDao dnaSecondarydataDao;
	public DnaExperiment get(String id) {
		DnaExperiment dnaExperiment = super.get(id);
		if(dnaExperiment!=null){
			dnaExperiment.setDnaExperimentImportList(dnaExperimentImportDao.findList(new DnaExperimentImport(dnaExperiment)));
			dnaExperiment.setDnaExperimentSpecimenList(dnaExperimentSpecimenDao.findList(new DnaExperimentSpecimen(dnaExperiment)));
		}
		return dnaExperiment;
	}

	public List<DnaExperiment> findList(DnaExperiment dnaExperiment) {
		return super.findList(dnaExperiment);
	}

	public Page<DnaExperiment> findPage(Page<DnaExperiment> page, DnaExperiment dnaExperiment) {
		return super.findPage(page, dnaExperiment);
	}
  
	@Transactional(readOnly = false)
	public void save(DnaExperiment dnaExperiment) {
		String code = codeRuleService.generateCode("dna_experiment_code");
		dnaExperiment.setCode(code);
		List<DnaExperimentSpecimen> dnaExperimentSpecimenList=	dnaExperiment.getDnaExperimentSpecimenList();
		for (DnaExperimentSpecimen dnaExperimentSpecimen : dnaExperimentSpecimenList) {
		if(entrustAbstractsDao.findSpecimenCode(dnaExperimentSpecimen.getSpecimenCode())!=null){
 			EntrustRegister entrustRegister = entrustRegisterService.get(entrustAbstractsDao.findSpecimenCode(dnaExperimentSpecimen.getSpecimenCode()));
 			if(entrustRegister!=null){
 					entrustRegister.setStatus("7");
 					entrustRegisterDao.update(entrustRegister);
	 			}
			}	
		}	
		
		super.save(dnaExperiment);
		for (DnaExperimentSpecimen dnaExperimentSpecimen : dnaExperiment.getDnaExperimentSpecimenList()){
			if (DnaExperimentSpecimen.DEL_FLAG_NORMAL.equals(dnaExperimentSpecimen.getDelFlag())){
				if (StringUtils.isBlank(dnaExperimentSpecimen.getId())){
					dnaExperimentSpecimen.setExperiment(dnaExperiment);
					dnaExperimentSpecimen.preInsert();
					dnaExperimentSpecimenDao.insert(dnaExperimentSpecimen);
				}else{
					dnaExperimentSpecimen.preUpdate();
					dnaExperimentSpecimenDao.update(dnaExperimentSpecimen);
				}
			}else{
				dnaExperimentSpecimenDao.delete(dnaExperimentSpecimen);
			}
		}
		//auto relation board
		if(StringUtils.isNotEmpty(dnaExperiment.getBoardId())){
			DnaBoard board = dnaBoardDao.get(dnaExperiment.getBoardId());
			DnaExperimentBoard experimentBoard = new DnaExperimentBoard();
			experimentBoard.preInsert();
			experimentBoard.setBoardCode(board.getCode());
			experimentBoard.setExperiment(dnaExperiment);
			dnaExperimentBoardDao.insert(experimentBoard);
		}
		// 启动流程
		actTaskService.startProcess(ActUtils.PD_DNA_APPRAISAL[0], ActUtils.PD_DNA_APPRAISAL[1], dnaExperiment.getId(), dnaExperiment.getCode());
	}

	
	
	@Transactional(readOnly = false)
	public void delete(DnaExperiment dnaExperiment) {
		super.delete(dnaExperiment);
		dnaExperimentImportDao.delete(new DnaExperimentImport(dnaExperiment));
		dnaExperimentSpecimenDao.delete(new DnaExperimentSpecimen(dnaExperiment));
	}
	/**
	 * select board by experimentId
	 * @param id
	 * @return
	 */
	public DnaBoard getLastBoard(String id) {
		List<DnaExperimentBoard> ebList = dnaExperimentBoardDao.findByParentId(id);
		if(ebList.isEmpty()){
			return null;
		}
		DnaBoard board = dnaBoardDao.getByCode(ebList.get(0).getBoardCode());
		board.setDnaBoardJggList(boardJggDao.findList(new DnaBoardJgg(board)));
		return board;
	}

	@Transactional(readOnly = false)
	public void saveBoard(DnaExperiment dnaExperiment,DnaElectrophoresisParting dnaElectrophoresisParting) {
		DnaBoard board = dnaExperiment.getBoard();
		dnaElectrophoresisParting.setCode(board.getCode());
		dnaElectrophoresisPartingDao.insert(dnaElectrophoresisParting);
		List<DnaExperimentSpecimen> specimens = dnaExperiment.getDnaExperimentSpecimenList();
		Map<String,DnaExperimentSpecimen>specimensMap = new HashMap<String, DnaExperimentSpecimen>();
		for(DnaExperimentSpecimen specimen:specimens){
			specimensMap.put(specimen.getSpecimenCode(), specimen);
		}
		//dna_experiment_specimen
		for(DnaBoardJgg jgg:board.getDnaBoardJggList()){
			if(StringUtils.isNotBlank(jgg.getSpecimenCode())){
				DnaBoardJgg entity = boardJggDao.get(jgg.getId());
				entity.setSpecimenCode(jgg.getSpecimenCode());
				entity.setUpdateDate( new Date()); 
				boardJggDao.update(entity);
			}
		}
		// 完成流程任务
		Map<String, Object> vars = Maps.newHashMap();
		actTaskService.complete(dnaExperiment.getAct().getTaskId(), dnaExperiment.getAct().getProcInsId(), dnaExperiment.getAct().getComment(), dnaExperiment.getCode(), vars);

	}
	@Transactional(readOnly = false)
	public   void  importData(DnaExperiment dnaExperiment){
		List<DnaExperimentSpecimen> dnaExperimentSpecimenList = dnaExperiment.getDnaExperimentSpecimenList();
		List<Map<String,String>>data = DnaDataParser.parse(dnaExperiment.getImportDataAddress());
		
		//判断是否包含
		String isContains=isContains(dnaExperimentSpecimenList, data);
		//判断是否含有基因座
		String  haveDna=haveDna(dnaExperiment);

		if(isContains!=null){
			throw new RuntimeException(isContains);
		}
		
		if(haveDna!=null){
			throw new RuntimeException(haveDna);
		}
		

		//判断二次导入
		String []	noCode=new String[2];
				//添加数据
				for(Map<String,String>item:data){
					String code = item.get("Sample Name");//样品编码
					if(code.contains("-G")){
							if(isExist(dnaExperimentSpecimenList,code)){
								DnaExperimentStr str = new DnaExperimentStr();
								str.setSpecimenCode(code);
								str.setX(item.get("Allele 1"));
								str.setY(item.get("Allele 2"));
								str.setExperiment(dnaExperiment);
								str.setGeneLoci(item.get("Marker"));
								str.preInsert();
								dnaExperimentStrDao.insert(str);
							}
					}else if(code.contains("-T")){
						if(isExist(dnaExperimentSpecimenList,code)){
							DnaExperimentStr str = new DnaExperimentStr();
							str.setSpecimenCode(code);
							str.setX(item.get("Allele 1"));
							str.setY(item.get("Allele 2"));
							str.setExperiment(dnaExperiment);
							str.setGeneLoci(item.get("Marker"));
							str.preInsert();
							dnaExperimentStrDao.insert(str);
						}
						
					}
					
					else	if(code.contains("(2)")){
						if(code.contains("C")){
							noCode[0]=code;
						}
						if(code.contains("F")||code.contains("M")){
							noCode[1]=code;
						}
					
					if(noCode[0]!=null){
						dnaSecondarydataDao.deleteAll();
						String noCode2=null;
						List<DnaExperimentStr> dnaExperimentStrs=new ArrayList<DnaExperimentStr>();
						DnaExperiment experiment=new DnaExperiment();
						String dnaId=null;
						for (String noCo : noCode) {
							noCode2=noCo=noCo.substring(0, noCo.length()-3);
							dnaExperimentStrs=dnaExperimentStrDao.getCode(noCo);
							dnaId=dnaExperimentStrs.get(0).getExperiment().getId();
						}
								for (String noCode1 : noCode) {
								 if(noCode1.equals(code)){
									 			 code=code.substring(0, code.length()-3);
									 			 DnaSecondarydata  secondarydata=new DnaSecondarydata();    
												 secondarydata.setSpecimencode(code);
												 secondarydata.setX(item.get("Allele 1"));
												 secondarydata.setY(item.get("Allele 2"));
												 secondarydata.setGeneloci(item.get("Marker"));
											     dnaSecondarydataService.save(secondarydata);
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
					}
				}
			else{
								if(isExist(dnaExperimentSpecimenList,code)){
									DnaExperimentStr str = new DnaExperimentStr();
									str.setSpecimenCode(code);
									str.setX(item.get("Allele 1"));
									str.setY(item.get("Allele 2"));
									str.setExperiment(dnaExperiment);
									str.setGeneLoci(item.get("Marker"));
									str.preInsert();
									//判断是否有基因频率
									String  flag=haveDna(dnaExperiment);
									if(flag ==null ){
										dnaExperimentStrDao.insert(str);
									}else{
										throw new RuntimeException(flag);
									}
						}
				}	
		}	
				// 完成流程任务
				Map<String, Object> vars = Maps.newHashMap();
				actTaskService.complete(dnaExperiment.getAct().getTaskId(), dnaExperiment.getAct().getProcInsId(), dnaExperiment.getAct().getComment(), dnaExperiment.getCode(), vars);
	}

	//判断是否包含
		private String isContains(List<DnaExperimentSpecimen> dnaExperimentSpecimenList, List<Map<String,String>>data) {
			String n=null;
			List<String>codes=new ArrayList<String>();
			  for(Map<String,String>item:data){
				  codes.add( item.get("Sample Name"));
			  }
			  
			for(DnaExperimentSpecimen specimen:dnaExperimentSpecimenList){
				if(!codes.contains(specimen.getSpecimenCode())){                      
					if(specimen.getSpecimenCode()==null||specimen.getSpecimenCode().equals("")){
						continue;
					}
					n="样品------"+specimen.getSpecimenCode()+"-----没有数据  请对比参数重新导入";
					break;
					}
			}
			return n;
		}
	//判断是否含有基因频率
	private String  haveDna(DnaExperiment dnaExperiment){

		String vn=null;
		Set<String>registerIdSet = new HashSet<String>();
		for(DnaExperimentSpecimen specimen:dnaExperiment.getDnaExperimentSpecimenList()){
			if(hasResult(specimen)){
				//修改物证摘要id
				String registerId = updateAbstractsExperimentId(dnaExperiment.getId(), specimen.getSpecimenCode());
				registerIdSet.add(registerId);
			}
		}

		for(String registerId1:registerIdSet){
			EntrustRegister register = entrustRegisterDao.get(registerId1);
			List<EntrustAbstracts> abstractsList = entrustAbstractsDao.findList(new EntrustAbstracts(register));

			for (EntrustAbstracts abstrac : abstractsList) {
				List<DnaExperimentStr> StrList = dnaExperimentStrDao.getByExperimentIdAndAbstractsId(abstrac.getDnaExperimentId(), abstrac.getId());
				for (DnaExperimentStr parentStr : StrList) {
					if (parentStr.getGeneLoci().toUpperCase().equals("AMEL")) {
						continue;
					}
					ParentageTestingEntity p = new ParentageTestingEntity("dd",
							new DnaGeneFrequency(parentStr.getXValue(),
									frequencyStorage.getProb(parentStr.getGeneLoci() + "_" + parentStr.getXValue())),
							new DnaGeneFrequency(parentStr.getYValue(),
									frequencyStorage.getProb(parentStr.getGeneLoci() + "_" + parentStr.getYValue())));
					if(p.getFirstProbability()==null){
						vn="样品"+parentStr.getSpecimenCode()+"中的"+parentStr.getGeneLoci()+"--------"+p.getFirstValue()+"不存在";
						break;
					}
					if(p.getSecondProbability()==null){
						vn="样品"+parentStr.getSpecimenCode()+"中的"+parentStr.getGeneLoci()+"--------"+p.getSecondValue()+"不存在";
						break;
					}
				}
			}
		}
		return vn;
	}





	private boolean isExist(List<DnaExperimentSpecimen> dnaExperimentSpecimenList, String code) {
		for(DnaExperimentSpecimen specimen:dnaExperimentSpecimenList){
			if(code.equals(specimen.getSpecimenCode())){
				return true;
			}
		}
		return false;
	}
	
	@Transactional(readOnly = false)
	public void autoActivate(DnaExperiment dnaExperiment) {
  		String noCo=null;
		String registerId2=null;
		Set<String>registerIdSet = new HashSet<String>();
		Set<String>registerIdSet2 = new HashSet<String>();
		for(DnaExperimentSpecimen specimen:dnaExperiment.getDnaExperimentSpecimenList()){
			if(specimen.getSpecimenCode()!=null){
				if(specimen.getSpecimenCode().contains("-G")){
						String registerId3 = updateAbstractsExperimentId(dnaExperiment.getId(), specimen.getSpecimenCode());
						if(entrustAbstractsDao.findSpecimenCode(specimen.getSpecimenCode())!=null){
				 			EntrustRegister entrustRegister = entrustRegisterService.get(entrustAbstractsDao.findSpecimenCode(specimen.getSpecimenCode()));
				 			if(entrustRegister!=null){ 
				 				//数据分析
								entrustRegister.setStatus("8");
								entrustRegisterDao.update(entrustRegister);
					 			}
							}	
						//激活相应的委托单流程
						EntrustRegister register = entrustRegisterDao.get(registerId3);
						if(isCompleted(register)){
							singal3(register);
						}
					
					
				}else if(specimen.getSpecimenCode().contains("-T")){
					if(hasResult(specimen)){
					String registerId3 = updateAbstractsExperimentId(dnaExperiment.getId(), specimen.getSpecimenCode());
					registerIdSet2.add(registerId3);
					
					if(entrustAbstractsDao.findSpecimenCode(specimen.getSpecimenCode())!=null){
			 			EntrustRegister entrustRegister = entrustRegisterService.get(entrustAbstractsDao.findSpecimenCode(specimen.getSpecimenCode()));
			 			if(entrustRegister!=null){ 
			 				//数据分析
							entrustRegister.setStatus("8");
							entrustRegisterDao.update(entrustRegister);
				 			}
						}	
					}
				}
				
				else	if(specimen.getSpecimenCode().contains("(2)")){
					noCo=specimen.getSpecimenCode().substring(0, specimen.getSpecimenCode().length()-3);
					registerId2=entrustAbstractsDao.findSpecimenCode(noCo);
					EntrustRegister register = entrustRegisterDao.get(registerId2);
					 singal2(register); 
				}
				else{                               
					String registerId =null;
				if(hasResult(specimen)){
					//修改物证摘要id
					 registerId = updateAbstractsExperimentId(dnaExperiment.getId(), specimen.getSpecimenCode());
					 registerIdSet.add(registerId);
				if(entrustAbstractsDao.findSpecimenCode(specimen.getSpecimenCode())!=null){
		 			EntrustRegister entrustRegister = entrustRegisterService.get(entrustAbstractsDao.findSpecimenCode(specimen.getSpecimenCode()));
		 			if(entrustRegister!=null){
		 				//数据分析
						entrustRegister.setStatus("8");
						entrustRegisterDao.update(entrustRegister);
			 			}
					}	
				}
			
		}
	}
}
		if(registerIdSet.size()!=0){
			for(String registerId:registerIdSet){
				//激活相应的委托单流程
				EntrustRegister register = entrustRegisterDao.get(registerId);
				
				if(isCompleted(register)){
					singal(register);
				}
			}
		}
		if(registerIdSet2.size()!=0){
			for(String registerId:registerIdSet2){
				//激活相应的委托单流程
				EntrustRegister register = entrustRegisterDao.get(registerId);
				if(isCompleted(register)){
					singal3(register);
				}
			}
		}
}
	private boolean hasResult(DnaExperimentSpecimen specimen) {
		List<DnaExperimentStr> list = dnaExperimentStrDao.getByExperimentIdAndCode(specimen.getExperiment().getId(), specimen.getSpecimenCode());
		if(list.isEmpty()){
			return false;
		}
		return true;
	}

	//判断是否完成了检验
	private boolean isCompleted(EntrustRegister register){
		List<EntrustAbstracts>notExperimentList = entrustAbstractsDao.listNotExperiment(register.getId());
		if(notExperimentList.isEmpty()){
			return true;
		}
		return false;
	}

	private String updateAbstractsExperimentId(String dnaExperimentId, String  specimenCode) {

		EntrustAbstracts abstracts=null;
		List<SpecimenMaterialRegisterItem> materialRegisterItems =  specimenMaterialRegisterItemDao.selectByCode(specimenCode);
		if(materialRegisterItems == null){
			throw new RuntimeException("不存在的样品编码:"+specimenCode);
		}
    
		abstracts = entrustAbstractsDao.get(materialRegisterItems.get(0).getAbstracts());
		abstracts.setDnaExperimentId(dnaExperimentId);
		abstracts.setSpecimenCode(materialRegisterItems.get(0).getCode());
		entrustAbstractsDao.update(abstracts);



		return abstracts.getRegister().getId();
	}
	@Transactional(readOnly = false)
	private void  singal(EntrustRegister entrustRegister) {
		//自动计算kpi
		entrustRegisterService.autoCalculatePi(entrustRegister);
		Execution execution1 = actTaskService.getProcessEngine().getRuntimeService()    
				.createExecutionQuery()  
				.processInstanceId(entrustRegister.getProcInsId())//流程实例ID      
				.activityId("waitImport")//当前活动的名称  
				.singleResult();      
		if(execution1!=null){
			actTaskService.getProcessEngine().getRuntimeService().signal(execution1.getId());
		}
	}
	
	

	//个体识别  只激活
	@Transactional(readOnly = false)
	private void  singal3(EntrustRegister entrustRegister) {
	/*	//自动计算kpi
		entrustRegisterService.autoCalculatePi(entrustRegister);*/
		Execution execution1 = actTaskService.getProcessEngine().getRuntimeService()    
				.createExecutionQuery()  
				.processInstanceId(entrustRegister.getProcInsId())//流程实例ID  
				.activityId("waitImport")//当前活动的名称  
				.singleResult();   
		if(execution1!=null){
			actTaskService.getProcessEngine().getRuntimeService().signal(execution1.getId());
		}
	}
	 
	
	
	//二次实验
	@Transactional(readOnly = false)
	private void  singal2(EntrustRegister entrustRegister) {
		//自动计算kpi
		entrustRegisterService.autoCalculatePi2(entrustRegister);
		Execution execution1 = actTaskService.getProcessEngine().getRuntimeService()    
				.createExecutionQuery()  
				.processInstanceId(entrustRegister.getProcInsId())//流程实例ID  
				.activityId("waitImport")//当前活动的名称  
				.singleResult(); 
		if(execution1!=null){
			actTaskService.getProcessEngine().getRuntimeService().signal(execution1.getId());
		}
	}
	
	
	
	@Transactional(readOnly = false)
	public void batchImportData(DnaExperiment dnaExperiment) {
		save(dnaExperiment);
		List<Map<String,String>>data = DnaDataParser.parse(dnaExperiment.getImportDataAddress());
		Set<String>specimenCodeList = new TreeSet<String>();
		//添加数据
		for(Map<String,String>item:data){
			String code = item.get("Sample Name");//样品编码
			DnaExperimentStr str = new DnaExperimentStr();
			str.setSpecimenCode(code);
			str.setX(item.get("Allele 1"));
			str.setY(item.get("Allele 2"));
			str.setExperiment(dnaExperiment);
			str.setGeneLoci(item.get("Marker")); 
			str.preInsert();
			dnaExperimentStrDao.insert(str);
			specimenCodeList.add(code);
		}
		for(String code:specimenCodeList){  
			DnaExperimentSpecimen scpecimen = new DnaExperimentSpecimen();
			scpecimen.setSpecimenCode(code);
			scpecimen.setExperiment(dnaExperiment);
			scpecimen.preInsert();
			dnaExperimentSpecimenDao.insert(scpecimen);
		}
	}
	
	
	@Transactional(readOnly = false)
	public void  calculate(List<DnaSpeIteam>dnaSpeIteams,String experimentId ) {
		// TODO Auto-generated method stub
		//dnaPiResultItemDao.delete();
		//dnaPiResultDao.delete();
		List<String>parentList = new ArrayList<String>();
		List<String>matherList=new ArrayList<String>();
		List<String>childrenList = new ArrayList<String>();
		String one=null;

		for (DnaSpeIteam dnaSpeIteam : dnaSpeIteams) {
			one=dnaSpeIteam.getParrens();
			if(StringUtils.isNotEmpty(one)){
				if(one.contains("F")){
					parentList.add(dnaSpeIteam.getSpecimen());   
				if(one.contains("M")){
					matherList.add(dnaSpeIteam.getSpecimen());
				}
				if(one.contains("C")){
					childrenList.add(dnaSpeIteam.getSpecimen());
				}
			}
		}
		//交叉生成结果
		for(String parent:parentList){
			List<DnaExperimentStr> parentStrList = dnaExperimentStrDao.getByExperimentIdAndCode(experimentId,parent);
			BigDecimal cpi = new BigDecimal(1);
			for(String child:childrenList){
				DnaPiResult dnaPiResult = new DnaPiResult();
				dnaPiResult.setParentCode(parent);
				dnaPiResult.setChildCode(child);
				List<DnaPiResultItem> dnaPiResultItemList = new ArrayList<DnaPiResultItem>();
				List<DnaExperimentStr> childStrList = dnaExperimentStrDao.getByExperimentIdAndCode(experimentId,child);
				//保存明细
				for(DnaExperimentStr parentStr:parentStrList ){
					if(parentStr.getGeneLoci().toUpperCase().equals("AMEL")){                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
						continue;
					}
					DnaExperimentStr childStr = getChildStr(parentStr,childStrList);
					if(childStr == null){
						continue;
					}

					ParentageTestingEntity p = new ParentageTestingEntity("父亲", 
							new DnaGeneFrequency(parentStr.getXValue(),frequencyStorage.getProb(parentStr.getGeneLoci()+"_"+parentStr.getXValue())),
							new DnaGeneFrequency(parentStr.getYValue(),frequencyStorage.getProb(parentStr.getGeneLoci()+"_"+parentStr.getYValue())));

					ParentageTestingEntity c = new ParentageTestingEntity("小孩",
							new DnaGeneFrequency(childStr.getXValue(),frequencyStorage.getProb(childStr.getGeneLoci()+"_"+childStr.getXValue())),
							new DnaGeneFrequency(childStr.getYValue(),frequencyStorage.getProb(childStr.getGeneLoci()+"_"+childStr.getYValue())));

					Object[] result = ParentageTesting.duos(c, p,parentStr.getGeneLoci());//0 公式
					DnaPiResultItem dnaPiResultItem = new DnaPiResultItem();
					if(!parentStr.getGeneLoci().toUpperCase().equals("AMEL")){
						dnaPiResultItem.setGeneLoci(parentStr.getGeneLoci());
					}
					dnaPiResultItem.setPi((Double) result[1]);
					dnaPiResultItem.setFormula(result[0]+"    ");
					dnaPiResultItem.setpProb((Double)result[3]);//p值
					dnaPiResultItem.setqProb((Double)result[4]);//q值
					dnaPiResultItem.setMin((Double)result[5]);//n值
					dnaPiResultItem.setCode(parent);
					dnaPiResultItemList.add(dnaPiResultItem);
					cpi = cpi.multiply(new BigDecimal(dnaPiResultItem.getPi()));
				}
				dnaPiResult.setCpi(cpi);
				dnaPiResult.setRcp(cpi.divide(cpi.add(new BigDecimal(1)),10,BigDecimal.ROUND_DOWN));
				dnaPiResult.setDnaPiResultItemList(dnaPiResultItemList);
				dnaPiResultService.save(dnaPiResult);

			}
		}                                                                                                                                                                                                                      
		for(String parent:matherList){
			List<DnaExperimentStr> parentStrList = dnaExperimentStrDao.getByExperimentIdAndCode(experimentId,parent);
			BigDecimal cpi = new BigDecimal(1);
			for(String child:childrenList){
				DnaPiResult dnaPiResult = new DnaPiResult();
				dnaPiResult.setParentCode(parent);
				dnaPiResult.setChildCode(child);
				List<DnaPiResultItem> dnaPiResultItemList = new ArrayList<DnaPiResultItem>();
				List<DnaExperimentStr> childStrList = dnaExperimentStrDao.getByExperimentIdAndCode(experimentId,child);
				//保存明细
				for(DnaExperimentStr parentStr:parentStrList ){

					if(parentStr.getGeneLoci().toUpperCase().equals("AMEL")){                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
						continue;
					}
					DnaExperimentStr childStr = getChildStr(parentStr,childStrList);
					if(childStr == null){
						continue;
					}

					ParentageTestingEntity p = new ParentageTestingEntity("母亲", 
							new DnaGeneFrequency(parentStr.getXValue(),frequencyStorage.getProb(parentStr.getGeneLoci()+"_"+parentStr.getXValue())),
							new DnaGeneFrequency(parentStr.getYValue(),frequencyStorage.getProb(parentStr.getGeneLoci()+"_"+parentStr.getYValue())));

					ParentageTestingEntity c = new ParentageTestingEntity("小孩", 
							new DnaGeneFrequency(childStr.getXValue(),frequencyStorage.getProb(childStr.getGeneLoci()+"_"+childStr.getXValue())),
							new DnaGeneFrequency(childStr.getYValue(),frequencyStorage.getProb(childStr.getGeneLoci()+"_"+childStr.getYValue())));

					Object[] result = ParentageTesting.duos(c, p,parentStr.getGeneLoci());//0 公式
					DnaPiResultItem dnaPiResultItem = new DnaPiResultItem();
					dnaPiResultItem.setGeneLoci(parentStr.getGeneLoci());
					dnaPiResultItem.setPi((Double) result[1]);
					dnaPiResultItem.setFormula(result[0]+"    ");
					dnaPiResultItem.setpProb((Double)result[3]);//p值
					dnaPiResultItem.setqProb((Double)result[4]);//q值
					dnaPiResultItem.setMin((Double)result[5]);//n值
					dnaPiResultItem.setCode(parent);
					dnaPiResultItemList.add(dnaPiResultItem);
					cpi = cpi.multiply(new BigDecimal(dnaPiResultItem.getPi()));
				}
				dnaPiResult.setCpi(cpi);
				dnaPiResult.setRcp(cpi.divide(cpi.add(new BigDecimal(1)),10,BigDecimal.ROUND_DOWN));
				dnaPiResult.setDnaPiResultItemList(dnaPiResultItemList);
				dnaPiResultService.save(dnaPiResult);
			}
		}
		}
	}
	//获得对应的子值
	private DnaExperimentStr getChildStr(DnaExperimentStr parentStr, List<DnaExperimentStr> childStrList) {
		for(DnaExperimentStr child:childStrList){
			if(child.getGeneLoci().equals(parentStr.getGeneLoci())){
				return child;
			}
		}
		return null;
	}


   

	public void export(HttpServletResponse response,DnaSpe dnaSpe) {
		WritableWorkbook  book;
		try {

			List<DnaSpeIteam> dnaSpeIteams=new ArrayList<DnaSpeIteam>();
			for (int i = 0; i < dnaSpe.getDnaSpeIteams().size(); i++) {
				if(dnaSpe.getDnaSpeIteams().get(i) .getSpecimen()!=null){
					dnaSpeIteams.add(dnaSpe.getDnaSpeIteams().get(i));
				}
			}


			if(CollectionUtils.isEmpty(dnaSpeIteams))
			{
				//啥也没选
				throw new Exception("未选择任何编码数据");
			}

			if(dnaSpeIteams.size()>3||dnaSpeIteams.size()<=1)
			{
				throw new Exception("选择个数导致无法检测");
			}

			OutputStream os = response.getOutputStream();// 取得输出流     
			response.reset();// 清空输出流
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String("计算结果导出".getBytes("GB2312"),
							"iso8859_1") + ".xls");// 设定输出文件头
			response.setContentType("application/msexcel");// 定义输出类型
			book=Workbook.createWorkbook(os);
			WritableSheet sheet = book.createSheet("受理", 0);

			/**
			 * 最多三个  父亲  儿子 母亲
			 * 选择三个的话  把按照顺序 分批显示
			 * 两个的话 就是一对显示即可
			 * F  C  M
			 */
			String child="";
			String father="";
			String mather="";
			List<String> bn = new ArrayList<String>();
			bn.add("基因座");//基因座以小孩的为标准
			Map<String,Object> param = new HashMap<String, Object>();

			for(DnaSpeIteam dnaItem:dnaSpeIteams){
				bn.add("案件编号-"+dnaItem.getSpecimen());
				if(!dnaItem.getParrens().contains("C")){//小孩不需要添加
					bn.add("使用公式");
					bn.add("pc值");
					bn.add("pd值");
					bn.add("n值");
					bn.add("PI");
				}else{
					child = dnaItem.getSpecimen();
					param.put("child",dnaItem.getSpecimen());
				}

				if(dnaItem.getParrens().contains("F")){
					father = dnaItem.getSpecimen();
					param.put("father",dnaItem.getSpecimen());
				}

				if(dnaItem.getParrens().contains("M")){
					mather = dnaItem.getSpecimen();
					param.put("mather",dnaItem.getSpecimen());
				}
			}

			if(StringUtils.isEmpty(child)){
				throw new Exception("未选择小孩");
			}

			//标题
			String[] columns =  bn.toArray(new String[bn.size()]);
			DnaPiResultItem item = new DnaPiResultItem();
			List<Double> faterPI = new ArrayList<Double>();
			List<Double> matherPI = new ArrayList<Double>();
			List<DnaExperimentStr> chlidDnaStr=dnaExperimentStrDao.getById(child);//根据小孩查询基因座个数
			for (int i = 0; i < columns.length; i++) {
				sheet.addCell(new Label(i, 0, columns[i]));
				// 从第二列开始就是每次的结果
				//小孩是一定要选的

				for(int j=0;j<chlidDnaStr.size();j++){
					List<DnaPiResultItem> itemList = null;
					//---------------------应该是每个小孩都有21个基因座-------------------
					if(i==0){
						sheet.addCell(new Label(i,j+1,chlidDnaStr.get(j).getGeneLoci()));
					}else{
						if(columns[i].contains(param.get("child").toString())){
							//这一列只显示小孩xy即可
							sheet.addCell(new Label(i,j+1,chlidDnaStr.get(j).getX()+" "+chlidDnaStr.get(j).getY()));
						}

						if(param.get("father")!=null&&columns[i].contains(param.get("father").toString())){
							//这一列显示父亲的XY  后面列根据父亲一并显示
							List<DnaExperimentStr> fatherDnaStr = dnaExperimentStrDao.getById(father);
							//后续列表数据
							sheet.addCell(new Label(i,j+1,fatherDnaStr.get(j).getX()+" "+fatherDnaStr.get(j).getY()));
							//后面跟着4列
							//1、使用公式
							// 2、pc值
							//3、pd值
							//4、n值
							item.setGeneLoci(fatherDnaStr.get(j).getGeneLoci());
							item.setCode(param.get("father").toString());
							itemList =  dnaPiResultItemDao.getByGeneLoci(item);
						}

						if(param.get("mather")!=null&&columns[i].contains(param.get("mather").toString())){
							//这一列显示母亲xy 后续跟上
							List<DnaExperimentStr> matherDnaStr = dnaExperimentStrDao.getById(mather);
							//后续列表数据
							sheet.addCell(new Label(i,j+1,matherDnaStr.get(j).getX()+" "+matherDnaStr.get(j).getY()));
							//后面跟着4列
							//1、使用公式
							// 2、pc值
							//3、pd值 
							//4、n值
							item.setGeneLoci(matherDnaStr.get(j).getGeneLoci());
							item.setCode(param.get("mather").toString());
							itemList =  dnaPiResultItemDao.getByGeneLoci(item);
						}

						if(!CollectionUtils.isEmpty(itemList)){
							//不为空
							sheet.addCell(new Label(i+1,j+1,itemList.get(0).getFormula()==null?"":itemList.get(0).getFormula()));
							sheet.addCell(new Number(i+2,j+1, itemList.get(0).getpProb()==null?0.00:itemList.get(0).getpProb()));
							sheet.addCell(new Number(i+3,j+1, itemList.get(0).getqProb()==null?0.00:itemList.get(0).getqProb()));
							sheet.addCell(new Number(i+4,j+1,itemList.get(0).getMin()==null?0.00:itemList.get(0).getMin()));
							sheet.addCell(new Number(i+5,j+1,itemList.get(0).getPi()==null?0.00:itemList.get(0).getPi()));

							if(param.get("father")!=null&&itemList.get(0).getCode().equals(param.get("father").toString())){
								//父亲那一列的数据
								faterPI.add(itemList.get(0).getPi());
							}

							if(param.get("mather")!=null&&itemList.get(0).getCode().equals(param.get("mather").toString())){
								//父亲那一列的数据
								matherPI.add(itemList.get(0).getPi());
							}
						}
					}
				}

				sheet.setColumnView(i,20);
			}

			double fatherPIX = 1.000000;
			double matherPIX = 1.000000;
			//再加一行计算PI 分开父亲母亲的即可
			if(faterPI!=null&&faterPI.size()>0){
				// 选择了父亲 并且PI值放在里面
				for(double d:faterPI){
					if(d!=0.00){
						fatherPIX *=d;
					}
				}
				sheet.addCell(new Label(0,chlidDnaStr.size()+1,"父亲pi乘积"));
				sheet.addCell(new Number(1,chlidDnaStr.size()+1,fatherPIX));
			}

			if(matherPI!=null&&matherPI.size()>0){
				// 选择了母亲 并且PI值放在里面
				for(double d:matherPI){
					if(d!=0.00){
						matherPIX *= d;
					}
				}
				sheet.addCell(new Label(2,chlidDnaStr.size()+1,"母亲pi乘积"));
				sheet.addCell(new Number(3,chlidDnaStr.size()+1,matherPIX));
			}
			//添加数据进去即可
			// 写入数据并关闭文件
			book.write();
			book.close();
			os.flush();
			os.close();
			response.flushBuffer();
			PrintWriter out = response.getWriter();
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();            
		}finally {

		}
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

