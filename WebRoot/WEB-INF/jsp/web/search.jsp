<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = 
			
			path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<%@ include file="/static/web/webinclude.jsp" %>
		<link rel="stylesheet" href="static/layuiadmin/layui/css/layui.css"  media="all">
		<script src="static/layuiadmin/layui/layui.js" charset="utf-8"></script>
		<title>搜索界面</title>
	</head>
	<body>
		<div class="search-input-wrapper">
			<div class="search">
				<input value="" autofocus="" id="title">
				<button onclick="searchInfo();" id="search_info"><i class="anticon anticon-search search-btn-icon"></i>&nbsp;搜索</button>
			</div>
			<div class="search_result" id="search_result">
				<div class="empty_content">暂无信息</div>
			</div>
			<div id="pageDiv" style="margin-bottom: 15px"></div>
		</div>
		<script type="text/javascript">
			$("#title").keyup(function(event){
				　　if(event.keyCode==13){
				　　		searchInfo();
				　　}
			});
			$("#search_ipt").focus(function() {
				$(this).addClass("border_ac");
			});
			$("#search_ipt").blur(function() {
				$(this).removeClass("border_ac");
			});
			var laypage;
			layui.use(['laypage', 'layer'], function(){
				laypage = layui.laypage;
			});
			
			//分页功能
			function pageFun(count){
				laypage.render({
				    elem: 'pageDiv'
				    ,count: count
				    ,layout: ['count', 'prev', 'page', 'next', 'skip']
			  		,groups:5
			  		,limit : 15	
				    ,jump: function(obj){
				    	$.ajax({
							type: "post",
							url: "<%=basePath%>web/index/searchInfo",
							data:{title : $('#title').val(), page:obj.curr, limit : obj.limit},
							dataType:"json",
							success: function(data) {
								console.log(data);
								if(data.list.length > 0){
									$('#search_result').html('');
									for(var i = 0; i < data.list.length; i++){
										$('#search_result').append(
											'<a class="search_item direction">' +
												'<span class="search_title" onclick="openSearchDetail(&quot;'+data.list[i].info_id+'&quot;,&quot;' + data.list[i].type + '&quot;)">' + data.list[i].title + '</span>' +
												'<span class="search_time">发布时间：' + data.list[i].rec_date + '    ' + data.list[i].name + '</span>' +
											'</a>'
										);
									}
								}else{
									$('#search_result').html('<div class="empty_content">暂无信息</div>');
								}
							},
							error: function(error) {
								console.log(error)
							}
						});
				    }
			  	});
			}
			//搜索内容
			function searchInfo(){
				$.ajax({
					type: "post",
					url: "<%=basePath%>web/index/searchInfo",
					data:{title : $('#title').val()},
					dataType:"json",
					success: function(data) {
						pageFun(data.count);
					},
					error: function(error) {
						console.log(error)
					}
				});
				return false;
			}
			
			function openSearchDetail(id, type){
				if(type =='accident'){
					window.open("<%=basePath%>web/index/accidentDetail?id=" + id);
				}else if(type=='home'){
					window.open("<%=basePath%>web/index/homeInfoDetail?id=" + id);
				}else if(type=='safe'){
					window.open("<%=basePath%>web/index/getSafeInfoDetail?id=" + id);
				}else if(type=='law'){
					window.open("<%=basePath%>web/index/getLawInfoDetail?id=" + id);
				}
			}
			
		</script>
	</body>
</html>
