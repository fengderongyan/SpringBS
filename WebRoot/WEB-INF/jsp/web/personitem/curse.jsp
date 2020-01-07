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
		<title></title>
	</head>
	<body>
		<div class="per_title">个人信息</div>
		<div class="curse_list" id="curse_list_div_id">
				
		</div>
		<div id="pageDiv" style="margin-bottom: 15px"></div>
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
			  		,limit : 5	
				    ,jump: function(obj){
				    	$.ajax({
								type : 'post',
								url : '<%=basePath%>web/personal/curseList',
								data : {page:obj.curr, limit : obj.limit},
								//async:false,//这一步很重要
								timeout:30000,
								dataType :'json',
								success : function(data) {//&quot;
									$('#curse_list_div_id').html('');
									for(var i=0;i<data.length;i++){
										$('#curse_list_div_id').append(
												'<a href="<%=basePath%>web/personal/curseDetail?lesson_id=' + data[i].lesson_id + '"  target="_blank" class="curse_item justifyS"> ' +
														'<div class="curse_left justify"> ' +
															'<div class="thumb_img"> ' +
																'<img src="' + data[i].cover + '"  class="curse_cover"> ' +
																
															'</div> ' +
															'<div class="curse_txt directionAr"> ' +
																'<div>' + data[i].title +'</div> ' +
																'<span>' + data[i].rec_date + '</span> ' +
																'<span>' + data[i].name + '</span> ' +
															'</div> ' +
														'</div> ' + 
														'<button type="button" class="right_btn" >去学习</button> ' +
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
