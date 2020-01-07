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
import com.fh.service.informationmanage.InformationAccidentManageService;
import com.fh.service.informationmanage.InformationManageService;
import com.fh.service.informationmanage.InformationSafeManageService;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.ResultUtils;

/**
 * 描述：事故案例
 * @author zhangcc
 * @Date : 2019-05-09
 */
@Controller
@RequestMapping(value="/informationAccidentManage")
public class InformationAccidentManageController extends BaseController {
	
	@Autowired
	private InformationAccidentManageService informationAccidentManageService;
	
	
	/**
	 * 描述：进入事故案例管理
	 * @param model
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-09
	 */
	@RequestMapping(value="/informationManageList")
	public String informationManageList(Model model){
		model.addAttribute("QX", Jurisdiction.getHC());
		return "informationmanage/accident/informationList";
	}
	
	/**
	 * 描述：获取事故案例列表
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-09
	 */
	@RequestMapping(value="/getInformaticaManageList")
	@ResponseBody
	public Map getInformaticaManageList() throws Exception{
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		List<PageData> informationList = informationAccidentManageService.getInformationManageList(pd);
		return ResultUtils.returnWebPage(informationList);
	}
	
	/**
	 * 描述：新增事故案例信息
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-09
	 */
	@RequestMapping(value="/addInformation")
	public String addInformation(Model model) throws Exception{
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		return "informationmanage/accident/addInformation";
	}
	
	/**
	 * 描述：保存事故案例信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-09
	 */
	@RequestMapping(value="/saveInformation")
	@ResponseBody
	public Map saveInformation(){
		return informationAccidentManageService.saveInformation();
	}
	
	/**
	 * 描述：获取事故案例详情
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-09
	 */
	@RequestMapping(value="/editInformationManager")
	public String editInformationManager(Model model) throws Exception{
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		PageData informationMap = informationAccidentManageService.getInformationMap();
		model.addAttribute("informationMap", informationMap);
		return "informationmanage/accident/editInformationManage";
	}
	
	/**
	 * 描述：修改事故案例信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-09
	 */
	@RequestMapping(value="/updateInformation")
	@ResponseBody
	public Map updateInformation(){
		return informationAccidentManageService.updateInformation();
	}
	
	/**
	 * 描述：视频上传
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-15
	 */
	@RequestMapping(value="/videoUpload")
	public String videoUpload() {
		return "informationmanage/accident/index";
	}
	
	/**
	 * 描述：删除事故案例信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-09
	 */
	@RequestMapping(value="/delInformation")
	@ResponseBody
	public Map delInformation(){
		return informationAccidentManageService.delInformatione();
	}
}
