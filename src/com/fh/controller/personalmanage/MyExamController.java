package com.fh.controller.personalmanage;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.service.personalmanage.MyExamService;
import com.fh.service.web.WebService;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.ResultUtils;

/**
 * 描述：试卷管理
 * @author yanbs
 * @Date : 2019-05-08
 */
@Controller
@RequestMapping(value="/myexam")
public class MyExamController extends BaseController {
	
	@Autowired
	private MyExamService myExamService;
	
	@Autowired
	private WebService webService;
	
	/**
	 * 描述：进入试卷管理
	 * @return
	 * @author yanbs
	 * @Date : 2019-04-29
	 */
	@RequestMapping(value="/myExamList")
	public String questionList(Model model) throws Exception{
		List<PageData> industryList = myExamService.getIndustryList();
		model.addAttribute("QX", Jurisdiction.getHC());
		model.addAttribute("industryList", industryList);
		return "personalmanage/myexam/myExamList";
	}
	
	/**
	 * 描述：获取题库列表
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-04-29
	 */
	@RequestMapping(value="/getMyExamList")
	@ResponseBody
	public Map getExamList() throws Exception{
		List<PageData> myExamList = myExamService.getMyExamList();
		System.out.println("myExamList:::::::" + myExamList);
		return ResultUtils.returnWebPage(myExamList);
	}
	
	/**
	 * 描述：答题
	 * @return
	 * @author yanbs
	 * @Date : 2019-05-10
	 */
	@RequestMapping(value="/takeExam")
	public String takeExam(Model model) throws Exception{
		myExamService.insertMyMxam();
		PageData examQuestionMap = myExamService.getExamQuestionMap();
		model.addAttribute("examQuestionMap", examQuestionMap);
		return "personalmanage/myexam/takeExam";
		
	}
	
	/**
	 * 描述：点击下一题
	 * @param model
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-10
	 */
	@RequestMapping(value="/takeExamAjax")
	public String takeExamAjax(Model model) throws Exception{
		PageData examQuestionMap = myExamService.getExamQuestionMap();
		model.addAttribute("examQuestionMap", examQuestionMap);
		return "personalmanage/myexam/takeExamAjax";
		
	}
	
	/**
	 * 描述：查看成绩
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-13
	 */
	@RequestMapping(value="/showResult")
	public String showResult(Model model) throws Exception{
		List<PageData> resultList = myExamService.showResult();
		String score = myExamService.getMyExamScore();
		model.addAttribute("resultList", resultList);
		model.addAttribute("score", score);
		return "personalmanage/myexam/showResult";
	}
	
	/**
	 * 描述：保存总得分信息
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-05-13
	 */
	@RequestMapping(value="/saveExam")
	@ResponseBody
	public int saveExam() throws Exception{
		myExamService.saveExam();
		return 1;
	}
	
	@RequestMapping(value="/subExam")
	@ResponseBody
	public int subExam() throws Exception{
		myExamService.getExamQuestionMap();//保存答案
		PageData pd = this.getPageData();
		String seq_num = pd.getString("seq_num");
		if("51".equals(seq_num)){//全部答完,获取积分
			String exam_type = webService.getExamTypeById(pd.getString("id"));
			PageData paramPd = new PageData();
			if("0".equals(exam_type)){//模拟考试
				System.out.println("========================模拟考试");
				paramPd.put("type", 5);
			}else if("1".equals(exam_type)){//正式考试
				System.out.println("========================正式考试");
				paramPd.put("type", 6);
			}
			paramPd.put("info_id", pd.getString("id"));
			webService.addMyExamPoint(paramPd);
		}
		return 1;
	}
}
