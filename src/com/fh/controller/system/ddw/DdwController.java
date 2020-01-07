package com.fh.controller.system.ddw;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.service.system.ddw.DdwService;
import com.fh.service.system.fhlog.FHlogManager;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.ResultUtils;

/** 
 * 类名称：RoleController 角色权限管理
 * 创建人：FH Q313596790
 * 修改时间：2018年7月6日
 * @version
 */
@Controller
@RequestMapping(value="/ddw")
public class DdwController extends BaseController {

	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	
	@Autowired
	private DdwService ddwService;
	
	/**
	 * 描述：进入页面
	 * @return
	 * @author yanbs
	 * @Date : 2019-04-25
	 */
	@RequestMapping(value="/ddwList")
	public String ddwList(Model model){
		model.addAttribute("QX", Jurisdiction.getHC());
		return "system/ddw/ddwList";
	}
	
	/**
	 * 描述：初始化查询列表并分页
	 * @return
	 * @author yanbs
	 * @Date : 2019-04-25
	 */
	@RequestMapping(value="/getDdwList")
	@ResponseBody
	public Map getDdwList() throws Exception{
		PageData pd = this.getPageData();
		List<PageData> ddwList = ddwService.getDdwList(pd);
		return ResultUtils.returnWebPage(ddwList);
	}
	
	/**
	 * 描述：去新增页面
	 * @param model
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-04-25
	 */
	@RequestMapping(value="/toAdd")
	public String toAdd(Model model) throws Exception{
		model.addAttribute("typeList", ddwService.getTypeList());
		return "system/ddw/toAdd";
	}
	
	@RequestMapping(value="/saveAdd")
	@ResponseBody
	public Map saveAdd(){
		PageData pd = this.getPageData();
		
		return null;
	}
	
}