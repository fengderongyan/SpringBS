package com.fh.controller.system.organization;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.service.system.fhlog.FHlogManager;
import com.fh.service.system.organization.OrganizationService;
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
@RequestMapping(value="/org")
public class OrganizationController extends BaseController {

	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	
	@Autowired
	private OrganizationService organizationService;
	
	/**
	 * 描述：进入组织页面
	 * @return
	 * @author yanbs
	 * @Date : 2019-04-26
	 */
	@RequestMapping(value="/orgList")
	public String orgList(Model model) throws Exception{
	 	model.addAttribute("QX", Jurisdiction.getHC());
		return "system/organization/orgList";
	}
	
	/**
	 * 描述：获取组织列表
	 * @return
	 * @author yanbs
	 * @Date : 2019-04-26
	 */
	@RequestMapping(value="/getOrgList")
	@ResponseBody
	public Map getOrgList() throws Exception{
		PageData pd = this.getPageData();
		pd.put("role_char", this.getUser().getRole().getROLE_CHAR());
		pd.put("role_lev", this.getUser().getRole().getROLE_LEV());
		pd.put("county_id", this.getUser().getOrganization().getCounty_id());
		pd.put("area_id", this.getUser().getOrganization().getArea_id());
		pd.put("org_id", this.getUser().getOrg_id());
		pd.put("role_cha_all", this.getUser().getRole().getROLE_CHA_ALL());
		List<PageData> orgList = organizationService.getOrgList(pd);
		return ResultUtils.returnWebPage(orgList);
	}
	
	/**
	 * 描述：新增页面
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-04-26
	 */
	@RequestMapping(value="/toAdd")
	@CrossOrigin
	public String toAdd(Model model) throws Exception{
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		String flag = pd.getString("flag");
		List<PageData> countyList = organizationService.getCountyList(pd);
		model.addAttribute("countyList", countyList);
		if("addCounty".equals(flag)){//新增县区
			return "system/organization/addCounty";
		}else if("addArea".equals(flag)){//新增园区
			return "system/organization/addArea";
		}else if("addOrg".equals(flag)){//新增企业
			
			List<PageData> industryList = organizationService.getIndustryList();
			model.addAttribute("industryList", industryList);
			return "system/organization/addOrg";
		}
		
		return null;
	}
	
	/**
	 * 描述：保存新增信息
	 * @return
	 * @author yanbs
	 * @Date : 2019-04-28
	 */
	@RequestMapping(value="/saveOrg")
	@ResponseBody
	public Map saveOrg(){
		PageData pd = this.getPageData();
		String org_id = this.get32UUID();
		pd.put("org_id", org_id);
		String flag = pd.getString("flag");
		if("addCounty".equals(flag)){//新增县区
			pd.put("org_lev", "3");
			pd.put("county_id", org_id);
		}else if("addArea".equals(flag)){//新增园区
			pd.put("org_lev", "4");
			pd.put("p_org_id", pd.getString("county_id"));//上级组织为县区
			pd.put("area_id", org_id);
		}else if("addOrg".equals(flag)){//新增企业
			pd.put("p_org_id", pd.getString("area_id"));//上级组织为园区
			pd.put("org_lev", "5");
		}
		pd.put("record_id", this.getUser().getUSER_ID());
		try {
			System.out.println("pd:::" + pd);
			organizationService.saveOrg(pd);
			return ResultUtils.returnWebOk();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnWebError();
		}
		
	}
	
	/**
	 * 描述：编辑页面
	 * @param model
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-04-28
	 */
	@RequestMapping(value="/toEdit")
	public String toEdit(Model model) throws Exception{
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		PageData orgMap = organizationService.getOrgMap(pd);
		model.addAttribute("orgMap", orgMap);
		List<PageData> countyList = organizationService.getCountyList(pd);
		model.addAttribute("countyList", countyList);
		if("3".equals(pd.getString("org_lev"))){//县区
			return "system/organization/editCounty";
		}else if("4".equals(pd.getString("org_lev"))){//园区
			return "system/organization/editArea";
		}else if("5".equals(pd.getString("org_lev"))){//企业
			List<PageData> areaList = organizationService.getAreaListByCountyId(pd);
			List<PageData> industryList = organizationService.getIndustryList();
			model.addAttribute("areaList", areaList);
			model.addAttribute("industryList", industryList);
			return "system/organization/editOrg";
		}
		return null;
	}
	
	/**
	 * 描述：保存编辑
	 * @return
	 * @author yanbs
	 * @Date : 2019-04-28
	 */
	@RequestMapping(value="/saveEditOrg")
	@ResponseBody
	public Map saveEditOrg(){
		PageData pd = this.getPageData();
		pd.put("record_id", this.getUser().getUSER_ID());
		if("3".equals(pd.getString("org_lev"))){//县区
			pd.put("county_id", pd.getString("org_id"));
		}else if("4".equals(pd.getString("org_lev"))){//园区
			pd.put("p_org_id", pd.getString("county_id"));
			pd.put("area_id", pd.getString("org_id"));
		}else if("5".equals(pd.getString("org_lev"))){//企业
			pd.put("p_org_id", pd.getString("area_id"));
		}
		try {
			organizationService.saveEditOrg(pd);
			return ResultUtils.returnWebOk();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnWebError();
		}
	}
	
	/**
	 * 描述：删除组织信息
	 * @return
	 * @author yanbs
	 * @Date : 2019-04-28
	 */
	@RequestMapping(value="/delOrg")
	@ResponseBody
	public Map delOrg(){
		PageData pd = this.getPageData();
		pd.put("record_id", this.getUser().getUSER_ID());
		try {
			boolean havingChildOrg = organizationService.isHavingChildOrg(pd);
			if(havingChildOrg){
				return ResultUtils.returnWebError("请先删除下级所有组织");
			}
			organizationService.delOrg(pd);
			return ResultUtils.returnWebOk();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnWebError();
		}
	}
	
	@RequestMapping(value="/getAreaListByCountyId")
	@ResponseBody
	public List getAreaListByCountyId(){
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		List<PageData> areaList = new ArrayList<PageData>();
		try {
			areaList = organizationService.getAreaListByCountyId(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return areaList;
	}
}