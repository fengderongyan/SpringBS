﻿<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
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
						<label class="layui-form-label">类型名称</label>
						<div class="layui-input-block">
							<input type="text" name="title" placeholder="请输入"
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
	<button class="layui-btn layui-btn-sm" name="checkExpert" onclick="returnValues('{{ d.title }}', '{{ d.expert_id }}', '{{ d.video }}', '{{ d.id }}');">选择</button>
</div>

<div type="text/html" id="cover_img" style="display:none">
	<img class="layui-upload-img" id="cover_img_url" src="{{ d.photo }}"  width="70px">
</div>

<script>
	var $;
	var layer;
	layui.config({
		base :  'static/layuiadmin/' //静态资源所在路径
	}).extend({
		webplus  : 'lib/webplus' //主入口模块
	}).use([ 'table', 'form','webplus', 'layer' ], function() {
		$ = layui.$, 
		form = layui.form, 
		table = layui.table,
		webplus=layui.webplus,
		layer = layui.layer;
		var cols=[ [ {
			title : '选择',
			toolbar : '#trTool',
			width : 75,
			align : 'left',
			fixed: 'right'
		},{
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
			field : 'photo',
			hide:true
		},{
			field : 'login_id',
			hide:true
		},{
			field : 'expert_id',
			hide:true
		},{
			field : 'video',
			hide:true
		},{
			field : 'is_edit',
			hide:true
		},{
			field : 'title',
			title : '视频标题',
			align : 'center'
		},{
			field : 'introduction',
			title : '视频简介',
			align : 'center'
		},{
			field : 'username',
			title : '录入人',
			align : 'center'
		} ] ];
		
		webplus.init('<%=basePath%>videomanage/video/getInformaticaManageList',cols);
		 //行单击事件
		  table.on('row(dataList)', function(obj){
			  obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
		  });
		});
		
		function returnValues(title, expertId, video, video_id){
    		$("#type_name", parent.document).val(title);
    		$("#video_name", parent.document).val(title);
		    $("#expert_id", parent.document).val(expertId);
		    $("#video_id", parent.document).val(video_id);
		    $("#video", parent.document).val(video);
            parent.layer.closeAll();
		}
		
	</script>
  
  
  </script>
</body>
</html>