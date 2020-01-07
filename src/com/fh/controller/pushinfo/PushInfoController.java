package com.fh.controller.pushinfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.druid.support.json.JSONUtils;
import com.fh.controller.base.BaseController;
import com.fh.entity.system.TreeModel;
import com.fh.service.informationmanage.InformationManageService;
import com.fh.service.informationmanage.InformationSafeManageService;
import com.fh.service.lessonmanage.LessonManageService;
import com.fh.service.lessonmanage.LessonTypeService;
import com.fh.service.pushinfo.PushInfoService;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.ResultUtils;
import com.fh.util.StringHelper;
import com.fh.util.JPush.JPushUtil;

/**
 * 描述：推送信息管理
 * @author zhangcc
 * @Date : 2019-05-27
 */
@Controller
@RequestMapping(value="/push/pushinfo")
public class PushInfoController extends BaseController {
	
	@Autowired
	private PushInfoService pushInfoService;
	
	@Autowired
	private LessonManageService lessonManageService;
	
	/**
	 * 描述：推送信息首页
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-27
	 */
	@RequestMapping(value="/informationManageList")
	public String informationManageList(Model model) throws Exception{
		model.addAttribute("QX", Jurisdiction.getHC());
		return "pushinfo/informationList";
	}
	
	
	
	/**
	 * 描述：获取消息列表
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-27
	 */
	@RequestMapping(value="/getInformaticaManageList")
	@ResponseBody
	public Map getInformaticaManageList() throws Exception{
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		List<PageData> informationList = pushInfoService.getMessageList(pd);
		return ResultUtils.returnWebPage(informationList);
	}
	
	
	
	/**
	 * 描述：新增推送信息
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zhangcc
	 * @Date : 2019-05-27
	 */
	@RequestMapping(value="/addInformation")
	public String addInformation(Model model) throws Exception{
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		//获取园区
		return "pushinfo/addInformation";
	}
	
	/**
	 * 描述：保存推送信息并推送
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-27
	 */
	@RequestMapping(value="/saveAndPush")
	@ResponseBody
	public Map saveAndPush(){
		PageData pd = this.getPageData();
		pd.put("user", this.getUser());
		Map infoMap = pushInfoService.saveInformation(pd);
		String appcode = StringHelper.get(infoMap, "appcode");
		String title = pd.getString("title");
		String pushContent = pd.getString("title");
		String pId = "";
		String userId = "";
		if(appcode.equals("1")){//成功
			//极光推送信息
			List infoList = pushInfoService.getJpushPids(pd);
			for(int i = 0; i < infoList.size(); i ++){
				Map map = (Map) infoList.get(i);
				pId = StringHelper.get(map, "registration_id");
				userId = StringHelper.get(map, "user_id");
				if(!pId.equals("")){//非空
					   JPushUtil.pushMsg(pId, pushContent, "link", "", title);
					   pd.put("user_id", userId);
					   lessonManageService.savePushLog(pd);
				   }
			}
		}
		return infoMap;
	}
	
	@RequestMapping(value="/delInformation")
	@ResponseBody
	public Map delInformation(){
		return pushInfoService.delInformatione();
	}
	
}
