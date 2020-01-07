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
					<div class="layui-inline" style="width: 260px">
						<label class="layui-form-label" style="width: 100px">题目适用行业</label>
						<div class="layui-input-block">
							<select name="industry">
								<c:forEach items="${industryList }" var="industryMap">
									<option value="${industryMap.dd_item_code }">${industryMap.dd_item_name }</option>
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
	<c:if test="${QX.edit == 1}">
		{{# if (d.flag_edit == 1) { }}
    	<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</a>
    	{{# } }}
	</c:if>
	<c:if test="${QX.del == 1}">
		{{# if (d.flag_edit == 1) { }}
    	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon layui-icon-delete"></i>删除</a>
    	{{# } }}
	</c:if>
</div>
<div type="text/html" id="toolbar" style="display:none" >
  <c:if test="${QX.add == 1}">
	  <div class="layui-btn-container">
	  	<button class="layui-btn layui-btn-sm" lay-event="addQuestion">新增试题</button>
	  	<button class="layui-btn layui-btn-sm" lay-event="impQuestion">批量导入</button>
	  </div>
  </c:if>
</div>

<script>
	layui.config({
		base :  'static/layuiadmin/' //静态资源所在路径
	}).extend({
		webplus  : 'lib/webplus' //主入口模块
	}).use([ 'table', 'form','webplus'], function() {
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
			field : 'flag_edit',
			hide:true
		},{
			field : 'question_info',
			title : '题目',
			width: 500,
			align : 'center'
		},{
			field : 'industry_name',
			title : '题目适用行业',
			align : 'center'
		},{
			field : 'source',
			title : '教材来源',
			align : 'center'
		},{
			field : 'type',
			title : '试题类型',
			align : 'center'
		},{
			title : '操作',
			toolbar : '#trTool',
			width : 210,
			align : 'left',
			fixed: 'right'
		} ] ];
		
		webplus.init('<%=basePath%>question/getQuestionList',cols);
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
		    		 webplus.openWindowBase('<%=basePath%>question/editQuestion?id=' + data.id, '试题详情', '550', '700', '1');
		    	   }
		   			break;
		    	case 'edit':
		    		//打开编辑弹出框
		    	   if(webplus.checkRowEditMode(obj)){
			    		 webplus.openWindowBase('<%=basePath%>question/editQuestion?id=' + data.id, '编辑试题', '550', '700');
		    	   }
		   			break;
		    	
		    	case 'del':
		    		if(webplus.checkRowEditMode(obj)){
		    			var params = {id : data.id};
		    			webplus.doAjax('<%=basePath%>question/delQuestion',params,'确定删除吗？','','0');//0：刷新当前页面，1：刷新父级页面
		    		}
		    		break;
		    }
		  });
		 
		 //监听表头按钮事件
		  table.on('toolbar(dataList)', function(obj){
		    var checkStatus = table.checkStatus(obj.config.id);
		    switch(obj.event){
		      	case 'addQuestion':
			         //添加试题
			         webplus.openWindowBase('<%=basePath%>question/addQuestion','添加试题','550','700');
		     	     break;
		      	case 'impQuestion':
			         //批量导入
			         webplus.openWindowBase('<%=basePath%>question/impQuestion','批量导入','500','550', '1');
		     	     break;
		    }
		  });	
			

		});
	</script>
  
  
  </script>
</body>
</html>
