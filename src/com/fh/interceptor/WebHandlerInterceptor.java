package com.fh.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
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
public class WebHandlerInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		String url = request.getRequestURL().toString();
		if(url.indexOf("/web/personal") > -1){//个人中心
			User user = (User)Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			if(user == null){
				response.sendRedirect(request.getContextPath());
				return false;	
			}
		}
		return true;
	}
	
}
