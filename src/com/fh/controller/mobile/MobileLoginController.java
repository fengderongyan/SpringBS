package com.fh.controller.mobile;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.service.mobile.MobileLoginService;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.util.AppUtil;
import com.fh.util.PageData;
import com.fh.util.ResultUtils;
import com.fh.util.Tools;



/**
 * 描述：APP端登陆接口
 * @author zhangcc
 * @Date : 2019-05-16
 */
@Controller
@RequestMapping(value="/mobile/app")
public class MobileLoginController extends BaseController {
	
	@Autowired
	private MobileLoginService mobileLoginService;
	
	/**
	 * 描述：校验登陆信息
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-16
	 */
	@RequestMapping(value="/checkLogin")
	@ResponseBody
	public Map checkLogin(){
		return mobileLoginService.checkLoginResult();
	}
	
	/**
	 * 描述：设备ID获取更新
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-29
	 */
	@RequestMapping(value="/updateRegId")
	@ResponseBody
	public Map updateRegId(){
		int res = mobileLoginService.updateResId();
		if(res == 1 )
			return ResultUtils.returnOk();
		return ResultUtils.returnError(-1, "更新失败");
	}
	
}
	
 