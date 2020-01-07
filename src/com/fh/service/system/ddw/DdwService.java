package com.fh.service.system.ddw;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PHelper;
import com.fh.util.PageData;

/** 
 * 类名称：RoleController 角色权限管理
 * 创建人：FH Q313596790
 * 修改时间：2018年7月6日
 * @version
 */
@Service
public class DdwService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public List<PageData> getDdwList(PageData pd) throws Exception{
		PHelper.startPage(pd);
		return (List<PageData>)dao.findForList("DdwMapper.getDdwList", pd);
	}
	
	/**
	 * 描述：获取大类列表
	 * @return
	 * @author yanbs
	 * @Date : 2019-04-25
	 */
	public List getTypeList() throws Exception{
		return (List<PageData>)dao.findForList("DdwMapper.getTypeList", null);
	}
	
}