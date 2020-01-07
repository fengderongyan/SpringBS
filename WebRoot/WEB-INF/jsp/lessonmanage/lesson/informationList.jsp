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
						<label class="layui-form-label">课程标题</label>
						<div class="layui-input-block">
							<input type="text" name="title" placeholder="请输入"
								autocomplete="off" class="layui-input">
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">课程类型</label>
						<div class="layui-input-block">
							<select name="type">
								<option value="">请选择...</option>
						      	<c:forEach items="${lessonTypeList}" var="type">
						      		<option value="${type.id }">${type.title} </option>
						      	</c:forEach>
						      </select>
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
	<c:if test="${QX.edit == 1  }">
		{{# if (d.is_edit == '1' || d.login_id == '63e0a45f717111e9bdc600163e1024a6' ){ }}
    	<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</a>
    	{{# } }}
	</c:if>
	<c:if test="${QX.del == 1}">
		{{# if (d.is_edit == '1' || d.login_id == '63e0a45f717111e9bdc600163e1024a6' ){ }}
    	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon layui-icon-delete"></i>删除</a>
    	{{# } }}
	</c:if>
</div>

<div type="text/html" id="cover_img" style="display:none">
	<img class="layui-upload-img" id="cover_img_url" src="{{ d.cover }}"  width="70px">
</div>

<div type="text/html" id="toolbar" style="display:none" >
  <c:if test="${QX.add == 1}">
	  <div class="layui-btn-container">
  		<button class="layui-btn layui-btn-sm" lay-event="addInformation"><i class="layui-icon">&#xe654;</i>新增课程</button>
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
			hide:true
		},{
			field : 'rec_id',
			hide:true
		},{
			field : 'cover',
			hide:true
		},{
			field : 'login_id',
			hide:true
		},{
			field : 'is_edit',
			hide:true
		},{
			field : 'title',
			title : '课程标题',
			align : 'center'
		},{
			field : 'type_name',
			title : '课程类型',
			align : 'center'
		},{
			field : 'introduction',
			title : '简介',
			align : 'center'
		},{
			title : '课程封面',
			toolbar : '#cover_img',
			width : 100,
			align : 'center',
		},{
			field : 'username',
			title : '录入人',
			align : 'center'
		},{
			field : 'rec_date',
			title : '录入时间',
			align : 'center'
		},{
			title : '操作',
			toolbar : '#trTool',
			width : 210,
			align : 'left',
			fixed: 'right'
		} ] ];
		
		webplus.init('<%=basePath%>lessonmanage/lesson/getInformaticaManageList',cols);
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
		    		 var title = '课程信息详情';
		    		 var url = '<%=basePath%>lessonmanage/lesson/editInformationManager?id='+ data.id;
		    		 var w = '900';
		    		 var h = '550';
		    		 webplus.openWindowBase(url, title, w, h, '1');
		    	   }
		   			break;
		    	case 'edit':
		    		//打开编辑弹出框
		    	   if(webplus.checkRowEditMode(obj)){
		    		   var role_lev = data.role_lev;
			    		 var title = '课程信息编辑';
			    		 var url = '<%=basePath%>lessonmanage/lesson/editInformationManager?id='+ data.id;
			    		 var w = '900';
			    		 var h = '550';
			    		 webplus.openWindowBase(url, title, w, h);
		    	   }
		   			break;
		    	case 'del':
		    		if(webplus.checkRowEditMode(obj)){
		    			var params = {user_id : data.user_id};
		    			webplus.doAjax('<%=basePath%>lessonmanage/lesson/delInformation?id=' + data.id ,params,'确定删除吗？','','0');//0：刷新当前页面，1：刷新父级页面
		    		}
		    		break;
		    }
		  });
		 
		 //监听表头按钮事件
		  table.on('toolbar(dataList)', function(obj){
		    var checkStatus = table.checkStatus(obj.config.id);
		    switch(obj.event){
		      	case 'addInformation':
			        //添加资讯信息
			        webplus.openWindowBase('<%=basePath%>lessonmanage/lesson/addInformation','新增课程信息','900','550');
	     	    break;
		    }
		  });	

		});
	</script>
  
  
  </script>
</body>
</html>
