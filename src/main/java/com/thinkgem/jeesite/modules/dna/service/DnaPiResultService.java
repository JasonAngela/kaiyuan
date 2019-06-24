/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dna.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.dna.entity.DnaMutation;
import com.thinkgem.jeesite.modules.dna.entity.DnaPiResult;
import com.thinkgem.jeesite.modules.dna.dao.DnaPiResultDao;
import com.thinkgem.jeesite.modules.dna.entity.DnaPiResultItem;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustRegister;
import com.thinkgem.jeesite.modules.dna.dao.DnaPiResultItemDao;
import org.springframework.util.CollectionUtils;

/**
 * 亲权指数Service
 *
 * @author zhuguli
 * @version 2017-07-04
 */
@Service
@Transactional(readOnly = true)
public class DnaPiResultService extends CrudService<DnaPiResultDao, DnaPiResult> {

    @Autowired
    private DnaPiResultItemDao dnaPiResultItemDao;
    @Autowired
    private DnaPiResultDao dnaPiResultdao;

    public DnaPiResult get(String id) {
        DnaPiResult dnaPiResult = super.get(id);
        dnaPiResult.setDnaPiResultItemList(dnaPiResultItemDao.findList(new DnaPiResultItem(dnaPiResult)));
        return dnaPiResult;
    }


    public List<DnaPiResult>  getRgister(String rgisterId) {
        List<DnaPiResult> results  = dnaPiResultdao.getRegister(rgisterId);
        if(!CollectionUtils.isEmpty(results)){
            for(DnaPiResult result:results){
                result.setDnaPiResultItemList(dnaPiResultItemDao.findList(new DnaPiResultItem(result)));
            }
            return results;
        }
        return new ArrayList<DnaPiResult>();
    }
    
    
    public List<DnaPiResult> findList(DnaPiResult dnaPiResult) {
        return super.findList(dnaPiResult);
    }

    public Page<DnaPiResult> findPage(Page<DnaPiResult> page, DnaPiResult dnaPiResult) {
        return super.findPage(page, dnaPiResult);
    }

    @Transactional(readOnly = false)
    public void save(DnaPiResult dnaPiResult) {
        super.save(dnaPiResult);
        for (DnaPiResultItem dnaPiResultItem : dnaPiResult.getDnaPiResultItemList()) {

            if (StringUtils.isBlank(dnaPiResultItem.getId())) {
                dnaPiResultItem.setResult(dnaPiResult);
                dnaPiResultItem.preInsert();
                dnaPiResultItemDao.insert(dnaPiResultItem);
            } else {
                dnaPiResultItem.preUpdate();
                dnaPiResultItemDao.update(dnaPiResultItem);
            }

        }
    }

    @Transactional(readOnly = false)
    public void save3(DnaPiResult dnaPiResult) {
        super.save(dnaPiResult);
    }

    @Transactional(readOnly = false)
    public void save2(DnaPiResultItem dnaPiResultItem) {
        dnaPiResultItem.preInsert();
        dnaPiResultItemDao.insert(dnaPiResultItem);

    }
    //获取当前基因突变
    @Transactional(readOnly = false)
    public DnaMutation getLoci(String chirdCode,String parentCode){
    	List<DnaPiResultItem>dnaPiResultItemList=new ArrayList<DnaPiResultItem>();
		String loci=null;
    	DnaPiResult result=	dnaPiResultdao.getParentCodeAndChirdCode(chirdCode, parentCode);
		dnaPiResultItemList.addAll(dnaPiResultItemDao.findResult(result.getId()));
	String locis2="";
	List<String>locis=new ArrayList<String>();
	DnaPiResult dnaPiResult=new DnaPiResult();
	for (DnaPiResultItem dnaPiResultItem : dnaPiResultItemList) {
		if(!StringUtils.isEmpty(dnaPiResultItem.getLoci())){
			loci=dnaPiResultItem.getLoci();
			locis2+=loci;
			locis.add(loci);
		}
		 dnaPiResult= dnaPiResultdao.get(dnaPiResultItem.getResult().getId());
	}
	DnaMutation dnaMutation=new DnaMutation();
	dnaMutation.setCcode(dnaPiResult.getChildCode());
	dnaMutation.setPcode(dnaPiResult.getParentCode());
	dnaMutation.setLocis(locis2);
	dnaMutation.setTotle(locis.size());
    return dnaMutation;
    }
    
    @Transactional(readOnly = false)
    public void delete(DnaPiResult dnaPiResult) {
        super.delete(dnaPiResult);
        dnaPiResultItemDao.delete(new DnaPiResultItem(dnaPiResult));
    }


    public List<DnaPiResult> selectByRegisterId(String registerId) {
        DnaPiResult entity = new DnaPiResult();
        entity.setRegister(new EntrustRegister(registerId));
        List<DnaPiResult> list = findList(entity);
        for (DnaPiResult dnaPiResult : list) {
            dnaPiResult.setDnaPiResultItemList(dnaPiResultItemDao.findList(new DnaPiResultItem(dnaPiResult)));
        }
        return list;
    }

}