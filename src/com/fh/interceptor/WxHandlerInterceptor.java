package com.fh.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.fh.entity.system.User;
import com.fh.entity.weixin.WxUser;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;

import net.sf.json.JSONObject;
/**
 * 
* 类名称：登录过滤，权限验证
* 类描述： 
* @author FH qq313596790[青苔]
* 作者单位： 
* 联系方式：
* 创建时间：2017年11月2日
* @version 1.6
 */
public class WxHandlerInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		String path = request.getServletPath();
		if(!path.matches(Const.WXINTERCEPTOR_PATH)){
			return true;
		}else{
			WxUser wxUser = (WxUser)Jurisdiction.getSession().getAttribute(Const.SESSION_WXUSER);
			if(wxUser == null || "".equals(wxUser.getOpenid())){//
				Map errorMap = new HashMap();
				errorMap.put("errorCode", 11006);
				errorMap.put("errorInfo", "用户信息失效或未绑定手机号");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(JSONObject.fromObject(errorMap));
				return false;		
			}else{
				return true;
			}
		}
	}

	
}
