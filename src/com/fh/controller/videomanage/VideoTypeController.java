package com.fh.controller.videomanage;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.fh.service.videomanage.VideoTypeService;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.ResultUtils;


/**
 * 描述：视频分类管理
 * @author zhangcc
 * @Date : 2019-05-20
 */
@Controller
@RequestMapping(value="/videomanage/videotype")
public class VideoTypeController extends BaseController {
	
	@Autowired
	private VideoTypeService videoTypeService;
	
	/**
	 * 描述：视频分类管理首页
	 * @param model
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-20
	 */
	@RequestMapping(value="/videoTypeManageList")
	public String videoTypeManageList(Model model){
		model.addAttribute("QX", Jurisdiction.getHC());
		return "videomanage/videotype/informationList";
	}
	
	
	/**
	 * 描述：视频类型列表
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-20
	 */
	@RequestMapping(value="/getTypeManageList")
	@ResponseBody
	public Map getTypeManageList() throws Exception{
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		List<PageData> informationList = videoTypeService.getTypeManageList(pd);
		return ResultUtils.returnWebPage(informationList);
	}
	

	/**
	 * 描述：视频类型新增页面
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-20
	 */
	@RequestMapping(value="/addInformation")
	public String addInformation(Model model) throws Exception{
		PageData pd = this.getPageData();
		List<PageData> hyList = videoTypeService.getHyList(pd);
		model.addAttribute("hyList", hyList);
		return "videomanage/videotype/addInformation";
	}
	
	
	/**
	 * 描述：保存视频类型信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-20
	 */
	@RequestMapping(value="/saveInformation")
	@ResponseBody
	public Map saveInformation(){
		return videoTypeService.saveInformation();
	}
	
	/**
	 * 描述：获取首页资讯详情
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-08
	 */
	@RequestMapping(value="/editInformationManager")
	public String editInformationManager(Model model) throws Exception{
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		PageData informationMap = videoTypeService.getInformationMap();
		List<PageData> hyList = videoTypeService.getHyList(pd);
		model.addAttribute("hyList", hyList);
		model.addAttribute("informationMap", informationMap);
		return "videomanage/videotype/editInformationManage";
	}
	
	/**
	 * 描述：修改首页资讯信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-08
	 */
	@RequestMapping(value="/updateInformation")
	@ResponseBody
	public Map updateInformation(){
		return videoTypeService.updateInformation();
	}
	
	/**
	 * 描述：删除首页资讯信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-08
	 */
	@RequestMapping(value="/delInformation")
	@ResponseBody
	public Map delInformation(){
		return videoTypeService.delInformatione();
	}
}
