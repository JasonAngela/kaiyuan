/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entrust.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import com.thinkgem.jeesite.modules.dna.dao.DnaExperimentStrDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaPiResultDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaPiResultItemDao;
import com.thinkgem.jeesite.modules.dna.dao.DnaReceiveIteamDao;
import com.thinkgem.jeesite.modules.dna.entity.DnaExperimentStr;
import com.thinkgem.jeesite.modules.dna.entity.DnaPiResult;
import com.thinkgem.jeesite.modules.dna.entity.DnaPiResultItem;
import com.thinkgem.jeesite.modules.dna.entity.DnaReceive;
import com.thinkgem.jeesite.modules.dna.entity.DnaReceiveIteam;
import com.thinkgem.jeesite.modules.dna.service.DnaPiResultService;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustAbstracts;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustPerson;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustRegister;
import com.thinkgem.jeesite.modules.entrust.entity.Licensed;
import com.thinkgem.jeesite.modules.entrust.entity.Word2007ToHtml;
import com.thinkgem.jeesite.modules.material.service.SpecimenMaterialRegisterService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.service.DictService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.word.WordExportUtil;

import math.geom3d.transform.Transform3D;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustExpertOpinion;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.entrust.dao.EntrustAbstractsDao;
import com.thinkgem.jeesite.modules.entrust.dao.EntrustExpertOpinionDao;
import com.thinkgem.jeesite.modules.entrust.dao.EntrustRegisterDao;
import com.thinkgem.jeesite.modules.entrust.dao.LicensedDao;

/**
 * 鉴定意见Service
 * @author zhuguli
 * @version 2017-05-11
 */
@Service
@Transactional(readOnly = true)
public class EntrustExpertOpinionService extends CrudService<EntrustExpertOpinionDao, EntrustExpertOpinion> {
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private EntrustRegisterService entrustRegisterService;

	@Autowired
	private EntrustAbstractsService entrustAbstractsService;

	@Autowired
	private SpecimenMaterialRegisterService specimenMaterialRegisterService;

	@Autowired
	private DnaExperimentStrDao dnaExperimentStrDao;

	@Autowired
	private EntrustExpertOpinionDao entrustExpertOpinionDao;
	@Autowired
	private DnaReceiveIteamDao dnaReceiveIteamDao;
	@Autowired
	private EntrustAbstractsDao entrustAbstractsDao;
	@Autowired
	private DnaPiResultService dnaPiResultService;
	@Autowired
	private DnaPiResultItemDao dnaPiResultItemDao;
	@Autowired
	private DnaPiResultDao dnaPiResultDao;
	@Autowired
	private LicensedDao licensedDao;
	
	@Autowired
	private EntrustRegisterDao entrustRegisterDao;
	public final static char[] upper = "零一二三四五六七八九十".toCharArray();
	public EntrustExpertOpinion get(String id) {
		return super.get(id);
	}

	public List<EntrustExpertOpinion> findList(EntrustExpertOpinion entrustExpertOpinion) {
		return super.findList(entrustExpertOpinion);
	}

	public Page<EntrustExpertOpinion> findPage(Page<EntrustExpertOpinion> page, EntrustExpertOpinion entrustExpertOpinion) {
		return super.findPage(page, entrustExpertOpinion);
	}
	
	//判断同一认定
	@Transactional(readOnly = false)
	public List<Integer> enable(String t, String t2, String t3) {
		// TODO Auto-generated method stub
		List<Integer>li=new ArrayList<Integer>();
		int tORt2=0;
		int t3ORt2=0;
		int t3ORt=0;
		
/*		
		String tAndt2="";
		String tAnadt3="";*/
		Map<String, List<String>>map =new HashMap<String, List<String> >();
		List<DnaExperimentStr>dnaExperimentStrT= dnaExperimentStrDao.getCode(t);
		List<DnaExperimentStr>dnaExperimentStrT2=dnaExperimentStrDao.getCode(t2);
		for (DnaExperimentStr dnaExperimentStrTs : dnaExperimentStrT) {
			for (DnaExperimentStr dnaExperimentStrTs2 : dnaExperimentStrT2){
				if(dnaExperimentStrTs.getGeneLoci().equals(dnaExperimentStrTs2.getGeneLoci())){
					 if(!dnaExperimentStrTs.getX().equals(dnaExperimentStrTs2.getX())||!dnaExperimentStrTs.getY().equals(dnaExperimentStrTs2.getY())){
						 tORt2++;
						// tAndt2+="样本"+dnaExperimentStrTs.getSpecimenCode()+"和样本"+dnaExperimentStrTs2.getSpecimenCode()+  "基因座的"+dnaExperimentStrTs.getGeneLoci()+"位点X的"+dnaExperimentStrTs.getX()+"和"+dnaExperimentStrTs2.getX()+"</br>";
					 }
				}
			}
		}
		
		if(t3!=null){
			List<DnaExperimentStr>dnaExperimentStrT3=dnaExperimentStrDao.getCode(t3);
			for (DnaExperimentStr dnaExperimentStr3 : dnaExperimentStrT3) {
				for (DnaExperimentStr dnaExperimentStrTs2 : dnaExperimentStrT2) {
					if(dnaExperimentStr3.getGeneLoci().equals(dnaExperimentStrTs2.getGeneLoci())){
					 if(!dnaExperimentStr3.getX().equals(dnaExperimentStrTs2.getX())||!dnaExperimentStr3.getY().equals(dnaExperimentStrTs2.getY())){
						 t3ORt2++;
					 }
				}
			}	
		}		
			for (DnaExperimentStr dnaExperimentStr3 : dnaExperimentStrT3) {
				for (DnaExperimentStr dnaExperimentStrTs : dnaExperimentStrT) {
					if(dnaExperimentStr3.getGeneLoci().equals(dnaExperimentStrTs.getGeneLoci())){
					if(!dnaExperimentStr3.getX().equals(dnaExperimentStrTs.getX())||!dnaExperimentStr3.getY().equals(dnaExperimentStrTs.getY())){
						t3ORt++;
					}
				}
			}
		}
	}
		li.add(tORt2);
		li.add(t3ORt2);
		li.add(t3ORt);
		return li;
	}
	
	
	

	//不通过
	@Transactional(readOnly = false)
	public void noFlact(String[] noCode){
		
		DnaReceive dnaReceive=dnaReceiveIteamDao.getCode(noCode[0]).getRecive();
		
		
		for (String code : noCode) {
			code=code+"(2)";
			DnaReceiveIteam dnaReceiveIteam=new DnaReceiveIteam();
			dnaReceiveIteam.setRecive(dnaReceive);
			dnaReceiveIteam.preInsert();
			dnaReceiveIteam.setSpecode(code);
			dnaReceiveIteamDao.insert(dnaReceiveIteam);
		}
	}
	
	public DnaReceiveIteam selectDnaReceive(String[] noCode){
		DnaReceiveIteam dnaReceiveIteam=new DnaReceiveIteam();
		for (String code : noCode) {
			code=code+"(2)";
			dnaReceiveIteam =dnaReceiveIteamDao.getCode(code);
		}
		return dnaReceiveIteam;
	}
	
	
	@Transactional(readOnly = false)
    /**
     * 成文修改
     */
    public void save(EntrustExpertOpinion entrustExpertOpinion) {
		if(!StringUtils.isEmpty(entrustExpertOpinion.getFinalVersion())){
		entrustExpertOpinion.setFinalVersion(StringEscapeUtils.unescapeHtml4(entrustExpertOpinion.getFinalVersion().trim()));
		}
		entrustExpertOpinion.setExplain(StringEscapeUtils.unescapeHtml4(entrustExpertOpinion.getExplain()).trim());
		if(entrustExpertOpinion.getIsNewRecord()){
			entrustExpertOpinion.setExplainRemark(StringEscapeUtils.unescapeHtml4(entrustExpertOpinion.getExplain()).trim());
			entrustExpertOpinion.setDraftRemark(StringEscapeUtils.unescapeHtml4(entrustExpertOpinion.getDraft()).trim());
		}
		super.save(entrustExpertOpinion);
		// 完成流程任务
		Map<String, Object> vars = Maps.newHashMap();
		
		if(!(entrustExpertOpinion.getAct().getTaskDefKey().equals("reportInit")||entrustExpertOpinion.getAct().getTaskDefKey().equals("requestEnd"))){
			vars.put("flag", entrustExpertOpinion.getAct().getFlag().equals("true")?1:0);
		}
		actTaskService.complete(entrustExpertOpinion.getAct().getTaskId(), entrustExpertOpinion.getAct().getProcInsId(), entrustExpertOpinion.getAct().getComment(), entrustExpertOpinion.getRemarks(), vars);
		  List<Licensed> licenseds = licensedDao.findEntrust(entrustExpertOpinion.getRegister().getId());
		//生成文档
		if(licenseds.size()>2){
			//查询出所需数据
		EntrustRegister entrustRegister = entrustRegisterService.get(entrustExpertOpinion.getAct().getBusinessId());
        if (entrustRegister != null) {
            SimpleDateFormat simple = new SimpleDateFormat("yyyy年MM月dd日");
            //DNA2017071100057
            
            String casecode = entrustRegister.getCaseCode().substring(11, 16);
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
            String fm = "";
            List<EntrustAbstracts> allentrustAbstracts = entrustAbstractsService.findAllentrustAbstracts(entrustRegister.getId());
            List<String> father = new ArrayList<String>();
            List<String> mather = new ArrayList<String>();
            List<String> children = new ArrayList<String>(); 
            String t=null;
            String t2=null;
            String t3=null;
            if(allentrustAbstracts.size()==1){
    			fm="分析"+allentrustAbstracts.get(0).getClientName()+"的DNA数据1111111";
    		}else if(allentrustAbstracts.get(0).getSpecimenCode().contains("T")){
				for (EntrustAbstracts entrustAbstracts2 : allentrustAbstracts) {
					if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 1, entrustAbstracts2.getSpecimenCode().length()).equals("T")) {
						t=entrustAbstracts2.getClientName();
					}
					if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 2, entrustAbstracts2.getSpecimenCode().length()).equals("T2")) {
						t2=entrustAbstracts2.getClientName();
					}
					
					if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 2, entrustAbstracts2.getSpecimenCode().length()).equals("T3")) {
						t3=entrustAbstracts2.getClientName();
					}
				}
				if(t3==null){
					fm=t+"和"+t2+"血样的DNA是否一致1111111";
				}else{
					fm=t+","+t2+"和"+t3+"血样的DNA是否一致1111111";
				}
			}else{
            for (int i = 0; i < allentrustAbstracts.size(); i++) {
                String appellationname = allentrustAbstracts.get(i).getAppellation();
                String ClientName = allentrustAbstracts.get(i).getClientName();
                if (appellationname.equals("1")) {
                    father.add(ClientName);
                }
                if (appellationname.equals("2")) {
                    mather.add(ClientName);
                }
                if (appellationname.equals("3")) {
                    children.add(ClientName);
                }
            }
            if (father.size() != 0) {
                for (int j = 0; j < father.size(); j++) {
                    for (int ds = 0; ds < children.size(); ds++) {
                        fm += father.get(j) + "是否为" + children.get(ds) + "的生物学父亲" + "<w:br/>";
                    }
                }

            }
            if (mather.size() > 0) {
                for (int j = 0; j < mather.size(); j++) {
                    for (int ds = 0; ds < children.size(); ds++) {
                        fm += mather.get(j) + "是否为" + children.get(ds) + "的生物学母亲" + "<w:br/>";
                    }
                }
            }
    		}

            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");//设置日期格式

            //数量
            int qty = 0;
            //鉴定类型
            String materialType = "";
            for (int ij = 0; ij < specimenMaterialRegisterService.findmaterial(entrustRegister.getId()).size(); ij++) {
                qty += specimenMaterialRegisterService.findmaterial(entrustRegister.getId()).get(ij).getQty();
                materialType = specimenMaterialRegisterService.findmaterial(entrustRegister.getId()).get(ij).getMaterialType();
            }

            //被鉴定人员
            List<EntrustAbstracts> entrustAbstracts = entrustAbstractsService.findAllentrustAbstracts(entrustRegister.getId());
            List<EntrustAbstracts> entrustAbstracts3 = new ArrayList<EntrustAbstracts>();
        	if(allentrustAbstracts.size()==1){
    			for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
    				if(!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())){
    					if(entrustAbstracts2.getSpecimenCode().contains("-G")){
    						entrustAbstracts3.add(entrustAbstracts2);
    					}
    				}
    			}    
    		}
            if (entrustAbstracts.size() == 2) {
                for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                    if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                        if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 1, entrustAbstracts2.getSpecimenCode().length()).equals("F")) {
                            entrustAbstracts3.add(entrustAbstracts2);
                        }
                    }
                }
                for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                    if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                        if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 1, entrustAbstracts2.getSpecimenCode().length()).equals("M")) {
                            entrustAbstracts3.add(entrustAbstracts2);
                        }
                    }
                }
                for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                    if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                        if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 2, entrustAbstracts2.getSpecimenCode().length()).contains("C")) {
                            entrustAbstracts3.add(entrustAbstracts2);
                        }
                    }
                }
                for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                    if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                        if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 1, entrustAbstracts2.getSpecimenCode().length()).equals("T")) {
                            entrustAbstracts3.add(entrustAbstracts2);
                        }
                    }
                }
                for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                    if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                        if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 2, entrustAbstracts2.getSpecimenCode().length()).equals("T2")) {
                            entrustAbstracts3.add(entrustAbstracts2);
                        }
                    }
                }
            }

            if (entrustAbstracts.size() >= 3) {
                for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                	
                    if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                        if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 1, entrustAbstracts2.getSpecimenCode().length()).equals("F")) {
                            entrustAbstracts3.add(entrustAbstracts2);
                        }
                    }
                }
                for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                    	  if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                              if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 1, entrustAbstracts2.getSpecimenCode().length()).equals("C")) {
                                  entrustAbstracts3.add(entrustAbstracts2);
                              }
                    	  } 
                } 
                    	   for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                         	  if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                         		 if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 2, entrustAbstracts2.getSpecimenCode().length()).equals("C2")) {
                                       entrustAbstracts3.add(entrustAbstracts2);
                                   }
                         	  }   
                    	   }  
                    	   for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                          	  if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                          		 if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 2, entrustAbstracts2.getSpecimenCode().length()).equals("C3")) {
                                        entrustAbstracts3.add(entrustAbstracts2);
                                    }
                          	  }   
                     	   }  
                    	   for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                          	  if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                          		 if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 2, entrustAbstracts2.getSpecimenCode().length()).equals("C4")) {
                                        entrustAbstracts3.add(entrustAbstracts2);
                                    }
                          	  }   
                     	   }  
                         
                    
                
                for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                    if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                        if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 1, entrustAbstracts2.getSpecimenCode().length()).equals("M")) {
                            entrustAbstracts3.add(entrustAbstracts2);
                        }
                    }
                }
                
                for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                    if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                        if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 1, entrustAbstracts2.getSpecimenCode().length()).equals("T")) {
                            entrustAbstracts3.add(entrustAbstracts2);
                        }
                    }
                }
                for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                    if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                        if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 2, entrustAbstracts2.getSpecimenCode().length()).equals("T2")) {
                            entrustAbstracts3.add(entrustAbstracts2);
                        }
                    }
                }
                for (EntrustAbstracts entrustAbstracts2 : entrustAbstracts) {
                    if (!StringUtils.isEmpty(entrustAbstracts2.getSpecimenCode())) {
                        if (entrustAbstracts2.getSpecimenCode().substring(entrustAbstracts2.getSpecimenCode().length() - 2, entrustAbstracts2.getSpecimenCode().length()).equals("T3")) {
                            entrustAbstracts3.add(entrustAbstracts2);
                        }
                    }
                }
            }

            
            String material = null;
            if (StringUtils.isNotEmpty(materialType)) {
                material = DictUtils.getDictLabels(materialType, "material_TypeCode", "未知");
            }
            //检案摘要
            String testcase = entrustRegister.getEntrustDate() + ",";
            String parens = "";
            String chird = "";
            for (String fathers : father) {
                parens += fathers + "、";
            }
            for (String mathers : mather) {
                parens += mathers + "、";
            }
            for (String childrens : children) {
                chird += childrens + "、";
            }
            
            if(entrustAbstracts.size()==1){
				   //由我鉴定中心工作人员采集刘欣玥的手指血一份，我鉴定中心仅对样本的检验负责。
				   testcase+=entrustRegister.getClientName()+"委托我鉴定中心对"+entrustAbstracts.get(0).getClientName()+"的DNA数据，由我鉴定中心工作人员采集"+entrustAbstracts.get(0).getClientName()
						   +"的手指血一份，我鉴定中心仅对样本的检验负责。";
			   }else if(entrustAbstracts.get(0).getSpecimenCode().contains("T")){
			   //的DNA是否一致，由委托方提供P000655008与P000655000的血痕各一份，我鉴定中心仅对样本的检验负责。
			   if(t3==null){
				   testcase+=entrustRegister.getClientName()+"委托我鉴定中心鉴定"+t+"和"+t2+"的DNA是否一致，由委托方提供"+t+"与"+t2+"的血痕各一份，我鉴定中心仅对样本的检验负责。";
			   }else{
				   testcase+=entrustRegister.getClientName()+"委托我鉴定中心鉴定"+t+"，"+t2+"和"+t3+"的DNA是否一致，由委托方提供"+t+"，"+t2+"与"+t3+"的血痕各一份，我鉴定中心仅对样本的检验负责。";
			   }
		   }else{
            String dd = parens + chird;
            String cc = dd.substring(0, dd.length() - 1);
            dd = cc.substring(0, cc.lastIndexOf("、")) + "与" + cc.substring(cc.lastIndexOf("、") + 1, cc.length());
            testcase += entrustRegister.getClientName() + "委托我鉴定中心对" + parens.substring(0, parens.length() - 1) + "与" + chird.substring(0, chird.length() - 1) + "进行亲子关系鉴定,由我鉴定中心工作人员采集"
                    + dd + "的" + material + "各一份，我鉴定中心仅对样本的检验负责。";
			  }
            List<DnaExperimentStr> strList = dnaExperimentStrDao.getByRegisterId(entrustRegister.getId());

            List<String> newList = new ArrayList<String>();
            for (DnaExperimentStr dnaExperimentStr : strList) {
                if (!newList.contains(dnaExperimentStr.getGeneLoci())) {
                    newList.add(dnaExperimentStr.getGeneLoci());
                }
            }
            LinkedHashMap<String, LinkedHashMap<String, String>> strMap = genateMapFromList2(strList);
            //实验报告
            List<EntrustExpertOpinion> entrustExpertOpinionList = entrustExpertOpinionDao.getByRegisterId(entrustRegister.getId());

            //分析说明
            String finaler = "";
            //鉴定意见
            String explain = "";
            if (entrustExpertOpinionList.size() > 0) {
                for (int i = 0; i < entrustExpertOpinionList.size(); i++) {
                    finaler = entrustExpertOpinionList.get(0).getFinalVersion();
                    explain = entrustExpertOpinionList.get(0).getDraft();
                }
            }
            Map<String, Object> beanParams = new HashMap<String, Object>();
            beanParams.put("clientName", transform(entrustRegister.getClientName()));
            beanParams.put("fm", fm.substring(0, fm.length() - 7));
            beanParams.put("df", entrustRegister.getEntrustDate());
            if (StringUtils.isNotEmpty(materialType)) {
                String materialType1 = DictUtils.getDictLabels(materialType, "material_TypeCode", "未知");
                beanParams.put("materialType", materialType1);
            }
            //qty
            beanParams.put("qty", qty);
            beanParams.put("entrustAbstracts", entrustAbstracts3);
            List<EntrustPerson> personList = new ArrayList<EntrustPerson>();
            for (EntrustAbstracts a1 : entrustAbstracts3) {
                EntrustPerson b = new EntrustPerson();
                b.setName(a1.getClientName());

                if (StringUtils.isNotEmpty(a1.getGender())) {
                    String sex = DictUtils.getDictLabels(a1.getGender(), "sex", "未知");
                    b.setSex(sex);
                }

                if (StringUtils.isNotEmpty(a1.getIdType())) {
                    String idTypeCode = DictUtils.getDictLabels(a1.getIdType(), "idTypeCode", "未知");
                    b.setIdType(idTypeCode);
                }

                b.setIdNo(a1.getIdNo());
                b.setSimpleCode(a1.getSpecimenCode());
                personList.add(b);
            }

            beanParams.put("personList", personList);
            beanParams.put("testcase", transform(testcase).replaceAll("<w:br>", "<w:br/>"));
            beanParams.put("str", strMap);
            beanParams.put("explain", explain.replaceFirst(";", ";<w:br/>   ").replaceAll("\r\n\r\n", "<w:br/>   "));

            //对finaler进行转义
            finaler = finaler.replaceAll("&nbsp;", " ").replaceAll("<p>", "")
                    .replaceAll("</p>", "").replaceAll("&times;", "×")
                    .replaceAll("<w:br>", "<w:br/>").replaceAll("</w:br>", "");
            beanParams.put("finaler", finaler);
            beanParams.put("simple", getUpperDate(simple.format(new Date())));
            SimpleDateFormat year = new SimpleDateFormat("yyyy");
            beanParams.put("year", year.format(new Date()));
            //casecode
            beanParams.put("casecode", casecode);
             String nameTo=null;  
            if(explain.contains("排除")){
            	nameTo= entrustRegister.getClientName() + "的鉴定意见书（排除）";
            }else{
            	nameTo= entrustRegister.getClientName() + "的鉴定意见书";
            }
            if(allentrustAbstracts.size()==1){
				   WordExportUtil.writeResponse(WordExportUtil.WORD_2003,nameTo,"templateDir","entrustExpertOpinion2.ftl",beanParams,entrustRegister);
            }else{
            	   WordExportUtil.writeResponse(WordExportUtil.WORD_2003,nameTo,"templateDir","entrustExpertOpinion.ftl",beanParams,entrustRegister); 
            }
            
            
          //word  转pdf
        String start = "D:"+File.separator+"information"+File.separator+"DNA"+File.separator+entrustRegister.getCode()+File.separator+nameTo;
   		String path="D:"+File.separator+"information"+File.separator+"DNA"+File.separator+entrustRegister.getCode()+File.separator+"report of DNA" + ".pdf";
   		Word2007ToHtml.wordToPDF2(start, path); 
			
		}
		}	
		
	}
        
        
        



	/**
	 *
			
	 * @param date
	 * @return
	 */
	public static String getUpperDate(String date) {
		//支持yyyy-MM-dd、yyyy/MM/dd、yyyyMMdd等格式
		if(date == null) return null;
		//非数字的都去掉
		date = date.replaceAll("\\D", "");
		if(date.length() != 8) return null;
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<4;i++) {//年
			sb.append(upper[Integer.parseInt(date.substring(i, i+1))]);
		}
		sb.append("年");//拼接年
		int month = Integer.parseInt(date.substring(4, 6));
		if(month <= 10) {
			sb.append(upper[month]);
		} else {
			sb.append("十").append(upper[month%10]);
		}
		sb.append("月");//拼接月

		int day = Integer.parseInt(date.substring(6));
		if (day <= 10) {
			sb.append(upper[day]);
		} else if(day < 20) {
			sb.append("十").append(upper[day % 10]);
		} else {
			sb.append(upper[day / 10]).append("十");
			int tmp = day % 10;
			if (tmp != 0) sb.append(upper[tmp]);
		}
		sb.append("日");//拼接日
		return sb.toString();
	}

	/**
	 * 处理转义字符
	 * @param str
	 * @return
	 */
	private String transform(String str){

		if(str.contains("<")||str.contains(">")||str.contains("&")){
			str=str.replaceAll("&", "&amp;");
			str=str.replaceAll("<", "&lt;");
			str=str.replaceAll(">", "&gt;");
		}

		return str;
	}

	private Map<String,Map<String,String>> genateMapFromList(List<DnaExperimentStr> strList) {
		Map<String,Map<String,String>> strMapList =  new HashMap<String, Map<String,String>>();
		for(DnaExperimentStr str :strList){
			String geneLoci = str.getGeneLoci();
			String specimenCode = str.getSpecimenCode();
			String x = str.getX();
			String y = str.getY();
			if(strMapList.containsKey(geneLoci)){
				Map<String, String> map = strMapList.get(geneLoci);
				map.put(specimenCode, x+"   "+(y));
				map.put(specimenCode+"x",x);
				map.put(specimenCode+"y",y);
			}else{
				Map<String, String> map =  new HashMap<String, String>();
				map.put(specimenCode, x+"   "+(y));
				map.put(specimenCode+"x",x);
				map.put(specimenCode+"y",y);
				strMapList.put(geneLoci, map);
			}
		}
		return strMapList;
	}

	@Transactional(readOnly = false)
	public void delete(EntrustExpertOpinion entrustExpertOpinion) {
		super.delete(entrustExpertOpinion);
	}

	public List<EntrustExpertOpinion> getByRegisterId(String registerId) {
		return this.dao.getByRegisterId(registerId);
	}
	@Transactional(readOnly = false)
	public void saveAnalyze(EntrustExpertOpinion entrustExpertOpinion,String [] noCode) {
		Map<String, Object> vars = Maps.newHashMap();
		if(entrustExpertOpinion.getAct().getFlag().equals("false")){
			String registerId=entrustAbstractsDao.findSpecimenCode(noCode[0]);
			List<DnaPiResult> piResults=dnaPiResultService.getRgister(registerId);
			for(DnaPiResult result:piResults){
				List<DnaPiResultItem>	dnaPiResultItems=result.getDnaPiResultItemList();
				for (DnaPiResultItem dnaPiResultItem : dnaPiResultItems) {
					dnaPiResultItemDao.delRegister(dnaPiResultItem.getId());
				}
				dnaPiResultDao.delRegister(result.getId());
			}

			DnaReceiveIteam dnaReceiveIteam=this.selectDnaReceive(noCode);
			if(dnaReceiveIteam==null){
				this.noFlact(noCode);
			}
			EntrustRegister entrustRegister = entrustRegisterService.get(registerId);
 			if(entrustRegister!=null){
 				//重返鉴定
				entrustRegister.setStatus("9");
				entrustRegisterDao.update(entrustRegister);
	 			}
		}else{
			String registerId1=	entrustExpertOpinion.getRegister().getId();
			EntrustRegister entrustRegister = entrustRegisterService.get(registerId1);
			if(entrustRegister!=null){
 				//第一鉴定人(初稿)
				entrustRegister.setStatus("10");
				entrustRegisterDao.update(entrustRegister);
	 			}
		}
	
		vars.put("flag", entrustExpertOpinion.getAct().getFlag().equals("true")?1:0);
		actTaskService.complete(entrustExpertOpinion.getAct().getTaskId(), entrustExpertOpinion.getAct().getProcInsId(), entrustExpertOpinion.getAct().getComment(), entrustExpertOpinion.getRemarks(), vars);
		
	}


    private LinkedHashMap<String, LinkedHashMap<String, String>> genateMapFromList2(List<DnaExperimentStr> strList) {

        LinkedHashMap<String, LinkedHashMap<String, String>> strMapList = new LinkedHashMap<String, LinkedHashMap<String, String>>();
        for (DnaExperimentStr str : strList) {
            String geneLoci = str.getGeneLoci();
            String specimenCode = str.getSpecimenCode();
            String x = str.getX();
            String y = str.getY();
            if (strMapList.containsKey(geneLoci)) {
                LinkedHashMap<String, String> map = strMapList.get(geneLoci);
                map.put(specimenCode, x + "   " + (y));
                map.put(specimenCode + "x", x);
                map.put(specimenCode + "y", y);
            } else {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put(specimenCode, x + "   " + (y));
                map.put(specimenCode + "x", x);
                map.put(specimenCode + "y", y);
                strMapList.put(geneLoci, map);
            }
        }
        return strMapList;
    }

	

}