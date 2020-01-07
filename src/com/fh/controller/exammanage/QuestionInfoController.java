package com.fh.controller.exammanage;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fh.controller.base.BaseController;
import com.fh.service.exammanage.QuestionInfoService;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.ResultUtils;

/**
 * 描述：题库管理
 * @author yanbs
 * @Date : 2019-05-08
 */
@Controller
@RequestMapping(value="/question")
public class QuestionInfoController extends BaseController {
	
	@Autowired
	private QuestionInfoService questionInfoService;
	
	/**
	 * 描述：进入题库管理
	 * @return
	 * @author yanbs
	 * @Date : 2019-04-29
	 */
	@RequestMapping(value="/questionList")
	public String questionList(Model model) throws Exception{
		List<PageData> industryList = questionInfoService.getIndustryList();
		model.addAttribute("QX", Jurisdiction.getHC());
		model.addAttribute("industryList", industryList);
		return "exammanage/questioninfo/questionList";
	}
	
	/**
	 * 描述：获取题库列表
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-04-29
	 */
	@RequestMapping(value="/getQuestionList")
	@ResponseBody
	public Map getQuestionList() throws Exception{
		List<PageData> questionList = questionInfoService.getQuestionList();
		return ResultUtils.returnWebPage(questionList);
	}

	
	
	/**
	 * 描述：添加试卷页面
	 * @return
	 * @author yanbs
	 * @Date : 2019-04-29
	 */
	@RequestMapping(value="/addQuestion")
	public String addQuestion(Model model) throws Exception{
		List<PageData> industryList = questionInfoService.getIndustryList();
		List<PageData> countyList = questionInfoService.getCountyList();
		model.addAttribute("industryList", industryList);
		model.addAttribute("countyList", countyList);
		return "exammanage/questioninfo/addQuestion";
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
		return questionInfoService.getAreaList();
	}
	
	@RequestMapping(value="/getOrgList")
	@ResponseBody
	public List getOrgList() throws Exception{
		return questionInfoService.getOrgList();
	}
	
	/**
	 * 描述：保存试题信息
	 * @return
	 * @author yanbs
	 * @Date : 2019-04-30
	 */
	@RequestMapping(value="/saveQuestion")
	@ResponseBody
	public Map saveQuestion(){
		return questionInfoService.saveQuestion();
	}
	
	/**
	 * 描述：编辑
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-07
	 */
	@RequestMapping(value="/editQuestion")
	public String editQuestion(Model model) throws Exception{
		List<PageData> industryList = questionInfoService.getIndustryList();
		List<PageData> countyList = questionInfoService.getCountyList();
		PageData questionMap = questionInfoService.getQuestionMap();
		model.addAttribute("industryList", industryList);
		model.addAttribute("countyList", countyList);
		model.addAttribute("questionMap", questionMap);
		return "exammanage/questioninfo/editQuestion";
	}
	
	/**
	 * 描述：保存编辑信息
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-05
	 */
	@RequestMapping(value="/saveEdit")
	@ResponseBody
	public Map saveEdit(){
		return questionInfoService.saveEdit();
	}
	
	
	/**
	 * 描述：删除试题
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-08
	 */
	@RequestMapping(value="/delQuestion")
	@ResponseBody
	public Map delQuestion(){
		return questionInfoService.delQuestion();
	}
	
	/**
	 * 描述：批量导入页面
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-08
	 */
	@RequestMapping(value="/impQuestion")
	public String impQuestion(){
		return "exammanage/questioninfo/impQuestion";
	}
	
	
	@RequestMapping(value="/impResult")
	@ResponseBody
	public Map impResult(@RequestParam(value="file") CommonsMultipartFile file){
		return questionInfoService.impResult(file);
	}
	
	
}
