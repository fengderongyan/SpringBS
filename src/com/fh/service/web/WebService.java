package com.fh.service.web;




import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.User;
import com.fh.service.base.BaseService;
import com.fh.util.DateHelper;
import com.fh.util.PHelper;
import com.fh.util.PageData;
import com.fh.util.ResultUtils;

/**
 * 描述：移动端登陆校验
 * @author zhangcc
 * @Date : 2019-05-16
 */
@Service
public class WebService extends BaseService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 描述：获取课程列表
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-27
	 */
	public List<PageData> getCurseList() throws Exception{
		PageData pd = this.getPageData();
		pd.put("org_id", this.getUser().getOrg_id());
		pd.put("org_lev", this.getUser().getOrganization().getOrg_lev());
		pd.put("industry", this.getUser().getOrganization().getIndustry());
		String flag_all = pd.getString("flag_all");
		if(!"1".equals(flag_all)){
			PHelper.startPage(pd);
		}
		return (List<PageData>)dao.findForList("WebMapper.getCurseList", pd);
	}

	/**
	 * 描述：获取课程详情
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-27
	 */
	public PageData getCurseDetail() throws Exception{
		PageData pd = this.getPageData();
		return (PageData)dao.findForObject("WebMapper.getCurseDetail", pd);
	}

	/**
	 * 描述：获取所有课程
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-27
	 */
	public List<PageData> getCurseAllList() throws Exception{
		PageData pd = new PageData();
		pd.put("org_id", this.getUser().getOrg_id());
		pd.put("org_lev", this.getUser().getOrganization().getOrg_lev());
		pd.put("industry", this.getUser().getOrganization().getIndustry());
		return (List<PageData>)dao.findForList("WebMapper.getCurseList", pd);
	}
	
	/**
	 * 描述：意见反馈
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-22
	 */
	public Map subProposal() {
		try {
			PageData pd = this.getPageData();
			pd.put("user_id", this.getUser().getUSER_ID());
			dao.save("WebMapper.subProposal", pd);
			return ResultUtils.returnOk();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}

	/**
	 * 描述：获取荣誉证书
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-27
	 */
	public PageData getHonor() throws Exception{
		PageData pd = new PageData();
		pd.put("user_id", this.getUser().getUSER_ID());
		return (PageData)dao.findForObject("WebMapper.getHonor", pd);
	}

	public List<PageData> getHomeInfoList(PageData pd) throws Exception{
		PHelper.startPage(pd);
		return (List<PageData>)dao.findForList("WebMapper.getHomeInfoList", pd);
	}

	public PageData getHomeInfoDetail() throws Exception{
		PageData pd = this.getPageData();
		dao.update("WebMapper.updateHomeInfoCnt", pd);
		return (PageData)dao.findForObject("WebMapper.getHomeInfoDetail", pd);
	}

	public List<PageData> getAccidentList(PageData pd) throws Exception{
		PHelper.startPage(pd);
		return (List<PageData>)dao.findForList("WebMapper.getAccidentList", pd);
	}

	public PageData getAccidentDetail() throws Exception{
		PageData pd = this.getPageData();
		dao.update("WebMapper.updateAccidentCnt", pd);
		return (PageData)dao.findForObject("WebMapper.getAccidentDetail", pd);
	}

	public List<PageData> getExpertInfoList(PageData pd) throws Exception{
		if(!"1".equals(pd.getString("flag_all"))){
			PHelper.startPage(pd);
		}
		return (List<PageData>)dao.findForList("WebMapper.getExpertInfoList", pd);
	}

	public PageData getExpertInfoDetail() throws Exception{
		PageData pd = this.getPageData();
		return (PageData)dao.findForObject("WebMapper.getExpertInfoDetail", pd);
	}

	public List<PageData> getExpertVideoList(PageData infodetail) throws Exception{
		return (List<PageData>)dao.findForList("WebMapper.getExpertVideoList", infodetail);
	}

	public List<PageData> getLawInfoList(PageData pd) throws Exception{
		PHelper.startPage(pd);
		return (List<PageData>)dao.findForList("WebMapper.getLawInfoList", pd);
	}

	public PageData getLawInfoDetail() throws Exception{
		PageData pd = this.getPageData();
		dao.update("WebMapper.updateLawInfoCnt", pd);
		return (PageData)dao.findForObject("WebMapper.getLawInfoDetail", pd);
	}

	public List<PageData> getSafeInfoList(PageData pd) throws Exception{
		PHelper.startPage(pd);
		return (List<PageData>)dao.findForList("WebMapper.getSafeInfoList", pd);
	}

	public PageData getSafeInfoDetail() throws Exception{
		PageData pd = this.getPageData();
		dao.update("WebMapper.updateSafeInfoCnt", pd);
		return (PageData)dao.findForObject("WebMapper.getSafeInfoDetail", pd);
	}
	
	/**
	 * 描述：获取教学课程列表
	 * @param pd
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-28
	 */
	public List<PageData> getLessonList(PageData pd) throws Exception{
		pd.put("user", this.getUser());
		PHelper.startPage(pd);
		return (List<PageData>)dao.findForList("WebMapper.getLessonList", pd);
	}
	
	/**
	 * 描述：课程详情
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-28
	 */
	public PageData getLessonDetail() throws Exception{
		PageData pd = this.getPageData();
		return (PageData)dao.findForObject("WebMapper.getLessonDetail", pd);
	}

	/**
	 * 描述：获取积分
	 * @author yanbs
	 * @Date : 2019-05-28
	 */
	public void getMyPoint(PageData pd) throws Exception{//type:2、观看资讯 3、观看视频
		User user = this.getUser();
		if(user != null){//已登录，获取积分
			String type = pd.getString("type");
			pd.put("user_id", this.getUser().getUSER_ID());
			pd.put("bill_day", DateHelper.getToday("yyyyMMdd"));
			PageData myPoint = (PageData)dao.findForObject("WebMapper.getMyPoint", pd);
			String total_point = "0";
			String flag_exists = "0";
			if(myPoint != null && myPoint.size() > 0){
				total_point = myPoint.getString("total_point") == "" ? "0" : myPoint.getString("total_point");
				flag_exists = myPoint.getString("flag_exists") == "" ? "0" : myPoint.getString("flag_exists");
			}
			if("0".equals(flag_exists)){//该条信息未被记录
				if("2".equals(type) && Integer.parseInt(total_point) < 5){//阅览资讯
					pd.put("id", this.get32UUID());
					pd.put("user_id", this.getUser().getUSER_ID());
					pd.put("org_id", this.getUser().getOrg_id());
					pd.put("point", 1);
					dao.update("WebMapper.savePonit", pd);
				}else if("3".equals(type) && Integer.parseInt(total_point) < 15){//观看视频
					pd.put("id", this.get32UUID());
					pd.put("user_id", this.getUser().getUSER_ID());
					pd.put("org_id", this.getUser().getOrg_id());
					pd.put("point", 3);
					dao.update("WebMapper.savePonit", pd);
				}
			}
		}
	}
	
	public String getExamTypeById(String string) throws Exception{
		String exam_type = (String)dao.findForObject("WebMapper.getExamTypeById", string);
		return exam_type;
	}
	
	public Map addMyExamPoint(PageData pd){
		try {
			String type = pd.getString("type");
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
					dao.update("WebMapper.savePonit", pd);
				}else if("6".equals(type) && Integer.parseInt(total_point) < 25){
					pd.put("user", this.getUser());
					pd.put("id", this.get32UUID());
					pd.put("point", 5);
					dao.update("WebMapper.savePonit", pd);
				}
			}
			return ResultUtils.returnOk();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}

	public String getWeather() throws Exception{
		String weatherStr = (String)dao.findForObject("TimingOperationMapper.getWeather", null);
		return weatherStr;
	}

	
	/**
	 * 描述：搜索内容
	 * @param pd
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-30
	 */
	public List<PageData> searchInfo(PageData pd) throws Exception{
		PHelper.startPage(pd);
		return (List<PageData>)dao.findForList("MobileInformationController.getSearchInfoList", pd);
	}
	
}
