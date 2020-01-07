package com.fh.service.personalmanage;


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
import com.fh.service.exammanage.ExamInfoService;
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
public class MyExamService extends BaseService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Resource(name = "examInfoService")
	private ExamInfoService examInfoService;
	
	/**
	 * 描述：获取题库列表
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-07
	 */
	public List<PageData> getMyExamList() throws Exception{
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		PHelper.startPage(pd);
		return (List<PageData>)dao.findForList("MyExamMapper.getMyExamList", pd);
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
	 * 描述：获取答题信息
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-10
	 */
	public PageData getExamQuestionMap() throws Exception{
		PageData pd = this.getPageData();
		PageData examQuestionCnt =  (PageData)dao.findForObject("ExamInfoMapper.getExamQuestionCnt", pd);
		Integer cnt = Integer.parseInt(examQuestionCnt.getString("cnt"));
		System.out.println("pd:::" + pd);
		if(cnt == 0){//若没有生成试卷, 执行生成试卷操作
			examInfoService.creatExamQuestion(pd.getString("id"));
		}
		String seq_num = pd.getString("seq_num");
		int seqNum  = Integer.parseInt(seq_num);
		if(seqNum > 1){//说明点击了下一题，保存上一题答案
			int score = 0;
			String type = pd.getString("type");//题类型
			String my_answer = pd.getString("my_answer");//选择的答案
			String answer = pd.getString("answer");//正确答案
			if(StringUtils.isNotBlank(my_answer) && StringUtils.isNotBlank(answer) && my_answer.equals(answer)){//选择的答案与正确答案相同
				score = 2;
			}else{
				if("100302".equals(type) ){//多选题,未答全
					char [] my_answer_char = my_answer.toCharArray();
					boolean flag = true;
					for (char c : my_answer_char) {
						if(answer.indexOf(c) == -1){//若选择的答案不在正确答案之中，说明选错了
							flag = false;
							break;
						};
					}
					if(flag){//部分答案正确
						score = 1;
					}
					
				}
			}
			
			PageData paramPd = new PageData();
			paramPd.put("id", this.get32UUID());
			paramPd.put("exam_info_id", pd.getString("id"));
			paramPd.put("question_info_id", pd.getString("question_info_id"));
			paramPd.put("my_answer", my_answer);
			paramPd.put("answer", answer);
			paramPd.put("score", 2);
			paramPd.put("my_score", score);
			paramPd.put("user_id", this.getUser().getUSER_ID());
			dao.save("MyExamMapper.saveMyAnswer", paramPd);
			
		}
		PageData examQuestionMap = (PageData)dao.findForObject("MyExamMapper.getExamQuestionMap", pd);
		return examQuestionMap;
	}
	
	/**
	 * 描述：开始答题，添加一条我的试卷记录
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-13
	 */
	public void insertMyMxam() throws Exception{
		PageData pd = this.getPageData();
		pd.put("exam_info_id", pd.getString("id"));
		pd.put("id", this.get32UUID());
		pd.put("user_id", this.getUser().getUSER_ID());
		pd.put("org_id", this.getUser().getOrg_id());
		pd.put("score", 0);
		dao.save("MyExamMapper.insertMyMxam", pd);
	}

	/**
	 * 描述：查看成绩
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-13
	 */
	public List<PageData> showResult() throws Exception{
		PageData pd = this.getPageData();
		pd.put("user_id", this.getUser().getUSER_ID());
		List<PageData> resultList = (List<PageData>)dao.findForList("MyExamMapper.showResult", pd);
		return resultList;
	}
	
	public String getMyExamScore() throws Exception{
		PageData pd = this.getPageData();
		pd.put("user_id", this.getUser().getUSER_ID());
		PageData scorePd = (PageData)dao.findForObject("MyExamMapper.getMyExamScore", pd);
		return scorePd.getString("score");
	}

	public void saveExam() throws Exception{
		PageData pd = this.getPageData();
		System.out.println("pd:::" + pd);
		pd.put("user_id", this.getUser().getUSER_ID());
		pd.put("org_id", this.getUser().getOrg_id());
		PageData resultPd = (PageData)dao.findForObject("MyExamMapper.getTotalScore", pd);
		String score = resultPd.getString("total_score");
		pd.put("score", score);
		dao.update("MyExamMapper.updateMyExamScore", pd);
	}
	

}
