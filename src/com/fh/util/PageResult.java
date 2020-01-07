package com.fh.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.util.JSONPObject;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/** 接口参数校验
 * @author:	fh qq313596790[青苔]
 * 修改日期：2017/11/2
 */
public class PageResult  {
	
	public static Map pageMap(List list){
		PageInfo pi = new PageInfo(list);
		Map map = new HashMap();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", pi.getTotal());
		map.put("data", pi.getList());
		return map;
	}
	
}
