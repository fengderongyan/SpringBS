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
import com.fh.service.videomanage.ExpertManageService;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.ResultUtils;



/**
 * 描述：专家信息管理
 * @author zhangcc
 * @Date : 2019-05-17
 */
@Controller
@RequestMapping(value="/videomanage/expert")
public class ExpertManageController extends BaseController {
	
	@Autowired
	private ExpertManageService expertManageService;
	
	
	/**
	 * 描述：专家信息管理首页
	 * @param model
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-17
	 */
	@RequestMapping(value="/expertManageList")
	public String expertManageList(Model model){
		model.addAttribute("QX", Jurisdiction.getHC());
		return "videomanage/expert/informationList";
	}
	
	
	/**
	 * 描述：获取专家信息列表
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-17
	 */
	@RequestMapping(value="/getExpertManageList")
	@ResponseBody
	public Map getExpertManageList() throws Exception{
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		List<PageData> informationList = expertManageService.getExperManageList(pd);
		return ResultUtils.returnWebPage(informationList);
	}
	
	/**
	 * 描述：新增首页资讯信息
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-07
	 */
	@RequestMapping(value="/addInformation")
	public String addInformation(Model model) throws Exception{
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		return "videomanage/expert/addInformation";
	}
	
	/**
	 * 描述：保存首页资讯信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-07
	 */
	@RequestMapping(value="/saveInformation")
	@ResponseBody
	public Map saveInformation(){
		return expertManageService.saveInformation();
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
		PageData informationMap = expertManageService.getInformationMap();
		model.addAttribute("informationMap", informationMap);
		return "videomanage/expert/editInformationManage";
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
		return expertManageService.updateInformation();
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
		return expertManageService.delInformatione();
	}
}
