package com.fh.controller.mobile;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.service.mobile.MobileInformationService;
import com.fh.service.mobile.MobileLoginService;
import com.fh.service.mobile.MobileVideoService;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.util.AppUtil;
import com.fh.util.PageData;
import com.fh.util.ResultUtils;
import com.fh.util.StringHelper;
import com.fh.util.Tools;

/**
 * 描述：APP端信息接口
 * @author zhangcc
 * @Date : 2019-05-16
 */
@Controller
@RequestMapping(value="/mobile/app")
public class MobileVidoeController extends BaseController {
	
	@Autowired
	private MobileVideoService mobileVideoService;
	
	
	/**
	 * 描述：获取视频类型列表
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-22
	 */
	@RequestMapping(value="/video/getVideoTypeList")
	@ResponseBody
	public Map getVideoTypeList(){
		return mobileVideoService.getVideoTypeList();
	}
	
	/**
	 * 描述：获取对应视频列表
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-22
	 */
	@RequestMapping(value="/video/getVideoList")
	@ResponseBody
	public Map getVideoList(){
		return mobileVideoService.getVideoList();
	}
	
	/**
	 * 描述：获取资讯详情
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-17
	 */
	@RequestMapping(value="/video/getVideoDetail")
	@ResponseBody
	public Map getVideoDetail(){
		return mobileVideoService.getVideoDetail();
	}
	
}
	
 