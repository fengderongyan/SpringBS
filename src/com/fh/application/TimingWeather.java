package com.fh.application;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
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
public class TimingWeather extends Thread{
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	private static final String TOH_KEY = "814304b223fd221e76fcc1984de81a06";//历史上的今天appkey
	
    /** 
     * 线程执行默认调用方法
     */ 
    public void run() {
        
        while(true) {  
        	//等待条件
        	String todaynum = DateHelper.getToday("yyyyMMddHH");
        	if(false){//若不是早上5点则休眠50分钟
        		try {
                    Thread.sleep(1000 * 60 * 50);//休眠50分钟
                } catch (InterruptedException e) {
                    continue;
                }
        	}else{
    			try {
    				Thread.sleep(1000 * 60 * 50);//休眠50分钟
    				String url = "http://apis.juhe.cn/simpleWeather/query?city=" + URLEncoder.encode("连云港", "UTF-8") + "&key=" + TOH_KEY;
            		HttpClient httpClient = new HttpClient(url);
        			httpClient.setHttps(true);
					httpClient.get();
					String resultJsonStr = httpClient.getContent();
					System.out.println("================================resultJsonStr " + resultJsonStr);
					Map param = new HashMap();
					param.put("content", resultJsonStr);
					Map<String,Object> map =  (Map<String,Object>)JSONUtils.parse(resultJsonStr);
					Integer error_code = (Integer)map.get("error_code");
					if(error_code == 0){
						dao.delete("TimingOperationMapper.delWeatherAll", null);//全部清空
						dao.save("TimingOperationMapper.saveWeatherResult", param);//批量插入
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
						System.out.println("=============================================");
	                } catch (Exception e2) {
	                    continue;
	                }
				} 
        		
        		
        	}
        	
            
        }
   }
    
}