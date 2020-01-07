package com.fh.service.chartlog;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.service.base.BaseService;
import com.fh.util.DateHelper;
import com.fh.util.DateUtil;
import com.fh.util.PageData;


/**
 * 描述：首页统计
 * @author yanbs
 * @Date : 2019-05-08
 */
@Service
public class ChartLogService extends BaseService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 描述：判断今日是否登录 过
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-15
	 */
	public boolean isLoginToday() throws Exception{
		PageData pd = new PageData();
		pd.put("user", this.getUser());
		PageData isLoginPd = (PageData)dao.findForObject("ChartLogMapper.isLoginToday", pd);
		String isLogin = isLoginPd.getString("cnt");
		if("0".equals(isLogin)){//未登录过
			return false;
		}
		return true;
	}
	
	public boolean isGetLoingPoint() throws Exception{
		PageData pd = new PageData();
		pd.put("user_id", this.getUser().getUSER_ID());
		PageData isGetLoingPointPd = (PageData)dao.findForObject("ChartLogMapper.isGetLoingPoint", pd);
		String isGetLoingPoint = isGetLoingPointPd.getString("cnt");
		if("0".equals(isGetLoingPoint)){//未获得积分
			return false;
		}
		return true;
	}
	
	/**
	 * 描述：当日未登录过，添加登录记录
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-15
	 */
	public void saveChartLogin(PageData pd) throws Exception{
		if(!this.isLoginToday()){//添加登录记录
			dao.save("ChartLogMapper.saveChartLogin", pd);
		}
		if(!this.isGetLoingPoint()){//当日未获得积分
			PageData paramPd = new PageData();
			paramPd.put("id", this.get32UUID());
			paramPd.put("user_id", this.getUser().getUSER_ID());
			paramPd.put("org_id", this.getUser().getOrg_id());
			paramPd.put("type", "1");
			paramPd.put("info_id", "login");
			paramPd.put("point", "5");
			dao.save("ChartLogMapper.saveLoingPoint",  paramPd);
		}
	}
	
	/**
	 * 描述：获取个县区下企业在各个时间点的登录情况
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-16
	 */
	public List<PageData> getChartCountyLoginList() throws Exception{
		PageData pd = new PageData();
		String role_lev = this.getUser().getRole().getROLE_LEV();
		if(Integer.parseInt(role_lev) < 3){
			pd.put("county_id", "320724");//管理员默认看灌南县区
		}else{
			pd.put("county_id", this.getUser().getOrganization().getCounty_id());
		}
		pd.put("bill_day", DateUtil.getDays());
		return (List<PageData>)dao.findForList("ChartLogMapper.getChartCountyLoginList", pd);
	}
	
	public List<String> getChartCountyLoginHour() throws Exception{
		PageData pd = new PageData();
		String role_lev = this.getUser().getRole().getROLE_LEV();
		if(Integer.parseInt(role_lev) < 3){
			pd.put("county_id", "320724");//管理员默认看灌南县区
		}else{
			pd.put("county_id", this.getUser().getOrganization().getCounty_id());
		}
		pd.put("bill_day", DateUtil.getDays());
		return (List<String>)dao.findForList("ChartLogMapper.getChartCountyLoginHour", pd);
	}

	public List<String> getChartCountyLoginArea() throws Exception{
		PageData pd = new PageData();
		String role_lev = this.getUser().getRole().getROLE_LEV();
		if(Integer.parseInt(role_lev) < 3){
			pd.put("county_id", "320724");//管理员默认看灌南县区
		}else{
			pd.put("county_id", this.getUser().getOrganization().getCounty_id());
		}
		pd.put("bill_day", DateUtil.getDays());
		return (List<String>)dao.findForList("ChartLogMapper.getChartCountyLoginArea", pd);
	}

	public List getOrgCnt(String area_id) throws Exception{
		PageData pd = new PageData();
		String role_lev = this.getUser().getRole().getROLE_LEV();
		if(Integer.parseInt(role_lev) < 3){
			pd.put("county_id", "320724");//管理员默认看灌南县区
		}else{
			pd.put("county_id", this.getUser().getOrganization().getCounty_id());
		}
		pd.put("bill_day", DateUtil.getDays());
		pd.put("area_id", area_id);
		return (List)dao.findForList("ChartLogMapper.getOrgCnt", pd);
	}
	
	public List<String> getAreaStudyDay() throws Exception{
		PageData pd = new PageData();
		String role_lev = this.getUser().getRole().getROLE_LEV();
		if(Integer.parseInt(role_lev) < 3){
			pd.put("county_id", "320724");//管理员默认看灌南县区
		}else{
			pd.put("county_id", this.getUser().getOrganization().getCounty_id());
		}
		pd.put("bill_year", DateHelper.getToday("yyyy"));
		pd.put("bill_week_num", DateHelper.getWeekNumber_yesterday());//获取昨天为本年的第几周
		return (List<String>)dao.findForList("ChartLogMapper.getAreaStudyDay", pd);
	}

	public List<String> getAreaStudyArea() throws Exception{
		PageData pd = new PageData();
		String role_lev = this.getUser().getRole().getROLE_LEV();
		if(Integer.parseInt(role_lev) < 3){
			pd.put("county_id", "320724");//管理员默认看灌南县区
		}else{
			pd.put("county_id", this.getUser().getOrganization().getCounty_id());
		}
		pd.put("bill_year", DateHelper.getToday("yyyy"));
		pd.put("bill_week_num", DateHelper.getWeekNumber_yesterday());
		return (List<String>)dao.findForList("ChartLogMapper.getAreaStudyArea", pd);
	}

	public List getStudyCnt(String area_id) throws Exception{
		PageData pd = new PageData();
		String role_lev = this.getUser().getRole().getROLE_LEV();
		if(Integer.parseInt(role_lev) < 3){
			pd.put("county_id", "320724");//管理员默认看灌南县区
		}else{
			pd.put("county_id", this.getUser().getOrganization().getCounty_id());
		}
		pd.put("bill_year", DateHelper.getToday("yyyy"));
		pd.put("area_id", area_id);
		pd.put("bill_week_num", DateHelper.getWeekNumber_yesterday());
		return (List)dao.findForList("ChartLogMapper.getStudyCnt", pd);
	}
	
	/**
	 * 描述：获取系统使用情况
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-16
	 */
	public List<PageData> getSysUseList() throws Exception{
		PageData pd = new PageData();
		String role_lev = this.getUser().getRole().getROLE_LEV();
		if(Integer.parseInt(role_lev) < 3){
			pd.put("county_id", "320724");//管理员默认看灌南县区
		}else{
			pd.put("county_id", this.getUser().getOrganization().getCounty_id());
		}
		pd.put("bill_day", DateHelper.getYesterday("yyyyMMdd"));
		return (List<PageData>)dao.findForList("ChartLogMapper.getSysUseList", pd);
	}
	
	/**
	 * 描述：获取热门资讯
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-20
	 */
	public List<PageData> getHotInfoList() throws Exception{
		return (List<PageData>)dao.findForList("ChartLogMapper.getHotInfoList", null);
	}
	
	/**
	 * 描述：统计试卷考试情况
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-21
	 */
	public List<PageData> getTableExamInfo() throws Exception{
		PageData pd = new PageData();
		pd.put("user", this.getUser());
		return (List<PageData>)dao.findForList("ChartLogMapper.getTableExamInfo", pd);
	}

	public List<PageData> getHisOfTodayList() throws Exception{
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("TimingOperationMapper.getHisOfTodayList", null);
	}
	
	public PageData getMoNiExamRate(PageData pd) throws Exception{
		return (PageData)dao.findForObject("ChartLogMapper.getMoNiExamRate", pd);
	}
	
	public PageData getZhengShiExamRate(PageData pd) throws Exception{
		return (PageData)dao.findForObject("ChartLogMapper.getZhengShiExamRate", pd);
	}

}
