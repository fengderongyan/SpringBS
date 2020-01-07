package com.fh.controller.web;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.fh.controller.base.BaseController;
import com.fh.service.chartlog.ChartLogService;
import com.fh.service.mobile.MobileExamService;
import com.fh.service.personalmanage.MyExamService;
import com.fh.service.web.WebService;
import com.fh.util.Jurisdiction;
import com.fh.util.PHelper;
import com.fh.util.PageData;
import com.github.pagehelper.PageInfo;



/**
 * 描述：web端
 * @author zhangcc
 * @Date : 2019-05-16
 */
@Controller
@RequestMapping(value="/web")
public class WebController extends BaseController {
	
	@Autowired
	private WebService webService;
	
	
	@Autowired
	private MyExamService myExamService;
	
	@Autowired
	private MobileExamService mobileExamService;
	
	@Autowired
	private ChartLogService chartLogService;
	
	/****************************************web端页面内容(不拦截)begin*************************************/
	@RequestMapping(value="/index")
	public String index(Model model) throws Exception{
		PageData pd = new PageData();
		
		//最新资讯
		pd.put("page", "1");
		pd.put("limit", "15");
		List<PageData> homeInfoList = webService.getHomeInfoList(pd);
		model.addAttribute("homeInfoList", homeInfoList);
		//事故案例
		pd.put("page", "1");
		pd.put("limit", "4");
		List<PageData> accidentList = webService.getAccidentList(pd);
		model.addAttribute("accidentList", accidentList);
		//专家介绍
		pd.put("page", "1");
		pd.put("limit", "4");
		List<PageData> expertInfoList = webService.getExpertInfoList(pd);
		model.addAttribute("expertInfoList", expertInfoList);
		//法律法规
		pd.put("page", "1");
		pd.put("limit", "8");
		List<PageData> lawInfoList = webService.getLawInfoList(pd);
		model.addAttribute("lawInfoList", lawInfoList);
		//安全常识
		pd.put("page", "1");
		pd.put("limit", "8");
		List<PageData> safeInfoList = webService.getSafeInfoList(pd);
		model.addAttribute("safeInfoList", safeInfoList);
		
		//教学课程
		pd.put("page", "1");
		pd.put("limit", "7");
		List<PageData> lessonList = webService.getLessonList(pd);
		model.addAttribute("lessonList", lessonList);
		
		return "web/index";
	}
	
	/**
	 * 描述：获取资讯页面
	 * @param model
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-27
	 */
	@RequestMapping(value="/index/homeInfo")
	public String homeInfo(Model model) throws Exception{
		PageData pd = this.getPageData();
		List<PageData> infoList = webService.getHomeInfoList(pd);
		PageInfo pi = new PageInfo(infoList);
		model.addAttribute("total", pi.getTotal());
		return "web/homeinfolist";
	}
	
	/**
	 * 描述：获取资讯列表
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-27
	 */
	@RequestMapping(value="/index/getHomeInfoList")
	@ResponseBody
	public List getHomeInfoList() throws Exception{
		PageData pd = this.getPageData();
		List<PageData> infoList = webService.getHomeInfoList(pd);
		return infoList;
	}
	
	@RequestMapping(value="/index/homeInfoDetail")
	public String homeInfoDetail(Model model) throws Exception{
		PageData infodetail = webService.getHomeInfoDetail();
		model.addAttribute("infodetail",infodetail);
		return "web/homeinfodetail";
	}
	
	@RequestMapping(value="/index/accident")
	public String accident(Model model) throws Exception{
		PageData pd = this.getPageData();
		List<PageData> infoList = webService.getAccidentList(pd);
		PageInfo pi = new PageInfo(infoList);
		model.addAttribute("total", pi.getTotal());
		return "web/accidentlist";
	}
	
	@RequestMapping(value="/index/getAccidentList")
	@ResponseBody
	public List getAccidentList() throws Exception{
		PageData pd = this.getPageData();
		List<PageData> infoList = webService.getAccidentList(pd);
		return infoList;
	}
	
	@RequestMapping(value="/index/accidentDetail")
	public String accidentDetail(Model model) throws Exception{
		PageData infodetail = webService.getAccidentDetail();
		model.addAttribute("infodetail",infodetail);
		return "web/accidentdetail";
	}
	
	@RequestMapping(value="/index/expertInfo")
	public String expertInfo(Model model) throws Exception{
		PageData pd = this.getPageData();
		pd.put("flag_all", "1");
		List<PageData> infoList = webService.getExpertInfoList(pd);
		model.addAttribute("infoList", infoList);
		return "web/expertinfolist";
	}
	

	@RequestMapping(value="/index/expertInfoDetail")
	public String expertInfoDetail(Model model) throws Exception{
		PageData infodetail = webService.getExpertInfoDetail();
		List<PageData> videoList = webService.getExpertVideoList(infodetail);
		model.addAttribute("infodetail",infodetail);
		model.addAttribute("videoList",videoList);
		return "web/expertinfodetail";
	}
	
	
	@RequestMapping(value="/index/lawInfo")
	public String lawInfo(Model model) throws Exception{
		PageData pd = this.getPageData();
		List<PageData> infoList = webService.getLawInfoList(pd);
		PageInfo pi = new PageInfo(infoList);
		model.addAttribute("total", pi.getTotal());
		return "web/lawinfolist";
	}
	
	@RequestMapping(value="/index/getLawInfoList")
	@ResponseBody
	public List getLawInfoList() throws Exception{
		PageData pd = this.getPageData();
		List<PageData> infoList = webService.getLawInfoList(pd);
		return infoList;
	}
	
	@RequestMapping(value="/index/getLawInfoDetail")
	public String getLawInfoDetail(Model model) throws Exception{
		PageData infodetail = webService.getLawInfoDetail();
		model.addAttribute("infodetail",infodetail);
		return "web/lawinfodetail";
	}
	
	@RequestMapping(value="/index/safeInfo")
	public String safeInfo(Model model) throws Exception{
		PageData pd = this.getPageData();
		List<PageData> infoList = webService.getSafeInfoList(pd);
		PageInfo pi = new PageInfo(infoList);
		model.addAttribute("total", pi.getTotal());
		return "web/safeinfolist";
	}
	
	@RequestMapping(value="/index/getSafeInfoList")
	@ResponseBody
	public List getSafeInfoList() throws Exception{
		PageData pd = this.getPageData();
		List<PageData> infoList = webService.getSafeInfoList(pd);
		return infoList;
	}
	
	@RequestMapping(value="/index/getSafeInfoDetail")
	public String getSafeInfoDetail(Model model) throws Exception{
		PageData infodetail = webService.getSafeInfoDetail();
		model.addAttribute("infodetail",infodetail);
		return "web/safeinfodetail";
	}
	
	@RequestMapping(value="/index/lesson")
	public String lesson(Model model) throws Exception{
		PageData pd = this.getPageData();
		List<PageData> infoList = webService.getLessonList(pd);
		PageInfo pi = new PageInfo(infoList);
		model.addAttribute("total", pi.getTotal());
		return "web/lessonlist";
	}
	
	@RequestMapping(value="/index/lessonDetail")
	public String lessonDetail(Model model) throws Exception{
		PageData infodetail = webService.getLessonDetail();
		model.addAttribute("infodetail",infodetail);
		return "web/lessondetail";
	}
	
	@RequestMapping(value="/index/getLessonList")
	@ResponseBody
	public List getLessonList() throws Exception{
		PageData pd = this.getPageData();
		List<PageData> infoList = webService.getLessonList(pd);
		return infoList;
	}
	
	@RequestMapping(value="/index/getZqkxRpt")
	public String getZqkxRpt(Model model) throws Exception{

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
		return  "web/zqkxRpt";
	}
	
	@RequestMapping(value="/index/getWeather",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getWeather() throws Exception{
		String weatherStr = webService.getWeather();
		return weatherStr;
	}
	
	/**
	 * 描述：搜索功能
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-30
	 */
	@RequestMapping(value="/index/search")
	public String search(){
		return "web/search";
	}
	
	@RequestMapping(value="/index/searchInfo")
	@ResponseBody
	public Map searchInfo() throws Exception{
		PageData pd =this.getPageData();
		if("".equals(pd.getString("page"))){
			pd.put("page", "1");
		}
		if("".equals(pd.getString("limit"))){
			pd.put("limit", "15");
		}
		List<PageData> result = webService.searchInfo(pd);
		PageInfo pi = new PageInfo(result);
		Map resultMap = new HashMap();
		resultMap.put("count", pi.getTotal());
		resultMap.put("list", pi.getList());
		return resultMap;
	}
	/****************************************web端页面内容(不拦截)end*************************************/
	
	/****************************************个人中心begin******************************************/
	/**
	 * 描述：个人中心页面
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-24
	 */
	@RequestMapping(value="/personal")
	public String personal(){
		
		return "web/person_center";
	}
	
	/**
	 * 描述：个人信息
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-24
	 */
	@RequestMapping(value="/personal/info")
	public String info(){
		
		return "web/personitem/info";
	}
	
	/**
	 * 描述：考试页面
	 * @param model
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-24
	 */
	@RequestMapping(value="/personal/exam")
	public String exam(Model model) throws Exception{
		List<PageData> myExamList = myExamService.getMyExamList();
		PageInfo pi = new PageInfo(myExamList);
		model.addAttribute("total", pi.getTotal());
		return "web/personitem/exam";
	}
	
	/**
	 * 描述：考试列表
	 * @param model
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-24
	 */
	@RequestMapping(value="/personal/examList")
	@ResponseBody
	public List<PageData> examList(Model model) throws Exception{
		List<PageData> myExamList = myExamService.getMyExamList();
		return myExamList;
	}
	
	@RequestMapping(value="/personal/integral")
	public String getMyPoint(Model model){
		Map map = mobileExamService.getMyPoint();
		Map data = (Map)map.get("data");
		model.addAttribute("total", (String)data.get("total"));
		model.addAttribute("myPointList", (List)data.get("myPointList"));
		return "web/personitem/integral";
	}
	
	/**
	 * 描述：我的课程页面
	 * @param model
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-27
	 */
	@RequestMapping(value="/personal/curse")
	public String curse(Model model) throws Exception{
		List<PageData> curseList = webService.getCurseList();
		PageInfo pi = new PageInfo(curseList);
		model.addAttribute("total", pi.getTotal());
		return "web/personitem/curse";
	}
	
	/**
	 * 描述：我的课程列表
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-27
	 */
	@RequestMapping(value="/personal/curseList")
	@ResponseBody
	public List<PageData> curseList() throws Exception{
		List<PageData> curseList = webService.getCurseList();
		return curseList;
	}
	
	/**
	 * 描述：课程详情
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-27
	 */
	@RequestMapping(value="/personal/curseDetail")
	public String curseDetail(Model model) throws Exception{
		PageData curseMap = webService.getCurseDetail();
		List<PageData> curseList = webService.getCurseAllList();
		model.addAttribute("curseMap", curseMap);
		model.addAttribute("curseList", curseList);
		return "web/videos";
	}
	
	/**
	 * 描述：意见反馈
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-27
	 */
	@RequestMapping(value="/personal/suggestion")
	public String suggestion(){
		return "web/personitem/suggestion";
	}
	
	/**
	 * 描述：保存意见反馈
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-27
	 */
	@RequestMapping(value="/personal/subProposal")
	@ResponseBody
	public Map subProposal(){
		return webService.subProposal();
	}
	
	/**
	 * 描述：荣誉证书
	 * @param model
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-27
	 */
	@RequestMapping(value="/personal/honor")
	public String honor(Model model) throws Exception{
		PageData honorMap = webService.getHonor();
		model.addAttribute("honorMap", honorMap);
		return "web/personitem/honor";
	}
	
	/**
	 * 描述：系统设置
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-27
	 */
	@RequestMapping(value="/personal/setting")
	public String setting(){
		return "web/personitem/setting";
	}
	/****************************************个人中心end******************************************/
	
	/**
	 * 描述：获取积分
	 * @author yanbs
	 * @Date : 2019-05-28
	 */
	@RequestMapping(value="/index/getMyPoint")
	public void getMyPoint() throws Exception{//获取积分
		System.out.println("进入。。。。。。。。。。。。。。。。。。。。。。");
		PageData pd = this.getPageData();
		webService.getMyPoint(pd);
	}
}
	
 