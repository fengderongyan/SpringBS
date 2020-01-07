package com.fh.controller.weixin.mywx;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.entity.weixin.WxUser;
import com.fh.service.weixin.mywx.WxLoginManager;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.ResultUtils;
import com.fh.util.StringUtil;

@Controller
@RequestMapping(value="/weixin/login")
public class WxLoginController extends BaseController{
	@Autowired
	WxLoginManager wxLoginService;
	
	@RequestMapping(value="/wxLogin.do")
	@ResponseBody
	public Map wxLogin(){
		PageData param = this.getPageData();
		Map resultMap = wxLoginService.getOpenid(param.getString("js_code"));
		if(resultMap == null || StringUtils.isBlank((String)resultMap.get("openid"))){
			return resultMap;
		}else{
			String openid = StringUtil.get(resultMap, "openid");
			param.put("openid", openid);
			try {
				PageData pd = wxLoginService.findWxUserByOpenid(param);
				if(pd == null || pd.size() == 0){
					return ResultUtils.returnError(11006, "未绑定手机号码");
				}
				WxUser wxUser = new WxUser();
				wxUser.setOpenid(pd.getString("openid"));
				wxUser.setName(pd.getString("name"));
				wxUser.setMobile(pd.getString("mobile"));
				wxUser.setStatus(pd.getString("status"));
				Session session = Jurisdiction.getSession();
				session.setAttribute(Const.SESSION_WXUSER, wxUser);
				return ResultUtils.returnOk();
			} catch (Exception e) {
				e.printStackTrace();
				return ResultUtils.returnError(15002, "数据库操作失败");
			}
		}
	}
	
	/**
	 * 描述：用户手机号与openId绑定
	 * @param openId
	 * @param mobile
	 * @return
	 * @see weixin.controller.WxLoginController#addOpenId()
	 * @author zhangyongbin
	 */
	@RequestMapping(value="/addOpenid.do")
	@ResponseBody
	public Map addOpenId() throws Exception{
		PageData pd = this.getPageData();
		String js_code = pd.getString("js_code");
		String mobile = pd.getString("mobile");
		if(StringUtils.isBlank(js_code)){
			return ResultUtils.returnError(12001, "js_code不能为空");
		}
		if(StringUtils.isBlank(mobile)){
			return ResultUtils.returnError(12001, "mobile不能为空");
		}
		return wxLoginService.addOpenId(pd);
	}

}
