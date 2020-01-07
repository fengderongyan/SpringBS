package com.fh.service.weixin.mywx.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.service.weixin.mywx.WxLoginManager;
import com.fh.util.HttpClient;
import com.fh.util.JsonUtil;
import com.fh.util.PageData;
import com.fh.util.ResultUtils;
import com.fh.util.StringUtil;

@Service
public class WxLoginService implements WxLoginManager{
	
	@Value("${weixin.appid}")
	private String appid;
	@Value("${weixin.appsecret}")
	private String appsecret;
	@Value("${jscode2session}")
	private String jscode2session; 
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Override
	public Map getOpenid(String js_code) {
		try {
			System.out.println("js_code:" + js_code);
			String jscode2session_url = jscode2session.replace("APPID", appid).replace("SECRET", appsecret).replace("JSCODE", js_code);
			System.out.println("jscode2session_url:" + jscode2session_url);
			HttpClient httpClient = new HttpClient(jscode2session_url);
			httpClient.setHttps(true);
			httpClient.get();
			String resultXml = httpClient.getContent();
			System.out.println("resultXml:" + resultXml);
			Map resultMap = JsonUtil.parseStringMap(resultXml);
			return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 描述：绑定手机号
	 * @param js_code
	 * @param mobile
	 * @return
	 * @see com.fh.service.weixin.mywx.WxLoginManager#addOpenId(java.lang.String, java.lang.String)
	 * @author Administrator
	 * @throws Exception 
	 */
	@Override
	public Map addOpenId(PageData param){
		PageData pd = new PageData();
		try {
			pd = (PageData)dao.findForObject("WxUserMapper.findByMoible", param);
			String isMobileExists = pd.get("cnt").toString();
			if("0".equals(isMobileExists)){//手机号码不存在
				return ResultUtils.returnError(11001, "手机号在系统中不存在");
			}
			pd = (PageData)dao.findForObject("WxUserMapper.findByMoibleAndOpenid", param);
			String isUserExists = pd.get("cnt").toString();
			if("1".equals(isUserExists)){//用户已存在
				return ResultUtils.returnError(11005, "用户已存在");
			}
			Map resultMap = this.getOpenid(param.getString("js_code"));
			if(resultMap == null){
				return ResultUtils.returnError(15002, "数据库操作失败");
			}else{
				String openid = (String)resultMap.get("openid");
				if(StringUtils.isBlank(openid)){
					return resultMap;
				}else{
					param.put("openid", openid);
					dao.update("WxUserMapper.updateWxUserByMobile", param);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError(15002, "数据库操作失败");
		}
		
		return ResultUtils.returnOk();
	}

	@Override
	public PageData findWxUserByOpenid(PageData param) throws Exception {
		return (PageData)dao.findForObject("WxUserMapper.findWxUserByOpenid", param);
	}

	
}
