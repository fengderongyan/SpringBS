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
						<label class="layui-form-label">用户名</label>
						<div class="layui-input-block">
							<input type="text" name="username" placeholder="请输入"
								autocomplete="off" class="layui-input">
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">姓名</label>
						<div class="layui-input-block">
							<input type="text" name="name" placeholder="请输入"
								autocomplete="off" class="layui-input">
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">手机号码</label>
						<div class="layui-input-block">
							<input type="text" name="phone" placeholder="请输入"
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
		{{# if (d.username != 'admin'){ }}
    	<a class="layui-btn layui-btn-success layui-btn-xs" lay-event="resetPwd"><i class="layui-icon layui-icon-auz"></i>重置密码</a>
    	{{# } }}
	</c:if>
	{{# if (d.username != 'admin'){ }}
	<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="show"><i class="layui-icon layui-icon-edit"></i>详情</a>
	{{# } }}
	<c:if test="${QX.edit == 1}">
		{{# if (d.username != 'admin'){ }}
    	<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</a>
    	{{# } }}
	</c:if>
	
	<c:if test="${QX.del == 1}">
		{{# if (d.username != 'admin'){ }}
    	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon layui-icon-delete"></i>删除</a>
    	{{# } }}
	</c:if>
</div>
<div type="text/html" id="toolbar" style="display:none" >
  <c:if test="${QX.add == 1}">
	  <div class="layui-btn-container">
	  	<c:if test="${USERROL.getRole().getROLE_LEV() < 5 }">
	  		<button class="layui-btn layui-btn-sm" lay-event="addSysManager">添加管理员</button>
	  	</c:if>
	  	<button class="layui-btn layui-btn-sm" lay-event="addSysUser">添加员工</button>
	  	<button class="layui-btn layui-btn-sm" lay-event="upLoadSysUser">批量导入员工</button>
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
			field : 'user_id',
			hide:true
		},{
			field : 'role_lev',
			hide:true
		},{
			field : 'username',
			title : '用户名',
			align : 'center'
		},{
			field : 'name',
			title : '姓名',
			align : 'center'
		},{
			field : 'role_name',
			title : '角色',
			align : 'center'
		},{
			field : 'org_name',
			title : '所属组织',
			align : 'center'
		},{
			field : 'phone',
			title : '手机号码',
			align : 'center'
		},{
			field : 'last_login',
			title : '最近登录时间',
			align : 'center'
		},{
			title : '操作',
			toolbar : '#trTool',
			width : 300,
			align : 'left',
			fixed: 'right'
		} ] ];
		
		webplus.init('<%=basePath%>sysuser/getSysUserList',cols);
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
		    		 var role_lev = data.role_lev;
		    		 var title = '管理员详情';
		    		 var url = '<%=basePath%>sysuser/editSysManager?user_id='+ data.user_id;
		    		 var w = '500';
		    		 var h = '550';
		    		 if(role_lev > 5){//员工
		    			 title = '员工详情';
		    			 url = '<%=basePath%>sysuser/editSysUser?user_id='+ data.user_id;
		    			 w = '750';
		    		 	 h = '550';
		    		 }
		    		 webplus.openWindowBase(url, title, w, h, '1');
		    	   }
		   			break;
		    	case 'edit':
		    		//打开编辑弹出框
		    	   if(webplus.checkRowEditMode(obj)){
		    		   var role_lev = data.role_lev;
			    		 var title = '管理员详情';
			    		 var url = '<%=basePath%>sysuser/editSysManager?user_id='+ data.user_id;
			    		 var w = '500';
			    		 var h = '550';
			    		 if(role_lev > 5){//员工
			    			 title = '员工详情';
			    			 url = '<%=basePath%>sysuser/editSysUser?user_id='+ data.user_id;
			    			 w = '750';
			    		 	 h = '550';
			    		 }
			    		 webplus.openWindowBase(url, title, w, h);
		    	   }
		   			break;
		    	
		    	case 'del':
		    		if(webplus.checkRowEditMode(obj)){
		    			var params = {user_id : data.user_id};
		    			webplus.doAjax('<%=basePath%>sysuser/delUser',params,'确定删除吗？','','0');//0：刷新当前页面，1：刷新父级页面
		    		}
		    		break;
		    	case 'resetPwd':
		    		if(webplus.checkRowEditMode(obj)){
		    			var params = {user_id : data.user_id,username:data.username};
		    			webplus.doAjax('<%=basePath%>sysuser/resetPwd',params,'确定重置密码吗（重置密码123456）？','','0');//0：刷新当前页面，1：刷新父级页面
		    		}
		    		break;
		    }
		  });
		 
		 //监听表头按钮事件
		  table.on('toolbar(dataList)', function(obj){
		    var checkStatus = table.checkStatus(obj.config.id);
		    switch(obj.event){
		      	case 'addSysManager':
			         //添加管理员 
			         webplus.openWindowBase('<%=basePath%>sysuser/addSysManager','添加管理员','500','550');
		     	     break;
		      	case 'addSysUser':
			         //添加用户
			         webplus.openWindowBase('<%=basePath%>sysuser/addSysUser','添加用户','750','550');
		     	     break;
		      	case 'upLoadSysUser':
			         //批量导入
			         webplus.openWindowBase('<%=basePath%>sysuser/upLoadSysUser','批量导入','500','550', '1');
		     	     break;
		    }
		  });	
			

		});
	</script>
  
  
  </script>
</body>
</html>
