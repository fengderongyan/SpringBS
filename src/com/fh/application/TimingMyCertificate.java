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
public class TimingMyCertificate extends Thread{
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
    /** 
     * 线程执行默认调用方法
     */ 
    public void run() {
        while(true) {  
        	//等待条件
        	String todaynum = DateHelper.getToday("yyyyMMddHH");
        	if(Integer.valueOf(todaynum.substring(8, 10)) < 10){//早上10点前不提醒
        		try {
                    Thread.sleep(1000 * 60 * 60 * 2);//休眠小时
                } catch (InterruptedException e) {
                    continue;
                }
        	}else{
    			try {
    				Map paramMap = new HashMap();
    				//证书将要过期
    				List list = (List) dao.findForList("TimingOperationMapper.getJpushIds", paramMap);
    				String title = "您有一条证书消息";
					String pushContent = "您的证书还有一周过期,请及时处理";
    				for(int i = 0; i < list.size(); i ++){
    					Map IdsMap = (Map) list.get(i);
    					if(IdsMap != null){
        					String registrationId = StringHelper.get(IdsMap, "registration_id");
        					String userId = StringHelper.get(IdsMap, "user_id");
        					title = "您有一条证书消息";
        					pushContent = "您的证书还有一周到期,请及时处理";
        					//极光推送信息
    						PageData pd = new PageData();
    						pd.put("title", title);
    						pd.put("content", pushContent);
    						if(!registrationId.equals(null) || "".equals(registrationId)){//非空
    						   JPushUtil.pushMsg(registrationId, pushContent, "link", "", title);
    						   dao.save("PushInfoMapper.saveLog", pd);
    					   }
        				}
    				}
    				//证书当天过期
    				list = (List)dao.findForList("TimingOperationMapper.getTodayJpushIds", paramMap);
    				for(int i = 0; i < list.size(); i ++){
    					Map TodayIdsMap = (Map) list.get(i);
    					if(TodayIdsMap != null){
        					String registrationId = StringHelper.get(TodayIdsMap, "registration_id");
        					String userId = StringHelper.get(TodayIdsMap, "user_id");
        					title = "您有一条证书消息";
        					pushContent = "您的证书今天到期,请及时处理";
        					//极光推送信息
    						PageData pd = new PageData();
    						pd.put("title", title);
    						pd.put("content", pushContent);
    						if(!registrationId.equals(null)){//非空
    						   JPushUtil.pushMsg(registrationId, pushContent, "link", "", title);
    						   dao.save("PushInfoMapper.saveLog", pd);
    					   }
        				}
    				}
    				//证书过期一周
    				list = (List)dao.findForList("TimingOperationMapper.getNextJpushIds", paramMap);
    				for(int i = 0; i < list.size(); i ++){
    					Map nextIdsMap = (Map) list.get(i);
    					if(nextIdsMap != null){
        					String registrationId = StringHelper.get(nextIdsMap, "registration_id");
        					String userId = StringHelper.get(nextIdsMap, "user_id");
        					title = "您有一条证书消息";
        					pushContent = "您的证书于一周前已经到期,请及时处理";
        					//极光推送信息
    						PageData pd = new PageData();
    						pd.put("title", title);
    						pd.put("content", pushContent);
    						if(!registrationId.equals(null)){//非空
    						   JPushUtil.pushMsg(registrationId, pushContent, "link", "", title);
    						   dao.save("PushInfoMapper.saveLog", pd);
    					   }
        				}
    				}
    				Thread.sleep(1000 * 60 * 60 * 2);//休眠2小时
				} catch (Exception e1) {
					e1.printStackTrace();
					try {
	                    Thread.sleep(1000 * 60 * 60 * 2);//休眠2小时
	                } catch (Exception e2) {
	                    continue;
	                }
				} 
        		
        		
        	}
        	
            
        }
   }
    
}