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
import org.springframework.beans.factory.annotation.Value;
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
 * 描述：移动端登陆校验
 * @author zhangcc
 * @Date : 2019-05-16
 */
@Service
public class MobileInformationService extends BaseService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Autowired
	private RedisDaoImpl redisDaoImpl;
	
	/**
	 * 描述：获取首页资讯列表
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-16
	 */
	public Map getBannerList() {
		try {
			Map map = new HashMap();
			List infoList = new ArrayList();
			PageData pd = this.getPageData();
			String page = pd.getString("page");
			String limit = pd.getString("limit");
			if(page.equals(""))
				return ResultUtils.returnError(12001, "无法获取page！");
			limit = limit.equals("") ? "15" : limit;
			pd.put("limit", limit);
			
			PHelper.startPage(pd);
			infoList = (List<PageData>)dao.findForList("MobileInformationController.getBannerList", pd);
			return ResultUtils.returnOk(infoList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}
	
	/**
	 * 描述：更新最新动态阅读量
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-17
	 */
	public int updateBannerReadNum(){
		try {
			Map map = new HashMap();
			Map infoMap = new HashMap();
			PageData pd = this.getPageData();
			String bannerId = pd.getString("banner_id");
			if(bannerId.equals(""))
				return 0;
			dao.findForObject("MobileInformationController.updateBannerReadNum", pd);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 描述：获取最新动态详情
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-16
	 */
	public Map getBannerInfo(){
		try {
			Map map = new HashMap();
			Map infoMap = new HashMap();
			PageData pd = this.getPageData();
			String bannerId = pd.getString("banner_id");
			if(bannerId.equals(""))
				return ResultUtils.returnError(12001, "无法获取bannerId！");
			PageData resultPd = (PageData)dao.findForObject("MobileInformationController.getBannerInfo", pd);
			return ResultUtils.returnOk(resultPd);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}
	
	
	/**
	 * 描述：获取首页法律法规
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-16
	 */
	public Map getLawList(){
		try {
			Map map = new HashMap();
			List infoList = new ArrayList();
			PageData pd = this.getPageData();
			String page = pd.getString("page");
			String limit = pd.getString("limit");
			if(page.equals(""))
				return ResultUtils.returnError(12001, "无法获取page！");
			limit = limit.equals("") ? "15" : limit;
			pd.put("limit", limit);
			PHelper.startPage(pd);
			infoList = (List<PageData>)dao.findForList("MobileInformationController.getLawList", pd);
			return ResultUtils.returnOk(infoList);
		}catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}
	
	/**
	 * 描述：法律法规阅读量更新
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-17
	 */
	public int updateLawReadNum(){
		try {
			Map map = new HashMap();
			Map infoMap = new HashMap();
			PageData pd = this.getPageData();
			String lawId = pd.getString("law_id");
			if(lawId.equals(""))
				return 0;
			dao.findForObject("MobileInformationController.updateBannerReadNum", pd);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 描述：获取法律法规详情
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-17
	 */
	public Map getLawInfo(){
		try {
			Map map = new HashMap();
			Map infoMap = new HashMap();
			PageData pd = this.getPageData();
			String law_id = pd.getString("law_id");
			if(law_id.equals(""))
				return ResultUtils.returnError(12001, "无法获取law_id！");
			PageData resultPd = (PageData)dao.findForObject("MobileInformationController.getLawInfo", pd);
			return ResultUtils.returnOk(resultPd);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}
	
	
	/**
	 * 描述：获取首页事故案例
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-16
	 */
	public Map getAccidentList(){
		try {
			Map map = new HashMap();
			List infoList = new ArrayList();
			PageData pd = this.getPageData();
			String page = pd.getString("page");
			String limit = pd.getString("limit");
			if(page.equals(""))
				return ResultUtils.returnError(12001, "无法获取page！");
			limit = limit.equals("") ? "15" : limit;
			pd.put("limit", limit);
			PHelper.startPage(pd);
			infoList = (List<PageData>)dao.findForList("MobileInformationController.getAccidentList", pd);
			return ResultUtils.returnOk(infoList);
		}catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}
	
	/**
	 * 描述：事故案例阅读量更新
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-17
	 */
	public int updateAccidentReadNum(){
		try {
			Map map = new HashMap();
			Map infoMap = new HashMap();
			PageData pd = this.getPageData();
			String accident_id = pd.getString("accident_id");
			if(accident_id.equals(""))
				return 0;
			dao.findForObject("MobileInformationController.updateAccidentReadNum", pd);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 描述：获取事故案例详情
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-17
	 */
	public Map getAccidentInfo(){
		try {
			Map map = new HashMap();
			Map infoMap = new HashMap();
			PageData pd = this.getPageData();
			String accident_id = pd.getString("accident_id");
			if(accident_id.equals(""))
				return ResultUtils.returnError(12001, "无法获取accident_id！");
			PageData resultPd = (PageData)dao.findForObject("MobileInformationController.getAccidentInfo", pd);
			return ResultUtils.returnOk(resultPd);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}
	
	
	/**
	 * 描述：获取安全常识列表
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-21
	 */
	public Map getSafeList(){
		try {
			Map map = new HashMap();
			List infoList = new ArrayList();
			PageData pd = this.getPageData();
			String page = pd.getString("page");
			String limit = pd.getString("limit");
			if(page.equals(""))
				return ResultUtils.returnError(12001, "无法获取page！");
			limit = limit.equals("") ? "15" : limit;
			pd.put("limit", limit);
			PHelper.startPage(pd);
			infoList = (List<PageData>)dao.findForList("MobileInformationController.getSafeList", pd);
			return ResultUtils.returnOk(infoList);
		}catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}
	
	
	/**
	 * 描述：安全常识阅读量更新
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-21
	 */
	public int updateSafeReadNum(){
		try {
			Map map = new HashMap();
			Map infoMap = new HashMap();
			PageData pd = this.getPageData();
			String safe_id = pd.getString("safe_id");
			if(safe_id.equals(""))
				return 0;
			dao.findForObject("MobileInformationController.updateSafeReadNum", pd);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 描述：获取安全常识详情
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-21
	 */
	public Map getSafeInfo(){
		try {
			Map map = new HashMap();
			Map infoMap = new HashMap();
			PageData pd = this.getPageData();
			String safe_id = pd.getString("safe_id");
			if(safe_id.equals(""))
				return ResultUtils.returnError(12001, "无法获取safe_id！");
			PageData resultPd = (PageData)dao.findForObject("MobileInformationController.getSafeInfo", pd);
			return ResultUtils.returnOk(resultPd);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}
	
	/**
	 * 描述：获取搜索资讯信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-22
	 */
	public Map getSearchInfoList(){
		try {
			Map map = new HashMap();
			List infoList = new ArrayList();
			PageData pd = this.getPageData();
			String page = pd.getString("page");
			String limit = pd.getString("limit");
			String title = pd.getString("title");
			if(page.equals(""))
				return ResultUtils.returnError(12001, "无法获取page！");
			if(title.equals(""))
				return ResultUtils.returnError(12001, "无法获取title！");
			limit = limit.equals("") ? "15" : limit;
			pd.put("limit", limit);
			PHelper.startPage(pd);
			infoList = (List<PageData>)dao.findForList("MobileInformationController.getSearchInfoList", pd);
			return ResultUtils.returnOk(infoList);
		}catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}
	
	
	/**
	 * 描述：更新资讯信息阅读量
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-22
	 */
	public int updateInfoReadNum(){
		try {
			Map map = new HashMap();
			Map infoMap = new HashMap();
			PageData pd = this.getPageData();
			String info_id = pd.getString("info_id");
			String type = pd.getString("type");
			if(info_id.equals(""))
				return 0;
			if(type.equals(""))
				return 0;
			dao.findForObject("MobileInformationController.updateInfoReadNum", pd);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
	/**
	 * 描述：获取资讯详情
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-22
	 */
	public Map getInfo(){
		try {
			Map map = new HashMap();
			Map infoMap = new HashMap();
			PageData pd = this.getPageData();
			String type = pd.getString("type");
			String info_id = pd.getString("info_id");
			if(info_id.equals(""))
				return ResultUtils.returnError(12001, "无法获取info_id！");
			if(type.equals(""))
				return ResultUtils.returnError(12001, "无法获取type！");
			PageData resultPd = (PageData)dao.findForObject("MobileInformationController.getInfo", pd);
			return ResultUtils.returnOk(resultPd);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}
	
	/**
	 * 描述：积分结果查询
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-21
	 */
	public Map getPointsResult(){
		try {
			Map map = new HashMap();
			Map infoMap = new HashMap();
			PageData pd = this.getPageData();
			pd.put("user", this.getUser());
			String type = pd.getString("type");
			String infoId = pd.getString("info_id");
			if(type.equals(""))
				type = "1";
			if(type.equals("1"))
				infoId = "login";
			if(infoId.equals(""))
				return ResultUtils.returnError(12001, "无法获取info_id！");
			pd.put("type", type);
			pd.put("info_id", infoId);
			PageData resultPd = (PageData)dao.findForObject("MobileInformationController.getMyPonitInfo", pd);
			return resultPd;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}
	
	/**
	 * 描述：积分保持
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-21
	 */
	public Map saveMypoints(){
		try {
			Map map = new HashMap();
			Map infoMap = new HashMap();
			PageData pd = this.getPageData();
			pd.put("user", this.getUser());
			String type = pd.getString("type");
			String infoId = pd.getString("info_id");
			String point = "";
			if(type.equals("")){//登陆
				type = "1";
				point = "5";
				infoId = "login";
			}else if(type.equals("2")){//阅读资讯
				point = "1";
			}else if(type.equals("3")){//观看视频
				point = "3";
			}else if(type.equals("4")){//每日一答
				point = "1";
			}
			String id =  UUID.randomUUID().toString().trim().replaceAll("-", "");
			pd.put("point", point);
			pd.put("id", id);
			pd.put("type", type);
			pd.put("info_id", infoId);
			if(infoId.equals(""))
				return ResultUtils.returnError(12001, "无法获取info_id！");
			dao.findForObject("MobileInformationController.savePonit", pd);
			return ResultUtils.returnOk();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}
	
	public Map getPointsResult(PageData pd){
		try {
			Map map = new HashMap();
			Map infoMap = new HashMap();
			pd.put("user", this.getUser());
			String type = pd.getString("type");
			String infoId = pd.getString("info_id");
			if(type.equals(""))
				type = "1";
			if(type.equals("1"))
				infoId = "login";
			if(infoId.equals(""))
				return ResultUtils.returnError(12001, "无法获取info_id！");
			pd.put("type", type);
			pd.put("info_id", infoId);
			PageData resultPd = (PageData)dao.findForObject("MobileInformationController.getMyPonitInfo", pd);
			return resultPd;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}
	
	public Map saveMypoints(PageData pd){
		try {
			Map map = new HashMap();
			Map infoMap = new HashMap();
			pd.put("user", this.getUser());
			String type = pd.getString("type");
			String infoId = pd.getString("info_id");
			String point = "";
			if(type.equals("")){//登陆
				type = "1";
				point = "5";
				infoId = "login";
			}else if(type.equals("2")){//阅读资讯
				point = "1";
			}else if(type.equals("3")){//观看视频
				point = "3";
			}else if(type.equals("4")){//每日一答
				point = "1";
			}
			String id =  UUID.randomUUID().toString().trim().replaceAll("-", "");
			pd.put("point", point);
			pd.put("id", id);
			pd.put("type", type);
			pd.put("info_id", infoId);
			if(infoId.equals(""))
				return ResultUtils.returnError(12001, "无法获取info_id！");
			dao.save("MobileInformationController.savePonit", pd);
			return ResultUtils.returnOk();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}
	
	public Map  addPoint(PageData pd){
		int total_point = 0;
		int flag_exists = 0;
		int type = 0;
		Map pointResult = this.getPointsResult(pd);
		System.out.println("===========================pointResult :  " + pointResult);
		if(pointResult != null &&  pointResult.size() > 0){
			String tp = StringHelper.get(pointResult, "total_point");
			String fe = StringHelper.get(pointResult, "flag_exists");
			String ty = StringHelper.get(pointResult, "type");
			if(tp == null) tp = "0";
			if(fe == null) fe = "0"; 
			if(ty == null) ty = "0";
			total_point = Integer.valueOf(tp);
			flag_exists = Integer.valueOf(fe);
			type = Integer.valueOf(ty);
		}
		if(flag_exists > 0)
			return ResultUtils.returnOk();
		if(type == 2 && total_point < 5 ){
			this.saveMypoints(pd);
			return ResultUtils.returnOk();
		}
		if(type == 3 && total_point < 15){
			this.saveMypoints(pd);
			return ResultUtils.returnOk();
		}
		if(type == 4 && total_point < 5){
			this.saveMypoints(pd);
			return ResultUtils.returnOk();
		}
		return ResultUtils.returnOk();
	}
	
	
	/**
	 * 描述：获取我的信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-22
	 */
	public Map getMyInfo(){
		try {
			Map map = new HashMap();
			Map infoMap = new HashMap();
			PageData pd = this.getPageData();
			pd.put("user", this.getUser());
			PageData resultPd = (PageData)dao.findForObject("MobileInformationController.getMyInfo", pd);
			return ResultUtils.returnOk(resultPd);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}
	
	
	/**
	 * 描述：获取我的课程列表
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-22
	 */
	public Map getMyLessonsList(){
		try {
			Map map = new HashMap();
			List infoList = new ArrayList();
			PageData pd = this.getPageData();
			pd.put("user", this.getUser());
			String page = pd.getString("page");
			String limit = pd.getString("limit");
			if(page.equals(""))
				return ResultUtils.returnError(12001, "无法获取page！");
			limit = limit.equals("") ? "15" : limit;
			System.out.println("========================pd : " + pd);
			pd.put("limit", limit);
			PHelper.startPage(pd);
			infoList = (List<PageData>)dao.findForList("MobileInformationController.getMyLessonsList", pd);
			System.out.println(infoList);
			return ResultUtils.returnOk(infoList);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("==========" + e.getMessage());
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}
	
	
	/**
	 * 描述：获取视频
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-27
	 */
	public Map getDetail(){
		try {
			Map map = new HashMap();
			PageData pd = this.getPageData();
			pd.put("user", this.getUser());
			String lesson_id = pd.getString("lesson_id");
			if(lesson_id.equals(""))
				return ResultUtils.returnError(12001, "无法获取lesson_id！");
			return ResultUtils.returnOk(dao.findForList("MobileInformationController.getMyLessonsDetail", pd));
		}catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}
	
	
	
	/**
	 * 描述：意见反馈
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-22
	 */
	public Map subProposal() {
		try {
			PageData pd = this.getPageData();
			pd.put("user", this.getUser());
			String content = pd.getString("content");
			if(content.equals(""))
				return ResultUtils.returnError(12001, "无法获取content！");
			dao.save("MobileInformationController.subProposal", pd);
			return ResultUtils.returnOk();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}
	
	
	/**
	 * 描述：个人证书信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-22
	 */
	public Map getMyCertificateInfo(){
		try {
			Map map = new HashMap();
			Map infoMap = new HashMap();
			PageData pd = this.getPageData();
			pd.put("user", this.getUser());
			PageData resultPd = (PageData)dao.findForObject("MobileInformationController.getMyCertificateInfo", pd);
			return ResultUtils.returnOk(resultPd);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}
	
	/**
	 * 描述：获取我的推送信息列表
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-27
	 */
	public Map getMyMessageList(){
		try {
			Map map = new HashMap();
			List infoList = new ArrayList();
			PageData pd = this.getPageData();
			pd.put("user", this.getUser());
			String page = pd.getString("page");
			String limit = pd.getString("limit");
			if(page.equals(""))
				return ResultUtils.returnError(12001, "无法获取page！");
			limit = limit.equals("") ? "15" : limit;
			pd.put("limit", limit);
			PHelper.startPage(pd);
			infoList = (List<PageData>)dao.findForList("MobileInformationController.getMyMessageList", pd);
			return ResultUtils.returnOk(infoList);
		}catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}
	
	/**
	 * 描述：获取电话
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-30
	 */
	public Map getPhoneList(){
		try {
			Map map = new HashMap();
			List infoList = new ArrayList();
			PageData pd = this.getPageData();
			pd.put("user", this.getUser());
			String page = pd.getString("page");
			String limit = pd.getString("limit");
			if(page.equals(""))
				return ResultUtils.returnError(12001, "无法获取page！");
			limit = limit.equals("") ? "15" : limit;
			pd.put("limit", limit);
			PHelper.startPage(pd);
			infoList = (List<PageData>)dao.findForList("MobileInformationController.getPhoneList", pd);
			return ResultUtils.returnOk(infoList);
		}catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}
	

	/**
	 * 描述：修改密码
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-22
	 */
	public Map updatePwd() {
		try {
			PageData pd = this.getPageData();
			String oldPwd = pd.getString("oldPwd");
			String newPwd = pd.getString("newPwd");
			if("".equals(oldPwd)){
				return ResultUtils.returnError(12001, "无法获取oldPwd！");
			}
			if("".equals(newPwd)){
				return ResultUtils.returnError(12001, "无法获取newPwd！");
			}
			String oldPwd_SHA = new SimpleHash("SHA-1", this.getUser().getUSERNAME(), oldPwd).toString();
			System.out.println("oldPwd_SHA++++++++++++++++++++++" + oldPwd_SHA);
			System.out.println("getPASSWORD()++++++++++++++++++++++" + this.getUser().getPASSWORD());
			if(!oldPwd_SHA.equals(this.getUser().getPASSWORD())){
				return ResultUtils.returnError(12002, "原密码不正确！");
			}else{
				pd.put("user", this.getUser());
				newPwd = new SimpleHash("SHA-1", this.getUser().getUSERNAME(), newPwd).toString();
				pd.put("newPwd", newPwd);
				dao.update("MobileInformationController.updatePwd", pd);
				//redis key置空
				String userRedisKey = Const.TOKEN_KEY_FRIST + this.getUser().getUSERNAME() + Const.TOKEN_KEY_END;
				boolean hasKey = redisDaoImpl.hasKey(userRedisKey);
				if(hasKey)
					redisDaoImpl.delete(userRedisKey);
				
				
				return ResultUtils.returnOk();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}
	
}
