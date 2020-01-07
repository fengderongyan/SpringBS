package com.fh.util;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.druid.proxy.jdbc.ClobProxyImpl;
/** 
 * 说明：参数封装Map
 * 创建人：FH Q313596790
 * 修改时间：2018年7月26日
 * @version
 */
public class BatchSql{
	
	private List sqlList = new ArrayList();
	
	public List getSqlList() {
		return sqlList;
	}

	public void setSqlList(List sqlList) {
		this.sqlList = sqlList;
	}

	public void addBatch(String sqlMapper, String method, Map<String, Object> pd) {
		PageData tmp = new PageData();
		pd.put("sqlMapper", sqlMapper);
		pd.put("method", method);
		for (Map.Entry<String, Object> entry : pd.entrySet()) {
            //Map.entry<Integer,String> 映射项（键-值对）  有几个方法：用上面的名字entry
            //entry.getKey() ;entry.getValue(); entry.setValue();
            //map.entrySet()  返回此映射中包含的映射关系的 Set视图。
            tmp.put(entry.getKey(), entry.getValue());
            
		}
		sqlList.add(tmp);
	}
	
	public void addBatch(String sqlMapper, String method) {
		PageData pd = new PageData();
		pd.put("sqlMapper", sqlMapper);
		pd.put("method", method);
		sqlList.add(pd);
	}
	
	
	/** 
	 * @description 清除批处理中记录
	 */ 
	public void clearBatch(){
	    this.sqlList.clear();
	}
	
}
