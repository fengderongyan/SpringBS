package com.fh.util;

import com.github.pagehelper.PageHelper;

/** 
 * 说明：日期处理
 * 创建人：FH Q313596790
 * 修改时间：2018年3月22日
 * @version
 */
public class PHelper {
	
	public static void startPage(PageData pd){
		PageHelper.startPage(Integer.parseInt(pd.getString("page")), Integer.parseInt(pd.getString("limit")));
	}
}
