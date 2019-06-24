/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clinic.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.filter.PhotoUploud;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicPhysicalIteam;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicRegister;
import com.thinkgem.jeesite.modules.clinic.entity.ClinicRegisterphy;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustAbstracts;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustRegister;
import com.thinkgem.jeesite.modules.entrust.entity.Word2007ToHtml;
import com.thinkgem.jeesite.modules.sys.service.SysCodeRuleService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.act.utils.ActUtils;
import com.thinkgem.jeesite.modules.clinic.dao.ClinicRegisterDao;
import com.thinkgem.jeesite.modules.clinic.dao.ClinicRegisterphyDao;

/**
 * 临床登记Service
 * @author zhuguli
 * @version 2017-10-15
 */
@Service
@Transactional(readOnly = true)
public class ClinicRegisterService extends CrudService<ClinicRegisterDao, ClinicRegister> {
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private SysCodeRuleService codeRuleService;
	@Autowired
	private ClinicRegisterDao clinicRegisterDao;
	@Autowired
	private ClinicRegisterphyDao clinicRegisterphyDao;

	@Autowired
	private DictService dictService;
	public ClinicRegister get(String id) {
		return super.get(id);
	}
	
	public List<ClinicRegister> findList(ClinicRegister clinicRegister) {
		return super.findList(clinicRegister);
	}
	
	public Page<ClinicRegister> findPage(Page<ClinicRegister> page, ClinicRegister clinicRegister) {
		return super.findPage(page, clinicRegister);
	}
	public List<ClinicRegister>findExport(ClinicRegister clinicRegister){
		return clinicRegisterDao.findExport(clinicRegister);
	}
	
public List<ClinicRegisterphy>   findListclinic(ClinicRegister clinicRegister){
	ClinicRegisterphy clinicRegisterphy=new ClinicRegisterphy();
	clinicRegisterphy.setRegister(clinicRegister);
	List<ClinicRegisterphy>clinicRegisterphyList=clinicRegisterphyDao.findList(clinicRegisterphy);
	return clinicRegisterphyList;
}
	
	
	/**
	 *
	 * @param clinicRegister
	 * @throws IOException
	 */
	public void exportWord(ClinicRegister clinicRegister) throws IOException{
		Map<String, Object> beanParams = new HashMap<String, Object>();
		String fileName = clinicRegister.getClientName()+ "的鉴定委托书";
	        //DNA2017071100057
	        String casecode = clinicRegister.getCaseCode().substring(10, 15);
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
	    beanParams.put("simple", clinicRegister.getCaseCode().substring(2, 6));
	    beanParams.put("casecode",casecode);
		beanParams.put("clientName",clinicRegister.getClientName());
		beanParams.put("agentName",clinicRegister.getAgentName());
		beanParams.put("clientZipcode",clinicRegister.getClientZipcode());
		beanParams.put("agentTel",clinicRegister.getAgentTel());
		beanParams.put("surveyorName",clinicRegister.getSurveyorName());
		Dict dict = new Dict();
		if(!StringUtils.isEmpty(clinicRegister.getSurveyorSex()))
		{
			dict.setType("sex");
			dict.setValue(clinicRegister.getSurveyorSex());
			Dict sex = dictService.findValue(dict);
			beanParams.put("surveyorSex",sex==null?"":sex.getLabel());
		}

		
		beanParams.put("surveyorBirthday",clinicRegister.getSurveyorBirthday());
		beanParams.put("clientReceiver",clinicRegister.getClientReceiver());
		beanParams.put("type",clinicRegister.getType());
		beanParams.put("clientEmail",clinicRegister.getClientEmail());
		beanParams.put("clientArea", StringUtils.isNotEmpty(clinicRegister.getClientArea())?clinicRegister.getClientArea():"");
		//验伤通知书
		if(!StringUtils.isEmpty(clinicRegister.getClinicTriage())){
			beanParams.put("clinicTriage",clinicRegister.getClinicTriage());
		}else{
			beanParams.put("clinicTriage","___");
		}
		//病史
		if(!StringUtils.isEmpty(clinicRegister.getClinicMedical())){
			beanParams.put("clinicMedical",clinicRegister.getClinicMedical());
		}else{
			beanParams.put("clinicMedical","___");
		}
		//出院小结
		if(!StringUtils.isEmpty(clinicRegister.getClinicSummary())){
			beanParams.put("clinicSummary",clinicRegister.getClinicSummary());
		}else{
			beanParams.put("clinicSummary","___");
		}
		//X片
		if(!StringUtils.isEmpty(clinicRegister.getClinicXray())){
			beanParams.put("clinicXray",clinicRegister.getClinicXray());
		}else{
			beanParams.put("clinicXray","___");
		}
		//Ct
		
		//X片
				if(!StringUtils.isEmpty(clinicRegister.getClinicCt())){
					beanParams.put("clinicCt",clinicRegister.getClinicCt());
				}else{
					beanParams.put("clinicCt","___");
				}
		
		//Mri
				if(!StringUtils.isEmpty(clinicRegister.getClinicMri())){
					beanParams.put("clinicMri",clinicRegister.getClinicMri());
				}else{
					beanParams.put("clinicMri","___");
				}
		//其它
				if(!StringUtils.isEmpty(clinicRegister.getAppraisalItem())){
					beanParams.put("appraisalItem",clinicRegister.getAppraisalItem());
				}else{
					beanParams.put("appraisalItem","_________________");
				}
		//报告方式		
		beanParams.put("sendMode",StringUtils.isNotEmpty(clinicRegister.getSendMode())?clinicRegister.getSendMode():"");
		
		//邮编
		if(!StringUtils.isEmpty(clinicRegister.getMattersEntrusted())){
			beanParams.put("mattersEntrusted",clinicRegister.getMattersEntrusted());
		}else{
			beanParams.put("mattersEntrusted","______");
		}
		//地址与收件人
		if(!StringUtils.isEmpty(clinicRegister.getClientAddress())){
			beanParams.put("clientAddress",clinicRegister.getClientAddress());
		}else{
			beanParams.put("clientAddress","_______");
		}
		
		beanParams.put("timeLimitResult",clinicRegister.getTimeLimitResult());
		
		//申请回避
		if(!StringUtils.isEmpty(clinicRegister.getTimeLimitReport())){
			beanParams.put("timeLimitReport",clinicRegister.getTimeLimitReport());
		}else{
			beanParams.put("timeLimitReport","______");
		}
		
		
		//理由
		if(!StringUtils.isEmpty(clinicRegister.getSpecialty())){
			beanParams.put("specialty",clinicRegister.getSpecialty());
		}else{
			beanParams.put("specialty","_______");
		}
		
		
		//本鉴定所意见
		if(!StringUtils.isEmpty(clinicRegister.getClientFax())){
			beanParams.put("clientFax",clinicRegister.getClientFax());
		}else{
			beanParams.put("clientFax","___________________");
		}
	
		
		beanParams.put("materialDispose",clinicRegister.getMaterialDispose());
		beanParams.put("material",StringUtils.isNotEmpty(clinicRegister.getMaterial())?clinicRegister.getMaterial():"");
		//标准
		if(clinicRegister.getClientFax()!=null){
			beanParams.put("standardFee",clinicRegister.getStandardFee());
		}else{
			beanParams.put("standardFee","_____");
		}
		//协议
		if(clinicRegister.getSpecialFee()!=null){
			beanParams.put("specialFee",clinicRegister.getSpecialFee());
		}else{
			beanParams.put("specialFee","_____");
		}
		 String time=  clinicRegister.getClientZipcode().substring(0, 4)+"-"+clinicRegister.getClientZipcode().substring(5, 7)+"-"+clinicRegister.getClientZipcode().substring(8, 10);
		 String time1=  clinicRegister.getClientZipcode().substring(0, 4);
		 beanParams.put("t",time1);
		 beanParams.put("m",time);
		//疑难复杂
		beanParams.put("totalFee",clinicRegister.getTotalFee());
		WordExportUtil.writeResponse(WordExportUtil.WORD_2003,fileName,"templateDir","clinicRegister.ftl",beanParams,clinicRegister);


		//word  转pdf
	    String docName = "D:"+File.separator+"information"+File.separator+"法医临床病理"+File.separator+clinicRegister.getCode()+File.separator+fileName;
		String path="D:"+File.separator+"information"+File.separator+"法医临床病理"+File.separator+clinicRegister.getCode()+File.separator+"power of attorney" + ".pdf";
		  
		Word2007ToHtml.wordToPDF2(docName, path); 
	}



	private String clinicRegister() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 发起流程
	 */
	@Transactional(readOnly = false)
	public void save(ClinicRegister clinicRegister,HttpServletRequest request)  throws Exception{
		boolean isNew =  clinicRegister.getIsNewRecord();
		if(isNew){
			clinicRegister.setStatus("2");//未物证登记
			String code=codeRuleService.generateCode("entrust_register_code");
			clinicRegister.setCode(code);
			if(clinicRegister.getType().equals("1")||clinicRegister.getType().equals("2")||clinicRegister.getType().equals("3")||clinicRegister.getType().equals("4")){
				//disabilityCode  残疾鉴定
				String casecode=codeRuleService.generateCode("clinic_disabilityCode");
				clinicRegister.setCaseCode(casecode);
			}
		}
		super.save(clinicRegister);
		
		// 上传到D盘的图片
				PhotoUploud photoUploud=new PhotoUploud();
				List<ClinicRegisterphy> clinicRegisterphyList= 	clinicRegister.getClinicRegisterphyList();
				String toName = "D:"+File.separator+"information"+File.separator+"法医临床病理"+File.separator+clinicRegister.getCode()+File.separator+"材料登记图片"+File.separator;
				for (int i = 0; i < clinicRegisterphyList.size(); i++) {
					photoUploud.UploudPhoto(clinicRegisterphyList.get(i).getUploud(), request, toName);
				}
		
		for (ClinicRegisterphy clinicRegisterphy : clinicRegister.getClinicRegisterphyList()){
			if (clinicRegisterphy.getId() == null){
				continue;
			}
			if (ClinicRegisterphy.DEL_FLAG_NORMAL.equals(clinicRegisterphy.getDelFlag())){
				if (StringUtils.isBlank(clinicRegisterphy.getId())){
					clinicRegisterphy.setRegister(clinicRegister);
					clinicRegisterphy.preInsert();
					clinicRegisterphyDao.insert(clinicRegisterphy);
				}else{
					clinicRegisterphy.preUpdate();
					clinicRegisterphyDao.update(clinicRegisterphy);
				}
			}else{
				clinicRegisterphyDao.delete(clinicRegisterphy);
			}
		}
		
		
		
		if(isNew){
			actTaskService.startProcess(ActUtils.PD_CLINIC[0], ActUtils.PD_CLINIC[1], clinicRegister.getId(), clinicRegister.getCode());
		}
		try{
			exportWord(clinicRegister);
		}catch (IOException e){
			return;
		}
	}
	

	@Transactional(readOnly = false)
	public void delete(ClinicRegister clinicRegister) {
		super.delete(clinicRegister);
	}

	public void saveMaterial(ClinicRegister clinicRegister) {
		// 完成流程任务
		Map<String, Object> vars = Maps.newHashMap();
		actTaskService.complete(clinicRegister.getAct().getTaskId(), clinicRegister.getAct().getProcInsId(), clinicRegister.getAct().getComment(), clinicRegister.getCode(), vars);
	
	}

	public void export(HttpServletResponse response, List<ClinicRegister> clinicRegisters,
			ClinicRegister clinicRegister) {
		// TODO Auto-generated method stub
		WritableWorkbook  book;
		
		
		
        try {
        	OutputStream os = response.getOutputStream();// 取得输出流     
        	response.reset();// 清空输出流
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String("查询exel".getBytes("GB2312"),
					"iso8859_1") + ".xls");// 设定输出文件头
			response.setContentType("application/msexcel");// 定义输出类型
			book=Workbook.createWorkbook(os);
			WritableSheet sheet = book.createSheet("受理", 0);
		List<ClinicRegister> clinicRegister3=new ArrayList<ClinicRegister>();
			for (ClinicRegister clinicRegister1 : clinicRegisters) {
				List<String> bn = new ArrayList<String>();
				ClinicRegister clinicRegister2=new ClinicRegister();
				if(!StringUtils.isEmpty(clinicRegister.getCode())){
					bn.add("编码");
					clinicRegister2.setCode(clinicRegister1.getCode());
				}
				if(!StringUtils.isEmpty(clinicRegister.getCaseCode())){
					bn.add("案件编码");
					clinicRegister2.setCaseCode(clinicRegister1.getCaseCode());
				}
				if(!StringUtils.isEmpty(clinicRegister.getClientName())){
					bn.add("委托人");
					clinicRegister2.setClientName(clinicRegister1.getClientName());
				}
				if(!StringUtils.isEmpty(clinicRegister.getClientTel())){
					bn.add("委托人电话");
					clinicRegister2.setClientTel(clinicRegister1.getClientTel());
				}
				if(!StringUtils.isEmpty(clinicRegister.getClientReceiver())){
					bn.add("委托收件人");
					clinicRegister2.setClientReceiver(clinicRegister1.getClientReceiver());
				}
				if(!StringUtils.isEmpty(clinicRegister.getAgentName())){
					bn.add("送检人");
					clinicRegister2.setAgentName(clinicRegister1.getAgentName());
				}
				
				if(!StringUtils.isEmpty(clinicRegister.getAgentTel())){
					bn.add("送检人电话");
					clinicRegister2.setAgentTel(clinicRegister1.getAgentTel());
				}
				if(!StringUtils.isEmpty(clinicRegister.getServerName())){
					bn.add("受理人");
					clinicRegister2.setServerName(clinicRegister1.getServerName());
				}	
				if(!StringUtils.isEmpty(clinicRegister.getType())){
					bn.add("专业");
					clinicRegister2.setType(clinicRegister1.getType());
				}
				if(clinicRegister.getTotalFee()!=null){
					bn.add("合计费用");
					clinicRegister2.setTotalFee(clinicRegister1.getTotalFee());
				}
				if(!StringUtils.isEmpty(clinicRegister.getSurveyorName())){
					bn.add("送检人姓名");
					clinicRegister2.setSurveyorName(clinicRegister1.getSurveyorName());
				}
				if(!StringUtils.isEmpty(clinicRegister.getSurveyorSex())){
					bn.add("被检人性别");
					clinicRegister2.setSurveyorSex(clinicRegister1.getSurveyorSex());
				}
				clinicRegister3.add(clinicRegister2);
				//标题
				String[] columns =  bn.toArray(new String[bn.size()]);
				for (int i = 0; i < columns.length; i++) {
					sheet.addCell(new Label(i, 0, columns[i]));
				}
			}	
			
			
  			
            for (int i = 0; i < clinicRegister3.size(); i++) {
            	int j=0;
            	if(!StringUtils.isEmpty(clinicRegister3.get(i).getCode())){
            		sheet.addCell(new Label(j++,i+1,clinicRegister3.get(i).getCode()));
				}
            	if(!StringUtils.isEmpty(clinicRegister3.get(i).getCaseCode())){
            		sheet.addCell(new Label(j++,i+1,clinicRegister3.get(i).getCaseCode()));
				}
            	if(!StringUtils.isEmpty(clinicRegister3.get(i).getClientName())){
            		sheet.addCell(new Label(j++,i+1,clinicRegister3.get(i).getClientName()));
				}
            	if(!StringUtils.isEmpty(clinicRegister3.get(i).getClientTel())){
            		sheet.addCell(new Label(j++,i+1,clinicRegister3.get(i).getClientTel()));
				}
            	if(!StringUtils.isEmpty(clinicRegister3.get(i).getClientReceiver())){
            		sheet.addCell(new Label(j++,i+1,clinicRegister3.get(i).getClientReceiver()));
				}
            	if(!StringUtils.isEmpty(clinicRegister3.get(i).getAgentName())){
            		sheet.addCell(new Label(j++,i+1,clinicRegister3.get(i).getAgentName()));
				}
            	if(!StringUtils.isEmpty(clinicRegister3.get(i).getAgentTel())){
            		sheet.addCell(new Label(j++,i+1,clinicRegister3.get(i).getAgentTel()));
				}
            	if(!StringUtils.isEmpty(clinicRegister3.get(i).getServerName())){
            		sheet.addCell(new Label(j++,i+1,clinicRegister3.get(i).getServerName()));
				}
            	if(!StringUtils.isEmpty(clinicRegister3.get(i).getType())){
            		String type= DictUtils.getDictLabels(clinicRegister3.get(i).getType(), "fayitypeCode" ,"未知");
            		sheet.addCell(new Label(j++,i+1,type));
				}
            	if(clinicRegister3.get(i).getTotalFee()!=null){
            		sheet.addCell(new Number(j++,i+1,clinicRegister3.get(i).getTotalFee()));
				}
            	if(!StringUtils.isEmpty(clinicRegister3.get(i).getSurveyorName())){
            		sheet.addCell(new Label(j++,i+1,clinicRegister3.get(i).getSurveyorName()));
				}
            	if(!StringUtils.isEmpty(clinicRegister3.get(i).getSurveyorSex())){
            		String sex= DictUtils.getDictLabels(clinicRegister3.get(i).getSurveyorSex(), "sex" ,"未知");
            		sheet.addCell(new Label(j++,i+1,sex));
				}
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
}