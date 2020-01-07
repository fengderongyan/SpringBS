package com.fh.controller.lessonmanage;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.druid.support.json.JSONUtils;
import com.fh.controller.base.BaseController;
import com.fh.entity.system.TreeModel;
import com.fh.service.informationmanage.InformationManageService;
import com.fh.service.informationmanage.InformationSafeManageService;
import com.fh.service.lessonmanage.LessonManageService;
import com.fh.service.lessonmanage.LessonTypeService;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.ResultUtils;
import com.fh.util.StringHelper;
import com.fh.util.JPush.JPushUtil;


/**
 * 描述：课程管理
 * @author zhangcc
 * @Date : 2019-05-21
 */
@Controller
@RequestMapping(value="/lessonmanage/lesson")
public class LessonManageController extends BaseController {
	
	@Autowired
	private LessonManageService lessonManageService;
	
	@Autowired
	private LessonTypeService lessonTypeService;
	
	
	/**
	 * 描述：课程信息管理首页
	 * @param model
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-21
	 */
	@RequestMapping(value="/informationManageList")
	public String informationManageList(Model model) throws Exception{
		model.addAttribute("QX", Jurisdiction.getHC());
		PageData pd = this.getPageData();
		List<PageData> lessonTypeList = lessonManageService.getLessonTypeList(pd);
		model.addAttribute("lessonTypeList", lessonTypeList);
		return "lessonmanage/lesson/informationList";
	}
	
	
	/**
	 * 描述：获取课程列表
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-21
	 */
	@RequestMapping(value="/getInformaticaManageList")
	@ResponseBody
	public Map getInformaticaManageList() throws Exception{
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		List<PageData> informationList = lessonManageService.getInformationManageList(pd);
		return ResultUtils.returnWebPage(informationList);
	}
	
	
	/**
	 * 描述：新增课程信息
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-21
	 */
	@RequestMapping(value="/addInformation")
	public String addInformation(Model model) throws Exception{
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		List<PageData> hyList = lessonTypeService.getHyList(pd);
		model.addAttribute("hyList", hyList);
		//获取园区
		List<PageData> yqList = lessonManageService.getAreaList(pd);
		model.addAttribute("yqList", yqList);
		List<PageData> lessonList = lessonManageService.getLessonTypeList(pd);
		model.addAttribute("lessonList", lessonList);
		return "lessonmanage/lesson/addInformation";
	}
	
	/**
	 * 描述：课程类型首页
	 * @param model
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-21
	 */
	@RequestMapping(value="/lessonTypeList")
	public String lessonTypeList(Model model){
		model.addAttribute("QX", Jurisdiction.getHC());
		return "lessonmanage/lesson/expertList";
	}
	
	/**
	 * 描述：企业选择页面
	 * @param model
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-21
	 */
	@RequestMapping(value="/orgList")
	public String orgList(Model model) throws Exception{
		model.addAttribute("QX", Jurisdiction.getHC());
		//获取园区
		PageData pd = this.getPageData();
		List<PageData> yqList = lessonManageService.getAreaList(pd);
		model.addAttribute("yqList", yqList);
		return "lessonmanage/lesson/orgList";
	}
	
	/**
	 * 描述：获取企业列表
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-21
	 */
	@RequestMapping(value="/getOrgList")
	@ResponseBody
	public Map getOrgList() throws Exception{
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		List<PageData> informationList = lessonManageService.getOrgList(pd);
		return ResultUtils.returnWebPage(informationList);
	}
	
	
	/**
	 * 描述：保存课程信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-21
	 */
	@RequestMapping(value="/saveInformation")
	@ResponseBody
	public Map saveInformation(){
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		Map infoMap = lessonManageService.saveInformation(pd);
		String appcode = StringHelper.get(infoMap, "appcode");
		String pushTitle = "您有一条信息的信息";
		String title = pd.getString("title");
		String pushContent = "您有新的课程需要学习:"+ title;
		pd.put("title", title);
		pd.put("content", pushContent);
		String pId = "";
		String userId = "";
		if(appcode.equals("1")){//成功
			//极光推送信息
			List infoList = lessonManageService.getJpushPids(pd);
			for(int i = 0; i < infoList.size(); i ++){
				Map map = (Map) infoList.get(i);
				pId = StringHelper.get(map, "registration_id");
				userId = StringHelper.get(map, "user_id");
				if(!pId.equals("")){//非空
				   JPushUtil.pushMsg(pId, pushContent, "link", "", title);
				   pd.put("user_id", userId);
				   lessonManageService.savePushLog(pd);
			   }
			}
			
		}
		return infoMap;
	}
	
	/**
	 * 描述：获取课程信息详情
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-21
	 */
	@RequestMapping(value="/editInformationManager")
	public String editInformationManager(Model model) throws Exception{
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		PageData informationMap = lessonManageService.getInformationMap();
		List<PageData> hyList = lessonTypeService.getHyList(pd);
		model.addAttribute("hyList", hyList);
		List<PageData> lessonList = lessonManageService.getLessonTypeList(pd);
		model.addAttribute("lessonList", lessonList);
		//获取园区
		List<PageData> yqList = lessonManageService.getAreaList(pd);
		model.addAttribute("yqList", yqList);
		model.addAttribute("informationMap", informationMap);
		return "lessonmanage/lesson/editInformationManage";
	}
	
	/**
	 * 描述：更新课程信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-21
	 */
	@RequestMapping(value="/updateInformation")
	@ResponseBody
	public Map updateInformation(){
		return lessonManageService.updateInformation();
	}
	
	/**
	 * 描述：课程上传
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-15
	 */
	@RequestMapping(value="/videoUpload")
	public String videoUpload() {
		return "lessonmanage/lesson/index";
	}
	
	/**
	 * 描述：删除事课程信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-09
	 */
	@RequestMapping(value="/delInformation")
	@ResponseBody
	public Map delInformation(){
		return lessonManageService.delInformatione();
	}
	
	/**
	 * 描述：获取类型所属行业
	 * @return
	 * @author zhangcc
	 * @throws IOException 
	 * @Date : 2019-05-22
	 */
	@RequestMapping(value="/getUpHy")
	public void getUpHy(HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(lessonManageService.getUpHy());
	}
	
	
	
}
