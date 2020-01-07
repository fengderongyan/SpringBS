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
						<label class="layui-form-label">大类名称</label>
						<div class="layui-input-block">
							<input type="text" name="data_type_name" placeholder="请输入"
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
	<c:if test="${QX.edit == 1}">
    	<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</a>
	</c:if>
	<c:if test="${QX.del == 1 }">
    	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon layui-icon-delete"></i>删除</a>
	</c:if>
</div>
<div type="text/html" id="toolbar" style="display:none" >
  <c:if test="${QX.add == 1}">
	  <div class="layui-btn-container">
	    <button class="layui-btn layui-btn-sm" lay-event="add"> <i class="layui-icon">&#xe654;</i>添加</button>
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
			field : 'id',
			title : '编码',
			hide:true
		}, {
			field : 'data_type_name',
			title : '大类名称',
			align : 'center'
		}, {
			field : 'data_type_code',
			title : '大类编码',
			align : 'center'
		}, 
		{
			field : 'dd_item_name',
			title : '小类名称',
			align : 'center'
		}, 
		{
			field : 'dd_item_code',
			title : '小类编码',
			align : 'center'
		}, 
		{
			field : 'dd_lev',
			title : '级别',
			align : 'center'
		}, 
		{
			title : '操作',
			toolbar : '#trTool',
			width : 200,
			align : 'center',
			fixed: 'right'
		} ] ];
		
		webplus.init('<%=basePath%>ddw/getDdwList',cols);
		 //行单击事件
		  table.on('row(dataList)', function(obj){
			  obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
		  });
	      
		 //监听行工具事件
		  table.on('tool(dataList)', function(obj){
		    var data = obj.data;
		    //判断操作的按钮类别  需要在标签上配置  lay-event='判断的类型值'
		    switch(obj.event){
		    	case 'edit':
		    		//打开编辑弹出框
		    	   if(webplus.checkRowEditMode(obj)){
		    	     webplus.openWindowBase('<%=basePath%>ddw/toEdit?id=' + data.id,'编辑','500','450');
		    	   }
		   			break;
		    	
		    	case 'del':
		    		if(webplus.checkRowEditMode(obj)){
		    			var params = {id : data.id};
		    			webplus.doAjax('<%=basePath%>ddw/delete',params,'确定删除吗？','','0');//0：刷新当前页面，1：刷新父级页面
		    		}
		    		break;
		    }
		  });
		 
		 //监听表头按钮事件
		  table.on('toolbar(dataList)', function(obj){
		    var checkStatus = table.checkStatus(obj.config.id);
		    switch(obj.event){
		      	case 'add':
			         //打开添加弹出框
			         webplus.openWindowBase('<%=basePath%>ddw/toAdd','新增','500','450');
		     	     break;
		    }
		  });	
			

		});
	</script>
  
  
  </script>
</body>
</html>
