package com.fh.service.lessonmanage;


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
import com.fh.util.StringHelper;
import com.fh.util.excel.FileHelper;
import com.fh.util.excel.HssfHelper;



/**
 * 描述：课程管理
 * @author zhangcc
 * @Date : 2019-05-21
 */
@Service
public class LessonManageService extends BaseService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 描述：获取课程信息列表
	 * @param pd
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-21
	 */
	public List<PageData> getInformationManageList(PageData pd) throws Exception{
		PHelper.startPage(pd);
		return (List<PageData>)dao.findForList("LessonManageMapper.getInformationManageList", pd);
	}
	
	/**
	 * 描述：获取企业列表
	 * @param pd
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-21
	 */
	public List<PageData> getOrgList(PageData pd) throws Exception{
		PHelper.startPage(pd);
		return (List<PageData>)dao.findForList("LessonManageMapper.getOrgList", pd);
	}
	
	/**
	 * 描述：获取课程类型列表
	 * @param pd
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-21
	 */
	public List<PageData> getLessonTypeList(PageData pd) throws Exception{
		return (List<PageData>)dao.findForList("LessonManageMapper.getLessonTypeList", pd);
	}
	
	/**
	 * 描述：获取园区列表
	 * @param pd
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-21
	 */
	public List<PageData> getAreaList(PageData pd) throws Exception{
		return (List<PageData>)dao.findForList("LessonManageMapper.getLAreaList", pd);
	}

	/**
	 * 描述：保存事故案列信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-07
	 */
	public Map saveInformation(PageData pd) {
		try {
			pd.put("user", this.getUser());
			dao.save("LessonManageMapper.saveInformation", pd);
			return ResultUtils.returnWebOk();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnWebError();
		}
	}
	
	/**
	 * 描述：推送信息保存
	 * @param pd
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-27
	 */
	public Map savePushLog(PageData pd) {
		try {
			pd.put("user", this.getUser());
			dao.save("PushInfoMapper.saveLog", pd);
			return ResultUtils.returnWebOk();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnWebError();
		}
	}
	
	/**
	 * 描述：获取推送用户的设备ID
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-24
	 */
	public List getJpushPids(PageData pd){
		try {
			return (List) dao.findForList("LessonManageMapper.getJpushPids", pd);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 描述：获取课程信息详情
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-21
	 */
	public PageData getInformationMap() throws Exception{
		PageData pd = this.getPageData();
		return (PageData)dao.findForObject("LessonManageMapper.getInformationMap", pd);
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
			dao.update("LessonManageMapper.updateInformation", pd);
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
	 * @Date : 2019-05-21
	 */
	public Map delInformatione() {
		PageData pd = this.getPageData();
		try {
			pd.put("user", this.getUser());
			dao.save("LessonManageMapper.saveInformationHis", pd);
			return ResultUtils.returnWebOk();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultUtils.returnWebError();
		}
	}
	
	/**
	 * 描述：获取类型所属行业
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-22
	 */
	public String getUpHy() {
		PageData pd = this.getPageData();
		try {
			Map info = (Map) dao.findForObject("LessonManageMapper.getUpHy", pd);
			String lesson_type = StringHelper.get(info, "lesson_type");
			String param_name = StringHelper.get(info, "param_name");
			System.out.println("======================================================" + lesson_type + "&" + param_name);
			return lesson_type + "&" + param_name;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	
}
