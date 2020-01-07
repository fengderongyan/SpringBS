package com.fh.interceptor;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fh.dao.redis.impl.RedisDaoImpl;
import com.fh.entity.system.User;
import com.fh.service.chartlog.ChartLogService;
import com.fh.service.mobile.MobileInformationService;
import com.fh.util.Const;
import com.fh.util.DateHelper;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.JwtUtil;
import com.fh.util.PageData;
import com.fh.util.StringHelper;
import com.fh.util.spring.SpringHelper;
import com.fh.util.spring.SpringUtil;

import io.jsonwebtoken.Claims;
import net.sf.json.JSONObject;

/**
 * 描述：移动端拦截器
 * @author zhangcc
 * @Date : 2019-05-15
 */
@Component
public class MobileHandlerInterceptor extends HandlerInterceptorAdapter{
	
	/*@Autowired
	public RedisDaoImpl redisDaoImpl;*/
	//private RedisDaoImpl redisDaoImpl = (RedisDaoImpl) SpringUtil.getSpringBean("redisDaoImpl");
	
	private String token_key = "1safe2363safe";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		//RedisDaoImpl redisDaoImpl = (RedisDaoImpl) SpringHelper.getBean("redisDaoImpl");
		RedisDaoImpl redisDaoImpl = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext()).getBean(RedisDaoImpl.class);
		MobileInformationService mobileInformationService = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext()).getBean(MobileInformationService.class);
		ChartLogService chartLogService = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext()).getBean(ChartLogService.class);
		String path = request.getServletPath();
		String url = request.getRequestURL().toString();
		Map redisInfo = new HashMap();
		if(path.matches(Const.MOBILETERCEPTOR_PATH)){
			return true;
		}
		//手机端请求
		if(url.indexOf("/mobile/app/") > -1){
			System.out.println("request url is ：" + url);
			String token = request.getHeader("token");
			//
			if(token == null || "".equals(token))
				token = request.getParameter("token");
			//
			System.out.println("token is : " + token);
			if(StringUtils.isBlank(token)){//获取不到token
				Map errorMap = new HashMap();
				errorMap.put("errorCode", 11004);
				errorMap.put("errorInfo", "token校验失败");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(JSONObject.fromObject(errorMap));
				return false;		
			}
			boolean isVerify = JwtUtil.isVerify(token, token_key);
			if(!isVerify){//token 签名不合法
				Map errorMap = new HashMap();
				errorMap.put("errorCode", 11004);
				errorMap.put("errorInfo", "token Invalid!");
				response.getWriter().print(JSONObject.fromObject(errorMap));
				return false;
			}
			Claims tokenInfo =  JwtUtil.parseJWT(token, token_key);
			String userRedisKey = Const.TOKEN_KEY_FRIST + tokenInfo.get("username") + Const.TOKEN_KEY_END;
			System.out.println("============userRedisKey : " + userRedisKey);
			boolean hasKey = redisDaoImpl.hasKey(userRedisKey);
			boolean isHasToken = false;
			
			if(hasKey){//缓存中KEY值存在
				redisInfo = redisDaoImpl.getMap(userRedisKey);
				isHasToken = token.equals(StringHelper.get(redisInfo, "token")) ? true : false;
			}else{
				Map errorMap = new HashMap();
				errorMap.put("errorCode", 11004);
				errorMap.put("errorInfo", "token Invalid!");
				response.getWriter().print(JSONObject.fromObject(errorMap));
				return false;
			}
			if(isHasToken){//token未失效
				this.initUserInfor(userRedisKey, request);
				//每日登陆
				String loginKey = Const.TOKEN_KEY_FRIST + tokenInfo.get("username") + DateHelper.getToday("yyyyMMdd");
				boolean hasLoginKey = redisDaoImpl.hasKey(loginKey);
				System.out.println("=============================================================hasLoginKey : " + !hasLoginKey);
				if(!hasLoginKey){//缓存中KEY值不存在
					mobileInformationService.saveMypoints();
					//记录登陆日志
					PageData chartPd = new PageData();
					String daynum = DateUtil.getSdfTimes();
					chartPd.put("id", UUID.randomUUID().toString().trim().replaceAll("-", ""));
					chartPd.put("bill_day", daynum.substring(0, 8));
					chartPd.put("bill_hour", daynum.substring(8, 10));
					User user = (User)Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
					chartPd.put("user_id", user.getUSER_ID());
					chartPd.put("org_id", user.getOrg_id());
					chartPd.put("login_type","2");//1 PC； 2 移动端
					chartLogService.saveChartLogin(chartPd);//添加登录记录（统计报表使用）
					
					/*String total_point = "0";
					Map pointResult = mobileInformationService.getPointsResult();
					if(pointResult != null &&  pointResult.size() > 0){
						total_point = StringHelper.get(pointResult, "total_point");
						total_point = total_point == "" ? "0" : total_point;
					}
					int total_point1 = Integer.parseInt(total_point);   //当日登陆总分
					if(total_point1 < 5){
					}*/
					//设置缓存
					redisDaoImpl.addString(loginKey, "1");
					redisDaoImpl.expire(loginKey,  60 * 60 * 24);
					
				}
			}else {
				Map errorMap = new HashMap();
				errorMap.put("errorCode", 11004);
				errorMap.put("errorInfo", "token Invalid!");
				response.getWriter().print(JSONObject.fromObject(errorMap));
				return false;
			}
			return true;
		}
		return true;
	}
	
	/**
	 * 描述：初始化用户信息
	 * @param userRedisKey
	 * @param request
	 * @author zhangcc
	 * @Date : 2019-05-15
	 */
	public void initUserInfor(String userRedisKey, HttpServletRequest request)  {
		RedisDaoImpl redisDaoImpl = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext()).getBean(RedisDaoImpl.class);
		// 用户信息
		User user = new User();
		Map redisInfo = new HashMap();
		redisInfo = redisDaoImpl.getMap(userRedisKey);
		
		user.setUSER_ID(StringHelper.get(redisInfo, "user_id"));
		user.setUSERNAME(StringHelper.get(redisInfo, "username"));
		user.setPASSWORD(StringHelper.get(redisInfo, "password"));
		user.setNAME(StringHelper.get(redisInfo, "name"));
		user.setROLE_ID(StringHelper.get(redisInfo, "role_id"));
		user.setToken(StringHelper.get(redisInfo, "token"));
		user.setOrg_id(StringHelper.get(redisInfo, "org_id"));
		user.setOrg_lev(StringHelper.get(redisInfo, "org_lev"));
		user.setROLE_NAME(StringHelper.get(redisInfo, "role_name"));
		user.setOrg_name(StringHelper.get(redisInfo, "org_name"));
		user.setCounty_id(StringHelper.get(redisInfo, "county_id"));
		user.setArea_id(StringHelper.get(redisInfo, "area_id"));
		user.setIndustry(StringHelper.get(redisInfo, "industry"));
		//gouBusinessService.setBusinessOperInfo(businessOper, busToken);
		Session session = Jurisdiction.getSession();
		session.setAttribute(Const.SESSION_USERROL, user);						//存入session	
		session.setAttribute("httpHref", "http://192.168.0.107:8080/SpringBS/");						//存入session	
	}

	
}
