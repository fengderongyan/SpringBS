package com.fh.controller.videomanage;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.fh.service.videomanage.VideoManageService;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.ResultUtils;


/**
 * 描述：视频管理
 * @author zhangcc
 * @Date : 2019-05-20
 */
@Controller
@RequestMapping(value="/videomanage/video")
public class VideoManageController extends BaseController {
	
	@Autowired
	private VideoManageService videoManageService;
	
	
	/**
	 * 描述：视频信息管理首页
	 * @param model
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-20
	 */
	@RequestMapping(value="/informationManageList")
	public String informationManageList(Model model) throws Exception{
		model.addAttribute("QX", Jurisdiction.getHC());
		PageData pd = this.getPageData();
		List<PageData> videoTypeList = videoManageService.getVideoTypeList(pd);
		model.addAttribute("videoTypeList", videoTypeList);
		return "videomanage/video/informationList";
	}
	
	
	/**
	 * 描述：获取视频列表
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-20
	 */
	@RequestMapping(value="/getInformaticaManageList")
	@ResponseBody
	public Map getInformaticaManageList() throws Exception{
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		List<PageData> informationList = videoManageService.getInformationManageList(pd);
		return ResultUtils.returnWebPage(informationList);
	}
	
	
	/**
	 * 描述：新增视频信息
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-20
	 */
	@RequestMapping(value="/addInformation")
	public String addInformation(Model model) throws Exception{
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		List<PageData> videoTypeList = videoManageService.getVideoTypeList(pd);
		model.addAttribute("videoTypeList", videoTypeList);
		return "videomanage/video/addInformation";
	}
	
	/**
	 * 描述：专家信息首页
	 * @param model
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-20
	 */
	@RequestMapping(value="/expertList")
	public String expertList(Model model){
		model.addAttribute("QX", Jurisdiction.getHC());
		return "videomanage/video/expertList";
	}
	
	
	/**
	 * 描述：保存视频信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-20
	 */
	@RequestMapping(value="/saveInformation")
	@ResponseBody
	public Map saveInformation(){
		return videoManageService.saveInformation();
	}
	
	
	/**
	 * 描述：获取视频信息详情
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-20
	 */
	@RequestMapping(value="/editInformationManager")
	public String editInformationManager(Model model) throws Exception{
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		PageData informationMap = videoManageService.getInformationMap();
		List<PageData> videoTypeList = videoManageService.getVideoTypeList(pd);
		model.addAttribute("videoTypeList", videoTypeList);
		model.addAttribute("informationMap", informationMap);
		return "videomanage/video/editInformationManage";
	}
	
	/**
	 * 描述：更新视频信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-20
	 */
	@RequestMapping(value="/updateInformation")
	@ResponseBody
	public Map updateInformation(){
		return videoManageService.updateInformation();
	}
	
	/**
	 * 描述：视频上传
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-15
	 */
	@RequestMapping(value="/videoUpload")
	public String videoUpload() {
		return "videomanage/video/index";
	}
	
	/**
	 * 描述：删除事视频信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-09
	 */
	@RequestMapping(value="/delInformation")
	@ResponseBody
	public Map delInformation(){
		return videoManageService.delInformatione();
	}
}
