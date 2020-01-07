package com.fh.controller.system.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.fh.controller.base.BaseController;
import com.fh.service.chartlog.ChartLogService;
import com.fh.service.fhoa.datajur.DatajurManager;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.buttonrights.ButtonrightsManager;
import com.fh.service.system.fhbutton.FhbuttonManager;
import com.fh.service.system.fhlog.FHlogManager;
import com.fh.service.system.loginimg.LogInImgManager;
import com.fh.service.system.menu.MenuManager;
import com.fh.entity.system.Menu;
import com.fh.entity.system.Role;
import com.fh.entity.system.User;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.IpUtil;
import com.fh.util.JsonUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.ResultUtils;
import com.fh.util.RightsHelper;
import com.fh.util.Tools;
/**
 * 总入口
 * @author fh QQ 3 1 3 5 9 6 7 9 0[青苔]
 * 修改日期：2018/8/2
 */
/**
 * @author Administrator
 *
 */
@Controller
public class LoginController extends BaseController {

	@Resource(name="userService")
	private UserManager userService;
	@Resource(name="menuService")
	private MenuManager menuService;
	@Resource(name="roleService")
	private RoleManager roleService;
	@Resource(name="buttonrightsService")
	private ButtonrightsManager buttonrightsService;
	@Resource(name="fhbuttonService")
	private FhbuttonManager fhbuttonService;
	@Resource(name="appuserService")
	private AppuserManager appuserService;
	@Resource(name="datajurService")
	private DatajurManager datajurService;
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	@Resource(name="loginimgService")
	private LogInImgManager loginimgService;
	
	@Resource(name="chartLogService")
	private ChartLogService chartLogService;
	
	
	/**访问登录页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login_toLogin")
	public ModelAndView toLogin()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = this.setLoginPd(pd);	//设置登录页面的配置参数
		mv.setViewName("system/index/login_web");
		mv.addObject("pd",pd);
		return mv;
	}
	
	@RequestMapping(value="/manage")
	public ModelAndView manage()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = this.setLoginPd(pd);	//设置登录页面的配置参数
		mv.setViewName("system/index/login_web");
		mv.addObject("pd",pd);
		return mv;
	}
	
	/**请求登录，验证用户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login_login" ,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object login()throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String errInfo = "";
		Session session = Jurisdiction.getSession();
		String USERNAME = pd.getString("loginname");
		String PASSWORD = pd.getString("password");
		String flag_personal = pd.getString("flag_personal");
		pd.put("USERNAME", USERNAME);
		//判断登录验证码
		String passwd = new SimpleHash("SHA-1", USERNAME, pd.getString("password")).toString();	//密码加密
		pd.put("PASSWORD", passwd);
		pd = userService.getUserByNameAndPwd(pd);	//根据用户名和密码去读取用户信息
		if(pd != null){
			this.removeSession(USERNAME);//请缓存
			pd.put("LAST_LOGIN",DateUtil.getTime().toString());
			userService.updateLastLogin(pd);
			User user = new User();
			System.out.println("pd===============================================================" + pd);
			user.setUSER_ID(pd.getString("USER_ID"));
			user.setUSERNAME(pd.getString("USERNAME"));
			user.setPASSWORD(pd.getString("PASSWORD"));
			user.setNAME(pd.getString("NAME"));
			user.setRIGHTS(pd.getString("RIGHTS"));
			user.setROLE_ID(pd.getString("ROLE_ID"));
			user.setLAST_LOGIN(pd.getString("LAST_LOGIN"));
			user.setIP(pd.getString("IP"));
			user.setSTATUS(pd.getString("STATUS"));
			session.setAttribute(Const.SESSION_USER, user);
			if("1".equals(flag_personal)){//个人登录先设置sessoin
				User userr = userService.getUserAndRoleById(user.getUSER_ID());				//通过用户ID读取用户信息和角色信息
				session.setAttribute(Const.SESSION_USERROL, userr);
				session.setAttribute(Const.SESSION_USERNAME, USERNAME);						//放入用户名到session
				session.setAttribute(Const.SESSION_U_NAME, user.getNAME());
				
				PageData chartPd = new PageData();
				String daynum = DateUtil.getSdfTimes();
				
				chartPd.put("id", this.get32UUID());
				chartPd.put("bill_day", daynum.substring(0, 8));
				chartPd.put("bill_hour", daynum.substring(8, 10));
				chartPd.put("user_id", this.getUser().getUSER_ID());
				chartPd.put("user", this.getUser().getOrg_id());
				chartPd.put("login_type","1");
				chartLogService.saveChartLogin(chartPd);//添加登录记录（统计报表使用）
			}
			//shiro加入身份验证
			Subject subject = SecurityUtils.getSubject(); 
		    UsernamePasswordToken token = new UsernamePasswordToken(USERNAME, PASSWORD); 
		    try { 
		        subject.login(token); 
		    } catch (AuthenticationException e) { 
		    	errInfo = "身份验证失败！";
		    }
		}else{
			errInfo = "usererror"; 				//用户名或密码有误
			logBefore(logger, USERNAME+"登录系统密码或用户名错误");
			FHLOG.save(USERNAME, "登录系统密码或用户名错误");
		}
		if(Tools.isEmpty(errInfo)){
			errInfo = "success";					//验证成功
			logBefore(logger, USERNAME+"登录系统");
			FHLOG.save(USERNAME, "登录系统");
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**访问系统首页
	 * @param changeMenu：切换菜单参数
	 * @return
	 */
	@RequestMapping(value="/main/index")
	public ModelAndView login_index(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);						//读取session中的用户信息(单独用户信息)
			if (user != null) {
				User userr = (User)session.getAttribute(Const.SESSION_USERROL);				//读取session中的用户信息(含角色信息)
				if(null == userr){
					user = userService.getUserAndRoleById(user.getUSER_ID());				//通过用户ID读取用户信息和角色信息
					session.setAttribute(Const.SESSION_USERROL, user);						//存入session	
				}else{
					user = userr;
				}
				String USERNAME = user.getUSERNAME();
				Role role = user.getRole();													//获取用户角色
				String roleRights = role!=null ? role.getRIGHTS() : "";						//角色权限(菜单权限)
				String ROLE_IDS = user.getROLE_IDS();
				session.setAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS, roleRights); 	//将角色权限存入session
				session.setAttribute(Const.SESSION_USERNAME, USERNAME);						//放入用户名到session
				session.setAttribute(Const.SESSION_U_NAME, user.getNAME());					//放入用户姓名到session
				this.setAttributeToAllDEPARTMENT_ID(session, USERNAME);						//把用户的组织机构权限放到session里面
				List<Menu> allmenuList = new ArrayList<Menu>();
				allmenuList = this.getAttributeMenu(session, USERNAME, roleRights, getArrayRoleRights(ROLE_IDS));//roleRights:主职权限   getArrayRoleRights(ROLE_IDS)：副职权利列表
				List<Menu> menuList = new ArrayList<Menu>();
				menuList = this.changeMenuF(allmenuList, session, USERNAME);	//切换菜单
				if(null == session.getAttribute(USERNAME + Const.SESSION_QX)){
					session.setAttribute(USERNAME + Const.SESSION_QX, this.getUQX(USERNAME));	//主职角色按钮权限放到session中
					session.setAttribute(USERNAME + Const.SESSION_QX2, this.getUQX2(USERNAME));	//副职角色按钮权限放到session中
					
				}
				this.getRemortIP(USERNAME);	//更新登录IP
				mv.setViewName("system/index/index");
				mv.addObject("user", user);
				mv.addObject("SKIN", null == session.getAttribute(Const.SKIN)?user.getSKIN():session.getAttribute(Const.SKIN)); 	//用户皮肤
				mv.addObject("menuList", menuList);
				
				PageData chartPd = new PageData();
				String daynum = DateUtil.getSdfTimes();
				
				chartPd.put("id", this.get32UUID());
				chartPd.put("bill_day", daynum.substring(0, 8));
				chartPd.put("bill_hour", daynum.substring(8, 10));
				chartPd.put("user_id", this.getUser().getUSER_ID());
				chartPd.put("org_id", this.getUser().getOrg_id());
				chartPd.put("login_type","1");
				chartLogService.saveChartLogin(chartPd);//添加登录记录（统计报表使用）
				
			}else {
				mv.setViewName("system/index/login_web");	//session失效后跳转登录页面
			}
		} catch(Exception e){
			mv.setViewName("system/index/login_web");
			logger.error(e.getMessage(), e);
		}
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
		mv.addObject("pd",pd);
		
		return mv;
	}
	
	@RequestMapping("/main/mainIndex")
	public String mainIndex(Model model) throws Exception{
		
		/*******************各园区企业登录实况begin***************************/
		List<String> getChartCountyLoginHour = chartLogService.getChartCountyLoginHour();
		List<String> getChartCountyLoginArea = chartLogService.getChartCountyLoginArea();
		List areaNameList = new ArrayList();
		List seriesList = new ArrayList();
		for (String str : getChartCountyLoginArea) {
			areaNameList.add(str.split(",")[0]);
			List orgCnt = chartLogService.getOrgCnt(str.split(",")[1]);
			String orgCntJson = JSONUtils.toJSONString(orgCnt);
			Map tmpMap = new HashMap();
			tmpMap.put("name", str.split(",")[0]);
			tmpMap.put("data", orgCntJson);
			seriesList.add(tmpMap);
		}
		model.addAttribute("areaNameList", JSONObject.toJSON(areaNameList));
		model.addAttribute("hourList", JSONObject.toJSON(getChartCountyLoginHour));
		model.addAttribute("seriesList", JSONObject.toJSON(seriesList));
		/*****************各园区企业登录实况end*****************************/
		
		/****************近一个月系统使用占比begin****************************/
		List<PageData> sysUseList = chartLogService.getSysUseList();
		List sysUseAreaList = new ArrayList();
		List sysUseSeriesList = new ArrayList();
		for (PageData pageData : sysUseList) {
			sysUseAreaList.add(pageData.getString("area_name"));
			Map tmpMap = new HashMap();
			tmpMap.put("name", pageData.getString("area_name"));
			tmpMap.put("value", pageData.getString("use_cnt"));
			sysUseSeriesList.add(tmpMap);
		}
		
		model.addAttribute("sysUseAreaList", JSONObject.toJSON(sysUseAreaList));
		model.addAttribute("sysUseSeriesList", JSONObject.toJSON(sysUseSeriesList));
		/****************近一个月系统使用占比end****************************/
		
		/*******************各园区活跃度begin***************************/
		List<String> getAreaStudyDay = chartLogService.getAreaStudyDay();
		List<String> getAreaStudyArea = chartLogService.getAreaStudyArea();
		List areaStudyAreaNameList = new ArrayList();
		List areaStudySeriesList = new ArrayList();
		for (String str : getAreaStudyArea) {
			areaStudyAreaNameList.add(str.split(",")[0]);
			List studyCnt = chartLogService.getStudyCnt(str.split(",")[1]);
			String studyCntJson = JSONUtils.toJSONString(studyCnt);
			Map tmpMap = new HashMap();
			tmpMap.put("name", str.split(",")[0]);
			tmpMap.put("data", studyCntJson);
			areaStudySeriesList.add(tmpMap);
		}
		model.addAttribute("getAreaStudyDay", JSONObject.toJSON(getAreaStudyDay));
		model.addAttribute("areaStudyAreaNameList", JSONObject.toJSON(areaStudyAreaNameList));
		model.addAttribute("areaStudySeriesList", JSONObject.toJSON(areaStudySeriesList));
		/*****************各园区活跃度end*****************************/
		
		/**********************试卷统计begin****************************/
		List<PageData> tableExamInfo = chartLogService.getTableExamInfo();
		for (PageData pageData : tableExamInfo) {
			if("0".equals(pageData.getString("exam_type"))){//模拟考试
				model.addAttribute("mnksMap", pageData);
			}
			if("1".equals(pageData.getString("exam_type"))){//正式考试
				model.addAttribute("zsksMap", pageData);
			}
		}
		/**********************试卷统计end*****************************/
		
		/**********************历史上的今天begin****************************/
		List<PageData> hisOfTodayList = chartLogService.getHisOfTodayList();
		model.addAttribute("hisOfTodayList", hisOfTodayList);
		/**********************历史上的今天end*****************************/
		
		/**********************考试统计（模拟考试、上次正式考试）begin****************************/
		PageData pd = new PageData();
		String county_id = this.getUser().getOrganization().getCounty_id();
		if(county_id == null || "".equals(county_id)){
			county_id = "320724";
		}
		pd.put("county_id", county_id);
		PageData ratePd = chartLogService.getMoNiExamRate(pd);
		PageData zhengshiRatePd = chartLogService.getZhengShiExamRate(pd);
		System.out.println("zhengshiRatePd::============================" + zhengshiRatePd);
		ratePd.putAll(zhengshiRatePd);
		System.out.println("ratePd::============================" + ratePd);
		model.addAttribute("ratePd", ratePd);
		/**********************考试统计（模拟考试、上次正式考试）end*****************************/
		
		return "system/index/mainIndex";
	}
	
	@RequestMapping("/main/getHotInfoList")
	@ResponseBody
	public Map getHotInfoList() throws Exception{
		List<PageData> hotInfoList = chartLogService.getHotInfoList();
		return ResultUtils.returnWebPage(hotInfoList);
	}
	
	/**获取副职角色权限List
	 * @param ROLE_IDS
	 * @return
	 * @throws Exception
	 */
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
	
	/**菜单缓存
	 * @param session
	 * @param USERNAME
	 * @param roleRights
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> getAttributeMenu(Session session, String USERNAME, String roleRights, List<String> arrayRoleRights) throws Exception{
		List<Menu> allmenuList = new ArrayList<Menu>();
		if(null == session.getAttribute(USERNAME + Const.SESSION_allmenuList)){	
			allmenuList = menuService.listAllMenuQx("0");							//获取所有菜单
			if(Tools.notEmpty(roleRights)){
				allmenuList = this.readMenu(allmenuList, roleRights, arrayRoleRights);				//根据角色权限获取本权限的菜单列表
			}
			session.setAttribute(USERNAME + Const.SESSION_allmenuList, allmenuList);//菜单权限放入session中
		}else{
			allmenuList = (List<Menu>)session.getAttribute(USERNAME + Const.SESSION_allmenuList);
		}
		return allmenuList;
	}
	
	/**根据角色权限获取本权限的菜单列表(递归处理)
	 * @param menuList：传入的总菜单
	 * @param roleRights：加密的权限字符串
	 * @return
	 */
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
	
	/**切换菜单处理
	 * @param allmenuList
	 * @param session
	 * @param USERNAME
	 * @param changeMenu
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> changeMenuF(List<Menu> allmenuList, Session session, String USERNAME){
		List<Menu> menuList = new ArrayList<Menu>();
		/** 菜单缓存为空 或者 传入的菜单类型和当前不一样的时候，条件成立，重新拆分菜单，把选择的菜单类型放入缓存 */
		if(null == session.getAttribute(USERNAME + Const.SESSION_menuList)){
			for(int i=0;i<allmenuList.size();i++){//拆分菜单
				Menu menu = allmenuList.get(i);
				menuList.add(menu);
			}
			session.removeAttribute(USERNAME + Const.SESSION_menuList);
			session.setAttribute(USERNAME + Const.SESSION_menuList, menuList);
			session.removeAttribute("changeMenu");
			session.setAttribute("changeMenu", "index");
		}else{
			menuList = (List<Menu>)session.getAttribute(USERNAME + Const.SESSION_menuList);
		}
		return menuList;
	}
	
	/**把用户的组织机构权限放到session里面
	 * @param session
	 * @param USERNAME
	 * @return
	 * @throws Exception 
	 */
	public void setAttributeToAllDEPARTMENT_ID(Session session, String USERNAME) throws Exception{
		String DEPARTMENT_IDS = "0",DEPARTMENT_ID = "0";
		if(!"admin".equals(USERNAME)){
			PageData pd = datajurService.getDEPARTMENT_IDS(USERNAME);
			DEPARTMENT_IDS = null == pd?"无权":pd.getString("DEPARTMENT_IDS");
			DEPARTMENT_ID = null == pd?"无权":pd.getString("DEPARTMENT_ID");
		}
		session.setAttribute(Const.DEPARTMENT_IDS, DEPARTMENT_IDS);	//把用户的组织机构权限集合放到session里面
		session.setAttribute(Const.DEPARTMENT_ID, DEPARTMENT_ID);	//把用户的最高组织机构权限放到session里面
	}
	
	/**
	 * 进入tab标签
	 * @return
	 */
	@RequestMapping(value="/tab")
	public String tab(){
		return "system/index/tab";
	}
	
	/**
	 * 进入首页后的默认页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/login_default")
	public ModelAndView defaultPage() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd.put("userCount", Integer.parseInt(userService.getUserCount("").get("userCount").toString())-1);				//系统用户数
		pd.put("appUserCount", Integer.parseInt(appuserService.getAppUserCount("").get("appUserCount").toString()));	//会员数
		mv.addObject("pd",pd);
		mv.setViewName("system/index/default");
		return mv;
	}
	
	/**
	 * 用户注销
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/logout")
	@ResponseBody
	public Map logout() throws Exception{
		String USERNAME = Jurisdiction.getUsername();	//当前登录的用户名
		logBefore(logger, USERNAME+"退出系统");
		FHLOG.save(USERNAME, "退出");
		this.removeSession(USERNAME);//请缓存
		//shiro销毁登录
		Subject subject = SecurityUtils.getSubject(); 
		subject.logout();
		return ResultUtils.returnOk();
	}
	
	/**
	 * 清理session
	 */
	public void removeSession(String USERNAME){
		Session session = Jurisdiction.getSession();	//以下清除session缓存
		session.removeAttribute(Const.SESSION_USER);
		session.removeAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS);
		session.removeAttribute(USERNAME + Const.SESSION_allmenuList);
		session.removeAttribute(USERNAME + Const.SESSION_menuList);
		session.removeAttribute(USERNAME + Const.SESSION_QX);
		session.removeAttribute(USERNAME + Const.SESSION_QX2);
		session.removeAttribute(Const.SESSION_userpds);
		session.removeAttribute(Const.SESSION_USERNAME);
		session.removeAttribute(Const.SESSION_U_NAME);
		session.removeAttribute(Const.SESSION_USERROL);
		session.removeAttribute("changeMenu");
		session.removeAttribute("DEPARTMENT_IDS");
		session.removeAttribute("DEPARTMENT_ID");
	}
	
	/**设置登录页面的配置参数
	 * @param pd
	 * @return
	 */
	public PageData setLoginPd(PageData pd){
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); 		//读取系统名称
		String strLOGINEDIT = Tools.readTxtFile(Const.LOGINEDIT);	//读取登录页面配置
		if(null != strLOGINEDIT && !"".equals(strLOGINEDIT)){
			String strLo[] = strLOGINEDIT.split(",fh,");
			if(strLo.length == 2){
				pd.put("isZhuce", strLo[0]);
				pd.put("isMusic", strLo[1]);
			}
		}
		try {
			List<PageData> listImg = loginimgService.listAll(pd);	//登录背景图片
			pd.put("listImg", listImg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}
	
	/**获取用户权限
	 * @param session
	 * @return
	 */
	public Map<String, String> getUQX(String USERNAME){
		PageData pd = new PageData();
		Map<String, String> map = new HashMap<String, String>();
		try {
			pd.put(Const.SESSION_USERNAME, USERNAME);
			
			PageData userpd = new PageData();
			userpd = userService.findByUsername(pd);	//通过用户名获取用户信息
			String ROLE_ID = userpd.get("ROLE_ID").toString();
			String ROLE_IDS = userpd.getString("ROLE_IDS");
			pd.put("ROLE_ID", ROLE_ID);					//获取角色ID
			pd = roleService.findObjectById(pd);									//获取角色信息														
			map.put("adds", pd.getString("ADD_QX"));	//增
			map.put("dels", pd.getString("DEL_QX"));	//删
			map.put("edits", pd.getString("EDIT_QX"));	//改
			map.put("chas", pd.getString("CHA_QX"));	//查
			List<PageData> buttonQXnamelist = new ArrayList<PageData>();
			if("admin".equals(USERNAME)){
				buttonQXnamelist = fhbuttonService.listAll(pd);						//admin用户拥有所有按钮权限
			}else{
				if(Tools.notEmpty(ROLE_IDS)){//(主副职角色综合按钮权限)
					ROLE_IDS = ROLE_IDS + ROLE_ID;
					String arryROLE_ID[] = ROLE_IDS.split(",fh,");
					buttonQXnamelist = buttonrightsService.listAllBrAndQxnameByZF(arryROLE_ID);
				}else{	//(主职角色按钮权限)
					buttonQXnamelist = buttonrightsService.listAllBrAndQxname(pd);	//此角色拥有的按钮权限标识列表
				}
			}
			for(int i=0;i<buttonQXnamelist.size();i++){
				map.put(buttonQXnamelist.get(i).getString("QX_NAME"),"1");			//按钮权限
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}	
		return map;
	}
	
	/**获取用户权限(处理副职角色)
	 * @param session
	 * @return
	 */
	public Map<String, List<String>> getUQX2(String USERNAME){
		PageData pd = new PageData();
		Map<String, List<String>> maps = new HashMap<String, List<String>>();
		try {
			pd.put(Const.SESSION_USERNAME, USERNAME);
			PageData userpd = new PageData();
			userpd = userService.findByUsername(pd);	//通过用户名获取用户信息
			String ROLE_IDS = userpd.getString("ROLE_IDS");
			if(Tools.notEmpty(ROLE_IDS)){
				String arryROLE_ID[] = ROLE_IDS.split(",fh,");
				PageData rolePd = new PageData();
				List<String> addsList = new ArrayList<String>();
				List<String> delsList = new ArrayList<String>();
				List<String> editsList = new ArrayList<String>();
				List<String> chasList = new ArrayList<String>();
				for(int i=0;i<arryROLE_ID.length;i++){
					rolePd.put("ROLE_ID", arryROLE_ID[i]);
					rolePd = roleService.findObjectById(rolePd);
					addsList.add(rolePd.getString("ADD_QX"));
					delsList.add(rolePd.getString("DEL_QX"));
					editsList.add(rolePd.getString("EDIT_QX"));
					chasList.add(rolePd.getString("CHA_QX"));
				}
				maps.put("addsList", addsList);		//增
				maps.put("delsList", delsList);		//删
				maps.put("editsList", editsList);	//改
				maps.put("chasList", chasList);		//查
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}	
		return maps;
	}
	
	/** 更新登录用户的IP
	 * @param USERNAME
	 * @throws Exception
	 */
	public void getRemortIP(String USERNAME) throws Exception {  
		PageData pd = new PageData();
		HttpServletRequest request = this.getRequest();
		String ip = IpUtil.getIpAddress(request);
		pd.put("USERNAME", USERNAME);
		pd.put("IP", ip);
		userService.saveIP(pd);
	} 
	
	/**
	 * 描述：基础信息
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-17
	 */
	public String baseinfo(Model model) throws Exception{
		User user = this.getUser();
		model.addAttribute("user", user);
		return "system/index/baseinfo";
	}
	
	/**
	 * 描述：修改密码
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-21
	 */
	@RequestMapping("/main/updatePassword")
	public String updatePassword(){
		
		return "system/index/updatePassword";
	}
	
	public static void main(String[] args) {
		String passwd = new SimpleHash("SHA-1", "admin", "1").toString();
		System.out.println(passwd);
		System.out.println("2019052318".substring(4, 6));
	}
	
}
