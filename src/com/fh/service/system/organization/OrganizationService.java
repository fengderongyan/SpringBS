package com.fh.service.system.organization;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PHelper;
import com.fh.util.PageData;

/** 
 * 类名称：RoleController 组织管理
 * 创建人：FH Q313596790
 * 修改时间：2018年7月6日
 * @version
 */
@Service
public class OrganizationService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 描述：获取组织列表
	 * @param pd
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-04-26
	 */
	public List<PageData> getOrgList(PageData pd) throws Exception{
		PHelper.startPage(pd);
		return (List<PageData>)dao.findForList("OrganizationMapper.getOrgList", pd);
	}

	/**
	 * 描述：获取县区信息
	 * @param pd
	 * @author yanbs
	 * @Date : 2019-04-26
	 */
	public List<PageData> getCountyList(PageData pd) throws Exception{
		
		return (List<PageData>)dao.findForList("OrganizationMapper.getCountyList", pd);
	}

	public List<PageData> getAddType(PageData pd) throws Exception{
		
		return (List<PageData>)dao.findForList("OrganizationMapper.getAddType", pd);
	}

	/**
	 * 描述：保存组织信息
	 * @param pd
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-04-28
	 */
	public void saveOrg(PageData pd) throws Exception{
		dao.save("OrganizationMapper.saveOrg", pd);
	}

	/**
	 * 描述：获取组织信息
	 * @param pd
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-04-28
	 */
	public PageData getOrgMap(PageData pd) throws Exception{
		return (PageData)dao.findForObject("OrganizationMapper.getOrgMap", pd);
	}

	/**
	 * 描述：保存编辑信息
	 * @param pd
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-04-28
	 */
	public void saveEditOrg(PageData pd) throws Exception{
		dao.update("OrganizationMapper.saveEditOrg", pd);
		
	}

	/**
	 * 描述：删除组织信息
	 * @param pd
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-04-28
	 */
	public void delOrg(PageData pd) throws Exception{
		dao.update("OrganizationMapper.delOrg", pd);
		
	}
	
	/**
	 * 描述：判断是否有下级组织
	 * @param pd
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-04-28
	 */
	public boolean isHavingChildOrg(PageData pd) throws Exception{
		List<PageData> childOrgList = (List<PageData>)dao.findForList("OrganizationMapper.getCildOrgList", pd);
		if(childOrgList != null && childOrgList.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 描述：获取行业信息
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-04-28
	 */
	public List<PageData> getIndustryList() throws Exception{
		return (List<PageData>)dao.findForList("OrganizationMapper.getIndustryList", null);
	}

	public List<PageData> getAreaListByCountyId(PageData pd) throws Exception{
		return (List<PageData>)dao.findForList("OrganizationMapper.getAreaListByCountyId", pd);
	}
	
	
	
	
}