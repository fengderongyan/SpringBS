package com.fh.controller.system.user;


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
import com.fh.service.system.user.SysUserService;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.ResultUtils;

/** 
 * 类名称：UserController
 * 创建人：FH fh313596790qq(青苔)
 * 更新时间：2018年6月24日
 * @version
 */
@Controller
@RequestMapping(value="/sysuser")
public class SysUserController extends BaseController {
	
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 描述：进入用户管理
	 * @return
	 * @author yanbs
	 * @Date : 2019-04-29
	 */
	@RequestMapping(value="/sysUserList")
	public String sysUserList(Model model){
		model.addAttribute("QX", Jurisdiction.getHC());
		return "system/user/sysUserList";
	}
	
	/**
	 * 描述：获取用户列表
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-04-29
	 */
	@RequestMapping(value="/getSysUserList")
	@ResponseBody
	public Map getSysUserList() throws Exception{
		PageData pd = this.getPageData();
		System.out.println("pd::" + pd);
		pd.put("user", this.getUser());
		List<PageData> sysUserList = sysUserService.getSysUserList(pd);
		return ResultUtils.returnWebPage(sysUserList);
	}
	
	/**
	 * 描述：添加管理员页面
	 * @return
	 * @author yanbs
	 * @throws Exception 
	 * @Date : 2019-04-29
	 */
	@RequestMapping(value="/addSysManager")
	public String addSysManager(Model model) throws Exception{
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		List<PageData> childRole = sysUserService.getChildRole(pd);
		model.addAttribute("childRole", childRole);
		return "system/user/addSysManager";
	}
	
	
	/**
	 * 描述：添加员工页面
	 * @return
	 * @author yanbs
	 * @Date : 2019-04-29
	 */
	@RequestMapping(value="/addSysUser")
	public String addSysUser(Model model) throws Exception{

		return "system/user/addSysUser";
	}
	
	/**
	 * 描述：跳转组织树
	 * @return
	 * @author yanbs
	 * @Date : 2019-04-30
	 */
	@RequestMapping(value="/orgTree")
	public String orgTree(){
		
		return "system/user/orgTree";
	}
	
	/**
	 * 描述：加载组织树
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-04-30
	 */
	@RequestMapping(value="/loadOrgTree")
	@ResponseBody
	public List loadOrgTree() throws Exception{
		PageData pd = this.getPageData();
		PageData rpd = sysUserService.getRoleLev(pd);
		List<PageData> orgList = sysUserService.loadOrgTree(rpd);
		List<TreeModel> treeModelList=new ArrayList<TreeModel>();
		if(orgList != null && orgList.size() > 0){
			for (PageData pageData : orgList) {
				TreeModel treeModel = new TreeModel();
				treeModel.setId(pageData.getString("org_id"));
				treeModel.setpId(pageData.getString("p_org_id"));
				treeModel.setName(pageData.getString("org_name"));
				if(rpd.getString("role_lev").equals(pageData.getString("org_lev"))){
					treeModel.setNocheck(false);
				}else{
					treeModel.setNocheck(true);
				}
				if("0".equals(pageData.getString("org_id"))){
					treeModel.setOpen(true);
				}
				treeModelList.add(treeModel);
				
			}
		}
		return treeModelList;
	}
	
	/**
	 * 描述：保存用户信息
	 * @return
	 * @author yanbs
	 * @Date : 2019-04-30
	 */
	@RequestMapping(value="/saveUser")
	@ResponseBody
	public Map saveUser(){
		return sysUserService.saveUser();
	}
	
	/**
	 * 描述：校验用户名是否重复
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-04-30
	 */
	@RequestMapping(value="/checkUsername")
	@ResponseBody
	public String checkUsername() throws Exception{
		String result = sysUserService.checkUsername();
		return result;
	}
	
	/**
	 * 描述：校验电话是否重复
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-04-30
	 */
	@RequestMapping(value="/checkPhone")
	@ResponseBody
	public String checkPhone() throws Exception{
		String result = sysUserService.checkPhone();
		return result;
	}
	
	/**
	 * 描述：去编辑/详情页面
	 * @return
	 * @author yanbs
	 * @Date : 2019-04-30
	 */
	@RequestMapping(value="/editSysManager")
	public String editSysManager(Model model) throws Exception{
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		List<PageData> childRole = sysUserService.getChildRole(pd);
		model.addAttribute("childRole", childRole);
		PageData userMap = sysUserService.getSysUserMap();
		model.addAttribute("userMap", userMap);
		return "system/user/editSysManager";
	}
	
	/**
	 * 描述：去编辑/详情页面
	 * @return
	 * @author yanbs
	 * @Date : 2019-04-30
	 */
	@RequestMapping(value="/editSysUser")
	public String editSysUser(Model model) throws Exception{
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		List<PageData> childRole = sysUserService.getChildRole(pd);
		model.addAttribute("childRole", childRole);
		PageData userMap = sysUserService.getSysUserMap();
		model.addAttribute("userMap", userMap);
		return "system/user/editSysUser";
	}
	
	/**
	 * 描述：保存编辑信息
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-05
	 */
	@RequestMapping(value="/saveEdit")
	@ResponseBody
	public Map saveEdit(){
		return sysUserService.saveEdit();
	}
	
	/**
	 * 描述：批量导入页面
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-05
	 */
	@RequestMapping(value="/upLoadSysUser")
	public String upLoadSysUser(Model model){
		String role_char = this.getUser().getRole().getROLE_CHAR();
		String role_lev = this.getUser().getRole().getROLE_LEV();
		Map orgInfoMap = new HashMap();
		orgInfoMap.put("role_char", role_char);
		orgInfoMap.put("role_char", role_lev);
		orgInfoMap.put("org_id", this.getUser().getOrg_id());
		orgInfoMap.put("org_name", this.getUser().getOrganization().getOrg_name());
		System.out.println("orgInfoMap:" + orgInfoMap);
		model.addAttribute("orgInfoMap", orgInfoMap);
		return "system/user/upLoadSysUser";
	}
	
	@RequestMapping(value="/saveUpload")
	@ResponseBody
	public Map saveUpload(@RequestParam(value="file") CommonsMultipartFile file, @RequestParam(value="org_id") String org_id){
		System.out.println("org_id:::" + org_id);
		System.out.println("file:::" + file);
		return sysUserService.saveUpload(file, org_id);
	}
	
	@RequestMapping(value="/delUser")
	@ResponseBody
	public Map delUser(){
		return sysUserService.delUser();
	}
	
	/**
	 * 描述：重置密码
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-21
	 */
	@RequestMapping(value="/resetPwd")
	@ResponseBody
	public Map resetPwd(){
		return sysUserService.resetPwd();
	}
	
	/**
	 * 描述：修改密码
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-21
	 */
	@RequestMapping(value="/updatePwd")
	@ResponseBody
	public Map updatePwd(){
		return sysUserService.updatePwd();
	}
}
