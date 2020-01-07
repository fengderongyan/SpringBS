package com.fh.service.system.menu.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.Menu;
import com.fh.service.system.menu.MenuManager;
import com.fh.util.PHelper;
import com.fh.util.PageData;
import com.github.pagehelper.PageHelper;

/** 
 * 类名称：MenuService 菜单处理
 * 创建人：FH qq  3  1 3 5 9 6 7 9 0[青苔]
 * @version v2
 */
@Service("menuService")
public class MenuService implements MenuManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 通过ID获取其子一级菜单
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> listPageSubMenuByParentId(PageData pd) throws Exception {
		PHelper.startPage(pd);
		return (List<Menu>) dao.findForList("MenuMapper.listSubMenuByParentId", pd.getString("MENU_ID"));
	}
	@SuppressWarnings("unchecked")
	public List<Menu> listSubMenuByParentId(String MENU_ID) throws Exception {
		return (List<Menu>) dao.findForList("MenuMapper.listSubMenuByParentId", MENU_ID);
	}
	
	/**
	 * 描述：获取所有菜单
	 * @return
	 * @throws Exception
	 * @author yanbs
	 * @Date : 2019-04-25
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> listAllMenus(PageData pd) throws Exception{
		//分页查询
		PHelper.startPage(pd);
		return (List<Menu>) dao.findForList("MenuMapper.listAllMenus", null);
	}
	
	/**
	 * 通过菜单ID获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getMenuById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MenuMapper.getMenuById", pd);
	}
	
	/**
	 * 新增菜单
	 * @param menu
	 * @throws Exception
	 */
	public void saveMenu(Menu menu) throws Exception {
		dao.save("MenuMapper.insertMenu", menu);
	}
	
	/**
	 * 取最大ID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findMaxId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MenuMapper.findMaxId", pd);
	}
	
	/**
	 * 删除菜单
	 * @param MENU_ID
	 * @throws Exception
	 */
	public void deleteMenuById(String MENU_ID) throws Exception {
		dao.save("MenuMapper.deleteMenuById", MENU_ID);
	}
	
	/**
	 * 编辑
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	public void edit(Menu menu) throws Exception {
		 dao.update("MenuMapper.updateMenu", menu);
	}
	
	/**
	 * 保存菜单图标 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData editicon(PageData pd) throws Exception {
		return (PageData)dao.findForObject("MenuMapper.editicon", pd);
	}
	
	/**
	 * 获取所有菜单并填充每个菜单的子菜单列表(菜单管理)(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listAllMenu(String MENU_ID) throws Exception {
		List<Menu> menuList = this.listSubMenuByParentId(MENU_ID);
		for(Menu menu : menuList){
			menu.setMENU_URL("menu.do?MENU_ID="+menu.getMENU_ID());
			menu.setSubMenu(this.listAllMenu(menu.getMENU_ID()));
			menu.setTarget("treeFrame");
		}
		return menuList;
	}

	/**
	 * 获取所有菜单并填充每个菜单的子菜单列表(系统菜单列表)(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listAllMenuQx(String MENU_ID) throws Exception {
		List<Menu> menuList = this.listSubMenuByParentId(MENU_ID);
		for(Menu menu : menuList){
			menu.setSubMenu(this.listAllMenuQx(menu.getMENU_ID()));
			menu.setTarget("treeFrame");
		}
		return menuList;
	}
	@Override
	public void updateState(PageData pd) throws Exception {
		 dao.update("MenuMapper.updateState", pd);
	}
	
}
