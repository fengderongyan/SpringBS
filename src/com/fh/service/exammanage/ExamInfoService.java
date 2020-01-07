package com.fh.service.exammanage;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fh.dao.DaoSupport;
import com.fh.service.base.BaseService;
import com.fh.util.BatchSql;
import com.fh.util.DateHelper;
import com.fh.util.PHelper;
import com.fh.util.PageData;
import com.fh.util.ResultUtils;
import com.fh.util.StringUtil;
import com.fh.util.excel.FileHelper;
import com.fh.util.excel.HssfHelper;


/**
 * 描述：试卷管理
 * @author yanbs
 * @Date : 2019-05-08
 */
@Service
public class ExamInfoService extends BaseService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 描述：获取题库列表
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-07
	 */
	public List<PageData> getExamList() throws Exception{
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		PHelper.startPage(pd);
		return (List<PageData>)dao.findForList("ExamInfoMapper.getExamList", pd);
	}
	
	/**
	 * 描述：获取行业类别
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-07
	 */
	public List<PageData> getIndustryList() throws Exception{
		PageData pd = new PageData();
		pd.put("data_type_code", "1001");
		return (List<PageData>)dao.findForList("DdwMapper.getItemListByType", pd);
	}
	
	/**
	 * 描述：获取县区列表
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-07
	 */
	public List<PageData> getCountyList() throws Exception{
		PageData pd = this.getPageData();
		pd.put("org_lev", "3");
		return (List<PageData>) dao.findForList("ExamInfoMapper.getOrgListByOrgLev", pd);
	}

	/**
	 * 描述：获取园区列表
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-07
	 */
	public List<PageData> getAreaList() throws Exception{
		PageData pd = this.getPageData();
		pd.put("org_lev", "4");
		return (List<PageData>) dao.findForList("ExamInfoMapper.getOrgListByOrgLev", pd);
	}
	
	/**
	 * 描述：获取企业列表
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-07
	 */
	public List<PageData> getOrgList() throws Exception{
		PageData pd = this.getPageData();
		pd.put("org_lev", "5");
		return (List<PageData>) dao.findForList("ExamInfoMapper.getOrgListByOrgLev", pd);
	}
	
	/**
	 * 描述：获取企业定制信息
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-09
	 */
	public Map getDzInfo() throws Exception{
		PageData pd = this.getPageData();
		List<PageData> dzInfoList = (List<PageData>)dao.findForList("ExamInfoMapper.getDzInfo", pd);
		Map dzInfoMap = new HashMap();
		dzInfoMap.put("dz_danxuan", dzInfoList.get(0).getString("cnt"));
		dzInfoMap.put("dz_duoxuan", dzInfoList.get(1).getString("cnt"));
		dzInfoMap.put("dz_panduan", dzInfoList.get(2).getString("cnt"));
		return dzInfoMap;
	}
	
	
	/**
	 * 描述：保存试题
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-07
	 */
	public Map saveExam(){
		PageData pd = this.getPageData();
		pd.put("id", this.get32UUID());
		pd.put("record_id", this.getUser().getUSER_ID());
		pd.put("record_role_lev", this.getUser().getRole().getROLE_LEV());
		pd.put("status", "1");
		pd.put("question_count", "50");
		try {
			String org_id = pd.getString("org_id");
			if(StringUtils.isNotBlank(org_id)){//企业定制试卷
				PageData industryPd = (PageData)dao.findForObject("QuestionInfoMapper.getIndustryByOrgId", pd);
				pd.put("industry", industryPd.getString("industry"));
			}
			
			String batch_exam = pd.getString("batch_exam");
			System.out.println("pd::::" + pd);
			if(StringUtils.isBlank(batch_exam)){
				dao.save("ExamInfoMapper.saveExam", pd);
			}else{
				Integer batch_exam_int = Integer.parseInt(batch_exam);
				String title = pd.getString("title");
				List<PageData> pdList = new ArrayList();
				for(int i = 1; i <= batch_exam_int; i++){
					pd.put("id", this.get32UUID());
					pd.put("title", title + i);
					pdList.add(PageData.mapToNewPd(pd));
				}
				dao.batchSave("ExamInfoMapper.batchSaveExam", pdList);
			}
			return ResultUtils.returnWebOk();
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnWebError();
		}
	}
	
	
	/**
	 * 描述：获取试卷详情
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-09
	 */
	public PageData getExamMap() throws Exception{
		PageData pd = this.getPageData();
		return (PageData)dao.findForObject("ExamInfoMapper.getExamMap", pd);
	}

	
	/**
	 * 描述：删除试题
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-07
	 */
	public Map delExam() {
		PageData pd = this.getPageData();
		try {
			dao.update("ExamInfoMapper.delExam", pd);
			return ResultUtils.returnWebOk();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnWebError();
		}
	}

	/**
	 * 描述：获取题目集合
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-09
	 */
	public List<PageData> getExamQuestionList() throws Exception{
		PageData pd = this.getPageData();
		PageData examQuestionCnt =  (PageData)dao.findForObject("ExamInfoMapper.getExamQuestionCnt", pd);
		Integer cnt = Integer.parseInt(examQuestionCnt.getString("cnt"));
		if(cnt == 0){//若没有生成试卷, 执行生成试卷操作
			System.out.println("pd.getString:::" + pd.getString("id"));
			creatExamQuestion(pd.getString("id"));
		}
		List<PageData> examQuestionList = (List<PageData>)dao.findForList("ExamInfoMapper.getExamQuestionList", pd);
		return examQuestionList;
	}

	
	/**
	 * 描述：生成试卷操作
	 * @author yanbs
	 * @Date : 2019-05-09
	 */
	public void creatExamQuestion(String exam_info_id) throws Exception{
		Integer danxuan = 20;//单选20题
		Integer duoxuan = 10;//多选20题
		Integer panduan = 20;//判断10题
		PageData pd = new PageData();
		pd.put("id", exam_info_id);
		PageData examMap = (PageData)dao.findForObject("ExamInfoMapper.getExamMap", pd);
		String dz_danxuan = examMap.getString("dz_danxuan");
		String dz_duoxuan = examMap.getString("dz_duoxuan");
		String dz_panduan = examMap.getString("dz_panduan");
		Integer dz_danxuan_int = Integer.parseInt("".equals(dz_danxuan) ? "0" : dz_danxuan);
		Integer dz_duoxuan_int = Integer.parseInt("".equals(dz_duoxuan) ? "0" : dz_duoxuan);
		Integer dz_panduan_int = Integer.parseInt("".equals(dz_panduan) ? "0" : dz_panduan);
		List<PageData> danxuanList = getQuestionList(dz_duoxuan_int, danxuan, "100301", examMap);//获取单选集合
		List<PageData> duoxuanList = getQuestionList(dz_danxuan_int, duoxuan, "100302", examMap);//获取多选集合
		List<PageData> panduanList = getQuestionList(dz_panduan_int, panduan, "100303", examMap);//获取判断集合
		
		List<PageData> questionList = new ArrayList<PageData>();
		questionList.addAll(danxuanList);
		questionList.addAll(duoxuanList);
		questionList.addAll(panduanList);
		int seq_num = 1;
		List<PageData> resultList = new ArrayList<PageData>();
		for (PageData pageData : questionList) {
			pageData.put("id", this.get32UUID());
			pageData.put("exam_info_id", exam_info_id);
			pageData.put("status", 1);
			pageData.put("seq_num", seq_num);
			resultList.add(pageData);
			seq_num++;
		}
		if(resultList.size() > 0){
			dao.batchSave("ExamInfoMapper.batchSaveExamQuestion", resultList);
		}
		
	}
	

	/**
	 * 描述：获取题目集合
	 * @param dz_int 定制数量
	 * @param totalCnt 要取题目的总数量
	 * @param type 要取题目的类型
	 * @param examMap 试卷Map
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-09
	 */
	public List<PageData> getQuestionList(Integer dz_int, Integer totalCnt, String type, PageData examMap) throws Exception{
		List<PageData> questionList = new ArrayList<PageData>();
		if(dz_int > 0){//有定制题目
			PageData paramPd = new PageData();
			paramPd.put("org_id", examMap.getString("org_id"));
			paramPd.put("type", type);
			List<PageData> dzList = (List<PageData>)dao.findForList("ExamInfoMapper.getDzList", paramPd);
			if(dzList != null && dzList.size() > 0){
				List<PageData> randomList = getRandomList(dzList, dz_int);
				questionList.addAll(randomList);
			}
			
		}
		Integer shengyuCnt = totalCnt - questionList.size();//获取剩余单选题目数
		PageData paramPd = new PageData();
		paramPd = examMap;
		paramPd.put("type", type);
		List<PageData> commonList = (List<PageData>)dao.findForList("ExamInfoMapper.getCommonList", paramPd);
		List<PageData> randomList = getRandomList(commonList, shengyuCnt);
		questionList.addAll(randomList);
		//再次随机取，以打乱定制题目与公共题目顺序
		List<PageData> resultList = getRandomList(questionList, totalCnt);
		return resultList;
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
	 * 描述：更新
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-14
	 */
	public Map updateExamStatus() {
		PageData pd = this.getPageData();
		try {
			dao.update("ExamInfoMapper.updateExamStatus", pd);
			return ResultUtils.returnWebOk();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnWebError();
		}
	}

	/**
	 * 描述：成绩统计图iframe页面
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-15
	 */
	public List<PageData> resultChartIframe() throws Exception{
		PageData pd = this.getPageData();
		String county_id = pd.getString("county_id");
		String area_id = pd.getString("area_id");
		String org_id = pd.getString("org_id");
		List<PageData> resultList = new ArrayList<PageData>();
		if("".equals(county_id) && "".equals(area_id) && "".equals(org_id)){//查看县区之间的成绩统计
			resultList = (List<PageData>)dao.findForList("ExamInfoMapper.getCountysScore", pd);
		}else if((!"".equals(county_id)) && "".equals(area_id) && "".equals(org_id) ){//查看某县区下园区之间的成绩统计
			resultList = (List<PageData>)dao.findForList("ExamInfoMapper.getAreasScore", pd);
		}else if((!"".equals(county_id)) && (!"".equals(area_id)) && "".equals(org_id) ){//查看某园区下企业之间的成绩统计
			resultList = (List<PageData>)dao.findForList("ExamInfoMapper.getOrgsScore", pd);
		}else{//查看指定企业的成绩
			resultList = (List<PageData>)dao.findForList("ExamInfoMapper.getOrgsScore", pd);
		}
		return resultList;
	}
	/**
	 * 描述：：成绩统计表iframe页面
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-15
	 */
	public List<PageData> resultTableIframe() throws Exception{
		PageData pd = this.getPageData();
		String county_id = pd.getString("county_id");
		String area_id = pd.getString("area_id");
		String org_id = pd.getString("org_id");
		List<PageData> resultList = new ArrayList<PageData>();
		if("".equals(county_id) && "".equals(area_id) && "".equals(org_id)){//查看县区之间的成绩统计
			resultList = (List<PageData>)dao.findForList("ExamInfoMapper.getCountysScore", pd);
		}else if((!"".equals(county_id)) && "".equals(area_id) && "".equals(org_id) ){//查看某县区下园区之间的成绩统计
			resultList = (List<PageData>)dao.findForList("ExamInfoMapper.getAreasScore", pd);
		}else if((!"".equals(county_id)) && (!"".equals(area_id)) && "".equals(org_id) ){//查看某园区下企业之间的成绩统计
			resultList = (List<PageData>)dao.findForList("ExamInfoMapper.getOrgsScore", pd);
		}else{//查看指定企业的成绩
			resultList = (List<PageData>)dao.findForList("ExamInfoMapper.getOrgScore", pd);//查询统计表显示公司已考人员考试得分详情
			this.getRequest().setAttribute("flag_emp", "1");
		}
		return resultList;
	}

}
