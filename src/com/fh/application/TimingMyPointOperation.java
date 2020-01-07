package com.fh.application;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.fh.dao.DaoSupport;
import com.fh.util.DateHelper;
import com.fh.util.HttpClient;
import com.fh.util.JsonUtil;
import com.fh.util.PageData;
import com.fh.util.StringHelper;
import com.fh.util.JPush.JPushUtil;

/** 
 * 定时任务执行
 * @date 2019-03-02 
 */
@Component
public class TimingMyPointOperation extends Thread{
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
    /** 
     * 线程执行默认调用方法
     */ 
    public void run() {
        while(true) {  
        	//等待条件
        	String todaynum = DateHelper.getToday("yyyyMMddHH");
        	if(!"10".equals(todaynum.substring(8, 10))){//若不是早上10点则休眠50分钟
        		try {
                    Thread.sleep(1000 * 60 * 50);//休眠50分钟
                } catch (InterruptedException e) {
                    continue;
                }
        	}else{
    			try {
    				Map paramMap = new HashMap();
    				String week = StringHelper.get((Map)dao.findForObject("TimingOperationMapper.getThisWeek", paramMap), "week");
    				System.out.println("============================week : " + week);
    				if(week.equals("7")){//周日
    					//极光推送信息
    					List infoList = (List) dao.findForList("TimingOperationMapper.getJpushInfos", paramMap);
    					for(int j = 0; j < infoList.size(); j ++){
    						Map infoMap = (Map) infoList.get(j);
        					if(!(infoMap == null)){//正确返回
        						String registrationId = StringHelper.get(infoMap, "registration_id");
            					String point = StringHelper.get(infoMap, "points");
            					String userId = StringHelper.get(infoMap, "user_id");
        						String title = "您有一条积分消息";
        						String pushContent = "";
        						PageData pd = new PageData();
        						pd.put("title", title);
        						pd.put("content", pushContent);
        						if(!registrationId.equals(null)){//非空
     							   pushContent = "您本周积分是：" + point + " 分";
     							   JPushUtil.pushMsg(registrationId, pushContent, "link", "", title);
     							   pd.put("user_id", userId);
     							   dao.save("PushInfoMapper.saveLog", pd);
     						   }
        					}
    					}
    				}
    				Thread.sleep(1000 * 60 * 50);//休眠50分钟
				} catch (Exception e1) {
					e1.printStackTrace();
					try {
	                    Thread.sleep(1000 * 60 * 50);//休眠50分钟
	                } catch (Exception e2) {
	                    continue;
	                }
				} 
        		
        		
        	}
        	
            
        }
   }
    
}