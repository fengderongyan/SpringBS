<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<%@ include file="/static/layuiadmin/include.jsp" %>
<body>
  <div class="layui-fluid">
		<div class="layui-card">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto" id="searchForm">
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">组织名称</label>
						<div class="layui-input-block">
							<input type="text" name="org_name" placeholder="请输入"
								autocomplete="off" class="layui-input">
						</div>
					</div>
					<div class="layui-inline">
						<button class="layui-btn layuiadmin-btn-sm" lay-submit
							lay-filter="searchSubmit" >查询</button>
					</div>
				</div>
			</div>
			<div class="layui-card-body">
				<table id="dataList" lay-filter="dataList" ></table>
			</div>
		</div>
	</div>
<div type="text/html" id="trTool" style="display:none">
	<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="show"><i class="layui-icon layui-icon-edit"></i>详情</a>
	<c:if test="${QX.edit == 1}">
		{{# if(d.flag_edit == 1){ }}
    	<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</a>
    	{{# } }}
	</c:if>
	<c:if test="${QX.del == 1}">
		{{# if(d.flag_edit == 1){ }}
    	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon layui-icon-delete"></i>删除</a>
		{{# } }}
	</c:if>
</div>
<div type="text/html" id="toolbar" style="display:none" >
  <c:if test="${QX.add == 1}">
	  <div class="layui-btn-container">
	  	<c:if test="${USERROL.getRole().getROLE_LEV() == 0}">
	    	<button class="layui-btn layui-btn-sm" lay-event="addCounty"> 添加县区</button>
	  	</c:if>
	  	<c:if test="${USERROL.getRole().getROLE_LEV() == 0 || USERROL.getRole().getROLE_LEV() == 3}">
	    	<button class="layui-btn layui-btn-sm" lay-event="addArea"> 添加园区</button>
	  	</c:if>
	  	<c:if test="${USERROL.getRole().getROLE_LEV() == 0 || USERROL.getRole().getROLE_LEV() == 3 || USERROL.getRole().getROLE_LEV() == 4}">
	   		 <button class="layui-btn layui-btn-sm" lay-event="addOrg"> 添加企业</button>
	  	</c:if>
	  </div>
  </c:if>
</div>
<script>
	layui.config({
		base :  'static/layuiadmin/' //静态资源所在路径
	}).extend({
		webplus  : 'lib/webplus' //主入口模块
	}).use([ 'table', 'form','webplus' ], function() {
		var $ = layui.$, 
		form = layui.form, 
		table = layui.table,
		webplus=layui.webplus;
		var cols=[ [ {
			type : 'numbers',
			title : '序号',
			width : 50
		},{
			field : 'org_id',
			title : '组织编码',
			hide:true
		}, {
			field : 'org_name',
			title : '企业名称',
			align : 'center'
		},{
			field : 'province_name',
			title : '归属省份',
			hide : true,
			align : 'center'
		}, 
		{
			field : 'city_name',
			title : '归属城市',
			hide : true,
			align : 'center'
		}, 
		{
			field : 'org_lev',
			title : '组织等级',
			hide : true,
			align : 'center'
		}, 
		{
			field : 'county_name',
			title : '归属县区',
			align : 'center'
		}, 
		{
			field : 'county_id',
			title : '归属县区编码',
			hide : true,
		},
		{
			field : 'area_name',
			title : '归属园区',
			align : 'center'
		},{
			field : 'industry_name',
			title : '所属行业',
			align : 'center'
		},{
			title : '操作',
			toolbar : '#trTool',
			width : 210,
			align : 'left',
			fixed: 'right'
		} ] ];
		
		webplus.init('<%=basePath%>org/getOrgList',cols);
		 //行单击事件
		  table.on('row(dataList)', function(obj){
			  obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
		  });
	      
		 //监听行工具事件
		  table.on('tool(dataList)', function(obj){
		    var data = obj.data;
		    //判断操作的按钮类别  需要在标签上配置  lay-event='判断的类型值'
		    switch(obj.event){
		    	case 'show':
		    		//打开详情弹出框
		    	   if(webplus.checkRowEditMode(obj)){
		    		   var w = '500', h = '450';
			    		  if(data.org_lev == 5){
			    			  w = '800';
			    			  h = '750';
			    		}
		    	     webplus.openWindowBase('<%=basePath%>org/toEdit?method=show&org_id=' + data.org_id + 
		    	    		 '&org_lev=' + data.org_lev + '&county_id=' + data.county_id,'详情',w, h, 1);//1：打开详情， 默认打开新增、编辑
		    	   }
		   			break;
		    	case 'edit':
		    		//打开编辑弹出框
		    	   if(webplus.checkRowEditMode(obj)){
		    		  var w = '500', h = '450';
		    		  if(data.org_lev == 5){
		    			  w = '800';
		    			  h = '750';
		    		  }
		    	     webplus.openWindowBase('<%=basePath%>org/toEdit?method=edit&org_id=' + data.org_id + 
		    	    		 '&org_lev=' + data.org_lev + '&county_id=' + data.county_id,'编辑', w, h);
		    	   }
		   			break;
		    	
		    	case 'del':
		    		if(webplus.checkRowEditMode(obj)){
		    			var params = {org_id : data.org_id, org_lev : data.org_lev};
		    			webplus.doAjax('<%=basePath%>org/delOrg',params,'确定删除吗？','','0');//0：刷新当前页面，1：刷新父级页面
		    		}
		    		break;
		    }
		  });
		 
		 //监听表头按钮事件
		  table.on('toolbar(dataList)', function(obj){
		    var checkStatus = table.checkStatus(obj.config.id);
		    switch(obj.event){
		      	case 'addCounty':
			         //添加县区
			         webplus.openWindowBase('<%=basePath%>org/toAdd?flag=addCounty','添加县区','500','450');
		     	     break;
		      	case 'addArea':
			         //添加园区
			         webplus.openWindowBase('<%=basePath%>org/toAdd?flag=addArea','添加园区','500','450');
		     	     break;
		      	case 'addOrg':
			         //添加企业
			         webplus.openWindowBase('<%=basePath%>org/toAdd?flag=addOrg','添加企业','800','750');
		     	     break;
		    }
		  });	
			

		});
	</script>
  
  
  </script>
</body>
</html>
