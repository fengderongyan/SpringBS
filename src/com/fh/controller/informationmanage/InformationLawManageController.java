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
import com.fh.service.informationmanage.InformationLawManageService;
import com.fh.service.informationmanage.InformationManageService;
import com.fh.service.informationmanage.InformationSafeManageService;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.ResultUtils;

/**
 * 描述：法律法规
 * @author zhangcc
 * @Date : 2019-05-08
 */
@Controller
@RequestMapping(value="/informationLawManage")
public class InformationLawManageController extends BaseController {
	
	@Autowired
	private InformationLawManageService informationLawManageService;
	
	
	/**
	 * 描述：进入法律法规管理
	 * @param model
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-07
	 */
	@RequestMapping(value="/informationManageList")
	public String informationManageList(Model model){
		model.addAttribute("QX", Jurisdiction.getHC());
		return "informationmanage/law/informationList";
	}
	
	/**
	 * 描述：获取法律法规列表
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
		List<PageData> informationList = informationLawManageService.getInformationManageList(pd);
		return ResultUtils.returnWebPage(informationList);
	}
	
	/**
	 * 描述：新增法律法规信息
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
		return "informationmanage/law/addInformation";
	}
	
	/**
	 * 描述：保存法律法规信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-07
	 */
	@RequestMapping(value="/saveInformation")
	@ResponseBody
	public Map saveInformation(){
		return informationLawManageService.saveInformation();
	}
	
	/**
	 * 描述：获取法律法规详情
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
		PageData informationMap = informationLawManageService.getInformationMap();
		model.addAttribute("informationMap", informationMap);
		return "informationmanage/law/editInformationManage";
	}
	
	/**
	 * 描述：修改法律法规信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-08
	 */
	@RequestMapping(value="/updateInformation")
	@ResponseBody
	public Map updateInformation(){
		return informationLawManageService.updateInformation();
	}
	
	/**
	 * 描述：删除法律法规信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-08
	 */
	@RequestMapping(value="/delInformation")
	@ResponseBody
	public Map delInformation(){
		return informationLawManageService.delInformatione();
	}
}
