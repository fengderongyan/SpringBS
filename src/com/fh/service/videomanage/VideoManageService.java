package com.fh.service.videomanage;


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
 * 描述：视频管理
 * @author zhangcc
 * @Date : 2019-05-20
 */
@Service
public class VideoManageService extends BaseService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 描述：获取视频信息列表
	 * @param pd
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-20
	 */
	public List<PageData> getInformationManageList(PageData pd) throws Exception{
		PHelper.startPage(pd);
		return (List<PageData>)dao.findForList("VideoManageMapper.getInformationManageList", pd);
	}
	
	/**
	 * 描述：获取视频类型列表
	 * @param pd
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-20
	 */
	public List<PageData> getVideoTypeList(PageData pd) throws Exception{
		return (List<PageData>)dao.findForList("VideoManageMapper.getVideoTypeList", pd);
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
			pd.put("user", this.getUser());
			dao.save("VideoManageMapper.saveInformation", pd);
			return ResultUtils.returnWebOk();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnWebError();
		}
		
	}
	
	/**
	 * 描述：获取视频信息详情
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-20
	 */
	public PageData getInformationMap() throws Exception{
		PageData pd = this.getPageData();
		return (PageData)dao.findForObject("VideoManageMapper.getInformationMap", pd);
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
			dao.update("VideoManageMapper.updateInformation", pd);
			return ResultUtils.returnWebOk();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnWebError();
		}
	}
	
	
	/**
	 * 描述：删除信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-20
	 */
	public Map delInformatione() {
		PageData pd = this.getPageData();
		try {
			pd.put("user", this.getUser());
			dao.save("VideoManageMapper.saveInformationHis", pd);
			return ResultUtils.returnWebOk();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultUtils.returnWebError();
		}
	}
	
	
}
