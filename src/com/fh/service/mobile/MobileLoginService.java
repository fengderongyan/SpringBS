package com.fh.service.mobile;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fh.dao.DaoSupport;
import com.fh.dao.redis.impl.RedisDaoImpl;
import com.fh.entity.system.User;
import com.fh.service.base.BaseService;
import com.fh.service.chartlog.ChartLogService;
import com.fh.util.BatchSql;
import com.fh.util.Const;
import com.fh.util.DateHelper;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.JwtUtil;
import com.fh.util.PHelper;
import com.fh.util.PageData;
import com.fh.util.ResultUtils;
import com.fh.util.StringHelper;
import com.fh.util.excel.FileHelper;
import com.fh.util.excel.HssfHelper;

/**
 * 描述：移动端登陆校验
 * @author zhangcc
 * @Date : 2019-05-16
 */
@Service
public class MobileLoginService extends BaseService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Autowired
	private RedisDaoImpl redisDaoImpl;
	
	@Autowired
	private ChartLogService chartLogService;
	
	@Autowired
	private MobileInformationService mobileInformationService;
	
	
	private String jwt_token_key = "1safe2363safe";

	/**
	 * 描述：验证登陆信息是否正确
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-16
	 */
	public Map checkLoginResult() {
		try {
			Map map = new HashMap();
			PageData pd = this.getPageData();
			String userName = pd.getString("username");//登陆名
			String password = pd.getString("password");//密码
			String registrationId = pd.getString("registrationId");//设备ID
			//空值校验
			if(userName.equals("")){
				return ResultUtils.returnError(12001, "无法获取username！");
			}
			if(password.equals("")){
				return ResultUtils.returnError(12001, "无法获取password！");
			}
			/*if(registrationId.equals("")){
				return ResultUtils.returnError(12001, "无法获取registrationId！");
			}*/
			password = new SimpleHash("SHA-1", userName, password).toString();
			pd.put("pwd", password);
			//校验登陆信息
			int result = (Integer) dao.findForObject("MobileLoginMapper.checkLoginResult", pd);
			System.out.println("============校验登录信息： " + result);
			if(result != 1)
				return ResultUtils.returnError(11002, "用户名或密码错误！");
			//生成token
			String token = JwtUtil.createJWT(60*60*24*30*1000, userName, password, jwt_token_key);
			//更新token
			//保存or更新设备ID(保证registrationId唯一)
			int checkResult = this.updateTokenAndResId(userName, registrationId, token);
			System.out.println("========================更新Token ： " + checkResult);
			if(checkResult == 0)
				return ResultUtils.returnError(-1, "后台更新token失败！");
			//设置user
			User userInfo = (User)dao.findForObject("UserMapper.getUserAndRoleByName", userName);
			System.out.println("=================userInfo.ROLE_NAME : " + userInfo.getROLE_NAME());
			this.setUser(userInfo);
			String tokenUserKey = Const.TOKEN_KEY_FRIST + userInfo.getUSERNAME() + Const.TOKEN_KEY_END;
			//放入session
			Session session = Jurisdiction.getSession();
			session.setAttribute(Const.SESSION_USERROL, userInfo);						//存入session	
			//记录积分
			/*String total_point = "0";
			String flag_exists = "0";
			Map pointResult = mobileInformationService.getPointsResult();
			if(pointResult != null &&  pointResult.size() > 0){
				total_point = StringHelper.get(pointResult, "total_point");
				total_point = total_point == "" ? "0" : total_point;
			}
			int total_point1 = Integer.parseInt(total_point);   //当日登陆总分
			if(total_point1 < 5){
				mobileInformationService.saveMypoints();
			}*/
				
			
			//记录登陆日志
			PageData chartPd = new PageData();
			String daynum = DateUtil.getSdfTimes();
			chartPd.put("id", this.get32UUID());
			chartPd.put("bill_day", daynum.substring(0, 8));
			chartPd.put("bill_hour", daynum.substring(8, 10));
			chartPd.put("user_id", userInfo.getUSER_ID());
			chartPd.put("org_id", userInfo.getOrg_id());
			chartPd.put("login_type","2");//1 PC； 2 移动端
			chartLogService.saveChartLogin(chartPd);//添加登录记录（统计报表使用）
			
			Map userMap = redisDaoImpl.getMap(tokenUserKey);
			System.out.println("================userMap : " + userMap);
			return ResultUtils.returnOk(userMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(-1, e.getMessage());
		}
	}
	
	/**
	 * 描述：更新设备ID
	 * @param username
	 * @param registrationId
	 * @param token
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-29
	 */
	public int updateResId(){
		PageData pd = this.getPageData();
		String userId = pd.getString("userId");//用户ID
		String registrationId = pd.getString("registrationId");//设备 
		if(userId.equals("")){
			return 0;
		}
		if(registrationId.equals("")){
			return 0;
		}
		try {
			dao.update("MobileLoginMapper.updateResInfo", pd);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * 描述：更新用户token及设备ID
	 * @param username
	 * @param registrationId
	 * @param token
	 * @return
	 * @author zhangcc
	 * @Date : 2019-05-16
	 */
	public int updateTokenAndResId(String username, String registrationId, String token){
		PageData pd = this.getPageData();
		pd.put("username", username);
		pd.put("registrationId", registrationId);
		pd.put("token", token);
		try {
			dao.update("MobileLoginMapper.updateUserInfo", pd);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * 描述：设置用户信息
	 * @param user
	 * @author zhangcc
	 * @Date : 2019-05-16
	 */
	public void setUser(User user){
		Map userInfo = new HashMap();
		String tokenUserKey = Const.TOKEN_KEY_FRIST + user.getUSERNAME() + Const.TOKEN_KEY_END;
		boolean isExistes =  redisDaoImpl.hasKey(tokenUserKey);
		System.out.println("====================isExistes " + isExistes);
		if(isExistes){//若存在，则删除
			redisDaoImpl.delete(tokenUserKey);
		}
		userInfo.put("user_id", user.getUSER_ID());
		userInfo.put("username", user.getUSERNAME());
		userInfo.put("password", user.getPASSWORD());
		userInfo.put("name", user.getNAME());
		userInfo.put("role_id", user.getROLE_ID());
		userInfo.put("token", user.getToken());
		userInfo.put("org_id", user.getOrg_id());
		userInfo.put("org_lev", user.getOrganization().getOrg_lev());
		userInfo.put("role_name", user.getROLE_NAME());
		userInfo.put("org_name", user.getOrg_name());
		userInfo.put("county_id", user.getCounty_id());
		userInfo.put("area_id", user.getArea_id());
		userInfo.put("industry", user.getIndustry());
		userInfo.put("is_need_certificate", user.getIS_NEED_CERTIFICATE());
		int role_lev = Integer.valueOf(user.getROLE_LEV());
		if(role_lev < 6){
			userInfo.put("is_gly", "1");
		}else{
			userInfo.put("is_gly", "0");
		}
		System.out.println("================userInfo : " + userInfo.toString());
		//设置缓存
		redisDaoImpl.addMap(tokenUserKey, userInfo);
		redisDaoImpl.expire(tokenUserKey,  60*60*24*30);
	}
	
}
