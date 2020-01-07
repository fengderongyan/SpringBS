package com.fh.service.weixin.mywx;

import java.util.Map;

import com.fh.util.PageData;

/**
 * 描述：微信用户
 * @author yanbs
 * DATA:2019年4月2日
 */
public interface WxLoginManager {
	
	//获取openid
	public Map getOpenid(String js_code);
	
	//绑定手机号
	public Map addOpenId(PageData pd) throws Exception;

	/**
	 * 描述：根据openid查找微信用户
	 * @param pd
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * DATA:2019-04-02
	 */
	public PageData findWxUserByOpenid(PageData pd) throws Exception;
}
