/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.material.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.filter.PhotoUploud;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.entrust.dao.EntrustRegisterDao;
import com.thinkgem.jeesite.modules.entrust.entity.EntrustRegister;
import com.thinkgem.jeesite.modules.entrust.service.EntrustRegisterService;
import com.thinkgem.jeesite.modules.material.dao.SpecimenMaterialRegisterDao;
import com.thinkgem.jeesite.modules.material.dao.SpecimenMaterialRegisterItemDao;
import com.thinkgem.jeesite.modules.material.entity.SpecimenMaterialRegister;
import com.thinkgem.jeesite.modules.material.entity.SpecimenMaterialRegisterItem;
import com.thinkgem.jeesite.modules.sys.service.SysCodeRuleService;
import com.thinkgem.jeesite.modules.xueka.dao.SpecimenXuekaDao;
import com.thinkgem.jeesite.modules.xueka.entity.SpecimenXueka;
import com.thinkgem.jeesite.modules.xueka.entity.SpecimenXuekaLsit;

/**
 * 物证登记Service
 *
 * @author zhuguli
 * @version 2017-05-10
 */
@Service
@Transactional(readOnly = true)
public class SpecimenMaterialRegisterService extends CrudService<SpecimenMaterialRegisterDao, SpecimenMaterialRegister> {

    @Autowired
    private SpecimenMaterialRegisterDao specimenMaterialRegisterDao;
    @Autowired
    private SpecimenMaterialRegisterItemDao specimenMaterialRegisterItemDao;
    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private SysCodeRuleService sysCodeRuleService;
    @Autowired
    private EntrustRegisterDao entrustRegisterDao;
    @Autowired
    private SpecimenXuekaDao specimenXuekaDao;
    /*@Autowired
    private EntrustRegisterService entrustRegisterService;*/

    public SpecimenMaterialRegister get(String id) {
        SpecimenMaterialRegister specimenMaterialRegister = super.get(id);
        specimenMaterialRegister.setSpecimenMaterialRegisterItemList(specimenMaterialRegisterItemDao.findList(new SpecimenMaterialRegisterItem(specimenMaterialRegister)));
        return specimenMaterialRegister;
    }

    public List<SpecimenMaterialRegister> findList(SpecimenMaterialRegister specimenMaterialRegister) {
        return super.findList(specimenMaterialRegister);
    }

    public Page<SpecimenMaterialRegister> findPage(Page<SpecimenMaterialRegister> page, SpecimenMaterialRegister specimenMaterialRegister) {
        return super.findPage(page, specimenMaterialRegister);
    }

    @Transactional(readOnly = false)
    public void save(SpecimenMaterialRegister specimenMaterialRegister, SpecimenXuekaLsit specimenXuekaLsit, HttpServletRequest request) throws Exception {
        //上传到D盘的文件
    	EntrustRegister entrustRegister1 = entrustRegisterDao.get(specimenMaterialRegister.getEntrustRegister().getId());
    	//entrustRegisterDao
        //entrustRegister1 = entrustRegisterService.get(specimenMaterialRegister.getEntrustRegister().getId());
        String toName = "D:" + File.separator + "information" + File.separator + "DNA" + File.separator + entrustRegister1.getCode() + File.separator + "图片" + File.separator;
        List<SpecimenMaterialRegisterItem> specimenMaterialRegisterItemList = specimenMaterialRegister.getSpecimenMaterialRegisterItemList();
        PhotoUploud photoUploud = new PhotoUploud();
        for (int i = 0; i < specimenMaterialRegisterItemList.size(); i++) {
            specimenMaterialRegisterItemList.get(i).getPic();
            photoUploud.UploudPhoto(specimenMaterialRegisterItemList.get(i).getPic(), request, toName);
        }

        if (specimenMaterialRegister.getIsNewRecord()) {
            String code = sysCodeRuleService.generateCode("material_register_code");
            specimenMaterialRegister.setCode(code);
        }

        super.save(specimenMaterialRegister);
        for (SpecimenMaterialRegisterItem specimenMaterialRegisterItem : specimenMaterialRegister.getSpecimenMaterialRegisterItemList()) {

            if (specimenMaterialRegisterItem.getId() == null) {
                continue;
            }
            if (SpecimenMaterialRegisterItem.DEL_FLAG_NORMAL.equals(specimenMaterialRegisterItem.getDelFlag())) {
                if (StringUtils.isBlank(specimenMaterialRegisterItem.getId())) {
                    specimenMaterialRegisterItem.setMaterialRegister(specimenMaterialRegister);
                    specimenMaterialRegisterItem.preInsert();
                    specimenMaterialRegisterItemDao.insert(specimenMaterialRegisterItem);
                } else {
                    specimenMaterialRegisterItem.preUpdate();
                    specimenMaterialRegisterItemDao.update(specimenMaterialRegisterItem);
                }
            } else {
                specimenMaterialRegisterItemDao.delete(specimenMaterialRegisterItem);
            }
        }


        if (specimenXuekaLsit.getSpecimenXuekas().size() > 0) {
            for (SpecimenXueka specimenXueka : specimenXuekaLsit.getSpecimenXuekas()) {
                specimenXueka.setMaterialRegisterItemId(specimenMaterialRegister.getEntrustRegister().getCode());
                specimenXuekaDao.insert(specimenXueka);
            }
        }
        //修改状态
        EntrustRegister entrustRegister = entrustRegisterDao.get(specimenMaterialRegister.getEntrustRegister().getId());
        entrustRegister.setStatus("3");
        entrustRegisterDao.update(entrustRegister);

        // 完成流程任务
        Map<String, Object> vars = Maps.newHashMap();
        actTaskService.complete(specimenMaterialRegister.getAct().getTaskId(), specimenMaterialRegister.getAct().getProcInsId(), specimenMaterialRegister.getAct().getComment(), specimenMaterialRegister.getCode(), vars);
    }

    @Transactional(readOnly = false)
    public void delete(SpecimenMaterialRegister specimenMaterialRegister) {
        super.delete(specimenMaterialRegister);
        specimenMaterialRegisterItemDao.delete(new SpecimenMaterialRegisterItem(specimenMaterialRegister));
    }

    @Transactional(readOnly = false)
    public List<SpecimenMaterialRegisterItem> findmaterial(String id) {
        SpecimenMaterialRegister specimenMaterialRegister = specimenMaterialRegisterDao.findid(id);
        if (specimenMaterialRegister != null) {
            return specimenMaterialRegisterItemDao.findall(specimenMaterialRegister.getId());
        }
        return new ArrayList<SpecimenMaterialRegisterItem>();

    }

    public static void copy(String file1, String file2) {
        File src = new File(file1);
        File dst = new File(file2);
        if (!dst.exists()) {
            dst.mkdirs();
        }
        InputStream in = null;
        OutputStream out = null;
        //System.out.println(file1.substring(file1.lastIndexOf("/"),file1.length()));//获取单个文件的源文件的名称
        try {
            in = new BufferedInputStream(new FileInputStream(src), 16 * 1024);
            FileOutputStream f = new FileOutputStream(dst + file1.substring(file1.lastIndexOf("/"), file1.length()));//一定要加上文件名称
            out = new BufferedOutputStream(f, 16 * 1024);
            byte[] buffer = new byte[16 * 1024];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}