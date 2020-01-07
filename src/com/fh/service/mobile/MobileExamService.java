package com.fh.service.mobile;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.service.base.BaseService;
import com.fh.service.chartlog.ChartLogService;
import com.fh.util.DateHelper;
import com.fh.util.DateUtil;
import com.fh.util.PHelper;
import com.fh.util.PageData;
import com.fh.util.ResultUtils;

/**
 * 描述：移动端登陆校验
 * @author zhangcc
 * @Date : 2019-05-16
 */
@Service
public class MobileExamService extends BaseService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Autowired
	private MobileInformationService mobileInformationService;
	
	@Autowired
	private ChartLogService chartLogService;
	
	public Map getExamInfoList(){
		PageData pd = this.getPageData();
		String exam_type = pd.getString("exam_type");//试卷类型
		if("".equals(exam_type)){
			return ResultUtils.returnError(12001, "无法获取exam_type！");
		}
		String page = pd.getString("page");
		String limit = pd.getString("limit");
		if(page.equals(""))
			return ResultUtils.returnError(12001, "无法获取page！");
		limit = limit.equals("") ? "15" : limit;
		pd.put("limit", limit);
		try {
			pd.put("user", this.getUser());
			PHelper.startPage(pd);
		 	List<PageData> examInfoList = (List<PageData>)dao.findForList("MoibleExamMapper.getExamInfoList", pd);
		 	return ResultUtils.returnOk(examInfoList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage()); 
		}
	}

	/**
	 * 描述：获取考试详情
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-17
	 */
	public PageData getExamInfoMap() throws Exception{
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		PageData result = (PageData)dao.findForObject("MoibleExamMapper.getExamInfoMap", pd);
		PageData score = (PageData)dao.findForObject("MoibleExamMapper.getMyExamInfoMap", pd);
		result.put("score", score == null ? "" : score.getString("score"));
		return result;
	}

	/**
	 * 描述：每日一答
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-20
	 */
	public PageData dailyExam() throws Exception{
		//判断今日是否有题目
		PageData pd = this.getPageData();
		if("".equals(pd.getString("seq_num"))){
			pd.put("seq_num", "1");
		}
		pd.put("user", this.getUser());
		pd.put("bill_day", DateHelper.getToday("yyyyMMdd"));
		PageData isHavePd = (PageData)dao.findForObject("MoibleExamMapper.isHaveDailyExam", pd);
		String isHave = isHavePd.getString("cnt");
		if("0".equals(isHave)){//当日未生成题目
			this.createDailExam(pd);
		}
		//根据题号获取每日一答题目
		PageData dailExamMap = (PageData)dao.findForObject("MoibleExamMapper.getDailExamMap", pd);
		if(dailExamMap != null && dailExamMap.size() > 0){
			//记录积分
			PageData paramPd = new PageData();
			paramPd.put("type", "4");
			paramPd.put("info_id", dailExamMap.getString("id"));
			mobileInformationService.addPoint(paramPd);
		}
		
		return dailExamMap;
	}

	/**
	 * 描述：生成每日一答题目
	 * @param pd
	 * @author yanbs
	 * @Date : 2019-05-20
	 */
	public void createDailExam(PageData pd) throws Exception{
		Integer danxuan = 2;//单选2题
		Integer duoxuan = 1;//多选1题
		Integer panduan = 2;//判断2题
		List<PageData> danxuanList = getQuestionList(danxuan, "100301");//获取单选集合
		List<PageData> duoxuanList = getQuestionList(duoxuan, "100302");//获取多选集合
		List<PageData> panduanList = getQuestionList(panduan, "100303");//获取判断集合
		
		List<PageData> questionList = new ArrayList<PageData>();
		questionList.addAll(danxuanList);
		questionList.addAll(duoxuanList);
		questionList.addAll(panduanList);
		int seq_num = 1;
		List<PageData> resultList = new ArrayList<PageData>();
		for (PageData pageData : questionList) {
			pageData.put("id", this.get32UUID());
			pageData.put("bill_day", pd.getString("bill_day"));
			pageData.put("user_id", this.getUser().getUSER_ID());
			pageData.put("seq_num", seq_num);
			resultList.add(pageData);
			seq_num++;
		}
		if(resultList.size() > 0){
			dao.batchSave("MoibleExamMapper.batchSaveDailyExam", resultList);
		}
	}
	
	/**
	 * 描述：获取题目
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-20
	 */
	public List<PageData> getQuestionList(Integer selectNum, String type) throws Exception{
		
		PageData orgInfo = (PageData)dao.findForObject("MoibleExamMapper.getOrgInfo", this.getUser().getOrg_id());
		PageData paramPd = new PageData();
		System.out.println("this.getUser().getIndustry()::=============================" + this.getUser().getIndustry());
		paramPd.put("type", type);
		if("-1".equals(this.getUser().getIndustry()) || "".equals(this.getUser().getIndustry())){//管理员每日一答标记
			paramPd.put("flag_daily_answer", "1");
		}
		paramPd.put("industry", this.getUser().getIndustry());
		paramPd.put("county_id", orgInfo.getString("county_id").equals("-1") ? "" : orgInfo.getString("county_id"));
		paramPd.put("area_id", orgInfo.getString("area_id").equals("-1") ? "" : orgInfo.getString("area_id"));
		
		List<PageData> commonList = (List<PageData>)dao.findForList("ExamInfoMapper.getCommonList", paramPd);
		List<PageData> randomList = getRandomList(commonList, selectNum);
		return randomList;
	}
	
	/**
	 * 描述：获取指定数量的随机集合
	 * @param list
	 * @param num
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-09
	 */
	public List<PageData> getRandomList(List<PageData> list, Integer num){
		if(list.size() < num){
			return list;
		}else{
			Random random = new Random();
			List<Integer> tempList = new ArrayList<Integer>();
			List<PageData> newList = new ArrayList<PageData>();
			int temp = 0;
			for(int i=0; i< num; i++) {
				temp = random.nextInt(list.size());
				if(!tempList.contains(temp)) {
					tempList.add(temp);
					newList.add(list.get(temp));
				}else {
					i--;
				}
			}
			return newList;
		}
		
		
	}

	/**
	 * 描述：查看成绩
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-21
	 */
	public List<PageData> getExamResult() throws Exception{
		PageData pd = this.getPageData();
		pd.put("user_id", this.getUser().getUSER_ID());
		return (List<PageData>)dao.findForList("MoibleExamMapper.getExamResult", pd);
	}
	
	/**
	 * 描述：积分管理
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-21
	 */
	public Map addMyExamPoint(PageData pd){
		try {
			String type = pd.getString("type");
			String info_id = pd.getString("info_id");
			if("".equals(type)){
				return ResultUtils.returnError(12001, "无法获取type！");
			}
			if("".equals(info_id)){
				return ResultUtils.returnError(12001, "无法获取info_id！");
			}
			
			pd.put("user_id", this.getUser().getUSER_ID());
			String beginDate = DateHelper.getBeginDayOfWeek("yyyyMMdd");
			String endDate = DateHelper.getEndDayOfWeek("yyyyMMdd");
			pd.put("beginDate", beginDate);
			pd.put("endDate", endDate);
			PageData myExamPoint = (PageData)dao.findForObject("MoibleExamMapper.getMyExamPoint", pd);
			String total_point = "0";
			String flag_exists = "0";
			if(myExamPoint != null && myExamPoint.size() > 0){
				total_point = myExamPoint.getString("total_point") == "" ? "0" : myExamPoint.getString("total_point");
				flag_exists = myExamPoint.getString("flag_exists") == "" ? "0" : myExamPoint.getString("flag_exists");
			}
			if("0".equals(flag_exists)){//该条信息未被记录
				if("5".equals(type) && Integer.parseInt(total_point) < 15){//该条信息未被记录且为模拟考试且本周积分未超过
					pd.put("user", this.getUser());
					pd.put("id", this.get32UUID());
					pd.put("point", 3);
					dao.update("MobileInformationController.savePonit", pd);
				}else if("6".equals(type) && Integer.parseInt(total_point) < 25){
					pd.put("user", this.getUser());
					pd.put("id", this.get32UUID());
					pd.put("point", 5);
					dao.update("MobileInformationController.savePonit", pd);
				}
			}
			return ResultUtils.returnOk();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}

	/**
	 * 描述：查询积分
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-22
	 */
	public Map getMyPoint(){
		PageData pd = new PageData();
		pd.put("user_id", this.getUser().getUSER_ID());
		pd.put("bill_day", DateHelper.getToday("yyyyMMdd"));
		pd.put("beginDate", DateHelper.getBeginDayOfWeek("yyyyMMdd"));
		pd.put("endDate", DateHelper.getEndDayOfWeek("yyyyMMdd"));
		try {
			List<PageData> resultList = (List<PageData>)dao.findForList("MoibleExamMapper.getMyPoint", pd);
			PageData myPointMap = new PageData();
			List<Map> myPointList = new ArrayList<Map>();
			String[] title = new String[]{"登陆", "阅读", "视频", "每日一答", "模拟考试", "正式考试"};
			String[] context = new String[]{"每日首次登陆", "每有效阅读一篇", "每有效观看一个", "每答一题试题", "每完成一次模拟考试", "每完成一次正式考试"};
			Integer[] max_point = new Integer[]{5, 5, 15, 5, 15, 25};
			for(int i = 0; i < title.length; i++){
				Map tmpMap = new HashMap();
				tmpMap.put("title", title[i]);
				tmpMap.put("context", context[i]);
				tmpMap.put("max_point", max_point[i]);
				tmpMap.put("have_point", 0);
				tmpMap.put("flag_finish", 0);
				myPointList.add(tmpMap);
			}
			if(resultList != null && resultList.size() > 0){
				for (PageData pageData : resultList) {
					String type = pageData.getString("type");
					String  totalPointStr = pageData.getString("total_point") == "" ? "0" : pageData.getString("total_point");
					int havePointNum = Integer.parseInt(totalPointStr);//已获积分
					if("-1".equals(type)){//总积分
						myPointMap.put("total", totalPointStr);
					}else if("1".equals(type)){//每日登陆
						myPointList.get(0).put("have_point", havePointNum);
						myPointList.get(0).put("flag_finish", havePointNum >= max_point[0] ? 1 : 0 );
					}else if("2".equals(type)){//浏览资讯
						myPointList.get(1).put("have_point", havePointNum);
						myPointList.get(1).put("flag_finish", havePointNum >= max_point[1] ? 1 : 0 );
					}else if("3".equals(type)){//观看视频
						myPointList.get(2).put("have_point", havePointNum);
						myPointList.get(2).put("flag_finish", havePointNum >= max_point[2] ? 1 : 0 );
					}else if("4".equals(type)){//每日一答
						myPointList.get(3).put("have_point", havePointNum);
						myPointList.get(3).put("flag_finish", havePointNum >= max_point[3] ? 1 : 0 );
					}else if("5".equals(type)){//模拟考试
						myPointList.get(4).put("have_point", havePointNum);
						myPointList.get(4).put("flag_finish", havePointNum >= max_point[4] ? 1 : 0 );
					}else if("6".equals(type)){//正式考试
						myPointList.get(5).put("have_point", havePointNum);
						myPointList.get(5).put("flag_finish", havePointNum >= max_point[5] ? 1 : 0 );
					}
				}
				myPointMap.put("myPointList", myPointList);
			}
			return ResultUtils.returnOk(myPointMap);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}

	public String getExamTypeById(String string) throws Exception{
		String exam_type = (String)dao.findForObject("MoibleExamMapper.getExamTypeById", string);
		return exam_type;
	}
	
	/**
	 * 描述：获取个县区下企业在各个时间点的登录情况
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-16
	 */
	public List<PageData> getChartCountyLoginList() throws Exception{
		PageData pd = new PageData();
		String role_lev = this.getUser().getOrg_lev();
		if(Integer.parseInt(role_lev) < 3){
			pd.put("county_id", "320724");//管理员默认看灌南县区
		}else{
			pd.put("county_id", this.getUser().getCounty_id());
		}
		pd.put("bill_day", DateUtil.getDays());
		return (List<PageData>)dao.findForList("ChartLogMapper.getChartCountyLoginList", pd);
	}
	
	public List<String> getChartCountyLoginHour() throws Exception{
		PageData pd = new PageData();
		String role_lev = this.getUser().getOrg_lev();
		if(Integer.parseInt(role_lev) < 3){
			pd.put("county_id", "320724");//管理员默认看灌南县区
		}else{
			pd.put("county_id", this.getUser().getCounty_id());
		}
		pd.put("bill_day", DateUtil.getDays());
		return (List<String>)dao.findForList("ChartLogMapper.getChartCountyLoginHour", pd);
	}

	public List<String> getChartCountyLoginArea() throws Exception{
		PageData pd = new PageData();
		String role_lev = this.getUser().getOrg_lev();
		if(Integer.parseInt(role_lev) < 3){
			pd.put("county_id", "320724");//管理员默认看灌南县区
		}else{
			pd.put("county_id", this.getUser().getCounty_id());
		}
		pd.put("bill_day", DateUtil.getDays());
		return (List<String>)dao.findForList("ChartLogMapper.getChartCountyLoginArea", pd);
	}

	public List getOrgCnt(String area_id) throws Exception{
		PageData pd = new PageData();
		String role_lev = this.getUser().getOrg_lev();
		if(Integer.parseInt(role_lev) < 3){
			pd.put("county_id", "320724");//管理员默认看灌南县区
		}else{
			pd.put("county_id", this.getUser().getCounty_id());
		}
		pd.put("bill_day", DateUtil.getDays());
		pd.put("area_id", area_id);
		return (List)dao.findForList("ChartLogMapper.getOrgCnt", pd);
	}
	
	public List<String> getAreaStudyDay() throws Exception{
		PageData pd = new PageData();
		String role_lev = this.getUser().getOrg_lev();
		if(Integer.parseInt(role_lev) < 3){
			pd.put("county_id", "320724");//管理员默认看灌南县区
		}else{
			pd.put("county_id", this.getUser().getCounty_id());
		}
		pd.put("bill_year", DateHelper.getToday("yyyy"));
		pd.put("bill_week_num", DateHelper.getWeekNumber_yesterday());
		return (List<String>)dao.findForList("MoibleExamMapper.getAreaStudyDay", pd);
	}

	public List<String> getAreaStudyArea() throws Exception{
		PageData pd = new PageData();
		String role_lev = this.getUser().getOrg_lev();
		if(Integer.parseInt(role_lev) < 3){
			pd.put("county_id", "320724");//管理员默认看灌南县区
		}else{
			pd.put("county_id", this.getUser().getCounty_id());
		}
		pd.put("bill_year", DateHelper.getToday("yyyy"));
		pd.put("bill_week_num", DateHelper.getWeekNumber_yesterday());
		return (List<String>)dao.findForList("MoibleExamMapper.getAreaStudyArea", pd);
	}

	public List getStudyCnt(String area_id) throws Exception{
		PageData pd = new PageData();
		String role_lev = this.getUser().getOrg_lev();
		if(Integer.parseInt(role_lev) < 3){
			pd.put("county_id", "320724");//管理员默认看灌南县区
		}else{
			pd.put("county_id", this.getUser().getCounty_id());
		}
		pd.put("bill_year", DateHelper.getToday("yyyy"));
		pd.put("bill_week_num", DateHelper.getWeekNumber_yesterday());
		pd.put("area_id", area_id);
		return (List)dao.findForList("MoibleExamMapper.getStudyCnt", pd);
	}
	
	/**
	 * 描述：获取系统使用情况
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-16
	 */
	public List<PageData> getSysUseList() throws Exception{
		PageData pd = new PageData();
		String role_lev = this.getUser().getOrg_lev();
		if(Integer.parseInt(role_lev) < 3){
			pd.put("county_id", "320724");//管理员默认看灌南县区
		}else{
			pd.put("county_id", this.getUser().getCounty_id());
		}
		pd.put("bill_day", DateHelper.getYesterday("yyyyMMdd"));
		return (List<PageData>)dao.findForList("ChartLogMapper.getSysUseList", pd);
	}
	
}
