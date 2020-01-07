package com.fh.controller.exammanage;


import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.TryCatchFinally;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.druid.support.json.JSONUtils;
import com.fh.controller.base.BaseController;
import com.fh.service.exammanage.ExamInfoService;
import com.fh.util.DateHelper;
import com.fh.util.DelAllFile;
import com.fh.util.FileDownload;
import com.fh.util.FileUtil;
import com.fh.util.FileZip;
import com.fh.util.Freemarker;
import com.fh.util.JsonUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.ResultUtils;
import com.mysql.fabric.xmlrpc.base.Array;

/**
 * 描述：试卷管理
 * @author yanbs
 * @Date : 2019-05-08
 */
@Controller
@RequestMapping(value="/exam")
public class ExamInfoController extends BaseController {
	
	@Autowired
	private ExamInfoService examInfoService;
	
	/**
	 * 描述：进入试卷管理
	 * @return
	 * @author yanbs
	 * @Date : 2019-04-29
	 */
	@RequestMapping(value="/examList")
	public String questionList(Model model) throws Exception{
		List<PageData> industryList = examInfoService.getIndustryList();
		model.addAttribute("QX", Jurisdiction.getHC());
		model.addAttribute("industryList", industryList);
		return "exammanage/exam/examList";
	}
	
	/**
	 * 描述：获取题库列表
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-04-29
	 */
	@RequestMapping(value="/getExamList")
	@ResponseBody
	public Map getExamList() throws Exception{
		List<PageData> examList = examInfoService.getExamList();
		return ResultUtils.returnWebPage(examList);
	}

	
	
	/**
	 * 描述：添加试卷页面
	 * @return
	 * @author yanbs
	 * @Date : 2019-04-29
	 */
	@RequestMapping(value="/addExam")
	public String addExam(Model model) throws Exception{
		List<PageData> industryList = examInfoService.getIndustryList();
		List<PageData> countyList = examInfoService.getCountyList();
		model.addAttribute("industryList", industryList);
		model.addAttribute("countyList", countyList);
		return "exammanage/exam/addExam";
	}
	
	/**
	 * 描述：获取园区信息
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-07
	 */
	@RequestMapping(value="/getAreaList")
	@ResponseBody
	public List getAreaList() throws Exception{
		return examInfoService.getAreaList();
	}
	
	@RequestMapping(value="/getOrgList")
	@ResponseBody
	public List getOrgList() throws Exception{
		return examInfoService.getOrgList();
	}
	
	
	/**
	 * 描述：获取企业定制信息
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-09
	 */
	@RequestMapping(value="/getDzInfo")
	@ResponseBody
	public Map getDzInfo() throws Exception{
		return examInfoService.getDzInfo();
	}
	
	
	
	/**
	 * 描述：保存试题信息
	 * @return
	 * @author yanbs
	 * @Date : 2019-04-30
	 */
	@RequestMapping(value="/saveExam")
	@ResponseBody
	public Map saveExam(){
		return examInfoService.saveExam();
	}
	
	/**
	 * 描述：获取详情
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-07
	 */
	@RequestMapping(value="/editExam")
	public String editExam(Model model) throws Exception{
		List<PageData> industryList = examInfoService.getIndustryList();
		PageData examMap = examInfoService.getExamMap();
		model.addAttribute("industryList", industryList);
		model.addAttribute("examMap", examMap);
		return "exammanage/exam/editExam";
	}
	
	/**
	 * 描述：删除试题
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-08
	 */
	@RequestMapping(value="/delExam")
	@ResponseBody
	public Map delExam(){
		return examInfoService.delExam();
	}
	
	/**
	 * 描述：预览试卷
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-09
	 */
	@RequestMapping(value="/showExam")
	public String showExam(Model model) throws Exception{
		List<PageData> examQuestionList = examInfoService.getExamQuestionList();
		model.addAttribute("examQuestionList", examQuestionList);
		return "exammanage/exam/showExam";
	}
	
	
	
	@RequestMapping(value="/updateExamStatus")
	@ResponseBody
	public Map updateExamStatus(){
		Map map = examInfoService.updateExamStatus();
		return map;
	}
	
	/**
	 * 描述：查看成绩统计页面
	 * @param model
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-14
	 */
	@RequestMapping(value="/resultTj")
	public String resultTj(Model model) throws Exception{
		List<PageData> countyList = examInfoService.getCountyList();
		model.addAttribute("countyList", countyList);
		return "exammanage/exam/resultTj";
	}
	
	/**
	 * 描述：成绩统计图iframe页面
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-15
	 */
	@RequestMapping(value="/resultChartIframe")
	public String resultChartIframe(Model model) throws Exception{
		List<PageData> resultList = examInfoService.resultChartIframe();
		List orgnizationNameList = new ArrayList();
		List totalNumList = new ArrayList();
		List passNumList = new ArrayList();
		List noPassNumList = new ArrayList();
		for (PageData pageData : resultList) {
			orgnizationNameList.add(pageData.getString("org_name"));
			totalNumList.add(pageData.getString("total_cnt"));
			passNumList.add(pageData.getString("pass_num"));
			noPassNumList.add(pageData.getString("no_pass_num"));
		}
		model.addAttribute("orgnizationNameList", JSONUtils.toJSONString(orgnizationNameList));
		model.addAttribute("totalNumList", JSONUtils.toJSONString(totalNumList));
		model.addAttribute("passNumList", JSONUtils.toJSONString(passNumList));
		model.addAttribute("noPassNumList", JSONUtils.toJSONString(noPassNumList));
		return "exammanage/exam/resultChartIframe";
	}
	
	@RequestMapping(value="/resultTableIframe")
	public String resultTableIframe(Model model) throws Exception{
		List<PageData> resultList = examInfoService.resultTableIframe();
		model.addAttribute("resultList", resultList);
		return "exammanage/exam/resultTableIframe";
	}
	
	/**
	 * 描述：添加问答题
	 * @return
	 * @author yanbs
	 * @Date : 2019-06-03
	 */
	@RequestMapping(value="/addWdt")
	public String addWdt(){
		
		return "exammanage/exam/addWdt";
	}
	
	/**
	 * 描述：打印试卷
	 * @param response
	 * @return
	 * @author yanbs
	 * @Date : 2019-06-03
	 */
	@RequestMapping(value="/printExam")
	@ResponseBody  
	public String printExam(HttpServletResponse response){
		try {
			List<PageData> examQuestionList = examInfoService.getExamQuestionList();
			List<PageData> questionList = new ArrayList<PageData>();
			List<PageData> answerList = new ArrayList<PageData>();
			if(examQuestionList != null && examQuestionList.size() > 0){
				for (PageData examPd : examQuestionList) {
					PageData questionMap = new PageData();
					PageData answerMap = new PageData();
					String question = examPd.getString("seq_num") + "、" + examPd.getString("question_info") + "(" + examPd.getString("type_name") + ")";
					questionMap.put("question", question);
					questionMap.put("optionA", examPd.getString("optionA"));
					questionMap.put("optionB", examPd.getString("optionB"));
					questionMap.put("optionC", examPd.getString("optionC"));
					questionMap.put("optionD", examPd.getString("optionD"));
					questionMap.put("optionE", examPd.getString("optionE"));
					questionList.add(questionMap);
					
					answerMap.put("question", question + "  正确答案：" + examPd.getString("answer"));
					answerMap.put("optionA", examPd.getString("optionA"));
					answerMap.put("optionB", examPd.getString("optionB"));
					answerMap.put("optionC", examPd.getString("optionC"));
					answerMap.put("optionD", examPd.getString("optionD"));
					answerMap.put("optionE", examPd.getString("optionE"));
					answerList.add(answerMap);
				}
			}
			Map<String,Object> rootQuestion = new HashMap<String,Object>();
			Map<String,Object> rootAnswer = new HashMap<String,Object>();
			PageData pd = this.getPageData();
			String wdt = pd.getString("wdt");
			String[] wdtArr = wdt.split(";-springbs-;");
			int seq_num = questionList.size();
			List wdtList = new ArrayList();
			if(wdtArr.length > 1){
				for(int i = 1; i < wdtArr.length; i++){
					if(!"".equals(wdtArr[i])){
						seq_num++;
						wdtList.add(seq_num + "、" + wdtArr[i] + "(问答题)");
					}
				}
			}
			String title = pd.getString("title");
			rootQuestion.put("title", title);
			rootQuestion.put("questionList", questionList);
			rootQuestion.put("wdtList", wdtList);
			//带答案
			rootAnswer.put("title", title);
			rootAnswer.put("questionList", answerList);
			rootAnswer.put("wdtList", wdtList);
			//DelAllFile.delFolder(PathUtil.getClasspath()+"admin/ftl");//生成文件之前，先清空之前的文件
			String uuid  = this.get32UUID();
			String filePath = "admin/ftl/exam/" + uuid + "/";						//存放路径
			String ftlPath = "examftl";								//ftl路径
			Freemarker.printFile("examTemplate.ftl", rootQuestion, title + ".doc", filePath, ftlPath);//生成试卷
			Freemarker.printFile("examTemplate.ftl", rootAnswer, title + "（附答案）.doc", filePath, ftlPath);//生成试卷附答案
			return uuid;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
	}
	
	/**
	 * 描述：下载压缩文件
	 * @param response
	 * @author yanbs
	 * @Date : 2019-06-03
	 */
	@RequestMapping(value="downLoadZip")
	public void downLoadZip(HttpServletResponse response){
		try {
			PageData pd = this.getPageData();
			String uuid = pd.getString("uuid");
			if(FileZip.zip(PathUtil.getClasspath()+"admin/ftl/exam/"+uuid, PathUtil.getClasspath()+"admin/ftl/"+ uuid +".zip")){
				/*下载试卷*/
				FileDownload.fileDownload(response, PathUtil.getClasspath()+"admin/ftl/" + uuid + ".zip", "exam.zip");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
