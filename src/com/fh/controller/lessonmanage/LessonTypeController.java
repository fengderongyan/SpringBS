package com.fh.controller.lessonmanage;


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
import com.fh.service.lessonmanage.LessonTypeService;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.ResultUtils;


/**
 * 描述：课程管理
 * @author zhangcc
 * @Date : 2019-05-21
 */
@Controller
@RequestMapping(value="/lessonmanage/lessontype")
public class LessonTypeController extends BaseController {
	
	@Autowired
	private LessonTypeService lessonTypeService;
	
	/**
	 * 描述：课程类型首页
	 * @param model
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-21
	 */
	@RequestMapping(value="/lessonTypeManageList")
	public String lessonTypeManageList(Model model){
		model.addAttribute("QX", Jurisdiction.getHC());
		return "lessonmanage/lessontype/informationList";
	}
	
	
	/**
	 * 描述：课程类型列表
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
		List<PageData> informationList = lessonTypeService.getTypeManageList(pd);
		return ResultUtils.returnWebPage(informationList);
	}
	

	/**
	 * 描述：课程类型新增页面
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-20
	 */
	@RequestMapping(value="/addInformation")
	public String addInformation(Model model) throws Exception{
		PageData pd = this.getPageData();
		List<PageData> hyList = lessonTypeService.getHyList(pd);
		model.addAttribute("hyList", hyList);
		return "lessonmanage/lessontype/addInformation";
	}
	
	
	/**
	 * 描述：保存课程类型信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-20
	 */
	@RequestMapping(value="/saveInformation")
	@ResponseBody
	public Map saveInformation(){
		return lessonTypeService.saveInformation();
	}
	
	/**
	 * 描述：获取课程类型详情
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
		PageData informationMap = lessonTypeService.getInformationMap();
		List<PageData> hyList = lessonTypeService.getHyList(pd);
		model.addAttribute("hyList", hyList);
		model.addAttribute("informationMap", informationMap);
		return "lessonmanage/lessontype/editInformationManage";
	}
	
	
	/**
	 * 描述：课程类型信息更新
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-21
	 */
	@RequestMapping(value="/updateInformation")
	@ResponseBody
	public Map updateInformation(){
		return lessonTypeService.updateInformation();
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
		return lessonTypeService.delInformatione();
	}
}
