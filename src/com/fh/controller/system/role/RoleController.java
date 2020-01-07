package com.fh.controller.system.role;


import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.Menu;
import com.fh.entity.system.Role;
import com.fh.entity.system.User;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.fhlog.FHlogManager;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.user.UserManager;
import com.fh.service.system.menu.MenuManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.PageResult;
import com.fh.util.ResultUtils;
import com.fh.util.RightsHelper;
import com.fh.util.Tools;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/** 
 * 类名称：RoleController 角色权限管理
 * 创建人：FH Q313596790
 * 修改时间：2018年7月6日
 * @version
 */
@Controller
@RequestMapping(value="/role")
public class RoleController extends BaseController {
	
	String menuUrl = "role.do"; //菜单地址(权限用)
	@Resource(name="menuService")
	private MenuManager menuService;
	@Resource(name="roleService")
	private RoleManager roleService;
	@Resource(name="userService")
	private UserManager userService;
	@Resource(name="appuserService")
	private AppuserManager appuserService;
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	
	/** 进入权限首页
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	public ModelAndView list()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(pd.getString("ROLE_ID") == null || "".equals(pd.getString("ROLE_ID").trim())){
				pd.put("ROLE_ID", "e2cdd69a87f411e9b2ec00163e1024a6");										//默认列出第一组角色(初始设计系统用户和会员组不能删除)
			}
			PageData fpd = new PageData();
			fpd.put("ROLE_ID", "0");
			List<Role> roleList = roleService.listAllRolesByPId(fpd);		//列出组(页面横向排列的一级组)
			List<Role> roleList_z = roleService.listAllRolesByPId(pd);		//列出此组下架角色
			pd = roleService.findObjectById(pd);							//取得点击的角色组(横排的)
			mv.addObject("pd", pd);
			mv.addObject("roleList", roleList);
			mv.addObject("roleList_z", roleList_z);
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
			mv.setViewName("system/role/roleList");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	@RequestMapping("/list2")
	@ResponseBody
	public Map list2() throws Exception{
		PageData pd = this.getPageData();
		pd.put("ROLE_ID", "e2cdd69a87f411e9b2ec00163e1024a6");	
		List<Role> roleList_z = roleService.listAllRolesByPId(pd);
		return ResultUtils.returnWebPage(roleList_z);
	}
	
	
	/**去新增页面
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			mv.addObject("msg", "add");
			mv.setViewName("system/role/role_edit");
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	@RequestMapping(value="/toAdd2")
	public ModelAndView toAdd2(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			mv.addObject("msg", "add");
			mv.setViewName("system/role/addRole");
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**保存新增角色
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ModelAndView add()throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"新增角色");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String parent_id = pd.getString("PARENT_ID");		//父类角色id
			pd.put("ROLE_ID", parent_id);			
			if("0".equals(parent_id)){
				pd.put("RIGHTS", "");							//菜单权限
			}else{
				String rights = roleService.findObjectById(pd).getString("RIGHTS");
				pd.put("RIGHTS", (null == rights)?"":rights);	//组菜单权限
			}
			pd.put("ROLE_ID", this.get32UUID());				//主键
			pd.put("ADD_QX", "0");	//初始新增权限为否
			pd.put("DEL_QX", "0");	//删除权限
			pd.put("EDIT_QX", "0");	//修改权限
			pd.put("CHA_QX", "0");	//查看权限
			roleService.add(pd);
			FHLOG.save(Jurisdiction.getUsername(), "新增角色:"+pd.getString("ROLE_NAME"));
		} catch(Exception e){
			logger.error(e.toString(), e);
			mv.addObject("msg","failed");
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	/**保存新增角色
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/add2",method=RequestMethod.POST)
	@ResponseBody
	public Object add2()throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"新增角色");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("ROLE_ID", this.get32UUID());
			pd.put("RIGHTS", "");	//
			pd.put("ADD_QX", "0");	//初始新增权限为否
			pd.put("DEL_QX", "0");	//删除权限
			pd.put("EDIT_QX", "0");	//修改权限
			pd.put("CHA_QX", "0");	//查看权限
			roleService.add(pd);
			FHLOG.save(Jurisdiction.getUsername(), "新增角色:"+pd.getString("ROLE_NAME"));
		} catch(Exception e){
			logger.error(e.toString(), e);
			return ResultUtils.returnWebError();
		}
		return ResultUtils.returnWebOk();
	}
	
	/**请求编辑
	 * @param ROLE_ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toEdit")
	public ModelAndView toEdit( String ROLE_ID )throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("ROLE_ID", ROLE_ID);
			pd = roleService.findObjectById(pd);
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
			mv.setViewName("system/role/role_edit");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	/**请求编辑
	 * @param ROLE_ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toEdit2")
	public ModelAndView toEdit2(String ROLE_ID)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("ROLE_ID", ROLE_ID);
			pd = roleService.findObjectById(pd);
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
			mv.setViewName("system/role/editRole");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**保存修改
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit()throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"修改角色");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			roleService.edit(pd);
			FHLOG.save(Jurisdiction.getUsername(), "修改角色:"+pd.getString("ROLE_NAME"));
			mv.addObject("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
			mv.addObject("msg","failed");
		}
		mv.setViewName("save_result");
		return mv;
	}
	/**保存修改
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edit2")
	@ResponseBody
	public Map edit2()throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"修改角色");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			roleService.edit(pd);
			FHLOG.save(Jurisdiction.getUsername(), "修改角色:"+pd.getString("ROLE_NAME"));
		} catch(Exception e){
			logger.error(e.toString(), e);
			ResultUtils.returnWebError();
		}
		mv.setViewName("save_result");
		return ResultUtils.returnWebOk();
	}
	
	/**删除角色
	 * @param ROLE_ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object deleteRole(@RequestParam String ROLE_ID)throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"删除角色");
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		String errInfo = "";
		try{
			pd.put("ROLE_ID", ROLE_ID);
			List<Role> roleList_z = roleService.listAllRolesByPId(pd);		//列出此部门的所有下级
			if(roleList_z.size() > 0){
				errInfo = "false";											//下级有数据时，删除失败
			}else{
				List<PageData> userlist = userService.listAllUserByRoldId(pd);			//此角色下的用户
				List<PageData> appuserlist = appuserService.listAllAppuserByRorlid(pd);	//此角色下的会员
				if(userlist.size() > 0 || appuserlist.size() > 0){						//此角色已被使用就不能删除
					errInfo = "false2";
				}else{
				roleService.deleteRoleById(ROLE_ID);	//执行删除
				FHLOG.save(Jurisdiction.getUsername(), "删除角色ID为:"+ROLE_ID);
				errInfo = "success";
				}
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**删除角色
	 * @param ROLE_ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete2")
	@ResponseBody
	public Object deleteRole2(@RequestParam String ROLE_ID)throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"删除角色");
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		String errInfo = "";
		try{
			pd.put("ROLE_ID", ROLE_ID);
			List<Role> roleList_z = roleService.listAllRolesByPId(pd);		//列出此部门的所有下级
			if(roleList_z.size() > 0){
				return ResultUtils.returnWebError("下级有数据,无法删除");			   //下级有数据时，删除失败
			}else{
				List<PageData> userlist = userService.listAllUserByRoldId(pd);			//此角色下的用户
				List<PageData> appuserlist = appuserService.listAllAppuserByRorlid(pd);	//此角色下的会员
				if(userlist.size() > 0 || appuserlist.size() > 0){						//此角色已被使用就不能删除
				return ResultUtils.returnWebError("此角色已被使用不能删除");	
				}else{
				roleService.deleteRoleById(ROLE_ID);	//执行删除
				FHLOG.save(Jurisdiction.getUsername(), "删除角色ID为:"+ROLE_ID);
				return ResultUtils.returnWebOk();
				}
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
			return ResultUtils.returnWebError();
		}
	}
	
	
	/**
	 * 显示菜单列表ztree(菜单授权菜单)
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/menuqx")
	public ModelAndView listAllMenu(Model model,String ROLE_ID, String flag)throws Exception{
		ModelAndView mv = this.getModelAndView();
		try{
			
			Role role = roleService.getRoleById(ROLE_ID);			//根据角色ID获取角色对象
			String roleRights = role.getRIGHTS();					//取出本角色菜单权限
			List<Menu> menuList = menuService.listAllMenuQx("0");	//获取所有菜单
			if(!"1".equals(flag)){//非操作admin菜单权限
				Session session = Jurisdiction.getSession();
				User user = (User)session.getAttribute(Const.SESSION_USERROL);	
				String userRoleRights = user.getRole().getRIGHTS();//获取主职菜单权限
				String userRoleIds = user.getROLE_IDS();//获取副职角色id
				List<String> arrayRoleRights = this.getArrayRoleRights(userRoleIds);//获取副职菜单权限列表
				List<Menu> roleListMenu = this.readMenu(menuList, userRoleRights, arrayRoleRights);//标记用户所拥有的菜单权限
				roleListMenu = this.getroleList(roleListMenu);//提取(管理员只能看到自己权限下的菜单)
				menuList = this.readMenu(roleListMenu, roleRights);
			}else{
				menuList = this.readMenu(menuList, roleRights);
			}
						//根据角色权限处理菜单权限状态(递归处理)
			JSONArray arr = JSONArray.fromObject(menuList);
			String json = arr.toString();
			json = json.replaceAll("MENU_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("MENU_NAME", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("ROLE_ID",ROLE_ID);
			mv.setViewName("system/role/menuqx");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	@RequestMapping(value="/roleMenu")
	public ModelAndView roleMenu(Model model,String ROLE_ID, String flag)throws Exception{
		ModelAndView mv = this.getModelAndView();
		try{
			
			Role role = roleService.getRoleById(ROLE_ID);			//根据角色ID获取角色对象
			String roleRights = role.getRIGHTS();					//取出本角色菜单权限
			List<Menu> menuList = menuService.listAllMenuQx("0");	//获取所有菜单
			if(!"1".equals(flag)){//非操作admin菜单权限
				Session session = Jurisdiction.getSession();
				User user = (User)session.getAttribute(Const.SESSION_USERROL);	
				String userRoleRights = user.getRole().getRIGHTS();//获取主职菜单权限
				String userRoleIds = user.getROLE_IDS();//获取副职角色id
				List<String> arrayRoleRights = this.getArrayRoleRights(userRoleIds);//获取副职菜单权限列表
				List<Menu> roleListMenu = this.readMenu(menuList, userRoleRights, arrayRoleRights);//标记用户所拥有的菜单权限
				roleListMenu = this.getroleList(roleListMenu);//提取(管理员只能看到自己权限下的菜单)
				menuList = this.readMenu(roleListMenu, roleRights);
			}else{
				menuList = this.readMenu(menuList, roleRights);
			}
						//根据角色权限处理菜单权限状态(递归处理)
			JSONArray arr = JSONArray.fromObject(menuList);
			String json = arr.toString();
			json = json.replaceAll("MENU_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("MENU_NAME", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("ROLE_ID",ROLE_ID);
			mv.setViewName("system/role/roleMenu");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**保存角色菜单权限
	 * @param ROLE_ID 角色ID
	 * @param menuIds 菜单ID集合
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/saveMenuqx")
	@ResponseBody
	public Map saveMenuqx(@RequestParam String ROLE_ID, @RequestParam String menuIds)throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"修改菜单权限");
		FHLOG.save(Jurisdiction.getUsername(), "修改角色菜单权限，角色ID为:"+ROLE_ID);
		PageData pd = new PageData();
		try{
			if(null != menuIds && !"".equals(menuIds.trim())){
				BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));//用菜单ID做权处理
				Role role = roleService.getRoleById(ROLE_ID);	//通过id获取角色对象
				role.setRIGHTS(rights.toString());
				roleService.updateRoleRights(role);				//更新当前角色菜单权限
				pd.put("rights",rights.toString());
			}else{
				Role role = new Role();
				role.setRIGHTS("");
				role.setROLE_ID(ROLE_ID);
				roleService.updateRoleRights(role);				//更新当前角色菜单权限(没有任何勾选)
				pd.put("rights","");
			}
				pd.put("ROLE_ID", ROLE_ID);
				if(!"e2cdd69a87f411e9b2ec00163e1024a6".equals(ROLE_ID)){						//当修改admin权限时,不修改其它角色权限
					roleService.setAllRights(pd);				//更新此角色所有子角色的菜单权限
				}
			return ResultUtils.returnWebOk(); 
		} catch(Exception e){
			logger.error(e.toString(), e);
			return ResultUtils.returnWebError(); 
		}
	}

	/**请求角色按钮授权页面(增删改查)
	 * @param ROLE_ID： 角色ID
	 * @param msg： 区分增删改查
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/b4Button")
	public ModelAndView b4Button(@RequestParam String ROLE_ID,@RequestParam String msg,Model model)throws Exception{
		ModelAndView mv = this.getModelAndView();
		try{
			List<Menu> menuList = menuService.listAllMenuQx("0"); //获取所有菜单
			Role role = roleService.getRoleById(ROLE_ID);		  //根据角色ID获取角色对象
			String roleRights = "";
			if("add_qx".equals(msg)){
				roleRights = role.getADD_QX();	//新增权限
			}else if("del_qx".equals(msg)){
				roleRights = role.getDEL_QX();	//删除权限
			}else if("edit_qx".equals(msg)){
				roleRights = role.getEDIT_QX();	//修改权限
			}else if("cha_qx".equals(msg)){
				roleRights = role.getCHA_QX();	//查看权限
			}
			//自己后加内容，管理员只能看自己权限下的菜单
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USERROL);	
			String userRoleRights = user.getRole().getRIGHTS();//获取主职菜单权限
			String userRoleIds = user.getROLE_IDS();//获取副职角色id
			List<String> arrayRoleRights = this.getArrayRoleRights(userRoleIds);//获取副职菜单权限列表
			List<Menu> roleListMenu = this.readMenu(menuList, userRoleRights, arrayRoleRights);//标记用户所拥有的菜单权限
			roleListMenu = this.getroleList(roleListMenu);//提取(管理员只能看到自己权限下的菜单)
			
			menuList = this.readMenu(roleListMenu, roleRights);		//根据角色权限处理菜单权限状态(递归处理)
			JSONArray arr = JSONArray.fromObject(menuList);
			String json = arr.toString();
			json = json.replaceAll("MENU_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("MENU_NAME", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("ROLE_ID",ROLE_ID);
			mv.addObject("msg", msg);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.setViewName("system/role/buttonMenu");
		return mv;
	}
	
	/**根据角色权限处理权限状态(递归处理)
	 * @param menuList：传入的总菜单
	 * @param roleRights：加密的权限字符串
	 * @return
	 */
	public List<Menu> readMenu(List<Menu> menuList,String roleRights){
		for(int i=0;i<menuList.size();i++){
			menuList.get(i).setHasMenu(RightsHelper.testRights(roleRights, menuList.get(i).getMENU_ID()));
			this.readMenu(menuList.get(i).getSubMenu(), roleRights);					//是：继续排查其子菜单
		}
		return menuList;
	}
	
	/**
	 * 保存角色按钮权限
	 */
	/**
	 * @param ROLE_ID
	 * @param menuIds
	 * @param msg
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/saveB4Button")
	@ResponseBody
	public Map saveB4Button(@RequestParam String ROLE_ID,@RequestParam String menuIds,@RequestParam String msg)throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"修改"+msg+"权限");
		FHLOG.save(Jurisdiction.getUsername(), "修改"+msg+"权限，角色ID为:"+ROLE_ID);
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			if(null != menuIds && !"".equals(menuIds.trim())){
				BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));
				pd.put("value",rights.toString());
			}else{
				pd.put("value","");
			}
			pd.put("ROLE_ID", ROLE_ID);
			roleService.saveB4Button(msg,pd);
			return ResultUtils.returnWebOk();
		} catch(Exception e){
			logger.error(e.toString(), e);
			return ResultUtils.returnWebError();
		}
	}
	
	public List<String> getArrayRoleRights(String ROLE_IDS) throws Exception{
		if(Tools.notEmpty(ROLE_IDS)){
			List<String> list = new ArrayList<String>();
			String arryROLE_ID[] = ROLE_IDS.split(",fh,");
			for(int i=0;i<arryROLE_ID.length;i++){
				PageData pd = new PageData();
				pd.put("ROLE_ID", arryROLE_ID[i]);
				pd = roleService.findObjectById(pd);
				if(null != pd){
					String RIGHTS = pd.getString("RIGHTS");
					if(Tools.notEmpty(RIGHTS)){
						list.add(RIGHTS);
					}
				}
			}
			return list.size() == 0 ? null : list;
		}else{
			return null;
		}
	}
	
	public List<Menu> readMenu(List<Menu> menuList,String roleRights, List<String> arrayRoleRights){
		for(int i=0;i<menuList.size();i++){
			Boolean b1 = RightsHelper.testRights(roleRights, menuList.get(i).getMENU_ID());
			menuList.get(i).setHasMenu(b1); //赋予主职角色菜单权限
			if(!b1 && null != arrayRoleRights){
				for(int n=0;n<arrayRoleRights.size();n++){
					if(RightsHelper.testRights(arrayRoleRights.get(n), menuList.get(i).getMENU_ID())){
						menuList.get(i).setHasMenu(true);
						break;
					}
				}
			}
			if(menuList.get(i).isHasMenu()){		//判断是否有此菜单权限
				this.readMenu(menuList.get(i).getSubMenu(), roleRights, arrayRoleRights);//是：继续排查其子菜单
			}
		}
		return menuList;
	}
	
	public List<Menu> getroleList(List<Menu> menuList){
		List<Menu> tempList = menuList;
		if(tempList != null && tempList.size() > 0){
			for(int i = 0; i< tempList.size(); i++){
				Menu menu = tempList.get(i);
				if(!menu.isHasMenu()){
					menuList.remove(i);
					this.getroleList(menuList);
				}
				this.getroleList(menu.getSubMenu());
			}
		}
		return menuList;
	}
	
}