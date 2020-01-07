package com.fh.application;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Component;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.fh.dao.DaoSupport;
import com.fh.util.DateHelper;
import com.fh.util.HttpClient;
import com.fh.util.JsonUtil;

/** 
 * 定时任务执行
 * @date 2019-03-02 
 */
@Component
public class TimingOperation extends Thread{
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	private static final String TOH_KEY = "f36040e5cdfbab4479328cf4a903b2e3";//历史上的今天appkey
	
    /** 
     * 线程执行默认调用方法
     */ 
    public void run() {
        
        while(true) {  
        	//等待条件
        	String todaynum = DateHelper.getToday("yyyyMMddHH");
        	if(!"02".equals(todaynum.substring(8, 10))){//若不是早上5点则休眠50分钟
        		try {
                    Thread.sleep(1000 * 60 * 50);//休眠50分钟
                } catch (InterruptedException e) {
                    continue;
                }
        	}else{
        		
    			try {
    				String month = todaynum.substring(4,6);
    				String day = todaynum.substring(6,8);
    				String url = "http://api.juheapi.com/japi/toh?key=" + TOH_KEY + "&v=1.0&month=" + month + "&day=" + day;
            		HttpClient httpClient = new HttpClient(url);
        			httpClient.setHttps(true);
					httpClient.get();
					String resultJsonStr = httpClient.getContent();
					Map<String,Object> map =  (Map<String,Object>)JSONUtils.parse(resultJsonStr);
					Integer error_code = (Integer)map.get("error_code");
					if(error_code == 0){
						List<Map> result = (List<Map>)map.get("result");
						dao.delete("TimingOperationMapper.delAll", null);//全部清空
						dao.batchSave("TimingOperationMapper.saveResult", result);//批量插入
					}
					try {
	                    Thread.sleep(1000 * 60 * 50);//休眠50分钟
	                } catch (InterruptedException e) {
	                    continue;
	                }
					
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