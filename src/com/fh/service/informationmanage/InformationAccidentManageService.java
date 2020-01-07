package com.fh.service.informationmanage;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fh.dao.DaoSupport;
import com.fh.service.base.BaseService;
import com.fh.util.BatchSql;
import com.fh.util.DateHelper;
import com.fh.util.PHelper;
import com.fh.util.PageData;
import com.fh.util.ResultUtils;
import com.fh.util.excel.FileHelper;
import com.fh.util.excel.HssfHelper;



/**
 * 描述：事故案列
 * @author zhangcc
 * @Date : 2019-05-09
 */
@Service
public class InformationAccidentManageService extends BaseService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public List<PageData> getInformationManageList(PageData pd) throws Exception{
		PHelper.startPage(pd);
		return (List<PageData>)dao.findForList("InformationAccidentManageMapper.getInformationManageList", pd);
	}

	/**
	 * 描述：保存事故案列信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-07
	 */
	public Map saveInformation() {
		try {
			PageData pd = this.getPageData();
			System.out.println("===============================" + pd);
			pd.put("user", this.getUser());
			dao.save("InformationAccidentManageMapper.saveInformation", pd);
			return ResultUtils.returnWebOk();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnWebError();
		}
		
	}
	
	/**
	 * 描述：获取事故案列详情
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-09
	 */
	public PageData getInformationMap() throws Exception{
		PageData pd = this.getPageData();
		return (PageData)dao.findForObject("InformationAccidentManageMapper.getInformationMap", pd);
	}

	/**
	 * 描述：修改事故案列信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-09
	 */
	public Map updateInformation() {
		PageData pd = this.getPageData();
		try {
			pd.put("user", this.getUser());
			dao.update("InformationAccidentManageMapper.updateInformation", pd);
			return ResultUtils.returnWebOk();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnWebError();
		}
	}
	
	/**
	 * 描述：删除信息
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-06
	 */
	public Map delInformatione() {
		PageData pd = this.getPageData();
		try {
			pd.put("user", this.getUser());
			dao.save("InformationAccidentManageMapper.saveInformationHis", pd);
			return ResultUtils.returnWebOk();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultUtils.returnWebError();
		}
	}
	
	
}
