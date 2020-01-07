package com.fh.controller.mobile;

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

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.fh.controller.base.BaseController;
import com.fh.service.mobile.MobileExamService;
import com.fh.service.mobile.MobileLoginService;
import com.fh.service.personalmanage.MyExamService;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.util.AppUtil;
import com.fh.util.PageData;
import com.fh.util.ResultUtils;
import com.fh.util.Tools;



/**
 * 描述：APP端登陆接口
 * @author zhangcc
 * @Date : 2019-05-16
 */
@Controller
@RequestMapping(value="/mobile/app")
public class MobileExamController extends BaseController {
	
	@Autowired
	private MobileExamService mobileExamService;
	
	@Autowired
	private MyExamService myExamService;
	
	/**
	 * 描述：获取考试列表
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-17
	 */
	@RequestMapping("/getExamInfoList")
	@ResponseBody
	public Map getExamInfoList(){
		return mobileExamService.getExamInfoList();
	}
	
	/**
	 * 描述：获取考试详情
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-17
	 */
	@RequestMapping("/getExamInfoMap")
	public String getExamInfoMap(Model model) throws Exception{
		PageData examInfoMap = mobileExamService.getExamInfoMap();
		model.addAttribute("examInfoMap", examInfoMap);
		model.addAttribute("token", this.getUser().getToken());
		return "mobile/exam/examInfo";
	}
	
	@RequestMapping("/toTakeExam")
	public String toTakeExam(Model model) throws Exception{
		
		String id = "";//校验id
		String seq_num = "";//校验序号
		myExamService.insertMyMxam();
		PageData examQuestionMap = myExamService.getExamQuestionMap();
		model.addAttribute("examQuestionMap", examQuestionMap);
		model.addAttribute("token", this.getUser().getToken());
		return "mobile/exam/questionInfo";
	}
	
	@RequestMapping("/toTakeExamAjax")
	@CrossOrigin//解决跨域问题
	public String toTakeExamAjax(Model model) throws Exception{
		PageData examQuestionMap = myExamService.getExamQuestionMap();
		myExamService.saveExam();//保存总分
		model.addAttribute("examQuestionMap", examQuestionMap);
		return "mobile/exam/takeExamAjax";
	}
	
	/**
	 * 描述：提交试卷
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-20
	 */
	@RequestMapping("/saveSubmit")
	@ResponseBody
	public Map saveSubmit() throws Exception{
		myExamService.getExamQuestionMap();//保存答案
		myExamService.saveExam();//保存总分
		PageData pd = this.getPageData();
		String seq_num = pd.getString("seq_num");
		if("51".equals(seq_num)){//全部答完,获取积分
			String exam_type = mobileExamService.getExamTypeById(pd.getString("id"));
			PageData paramPd = new PageData();
			if("0".equals(exam_type)){//模拟考试
				System.out.println("========================模拟考试");
				paramPd.put("type", 5);
			}else if("1".equals(exam_type)){//正式考试
				System.out.println("========================正式考试");
				paramPd.put("type", 6);
			}
			paramPd.put("info_id", pd.getString("id"));
			mobileExamService.addMyExamPoint(paramPd);
		}
		return ResultUtils.returnOk();
	}
	
	/**
	 * 描述：考试完成
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-20
	 */
	@RequestMapping("/examFinish")
	public String examFinish(){
		return "mobile/exam/examFinish";
	}
	
	/**
	 * 描述：查看成绩
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-21
	 */
	@RequestMapping("/getExamResult")
	public String getExamResult(Model model) throws Exception{
		List<PageData> examResultList = mobileExamService.getExamResult();
		model.addAttribute("examResultList", examResultList);
		return "mobile/exam/getExamResult";
	}
	
	
	/**
	 * 描述：每日一答
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-20
	 */
	@RequestMapping("/dailyExam")
	public String dailyExam(Model model) throws Exception{
		PageData dailExamMap =  mobileExamService.dailyExam();
		model.addAttribute("dailyExamMap", dailExamMap);
		model.addAttribute("token", this.getUser().getToken());
		return "mobile/dailyexam/dailyExam";
	}
	
	/**
	 * 描述：每日一答点击下一题
	 * @param model
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-21
	 */
	@RequestMapping("/dailyExamAjax")
	public String dailyExamAjax(Model model) throws Exception{
		PageData dailExamMap = mobileExamService.dailyExam();
		model.addAttribute("dailyExamMap", dailExamMap);
		return "mobile/dailyexam/dailyExamAjax";
		
	}
	
	/**
	 * 描述：获取积分(加积分)
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-22
	 */
	@RequestMapping("/addMyExamPoint")
	@ResponseBody
	public Map addMyExamPoint(){
		PageData pd = this.getPageData();
		return mobileExamService.addMyExamPoint(pd);
	}
	
	/**
	 * 描述：查询积分
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-22
	 */
	@RequestMapping("/getMyPoint")
	@ResponseBody
	public Map getMyPoint(){
		return mobileExamService.getMyPoint();
	}
	
	/**
	 * 描述：获取政企快线页面
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-22
	 */
	@RequestMapping("/getZqkxRpt")
	public String getZqkxRpt(Model model) throws Exception{
		/*******************各园区企业登录实况begin***************************/
		List<String> getChartCountyLoginHour = mobileExamService.getChartCountyLoginHour();
		List<String> getChartCountyLoginArea = mobileExamService.getChartCountyLoginArea();
		List areaNameList = new ArrayList();
		List seriesList = new ArrayList();
		for (String str : getChartCountyLoginArea) {
			areaNameList.add(str.split(",")[0]);
			List orgCnt = mobileExamService.getOrgCnt(str.split(",")[1]);
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
		List<PageData> sysUseList = mobileExamService.getSysUseList();
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
		List<String> getAreaStudyDay = mobileExamService.getAreaStudyDay();
		List<String> getAreaStudyArea = mobileExamService.getAreaStudyArea();
		List areaStudyAreaNameList = new ArrayList();
		List areaStudySeriesList = new ArrayList();
		for (String str : getAreaStudyArea) {
			areaStudyAreaNameList.add(str.split(",")[0]);
			List studyCnt = mobileExamService.getStudyCnt(str.split(",")[1]);
			String studyCntJson = JSONUtils.toJSONString(studyCnt);
			Map tmpMap = new HashMap();
			tmpMap.put("name", str.split(",")[0]);
			tmpMap.put("data", studyCntJson);
			areaStudySeriesList.add(tmpMap);
		}
		model.addAttribute("getAreaStudyDay", JSONObject.toJSON(getAreaStudyDay));
		model.addAttribute("areaStudyAreaNameList", JSONObject.toJSON(areaStudyAreaNameList));
		model.addAttribute("areaStudySeriesList", JSONObject.toJSON(areaStudySeriesList));
		
		return "mobile/zqkxrpt/examFinish";
	}
	
}
	
 