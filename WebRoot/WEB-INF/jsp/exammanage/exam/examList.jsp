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
						<label class="layui-form-label">标题</label>
						<div class="layui-input-block">
							<input class="layui-input" name="title" id="title" value="" autocomplete="off">
						</div>
					</div>
					<div class="layui-inline" >
						<label class="layui-form-label">试卷类型</label>
						<div class="layui-input-block">
							<select name="exam_type">
								<option value="">请选择</option>
								<option value="1">正式试卷</option>
								<option value="0">模拟试卷</option>
							</select>
						</div>
					</div>
					<div class="layui-inline" >
						<label class="layui-form-label">适用行业</label>
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
	{{# if (d.flag_show_exam == 1) { }}
	<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="show_exam">预览试卷</a>
	{{# } }}
	<a class="layui-btn layui-btn-xs" lay-event="resultTj">成绩统计</a>
	<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="show"><i class="layui-icon layui-icon-edit"></i>详情</a>
	<c:if test="${QX.del == 1}">
		{{# if (d.flag_edit == 1) { }}
    	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon layui-icon-delete"></i>删除</a>
    	{{# } }}
	</c:if>
</div>
<div type="text/html" id="toolbar" style="display:none" >
  <c:if test="${QX.add == 1}">
	  <div class="layui-btn-container">
	  	<button class="layui-btn layui-btn-sm" lay-event="addExam">新增试卷</button>
	  </div>
  </c:if>
</div>
<script type="text/html" id="statusTr" style="display:none">
  	{{# if(d.flag_edit == 1) { }}
	   <input type="checkbox" name="status" value="{{d.id}}" lay-skin="switch" lay-filter="status" lay-text="已发布|未发布" {{d.status == '2'?'checked':''}}>
	{{# } }}
	{{# if(d.flag_edit == 0) { }}
	   已发布
	{{# } }}
</script>
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
			field : 'status',
			hide:true
		},{
			field : 'flag_online',
			hide:true
		},{
			field : 'title',
			title : '标题',
			width : 450,
			align : 'center'
		},{
			field : 'exam_type',
			title : '试卷类型',
			align : 'center'
		},{
			field : 'flag_online_name',
			title : '线上/下线试卷',
			align : 'center'
		},{
			field : 'total_score',
			title : '总分',
			align : 'center'
		},{
			field : 'time_num',
			title : '时长',
			align : 'center'
		},{
			field : 'industry_name',
			title : '适用行业',
			align : 'center'
		},{
			title : '是否发布',
			toolbar : '#statusTr',
			align : 'center'
		},{
			title : '操作',
			toolbar : '#trTool',
			width : 300,
			align : 'left',
			fixed: 'right'
		} ] ];
		
		webplus.init('<%=basePath%>exam/getExamList',cols);
		 //行单击事件
		  table.on('row(dataList)', function(obj){
			  obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
		  });
	      
		 //监听行工具事件
		  table.on('tool(dataList)', function(obj){
		    var data = obj.data;
		    //判断操作的按钮类别  需要在标签上配置  lay-event='判断的类型值'
		    switch(obj.event){
			    case 'show_exam':
		    		//打开详情弹出框
		    	   if(webplus.checkRowEditMode(obj)){
		    		 webplus.openWindowBase('<%=basePath%>exam/showExam?id=' + data.id + 
		    				 '&title=' + data.title + '&flag_online=' + data.flag_online, '预览试卷(' + data.title + ')', '700', '', '1');
		    	   }
	   			break;
	   			
		    	case 'show':
		    		//打开详情弹出框
		    	   if(webplus.checkRowEditMode(obj)){
		    		 webplus.openWindowBase('<%=basePath%>exam/editExam?id=' + data.id, '试卷信息', '550', '700', '1');
		    	   }
		   		break;
		    	case 'del':
		    		if(webplus.checkRowEditMode(obj)){
		    			var params = {id : data.id};
		    			webplus.doAjax('<%=basePath%>exam/delExam',params,'确定删除吗？','','0');//0：刷新当前页面，1：刷新父级页面
		    		}
		    		break;
		    	case 'resultTj':
		    		if(webplus.checkRowEditMode(obj)){
		    			var params = {id : data.id};
		    			webplus.openWindowBase('<%=basePath%>exam/resultTj?id=' + data.id + '&title=' + data.title, '成绩统计图', '900', '700', '1');
		    		}
		    		break;
		    }
		  });
		 
		 //监听表头按钮事件
		  table.on('toolbar(dataList)', function(obj){
		    var checkStatus = table.checkStatus(obj.config.id);
		    switch(obj.event){
		      	case 'addExam':
			         //添加试题
			         webplus.openWindowBase('<%=basePath%>exam/addExam','添加试卷','550','700');
		     	     break;
		    }
		  });	
		  //监听启用操作
		  form.on('switch(status)', function(obj){
			  if(webplus.checkFormEditMode(obj)){
				  var status="1";//1：未发布 2：已发布
		    	   if(obj.elem.checked){
		    		   status="2";
		    	   }
		    	   var params={};
		    	   params.status=status;
		    	   params.id=obj.value;
		    	   webplus.doAjax("<%=basePath%>exam/updateExamStatus",params,"","","0");
			  }
			 
		  });
			

		});
	
	 
	</script>
  
  
  </script>
</body>
</html>
