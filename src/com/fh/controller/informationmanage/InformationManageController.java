package com.fh.controller.informationmanage;


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
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.ResultUtils;


/**
 * 描述：首页资讯管理
 * @author zhangcc
 * @Date : 2019-05-07
 */
@Controller
@RequestMapping(value="/informationmanage")
public class InformationManageController extends BaseController {
	
	@Autowired
	private InformationManageService informationManageService;
	
	
	/**
	 * 描述：进入首页资讯管理
	 * @param model
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-07
	 */
	@RequestMapping(value="/informationManageList")
	public String informationManageList(Model model){
		model.addAttribute("QX", Jurisdiction.getHC());
		return "informationmanage/homepage/informationList";
	}
	
	/**
	 * 描述：获取首页资讯列表
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-07
	 */
	@RequestMapping(value="/getInformaticaManageList")
	@ResponseBody
	public Map getInformaticaManageList() throws Exception{
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		List<PageData> informationList = informationManageService.getInformationManageList(pd);
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
		return "informationmanage/homepage/addInformation";
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
		return informationManageService.saveInformation();
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
		PageData informationMap = informationManageService.getInformationMap();
		model.addAttribute("informationMap", informationMap);
		return "informationmanage/homepage/editInformationManage";
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
		return informationManageService.updateInformation();
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
		return informationManageService.delInformatione();
	}
}
