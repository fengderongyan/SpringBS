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
		<title>法律法规</title>
	</head>
	<body>
		<div class="news_content">
			<div class="info_title justifyS">
				<span class="title_txt">法律法规</span>
			</div>
			<!-- 消息列表 -->
			<div class="news_page" id="infoListId">
			</div>
			<!-- 分页 -->
			<div id="pageDiv" style="margin-bottom: 15px"></div>
		</div>
		<script type="text/javascript">
			layui.use(['laypage', 'layer'], function(){
				var laypage = layui.laypage;
				layer = layui.layer;
				//完整功能
			  	laypage.render({
				    elem: 'pageDiv'
				    ,count: '${total}'
				    ,layout: ['count', 'prev', 'page', 'next', 'skip']
			  		,groups:5
			  		,limit : 15	
				    ,jump: function(obj){
				    	$.ajax({
								type : 'post',
								url : '<%=basePath%>web/index/getLawInfoList',
								data : {page:obj.curr, limit : obj.limit},
								//async:false,//这一步很重要
								timeout:30000,
								dataType :'json',
								success : function(data) {//&quot;
									$('#infoListId').html('');
									for(var i=0;i<data.length;i++){
										$('#infoListId').append(
												'<a href="<%=basePath%>web/index/getLawInfoDetail?id=' + data[i].id + '" target="_blank"  class="news_item justifyS">' +
													'<span class="news_title">' + data[i].title + '</span>' +
													'<span class="news_date">' + data[i].rec_date + '</span>' +
												'</a>'
										);
									}
								},
								error : function(jqXHR, textStatus, errorThrown) {
									
								}
						});
				    }
			  	});
				
			});
		
		</script>
	</body>
</html>
