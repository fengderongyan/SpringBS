package com.fh.service.mobile;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fh.dao.DaoSupport;
import com.fh.dao.redis.impl.RedisDaoImpl;
import com.fh.entity.system.User;
import com.fh.service.base.BaseService;
import com.fh.service.chartlog.ChartLogService;
import com.fh.util.BatchSql;
import com.fh.util.Const;
import com.fh.util.DateHelper;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.JwtUtil;
import com.fh.util.PHelper;
import com.fh.util.PageData;
import com.fh.util.ResultUtils;
import com.fh.util.StringHelper;
import com.fh.util.excel.FileHelper;
import com.fh.util.excel.HssfHelper;

/**
 * 描述：APP端信息接口
 * @author zhangcc
 * @Date : 2019-05-16
 */
@Service
public class MobileVideoService extends BaseService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 描述：视频类型列表
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-22
	 */
	public Map getVideoTypeList() {
		try {
			Map map = new HashMap();
			List infoList = new ArrayList();
			PageData pd = this.getPageData();
			pd.put("user", this.getUser());
			infoList = (List<PageData>)dao.findForList("MobileVideoMapper.getVideoTypeList", pd);
			return ResultUtils.returnOk(infoList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}
	
	
	/**
	 * 描述：获取视频列表信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-22
	 */
	public Map getVideoList() {
		try {
			Map map = new HashMap();
			List infoList = new ArrayList();
			PageData pd = this.getPageData();
			pd.put("user", this.getUser());
			String lessonTypeId = pd.getString("lesson_type_id");
			if(lessonTypeId.equals(""))
				return ResultUtils.returnError(12001, "无法获取lesson_type_id！");
			String page = pd.getString("page");
			String limit = pd.getString("limit");
			if(page.equals(""))
				return ResultUtils.returnError(12001, "无法获取page！");
			limit = limit.equals("") ? "15" : limit;
			pd.put("limit", limit);
			PHelper.startPage(pd);
			infoList = (List<PageData>)dao.findForList("MobileVideoMapper.getVideoList", pd);
			return ResultUtils.returnOk(infoList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}
	
	/**
	 * 描述：获取最新动态详情
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-16
	 */
	public Map getVideoDetail(){
		try {
			Map map = new HashMap();
			Map infoMap = new HashMap();
			PageData pd = this.getPageData();
			String lesson_id = pd.getString("lesson_id");
			if(lesson_id.equals(""))
				return ResultUtils.returnError(12001, "无法获取lesson_id！");
			PageData resultPd = (PageData)dao.findForObject("MobileVideoMapper.getVideoDetail", pd);
			return ResultUtils.returnOk(resultPd);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}
}
